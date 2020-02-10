package com.tibco.cep.runtime.service.management;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.management.openmbean.TabularDataSupport;

import com.tibco.be.util.BEProperties;
import com.tibco.cep.kernel.helper.Format;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.PropertyArray;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.model.element.impl.EntityImpl;
import com.tibco.cep.runtime.model.event.EventPayload;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.TimeEvent;
import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.service.management.exception.BEMMEntityNotFoundException;
import com.tibco.cep.runtime.service.management.exception.BEMMException;
import com.tibco.cep.runtime.service.management.exception.BEMMUserActivityException;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Mar 8, 2010
 * Time: 11:54:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class OMMethodsImpl extends EntityMBeansHelper {
    protected RuleServiceProvider ruleServiceProvider;

    //OM
   /* public void forceOMCheckpoint(String session) {
        try {
            logger.log(Level.INFO, "Forcing OM checkpoint for session: " + session);
            if (session == null || session.trim().length() == 0) {   //no session name provided
                throw new IllegalArgumentException("Please provide a session name.");
            } else {
                final RuleSession ruleSession = ruleServiceProvider.getRuleRuntime().getRuleSession(session);
                if (null == ruleSession) {
                    throw new IllegalArgumentException("The session " + session + " does not exist. Please provide a valid session name.");
                }
                if (ruleSession.getObjectManager() instanceof PersistentStore) {
                    logger.log(Level.DEBUG,"Enforcing checkpoint for session " + session);
                    ((PersistentStore) ruleSession.getObjectManager()).forceCheckpoint();
                    logger.log(Level.DEBUG,"Checkpoint for session " + session + " committed");
                } else {
                    throw new Exception("Session " + session + " does not use a persistent Object Manager.");
                }
            }
        }//try
        catch (Exception e) {
            logger.log(Level.ERROR,e, e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }//catch
    } //forceOMCheckpoint*/

    /** *
     * @param isExternal: String representing boolean: "true" means the id is extID. Any other string means id is internal id.
     * @param id: Id of the Event.
     * @param sessionName: Name of the Session
     */

    public TabularDataSupport GetEvent(String sessionName, String id, String isExternal) throws Exception {
        try {
            id = id.trim();
            final String INVOKED_METHOD = "getEvent";
            MBeanTabularDataHandler tabularDataHandler = new MBeanTabularDataHandler(logger);
            tabularDataHandler.setTabularData(INVOKED_METHOD);

             if ((null == id) || "".equals(id)) {
                throw new BEMMUserActivityException("Id field cannot be empty. Please provide a valid event id.");
            }

            RuleSession[] sessions = getRuleSessions(ruleServiceProvider, sessionName);
            int line = 0;
            for (int i = 0; i < sessions.length; i++) {
                //only returns the event for agents of type inference
                if (getAgentType(sessions[i]) == CacheAgent.Type.INFERENCE) {
                    final RuleSession session = sessions[i];
                    sessionName = session.getName();
                    Event event = null;
                    try {
                        event = Boolean.parseBoolean(isExternal) ? session.getObjectManager().getEvent(id)
                                                                 : session.getObjectManager().getEvent(Long.parseLong(id));
                    } catch (Exception e) {
                        handleIllegalBeEntityCast(e, event);
                    }
                    if (null != event) {
                        for (Iterator it = this.getEventAttributes(event).iterator(); it.hasNext(); line++) {
                            putEventOrConceptOrScorecardInTableRow(tabularDataHandler, line, sessionName, (EntityHawkAttribute) it.next());
                        }//for
                    } else {

                    }
                } //if
            }//for

            if (tabularDataHandler.getTabularData(INVOKED_METHOD).size() == 0) {
                final String pattern = "No event with ''{0}'' id ''{1}'' found. Please provide a valid event id.";
                String msg = Boolean.parseBoolean(isExternal) ? MessageFormat.format(pattern, "external", id) :
                                                                MessageFormat.format(pattern, "internal", id);
                throw new BEMMEntityNotFoundException(msg);
            }

            return tabularDataHandler.getTabularData(INVOKED_METHOD);
        } catch (NumberFormatException ne) {
            final String msg = "Internal ID provided: \'" + id + "\' is of wrong data type. " +
                                "Internal ID must be an integer number.";
            logger.log(Level.WARN, msg);
            throw new BEMMUserActivityException(msg);
        } catch (BEMMException bemme) {
            logger.log(Level.WARN, bemme.getMessage());
            throw bemme;
        }
        catch (Exception e) {
            logger.log(Level.ERROR, e, e.getMessage());
            throw e;
        } //catch
    } //getEvent

    private void putEventOrConceptOrScorecardInTableRow(MBeanTabularDataHandler tabularDataHandler, int row, String sessionName, EntityHawkAttribute attribute ) {
        Object[] itemValues = new Object[tabularDataHandler.getNumItems()];
        itemValues[0] = row;
        itemValues[1] = sessionName;
        itemValues[2] = EntityHawkAttribute.ATTRIBUTE == attribute.getType() ? "Attribute" : "Property";
        itemValues[3] = attribute.getName();

        String value = attribute.getValue();
        if ((attribute.getType() == EntityHawkAttribute.ATTRIBUTE)
                && EntityHawkAttribute.TYPE.equals(attribute.getName())) {
            final String prefix = BEProperties.getInstance().getString("be.codegen.rootPackage", "be.gen");
            if (value.startsWith(prefix)) {
                value = value.substring(prefix.length() + 1);
            }
        }
        itemValues[4] = value;
        //ads current row to the table
        tabularDataHandler.put(itemValues);
    }  //putEventOrConceptOrScorecardInTableRow

    private List getEventAttributes(Event event) {
        if (event instanceof SimpleEvent) {
            return this.getSimpleEventAttributes((SimpleEvent) event);
        }
        if (event instanceof TimeEvent) {
            return this.getTimeEventAttributes((TimeEvent) event);
        }
        return null;
    }  //getEventAttributes

    private List getSimpleEventAttributes(SimpleEvent simpleEvent) {
        List attrList = new LinkedList();
        EntityHawkAttribute attrib;
        attrib = new EntityHawkAttribute("id", String.valueOf(simpleEvent.getId()), EntityHawkAttribute.ATTRIBUTE);
        attrList.add(attrib);
        attrib = new EntityHawkAttribute(EntityImpl.ATTRIBUTE_EXTID, simpleEvent.getExtId(), EntityHawkAttribute.ATTRIBUTE);
        attrList.add(attrib);
        attrib = new EntityHawkAttribute("type", simpleEvent.getClass().getName(), EntityHawkAttribute.ATTRIBUTE);
        attrList.add(attrib);
        //attrib = new EntityHawkAttribute("status", decodeStatus(), EntityHawkAttribute.ATTRIBUTE);  //todo - add this
        //attrList.add(attrib);
        attrib = new EntityHawkAttribute("ttl", String.valueOf(simpleEvent.getTTL() < 0 ? -1 : simpleEvent.getTTL()), EntityHawkAttribute.ATTRIBUTE);
        attrList.add(attrib);

        //payload
        EventPayload obj = simpleEvent.getPayload();
        if (obj != null) {
            //todo SS- convert to XML?
            attrib = new EntityHawkAttribute("payload", obj.toString(), EntityHawkAttribute.ATTRIBUTE);
            attrList.add(attrib);
        }
//        attrList.add(attrib);
//        EventPayload ep= getPayload();
//        try {
//            if (ep != null) {
//                XiNode xiNode= ep.getNode();
//                Writer writer=new StringWriter();
//                DefaultXmlContentSerializer handler = new DefaultXmlContentSerializer(writer, "UTF-8");
//                xiNode.serialize(handler);
//                attrib = new EntityHawkAttribute("payload", writer.toString(), EntityHawkAttribute.ATTRIBUTE);
//                attrList.add(attrib);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        //properties
        String[] names = simpleEvent.getPropertyNames();
        for (int i = 0; i < names.length; i++) {
            try {
                Object value = simpleEvent.getProperty(names[i]);
                if (value != null) {
                    attrib = new EntityHawkAttribute(names[i], value.toString(), EntityHawkAttribute.PROPERTY);
                    attrList.add(attrib);
                }
            }
            catch (NoSuchFieldException ex) {
                ex.printStackTrace();
            }
        }
        return attrList;
    } //getSimpleEventAttributes

    private List getTimeEventAttributes(TimeEvent timeEvent) {
        List attrList = new LinkedList();
        EntityHawkAttribute attrib;
        attrib = new EntityHawkAttribute("id", String.valueOf(timeEvent.getId()), EntityHawkAttribute.ATTRIBUTE);
        attrList.add(attrib);
        attrib = new EntityHawkAttribute(EntityImpl.ATTRIBUTE_EXTID, timeEvent.getExtId(), EntityHawkAttribute.ATTRIBUTE);
        attrList.add(attrib);
        attrib = new EntityHawkAttribute("type", timeEvent.getClass().getName(), EntityHawkAttribute.ATTRIBUTE);
        attrList.add(attrib);
        //attrib = new EntityHawkAttribute("status", decodeStatus(), EntityHawkAttribute.ATTRIBUTE);  //todo - add this
        //attrList.add(attrib);
        attrib = new EntityHawkAttribute("ttl", String.valueOf(timeEvent.getTTL() < 0 ? -1 : timeEvent.getTTL()), EntityHawkAttribute.ATTRIBUTE);
        attrList.add(attrib);
        attrib = new EntityHawkAttribute("repeating", String.valueOf(timeEvent.isRepeating()), EntityHawkAttribute.ATTRIBUTE);
        attrList.add(attrib);
        if (timeEvent.isRepeating()) { //static time event
            attrib = new EntityHawkAttribute("interval", String.valueOf(timeEvent.getInterval()), EntityHawkAttribute.ATTRIBUTE);
            attrList.add(attrib);
        } else {  //rule based time event
            attrib = new EntityHawkAttribute("scheduleTime", Format.formatCalendar(timeEvent.getScheduledTime()), EntityHawkAttribute.ATTRIBUTE);
            attrList.add(attrib);
            attrib = new EntityHawkAttribute("closure", timeEvent.getClosure(), EntityHawkAttribute.ATTRIBUTE);
            attrList.add(attrib);
        }
        return attrList;
    } //getTimeEventAttributes
    
    public TabularDataSupport GetInstanceByUri(String sessionName, String extId, String uri) throws Exception {

        try {
            sessionName = sessionName.trim();
            extId = extId.trim();
            uri = uri.trim();
            final String INVOKED_METHOD = "getInstanceByUri";
            MBeanTabularDataHandler tabularDataHandler = new MBeanTabularDataHandler(logger);
            tabularDataHandler.setTabularData(INVOKED_METHOD);

            if ((null == extId) || "".equals(extId)) {
                throw new BEMMUserActivityException("Concept ID cannot be empty. Please provide a valid concept id.");
            }
            
            if ((null == uri) || "".equals(uri)) {
                throw new BEMMUserActivityException("URI cannot be empty. Please provide a valid URI.");
            }

            RuleSession[] sessions = getRuleSessions(ruleServiceProvider, sessionName);
            int line = 0;
            for (int i = 0; i < sessions.length; i++) {
                //only return the concept instances for agents of type inference
                if (getAgentType(sessions[i]) == CacheAgent.Type.INFERENCE) {
                    final RuleSession session = sessions[i];
                    sessionName = session.getName();
                    Concept concept=null;
                    try {
                        concept =  (Concept) session.getObjectManager().getElementByUri(extId, uri);
                    } catch (Exception e) {
                        handleIllegalBeEntityCast(e, concept);
                    }
                    if (null != concept) {
                        for (Iterator it = getEntityHawkAttributes(concept).iterator(); it.hasNext(); line++) {
                            putEventOrConceptOrScorecardInTableRow(tabularDataHandler, line, sessionName, (EntityHawkAttribute) it.next());
                        }
                    }
                } 
            }

            if (tabularDataHandler.getTabularData(INVOKED_METHOD).size() == 0) {
                final String pattern = "No concept instance with ''{0}'' id ''{1}'' found. Please provide a valid concept id.";
                String msg =  MessageFormat.format(pattern, "external", extId);
                throw new BEMMEntityNotFoundException(msg);
            }

            return tabularDataHandler.getTabularData(INVOKED_METHOD);
        } catch (BEMMException bemme) {
            logger.log(Level.WARN, bemme.getMessage());
            throw bemme;
        }
        catch (Exception e) {
            logger.log(Level.ERROR, e, e.getMessage());
            throw e;
        } 
    
    	
    }

    public TabularDataSupport GetInstance(String sessionName, String conceptID, String isExternal) throws Exception {
        try {
            sessionName = sessionName.trim();
            conceptID = conceptID.trim();
            isExternal = isExternal.trim();
            final String INVOKED_METHOD = "getInstance";
            MBeanTabularDataHandler tabularDataHandler = new MBeanTabularDataHandler(logger);
            tabularDataHandler.setTabularData(INVOKED_METHOD);

            if ((null == conceptID) || "".equals(conceptID)) {
                throw new BEMMUserActivityException("Concept ID cannot be empty. Please provide a valid concept id.");
            }

            RuleSession[] sessions = getRuleSessions(ruleServiceProvider, sessionName);
            int line = 0;
            for (int i = 0; i < sessions.length; i++) {
                //only return the concept instances for agents of type inference
                if (getAgentType(sessions[i]) == CacheAgent.Type.INFERENCE) {
                    final RuleSession session = sessions[i];
                    sessionName = session.getName();
                    Concept concept=null;
                    try {
                        concept = this.getConcept(conceptID, Boolean.parseBoolean(isExternal), session);
                    } catch (Exception e) {
                        handleIllegalBeEntityCast(e, concept);
                    }
                    if (null != concept) {
                        for (Iterator it = getEntityHawkAttributes(concept).iterator(); it.hasNext(); line++) {
                            putEventOrConceptOrScorecardInTableRow(tabularDataHandler, line, sessionName, (EntityHawkAttribute) it.next());
                        }//for
                    }
                } //if
            }//for

            if (tabularDataHandler.getTabularData(INVOKED_METHOD).size() == 0) {
                final String pattern = "No concept instance with ''{0}'' id ''{1}'' found. Please provide a valid concept id.";
                String msg = Boolean.parseBoolean(isExternal) ? MessageFormat.format(pattern, "external", conceptID) :
                                                                MessageFormat.format(pattern, "internal", conceptID);
                throw new BEMMEntityNotFoundException(msg);
            }

            return tabularDataHandler.getTabularData(INVOKED_METHOD);
        } catch (BEMMException bemme) {
            logger.log(Level.WARN, bemme.getMessage());
            throw bemme;
        }
        catch (Exception e) {
            logger.log(Level.ERROR, e, e.getMessage());
            throw e;
        } //catch
    } //getInstance

    private void handleIllegalBeEntityCast(Exception e, Object instance) throws Exception {
        if ( e instanceof  ClassCastException || e.getCause() instanceof ClassCastException
                || ( e.getCause()!=null && e.getCause().getCause() instanceof ClassCastException) ) {
            //means an entity of type other than concept has been found
            instance = null;
        } else
            throw e;  // don't know the cause so propagate it
    }

    private Concept getConcept(String id, boolean isExternal, RuleSession session) throws Exception {
        if (isExternal) {
            return (Concept) session.getObjectManager().getElement(id);
        } else {
            try {
                return (Concept) session.getObjectManager().getElement(Long.parseLong(id));
            } catch (NumberFormatException e) {
                new BEMMUserActivityException("Internal ID provided: \'" + id + "\' is of wrong data type. " +
                                "Internal ID must be an integer number.");
            }
        }
        return null;
    } //getConcept

    private List getEntityHawkAttributes(Concept concept) {
        final List attrList = new LinkedList();
        attrList.add(new EntityHawkAttribute("id", String.valueOf(concept.getId()), EntityHawkAttribute.ATTRIBUTE));
        attrList.add(new EntityHawkAttribute(EntityImpl.ATTRIBUTE_EXTID, concept.getExtId(), EntityHawkAttribute.ATTRIBUTE));
        attrList.add(new EntityHawkAttribute("type", concept.getClass().getName(), EntityHawkAttribute.ATTRIBUTE));
        //attrList.add(new EntityHawkAttribute("status", decodeStatus(), EntityHawkAttribute.ATTRIBUTE));  //todo - add this

        final Property[] properties = concept.getProperties();
        for (int i = 0; i < properties.length; i++) {
            final Property p = properties[i];
            if(p instanceof PropertyAtom) {
                attrList.add(new EntityHawkAttribute(properties[i].getName(),
                        ((PropertyAtom) properties[i]).getString(), EntityHawkAttribute.PROPERTY));
            } else {  //must be array
                final PropertyArray parr = (PropertyArray) p;
                for(int j =0; j < parr.length(); j++) {
                    attrList.add(new EntityHawkAttribute(parr.getName() + "[" + j +"]",
                            ((PropertyAtom)parr.get(j)).getString(), EntityHawkAttribute.PROPERTY));
                }
            }
        }
        return attrList;
    } //getEntityHawkAttributes

    public TabularDataSupport GetScorecards(String sessionName, String URI) throws Exception {
        try {
            sessionName = sessionName.trim();
            URI = URI.trim();
            String invokedMethod;
            if ((null == URI) || "".equals(URI))
                invokedMethod = "getScorecards";  //no URI provided. Returns a table with of all of the scorecards in every inference session
            else
                invokedMethod = "getScorecard"; //For every inference session, retrieves information of the scorecard with the given URI

            MBeanTabularDataHandler tabularDataHandler = new MBeanTabularDataHandler(logger);
            tabularDataHandler.setTabularData(invokedMethod);

            RuleSession[] sessions = getRuleSessions(ruleServiceProvider, sessionName);
            int line = 0;
            if ( invokedMethod.equals("getScorecard") ) {
                for (int i = 0; i < sessions.length; i++) {
                    //only returns the concept instances for agents of type inference
                    if (getAgentType(sessions[i]) == CacheAgent.Type.INFERENCE) {
                        final RuleSession session = sessions[i];
                        final TypeManager.TypeDescriptor desc =
                                session.getRuleServiceProvider().getTypeManager().getTypeDescriptor(URI);

                        if(desc != null) {
                            Concept concept=null;
                            try {
                                concept = (Concept) session.getObjectManager().getNamedInstance(desc.getURI(), desc.getImplClass());
                            } catch (Exception e) {
                                handleIllegalBeEntityCast(e, concept);
                            }

                            if (null != concept) {
                                for (Iterator it = getEntityHawkAttributes(concept).iterator(); it.hasNext(); line++) {
                                    putEventOrConceptOrScorecardInTableRow(tabularDataHandler, line, session.getName(), (EntityHawkAttribute) it.next());
                                }//for
                            }//if
                        }
                    } //if
                }//for
            } else {   //equals("getScorecards") - returns table with every scorecard in every session
                for (int i = 0; i < sessions.length; i++) {
                    //only return the concept instances for agents of type inference
                    if (getAgentType(sessions[i]) == CacheAgent.Type.INFERENCE) {
                        final RuleSession session = sessions[i];
                        final Collection scorecards = session.getRuleServiceProvider().getTypeManager().getTypeDescriptors(
                                TypeManager.TYPE_NAMEDINSTANCE);
                         for (Iterator it = scorecards.iterator(); it.hasNext(); line++) {
                            final TypeManager.TypeDescriptor desc = (TypeManager.TypeDescriptor) it.next();
                            Concept sc = null;
                            try {
                                sc = (Concept) session.getObjectManager().getNamedInstance(desc.getURI(), desc.getImplClass());
                            } catch (Exception e) {
                                handleIllegalBeEntityCast(e, sc);
                            }

                            if(sc != null)
                                putScorecardInTableRow(tabularDataHandler, line, session.getName(), sc);
                        }//for
                    }//if
                }//for
            } //else

            if (tabularDataHandler.getTabularData(invokedMethod).size() == 0) {
                String pattern;
                String msg;
                if ( invokedMethod.equals("getScorecards") ) {
                    pattern = "No scorecards deployed";

                    if ( !((null == sessionName) || "".equals(sessionName)) )
                        pattern += " for session ''{0}''";

                    msg = MessageFormat.format(pattern, sessionName);
                }
                else { //getScorecard
                    pattern = "No scorecard with URI ''{0}'' found";
                    if ( !((null == sessionName) || "".equals(sessionName)) ) {
                        pattern += " for session ''{1}''. Please provide a valid URI, starting with /";
                    }

                    msg = MessageFormat.format(pattern, URI, sessionName);
                }

                throw new BEMMEntityNotFoundException(msg);
            }

            return tabularDataHandler.getTabularData(invokedMethod);

        } catch (BEMMException bemme) {
            logger.log(Level.WARN, bemme.getMessage());
            throw bemme;
        }
        catch (Exception e) {
            logger.log(Level.ERROR, e, e.getMessage());
            throw e;
        }
    } //getScorecards

    private void putScorecardInTableRow(MBeanTabularDataHandler tabularDataHandler, int row, String sessionName, Concept sc) {
        Object[] itemValues = new Object[tabularDataHandler.getNumItems()];
        itemValues[0] = row;
        itemValues[1] = sessionName;
        itemValues[2] = sc.getId();
        itemValues[3] = sc.getExtId();

        //todo check this still valid in 4.0
        String type = sc.getClass().getName();
        String prefix = ruleServiceProvider.getProperties().getProperty("be.codegen.rootPackage", "be.gen");
        if(type.startsWith(prefix)) {
            type = type.substring(prefix.length() +1);
        }
        itemValues[4] = type;
        //ads current row to the table
        tabularDataHandler.put(itemValues);
    }  //putEventOrConceptOrScorecardInTableRow

}
