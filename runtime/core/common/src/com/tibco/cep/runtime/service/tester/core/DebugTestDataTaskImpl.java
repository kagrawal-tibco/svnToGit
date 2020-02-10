package com.tibco.cep.runtime.service.tester.core;

import static com.tibco.cep.runtime.model.TypeManager.DEFAULT_BE_NAMESPACE_URI;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xml.sax.InputSource;

import com.tibco.be.parser.codegen.CGConstants;
import com.tibco.cep.kernel.model.entity.Element;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.kernel.service.ObjectManager;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.channel.Channel;
import com.tibco.cep.runtime.channel.SerializationContext;
import com.tibco.cep.runtime.channel.impl.DefaultSerializationContext;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.impl.XiNodePayload;
import com.tibco.cep.runtime.service.cluster.om.DistributedCacheBasedStore;
import com.tibco.cep.runtime.service.debug.AbstractDebugResponseTask;
import com.tibco.cep.runtime.service.debug.DebuggerService;
import com.tibco.cep.runtime.service.tester.model.ResultObject;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionConfig.InputDestinationConfig;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;
import com.tibco.cep.runtime.session.impl.RuleSessionManagerImpl;
import com.tibco.xml.channel.infoset.XmlContentHandler;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParserFactory;

/**
 * 
 * @author sasahoo
 *
 */
public class DebugTestDataTaskImpl extends AbstractDebugResponseTask {

    private static final String DEFAULT_ENCODING = "UTF-8";
    private String testerSessionName;
    private RuleServiceProvider provider;
    private String[] xmlData;
    private String[] entityURI;
    private String[] destinationURI;
    private RuleSession ruleSession;
    int sessionCounter;
    private static final Logger logger = LogManagerFactory.getLogManager().getLogger(DebugTestDataTaskImpl.class);
    private Map<Entity, Map<String, Object>> modifiedScorecardMap =  new HashMap<Entity, Map<String,Object>>();
    
    /**
     * @param rsp
     * @param ruleSessionName
     * @param testerSessionName
     * @param xmlData
     * @param entityURI
     * @param destinationURI
     * @param sessionCounter
     */
    public DebugTestDataTaskImpl(RuleServiceProvider rsp,
		                         String ruleSessionName,
		                         String testerSessionName,
		                         String[] xmlData, 
		                         String[] entityURI,
		                         String[] destinationURI, 
		                         int sessionCounter) {
        this.provider = rsp;
        this.xmlData = xmlData;
        this.entityURI = entityURI;
        this.destinationURI = destinationURI;
        this.testerSessionName = testerSessionName;
        this.ruleSession = provider.getRuleRuntime().getRuleSession(ruleSessionName);
        this.sessionCounter = sessionCounter;
        ReteObjectInfoProvider.getInstance().getReteObjectList().clear();
        logger.log(Level.INFO, "Creating test task with RuleSession %s", ruleSession.getName());
        
    }
    
    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    public void run() {
        if (ruleSession == null) {
            logger.log(Level.ERROR, "Rulesession not found");
            return;
        }
        RuleSessionManagerImpl rsm =  (RuleSessionManagerImpl) ruleSession.getRuleRuntime();
        TesterRun testerRun = startRun(testerSessionName);
        try {
            for (int i = 0; i < xmlData.length ; i++) {
                logger.log(Level.DEBUG, "xmlData >>> %s", xmlData[i]);
                Entity entity = deserializeEntity(xmlData[i], entityURI[i], destinationURI[i]);
                logger.log(Level.DEBUG, "Deserialized entity >>> %s", entity);
                if (entity == null) {
                	continue;
                }
                testerRun.assertObject(ruleSession, entity);
            }
            if (modifiedScorecardMap.size() > 0 ) {
            	 WorkingMemoryManipulator workingMemoryManipulator = new WorkingMemoryManipulator(ruleSession);
            	 workingMemoryManipulator.updateScorecardInstance(modifiedScorecardMap); 
            }
            //ResultObject serialization needs thread-local rulesession
            rsm.setCurrentRuleSession(ruleSession);
            logger.log(Level.DEBUG, "**************ReteObjectInfoProvider*************** %s", ReteObjectInfoProvider.getInstance().getReteObjectList().size());
            ResultObject resultObject =	new ResultObject(testerRun.getQueuedObjects(), testerRun.getRunName());
            logger.log(Level.DEBUG, "resultObjects size>>> %s", resultObject.getQueuedObjects().size());
            if ( resultObject.getQueuedObjects().size() == 0) {
            	resultObject =	new ResultObject( ReteObjectInfoProvider.getInstance().getReteObjectList(), testerRun.getRunName());
            }
            ResultObjectSerializer serializer = new ResultObjectSerializer();
            response = serializer.serialize(resultObject);
            logger.log(Level.DEBUG, "Respose >>> %s", response);
        }
        catch (Exception e) {
            logger.log(Level.ERROR, e, e.getMessage());
        } finally {
        	rsm.setCurrentRuleSession(null);
        }
    }
    
    /**
     * @param id
     * @param includeContained
     * @return
     */
    public Concept cacheLoadConceptById(long id, boolean includeContained) {
        try {
            RuleSession session = RuleSessionManager.getCurrentRuleSession();
            ObjectManager om = session.getObjectManager();
            if (om instanceof DistributedCacheBasedStore) {
                DistributedCacheBasedStore cs_om = (DistributedCacheBasedStore) om;
                Element element = cs_om.getElement(id);
                if (element != null) {
                    boolean isDeleted = ((ConceptImpl) element).isMarkedDeleted();
                    if (!isDeleted) {
                        if (includeContained) {
                            List children = element.getChildren();
                            if (children != null) {
                                children.add(element);
                                ((RuleSessionImpl) session).reloadFromCache(children);
                            }
                            else {
                                List toLoad = new ArrayList();
                                toLoad.add(element);
                                ((RuleSessionImpl) session).reloadFromCache(toLoad);
                            }
                        }
                        else {
                            List toLoad = new ArrayList();
                            toLoad.add(element);
                            ((RuleSessionImpl) session).reloadFromCache(toLoad);
                        }
                    }
                    else {
                        return null;
                    }
                }
                return (Concept) element;
            }
            else {
                throw new RuntimeException("Coherence Object Manager is not defined for this Rule Session");
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     *
     * @param eventClass
     * @return
     * @throws Exception
     */
    private SimpleEvent createEvent (Class eventClass, byte[] bytes) throws Exception {
        Constructor cons = eventClass.getConstructor(new Class[]{long.class});
        SimpleEvent event =
                (SimpleEvent) cons.newInstance(new Object[]{new Long(provider.getIdGenerator().nextEntityId(eventClass))});
        if (xmlData == null) {
            return event;
        }
        cons = DebuggerService.getSax2EventClass().getConstructor(new Class[] {  RuleSession.class, SimpleEvent.class});
        XmlContentHandler handler = (XmlContentHandler) cons.newInstance(new Object[] { ruleSession,event});
        XiParserFactory.newInstance().parse(new InputSource(new ByteArrayInputStream(bytes)), handler);
        
        // check if this need to be converted to JSON
        if (TestDataTaskImpl.checkForJSONPayloadConversion(event)) {
        	logger.log(Level.DEBUG, "Setting Event "+event.getExpandedName()+"'s payload as JSON");
        	XiNodePayload payloadNode = (XiNodePayload)event.getPayload();
        	payloadNode.setJSONPayload(true);
        }
        
        return event;
    }

    /**
     *
     * @return
     * @throws Exception
     */
    private Entity deserializeEntity(String data, String entityURI, String destinationURI) throws Exception {
        byte[] bytes = data.getBytes(DEFAULT_ENCODING);
     
 
        XiNode rootNode = XiParserFactory.newInstance().parse(new InputSource(new ByteArrayInputStream(bytes)));
        String namespace = rootNode.getFirstChild().getFirstNamespace().getStringValue();
        logger.log(Level.DEBUG, "Default namespace URI >>> %s", namespace);
        //Chunk it
        int index = namespace.indexOf(DEFAULT_BE_NAMESPACE_URI);
        String entityURI1 = namespace.substring(index + DEFAULT_BE_NAMESPACE_URI.length(), namespace.length());
        
        Class entityClass = provider.getTypeManager().getTypeDescriptor(entityURI1).getImplClass();
        Entity entity = null;
        logger.log(Level.DEBUG, "Entity Class >>> %s", entityClass);
        //TODO Obviate the need to parse XML twice.
        if (SimpleEvent.class.isAssignableFrom(entityClass)) {
            entity = createEvent(entityClass, bytes);
            logger.log(Level.INFO, "Creating event: %s", entityURI1);
            Channel.Destination destination = provider.getChannelManager().getDestination(destinationURI);
            if (destination != null) {
            	SerializationContext sci= new DefaultSerializationContext(ruleSession, destination);
            	InputDestinationConfig config = sci.getDeployedDestinationConfig();
            	if(config != null) {
        			RuleFunction preprocessor = sci.getDeployedDestinationConfig().getPreprocessor();	
                    if (preprocessor != null) {
                        ((RuleSessionImpl) ruleSession).preprocessPassthru(preprocessor, (SimpleEvent)entity);
                        setResponse(true);
                        return null;
                    }
            	}
            }
        } else if (Concept.class.isAssignableFrom(entityClass)) {
        	entity = createInstance(entityClass, bytes);
        	boolean isScorecard = false;
        	Class<?> clazz = entity.getClass();
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
        return entity;
    }

    /**
     *
     * @param conceptClass
     * @return
     * @throws Exception
     */
    private Concept createInstance (Class conceptClass, byte[] bytes) throws Exception{

        Constructor cons = conceptClass.getConstructor(new Class[]{long.class});
        Concept cept =
                (Concept) cons.newInstance(new Object[]{new Long(provider.getIdGenerator().nextEntityId(conceptClass))});
        if (xmlData == null) {
            return cept;
        }

        cons = DebuggerService.getSax2ConceptClass().getConstructor(new Class[] {Concept.class,RuleSession.class });
        XmlContentHandler handler =
                (XmlContentHandler) cons.newInstance(new Object[] {cept, ruleSession });
        XiParserFactory.newInstance().parse(new InputSource(new ByteArrayInputStream(bytes)), handler);

        return cept;
    }



    /**
     * @param sessionName
     * @return
     */
    private TesterSession registerSession(String sessionName) {
        if (provider instanceof TesterRuleServiceProvider) {
            TesterRuleServiceProvider trsp = (TesterRuleServiceProvider)provider;
            TesterSession testerSession = trsp.registerSession(ruleSession, sessionName);
            return testerSession;
        }
        return null;
    }

    /**
     * @param sessionName
     * @return
     */
    private TesterRun startRun(String sessionName) {
        TesterSession testerSession = registerSession(sessionName);
        if (testerSession != null) {
            TesterRun testerRun = testerSession.start();
            return testerRun;
        }
        return null;
    }
}
