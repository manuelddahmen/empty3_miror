package one.empty3.library.zshapecontrols;

import one.empty3.library.Representable;
import one.empty3.library.ZBufferImpl;
import one.empty3.library.core.nurbs.ParametricCurve;
import one.empty3.library.core.nurbs.ParametricSurface;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

public class ZBufferExtension {
    private final ParametricSurface surface;
    private final Object shapeModifiers;

    public ZBufferExtension(ZBufferImpl zBuffer, ParametricSurface surface) {
        try {
            this.surface = surface;
            shapeModifiers = surface.getProperty("shapeModifiers");
            Class shapeModifiers1 = surface.getPropertyType("shapeModifiers");
            if(shapeModifiers1.equals(ArrayList.class)) {

                ;

            }
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public void draw() {
        for(double i=surface.getStartU(); i<surface.getEndU(); i+=surface.getIncrU())
            for(double j=surface.getStartV(); j<surface.getEndV(); j+=surface.getIncrV()) {

            }
    }


    public void render() {

    }
}
