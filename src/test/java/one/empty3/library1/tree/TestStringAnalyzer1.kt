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
}