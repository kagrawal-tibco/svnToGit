package com.tibco.cep.runtime.service.management.agent.ontology;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.model.entity.NamedInstance;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.TimeEvent;
import com.tibco.cep.runtime.service.management.agent.impl.StatsMBeanException;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.util.SystemProperty;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: hlouro
 * Date: 10/15/12
 * Time: 2:03 PM
 */

public class OntologyMBeanFactory {
    //URI must start with a / and have one or more /
    private static final Pattern VALID_URI = Pattern.compile("^[a-zA-Z0-9_/]+$");
    //We assume that the extId does not contain a / in it's name. Although it's possible
    //that the user chooses an extId with a / in its name, it is extremely unlikely.
    //It is currently a limitation, though

    private static final Pattern EXT_ID = Pattern.compile("^[a-zA-Z0-9_:]+$");

    private static final Pattern INVALID_OBJ_NAME_CHAR= Pattern.compile("[:*?,=]");

    private static enum ONTOLOGY_TYPE {SCORECARD, CONCEPT, SIMPLE_EVENT, TIME_EVENT, OTHER}
    private static String OBJ_NAME_PATTERN =
            "com.tibco.be:type=Agent," +
            "agentId=%d,service=Stats," +
            "stat=%s,name=%s";

    private static final Logger logger =
            LogManagerFactory.getLogManager().
                    getLogger(OntologyMBeanFactory.class.getName());

    private static final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

    private RuleServiceProvider rsp;
    private RuleSession session;
    private String uri;
    private int agentId;
    private int count;          //Number of entities registered/unregistered (can be more than one if it is a wildcard or pattern)
    private boolean scorecardsOnly;

    public OntologyMBeanFactory(RuleServiceProvider rsp, RuleSession session, int agentId) {
        this.rsp = rsp;
        this.session = session;
        this.agentId = agentId;
        this.scorecardsOnly = Boolean.valueOf(
                System.getProperty(
                    SystemProperty.MONITOR_SCORECARDS_ONLY.getPropertyName(),
                    "true") );
    }

    public synchronized int registerMbean(String uriStr) throws StatsMBeanException {
        if (isValidUri(uriStr)) {
            init(uriStr);

            doRegistration(getOntolgyObjects());
            //number or ontology entities unregistered
            return count;
        }

        logger.log(Level.DEBUG, "No entities registered for user input uri: %s", uri);
        return 0;
    }

    public synchronized int unRegisterMbean(String uriStr) throws StatsMBeanException {
        if (isValidUri(uriStr)) {
            init(uriStr);

            doUnregistration(getOntolgyObjects());

            //number or ontology entities unregistered
            return count;
        }

        logger.log(Level.DEBUG, "No entities registered for user input uri: %s", uri);
        return 0;
    }

    private void doUnregistration(Object oo)
            throws StatsMBeanException{

        if (oo == null)
            return;

        ObjectName on = null;

        if (oo instanceof Entity) {
            unregister(
                    getObjectName(
                            getOntologyType((Entity)oo),
                            (Entity)oo ) );
        } else {
            for (Entity e : (List<Entity>) oo) {
                try {
                    on = getObjectName(getOntologyType(e),e);
                    unregister(on);
                } catch (Exception e1) {
                    logger.log( Level.DEBUG, "Error unregistering Stats MBean " +
                                "with name %s, for ontology entity %s",
                                on, e.getClass().getName() );
                }
            }
        }
    }

    private void unregister(ObjectName on) throws StatsMBeanException {
        try {
        if (on == null)
            return;

            if (mbs.isRegistered(on)) {
                mbs.unregisterMBean(on);
            }
            count++;
        } catch (Exception e) {
            throw new StatsMBeanException(e, String.format(
               "Exception occurred unregistering Stats MBean: %s", on));
        }
    }


    private void init(String uriStr) {
        count = 0;
        uri = uriStr.trim();
    }

    private boolean isValidUri(String uriStr) {
        if(uriStr == null || uriStr.trim().isEmpty()) {
           return false;
        }

        return true;
    }


    private Object getOntolgyObjects() {
        //using URI the only ontology object that is
        // possible to retrieve is of type Scorecard
        if (VALID_URI.matcher(uri).matches()) {
            return getOntologyObjFromUri();
        } else if (EXT_ID.matcher(uri).matches()) {
            return getOntologyObjFromExtId();
        }
        //It is a pattern or wildcard
        return getOnotologyObjFromPattern();
    }

    private void doRegistration(Object oo) throws StatsMBeanException {
        if (oo == null)
            return;

        if (oo instanceof Entity) {
            doRegistration((Entity) oo);
            return;
        }

        doRegistration((List<Entity>) oo);
    }

    public List<Entity> getOnotologyObjFromPattern() {
        try {
            List allObjs = session.getObjects();
            List<Entity> filteredObjs = new LinkedList<Entity>();

            OntologyFilter filter = new OntologyFilter();

            for (Object obj : allObjs) {
                if (filter.matches(obj)) {
                    filteredObjs.add((Entity)obj);
                }
            }

            return filteredObjs;
        } catch (ClassCastException ce) {
            logger.log(Level.DEBUG, "Some ontology objects are not of type 'Entity'. ", ce); //validate log level
            return null;
        } catch (Exception e) {
            logger.log(Level.DEBUG, "Exception occurred while trying " +
                    "to retrieve the ontology objects matching a pattern", e);
            return null;
        }
    }

    private ObjectName getObjectName(ONTOLOGY_TYPE ot, Entity oo)
            throws StatsMBeanException {

        final String ontlgyType = ot.toString().toLowerCase();
        String entityName = oo.getClass().getName();

        if(entityName == null ||
           entityName.isEmpty()) {
            return null;
        }

        //Format to a valid object name
        entityName = buildValidObjName(entityName, ot, oo);

        try {
            return new ObjectName(String.format(OBJ_NAME_PATTERN,
                                  agentId,
                                  ontlgyType.toLowerCase(),
                                  entityName) );
        } catch (MalformedObjectNameException e) {
            final String m = String.format(
                    "Malformed object name for entity: %s", entityName);
            logger.log(Level.DEBUG, m);
            throw new StatsMBeanException(e, m);
        }
    }

    private String buildValidObjName(String entityName, ONTOLOGY_TYPE ot, Entity oo) {

        //if it is scorecard or time/simple event, add extId
        if(!(ot.compareTo(ONTOLOGY_TYPE.SCORECARD) == 0)) {
            String mbExtId = oo.getExtId();

            mbExtId = mbExtId != null && !mbExtId.trim().isEmpty() ?
                      mbExtId :
                      new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(new Date());

            entityName = new StringBuilder(entityName).
                    append("@").
                    append(mbExtId).
                    toString();
        }

        //Substring at index 7 corresponds to removing be.gen.
        //from the beginning of the entity name
        if (entityName.startsWith("be.gen.")) {
            entityName = entityName.substring(7);
        }


        return entityName.replaceAll(INVALID_OBJ_NAME_CHAR.toString(),".");
    }

    private Entity getOntologyObjFromUri() {
        final TypeManager.TypeDescriptor desc =
                rsp.getTypeManager().getTypeDescriptor(uri);
        if (desc != null) {
            try {
                Entity oo = session.getObjectManager().
                        getNamedInstance(desc.getURI(), desc.getImplClass());
                return oo;
            } catch (Exception e) {
                //The object is not a Concept nor a Scorecard.
                //It's likely an Event. If it is an event, then to be
                //retrieved, the user must provide the extId.
                logger.log(Level.DEBUG, e,
                           "Exception occurred while retrieving " +
                           "ontology object with URI %s", uri);
            }
        }
        return null;
    }


    private Entity getOntologyObjFromExtId() {
        Entity oo;
        try {
            oo = session.getObjectManager().getElement(uri);

            if (oo == null) { //either URI is wrong, or it's an Event
                oo = session.getObjectManager().getEvent(uri);
            }
            return oo;
        } catch (Exception e) {
            logger.log(Level.DEBUG, e,
                       "Exception occurred while retrieving " +
                       "ontology object with ext-id %s" + uri);
            return null;
        }
    }

    private void doRegistration(List<Entity> loo) { //loo - list ontology object
        for (Entity e : loo) {
            try {
                doRegistration(e);
            } catch (Exception e1) {
                logger.log(Level.DEBUG, "Error registering Stats MBean for " +
                           "ontology entity %s", loo.getClass().getName());
            }
        }
    }

    private void doRegistration(Entity oo, ONTOLOGY_TYPE ot)
            throws StatsMBeanException { //oo - ontology object

        ObjectName on = null;
        try {
            if(oo == null || ot == null)
                return;

            on = getObjectName(ot, oo);
            if(on == null)
                return;

            OntologyMBean mb = null;

            switch (ot) {
                case SCORECARD:
                    mb = new ScorecardMBean((Concept)oo, on, logger);
                    break;
                case CONCEPT:
                    mb = new ConceptMBean((Concept)oo, on, logger);
                    break;
                case SIMPLE_EVENT:
                    mb = new SimpleEventMBean((SimpleEvent) oo, on, logger);
                    break;
                case TIME_EVENT:

                    break;

                case OTHER:
                default:
                    return;
            }

            //Do not register if Entity is not of type scorecard and we want to
            //register scorecards only.
            //This code is in here, in addition to the filter, to handle
            //the cases when no patterns are used
            if (scorecardsOnly &&
                ot.compareTo(ONTOLOGY_TYPE.SCORECARD) != 0)
                return;

            if (mb!=null && !mbs.isRegistered(on)) {
                mbs.registerMBean(mb, on);
                count ++;
            }
        } catch (Exception e) {
            final String msg = String.format("Error occurred registering " +
                               "MBean with object name %s, for entity %s",
                                on,
                                oo == null ? null : oo.getClass().getName());

            logger.log(Level.DEBUG, msg);
            throw new StatsMBeanException(e, msg);
        }
    }

    //oo - ontology object
    private void doRegistration(Entity oo)
            throws StatsMBeanException {
        doRegistration(oo, getOntologyType(oo));
    }


    private ONTOLOGY_TYPE getOntologyType(Entity oo) { //oo - ontology object
        if (oo == null)
            return null;

        if (oo instanceof NamedInstance)
            return ONTOLOGY_TYPE.SCORECARD;
        else if (oo instanceof Concept)
            return ONTOLOGY_TYPE.CONCEPT;
        else if (oo instanceof SimpleEvent)
            return ONTOLOGY_TYPE.SIMPLE_EVENT;
        if (oo instanceof TimeEvent)
            return ONTOLOGY_TYPE.TIME_EVENT;
        else
            return ONTOLOGY_TYPE.OTHER;
    }

    //ONTOLOGY_PATTERN
    private enum OP {
        SCORECARD(".scorecard"),
        CONCEPT(".concept"),
        EVENT(".event"),
        SIMPLE_EVENT(".simpleevent"),
        TIME_EVENT(".timeevent")
        ;

        String pattern;

        OP (String pattern) {
            this.pattern = pattern;
        }

        String pattern() {
            return pattern;
        }
    }

    private class OntologyFilter{
        Pattern p;

        private OntologyFilter() {
            try {
                buildValidPattern();
                p = Pattern.compile(uri);
            } catch (Exception e) {
                logger.log(Level.DEBUG, "Error compiling regular expression: %s", uri);
                p = null;
                throw new IllegalArgumentException("Invalid pattern");
            }
        }

        private void buildValidPattern() {
            if (uri.equals("*")) {
                uri = ".*";
            } else {
                for (OP p : OP.values()) {
                    if (uri.equals("*" + p.pattern())) {
                        uri = ".*\\"+p.pattern();
                        break;
                    }
                }
            }
        }

        public boolean matches(Object o) {
            if (o == null) //validate for p
                return false;

            boolean isMatch = false;

            //Attempts to match using Ext-Id for every ontology entity type.
            if (o instanceof Entity) {
                ONTOLOGY_TYPE ot = getOntologyType((Entity)o);

                //Do not register if Entity is not of type
                //scorecard and we want to register scorecards only.
                if (scorecardsOnly &&
                    ot.compareTo(ONTOLOGY_TYPE.SCORECARD) != 0) {
                    return false;
                }

                if ( isMatch(((Entity) o).getExtId()) ) {
                    isMatch = true;
                } else {
                    switch (ot) {
                        //attempts to match using wildcards .scorecard, .concept, .event, ...
                        case SCORECARD:
                            if(isMatch(OP.SCORECARD.pattern()) )
                                isMatch = true;
                        break;

                        case CONCEPT:
                            if (isMatch(OP.CONCEPT.pattern()))
                                isMatch = true;
                        break;

                        case SIMPLE_EVENT:
                            if ( isMatch(OP.SIMPLE_EVENT.pattern()) ||
                                 isMatch(OP.EVENT.pattern()) )
                                isMatch = true;
                        break;

                        case TIME_EVENT:
                            if (isMatch(OP.TIME_EVENT.pattern()))
                                isMatch = true;
                        break;

                        default:
                        break;
                    }
                }
                //try matching by URI or by name only
                if (!isMatch) {
                    StringBuilder scUri = new StringBuilder("/");
                    scUri.append(o.getClass().getName().
                            substring(7).replaceAll("\\.","/"));
                    isMatch = isMatch(scUri);
                }
            }

            return isMatch;
        }

        private boolean isMatch(CharSequence toMatch) {
            return toMatch!=null && p.matcher(toMatch).matches();
        }
    }

}
