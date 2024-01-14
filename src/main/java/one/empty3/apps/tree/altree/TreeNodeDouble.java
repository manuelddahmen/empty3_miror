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

package one.empty3.apps.tree.altree;

import one.empty3.apps.tree.altree.DoubleTreeNodeType;
import one.empty3.apps.tree.altree.TreeNode;

/*__
 * Created by manue on 01-07-19.
 */
public class TreeNodeDouble extends one.empty3.apps.tree.altree.TreeNode {
    public TreeNodeDouble(TreeNode src, Object[] objects, DoubleTreeNodeType doubleTreeNodeType) {
        super(src, objects, doubleTreeNodeType);
    }

    @Override
    public Double eval() {
        return (Double) objects[1];
    }
}
