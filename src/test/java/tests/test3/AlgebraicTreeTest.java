/*
 * Copyright (c) 2022-2023. Manuel Daniel Dahmen
 *
 *
 *    Copyright 2012-2023 Manuel Daniel Dahmen
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package tests.test3;

import one.empty3.library.core.raytracer.tree.AlgebraicFormulaSyntaxException;
import one.empty3.library.core.raytracer.tree.AlgebricTree;
import one.empty3.library.core.raytracer.tree.TreeNodeEvalException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/*__
 * Created by Manuel Dahmen on 15-12-16.
 */
public class AlgebraicTreeTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void add() throws Exception {

    }

    @Test
    public void addFactors() throws Exception {

    }

    @Test
    public void addTerms() throws Exception {

    }

    @Test
    public void addExponent() throws Exception {

    }

    @Test
    public void addFunction() throws Exception {

    }

    private boolean testResultVariable(String expr, double expectedResult, Map<String, Double> map, boolean echo) {
        AlgebricTree algebricTree = null;
        try {
            algebricTree = new AlgebricTree(expr);
            algebricTree.setParametersValues(map);
            algebricTree.construct();
            if (echo)
                Logger.getAnonymousLogger().log(Level.INFO, ""+algebricTree);
            try {
                Object result;
                result = algebricTree.eval();
                if (echo)
                    Logger.getAnonymousLogger().log(Level.INFO, "Result : " + result);
                if (echo)
                    Logger.getAnonymousLogger().log(Level.INFO, "Expected : " + expectedResult);
                assertTrue((double) result == expectedResult);
                return true;
            } catch (TreeNodeEvalException e) {
                e.printStackTrace();
                assertFalse(true);
            }
        } catch (AlgebraicFormulaSyntaxException e) {
            e.printStackTrace();
assertFalse(true);
        } catch(NullPointerException ex) {
            ex.printStackTrace();
assertFalse(true);
}
        return false;
    }

    protected boolean testResult(String expr, double expectedResult, boolean echo) {
        AlgebricTree algebricTree = null;
        try {
            algebricTree = new AlgebricTree(expr);
            algebricTree.construct();
            if (echo)
                Logger.getAnonymousLogger().log(Level.INFO, ""+algebricTree);
            try {
                Object result;
                result = algebricTree.eval();
                if (echo)
                    Logger.getAnonymousLogger().log(Level.INFO, "Result : " + result);
                if (echo)
                    Logger.getAnonymousLogger().log(Level.INFO, "Expected : " + expectedResult);
                assertTrue((double) result == expectedResult);
                return true;
            } catch (TreeNodeEvalException e) {
                e.printStackTrace();
                assertFalse(true);
            }
        } catch (AlgebraicFormulaSyntaxException e) {
            e.printStackTrace();
assertFalse(true);
        } catch(NullPointerException ex) {
            ex.printStackTrace();
assertFalse(true);
}
        return false;
    }

    @Test
    public void testSimpleEquation1() {
        testResult("1", 1.0, false);
    }

    @Test
    public void testSimpleEquationAdd() {
        testResult("1+1", 2.0, false);
    }

    @Test
    public void testSimpleEquationAddSubMult() {
        testResult("2*3+1*6-4", 2.0 * 3 + 1 * 6 - 4, false);
    }

    @Test
    public void testSimpleEquationAddSubMult2() {
        testResult("2*3-1*6-4", 2.0 * 3 - 1 * 6 - 4, false);
    }

    @Test
    public void testSimpleEquationAddMult() {
        testResult("2*3+1*6+4", 2 * 3 + 1 * 6 + 4, false);
    }

    @Test
    public void testSimpleEquationMult() {
        testResult("2*3", 6.0, false);
    }

    @Test
    public void testSimpleEquationAddAdd() {
        testResult("1+2+3+4+5+6", 1.0 + 2 + 3 + 4 + 5 + 6, false);
    }

    @Test
    public void testSimpleEquationAddSub() {
        testResult("1-2+3-4+5-6", 1.0 - 2 + 3 - 4 + 5 - 6, false);
    }

    @Test
    public void testSimpleEquationBracedAddAdd() {
        testResult("1+2+3-(4*2/1.5+5)*22+6", 1 + 2 + 3 - (4 * 2 / 1.5 + 5) * 22 + 6, false);
    }

    @Test
    public void testSimpleEquationAddSub2() {

        testResult("4*2/5", 4 * 2.0 / 5, false);
    }
    @Test
    public void testZeroPlusZero() {

        testResult("0+0", 0+0, false);
    }

    @Test
    public void testVariable() {

        HashMap<String, Double> vars = new HashMap<>();
        vars.put("u", 4.0);
        vars.put("v", 13.0);
        testResultVariable("u+v", 4.0 + 13.0, vars, true);
    }
    @Test
    public void testVariableZeroPlusZero() {

        HashMap<String, Double> vars = new HashMap<>();
        vars.put("R", 0.0);
        vars.put("u", 0.0);
        testResultVariable("R+u", 0, vars, false);
    }
    @Test
    public void testVariableCircle() {

        HashMap<String, Double> vars = new HashMap<>();
        vars.put("coordArr", 4.0);
        vars.put("coordArr", 4.0);
        vars.put("y", 13.0);
        vars.put("z", 13.0);
        vars.put("R", 20.0);

        testResultVariable("coordArr*coordArr+y*y+z*z-R*R", 4.0*4 + 13.0*13+13.0*13.0-20.0*20.0, vars, false);
    }

    @Test
    public void testSimpleEquationBracedMultDiv() {
        testResult("1*2*3/4*5*4", 1.0 * 2.0 * 3.0 / 4.0 * 5.0 * 4.0, false);
    }

    @Test
    public void testSimpleFunction() {
        testResult("sin(3.14)*4", Math.sin(3.14) * 4, false);
    }
    @Test
    public void testSimpleFunction1() {
        double u = 10;
        HashMap<String, Double> vars = new HashMap<>();
        vars.put("u", u);


        testResultVariable("10*cos(10*u)", 10*Math.cos(10*u), vars, true);
    }

@Test
    public void testSimpleFunction3() {
        double u = 10;
        HashMap<String, Double> vars = new HashMap<>();
        vars.put("u", u);


        testResultVariable("cos(10*u)+u", Math.cos(10*u)+u, vars, true);
    }

    @Test
    public void testSimpleFunction2() {
        double u = 10;
        HashMap<String, Double> vars = new HashMap<>();
        vars.put("u", u);


        testResultVariable("cos(5*u)*10", Math.cos(5*u)*10, vars, true);
    }

    @Test
    public void testSimple() {
        assertTrue(testResult("1", 1.0, false));
    }
    @Test
    public void testSimple3() {
        assertTrue(testResult("1+1", 2.0, false));
    }
    @Test
    public void testSimpleParentheses() {
        assertTrue(testResult("(2+3)*(4+5)", (2+3)*(4+5), false));
    }

    @Test
    public void testSimple2() {
        assertTrue(testResult("1.5", 1.5, false));
    }
}
