package one.empty3.library;

import java.awt.image.BufferedImage;
import java.util.Arrays;

public class TextureMorphing extends ITexture {
    private final ITexture imageRead2;
    private final ITexture imageRead1;
    private final int indexesIntermediates;
    private final LumiereIdent ident;
    private int frameNo;

    public TextureMorphing(ITexture imageRead1, ITexture imageRead2, int indexesIntermediates) {
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
    public void timeNext() {
        setFrameNo(frameNo+1);
    }
    @Override
    public int getColorAt(double x, double y) {
        int w1 = 1;
        int h1 = 1;
        int w2 = 1;
        int h2 = 1;

        double r = 1.0*frameNo/indexesIntermediates;

        int rgb1 = imageRead1.getColorAt(x, y);
        int rgb2 = imageRead2.getColorAt(x, y);
        double [] dRgb1 = Lumiere.getDoubles(rgb1);
        double [] dRgb2 = Lumiere.getDoubles(rgb2);
        double[] d = new double[dRgb1.length];
        for(int i=0; i<dRgb1.length; i++) {
            d[i] = (1-r)*dRgb1[i]+(r)*dRgb2[i];
        }
        return Lumiere.getInt(d);
    }

    @Override
    public MatrixPropertiesObject copy() throws CopyRepresentableError, IllegalAccessException, InstantiationException {
        ITexture copy1 = imageRead1.copy();
        ITexture copy2 = imageRead2.copy();
        TextureMorphing morphing = new TextureMorphing(copy1, copy2, indexesIntermediates);
        morphing.setFrameNo(frameNo);
        return  morphing;
    }
}
