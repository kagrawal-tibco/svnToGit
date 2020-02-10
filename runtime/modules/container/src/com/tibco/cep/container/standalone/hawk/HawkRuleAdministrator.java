package com.tibco.cep.container.standalone.hawk;


import COM.TIBCO.hawk.ami.*;
import com.tibco.be.util.BEProperties;
import com.tibco.cep.container.standalone.hawk.methods.channels.*;
import com.tibco.cep.container.standalone.hawk.methods.engine.*;
import com.tibco.cep.container.standalone.hawk.methods.om.GetCacheRecoveryInfo;
import com.tibco.cep.container.standalone.hawk.methods.om.GetEventMethod;
import com.tibco.cep.container.standalone.hawk.methods.om.GetInstanceMethod;
import com.tibco.cep.container.standalone.hawk.methods.om.GetOMInfoMethod;
import com.tibco.cep.container.standalone.hawk.methods.profiler.StartFileBasedProfiler;
import com.tibco.cep.container.standalone.hawk.methods.profiler.StopFileBasedProfiler;
import com.tibco.cep.container.standalone.hawk.methods.recorder.StartFileBasedRecorder;
import com.tibco.cep.container.standalone.hawk.methods.recorder.StopFileBasedRecorder;
import com.tibco.cep.container.standalone.hawk.methods.rsp.ResumeRuleServiceProvider;
import com.tibco.cep.container.standalone.hawk.methods.rsp.SuspendRuleServiceProvider;
import com.tibco.cep.container.standalone.hawk.methods.scorecards.GetScorecardInfoMethod;
import com.tibco.cep.container.standalone.hawk.methods.scorecards.GetScorecardsMethod;
import com.tibco.cep.container.standalone.hawk.methods.util.ExecuteMethod;
import com.tibco.cep.container.standalone.hawk.methods.wm.*;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.runtime.service.ft.FTNode;
import com.tibco.cep.runtime.session.RuleAdministrator;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.impl.FTAsyncRuleServiceProviderImpl;
import com.tibco.cep.runtime.session.impl.RuleServiceProviderImpl;
import com.tibco.cep.util.ResourceManager;
import com.tibco.sdk.MServiceState;
import com.tibco.tibrv.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;



//TODO get rid of service interface
//TODO states: handle non-FT case
public class HawkRuleAdministrator implements RuleAdministrator {


    public static final String APPLICATION_STATE_UNINITIALIZED = "UNINITIALIZED";
    public static final String APPLICATION_STATE_INITIALIZING = "INITIALIZING";
    public static final String APPLICATION_STATE_RUNNING = "RUNNING";
    public static final String APPLICATION_STATE_STOPPING = "STOPPING";
    public static final String APPLICATION_STATE_STOPPED = "STOPPED";
    public static final String APPLICATION_STATE_STANDBY = "STANDBY";

    public static final String DEFAULT_HAWK_SERVICE = "7474";
    public static final String DEFAULT_HAWK_NETWORK = null;
    public static final String DEFAULT_HAWK_DAEMON = "tcp:7474";
    public static final Object guard = new Object();

    private String m_amiSessionDisplay;
    private String m_amiSessionName;
    private String m_amiSessionHelp = "BE Engine";
    private AmiSession m_amiSession;
    private TibrvQueue m_rvQueue;
    private HawkEngineQueueHandler m_queueHandler;
    private long m_startTime;
    private RuleServiceProvider serviceProvider;
    private BEProperties environment;

    final static Logger logger = LogManagerFactory.getLogManager().getLogger(HawkRuleAdministrator.class);


    public HawkRuleAdministrator(RuleServiceProvider serviceProvider, BEProperties env) {
        this.serviceProvider = serviceProvider;
        this.environment = env;
    }


    protected void destroy() {
        if (m_queueHandler != null) {
            m_queueHandler.stopDispatch();
            if (m_rvQueue != null) {
                m_rvQueue.destroy();  // dispatch() will throw Invalid m_queue exception
                m_rvQueue = null;
            }//if
            m_queueHandler = null;
        }//if
        stopAmiSession();
    }//destroy


    /**
     * @return a String that represents the application State
     * (for example {@link #APPLICATION_STATE_UNINITIALIZED}, {@link #APPLICATION_STATE_INITIALIZING},
     * {@link #APPLICATION_STATE_RUNNING}, etc.
     */
    public String getApplicationState() {
		RuleServiceProvider rsp = this.getServiceProvider();
		if (rsp instanceof RuleServiceProviderImpl) {
			if (((RuleServiceProviderImpl) rsp).isContained()) {
				rsp = ((RuleServiceProviderImpl) rsp).getContainerRsp();
				final FTNode currentNode;
				try {
					currentNode = ((FTAsyncRuleServiceProviderImpl) rsp)
							.getCurrentNode();
				} catch (Exception e) {
					if (null != this.logger) {
						this.logger.log(Level.DEBUG,
                                "Application State set to INITIALIZING upon exception trying to get current node");
						this.logger.log(Level.DEBUG, "Current node will null before joining the cluster");
					}
					return APPLICATION_STATE_INITIALIZING;
				}
				if (null == currentNode) {
					if (null != this.logger) {
						this.logger.log(Level.DEBUG,
                                "Application State set to INITIALIZING upon finding current node is null.");
					}
					return APPLICATION_STATE_INITIALIZING;
				}

				final int nodeState = currentNode.getNodeState();
				switch (nodeState) {
				case FTNode.NODE_SHUTDOWN:
					return APPLICATION_STATE_STOPPING;
				case FTNode.NODE_CREATED:
				case FTNode.NODE_JOINED_CACHE:
					return APPLICATION_STATE_INITIALIZING;
				case FTNode.NODE_INACTIVE:
				case FTNode.NODE_WAIT_FOR_ACTIVATION:
					return APPLICATION_STATE_STANDBY;
				}
			} else {
				switch (((RuleServiceProviderImpl) rsp).getStatus()) {
				case RuleServiceProviderImpl.STATE_PROJECT_INITIALIZED:
				case RuleServiceProviderImpl.STATE_UNINITIALIZED:
					return APPLICATION_STATE_INITIALIZING;
				case RuleServiceProviderImpl.STATE_RUNNING:
					return APPLICATION_STATE_RUNNING;
				}
			}
		}
		return APPLICATION_STATE_RUNNING;
	}// getApplicationState


    /**
	 * @return the Container that contains this BEHawkMicroAgent.
	 */
    public RuleServiceProvider getServiceProvider() {
        return serviceProvider;
    }


    public Logger getLogger() {
        return this.logger;
    }


    // Courtesy of Jean Marie
    private static String getEmbeddedTicket() {
        String a = "wGP";
        StringBuffer buf = new StringBuffer("c3M8");  // What are we achieving
														// here??? Not
														// obfuscation,
														// certainly.
        buf.append('-');
        buf.append('G');
        buf.append("PV");
        buf.append("-Jmu-");
        buf.append("J");
        buf.append(a);
        buf.append("-a");
        buf.append("B5-JW8-sb4J");
        return buf.toString();
    }//getEmbeddedTicket


    public String getSessionName() {
        return m_amiSessionName;
    }


    public long getStartTime() {
        return m_startTime;
    }


    /*
    public void _init() throws Exception {
        new Thread(new Runnable() {
            public void run() {
                synchronized(guard) {
                    try {
                        init();
                    }
                    catch (Exception e) {
                        throw new RuntimeException(e);

                    }
                }
            }
        }).start();

    }//init
    */

    public void init() throws TibrvException, AmiException {
        if (null != m_queueHandler) {
            return; // Already initialized
        }

        final Properties properties = serviceProvider.getProperties();

        m_startTime = System.currentTimeMillis();
        m_amiSessionName = properties.getProperty("Hawk.AMI.DisplayName");
        if ((null == this.m_amiSessionName) || "".equals(this.m_amiSessionName)) {
            this.m_amiSessionName = serviceProvider.getName();
        }
        this.m_amiSessionDisplay = this.m_amiSessionName;

        String strHawkEnable = properties.getProperty("Hawk.Enabled"); //from TRA
        boolean hawkEnable;
        String service;
        String network;
        String daemon;

        if (strHawkEnable == null || strHawkEnable.trim().length() == 0) {
            final GlobalVariables gv = this.serviceProvider.getGlobalVariables();
            if (null == gv) {
                return; // Cannot initialize.
            }
            hawkEnable = gv.getVariableAsBoolean("HawkEnabled", true);
            service = gv.getVariableAsString("TIBHawkService", DEFAULT_HAWK_SERVICE);
            network = gv.getVariableAsString("TIBHawkNetwork", DEFAULT_HAWK_NETWORK);
            daemon = gv.getVariableAsString("TIBHawkDaemon", DEFAULT_HAWK_DAEMON);
        } else {
            hawkEnable = Boolean.valueOf(strHawkEnable).booleanValue();
            service = properties.getProperty("Hawk.Service", DEFAULT_HAWK_SERVICE);
            network = properties.getProperty("Hawk.Network", DEFAULT_HAWK_NETWORK);
            daemon = properties.getProperty("Hawk.Daemon", DEFAULT_HAWK_DAEMON);
        }

        if (hawkEnable) {
            try {
                Tibrv.open(Tibrv.IMPL_NATIVE);
            } catch (TibrvException e) {
                logger.log(Level.ERROR,
                        ResourceManager.getInstance()
                                .formatMessage("hawk.rv.unavailable", TibrvMsg.getStringEncoding()),
                        e);
                throw e;
            }
            logger.log(Level.INFO,
                    ResourceManager.getInstance().formatMessage("hawk.microAgent.params",service, network, daemon,
                            TibrvMsg.getStringEncoding()));

            TibrvTransport transport = new TibrvRvdTransport(service, network, daemon,
                    "TIBCOAdministrator 123704e never " + getEmbeddedTicket());

            m_rvQueue = new TibrvQueue();
            m_queueHandler = new HawkEngineQueueHandler(m_rvQueue);

            m_amiSession = new AmiSession(transport, m_rvQueue, m_amiSessionName, m_amiSessionDisplay, m_amiSessionHelp);
            registerAmiMethods();
            m_amiSession.announce();
        }//if
    }


    protected void registerAmiMethods() throws AmiException {
        for (Iterator it = this.getAmiMethods().iterator(); it.hasNext(); ) {
            this.m_amiSession.addMethod((AmiMethodInterface) it.next());
        }
    }

    protected List getAmiMethods() throws AmiException {
        final List methods = new ArrayList();

        final ExecuteMethod executeMethod = new ExecuteMethod(this);
        executeMethod.register("getJoinTable", new GetJoinTableMethod(this));
        executeMethod.register("printRuleNetwork", new PrintRuleNetworkMethod(this));
        executeMethod.register("workingMemoryDump", new WorkingMemoryDumpMethod(this));
        executeMethod.register("startFileBasedRecorder", new StartFileBasedRecorder(this));
        executeMethod.register("stopFileBasedRecorder", new StopFileBasedRecorder(this));

        // Methods sorted by name for easier use within Administrator's Hawk Console.
        methods.add(new ActivateRuleMethod(this));
        methods.add(new DeactivateRuleMethod(this));
        methods.add(executeMethod);
//        methods.add(new ForceOMCheckpointMethod(this));
        methods.add(new GetChannelsMethod(this));
        methods.add(new GetDestinationsMethod(this));
        methods.add(new GetEventMethod(this));
        methods.add(new GetExecInfoMethod(this));
        methods.add(new GetHostInformationMethod(this));
        methods.add(new GetInstanceMethod(this));
        methods.add(new GetLoggerNamesWithLevels(this));
        methods.add(new GetMemoryUsageMethod(this));
        methods.add(new GetNumberOfEventsMethod(this));
        methods.add(new GetNumberOfInstancesMethod(this));
        methods.add(new GetOMInfoMethod(this));
        methods.add(new GetRulesMethod(this));
        methods.add(new GetRuleMethod(this));
        methods.add(new GetScorecardInfoMethod(this));
        methods.add(new GetScorecardsMethod(this));
        methods.add(new GetSessionInputDestinations(this));
        methods.add(new GetSessionsMethod(this));
        methods.add(new GetStatusMethod(this));
        methods.add(new GetTotalNumberRulesFiredMethod(this));
        methods.add(new GetTraceSinksMethod(this));
        methods.add(new ReconnectChannelsMethod(this));
        methods.add(new ResetTotalNumberRulesFiredMethod(this));
        methods.add(new ResumeChannelsMethod(this));
        methods.add(new ResumeDestinationsMethod(this));
        methods.add(new ResumeRuleServiceProvider(this));
        methods.add(new SetLogLevel(this));
        methods.add(new StopApplicationInstanceMethod(this));
        methods.add(new SuspendChannelsMethod(this));
        methods.add(new SuspendDestinationsMethod(this));
        methods.add(new SuspendRuleServiceProvider(this));
        // Added by Nick and Puneet for Citibank
        methods.add(new GetCacheRecoveryInfo(this));
        // Added for Profiler
        methods.add(new StartFileBasedProfiler(this));
        methods.add(new StopFileBasedProfiler(this));

        return methods;
    }//registerAmiMethods


    /**
     * Informs the Hawk agent that this microagent is stopping.
     * The Hawk agent will immediately delete its entry for this microagent
     * without waiting 30 seconds.
     */
    protected void stopAmiSession() {
        if (m_amiSession != null) {
            try {
                m_amiSession.stop();
            } catch (AmiException e) {
                System.err.println("EngineHawkMicroagent.stopAmiSession: " + e);
            }
            m_amiSession = null;
        }//if
    }//stopAmiSession



    static class HawkEngineQueueHandler extends Thread {
        protected TibrvQueue m_queue;
        protected boolean m_run = true;

        public HawkEngineQueueHandler(TibrvQueue q) {
            m_queue = q;
            setName("HawkEngineQueueHandler");
            start();
        }//constr

        public void run() {
            while (m_run) {
                try {
                    m_queue.dispatch(); //use a blocking timed dispatch as opposed to wait indefinitely
                } catch (Exception e) {
                    break;
                }
            }//while
        }//m_run

        void stopDispatch() {
            m_run = false;
        }

    }//class HawkEngineQueueHandler


    public void start() throws Exception {
    }

    public void shutdown()  {
        destroy();
    }

    public void stop() {
    }


    public void updateState(byte state) {
        if (state == RuleAdministrator.STATE_RUNNING) {
            try {
                MServiceState.set(MServiceState.RUNNING); // Asks the wrapper to update the service manager.
            } catch (Exception ignored) {
            }
        }//if
    }


    public static void main(String[] args) throws Exception {

        final HawkRuleAdministrator hra = new HawkRuleAdministrator(null, null);

        System.err.println("<html><head><title>Hawk Methods in TIBCO BusinessEvents</title>\n" +
                "<style>\n" +
                "html, td, th { font-size: small; } \n" +
                ".one { width: 7em; }\n" +
                ".two { width: 7em; }\n" +
                "h3 { font-size: 1.4em; }\n" +
                "h3, th { text-align: left; font-family: \"helvetica\", \"verdana\", \"arial\", sans-serif; }\n" +
                "table  { border-collapse: collapse; border-spacing: 0; }\n" +
                ".item th, .item td { white-space: nowrap; padding: 0.5em; }\n" +
                ".detail th, .detail td { padding: 0.05em 0.5em; }\n" +
                ".detail td.two { font-family: \"courier new\", \"courier\", monospace; white-space: nowrap; }\n" +
                ".method { margin: 3em 1em; padding: 1.5em 0; border-top: solid 2px #36b; }\n" +
                "</style>\n" +
                "</head><body>\n" +
                "<h1>Hawk Methods in TIBCO BusinessEvents</h1>\n");

        for (Iterator it = hra.getAmiMethods().iterator(); it.hasNext(); ) {
            final AmiMethod method = (AmiMethod) it.next();
            System.err.println("<div class=\"method\">");
            System.err.println("<h3>" + method.getName() + "()</h3>");
            System.err.println("<table>");
            System.err.println("<tr class=\"item\"><th class=\"one\">Purpose</th><td colspan=\"2\" class=\"two\">"
                    + method.getHelp() + "</td></tr>");
            System.err.println("<tr class=\"item\"><th class=\"one\">Type</th><td colspan=\"2\" class=\"two\">Open, Synchronous, IMPACT_"
                    + method.getType() + "</td></tr>");
            System.err.println("<tr class=\"item\"><th class=\"one\">Parameters</th><th class=\"two\">Name</th><th class=\"three\">Description</th></tr>");
            AmiParameterList params = method.getArguments();
            if (null != params) {
                for (Iterator i = params.iterator(); i.hasNext();) {
                    final AmiParameter param = (AmiParameter) i.next();
                    System.err.println("<tr class=\"detail\"><th class=\"one detail\"></th><td class=\"two\">"
                            + param.getName() + "</td><td class=\"three\">" + param.getHelp() + "</td></tr>");
                }
            }
            System.err.println("<tr class=\"item\"><th class=\"one\">Returns</th><th class=\"two\">Name</th><th class=\"three\">Description</th></tr>");
            params = method.getReturns();
            if (null != params) {
                for (Iterator i = params.iterator(); i.hasNext();) {
                    final AmiParameter param = (AmiParameter) i.next();
                    System.err.println("<tr class=\"detail\"><th class=\"one detail\"></th><td class=\"two\">"
                            + param.getName() + "</td><td class=\"three\">" + param.getHelp() + "</td></tr>");
                }
            }
            System.err.println("</table>");
            System.err.println("</div>");
            System.err.println();
        }
        System.err.println("</body ></html>");
    }
}//class
