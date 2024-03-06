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

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestStringAnalyzer1xml {
    class Parser {
        public boolean parse(StringAnalyzer1.Token token, String text) {
            if (token != null && text != null) {
                StringAnalyzer1 stringAnalyzer1 = new StringAnalyzer1();
                int parse = stringAnalyzer1.parse(token, text);
                if (parse > text.length()) {
                    return true;
                } else {
                    return false;
                }
            }
            return false;
        }

    }

    Parser parser = new Parser();

    private StringAnalyzer1.Token getXmlToken() {
        StringAnalyzer1 stringAnalyzer1 = new StringAnalyzer1();
        StringAnalyzer1.Token startToken = stringAnalyzer1.new TokenString("<?xml");
        StringAnalyzer1.MultiTokenOptional multiOptionalAttributes = stringAnalyzer1.new MultiTokenOptional(stringAnalyzer1.new TokenAttribute(""));
        StringAnalyzer1.Token endXmlDeclaration = stringAnalyzer1.new TokenString("?>");
        startToken.addToken(multiOptionalAttributes);
        multiOptionalAttributes.addToken(endXmlDeclaration);

        return startToken;
    }

    @Test
    public void testXmlText1() {
        String text = "";
        StringAnalyzer1.Token xmlToken = getXmlToken();
        parser.parse(xmlToken, "<?xml version='1.0' encoding='UTF-8' ?>");
        StringAnalyzer1.MultiTokenOptional token = (StringAnalyzer1.MultiTokenOptional) xmlToken.getNextToken().getData1d().get(0);

        int length = token.choices.size();
        for (int i = 0; i < length; i++) {
            StringAnalyzer1.Token tokenAttribute = token.choices.get(i);
            if (tokenAttribute instanceof StringAnalyzer1.TokenAttribute) {

                System.out.printf("token.getAttributeName()==" +
                        (((StringAnalyzer1.TokenAttribute) tokenAttribute).getAttributeName()));
                System.out.printf("token.getAttributeName()==" +
                        (((StringAnalyzer1.TokenAttribute) tokenAttribute).getAttributeValue()));
            }
        }
        assertTrue(!parser.parse(getXmlToken(), text));
    }


}
