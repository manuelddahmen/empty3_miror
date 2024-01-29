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

public class StringAnalyser {
    protected HashMap<Integer, Token> definitions = new HashMap<>();
    private HashMap<String, Class> classes;
    private int index = 0;

    @NotNull
    protected Construct getConstruct() {
        return construct;
    }

    abstract class Token {
        protected Action action;
        protected Class aClass;
        protected Method method;
        protected Variable variable;
        private boolean successful = false;
        private StructureMatrix<Token> nextTokens = new StructureMatrix<>(1, Token.class);

        public Token() {
            this.action = action;
        }

        public Token addToken(Token token) {
            index++;
            definitions.put(index, token);
            this.nextTokens.setElem(token, this.nextTokens.getData1d().size());
            index++;
            return this;
        }

        public void setAction(Action action) {
            this.action = action;
        }

        public int skipBlanks(String input, int position) {
            boolean passed = false;
            while (position < input.length() && (Character.isSpaceChar(input.charAt(position)) || Character.isWhitespace(input.charAt(position)))) {
                position++;
                passed = true;
            }
            return passed ? position : 0;
        }

        public int parse(String input, int position) {
            position = skipBlanks(input, position);
            int position1 = 0;
            System.out.printf("\nClasse\t" + getClass() + "\nPosition\t" + position + "\nReste\n<<<<\n" + input.substring(position) + "\n>>>>\n");
            return position1;
        }

        protected boolean isSuccessful() {
            return successful;
        }

        public void setSuccessful(boolean successful) {
            this.successful = successful;
        }

        public Action getAction() {
            return action;
        }

        @Override
        public String toString() {
            return getClass().getName() + "{" +
                    "action=" + action +
                    ", aClass=" + aClass +
                    ", method=" + method +
                    ", variable=" + variable +
                    ", successful=" + successful +
                    '}';
        }

        public StructureMatrix<Token> getNextToken() {
            return nextTokens;
        }
    }

    abstract class Action {
        protected final Token token;

        public Token getToken() {
            return token;
        }

        public Action(Token token) {
            this.token = token;
            token.action = this;

        }

        public abstract boolean action();
    }

    class TokenPrivacyModifier extends TokenChoiceExclusive {
        private String[] values = new String[]{"private", "public", "protected", ""};

        public TokenPrivacyModifier() {


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

    /***
     * Il faut choisir une des strings c'est obligatoire.
     */
    class TokenChoiceStringMandatory extends Token {
        private final String[] names;
        private String choice = "";

        public TokenChoiceStringMandatory(String[] values) {

            this.names = values;
        }

        @Override
        public int parse(String input, int position) {
            position = super.parse(input, position);
            input = input.substring(position);
            for (String s : names) {
                if (input.startsWith(s)) {
                    this.choice = s;
                    setSuccessful(true);
                    return position + s.length();
                }
            }
            return position;
        }

        public String getChoice() {
            return choice;
        }
    }

    class TokenClassScope extends TokenChoiceStringMandatory {
        public TokenClassScope() {
            super(new String[]{"public", "private", "package", "protected", ""});
        }
    }

    class TokenConstantModifier extends TokenChoiceStringMandatory {
        public TokenConstantModifier() {
            super(new String[]{"final"});
        }

    }


    class TokenCodeFile extends Token {
        public TokenCodeFile() {
            super();
            aClass = new Class();
        }

        @Override
        public int parse(String input, int position) {
            position = super.parse(input, position);
            input = input.substring(position);
            if (getNextToken().data1d.size() > 0) {
                position = getNextToken().getElem(0).parse(input, 0);
                if (getNextToken().getElem(0).isSuccessful()) {
                    return position;
                }
            }
            setSuccessful(false);
            return position;
        }
    }

    class TokenChoiceInclusive extends Token {
        protected List<Token> choices;

        public TokenChoiceInclusive(Token... choices) {

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


    /***
     * Tous les tokens choisis et aucun autre.
     */
    class TokenChoiceExclusive extends Token {
        protected final Token[] choices;

        public TokenChoiceExclusive(Token... choices) {

            this.choices = choices;
        }

        @Override
        public int parse(String input, int position) {
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
        public TokenPackage() {
            super(new String[]{"package"});
        }

        @Override
        public int parse(String input, int position) {
            position = super.parse(input, position);
            int position1 = super.parse(input, position);
            return position1;
        }
    }

    class TokenQualifiedName extends TokenName {
        public TokenQualifiedName() {

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

        public TokenClassKeyword() {
            super("class");
        }

    }

    class TokenString extends Token {
        protected String name = "{";

        public TokenString() {

        }

        public TokenString(String aPackage) {

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
        public TokenOpenBracket() {

            name = "{";
        }

    }

    class TokenComa extends TokenString {
        public TokenComa() {

            name = ",";
        }

    }

    class TokenCloseBracket extends TokenString {
        public TokenCloseBracket() {

            name = "}";

        }

    }

    class TokenOpenParenthesized extends TokenString {
        public TokenOpenParenthesized() {

            name = "(";
        }

    }

    class TokenCloseParenthesized extends TokenString {

        public TokenCloseParenthesized() {

            name = ")";
        }

    }

    class MultiTokenOptional extends TokenChoiceInclusive {

        public MultiTokenOptional(Token... options) {

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
                    int position2 = token.parse(input, position1);
                    if (!token.isSuccessful()) {
                        allOk = false;
                        allNotOk = false;
                    } else {
                        position1 = position2;
                    }
                }
            }
            setSuccessful(true);
            if (isSuccessful() && getNextToken().getElem(0) != null)
                return getNextToken().getElem(0).parse(input, position1);
            return position1;
        }
    }

    class MultiTokenMandatory extends TokenChoiceInclusive {

        public MultiTokenMandatory(Token... mandatory) {

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
        public TokenMethodMemberDefinition() {
            super();
        }
    }

    class TokenVariableMemberDefinition extends TokenName {
        public TokenVariableMemberDefinition() {

        }
    }


    class TokenName extends Token {
        private String name;

        public TokenName() {


        }

        @Override
        public int parse(String input, int position) {
            int position1 = super.parse(input, position);
            input = input.substring(position1);
            int i = position1;
            boolean passed = false;
            while (i < input.length() && (Character.isLetterOrDigit(input.charAt(i)) || input.charAt(i) == '_') || input.charAt(i) == '.') {
                i++;
                passed = true;
            }
            if (passed && i - position1 > 0) {
                this.setName(input.substring(position1, i));
                setSuccessful(true);
                return i;
            } else {
                setSuccessful(false);
                return position1;
            }

        }

        private void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    class TokenVariableMemberDefinitionClassName extends TokenName {
        public TokenVariableMemberDefinitionClassName() {

        }
    }

    class TokenVariableInMethodName extends TokenName {
        public TokenVariableInMethodName() {

        }
    }

    class TokenEquals extends TokenString {
        public TokenEquals() {

            name = "=";
        }
    }

    class TokenExpression extends Token {
        public TokenExpression() {

        }
    }

    class TokenSemiColon extends TokenString {
        public TokenSemiColon() {
            super(";");
        }
    }

    public StringAnalyser() {
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
        Token className = new TokenName();
        Action setNewClassName = new Action(className) {
            @Override
            public boolean action() {
                String name = ((TokenName) getToken()).getName();
                construct.currentClass.setPackageName(construct.packageName);
                construct.currentClass.setName(name);
                if (construct.currentClass != null) {

                }
                actualContext.setCurrentClassname(construct.currentClass);
                return true;
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
        Token aPackage = definitions.put(0, new TokenCodeFile().addToken(new MultiTokenOptional(
                new MultiTokenMandatory(new TokenString("package"),
                        packageQualifiedName, new TokenSemiColon()))
                .addToken(new MultiTokenOptional(new TokenClassScope(),
                        isFinal).addToken(tokenClassKeyword.addToken(className).addToken(new TokenOpenBracket())
                                .addToken(new MultiTokenOptional(new Token[]{
                                        // Variables
                                        new MultiTokenOptional(new Token[]{tokenVariableScope, tokenConstantModifier})
                                                .addToken(tokenQualifiedNameVariable)}).addToken(new TokenSemiColon()))// Commit changes
                                // Methods
                                .addToken(new MultiTokenOptional(new Token[]{tokenNameReturnType, tokenMethodScope
                                        .addToken(tokenConstantModifierMethod).addToken(tokenMethodMemberDefinition)
                                        // Arguments' list
                                        .addToken(new TokenOpenParenthesized()).addToken(new MultiTokenOptional(new MultiTokenMandatory(
                                                new TokenVariableMemberDefinitionClassName(), new TokenName(), new TokenComa()
                                        )))
                                        .addToken(new TokenCloseParenthesized())
                                        // Instructions' block
                                        .addToken(tokenBeginOfMethod.
                                        addToken(new MultiTokenOptional(new MultiTokenMandatory(tokenVariableInMethodName
                                                , new TokenName(), new TokenEquals())
                                                .addToken(new TokenExpression()), new TokenComa()).addToken(endOfInstruction)// Commit changes
                                        ))})))
                        .addToken(closeBracket))));// Commit changes

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
    class Construct {
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
        Token token = definitions.get(0);
        int position1 = token.parse(input, 0);
        if (token.isSuccessful()) {
            return position1;
        } else {
            return -1;
        }
    }
}
