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

package one.empty3.feature;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class TreeNodeDiagram {
    protected ClassSchemaBuilder.DiagramElement element;
    protected boolean isExecuted = false;
    protected TreeNodeDiagram parentNode;

    public TreeNodeDiagram() {
        children = new ArrayList<>();
    }
    List<TreeNodeDiagram> children;

    public List<TreeNodeDiagram> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNodeDiagram> children) {
        this.children = children;
    }

    public ClassSchemaBuilder.DiagramElement getElement() {
        return element;
    }

    public void setElement(ClassSchemaBuilder.DiagramElement element) {
        this.element = element;
    }

    public void addToNode(List<ClassSchemaBuilder.DiagramElement> diagramElements1,
                          TreeNodeDiagram current,  List<ClassSchemaBuilder.ClassElement> removed ) {
        if (current != null) {
                for (int i = 0; i < diagramElements1.size(); i++) {
                    ClassSchemaBuilder.ClassElement ce = (ClassSchemaBuilder.ClassElement) diagramElements1.get(i);
                    if (ce.partAfter.element != null || ce.partAfter.element.equals(current)) {
                        removed.add(ce);
                        TreeNodeDiagram treeNodeDiagram = new TreeNodeDiagram();
                        treeNodeDiagram.setElement(ce);
                        treeNodeDiagram.setParentNode(this);
                        current.getChildren().add(treeNodeDiagram);
                        diagramElements1.removeAll(removed);
                        removed.clear();
                        current.getChildren().add(treeNodeDiagram);
                        treeNodeDiagram.addToNode(diagramElements1, treeNodeDiagram, removed);
                    }
                }
                diagramElements1.removeAll(removed);
        }
    }

    private void setParentNode(TreeNodeDiagram treeNodeDiagram) {
        this.parentNode = treeNodeDiagram;
    }

    public TreeNodeDiagram getParentNode() {
        return parentNode;
    }

    public boolean isExecuted() {
        return isExecuted;
    }

    public void setExecuted(boolean executed) {
        isExecuted = executed;
    }

    public void execute() {
        for(TreeNodeDiagram treeNodeDiagram : children) {
            if(treeNodeDiagram.isExecuted()) {
            } else {
                if(element instanceof ClassSchemaBuilder.ClassElement) {
                    ClassSchemaBuilder.ClassElement classElement = (ClassSchemaBuilder.ClassElement) element;

                } else if(element instanceof ClassSchemaBuilder.ClassMultiInputElement) {
                    ClassSchemaBuilder.ClassElement classElement = (ClassSchemaBuilder.ClassMultiInputElement) element;
                }

            }
        }
    }

    public List<TreeNodeDiagram> searchForLeaves(List<TreeNodeDiagram> explore, List<TreeNodeDiagram> leaves) {
        if(explore==null)
            explore = new ArrayList<>();
        if(leaves==null)
            leaves = new ArrayList<>();

        if(children.size()>0) {
            for(TreeNodeDiagram tn : children) {
                searchForLeaves(explore, leaves);
            }
        } else {
            leaves.add(this);
        }

        return leaves;
    }

}
