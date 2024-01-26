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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class StringAnalyser {
    private HashMap<Integer, Token> definitions = new HashMap<>();
    private HashMap<String, Class> classes;
    private int index = 0;

    abstract class Token {
        private int indCurrent = 0;
        protected Class aClass;
        protected Method method;
        protected Variable variable;
        private boolean successful = false;

        public Token(int indCurrent) {
            this.indCurrent = indCurrent;
            index++;
        }

        public Token addToken(Token token) {
            definitions.put(index, token);
            return token;
        }

        public int skipBlanks(String input, int position) {
            boolean passed = false;
            while (position < input.length() && Character.isSpaceChar(input.charAt(position))) {
                position++;
                passed = true;
            }
            return passed ? position - 1 : 0;
        }

        public int parse(String input, int position) {
            return position = skipBlanks(input, position);
        }

        protected boolean isSuccessful() {
            return successful;
        }

        public void setSuccessful(boolean successful) {
            this.successful = successful;
        }
    }


    class TokenPrivacyModifier extends TokenChoiceExclusive {
        private String[] values = new String[]{"private", "public", "protected", ""};

        public TokenPrivacyModifier(int indexCurrent) {
            super(indexCurrent);

        }

        @Override
        public int parse(String input, int position) {
            position = super.parse(input, position);
            input = input.substring(position);
            for (String searched : values) {
                if (input.startsWith(searched)) {
                    this.setSuccessful(true);
                    construct.currentClass.setAccessModifier(searched);
                    return searched.length();
                }
            }
            return 0;
        }
    }

    class TokenChoiceStringMandatory extends Token {
        private final String[] names;

        public TokenChoiceStringMandatory(int indexCurrent, String[] values) {
            super(indexCurrent);
            this.names = values;
        }

        @Override
        public int parse(String input, int position) {
            position = super.skipBlanks(input, position);
            input = input.substring(position);
            for (String s : names) {
                if (input.startsWith(s)) {
                    setSuccessful(true);
                    return position + s.length();
                }
            }
            return position;
        }
    }

    class TokenClassScope extends TokenChoiceStringMandatory {
        public TokenClassScope(int indexCurrent) {
            super(indexCurrent, new String[]{"static", ""});
        }
    }

    class TokenConstantModifier extends TokenChoiceStringMandatory {
        public TokenConstantModifier(int indexCurrent) {
            super(indexCurrent, new String[]{"final", ""});
        }
    }


    class TokenCodeFile extends Token {
        public TokenCodeFile(int indexCurrent) {
            super(indexCurrent);
            aClass = new Class();
        }

    }

    class TokenChoiceInclusive extends Token {
        protected List<Token> choices;

        public TokenChoiceInclusive(int indexCurrent, Token... choices) {
            super(indexCurrent);
            this.choices = Arrays.stream(choices).toList();
        }

        @Override
        public int parse(String input, int position) {
            position = super.parse(input, position);
            input = input.substring(position);
            for (Token token : choices) {
                int position1 = position;
                position1 = token.parse(input, position);
                if (token.isSuccessful()) {
                    setSuccessful(true);
                    return position1;
                }
            }
            setSuccessful(false);
            return position;
        }
    }

    class TokenChoiceExclusive extends Token {
        protected final Token[] choices;

        public TokenChoiceExclusive(int indCurr, Token... choices) {
            super(indCurr);
            this.choices = choices;
        }

        @Override
        public int parse(String input, int position) {
            position = super.parse(input, position);
            input = input.substring(position);
            position = super.parse(input, position);
            input = input.substring(position);
            int position1 = position;
            for (Token token : choices) {
                position1 = position;
                position1 = token.parse(input, position);
                if (!token.isSuccessful()) {
                    setSuccessful(false);
                    return position;
                }
            }
            setSuccessful(true);
            return position;
        }
    }

    class TokenPackage extends TokenChoiceStringMandatory {
        public TokenPackage(int currIndex) {
            super(currIndex, new String[]{"package"});
        }

        @Override
        public int parse(String input, int position) {
            position = super.parse(input, position);
            int position1 = super.parse(input, position);
            return position1;
        }
    }

    class TokenQualifiedName extends TokenName {
        public TokenQualifiedName(int currIndex) {
            super(currIndex);
        }

        @Override
        public int parse(String input, int position) {
            position = super.parse(input, position);
            int position1 = super.parse(input, position);
            int i = 0;
            boolean passed = false;
            while (i < input.charAt(i) && (Character.isLetterOrDigit(input.charAt(i)) || Character.isAlphabetic(input.charAt(i))
                    || input.charAt(i) == '_')) {
                passed = true;
            }
            if (passed) {
                if (input.substring(position1, i).length() > 0) {
                    setSuccessful(true);
                    return position1 + i;
                }
            }
            setSuccessful(false);
            return position1;
        }
    }

    class TokenClassKeyword extends TokenString {

        public TokenClassKeyword(int currIndex) {
            super(currIndex, "class");
        }

    }

    class TokenString extends Token {
        protected String name = "{";

        public TokenString(int currIndex) {
            super(currIndex);
        }

        public TokenString(int index, String aPackage) {
            super(index);
            this.name = aPackage;
        }

        @Override
        public int parse(String input, int position) {
            position = super.parse(input, position);
            input = input.substring(position);
            if (input.startsWith(name)) {
                setSuccessful(true);
                return position + name.length();
            }
            setSuccessful(false);
            return position;
        }
    }

    class TokenOpenBracket extends TokenString {
        public TokenOpenBracket(int currIndex) {
            super(currIndex);
            name = "{";
        }

    }

    class TokenComa extends TokenString {
        public TokenComa(int currIndex) {
            super(currIndex);
            name = ",";
        }

    }

    class TokenCloseBracket extends TokenString {
        public TokenCloseBracket(int currIndex) {
            super(currIndex);
            name = "}";

        }

    }

    class TokenOpenParenthesized extends TokenString {
        public TokenOpenParenthesized(int currIndex) {
            super(currIndex);
            name = "(";
        }

    }

    class TokenCloseParenthesized extends TokenString {

        public TokenCloseParenthesized(int currIndex) {
            super(currIndex);
            name = ")";
        }

    }

    class MultiTokenOptional extends TokenChoiceInclusive {

        public MultiTokenOptional(int indCurrent, Token... options) {
            super(indCurrent);
            this.choices = Arrays.stream(options).toList();
        }

        @Override
        public int parse(String input, int position) {
            position = super.parse(input, position);
            input = input.substring(position);
            boolean allOk = true;
            boolean allNotOk = false;
            int position1 = position;
            while (allNotOk) {
                allNotOk = true;
                for (Token token : choices) {
                    position1 = token.parse(input, position1);
                    if (!token.isSuccessful()) {
                        allOk = false;
                    } else {
                        allNotOk = false;
                    }
                }
            }
            setSuccessful(allOk);
            return position1;
        }
    }

    class MultiTokenMandatory extends TokenChoiceInclusive {

        public MultiTokenMandatory(int indCurrent, Token... mandatory) {
            super(indCurrent);
            this.choices = Arrays.stream(mandatory).toList();
        }

        @Override
        public int parse(String input, int position) {
            position = super.parse(input, position);
            input = input.substring(position);
            boolean allOk = true;
            int position1 = position;
            boolean allNotOk = false;
            while (!allNotOk) {
                allNotOk = true;
                for (Token token : choices) {
                    position1 = token.parse(input, position1);
                    if (!token.isSuccessful()) {
                        allOk = false;
                        setSuccessful(allOk);
                        return position;
                    } else {
                        allNotOk = false;
                    }
                }
            }
            setSuccessful(allOk);
            return position1;
        }
    }

    class TokenMethodMemberDefinition extends TokenName {
        public TokenMethodMemberDefinition(int indexCurrent) {
            super(indexCurrent);
        }
    }

    class TokenVariableMemberDefinition extends TokenName {
        public TokenVariableMemberDefinition(int indexCurrent) {
            super(indexCurrent);
        }
    }


    class TokenName extends Token {
        public TokenName(int indexCurrent) {
            super(indexCurrent);

        }

        @Override
        public int parse(String input, int position) {
            return super.parse(input, position);
        }
    }

    class TokenVariableMemberDefinitionClassName extends TokenName {
        public TokenVariableMemberDefinitionClassName(int indexCurrent) {
            super(indexCurrent);
        }
    }

    class TokenEquals extends TokenString {
        public TokenEquals(int indexCurrent) {
            super(indexCurrent);
            name = "=";
        }
    }

    class TokenExpression extends Token {
        public TokenExpression(int indexCurrent) {
            super(indexCurrent);
        }
    }

    public StringAnalyser() {
        definitions.put(0, new TokenCodeFile(index).addToken(new TokenChoiceInclusive(index,
                        new MultiTokenMandatory(index, new TokenString(index, "package"), new TokenName(index)), new TokenClassScope(index)))
                .addToken(new TokenConstantModifier(index)).addToken(new TokenClassKeyword(index)).addToken(new TokenOpenBracket(index))
                .addToken(new MultiTokenOptional(index,
                        // Variables
                        new MultiTokenOptional(index,
                                new TokenClassScope(index)
                                        .addToken(new TokenConstantModifier(index)).addToken(
                                                new TokenVariableMemberDefinitionClassName(index))),
                        // Methods
                        new TokenClassScope(index)
                                .addToken(new TokenConstantModifier(index)).addToken(new TokenMethodMemberDefinition(index))
                                // Arguments' list
                                .addToken(new TokenOpenParenthesized(index)).addToken(new MultiTokenOptional(index, new MultiTokenMandatory(index,
                                        new TokenVariableMemberDefinitionClassName(index), new TokenName(index), new TokenComa(index)
                                )))
                                .addToken(new TokenCloseParenthesized(index))
                                // Instructions' block
                                .addToken(new TokenOpenBracket(index).
                                        addToken(new MultiTokenOptional(index, new MultiTokenMandatory(index,
                                                new TokenVariableMemberDefinitionClassName(index), new TokenName(index), new TokenEquals(index))
                                                .addToken(new TokenExpression(index)), new TokenComa(index))
                                        )))
                        .addToken(new TokenOpenBracket(index))));

    }


    static class ActualContext {
        private enum ContextType {Classname, FieldName, MethodName, Instruction}

        private ContextType currentContextType;
        private String currentClassname;
        private String currentFieldName;
        private String currentMethodName;

        public ActualContext(ContextType currentContextType, String currentClassname, String currentFieldName, String currentMethodName) {
            this.currentContextType = currentContextType;
            this.currentClassname = currentClassname;
            this.currentFieldName = currentFieldName;
            this.currentMethodName = currentMethodName;
        }

        public ContextType getCurrentContextType() {
            return currentContextType;
        }

        public void setCurrentContextType(ContextType currentContextType) {
            this.currentContextType = currentContextType;
        }

        public String getCurrentClassname() {
            return currentClassname;
        }

        public void setCurrentClassname(String currentClassname) {
            this.currentClassname = currentClassname;
        }

        public String getCurrentFieldName() {
            return currentFieldName;
        }

        public void setCurrentFieldName(String currentFieldName) {
            this.currentFieldName = currentFieldName;
        }

        public String getCurrentMethodName() {
            return currentMethodName;
        }

        public void setCurrentMethodName(String currentMethodName) {
            this.currentMethodName = currentMethodName;
        }
    }

    class Construct {
        protected Class currentClass = new Class();
        protected List<Class> cited = new ArrayList<>();
        protected List<Variable> fieldMembers = new ArrayList<>();
        protected List<Method> methodMembers = new ArrayList<>();


        public void construct() {
            Token token = definitions.get(0);
        }
    }

    private Construct construct = new Construct();
    private ActualContext actualContext;

    public int parse(String input) {
        return definitions.get(0).parse(input, 0);
    }
}
