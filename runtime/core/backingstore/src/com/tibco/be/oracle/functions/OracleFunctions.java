package com.tibco.be.oracle.functions;

import java.lang.reflect.Constructor;
import java.util.HashMap;

import static com.tibco.be.model.functions.FunctionDomain.*;

import com.tibco.be.model.functions.Enabled;
import com.tibco.be.oracle.impl.OracleAdapter;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Oct 23, 2006
 * Time: 3:02:57 PM
 * To change this template use File | Settings | File Templates.
 */


@com.tibco.be.model.functions.BEPackage(
		catalog = "Oracle",
		category = "Oracle",
		enabled = @Enabled(value=false),
		synopsis = "Functions for interaction with Oracle")
public class OracleFunctions {
    static HashMap context = new HashMap();

    /**
     *
     * @return
     */
    @com.tibco.be.model.functions.BEFunction(
    		name = "getClassLoader",
    		synopsis = "Returns the ClassLoader object",
    		signature = "Object getClassLoader()",
    		params={
    		},
    		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name="", type="Object", desc="Object of type ClassLoader"),
    		version = "1.0",
    		see = "",
    		mapper = @com.tibco.be.model.functions.BEMapper(),
    		description = "Returns the ClassLoader Object",
    		cautions = "none",
    		fndomain = {ACTION, CONDITION, QUERY, BUI},
    		example = "")
    
    protected static ClassLoader getClassLoader() {
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        return session.getRuleServiceProvider().getClassLoader();
    }

    /**
     *
     * @param folderPath
     * @param id
     * @param extId
     * @return
     * @throws Exception
     */
    @com.tibco.be.model.functions.BEFunction(
    		name = "newEntity",
    		synopsis = "Returns the Entity object",
    		signature = "Object newEntity(String folderPath, long id, String extId)",
    		params={
    				@com.tibco.be.model.functions.FunctionParamDescriptor(name="folderPath", type="String", desc="Path to the directory"),
    				@com.tibco.be.model.functions.FunctionParamDescriptor(name="id", type="long", desc="Id"),
    				@com.tibco.be.model.functions.FunctionParamDescriptor(name="extId", type="String", desc="extId")
    		},
    		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name="", type="Object", desc="Object of type Entity"),
    		version = "1.0",
    		see = "",
    		mapper = @com.tibco.be.model.functions.BEMapper(),
    		description = "Returns the Entity Object",
    		cautions = "none",
    		fndomain = {ACTION, CONDITION, QUERY, BUI},
    		example = "")
    
    protected static Entity newEntity(String folderPath, long id, String extId) throws Exception{
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        Class entityClz= session.getRuleServiceProvider().getTypeManager().getTypeDescriptor(folderPath).getImplClass();
        Constructor cons = entityClz.getConstructor(new Class[] {long.class, String.class});
        return (com.tibco.cep.kernel.model.entity.Entity) cons.newInstance(new Object[] {new Long(id), extId});
    }

    /**
     *
     * @return
     */
    @com.tibco.be.model.functions.BEFunction(
    		name = "getTypeManager",
    		synopsis = "Returns the TypeManager Object",
    		signature = "Object getTypeManager()",
    		params = {
    		},
    		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name="", type="Object", desc="Object of type TypeManager"),
    		version = "1.0",
    		see = "",
    		mapper = @com.tibco.be.model.functions.BEMapper(),
    		description = "Returns the TypeManager Object",
    		cautions = "none",
    		fndomain = {ACTION, CONDITION, QUERY, BUI},
    		example = "")
    
    protected static TypeManager getTypeManager() {
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        return  session.getRuleServiceProvider().getTypeManager();
    }


    /**
     *
     * @param key
     * @param url
     * @param user
     * @param password
     * @throws RuntimeException
     */
    @com.tibco.be.model.functions.BEFunction(
    		name = "configure",
    		synopsis = "",
    		enabled = @Enabled(value=false),
    		signature = "void configure(String key, String url, String user, String password, String entityTableName)",
    		params={
    				@com.tibco.be.model.functions.FunctionParamDescriptor(name="key", type="String", desc=""),
    				@com.tibco.be.model.functions.FunctionParamDescriptor(name="url", type="String", desc=""),
    				@com.tibco.be.model.functions.FunctionParamDescriptor(name="user", type="String", desc=""),
    				@com.tibco.be.model.functions.FunctionParamDescriptor(name="password", type="String", desc=""),
    				@com.tibco.be.model.functions.FunctionParamDescriptor(name="entityTableName", type="String", desc="")
    		},
    		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name="", type="void", desc=""),
    		version = "1.0",
    		see = "",
    		mapper = @com.tibco.be.model.functions.BEMapper(),
    		description = "",
    		cautions = "none",
    		fndomain = {ACTION, CONDITION, QUERY, BUI},
    		example = "")
    
    public final static void configure (String key, String url, String user, String password, String entityTableName) throws RuntimeException{
        throw new RuntimeException("Oracle Functions should never get called");
        /*

        try {

            // Get the oracle connection


            DriverManager.registerDriver(new OracleDriver());
            //DriverManager.getConnection()
            OracleDataSource ds = new oracle.jdbc.pool.OracleDataSource();
            ds.setURL(url);
            ds.setUser(user);
            ds.setPassword(password);




            OracleConnectionCache.registerConnection(key, ds, 10);
            //ds.setURL("jdbc:oracle:thin:@localhost:1521:ORCL");

            //OracleConnection connection = (OracleConnection) ds.getConnection(user, password);
            //connection.setAutoCommit(false);

            OracleAdapter config = new OracleAdapter(RuleSessionManager.getCurrentRuleSession().getName(),
                    RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider());

            if ((entityTableName != null) && (entityTableName.trim().length() > 0)) {
                config.setEntityTableName(entityTableName);
            }

            RuleSession session = RuleSessionManager.getCurrentRuleSession();
            Ontology ontology= session.getRuleServiceProvider().getProject().getOntology();

            // Add all the concepts
            Iterator allConcepts= ontology.getConcepts().iterator();
            while (allConcepts.hasNext()) {
                Concept cept = (Concept) allConcepts.next();
                config.loadConcept(cept);
            }

            // Add all the events
            Iterator allEvents= ontology.getEvents().iterator();
            while (allEvents.hasNext()) {
                Event evt = (Event) allEvents.next();
                if (evt.getType() == Event.SIMPLE_EVENT) {
                    config.loadEvent(evt);
                }
            }
            context.put(key, config);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        */

    }

    /**
     *
     * @param key
     * @param cept
     * @throws RuntimeException
     */
    @com.tibco.be.model.functions.BEFunction(
    		name = "saveObject",
    		synopsis = "This method inserts and commits the Concept Object.<br/>In case of some failure a roll back is performed.",
    		signature = "void saveObject(String key, Object cept)",
    		params = {
    				@com.tibco.be.model.functions.FunctionParamDescriptor(name="key", type="String", desc="Key to get the OracleAdapter"),
    				@com.tibco.be.model.functions.FunctionParamDescriptor(name="cept", type="Object", desc="Concept Object to be inserted")
    		},
    		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name="", type="void", desc=""),
    		version = "1.0",
    		see = "",
    		mapper = @com.tibco.be.model.functions.BEMapper(),
    		description = "This method inserts and commits the Concept Object being passed<br/>In case of some failure a roll back is performed.",
    		cautions = "none",
    		fndomain = {ACTION, CONDITION, QUERY, BUI},
    		example = "")
    
    public  static void saveObject(String key, com.tibco.cep.runtime.model.element.Concept cept) throws RuntimeException{
        OracleAdapter config = (OracleAdapter) context.get(key);
        try {
            config.insertConcept("all_entities", cept);
            config.commit();
        } catch (Exception e) {
            config.rollback(true);
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param key
     * @param cept
     * @throws RuntimeException
     */
    @com.tibco.be.model.functions.BEFunction(
    		name = "insertObject",
    		synopsis = "This method inserts and commits the Concept Object.<br/>In case of some failure a roll back is performed.",
    		signature = "void insertObject(String key, Object cept)",
    		params = {
    				@com.tibco.be.model.functions.FunctionParamDescriptor(name="key", type="String", desc="Key to get the OracleAdapter"),
    				@com.tibco.be.model.functions.FunctionParamDescriptor(name="cept", type="Object", desc="Concept Object to be inserted")
    		},
    		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name="", type="void", desc=""),
    		version = "1.0",
    		see = "",
    		mapper = @com.tibco.be.model.functions.BEMapper(),
    		description = "This method inserts and commits the Concept Object being passed<br/>In case of some failure a roll back is performed.",
    		cautions = "none",
    		fndomain = {ACTION, CONDITION, QUERY, BUI},
    		example = "")
    
    public  static void insertObject(String key, com.tibco.cep.runtime.model.element.Concept cept) throws RuntimeException{
        OracleAdapter config = (OracleAdapter) context.get(key);
        try {
            config.insertConcept("all_entities", cept);
            config.commit();
        } catch (Exception e) {
            config.rollback(true);
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param key
     * @param cept
     * @throws RuntimeException
     */
    @com.tibco.be.model.functions.BEFunction(
    		name = "updateObject",
    		synopsis = "This method updates and commits the Concept Object.<br/>In case of some failure a roll back is performed.",
    		signature = "void updateObject(String key, Object cept)",
    		params = {
    				@com.tibco.be.model.functions.FunctionParamDescriptor(name="key", type="String", desc="Key to get the OracleAdapter"),
    				@com.tibco.be.model.functions.FunctionParamDescriptor(name="cept", type="Object", desc="Concept Object to be inserted")
    		},
    		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name="", type="void", desc=""),
    		version = "1.0",
    		see = "",
    		mapper = @com.tibco.be.model.functions.BEMapper(),
    		description = "This method updates and commits the Concept Object being passed<br/>In case of some failure a roll back is performed.",
    		cautions = "none",
    		fndomain = {ACTION, CONDITION, QUERY, BUI},
    		example = "")
    
    public  static void updateObject(String key, com.tibco.cep.runtime.model.element.Concept cept) throws RuntimeException{
        OracleAdapter config = (OracleAdapter) context.get(key);
        try {
            config.updateConcept("all_entities", cept);
            config.commit();
        } catch (Exception e) {
            config.rollback(true);
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param key
     * @param cept
     * @throws RuntimeException
     */
    @com.tibco.be.model.functions.BEFunction(
    		name = "deleteObject",
    		synopsis = "This method deletes and commits.<br/>In case of some failure a roll back is performed.",
    		signature = "void deleteObject(String key, Object cept)",
    		params = {
    				@com.tibco.be.model.functions.FunctionParamDescriptor(name="key", type="String", desc="Key to get the OracleAdapter"),
    				@com.tibco.be.model.functions.FunctionParamDescriptor(name="cept", type="Object", desc="Concept Object to be inserted")
    		},
    		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name="", type="void", desc=""),
    		version = "1.0",
    		see = "",
    		mapper = @com.tibco.be.model.functions.BEMapper(),
    		description = "This method deletes and commits.<br/>In case of some failure a roll back is performed.",
    		cautions = "none",
    		fndomain = {ACTION, CONDITION, QUERY, BUI},
    		example = "")
    
    public  static void deleteObject(String key, com.tibco.cep.runtime.model.element.Concept cept) throws RuntimeException{
        OracleAdapter config = (OracleAdapter) context.get(key);
        try {
            config.deleteEntityById("all_entities", cept.getId());
            config.commit();
        } catch (Exception e) {
            config.rollback(true);
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param key
     * @param id
     * @return
     * @throws RuntimeException
     */
    @com.tibco.be.model.functions.BEFunction(
    		name = "getObjectById",
    		synopsis = "This method returns a Concept Object using the id provided.<br/>In case of some failure a roll back is performed.",
    		signature = "Object getObjectById(String key, long id)",
    		params = {
    				@com.tibco.be.model.functions.FunctionParamDescriptor(name="key", type="String", desc="Key to get the OracleAdapter"),
    				@com.tibco.be.model.functions.FunctionParamDescriptor(name="id", type="String", desc="id for the Object to be returned")
    		},
    		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name="", type="Object", desc="Object of type com.tibco.cep.runtime.model.element.Concept"),
    		version = "1.0",
    		see = "",
    		mapper = @com.tibco.be.model.functions.BEMapper(),
    		description = "This method returns a Concept Object using the id provided.<br/>In case of some failure a roll back is performed.",
    		cautions = "none",
    		fndomain = {ACTION, CONDITION, QUERY, BUI},
    		example = "")
    
    public static com.tibco.cep.runtime.model.element.Concept getObjectById(String key, long id) throws RuntimeException{
        OracleAdapter config = (OracleAdapter) context.get(key);
        try {
            return config.getConceptById("all_entities", id);
        } catch (Exception e) {
            config.rollback(true);
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param key
     * @param extId
     * @return
     * @throws RuntimeException
     */
    @com.tibco.be.model.functions.BEFunction(
    		name = "getObjectByExtId",
    		synopsis = "This method returns a Concept Object using the extId provided.<br/>In case of some failure a roll back is performed.",
    		signature = "Object getObjectByExtId(String key, String extId)",
    		params = {
    				@com.tibco.be.model.functions.FunctionParamDescriptor(name="key", type="String", desc="Key to get the OracleAdaper"),
    				@com.tibco.be.model.functions.FunctionParamDescriptor(name="extId", type="String", desc="extId for the Object to be returned")
    		},
    		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name="", type="Object", desc="Object of type com.tibco.cep.runtime.model.element.Concept"),
    		version = "1.0",
    		see = "",
    		mapper = @com.tibco.be.model.functions.BEMapper(),
    		description = "This method returns a Concept Object using the extId provided.<br/>In case of some failure a roll back is performed.",
    		cautions = "none",
    		fndomain = {ACTION, CONDITION, QUERY, BUI},
    		example = "")
    
    public static com.tibco.cep.runtime.model.element.Concept getObjectByExtId(String key, String extId) throws RuntimeException{
        OracleAdapter config = (OracleAdapter) context.get(key);
        try {
            return config.getConceptByExtId("all_entities", extId);
        } catch (Exception e) {
            config.rollback(true);
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param key
     * @param id
     * @return
     * @throws RuntimeException
     */
    @com.tibco.be.model.functions.BEFunction(
    		name = "getEventById",
    		synopsis = "This method returns the Event using the id provided.<br/>In case of some failure roll back is performed.",
    		signature = "Object getEventById(String key, long id)",
    		params = {
    			@com.tibco.be.model.functions.FunctionParamDescriptor(name="key", type="String", desc="The key to get the OracleAdapter"),
    			@com.tibco.be.model.functions.FunctionParamDescriptor(name="id", type="long", desc="The id to get the Event Object")
    		},
    		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name="", type="Object", desc="Object of type com.tibco.cep.kernel.model.entity.Event"),
    		version = "1.0",
    		see = "",
    		mapper = @com.tibco.be.model.functions.BEMapper(),
    		description = "This method returns the Event using the id provided.<br/>In case of some failure roll back is performed.",
    		cautions = "none",
    		fndomain = {ACTION, CONDITION, QUERY, BUI},
    		example = "")
    
    public final static com.tibco.cep.kernel.model.entity.Event getEventById(String key, long id) throws RuntimeException{
        OracleAdapter config = (OracleAdapter) context.get(key);
        try {
            return config.getEventById("all_entities", id);
        } catch (Exception e) {
            config.rollback(true);
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param key
     * @param extId
     * @return
     * @throws RuntimeException
     */
    @com.tibco.be.model.functions.BEFunction(
    		name = "getEventByExtId",
    		synopsis = "This method returns the Event using the extId provided.<br/>In case of some failure roll back is performed.",
    		signature = "Object getEventByExtId(String key, long extId)",
    		params = {
    			@com.tibco.be.model.functions.FunctionParamDescriptor(name="key", type="String", desc="The key to get the OracleAdapter"),
    			@com.tibco.be.model.functions.FunctionParamDescriptor(name="extId", type="long", desc="The extId to get the Event Object")
    		},
    		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name="", type="Object", desc="Object of type com.tibco.cep.kernel.model.entity.Event"),
    		version = "1.0",
    		see = "",
    		mapper = @com.tibco.be.model.functions.BEMapper(),
    		description = "This method returns the Event using the extId provided.<br/>In case of some failure roll back is performed.",
    		cautions = "none",
    		fndomain = {ACTION, CONDITION, QUERY, BUI},
    		example = "")
    
    public final static com.tibco.cep.kernel.model.entity.Event getEventByExtId(String key, String extId) throws RuntimeException{
        OracleAdapter config = (OracleAdapter) context.get(key);
        try {
            return config.getEventByExtId("all_entities", extId);
        } catch (Exception e) {
            config.rollback(true);
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param key
     * @param event
     * @throws RuntimeException
     */
    @com.tibco.be.model.functions.BEFunction(
    		name = "insertEvent",
    		synopsis = "This method inserts Event Object and commits.<br/>In case of some failure roll back is performed.",
    		signature = "void insertEvent(String key, Object event)",
    		params = {
    				@com.tibco.be.model.functions.FunctionParamDescriptor(name="key", type="String", desc="The key to get the OracleAdapter"),
    				@com.tibco.be.model.functions.FunctionParamDescriptor(name="event", type="Object", desc="Object of type com.tibco.cep.kernel.model.entity.Event")
    		},
    		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name="", type="void", desc=""),
    		version = "1.0",
    		see = "",
    		mapper = @com.tibco.be.model.functions.BEMapper(),
    		description = "This method inserts Event Object and commits.<br/>In case of some failure roll back is performed.",
    		cautions = "none",
    		fndomain = {ACTION, CONDITION, QUERY, BUI},
    		example = "")
    
    public final static void insertEvent(String key, com.tibco.cep.kernel.model.entity.Event event) throws RuntimeException {
        OracleAdapter config = (OracleAdapter) context.get(key);
        try {
            config.insertEvent("all_entities", event);
            config.commit();
        } catch (Exception e) {
            config.rollback(true);
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param key
     * @param event
     * @throws RuntimeException
     */
    @com.tibco.be.model.functions.BEFunction(
    		name = "deleteEvent",
    		synopsis = "This method deletes an Event Object using the Event Object provided and commits.<br/>In case of some failure roll back is performed.",
    		signature = "void deleteEvent(String key, Object event)",
    		params = {
    				@com.tibco.be.model.functions.FunctionParamDescriptor(name="key", type="String", desc="The key to get the OracleAdapter"),
    				@com.tibco.be.model.functions.FunctionParamDescriptor(name="event", type="Object", desc="Object of type com.tibco.cep.kernel.model.entity.Event")
    		},
    		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name="", type="void", desc=""),
    		version = "1.0",
    		see = "",
    		mapper = @com.tibco.be.model.functions.BEMapper(),
    		description = "This method deletes an Event Object using the Event Object provided and commits.<br/>In case of some failure roll back is performed.",
    		cautions = "none",
    		fndomain = {ACTION, CONDITION, QUERY, BUI},
    		example = "")
    
    public final static void deleteEvent(String key, com.tibco.cep.kernel.model.entity.Event event) throws RuntimeException {
        OracleAdapter config = (OracleAdapter) context.get(key);
        try {
            config.deleteEntityById("all_entities", event.getId());
            config.commit();
        } catch (Exception e) {
            config.rollback(true);
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param key
     * @throws RuntimeException
     */
    @com.tibco.be.model.functions.BEFunction(
    		name = "purgeAll",
    		synopsis = "This method purges all the records from the OracleAdapter entity obtained using the key provided.<br/>In case of some failure roll back is performed.",
    		signature = "void purgAll(String key)",
    		params = {
    				@com.tibco.be.model.functions.FunctionParamDescriptor(name="key", type="String", desc="The key to get the OracleAdapter")
    		},
    		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name="", type="void", desc=""),
    		version = "1.0",
    		see = "",
    		mapper = @com.tibco.be.model.functions.BEMapper(),
    		description = "",
    		cautions = "none",
    		fndomain = {ACTION, CONDITION, QUERY, BUI},
    		example = "")
    
    public final static void purgeAll(String key) throws RuntimeException {
        OracleAdapter config = (OracleAdapter) context.get(key);
        try {
            config.purgeAll("all_entities");
            config.commit();
        } catch (Exception e) {
            config.rollback(true);
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param key
     * @param predicate
     * @throws RuntimeException
     */
    @com.tibco.be.model.functions.BEFunction(
    		name = "queryAndAssert",
    		synopsis = "This method performs the operation of querying, appends the predicate to the query if not null.<br/>In case of some failure roll back is performed",
    		signature = "void queryAndAssert(String key, String predicate)",
    		params = {
    				@com.tibco.be.model.functions.FunctionParamDescriptor(name="key", type="String", desc="The key to get the OracleAdapter"),
    				@com.tibco.be.model.functions.FunctionParamDescriptor(name="predicate", type="String", desc="The predicate to be appended to the query")
    		},
    		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name="", type="void", desc=""),
    		version = "1.0",
    		see = "",
    		mapper = @com.tibco.be.model.functions.BEMapper(),
    		description = "This method performs the operation of querying, appends the predicate to the query if not null.<br/>In case of some failure roll back is performed",
    		cautions = "none",
    		fndomain = {ACTION, CONDITION, QUERY, BUI},
    		example = "")
    
    public final static void queryAndAssert(String key, String predicate) throws RuntimeException {
        OracleAdapter config = (OracleAdapter) context.get(key);
        try {
            config.queryAndAssert(predicate, RuleSessionManager.getCurrentRuleSession());
            config.commit();
        } catch (Exception e) {
            config.rollback(true);
            throw new RuntimeException(e);
        }
    }
}
