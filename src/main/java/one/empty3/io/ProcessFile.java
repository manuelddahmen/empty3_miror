package one.empty3.io;

import one.empty3.feature.PixM;
import one.empty3.feature.ProcessBean;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class ProcessFile {
    public ProcessBean bean;
    protected int maxRes = 400;
    private Properties property;
    private File outputDirectory = null;
    private List<File> imagesStack = new ArrayList<>();

    public File getOutputDirectory() {
        return outputDirectory;
    }

    public void setOutputDirectory(File outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public PixM getSource(String s) {
        try {
            Properties p = getProperty();
            String property = p.getProperty(s);
            File file = new File(property);
            BufferedImage read = null;
            read = ImageIO.read(file);
            return (new PixM(read));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Properties getProperty() {
        return property;
    }

    public void setProperty(Properties property) {
        this.property = property;
    }

    public boolean process(File in, File out) {
        // in
        // ->
        // out
        // appeler la méthode processMem après avoir sorti
        // de toutes les classes implantées
        // la boucle infinie.
        try {
            boolean filesOk = in != null && in.exists() && out != null && !out.exists();
            if (!filesOk) return false;
            FileInputStream fileInputStream = new FileInputStream(in);
            ImageReader ir = null;
            Iterator<ImageReader> it = ImageIO.getImageReaders(fileInputStream);
            if (it.hasNext())
                ir = it.next();
            else
                return false;
            ImageWriter iw = null;
            Iterator<ImageWriter> itW = ImageIO.getImageWriters(
                    javax.imageio.ImageTypeSpecifier.createFromBufferedImageType(BufferedImage.TYPE_4BYTE_ABGR),
                    "jpg");
            if (itW.hasNext())
                iw = itW.next();
            else
                return false;
            PixM inPix = PixM.getPixM(ir.read(0), maxRes);
            PixM outPix = inPix.copy();

            return processMem(inPix, outPix);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean processMem(PixM in, PixM out) {
        return in != null && out != null;
    }

    public void setMaxRes(int maxRes) {
        this.maxRes = maxRes;
    }

    public File getStackItem(int index) {
        System.out.printf("STACK %d : %s", index, imagesStack.get(index));
        return imagesStack.get(index);
    }

    public void setStack(List<File> files1) {
        this.imagesStack = files1;
    }

    public void addSource(File fo) {
        imagesStack.add(fo);
    }


    protected static boolean isImage(File in) {
        return in!=null && (in.getAbsolutePath().toLowerCase().endsWith(".jpg")
            || in.getAbsolutePath().toLowerCase().endsWith(".png"));
    }

}
