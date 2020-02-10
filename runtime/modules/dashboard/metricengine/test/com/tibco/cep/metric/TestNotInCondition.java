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
public class TestNotInCondition {

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
		evaluator = new ASTWalker("$testIntField not in (0, 2, 3, 5, 7, 9)");
		Map<String, Object> bindValues = new HashMap<String, Object>();
		bindValues.put("testIntField", 1.0);
		Assert.assertEquals(evaluator.evaluate(concept, bindValues), true);
		evaluator = new ASTWalker("testIntField not in (0,2)");
		Assert.assertEquals(evaluator.evaluate(concept), true);
		evaluator = new ASTWalker("testIntField not in (0,2) and testIntField>0");
		Assert.assertEquals(evaluator.evaluate(concept), true);
		evaluator = new ASTWalker("testStringField not in (null, \"2\", \"dummy\", \"tester2\")");
		Assert.assertEquals(evaluator.evaluate(concept), true);
		evaluator = new ASTWalker("@id not in (-1,1,2)");
		Assert.assertEquals(evaluator.evaluate(concept), true);
	}

	@AfterClass
	public void tearDown() throws Exception {
		// todo
	}
}
