package com.tibco.be.functions.bw;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.tibco.be.model.functions.FunctionDomain.*;
import com.tibco.be.model.rdf.RDFTnsFlavor;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.service.ft.FTNodeController;
import com.tibco.cep.runtime.session.RuleRuntime;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.session.impl.FTAsyncRuleServiceProviderImpl;
import com.tibco.cep.runtime.session.impl.RuleServiceProviderImpl;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;
import com.tibco.cep.runtime.util.SystemProperty;
import com.tibco.pe.PEMain;
import com.tibco.pe.core.Engine;
import com.tibco.pe.core.JobPool;
import com.tibco.pe.core.JobPoolCreator;
import com.tibco.share.util.Trace;
import com.tibco.tibrv.Tibrv;

/**
 * Created by IntelliJ IDEA.
 * User: root
 * Date: May 16, 2007
 * Time: 11:30:49 AM
 */

public class PEContainer {
	
    static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }
    

	private static PEContainer instance = null;
    private BWNodeController ftBwController = null;
	JobPoolCreator jpServer;


    protected final static Pattern PATTERN_HMA_NAME= Pattern.compile("(COM\\.TIBCO\\.ADAPTER\\.)be-engine(\\..+)");

    private PEMain m_pe;

	private PEContainer() {
		RuleServiceProvider rsp = null;
		// if BW is being used....
		if ((rsp = RuleServiceProviderManager.getInstance()
				.getDefaultProvider()) != null) {
			// and if BE is the container.
			if (rsp instanceof FTAsyncRuleServiceProviderImpl) {
				// and if running in FT mode.
				FTAsyncRuleServiceProviderImpl ftRsp = (FTAsyncRuleServiceProviderImpl) rsp;
				if (ftBwController == null) {
					// then register a callback to the FT layer.
					//ftListener = new BWFtEventNotifier(rsp);
                    ftBwController = new BWNodeController(rsp);
                    ftRsp.getAsyncQueueManager().addNodeController(BWNodeController.class.getName(),ftBwController);
                }
            } else {
            	// non-ft mode, register a "shutdownlistener" so that when BE shuts down, i will be nofified
            	if (rsp instanceof RuleServiceProviderImpl) {
            		RuleServiceProviderImpl rspImpl = (RuleServiceProviderImpl) rsp;
            		try {
            			rspImpl.addShutdownListener(new PEContainerShutdownListener(rspImpl));
            		} catch (Exception e) {
            		}
            	}
            }
		}

		if (m_pe == null) {

			try {
				if (PEMain.isMain || Engine.testMode) {
					//started via BWContainer
					m_pe = (PEMain) BWSupport.getStaticField(PEMain.class,
							PEMain.class);
				} else {
					// started via BE container
					m_pe = newBWMain();
				}
			} catch (Throwable e) {
				RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getLogger(PEContainer.class)
                        .log(Level.ERROR, "Exception when initializing PEContainer: ", e);
			}
		}

	}

	public synchronized static PEContainer getInstance() throws Exception {
		if (instance == null) {
			/*
			 * Tibrv.open is required here. Needs to be started
			 * before starting BW.
			 * 
			 */
			Tibrv.open(Tibrv.IMPL_NATIVE);
			instance = new PEContainer();
		}
		return instance;
	}

	public synchronized static void shutdown() throws Exception {
		if (instance != null) {
			try {
				instance.stop();
				instance = null;
			} catch (Exception e) {
				instance = null;
				throw e;
			}
		}
	}

	@com.tibco.be.model.functions.BEFunction(
        name = "init",
        synopsis = "Initializes the Process Engine.  This is not necessary and is provided as a convenince to start BW before the first process invokation.",
        signature = "void init()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Creates an empty instance using Default Constructor",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
	public synchronized static void init() throws RuntimeException {
		try {
			getInstance();
		} catch (Exception e) {
			RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getLogger(PEContainer.class)
                        .log(Level.ERROR, "Exception when initializing PEContainer: ", e);
			throw new RuntimeException(e);
		}
	}

	@com.tibco.be.model.functions.BEFunction(
        name = "startProcess",
        synopsis = "Starts a BusinessWork process and executes asysnchronous. Upon completion invokes the RuleFunction\nto notify the return values. The BusinessWorks process that needs to be started should be a non-job starter\nand must return an Event in XML - meaning the End Activity's input must be selected from the BusinessEvent\nEvent Schema.",
        signature = "long startProcess(String processName, Object input, String ruleFnURI, Object context)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "processName", type = "String", desc = "The BusinessWorks processName. The process must be in the PAR and must not be a Job Starter"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "input", type = "Object", desc = "The Object could be an Instance or Event or any BusinessEvents primitive, which is mapped one-to-one to the Input BW process. It can also be null"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "ruleFnURI", type = "String", desc = "<li>closure - The closure object that you passed along with the startprocess.</li></ul> </p>"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "context", type = "Object", desc = "as a parameter back to the rule function")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "long", desc = "A successful Job Id, which could be used for query status or cancellation of the Job"),
        version = "2.1.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Starts a BusinessWork process and executes asysnchronous. Upon completion invokes the RuleFunction\nto notify the return values. The BusinessWorks process that needs to be started should be a non-job starter\nand must return an Event in XML - meaning the End Activity's input must be selected from the BusinessEvent\nEvent Schema.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public synchronized static long startProcess(String processName, Object input,
			String ruleFnURI, Object context) throws Exception {
		if (PEMain.isMain || Engine.testMode) // no need to wait for BW if BE is not container
            getInstance();
        else
            waitForBW();
		BEServiceAgent agent = BEServiceAgent.getInstance();
		RuleSessionImpl session = (RuleSessionImpl) RuleSessionManager
				.getCurrentRuleSession();
        RuleFunction ruleFn = null;
        if( null != ruleFnURI) {
            ruleFn = session.getRuleFunction(ruleFnURI);
        }

		long l = agent.startProcess(processName, input, session, ruleFn,
				context, false);  // Suresh says always invoke callback runfunction in async mood.
		return l;
	}

	@com.tibco.be.model.functions.BEFunction(
        name = "invokeProcess",
        synopsis = "Invokes a BusinessWork process and executes sysnchronously and returns a SimpleEvent.\nThe BusinessWorks process that needs to be started should be a non-job starter\nand must return an Event in XML - meaning the End Activity's input must be selected from the BusinessEvent\nEvent Schema. An AdvisoryEvent will be created or asserted should the invoked BusinessWorks process fails\nor times out.",
        signature = "SimpleEvent invokeProcess(String processName, Object inputArg)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "processName", type = "String", desc = "The BusinessWorks processName. The process must be in the PAR and must not be a Job Starter"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "input", type = "Object", desc = "The Object could be an Instance or Event or any BusinessEvents primitive, which is mapped one-to-one to the Input BW process. It can also be null"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "timeout", type = "Long", desc = "The amount of time to wait for the process to complete. 0 means indefinite, and +ve value means that many milliseconds.     *")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "SimpleEvent", desc = "A SimpleEvent as per the process definition's output transformed. If the output is a null, then it is null"),
        version = "2.1.2",
        see = "startProcess",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Invokes a BusinessWork process executes sysnchronous and returns a SimpleEvent. The BusinessWorks process that needs to be started should be a non-job starter\nand must return an Event in XML - meaning the End Activity's input must be selected from the BusinessEvent\nEvent Schema. An AdvisoryEvent will be created or asserted should the invoked BusinessWorks process fails\nor times out.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )

	public synchronized static Event invokeProcess(String processName, Object input,
			long timeout) throws Exception {
		if (PEMain.isMain || Engine.testMode) // no need to wait for BW if BE is not container
            getInstance();
        else
            waitForBW();
		BEServiceAgent agent = BEServiceAgent.getInstance();
		RuleSessionImpl session = (RuleSessionImpl) RuleSessionManager
				.getCurrentRuleSession();
		Event se = agent.invokeProcess(processName, input, session, timeout);
		return se; //Can be advisory or SimpleEvent
	}

	@com.tibco.be.model.functions.BEFunction(
        name = "cancelProcess",
        synopsis = "Cancel a BusinessWork process by this jobId",
        signature = "void cancelProcess(long jobId)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "id", type = "long", desc = "The job identifier returned by the startProcess that needs to be canceled.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "none", desc = ""),
        version = "2.1.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public synchronized static String cancelProcess(long id) throws Exception {
		if (!PEMain.isMain && !Engine.testMode) // Wait for BW only when BE is the container
            waitForBW();
		PEContainer container = getInstance();
		String killMsg =  container.killJob(id);
		RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getLogger(PEContainer.class)
                        .log(Level.DEBUG, killMsg);
		return killMsg;
	}

	private String killJob(long id) throws Exception {
        if (m_pe != null) {
            return m_pe.killJob(id);
        }
        else {
            // TODO: what to do when Engine.testMode == ture?
            return null;
        }
    }

	private PEMain newBWMain() throws Exception {
		// need to do this because BW Engine runs off of a repo dir or
		// DAT file as opposed to an EAR

		RuleSession rs = RuleSessionManager.getCurrentRuleSession();
		RuleRuntime rr = rs.getRuleRuntime();
		RuleServiceProvider rsp = rr.getRuleServiceProvider();
		Properties p = rsp.getProperties();
		Properties bwProps = createBWProperties(p);
        final com.tibco.cep.kernel.service.logging.Logger logger = rsp.getLogger(this.getClass());
		logger.log(Level.INFO, "Loading BW project from: %s", bwProps.getProperty("tibco.repourl"));
		logger.log(Level.INFO, "Starting in-process BW Engine: %s", bwProps.getProperty("name"));
		System.setProperty(RDFTnsFlavor.TNS_CACHE_INIT_CONTEXT, "be-bw");
		PEMain pe = new PEMain(bwProps);
		System.setProperty(RDFTnsFlavor.TNS_CACHE_INIT_CONTEXT, "");
		pe.setQuietMode(false);
		removeBWShutdownHook(pe);
		pe.start();
		return pe;
	}

	// todo: create temp repo dir-- for now require repo dir to be specified
	private static Properties createBWProperties(Properties beProps) {
		final Properties bwProps = new Properties();
        for (Map.Entry e : beProps.entrySet()) {
            if ((e.getKey() instanceof String) && (e.getValue() instanceof String)) {
                bwProps.put(e.getKey(), e.getValue());
            }
        }

		//since BE as well as BW both use Engine.Ft.UseFT parameter to decide if FT is enabled,
		// need to explicitly set this property to false for BW, when BE is the container.
		// cannot have BW FT enabled when BE ft is also enabled
		// (createBWProperties is invoked only when BE is the container)
		bwProps.put("Engine.FT.UseFT", "false");

		final String repoURL = beProps.getProperty(SystemProperty.BW_REPO_URL.getPropertyName());
		if (repoURL != null && !repoURL.trim().equals("")) {
			bwProps.setProperty("tibco.repourl", repoURL);
		}
		String engineName = beProps.getProperty("tibco.bwengine.name");
		if (engineName == null || engineName != null && engineName.equals("")) {
			engineName = RuleServiceProviderManager.getInstance().getDefaultProvider().getName();
		}
		bwProps.setProperty("name", engineName);
		bwProps.setProperty("ServiceAgent.BEServiceAgent.Class",
				BEServiceAgent.class.getName());
		String amiName = bwProps.getProperty("Hawk.AMI.DisplayName");
		if (null != amiName) {
			final Matcher m = PATTERN_HMA_NAME.matcher(amiName);
			if (m.matches()) {
				amiName = m.replaceFirst("$1be-bwengine$2");
			} else {
				amiName += ".bw";
			}
			bwProps.setProperty("Hawk.AMI.DisplayName", amiName);
		}
		setLogger(bwProps);
		return bwProps;
	}

	/**
	 *
	 * @param bwProps
	 * Used when BE is the container. Set BW to use log4j and set its appenders upfront.
	 *
	 */
	private static void setLogger(Properties bwProps) {

		try {
			final boolean log4j = (BWSupport.getFieldByName(Trace.class, "log4j", null) != null);

			if (log4j) {
                BWSupport.setFieldValue(Trace.class, "log4j", null, Boolean.TRUE);
			} else {
                //todo
//				Logger l = RuleServiceProviderManager.getInstance().getDefaultProvider().getLogger(PEContainer.class);
//				String logFilePath = l.getLogFilePath();
//
//				final File logFile = new File(logFilePath).getCanonicalFile();
//				logFilePath = logFile.getPath();
//
//				String logFileName = logFile.getName();
//				logFilePath = logFilePath.substring(0, logFilePath
//						.indexOf(logFileName));
//				//System.out.println("***** logfilepath = " + logFilePath);
//
//				if (logFileName.endsWith(".log")) {
//					logFileName = logFileName.substring(0, logFileName
//							.indexOf(".log"));
//				}
//				//System.out.println("***** logfile = " + logFileName);
//				//bwProps.setProperty("name", logFileName);
//				bwProps.setProperty("Engine.Log.Dir", logFilePath);
//				bwProps.setProperty("Engine.Log.Append", "true");
			}
		} catch (Exception e) {
			RuleServiceProviderManager.getInstance().getDefaultProvider().getLogger(PEContainer.class)
                        .log(Level.DEBUG, "Cannot set BW loggers");
		}
	}

	private static File createTempRepoDir(File repoFole) throws IOException {
		File tmpDir = File.createTempFile("bebwbmpdir", null);
		String pathToDirectory = System.getProperty("java.io.tmpdir");
		return tmpDir;
	}

	public synchronized void stop() {
		if (m_pe != null && !PEMain.isMain) {
			m_pe.destroy();
			m_pe = null;
		}
	}

	/*
	 * When BE is the container, need to remove the shutdownhook registered by BW
	 * If not, then when PEMain.destroy() is eventually invoked during BE shutdown, 
	 * internally, it tries to remove it's shutdownhook, causing an IllegalStateException. 
	 * (can't remove shutdown hook during shutdown)
	 *  
	 */
	private void removeBWShutdownHook(PEMain pe) {

		try {

			JobPoolCreator jpServer = (JobPoolCreator) BWSupport.getField(PEMain.class,
					JobPoolCreator.class, pe);

			JobPool jp = (JobPool)BWSupport.getField(JobPoolCreator.class,
					JobPool.class, jpServer);
			Thread shutdownHook = (Thread)BWSupport.getField(JobPool.class,
					Thread.class, jp);
			if (shutdownHook != null) {
				Runtime.getRuntime().removeShutdownHook(shutdownHook);
				//and set it to null inside the object
				BWSupport.setFieldValue(JobPool.class, Thread.class, jp, null);
			}
			
			
		} catch (Exception e) {
			//can't do much if reflection does not oblige :)
		}
	}

	public static void main(String[] args) {
		try {
			PEContainer container = new PEContainer();
			//container.start(System.getProperties());
			synchronized (container) {
				container.wait();
			}
			container.stop();
		} catch (Exception e) {
			RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getLogger(PEContainer.class)
                        .log(Level.ERROR, "Exception in PEContainer main(): ", e);
		}
	}

	private synchronized static void waitForBW() throws Exception {

		getInstance();
		int atmpt = 0;

		while (instance.jpServer == null) {
			//System.out.println("** Attempt No: " + ++atmpt);
			try {
				instance.jpServer = (JobPoolCreator) BWSupport.getField(PEMain.class,
						JobPoolCreator.class, instance.m_pe);
				if (instance.jpServer != null) {
					break;
				}
				synchronized (instance) {
					instance.wait(1000);
				}

			} catch (Exception e) {
				RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getLogger(PEContainer.class)
                        .log(Level.ERROR, "Exception when waiting for BW starting: ", e);
				return;
			}
		}
		
	}

	/**
	 *
	 * @author bgokhale
	 * This callback is registered only if PE code is encountered. PE code will be encountered upon the first use of
	 * engine is primary. Else, would need an extra (system property?) to tell if BW would be invoked.
	 *
	 */

//	class BWFtEventNotifier implements FTEventNotifier {
//
//		private RuleServiceProvider rsp;
//
//		public BWFtEventNotifier(RuleServiceProvider rsp) {
//			this.rsp = rsp;
//		}
//
//		public void activated() {
//			//this.rsp.getLogger().logDebug("** BW activated **");
//		}
//
//		public void deactivated() {
//			this.rsp.getLogger().logDebug("** Deactivating BW **");
//			try {
//				PEContainer.shutdown();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}

    /**
	 *
	 * @author bgokhale
	 * This callback is registered only if PE code is encountered. PE code will be encountered upon the first use of
	 * engine is primary. Else, would need an extra (system property?) to tell if BW would be invoked.
	 *
	 */
    class BWNodeController implements FTNodeController {
        private RuleServiceProvider rsp;

        public BWNodeController(RuleServiceProvider rsp) {
            this.rsp = rsp;
        }
        public void activateRTC() throws Exception {
        	this.rsp.getLogger(PEContainer.class).log(Level.DEBUG, "BWNodeController:activateRTC");
        }

        public String getControllerName() {
        	this.rsp.getLogger(PEContainer.class).log(Level.DEBUG, "BWNodeController:getControllerName");
            return null;
        }

        public byte[] getNodeArchiveDigest() throws Exception {
        	this.rsp.getLogger(PEContainer.class).log(Level.DEBUG, "BWNodeController:getNodeArchiveDigest");
            return new byte[0];
        }

        public void initAll() throws Exception {
        	this.rsp.getLogger(PEContainer.class).log(Level.DEBUG, "BWNodeController:initAll");
        }

        public void initChannels() throws Exception {
        	this.rsp.getLogger(PEContainer.class).log(Level.DEBUG, "BWNodeController:initChannels");
        }

        public void initRuleSessions() throws Exception {
        	this.rsp.getLogger(PEContainer.class).log(Level.DEBUG, "BWNodeController:initRuleSessions");
        }

        public void nodeStarted() throws Exception {
        	this.rsp.getLogger(PEContainer.class).log(Level.DEBUG, "BWNodeController:nodeStarted");
        }

        public void setInactive() throws Exception {
            this.rsp.getLogger(PEContainer.class).log(Level.DEBUG, "BWNodeController:Stopping BusinessWorks");
            try {
                PEContainer.shutdown();
            } catch (Exception e) {
                this.rsp.getLogger(PEContainer.class).log(Level.ERROR, null, e);
                throw e;
            }
        }

        public void shutdown() throws Exception {
        	this.rsp.getLogger(PEContainer.class).log(Level.DEBUG, "BWNodeController:shutdown");
        }

        public void startChannels() throws Exception {
        	this.rsp.getLogger(PEContainer.class).log(Level.DEBUG, "BWNodeController:startChannels");
        }

        public void stopChannels() throws Exception {
        	this.rsp.getLogger(PEContainer.class).log(Level.DEBUG, "BWNodeController:stopChannels");
        }

        public void stopNode() throws Exception {
        	this.rsp.getLogger(PEContainer.class).log(Level.DEBUG, "BWNodeController:stopNode");
        }

        public void suspendRTC() throws Exception {
        	this.rsp.getLogger(PEContainer.class).log(Level.DEBUG, "BWNodeController:suspendRTC");
        }

        public void waitForActivation() throws Exception {
        	this.rsp.getLogger(PEContainer.class).log(Level.DEBUG, "BWNodeController:waitForActivation");
        }

        public void waitForRuleCycles() throws Exception {
        	this.rsp.getLogger(PEContainer.class).log(Level.DEBUG, "BWNodeController:waitForRuleCycles");
        }

        public void waitBeforeStart() throws Exception {
            this.rsp.getLogger(PEContainer.class).log(Level.DEBUG, "BWNodeController:waitBeforeStart");
        }
    }


    class PEContainerShutdownListener implements RuleServiceProviderImpl.RSPShutdownListener {
		RuleServiceProviderImpl rsp;
		public PEContainerShutdownListener (RuleServiceProviderImpl rsp) {
			this.rsp = rsp;
		}
		public String getName() {
			return "BusinessWorks";
		}
		public void onShutdown() {
			try {
				rsp.getLogger(PEContainer.class).log(Level.INFO, "Shutting down BusinessWorks");
				PEContainer.shutdown();
			} catch (Exception e) {
				rsp.getLogger(PEContainer.class).log(Level.ERROR, "Error while shutting down BusinessWorks");
			}
		}

	}
	/**
	 *
	 * @return BW handle
	 * BWLogger needs a handle to PEMain.
	 */
	public PEMain getPEMain() {
		return this.m_pe;
	}
}
