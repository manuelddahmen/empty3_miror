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

import one.empty3.library.StructureMatrix;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * The StringAnalyzer1 class is responsible for analyzing string inputs and performing parsing operations.
 * It contains methods for parsing and retrieving constructs.
 *
 * @see AlgebraicTree
 */
public class StringAnalyzer1 {
    protected HashMap<Integer, Token> definitions = new HashMap<>();
    private HashMap<String, Class> classes;
    private int index = 0;

    /**
     * Retrieves the Construct object from the current instance.
     *
     * @return the Construct object
     */
    @NotNull
    protected Construct getConstruct() {
        return construct;
    }

    /**
     * Represents a token in a parsing process.
     */
    public abstract class Token {
        protected Action action;
        protected Class aClass;
        protected Method method;
        protected Variable variable;
        private boolean successful = false;
        private final StructureMatrix<Token> nextTokens = new StructureMatrix<>(1, Token.class);

        public Token() {
        }

        public Token addToken(Token token) {
            index++;
            definitions.put(index, token);
            this.nextTokens.setElem(token, this.nextTokens.getData1d().size());
            return this;
        }

        public int nextToken(String input, int position) {
            if (!nextTokens.getData1d().isEmpty()) {
                return nextTokens.getData1d().get(0).parse(input, position);
            }
            return position;
        }

        public Token nextToken() {
            if (!nextTokens.getData1d().isEmpty()) {
                return nextTokens.getData1d().get(0);
            }
            return null;
        }

        public void setAction(Action action) {
            this.action = action;
        }

        /***
         * Skips over any blank spaces in the input string starting from the given position.
         *
         * @param input    the input string
         * @param position the starting position
         * @return the new position after skipping the blank spaces
         */
        public int skipBlanks(String input, int position) {
            boolean passed = false;
            int position1 = position;
            while (position1 < input.length() && (Character.isSpaceChar(input.charAt(position1)) || Character.isWhitespace(input.charAt(position1)))) {
                position1++;
                passed = true;
            }
            int position2 = passed ? position1 : position;//(input.length() > position2 ? input.substring(position2) : "")+
            System.out.printf("\n\n\nClasse\t" + getClass() + "\nPosition\t" + position2 + "/" + input.length() + "\nReste <<<<\n" +
                    (input.length() > position2 ? input.substring(position2) : "")
                    + "\n>>>>\ntoString()\n" + this + "\n\n");
            return position2;
        }

        /***
         * Parses the input string starting from the given position and skips over any blank spaces.
         *
         * @param input    the input string
         * @param position the starting position
         * @return the new position after skipping the blank spaces
         */
        public int parse(String input, int position) {
            position = skipBlanks(input, position);
            return position;
        }

        /**
         * Determines whether the current operation was successful or not.
         *
         * @return true if the operation was successful, false otherwise
         */
        protected boolean isSuccessful() {
            return successful;
        }

        /**
         * Sets the success state of the current operation.
         *
         * @param successful the flag indicating whether the operation was successful
         */
        public void setSuccessful(boolean successful) {
            this.successful = successful;
            if (successful && action != null)
                action();
            if (!successful) {
                System.err.println("<<<Error : " + toString() + ">>>");
            }
        }

        /**
         * Retrieves the action associated with the token.
         *
         * @return the action associated with the token
         */
        public Action getAction() {
            return action;
        }

        /**
         * Executes the action associated with the token, if it is not null.
         */
        public void action() {
            if (action != null) getAction().action();
        }

        /**
         * Processes the next token in the input string starting from the given position.
         *
         * @param input    the input string
         * @param position the starting position
         * @return the new position after processing the next token
         */
        protected int processNext(String input, int position) {
            if (nextToken() != null) {
                int nextToken = nextToken(input, position);
                if (nextToken().isSuccessful()) {
                    setSuccessful(true);
                    return nextToken;
                } else {
                    setSuccessful(false);
                    return position;
                }
            } else {
                setSuccessful(true);
                return position;
            }
        }

        @Override
        public String toString() {
            return getClass().getName() + "{" +
                    "action=" + action +
                    ", aClass=" + aClass +
                    ", method=" + method +
                    ", variable=" + variable +
                    ", successful=" + successful +
                    "}\n";
        }

        public StructureMatrix<Token> getNextToken() {
            return nextTokens;
        }
    }

    /***
     * The Action class represents an action that can be performed in a parsing process.
     * It is an abstract class that must be subclassed to implement the action logic.
     */
    public abstract class Action {
        protected final Token token;

        /**
         * Retrieves the token associated with the current action.
         *
         * @return the token associated with the current action
         */
        public Token getToken() {
            return token;
        }

        /**
         * This class represents an action that can be performed in a parsing process.
         * It is an abstract class that must be subclassed to implement the action logic.
         */
        public Action(Token token) {
            this.token = token;
            token.action = this;

        }

        /**
         * Executes the action associated with the token.
         *
         * @return true if the action was executed successfully, false otherwise
         */
        public abstract boolean action();
    }
/*
    class TokenPrivacyModifier extends TokenChoiceStringMandatory {

        public TokenPrivacyModifier() {
            super(new String[]{"private", "public", "protected"});

        }

        @Override
        public int parse(String input, int position) {
            position = super.parse(input.substring(position), position) + position;
            if (position >= input.length() || input.substring(position).trim().isEmpty()) {
                setSuccessful(true);
                return position;
            }
            for (String searched : names) {
                if (input.substring(position).startsWith(searched)) {
                    setSuccessful(true);
                    return position + searched.length();
                }
            }
            return 0;
        }
    }
*/

    /**
     * The SingleTokenExclusiveXor class represents a token that matches only one of the specified tokens in an exclusive XOR manner.
     * It extends the Token class and overrides the parse method to implement the exclusive XOR logic.
     */
    class SingleTokenExclusiveXor extends Token {
        private final List<Token> choices;

        public SingleTokenExclusiveXor(Token... mandatory) {
            super();
            this.choices = Arrays.stream(mandatory).toList();
        }

        @Override
        public int parse(String input, int position) {
            if (position >= input.length() || input.substring(position).trim().isEmpty()) {
                setSuccessful(false);
                return position;
            }
            position = super.parse(input, position);
            int position1 = position;
            int i = 0;
            int position0 = position1;
            for (Token token : choices) {
                position1 = token.parse(input, position0);
                if (token.isSuccessful()) {
                    return processNext(input, position1);
                }
            }
            setSuccessful(false);
            return position0;

        }
    }

    /**
     * Represents a mandatory choice token, which matches one of the given string values.
     * Extends the {@link Token} class.
     */
    class TokenChoiceStringMandatory extends Token {
        protected final String[] names;
        protected String choice = "";

        public TokenChoiceStringMandatory(String[] values) {
            super();
            this.names = values;
        }

        @Override
        public int parse(String input, int position) {
            if (position >= input.length() || input.substring(position).trim().isEmpty()) {
                setSuccessful(false);
                return position;
            }
            int position1 = super.parse(input, position);
            int position2 = position1;
            boolean success = false;
            for (String s : names) {
                if (position2 < input.length() && input.substring(position2).startsWith(s)) {
                    this.choice = s;
                    position2 = position2 + s.length();
                    success = true;
                    break;
                }
            }
            if (success) {
                return processNext(input, position2);
            } else {
                setSuccessful(false);
                return position1;
            }
        }

        public String getChoice() {
            return choice;
        }

        @Override
        public String toString() {
            return "TokenChoiceStringMandatory{" +
                    "names=" + Arrays.toString(names) +
                    ", choice='" + choice + '\'' +
                    '}';
        }
    }

    class TokenClassScope extends TokenChoiceStringMandatory {
        public TokenClassScope() {
            super(new String[]{"public", "private", "package", "protected"/*, ""*/});
        }
    }

    class TokenConstantModifier extends TokenChoiceStringMandatory {
        public TokenConstantModifier() {
            super(new String[]{"final"/*, ""*/});
        }

    }


    class TokenCodeFile extends Token {
        public TokenCodeFile() {
            super();
            aClass = new Class();
        }

        @Override
        public int parse(String input, int position) {
            if (position >= input.length() || input.substring(position).trim().isEmpty()) {
                setSuccessful(true);
                return input.length();
            }
            position = super.parse(input, position);
            return processNext(input, position);

        }
    }

    class TokenChoiceInclusive extends Token {
        protected List<Token> choices;

        public TokenChoiceInclusive(Token... choices) {
            super();
            this.choices = Arrays.stream(choices).toList();
        }

        @Override
        public int parse(String input, int position) {
            if (position >= input.length() || input.substring(position).trim().isEmpty()) {
                setSuccessful(true);
                return input.length();
            }
            position = super.parse(input.substring(position), position) + position;
            for (Token token : choices) {
                int position1 = position;
                position1 = token.parse(input.substring(position1), position);
                if (token.isSuccessful()) {
                    setSuccessful(true);
                    return position1;
                }
            }
            setSuccessful(false);
            return position;
        }

        @Override
        public String toString() {
            return "TokenChoiceInclusive{" +
                    "choices=" + choices +
                    '}';
        }
    }


    /***
     * Tous les tokens choisis et aucun autre.
     */
    class TokenChoiceExclusive extends Token {
        protected final Token[] choices;

        public TokenChoiceExclusive(Token... choices) {
            super();
            this.choices = choices;
        }

        @Override
        public int parse(String input, int position) {
            if (position >= input.length() || input.substring(position).trim().isEmpty()) {
                setSuccessful(true);
                return input.length();
            }
            position = super.parse(input.substring(position), position) + position;
            int position1 = position;
            for (Token token : choices) {
                position1 = position;
                position1 = token.parse(input.substring(position1), position);
                if (!token.isSuccessful()) {
                    setSuccessful(false);
                    return position;
                }
            }
            setSuccessful(true);
            return position;
        }

        @Override
        public String toString() {
            return "TokenChoiceExclusive{" +
                    "choices=" + Arrays.toString(choices) +
                    '}';
        }
    }

    class TokenPackage extends TokenString {
        public TokenPackage() {
            super("package");
        }
    }

    class TokenQualifiedName extends TokenName {
        public TokenQualifiedName() {
            super();
        }

        @Override
        public int parse(String input, int position) {
            if (position >= input.length() || input.substring(position).trim().isEmpty()) {
                setSuccessful(true);
                return input.length();
            }
            position = super.skipBlanks(input, position);
            int position1 = position;
            int i = position1;
            boolean passed = false;
            while (i < input.length() && (Character.isLetterOrDigit(input.charAt(i))
                    || Character.isAlphabetic(input.charAt(i))
                    || input.charAt(i) == '_' || input.charAt(i) == '.')) {
                passed = true;
                i++;
            }
            if (passed) {
                if (!input.substring(position1, i).isEmpty()) {
                    setName(input.substring(position1, i));
                    return processNext(input, i);

                }
            }
            setSuccessful(false);
            return position1;
        }
    }

    class TokenClassKeyword extends TokenString {

        public TokenClassKeyword() {
            super("class");
        }

    }

    /**
     * Represents a token for matching a specific string in a parsing process.
     */
    class TokenString extends Token {
        protected String name;

        public TokenString(String name) {
            super();
            this.name = name;
        }

        @Override
        public int parse(String input, int position) {
            if (position >= input.length() || input.substring(position).trim().isEmpty()) {
                throw new RuntimeException("TokenString : position>=input.length()");
                //                setSuccessful(true);
                //                return input.length();
            }
            position = super.parse(input, position);
            if (position < input.length() && input.substring(position).startsWith(name)) {
                int position1 = position + name.length();
                if (!getNextToken().getData1d().isEmpty()) {
                    int position2 = nextToken(input, position1);
                    if (nextToken().isSuccessful()) {
                        setSuccessful(true);
                        return position2;
                    } else {
                        setSuccessful(false);
                        return position1;
                    }
                } else {
                    setSuccessful(true);
                    return position1;
                }
            } else {
                setSuccessful(false);
                return position;
            }
        }

        @Override
        public String toString() {
            return getClass().getName() + "<=TokenString{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    class TokenOpenBracket extends TokenString {
        public TokenOpenBracket() {
            super("{");

        }

    }

    class TokenComa extends TokenString {
        public TokenComa() {
            super(",");
        }

    }

    class TokenCloseBracket extends TokenString {
        public TokenCloseBracket() {
            super("}");

        }

    }

    class TokenOpenParenthesized extends TokenString {
        public TokenOpenParenthesized() {
            super("(");
        }

    }

    class TokenCloseParenthesized extends TokenString {

        public TokenCloseParenthesized() {
            super(")");
        }

    }

    /**
     * Represents a token class for handling multiple optional tokens.
     */
    class MultiTokenOptional extends Token {

        private final Token[] choices;

        public MultiTokenOptional(Token... choices) {
            super();
            this.choices = choices;
        }

        @Override
        public int parse(String input, int position) {
            if (position >= input.length() || input.substring(position).trim().isEmpty()) {
                setSuccessful(true);
                return input.length();
            }
            position = super.parse(input, position);
            boolean allNotOk = false;
            int position1 = position;
            int position2 = position1;
            while (!allNotOk) {
                allNotOk = true;
                for (Token token : choices) {
                    position2 = token.parse(input, position1);
                    if (!token.isSuccessful()) {
                        position2 = position1;
                    } else {
                        allNotOk = false;
                        position1 = position2;
                    }
                }
            }
            setSuccessful(true);
            return position1;
        }
    }

    /**
     * Represents a single optional token in a parsing process.
     * Extends the base class Token.
     */
    class SingleTokenOptional extends Token {

        private final Token choice;

        public SingleTokenOptional(Token choice) {
            super();
            this.choice = choice;
        }

        @Override
        public int parse(String input, int position) {
            if (position >= input.length() || input.substring(position).trim().isEmpty()) {
                setSuccessful(true);
                return input.length();
            }
            position = super.parse(input, position);
            int position1 = position;
            int position2 = choice.parse(input, position1);
            return processNext(input, position2);
        }
    }

    /**
     * Represents a multi-token that is mandatory for the parsing process.
     */
    class MultiTokenMandatory extends Token {

        private final List<Token> choices;

        public MultiTokenMandatory(Token... mandatory) {
            super();
            this.choices = Arrays.stream(mandatory).toList();
        }

        @Override
        public int parse(String input, int position) {
            if (position >= input.length() || input.substring(position).trim().isEmpty()) {
                setSuccessful(true);
                return input.length();
            }
            position = super.parse(input, position);
            boolean allOk = true;
            int position1 = position;
            int i = 0;
            int position0 = position1;
            while (allOk) {
                for (Token token : choices) {
                    position1 = token.parse(input, position1);
                    if (!token.isSuccessful()) {
                        if (i > 0) {
                            allOk = false;
                            break;
                        } else {
                            allOk = false;
                            setSuccessful(false);
                            return position;
                        }
                    }
                }
                if (allOk)
                    position0 = position1;
                i++;
            }
            return processNext(input, position0);


        }

        @Override
        public String toString() {
            return "MultiTokenMandatory{" +
                    "choices=" + choices +
                    ", successful=" + isSuccessful() + '}';
        }
    }

    class TokenMethodMemberDefinition extends TokenName {
        public TokenMethodMemberDefinition() {
            super();
        }
    }

    class TokenVariableMemberDefinition extends TokenName {
        public TokenVariableMemberDefinition() {
            super();
        }
    }


    /**
     * Represents a token that identifies a name in a parsing process.
     */
    class TokenName extends Token {
        private String name;

        public TokenName() {
            super();
        }

        @Override
        public int parse(String input, int position) {
            if (position >= input.length() || input.substring(position).trim().isEmpty()) {
                setSuccessful(true);
                return input.length();
            }
            int position1 = super.parse(input, position);
            int i = position1;
            boolean passed = false;
            while (i < input.length() && (Character.isLetterOrDigit(input.charAt(i)) || input.charAt(i) == '_' || input.charAt(i) == '.')) {
                i++;
                passed = true;
            }
            if (passed && i - position1 > 0) {
                this.setName(input.substring(position1, i));
                return processNext(input, i);
            } else {
                setSuccessful(false);
                return position1;
            }

        }


        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return getClass().getName() + "{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    class TokenVariableMemberDefinitionClassName extends TokenName {
        public TokenVariableMemberDefinitionClassName() {
            super();
        }
    }

    class TokenVariableInMethodName extends TokenName {
        public TokenVariableInMethodName() {
            super();
        }
    }

    class TokenEquals extends TokenString {
        public TokenEquals() {
            super("=");
        }
    }


    /**
     * Represents a token for parsing expressions in a given input string.
     * Extends the Token class.
     */
    class TokenExpression extends Token {
        public TokenExpression() {
            super();
        }

        @Override
        public int parse(String input, int position) {
            if (position >= input.length() || input.substring(position).trim().isEmpty()) {
                setSuccessful(true);
                return input.length();
            }
            position = super.parse(input, position);
            int i = position;
            boolean passed = false;
            while (i < input.length() && input.charAt(i) != ';') {
                i++;
                passed = true;
            }
            if (passed && (i >= input.length() || input.charAt(i) == ';')) {
                return processNext(input, i);

            } else {
                setSuccessful(false);
                return position;
            }
        }
    }

    /**
     * Represents a specific type of Token.
     */
    class TokenExpression1 extends Token {
        public TokenExpression1() {
            super();
        }

        protected boolean isValid(String input, int p) {
            return p >= input.length() || (input.charAt(p) == ';');
        }

        protected boolean isValid2(String input, int p) {
            return p >= input.length() || (input.charAt(p) == '{' || input.charAt(p) == '}');
        }

        @Override
        public int parse(String input, int position) {
            if (position >= input.length() || input.substring(position).trim().isEmpty()) {
                setSuccessful(true);
                return input.length();
            }
            position = super.parse(input, position);
            int i = position;
            boolean passed = false;
            while (i < input.length() && isValid(input, i) && isValid2(input, i)) {
                i++;
                passed = true;
            }
            if (passed && !isValid(input, i)) {
                return processNext(input, i);

            } else {
                setSuccessful(false);
                return position;
            }
        }
    }

    class TokenSemiColon extends TokenString {
        public TokenSemiColon() {
            super(";");
        }
    }

    /**
     * This class represents a StringAnalyzer1 object that can perform parsing operations on a string input.
     */
    public StringAnalyzer1() {
        TokenQualifiedName packageQualifiedName = new TokenQualifiedName();
        Action actionPackageName = new Action(packageQualifiedName) {
            @Override
            public boolean action() {
                construct.packageName = getToken().isSuccessful() ? ((TokenQualifiedName) getToken()).getName() : "";
                return true;
            }
        };
        Token isFinal = new TokenConstantModifier();
        Action actionConstantModifier = new Action(isFinal) {
            @Override
            public boolean action() {
                if (getToken().isSuccessful()) {
                    String choice = (((TokenConstantModifier) token)).getChoice();
                    construct.currentClass.setFinal(true);
                }
                return true;
            }
        };
        TokenName className = new TokenName();
        Action setNewClassName = new Action(className) {
            @Override
            public boolean action() {
                if (token.isSuccessful()) {
                    String name = ((TokenName) getToken()).getName();
                    construct.currentClass.setPackageName(construct.packageName);
                    construct.currentClass.setName(name);
                    if (construct.currentClass != null) {

                    }
                    actualContext.setCurrentClassname(construct.currentClass);
                    return true;
                }
                return false;
            }
        };
        Token closeBracket = new TokenCloseBracket();
        Action actionCloseClassBracket = new Action(closeBracket) {
            @Override
            public boolean action() {
                //((TokenCloseBracket)getToken())
                if (token.isSuccessful()) {
                    construct.cited.put(construct.packageName + "." + construct.currentClass.getName(), construct.currentClass);
                    construct.currentClass = new Class();
                }
                return true;
            }
        };
        TokenClassScope tokenVariableScope = new TokenClassScope();
        Action action = new Action(tokenVariableScope) {
            @Override
            public boolean action() {
                if (token.isSuccessful()) {
                    Variable variable = new Variable();
                    construct.currentClass.getVariableList().add(variable);
                    variable.setScope(tokenVariableScope.getChoice());

                }
                return true;
            }
        };
        TokenQualifiedName tokenQualifiedNameVariable = new TokenQualifiedName();

        Action actionQualifiedNameVariable = new Action(tokenQualifiedNameVariable) {
            @Override
            public boolean action() {
                if (token.isSuccessful()) {
                    List<Variable> variableList = construct.currentClass.getVariableList();
                    if (variableList.isEmpty())
                        variableList.add(new Variable());
                    variableList.get(variableList.size() - 1).setName(((TokenQualifiedName) token).getName());

                }
                return true;
            }
        };
        TokenClassScope tokenMethodScope = new TokenClassScope();
        Action actionClassScope = new Action(tokenMethodScope) {
            @Override
            public boolean action() {
                List<Method> methodList = construct.currentClass.getMethodList();
                if (methodList.isEmpty())
                    methodList.add(new Method());
                if (token.isSuccessful()) {
                    methodList.get(methodList.size() - 1).setScope(((TokenClassScope) token).getChoice());
                    return true;
                }
                return false;
            }
        };
        TokenMethodMemberDefinition tokenMethodMemberDefinition = new TokenMethodMemberDefinition();
        Action actionMethodName = new Action(tokenMethodScope) {
            @Override
            public boolean action() {
                List<Method> methodList = construct.currentClass.getMethodList();
                if (methodList.isEmpty())
                    methodList.add(new Method());
                if (token.isSuccessful()) {
                    methodList.get(methodList.size() - 1).setName(((TokenName) token).getName());
                    return true;
                }
                return false;
            }
        };


        TokenConstantModifier tokenConstantModifierMethod = new TokenConstantModifier();
        TokenConstantModifier tokenConstantModifier = new TokenConstantModifier();
        TokenName tokenNameReturnType = new TokenName();


        TokenVariableInMethodName tokenVariableInMethodName = new TokenVariableInMethodName();
        Action actionTokenVariableInMethodName = new Action(tokenVariableInMethodName) {
            @Override
            public boolean action() {
                if (token.isSuccessful()) {
                    String name = ((TokenVariableInMethodName) token).getName();
                    List<Instruction> instructions = construct.methodMembers.get(construct.methodMembers.size() - 1).getInstructions();
                    Instruction instruction = instructions.get(instructions.size() - 1);
                    instruction.setType(name);
                }
                return true;
            }
        };

        Token endOfInstruction = new TokenSemiColon();
        Action actionEndOfInstruction = new Action(endOfInstruction) {
            @Override
            public boolean action() {
                if (token.isSuccessful()) {
                    List<Instruction> instructions = construct.methodMembers.get(construct.methodMembers.size() - 1).getInstructions();
                    instructions.add(new Instruction());
                    construct.methodMembers.get(construct.methodMembers.size() - 1)
                            .setInstructions(instructions);
                    Instruction instruction = instructions.get(instructions.size() - 1);
                    return true;
                }
                return false;
            }
        };
        Token tokenBeginOfMethod = new TokenOpenBracket();
        Action actionBeginOfInstructions = new Action(tokenBeginOfMethod) {
            @Override
            public boolean action() {
                if (token.isSuccessful()) {
                    construct.methodMembers.get(construct.methodMembers.size() - 1).setInstructions(new ArrayList<>());
                    construct.methodMembers.get(construct.methodMembers.size() - 1).getInstructions().add(new Instruction());
                    return true;
                }
                return false;
            }
        };
        TokenClassKeyword tokenClassKeyword = new TokenClassKeyword();
        Action actionClassKeyword = new Action(tokenClassKeyword) {
            @Override
            public boolean action() {
                if (token.isSuccessful()) {
                    construct.methodMembers = new HashMap<>();
                    construct.fieldMembers = new HashMap<>();
                    construct.currentClass = new Class();
                    construct.currentClass.setPackageName(construct.packageName);
                    construct.currentMethod = new Method();
                    construct.currentField = new Variable();
                    return true;
                }
                return false;
            }
        };
        Token aPackage = definitions.put(0, new MultiTokenMandatory(
                new MultiTokenOptional(new TokenString("package"), packageQualifiedName, new TokenSemiColon()),
                new MultiTokenOptional(new TokenClassScope(), isFinal,
                        new MultiTokenMandatory(tokenClassKeyword, className, new TokenOpenBracket()),
                        new MultiTokenOptional(new MultiTokenMandatory(
                                // Variables
                                new MultiTokenOptional(tokenVariableScope, tokenConstantModifier), tokenQualifiedNameVariable),
                                new MultiTokenOptional(new TokenEquals(), new TokenExpression(),
                                        new TokenSemiColon())),// Commit changes
                        // Methods
                        new MultiTokenOptional(
                                new MultiTokenMandatory(new MultiTokenOptional(tokenNameReturnType, tokenMethodScope,
                                        tokenConstantModifierMethod), tokenMethodMemberDefinition,
                                        // Arguments' list
                                        new MultiTokenMandatory(new TokenOpenParenthesized(),
                                                new MultiTokenOptional(new MultiTokenMandatory(
                                                        new TokenVariableMemberDefinitionClassName(), new TokenName(), new TokenComa()
                                                ))),
                                        new TokenCloseParenthesized(),
                                        // Instructions' block
                                        new MultiTokenMandatory(
                                                tokenBeginOfMethod,
                                                new MultiTokenOptional(new MultiTokenMandatory(tokenVariableInMethodName
                                                        , new TokenName(), new TokenEquals()),
                                                        new TokenExpression1()), /*new TokenComa(),*/ endOfInstruction))),// Commit changes
                        closeBracket), closeBracket));// Commit changes

    }


    static class ActualContext {
        private enum ContextType {Classname, FieldName, MethodName, Instruction}

        private ContextType currentContextType;
        private Class currentClassname;
        private Variable currentFieldName;
        private Method currentMethodName;

        public ActualContext() {

        }

        public ActualContext(ContextType currentContextType, Class currentClassname, Variable currentFieldName, Method currentMethodName) {
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

        public Class getCurrentClassname() {
            return currentClassname;
        }

        public void setCurrentClassname(Class currentClassname) {
            this.currentClassname = currentClassname;
        }

        public Variable getCurrentFieldName() {
            return currentFieldName;
        }

        public void setCurrentFieldName(Variable currentFieldName) {
            this.currentFieldName = currentFieldName;
        }

        public Method getCurrentMethodName() {
            return currentMethodName;
        }

        public void setCurrentMethodName(Method currentMethodName) {
            this.currentMethodName = currentMethodName;
        }
    }

    /*
        static class ActualContext {
            private enum ContextType {Classname, FieldName, MethodName, Instruction}

            private ContextType currentContextType;
            private String currentClassname;
            private String currentFieldName;
            private String currentMethodName;

            public ActualContext() {

            }

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
    */
    public class Construct {
        public Variable currentField;
        public Method currentMethod;
        protected String packageName = "";
        protected Class currentClass = new Class();
        protected HashMap<String, Class> cited = new HashMap<>();
        protected HashMap<String, Variable> fieldMembers = new HashMap<>();
        protected HashMap<String, Method> methodMembers = new HashMap<>();


        public void construct() {
            Token token = definitions.get(0);
        }

        @Override
        public String toString() {
            return "Construct{" +
                    "currentField=" + currentField +
                    ", currentMethod=" + currentMethod +
                    ", packageName='" + packageName + '\'' +
                    ", currentClass=" + currentClass +
                    ", cited=" + cited +
                    ", fieldMembers=" + fieldMembers +
                    ", methodMembers=" + methodMembers +
                    '}';
        }
    }

    private final Construct construct = new Construct();
    private final ActualContext actualContext = new ActualContext();

    public int parse(String input) {
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e) {
                }
                System.exit(1);
            }
        }.start();
        Token token = definitions.get(0);
        int position1 = token.parse(input, 0);
        if (token.isSuccessful()) {
            return position1;
        } else {
            return -1;
        }
    }
}
