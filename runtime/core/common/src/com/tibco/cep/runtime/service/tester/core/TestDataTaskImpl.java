package com.tibco.cep.runtime.service.tester.core;

import static com.tibco.cep.runtime.model.TypeManager.DEFAULT_BE_NAMESPACE_URI;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.xml.sax.InputSource;

import com.tibco.be.parser.codegen.CGConstants;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.channel.Channel;
import com.tibco.cep.runtime.channel.JSONPayloadDestination;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.PropertyAtomConceptReference;
import com.tibco.cep.runtime.model.element.PropertyAtomContainedConcept;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyArrayConceptReferenceSimple;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyArrayContainedConceptSimple;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.impl.XiNodePayload;
import com.tibco.cep.runtime.service.debug.AbstractDebugResponseTask;
import com.tibco.cep.runtime.service.debug.DebuggerService;
import com.tibco.cep.runtime.service.tester.model.ResultObject;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.impl.RuleSessionManagerImpl;
import com.tibco.xml.XiNodeUtilities;
import com.tibco.xml.channel.infoset.XmlContentHandler;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParserFactory;

public class TestDataTaskImpl extends AbstractDebugResponseTask {

	private class Assertable {
		Entity entity;
		String preprocessorURI;
		long delay = -1;
	}
	
    private static final String DEFAULT_ENCODING = "UTF-8";

    private String testerSessionName;

    private RuleServiceProvider provider;

    /**
     * This will be multiple
     */
    private String[] inputData;

    private RuleSession ruleSession;

    int sessionCounter;

    private static final Logger logger = LogManagerFactory.getLogManager().getLogger(TestDataTaskImpl.class);

    private Map<Entity, Map<String, Object>> modifiedScorecardMap =  new HashMap<Entity, Map<String,Object>>();
    
    private HashMap <String, List<Entity>> conceptstobeAsserted = new HashMap<String, List<Entity>> ();
    
    private List <Entity> dependentConceptsList=new LinkedList<Entity>();
    
  //  private List <String> valueList=new LinkedList<String>();

    /**
     *
     * @param rsp
     * @param ruleSessionName
     * @param testerSessionName
     * @param inputData
     */
    public TestDataTaskImpl(RuleServiceProvider rsp,
                            String ruleSessionName,
                            String testerSessionName,
                            String[] inputData) {
        this.provider = rsp;
        
        this.inputData = inputData;

        this.testerSessionName = testerSessionName;

        this.ruleSession = provider.getRuleRuntime().getRuleSession(ruleSessionName);
        
        ReteObjectInfoProvider.getInstance().getReteObjectList().clear();
        
        //Purposefully called ruleSession.getName()
        logger.log(Level.INFO, "Creating test task with RuleSession %s", ruleSession.getName());
    }
    
    public void run() {
        if (ruleSession == null) {
            logger.log(Level.ERROR, "Rulesession not found");
            return;
        }
        
        //Commenting Code - In case of caller thread(debuggerservice), 
        //setting rulesession will throw an exception in preprocessPassthru
        RuleSessionManagerImpl rsm =  (RuleSessionManagerImpl) ruleSession.getRuleRuntime();
        //rsm.setCurrentRuleSession(ruleSession);
        TesterRun testerRun = startRun(testerSessionName);
        // also the deserializeEntity which now calls SAX4ConceptInstance needs the rulesession from current thread
        rsm.setCurrentRuleSession(ruleSession);
 //       ArrayList <XiNode> XiArray = new ArrayList<XiNode>();
        List<Assertable> assertableList=new LinkedList<Assertable>();
        try {
            for (String data : inputData) {
            	logger.log(Level.DEBUG, "InputData >>> %s", data);
//            	create an assertable class with preprocessor and delay
//            	the preprocessor needs a URI - add it under checkbox
                Assertable assertable = deserializeEntity(data);
                if (assertable == null) {
                	continue;
                }
                logger.log(Level.DEBUG, "Deserialized entity >>> %s", assertable.entity);
                
                assertableList.add(assertable);
             }
            
            for(Assertable a:assertableList){
            	long delay = a.delay;
            	if (delay > 0) {
            		// delay the assert
            		logger.log(Level.DEBUG, "Delaying the assert by %s milliseconds", delay);
            		Thread.sleep(delay);
            	}
            	if (a.preprocessorURI != null && !a.preprocessorURI.isEmpty()) {
            		logger.log(Level.DEBUG, "Asserting object with preprocessor >>> %s", a.preprocessorURI);
            		testerRun.assertObject(ruleSession, a.entity, true, a.preprocessorURI);
            	} else {
            		testerRun.assertObject(ruleSession, a.entity);
            	}
            }
            
            if (modifiedScorecardMap.size() > 0 ) {
            	 WorkingMemoryManipulator workingMemoryManipulator = new WorkingMemoryManipulator(ruleSession);
            	 workingMemoryManipulator.updateScorecardInstance(modifiedScorecardMap); 
            }
            //ResultObject serialization needs thread-local rulesession
            rsm.setCurrentRuleSession(ruleSession);
            logger.log(Level.DEBUG, "**************ReteObjectInfoProvider*************** %s", ReteObjectInfoProvider.getInstance().getReteObjectList().size());
            ResultObject resultObject =	new ResultObject(testerRun.getQueuedObjects(), testerRun.getRunName());
            if ( resultObject.getQueuedObjects().size() == 0) {
            	resultObject =	new ResultObject( ReteObjectInfoProvider.getInstance().getReteObjectList(), testerRun.getRunName());
            } 
            logger.log(Level.DEBUG, "resultObjects size>>> %s", resultObject.getQueuedObjects().size());
            ResultObjectSerializer serializer = new ResultObjectSerializer();
            response = serializer.serialize(resultObject);
        }
        catch (Exception e) {
            logger.log(Level.ERROR, e, e.getMessage());
        } finally {
        	rsm.setCurrentRuleSession(null);
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

        if (inputData == null) {
            return event;
        }
        cons = DebuggerService.getSax2EventClass().getConstructor(new Class[] {RuleSession.class, SimpleEvent.class});

        XmlContentHandler handler = (XmlContentHandler) cons.newInstance(new Object[] { ruleSession,event});

        XiParserFactory.newInstance().parse(new InputSource(new ByteArrayInputStream(bytes)), handler);

        // check if this need to be converted to JSON
        if (checkForJSONPayloadConversion(event)) {
        	XiNodePayload payloadNode = (XiNodePayload)event.getPayload();
        	logger.log(Level.DEBUG, "Setting Event "+event.getExpandedName()+"'s payload as JSON");
        	payloadNode.setJSONPayload(true);
        }
        
        return event;
    }
    
    public static boolean checkForJSONPayloadConversion(SimpleEvent evt) throws Exception {
    	RuleServiceProvider rsp = RuleServiceProviderManager.getInstance().getDefaultProvider();
    	if (rsp == null) {
    		// this can happen in API mode
    		return false;
    	}
    	Channel.Destination destination = rsp.getChannelManager().getDestination(evt.getDestinationURI());
    	if (destination instanceof JSONPayloadDestination) {
    		return (((JSONPayloadDestination)destination).isJSONPayload() && evt.getPayload() instanceof XiNodePayload);
    	}

    	return false;
    }
    /**
     *
     * @return
     * @throws Exception
     */
    private Assertable deserializeEntity(String data) throws Exception {
    	Assertable assertable = new Assertable();

    	List<String> resPathList=new LinkedList<String>();
    	List<String> valueList=new LinkedList<String>();
    	Map<String,String> nameTypeMap=new LinkedHashMap<String,String>();
    	logger.log(Level.INFO, "Here's the data: "+data);
    	logger.log(Level.DEBUG, "Changes are found");
       	byte[] bytes = data.getBytes(DEFAULT_ENCODING);
        XiNode rootNode = XiParserFactory.newInstance().parse(new InputSource(new ByteArrayInputStream(bytes)));
        XiNode child = rootNode.getFirstChild();
        XiNode delay = child.getAttribute(ExpandedName.makeName("delay"));
        boolean modified = false;
        if (delay != null) {
        	logger.log(Level.DEBUG, "Delay the input by >>> %s", delay.getStringValue());
        	child.removeAttribute(ExpandedName.makeName("delay"));
        	assertable.delay = (long) Double.parseDouble(delay.getStringValue());
        	modified = true;
        }
        XiNode invokePreproc = child.getAttribute(ExpandedName.makeName("preprocessor"));
        if (invokePreproc != null) {
        	logger.log(Level.DEBUG, "Should invoke the preprocessor >>> %s", invokePreproc.getStringValue());
        	child.removeAttribute(ExpandedName.makeName("preprocessor"));
        	assertable.preprocessorURI = invokePreproc.getStringValue();
        	modified = true;
        }
        String namespace = child.getFirstNamespace().getStringValue();
        logger.log(Level.DEBUG, "Default namespace URI >>> %s", namespace);
        //Chunk it
        int index = namespace.indexOf(DEFAULT_BE_NAMESPACE_URI);
        String entityURI = namespace.substring(index + DEFAULT_BE_NAMESPACE_URI.length(), namespace.length());
        logger.log(Level.DEBUG, "Entity namespace URI >>> %s", entityURI);
        Class entityClass = provider.getTypeManager().getTypeDescriptor(entityURI).getImplClass();
        Entity entity = null;
        logger.log(Level.DEBUG, "Entity Class >>> %s", entityClass);
        //TODO Obviate the need to parse XML twice.
        if (SimpleEvent.class.isAssignableFrom(entityClass)) {
        	if (modified) {
        		bytes = XiNodeUtilities.toString(rootNode, "UTF-8", false).getBytes();
        	}
            entity = createEvent(entityClass, bytes);
        } else if (Concept.class.isAssignableFrom(entityClass)) {
        	Iterator<XiNode> propertyNodeIterator = rootNode.getChildren();
        	while(propertyNodeIterator.hasNext()){
        		XiNode propertyNode = propertyNodeIterator.next();
        		Iterator<XiNode> it=propertyNode.getChildren();
        		while(it.hasNext()){
        			XiNode node=it.next();
        			if(node.getName().toString()!=null){
        				String type = node.getAttributeStringValue(ExpandedName.makeName("type"));
        				nameTypeMap.put(node.getName().toString(), node.getAttributeStringValue(ExpandedName.makeName("type")));
        				if("ContainedConcept".equals(type)){
        					if(node.getStringValue()!=null){
        						valueList.add(node.getStringValue());
        					}
        				}
        			}
        			if(node.getAttributeStringValue(ExpandedName.makeName("resourcePath"))!=null){
        				resPathList.add(node.getAttributeStringValue(ExpandedName.makeName("resourcePath")));
        			}
        		}
        	}

        	entity = createInstance(entityClass, bytes);
       	
        	if(resPathList.size()>0){
        		Concept cept=(Concept)entity;
        		Set<String> names=nameTypeMap.keySet();
        		int valueCount=0;
        		for(String pName:names){
        			String type=nameTypeMap.get(pName);
        			if("ConceptReference".equals(type)) {
						if(cept.getProperty(pName) instanceof PropertyArrayConceptReferenceSimple){
							PropertyArrayConceptReferenceSimple property=(PropertyArrayConceptReferenceSimple)cept.getProperty(pName);
							String conceptName=property.getType().getName();
							String[] array=conceptName.split("\\.");
							conceptName=array[array.length-1];
							int len=0;
							List<Entity> entityList=conceptstobeAsserted.get(conceptName);
							if(entityList!=null){
								Iterator<Entity> it=entityList.iterator();
								while(it.hasNext()){
									Concept c = (Concept) it.next();
									property.set(len++,c);
								}
							}
						}	
						if(cept.getProperty(pName) instanceof PropertyAtomConceptReference){
							PropertyAtomConceptReference property=(PropertyAtomConceptReference)cept.getProperty(pName);
							String dependentConName=property.getType().getName();
	       					String[] array=dependentConName.split("\\.");
	        				dependentConName=array[array.length-1];
	        				List<Entity> entityList=conceptstobeAsserted.get(dependentConName);
	        				if(entityList!=null){
	        					Concept dependentConcept=(Concept)entityList.get(0);
	        					property.setConcept(dependentConcept);
	        				}
						}
        			}
        			if("ContainedConcept".equals(type)){
       					if(cept.getProperty(pName) instanceof PropertyArrayContainedConceptSimple){
       						PropertyArrayContainedConceptSimple property=(PropertyArrayContainedConceptSimple)cept.getProperty(pName);
       						
        					for(int len=0;len<property.length();len++){
        						PropertyAtomContainedConcept con=(PropertyAtomContainedConcept)property.get(len);
        						Concept concept=con.getConcept();
        						Property properties[]=concept.getProperties();
        						String values[]=valueList.get(valueCount++).split(";");
        						for(int counter=0;counter<properties.length;counter++){
        							if (counter >= values.length) {
        								break;
        							}
        							concept.setPropertyValue(properties[counter].getName(), values[counter]);
        						}
        					}
       					}
       					if(cept.getProperty(pName) instanceof PropertyAtomContainedConcept){
        					PropertyAtomContainedConcept con=(PropertyAtomContainedConcept)cept.getProperty(pName);
        					Concept concept=con.getConcept();
        					if (concept != null) {
        						Property properties[]=concept.getProperties();
        						String values[]=valueList.get(valueCount++).split(";");
        						for(int counter=0;counter<properties.length;counter++){
        							if (counter >= values.length) {
        								break;
        							}
        							concept.setPropertyValue(properties[counter].getName(), values[counter]);
        						}
        					}
        				}
        			}
        		}
            }
        	String [] entityName=entityURI.split("/");
        	String entName=entityName[entityName.length-1];

        	if(conceptstobeAsserted.keySet().contains(entName)){

        		dependentConceptsList.add(entity);
        	}
        	else{

        		dependentConceptsList=new LinkedList<Entity>();
        		dependentConceptsList.add(entity);
//        		valueList=new LinkedList<String>();
//        		valueList.add(value);
        	}
        	conceptstobeAsserted.put(entName, dependentConceptsList);					// as of now put both concept and scorecard in the Map  - changed here
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
        assertable.entity = entity;
        return assertable;
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
        if (inputData == null) {
            return cept;
        }
                  
        cons = DebuggerService.getSax2ConceptClass().getConstructor(new Class[] {Concept.class,RuleSession.class });
        XmlContentHandler handler =
                (XmlContentHandler) cons.newInstance(new Object[] {cept, ruleSession });
        XiParserFactory.newInstance().parse(new InputSource(new ByteArrayInputStream(bytes)), handler);
        return cept;
    }

    private TesterSession registerSession(String sessionName) {
        if (provider instanceof TesterRuleServiceProvider) {
            TesterRuleServiceProvider trsp = (TesterRuleServiceProvider)provider;
            TesterSession testerSession = trsp.registerSession(ruleSession, sessionName);
            return testerSession;
        }
        return null;
    }

    private TesterRun startRun(String sessionName) {
        TesterSession testerSession = registerSession(sessionName);
        if (testerSession != null) {
            TesterRun testerRun = testerSession.start();
            return testerRun;
        }
        return null;
    }
}