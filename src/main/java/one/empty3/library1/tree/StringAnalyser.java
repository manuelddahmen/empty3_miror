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
        private final StructureMatrix<Token> nextTokens = new StructureMatrix<>(1, Token.class);

        public Token() {
        }

        public Token addToken(Token token) {
            index++;
            definitions.put(index, token);
            this.nextTokens.setElem(token, this.nextTokens.getData1d().size());
            return this;
        }

        public void setAction(Action action) {
            this.action = action;
        }

        public int skipBlanks(String input, int position) {
            boolean passed = false;
            int position1 = position;
            while (position1 < input.length() && (Character.isSpaceChar(input.charAt(position1)) || Character.isWhitespace(input.charAt(position1)))) {
                position1++;
                passed = true;
            }
            int position2 = passed ? position1 : position;//(input.length() > position2 ? input.substring(position2) : "")+
            System.out.printf("\n\n\nClasse\t" + getClass() + "\nPosition\t" + position2 + "\nReste <<<<\n" +
                    (input.length() > position2 ? input.substring(position2) : "")
                    + "\n>>>>\ntoString()\n" + this + "\n\n");
            return position2;
        }

        public int parse(String input, int position) {
            position = skipBlanks(input, position);
            return position;
        }

        protected boolean isSuccessful() {
            return successful;
        }

        public void setSuccessful(boolean successful) {
            this.successful = successful;
            if (successful && action != null)
                action();
        }

        public Action getAction() {
            return action;
        }

        public void action() {
            if (action != null) getAction().action();
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

    class TokenPrivacyModifier extends TokenChoiceStringMandatory {

        public TokenPrivacyModifier() {
            super(new String[]{"private", "public", "protected", ""});

        }

        @Override
        public int parse(String input, int position) {
            position = super.parse(input.substring(position), position) + position;

            for (String searched : names) {
                if (input.substring(position).startsWith(searched)) {
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
        protected final String[] names;
        protected String choice = "";

        public TokenChoiceStringMandatory(String[] values) {

            this.names = values;
        }

        @Override
        public int parse(String input, int position) {
            int position1 = super.parse(input, position);
            boolean success = true;
            for (String s : names) {
                if (position1 < input.length()) {
                    if (input.substring(position).startsWith(s)) {
                        this.choice = s;
                        position1 = position1 + s.length();
                    } else {
                        success = false;
                    }
                    position1 = super.parse(input, position1);
                }
            }
            if (success) {
                setSuccessful(true);
                return position1;
            } else {
                setSuccessful(false);
                return position;
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
            super(new String[]{"public", "private", "package", "protected", ""});
        }
    }

    class TokenConstantModifier extends TokenChoiceStringMandatory {
        public TokenConstantModifier() {
            super(new String[]{"final", ""});
        }

    }


    class TokenCodeFile extends Token {
        public TokenCodeFile() {
            super();
            aClass = new Class();
        }

        @Override
        public int parse(String input, int position) {
            position = skipBlanks(input, position);
            if (!getNextToken().data1d.isEmpty()) {
                int position1 = getNextToken().getElem(0).parse(input, position);
                if (getNextToken().getElem(0).isSuccessful()) {
                    setSuccessful(true);
                    return position1;
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

            this.choices = choices;
        }

        @Override
        public int parse(String input, int position) {
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

    class TokenPackage extends TokenChoiceStringMandatory {
        public TokenPackage() {
            super(new String[]{"package"});
        }

        @Override
        public int parse(String input, int position) {
            position = super.parse(input.substring(position), position) + position;
            return position;
        }

    }

    class TokenQualifiedName extends TokenName {
        public TokenQualifiedName() {

        }

        @Override
        public int parse(String input, int position) {
            position = super.skipBlanks(input, position);
            int position1 = position;
            int i = position1;
            boolean passed = false;
            while (i < input.substring(position).charAt(i) && (Character.isLetterOrDigit(input.substring(position).charAt(i))
                    || Character.isAlphabetic(input.substring(position).charAt(i))
                    || input.substring(position).charAt(i) == '_' || input.substring(position).charAt(i) == '.')) {
                passed = true;
                i++;
            }
            if (passed && i < input.length()) {
                if (!input.substring(position1, i).isEmpty()) {
                    setSuccessful(true);
                    setName(input.substring(position1, i));
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
        protected String name;

        public TokenString(String name) {

            this.name = name;
        }

        @Override
        public int parse(String input, int position) {
            position = super.skipBlanks(input, position);
            if (position < input.length() && input.substring(position).startsWith(name)) {
                setSuccessful(true);
                position += name.length();
                if (!getNextToken().getData1d().isEmpty()) {
                    position = getNextToken().getElem(0).parse(input, position);
                    if (getNextToken().getElem(0).isSuccessful()) {
                        return position;
                    }
                }
                return position;
            }
            setSuccessful(false);
            return position;
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

    class MultiTokenOptional extends Token {

        private final Token[] choices;

        public MultiTokenOptional(Token... choices) {
            this.choices = choices;
        }

        @Override
        public int parse(String input, int position) {
            position = super.parse(input, position);
            boolean allOk = true;
            boolean allNotOk = false;
            int position1 = position;
            int position2 = position1;
            while (!allNotOk) {
                allNotOk = true;
                for (Token token : choices) {
                    position2 = token.parse(input, position1);
                    if (!token.isSuccessful()) {
                        allOk = false;
                    } else {
                        allNotOk = false;
                        position1 = position2;
                    }
                }
            }
            if (isSuccessful() && !getNextToken().getData1d().isEmpty()) {
                int position3 = getNextToken().getElem(0).parse(input, position2);
                setSuccessful(getNextToken().getElem(0).isSuccessful());
                if (isSuccessful()) {
                    return position3;
                } else {
                    return position2;
                }
            }
            setSuccessful(true);
            return position2;
        }
    }

    class SingleTokenOptional extends Token {

        private final Token choice;

        public SingleTokenOptional(Token choice) {
            this.choice = choice;
        }

        @Override
        public int parse(String input, int position) {
            position = super.parse(input, position);
            int position1 = position;
            int position2 = choice.parse(input, position1);
            if (choice.isSuccessful()) {
                if (!getNextToken().getData1d().isEmpty()) {
                    int position3 = getNextToken().getElem(0).parse(input, position2);
                    if (getNextToken().getElem(0).isSuccessful()) {
                        setSuccessful(true);
                        return position3;
                    } else {
                        setSuccessful(false);
                        return position3;
                    }
                } else {
                    setSuccessful(true);
                    return position2;
                }
            }
            setSuccessful(false);
            return position2;
        }
    }

    class MultiTokenMandatory extends Token {

        private final List<Token> choices;

        public MultiTokenMandatory(Token... mandatory) {

            this.choices = Arrays.stream(mandatory).toList();
        }

        @Override
        public int parse(String input, int position) {
            position = super.parse(input, position);
            boolean allOk = true;
            int position1 = position;
            int i = 0;
            int position0 = position1;
            while (allOk) {
                for (Token token : choices) {
                    position1 = token.parse(input, position1);
                    if (!token.isSuccessful()) {
                        allOk = false;
                        if (i > 0) {
                            setSuccessful(true);
                            if (!getNextToken().getData1d().isEmpty())
                                return getNextToken().getData1d().get(0).parse(input, position0);
                            return position0;
                        } else {
                            setSuccessful(false);
                            return position;
                        }
                    }
                }
                position0 = position1;
                if (allOk) {
                    setSuccessful(true);
                    if (!getNextToken().getData1d().isEmpty())
                        return getNextToken().getData1d().get(0).parse(input, position0);
                    return position0;

                }
                i++;
            }
            setSuccessful(false);
            return position;
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
            int i = position1;
            boolean passed = false;
            while (i < input.length() && (Character.isLetterOrDigit(input.charAt(i)) || input.charAt(i) == '_' || input.charAt(i) == '.')) {
                i++;
                passed = true;
            }
            if (passed && i - position1 > 0) {
                this.setName(input.substring(position1, i));
                setSuccessful(true);
                if (!getNextToken().getData1d().isEmpty()) {
                    i = getNextToken().getElem(0).parse(input, i);
                    if (getNextToken().getElem(0).isSuccessful()) {
                        return i;
                    } else {
                        setSuccessful(false);
                        return position1;
                    }
                } else {
                    return i;
                }
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

        }
    }

    class TokenVariableInMethodName extends TokenName {
        public TokenVariableInMethodName() {

        }
    }

    class TokenEquals extends TokenString {
        public TokenEquals() {
            super("=");
        }
    }

    class TokenExpression extends Token {
        public TokenExpression() {
        }

        @Override
        public int parse(String input, int position) {
            position = super.skipBlanks(input, position);
            int i = position;
            boolean passed = false;
            while (i < input.length() && input.charAt(i) != ';') {
                i++;
                passed = true;
            }
            if (passed && input.charAt(i) == ';') {
                return i;
            } else {
                return position;
            }
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
        Token aPackage = definitions.put(0, new TokenCodeFile().addToken(new SingleTokenOptional(
                        new MultiTokenMandatory(new TokenString("package"),
                                packageQualifiedName, new TokenSemiColon())))
                .addToken(new MultiTokenOptional(new TokenClassScope(),
                        isFinal).addToken(new MultiTokenMandatory(tokenClassKeyword, className, new TokenOpenBracket()))
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
                .addToken(closeBracket));// Commit changes

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
