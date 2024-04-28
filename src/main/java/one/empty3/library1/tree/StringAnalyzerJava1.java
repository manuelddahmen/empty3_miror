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
import java.util.List;

public class StringAnalyzerJava1 extends StringAnalyzer3 {


    public class TokenTypeArrayOrNot extends Token {
        public TokenTypeArrayOrNot() {
            super();
        }
    }

    public class TokenArrayAccessor extends Token {
        public TokenArrayAccessor() {

        }
    }

    public class TokenConstructorArray extends Token {
        public TokenConstructorArray() {
            super();
        }
    }

    public class TokenMethodCall extends Token {
        public TokenMethodCall() {
            super();
        }
    }

    public class TokenConstructorCall extends Token {
        public TokenConstructorCall() {
            super();
        }
    }

    public class TokenConstructor extends Token {
        public TokenConstructor() {

        }

    }

    public class TokenType2 extends TokenQualifiedName {
        protected String[] brackets = new String[7];
        protected int passBrackets;
        protected int bracketsCount;

        public TokenType2() {
            super();
        }

        public String[] getBrackets() {
            return brackets;
        }

        public void setBrackets(String[] brackets) {
            this.brackets = brackets;
        }

        public int getPassBrackets() {
            return passBrackets;
        }

        public void setPassBrackets(int passBrackets) {
            this.passBrackets = passBrackets;
        }

        @Override
        public int parse(String input, int position) {
            if (position >= input.length() || input.substring(position).trim().isEmpty()) {
                mPosition = position;
                setSuccessful(false);
                throw new RuntimeException(getClass() + " : position>=input.length()");
            }
            position = skipBlanks(input, position);
            int position1 = position;
            int i = position1;
            int[] i1 = new int[14];
            boolean passed = false;
            int iWord = i;
            passBrackets = 0;
            while (i < input.length() && (((Character.isLetterOrDigit(input.charAt(i))
                    || Character.isAlphabetic(input.charAt(i))
                    || input.charAt(i) == '_' || input.charAt(i) == '.' || Character.isWhitespace(input.charAt(i))
                    || input.charAt(i) == '[')))) {
                passed = true;
                if (Character.isWhitespace(input.charAt(i))) {
                    passed = true;
                    iWord = i;
                    break;
                }
                i++;
            }
            boolean bStart = false;
            while (i < input.length() && (((Character.isWhitespace(input.charAt(i))) || (input.charAt(i) == '[') || (input.charAt(i) == ']')))) {
                if (input.charAt(i) == '[' && !bStart) {
                    bStart = true;
                    i1[passBrackets] = i;
                }
                if (input.charAt(i) == ']' && bStart) {
                    bStart = false;
                    i1[passBrackets + 1] = i;
                    passBrackets += 2;
                }
                i++;
            }

            if (passed && iWord < input.length() && containsNoKeyword(input.substring(position1, iWord))) {
                if (!input.substring(position1, iWord).isEmpty()) {
                    setName(input.substring(position1, iWord));

                }
            }
            for (int j = 0; j < passBrackets; j += 2) {
                brackets[j / 2] = input.substring(i1[j], i1[j + 1]);
                bracketsCount++;
            }
            if (i == position1 || name == null || name.isEmpty()) {
                setSuccessful(false);
                return position1;
            } else {
                setSuccessful(true);
                return processNext(input, i);
            }

        }

        @Override
        public Token copy(Token token) {
            return new TokenQualifiedName();
        }

        public int getBracketsCount() {
            return bracketsCount;
        }

        public void setBracketsCount(int bracketsCount) {
            this.bracketsCount = bracketsCount;
        }
    }

    public class MultiTokenSequence extends Token {
        private final Token start;
        private final Token end;
        private final Token separator;
        private final Token tokenType;

        public MultiTokenSequence(Token start, Token end, Token separator, Token tokenType) {
            this.start = start;
            this.end = end;
            this.separator = separator;
            this.tokenType = tokenType;
        }

        @Override
        public int parse(String input, int position) {
            if (position >= input.length() || input.substring(position).trim().isEmpty()) {
                mPosition = position;
                setSuccessful(false);
                throw new RuntimeException(getClass() + " : position>=input.length()");
            }
            int position1 = start.parse(input, position);
            boolean passOne = false;
            int betweenTokens = 0;
            if (start.isSuccessful()) {
                betweenTokens = 1;
                do {
                    position1 = start.parse(input, position1);
                    while (start.isSuccessful()) {
                        betweenTokens++;
                        position1 = start.parse(input, position1);
                    }
                    position1 = end.parse(input, position1);
                    while (end.isSuccessful() && betweenTokens > 0) {
                        betweenTokens--;
                        position1 = end.parse(input, position1);
                    }
                    if (!passOne && betweenTokens == 0) {
                        position1 = separator.parse(input, position1);
                        if (separator.isSuccessful()) {
                            passOne = false;
                        }
                    }
                    if (betweenTokens == 0) {
                        position1 = tokenType.parse(input, position1);
                        if (tokenType.isSuccessful()) {
                            position = position1;
                        }
                    }
                    position1 = start.parse(input, position1);
                    while (start.isSuccessful()) {
                        betweenTokens++;
                        position1 = start.parse(input, position1);
                    }
                    position1 = end.parse(input, position1);
                    while (end.isSuccessful() && betweenTokens > 0) {
                        betweenTokens--;
                        position1 = end.parse(input, position1);
                    }
                } while (betweenTokens > 0 || !end.isSuccessful());
            }

            return processNext(input, position1);

        }
    }


    public class TokenExpression2 extends TokenName {
        protected List<DataExpression> expressions = new ArrayList<DataExpression>();
        protected List<DataExpression> bracketsExpressions = new ArrayList<DataExpression>();
        public String[] brackets = new String[7];
        public static int methodCallArgument = 1;
        public int variable = 2;
        public int memberVariable = 4;
        public static int dotCall = 8;
        public int classNameReference = 16;
        public static int classArrayAccess = 32;
        public int classArrayNew = 64;
        public int classClassNew = 128;
        public boolean operator;
        public boolean numberDouble;
        public boolean numberFloat;
        public boolean numberBoolean;
        public boolean numberString;
        public boolean numberChar;
        protected int passBrackets;


        public TokenExpression2() {
            super();
        }

        void reinit() {
            expressions = new ArrayList<DataExpression>();
            bracketsExpressions = new ArrayList<DataExpression>();
            brackets = new String[7];
            int passBrackets = -1;
            name = null;
        }

        @Override
        public int parse(String input, int position) {
            reinit();

            if (position >= input.length() || input.substring(position).trim().isEmpty()) {
                mPosition = position;
                setSuccessful(true);
                name = "";
                return position;
            }
            position = skipBlanks(input, position);
            int position1 = position;
            int i = position1;
            int[] i1 = new int[14];
            boolean passed = false;
            int iWord = -1;
            passBrackets = 0;

            if (i < input.length() && (input.charAt(i) == ']' || input.charAt(i) == ')' || input.charAt(i) == ';' || input.charAt(i) == ',' || input.charAt(i) == '.')) {
                setSuccessful(true);
                return i - 1;
            }
            boolean newOperator = false;
            int i0 = i;
            String charsAlgebraic = new Character[]{'+', '-', '*', '/', '=', '^'}.toString();
            while (i < input.length() && (Character.isLetterOrDigit(input.charAt(i))
                    || Character.isAlphabetic(input.charAt(i)) || charsAlgebraic.contains("" + input.charAt(i))
                    || input.charAt(i) == '_' || Character.isWhitespace(input.charAt(i)))) {
                if (i < input.length() - 4 && input.substring(i, i + 3).equals("new") && Character.isWhitespace(input.charAt(i + 4))) {
                    i += 4;
                    newOperator = true;
                    i0 = i;
                }
                passed = true;
                i++;
                iWord = i;
            }
            boolean bStart = false;
            boolean pStart = false;
            int bCount = 0;
            int pCount = 0;
            int iStart = 0;
            int iEnd = 0;
            if (iWord >= 0) {
                setName(input.substring(i0, iWord));
                bStart = false;
                pStart = false;
                bCount = 0;
                pCount = 0;
                int pStartI = 0;
                int pEndI = 0;
                int bStartI = 0;
                int bEndI = 0;
                while (i < input.length()) {
                    if (input.charAt(i) == '[' && !bStart) {
                        bCount++;
                        bStart = true;
                        bStartI = i + 1;
                    } else if (input.charAt(i) == ']' && bStart) {
                        iEnd = i;
                        bCount--;
                    }
                    if (input.charAt(i) == '(' && pStart) {
                        pCount++;
                        pStart = true;
                    } else if (input.charAt(i) == ')' && pStart) {
                        pCount--;
                    }
                    if (input.charAt(i) == '(' && !pStart && !bStart) {
                        pStartI = i + 1;
                        pStart = true;
                        pCount++;
                    }
                    if (input.charAt(i) == ')' && pStart && pCount == 0 && bCount == 0) {
                        TokenExpression2 tokenExpression2 = new TokenExpression2();
                        String substring = input.substring(pStartI, i);
                        int parse = tokenExpression2.parse(substring, 0);
                        if (tokenExpression2.isSuccessful()) {
                            expressions.add(new DataExpression(methodCallArgument, tokenExpression2, tokenExpression2.name));
                            i = pStartI + parse + 1;
                            i = skipBlanks(input, i + 1);
                        }
                        pStart = false;
                    } else if (input.charAt(i) == ']' && bStart && bCount == 0 && pCount == 0) {
                        TokenExpression2 tokenExpression2 = new TokenExpression2();
                        String substring = input.substring(bStartI, i);
                        int parse = tokenExpression2.parse(substring, 0);
                        if (tokenExpression2.isSuccessful()) {
                            expressions.add(new DataExpression(classArrayAccess, tokenExpression2, tokenExpression2.name));
                            i = parse + bStartI + 1;
                            i = skipBlanks(input, i);
                        }
                        bStart = false;
                    } else if ((input.charAt(i) == ';' || input.charAt(i) == ',')
                            && pCount == 0 && bCount == 0) {
                        break;
                    } else if (input.charAt(i) == '.') {
                        i++;
                        TokenExpression2 tokenExpression2 = new TokenExpression2();
                        int posCall = tokenExpression2.parse(input, i);
                        if (tokenExpression2.isSuccessful()) {
                            expressions.add(new DataExpression(dotCall, tokenExpression2, null));
                            addToken(tokenExpression2);
                            i = posCall;
                            setSuccessful(true);
                            return processNext(input, i);
                        }
                    }
                    i++;
                }
            }/* else {
                setSuccessful(false);
                setName(null);
                return position1;
            }*/
            i = skipBlanks(input, i);
            //while (i < input.length() && (((Character.isWhitespace(input.charAt(i))) || (input.charAt(i) == '[') || (input.charAt(i) == ']')))) {
            //if (i < input.length() - 4 && input.substring(i, i + 3).equals("new") && Character.isWhitespace(input.charAt(i + 4))) {
            //    i += 4;
            //    //constructor = 3;
            //}
            /*
            while (i < input.length() && (((Character.isWhitespace(input.charAt(i))) || (input.charAt(i) == '[') || (input.charAt(i) == ']')))) {
                if (input.charAt(i) == '[' && bStart) {
                    bCount++;
                    bStart = true;
                    iStart = i + 1;
                } else if (input.charAt(i) == ']' && bStart) {
                    iEnd = i;
                    bCount--;
                }
                if (input.charAt(i) == '[' && !bStart && !pStart && bCount == 0) {
                    iStart = i + 1;
                    bStart = true;
                    i1[passBrackets] = i;
                } else if (input.charAt(i) == ']' && bStart && bCount == 0) {
                    iEnd = i;
                    bStart = false;
                    i1[passBrackets + 1] = i;
                    passBrackets += 2;
                } else if (input.charAt(i) == '(' && pStart) {
                    pCount++;
                } else if (input.charAt(i) == ')' && pStart) {
                    pCount--;
                }
                if (input.charAt(i) == '.' && bCount == 0 && pCount == 0 && bStart) {
                    TokenExpression2 tokenExpression2 = new TokenExpression2();
                    String substring = input.substring(iStart, iEnd);
                    int parse = tokenExpression2.parse(substring, 0);
                    expressions.add(new DataExpression(classArrayAccess, tokenExpression2, tokenExpression2.name));
                    bStart = false;
                }
                i++;
            }*/

/*
            for (int j = 0; j < passBrackets; j += 2) {
                brackets[j / 2] = input.substring(i1[j], i1[j + 1]);
                TokenExpression2 tokenExpression = new TokenExpression2();
                int parse = tokenExpression.parse(brackets[j / 2], i1[j]);
                if (parse < i1[j + 1]) {
                    bracketsExpressions.add(new DataExpression(0,
                            tokenExpression, brackets[j + 2]));
                }
            }
  */
            if (i == position1 || name == null) {
                setSuccessful(false);
                return i;
            } else {
                TokenExpression2 token = new TokenExpression2();
                int parse = token.parse(input, i);
                setSuccessful(true);
                if (parse > i) {
                    addToken(token);
                }
                return processNext(input, parse);

            }

        }

        @Override
        public Token copy(Token token) {
            return new TokenQualifiedName();
        }

        public String[] getBrackets() {
            return brackets;
        }

        public void setBrackets(String[] brackets) {
            this.brackets = brackets;
        }

        @Override
        public String toString() {
            return "TokenExpression2{" +
                    "name=" + name +
                    "expressions=" + expressions +
                    ", bracketsExpressions=" + bracketsExpressions +
                    ", brackets=" + Arrays.toString(brackets) +
                    ", variable=" + variable +
                    ", passBrackets=" + passBrackets +
                    '}';
        }
    }


    public class TokenNameDeclaration2 extends TokenName {
        private String[] brackets = new String[7];

        public TokenNameDeclaration2() {
            super();
        }

        /*

                @Override
                public int parse(String input, int position) {
                    if (position >= input.length() || input.substring(position).trim().isEmpty()) {
                        mPosition = position;
                        setSuccessful(false);
                        throw new RuntimeException(getClass() + " : position>=input.length()");
                    }
                    position = skipBlanks(input, position);
                    int position1 = position;
                    int i = position1;
                    int[] i1 = new int[14];
                    boolean passed = false;
                    int iWord = i;
                    int passBrackets = 0;
                    while (i < input.length() && (((Character.isLetterOrDigit(input.charAt(i))
                            || Character.isAlphabetic(input.charAt(i))
                            || input.charAt(i) == '_' || input.charAt(i) == '.' //|| Character.isWhitespace(input.charAt(i)
                            || input.charAt(i) == '[')))) {
                        passed = true;
                        i++;
                    }
                    if (i > position1 && Character.isLetter(input.charAt(position1)))
                        iWord = i;
                    boolean bStart = false;
                    while (i < input.length() && (((Character.isWhitespace(input.charAt(i))) || (input.charAt(i) == '[') || (input.charAt(i) == ']')))) {
                        if (input.charAt(i) == '[' && !bStart) {
                            bStart = true;
                            i1[passBrackets] = i;
                        }
                        if (input.charAt(i) == ']' && bStart) {
                            bStart = false;
                            i1[passBrackets + 1] = i;
                            passBrackets += 2;
                        }
                        i++;
                    }

                    if (passed && containsNoKeyword(input.substring(position1, iWord))) {
                        if (!input.substring(position1, iWord).isEmpty()) {
                            setName(input.substring(position1, iWord));

                        }
                    }
                    for (int j = 0; j < passBrackets; j += 2) {
                        brackets[j / 2] = input.substring(i1[j], i1[j + 1]);
                    }
                    if (i == position1 || name == null || name.isEmpty()) {
                        setSuccessful(false);
                        return position1;
                    } else {
                        setSuccessful(true);
                        return processNext(input, i);
                    }
                }
        */
        @Override
        public Token copy(Token token) {
            return new TokenQualifiedName();
        }

    }
}
