package com.tibco.cep.metric;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.tibco.cep.metric.evaluator.ASTWalker;

@Test(groups = { "ConditionTests", "All" }, sequential = true)
public class TestInCondition {

	private ASTWalker evaluator;
	private TestMetric concept;

	@BeforeClass
	public void setup() throws Exception {
		concept = new TestMetric();
		concept.setPropertyValue("testBooleanField", true);
		concept.setPropertyValue("testDateTimeField", Calendar.getInstance());
		concept.setPropertyValue("testDoubleField", 1.0);
		concept.setPropertyValue("testLongField", 1);
		concept.setPropertyValue("testIntField", 1);
		concept.setPropertyValue("testStringField", "tester1");
	}

	@Test
	public void test() throws Exception {
		evaluator = new ASTWalker("$testId in (1, 2, 3, 5, 7, 9)");
		Map<String, Object> bindValues = new HashMap<String, Object>();
		bindValues.put("testId", 1.0);
		Assert.assertEquals(evaluator.evaluate(concept, bindValues), true);
		evaluator = new ASTWalker("@id not in (1,2)");
		Assert.assertEquals(evaluator.evaluate(concept), true);
		evaluator = new ASTWalker("testLongField in (1,2) and testLongField>0");
		Assert.assertEquals(evaluator.evaluate(concept), true);
		evaluator = new ASTWalker("testStringField in (null, \"1\", \"dummy\", \"tester1\")");
		Assert.assertEquals(evaluator.evaluate(concept), true);
	}

	@AfterClass
	public void tearDown() throws Exception {
		// todo
	}
}
