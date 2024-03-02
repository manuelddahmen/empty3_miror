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
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * The StringAnalyzer1 class is responsible for analyzing string inputs and performing parsing operations.
 * It contains methods for parsing and retrieving constructs.
 *
 * @see AlgebraicTree
 */
public class StringAnalyzer1 {
    protected HashMap<Integer, Token> definitions = new HashMap<>();
    protected int mPosition;
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
            index++;
            definitions.put(index, this);
        }

        public void addToken(Token token) {
            this.nextTokens.setElem(token, this.nextTokens.getData1d().size());
        }

        private int nextToken(String input, int position) {
            int parse = position;
            if (!nextTokens.getData1d().isEmpty()) {
                position = nextTokens.getData1d().get(0).parse(input, position);
                setSuccessful(true);
            } else {
                setSuccessful(true);
            }
            return position;
        }

        private Token nextToken() {
            if (!nextTokens.getData1d().isEmpty()) {
                return nextTokens.getData1d().get(0);
            }
            return null;
        }

        public Token setAction(Action action) {
            this.action = action;
            return this;
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
            int position2 = passed ? position1 : position;
            mPosition = position2;
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
            if (getAction() != null) getAction().action();
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
                if (Objects.requireNonNull(nextToken()).isSuccessful()) {
                    setSuccessful(true);
                    if (action != null) action();
                    return nextToken;
                } else {
                    setSuccessful(false);
                    if (action != null) action();
                    return position;
                }
            } else {
                setSuccessful(true);
                if (action != null) action();
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

        /**
         * Creates a copy of the current Token object.
         *
         * @return a new Token object that is a copy of the current Token (further step: make a deep copy with
         * nextTokens and Token type fields.
         */
        public abstract Token copy();
    }

    class MultiTokenExclusiveXor extends Token {
        private final List<Token> choices;

        public MultiTokenExclusiveXor(Token... mandatory) {
            super();
            this.choices = Arrays.stream(mandatory).toList();
        }

        public MultiTokenExclusiveXor(ArrayList<Token> objects) {
            this.choices = objects;
        }

        @Override
        public int parse(String input, int position) {
            if (position >= input.length() || input.substring(position).trim().isEmpty()) {
                mPosition = position;
                setSuccessful(false);
                return position;
//                throw new RuntimeException(getClass() + " : position>=input.length()");
            }
            position = super.skipBlanks(input, position);
            int position1 = position;
            int i = 0;
            int position0 = position1;
            boolean passed = false;
            boolean next = true;
            boolean first = true;
            while (next) {
                next = false;
                for (int j = 0; j < choices.size(); j++) {
                    Token token = choices.get(j);
                    position1 = token.parse(input, position0);
                    if (token.isSuccessful()) {
                        position0 = position1;
                        next = true;
                        break;
                    }
                }

                if (first && !next) {
                    setSuccessful(false);
                    return position;
                }
                first = false;
            }
            return processNext(input, position0);

        }

        @Override
        public Token copy() {
            ArrayList<Token> objects = new ArrayList<>();
            Collections.copy(objects, this.choices);
            return new MultiTokenExclusiveXor(objects);
        }
    }

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

        public SingleTokenExclusiveXor(ArrayList<Token> objects) {
            this.choices = objects;
        }

        @Override
        public int parse(String input, int position) {
            if (position >= input.length() || input.substring(position).trim().isEmpty()) {
                mPosition = position;
                setSuccessful(false);
                return position;
                //throw new RuntimeException(getClass() + " : position>=input.length()");
            }
            position = super.skipBlanks(input, position);
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


        @Override
        public Token copy() {
            ArrayList<Token> objects = new ArrayList<>();
            Collections.copy(objects, this.choices);
            return new SingleTokenExclusiveXor(objects);
        }
    }

    /**
     * Represents a mandatory choice token, which matches one of the given string values.
     * Extends the {@link Token} class.
     */
    class TokenChoiceStringMandatory extends Token {
        protected String[] names = new String[0];
        protected String choice = "";

        public TokenChoiceStringMandatory(String[] values) {
            super();
            this.names = values;
        }

        public TokenChoiceStringMandatory(ArrayList<String> objects) {
            names = new String[objects.size()];
            objects.toArray(this.names);
        }

        @Override
        public int parse(String input, int position) {
            if (position >= input.length() || input.substring(position).trim().isEmpty()) {
                mPosition = position;
                setSuccessful(false);
                return position;
                //throw new RuntimeException(getClass() + " : position>=input.length()");
            }
            int position1 = super.skipBlanks(input, position);
            int position2 = position1;
            for (String s : names) {
                if (position2 < input.length() && input.substring(position2).startsWith(s)) {
                    this.choice = s;
                    position2 = position2 + s.length();
                    return processNext(input, position2);
                }
            }

            setSuccessful(false);
            return position1;
        }

        public String getChoice() {
            return choice;
        }

        @Override
        public String toString() {
            return "TokenChoiceStringMandatory{" +
                    "names=" + Arrays.toString(names) +
                    ", choice='" + choice + '\'' +
                    "}\n";
        }

        @Override
        public Token copy() {
            ArrayList<String> objects = new ArrayList<>();
            Collections.copy(objects, Arrays.stream(this.names).toList());
            return new TokenChoiceStringMandatory(objects);
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
                mPosition = position;
                setSuccessful(false);
                return position;
                //throw new RuntimeException(getClass() + " : position>=input.length()");
            }
            position = super.skipBlanks(input, position);
            return processNext(input, position);

        }

        @Override
        public Token copy() {
            return null;
        }
    }

    /**
     * Represents a token that provides inclusive choices for parsing.
     */
    class TokenChoiceInclusive extends Token {
        protected List<Token> choices;

        public TokenChoiceInclusive(Token... choices) {
            super();
            this.choices = Arrays.stream(choices).toList();
        }

        @Override
        public int parse(String input, int position) {
            if (position >= input.length() || input.substring(position).trim().isEmpty()) {
                mPosition = position;
                setSuccessful(false);
                return position;
                //throw new RuntimeException(getClass() + " : position>=input.length()");
            }
            position = super.skipBlanks(input, position);
            for (Token token : choices) {
                int position1 = position;
                position1 = token.parse(input, position);
                if (token.isSuccessful()) {
                    return processNext(input, position1);
                }
            }
            setSuccessful(false);
            return position;
        }

        @Override
        public String toString() {
            return "TokenChoiceInclusive{" +
                    "choices=" + choices +
                    "}\n";
        }

        @Override
        public Token copy() {
            return null;
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
                mPosition = position;
                setSuccessful(false);
                return position;
                //throw new RuntimeException(getClass() + " : position>=input.length()");
            }
            position = super.skipBlanks(input, position);
            int position1 = position;
            for (Token token : choices) {
                position1 = token.skipBlanks(input, position1);
                if (!token.isSuccessful()) {
                    setSuccessful(false);
                    return position;
                }
            }
            return processNext(input, position1);
        }

        @Override
        public String toString() {
            return "TokenChoiceExclusive{" +
                    "choices=" + Arrays.toString(choices) +
                    "}\n";
        }

        @Override
        public Token copy() {
            ArrayList<Token> objects = new ArrayList<>();
            return new TokenChoiceExclusive(this.choices);
        }
    }

    class TokenPackage extends TokenString {
        public TokenPackage() {
            super("package");
        }
    }

    class TokenQualifiedName extends Token {
        private String name = null;

        public TokenQualifiedName() {
            super();
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public int parse(String input, int position) {
            if (position >= input.length() || input.substring(position).trim().isEmpty()) {
                mPosition = position;
                setSuccessful(false);
                return position;
                //throw new RuntimeException(getClass() + " : position>=input.length()");
            }
            position = skipBlanks(input, position);
            int position1 = position;
            int i = position1;
            boolean passed = false;
            while (i < input.length() && (Character.isLetterOrDigit(input.charAt(i))
                    || Character.isAlphabetic(input.charAt(i))
                    || input.charAt(i) == '_' || input.charAt(i) == '.')) {
                passed = true;
                i++;
            }
            if (passed && i < input.length()) {
                if (!input.substring(position1, i).isEmpty()) {
                    setName(input.substring(position1, i));
                    return processNext(input, i);

                }
            }
            setSuccessful(false);
            return position1;
        }

        @Override
        public Token copy() {
            return new TokenQualifiedName();
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

        public void setName(String name) {
            this.name = name;
        }

        public TokenString(String name) {
            super();
            setName(name);
        }

        @Override
        public int parse(String input, int position) {
            if (position >= input.length() || input.substring(position).trim().isEmpty()) {
                mPosition = position;
                //throw new RuntimeException(getClass() + " : position>=input.length()");
                setSuccessful(false);
                return position;

            }
            position = super.skipBlanks(input, position);
            if (position < input.length() && input.substring(position).startsWith(name)) {
                int position1 = position + name.length();
                return processNext(input, position1);
            } else if (position >= input.length()) {
                setSuccessful(true);
                return input.length();
            } else {
                setSuccessful(false);
                return position;
            }
        }

        @Override
        public String toString() {
            return getClass().getName() + "<=TokenString{" +
                    "name='" + name + '\'' +
                    "}\n";
        }

        @Override
        public Token copy() {
            return new TokenString(this.name);
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

        Token[] choices;

        public MultiTokenOptional(Token... choices) {
            super();
            this.choices = choices;
        }

        @Override
        public int parse(String input, int position) {
            if (position >= input.length() || input.substring(position).trim().isEmpty()) {
                mPosition = position;
                setSuccessful(true);
                return position;
//                throw new RuntimeException(getClass() + " : position>=input.length()");
            }
            position = super.skipBlanks(input, position);
            boolean allNotOk = false;
            int position1 = position;
            int position2 = position1;
            int i = 0;
            while (!allNotOk) {
                allNotOk = true;
                for (Token token : choices) {
                    position2 = token.parse(input, position1);
                    if (position2 != position1 && token.isSuccessful()) {
                        allNotOk = false;
                        position1 = position2;
                    } else if (!token.isSuccessful()) {
                        position2 = position1;
                    }
                }
                i++;
            }
            return processNext(input, position1);
        }

        @Override
        public String toString() {
            StringBuilder s = new StringBuilder("MultiTokenOptional{" +
                    ", action=" + action +
                    ", aClass=" + aClass +
                    ", method=" + method +
                    ", variable=" + variable +
                    ", successful=" + isSuccessful() +
                    "}\n");
            s.append("choices=");
            for (Token choice : choices) {
                s.append(choice.toString());
            }
            s.append("\n");
            return s.toString();
        }

        @Override
        public Token copy() {
            Token[] tokens = new Token[this.choices.length];
            Arrays.stream(this.choices).toList().toArray(tokens);
            return new MultiTokenOptional(tokens);
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
                mPosition = position;
                setSuccessful(false);
                return position;
            }
            position = super.skipBlanks(input, position);
            int position1 = position;
            int position2 = choice.parse(input, position1);
            if (choice.isSuccessful()) {
                return processNext(input, position2);
            } else {
                setSuccessful(false);
                return position1;
            }
        }


        @Override
        public String toString() {
            StringBuilder s = new StringBuilder("SingleTokenOptional {" +
                    ", action=" + action +
                    ", aClass=" + aClass +
                    ", method=" + method +
                    ", variable=" + variable +
                    ", successful=" + isSuccessful() +
                    "}\n");
            s.append("choice=");
            s.append(choice.toString());
            s.append("\n");
            return s.toString();
        }

        @Override
        public Token copy() {
            return new SingleTokenOptional(this.choice);
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
                mPosition = position;
                setSuccessful(true);
                return position;
            }
            assert choices.size() > 0;
            position = skipBlanks(input, position);
            boolean allOk = true;
            int position1 = position;
            int i = 0;
            int position0 = position;
            int j = 0;
            while (allOk) {
                position0 = position1;
                for (j = 0; j < choices.size(); j++) {
                    Token token = choices.get(j);
                    position1 = token.parse(input, position1);
                    if (!token.isSuccessful()) {
                        allOk = false;
                        position1 = position0;
                        if (i > 0) {
                            break;
                        } else {
                            setSuccessful(false);
                            return position;
                        }
                    }
                }
//                if (allOk)
//                    action();
                i++;
            }
            if (i >= 1) {
                return processNext(input, position0);
            }
            return position0;


        }

        @Override
        public String toString() {
            StringBuilder s = new StringBuilder("MultiTokenMandatory {" +
                    ", action=" + action +
                    ", aClass=" + aClass +
                    ", method=" + method +
                    ", variable=" + variable +
                    ", successful=" + isSuccessful() +
                    "}\n");
            s.append("choices=");
            for (int i = 0; i < choices.size(); i++) {
                s.append(choices.get(i).toString());
            }
            s.append("\n");
            return s.toString();
        }

        @Override
        public Token copy() {
            Token[] tokens = new Token[this.choices.size()];
            this.choices.toArray(tokens);
            return new MultiTokenMandatory(tokens);
        }
    }

    class SingleTokenMandatory extends Token {

        private final List<Token> choices;

        public SingleTokenMandatory(Token... mandatory) {
            super();
            this.choices = Arrays.stream(mandatory).toList();
        }

        @Override
        public int parse(String input, int position) {
            if (position >= input.length() || input.substring(position).trim().isEmpty()) {
                mPosition = position;
                setSuccessful(true);
                return position;
            }
            assert choices.size() > 0;
            position = skipBlanks(input, position);
            boolean allOk = true;
            int position1 = position;
            int i = 0;
            int position0 = position;
            int j = 0;
            position0 = position1;
            for (j = 0; j < choices.size(); j++) {
                Token token = choices.get(j);
                position1 = token.parse(input, position1);
                if (!token.isSuccessful()) {
                    allOk = false;
                    position1 = position0;

                    setSuccessful(false);
                    return position;
                }
            }
//                if (allOk)
//                    action();
            return processNext(input, position1);


        }

        @Override
        public String toString() {
            StringBuilder s = new StringBuilder("SingleTokenMandatory {" +
                    ", action=" + action +
                    ", aClass=" + aClass +
                    ", method=" + method +
                    ", variable=" + variable +
                    ", successful=" + isSuccessful() +
                    "}\n");
            s.append("choices=");
            for (int i = 0; i < choices.size(); i++) {
                s.append(choices.get(i).toString());
            }
            s.append("\n");
            return s.toString();
        }

        @Override
        public Token copy() {
            Token[] tokens = new Token[this.choices.size()];
            this.choices.toArray(tokens);
            return new SingleTokenMandatory(tokens);
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
                //mPosition = position;
                setSuccessful(false);
                return position;
                //throw new RuntimeException(getClass() + " : position>=input.length()");
            }
            int position1 = super.skipBlanks(input, position);
            int i = position1;
            boolean passed = false;
            while (i < input.length() && (Character.isLetterOrDigit(input.charAt(i)) || input.charAt(i) == '_' || input.charAt(i) == '.')) {
                i++;
                passed = true;
            }
            if (passed && i - position1 > 0) {
                this.setName(input.substring(position1, i));
                return processNext(input, i);
            } else if (i >= input.length()) {
                setSuccessful(true);
                return input.length();
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
                    "}\n";
        }

        @Override
        public Token copy() {
            return new TokenName();
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
                mPosition = position;
                setSuccessful(false);
                return position;
                //throw new RuntimeException(getClass() + " : position>=input.length()");
            }
            position = super.skipBlanks(input, position);
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

        @Override
        public Token copy() {
            return new TokenExpression();
        }
    }

    /**
     * Represents a specific type of Token.
     */
    class TokenExpression1 extends Token {
        protected String expression;

        public TokenExpression1() {
            super();
        }

        protected boolean isValid(String input, int p) {
            return p < input.length() && (input.charAt(p) != ';');
        }

        protected boolean isNotValid2(String input, int p) {
            return !(p < input.length() && !(input.charAt(p) == '{' || input.charAt(p) == '}'));
        }

        @Override
        public int parse(String input, int position) {
            if (position >= input.length() || input.substring(position).trim().isEmpty()) {
                mPosition = position;
                setSuccessful(false);
                return position;
                //throw new RuntimeException(getClass() + " : position>=input.length()");
            }
            position = super.skipBlanks(input, position);
            int i = position;
            boolean passed = false;

            while (i < input.length() && (isValid(input, i) && !isNotValid2(input, i))) {
                i++;
                passed = true;
            }
            if (passed && (i >= input.length() || !isValid(input, i) || isNotValid2(input, i))) {
                if (i < input.length()) {
                    expression = input.substring(position, i);
                }
                return processNext(input, i);

            } else {
                setSuccessful(false);
                return position;
            }
        }


        @Override
        public Token copy() {
            return new TokenExpression1();
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
                String string = getToken().isSuccessful() ? ((TokenQualifiedName) getToken()).getName() : "";
                if (string != null && !string.isEmpty()) {
                    construct.packageName = string;
                    construct.currentClass.setPackageName(construct.packageName);
                }
                return true;
            }
        };
        Token isFinal = new TokenConstantModifier();
        Action actionConstantModifier = new Action(isFinal) {
            @Override
            public boolean action() {
                if (getToken().isSuccessful()) {
                    String choice = (((TokenConstantModifier) token)).getChoice();
                    if (choice != null && !choice.isEmpty()) {
                        construct.currentClass.setFinal(true);
                    }
                }
                return true;
            }
        };
        TokenName className = new TokenName();
        Action setNewClassName = new Action(className) {
            @Override
            public boolean action() {
                if (token.isSuccessful()) {
                    String string = getToken().isSuccessful() ? ((TokenName) getToken()).getName() : "";
                    if (string != null && !string.isEmpty()) {
                        construct.currentClass.setName(string);
                    }

                    return true;
                }
                return false;
            }
        };
        Token closeBracketClass = new TokenCloseBracket();
        Action actionCloseClassBracket = new Action(closeBracketClass) {
            @Override
            public boolean action() {
                //((TokenCloseBracket)getToken())
                if (token.isSuccessful()) {
                    //construct.cited.put(construct.packageName + "." + construct.currentClass.getName(), construct.currentClass);
                    //construct.currentClass = new Class();
                }
                return true;
            }
        };
        Token closeBracketMethod = new TokenCloseBracket();
        actionCloseClassBracket = new Action(closeBracketMethod) {
            @Override
            public boolean action() {
                //((TokenCloseBracket)getToken())
                if (token.isSuccessful()) {
                    //construct.cited.put(construct.packageName + "." + construct.currentClass.getName(), construct.currentClass);
                    //construct.currentClass = new Class();
                }
                return true;
            }
        };

        TokenClassScope tokenVariableScope = new TokenClassScope();
        Action action = new Action(tokenVariableScope) {
            @Override
            public boolean action() {
                if (token.isSuccessful()) {
                    List<Variable> variableList = construct.currentClass.getVariableList();
                    if (variableList.isEmpty())
                        variableList.add(new Variable());
                    String string = tokenVariableScope.getChoice();
                    if (string != null && !string.isEmpty()) {
                        //construct.currentClass.getVariableList().add(variable);
                        variableList.get(variableList.size() - 1).setScope(string);
                    }

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
                    String name = ((TokenQualifiedName) token).getName();
                    if (name != null && !name.isEmpty()) {
                        variableList.get(variableList.size() - 1).setName(name);
                    }

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
                    String name = ((TokenClassScope) token).getChoice();
                    if (name != null && !name.isEmpty()) {
                        methodList.get(methodList.size() - 1).setScope(name);
                    }
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
                    String name = ((TokenName) token).getName();
                    if (name != null && !name.isEmpty()) {
                        methodList.get(methodList.size() - 1).setName(((TokenName) token).getName());
                    }
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
                    if (name != null && !name.isEmpty()) {
                        List<Instruction> instructions = construct.methodMembers.get(construct.methodMembers.size() - 1).getInstructions();
                        Instruction instruction = instructions.get(instructions.size() - 1);
                        instruction.setType(name);
                    }
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
                    //construct.methodMembers.get(construct.methodMembers.size() - 1)
                    //       .setInstructions(instructions);
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
//                    construct.currentClass = new Class();
//                    construct.methodMembers = new HashMap<>();
//                    construct.fieldMembers = new HashMap<>();
//                    construct.currentClass = new Class();
//                    construct.currentClass.setPackageName(construct.packageName);
//                    construct.currentMethod = new Method();
//                    construct.currentField = new Variable();
                    return true;
                }
                return false;
            }
        };
        TokenString aPackage1 = new TokenString("package");
        aPackage1.addToken(packageQualifiedName);
        packageQualifiedName.addToken(new TokenSemiColon());
        Token aPackage = definitions.put(0, new MultiTokenMandatory(

                new SingleTokenOptional(aPackage1),
                new MultiTokenOptional(new TokenClassScope(), isFinal,
                        new MultiTokenMandatory(tokenClassKeyword, className, new TokenOpenBracket()),
                        // Variables
                        new MultiTokenOptional(new MultiTokenMandatory(
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
                                                        new TokenExpression1()), endOfInstruction))),// Commit changes
                        closeBracketMethod), closeBracketClass));// Commit changes

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
        public Variable currentField = new Variable();
        public Method currentMethod = new Method();
        protected String packageName = "";
        protected Class currentClass = new Class();
        protected HashMap<String, Class> cited = new HashMap<>();
        protected HashMap<String, Variable> fieldMembers = new HashMap<>();
        protected ArrayList<Method> methodMembers = new ArrayList<>();
        protected ArrayList<Class> classes = new ArrayList<>();


        public Construct() {

        }

        @Override
        public String toString() {
            final String[] fieldsStr = {""};
            fieldMembers.forEach((s, variable) -> fieldsStr[0] += "\n" + s + "\n" + variable.toString());
            final String[] classesStr = {""};
            classes.forEach(aClass -> classesStr[0] += "\n" + "\n" + aClass.toString());
            final String[] citedStr = {""};
            cited.forEach((s, aClass) -> citedStr[0] += aClass.toString());
            final String[] methodStr = {""};
            methodMembers.forEach(method -> {
                methodStr[0] += "\n" + method.getName() + "\n" + method.toString();
            });
            String[] string = {"Construct{" +
                    "packageName='" + packageName +
                    "', classes=[" + classesStr[0] +
                    "], fieldMembers=['" + fieldsStr[0] +
                    "'], methodMembers=[" + methodStr[0] +
                    "], currentClass={" + currentClass +
                    "], currentMethod={" + currentMethod +
                    "], currentField={" + currentField +
                    "}, cited=[" + citedStr[0] +
                    "]}"};

            return string[0];
        }

        public String toLangStringJava() {
            StringBuilder sb = new StringBuilder();
            if (!this.packageName.isEmpty()) {
                sb.append("package ").append(packageName).append(";\n");
            }
            if (classes.isEmpty() && currentClass.getName() != null) {
                classes.add(currentClass);
            }///
            classes.forEach(aClass -> {
                sb.append("class ").append(aClass.getName()).append(" {\n");
                aClass.getVariableList().forEach(variable -> sb.append("\t").append(variable.getClassStr()).append(" ").append(variable.getName()).append(";\n"));
                aClass.getMethodList().forEach(method -> {
                    sb.append("\t").append(method.getOfClass() != null ? method.getOfClass().getClassStr() : "").append(" ").append(method.getName()).append(" ( ");
                    if (!method.getParameterList().isEmpty()) {
                        Variable variable = method.getParameterList().get(0);
                        sb.append(variable.getClassStr()).append(" ").append(variable.getName());
                        for (int i = 1; i < method.getParameterList().size(); i++) {
                            variable = method.getParameterList().get(i);
                            sb.append(", ").append(variable.getClassStr()).append(" ").append(variable.getName());
                        }
                    }
                    sb.append(" )");
                    sb.append(" {\n");
                    method.getInstructions().forEach(instruction -> sb.append("\t\t").append(instruction.getType() != null ? instruction.getType() : "")
                            .append(" ").append(instruction.getName() != null ? instruction.getName() : "")
                            .append(" ").append(instruction.getExpression() != null ? " = " +
                                    instruction.getExpression() : "").append(";\n"));
                    sb.append("\t}\n");
                });
                sb.append("}");
            });
            return sb.toString();
        }
    }

    private final Construct construct = new Construct();

    public int parse(@NotNull String input) {
        /*new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e) {
                }
                System.exit(1);
            }
        }.start();
        */
        int position1 = 0;
        try {
            Token token = definitions.get(0);
            position1 = token.parse(input, 0);
            return position1;
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }
        return position1;
    }

    public int parse(@NotNull Token token1, @Nullable String input) {
        int position1 = 0;
        position1 = token1.parse(input, position1);
        return position1;
    }

    public class TokenAttribute extends TokenString {
        private String requiredName;
        private String attributeName;
        private String attributeValue;

        public TokenAttribute(String version) {
            super("version");
            this.requiredName = version;
        }

        public String getRequiredName() {
            return requiredName;
        }

        public String getAttributeName() {
            return attributeName;
        }

        public String getAttributeValue() {
            return attributeValue;
        }

        @Override
        public int parse(String input, int position) {
            if (position >= input.length() || input.substring(position).trim().isEmpty()) {
                //mPosition = position;
                setSuccessful(false);
                return position;
                //throw new RuntimeException(getClass() + " : position>=input.length()");
            }
            int position1 = super.skipBlanks(input, position);
            int i = position1;
            boolean passed = false;
            while (i < input.length() && (Character.isLetterOrDigit(input.charAt(i)) || input.charAt(i) == '_' || input.charAt(i) == '-')) {
                i++;
                passed = true;
            }
            if (passed && i - position1 > 0) {
                this.setAttributeName(input.substring(position1, i));
                position1 = i;
                passed = false;
                position1 = skipBlanks(input, position1);
                if (input.charAt(position1) == '=') {
                    position1 = skipBlanks(input, position1 + 1);
                    char start = 0;
                    i = position1;
                    int i1 = i;
                    char end = 0;
                    if (input.charAt(i) == '\'' || input.charAt(i) == '"') {
                        start = input.charAt(i);
                        i++;
                        while (i < input.length() && (input.charAt(i) != /*'\\' +*/ start)) {
                            i++;
                            passed = true;
                        }
                        if (passed)
                            end = input.charAt(i);
                    }
                    if (passed && start != 0 && end == start) {
                        setAttributeValue(input.substring(i1 + 1, i));
                    }
                }
                return processNext(input, position1);
            } else if (i >= input.length()) {
                setSuccessful(true);
                return input.length();
            } else {
                setSuccessful(false);
                return position1;
            }

        }

        private void setAttributeValue(String substring) {
            this.attributeValue = substring;
        }

        private void setAttributeName(String substring) {
            this.attributeName = substring;
        }

        @Override
        public Token copy() {
            TokenAttribute tokenAttribute = new TokenAttribute(requiredName);
            tokenAttribute.setAttributeValue(attributeValue);
            tokenAttribute.setAttributeName(attributeName);
            return super.copy();
        }
    }

    class TokenElementOpening extends Token {
        private final String requiredName;
        private String elementName;

        public TokenElementOpening(String requiredName) {
            this.requiredName = requiredName;
        }

        @Override
        public int parse(String input, int position) {
            if (position >= input.length() || input.substring(position).trim().isEmpty()) {
                //mPosition = position;
                setSuccessful(false);
                return position;
                //throw new RuntimeException(getClass() + " : position>=input.length()");
            }
            int position1 = super.skipBlanks(input, position);
            int i = position1;
            boolean passed = false;
            if (i >= input.length() || (!Character.isLetterOrDigit(input.charAt(i)) && input.charAt(i) != '_' && input.charAt(i) != '-')) {
                i++;
                setSuccessful(false);
                return position1;
            }
            i = position1;
            if (i < input.length() && input.charAt(i) == '<') {
                position1 = i + 1;
                position1 = skipBlanks(input, position1);
                i = position1;
                while (i < input.length() && (Character.isLetterOrDigit(input.charAt(i)) || input.charAt(i) == '_' || input.charAt(i) == '-')) {
                    i++;
                    passed = true;
                }
            }
            if (passed && i - position1 > 0) {
                this.setElementName(input.substring(position1, i));
                return processNext(input, i);
            } else if (i >= input.length()) {
                setSuccessful(true);
                return input.length();
            } else {
                setSuccessful(false);
                return position1;
            }

        }

        @Override
        public Token copy() {
            TokenElementOpening tokenElementOpening = new TokenElementOpening(requiredName);
            tokenElementOpening.elementName = elementName;
            return tokenElementOpening;
        }

        private void setElementName(String substring) {
            this.elementName = substring;
        }

    }

    class TokenCloseElement extends TokenString {
        public TokenCloseElement() {
            super(">");
        }
    }

    class TokenCloseElementShort extends TokenString {
        public TokenCloseElementShort() {
            super("/>");
        }
    }

    class TokenCloseElementShortXmlDeclaration extends TokenString {
        public TokenCloseElementShortXmlDeclaration() {
            super("?>");
        }
    }
}
