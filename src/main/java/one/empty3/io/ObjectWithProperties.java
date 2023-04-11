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
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ObjectWithProperties {
    public enum ClassTypes  {
        AtomicInt, AtomicDouble, AtomicChar, AtomicBoolean, String
    }
    public List<Class> realTypes = new ArrayList<>();
    private HashMap<String, ProcessNFiles> propertyList = new HashMap<>();
    private HashMap<String, ClassTypes> types = new HashMap<>();
    private HashMap<String, StructureMatrix<Object>> values = new HashMap<>();
    ProcessNFiles currentProcess;

    public ObjectWithProperties(ProcessNFiles currentProcess) {
        this.currentProcess = currentProcess;
        realTypes.add(AtomicInteger.class);
        realTypes.add(Double.class);
        realTypes.add(Character.class);
        realTypes.add(AtomicBoolean.class);
        realTypes.add(StringBuilder.class);
    }

    public void addProperty(String name, ClassTypes type, Object value) {
        if(currentProcess.equals(propertyList.get(name))) {
            propertyList.putIfAbsent(name, currentProcess);
            types.put(name, type);
            StructureMatrix<Object> value2 = new StructureMatrix<>(0, Object.class);
            value2.setElem(value);
            values.put(name, value2);
        }
    }
    public boolean deleteProperty(String name) {
        if(currentProcess.equals(propertyList.get(name))) {
            types.remove(name);
            values.remove(name);
            return true;
        }
        return false;
    }
    public Object getProperty(String name) {
        if(currentProcess.equals(propertyList.get(name))) {
            return values.get(name).getElem();
        }
        throw new UnsupportedOperationException("property"+name+" not in "+currentProcess.toString()+" or not defined");

    }
    public void updateProperty(String name, Object value) {
        if(currentProcess.equals(propertyList.get(name))) {
            StructureMatrix<Object> objectStructureMatrix = values.get(name);
            if (objectStructureMatrix != null) {
                objectStructureMatrix.setElem(value);
            } else {
                throw new UnsupportedOperationException("update without previous value");
            }
        }
    }
    public ClassTypes getPropertyType(String name) {
        if(currentProcess.equals(propertyList.get(name))) {
            return types.get(name);
        }
        throw new UnsupportedOperationException("property"+name+" not in "+currentProcess.toString()+" or not defined");
    }
    public Class getPropertyClass(String name) {
        if(currentProcess.equals(propertyList.get(name))) {
            return types.get(name).getClass();
        }
        throw new UnsupportedOperationException("property"+name+" not in "+currentProcess.toString()+" or not defined");
    }

    public Object parseValue(String s) {
        return null;
    }

    public void sharePropertiesWith(ObjectWithProperties bis) {
        Collection<String> propertyListShared = getPropertyList();
        propertyListShared.forEach(s -> {
            Object value = bis.getProperty(s);
            Class propertyClass = bis.getPropertyClass(s);
            ClassTypes propertyType = bis.getPropertyType(s);
            bis.addProperty(s, propertyType, value);
        });
    }
    public Collection<String> getPropertyList() {
        return
                values.keySet().stream()
                        .filter(new Predicate<String>() {
                            @Override
                            public boolean test(String s) {
                                return !currentProcess.equals(propertyList.get(s));
                            }
                        }).collect(Collectors.toList());
    }
}
