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
public class TestNotCondition {

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
		evaluator = new ASTWalker("not $testId > 1 and not $testId < 1");
		Map<String, Object> bindValues = new HashMap<String, Object>();
		bindValues.put("testId", 1);
		Assert.assertEquals(evaluator.evaluate(concept, bindValues), false);
		evaluator = new ASTWalker("not testIntField > 1 and not testIntField < 1");
		Assert.assertEquals(evaluator.evaluate(concept), false);
		evaluator = new ASTWalker("not @id > 1 and not @id < -1");
		Assert.assertEquals(evaluator.evaluate(concept), false);
	}

	@AfterClass
	public void tearDown() throws Exception {
		// todo
	}
}
