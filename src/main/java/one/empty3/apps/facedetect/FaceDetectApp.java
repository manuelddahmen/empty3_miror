/*
 *
 *  * Copyright (c) 2024. Manuel Daniel Dahmen
 *  *
 *  *
 *  *    Copyright 2024 Manuel Daniel Dahmen
 *  *
 *  *    Licensed under the Apache License, Version 2.0 (the "License");
 *  *    you may not use this file except in compliance with the License.
 *  *    You may obtain a copy of the License at
 *  *
 *  *        http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *    Unless required by applicable law or agreed to in writing, software
 *  *    distributed under the License is distributed on an "AS IS" BASIS,
 *  *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *    See the License for the specific language governing permissions and
 *  *    limitations under the License.
 *
 *
 */

package one.empty3.apps.facedetect;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionScopes;
import com.google.api.services.vision.v1.model.*;
import com.google.api.services.vision.v1.model.Image;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.collect.ImmutableList;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import one.empty3.library.Point3D;
import one.empty3.modelling.Face;
//import com.google.cloud.vision.v1.AnnotateImageRequest;
//import com.google.cloud.vision.v1.AnnotateImageResponse;
//import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
//import com.google.cloud.vision.v1.Feature;
//import com.google.cloud.vision.v1.Feature.Type;
//import com.google.cloud.vision.v1.Image;
//import com.google.cloud.vision.v1.ImageAnnotatorClient;
//import com.google.cloud.vision.v1.ImageSource;

import java.awt.*;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.*;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javax.imageio.ImageIO;

public class FaceDetectApp {
    private static final String OUTPUT_BUCKET_NAME = "output-pictures";
    private static final String INPUT_BUCKET_NAME = "input-pictures";
    private static Storage storage = StorageOptions.getDefaultInstance().getService();
    private static final String APPLICATION_NAME = "MeshMask";
    private static final int MAX_RESULTS = 10;
    private static String projectId;
    private final Vision vision;
    private HashMap<String, Polygon> polys = new HashMap<>();
    private String[][][] landmarks0 = {{{"LEFT_EYE", "RIGHT_EYE", "FOREHEAD_GLABELLA"}}};
    private String[][][] landmarks = {{{"LEFT_EYE", "RIGHT_EYE", "FOREHEAD_GLABELLA"}}};

    public FaceDetectApp(Vision visionService) {
        this.vision = visionService;
    }

    public void initStructurePolygons() {
        landmarks = new String[][][]{
                {
                        {"LEFT_EAR_TRAGION", "CHIN_LEFT_GONION", "CHIN_GNATHION", "LEFT_CHEEK_CENTER"},
                        {"MOUTH_LEFT", "UPPER_LIP", "MOUTH_RIGHT", "MOUTH_CENTER"},
                        {"LEFT_EYE_LEFT_CORNER", "LEFT_EYE_TOP_BOUNDARY", "LEFT_EYE_RIGHT_CORNER", "LEFT_EYE_BOTTOM_BOUNDARY"},
                        {"LEFT_OF_LEFT_EYEBROW", "LEFT_EYEBROW_UPPER_MIDPOINT", "RIGHT_OF_LEFT_EYEBROW"},
                        {"MIDPOINT_BETWEEN_EYES", "NOSE_TIP", "NOSE_BOTTOM_LEFT"},
                },
                {
                        {"RIGHT_EAR_TRAGION", "CHIN_RIGHT_GONION", "CHIN_GNATHION", "RIGHT_CHEEK_CENTER"},
                        {"MOUTH_LEFT", "LOWER_LIP", "MOUTH_RIGHT", "MOUTH_CENTER"},
                        {"RIGHT_EYE_LEFT_CORNER", "RIGHT_EYE_TOP_BOUNDARY", "RIGHT_EYE_RIGHT_CORNER", "RIGHT_EYE_BOTTOM_BOUNDARY"},
                        {"LEFT_OF_RIGHT_EYEBROW", "RIGHT_EYEBROW_UPPER_MIDPOINT", "RIGHT_OF_RIGHT_EYEBROW"},
                        {"MIDPOINT_BETWEEN_EYES", "NOSE_TIP", "NOSE_BOTTOM_RIGHT"},
                }, {
                {"NOSE_TIP", "NOSE_BOTTOM_RIGHT", "NOSE_BOTTOM_CENTER", "NOSE_BOTTOM_LEFT"}

        }
        };
        polys = new HashMap<>();
    }

    public void frontal(BufferedImage img, FaceAnnotation faceAnnotation) {
        String[] landmarks0 = new String[]{"LEFT_EAR_TRAGION", "RIGHT_EAR_TRAGION", "CHIN_GNATHION"};
        Position[] leftTragion = {null};
        final Position[] rightTragion = {null};
        final Position[] chinGnathion = {null};
        faceAnnotation.getLandmarks().stream().forEach(new Consumer<Landmark>() {
            @Override
            public void accept(Landmark landmark) {
                if (landmark.getType().equals(landmarks0[0])) {
                    leftTragion[0] = landmark.getPosition();
                } else if (landmark.getType().equals(landmarks0[1])) {
                    rightTragion[0] = landmark.getPosition();
                } else if (landmark.getType().equals(landmarks0[2])) {
                    chinGnathion[0] = landmark.getPosition();
                }
            }
        });
        if (leftTragion[0] != null && rightTragion[0] != null && chinGnathion[0] != null) {
            Point3D le = new Point3D((double) leftTragion[0].getX(), (double) leftTragion[0].getY(), 0.0);
            Point3D re = new Point3D((double) rightTragion[0].getX(), (double) rightTragion[0].getY(), 0.0)
            Point3D me = new Point3D((double) chinGnathion[0].getX(), (double) chinGnathion[0].getY(), 0.0);

            Double a = le.moins(re).norme();
            Point3D vecLr = re.moins(le);
            Point3D quatroFrontol = me.plus(le.plus(vecLr.mult(0.5)).moins(me).mult(2));

            Graphics graphics = img.getGraphics();
            graphics.setColor(Color.RED);
            graphics.drawOval((int)(double)le.getX(), (int)(double)quatroFrontol.getY(),
                    (int)(double)vecLr.norme(), (int)(double) quatroFrontol.moins(me).norme());
        }
    }

    /**
     * Connects to the Vision API using Application Default Credentials.
     */
    public static Vision getVisionService() throws IOException, GeneralSecurityException {
        GoogleCredentials credential =
                GoogleCredentials.getApplicationDefault().createScoped(VisionScopes.all());
        JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
        return new Vision.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                jsonFactory,
                new HttpCredentialsAdapter(credential))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    /**
     * Gets up to {@code maxResults} faces for an image stored at {@code path}.
     */
    public List<FaceAnnotation> detectFaces(Path path, int maxResults) throws IOException {
        byte[] data = Files.readAllBytes(path);

        AnnotateImageRequest request =
                new AnnotateImageRequest()
                        .setImage(new Image().encodeContent(data))
                        .setFeatures(
                                ImmutableList.of(
                                        new Feature().setType("FACE_DETECTION").setMaxResults(maxResults)));
        Vision.Images.Annotate annotate =
                vision
                        .images()
                        .annotate(new BatchAnnotateImagesRequest().setRequests(ImmutableList.of(request)));
        // Due to a bug: requests to Vision API containing large images fail when GZipped.
        annotate.setDisableGZipContent(true);

        BatchAnnotateImagesResponse batchResponse = annotate.execute();
        assert batchResponse.getResponses().size() == 1;
        AnnotateImageResponse response = batchResponse.getResponses().get(0);
        if (response.getFaceAnnotations() == null) {
            throw new IOException(
                    response.getError() != null
                            ? response.getError().getMessage()
                            : "Unknown error getting image annotations");
        }
        return response.getFaceAnnotations();
    }


    /**
     * Annotates an image {@code img} with a polygon around each face in {@code faces}.
     */
    private void annotateWithFaces2(BufferedImage img, FaceAnnotation face) {
        Graphics2D gfx = img.createGraphics();
        Polygon poly = new Polygon();
        BoundingPoly boundingPoly = face.getBoundingPoly();
        for (int i = 0; i < boundingPoly.getVertices().size(); i++) {
            Vertex current = boundingPoly.getVertices().get(i);
            if (current.getX() != null && current.getY() != null) {
                poly.addPoint(current.getX(), current.getY());
            }
        }
        //poly.addPoint(boundingPoly.getVertices().get(0).getX(),
        //        boundingPoly.getVertices().get(0).getY());
        polys.put("FACE", poly);
        gfx.setStroke(new BasicStroke(2));
        gfx.setColor(new Color(0x00ff00));
        gfx.draw(poly);
    }

    int landmarkIndex = 0;

    private void writePolygonsData(BufferedImage img, FaceAnnotation face) {
        Graphics2D gfx = img.createGraphics();
        face.getLandmarks().forEach(new Consumer<Landmark>() {
            @Override
            public void accept(Landmark landmark) {
                System.out.printf("Landmark #%d\n", landmarkIndex);
                System.out.println("TYPE " + landmark.getType());
                System.out.println("POSITION " + landmark.getPosition());
                Iterator<Map.Entry<String, Object>> iterator = landmark.entrySet().iterator();
                iterator.forEachRemaining(new Consumer<Map.Entry<String, Object>>() {
                    @Override
                    public void accept(Map.Entry<String, Object> next) {
                        //Map.Entry<String, Object> next = iterator.next();
                        System.out.printf("Landmark # %d KEY{%s} TYPE {%s}: %s\n", landmarkIndex, String.valueOf(next.getKey()), String.valueOf(next.getValue().getClass().getCanonicalName()), String.valueOf(next.getValue()));
                        if ((next.getValue() instanceof Position p) && Arrays.asList(landmarks).contains(landmark.getType())) {
                            if (p.getX() != null && p.getY() != null) {
                                gfx.setStroke(new BasicStroke(2));
                                gfx.setColor(new Color(0x00ff00));
                                gfx.drawOval((int) (double) p.getX(), (int) (double) p.getY(), 1, 1);
                                gfx.drawString(landmark.getType(), (int) (double) p.getX(), (int) (double) p.getY());
                            }
                        }
                        landmarkIndex++;

                    }
                });
                {
                }
            }
        });
    }

    private void writePolygonsDataPoly(BufferedImage img, FaceAnnotation face) {
        initStructurePolygons();
        Graphics2D gfx = img.createGraphics();
        for (int i = 0; i < landmarks.length; i++) {
            for (int j = 0; j < landmarks[i].length; j++) {
                Polygon poly = new Polygon();
                for (int i1 = 0; i1 < landmarks[i][j].length; i1++) {
                    String landMarkType = landmarks[i][j][i1];
                    face.getLandmarks().forEach(landmark -> {
                        if (landmark.getType().equals(landMarkType) &&
                                landmark.getPosition().getX() != null && landmark.getPosition().getY() != null) {
                            poly.addPoint((int) (double) landmark.getPosition().getX(),
                                    (int) (double) landmark.getPosition().getY());
                        }
                    });
                }
                gfx.setStroke(new BasicStroke(2));
                gfx.setColor(new Color(0x0000ff));
                gfx.drawPolygon(poly);
                polys.put(landmarks[i][j][0], poly);

            }
        }
    }

    /**
     * Annotates an image {@code img} with a polygon defined by {@code face}.
     */
    private void annotateWithFace(BufferedImage img, FaceAnnotation face) {
        Graphics2D gfx = img.createGraphics();
        Polygon poly = new Polygon();
        for (Vertex vertex : face.getFdBoundingPoly().getVertices()) {
            if (vertex.getX() != null && vertex.getY() != null) {
                poly.addPoint(vertex.getX(), vertex.getY());
            }
        }
        polys.put("FACE_RECT", poly);
        gfx.setStroke(new BasicStroke(5));
        gfx.setColor(new Color(0x00ff00));
        gfx.draw(poly);
    }

    /**
     * Annotates an image using the Vision API.
     */
    public static void main(String[] args) throws IOException, GeneralSecurityException {
        if (args.length != 2) {
            System.err.println("Usage:");
            System.err.printf(
                    "\tjava %s inputImagePath outputImagePath\n", FaceDetectApp.class.getCanonicalName());
            System.exit(1);
        }
        Path inputPath = Paths.get(args[0]);
        Path outputPath = Paths.get(args[1]);
        if (!outputPath.toString().toLowerCase().endsWith(".jpg")) {
            System.err.println("outputImagePath must have the file extension 'jpg'.");
            System.exit(1);
        }

        FaceDetectApp app = new FaceDetectApp(getVisionService());
        List<FaceAnnotation> faces = app.detectFaces(inputPath, MAX_RESULTS);
        System.out.printf("Found %d face%s\n", faces.size(), faces.size() == 1 ? "" : "s");
        System.out.printf("Writing to file %s\n", outputPath);
        BufferedImage img = ImageIO.read(inputPath.toFile());
        faces.forEach(new Consumer<FaceAnnotation>() {
            @Override
            public void accept(FaceAnnotation faceAnnotation) {
                app.annotateWithFaces(img, faceAnnotation);
                app.annotateWithFaces2(img, faceAnnotation);
                app.writePolygonsDataPoly(img, faceAnnotation);
                app.writePolygonsData(img, faceAnnotation);

            }
        });

        File output_filename = new File(outputPath.toFile().getName() + "-" + UUID.randomUUID() + ".jpg");

        ImageIO.write(img, "jpg", output_filename);

        uploadFile(output_filename);
    }

    private void annotateWithFaces(BufferedImage img, FaceAnnotation faceAnnotation) {
    }

    // upload file to GCS
    public static void uploadFile(File filename) throws IOException {
        // Create a new GCS client
        storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
        // The blob ID identifies the newly created blob, which consists of a bucket name and an object
        // name
        BlobId blobId = BlobId.of(OUTPUT_BUCKET_NAME, filename.getName());
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();


        // upload the file and print the status
        storage.createFrom(blobInfo, Paths.get(filename.getAbsolutePath()));
        System.out.println("File " + filename.getAbsolutePath() + " uploaded to bucket " + OUTPUT_BUCKET_NAME + " as " + filename);
    }
}