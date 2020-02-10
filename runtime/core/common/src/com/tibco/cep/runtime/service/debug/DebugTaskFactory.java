package com.tibco.cep.runtime.service.debug;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.tester.core.ClearWorkingMemoryResponseTaskImpl;
import com.tibco.cep.runtime.service.tester.core.DebugTestDataTaskImpl;
import com.tibco.cep.runtime.service.tester.core.Remote;
import com.tibco.cep.runtime.service.tester.core.TestDataTaskImpl;
import com.tibco.cep.runtime.service.tester.core.WorkingMemoryManipulatorResponseTaskImpl;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.util.PlatformUtil;

public class DebugTaskFactory {

    private static DebugTaskFactory instance;
    public static Logger LOGGER;
    static {
    	if(!PlatformUtil.INSTANCE.isStudioPlatform()) {
    		LOGGER = LogManagerFactory.getLogManager().getLogger(DebugTaskFactory.class);
    	}
    }

    private DebugTaskFactory() {
    }

    public static DebugTaskFactory getInstance() {
        if (instance == null) {
            instance = new DebugTaskFactory();
        }
        return instance;
    }

    /**
     * @param rules
     * @param deploy
     * @throws Exception
     */
    public static void deployRules(String[] rules, boolean deploy ) throws Exception {
        if (DebuggerService.getInstance() != null) {
            DebugTask task = new DeployerTaskImpl(DebuggerService.getInstance().getRuleServiceProvider(), rules,deploy);
            DebuggerService.getInstance().queueTask(task);
        }
    }

    /**
     * @param entityURI
     * @param xmlData
     * @param ruleSessionURI
     * @param destinationURI
     * @param sessionCounter
     * @throws Exception
     */
    public static void newDebugTask(String entityURI,
                                    String xmlData,
                                    String ruleSessionURI,
                                    String destinationURI,
                                    int sessionCounter) throws Exception {
        DebugTask task = new DebugTaskImpl(DebuggerService.getInstance().getRuleServiceProvider(),
                    entityURI,
                    xmlData,
                    ruleSessionURI,
                    destinationURI,
                    sessionCounter);
        DebuggerService.getInstance().queueTask(task);

    }
    
    /**
     * @param entityURI
     * @param xmlData
     * @param destinationURI
     * @param ruleSessionURI
     * @param testerSessionName
     * @param sessionCounter
     * @throws Exception
     */
    public static void newDebugTestDataTask(String[] entityURI,
								    		String[] xmlData,
								    		String[] destinationURI, 
								    		String ruleSessionURI,
								    		String testerSessionName,
								    		int sessionCounter) throws Exception {
    	DebugTestDataTaskImpl task = new DebugTestDataTaskImpl(DebuggerService.getInstance().getRuleServiceProvider(),
												    			ruleSessionURI,
												    			testerSessionName,
												    			xmlData,
												    			entityURI,
												    			destinationURI,
												    			sessionCounter);
    	DebuggerService.getInstance().queueTask(task);
    }



    /**
     * <p>
     * Invoke this method from the client.
     * </p>
     * @param inputData
     * @param ruleSessionName
     * @param testerSessionName
     * @throws Exception
     */
    @Remote
    public static void newTestDataTask(String[] inputData, String ruleSessionName, String testerSessionName) throws Exception {
        DebuggerService debuggerService = DebuggerService.getInstance();
        RuleServiceProvider ruleServiceProvider = debuggerService.getRuleServiceProvider();

        LOGGER.log(Level.INFO, "Create new Test Data Task");
        DebugTask task = new TestDataTaskImpl(ruleServiceProvider,
                                              ruleSessionName,
                                              testerSessionName,
                                              inputData);
        debuggerService.queueTask(task);
    }

    /**
     *
     * @param ruleSessionName
     * @param fetchCount
     * @throws Exception
     */
    @Remote
    public static void newWMContentsTask(String ruleSessionName, int fetchCount) throws Exception {

        DebuggerService debuggerService = DebuggerService.getInstance();

        LOGGER.log(Level.INFO, "Create new WM Contents Task");
        DebugTask wmContentsTask = new WMContentsResponseTaskImpl(ruleSessionName, fetchCount);
        debuggerService.queueTask(wmContentsTask);
    }
    
    public static void newManipulateWMTask(String ruleSessionName, long id, String[] index, String[] properties) throws Exception {

        DebuggerService debuggerService = DebuggerService.getInstance();

        LOGGER.log(Level.INFO, "Create new WM Manipulator Contents Task");
        DebugTask wmContentsTask = new WorkingMemoryManipulatorResponseTaskImpl(ruleSessionName, id, index, properties);
        debuggerService.queueTask(wmContentsTask);
    }
    
    public static void newClearWMContentsTask(String ruleSessionName) throws Exception {
    	
    	DebuggerService debuggerService = DebuggerService.getInstance();
    	
    	 LOGGER.log(Level.INFO, "Create new Clear Working Memory Task");
    	 DebugTask clearWMContentsTask = new ClearWorkingMemoryResponseTaskImpl(ruleSessionName);
    	 debuggerService.queueTask(clearWMContentsTask);

    }
}
