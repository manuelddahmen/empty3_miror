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

public class TreeDiagram {
    protected TreeNodeDiagram head = null;

    public TreeDiagram(List<ClassSchemaBuilder.DiagramElement> diagramElements) {
        head = constructTreeReverseProcesses(diagramElements);
    }

    public TreeNodeDiagram constructTreeReverseProcesses(List<ClassSchemaBuilder.DiagramElement> diagramElements) {
        TreeMap<ClassSchemaBuilder.ClassElement, ClassSchemaBuilder.ClassElement> lists = new TreeMap<>();
        List<List<ClassSchemaBuilder.ClassElement>> heads = new ArrayList<>();

        List<ClassSchemaBuilder.DiagramElement> diagramElements1 = new ArrayList<>();

        for (ClassSchemaBuilder.DiagramElement classElement : diagramElements) {
            if (classElement instanceof ClassSchemaBuilder.ClassElement) {
                heads.add(new ArrayList<>());
                heads.get(heads.size()-1).add((ClassSchemaBuilder.ClassElement)classElement);
            }
            diagramElements1.add(classElement);
        }

        head = null;

        for (int i = 0; i < heads.size(); i++) {
            ClassSchemaBuilder.ClassElement classElement = (ClassSchemaBuilder.ClassElement) (heads.get(i).get(0));
            if (classElement instanceof ClassSchemaBuilder.ClassElement) {
                if (classElement.partAfter.element == null || classElement.partAfter.element.equals(null)) {
                    head = new TreeNodeDiagram();
                    head.setElement(classElement);
                    diagramElements1.remove(head.getElement());
                }
            }
        }

        addToNode(diagramElements1, head, new ArrayList<>());
        // Noeuds à tête children>=1
        return null;
    }

    private void addToNode(List<ClassSchemaBuilder.DiagramElement> diagramElements1, TreeNodeDiagram current, List<ClassSchemaBuilder.ClassElement> removed) {

        if(current!=null) {
                for (int i = 0; i < diagramElements1.size(); i++) {
                    ClassSchemaBuilder.ClassElement ce = (ClassSchemaBuilder.ClassElement) diagramElements1.get(i);
                    if (ce.partAfter.element != null || ce.partAfter.element.equals(current)) {
                        removed.add(ce);
                        TreeNodeDiagram treeNodeDiagram = new TreeNodeDiagram();
                        treeNodeDiagram.setElement(ce);
                        diagramElements1.removeAll(removed);
                        removed.clear();
                        current.getChildren().add(treeNodeDiagram);
                        treeNodeDiagram.addToNode(diagramElements1, treeNodeDiagram, removed);
                    }
                }
                diagramElements1.removeAll(removed);
        }
    }

    public void executeOneStart() {
        ClassSchemaBuilder.DiagramElement element = head.getElement();
    }
    public void run() {

        // Trouver les fichiers attachés.
        // "webcam"
        // Pictures Folder
        // Pictures
        // Picture
        // Movie

    }

}
