package com.tibco.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tibco.cep.kernel.model.knowledgebase.DuplicateExtIdException;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.service.tester.beunit.BETestEngine;
import com.tibco.cep.runtime.service.tester.beunit.ExpectationType;
import com.tibco.cep.runtime.service.tester.beunit.Expecter;
import com.tibco.cep.runtime.service.tester.beunit.TestDataHelper;

/**
 * @description 
 */
public class BEUnitTestSuite {
	private static BETestEngine engine;
	private static TestDataHelper helper;
	private static Expecter expecter;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		engine = new BETestEngine("../BEUnitTest.ear", "../../../../bin/be-engine.tra",
				"Deployments/test.cdd", "default", "inference-class", true);

		// Start the test engine
		engine.start();
		
		// Create a helper to work with test data
		helper = new TestDataHelper(engine);
		
		// Create an Expecter object to test rule execution, modifications, assertions, etc.
		expecter = new Expecter(engine);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		try {
			engine.shutdown();
		} catch (Exception localException) {
		}
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	/**
	* Test whether one rule fired after another rule during rule execution, in order 
	* (use expectUnordered to test whether both rules fired in any order) 
	*/
	@Test
	public void testRuleOrder() throws Exception {
		engine.resetSession(); // (optional) reset the rule session, which will clear working memory, restart timers, and clear the data from any previous tests

		assertTestData();
		
		engine.executeRules();
		List<String> rules = new ArrayList<String>();
		rules.add("/Rules/PromotionalRule");
		rules.add("/Rules/PriceAdjustmentRule");
		expecter.expectOrdered(rules, ExpectationType.RULE_EXECTION);
	}

	/**
	* Test whether a particular Event was asserted by the engine during rule execution
	*/
	@Test
	public void testEventAsserted() throws Exception {
		engine.resetSession(); // (optional) reset the rule session, which will clear working memory, restart timers, and clear the data from any previous tests

		assertTestData();
		
		engine.executeRules();
		expecter.expectEventAsserted("/Events/PriceAdjustmentEvent");
	}
	
	/**
	* Test whether a particular Event was sent by the engine during rule execution
	*/
	@Test
	public void testEventSent() throws Exception {
		engine.resetSession(); // (optional) reset the rule session, which will clear working memory, restart timers, and clear the data from any previous tests

		assertTestData();
		
		engine.executeRules();

		//expecter.expectEventSent("/Events/PriceAdjustmentEvent");
	}
	
	/**
	* Test whether a particular Concept was modified by the engine during rule execution
	*/
	@Test
	public void testConceptModified() throws Exception {
		engine.resetSession(); // (optional) reset the rule session, which will clear working memory, restart timers, and clear the data from any previous tests

		assertTestData();
		
		engine.executeRules();

		expecter.expectModifiedByExtId("1234", "Price", 500.0);
	}
	
	/**
	* Test whether a particular Rule has fired
	*/
	@Test
	public void testRuleFired() throws Exception {
		engine.resetSession(); // (optional) reset the rule session, which will clear working memory, restart timers, and clear the data from any previous tests

		assertTestData();
		
		engine.executeRules();

		expecter.expectRuleFired("/Rules/PriceAdjustmentRule");
	}	

	private void assertTestData() throws Exception, DuplicateExtIdException {
		List<Concept> concepts = helper.createConceptsFromTestData("/TestData/InventoryItem");
		engine.assertConcepts(concepts, false);
		String jsonString = "{\"PromotionalEvent\":{\"PriceAdjustment\":-250.0,\"SKU\":1234}}";
		SimpleEvent promotionEvent = (SimpleEvent) helper.createEventFromJSON("/Events/PromotionalEvent", jsonString);
		engine.assertEvent(promotionEvent, true);
	}
	
}