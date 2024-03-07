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

public class InstructionBlock {
    protected List<InstructionBlock> instructionList = new ArrayList<>();

    public InstructionBlock() {
    }

    public List<InstructionBlock> getInstructionList() {
        return instructionList;
    }

    public void setInstructionList(List<InstructionBlock> instructionList) {
        this.instructionList = instructionList;
    }

    private String debugString(boolean isDebug, String tokenLangString) {
        return isDebug ? "{" + tokenLangString + "}" : tokenLangString;
    }

    public String toLangStringJava(boolean debug) {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder array1 = new StringBuilder();
        if (this.getClass().isAssignableFrom(InstructionBlock.class)) {
            array1.append(debugString(debug, "{\n"));
        }
        for (InstructionBlock instruction : getInstructionList()) {
            array1.append(instruction.toLangStringJava(debug)).append(debugString(debug, ";"));
        }
        if (this.getClass().isAssignableFrom(InstructionBlock.class)) {
            array1.append(debugString(debug, "}\n"));
        }
        System.out.println(getClass().getSimpleName());
        switch (getClass().getSimpleName()) {
            case "If" ->
                    stringBuilder.append(debugString(debug, "if(")).append(debugString(debug, ((ControlledInstructions.If) this).controlExpression)).append(debugString(debug, ") {\n")).append(debugString(debug, array1.toString())).append("\n");
            case "While" -> stringBuilder.append(debugString(debug, "while(")).append(
                            debugString(debug, ((ControlledInstructions.While) this).controlExpression))
                    .append(debugString(debug, ") {\n")).append(debugString(debug, ";"))
                    .append(debugString(debug, array1.toString())).append("}\n");
            case "Do" ->
                    stringBuilder.append(debugString(debug, "do {\n")).append(debugString(debug, array1.toString())).append(debugString(debug, "} while("))
                            .append(debugString(debug, ((ControlledInstructions.DoWhile) this).controlExpression))
                            .append(debugString(debug, ")\n"));
            case "ControlledInstructions" -> stringBuilder.append(debugString(debug, "{\n"))
                    .append(debugString(debug, array1.toString()))
                    .append(debugString(debug, "}\n"));
        }
        return stringBuilder.toString();

    }
}
