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
public class TestEqualsCondition {

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
		evaluator = new ASTWalker("$testId = 1");
		Map<String, Object> bindValues = new HashMap<String, Object>();
		bindValues.put("testId", 1.0);
		Assert.assertEquals(evaluator.evaluate(concept, bindValues), true);
		evaluator = new ASTWalker("1 = $testId");
		Assert.assertEquals(evaluator.evaluate(concept, bindValues), true);
		evaluator = new ASTWalker("$testName = 'a'");
		bindValues = new HashMap<String, Object>();
		bindValues.put("testName", 'a');
		Assert.assertEquals(evaluator.evaluate(concept, bindValues), true);
		evaluator = new ASTWalker("testIntField = 1");
		Assert.assertEquals(evaluator.evaluate(concept), true);
		evaluator = new ASTWalker("testIntField = 1.0");
		Assert.assertEquals(evaluator.evaluate(concept), true);
		evaluator = new ASTWalker("testStringField = \"tester1\"");
		Assert.assertEquals(evaluator.evaluate(concept), true);
		evaluator = new ASTWalker("testStringField = 'tester1'");
		Assert.assertEquals(evaluator.evaluate(concept), true);
		evaluator = new ASTWalker("testStringField = 'tester1'");
		Assert.assertEquals(evaluator.evaluate(concept), true);
		evaluator = new ASTWalker("testBooleanField = true");
		Assert.assertEquals(evaluator.evaluate(concept), true);
		evaluator = new ASTWalker("1.000001 = 1.000001");
		Assert.assertEquals(evaluator.evaluate(concept), true);
		evaluator = new ASTWalker("'2009-12-31' = '2009-12-31T00:00:00.000-0800'");
		Assert.assertEquals(evaluator.evaluate(concept), true);
		evaluator = new ASTWalker("@id = 0");
		Assert.assertEquals(evaluator.evaluate(concept), true);
	}

	@AfterClass
	public void tearDown() throws Exception {
		// todo
	}
}
