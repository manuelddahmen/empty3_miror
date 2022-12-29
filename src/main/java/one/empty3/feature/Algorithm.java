/*
 * Copyright (c) 2022. Manuel Daniel Dahmen
 */

package one.empty3.feature;

import java.util.List;

public interface Algorithm {

    public void setPoints(List<DataPoint> points);

    public void cluster();

}