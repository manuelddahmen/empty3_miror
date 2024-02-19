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

package one.empty3.library1.tree

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Test class for AlgebraicTree.
 */
@RunWith(JUnit4::class)
class TestStringAnalyzer1 {
    @Test
    fun testStringAnalyzerPackage() {
        val stringAnalyzer1 = StringAnalyzer1()
        val token = stringAnalyzer1.TokenPackage()
        val input = "package"
        var parse = -1
        try {
            parse = stringAnalyzer1.parse(token, input)
//            parse = stringAnalyzer1.mPosition
        } catch (ex: RuntimeException) {
            if (token.isSuccessful) {
                if (parse >= input.length) {
                    Assert.assertTrue(true)

                    println(stringAnalyzer1.construct)
                    return
                }
            }

            Assert.assertTrue(false)
        }

        println("TestStringAnalyzer1.testStringAnalyzerPackage")
        println("parse = " + parse + "/" + input.length)
        println("isSuccessful = " + token.isSuccessful)


        if (token.isSuccessful) {
            if (parse >= input.length) {
                Assert.assertTrue(true)
                return

            }
        }
        Assert.assertTrue(false)

    }

    @Test
    fun testStringAnalyzerPackageDeclaration() {
        val stringAnalyzer1 = StringAnalyzer1()
        val tokenPackage = stringAnalyzer1.TokenPackage()
        val tokenPackageName = stringAnalyzer1.TokenQualifiedName()
        val tokenPackageSemicolon = stringAnalyzer1.TokenSemiColon()

        tokenPackage.addToken(tokenPackageName)
        tokenPackageName.addToken(tokenPackageSemicolon)

        val token = stringAnalyzer1.SingleTokenOptional(tokenPackage)


        val input = "package com.example;"
        var parse = -1
        try {
            parse = stringAnalyzer1.parse(token, input)
//            parse = stringAnalyzer1.mPosition
        } catch (ex: RuntimeException) {
            if (token.isSuccessful) {
                if (parse >= input.length) {
                    Assert.assertTrue(true)

                    println(stringAnalyzer1.construct)
                    return
                }
            }

            Assert.assertTrue(false)
        }

        stringAnalyzer1.construct.packageName = tokenPackageName.name


        println("TestStringAnalyzer1.testStringAnalyzerPackage")
        println("parse = " + parse + "/" + input.length)
        println("isSuccessful = " + token.isSuccessful + " tokenSemicolon : " + tokenPackageSemicolon.isSuccessful)
        println("Name : " + tokenPackageName.name)

        if (token.isSuccessful) {
            if (parse >= input.length) {
                Assert.assertTrue(true)

                println(stringAnalyzer1.construct)
                return
            }
        }

        Assert.assertTrue(false)


    }

    @Test
    fun testStringAnalyzerPackageAndClassDeclaration() {
        val stringAnalyzer1 = StringAnalyzer1()
        val tokenPackage = stringAnalyzer1.TokenPackage()
        val tokenPackageName = stringAnalyzer1.TokenQualifiedName()
        val tokenPackageSemicolon = stringAnalyzer1.TokenSemiColon()
        val tokenClass = stringAnalyzer1.TokenClassKeyword()
        val tokenClassName = stringAnalyzer1.TokenName()
        val tokenOpenBracket = stringAnalyzer1.TokenOpenBracket()
        val tokenCloseBracket = stringAnalyzer1.TokenCloseBracket()


        tokenPackage.addToken(tokenPackageName)
        tokenPackageName.addToken(tokenPackageSemicolon)
        tokenClass.addToken(tokenClassName)
        tokenClassName.addToken(tokenOpenBracket)
        tokenOpenBracket.addToken(tokenCloseBracket)

        val token = stringAnalyzer1.SingleTokenOptional(tokenPackage)

        token.addToken(tokenClass)

        val input = "package com.example;\nclass Graph {\n}"
        var parse = -1
        try {
            parse = stringAnalyzer1.parse(token, input)
//            parse = stringAnalyzer1.mPosition
        } catch (ex: RuntimeException) {
            if (token.isSuccessful) {
                if (parse >= input.length) {
                    Assert.assertTrue(true)

                    println(stringAnalyzer1.construct)
                    return
                }
            }

            Assert.assertTrue(false)
        }

        stringAnalyzer1.construct.packageName = tokenPackageName.name
        stringAnalyzer1.construct.currentClass.name = tokenClassName.name


        println("TestStringAnalyzer1.testStringAnalyzerPackage")
        println("parse = " + parse + "/" + input.length)
        println("isSuccessful = " + token.isSuccessful + " tokenSemicolon : " + tokenPackageSemicolon.isSuccessful)
        println("Name : " + tokenPackageName.name)

        if (token.isSuccessful) {
            if (parse >= input.length) {
                Assert.assertTrue(true)

                println(stringAnalyzer1.construct)
                return
            }
        }

        Assert.assertTrue(false)


    }

    /*
        private fun buildJavaClassTokenList(stringAnalyzer1: StringAnalyzer1): StringAnalyzer1.SingleTokenOptional {
            val tokenPackage = stringAnalyzer1.TokenPackage()
            val tokenPackageName = stringAnalyzer1.TokenQualifiedName()
            val tokenPackageSemicolon = stringAnalyzer1.TokenSemiColon()
            val tokenClass = stringAnalyzer1.TokenClassKeyword()
            val tokenClassName = stringAnalyzer1.TokenName()
            val tokenOpenBracket = stringAnalyzer1.TokenOpenBracket()
            val tokenCloseBracket = stringAnalyzer1.TokenCloseBracket()

            val tokenMemberVarType1 = stringAnalyzer1.TokenQualifiedName()
            val tokenMemberVarName1 = stringAnalyzer1.TokenName()
            val tokenMemberVarEquals1 = stringAnalyzer1.TokenEquals()
            val tokenMemberExpression1 = stringAnalyzer1.TokenExpression1()
            val tokenSemiColonVar1 = stringAnalyzer1.TokenSemiColon()

            val tokenMemberVarType2 = stringAnalyzer1.TokenQualifiedName()
            val tokenMemberVarName2 = stringAnalyzer1.TokenName()
            val tokenSemiColonVar2 = stringAnalyzer1.TokenSemiColon()

            tokenMemberVarType1.addToken(tokenMemberVarName1)
            tokenMemberVarEquals1.addToken(tokenMemberVarEquals1)
            tokenMemberVarEquals1.addToken(tokenMemberExpression1)
            tokenMemberExpression1.addToken(tokenSemiColonVar1)

            tokenMemberVarType2.addToken(tokenMemberVarName2)
            tokenMemberVarName2.addToken(tokenSemiColonVar2)


            val tokenMemberMethodVarType1 = stringAnalyzer1.TokenQualifiedName()
            val tokenMemberMethodVarName1 = stringAnalyzer1.TokenName()
            val tokenSemiMethodColonVar1 = stringAnalyzer1.TokenSemiColon()

            val tokenMemberMethodVarType2 = stringAnalyzer1.TokenQualifiedName()
            val tokenMemberMethodVarName2 = stringAnalyzer1.TokenName()
            val tokenMemberMethodVarEquals2 = stringAnalyzer1.TokenEquals()
            val tokenMemberMethodExpression2 = stringAnalyzer1.TokenExpression1()
            val tokenMethodSemiColonVar2 = stringAnalyzer1.TokenSemiColon()


            tokenMemberMethodVarType2.addToken(tokenMemberMethodVarName2)
            tokenMemberMethodVarName2.addToken(tokenMemberMethodVarEquals2)
            tokenMemberMethodVarEquals2.addToken(tokenMethodSemiColonVar2)
            tokenMethodSemiColonVar2.addToken(tokenMemberMethodExpression2)

            tokenMemberMethodVarType1.addToken(tokenMemberMethodVarName1)
            tokenMemberMethodVarName1.addToken(tokenSemiMethodColonVar1)

            val tokenMemberVar = stringAnalyzer1.SingleTokenExclusiveXor(
                tokenMemberVarType1, tokenMemberVarType2
            )

            val tokenMemberMethodType = stringAnalyzer1.TokenQualifiedName()
            val tokenMemberMethodName = stringAnalyzer1.TokenName()
            val tokenOpenBracketMethod = stringAnalyzer1.TokenOpenBracket()

            tokenMemberMethodType.addToken(tokenMemberMethodName)
            tokenMemberMethodName.addToken(tokenOpenBracketMethod)

            // Instructions
            val singleTokenExclusiveXorMethodInstruction =
                stringAnalyzer1.SingleTokenExclusiveXor(
                    tokenMemberMethodVarType1,
                    tokenMemberMethodVarType2
                )

            val tokenMemberMethodVarType = stringAnalyzer1.TokenQualifiedName()
            val tokenMemberMethodVarName = stringAnalyzer1.TokenName()
            val tokenMemberMethodEquals = stringAnalyzer1.TokenEquals()
            val tokenMemberMethodExpression = stringAnalyzer1.TokenExpression1()


            // End of Instructions

            val tokenMultiMembersInstructions = stringAnalyzer1.MultiTokenOptional(
                tokenMemberMethodVarType1, tokenMemberMethodVarType2
            )

            tokenMemberMethodType.addToken(tokenMultiMembersInstructions)

            tokenPackage.addToken(tokenPackageName)
            tokenPackageName.addToken(tokenPackageSemicolon)
            tokenClass.addToken(tokenClassName)
            tokenClassName.addToken(tokenOpenBracket)
            val multiTokenOptional = stringAnalyzer1.MultiTokenOptional(
                tokenMemberVar, tokenMemberMethodType
            )
            tokenOpenBracket.addToken(multiTokenOptional)
            multiTokenOptional.addToken(tokenCloseBracket)

            val token = stringAnalyzer1.SingleTokenOptional(tokenPackage)

            token.addToken(tokenClass)

            return token

        }
    */
    @Test
    fun testStringAnalyzerPackageAndClassDeclarationAndImpl() {
        val stringAnalyzer1: StringAnalyzer1 = StringAnalyzer1()


        val tokenPackage = stringAnalyzer1.TokenPackage()
        val tokenPackageName = stringAnalyzer1.TokenQualifiedName()
        val tokenPackageSemicolon = stringAnalyzer1.TokenSemiColon()
        val tokenClass: StringAnalyzer1.Token = stringAnalyzer1.TokenClassKeyword()


        class ActionPackageName : Action(tokenPackageName) {
            public override fun action(): Boolean {
                stringAnalyzer1.construct.currentClass.setPackageName(tokenPackageName.name)
                stringAnalyzer1.construct.packageName = tokenPackageName.name
                return true
            }
        }
        tokenPackageName.setAction(ActionPackageName())

        class ActionClassKeyword : Action(tokenClass) {
            public override fun action(): Boolean {

                stringAnalyzer1.construct.currentClass = Class()
                stringAnalyzer1.construct.classes.add(stringAnalyzer1.construct.currentClass)
                stringAnalyzer1.construct.packageName = tokenPackageName.name
                stringAnalyzer1.construct.currentClass.setPackageName(tokenPackageName.name)
                return true
            }
        }
        tokenClass.setAction(ActionClassKeyword())

        val tokenClassName = stringAnalyzer1.TokenName()

        class ActionClassname : Action(tokenClassName) {
            override fun action(): Boolean {
                stringAnalyzer1.construct.currentClass.name = tokenPackageName.name
                return true
            }
        }
        tokenClass.setAction(ActionClassname())
        val tokenOpenBracket = stringAnalyzer1.TokenOpenBracket()
        val tokenCloseBracketClass = stringAnalyzer1.TokenCloseBracket()

        val tokenMemberVarType1 = stringAnalyzer1.TokenQualifiedName()
        val tokenMemberVarName1 = stringAnalyzer1.TokenName()
        val tokenMemberVarEquals1 = stringAnalyzer1.TokenEquals()
        val tokenMemberExpression1 = stringAnalyzer1.TokenExpression1()
        val tokenSemiColonVar1 = stringAnalyzer1.TokenSemiColon()

        val tokenMemberVarType2 = stringAnalyzer1.TokenQualifiedName()
        val tokenMemberVarName2 = stringAnalyzer1.TokenName()
        val tokenSemiColonVar2 = stringAnalyzer1.TokenSemiColon()

        tokenMemberVarType1.addToken(tokenMemberVarName1)
        tokenMemberVarEquals1.addToken(tokenMemberVarEquals1)
        tokenMemberVarEquals1.addToken(tokenMemberExpression1)
        tokenMemberExpression1.addToken(tokenSemiColonVar1)

        tokenMemberVarType2.addToken(tokenMemberVarName2)
        tokenMemberVarName2.addToken(tokenSemiColonVar2)


        val tokenMemberMethodVarType1 = stringAnalyzer1.TokenQualifiedName()
        val tokenMemberMethodVarName1 = stringAnalyzer1.TokenName()
        val tokenSemiMethodColonVar1 = stringAnalyzer1.TokenSemiColon()

        val tokenMemberMethodVarType2 = stringAnalyzer1.TokenQualifiedName()
        val tokenMemberMethodVarName2 = stringAnalyzer1.TokenName()
        val tokenMemberMethodVarEquals2 = stringAnalyzer1.TokenEquals()
        val tokenMemberMethodExpression2 = stringAnalyzer1.TokenExpression1()
        val tokenMethodSemiColonVar2 = stringAnalyzer1.TokenSemiColon()

        val tokenMemberMethodExpression3 = stringAnalyzer1.TokenExpression1()
        val tokenMethodSemiColonVar3 = stringAnalyzer1.TokenSemiColon()

        tokenMemberMethodVarType2.addToken(tokenMemberMethodVarName2)
        tokenMemberMethodVarName2.addToken(tokenMemberMethodVarEquals2)
        tokenMemberMethodVarEquals2.addToken(tokenMemberMethodExpression2)
        tokenMemberMethodExpression2.addToken(tokenMethodSemiColonVar2)

        tokenMemberMethodVarType1.addToken(tokenMemberMethodVarName1)
        tokenMemberMethodVarName1.addToken(tokenSemiMethodColonVar1)

        tokenMemberMethodExpression3.addToken(tokenMethodSemiColonVar3)

        val tokenMemberVar = stringAnalyzer1.SingleTokenExclusiveXor(
            tokenMemberVarType1, tokenMemberVarType2, tokenMemberMethodExpression3
        )

        val tokenMemberMethodType = stringAnalyzer1.TokenQualifiedName()
        val tokenMemberMethodName = stringAnalyzer1.TokenName()
        val tokenOpenBracketMethod = stringAnalyzer1.TokenOpenBracket()

        tokenMemberMethodType.addToken(tokenMemberMethodName)
        tokenMemberMethodName.addToken(tokenOpenBracketMethod)

        // Instructions
        val singleTokenExclusiveXorMethodInstruction =
            stringAnalyzer1.SingleTokenExclusiveXor(
                tokenMemberMethodVarType1,
                tokenMemberMethodVarType2
            )

        val tokenMemberMethodVarType = stringAnalyzer1.TokenQualifiedName()
        val tokenMemberMethodVarName = stringAnalyzer1.TokenName()
        val tokenMemberMethodEquals = stringAnalyzer1.TokenEquals()
        val tokenMemberMethodExpression = stringAnalyzer1.TokenExpression1()
        val tokenMemberMethodSemicolon = stringAnalyzer1.TokenSemiColon()


        // End of Instructions

        val tokenMultiMembersInstructions = stringAnalyzer1.MultiTokenOptional(
            singleTokenExclusiveXorMethodInstruction
        )

        //tokenOpenBracketMethod.addToken(tokenMultiMembersInstructions)
        tokenPackage.addToken(tokenPackageName)
        tokenPackageName.addToken(tokenPackageSemicolon)
        tokenPackageSemicolon.addToken(tokenClass)
        tokenClass.addToken(tokenClassName)
        tokenClassName.addToken(tokenOpenBracket)
        val multiTokenOptional = stringAnalyzer1.MultiTokenOptional(
            tokenMemberVar, tokenMemberMethodType
        )
        tokenOpenBracket.addToken(multiTokenOptional)
        val tokenCloseBracketMethod = stringAnalyzer1.TokenCloseBracket()
        tokenMultiMembersInstructions.addToken(tokenCloseBracketMethod)
        multiTokenOptional.addToken(tokenCloseBracketClass)


        val token = tokenPackage

        //token.addToken(tokenClass)

        val input = "package com.example;\nclass Graph {\n}\n"
        var parse = -1
        try {
            parse = stringAnalyzer1.parse(token, input)
//            parse = stringAnalyzer1.mPosition
        } catch (ex: RuntimeException) {
            if (token.isSuccessful) {
                if (parse >= input.length) {
                    Assert.assertTrue(true)
                    return
                }
            }
        }
        println("TestStringAnalyzer1.testStringAnalyzerPackage")
        println("parse = " + parse + "/" + input.length)
        println("isSuccessful = " + token.isSuccessful + " tokenSemicolon : " + tokenPackageSemicolon.isSuccessful)
        println("Name : " + tokenPackageName.name)
        println(stringAnalyzer1.construct)
        if (parse >= input.length)
            println("")
        else
            println(input.substring(parse))

        if (token.isSuccessful) {
            if (parse >= input.length) {
                Assert.assertTrue(true)
                return
            }
        }

        Assert.assertTrue(false)


    }

}