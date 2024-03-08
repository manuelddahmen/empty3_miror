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

    public List<InstructionBlock> getInstructionsList() {
        return instructionList;
    }

    public void setInstructionsList(ArrayList<InstructionBlock> instructions) {
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

}
