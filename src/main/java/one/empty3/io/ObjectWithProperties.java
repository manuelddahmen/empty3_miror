/*
 * Copyright (c) 2023. Manuel Daniel Dahmen
 *
 *
 *    Copyright 2012-2023 Manuel Daniel Dahmen
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package one.empty3.io;

import one.empty3.library.StructureMatrix;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ObjectWithProperties {
    public enum ClassTypes  {
        AtomicInt, AtomicDouble, AtomicChar, AtomicBoolean, String
    }
    public List<Class> realTypes = new ArrayList<>();
    private HashMap<String, ClassTypes> types = new HashMap<>();
    private HashMap<String, StructureMatrix<Object>> values = new HashMap<>();

    public ObjectWithProperties() {
        realTypes.add(AtomicInteger.class);
        realTypes.add(Double.class);
        realTypes.add(Character.class);
        realTypes.add(AtomicBoolean.class);
        realTypes.add(StringBuilder.class);
    }

    public void addProperty(String name, ClassTypes type, Object value) {
        types.put(name, type);
        StructureMatrix<Object> value2 = new StructureMatrix<>(0, Object.class);
        values.put(name, value2);
    }
    public boolean deleteProerty(String name) {
        types.remove(name);
        values.remove(name);
        return true;
    }
    public Object getProperty(String name) {
        return values.get(name).getElem();
    }
    public void updateProperty(String name, Object value) {
        StructureMatrix<Object> objectStructureMatrix = values.get(name);
        if(objectStructureMatrix!=null) {
            objectStructureMatrix.setElem(value);
        } else {
            throw new UnsupportedOperationException("update without previous value");
        }
    }
    public ClassTypes getPropertyType(String name) {
        return types.get(name);
    }
    public Class getPropertyClass(String name) {
        return types.get(name).getClass();
    }

    public Object parseValue(String s) {
        return null;
    }

    public void shareProperties(ObjectWithProperties bis) {
        bis.values = values;
        bis.types = types;
    }
    public Collection<String> getPropertyList() {
        return values.keySet();
    }
}
