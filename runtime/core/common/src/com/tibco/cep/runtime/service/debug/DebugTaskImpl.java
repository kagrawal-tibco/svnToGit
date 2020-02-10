package com.tibco.cep.runtime.service.debug;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.xml.sax.InputSource;

import com.tibco.be.parser.codegen.CGConstants;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.channel.Channel;
import com.tibco.cep.runtime.channel.SerializationContext;
import com.tibco.cep.runtime.channel.impl.DefaultSerializationContext;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.service.tester.core.WorkingMemoryManipulator;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionConfig.InputDestinationConfig;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;
import com.tibco.xml.channel.infoset.XmlContentHandler;
import com.tibco.xml.datamodel.XiParserFactory;

class DebugTaskImpl extends AbstractDebugResponseTask {

    private static final String DEFAULT_ENCODING = "UTF-8";

	RuleServiceProvider provider;
    String entityURI;
    String xmlData;

    RuleSession ruleSession;
    Channel.Destination destination;
    int sessionCounter;

    private Map<Entity, Map<String, Object>> modifiedScorecardMap =  new HashMap<Entity, Map<String,Object>>();


    DebugTaskImpl(RuleServiceProvider provider, String entityURI, String xmlData, String ruleSessionURI, String destinationURI,int sessionCounter)
    {
        this.provider = provider;
        this.entityURI = entityURI;
        this.xmlData = xmlData;
        this.sessionCounter = sessionCounter;
        provider.getLogger(this.getClass()).log(Level.INFO, "Creating debug task...");
        if (ruleSessionURI != null) {
            ruleSession = provider.getRuleRuntime().getRuleSession(ruleSessionURI);
        }
        if (destinationURI != null) {
            destination = provider.getChannelManager().getDestination(destinationURI);
        }
    }

    public void run()
    {
        final Logger logger = this.provider.getLogger(this.getClass());
        logger.log(Level.INFO, "Starting debug task...");
        logger.log(Level.DEBUG, xmlData);
        if(ruleSession == null) {
        	logger.log(Level.ERROR, "Rulesession not found");
        	setResponse(false);
        	return;
        }
  //      RuleSessionManagerImpl rsm =  (RuleSessionManagerImpl) ruleSession.getRuleRuntime();
  //      rsm.setCurrentRuleSession(ruleSession);
        //In case of caller thread, 
        //setting rulesession will throw an exception while preprocessPassthru 
        //RuleSessionManagerImpl rsm =  (RuleSessionManagerImpl) ruleSession.getRuleRuntime();
        //rsm.setCurrentRuleSession(ruleSession);
        Class entityClass = provider.getTypeManager().getTypeDescriptor(entityURI).getImplClass();
        Entity entity = null;
        Properties p = new Properties();
        p.setProperty("tibco.clientVar.Debugger/sessionCounter",Integer.valueOf(sessionCounter).toString());
        provider.getGlobalVariables().overwriteGlobalVariables(p);
        provider.getGlobalVariables().validateGlobalVariables();
        try {
            if (SimpleEvent.class.isAssignableFrom(entityClass)) {
            	logger.log(Level.INFO, "Creating event: %s", this.entityURI);
                entity = createEvent(entityClass);
                if (destination != null) {
                	SerializationContext sci= new DefaultSerializationContext(ruleSession, destination);
                	InputDestinationConfig config = sci.getDeployedDestinationConfig();
                	if(config != null) {
            			RuleFunction preprocessor = sci.getDeployedDestinationConfig().getPreprocessor();	
                        if (preprocessor != null) {
                        	RuleSession threadSession = RuleSessionManager.getCurrentRuleSession();
                    		if (threadSession == ruleSession) {
                    			logger.log(Level.DEBUG, "------Already Inside the Rule Session----" + ruleSession.getName());
                    			((RuleSessionImpl)ruleSession).getWorkingMemory().invoke(preprocessor, new Object[]{(SimpleEvent)entity});
                    		} else {
                    			 ((RuleSessionImpl) ruleSession).preprocessPassthru(preprocessor, (SimpleEvent)entity);
                    		}
                            setResponse(true);
                            return;
                        }
                	}
                }
            }
            else {
            	if (Concept.class.isAssignableFrom(entityClass)) {
            		logger.log(Level.INFO, "Creating concept: %s", this.entityURI);
            		entity = createInstance(entityClass);
            		Class<?> clazz = entity.getClass();
            		boolean isScorecard = false;
            		try {
            			clazz.getMethod(CGConstants.scorecardInstanceGetter);
            			isScorecard = true;
            		} catch (NoSuchMethodException nme) {
            			//Do not handle.
            		} finally {
            			if (isScorecard) {
            				Concept newScorecard = (Concept)entity;
            				String name = newScorecard.getExpandedName().getLocalName();
            				logger.log(Level.DEBUG, "New Scorecard name: %s", name);
            				Concept wmScorecard = null;
            				Map<String, Object> changedPropertiesMap = new HashMap<String, Object>();
            				for (Object ob : ruleSession.getObjectManager().getObjects()) {
            					if (ob instanceof Concept) {
            						wmScorecard = (Concept)ob;
            						if (wmScorecard.getExpandedName().getNamespaceURI()
            								.equals(newScorecard.getExpandedName().getNamespaceURI())) {
            							logger.log(Level.DEBUG, "Scorecard: %s", wmScorecard);
            							logger.log(Level.DEBUG, "Scorecard: %s", wmScorecard.getId());
                						for (Property prop : newScorecard.getProperties()) {
                	    					String propName = prop.getName();
                	    					Object newValue = newScorecard.getPropertyValue(propName);
                	    					Object oldValue = wmScorecard.getPropertyValue(propName);
                	    					if (!newValue.equals(oldValue)) {
                	    						logger.log(Level.DEBUG, "Modified Property name: %s", propName);
                	    						logger.log(Level.DEBUG, "Modified Property value: %s", propName);
                	    						changedPropertiesMap.put(propName, newValue.toString());
                	    					}
                	    					logger.log(Level.DEBUG, "Property value: %s", newValue);
                	    				}
                						break;	
            						}
            					}
            				}
            				if (wmScorecard != null && changedPropertiesMap.size() > 0) {
            					modifiedScorecardMap.put(wmScorecard, changedPropertiesMap);
            				}            				
            				logger.log(Level.DEBUG, "scorecard: %s", newScorecard);
            				logger.log(Level.DEBUG, "IsScorecard: %s", "true");
            				entity = null;
            			} else {
            				logger.log(Level.DEBUG, "IsScorecard: %s", "false");
            			}
            		}
            	}
            }
            logger.log(Level.INFO, "Asserting objects: %s", entity);
            if (entity != null) {
                ruleSession.assertObject(entity, true);
                setResponse(true);
            }
            if (modifiedScorecardMap.size() > 0 ) {
            	WorkingMemoryManipulator workingMemoryManipulator = new WorkingMemoryManipulator(ruleSession);
            	workingMemoryManipulator.updateScorecardInstance(modifiedScorecardMap); 
            	setResponse(true);
            }

        }
        catch (Exception ex) {
            ex.printStackTrace();
            setResponse(false);
        } finally {
        	logger.log(Level.INFO, "Debug task executed...");
        }
    }

    private SimpleEvent createEvent (Class eventClass) throws Exception {

        Constructor cons = eventClass.getConstructor(new Class[]{long.class});
        SimpleEvent event = (SimpleEvent) cons.newInstance(new Object[]{new Long(provider.getIdGenerator().nextEntityId(eventClass))});
        if (xmlData == null) return event;

        cons = DebuggerService.getSax2EventClass().getConstructor(new Class[] {  RuleSession.class, SimpleEvent.class});
        XmlContentHandler handler = (XmlContentHandler) cons.newInstance(new Object[] { ruleSession,event});
        XiParserFactory.newInstance().parse(new InputSource(new ByteArrayInputStream(xmlData.getBytes(DEFAULT_ENCODING))), handler);

        return event;
    }

    private Concept createInstance (Class conceptClass) throws Exception{

    	Constructor cons = conceptClass.getConstructor(new Class[]{long.class});
        Concept cept = (Concept) cons.newInstance(new Object[]{new Long(provider.getIdGenerator().nextEntityId(conceptClass))});
        if (xmlData == null) return cept;

        cons = DebuggerService.getSax2ConceptClass().getConstructor(new Class[] {Concept.class,RuleSession.class });
        XmlContentHandler handler = (XmlContentHandler) cons.newInstance(new Object[] { cept,ruleSession });
        XiParserFactory.newInstance().parse(new InputSource(new ByteArrayInputStream(xmlData.getBytes(DEFAULT_ENCODING))), handler);

        return cept;
    }
}
