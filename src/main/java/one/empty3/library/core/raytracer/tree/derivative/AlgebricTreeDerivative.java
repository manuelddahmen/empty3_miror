package one.empty3.library.core.raytracer.tree.derivative;

import com.android.tools.r8.code.D1;
import one.empty3.library.core.raytracer.tree.*;

public class AlgebricTreeDerivative {

    public TreeNode getDerivative(AlgebricTree algebricTree, String variable, TreeNode currentTreeNode,
                                  TreeNode derivate) {
        algebricTree = new AlgebricTree(algebricTree.getFormula(), algebricTree.getParametersValues());

        Class<? extends TreeNodeType> type = currentTreeNode.getType().getClass();

        TreeNodeDouble treeNodeDouble = new TreeNodeDouble(derivate, new Object[]{}, new DoubleTreeNodeType());

        if (VariableTreeNodeType.class.equals(type)) {
            if(currentTreeNode.getExpressionString().equals(variable)) {
                treeNodeDouble.getType().setValues(new Object[] {1.0});
            } else {
                treeNodeDouble.getType().setValues(new Object[] {1.0});
            }
        }
        throw new UnsupportedOperationException("Not yet implemented");
        //return algebricTree;

    }

}
