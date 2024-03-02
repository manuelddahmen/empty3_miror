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

import org.jetbrains.annotations.Nullable;

public class Instruction {
    @Nullable
    private String type;
    @Nullable
    private String name;
    private ListInstructions.Instruction expression = new ListInstructions.Instruction(0, null, null);

    public void setName(String name) {

    }

    public void setType(String name) {
        this.type = name;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public ListInstructions.Instruction getExpression() {
        return expression;
    }

    public void setExpression(ListInstructions.Instruction expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "Instruction{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", expression=" + expression +
                '}';
    }
}
