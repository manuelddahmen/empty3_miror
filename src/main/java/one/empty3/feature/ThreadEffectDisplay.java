package one.empty3.feature;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamImageTransformer;
import com.github.sarxos.webcam.WebcamLockException;
import one.empty3.feature.gui.DirestEffect;
import one.empty3.feature.motion.DiffMotion;
import one.empty3.feature.motion.Motion;
import one.empty3.io.ProcessFile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class ThreadEffectDisplay extends Thread {
    private static ThreadEffectDisplay uniqueObject;
    private BufferedImage image;
    private JPanel jPanel;
    private ClassSchemaBuilder main;
    private BufferedImage imageIn;
    private BufferedImage imageMotion;
    public Webcam webcam;
    private DirestEffect directEffect;
    public Motion motion = null;
    private RunEffect runEffect;
    private String tempDir;
    private BufferedImage imageIn2;
    private boolean motionActive = true;
    private boolean effectActive = true;

    public ThreadEffectDisplay() {

        //ResourceBundle globalSettings = ResourceBundle.getBundle("settings.properties");
        Properties globalSettings = new Properties();
        try {
            globalSettings.load(new FileInputStream("settings.properties"));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        tempDir = globalSettings.getProperty("tempDir") + File.separator;
        new File(tempDir).mkdirs();

        if (uniqueObject == null)
            uniqueObject = this;
        else {
            System.err.println("Error duplicate class viewer (?)");
            //System.exit(-1);
        }
    }

    public void setTempDir(String tempDir) {
        this.tempDir = tempDir;
    }

    @Override
    public void run() {
        if (webcam != null && webcam.isOpen())
            Webcam.getDefault().close();

        webcam = Webcam.getDefault();
        while (directEffect == null) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(!webcam.isOpen()) {
            webcam.setViewSize(new Dimension(directEffect.viewSizes[0]));
            try {
                webcam.open();
            } catch (WebcamLockException exception) {
                exception.printStackTrace();
            }
        }
        webcam.setImageTransformer(bufferedImage -> {

            BufferedImage bufferedImage1 = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(),
                    BufferedImage.TYPE_INT_RGB);
            for (int i = 0; i < bufferedImage.getWidth(); i++)
                for (int j = 0; j < bufferedImage.getHeight(); j++) {
                    bufferedImage1.setRGB(bufferedImage.getWidth() - i - 1, j,
                            bufferedImage.getRGB(i, j));
                }
            return bufferedImage1;
        });

        do {
            main.files.clear();
            boolean add = main.files.add(new File[]{
                    new File("./temp/webcam.jpg")
            });
            image = webcam.getImage();


            try {
                if (image != null)
                    ImageIO.write(image, "jpg", new File(tempDir + File.separator + "webcam.jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            main.buttonGOActionPerformed(null);

            while((image=imageIn)==null) {
                try {
                    Thread.sleep(10);
                    main.buttonGOActionPerformed(null);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            imageIn = null;

            if(isMotionActive()) {
                motion.addFrame(image);
                imageIn = motion.processFrame();
            } else {
                imageIn = image;
            }
            Graphics graphics = jPanel.getGraphics();
            if (imageIn != null) {
                graphics.drawImage(imageIn, 0, 0, jPanel.getWidth(), jPanel.getHeight(), null);
            }

        } while (directEffect.isVisible());


    }

    private boolean isMotionActive() {
        return motionActive;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.imageIn = image;
    }

    public void setJpanel(JPanel panel1) {
        this.jPanel = panel1;
    }

    public void setMain(ClassSchemaBuilder main) {
        this.main = main;
    }

    public void setImageIn(BufferedImage read) {
        imageIn = read;
    }

    public void setImageMotion(BufferedImage process) {
        imageMotion = process;
    }

    public void setDirectEffect(DirestEffect direstEffect) {
        this.directEffect = direstEffect;
    }

    public void setRunEffect(RunEffect runEffect) {
        this.runEffect = runEffect;
    }

    public BufferedImage getImageIn() {
        return imageIn;
    }

    public void setMotionActive(boolean b) {
        this.motionActive = b;
    }

    public void setEffectActive(boolean b) {
        this.effectActive = b;
    }
}
