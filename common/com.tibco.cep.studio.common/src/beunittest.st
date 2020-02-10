package $packageName$;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.List;
import com.tibco.cep.runtime.model.element.Concept;

$if(testRuleOrder)$
import java.util.ArrayList;
import com.tibco.cep.runtime.service.tester.beunit.ExpectationType;
import com.tibco.cep.runtime.model.element.Concept;
$endif$
$if(testEventFired)$
import com.tibco.cep.runtime.model.event.SimpleEvent;
$endif$
$if(testRuleExecution)$
import com.tibco.cep.runtime.model.element.Concept;
$endif$
$if(testConceptModification)$
$if(!testRuleOrder)$
import com.tibco.cep.runtime.model.element.Concept;
$endif$
$endif$

import com.tibco.cep.runtime.service.tester.beunit.BETestEngine;
import com.tibco.cep.runtime.service.tester.beunit.Expecter;
import com.tibco.cep.runtime.service.tester.beunit.TestDataHelper;

/**
 * @description $description$
 */
public class $className$ {
	private static BETestEngine engine;
	private static TestDataHelper helper;
	private static Expecter expecter;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		engine = new BETestEngine("$earFile$", "$traFile$",
				"$cddFile$", "$procUnit$", "$agentName$", true);

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
	$if(testRuleOrder)$
	
	/**
	* Test whether one rule fired after another rule during rule execution, in order 
	* (use expectUnordered to test whether both rules fired in any order) 
	*/
	@Test
	public void testRuleOrder() throws Exception {
		engine.resetSession(); // (optional) reset the rule session, which will clear working memory, restart timers, and clear the data from any previous tests

		// TODO : Change test data path here to create concepts to be asserted from a test data file
		List<Concept> concepts = helper.createConceptsFromTestData("$conceptTestData$");
		engine.assertConcepts(concepts, false);
		
		engine.executeRules();
		List<String> rules = new ArrayList<String>();
		rules.add("/Rules/<first rule name>"); // TODO : Change the name to the first expected rule
		rules.add("/Rules/<second rule name>"); // TODO : Change the name to a rule expected to fire after the previous rule
		expecter.expectOrdered(rules, ExpectationType.RULE_EXECTION);
	}
	$endif$
	$if(testEventFired)$
	
	/**
	* Test whether a particular Event was asserted by the engine during rule execution
	*/
	@Test
	public void testEventAsserted() throws Exception {
		engine.resetSession(); // (optional) reset the rule session, which will clear working memory, restart timers, and clear the data from any previous tests

		// TODO : Change test data path here to create events to be asserted from a test data file
		List<SimpleEvent> events = helper.createEventsFromTestData("$eventTestData$");
		if (events.size() > 0) {
			engine.assertEvent(events.get(0), false);
		}
		engine.executeRules(); 
		expecter.expectEventAsserted("/Events/<event name>");
	}
	$endif$
	$if(testConceptModification)$
	
	/**
	* Test whether a particular Concept was modified by the engine during rule execution
	*/
	@Test
	public void testConceptModified() throws Exception {
		engine.resetSession(); // (optional) reset the rule session, which will clear working memory, restart timers, and clear the data from any previous tests

		// TODO : Change test data path here to create concepts to be asserted from a test data file
		List<Concept> concepts = helper.createConceptsFromTestData("$conceptTestData$");
		engine.assertConcepts(concepts, false);
		
		engine.executeRules();
		// TODO : Change the concept name to test whether the concept was modified during rule processing
		expecter.expectModified("/Concepts/<concept name>");
	}
	$endif$
	$if(testWorkingMemory)$
	
	/**
	* Test whether a particular Concept or Event is still in working memory
	*/
	@Test
	public void testWorkingMemory() throws Exception {
		engine.resetSession(); // (optional) reset the rule session, which will clear working memory, restart timers, and clear the data from any previous tests

		// TODO : Change test data path here to create concepts to be asserted from a test data file
		List<Concept> concepts = helper.createConceptsFromTestData("$conceptTestData$");
		engine.assertConcepts(concepts, false);
		
		engine.executeRules();
		// TODO : Change the concept name to test whether the concept was modified during rule processing
		expecter.expectInWorkingMemory("/Concepts/<concept name>");
	}
	$endif$
	$if(testRuleExecution)$
	
	/**
	* Test whether a particular Rule has fired
	*/
	@Test
	public void testRuleFired() throws Exception {
		engine.resetSession(); // (optional) reset the rule session, which will clear working memory, restart timers, and clear the data from any previous tests

		// TODO : Change test data path here to create concepts to be asserted from a test data file
		List<Concept> concepts = helper.createConceptsFromTestData("$conceptTestData$");
		engine.assertConcepts(concepts, false);
		
		engine.executeRules();
		expecter.expectRuleFired("/Rules/<rule name>"); // TODO : Change the name to a rule expected to fire
	}
	$endif$	
}