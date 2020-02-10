package com.tibco.cep.query.stream.expression;

import com.tibco.cep.query.stream.framework.AbstractTest;
import com.tibco.cep.query.stream.framework.SimpleIdGenerator;
import com.tibco.cep.query.stream.framework.TestGroupNames;
import com.tibco.cep.query.stream.framework.TrackedTuple;
import com.tibco.cep.query.stream.impl.expression.*;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomIntSimple;
import org.mvel.MVEL;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/*
* Author: Ashwin Jayaprakash Date: Jun 10, 2008 Time: 6:28:49 PM
*/
public class TupleAndMapExtractorTest extends AbstractTest {
    @Test(groups = {TestGroupNames.RUNTIME})
    public void testGroupByExpressionAfterJoin()
            throws ClassNotFoundException, NoSuchMethodException,
            InstantiationException, IllegalAccessException, InvocationTargetException {
        TupleExtractor innerTupleGetter = new TupleExtractor(0);

        TupleExtractor personColumnGetter = new TupleExtractor(0);

        ModelExtractor agePropertyGetter = new ModelExtractor(ModelType.CONCEPT, "age");
        ModelExtractor[] modelExtractors = new ModelExtractor[]{agePropertyGetter};

        ModelExtractor ageValueGetter = new ModelExtractor(ModelType.PROPERTY_ATOM_INT);
        ModelExtractorAdapter ageValueGetterAdapter = new ModelExtractorAdapter(ageValueGetter);

        ChainedTupleExtractor tupleExtractor = new ChainedTupleExtractor(innerTupleGetter,
                personColumnGetter, modelExtractors, ageValueGetterAdapter);

        ExtractorToEvaluatorAdapter lhs = new ExtractorToEvaluatorAdapter(tupleExtractor,
                EvaluatorToExtractorAdapter.FIXED_KEY_POSITION_IN_MAP);

        final int rhsAge = 45;
        ExpressionEvaluator rhs = new ConstantValueEvaluator(rhsAge);

        SimpleEvaluator simpleEvaluator =
                new SimpleEvaluator(lhs, rhs, JavaType.INTEGER, Operation.ADD);
        EvaluatorToExtractorAdapter e2eAdapter = new EvaluatorToExtractorAdapter(simpleEvaluator);

        //-----------

        Class customerClass = Class.forName("be.gen.Concepts.Customer");

        Constructor customerConstructor = customerClass.getConstructor(new Class[]{long.class,
                String.class});

        Object customer =
                customerConstructor.newInstance(new Object[]{new Long(0), 0 + ":" + customerClass});

        final int age = 30;
        Property ageProp = ((Concept) customer).getProperty("age");
        ((PropertyAtomIntSimple) ageProp).setInt(age);

        InputTuple inputTuple =
                new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{customer});

        JoinedInputTuple joinedInputTuple =
                new JoinedInputTuple(SimpleIdGenerator.generateNewId(), new Object[]{inputTuple});

        //Use: .. from Customer, Address .... group by (Customer.age + 45)

        int newAge = e2eAdapter.extractInteger(null, null, joinedInputTuple);
        Assert.assertEquals(newAge, (rhsAge + age), "Result is wrong.");

        //------------

        Serializable compiledMVEL = MVEL.compileExpression(
                "getColumn(0).getColumn(0).getProperty(\"age\").getValue() + 45");
        Object result = MVEL.executeExpression(compiledMVEL, joinedInputTuple);
        newAge = (Integer) result;
        Assert.assertEquals(newAge, (rhsAge + age), "MVEL evaluation was wrong.");

        //------------

        long expressionTotal = 0;
        long mvelTotal = 0;
        final long innerLoopTimes = 1000;
        for (int h = 0; h < 100000; h++) {
            int c = 0;
            long startExpression = System.nanoTime();
            c = innerLoopE2E(rhsAge, e2eAdapter, age, joinedInputTuple, innerLoopTimes, c);
            long endExpression = System.nanoTime();

            if (h > 100) {
                System.out.println(
                        "Expression time nanos: " + (endExpression - startExpression) + " over: " +
                                c);

                expressionTotal += (endExpression - startExpression);
            }

            //------------

            c = 0;
            startExpression = System.nanoTime();
            c = innerLoopMVEL(rhsAge, age, joinedInputTuple, compiledMVEL, innerLoopTimes, c);
            endExpression = System.nanoTime();

            if (h > 100) {
                System.out.println(
                        "MVEL time nanos:       " + (endExpression - startExpression) + " over: " +
                                c);

                mvelTotal += (endExpression - startExpression);
            }

            System.out.println();
        }

        System.err.println("======= testGroupByExpressionAfterJoin =======");
        System.err.println("Expression total micro: " + expressionTotal / 1000);
        System.err.println("MVEL total micro:       " + mvelTotal / 1000);
    }

    private int innerLoopMVEL(int rhsAge, int age,
                              JoinedInputTuple joinedInputTuple,
                              Serializable compiledMVEL, long innerLoopTimes, int c) {
        Object result;
        for (int i = 0; i < innerLoopTimes; i++) {
            result = MVEL.executeExpression(compiledMVEL, joinedInputTuple);
            int finalAge = (Integer) result;

            if (finalAge == age + rhsAge) {
                c++;
            }
        }
        return c;
    }

    private int innerLoopE2E(int rhsAge, EvaluatorToExtractorAdapter e2eAdapter, int age,
                             JoinedInputTuple joinedInputTuple,
                             long innerLoopTimes, int c) {
        for (int i = 0; i < innerLoopTimes; i++) {
            int finalAge = e2eAdapter.extractInteger(null, null, joinedInputTuple);

            if (finalAge == age + rhsAge) {
                c++;
            }
        }
        return c;
    }

    @Test(groups = {TestGroupNames.RUNTIME})
    public void testJoinExpression()
            throws ClassNotFoundException, NoSuchMethodException,
            InstantiationException, IllegalAccessException, InvocationTargetException {
        TupleExtractor tupleGetter = new TupleExtractor("personStream");

        TupleExtractor personColumnGetter = new TupleExtractor(0);

        ModelExtractor agePropertyGetter = new ModelExtractor(ModelType.CONCEPT, "age");
        ModelExtractor[] modelExtractors = new ModelExtractor[]{agePropertyGetter};

        ModelExtractor ageValueGetter = new ModelExtractor(ModelType.PROPERTY_ATOM_INT);
        ModelExtractorAdapter ageValueGetterAdapter = new ModelExtractorAdapter(ageValueGetter);

        ChainedMapExtractor lhs = new ChainedMapExtractor(tupleGetter,
                personColumnGetter, modelExtractors, ageValueGetterAdapter);

        final int rhsAge = 45;
        ExpressionEvaluator rhs = new ConstantValueEvaluator(rhsAge);

        SimpleEvaluator simpleEvaluator =
                new SimpleEvaluator(lhs, rhs, JavaType.INTEGER, Operation.EQUAL);

        //-----------

        Class customerClass = Class.forName("be.gen.Concepts.Customer");

        Constructor customerConstructor = customerClass.getConstructor(new Class[]{long.class,
                String.class});

        Object customer =
                customerConstructor.newInstance(new Object[]{new Long(0), 0 + ":" + customerClass});

        final int age = 30;
        Property ageProp = ((Concept) customer).getProperty("age");
        ((PropertyAtomIntSimple) ageProp).setInt(age);

        InputTuple inputTuple =
                new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{customer});

        //Use: .. from Customer, Address where Customer.age = 45

        FixedKeyHashMap<String, Tuple> aliasAndTuples =
                new FixedKeyHashMap<String, Tuple>("personStream", "customerStream");
        aliasAndTuples.put("personStream", inputTuple);

        boolean ageComparison = simpleEvaluator.evaluateBoolean(null, null, aliasAndTuples);
        Assert.assertFalse(ageComparison, "Result is wrong.");

        //------------

        Serializable compiledMVEL = MVEL.compileExpression(
                "get(\"personStream\").getColumn(0).getProperty(\"age\").getValue() == 45");
        Object result = MVEL.executeExpression(compiledMVEL, (Object) aliasAndTuples);
        boolean resultBool = (Boolean) result;
        Assert.assertFalse(resultBool, "MVEL evaluation was wrong.");

        //------------

        long expressionTotal = 0;
        long mvelTotal = 0;
        final long innerLoopTimes = 1000;
        for (int h = 0; h < 100000; h++) {
            int c = 0;
            long startExpression = System.nanoTime();
            c = innerLoopAgeEval(simpleEvaluator, aliasAndTuples, innerLoopTimes, c);
            long endExpression = System.nanoTime();

            if (h > 100) {
                System.out.println(
                        "Expression time nanos: " + (endExpression - startExpression) + " over: " +
                                c);

                expressionTotal += (endExpression - startExpression);
            }

            //------------

            /*
            This MVEL test does not correctly replicate the scenario. The last step of wrapping
            the whole thing in an evaluator/extractor is missing. So, any perf results should
            consider that too.
            */

            c = 0;
            startExpression = System.nanoTime();
            c = innerLoopMVELAgeEval(aliasAndTuples, compiledMVEL, innerLoopTimes, c);
            endExpression = System.nanoTime();

            if (h > 100) {
                System.out.println(
                        "MVEL time nanos:       " + (endExpression - startExpression) + " over: " +
                                c);

                mvelTotal += (endExpression - startExpression);
            }

            System.out.println();
        }

        System.err.println("======= testJoinExpression =======");
        System.err.println("Expression total micro: " + expressionTotal / 1000);
        System.err.println("MVEL total micro:       " + mvelTotal / 1000);
    }

    private int innerLoopMVELAgeEval(FixedKeyHashMap<String, Tuple> aliasAndTuples,
                                     Serializable compiledMVEL, long innerLoopTimes, int c) {
        Object result;
        boolean resultBool;
        for (int i = 0; i < innerLoopTimes; i++) {
            result = MVEL.executeExpression(compiledMVEL, (Object) aliasAndTuples);
            resultBool = (Boolean) result;

            if (resultBool == false) {
                c++;
            }
        }
        return c;
    }

    private int innerLoopAgeEval(SimpleEvaluator simpleEvaluator,
                                 FixedKeyHashMap<String, Tuple> aliasAndTuples, long innerLoopTimes,
                                 int c) {
        boolean ageComparison;
        for (int i = 0; i < innerLoopTimes; i++) {
            ageComparison = simpleEvaluator.evaluateBoolean(null, null, aliasAndTuples);

            if (ageComparison == false) {
                c++;
            }
        }
        return c;
    }

    public static class InputTuple extends TrackedTuple {
        public InputTuple(Number id, Object[] columns) {
            super(id, columns);
        }
    }

    public static class JoinedInputTuple extends TrackedTuple {
        public JoinedInputTuple(Number id, Object[] columns) {
            super(id, columns);
        }
    }
}
