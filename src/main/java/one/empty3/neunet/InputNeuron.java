package one.empty3.neunet;

import one.empty3.feature.PixM;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class InputNeuron extends Neuron {
    private int res;
    private File uri;

    public InputNeuron(int length) {
        super(length);
    }


    public File getUri() {
        return uri;
    }

    public void setUri(File uri) {
        this.uri = uri;
    }


    public boolean loadData(File file) throws Exception {
        PixM p = null;
        try {
            if (ImageIO.getImageReaders(file) == null)
                return false;
            if (!Arrays.asList("jpg", "png").contains(file.getAbsolutePath().substring(
                    file.getAbsolutePath().length() - 3, file.getAbsolutePath().length())))
                return false;
            p = PixM.getPixM(ImageIO.read(file), Config.RES);
            this.setW(new double[Config.RES * Config.RES * 3]);
            for (int j = 0; j < Config.RES; j++)
                for (int i = 0; i < Config.RES; i++) {
                    for (int comp = 0; comp < p.getCompCount(); comp++) {
                        p.setCompNo(comp);
                        this.getW()[ordPix(i, j, comp)] = p.get(i, j);
                    }
                }
            this.uri = file;
            return true;
        } catch (IIOException exception) {
            System.out.println("Error reading image. Returns");
            throw new Exception(exception);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private int ordPix(int i, int j, int comp) {
        return ((j*res+i)*res)*comp+comp;
    }
}