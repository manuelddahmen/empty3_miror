package one.empty3.library1.tree
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

import one.empty3.Run
import org.junit.Assert
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.fileVisitor
import kotlin.system.exitProcess

/**
 * Test class for AlgebraicTree.
 */
@RunWith(JUnit4::class)
class TestStringAnalyzer5 {
    private var isDebug: Boolean = true
    fun readDir(directory_path: String) {
        var succeed = true
        File(directory_path).listFiles()?.forEach {
            val file = it
            if (file != null && file.name.endsWith(".java_code")) {
                val stringAnalyzer3: StringAnalyzer3 = StringAnalyzer3()
                val javaToken5 = getJavaToken5(stringAnalyzer3)
                if (javaToken5 != null) {
                    val readString = readString(file.absolutePath)
                    val parse = 0
                    try {
                        val parse = javaToken5.parse(readString, parse)

                    } catch (ex: RuntimeException) {
                        if (stringAnalyzer3.mPosition < readString.length - 1) {
                            ex.printStackTrace()
                        }
                    }

                    println("------------------------------------------------------------------------")
                    println("- " + file.name)
                    println("------------------------------------------------------------------------")
                    println("- " + stringAnalyzer3.construct.toLangStringJava(isDebug))
                    println("------------------------------------------------------------------------")
                    println("- " + "amount of code: " + (stringAnalyzer3.mPosition + 1) + "/" + readString.length)
                    println("------------------------------------------------------------------------")



                    succeed = succeed && (stringAnalyzer3.mPosition + 1 >= readString.length)
                };
            }
        }
        Assert.assertTrue(succeed)
    }


    fun readString(file_path: String): String {
        try {
            val allLines = Files.readAllLines(Paths.get(file_path))
            // Convert the List of strings to a single string
            val sb: StringBuilder = StringBuilder()
            allLines.forEach { s -> sb.append(s).append("\n") }
            return sb.toString()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        throw RuntimeException("Not found or read fails")
    }

    public fun getJavaToken5(stringAnalyzer3: StringAnalyzer3): StringAnalyzer3.Token {
        val tokenPackage = stringAnalyzer3.TokenPackage()
        val tokenPackageName = stringAnalyzer3.TokenQualifiedName()
        val tokenPackageSemicolon = stringAnalyzer3.TokenSemiColon()
        val tokenClass: StringAnalyzer3.Token = stringAnalyzer3.TokenClassKeyword()


        class ActionPackageName(token: StringAnalyzer3.Token?) : Action3(token) {
            public override fun action(): Boolean {
                if (!tokenPackageName.name.isEmpty()) {
                    stringAnalyzer3.construct.currentClass.setPackageName(tokenPackageName.name)
                    stringAnalyzer3.construct.packageName = tokenPackageName.name
                }
                return true
            }
        }
        ActionPackageName(tokenPackageName)

        val tokenImport = stringAnalyzer3.TokenString("import")
        val tokenImportName = stringAnalyzer3.TokenQualifiedName()
        val tokenImportSemicolon = stringAnalyzer3.TokenSemiColon()


        class ActionClassKeyword(token: StringAnalyzer3.Token?) : Action3(token) {
            public override fun action(): Boolean {
                stringAnalyzer3.construct.classes.add(stringAnalyzer3.construct.currentClass)
                stringAnalyzer3.construct.currentClass.setPackageName(stringAnalyzer3.construct.packageName)
                return true
            }
        }
        tokenClass.setAction(ActionClassKeyword(tokenClass))

        val tokenClassName = stringAnalyzer3.TokenName()

        class ActionClassname(token: StringAnalyzer3.Token) : Action3(token) {
            override fun action(): Boolean {
                if (tokenClassName.name != null) {
                    stringAnalyzer3.construct.currentClass.name = tokenClassName.name
                }
                return true
            }
        }
        ActionClassname(tokenClassName)

        val tokenOpenBracket = stringAnalyzer3.TokenOpenBracket()
        val tokenCloseBracketClass = stringAnalyzer3.TokenCloseBracket()

        // Variables members declarations
        val tokenMemberVarType1 = stringAnalyzer3.TokenQualifiedName()
        val tokenMemberVarName1 = stringAnalyzer3.TokenName()
        val tokenMemberVarEquals1 = stringAnalyzer3.TokenEquals()
        val tokenMemberExpression1 = stringAnalyzer3.TokenExpression1()
        val tokenMemberVarSemiColon1 = stringAnalyzer3.TokenSemiColon()

        val tokenMemberVarType2 = stringAnalyzer3.TokenQualifiedName()
        val tokenMemberVarName2 = stringAnalyzer3.TokenName()
        val tokenMemberVarSemiColon2 = stringAnalyzer3.TokenSemiColon()

        tokenMemberVarType1.addToken(tokenMemberVarName1)
        tokenMemberVarName1.addToken(tokenMemberVarEquals1)
        tokenMemberVarEquals1.addToken(tokenMemberExpression1)
        tokenMemberExpression1.addToken(tokenMemberVarSemiColon1)

        tokenMemberVarType2.addToken(tokenMemberVarName2)
        tokenMemberVarName2.addToken(tokenMemberVarSemiColon2)

        // Method's instructions
        val tokenMemberMethodVarType1 = stringAnalyzer3.TokenQualifiedName()
        val tokenMemberMethodVarName1 = stringAnalyzer3.TokenName()
        val tokenMethodSemiColonVar1 = stringAnalyzer3.TokenSemiColon()

        val tokenMemberMethodVarType2 = stringAnalyzer3.TokenQualifiedName()
        val tokenMemberMethodVarName2 = stringAnalyzer3.TokenName()
        val tokenMemberMethodVarEquals2 = stringAnalyzer3.TokenEquals()
        val tokenMemberMethodExpression2 = stringAnalyzer3.TokenExpression1()
        val tokenMethodSemiColonVar2 = stringAnalyzer3.TokenSemiColon()

        val tokenMemberMethodExpression3 = stringAnalyzer3.TokenExpression1()
        val tokenMethodSemiColonVar3 = stringAnalyzer3.TokenSemiColon()

        val tokenMemberMethodVarName4 = stringAnalyzer3.TokenName()
        val tokenMemberMethodVarEquals4 = stringAnalyzer3.TokenEquals()
        val tokenMemberMethodExpression4 = stringAnalyzer3.TokenExpression1()
        val tokenMethodSemiColonVar4 = stringAnalyzer3.TokenSemiColon()

        class ActionTokenSemiColonInstruction(token: StringAnalyzer3.Token?) : Action3(token) {
            override fun action(): Boolean {
                val instructions = stringAnalyzer3.construct.currentInstructions
                val instruction = Instruction()
                if (tokenMemberMethodVarType1.name != null) {
                    instruction.type = tokenMemberMethodVarType1.name
                }
                if (tokenMemberMethodVarType2.name != null) {
                    instruction.type = tokenMemberMethodVarType2.name
                }
                if (tokenMemberMethodVarName1.name != null) {
                    instruction.name = tokenMemberMethodVarName1.name
                }
                if (tokenMemberMethodVarName2.name != null) {
                    instruction.name = tokenMemberMethodVarName2.name
                    instruction.expression.leftHand = tokenMemberMethodVarName2.name
                }
                if (tokenMemberMethodVarName4.name != null) {
                    instruction.name = tokenMemberMethodVarName4.name
                    instruction.expression.leftHand = tokenMemberMethodVarName2.name
                }
                if (tokenMemberMethodExpression2.expression != null) {
                    instruction.expression.expression = tokenMemberMethodExpression2.expression
                }
                if (tokenMemberMethodExpression4.expression != null) {
                    instruction.expression.expression = tokenMemberMethodExpression4.expression
                }
                instructions.instructionList.add(instruction)
                return true
            }

        }
        ActionTokenSemiColonInstruction(tokenMethodSemiColonVar1)
        ActionTokenSemiColonInstruction(tokenMethodSemiColonVar2)
        ActionTokenSemiColonInstruction(tokenMethodSemiColonVar3)
        ActionTokenSemiColonInstruction(tokenMethodSemiColonVar4)

        tokenMemberMethodVarType2.addToken(tokenMemberMethodVarName2)
        tokenMemberMethodVarName2.addToken(tokenMemberMethodVarEquals2)
        tokenMemberMethodVarEquals2.addToken(tokenMemberMethodExpression2)
        tokenMemberMethodExpression2.addToken(tokenMethodSemiColonVar2)

        tokenMemberMethodVarType1.addToken(tokenMemberMethodVarName1)
        tokenMemberMethodVarName1.addToken(tokenMethodSemiColonVar1)

        tokenMemberMethodExpression3.addToken(tokenMethodSemiColonVar3)

        tokenMemberMethodVarName4.addToken(tokenMemberMethodVarEquals4)
        tokenMemberMethodVarEquals4.addToken(tokenMemberMethodExpression4)
        tokenMemberMethodExpression4.addToken(tokenMethodSemiColonVar4)

        val tokenMemberVar = stringAnalyzer3.SingleTokenExclusiveXor(
            tokenMemberVarType1, tokenMemberVarType2
        )

        val tokenMemberMethodType = stringAnalyzer3.TokenQualifiedName()
        val tokenMemberMethodName = stringAnalyzer3.TokenName()

        // Arguments' list
        val tokenOpenParenthesizedMethodParameter = stringAnalyzer3.TokenOpenParenthesized()
        val tokenComaMethodParameter1 = stringAnalyzer3.TokenComa()
        val tokenQualifiedNameMethodParameter1 = stringAnalyzer3.TokenQualifiedName()
        val tokenNameMethodParameter1 = stringAnalyzer3.TokenName()

        //val tokenComaMethodParameter2 = stringAnalyzer3.TokenComa()
        val tokenQualifiedNameMethodParameter2 = stringAnalyzer3.TokenQualifiedName()
        val tokenNameMethodParameter2 = stringAnalyzer3.TokenName()

        val tokenCloseParenthesizedMethodParameter = stringAnalyzer3.TokenCloseParenthesized()

        val multiTokenOptionalMethodParameter2 =
            stringAnalyzer3.MultiTokenMandatory(
                tokenComaMethodParameter1, tokenQualifiedNameMethodParameter1, tokenNameMethodParameter1
            )
        val multiTokenOptionalMethodParameter1 =
            stringAnalyzer3.MultiTokenMandatory(
                tokenQualifiedNameMethodParameter2, tokenNameMethodParameter2
            )

        val multiTokenOptionalMethodParameter = stringAnalyzer3.MultiTokenExclusiveXor(
            multiTokenOptionalMethodParameter1, multiTokenOptionalMethodParameter2
        )

        tokenOpenParenthesizedMethodParameter.addToken(multiTokenOptionalMethodParameter)
        multiTokenOptionalMethodParameter.addToken(tokenCloseParenthesizedMethodParameter)
        //multiTokenOptionalMethodParameter2.addToken(tokenCloseParenthesizedMethodParameter)//???
        val tokenOpenBracketMethod = stringAnalyzer3.TokenOpenBracket()
        tokenCloseParenthesizedMethodParameter.addToken(tokenOpenBracketMethod)


        class ActionTokenOpenParenthesizedMethodParameter(token: StringAnalyzer3.Token?) : Action3(token) {

            override fun action(): Boolean {
                if (tokenMemberMethodType.name != null && tokenMemberMethodName.name != null) {
                    stringAnalyzer3.construct.currentMethod.ofClass = Variable()
                    stringAnalyzer3.construct.currentMethod.ofClass.classStr = tokenMemberMethodType.name
                    stringAnalyzer3.construct.currentMethod.name = tokenMemberMethodName.name

                    if (stringAnalyzer3.construct.currentMethod.instructions.instructionList == null) {
                        stringAnalyzer3.construct.currentMethod.instructions = InstructionBlock()
                    }
                    stringAnalyzer3.construct.pushInstructions(stringAnalyzer3.construct.currentMethod.instructions)
                }
                return true
            }
        }

        class ActionParamType(token: StringAnalyzer3.Token?) : Action3(token) {
            override fun action(): Boolean {
                if (token.isSuccessful) {
                    val name = (token as StringAnalyzer3.TokenQualifiedName).name
                    if (name != null) {
                        val parameterList = stringAnalyzer3.construct.currentMethod.parameterList
                        parameterList.add(Variable())
                        if (parameterList.size > 0) {
                            parameterList[parameterList.size - 1].classStr = name
                            (token as StringAnalyzer3.TokenQualifiedName).name = null
                        }
                    }
                }
                return true
            }
        }

        class ActionParamName(token: StringAnalyzer3.Token?) : Action3(token) {
            override fun action(): Boolean {
                if (token.isSuccessful) {
                    val name = (token as StringAnalyzer3.TokenName).name
                    if (name != null) {
                        val parameterList = stringAnalyzer3.construct.currentMethod.parameterList
                        parameterList[parameterList.size - 1].name = name
                        (token as StringAnalyzer3.TokenName).name = null
                    }
                }
                return true
            }
        }

        ActionParamType(tokenQualifiedNameMethodParameter1)
        ActionParamType(tokenQualifiedNameMethodParameter2)
        ActionParamName(tokenNameMethodParameter1)
        ActionParamName(tokenNameMethodParameter2)

        class ActionVarType(token: StringAnalyzer3.Token?) : Action3(token) {
            override fun action(): Boolean {
                if (token.isSuccessful) {

                    var name: String? = null
                    if (token == tokenMemberVarSemiColon1) {
                        name = tokenMemberVarType1.name
                    } else if (token == tokenMemberVarSemiColon2) {
                        name = tokenMemberVarType2.name
                    }

                    if (name != null) {
                        val parameterList = stringAnalyzer3.construct.currentClass.variableList
                        val variable = Variable()
                        parameterList.add(variable)
                        variable.classStr = name
                    }
                    name = null
                    if (token == tokenMemberVarSemiColon1) {
                        name = tokenMemberVarName1.name
                    } else if (token == tokenMemberVarSemiColon2) {
                        name = tokenMemberVarName2.name
                    }
                    if (name != null) {
                        val parameterList = stringAnalyzer3.construct.currentClass.variableList
                        val variable = parameterList.get(parameterList.size - 1)// Variable()
                        variable.name = name
                    }
                }
                return true
            }
        }

        ActionVarType(tokenMemberVarSemiColon1)
//        ActionVarName(tokenMemberVarSemiColon1)
        ActionVarType(tokenMemberVarSemiColon2)
//        ActionVarName(tokenMemberVarSemiColon2)

        tokenMemberMethodType.addToken(tokenMemberMethodName)
        tokenMemberMethodName.addToken(tokenOpenParenthesizedMethodParameter)

        val tokenIf = stringAnalyzer3.TokenString("if")
        val tokenElse = stringAnalyzer3.TokenString("else")

        val tokenWhile = stringAnalyzer3.TokenString("while")
        val tokenDo = stringAnalyzer3.TokenString("do")

        val instruction = stringAnalyzer3.SingleTokenExclusiveXor(
            tokenIf, // Test keywords first.
            tokenDo,
            tokenWhile,
            tokenMemberMethodVarType1,
            tokenMemberMethodVarType2,
            tokenMemberMethodExpression3,
            tokenMemberMethodVarName4
        )
        // Instructions
        val tokenSingleInstructionIf = instruction//.copy(instruction)
        val tokenSingleInstructionElse = instruction//.copy(instruction)
        val tokenSingleInstructionDo = instruction//.copy(instruction)
        val tokenSingleInstructionWhile = instruction//.copy(instruction)
        val tokenMultiMembersInstructions = stringAnalyzer3.MultiTokenExclusiveXor(instruction)
        val tokenMultiMembersInstructionsWhile = stringAnalyzer3.MultiTokenExclusiveXor(instruction)
        val tokenMultiMembersInstructionsIf = stringAnalyzer3.MultiTokenExclusiveXor(instruction)


        // End of Instructions

        val tokenCloseBracketMethod = stringAnalyzer3.TokenCloseBracket()
        tokenOpenBracketMethod.addToken(tokenMultiMembersInstructions)
        tokenMultiMembersInstructions.addToken(tokenCloseBracketMethod)

        ActionTokenOpenParenthesizedMethodParameter(tokenOpenBracketMethod)

        // Instructions' flow controls (if-else, while, do while, for-i, for-:, switch)

        // Block without controls
        val instructionBlockOpenBracket = stringAnalyzer3.TokenString("{")
        val instructionBlockCloseBracket = stringAnalyzer3.TokenString("}")
        val instructionBlock = stringAnalyzer3.SingleTokenMandatory(
            stringAnalyzer3.TokenString("{"),
            tokenMultiMembersInstructions,
            stringAnalyzer3.TokenString("}")
        )
        val instructionBlockWhile = stringAnalyzer3.SingleTokenMandatory(
            stringAnalyzer3.TokenString("{"),
            tokenMultiMembersInstructionsWhile,
            stringAnalyzer3.TokenString("}")
        )
        val instructionBlockIf = stringAnalyzer3.SingleTokenMandatory(
            stringAnalyzer3.TokenString("{"),
            tokenMultiMembersInstructionsIf,
            stringAnalyzer3.TokenString("}")
        )
        // Logical expression
        val logicalExpressionExpression = stringAnalyzer3.TokenLogicalExpression()
        val logicalExpression = stringAnalyzer3.SingleTokenMandatory(logicalExpressionExpression)
        val logicalExpressionWhile = stringAnalyzer3.TokenLogicalExpression()
        val logicalExpressionDo = stringAnalyzer3.SingleTokenMandatory(logicalExpressionExpression)

        // if flow control

        val instructionsIf = stringAnalyzer3.SingleTokenExclusiveXor(
            tokenSingleInstructionIf, instructionBlockIf
        )
        val instructionsElse = stringAnalyzer3.SingleTokenExclusiveXor(
            tokenSingleInstructionElse, instructionBlock
        )
        val instructionsWhile = stringAnalyzer3.SingleTokenExclusiveXor(
            tokenSingleInstructionWhile, instructionBlockWhile
        )
        val instructionsDo = stringAnalyzer3.SingleTokenExclusiveXor(
            tokenSingleInstructionDo, instructionBlock
        )

        tokenIf.addToken(logicalExpression)
        logicalExpression.addToken(instructionsIf)
        instructionsIf.addToken(
            stringAnalyzer3.SingleTokenOptional(
                stringAnalyzer3.SingleTokenMandatory(
                    tokenElse
                )
            )
        )
        tokenWhile.addToken(logicalExpressionWhile)
        logicalExpressionWhile.addToken(instructionsWhile)
        //tokenDo.addToken(logicalExpressionDo)
        //logicalExpressionDo.addToken(instructionsWhile)

        class ActionExpressionType(token: StringAnalyzer3.Token) : Action3(token) {
            override fun action(): Boolean {
                if (token.isSuccessful) {
                    if (stringAnalyzer3.construct.currentInstructions != null) {
                        val instructions = stringAnalyzer3.construct.currentInstructions.instructionList
                        if (token == tokenMethodSemiColonVar1) {
                            if (tokenMemberMethodVarType1.name != null) {
                                val name = tokenMemberMethodVarType1.name
                                //tokenMemberMethodVarType1.name = null
                                instructions.add(Instruction())
                                val get = instructions.get(instructions.size - 1)
                                (get as Instruction).setType(name)
                                tokenMemberMethodVarType1.name = null
                            }
                            if (tokenMemberMethodVarName1.name != null) {
                                val name = tokenMemberMethodVarName1.name
                                val get = instructions.get(instructions.size - 1) as Instruction
                                get.setName(name)
                                get.expression.leftHand = name
                                tokenMemberMethodVarName1.name = null
                            }
                        }
                        if (token == tokenMethodSemiColonVar2) {
                            if (tokenMemberMethodVarType2.name != null) {
                                val name = tokenMemberMethodVarType2.name
                                instructions.add(Instruction())
                                val get = instructions.get(instructions.size - 1)
                                (get as Instruction).setType(name)
                                tokenMemberMethodVarType2.name = null
                            }
                            if (tokenMemberMethodVarName2.name != null) {
                                var name = tokenMemberMethodVarName2.name
                                //instructions.add(Instruction())
                                val get = instructions.get(instructions.size - 1)
                                (get as Instruction).setName(name)
                                get.expression.leftHand = name
                                tokenMemberMethodVarName2.name = null
                            }
                        }
                        if (token == tokenMethodSemiColonVar4) {
                            if (tokenMemberMethodVarName4.name != null) {
                                var name = tokenMemberMethodVarName4.name
                                //tokenMemberMethodVarName2.name = null
                                instructions.add(Instruction())
                                val get = instructions.get(instructions.size - 1)
                                (get as Instruction).setName(name)
                                get.expression.leftHand = name
                                tokenMemberMethodVarName4.name = null
                            }
                        }
                        if (token == tokenMethodSemiColonVar2) {
                            if (tokenMemberMethodExpression2.expression != null) {
                                var name = tokenMemberMethodExpression2.expression
                                val get = instructions.get(instructions.size - 1)
                                (get as Instruction).getExpression().expression = name
                                tokenMemberMethodExpression2.expression = null
                            }
                        }
                        if (token == tokenMethodSemiColonVar3) {
                            if (tokenMemberMethodExpression3.expression != null) {
                                var name = tokenMemberMethodExpression3.expression
                                instructions.add(Instruction())
                                val get = instructions.get(instructions.size - 1)
                                (get as Instruction).getExpression().expression = name
                                tokenMemberMethodExpression3.expression = null
                            }
                        }
                        if (token == tokenMethodSemiColonVar4) {
                            if (tokenMemberMethodExpression4.expression != null) {
                                val name = tokenMemberMethodExpression4.expression
                                val get = instructions.get(instructions.size - 1)
                                (get as Instruction).getExpression().expression = name
                                tokenMemberMethodExpression4.expression = null
                            }
                        }
                    }
                }
                return true
            }
        }

        ActionExpressionType(tokenMethodSemiColonVar1)
        ActionExpressionType(tokenMethodSemiColonVar2)
        ActionExpressionType(tokenMethodSemiColonVar3)
        ActionExpressionType(tokenMethodSemiColonVar4)

        tokenElse.addToken(instructionsElse)///??? Imbriquées ?
        class ActionPushMethod(token: StringAnalyzer3.Token?) : Action3(token) {
            override fun action(): Boolean {
                if (tokenMemberMethodName.name != null && !tokenMemberMethodName.name.isEmpty() &&
                    tokenMemberMethodType.name != null && !tokenMemberMethodType.name.isEmpty()
                ) {
                    val methodList = stringAnalyzer3.construct.currentClass.methodList
                    methodList.add(stringAnalyzer3.construct.currentMethod)
                    //stringAnalyzer3.construct.currentMethod.name = tokenMemberMethodName.name
                    //stringAnalyzer3.construct.currentMethod.ofClass.classStr = tokenMemberMethodType.name
                    //stringAnalyzer3.construct.currentMethod.ofClass.name = tokenMemberMethodType.name
                    stringAnalyzer3.construct.popInstructions()
                    stringAnalyzer3.construct.currentMethod = Method()
                } else {

                }
                return true
            }
        }
        ActionPushMethod(tokenCloseBracketMethod)

        class ActionPopContext(token: StringAnalyzer3.Token) : Action3(token) {
            override fun action(): Boolean {
                //val popInstructions = stringAnalyzer3.construct.popInstructions()
                //stringAnalyzer3.construct.currentInstructions.instructionList.add(popInstructions)
                return true
            }
        }

        //ActionPopContext(instructionsIf)
        //ActionPopContext(instructionsElse)
        //ActionPopContext(instructionsWhile)
        //ActionPopContext(instructionBlock)
        //ActionPopContext(instructionBlockCloseBracket)

        tokenPackage.addToken(tokenPackageName)
        tokenPackageName.addToken(tokenPackageSemicolon)
        val tokenPackageOptional =
            stringAnalyzer3.SingleTokenOptional(
                tokenPackage
            )
        tokenImport.addToken(tokenImportName)
        tokenImportName.addToken(tokenImportSemicolon)

        val multiTokenMandatoryImport =
            stringAnalyzer3.MultiTokenOptional(
                tokenImport
            )

        tokenPackageOptional.addToken(multiTokenMandatoryImport)
        multiTokenMandatoryImport.addToken(tokenClass)
        tokenClass.addToken(tokenClassName)
        tokenClassName.addToken(tokenOpenBracket)
        val multiTokenOptional = stringAnalyzer3.MultiTokenExclusiveXor(
            tokenCloseBracketClass, tokenMemberMethodType, tokenMemberVar
        )
        tokenOpenBracket.addToken(multiTokenOptional)
        //multiTokenOptional.addToken(tokenCloseBracketClass)
        class ActionCloseBracketClass(token: StringAnalyzer3.Token) : Action3(token) {
            override fun action(): Boolean {
                if (token.isSuccessful) {
                    stringAnalyzer3.construct.classes.add(stringAnalyzer3.construct.currentClass)
                    stringAnalyzer3.construct.currentClass = Class()
                }
                return true
            }
        }
        ActionCloseBracketClass(tokenCloseBracketClass)

        class ActionIf(token: StringAnalyzer3.Token) : Action3(token) {
            init {
                //on = ON_RETURNS_TRUE_NEXT_TOKEN
            }

            override fun action(): Boolean {
                //if (this.token == tokenIf) {
                try {
                    //if (token.isSuccessful) {
                    val value: ControlledInstructions.If =
                        ControlledInstructions.If(logicalExpressionExpression.expression)
                    stringAnalyzer3.construct.currentInstructions.instructionList.add(value)
                    //value.instructionList.add(InstructionBlock())
                    stringAnalyzer3.construct.pushInstructions(value)
                    tokenIf.isSuccessful = false
                    //}
                } catch (ex: IndexOutOfBoundsException) {
                    ex.printStackTrace()
                }
                return true
            }
        }

        class ActionElseInstructions(token: StringAnalyzer3.Token) : Action3(token) {
            override fun action(): Boolean {
                try {
                    if (stringAnalyzer3.construct.currentInstructions != null &&
                        stringAnalyzer3.construct.currentInstructions.instructionList.size > 0
                    ) {
                        stringAnalyzer3.construct.popInstructions()
                    } else {
                        throw EmptyEndOfBlockList("ElseInstruction : empty after end of block instructions\' list");
                    }
                } catch (ex: IndexOutOfBoundsException) {
                    ex.printStackTrace()
                }
                return true
            }
        }


        class ActionElse(token: StringAnalyzer3.Token) : Action3(token) {
            override fun action(): Boolean {
                var instructionList: MutableList<InstructionBlock> =
                    stringAnalyzer3.construct.currentInstructions.instructionList
                //stringAnalyzer3.construct.popInstructions()
                instructionList = stringAnalyzer3.construct.currentInstructions.instructionList
                if (instructionList.size > 0) {
                    val instructionIf = instructionList.get(instructionList.size - 1)
                    if (instructionIf is ControlledInstructions.If) {
//                        stringAnalyzer3.construct.popInstructions()
                        stringAnalyzer3.construct.pushInstructions(instructionIf.instructionsElse)
                    }
                }
                return true
            }
        }

        // REFACTOR
        class ActionIfInstructions(token: StringAnalyzer3.Token) : Action3(token) {
            override fun action(): Boolean {
                var instructionList: MutableList<InstructionBlock> =
                    stringAnalyzer3.construct.currentInstructions.instructionList
                stringAnalyzer3.construct.popInstructions()
                instructionList = stringAnalyzer3.construct.currentInstructions.instructionList
                if (instructionList.size > 0) {
                    val instructionIf = instructionList.get(instructionList.size - 1)
                    if (instructionIf is ControlledInstructions.If && tokenElse.isSuccessful) {
                        //stringAnalyzer3.construct.pushInstructions(instructionIf.instructionsElse)
                        //tokenElse.isSuccessful = false
                    } else if (!tokenElse.isSuccessful) {

                    }
                }
                return true
            }
        }

        ActionIf(logicalExpressionExpression)
        ActionElse(tokenElse)
        ActionElseInstructions(instructionsElse)
        ActionIfInstructions(instructionsIf)

        class ActionWhile(token: StringAnalyzer3.Token) : Action3(token) {
            override fun action(): Boolean {
                try {
                    val value: ControlledInstructions.While =
                        ControlledInstructions.While(logicalExpressionExpression.expression)
                    stringAnalyzer3.construct.currentInstructions.instructionList.add(value)
                    stringAnalyzer3.construct.pushInstructions(value)
                } catch (ex: IndexOutOfBoundsException) {
                    ex.printStackTrace()
                }
                return true
            }

        }

        class ActionWhileStart(token: StringAnalyzer3.Token) : Action3(token) {
            override fun action(): Boolean {

                val currentInstructions = stringAnalyzer3.construct.currentInstructions
                if (currentInstructions is ControlledInstructions.While) {
                    currentInstructions.controlExpression =
                        logicalExpressionWhile.expression
                }
                return true
            }
        }

        class ActionWhileEnd(token: StringAnalyzer3.Token) : Action3(token) {
            override fun action(): Boolean {
                //stringAnalyzer3.construct.popInstructions()
                return true
            }
        }

        ActionWhile(tokenWhile)
        ActionWhileStart(logicalExpressionWhile)
        ActionWhileEnd(instructionsWhile)

        class ActionDoWhile_Start
            (token: StringAnalyzer3.Token) : Action3(token) {
            override fun action(): Boolean {
                try {
                    val currentInstructions = stringAnalyzer3.construct.currentInstructions
                    if (currentInstructions is ControlledInstructions.DoWhile) {
                        val value: ControlledInstructions.DoWhile =
                            ControlledInstructions.DoWhile(logicalExpressionWhile.expression)
                        tokenIf.isSuccessful = false
                    }
                } catch (ex: IndexOutOfBoundsException) {
                    ex.printStackTrace()
                }
                return true
            }
        }

        return tokenPackageOptional
    }


    @Test
    fun testJavaClass4() {
        isDebug = false
        val stringAnalyzer3 = StringAnalyzer3()
        val javaToken = TestStringAnalyzer5().getJavaToken5(stringAnalyzer3)
        val token = javaToken
        val input = readString("resources/text_parser/Number4.java.java_code")

        var parse = -1
        try {
            parse = stringAnalyzer3.parse(token, input)
        } catch (ex: RuntimeException) {
            ex.printStackTrace()
            if (parse >= input.length) {
                Assert.assertTrue(true)
            } else {
                ex.printStackTrace()
                Assert.assertTrue(false)
            }
        }
        println(stringAnalyzer3.construct.toLangStringJava(false));

        if (parse >= input.length || input.substring(parse).isBlank())
        else
            if (parse >= input.length || input.substring(parse).isBlank()) {
                Assert.assertTrue(true)
            } else {
                Assert.assertFalse(true)
            }

    }

    @Test
    fun testReadMultiSources() {
        isDebug = false
        val directory_path = "resources/text_parser/"
        readDir(directory_path)
    }
}