/*
 * Copyright (c) 2022. Manuel Daniel Dahmen
 */

package one.empty3.apps.opad.game;

import java.util.ArrayList;
import java.util.List;

public abstract class StarGazer extends BlockUnit {
    private List<Product> products = new ArrayList<>();
    public abstract List<Unit> production();
}
