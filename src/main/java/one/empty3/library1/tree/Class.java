/*
 *
 *  * Copyright (c) 2024. Manuel Daniel Dahmen
 *  *
 *  *
 *  *    Copyright 2024 Manuel Daniel Dahmen
 *  *
 *  *    Licensed under the Apache License, Version 2.0 (the "License");
 *  *    you may not use this file except in compliance with the License.
 *  *    You may obtain a copy of the License at
 *  *
 *  *        http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *    Unless required by applicable law or agreed to in writing, software
 *  *    distributed under the License is distributed on an "AS IS" BASIS,
 *  *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *    See the License for the specific language governing permissions and
 *  *    limitations under the License.
 *
 *
 */

package one.empty3.library1.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Class {
    private String name;
    private List<Variable> variableList = new ArrayList<>();
    private List<Method> methodList = new ArrayList<>();

    public Class() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Variable> getVariableList() {
        return variableList;
    }

    public void setVariableList(List<Variable> variableList) {
        this.variableList = variableList;
    }

    public List<Method> getMethodList() {
        return methodList;
    }

    public void setMethodList(List<Method> methodList) {
        this.methodList = methodList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Class aClass = (Class) o;

        if (!Objects.equals(variableList, aClass.variableList))
            return false;
        return Objects.equals(methodList, aClass.methodList);
    }

    @Override
    public int hashCode() {
        int result = variableList != null ? variableList.hashCode() : 0;
        result = 31 * result + (methodList != null ? methodList.hashCode() : 0);
        return result;
    }
}
