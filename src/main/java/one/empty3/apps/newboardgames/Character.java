/*
 * Copyright (c) 2022. Manuel Daniel Dahmen
 */

package one.empty3.apps.newboardgames;

import one.empty3.library.Representable;

import java.util.HashMap;
import java.util.List;

public class Character {
    HashMap<Integer, List<Representable>> character = new HashMap<Integer, List<Representable>>();

    public HashMap<Integer, List<Representable>> getCharacter() {
        return character;
    }

    public void setCharacter(HashMap<Integer, List<Representable>> character) {
        this.character = character;
    }

}
