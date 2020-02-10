package com.tibco.cep.runtime.service.tester.beunit;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.tibco.be.model.rdf.RDFTnsFlavor;
import com.tibco.be.util.BEPropertiesFactory;
import com.tibco.cep.kernel.core.base.BaseObjectManager;
import com.tibco.cep.kernel.core.rete.ReteWM;
import com.tibco.cep.kernel.model.knowledgebase.DuplicateExtIdException;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.PropertyArrayConceptReference;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.service.debug.DebuggerService;
import com.tibco.cep.runtime.service.decision.impl.VRFImpl;
import com.tibco.cep.runtime.service.loader.BEClassLoader;
import com.tibco.cep.runtime.service.tester.core.TesterRuleServiceProvider;
import com.tibco.cep.runtime.service.tester.core.TesterRun;
import com.tibco.cep.runtime.service.tester.core.TesterSession;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;
import com.tibco.cep.runtime.session.impl.RuleSessionManagerImpl;
import com.tibco.xml.data.primitive.ExpandedName;

/**
 * Base Class for BEUnit Test Suite test cases.
 *
 * @.category public-api
 * @since 5.4.0
 */

public class BETestEngine {
    private static final Logger logger;

	static {
        logger = LogManagerFactory.getLogManager().getLogger(BETestEngine.class);
    }
	
	private RuleServiceProvider provider;
	private RuleServiceProvider testerProvider;
	private RuleSession session;
	private Properties env = new Properties();
	private String agent;

    private TesterRun currentRun;
	private boolean apiMode;
    
    /**
     * Creates a new {@link BETestEngine} and sets the engine
     * properties based on the propertyFile provide.  These properties
     * include:<br>
     * be.project.ear - the path to the project ear being tested<br>
     * be.project.cdd - the path to the .cdd file being used.  This may be absolute or relative to the project ear<br>
     * be.project.puid - the processing unit ID<br>
     * be.project.agent - the agent name or agent key<br>
     * be.project.tra (optional) - the .tra file used to configure the engine
     * @.category public-api
     * @param propertyFile
     * @throws IOException
     * @since 5.4
     */
	public BETestEngine(String propertyFile) throws IOException {

		FileInputStream in = null;
		try {
			in = new FileInputStream(propertyFile);
			Properties tmp = new Properties();
			tmp.load(in);
			in.close();

			env.put("tibco.repourl", tmp.get("be.project.ear"));
			env.put("be.bootstrap.property.file", tmp.get("be.project.tra"));
			env.put("tibco.clientVar.CDD", tmp.get("be.project.cdd"));
			env.put("tibco.clientVar.PUID", tmp.get("be.project.puid"));
			validate(tmp.getProperty("be.project.agent"));
		} finally {
			if (in != null)
				in.close();
		}
	}
	
	/**
	 * Creates a new {@link BETestEngine} and sets the engine properties
	 * based on the parameters provided
	 * @param ear - the path to the project ear being tested<br>
	 * @param tra (optional) - the .tra file used to configure the engine
	 * @param cdd - the path to the .cdd file being used.  This may be absolute or relative to the project ear<br>
	 * @param pu - the processing unit ID<br>
	 * @param agent (optional) - the agent name or agent key.  If unspecified, the first agent found in the cluster configuration will be used<br>
	 * @throws Exception
	 * @since 5.4
	 * @.category public-api
	 */
	public BETestEngine(String ear, String tra, String cdd, String pu, String agent) throws Exception {
		this(ear, tra, cdd, pu, agent, false);
	}

	/**
	 * @deprecated The 'mode' parameter has been replaced by a boolean flag to run in API mode.  Any setting other than mode == RuleServiceProvider.MODE_API will be ignored.
	 * Use {@code BETestEngine(String ear, String tra, String cdd, String pu, String agent, boolean apiMode)} instead
	 * @param ear
	 * @param tra
	 * @param cdd
	 * @param pu
	 * @param agent
	 * @param mode
	 * @throws Exception
	 */
	public BETestEngine(String ear, String tra, String cdd, String pu, String agent, int mode) throws Exception {
		this(ear, tra, cdd, pu, agent, mode == RuleServiceProvider.MODE_API);
	}
	
	/**
	 * Creates a new {@link BETestEngine} and sets the engine properties
	 * based on the parameters provided
	 * @param ear - the path to the project ear being tested<br>
	 * @param tra (optional) - the .tra file used to configure the engine
	 * @param cdd - the path to the .cdd file being used.  This may be absolute or relative to the project ear<br>
	 * @param pu - the processing unit ID<br>
	 * @param agent (optional) - the agent name or agent key.  If unspecified, the first agent found in the cluster configuration will be used<br>
	 * @param apiMode - whether to run the test engine in API mode.  In API mode, the channels in the project will not be started,
	 * and certain catalog functions will not be available.  In many cases, this is the desired behavior while running unit tests, to
	 * prevent external side-effects.  If set to false, the mode of the test engine will be determined by the specified CDD file.
	 * @see {@link RuleServiceProvider.MODE_API}
	 * @throws Exception
	 * @since 5.4
	 * @.category public-api
	 */
	public BETestEngine(String ear, String tra, String cdd, String pu, String agent, boolean apiMode) throws Exception {
		
		String runDir = System.getProperty("user.dir");
		ear = makeAbsolute(runDir, ear);
		tra = makeAbsolute(runDir, tra);
		cdd = makeAbsolute(runDir, cdd);
		this.env.put("tibco.repourl", ear);
		if (tra == null || tra.trim().length() == 0) {
			String be_home = System.getProperty("tibco.env.BE_HOME");
			if (be_home == null || be_home.trim().length() == 0) {
				be_home = System.getProperty("BE_HOME");
				if (be_home != null) {
					tra = be_home+"/bin/be-engine.tra";
				}
			}
		}
		this.env.put("be.bootstrap.property.file", tra);
		env.put("tibco.clientVar.CDD", cdd);
		env.put("tibco.clientVar.PUID", pu);
		// this.env.put("com.tibco.be.config.path", cdd);
		// this.env.put("com.tibco.be.config.unit.id", pu);
		this.apiMode = apiMode;
		validate(agent);
	}

	private boolean validate(String defaultName) {
		try {
			this.agent = BEPropertiesFactory.getInstance().validateProperties(this.env, defaultName);
		} catch (IllegalArgumentException e) {
			logger.log(Level.ERROR, "Unable to configure the test engine with the provided parameters.  [Details: "+e.getLocalizedMessage()+"]");
			throw e;
		}
		return true;
	}

	private String makeAbsolute(String runDir, String path) {
		if (runDir == null || path == null || path.length() == 0) {
			return path;
		}
		if (path.startsWith("..")) {
			File absFile = new File(new File(runDir+"/"+path).toURI().normalize());
			return absFile.getAbsolutePath();
		}
		return path;
	}

	public void appendConceptReference(Concept parent, String propertyArrayName, Concept referenceConcept) {
		this.invokeCatalogFunction("Instance.PropertyArray.appendConceptReference",
				new Object[] { parent.getPropertyArray(propertyArrayName), referenceConcept, 0 });
	}
	
	public void appendConceptReferences(Concept parent, String propertyArrayName, ArrayList<Concept> referenceConcept) {
		PropertyArrayConceptReference ref = (PropertyArrayConceptReference) parent.getPropertyArray(propertyArrayName);
		for (Concept c : referenceConcept) {
			this.invokeCatalogFunction("Instance.PropertyArray.appendConceptReference", new Object[] { ref, c, 0 });
		}
	}

	/**
	 * Asserts a new concept into the current run's working memory
	 * @param c - the Concept to insert
	 * @param executeRules - if true, asserting the Concept will execute any related rules
	 * @throws DuplicateExtIdException - if the external ID of the object to assert already exists in the session.
	 * @.category public-api
	 * @since 5.4
	 */
	public void assertConcept(Concept c, boolean executeRules) throws DuplicateExtIdException {
		try {
			this.currentRun.assertObject(session, (Object) c, executeRules);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Asserts a new scorecard into the current run's working memory
	 * @param sc- the Scorecard to insert
	 * @param executeRules - if true, asserting the Concept will execute any related rules
	 * @.category public-api
	 * @since 5.4
	 */
	public void assertScorecard(Concept sc, boolean executeRules) throws DuplicateExtIdException {
		assertConcept(sc, executeRules);
	}

	/**
	 * Asserts all concepts into the current run's working memory
	 * @param concepts - a List of the Concepts to insert
	 * @param executeRules - if true, asserting the Concepts will execute any related rules
	 * @.category public-api
	 * @since 5.4
	 */
	public void assertConcepts(List<Concept> concepts, boolean executeRules) {
		for (Concept o : concepts) {
			try {
				this.currentRun.assertObject(session, (Object) o, executeRules);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	/**
	 * Asserts Scorecard into the current run's working memory
	 * @param scorecards - a List of the scorecards to insert
	 * @param executeRules - if true, asserting the Concepts will execute any related rules
	 * @.category public-api
	 * @since 5.4
	 */
	public void assertScorecards(List<Concept> scorecards, boolean executeRules) {
		assertConcepts(scorecards, executeRules);
	}

	/**
	 * Asserts a new event into the current run's working memory
	 * @param event - the Event to insert
	 * @param executeRules - if true, asserting the Event will execute any related rules
	 * @param preprocessorUri - if specified, the Rule Function will be called with the <code>event</code> to simulate a preprocessor. 
	 * The specified preprocessorUri's arguments must match the SimpleEvent type
	 * @throws DuplicateExtIdException - if the external ID of the object to assert already exists in the session.
	 * @.category public-api
	 * @since 5.4
	 */
	public void assertEvent(SimpleEvent event, boolean executeRules, String preprocessorUri) throws DuplicateExtIdException {
		try {
			this.currentRun.assertObject(session, (Object) event, executeRules, preprocessorUri);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	/**
	 * Asserts a new event into the current run's working memory
	 * @param event - the Event to insert
	 * @param executeRules - if true, asserting the Event will execute any related rules
	 * @throws DuplicateExtIdException - if the external ID of the object to assert already exists in the session.
	 * @.category public-api
	 * @since 5.4
	 */
	public void assertEvent(SimpleEvent event, boolean executeRules) throws DuplicateExtIdException {
		try {
			this.currentRun.assertObject(session, (Object) event, executeRules);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Asserts all events into the current run's working memory
	 * @param events - a List of the Events to insert
	 * @param executeRules - if true, asserting the Events will execute any related rules
	 * @.category public-api
	 * @since 5.4
	 */
	public void assertEvents(List<SimpleEvent> events, boolean executeRules) {
		events.forEach(o -> {
			try {
				this.currentRun.assertObject(session, (Object) o, executeRules);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}

	/**
	 * Clear the working memory for the current tester rule session.
	 * @.category public-api
	 * @since 5.4
	 */
	public void clearWorkingMemory() {
		((BaseObjectManager)this.session.getObjectManager()).reset();
	}

	/**
	 * Execute the rules in the current rule session.  This is typically used after asserting new
	 * Concepts or Events into working memory.  An {@link Expecter} can then be used to inspect the results
	 * @.category public-api
	 * @since 5.4
	 */
	public void executeRules() {
		this.session.executeRules();
	}

	public Concept getConceptByExtId(String extId) {
		return (Concept) this.session.invokeCatalog("Instance.getByExtId", new Object[] { extId });
	}

	public String getProjectLocation() {
		return this.env.getProperty("tibco.repourl");
	}

	public RuleSession getRuleSession() {
		return this.session;
	}

	/**
     * Invoke a catalog function on the current tester <code>RuleSession</code>.
     *
     * @param functionFQName the fully qualified Catalog function name to be invoked i.e. "System.debugOut"
     * @param args a variable argument specifying the input arguments
     * @return the result of the Catalog function
     * @.category public-api
     *
     * @since 5.4
     */    
	public Object invokeCatalogFunction(String functionFQName, Object... args) {
		return this.session.invokeCatalog(functionFQName, args);
	}

	/**
     * Invoke a RuleFunction on the current tester <code>RuleSession</code>.
     *
     * @param functionURI the URI of the RuleFunction to be invoked
     * @param args an Object array specifies the input arguments
     * @return the result of the RuleFunction
     * @.category public-api
     * @since 5.4
     */    
	public Object invokeRuleFunction(String funcNameUri, Object... args) {
		return this.session.invokeFunction(funcNameUri, args, true);
	}

	/**
	 * Force the invocation of a RuleFunction in the current tester <code>RuleSession</code>.
	 * Generally executing a Rule Function inside of a RuleSession is disallowed due to the
	 * side-effects that can occur.  This method forces the Rule Function to be invoked, which
	 * can be useful to test specific business logic in the unit test.
	 *
	 * @param functionURI the URI of the RuleFunction to be invoked
	 * @param args an Object array specifies the input arguments
	 * @return the result of the RuleFunction
	 * @.category public-api
	 * @since 5.6.1
	 */    
	public Object invokeRuleFunctionInSession(String funcNameUri, Object... args) {
		return ((RuleSessionImpl)this.session).invokeFunction(funcNameUri, args, true, true);
	}
	
	/**
	 * Invoke a specific Decision Table on the current tester <code>RuleSession</code>.
	 *
	 * @param vrfURI the URI of the virtual rule function
	 * @param tableURI the URI of the Decision Table to be invoked
	 * @param args an Object array specifies the input arguments
	 * @return Object - an int[] with the matching row numbers of the decision table
	 * @.category public-api
	 * @since 5.4
	 */    
	public Object invokeDecisionTable(String vrfURI, String tableURI, Object... args) {
        ClassLoader cl = this.session.getRuleServiceProvider().getClassLoader();
        if(cl instanceof BEClassLoader) {
            VRFImpl impl = ((BEClassLoader)cl).getVRFRegistry().getVRFImpl(vrfURI, tableURI);
            if (impl != null) {
            	return impl.invoke(args);
            }
        }

		return null;
	}
	
	/**
	 * Invoke the default Decision Table for the virtual rule function on the current tester <code>RuleSession</code>.
	 *
	 * @param vrfURI the URI of the virtual rule function
	 * @param args an Object array specifies the input arguments
	 * @return Object - an int[] with the matching row numbers of the decision table
	 * @.category public-api
	 * @since 5.4
	 */    
	public Object invokeDecisionTable(String vrfURI, Object... args) {
		ClassLoader cl = this.session.getRuleServiceProvider().getClassLoader();
		if(cl instanceof BEClassLoader) {
			VRFImpl impl = ((BEClassLoader)cl).getVRFRegistry().getVRFDefaultImpl(vrfURI);
			if (impl != null) {
				return impl.invoke(args);
			}
		}
		
		return null;
	}
	
	/**
	 * Reset the current tester rule session to a start state.  This will perform the following actions:<br>
	 * - Clear the working memory<br>
	 * - Reset all timers and clear any scheduled TimeEvents<br>
	 * - Reset all counters (number of rules fired, number of asserted objects, etc.)
	 * @param resetTester if set to 'true', the reset will also clear the following tracked information:<br>
     * - Created entities<br>
     * - Modified entities<br>
     * - Rules fired<br>
     * - Retracted entities<br>
     * - Events sent<br>
     * The result is that any Expecter methods, such as expectRuleFired, will fail immediately after the call to resetSession. 
     * To maintain this information between unit tests, use resetSession(false)
	 * @throws Exception
	 * @.category public-api
	 * @since 6.0
	 */
	public void resetSession(boolean resetTester) throws Exception {
		logger.log(Level.INFO, String.format("resetting rule session %s", this.session.getName()));
		long s = System.currentTimeMillis();
//		this.session.reset(); // timer threads for events do not restart properly (with context) with this call
		((RuleSessionManagerImpl)this.session.getRuleRuntime()).setCurrentRuleSession(this.session);
		((ReteWM)((RuleSessionImpl)this.session).getWorkingMemory()).testerReset();
		
		// always reset the Id and ExtId tables?
//		try {
//			((ObjectTableCache)this.provider.getCluster().getObjectTableCache()).getObjectExtIdTable().clear();
//			((ObjectTableCache)this.provider.getCluster().getObjectTableCache()).getObjectIdTable().clear();
//			Collection<? extends EntityDao> allEntityDao = this.provider.getCluster().getDaoProvider().getAllEntityDao();
//			for (EntityDao entityDao : allEntityDao) {
//				entityDao.clear(null);
//			}
//		} catch (Exception e) {
//			logger.log(Level.ERROR, "Error clearing the object table cache", e);
//		}
		if (resetTester) {
			logger.log(Level.INFO, String.format("clearing tracked information from tester session"));
			this.currentRun.reset();
		}
		logger.log(Level.INFO, String.format("reset rule session took %d", (System.currentTimeMillis() - s)));
        logger.log(Level.INFO, String.format("rule session %s reset", this.session.getName()));
	}
	
	/**
	 * Reset the current tester rule session to a start state.  This will perform the following actions:<br>
	 * - Discard any existing Rete Network information (rules fired, modified concepts, sent events, etc.)<br>
	 * - Clear the working memory<br>
	 * - Reset all timers and clear any scheduled TimeEvents<br>
	 * - Reset all counters (number of rules fired, number of asserted objects, etc.)<br><br>
	 * This is fully equivalent to calling resetSession(true)
	 * @throws Exception
	 * @.category public-api
	 * @since 5.4
	 */
	public void resetSession() throws Exception {
		this.resetSession(true);
	}

	public String serializeConcept(Concept c) {
		return (String) this.session.invokeCatalog("Instance.serializeUsingDefaults", new Object[] { c });
	}
	
	public Concept deleteInstance(Concept c) {
		return (Concept) this.session.invokeCatalog("Instance.deleteInstance", new Object[] { c });
	}
	
	public void evictCacheForConcept(Concept c) {
		ExpandedName expandedName = c.getExpandedName();
		String fullPath = expandedName.namespaceURI.substring(RDFTnsFlavor.BE_NAMESPACE.length());
		String cacheName = (String) this.session.invokeCatalog("Cluster.DataGrid.CacheName", fullPath);
		this.session.invokeCatalog("Cluster.DataGrid.EvictCache", new Object[] { cacheName, null, true });
	}
	
    public void setConceptReference(Concept parent, String propertyName, Concept referenceConcept) {
		this.invokeCatalogFunction("Instance.PropertyAtom.setConceptReference",
				new Object[] { parent.getPropertyAtom(propertyName), referenceConcept, 0 });
	}

    /**
	 * Shut down the test engine and the current rule session
	 * @.category public-api
	 * @since 5.4
	 */
	public void shutdown() {
		logger.log(Level.INFO, String.format("provider %s shutting down", getDisplayName()));
		if (testerProvider != null) {
			testerProvider.shutdown();
			RuleServiceProviderManager.getInstance().removeProvider(testerProvider.getName());
		}
		logger.log(Level.INFO, String.format("provider %s shut down", getDisplayName()));
	}
    
    /**
	 * Create a new {@link RuleServiceProvider} and start the rule session specified by the agent constructor parameter
	 * @.category public-api
	 * @since 5.4
	 */
	public void start() {
    	logger.log(Level.INFO, String.format("BE engine %s initializing", getDisplayName()));

    	logger.log(Level.INFO, String.format("%s -> %s", "tibco.repourl", env.get("tibco.repourl")));

        try {
    		this.provider = RuleServiceProviderManager.getInstance().newProvider(agent, this.env);
    		this.provider.getProperties().setProperty("be.engine.hotDeploy.enabled", "false");
			RuleServiceProviderManager.getInstance().setDefaultProvider(provider);
    		if (apiMode) {
        		this.provider.configure(RuleServiceProvider.MODE_API);
    		} else {
    			if (provider.getProject().isCacheEnabled()) {
    				if (RuleServiceProviderManager.isConfigAsCacheServer(provider.getProperties())) {
    					logger.log(Level.INFO, String.format("configuring rule service provider in MODE_CLUSTER_CACHESERVER"));
    					provider.configure(RuleServiceProvider.MODE_CLUSTER_CACHESERVER);
    				}
    				else {
    					logger.log(Level.INFO, String.format("configuring rule service provider in MODE_CLUSTER"));
    					provider.configure(RuleServiceProvider.MODE_CLUSTER);
    				}
    			} else {
    				logger.log(Level.INFO, String.format("configuring rule service provider in MODE_PRIMARY"));
    				provider.configure(RuleServiceProvider.MODE_PRIMARY);
    			}
    		}
//    		this.session = this.provider.getRuleRuntime().getRuleSession(this.agent);
    		logger.log(Level.INFO, String.format("rule service provider configured"));
            DebuggerService.getInstance().init(null, provider);
//            DebuggerService.getInstance().setRuleServiceProvider(ruleServiceProvider);
            testerProvider = DebuggerService.getInstance().getRuleServiceProvider();
            startRuleSession(this.agent);
        } 
        catch (final Exception e) {
        	logger.log(Level.ERROR, "Error initializing Rule Service Provider", e);
//        	e.printStackTrace();
        }
	}

	private Object getDisplayName() {
		return this.agent;
	}

	private TesterSession registerSession(String sessionName) {
        if (testerProvider instanceof TesterRuleServiceProvider) {
            TesterRuleServiceProvider trsp = (TesterRuleServiceProvider)testerProvider;
            TesterSession testerSession = trsp.registerSession(this.session, sessionName);
            return testerSession;
        }
        return null;
    }

	private void startRuleSession(String ruleSessionName) {
		logger.log(Level.INFO, String.format("starting rule session %s", ruleSessionName));
        this.session = testerProvider.getRuleRuntime().getRuleSession(ruleSessionName);
		try {
	        if (this.session == null) {
	        	this.session = testerProvider.getRuleRuntime().createRuleSession(ruleSessionName);
	        }
			if (this.session != null) {
				currentRun = startRun(ruleSessionName);
			}
//			TesterSession ts = new TesterSession((TesterRuleServiceProvider) testerProvider, ruleSession, ruleSessionName);
//			currentRun = ts.start();
		} catch (Exception e) {
			logger.log(Level.ERROR, e, "rule session not started");
			e.printStackTrace();
		}
		logger.log(Level.INFO, String.format("rule session %s started", ruleSessionName));
	}

	private TesterRun startRun(String sessionName) {
        TesterSession testerSession = registerSession(sessionName);
        if (testerSession != null) {
            TesterRun testerRun = testerSession.start();
            return testerRun;
        }
        return null;
    }

	/*package*/ TesterRun getCurrentRun() {
		return currentRun;
	}
	
	/*package*/ TypeManager getTypeManager() {
		return this.testerProvider.getTypeManager();
	}
	
}
