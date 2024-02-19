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

/***
 * The Action class represents an action that can be performed in a parsing process.
 * It is an abstract class that must be subclassed to implement the action logic.
 */
public class Action {
    protected StringAnalyzer1.Token token;

    /**
     * Retrieves the token associated with the current action.
     *
     * @return the token associated with the current action
     */
    public StringAnalyzer1.Token getToken() {
        return token;
    }

    /**
     * This class represents an action that can be performed in a parsing process.
     * It is an abstract class that must be subclassed to implement the action logic.
     */
    public Action(StringAnalyzer1.Token token) {
        this.token = token;
        token.setAction(this);

    }

    public void setToken(StringAnalyzer1.Token token) {
        this.token = token;
        token.setAction(this);
    }

    /**
     * Executes the action associated with the token.
     *
     * @return true if the action was executed successfully, false otherwise
     */
    public boolean action() {
        return false;
    }
}
