package one.empty3.library.core.raytracer.tree.derivative;

import one.empty3.library.core.raytracer.tree.*;

import java.util.concurrent.atomic.AtomicInteger;

public class AlgebricTreeDerivative {

    public TreeNode getDerivative(String variable, TreeNode currentTreeNode,
                                  TreeNode derivate) {

        Class<? extends TreeNodeType> type = currentTreeNode.getType().getClass();


        if (VariableTreeNodeType.class.equals(type)) {
            TreeNodeDouble treeNodeDouble = new TreeNodeDouble(derivate, new Object[]{}, new DoubleTreeNodeType());
            if (currentTreeNode.getExpressionString().equals(variable)) {
                treeNodeDouble.getType().setValues(new Object[]{1.0});
            } else {
                treeNodeDouble.getType().setValues(new Object[]{0.0});
            }
            return treeNodeDouble;
        } else if (TermTreeNodeType.class.equals(type)) {

            TermTreeNodeType termTreeNodeType = new TermTreeNodeType(+1.0);

            TreeNode treeNode = new TreeNode(derivate, new Object[]{currentTreeNode.getExpressionString()},
                    termTreeNodeType);

            currentTreeNode.getChildren().forEach(tn -> {
                derivate.getChildren().add(getDerivative(variable, tn, derivate));
            });
            return treeNode;
        } else if (FactorTreeNodeType.class.equals(type)) {

            TermTreeNodeType termTreeNodeType = new TermTreeNodeType(+1.0);

            for (int i = 0; i < currentTreeNode.getChildren().size(); i++) {

                TreeNode treeNode = new TreeNode(derivate, new Object[]{currentTreeNode.getExpressionString()},
                        termTreeNodeType);
                AtomicInteger j= new AtomicInteger();
                int finalI = i;
                currentTreeNode.getChildren().forEach(tn -> {
                    TreeNode treeNode1;
                    if(finalI ==j.get()) {
                        treeNode1 = getDerivative(variable, tn, treeNode);
                    } else {
                        treeNode1 = new TreeNode(tn, new Object[]{currentTreeNode.getExpressionString()},
                                new FactorTreeNodeType(tn.getType().getSign1()));
                    }
                    derivate.getChildren().add(treeNode1);
                    j.getAndIncrement();
                });
            }
            return derivate;

        } else if (ExponentTreeNodeType.class.equals(type)) {

        }

        throw new UnsupportedOperationException("Not yet implemented");
        //return algebricTree;

    }

}
