package one.empty3.library1.tree;

import java.util.ArrayList;
import java.util.List;

public class ControlledInstructions extends Instruction {
    public String controlExpression;

    public ControlledInstructions(String controlExpression) {
        super(null);
        this.controlExpression = controlExpression;
    }

    public String getControlExpression() {
        return controlExpression;
    }

    public void setControlExpression(String controlExpression) {
        this.controlExpression = controlExpression;
    }

    public List<InstructionBlock> getInstructions() {
        return instructionList;
    }

    public void setInstructions(ArrayList<InstructionBlock> instructions) {
        this.instructionList = instructions;
    }

    public static class If extends ControlledInstructions {
        public InstructionBlock instructionsElse = new InstructionBlock();

        public If(String controlExpression) {
            super(controlExpression);
        }
    }

    public static class For extends ControlledInstructions {

        public Instruction firstForInstruction;

        public For(String controlExpression) {
            super(controlExpression);
        }
    }

    public static class While extends ControlledInstructions {
        public While(String controlExpression) {
            super(controlExpression);
        }
    }

    public static class DoWhile extends ControlledInstructions {
        public DoWhile(String controlExpression) {
            super(controlExpression);
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder array1 = new StringBuilder();
        for (InstructionBlock instruction : getInstructions()) {
            array1.append(instruction.toString() + ";");
        }

        switch (getClass().getName()) {
            case "If" -> stringBuilder.append("if(" + controlExpression + ") {\n" +
                    array1.toString() + "\n");
            case "While" -> stringBuilder.append("while(" + controlExpression + ") {\n" +
                    ";" + array1.toString() + "}\n");
            case "Do" -> stringBuilder.append("do {\n" +
                    array1.toString()
                    + "} while(" + controlExpression + ")\n");
            case "ControlledInstructions" -> stringBuilder.append("{\n" +
                    array1.toString()
                    + "}\n");
        }
        return stringBuilder.toString();
    }
}
