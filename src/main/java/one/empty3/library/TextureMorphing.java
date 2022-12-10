package one.empty3.library;

import java.awt.image.BufferedImage;
import java.util.Arrays;

public class TextureMorphing extends ITexture {
    private final BufferedImage imageRead2;
    private final BufferedImage imageRead1;
    private final int indexesIntermediates;
    private final LumiereIdent ident;
    private int frameNo;

    public TextureMorphing(BufferedImage imageRead1, BufferedImage imageRead2, int indexesIntermediates) {
        super();
        this.imageRead1 = imageRead1;
        this.imageRead2 = imageRead2;
        this.indexesIntermediates = indexesIntermediates;
        ident = new LumiereIdent();
    }

    public void setFrameNo(int frameNo) {
        this.frameNo = frameNo;
    }

    @Override
    public int getColorAt(double x, double y) {
        int w1 = imageRead1.getWidth();
        int h1 = imageRead1.getHeight();
        int w2 = imageRead2.getWidth();
        int h2 = imageRead2.getHeight();

        double r = (double)frameNo/indexesIntermediates;

        int rgb1 = imageRead1.getRGB((int) (w1 * x), (int) (h1 * x));
        int rgb2 = imageRead2.getRGB((int)(w2*x), (int)(h2*x));
        double [] dRgb1 = Lumiere.getDoubles(rgb1);
        double [] dRgb2 = Lumiere.getDoubles(rgb2);
        double[] d = new double[dRgb2.length];
        for(int i=0; i<dRgb1.length; i++) {
            d[i] = (1-r)*dRgb1[i]+(r)*dRgb2[i];
        }
        return Lumiere.getInt(d);
    }

    @Override
    public MatrixPropertiesObject copy() throws CopyRepresentableError, IllegalAccessException, InstantiationException {
        return null;
    }
}
