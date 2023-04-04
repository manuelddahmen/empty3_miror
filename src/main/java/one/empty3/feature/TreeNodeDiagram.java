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
    private ClassSchemaBuilder.DiagramElement element;
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
            while (diagramElements1.size() > 0) {
                removed = new ArrayList<>();
                int j = 0;
                for (int i = 0; i < diagramElements1.size(); i++) {
                    ClassSchemaBuilder.ClassElement ce = (ClassSchemaBuilder.ClassElement) diagramElements1.get(i);
                    if (ce.partAfter.element != null || ce.partAfter.element.equals(current)) {
                        removed.add(ce);
                        TreeNodeDiagram treeNodeDiagram = new TreeNodeDiagram();
                        treeNodeDiagram.setElement(ce);
                        current.getChildren().add(treeNodeDiagram);
                        diagramElements1.removeAll(removed);
                        removed.clear();
                        current.getChildren().get(i).addToNode(diagramElements1, treeNodeDiagram,ce, removed);
                        j++;
                    }
                }
                diagramElements1.removeAll(removed);
            }
            return;
        }
    }
    }
}
