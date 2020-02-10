package com.tibco.cep.runtime.service.tester.beunit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.model.knowledgebase.Handle;
import com.tibco.cep.kernel.model.rule.Rule;
import com.tibco.cep.kernel.service.ObjectManager;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.TimeEvent;
import com.tibco.cep.runtime.model.event.impl.SimpleEventImpl;
import com.tibco.cep.runtime.service.cluster.scheduler.SchedulerCache;
import com.tibco.cep.runtime.service.tester.core.ChannelTask;
import com.tibco.cep.runtime.service.tester.core.PreprocessorCause;
import com.tibco.cep.runtime.service.tester.core.SchedulerTask;
import com.tibco.cep.runtime.service.tester.core.TesterRun;
import com.tibco.cep.runtime.service.tester.core.TesterRun.RunSummary;
import com.tibco.cep.runtime.service.tester.model.LifecycleEventType;
import com.tibco.cep.runtime.service.tester.model.LifecycleObject;
import com.tibco.cep.runtime.service.tester.model.ModifiedReteObject;
import com.tibco.cep.runtime.service.tester.model.ReteChangeType;
import com.tibco.cep.runtime.service.tester.model.ReteObject;
import com.tibco.cep.runtime.service.tester.model.TesterObject;
import com.tibco.xml.data.primitive.ExpandedName;


/**
 * Base Class for BEUnit Test Suite Expecters.
 *
 * @.category public-api
 * @since 5.4.0
 */


public class Expecter {

	public final static String BE_NAMESPACE= "www.tibco.com/be/ontology";
	private final SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
	private BETestEngine engine;
	private boolean includePreprocessorContext = false;

	/**
	 * Create a new Expecter object to be used by the unit test engine 
	 * @param engine
	 * @.category public-api
	 */
	public Expecter(BETestEngine engine) {
		this.engine = engine;
	}

	/**
	 * 
	 * @param engine
	 * @param includePreprocessorContext
	 */
	public Expecter(BETestEngine engine, boolean includePreprocessorContext) {
		this.engine = engine;
		this.includePreprocessorContext = includePreprocessorContext;
	}
	
	/**
	 * Set whether to include objects created and asserted within a preprocessor when
	 * expecting certain operations.  For instance, setting this to true will include
	 * any Concepts created and asserted from within a preprocessor Rule Function 
	 * when expecting whether the Concept was asserted
	 * @param includeTesterObjects
	 */
	public void setIncludePreprocessorContext(boolean includePreprocessorContext) {
		this.includePreprocessorContext = includePreprocessorContext;
	}
	
	/**
	 * Expect that an Event specified by <code>eventUri</code> has been created/asserted
	 * @.category public-api
	 * @param eventUri - the Event uri, for example "/Events/FraudDetected"
	 */
	public void expectEventAsserted(String eventUri) {
		expect(eventUri, ExpectationType.EVENT_ASSERTED);
	}
	
	/**
	 * Expect that an Event specified by <code>eventUri</code> has been sent
	 * @.category public-api
	 * @param eventUri - the Event uri, for example "/Events/FraudDetected"
	 */
	public void expectEventSent(String eventUri) {
		TesterObject obj = findReteObject(eventUri, ExpectationType.EVENT_SENT, null);
		if (obj == null) {
			throw new AssertionError("Expected Event '"+eventUri+"' to be sent, but no such event was found");
		}
	}

	/**
	 * Expect that the specified Entity currently exists in working memory.<br>
	 * @param entity - an instance of an Entity, typically created from the {@link TestDataHelper}
	 * @.category public-api
	 * @since 5.4
	 */
	public void expectInWorkingMemory(Entity entity) {
		TesterRun currentRun = engine.getCurrentRun();
		Handle handle = currentRun.getOwnerSession().getRuleSession().getObjectManager().getHandle(entity);
		if (handle == null) {
			throw new AssertionError("Expected '"+entity+"' to be in working memory, but the object was not found");
		}
	}

	/**
	 * Expect that at least one entity specified by <code>uri</code> currently exists in working memory.<br>
	 * For example:<br>
	 * <code>expectInWorkingMemory("/Concepts/Person")</code><br>
	 * tests whether there is an instance of '/Concepts/Person' currently in working memory<br><br>
	 * @param uri - the entity uri, for instance "/Concepts/Person"
	 * @.category public-api
	 * @since 5.4
	 */
	public void expectInWorkingMemory(String uri) {
		List<Handle> handles = getHandles(uri, false);
		if (handles.size() == 0) {
			throw new AssertionError("Expected '"+uri+"' to be in working memory, but the object was not found");
		}
	}
	
	/**
	 * Expect that a particular entity specified by <code>uri</code> has been modified
	 * For example, expectModified("/Concepts/Person") will test whether a
	 * Person concept has been modified<br><br>
	 * Note that this will test whether <i>any</i> /Concepts/Person instance has been modified
	 * To test a specific entity instance, use the <code>expectModified(String uri, String propertyName, Object newValue, String extId)</code>
	 * method instead
	 * @param uri - the entity uri (i.e. "/Concepts/Person")
	 * @.category public-api
	 * @since 5.4
	 */
	public void expectModified(String uri) {
		expectModified(uri, null);
	}
	
	/**
	 * Expect that a particular property with the name <code>propertyName</code> 
	 * in the entity specified by <code>uri</code> has been modified
	 * For example, expectModified("/Concepts/Person", "department") will test whether a
	 * Person concept has a property named 'department' that has been modified<br><br>
	 * Note that this will test whether <i>any</i> /Concepts/Person instance has been modified
	 * To test a specific entity instance, use the <code>expectModified(String uri, String propertyName, Object newValue, String extId)</code>
	 * method instead
	 * @param uri - the entity uri (i.e. "/Concepts/Person")
	 * @param propertyName - the name of the property to check
	 * @.category public-api
	 * @since 5.4
	 */
	public void expectModified(String uri, String propertyName) {
		expectModified(uri, propertyName, null);
	}

	/**
	 * Expect that a particular property with the name <code>propertyName</code> 
	 * in the entity specified by <code>uri</code> has been modified, and the new value
	 * of the property is equal to <code>newValue</code><br><br>
	 * For example, expectModified("/Concepts/Person", "department", 1234) will test whether a
	 * Person concept has a property named 'department' that has been modified to the value '1234'<br><br>
	 * Note that this will test whether <i>any</i> /Concepts/Person has been modified to the expected value.
	 * To test a specific entity instance, use the <code>expectModified(String uri, String propertyName, Object newValue, String extId)</code>
	 * method instead
	 * @param uri - the entity uri (i.e. "/Concepts/Person")
	 * @param propertyName - the name of the property to check
	 * @param newValue - the expected new value of the property. If null, then newValue will be ignored and no value check will occur
	 * @.category public-api
	 * @since 5.4
	 */
	public void expectModified(String uri, String propertyName, Object newValue) {
		TesterRun currentRun = engine.getCurrentRun();
		RunSummary summary = currentRun.getCurrentRunSummary();
		if (summary.getNumModifiedObjects() == 0) {
			throw new AssertionError("Expected '"+uri+"' to be modified, but no modifications were found");
		}

		TesterObject reteObject = findReteObject(uri, ExpectationType.CONCEPT_MODIFICATION, null);
		if (reteObject == null) {
			throw new AssertionError("Expected entity '"+uri+"' was not modified");
		}
		if (propertyName != null) {
			List<Property> properties = ((ModifiedReteObject)reteObject).getModifiedProperties();
			for (Property property : properties) {
				if (property.getName().equals(propertyName)) {
					if (newValue == null) {
						// BE-25007 : no need to check against newValue
						return;
					}
					try {
						Object val = property.getParent().getPropertyValue(propertyName); 
						if (val.equals(newValue)) {
							return;
						} else {
							throw new AssertionError("The expected property '"+propertyName+"' was modified to value '"+val+"', but it did not match the expected new value of '"+newValue+"'");
						}
					} catch (NoSuchFieldException e) {
						throw new RuntimeException(e);
					}
				}
			}
			throw new AssertionError("The expected entity '"+uri+"' was modified, but the property '"+propertyName+"' was not");
		}
	}
	
	/**
	 * Expect that at least one entity with the specified <code>extId</code> currently exists in working memory.<br>
	 * For example:<br>
	 * <code>expectInWorkingMemoryByExtId("MY_ID")</code><br>
	 * tests whether there is an entity with the extId 'MY_ID' currently in working memory<br><br>
	 * @param extId - the extId of the entity
	 * @.category public-api
	 * @since 5.4
	 */
	public void expectInWorkingMemoryByExtId(String extId) {
		TesterRun currentRun = engine.getCurrentRun();
		ObjectManager objectManager = currentRun.getOwnerSession().getRuleSession().getObjectManager();
		if (objectManager.getElement(extId) != null) {
			return;
		}
		if (objectManager.getEvent(extId) != null) {
			return;
		}
		throw new AssertionError("Expected an object with extId '"+extId+"' to be in working memory, but the object was not found");
	}
	
	/**
	 * Expect that a particular entity specified by <code>extId</code> has been modified
	 * For example, expectModifiedByExtId("MY_ID") will test whether a
	 * Concept or Event with the extId equal to "MY_ID" has been modified<br><br>
	 * @param extId - the extId of the entity
	 * @.category public-api
	 * @since 5.4
	 */
	public void expectModifiedByExtId(String extId) {
		expectModifiedByExtId(extId, null);
	}
	
	/**
	 * Expect that a particular property with the name <code>propertyName</code> 
	 * in the entity specified by <code>extId</code> has been modified
	 * For example, expectModifiedByExtId("MY_ID", "department") will test whether the
	 * Entity matching the extId has a property named 'department' that has been modified<br><br>
	 * @param extId - the extId of the entity
	 * @param propertyName - the name of the property to check
	 * @.category public-api
	 * @since 5.4
	 */
	public void expectModifiedByExtId(String extId, String propertyName) {
		expectModifiedByExtId(extId, propertyName, null);
	}

	/**
	 * Expect that a particular property with the name <code>propertyName</code> 
	 * in the entity with the matching <code>extId</code> has been modified, and the new value
	 * of the property is equal to <code>newValue</code><br><br>
	 * For example, expectModifiedByExtId("MY_ID", "department", 1234) will test whether an
	 * Entity matching extId 'MY_ID' has a property named 'department' that has been modified to the value '1234'<br><br>
	 * @param extId - the extId of the entity
	 * @param propertyName - the name of the property to check
	 * @param newValue - the expected new value of the property. If null, then newValue will be ignored and no value check will occur
	 * @.category public-api
	 * @since 5.4
	 */
	public void expectModifiedByExtId(String extId, String propertyName, Object newValue) {
		TesterRun currentRun = engine.getCurrentRun();
		RunSummary summary = currentRun.getCurrentRunSummary();
		if (summary.getNumModifiedObjects() == 0) {
			throw new AssertionError("Expected '"+extId+"' to be modified, but no modifications were found");
		}

		TesterObject reteObject = findReteObject(null, ExpectationType.CONCEPT_MODIFICATION, extId);
		if (reteObject == null) {
			throw new AssertionError("Expected entity '"+extId+"' was not modified");
		}
		if (propertyName != null) {
			List<Property> properties = ((ModifiedReteObject)reteObject).getModifiedProperties();
			for (Property property : properties) {
				if (property.getName().equals(propertyName)) {
					if (newValue == null) {
						// BE-25007 : no need to check against newValue
						return;
					}
					try {
						Object val = property.getParent().getPropertyValue(propertyName); 
						if (val.equals(newValue)) {
							return;
						} else {
							throw new AssertionError("The expected property '"+propertyName+"' was modified to value '"+val+"', but it did not match the expected new value of '"+newValue+"'");
						}
					} catch (NoSuchFieldException e) {
						throw new RuntimeException(e);
					}
				}
			}
			throw new AssertionError("The expected entity '"+extId+"' was modified, but the property '"+propertyName+"' was not");
		}
	}
	
	/**
	 * Expect that <i>nothing</i> has happened in the current Rule Session.  This assumes the following:<br>
	 * - No objects were created<br>
	 * - No objects were modified<br>
	 * - No objects were retracted<br>
	 * - No rules were fired
	 * @.category public-api
	 * @since 5.4
	 */
	public void expectNothing() {
		TesterRun currentRun = engine.getCurrentRun();
		RunSummary summary = currentRun.getCurrentRunSummary();
		if (summary.getNumCreatedObjects() > 0) {
			throw new AssertionError("Expected nothing, but "+summary.getNumCreatedObjects()+" objects were created");
		}
		if (summary.getNumModifiedObjects() > 0) {
			throw new AssertionError("Expected nothing, but "+summary.getNumModifiedObjects()+" objects were modified");
		}
		if (summary.getNumRetractedObjects() > 0) {
			throw new AssertionError("Expected nothing, but "+summary.getNumRetractedObjects()+" objects were retracted");
		}
		if (summary.getNumRulesFired() > 0) {
			throw new AssertionError("Expected nothing, but "+summary.getNumRulesFired()+" rules were executed");
		}
	}
	
	/**
	 * Expect that the specified Entity does <i>not</i> currently exist in working memory.<br>
	 * @param entity - an instance of an Entity (i.e a Concept or an Event), typically created from the {@link TestDataHelper}
	 * @.category public-api
	 * @since 5.4
	 */
	public void expectNotInWorkingMemory(Entity entity) {
		TesterRun currentRun = engine.getCurrentRun();
		Handle handle = currentRun.getOwnerSession().getRuleSession().getObjectManager().getHandle(entity);
		if (handle != null) {
			throw new AssertionError("Did not expect '"+entity+"' to be in working memory, but the object was found");
		}
	}

	/**
	 * Expect that no entity specified by <code>uri</code> currently exists in working memory.<br>
	 * For example:<br>
	 * <code>expectNotInWorkingMemory("/Concepts/Person")</code><br>
	 * tests whether there is an instance of '/Concepts/Person' currently in working memory, and will fail if one is found<br><br>
	 * @param uri - the entity uri, for instance "/Concepts/Person"
	 * @.category public-api
	 * @since 5.4
	 */
	public void expectNotInWorkingMemory(String uri) {
		TesterRun currentRun = engine.getCurrentRun();
		List handles = currentRun.getOwnerSession().getRuleSession().getObjectManager().getHandles();
		for (Object object : handles) {
			Handle handle = (Handle) object;
			Object handleObj = handle.getObject();
			if (handleObj instanceof Concept) {
				if (((Concept) handleObj).getExpandedName().equals(getExpandedName(uri))) {
					throw new AssertionError("Did not expect '"+handleObj+"' to be in working memory, but the object was found");
				}
			}
		}
	}
	
	/**
	 * Expect that no entity with the matching <code>extId</code> currently exists in working memory.<br>
	 * For example:<br>
	 * <code>expectNotInWorkingMemory("MY_ID")</code><br>
	 * tests whether there is an Event or Concept currently in working memory with the extId of 'MY_ID', and will fail if one is found<br><br>
	 * @param extId - the extId of the entity
	 * @.category public-api
	 * @since 5.4
	 */
	public void expectNotInWorkingMemoryByExtId(String extId) {
		TesterRun currentRun = engine.getCurrentRun();
		List handles = currentRun.getOwnerSession().getRuleSession().getObjectManager().getHandles();
		for (Object object : handles) {
			Handle handle = (Handle) object;
			Object handleObj = handle.getObject();
			if (handleObj instanceof Concept) {
				if (((Concept) handleObj).getExtId().equals(extId)) {
					throw new AssertionError("Did not expect '"+handleObj+"' to be in working memory, but the object was found");
				}
			}
		}
	}
	
	/**
	 * Expect that the {@link ExpectationType} operation occurred only once for any entity specified by <code>uri</code><br>
	 * For example:<br>
	 * <code>expectOnce("/Concepts/Person", ExpectationType.CONCEPT_MODIFICATION)</code><br>
	 * tests whether exactly one instance of '/Concepts/Person' has been modified<br><br>
	 * Example 2:<br> 
	 * <code>expectOnce("/Events/Fraud", ExpectationType.EVENT_CREATED)</code><br>
	 * tests whether exactly one '/Events/Fraud' has been created<br><br>
	 * @param uri - the entity uri, for instance "/Concepts/Person"
	 * @param type - the {@link ExpectationType} operation
	 * @.category public-api
	 * @since 5.4
	 */
	public void expectOnce(String uri, ExpectationType type) {
		List<ReteObject> reteObjects = findReteObjects(uri, type);
		if (reteObjects.size() == 0) {
			throw new AssertionError("Expected only one "+type+" for '"+uri+"', but no such object was found");
		}
		if (reteObjects.size() > 1) {
			throw new AssertionError("Expected only one "+type+" for '"+uri+"', but more than one object was found");
		}
	}
	
	/**
	 * Expect that the {@link ExpectationType} operation occurred at least once for any entity specified by <code>uri</code><br>
	 * For example:<br>
	 * <code>expect("/Concepts/Person", ExpectationType.CONCEPT_MODIFICATION)</code><br>
	 * tests whether at least one instance of '/Concepts/Person' has been modified<br><br>
	 * Example 2:<br> 
	 * <code>expect("/Events/Fraud", ExpectationType.EVENT_CREATED)</code><br>
	 * tests whether at least one '/Events/Fraud' has been created<br><br>
	 * @param uri - the entity uri, for instance "/Concepts/Person"
	 * @param type - the {@link ExpectationType} operation
	 * @.category public-api
	 * @since 5.4
	 */
	public void expect(String uri, ExpectationType type) {
		List<ReteObject> reteObjects = findReteObjects(uri, type);
		//expectInWorkingMemory(uri);
		if (reteObjects.size() == 0) {
			throw new AssertionError("Expected at least one "+type+" for '"+uri+"', but no such object was found");
		}
	}
	
	/**
	 * Expect that all rules specified in <code>ruleURIs</code> have fired, in a specific order.
	 * For example:<br><br>
	 * <code>
	 *  engine.executeRules(); <br>
	 *  List<String> rules = new ArrayList<String>();<br>
	 *  rules.add("/Rules/ProcessApplication");<br>
	 *  rules.add("/Rules/FraudDetected");<br>
	 *  expecter.expectOrdered(rules, ExpectationType.RULE_EXECUTION);<br>
	 * </code><br>
	 * will test whether the rule 'ProcessApplication' fired <i>before</i> the 'FraudDetected' rule, and will fail if
	 * any of the rules did not fire, or if the order of the executed rules differs from the specified order
	 * @param ruleURIs - a List of Strings corresponding to the expected order of rule execution
	 * @.category public-api
	 * @since 5.4
	 */
	public void expectOrdered(List<String> ruleURIs, ExpectationType type) {
		Date prevTime = new Date(0);
		for (String uri : ruleURIs) {
			TesterObject reteObject = findReteObject(uri, type, null);
			if (reteObject == null) {
				throw new AssertionError("Expected rule '"+uri+"' not found");
			}
			String timestamp = reteObject.getTimestamp();
			try {
				Date reteTS = timeFormat.parse(timestamp);
				if (prevTime.after(reteTS)) {
					throw new AssertionError("The order of the rules executed does not match the expected order");
				}
				prevTime = reteTS;
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	/**
	 * Expect that the rule specified by <code>ruleURI</code> has fired.<br>
	 * For example:
	 * <code>expectRuleFired("/Rules/FraudDetected")</code> will check whether the rule 'FraudDetected' has fired, and will
	 * fail if not
	 * @param ruleURI - the uri of the rule, for instance "/Rules/FraudDetected"
	 * @.category public-api
	 * @since 5.4
	 */
	public void expectRuleFired(String ruleURI) {
		TesterObject reteObject = findReteObject(ruleURI, ExpectationType.RULE_EXECUTION, null);
		if (reteObject == null) {
			throw new AssertionError("Rule "+ruleURI+" did not fire");
		}
	}

	/**
	 * Expect that the Time Event specified by <code>timeEventUri</code> has been scheduled.
	 * Convenience method.  This is fully equivalent to <code>expectScheduled(timeEventUri, false)</code>
	 * @param timeEventUri - the Time Event uri, for instance "/Events/ScheduledPaymentEvent"
	 * @.category public-api
	 * @since 5.4
	 */
	public void expectScheduled(String timeEventUri) {
		expectScheduled(timeEventUri, false);
	}
	
	/**
	 * Expect that the Time Event specified by <code>timeEventUri</code> has been scheduled.
	 * Convenience method.  This is fully equivalent to <code>expectScheduled(timeEventUri, false)</code>
	 * @param extId - the extId of the Time Event
	 * @.category public-api
	 * @since 5.4
	 */
	public void expectScheduledByExtId(String extId) {
		expectScheduledByExtId(extId, false);
	}
	
	/**
	 * Expect that the Time Event specified by <code>timeEventUri</code> has been scheduled.  If <code>failIfAlreadyFired</code>
	 * is set to true, an AssertionError will be thrown if the Time Event was scheduled but has already fired
	 * @param timeEventUri - the Time Event uri, for instance "/Events/ScheduledPaymentEvent"
	 * @param failIfAlreadyFired - if true, an AssertionError will be thrown if the Time Event was scheduled but has already fired
	 * @.category public-api
	 * @since 5.4
	 */
	public void expectScheduled(String timeEventUri, boolean failIfAlreadyFired) {
		expectScheduled(timeEventUri, failIfAlreadyFired, null);
	}

	/**
	 * Expect that the Time Event specified by <code>timeEventUri</code> has been scheduled.  If <code>failIfAlreadyFired</code>
	 * is set to true, an AssertionError will be thrown if the Time Event was scheduled but has already fired
	 * @param extId - the extId of the Time Event
	 * @param failIfAlreadyFired - if true, an AssertionError will be thrown if the Time Event was scheduled but has already fired
	 * @.category public-api
	 * @since 5.4
	 */
	public void expectScheduledByExtId(String extId, boolean failIfAlreadyFired) {
		expectScheduled(null, failIfAlreadyFired, extId);
	}
	
	private void expectScheduled(String timeEventUri, boolean failIfAlreadyFired, String extId) {
		TesterObject reteObject = findReteObject(timeEventUri, ExpectationType.TIME_EVENT_SCHEDULED, extId);
		if (reteObject == null) {
			reteObject = findReteObject(timeEventUri, ExpectationType.WORK_SCHEDULED, extId);
		}

		if (reteObject == null) {
			throw new AssertionError("Event "+timeEventUri+" was not scheduled");
		}
		
		if (!failIfAlreadyFired) {
			return;
		}

		Object wrappedObject = reteObject.getWrappedObject();
		if (wrappedObject instanceof SchedulerTask) {
			wrappedObject = ((SchedulerTask) wrappedObject).getTarget();
			reteObject = findReteObject(timeEventUri, ExpectationType.EVENT_SENT, extId);
			if (reteObject != null) {
				throw new AssertionError("Expected "+timeEventUri+" to be scheduled, but the event was already sent");
			}
		}
		if (wrappedObject instanceof TimeEvent) {
			if (!((TimeEvent) wrappedObject).isFired()) {
				System.err.println("didnt fire yet");
//				return;
			}
		}
		if (wrappedObject instanceof SimpleEvent) {
			SimpleEventImpl ev = (SimpleEventImpl) wrappedObject;
//			if (!((SimpleEvent) wrappedObject).isFired()) {
				System.err.println("didnt fire yet");
//				return;
//			}
		}
		
		reteObject = findReteObject(timeEventUri, ExpectationType.EVENT_RETRACTED, extId);
		if (reteObject != null) {
			throw new AssertionError("Event "+timeEventUri+" was retracted");
		}
		reteObject = findReteObject(timeEventUri, ExpectationType.EVENT_SENT, extId);
		if (reteObject != null) {
			throw new AssertionError("Event "+timeEventUri+" was already sent");
		}
		
		// attempt to lookup event in WM
		TesterRun currentRun = engine.getCurrentRun();
		List handles = currentRun.getOwnerSession().getRuleSession().getObjectManager().getHandles();
		for (Object object : handles) {
			Handle handle = (Handle) object;
			Object handleObj = handle.getObject();
			if (handleObj instanceof TimeEvent) {
				TimeEvent ev = (TimeEvent) handleObj;
				String type = ev.getType();
				if (getExpandedName(timeEventUri).namespaceURI.equals(type)) {
					boolean fired = ev.isFired();
					if (fired) {
						throw new AssertionError("Expected '"+timeEventUri+"' to be scheduled, but it already fired");
					}
					return;
				}
			}
		}
		SchedulerCache schedulerCache = currentRun.getOwnerSession().getRSP().getCluster().getSchedulerCache();
		if (schedulerCache != null) {
			schedulerCache.schedulerExists("");
		}
//		throw new AssertionError("Expected '"+timeEventUri+"' to be scheduled, but could not find it in working memory");
	}
	
	/**
	 * Expect that <i>something</i> has happened in the current Rule Session.  This assumes the following:<br>
	 * - At least one object was created, OR<br>
	 * - At least one object was modified, OR<br>
	 * - At least one object was retracted, OR<br>
	 * - At least one rule was fired
	 * @.category public-api
	 * @since 5.4
	 */
	public void expectSomething() {
		TesterRun currentRun = engine.getCurrentRun();
		RunSummary summary = currentRun.getCurrentRunSummary();
		if (summary.getNumCreatedObjects() > 0) {
			return;
		}
		if (summary.getNumModifiedObjects() > 0) {
			return;
		}
		if (summary.getNumRetractedObjects() > 0) {
			return;
		}
		if (summary.getNumRulesFired() > 0) {
			return;
		}
		throw new AssertionError("Expected something, but no objects were created and no rules were executed");
	}

	/**
	 * Expect that a particular entity specified by <code>uri</code> has <i>not</i> been modified
	 * For example, expectUnmodified("/Concepts/Person") will test whether a
	 * Person concept has been modified, and will fail if so<br><br>
	 * Note that this will test whether <i>any</i> /Concepts/Person instance has been modified
	 * To test a specific entity instance, use the <code>expectModified(String uri, String propertyName, Object newValue, String extId)</code>
	 * method instead
	 * @param uri - the entity uri (i.e. "/Concepts/Person")
	 * @.category public-api
	 * @since 5.4
	 */
	public void expectUnmodified(String uri) {
		expectUnmodified(uri, null);
	}

	/**
	 * Expect that a particular property with the name <code>propertyName</code> 
	 * in the entity specified by <code>uri</code> has <i>not</i> been modified
	 * For example, expectModified("/Concepts/Person", "department") will test whether a
	 * Person concept has a property named 'department' that has been modified, and will fail if so<br><br>
	 * To test a specific entity instance, use the <code>expectModified(String uri, String propertyName, Object newValue, String extId)</code>
	 * method instead
	 * @param uri - the entity uri (i.e. "/Concepts/Person")
	 * @param propertyName - the name of the property to check
	 * @.category public-api
	 * @since 5.4
	 */
	public void expectUnmodified(String uri, String propertyName) {
		TesterObject reteObject = findReteObject(uri, ExpectationType.CONCEPT_MODIFICATION, null);
		if (reteObject == null) {
			return;
		}
		// check to ensure that individual properties were changed?
		throw new AssertionError("Expected entity '"+uri+"' was modified");
	}
	
	/**
	 * Expect that a particular entity specified by <code>uri</code> has <i>not</i> been modified
	 * For example, expectUnmodified("/Concepts/Person") will test whether a
	 * Person concept has been modified, and will fail if so<br><br>
	 * Note that this will test whether <i>any</i> /Concepts/Person instance has been modified
	 * To test a specific entity instance, use the <code>expectModified(String uri, String propertyName, Object newValue, String extId)</code>
	 * method instead
	 * @param extId - the extId of the entity
	 * @.category public-api
	 * @since 5.4
	 */
	public void expectUnmodifiedByExtId(String extId) {
		expectUnmodifiedByExtId(extId, null);
	}

	/**
	 * Expect that a particular property with the name <code>propertyName</code> 
	 * in the entity specified by <code>uri</code> has <i>not</i> been modified
	 * For example, expectModified("/Concepts/Person", "department") will test whether a
	 * Person concept has a property named 'department' that has been modified, and will fail if so<br><br>
	 * To test a specific entity instance, use the <code>expectModified(String uri, String propertyName, Object newValue, String extId)</code>
	 * method instead
	 * @param extId - the extId of the entity
	 * @param propertyName - the name of the property to check
	 * @.category public-api
	 * @since 5.4
	 */
	public void expectUnmodifiedByExtId(String extId, String propertyName) {
		TesterObject reteObject = findReteObject(null, ExpectationType.CONCEPT_MODIFICATION, extId);
		if (reteObject == null) {
			return;
		}
		// check to ensure that individual properties were changed?
		throw new AssertionError("Expected entity '"+extId+"' was modified");
	}
	
	/**
	 * Expect that all rules specified in <code>ruleURIs</code> have fired, in any order.
	 * For example:<br><br>
	 * <code>
	 *  engine.executeRules(); <br>
	 *  List<String> rules = new ArrayList<String>();<br>
	 *  rules.add("/Rules/ProcessApplication");<br>
	 *  rules.add("/Rules/FraudDetected");<br>
	 * </code><br>
	 * Will test whether the rules 'ProcessApplication' and 'FraudDetected' have fired, in <i>any</i> order, and will fail if
	 * any of the rules did not fire
	 * @param ruleURIs - a List of Strings corresponding to the expected rules
	 * @.category public-api
	 * @since 5.4
	 */
	public void expectUnordered(List<String> ruleURIs) {
		for (String uri : ruleURIs) {
			TesterObject reteObject = findReteObject(uri, ExpectationType.RULE_EXECUTION, null);
			if (reteObject == null) {
				throw new AssertionError("Expected Rule '"+uri+"' did not fire");
			}
		}
	}
	
	private TesterObject findReteObject(String uri, ExpectationType type, String extId) {
		TesterRun currentRun = engine.getCurrentRun();
		RunSummary runSummary = currentRun.new RunSummary();
		TesterObject[] inputObjects = runSummary.getCurrentQueuedObjects();
		TesterObject foundObj = searchTesterObjects(uri, type, extId, inputObjects);
		if (foundObj == null) {
			inputObjects = runSummary.getCurrentQueuedLifecycleObjects();
			foundObj = searchTesterObjects(uri, type, extId, inputObjects);
		}
		return foundObj;
	}

	private TesterObject searchTesterObjects(String uri, ExpectationType type, String extId, TesterObject[] inputObjects) {
		// search in reverse order, so that we check the final value of modified properties
		for (int i = inputObjects.length - 1; i >= 0; i--) {
			TesterObject testerObject = inputObjects[i];
			boolean isExpectedType = testerObject instanceof ReteObject ? isExpectedType(type, ((ReteObject)testerObject).getReteChangeType()) : isExpectedType(type, ((LifecycleObject)testerObject).getLifecycleEventType());
			if (type == null || isExpectedType) {
				if (testerObject.getWrappedObject() instanceof PreprocessorCause || testerObject.getInvocationObject().getWrappedObject() instanceof PreprocessorCause) {
					if (!includePreprocessorContext) {
						return null;
					}
				}
				Object obj = testerObject.getWrappedObject();
				if (obj instanceof SchedulerTask) {
					obj = ((SchedulerTask) obj).getTarget();
				}
				if (obj instanceof ChannelTask) {
					TesterObject[] causalObjects = testerObject.getInvocationObject().getCausalObjects();
					if (causalObjects != null && causalObjects.length == 1) {
						// this SHOULD be the case
						obj = causalObjects[0].getWrappedObject();
					}
				}
//				if (obj instanceof SendEventTask) {
//					obj = ((SendEventTask) obj).getEvent();
//				}
				if (obj instanceof Rule) {
					Rule rule = (Rule) obj;
					if (uri != null && uri.equals(rule.getUri())) {
						return testerObject;
					}
				} else if (obj instanceof Concept) {
					if (uri == null && extId != null) {
						if (((Concept) obj).getExtId().equals(extId)) {
							return testerObject;
						}
					}
					if (uri != null) {
						ExpandedName expandedName = ((Concept) obj).getExpandedName();
						if (expandedName.equals(getExpandedName(uri))) {
							if (extId == null) {
								return testerObject;
							} else if (((Concept) obj).getExtId().equals(extId)) {
								return testerObject;
							}
						}
					} else if (extId != null) {
						if (((Concept) obj).getExtId().equals(extId)) {
							return testerObject;
						}
					}
				} else if (obj instanceof SimpleEvent) {
					if (uri == null && extId != null) {
						if (((SimpleEvent) obj).getExtId().equals(extId)) {
							return testerObject;
						}
					}
					if (uri != null) {
						ExpandedName expandedName = ((SimpleEvent) obj).getExpandedName();
						if (expandedName.equals(getExpandedName(uri))) {
							if (extId == null) {
								return testerObject;
							} else if (((SimpleEvent) obj).getExtId().equals(extId)) {
								return testerObject;
							}
						}
					} else if (extId != null) {
						if (((SimpleEvent) obj).getExtId().equals(extId)) {
							return testerObject;
						}
					}
				} else if (obj instanceof TimeEvent) {
					if (uri == null && extId != null) {
						if (((TimeEvent) obj).getExtId().equals(extId)) {
							return testerObject;
						}
					}
					if (uri != null) {
						String evType = ((TimeEvent) obj).getType();
						if (evType.equals(getExpandedName(uri).namespaceURI)) {
							if (extId == null) {
								return testerObject;
							} else if (((TimeEvent) obj).getExtId().equals(extId)) {
								return testerObject;
							}
						}
					} else if (extId != null) {
						if (((TimeEvent) obj).getExtId().equals(extId)) {
							return testerObject;
						}
					}
				}
			}
		}
		return null;
	}
	
	private List<ReteObject> findReteObjects(String uri, ExpectationType type) {
		List<ReteObject> objects = new ArrayList<ReteObject>();
		TesterRun currentRun = engine.getCurrentRun();
		RunSummary runSummary = currentRun.new RunSummary();
		ReteObject[] inputObjects = runSummary.getCurrentQueuedObjects();
		for (ReteObject reteObject : inputObjects) {
			if (type == null || isExpectedType(type, reteObject.getReteChangeType())) {
				Object obj = type == ExpectationType.RULE_EXECUTION ? reteObject.getInvocationObject().getWrappedObject() : reteObject.getWrappedObject();
				if (obj instanceof Rule) {
					Rule rule = (Rule) obj;
					if (uri.equals(rule.getUri())) {
						objects.add(reteObject);
					}
				} else if (obj instanceof Concept) {
					ExpandedName expandedName = ((Concept) obj).getExpandedName();
					if (getExpandedName(uri).equals(expandedName)) {
						objects.add(reteObject);
					}
				} else if (obj instanceof SimpleEvent) {
					ExpandedName expandedName = ((SimpleEvent) obj).getExpandedName();
					if (getExpandedName(uri).equals(expandedName)) {
						objects.add(reteObject);
					}
				}
			}
		}
//		if (includeTesterObjects ) {
//			Set<ReteObject> inputObjs = runSummary.getInputObjects();
//			Iterator<ReteObject> it = inputObjs.iterator();
//			while (it.hasNext()) {
//				ReteObject reteObject = (ReteObject) it.next();
//				if (type == null || isExpectedType(type, reteObject.getReteChangeType())) {
//					Object obj = reteObject.getWrappedObject();
//					if (obj instanceof Rule) {
//						Rule rule = (Rule) obj;
//						if (uri.equals(rule.getUri())) {
//							objects.add(reteObject);
//						}
//					} else if (obj instanceof Concept) {
//						ExpandedName expandedName = ((Concept) obj).getExpandedName();
//						if (getExpandedName(uri).equals(expandedName)) {
//							objects.add(reteObject);
//						}
//					} else if (obj instanceof SimpleEvent) {
//						ExpandedName expandedName = ((SimpleEvent) obj).getExpandedName();
//						if (getExpandedName(uri).equals(expandedName)) {
//							objects.add(reteObject);
//						}
//					}
//				}
//			}
//		}
		return objects;
	}

	private ExpandedName getExpandedName(String uri) {
		int idx = uri.lastIndexOf('/');
		String locName = uri;
	
		locName = uri.substring(idx+1);
		
		return ExpandedName.makeName(BE_NAMESPACE+uri, locName);
	}

	private boolean isExpectedType(ExpectationType type, LifecycleEventType eventType) {
		switch (type) {
			
		case EVENT_SENT:
			return eventType == LifecycleEventType.EVENT_SENT;
			
		case WORK_REMOVED:
			return eventType == LifecycleEventType.WORK_REMOVED;
			
		case WORK_SCHEDULED:
			return eventType == LifecycleEventType.WORK_SCHEDULED;
			
		default:
			break;
		}
		return false;
	}
	
	private boolean isExpectedType(ExpectationType type, ReteChangeType reteChangeType) {
		switch (type) {
			
		case CONCEPT_MODIFICATION:
			return reteChangeType == ReteChangeType.MODIFY;
			
		case CONCEPT_ASSERTED:
		case EVENT_ASSERTED:
			return reteChangeType == ReteChangeType.ASSERT;
			
		case RULE_EXECUTION:
		case RULE_EXECTION:
			return reteChangeType == ReteChangeType.RULEEXECUTION || reteChangeType == ReteChangeType.RULEFIRED;
			
		case TIME_EVENT_SCHEDULED:
			return reteChangeType == ReteChangeType.SCHEDULEDTIMEEVENT;
			
		case EVENT_RETRACTED:
			return reteChangeType == ReteChangeType.RETRACT;
			
		default:
			break;
		}
		return false;
	}
	
	private List<Handle> getHandles(String uri, boolean all) {
		TesterRun currentRun = engine.getCurrentRun();
		List<Handle> matchingHandles = new ArrayList<Handle>();
		List handles = currentRun.getOwnerSession().getRuleSession().getObjectManager().getHandles();
		for (Object object : handles) {
			Handle handle = (Handle) object;
			Object handleObj = handle.getObject();
			ExpandedName entityName = null;
			if (handleObj instanceof Concept) {
				entityName = ((Concept) handleObj).getExpandedName();
			} else if (handleObj instanceof SimpleEvent) {
				entityName = ((SimpleEvent) handleObj).getExpandedName();
			} else if (handleObj instanceof TimeEvent) {
				entityName = getExpandedName(((TimeEvent) handleObj).getType());
			}
			if (getExpandedName(uri).equals(entityName)) {
				matchingHandles.add(handle);
				if (!all) {
					return matchingHandles;
				}
			}
		}
		return matchingHandles;
	}

}
