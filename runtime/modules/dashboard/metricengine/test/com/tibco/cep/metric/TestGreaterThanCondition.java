package com.tibco.cep.metric;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.tibco.cep.metric.evaluator.ASTWalker;

@Test(groups = { "ConditionTests", "All" }, sequential = true)
public class TestGreaterThanCondition {

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
		evaluator = new ASTWalker("$testId > 0");
		Map<String, Object> bindValues = new HashMap<String, Object>();
		bindValues.put("testDate", Calendar.getInstance());
		evaluator = new ASTWalker("$testDate > '2010-04-28T00:00:00.000-0700'");
		Assert.assertEquals(evaluator.evaluate(concept, bindValues), true);
		bindValues.put("testId", 1.0);
		Assert.assertEquals(evaluator.evaluate(concept, bindValues), true);
		evaluator = new ASTWalker("testLongField > 0");
		Assert.assertEquals(evaluator.evaluate(concept), true);
		evaluator = new ASTWalker("'12:00:00.001-0000' > '12:00:00.000+0000'");
		Assert.assertEquals(evaluator.evaluate(concept), true);
		evaluator = new ASTWalker("'2010-04-29' > '2010-04-29T00:00:00.000-0600'");
		Assert.assertEquals(evaluator.evaluate(concept), true);
		evaluator = new ASTWalker("'2010-04-29' > '2010-04-29T00:00:00.000-0700'");
		Assert.assertEquals(evaluator.evaluate(concept), false);
		evaluator = new ASTWalker("'2010-04-29' > '2010-04-29T00:00:00.000-0800'");
		Assert.assertEquals(evaluator.evaluate(concept), false);
		evaluator = new ASTWalker("@id > -1");
		Assert.assertEquals(evaluator.evaluate(concept), true);
	}

	@AfterClass
	public void tearDown() throws Exception {
		// todo
	}
}
