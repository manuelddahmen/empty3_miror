package one.empty3.library.zshapecontrols;

import one.empty3.library.Representable;

import java.util.HashMap;

public class Buffer {
    int w, h;
    /**
     * [sx*y+x] = x,y,z+
     */
    double[] points;
    /**
     * [sx*y+x] = r,g,b
     */
    double[] colors;
    /**
     * [sx*y+x] = x,y = i
     */
    int[] numObject;
    /***
     * [sx*y+x] = numObject(u,v)
     */
    double[] u, v;
    /**
     * Retrieve object's instance of class ? extends Representable
     */
    HashMap<Integer, Representable> dict;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Buffer buffer = new Buffer();

        buffer.w = w;
        buffer.h = h;
        buffer.points = points.clone();
        buffer.colors = colors.clone();
        buffer.numObject = numObject.clone();
        buffer.dict = (HashMap<Integer, Representable>) dict.clone();
        buffer.u = u.clone();
        buffer.v = v.clone();

        return buffer;
    }

}
