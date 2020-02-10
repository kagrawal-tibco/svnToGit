// (c) Copyright 2001,2002 TIBCO Software Inc.  All rights reserved.
// LEGAL NOTICE:  This source code is provided to specific authorized end
// users pursuant to a separate license agreement.  You MAY NOT use this
// source code if you do not have a separate license from TIBCO Software
// Inc.  Except as expressly set forth in such license agreement, this
// source code, or any portion thereof, may not be used, modified,
// reproduced, transmitted, or distributed in any form or by any means,
// electronic or mechanical, without written permission from  TIBCO
// Software Inc.
package com.tibco.hawk.jshma.plugin;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import COM.TIBCO.hawk.console.hawkeye.AgentID;
import COM.TIBCO.hawk.console.hawkeye.AgentInstance;
import COM.TIBCO.hawk.console.hawkeye.AgentManager;
import COM.TIBCO.hawk.console.hawkeye.AgentMonitor;
import COM.TIBCO.hawk.console.hawkeye.AlertState;
import COM.TIBCO.hawk.console.hawkeye.MicroAgentListMonitorEvent;
import COM.TIBCO.hawk.console.hawkeye.MicroAgentListMonitorListener;
import COM.TIBCO.hawk.console.hawkeye.RuleBaseStatus;
import COM.TIBCO.hawk.console.hawkeye.TIBHawkConsole;
import COM.TIBCO.hawk.console.security.HsConsoleInterface;
import COM.TIBCO.hawk.publisher.AlertFilter;
import COM.TIBCO.hawk.publisher.Publisher;
import COM.TIBCO.hawk.publisher.PublisherAgent;
import COM.TIBCO.hawk.publisher.PublisherAlert;
import COM.TIBCO.hawk.publisher.PublisherCommand;
import COM.TIBCO.hawk.publisher.PublisherListener;
import COM.TIBCO.hawk.talon.CompositeData;
import COM.TIBCO.hawk.talon.CompositeDataDescriptor;
import COM.TIBCO.hawk.talon.DataDescriptor;
import COM.TIBCO.hawk.talon.DataElement;
import COM.TIBCO.hawk.talon.MethodDescriptor;
import COM.TIBCO.hawk.talon.MethodInvocation;
import COM.TIBCO.hawk.talon.MethodSubscription;
import COM.TIBCO.hawk.talon.MicroAgentData;
import COM.TIBCO.hawk.talon.MicroAgentDescriptor;
import COM.TIBCO.hawk.talon.MicroAgentException;
import COM.TIBCO.hawk.talon.MicroAgentID;
import COM.TIBCO.hawk.talon.MicroAgentServer;
import COM.TIBCO.hawk.talon.Subscription;
import COM.TIBCO.hawk.talon.SubscriptionHandler;
import COM.TIBCO.hawk.talon.TabularData;
import COM.TIBCO.hawk.talon.TabularDataDescriptor;
import COM.TIBCO.hawk.utilities.command.Command;
import COM.TIBCO.hawk.utilities.misc.Strtok;
import COM.TIBCO.hawk.utilities.misc.TimeTool;
import COM.TIBCO.hawk.utilities.trace.Trace;

import com.tibco.cep.driver.hawk.HawkConstants;
import com.tibco.cep.driver.hawk.util.HawkUtil;
import com.tibco.hawk.jshma.util.ArrayUtil;
import com.tibco.hawk.jshma.util.ContextControl;
import com.tibco.hawk.jshma.util.Filter;
import com.tibco.hawk.jshma.util.NamedArray;
import com.tibco.hawk.jshma.util.NamedTabularData;
import com.tibco.hawk.jshma.util.Queue;

/**
 * The main class to provide TIBCO Hawk Console access.
 */
public class HawkConsoleBase implements PublisherListener, MicroAgentListMonitorListener, SubscriptionHandler {

	public static final String HAWK_CONSOLE_PROPERTY_SERVICE = "service";
	public static final String HAWK_CONSOLE_PROPERTY_NETWORK = "network";
	public static final String HAWK_CONSOLE_PROPERTY_DAEMON = "daemon";
	public static final String HAWK_CONSOLE_PROPERTY_EMSTRANSPORT = "emsTransport";
	public static final String HAWK_AMI_PROPERTY_SERVICE = "amiService";
	public static final String HAWK_AMI_PROPERTY_NETWORK = "amiNetwork";
	public static final String HAWK_AMI_PROPERTY_DAEMON = "amiDaemon";
	public static final String HAWK_CONSOLE_PROPERTY_SECURITY = "security";
	public static final String HAWK_CONSOLE_PROPERTY_DOMAIN_HOME = "domainHome";
	public static final String HAWK_CONSOLE_PROPERTY_AGENT_NAME = "agentName";
	public static final String HAWK_CONSOLE_PROPERTY_AGENT_HEARTBEAT_INTERVAL = "agentHeartbeatInterval";
	public static final String HAWK_CONSOLE_PROPERTY_CLUSTER_NAME = "cluster";
	public static final String[] s_ems_ssl_transport_parameters = { "ssl_trace", "ssl_debug_trace", "ssl_vendor",
			"ssl_trusted", "ssl_expected_hostname", "ssl_identity", "ssl_identity_encoding", "ssl_private_key",
			"ssl_private_key_encoding", "ssl_password", "ssl_no_verify_hostname", "ssl_no_verify_host",
			"ssl_verify_hostname", "ssl_verify_host", "ssl_ciphers" };

	private static String sClassName = "com.tibco.hawk.jshma.plugin.HawkConsoleBase";
	private static String sHawkConsoleBaseVersion = "(5.1.2.2)";

	private static String TRACE_CATEGORY = "HawkConsoleBase";
	/**
	 * TIBCO Hawk Trace handler
	 */
	protected Trace mTrace = null;
	protected String mDomain = null;

	private boolean mIsDestroyed = false;
	private TIBHawkConsole mConsole = null;
	private Filter[] mAgentANDFilters = null;
	private Filter[] mAgentORFilters = null;
	private String mSecurityPolicy = null;
	private long mConsoleInitTime = System.currentTimeMillis();
	private long mMaxHeartbeatInterval = 33000;

	private Publisher mPublisher = null;
	private Command mCommand = null;
	private AgentMonitor mHawkAgentMonitor = null;
	private AgentManager mHawkAgentManager = null;
	private MicroAgentServer mMicroAgentServer = null;

	private boolean mInfoMethodOnly = false;

	private boolean mEnableMethodInvocationOutputTracing = false;

	// It's AgentManager if from outside, it's MicroAgentServer from inside

	private Hashtable mClusterHashTable = new Hashtable(10, 10);
	// key: cluster Name
	// data: [int[4], Vector of live agentInst, Vector of dead agent]
	// The int[4] has high, medium, low, no alerts

	private Hashtable mAgentHashTable = new Hashtable(50, 50);
	// key: AgentName
	// data: agentInst
	private static final int MA_TABLE_MAX_SIZE = 256;
	private Hashtable mMicroAgentHashTable = new Hashtable(50, 50);
	// key: MA name + MA checksum
	// data: MA descriptor
	// private Hashtable mMASubscriptionTable = new Hashtable( 25, 25 );
	// key: MA sub id
	// data: MA's
	private Hashtable mSubContextTable = new Hashtable(25, 25);
	// key: MA sub id
	// data: [sSubContext, colNames, HawkMethodSubscriber, subsError]
	/**
	 * A Hashtable keeps track of active alerts for each TIBCO Hawk agent. The
	 * key is agnet Name. Data is a Vector of <code>PublisherAlert</code>
	 */
	protected Hashtable mAlertTable = new Hashtable(50, 50);
	// key: AgentName
	// data: alerts
	private static int sSubContext = 0;
	private Vector mMAListenerList = new Vector();
	private Vector mMATrackerList = new Vector();
	private Vector mEventTrackerList = new Vector();

	private ArrayList mPublisherListeners = new ArrayList();
	// Data: ArrayList of listeners
	private ArrayList mMicroAgentListMonitorListeners = new ArrayList();
	// Data: ArrayList of listeners

	private int mMaxAlertPerAgent = 50;
	private int mMaxAlertPurgeInterval = 5000; // 5 seconds
	private long mLastAlertPurgeTime = 0;
	private String mLastAlertPurgeAgentName = null;
	private long mAlertPurgeCount = 0;

	private Timer mAsyncInvokeTimer = null;
	private int mAsyncInvokeTimerTaskCount = 0;

	private static final String defaultTimeFormat = "hh:mm:ss MMM dd, yyyy";
	private static String[] sAllAgentColNames = PluginResources.getResources().getStringArray(
			PluginResources.ALL_AGENT_COL_NAMES);
	private static String[] sClusterColNames = PluginResources.getResources().getStringArray(
			PluginResources.ALL_CLUSTER_COL_NAMES);
	private static String[] sAlertColNames = PluginResources.getResources().getStringArray(
			PluginResources.ALERT_COL_NAMES);
	private static String[] sAgentViewColNames = PluginResources.getResources().getStringArray(
			PluginResources.AGENT_VIEW_COL_NAMES);
	private static String[] sAgentInfoColNames = PluginResources.getResources().getStringArray(
			PluginResources.AGENT_INFO_COL_NAMES);
	private static String[] sMicroAgentViewColNames = PluginResources.getResources().getStringArray(
			PluginResources.MA_VIEW_COL_NAMES);
	private static String[] sMethodViewColNames = PluginResources.getResources().getStringArray(
			PluginResources.METHOD_VIEW_COL_NAMES);

	private static String sNoSourceErr = "NoDataSourceError for Rule ";

	private static String[] sDefaultPublisherFilters = new String[] { "-filter_agent_alive", "ON",
			"-filter_agent_dead", "ON", "-filter_high_alerts", "ON", "-filter_medium_alerts", "ON",
			"-filter_low_alerts", "ON", "-filter_information", "ON", "-filter_rulebase", "ALL", "-filter_host_name",
			"ALL", "-filter_dns_name", "ALL" };

	private static String[] sNoAlertPublisherFilters = new String[] { "-filter_agent_alive", "OFF",
			"-filter_agent_dead", "OFF", "-filter_high_alerts", "OFF", "-filter_medium_alerts", "OFF",
			"-filter_low_alerts", "OFF", "-filter_information", "OFF", "-filter_rulebase", "", "-filter_host_name", "",
			"-filter_dns_name", "" };

	/*
	 * public HawkConsoleBase( String[] inArgs) throws Throwable { this(inArgs,
	 * true); }
	 */

	/**
	 * Dummy constructor. For internal use only.
	 */
	protected HawkConsoleBase() {
	};

	/**
	 * Instantiate TIBCO Hawk Console for all machines on the network without
	 * TIBCO Hawk security policy.
	 * 
	 * @param service
	 *            TIBCO RV service paramter
	 * @param network
	 *            TIBCO RV network paramter
	 * @param daemon
	 *            TIBCO RV daemon paramter
	 * @param hawkDomain
	 *            TIBCO Hawk domain parameter
	 * @param trackAlerts
	 *            indicates whether TIBCO Hawk alerts would be tracked. It
	 *            should be set set to <code>false</code> if the user program
	 *            has no need to monitor TIBCO Hawk alerts.
	 * @throws Exception
	 */
	public HawkConsoleBase(String service, String network, String daemon, String hawkDomain, boolean trackAlerts)
			throws Exception {
		this(service, network, daemon, hawkDomain, trackAlerts, (Filter[]) null, null);
	}

	/**
	 * Instantiate TIBCO Hawk Console for all machines on the network with
	 * specified TIBCO Hawk security policy.
	 * 
	 * @param service
	 *            TIBCO RV service paramter
	 * @param network
	 *            TIBCO RV network paramter
	 * @param daemon
	 *            TIBCO RV daemon paramter
	 * @param hawkDomain
	 *            TIBCO Hawk domain parameter
	 * @param securityPolicy
	 *            TIBCO Hawk security policy paramter
	 * @param trackAlerts
	 *            indicates whether TIBCO Hawk alerts would be tracked. It
	 *            should be set set to <code>false</code> if the user program
	 *            has no need to monitor TIBCO Hawk alerts.
	 * @throws Exception
	 */
	public HawkConsoleBase(String service, String network, String daemon, String hawkDomain, String securityPolicy,
			boolean trackAlerts) throws Exception {
		this(service, network, daemon, hawkDomain, trackAlerts, (Filter[]) null, securityPolicy);
	}

	/**
	 * Instantiate TIBCO Hawk Console for the specified machine without TIBCO
	 * Hawk security policy.
	 * 
	 * @param service
	 *            TIBCO RV service paramter
	 * @param network
	 *            TIBCO RV network paramter
	 * @param daemon
	 *            TIBCO RV daemon paramter
	 * @param hawkDomain
	 *            TIBCO Hawk domain parameter
	 * @param agentName
	 *            The TIBCO Hawk agent name (normally it's the same as the
	 *            machine host name) of the machine to be monitored
	 * @param trackAlerts
	 *            indicates whether TIBCO Hawk alerts would be tracked. It
	 *            should be set set to <code>false</code> if the user program
	 *            has no need to monitor TIBCO Hawk alerts.
	 * @throws Exception
	 */
	public HawkConsoleBase(String service, String network, String daemon, String hawkDomain, boolean trackAlerts,
			String agentName) throws Exception {
		this(service, network, daemon, hawkDomain, trackAlerts,
				new Filter[] { new Filter("AgentName", "is", agentName) }, null);
	}

	/**
	 * Instantiate TIBCO Hawk Console for all machines specified by the "OR"
	 * Filters with specified TIBCO Hawk security policy.
	 * 
	 * @param service
	 *            TIBCO RV service paramter
	 * @param network
	 *            TIBCO RV network paramter
	 * @param daemon
	 *            TIBCO RV daemon paramter
	 * @param hawkDomain
	 *            TIBCO Hawk domain parameter
	 * @param trackAlerts
	 *            indicates whether TIBCO Hawk alerts would be tracked. It
	 *            should be set set to <code>false</code> if the user program
	 *            has no need to monitor TIBCO Hawk alerts.
	 * @param agentORFilters
	 *            The TIBCO Hawk agent name "OR" Filters It allows users to
	 *            specify multiple machines to be monitored.
	 * @param securityPolicy
	 *            TIBCO Hawk security policy paramter
	 * @throws Exception
	 * @see Filter
	 */
	public HawkConsoleBase(String service, String network, String daemon, String hawkDomain, boolean trackAlerts,
			Filter[] agentORFilters, String securityPolicy) throws Exception {
		HawkConsoleBaseInit(service, network, daemon, hawkDomain, trackAlerts, null, agentORFilters, securityPolicy);
	}

	/**
	 * Instantiate TIBCO Hawk Console for all machines on the network without
	 * TIBCO Hawk security policy.
	 * 
	 * @param transportEnv
	 *            transport env parameter HashMap The keys include those
	 *            constants from com.tibco.pof.util.SystemEnvironment
	 *            HAWK_CONSOLE_PROPERTY_SERVICE, HAWK_CONSOLE_PROPERTY_NETWORK,
	 *            HAWK_CONSOLE_PROPERTY_DAEMON,
	 *            HAWK_CONSOLE_PROPERTY_EMSTRANSPORT,
	 *            HAWK_CONSOLE_PROPERTY_SECURITY,
	 *            HAWK_CONSOLE_PROPERTY_DOMAIN_HOME, HAWK_AMI_PROPERTY_SERVICE,
	 *            HAWK_AMI_PROPERTY_NETWORK, HAWK_AMI_PROPERTY_DAEMON,
	 *            HAWK_CONSOLE_PROPERTY_AGENT_NAME,
	 *            HAWK_CONSOLE_PROPERTY_AGENT_HEARTBEAT_INTERVAL
	 *            HAWK_CONSOLE_PROPERTY_CLUSTER_NAME, and the parameters in
	 *            HawkConsoleBase.s_ems_ssl_transport_parameters
	 * @param hawkDomain
	 *            TIBCO Hawk domain parameter
	 * @param trackAlerts
	 *            indicates whether TIBCO Hawk alerts would be tracked. It
	 *            should be set set to <code>false</code> if the user program
	 *            has no need to monitor TIBCO Hawk alerts.
	 * @throws Exception
	 */
	public HawkConsoleBase(HashMap transportEnv, String hawkDomain, boolean trackAlerts) throws Exception {
		this(transportEnv, hawkDomain, trackAlerts, (Filter[]) null, (String) transportEnv
				.get(HAWK_CONSOLE_PROPERTY_SECURITY));
	}

	/**
	 * Instantiate TIBCO Hawk Console for all machines on the network with
	 * specified TIBCO Hawk security policy.
	 * 
	 * @param transportEnv
	 *            transport env parameter HashMap The keys include those
	 *            constants from com.tibco.pof.util.SystemEnvironment
	 *            HAWK_CONSOLE_PROPERTY_SERVICE, HAWK_CONSOLE_PROPERTY_NETWORK,
	 *            HAWK_CONSOLE_PROPERTY_DAEMON,
	 *            HAWK_CONSOLE_PROPERTY_EMSTRANSPORT,
	 *            HAWK_CONSOLE_PROPERTY_SECURITY,
	 *            HAWK_CONSOLE_PROPERTY_DOMAIN_HOME, HAWK_AMI_PROPERTY_SERVICE,
	 *            HAWK_AMI_PROPERTY_NETWORK, HAWK_AMI_PROPERTY_DAEMON,
	 *            HAWK_CONSOLE_PROPERTY_AGENT_NAME,
	 *            HAWK_CONSOLE_PROPERTY_CLUSTER_NAME, and the parameters in
	 *            HawkConsoleBase.s_ems_ssl_transport_parameters
	 * @param hawkDomain
	 *            TIBCO Hawk domain parameter
	 * @param securityPolicy
	 *            TIBCO Hawk security policy paramter
	 * @param trackAlerts
	 *            indicates whether TIBCO Hawk alerts would be tracked. It
	 *            should be set set to <code>false</code> if the user program
	 *            has no need to monitor TIBCO Hawk alerts.
	 * @throws Exception
	 */
	public HawkConsoleBase(HashMap transportEnv, String hawkDomain, String securityPolicy, boolean trackAlerts)
			throws Exception {
		this(transportEnv, hawkDomain, trackAlerts, (Filter[]) null, securityPolicy);
	}

	/**
	 * Instantiate TIBCO Hawk Console for the specified machine without TIBCO
	 * Hawk security policy.
	 * 
	 * @param transportEnv
	 *            transport env parameter HashMap The keys include those
	 *            constants from com.tibco.pof.util.SystemEnvironment
	 *            HAWK_CONSOLE_PROPERTY_SERVICE, HAWK_CONSOLE_PROPERTY_NETWORK,
	 *            HAWK_CONSOLE_PROPERTY_DAEMON,
	 *            HAWK_CONSOLE_PROPERTY_EMSTRANSPORT,
	 *            HAWK_CONSOLE_PROPERTY_SECURITY,
	 *            HAWK_CONSOLE_PROPERTY_DOMAIN_HOME, HAWK_AMI_PROPERTY_SERVICE,
	 *            HAWK_AMI_PROPERTY_NETWORK, HAWK_AMI_PROPERTY_DAEMON,
	 *            HAWK_CONSOLE_PROPERTY_AGENT_NAME,
	 *            HAWK_CONSOLE_PROPERTY_CLUSTER_NAME, and the parameters in
	 *            HawkConsoleBase.s_ems_ssl_transport_parameters
	 * @param hawkDomain
	 *            TIBCO Hawk domain parameter
	 * @param agentName
	 *            The TIBCO Hawk agent name (normally it's the same as the
	 *            machine host name) of the machine to be monitored
	 * @param trackAlerts
	 *            indicates whether TIBCO Hawk alerts would be tracked. It
	 *            should be set set to <code>false</code> if the user program
	 *            has no need to monitor TIBCO Hawk alerts.
	 * @throws Exception
	 */
	public HawkConsoleBase(HashMap transportEnv, String hawkDomain, boolean trackAlerts, String agentName)
			throws Exception {
		this(transportEnv, hawkDomain, trackAlerts, new Filter[] { new Filter("AgentName", "is", agentName) },
				(String) transportEnv.get(HAWK_CONSOLE_PROPERTY_SECURITY));
	}

	/**
	 * Instantiate TIBCO Hawk Console for all machines specified by the "OR"
	 * Filters with specified TIBCO Hawk security policy.
	 * 
	 * @param transportEnv
	 *            transport env parameter HashMap The keys include those
	 *            constants from com.tibco.pof.util.SystemEnvironment
	 *            HAWK_CONSOLE_PROPERTY_SERVICE, HAWK_CONSOLE_PROPERTY_NETWORK,
	 *            HAWK_CONSOLE_PROPERTY_DAEMON,
	 *            HAWK_CONSOLE_PROPERTY_EMSTRANSPORT,
	 *            HAWK_CONSOLE_PROPERTY_SECURITY,
	 *            HAWK_CONSOLE_PROPERTY_DOMAIN_HOME, HAWK_AMI_PROPERTY_SERVICE,
	 *            HAWK_AMI_PROPERTY_NETWORK, HAWK_AMI_PROPERTY_DAEMON,
	 *            HAWK_CONSOLE_PROPERTY_AGENT_NAME,
	 *            HAWK_CONSOLE_PROPERTY_CLUSTER_NAME, and the parameters in
	 *            HawkConsoleBase.s_ems_ssl_transport_parameters
	 * @param hawkDomain
	 *            TIBCO Hawk domain parameter
	 * @param trackAlerts
	 *            indicates whether TIBCO Hawk alerts would be tracked. It
	 *            should be set set to <code>false</code> if the user program
	 *            has no need to monitor TIBCO Hawk alerts.
	 * @param agentORFilters
	 *            The TIBCO Hawk agent name "OR" Filters It allows users to
	 *            specify multiple machines to be monitored.
	 * @param securityPolicy
	 *            TIBCO Hawk security policy paramter
	 * @throws Exception
	 * @see Filter
	 */
	public HawkConsoleBase(HashMap transportEnv, String hawkDomain, boolean trackAlerts, Filter[] agentORFilters,
			String securityPolicy) throws Exception {
		HawkConsoleBaseInit(transportEnv, hawkDomain, trackAlerts, null, agentORFilters, securityPolicy);
	}

	/**
	 * Instantiate TIBCO Hawk Console inside a TIBCO Hawk agent - This is for
	 * internal use only.
	 * 
	 * @param microAgentServer
	 *            TIBCO Hawk MicroAgentServer
	 */

	public HawkConsoleBase(MicroAgentServer microAgentServer) {
		mMicroAgentServer = (MicroAgentServer) microAgentServer;

		mClusterHashTable = null;
		mAgentHashTable = null;
		mAlertTable = null;
		mEventTrackerList = null;

		mTrace = ContextControl.getTrace();

	}

	/**
	 * Init TIBCO Hawk Console. This is for internal use only.
	 */

	void HawkConsoleBaseInit(String service, String network, String daemon, String hawkDomain, boolean trackAlerts,
			Filter[] agentANDFilters, Filter[] agentORFilters, String securityPolicy) throws Exception {

		HashMap transportEnv = new HashMap();
		transportEnv.put(HAWK_CONSOLE_PROPERTY_SERVICE, service);
		transportEnv.put(HAWK_CONSOLE_PROPERTY_NETWORK, network);
		transportEnv.put(HAWK_CONSOLE_PROPERTY_DAEMON, daemon);

		HawkConsoleBaseInit(transportEnv, hawkDomain, trackAlerts, agentANDFilters, agentORFilters, securityPolicy);

	}

	private static String stripQuoteChars(String s) {
		if (s != null && s.startsWith("\""))
			return s.substring(1, s.length() - 1);
		else
			return s;
	}

	/**
	 * Init TIBCO Hawk Console. This is for internal use only.
	 */

	void HawkConsoleBaseInit(HashMap transportEnv, String hawkDomain, boolean trackAlerts, Filter[] agentANDFilters,
			Filter[] agentORFilters, String securityPolicy) throws Exception {

		if (System.getProperty("EnableJshmaDebug") != null) {
			mTrace = new Trace();
			ContextControl.setTrace(mTrace);
			ContextControl.setTraceLevel(-1);
		}

		mAgentANDFilters = agentANDFilters;
		mAgentORFilters = agentORFilters;

		/*
		 * String s = System.getProperty("hawk.hawk_root"); if (s == null) {
		 * throw new
		 * Exception("System property 'hawk.hawk_root' is not defined."); } if
		 * (s != null) s = s + File.separator + "bin";
		 */

		String service = (String) transportEnv.get(HAWK_CONSOLE_PROPERTY_SERVICE);
		String network = (String) transportEnv.get(HAWK_CONSOLE_PROPERTY_NETWORK);
		String daemon = (String) transportEnv.get(HAWK_CONSOLE_PROPERTY_DAEMON);
		String emsTransport = (String) transportEnv.get(HAWK_CONSOLE_PROPERTY_EMSTRANSPORT);

		String agentHeartBeat = (String) transportEnv.get(HAWK_CONSOLE_PROPERTY_AGENT_HEARTBEAT_INTERVAL);

		if (agentHeartBeat != null) {
			int interval = -1;
			try {
				interval = Integer.parseInt(agentHeartBeat);
			} catch (Throwable ignore) {
			}

			if (interval > 0) {
				// System.out.println("setMaxAgentHeartbeatInterval=" +
				// interval);
				setMaxAgentHeartbeatInterval(interval);
			}
		}

		String[] args = null;

		if (emsTransport == null) {
			args = new String[] { "-hawk_rvd_session", service == null ? "" : service, network == null ? "" : network,
					daemon == null ? "" : daemon };
		} else {
			String s = new String(emsTransport);

			StringTokenizer tokens = new StringTokenizer(s, " ");
			String url = null;
			String usr = null;
			String pswd = null;
			if (tokens.hasMoreTokens()) {
				url = stripQuoteChars(tokens.nextToken());
			}
			if (tokens.hasMoreTokens()) {
				usr = stripQuoteChars(tokens.nextToken());
				if (tokens.hasMoreTokens())
					pswd = stripQuoteChars(tokens.nextToken());
			}
			ArrayList a = new ArrayList();
			a.add("-ems_url");
			a.add(url);
			if (usr != null) {
				a.add("-ems_id");
				a.add(usr);
			}
			if (pswd != null && pswd.length() > 0) {
				a.add("-ems_pw");
				a.add(pswd);
			}
			for (int i = 0; i < s_ems_ssl_transport_parameters.length; i++) {
				String p = (String) transportEnv.get(s_ems_ssl_transport_parameters[i]);
				if (p != null) {
					if (s_ems_ssl_transport_parameters[i].equals("ssl_no_verify_hostname")) {
						a.add("-ssl_verify_hostname");
						a.add("disabled");
					} else if (s_ems_ssl_transport_parameters[i].equals("ssl_no_verify_host")) {
						a.add("-ssl_verify_host");
						a.add("disabled");
					} else {
						a.add("-" + s_ems_ssl_transport_parameters[i]);
						a.add(stripQuoteChars(p));
					}
				}
			}
			args = new String[a.size()];
			int n = 0;
			for (Iterator j = a.iterator(); j.hasNext(); n++) {
				String x = (String) j.next();
				// System.out.println("Add to args:" + x);
				args[n] = x;
			}

		}

		if (hawkDomain != null) {
			mDomain = hawkDomain;
			String[] domainArgs = null;
			domainArgs = new String[] { "-hawk_domain", mDomain };
			args = (String[]) ArrayUtil.merge(args, domainArgs, String.class);
		}

		if (securityPolicy != null) {
			mSecurityPolicy = securityPolicy;
			String[] secArgs = null;
			secArgs = new String[] { "-security_policy", mSecurityPolicy };
			args = (String[]) ArrayUtil.merge(args, secArgs, String.class);
		}

		HawkConsoleBaseInit((String[]) ArrayUtil.merge(args, trackAlerts ? sDefaultPublisherFilters
				: sNoAlertPublisherFilters, String.class), trackAlerts);
	}

	private void HawkConsoleBaseInit(String[] inArgs, boolean trackAlerts) throws Exception {
		// System.out.println("const");
		mTrace = ContextControl.getTrace();

		Command mCommand = new Command("HawkConsole", "HawkConsole Plugin", null, mTrace);

		// Add command line parameters for Hawk alert publisher from
		// publisher package.
		mCommand.addParameters(new PublisherCommand());

		// Parse command line based on resulting command line definition. The
		// true argument will cause the Command object to perform a
		// System.exit()
		// on parsing errors or if command help is requested.
		// System.out.println("inargs="+ ObjChecker.toString(inArgs));
		mCommand.parse(inArgs, false);

		AlertFilter af = new AlertFilter();
		if (trackAlerts)
			af.setInformationActive("ON");
		else {
			af.setAlertHighActive("OFF");
			af.setAlertMediumActive("OFF");
			af.setAlertLowActive("OFF");
			af.setInformationActive("OFF");
		}

		// Create a publisher object. We pass in the Command which contains any
		// entered values for the Publisher command line parameters. We pass in
		// a this pointer because Publisher implements the PublisherListener
		// interface. We pass in our Trace object so the Publisher logs to our
		// trace log.

		mPublisher = new Publisher(mCommand, this, mTrace, af);

		mPublisher.startPublisher(false);

		mConsole = mPublisher.getConsole();
		mHawkAgentMonitor = mConsole.getAgentMonitor();
		mHawkAgentManager = mConsole.getAgentManager();
		mMicroAgentServer = mHawkAgentManager;

		// Adding AgentMonitorListener
		mHawkAgentMonitor.addMicroAgentListMonitorListener(this);

		// Initializing Agent Monitor
		mHawkAgentMonitor.initialize();

		// Initializing Agent Manager, appropriately. If Security is specified,
		// than, Intialize AgentMangaer with security policy class.

		if (mSecurityPolicy == null) {
			mHawkAgentManager.initialize();
			mTrace.log(Trace.DEBUG, "Initializing without Security Class.");
		} else {
			mHawkAgentManager.initialize(mSecurityPolicy, HsConsoleInterface.DAEMON);
			mTrace.log(Trace.DEBUG, "Initializing with Security Class: " + mSecurityPolicy);

		}
		/*
		 * if (sHawkConsoleBase == null) sHawkConsoleBase = this;
		 */
	} // Constructor

	// ***************************************************************************
	// * Method: isDestroyed
	// *
	/**
	 * @return true if this console instance has been destroyed
	 */
	public boolean isDestroyed() {
		return mIsDestroyed;
	}

	// ***************************************************************************
	// * Method: destroy
	// *
	/**
	 * destroy the console instance
	 */
	public void destroy() {
		mPublisherListeners = new ArrayList();
		mMicroAgentListMonitorListeners = new ArrayList();
		mAlertTable = new Hashtable(); // clear this first to help GC
		mIsDestroyed = true;

		mHawkAgentMonitor.removeMicroAgentListMonitorListener(this);
		mPublisher.stopPublisher();
		mHawkAgentMonitor.shutdown();
		mHawkAgentManager.shutdown();
	}

	// ***************************************************************************
	// * Method: MicroAgent.publisherStartup
	// *
	/*
	 * This method is called when the alert publisher is first started. This
	 * gives the application the chance to perform any application specific
	 * initialization. This event can be used for fault-tolerance by clearing
	 * all outstanding alerts because the alert publisher (on startup) will
	 * completely refresh the current alert status for all agents. In this way,
	 * whenever the publisher is started, the application will totally refresh
	 * its alert status (i.e. start over).
	 * *************************************************************************
	 */
	/**
	 * PublisherListener callback - This method is for internal use only.
	 */
	public void publisherStartup() {
		if (mTrace.isLevelOn(Trace.DEBUG))
			mTrace.log(Trace.DEBUG, "Publisher Started for HawkConsoleBase " + sHawkConsoleBaseVersion + ".");
	}

	// ***************************************************************************
	// * Method: MicroAgent.publisherShutdown
	// *
	/*
	 * This method is called when the alert publisher is stopped. This gives the
	 * application the chance to perform any application specific clean-up.
	 * *************************************************************************
	 */
	/**
	 * PublisherListener callback - This method is for internal use only.
	 */
	public void publisherShutdown() {
		// If publisher is shutting down, remove all elements....

		mTrace.log(Trace.DEBUG, "Publisher shutting down.");
		mAgentHashTable = new Hashtable(50, 50);
		mClusterHashTable = new Hashtable(10, 10);
		mMicroAgentHashTable = new Hashtable(50, 50);
		// mMASubscriptionTable = new Hashtable( 25, 25 );
		mSubContextTable = new Hashtable(25, 25);
		mAlertTable = new Hashtable(50, 50);
		mMAListenerList = new Vector();
		mMATrackerList = new Vector();
		mEventTrackerList = new Vector();

	}

	// ***************************************************************************
	// * Method: Publisher.publishAgentAlive
	// *
	/*
	 * This method is called when an Agent Alive event has occurred. The
	 * Publisher will automatically inform the listener of any alerts which
	 * should be cleared as a result of this Agent Alive event by calling
	 * PublisherListener.publishAlertClear() for each alert. The Publisher will
	 * automatically get all current alerts from the Agent comming alive and
	 * call PublisherListener.publishAlert() for each alert.
	 * 
	 * @param inPublisherAgent Instance of PublisherAgent class.
	 * *************************************************************************
	 */
	/**
	 * PublisherListener callback - This method is for internal use only.
	 */
	public void publishAgentAlive(PublisherAgent inPublisherAgent) {
		// int j = 0;
		String agentName = inPublisherAgent.mAgentID.getName();
		if (agentName == null) {
			mTrace.log(Trace.DEBUG, "Agent Alive: " + agentName + " on " + inPublisherAgent.mAgentID);
			return;
		}

		if (mAgentANDFilters != null)
			for (int i = 0; i < mAgentANDFilters.length; i++) {
				Filter f = mAgentANDFilters[i];
				if (!f.validate(agentName))
					return;
			}

		if (mAgentORFilters != null)
			for (int i = 0; i < mAgentORFilters.length; i++) {
				Filter f = mAgentORFilters[i];
				if (f.validate(agentName))
					break;
			}

		mTrace.log(Trace.DEBUG, "Agent Alive: " + agentName);
		// Getting AgentInstance
		AgentInstance agentInst = inPublisherAgent.mAgentInstance;
		// synchronized (mAgentHashTable)
		// {
		mAgentHashTable.put(agentName, agentInst);
		updateCluster(true, false, agentInst);

		for (Enumeration e = mEventTrackerList.elements(); e.hasMoreElements();) {
			HawkEventTrackerBase tracker = (HawkEventTrackerBase) e.nextElement();
			tracker.add(agentName);
		}
		MicroAgentID[] MicroAgentList = agentInst.getStatusMicroAgents();
		for (int j = 0; j < MicroAgentList.length; j++) {
			String maName = MicroAgentList[j].getDisplayName();
			String maInst = MicroAgentList[j].getInstance();
			for (Enumeration e = mMATrackerList.elements(); e != null && e.hasMoreElements();) {
				HawkMicroagentTrackerBase tracker = (HawkMicroagentTrackerBase) e.nextElement();
				try {
					tracker.add(agentInst, maName + ":" + maInst);
				} catch (Exception ex) {
					// TODO
				}
			}
			for (Enumeration e = mEventTrackerList.elements(); e.hasMoreElements();) {
				HawkEventTrackerBase tracker = (HawkEventTrackerBase) e.nextElement();
				tracker.add(agentName, maName + ":" + maInst);
			}

		}
		// }

		for (Iterator i = getPublisherListeners().iterator(); i.hasNext();) {
			PublisherListener l = (PublisherListener) i.next();
			l.publishAgentAlive(inPublisherAgent);
		}

	}

	// ***************************************************************************
	// * Method: Publisher.publishAgentDead
	// *
	/*
	 * This method is called when an Agent Dead event has occurred. The
	 * Publisher will automatically inform the listener of any alerts which have
	 * been cleared as a result of this Agent Dead event by calling
	 * PublisherListener.publishAlertClear() for each alert.
	 * 
	 * @param inPublisherAgent PublisherAgent.
	 * *************************************************************************
	 */
	/**
	 * PublisherListener callback - This method is for internal use only.
	 */
	public void publishAgentDead(PublisherAgent inPublisherAgent) {
		String agentName = inPublisherAgent.mAgentID.getName();
		if (agentName == null) {
			mTrace.log(Trace.DEBUG, "Agent Dead: " + agentName + " on " + inPublisherAgent.mAgentID);
			return;
		}
		if (mAgentANDFilters != null)
			for (int i = 0; i < mAgentANDFilters.length; i++) {
				Filter f = mAgentANDFilters[i];
				if (!f.validate(agentName))
					return;
			}

		if (mAgentORFilters != null)
			for (int i = 0; i < mAgentORFilters.length; i++) {
				Filter f = mAgentORFilters[i];
				if (f.validate(agentName))
					break;
			}

		mTrace.log(Trace.DEBUG, "Agent Dead: " + agentName);
		// synchronized (mAgentHashTable)
		// {
		AgentInstance agentInst = (AgentInstance) mAgentHashTable.get(agentName);

		if (agentInst == null)
			return;

		MicroAgentID[] MicroAgentList = agentInst.getStatusMicroAgents();
		for (int j = 0; j < MicroAgentList.length; j++) {
			mTrace.log(Trace.DEBUG, "Removing MicroAgent: " + MicroAgentList[j].getDisplayName());
			// removeMicroAgentDescriptor( MicroAgentList[j] );
		}
		mAgentHashTable.remove(agentName);
		updateCluster(false, false, agentInst);
		mAlertTable.remove(agentName);

		for (Enumeration e = mMATrackerList.elements(); e.hasMoreElements();) {
			HawkMicroagentTrackerBase tracker = (HawkMicroagentTrackerBase) e.nextElement();
			if (tracker == null)
				continue;
			tracker.remove(agentInst);
		}
		for (Enumeration e = mEventTrackerList.elements(); e.hasMoreElements();) {
			HawkEventTrackerBase tracker = (HawkEventTrackerBase) e.nextElement();
			tracker.remove(agentName);
		}
		// }

		for (Iterator i = getPublisherListeners().iterator(); i.hasNext();) {
			PublisherListener l = (PublisherListener) i.next();
			l.publishAgentDead(inPublisherAgent);
		}
	}

	// ***************************************************************************
	// * Method: Publisher.publishAlert
	// *
	/*
	 * This method is called when a Hawk Alert has occurred.
	 * 
	 * @param inPublisherAlert Instance of PublisherAlert class
	 * *************************************************************************
	 */
	/**
	 * PublisherListener callback - This method is for internal use only.
	 */
	public void publishAlert(PublisherAlert inPublisherAlert) {
		AgentInstance agentInst = inPublisherAlert.mAgentInstance;
		String agentName = agentInst.getAgentID().getName();
		Vector alerts = (Vector) mAlertTable.get(agentName);
		if (alerts != null && alerts.size() > mMaxAlertPerAgent)
			purgeAlerts(agentName, alerts);

		if (mAgentANDFilters != null)
			for (int i = 0; i < mAgentANDFilters.length; i++) {
				Filter f = mAgentANDFilters[i];
				if (!f.validate(agentName))
					return;
			}

		if (mAgentORFilters != null)
			for (int i = 0; i < mAgentORFilters.length; i++) {
				Filter f = mAgentORFilters[i];
				if (f.validate(agentName))
					break;
			}

		if (alerts == null) {
			alerts = new Vector();
			mAlertTable.put(agentName, alerts);
		}

		if (mTrace.isLevelOn(Trace.DEBUG))
			mTrace.log(Trace.DEBUG, "Received alert from Agent: " + agentName + " alert: "
					+ inPublisherAlert.mAlertText);
		/*
		 * for (int i = 0; i < alerts.size(); i++) { PublisherAlert a =
		 * (PublisherAlert)alerts.elementAt(i); if (a.mAlertID ==
		 * inPublisherAlert.mAlertID) { alerts.removeElementAt(i); mTrace.log(
		 * Trace.DEBUG, "Remove dup alert " + inPublisherAlert.mAlertID +
		 * " from Agent: " + agentName + " alert: " +
		 * inPublisherAlert.mAlertText); break; } }
		 */
		alerts.addElement(inPublisherAlert);
		updateCluster(true, true, agentInst);

		for (Enumeration e = mEventTrackerList.elements(); e.hasMoreElements();) {
			String maName = inPublisherAlert.mDataSource;
			HawkEventTrackerBase tracker = (HawkEventTrackerBase) e.nextElement();
			if (maName == null) {
				// System.out.println("NOMA");
				int idx = inPublisherAlert.mAlertText.indexOf(sNoSourceErr);
				if (idx < 0)
					continue;
				// System.out.println("IDX="+idx);
				String s = inPublisherAlert.mAlertText.substring(idx + sNoSourceErr.length());
				idx = s.lastIndexOf(':');
				// System.out.println("IDX="+idx);
				if (isValidMicroagentInstanceNumber(s.substring(idx + 1)))
					maName = s.substring(0, idx);
				else
					maName = s;
				// System.out.println("MA="+maName);
			}
			tracker.add(agentName, maName, inPublisherAlert);
		}

		for (Iterator i = getPublisherListeners().iterator(); i.hasNext();) {
			PublisherListener l = (PublisherListener) i.next();
			l.publishAlert(inPublisherAlert);
		}
	}

	// ***************************************************************************
	// * Method: Publisher.publishAlertClear
	// *
	/*
	 * This method is called when a Hawk Clear has occurred.
	 * 
	 * @param inAgentID ID of agent associated wih alert to be cleared.
	 * 
	 * @param inPublisherAlert Instance of PublisherAlert class.
	 * 
	 * @param inReason Reason that alert was cleared.
	 * *************************************************************************
	 */
	/**
	 * PublisherListener callback - This method is for internal use only.
	 */
	public void publishAlertClear(AgentID inAgentID, PublisherAlert inPublisherAlert, String inReason) {
		AgentInstance agentInst = inPublisherAlert.mAgentInstance;
		String agentName = agentInst.getAgentID().getName();
		if (mAgentANDFilters != null)
			for (int i = 0; i < mAgentANDFilters.length; i++) {
				Filter f = mAgentANDFilters[i];
				if (!f.validate(agentName))
					return;
			}

		if (mAgentORFilters != null)
			for (int i = 0; i < mAgentORFilters.length; i++) {
				Filter f = mAgentORFilters[i];
				if (f.validate(agentName))
					break;
			}

		// mAgentHashTable.put(agentName, agentInst);
		Vector alerts = (Vector) mAlertTable.get(agentName);
		if (alerts == null) {
			return;
		}

		if (mTrace.isLevelOn(Trace.DEBUG))
			mTrace.log(Trace.DEBUG, "Clear alert on Agent: " + agentName + " alert: " + inPublisherAlert.mAlertText);
		boolean found = true;
		while (found) {
			found = false;
			for (int i = 0; i < alerts.size(); i++) {
				PublisherAlert a = (PublisherAlert) alerts.elementAt(i);
				if (a.mAlertID == inPublisherAlert.mAlertID) {
					alerts.removeElementAt(i);
					found = true;
					break;
				}
			}
		}
		updateCluster(true, true, agentInst);

		for (Enumeration e = mEventTrackerList.elements(); e.hasMoreElements();) {
			String maName = inPublisherAlert.mDataSource;
			HawkEventTrackerBase tracker = (HawkEventTrackerBase) e.nextElement();
			if (maName == null) {
				int idx = inPublisherAlert.mAlertText.indexOf(sNoSourceErr);
				if (idx < 0)
					continue;
				String s = inPublisherAlert.mAlertText.substring(idx + sNoSourceErr.length());
				idx = s.lastIndexOf(':');
				if (isValidMicroagentInstanceNumber(s.substring(idx + 1)))
					maName = s.substring(0, idx);
				else
					maName = s;
			}
			tracker.remove(agentName, maName, inPublisherAlert);
		}

		for (Iterator i = getPublisherListeners().iterator(); i.hasNext();) {
			PublisherListener l = (PublisherListener) i.next();
			l.publishAlertClear(inAgentID, inPublisherAlert, inReason);
		}
	}

	/**
	 * Get active alerts for a TIBCO Hawk agent
	 * 
	 * @param agentName
	 *            The TIBCO Hawk agent name to get alerts
	 * @return Data with the following columns: "State", "TimeStamp",
	 *         "RuleBase", "Source", "Text", "Properties"
	 *         <p>
	 *         Default TimeStamp format is "hh:mm:ss MMM dd, yyyy"
	 *         <p>
	 *         The properties are in Hashtable data type.
	 */
	public NamedTabularData getActiveAlerts(String agentName) {
		return getActiveAlerts(agentName, defaultTimeFormat, null, null, true);
	}

	private String alertState2Str(int state) {
		String r = "";
		switch (state) {
		case AlertState.ALERT_HIGH:
			r = "alert-high";
			break;

		case AlertState.ALERT_MEDIUM:
			r = "alert-medium";
			break;

		case AlertState.ALERT_LOW:
			r = "alert-low";
			break;

		case AlertState.NO_ALERT:
			r = "";
			break;
		}
		return r;
	}

	static String propertyToString(Hashtable prop, String propertyFormat, String propertyLineSeparator,
			boolean userPropertiesOnly) {
		if (prop == null || propertyFormat == null)
			return "";
		String separator = propertyLineSeparator == null ? "\n" : propertyLineSeparator;
		StringBuffer s = new StringBuffer("");
		MessageFormat mf = new MessageFormat(propertyFormat);
		int i = 0;
		for (Enumeration e = prop.keys(); e.hasMoreElements();) {
			String key = (String) e.nextElement();
			boolean actionProp = key.startsWith("Action.");
			if (userPropertiesOnly && (!actionProp))
				continue;
			String val = "" + prop.get(key);
			if (userPropertiesOnly && actionProp)
				key = key.substring(7);
			String tmp = "";
			try {
				tmp = MessageFormat.format(propertyFormat, new Object[] { key, val });
			} catch (Exception err) {
				ContextControl.getTrace().log(Trace.DEBUG,
						"Failed to format alert property with format '" + propertyFormat + "' because of " + err);
			}
			if (tmp.length() > 0) {
				if (i == 0)
					s.append(tmp);
				else
					s.append(separator + tmp);
				i++;
			}
		}
		return s.toString();
	}

	/**
	 * Get active alerts for a TIBCO Hawk agent
	 * 
	 * @param agentName
	 *            The TIBCO Hawk agent name to get alerts
	 * @param timeStampFormat
	 *            the format to present TimeStamp. If null is specified, the
	 *            Date Object will be used
	 * @param propertyFormat
	 *            If null, the properties are returned as Hashtables. Otherwise,
	 *            they are formatted according to the specified format
	 *            (java.text.MessageFormat). In the propertyFormat, the property
	 *            name is {0} and the value is {1}, for example, "{0}:{1}".
	 * @param propertyLineSeparator
	 *            This is used only when the propertyFormat is specified. The
	 *            property lines are separated with this separator. If not
	 *            specified, line feed character is used.
	 * @param userPropertiesOnly
	 *            If true and propertyFormat is not null, only the properties
	 *            starts with "Action." will be returned in the "Properties"
	 *            field and the "Action." prefix will be removed from property
	 *            name.
	 * @return Data with the following columns: "State", "TimeStamp",
	 *         "RuleBase", "Source", "Text", "Properties", "ID"
	 */
	public NamedTabularData getActiveAlerts(String agentName, String timeStampFormat, String propertyFormat,
			String propertyLineSeparator, boolean userPropertiesOnly) {
		if (agentName == null || agentName.length() == 0)
			agentName = ContextControl.hostname();
		Vector alerts = (Vector) mAlertTable.get(agentName);
		if (alerts == null) {
			return null;
		}
		Object[][] r = new Object[alerts.size()][];
		for (int i = 0; i < alerts.size(); i++) {
			PublisherAlert a = (PublisherAlert) alerts.elementAt(i);
			Object[] data = new Object[sAlertColNames.length];
			data[0] = alertState2Str(a.mAlertState);
			data[1] = timeStampFormat == null ? (Object) new Date(a.mTimeGenerated) : (Object) TimeTool.localtime(
					a.mTimeGenerated, timeStampFormat);
			data[2] = a.mRuleBaseName;
			data[3] = a.mDataSource;
			data[4] = a.mAlertText;
			data[5] = propertyFormat == null ? (Object) a.mPostAlertEventProperties : (Object) propertyToString(
					a.mPostAlertEventProperties, propertyFormat, propertyLineSeparator, userPropertiesOnly);
			data[6] = new Long(a.mAlertID);
			r[i] = data;
		}
		return new NamedTabularData(sAlertColNames, r);
	}

	// Following two methods are for MicroAgentListMonitor Event....
	private boolean maExists(MicroAgentID maID, AgentInstance agentInst) {
		MicroAgentID[] MicroAgentList = agentInst.getStatusMicroAgents();
		for (int j = 0; j < MicroAgentList.length; j++) {
			// System.out.println("****against:" +
			// MicroAgentList[j].getDisplayName() +"\nid=" + MicroAgentList[j]);
			if (MicroAgentList[j] == maID)
				return true;
		}
		// System.out.println("\nNOT FOUND\n");
		return false;
	}

	/**
	 * This method is used to add a HawkMicroagentListener. When a TIBCO Hawk
	 * microagent is discovered ore removed on the netwrok, the
	 * HawkMicroagentListener <code>onMicroAgentAdded</code> or
	 * <code>onMicroAgentRemoved</code> will be called.
	 */
	public void addMicroAgentListener(HawkMicroagentListener listener) {
		mMAListenerList.addElement(listener);
	}

	/**
	 * This method is used to add a HawkMicroagentListener. When a TIBCO Hawk
	 * microagent is discovered on the netwrok, the HawkMicroagentListener
	 * <code>onMicroAgentAdded(String agentName, String microagentName)</code>
	 * will be called.
	 */
	public void removeMicroAgentListener(HawkMicroagentListener listener) {
		mMAListenerList.removeElement(listener);
	}

	/**
	 * MicroAgentListMonitorListener callback - This method is for internal use
	 * only.
	 */
	public void onMicroAgentAdded(MicroAgentListMonitorEvent ev) {
		// System.out.println("onMicroAgentAdded:");
		MicroAgentID maID = ev.getMicroAgentID();
		AgentInstance agentInst = ev.getAgentInstance();
		// System.out.println("onMicroAgentAdded:"+ maID);

		AgentID agentId = agentInst.getAgentID();
		String agentName = agentId.getName();

		if (mAgentANDFilters != null)
			for (int i = 0; i < mAgentANDFilters.length; i++) {
				Filter f = mAgentANDFilters[i];
				if (!f.validate(agentName))
					return;
			}

		if (mAgentORFilters != null)
			for (int i = 0; i < mAgentORFilters.length; i++) {
				Filter f = mAgentORFilters[i];
				if (f.validate(agentName))
					break;
			}

		String maName = maID.getDisplayName();
		String maInst = maID.getInstance();
		String maFullName = maName + ":" + maInst;
		mTrace.log(Trace.DEBUG, "Adding MicroAgent on Agent " + agentName + ": " + maFullName);
		// synchronized (mAgentHashTable)
		// {
		// mAgentHashTable.put(agentName, agentInst); //replace agent instance

		for (Enumeration e = mMAListenerList.elements(); e.hasMoreElements();) {
			HawkMicroagentListener l = (HawkMicroagentListener) e.nextElement();
			l.onMicroAgentAdded(agentName, maFullName);
		}

		for (Enumeration e = mMATrackerList.elements(); e.hasMoreElements();) {
			HawkMicroagentTrackerBase tracker = (HawkMicroagentTrackerBase) e.nextElement();
			try {
				tracker.add(agentInst, maFullName);
			} catch (Exception ex) {
				// TODO
			}
		}
		for (Enumeration e = mEventTrackerList.elements(); e.hasMoreElements();) {
			HawkEventTrackerBase tracker = (HawkEventTrackerBase) e.nextElement();
			tracker.add(agentName, maFullName);
		}

		// }

		for (Iterator i = getMicroAgentListMonitorListeners().iterator(); i.hasNext();) {
			((MicroAgentListMonitorListener) i.next()).onMicroAgentAdded(ev);
		}

	}

	/**
	 * MicroAgentListMonitorListener callback - This method is for internal use
	 * only.
	 */
	public void onMicroAgentRemoved(MicroAgentListMonitorEvent ev) {
		// System.out.println("onMicroAgentRemoved:");
		MicroAgentID maID = ev.getMicroAgentID();
		AgentInstance agentInst = ev.getAgentInstance();
		// System.out.println("onMicroAgentRemoved:"+ maID);

		AgentID agentId = agentInst.getAgentID();
		String agentName = agentId.getName();

		if (mAgentANDFilters != null)
			for (int i = 0; i < mAgentANDFilters.length; i++) {
				Filter f = mAgentANDFilters[i];
				if (!f.validate(agentName))
					return;
			}

		if (mAgentORFilters != null)
			for (int i = 0; i < mAgentORFilters.length; i++) {
				Filter f = mAgentORFilters[i];
				if (f.validate(agentName))
					break;
			}
		String maName = maID.getDisplayName();
		String maInst = maID.getInstance();
		String maFullName = maName + ":" + maInst;
		mTrace.log(Trace.DEBUG, "Removing MicroAgent from Agent " + agentName + ": " + maFullName);
		// removeMicroAgentDescriptor( maID );

		for (Enumeration e = mMAListenerList.elements(); e.hasMoreElements();) {
			HawkMicroagentListener l = (HawkMicroagentListener) e.nextElement();
			l.onMicroAgentRemoved(agentName, maFullName);
		}

		for (Enumeration e = mMATrackerList.elements(); e.hasMoreElements();) {
			HawkMicroagentTrackerBase tracker = (HawkMicroagentTrackerBase) e.nextElement();
			tracker.remove(agentInst, maFullName);
		}

		for (Enumeration e = mEventTrackerList.elements(); e.hasMoreElements();) {
			HawkEventTrackerBase tracker = (HawkEventTrackerBase) e.nextElement();
			tracker.remove(agentName, maFullName);
		}

		// mAgentHashTable.put(agentName, agentInst); //replace agent instance

		for (Iterator i = getMicroAgentListMonitorListeners().iterator(); i.hasNext();) {
			((MicroAgentListMonitorListener) i.next()).onMicroAgentRemoved(ev);
		}
	}

	private int findColIndex(String name, String[] colNames) {
		for (int i = 0; i < colNames.length; i++) {
			if (name.equals(colNames[i]))
				return i;
		}
		return -1;
	}

	private NamedTabularData extractData(Object mobj, String[] colNames) throws MicroAgentException {
		if (mobj == null) {
			mTrace.log(Trace.DEBUG, "extractData mobj == null");
			return null;
		} // mobj == null

		if (mobj instanceof MicroAgentException) {
			throw (MicroAgentException) mobj;
		} // mobj == MicroAgentException

		if (mobj instanceof MicroAgentData) {
			Object val = ((MicroAgentData) mobj).getData();
			if (val instanceof MicroAgentException)
				throw (MicroAgentException) val;
		}

		// mTrace.log(Trace.DEBUG, "extractData mobj=" + mobj);
		Object[] row;
		Object[][] table;
		if (colNames == null) {
			mTrace.log(Trace.ERROR, PluginResources.NO_COLUMN_NAMES,
					PluginResources.getResources().getString(PluginResources.NO_COLUMN_NAMES));
			return null;

		}
		int n = colNames.length;
		int idx[] = new int[n];
		for (int i = 0; i < n; i++)
			idx[i] = -1;

		if (mobj instanceof CompositeData) {
			DataElement[] retval = ((CompositeData) mobj).getDataElements();
			// n = retval.length;

			row = new Object[n];

			if (retval != null) {
				try {
					for (int i = 0; i < n; i++) {
						if (idx[i] < 0) {
							String name = retval[i].getName();
							// mTrace.log( Trace.DEBUG,
							// "CompositeData Parameter Name: " + name);
							idx[i] = findColIndex(name, colNames);
							if (idx[i] < 0) {
								mTrace.log(
										Trace.ERROR,
										PluginResources.PARAM_NOT_EXISTS,
										PluginResources.getResources().getFormattedString(
												PluginResources.PARAM_NOT_EXISTS, new String[] { name }));
								return null;
							}
						}
						row[idx[i]] = retval[i].getValue();

						// mTrace.log( Trace.DEBUG,
						// "CompositeData Parameter Value: " + row[idx[i]]);
					}
					return new NamedTabularData(colNames, new Object[][] { row });
				} // try/catch block
				catch (Exception e) {
					mTrace.log(
							Trace.ERROR,
							TRACE_CATEGORY,
							PluginResources.EXTRACT_DATA_FAILED,
							PluginResources.getResources().getFormattedString(PluginResources.EXTRACT_DATA_FAILED,
									new String[] { e.toString() }), e);
					return null;
				}
			} // retval
		} // mboj == CompositeData
		else if (mobj instanceof TabularData) {
			// mTrace.log( Trace.DEBUG, "Reporting Tabular Data ");

			DataElement[][] d = ((TabularData) mobj).getAllDataElements();
			// n = d[0].length;
			table = d == null ? new Object[0][n] : new Object[d.length][n];

			if (d != null) {
				// Getting Data in Tabular form
				try {
					for (int k = 0; k < d.length; k++) {
						for (int p = 0; p < d[k].length && p < idx.length; p++) {
							if (idx[p] < 0) {
								String name = d[k][p].getName();
								// mTrace.log( Trace.DEBUG,
								// "TabularData Parameter Name: " + name);
								idx[p] = findColIndex(name, colNames);
								if (idx[p] < 0) {
									mTrace.log(
											Trace.ERROR,
											PluginResources.PARAM_NOT_EXISTS,
											PluginResources.getResources().getFormattedString(
													PluginResources.PARAM_NOT_EXISTS, new String[] { name }));
									return null;
								}
							}
							table[k][idx[p]] = d[k][p].getValue();
							// mTrace.log( Trace.DEBUG,
							// "TabularData Parameter Value: " +
							// table[k][idx[p]]);
						}
					}
					return new NamedTabularData(colNames, table);
				} // try/catch block
				catch (Exception e) {
					mTrace.log(
							Trace.ERROR,
							TRACE_CATEGORY,
							PluginResources.EXTRACT_DATA_FAILED,
							PluginResources.getResources().getFormattedString(PluginResources.EXTRACT_DATA_FAILED,
									new String[] { e.toString() }), e);
					return null;
				}
			} // d != null
		} // else if ( mobj == TabularData )
		else if (n == 1) {
			Object val = mobj;
			if (mobj instanceof MicroAgentData) {
				val = ((MicroAgentData) mobj).getData();
				mTrace.log(Trace.DEBUG, "MOBJ is MicroAgentData val=" + val);
			}

			return new NamedTabularData(colNames, new Object[][] { new Object[] { val } });
		} else {
			mTrace.log(Trace.DEBUG, "Unaccetable result class " + mobj.getClass().getName() + " mobj=" + mobj);
		}

		return null;
	}

	/**
	 * This method can be used to disallow all ACTION or ACTION_INFO methods.
	 */
	public void disallowActionMethods() {
		mInfoMethodOnly = true;
	}

	/**
	 * This method enables method invocation output debug tracing
	 */
	public void enableMethodInvocationOutputTracing() {
		mEnableMethodInvocationOutputTracing = true;
	}

	/**
	 * This method disables method invocation output debug tracing
	 */
	public void disableMethodInvocationOutputTracing() {
		mEnableMethodInvocationOutputTracing = false;
	}

	/**
	 * Invoke a synchronous TIBCO Hawk method.
	 * 
	 * @param agentName
	 *            TIBCO Hawk agent name (Normally, it's the same as the machine
	 *            host name).
	 * @param microAgentName
	 *            TIBCO Hawk MicroAgent display name. If a microagent does not
	 *            have a display name, then the full microagent name is used. If
	 *            the instchance number (with <code>":<n>"</code> at the end) is
	 *            specified, the method is invoked on the specified MicroAgent
	 *            instance; otherwise, the method is invoked on the first
	 *            discovered MicroAgent instance.
	 * @param methodName
	 *            TIBCO Hawk MicroAgent method name
	 * @param inputParameters
	 *            method input parameters The parameters specified in the
	 *            NamedArray does not have to follow the exact order defined in
	 *            the "method description". Also, optional parameters can be
	 *            omitted.
	 */
	public NamedTabularData invokeHawkMethod(String agentName, String microAgentName, String methodName,
			NamedArray inputParameters) throws MicroAgentException, HawkConsoleException {
		return (NamedTabularData) handleHawkMethod(agentName, microAgentName, methodName, inputParameters, null, false,
				-1);
	}

	/**
	 * Get the first or the only one result from an asynchronous method in a
	 * synchronous manner
	 * 
	 * @param agentName
	 *            TIBCO Hawk agent name (Normally, it's the same as the machine
	 *            host name).
	 * @param microAgentName
	 *            TIBCO Hawk MicroAgent display name. If a microagent does not
	 *            have a display name, then the full microagent name is used. If
	 *            the instchance number (with <code>":<n>"</code> at the end) is
	 *            specified, the method is invoked on the specified MicroAgent
	 *            instance; otherwise, the method is invoked on the first
	 *            discovered MicroAgent instance.
	 * @param methodName
	 *            TIBCO Hawk MicroAgent asynchronous method name
	 * @param inputParameters
	 *            method input parameters The parameters specified in the
	 *            NamedArray does not have to follow the exact order defined in
	 *            the "method description". Also, optional parameters can be
	 *            omitted.
	 * @param timeout
	 *            the timeout value in seconds - the method subscription is
	 *            cancelled if no data returned within the specified timeout
	 *            value
	 */
	public NamedTabularData asyncInvokeHawkMethod(String agentName, String microAgentName, String methodName,
			NamedArray inputParameters, int timeout) throws MicroAgentException, HawkConsoleException {
		int delay = (timeout > 0 ? timeout : 10) * 1000;

		final Object[] shuttle = new Object[1];

		if (mAsyncInvokeTimer == null) {
			mAsyncInvokeTimer = new Timer();
			mAsyncInvokeTimerTaskCount = 0;
		}
		final TimerTask timerTask = new java.util.TimerTask() {
			public void run() {
				synchronized (shuttle) {
					shuttle[0] = new MicroAgentException("Request timed out");
					shuttle.notify();
				}
			}
		};

		HawkSubscriptionListener listener = new HawkSubscriptionListener() {
			public void onData(String agentName, String microagentName, NamedTabularData data) {
				synchronized (shuttle) {
					shuttle[0] = data;
					try {
						shuttle.notify();
					} catch (Throwable th) {
						mTrace.log(Trace.DEBUG, "shuttle.notify() failed:" + th);
					}
				}
			}

			public void onSubscriptionStatus(String agentName, String microagentName,
					HawkMethodSubscription subscription, String status, MicroAgentException exception) {

				if (status.equals(HawkSubscriptionListener.SUBSCRIPTION_TERMINATED)) {
					Exception ex = exception;
					if (ex == null) {
						ex = new MicroAgentException("Method subscription terminated");
					}
					synchronized (shuttle) {
						shuttle[0] = ex;
						try {
							shuttle.notify();
						} catch (Throwable th) {
							mTrace.log(Trace.DEBUG, "shuttle.notify() failed:" + th);
						}
					}
				} else if (status.equals(HawkSubscriptionListener.SUBSCRIPTION_ERROR) && exception != null) {
					synchronized (shuttle) {
						shuttle[0] = exception;
						try {
							shuttle.notify();
						} catch (Throwable th) {
							mTrace.log(Trace.DEBUG, "shuttle.notify() failed:" + th);
						}
					}
				}
			}
		};

		HawkMethodSubscription subs = new HawkMethodSubscription(this, agentName, microAgentName, methodName,
				inputParameters, -1, listener);

		mAsyncInvokeTimer.schedule(timerTask, delay);
		mAsyncInvokeTimerTaskCount++;

		synchronized (shuttle) {
			try {
				if (shuttle[0] == null) // Error may be already back
					shuttle.wait();
			} catch (InterruptedException ie) {
				shuttle[0] = new MicroAgentException("wait() was interrupted: " + ie);
			}
			try {
				timerTask.cancel(); // cancel the timer no matter what
			} catch (Exception ignore) {
			}
			mAsyncInvokeTimerTaskCount--;
			if (mAsyncInvokeTimerTaskCount <= 0)
				mAsyncInvokeTimer = null;
			try {
				subs.destroy(); // destroy the subscription no matter what
			} catch (Exception ignore) {
			}
			if (shuttle[0] instanceof NamedTabularData)
				return (NamedTabularData) shuttle[0];
			else if (shuttle[0] instanceof MicroAgentException)
				throw (MicroAgentException) shuttle[0];
			else if (shuttle[0] instanceof HawkConsoleException)
				throw (HawkConsoleException) shuttle[0];
			else
				throw new MicroAgentException("" + shuttle[0]);
		}
	}

	/**
	 * Invoke a synchronous TIBCO Hawk method.
	 * 
	 * @param agentName
	 *            TIBCO Hawk agent name (Normally, it's the same as the machine
	 *            host name).
	 * @param microAgentName
	 *            TIBCO Hawk MicroAgent display name. If a microagent does not
	 *            have a display name, then the full microagent name is used. If
	 *            the instchance number (with <code>":<n>"</code> at the end) is
	 *            specified, the method is invoked on the specified MicroAgent
	 *            instance; otherwise, the method is invoked on the first
	 *            discovered MicroAgent instance.
	 * @param methodName
	 *            TIBCO Hawk MicroAgent method name
	 * @param inputParameters
	 *            method input parameters The parameters specified in the
	 *            OBject[] have to follow the exact order defined in the
	 *            "method description". Also, optional parameters can be omitted
	 *            if there's no value to be specified after the last specified
	 *            input parameter value.
	 */
	public NamedTabularData invokeHawkMethod(String agentName, String microAgentName, Object[] inputParameters,
			String methodName) throws MicroAgentException, HawkConsoleException {
		MicroAgentID microAgentID = getMicroAgentID(agentName, microAgentName);
		if (mTrace.isLevelOn(Trace.DEBUG))
			mTrace.log(Trace.DEBUG, "Invoke MicroAgent: " + microAgentName + " Method: " + methodName + " on Agent: "
					+ agentName + " microAgentID: " + microAgentID);
		if (microAgentID == null) {
			throw new HawkConsoleException(PluginResources.getResources().getFormattedString(
					PluginResources.MA_NOT_EXISTS, new String[] { microAgentName, agentName }));
		}
		return (NamedTabularData) handleHawkMethodInternalPrivate(new MicroAgentID[] { microAgentID }, methodName,
				inputParameters, null, false, -1);
	}

	/**
	 * Invoke a synchronous TIBCO Hawk method on multiple machines.
	 * 
	 * @param agentNames
	 *            TIBCO Hawk agent names (Normally, a agent name is the same as
	 *            the machine host name).
	 * @param microAgentName
	 *            TIBCO Hawk MicroAgent display name. If a microagent does not
	 *            have a display name, then the full microagent name is used. If
	 *            the instchance number (with <code>":<n>"</code> at the end) is
	 *            specified, the method is invoked on the specified MicroAgent
	 *            instance; otherwise, the method is invoked on the first
	 *            discovered MicroAgent instance.
	 * @param methodName
	 *            TIBCO Hawk MicroAgent method name
	 * @param inputParameters
	 *            method input parameters The parameters specified in the
	 *            NamedArray does not have to follow the exact order defined in
	 *            the "method description". Also, optional parameters can be
	 *            omitted.
	 */
	public NamedTabularData invokeHawkMethod(String[] agentNames, // for
																	// multiple
			String microAgentName, String methodName, NamedArray inputParameters) throws MicroAgentException,
			HawkConsoleException {
		if (agentNames == null || agentNames.length == 0)
			return null;
		MicroAgentID[] microAgentIDs = new MicroAgentID[agentNames.length];
		for (int i = 0; i < agentNames.length; i++)
			microAgentIDs[i] = getMicroAgentID(agentNames[i], microAgentName);

		return (NamedTabularData) handleHawkMethodInternalPrivate(microAgentIDs, methodName, inputParameters, null,
				false, -1);
	}

	/**
	 * @deprecated
	 */
	public NamedTabularData invokeHawkMethod(String agentName, String microAgentName, NamedArray inputParameters,
			String methodName) throws Exception {
		return (NamedTabularData) handleHawkMethod(agentName, microAgentName, methodName, inputParameters, null, false,
				-1);
	}

	/*
	 * Use HawkxxxxSubscriber for method subscriptions
	 * 
	 * public Object subscribeHawkMethod( String inAgentName, String
	 * inMicroAgentName, String inMethodName, NamedArray inParam ) throws
	 * MicroAgentException, HawkConsoleException { return
	 * handleHawkMethod(inAgentName, inMicroAgentName, inMethodName, inParam,
	 * null, true, -1); }
	 * 
	 * public Object subscribeHawkMethod( String inAgentName, String
	 * inMicroAgentName, String inMethodName, NamedArray inParam,
	 * HawkMethodSubscriber inMethodSubscriber ) throws MicroAgentException,
	 * HawkConsoleException { return handleHawkMethod(inAgentName,
	 * inMicroAgentName, inMethodName, inParam, inMethodSubscriber, true, -1); }
	 */

	/**
	 * This method is to be used by HawkMethodSubscriber only. Users should not
	 * call this method directly.
	 */
	Object subscribeHawkMethod(String inAgentName, String inMicroAgentName, String inMethodName, NamedArray inParam,
			int interval, HawkMethodSubscriber inMethodSubscriber) throws MicroAgentException, HawkConsoleException {
		return handleHawkMethod(inAgentName, inMicroAgentName, inMethodName, inParam, inMethodSubscriber, true,
				interval);
	}

	// This method would respond in case of Method Invocation and subscription

	private Object handleHawkMethod(String inAgentName, String inMicroAgentName, String inMethodName,
			NamedArray inParam, HawkMethodSubscriber inMethodSubscriber, boolean inIsSubscription, int inInterval)
			throws MicroAgentException, HawkConsoleException {
		MicroAgentID microAgentID = getMicroAgentID(inAgentName, inMicroAgentName);
		if (mTrace.isLevelOn(Trace.DEBUG))
			mTrace.log(Trace.DEBUG, "Handle MicroAgent: " + inMicroAgentName + " Method: " + inMethodName
					+ " on Agent: " + inAgentName + " microAgentID: " + microAgentID);

		if (microAgentID == null) {
			throw new HawkConsoleException(PluginResources.getResources().getFormattedString(
					PluginResources.MA_NOT_EXISTS, new String[] { inMicroAgentName, inAgentName }));
		}

		return handleHawkMethodInternal(microAgentID, inMethodName, inParam, inMethodSubscriber, inIsSubscription,
				inInterval);

	}

	/**
	 * Invoke a synchronous TIBCO Hawk method inside a TIBCO Hawk agent - This
	 * is for internal use only.
	 * 
	 * @param microAgentID
	 *            The MicroAgentID instance associated with the TIBCO Hawk
	 *            MicroAgent of interest.
	 * @param inMethodName
	 *            TIBCO Hawk MicroAgent method name
	 * @param inParameters
	 *            method input parameters The parameters specified in the
	 *            NamedArray does not have to follow the exact order defined in
	 *            the "method description". Also, optional parameters can be
	 *            omitted.
	 * @param inMethodSubscriber
	 *            Optional HawkMethodSubscriber object
	 * @param inIsSubscription
	 *            true if it's a method subscription
	 * @param inInterval
	 *            the subscription internal in number of seconds
	 */
	public Object handleHawkMethodInternal(MicroAgentID microAgentID, String inMethodName, NamedArray inParameters,
			HawkMethodSubscriber inMethodSubscriber, boolean inIsSubscription, int inInterval)
			throws MicroAgentException, HawkConsoleException {
		return handleHawkMethodInternalPrivate(new MicroAgentID[] { microAgentID }, inMethodName, inParameters,
				inMethodSubscriber, inIsSubscription, inInterval);
	}

	private Object[] getInputDataElementAndColNames(MicroAgentID microAgentID, String inMethodName, Object inParameters)
			throws MicroAgentException, HawkConsoleException {

		DataDescriptor[] argDataDesc = null;
		DataDescriptor retDataDesc = null;
		int TotalArg = 0;
		int n = 0;
		String[] colNames = null;
		String[] argNames = null;

		// Now let us create MethodInvocation object..
		// To create MethodInvocation object, we require methodname as a first
		// parameter and second parameter is DataElement, we know the method
		// Name

		MicroAgentDescriptor microAgentDesc = getMicroAgentDescriptor(microAgentID);
		if (microAgentDesc == null)
			return null;

		MethodDescriptor[] methodDesc = microAgentDesc.getMethodDescriptors();

		// get the Argument Descriptor for given method
		boolean found = false;
		for (n = 0; n < methodDesc.length; n++) {
			String mName = methodDesc[n].getName();
			if (mName.equals(inMethodName)) {
				found = true;

				if (mInfoMethodOnly) {
					int impact = methodDesc[n].getImpact();
					if (impact == MethodDescriptor.IMPACT_ACTION || impact == MethodDescriptor.IMPACT_ACTION_INFO)
						throw new HawkConsoleException(PluginResources.getResources().getFormattedString(
								PluginResources.ACTION_METHOD_NOT_ALLOWED,
								new String[] { microAgentID.getName(), inMethodName }));
				}

				argDataDesc = methodDesc[n].getArgumentDescriptors();
				TotalArg = argDataDesc == null ? 0 : argDataDesc.length;
				if (TotalArg > 0) {
					argNames = new String[argDataDesc.length];
					for (int m = 0; m < argDataDesc.length; m++)
						argNames[m] = argDataDesc[m].getName();

				}
				retDataDesc = methodDesc[n].getReturnDescriptor();
				// mTrace.log( Trace.DEBUG, "retDataDesc=" + retDataDesc);
				if (retDataDesc instanceof TabularDataDescriptor)
					colNames = ((TabularDataDescriptor) retDataDesc).getColumnNames();
				else if (retDataDesc instanceof CompositeDataDescriptor) {
					DataDescriptor[] dd = ((CompositeDataDescriptor) retDataDesc).getElementDescriptors();
					colNames = new String[dd.length];
					for (int i = 0; i < dd.length; i++)
						colNames[i] = dd[i].getName();
				} else if (retDataDesc instanceof DataDescriptor) {
					colNames = new String[] { retDataDesc.getName() };
				}
				break;
			} else
				continue;
		}
		if (!found)
			throw new HawkConsoleException(PluginResources.getResources().getFormattedString(
					PluginResources.METHOD_NOT_EXISTS, new String[] { microAgentID.getName(), inMethodName }));

		Object[] Value = null;
		if (TotalArg > 0) {
			if (inParameters != null) {
				if (inParameters instanceof Object[])
					Value = (Object[]) inParameters;
				else if (inParameters instanceof NamedArray)
					Value = ((NamedArray) inParameters).extractOrderedArray(argNames);
				else
					mTrace.log(Trace.DEBUG, "inParameters class=" + inParameters.getClass().getName());
			}
		}
		// mTrace.log(Trace.DEBUG, "inParameters values: " +
		// ObjChecker.toString(Value));

		// We need to create DataElement[] array...

		DataElement Darray[] = null;
		if (TotalArg != 0) {
			// Method does take argument(s)
			Darray = new DataElement[TotalArg];
			for (int m = 0; m < argDataDesc.length; m++) {
				// Name of the Argument and Type of The Argument...
				String ArgName = argNames[m];
				String ArgType = argDataDesc[m].getType();

				// Now we covert to appropriate type before creating DataElement
				// object
				// Retrieve the Value of Argument from HashTable...
				Object ArgVal = Value == null ? null : Value[m];

				if (mTrace.isLevelOn(Trace.DEBUG))
					mTrace.log(Trace.DEBUG, "Arg: " + ArgName + "=" + ArgVal + " (" + ArgType + ")");
				// Now we covert to appropriate type before creating DataElement
				// object
				try {
					Class cObject = Class.forName(ArgType);
					Object mobject = convertValue(cObject, ArgVal);
					// Object mobject = convertValue( ArgType.getClass(), ArgVal
					// );
					Darray[m] = new DataElement(argDataDesc[m].getName(), mobject);
				} catch (ClassNotFoundException e) {
					mTrace.log(
							Trace.ERROR,
							TRACE_CATEGORY,
							PluginResources.CLASS_NOT_FOUND,
							PluginResources.getResources().getFormattedString(PluginResources.CLASS_NOT_FOUND,
									new String[] { ArgType }), e);

					// Now we covert to appropriate type before creating
					// DataElement object
				}
			}
		}
		return new Object[] { colNames, Darray };
	}

	private Object handleHawkMethodInternalPrivate(MicroAgentID[] microAgentIDs, String inMethodName,
			Object inParameters, HawkMethodSubscriber inMethodSubscriber, boolean inIsSubscription, int inInterval)
			throws MicroAgentException, HawkConsoleException {
		if (microAgentIDs == null || microAgentIDs.length == 0)
			return null;

		DataElement[] Darray = null;
		String[] colNames = null;
		try {
			for (int i = 0; i < microAgentIDs.length; i++) {
				Object[] tmp = null;
				try {
					tmp = getInputDataElementAndColNames(microAgentIDs[i], inMethodName, inParameters);
				} catch (Exception ignore) {
				}
				if (tmp != null) {
					colNames = (String[]) tmp[0];
					Darray = (DataElement[]) tmp[1];
				}
			}
		} catch (Exception ignore) {
		}

		// Check to see if this method is asynch

		if (inIsSubscription) {
			// This function registers subscription with Agent for
			// MicroAgent/Method.
			return handleSubscription(microAgentIDs[0], inMethodName, Darray, colNames, inMethodSubscriber, inInterval);
		}

		// Creating MethodInvocation Object ..

		MicroAgentID microAgentID = microAgentIDs[0];

		MethodInvocation mInv = new MethodInvocation(inMethodName, Darray);

		// invoking method on mMicroAgentServer...

		Object r = null;
		if (microAgentIDs.length > 1) {
			MicroAgentData reply[] = mHawkAgentManager.groupOp(microAgentIDs, mInv);
			if (colNames == null)
				return null;

			Object[] rr = new NamedTabularData[reply.length];
			for (int ii = 0; ii < reply.length; ii++) {
				MicroAgentData mData = reply[ii];
				MicroAgentID mid = mData.getSource();
				if (mTrace.isLevelOn(Trace.DEBUG))
					mTrace.log(Trace.DEBUG, "Received sync data from Agent: " + mid.getAgent().getName()
							+ " MicroAgent: " + mid.getName() + " Method: " + inMethodName
							+ (mEnableMethodInvocationOutputTracing ? (" Data:" + mData.getData()) : ""));
				Object mobj = mData.getData();
				try {
					NamedTabularData d = extractData(mobj, colNames);
					d.setTableAttr("AgentName", mid.getAgent().getName());
					d.setTableAttr("MicroAgentName", mid.getDisplayName() + ":" + mid.getInstance());
					rr[ii] = d;
				} catch (MicroAgentException e) {
					// mTrace.log(Trace.DEBUG, "extractData returned exception:"
					// + e);

					throw new HawkConsoleException(PluginResources.getResources().getFormattedString(
							PluginResources.HAWK_METHOD_INVOKE_FAILED,
							new String[] { microAgentID.getName(), inMethodName, e.toString() }), e);
				} catch (Exception e) {
					// must be runtime exception
					mTrace.log(Trace.DEBUG, "extractData() returned exception:" + e, e);
					throw new HawkConsoleException("extractData() failed because of " + e, e);
				}

			}
			r = rr;
		} else {

			MicroAgentData mData = mMicroAgentServer.invoke(microAgentIDs[0], mInv);
			if (mTrace.isLevelOn(Trace.DEBUG))
				mTrace.log(Trace.DEBUG, "Received sync data from Agent: " + microAgentIDs[0].getAgent().getName()
						+ " MicroAgent: " + microAgentIDs[0].getName() + " Method: " + inMethodName +
						// + " mobj=" + mData.getData() + " colNames=" +
						// colNames
						(mEnableMethodInvocationOutputTracing ? (" Data:" + mData.getData()) : ""));
			Object mobj = mData.getData();
			if (mobj instanceof MicroAgentException)
				throw (MicroAgentException) mobj;

			if (colNames != null)
				try {
					r = extractData(mobj, colNames);
				} catch (MicroAgentException e) {
					// mTrace.log(Trace.DEBUG, "extractData returned exception:"
					// + e);

					throw new HawkConsoleException(PluginResources.getResources().getFormattedString(
							PluginResources.HAWK_METHOD_INVOKE_FAILED,
							new String[] { microAgentID.getName(), inMethodName, e.toString() }), e);
				} catch (Exception e) {
					// must be runtime exception
					mTrace.log(Trace.DEBUG, "extractData() returned exception:" + e, e);
					throw new HawkConsoleException("extractData() failed because of " + e, e);
				}

		}

		return r;

	}

	private Object handleSubscription(MicroAgentID inMAgentID, String inMethodName, DataElement[] inArgDataArray,
			String[] inColNames, HawkMethodSubscriber inMethodSubscriber, int inInterval) throws MicroAgentException {
		Subscription s = null;
		String AgentName = inMAgentID.getAgent().getName();

		// First we have to create MethodSubscription object,
		mTrace.log(Trace.DEBUG, "Method handleSubscription interval = " + inInterval);
		MethodSubscription msub = inInterval > 0 ? new MethodSubscription(inMethodName, inArgDataArray,
				(long) (inInterval * 1000)) : new MethodSubscription(inMethodName, inArgDataArray);

		// we keep track of subscription through creating uniqe subscription
		// context. here sSubContext is class variable. So we are guaranteed to
		// have one and only one instance of sSubContext.

		sSubContext++;
		String StrObj = Integer.toString(sSubContext);
		Object closure = StrObj;
		// Object[] closure = new Object[] {StrObj, inColNames};

		// First we have to register subscription with microagent
		Object x = inMethodSubscriber == null ? (Object) new Queue(10) : (Object) inMethodSubscriber;
		Object[] subsContext = new Object[] { null, inColNames, x, null };
		// Let us save this subscription object, keyed by subscription context
		// This subscription object is used to cancel the subscription
		// Do this before the "subscribe" because error may come back very fast
		mSubContextTable.put(closure, subsContext);
		try {
			s = mMicroAgentServer.subscribe(inMAgentID, msub, this, closure);
			subsContext[0] = s;
			mTrace.log(
					Trace.INFO,
					PluginResources.HAWK_METHOD_SUBSCRIPTION_SUCCESSFUL,
					PluginResources.getResources().getFormattedString(
							PluginResources.HAWK_METHOD_SUBSCRIPTION_SUCCESSFUL,
							new String[] { inMethodName, AgentName, inMAgentID.getName() }));
		} catch (MicroAgentException e) {
			mSubContextTable.remove(closure);
			mTrace.log(
					Trace.ERROR,
					PluginResources.HAWK_METHOD_SUBSCRIPTION_FAILED,
					PluginResources.getResources().getFormattedString(PluginResources.HAWK_METHOD_SUBSCRIPTION_FAILED,
							new String[] { AgentName, inMAgentID.getName(), inMethodName, e.toString() }));
			// We should return appropriate message to subscriber ( web browser
			// ).
			sSubContext--;
			throw e;
		}

		return closure;
		// Now we have successfully creadted subscription object.

	} // handleSubscription

	private Object convertValue(Class inType, Object inValue) {
		Object Result = null;

		// If the specified value is the same data type as this property
		// then no conversion is necessary.

		if (inValue == null)
			inValue = new String("");

		if (inType.isInstance(inValue)) {
			Result = inValue;
			return Result;
		}

		// Perform appropriate conversion.
		if (inType.equals(String.class)) {
			Result = inValue.toString();
		} else if (inType.equals(Boolean.class)) {

			if (inValue instanceof java.lang.String) {
				Result = new Boolean((String) inValue);
			} else
				Result = Boolean.FALSE;

		} else if (inType.equals(Integer.class)) {
			if (inValue instanceof java.lang.String) {
				try {
					Result = Integer.valueOf((String) inValue);
				} catch (NumberFormatException e) {
					Result = new Integer(0);
				}
			} else if (inValue instanceof java.lang.Number) {
				Result = new Integer(((Number) inValue).intValue());
			} else if (inValue instanceof java.lang.Boolean) {
				Result = (((Boolean) inValue).booleanValue()) ? new Integer(1) : new Integer(0);
			} else if (inValue instanceof java.lang.Character) {
				Result = new Integer(Character.digit(((Character) inValue).charValue(), 10));
			}
		} else if (inType.equals(Long.class)) {
			if (inValue instanceof java.lang.String) {
				try {
					Result = Long.valueOf((String) inValue);
				} catch (NumberFormatException e) {
					Result = new Long(0L);
				}
			} else if (inValue instanceof java.lang.Number) {
				Result = new Long(((Number) inValue).longValue());
			} else if (inValue instanceof java.lang.Boolean) {
				Result = (((Boolean) inValue).booleanValue()) ? new Long(1L) : new Long(0L);
			} else if (inValue instanceof java.lang.Character) {
				Result = new Long((long) Character.digit(((Character) inValue).charValue(), 10));
			}
		} else if (inType.equals(Double.class)) {
			if (inValue instanceof java.lang.String) {
				try {
					Result = new Double((String) inValue);
				} catch (NumberFormatException e) {
					Result = new Double(0.0);
				}
			} else if (inValue instanceof java.lang.Number) {
				Result = new Double(((Number) inValue).doubleValue());
			} else if (inValue instanceof java.lang.Boolean) {
				Result = (((Boolean) inValue).booleanValue()) ? new Double(1D) : new Double(0D);
			} else if (inValue instanceof java.lang.Character) {
				Result = new Double((double) Character.digit(((Character) inValue).charValue(), 10));
			}
		} else if (inType.equals(BigInteger.class)) {
			if (inValue instanceof java.lang.String) {
				try {
					Result = new BigInteger((String) inValue);
				} catch (NumberFormatException e) {
					Result = BigInteger.valueOf(0L);
				}
			} else if (inValue instanceof java.lang.Number) {
				Result = BigInteger.valueOf(((Number) inValue).longValue());
			} else if (inValue instanceof java.lang.Boolean) {
				Result = (((Boolean) inValue).booleanValue()) ? BigInteger.valueOf(1L) : BigInteger.valueOf(0L);
			} else if (inValue instanceof java.lang.Character) {
				Result = BigInteger.valueOf((long) Character.digit(((Character) inValue).charValue(), 10));
			}
		}

		return Result;
	}

	/**
	 * Get all available method names for a MicroAgent
	 * 
	 * @param inAgentName
	 *            the TIBCO Hawk agent name
	 * @param inMicroAgentName
	 *            the TIBCO Hawk MicroAgent name
	 */
	public String[] getMethodNames(String inAgentName, String inMicroAgentName) throws MicroAgentException {

		MicroAgentID microAgentID = null;
		try {
			microAgentID = getMicroAgentID(inAgentName, inMicroAgentName);
		} catch (Exception ignore) {
		}

		if (microAgentID == null) {
			mTrace.log(
					Trace.ERROR,
					PluginResources.MA_NOT_EXISTS,
					PluginResources.getResources().getFormattedString(PluginResources.MA_NOT_EXISTS,
							new String[] { inMicroAgentName, inAgentName }));
			return null;
		}

		String[] result = null;

		// Now let us create MethodInvocation object..
		// To create MethodInvocation object, we require methodname as a first
		// parameter and second parameter is DataElement, we know the method
		// Name

		MicroAgentDescriptor microAgentDesc = getMicroAgentDescriptor(microAgentID);
		if (microAgentDesc == null)
			return null;

		MethodDescriptor[] methodDesc = microAgentDesc.getMethodDescriptors();

		result = new String[methodDesc.length];
		// get the Argument Descriptor for given method
		for (int n = 0; n < methodDesc.length; n++)
			result[n] = methodDesc[n].getName();

		return result;

	}

	// Four method implementation to support SubscriptionHandler interface

	// onData method receives data for particular subscription
	// s is the subscription object, generated when subscription was made
	// data produced by MicroAgent

	/**
	 * SubscriptionHandler callback - This method is for internal use only.
	 */
	public void onData(Subscription s, MicroAgentData data) {
		MethodSubscription msub = s.getMethodSubscription();
		String methodName = msub.getMethodName();
		MicroAgentID mID = s.getMicroAgentID();
		String microAgentName = mID.getDisplayName();
		Object handback = s.getHandback();

		if (mTrace.isLevelOn(Trace.DEBUG))
			mTrace.log(Trace.DEBUG, "Received Data for Subscription: Agent: " + mID.getAgent().getName()
					+ " MicroAgent: " + microAgentName + " Method: " + methodName + " Subscription: " + handback
					+ (mEnableMethodInvocationOutputTracing ? (" Data: " + data) : ""));

		// Let is strore subscription information into Hashtable, key being
		// Subscription and data being MicroAgentData data being MicroAgentData.

		Object mobj = data.getData();
		if (data == null)
			return;
		NamedTabularData r = null;
		Object[] paramNameList = null;
		Object[] cx = (Object[]) mSubContextTable.get(handback);
		if (cx == null) {
			mTrace.log(
					Trace.ERROR,
					PluginResources.INTERNAL_ERROR,
					PluginResources.getResources().getFormattedString(PluginResources.INTERNAL_ERROR,
							new String[] { "Subscription context is null." }));
			return;
		}
		if (cx[1] != null)
			try {
				r = extractData(mobj, (String[]) cx[1]);
				if (r != null)
					r.setTableAttr("TimeStamp", new Date(System.currentTimeMillis()));
			} catch (MicroAgentException e) {
				mTrace.log(Trace.INFO, "Method subscription on MicroAgent '" + microAgentName + "' method '"
						+ methodName + "' received exception: " + e.toString());
				return;
			} catch (Exception e) {
				// must be runtime exception
				mTrace.log(
						Trace.ERROR,
						TRACE_CATEGORY,
						PluginResources.EXTRACT_DATA_FAILED,
						PluginResources.getResources().getFormattedString(PluginResources.EXTRACT_DATA_FAILED,
								new String[] { e.toString() }), e);
				return;
			}
		// mTrace.log( Trace.DEBUG, "Received async data: " + r.toString());
		if (r == null) {
			mTrace.log(Trace.DEBUG, "Subscription result is null.");
			return;
		}

		if (mTrace.isLevelOn(Trace.DEBUG) && mEnableMethodInvocationOutputTracing)
			mTrace.log(Trace.DEBUG, "Data=" + r);
		if (cx[2] instanceof HawkMethodSubscriber)
			((HawkMethodSubscriber) cx[2]).addResult(r);
		else {
			Queue msgQ = (Queue) cx[2];
			msgQ.enqueue(r);
		}
	}

	/*
	 * boolean hasSubscriptionData(Object subscriptionHandle) {
	 * //System.out.println
	 * ("hasSubscriptionData subscriptionHandle="+subscriptionHandle); if
	 * (subscriptionHandle == null) {
	 * //System.out.println("subscriptionHandle is null"); return false; }
	 * Object[] cx = (Object[])mSubContextTable.get(subscriptionHandle); if
	 * (cx[2] instanceof HawkMethodSubscriber) return
	 * ((HawkMethodSubscriber)cx[2]).hasSubscriptionData(); else { Queue msgQ =
	 * (Queue)cx[2]; //System.out.println("msgQ="+ msgQ); if (msgQ == null ||
	 * msgQ.length() <= 0) return false; return true; }
	 * 
	 * }
	 * 
	 * Object getSubscriptionData(Object subscriptionHandle) { if
	 * (subscriptionHandle == null) {
	 * //System.out.println("subscriptionHandle is null"); return null; }
	 * Object[] cx = (Object[])mSubContextTable.get(subscriptionHandle); if
	 * (cx[2] instanceof HawkMethodSubscriber) return null; else { Queue msgQ =
	 * (Queue)cx[2]; //System.out.println("msgQ="+ msgQ); if (msgQ == null ||
	 * msgQ.length() <= 0) return null; return msgQ.dequeue(); }
	 * 
	 * }
	 */

	Object[] getSubscriptionContext(Object subscriptionHandle) {
		return subscriptionHandle == null ? null : (Object[]) mSubContextTable.get(subscriptionHandle);
	}

	void cancelSubscription(Object subscriptionHandle) {
		Object[] cx = (Object[]) mSubContextTable.remove(subscriptionHandle);
		if (cx == null || cx.length == 0)
			return;
		Subscription s = (Subscription) cx[0];
		if (s != null) {
			mTrace.log(Trace.DEBUG, "Cancelling Subscription:" + s);
			s.cancel();
		}

	}

	// onError
	// This method is called when an error causes an interruption in the
	// servicing of a subscription.

	/**
	 * SubscriptionHandler callback - This method is for internal use only.
	 */
	public void onError(Subscription s, MicroAgentException e) {
		MethodSubscription msub = s.getMethodSubscription();
		String methodName = msub.getMethodName();
		MicroAgentID mID = s.getMicroAgentID();
		String microAgentName = mID.getDisplayName();
		Object handback = s.getHandback();
		// String ErrorStr = new
		// String("Received Error for this subscription.");

		String msg = PluginResources.getResources().getFormattedString(PluginResources.HAWK_METHOD_SUBSCRIPTION_ERROR,
				new String[] { mID.getAgent().getName(), microAgentName, methodName, e.toString() });

		Object[] cx = (Object[]) mSubContextTable.get(handback);
		if (cx == null) {
			mTrace.log(Trace.DEBUG, "cx is null, msg=" + msg);
			return;
		}

		cx[3] = e;
		if (cx[2] instanceof HawkMethodSubscription) {
			mTrace.log(Trace.DEBUG, msg);
			((HawkMethodSubscription) cx[2]).onSubscriptionStatus(HawkSubscriptionListener.SUBSCRIPTION_ERROR, e);
		} else {
			mTrace.log(Trace.INFO, msg);
		}

	}

	// onErrorCleared
	// This method is called when there is no longer an error
	// condition which is interrupting service.

	/**
	 * SubscriptionHandler callback - This method is for internal use only.
	 */
	public void onErrorCleared(Subscription s) {
		MethodSubscription msub = s.getMethodSubscription();
		String methodName = msub.getMethodName();
		MicroAgentID mID = s.getMicroAgentID();
		String microAgentName = mID.getDisplayName();
		Object handback = s.getHandback();
		// String ErrorClearedStr = new
		// String("Error got cleared for this subscription");

		String msg = PluginResources.getResources().getFormattedString(
				PluginResources.HAWK_METHOD_SUBSCRIPTION_ERROR_CLEARED,
				new String[] { mID.getAgent().getName(), microAgentName, methodName });

		Object[] cx = (Object[]) mSubContextTable.get(handback);
		if (cx == null) {
			mTrace.log(Trace.DEBUG, msg);
			return;
		}

		cx[3] = null;
		if (cx[2] instanceof HawkMethodSubscription) {
			mTrace.log(Trace.DEBUG, msg);
			((HawkMethodSubscription) cx[2]).onSubscriptionStatus(HawkSubscriptionListener.SUBSCRIPTION_ERROR_CLEARED,
					null);
		} else {
			mTrace.log(Trace.INFO, msg);
		}

	}

	// onTermination
	// This method is called when an error condition causes the subscription
	// to be terminated.

	/**
	 * SubscriptionHandler callback - This method is for internal use only.
	 */
	public void onTermination(Subscription s, MicroAgentException e) {
		MethodSubscription msub = s.getMethodSubscription();
		String methodName = msub.getMethodName();
		MicroAgentID mID = s.getMicroAgentID();
		String microAgentName = mID.getDisplayName();
		Object handback = s.getHandback();
		// String SubTermStr = new
		// String("This subscription got terminated. Please check with your TIB/Hawk Administrator.");

		String msg = PluginResources.getResources().getFormattedString(
				PluginResources.HAWK_METHOD_SUBSCRIPTION_TERMINATED,
				new String[] { mID.getAgent().getName(), microAgentName, methodName });

		Object[] cx = (Object[]) mSubContextTable.get(handback);
		if (cx == null) {
			mTrace.log(Trace.DEBUG, msg);
			return;
		}

		if (cx[2] instanceof HawkMethodSubscription) {
			mTrace.log(Trace.DEBUG, msg);
			((HawkMethodSubscription) cx[2]).onSubscriptionStatus(HawkSubscriptionListener.SUBSCRIPTION_TERMINATED,
					null);
		} else {
			mTrace.log(Trace.INFO, msg);
		}
		mSubContextTable.remove(handback);
	}

	void addMATracker(HawkMicroagentTrackerBase tracker) {
		// synchronized (mAgentHashTable)
		// {
		Enumeration e = mAgentHashTable.keys();
		while (e.hasMoreElements()) {
			String agentName = (String) e.nextElement();
			AgentInstance agentInst = (AgentInstance) mAgentHashTable.get(agentName);

			// System.out.println("agentInst=" + agentInst + "agentName = "
			// +agentName);
			MicroAgentID[] MicroAgentList = agentInst.getStatusMicroAgents();
			// System.out.println("MicroAgentList=" + MicroAgentList);
			for (int j = 0; j < MicroAgentList.length; j++) {
				String maName = MicroAgentList[j].getDisplayName();
				String maInst = MicroAgentList[j].getInstance();
				String maFullName = maName + ":" + maInst;
				try {
					tracker.add(agentInst, maFullName);
				} catch (Exception ex) {
					// TODO
				}
			}
		}

		// }
		mMATrackerList.addElement(tracker);

	}

	void destroyMATracker(HawkMicroagentTrackerBase tracker) {
		mMATrackerList.removeElement(tracker);
	}

	void addAlertTracker(HawkEventTrackerBase tracker) {
		if (tracker == null)
			return;
		mEventTrackerList.addElement(tracker);
		for (Enumeration e = mAlertTable.keys(); e.hasMoreElements();) {
			String agentName = (String) e.nextElement();
			Vector alerts = (Vector) mAlertTable.get(agentName);
			if (alerts == null)
				continue;
			for (Enumeration e2 = alerts.elements(); e2.hasMoreElements();) {
				PublisherAlert publisherAlert = (PublisherAlert) e2.nextElement();
				String maName = publisherAlert.mDataSource;
				if (maName == null) {
					// System.out.println("NOMA");
					int idx = publisherAlert.mAlertText.indexOf(sNoSourceErr);
					if (idx < 0)
						continue;
					// System.out.println("IDX="+idx);
					String s = publisherAlert.mAlertText.substring(idx + sNoSourceErr.length());
					idx = s.lastIndexOf(':');
					// System.out.println("IDX="+idx);
					if (isValidMicroagentInstanceNumber(s.substring(idx + 1)))
						maName = s.substring(0, idx);
					else
						maName = s;
					// System.out.println("MA="+maName);
				}
				tracker.add(agentName, maName, publisherAlert);
			}
		}
	}

	void destroyAlertTracker(HawkEventTrackerBase tracker) {
		mEventTrackerList.removeElement(tracker);
	}

	/**
	 * Get the AgentInstance object associated with the specified TIBCO Hawk
	 * agent.
	 * 
	 * @param agentName
	 *            The TIBCO Hawk agent name.
	 * @return The AgentInstance object. It is defined in TIBCO Hawk Console
	 *         API.
	 */

	public AgentInstance getAgentInstance(String agentName) {
		return (AgentInstance) mAgentHashTable.get(agentName);
	}

	/*
	 * agentAlive == true if agent alive or alert change == false if agent dead
	 * alertChangeOnly == true if for alert change only
	 */
	private void updateCluster(boolean agentAlive, boolean alertChangeOnly, AgentInstance agentInst) {
		String cluster = agentInst.getCluster();
		Object[] clusterInfo = (Object[]) mClusterHashTable.get(cluster);
		int[] counters = null;
		if (clusterInfo == null) {
			clusterInfo = new Object[3];
			counters = new int[4];
			clusterInfo[0] = counters;
			clusterInfo[1] = new Vector();
			clusterInfo[2] = new Vector();
			if (agentAlive)
				((Vector) clusterInfo[1]).addElement(agentInst);
			else
				((Vector) clusterInfo[2]).addElement(agentInst);
			mClusterHashTable.put(cluster, clusterInfo);

		} else {
			counters = (int[]) clusterInfo[0];
			if (!alertChangeOnly) {
				if (agentAlive) {
					// ((Vector)clusterInfo[2]).removeElement(agentInst);
					// This doesn't work because instance has been changed.
					for (Enumeration e = ((Vector) clusterInfo[2]).elements(); e != null && e.hasMoreElements();) {
						AgentInstance agInst = (AgentInstance) e.nextElement();
						String agentName = agInst.getAgentID().getName();
						if (agentName.equals(agentInst.getAgentID().getName())) {
							((Vector) clusterInfo[2]).removeElement(agInst);
							break;
						}
					}
					((Vector) clusterInfo[1]).addElement(agentInst);
				} else {
					((Vector) clusterInfo[1]).removeElement(agentInst);
					((Vector) clusterInfo[2]).addElement(agentInst);
				}
			}
		}

		for (int i = 0; i < 4; i++)
			counters[i] = 0;

		for (Enumeration e = ((Vector) clusterInfo[1]).elements(); e != null && e.hasMoreElements();) {
			AgentInstance agInst = (AgentInstance) e.nextElement();
			String agentName = agInst.getAgentID().getName();
			// System.out.println("agent: " + agentName + " cluster:" +
			// cluster);

			switch (agInst.getRuleBaseEngineState()) {
			case AlertState.ALERT_HIGH:
				counters[0]++;
				break;

			case AlertState.ALERT_MEDIUM:
				counters[1]++;
				break;

			case AlertState.ALERT_LOW:
				counters[2]++;
				break;

			case AlertState.NO_ALERT:
				counters[3]++;

			} // switch
			/*
			 * System.out.print("counters"); for (int i = 0; i < 5; i++)
			 * System.out.print( ":" + counters[i] ); System.out.println("");
			 */

		}

	}

	/**
	 * Get all cluster information.
	 * 
	 * @return The data returned include "Cluster", "High Alert",
	 *         "Medium Alert", "Low Alert", "No Alert", "Lost", "Total", i.e.,
	 *         the cluster name, the number of machines in high alert state,
	 *         medium alert state, ..., and the total number machines in this
	 *         cluster.
	 */
	public NamedTabularData getClusterInfo() {
		int rows = mClusterHashTable.size();
		Object[][] r = new Object[rows][7];
		int row = 0;
		for (Enumeration e = mClusterHashTable.keys(); e != null && e.hasMoreElements();) {
			String cluster = (String) e.nextElement();
			Object[] clusterInfo = (Object[]) mClusterHashTable.get(cluster);
			int[] counters = (int[]) clusterInfo[0];
			r[row][0] = cluster;
			int total = 0;
			for (int i = 0; i < 4; i++) {
				r[row][i + 1] = new Integer(counters[i]);
				total += counters[i];
			}
			int nDead = ((Vector) clusterInfo[2]).size();
			r[row][5] = new Integer(nDead);
			total += nDead;
			r[row][6] = new Integer(total);
			row++;
		}
		return new NamedTabularData(sClusterColNames, r);

	}

	/**
	 * Get all Agents in a cluster.
	 * 
	 * @return The data returned include "Agent", "Highest Alert" and "OS Name"
	 */
	public NamedTabularData getAgentsInACluster(String cluster) {
		if (cluster == null)
			return null;
		Object[] clusterInfo = (Object[]) mClusterHashTable.get(cluster);

		if (clusterInfo == null)
			return null;

		String[] colNames = sAgentViewColNames;
		Vector agents = (Vector) clusterInfo[1];
		Vector deadAgents = (Vector) clusterInfo[2];

		String[][] data = new String[agents.size() + deadAgents.size()][colNames.length];

		int i = 0;
		for (Enumeration e = agents.elements(); e != null && e.hasMoreElements(); i++) {
			AgentInstance agInst = (AgentInstance) e.nextElement();
			data[i][0] = agInst.getAgentID().getName();
			// System.out.println("agent: " + agentName + " cluster:" +
			// cluster);
			data[i][2] = agInst.getAgentPlatform().getOsName();

			switch (agInst.getRuleBaseEngineState()) {
			case AlertState.ALERT_HIGH:
				data[i][1] = "alert-high";
				break;

			case AlertState.ALERT_MEDIUM:
				data[i][1] = "alert-medium";
				break;

			case AlertState.ALERT_LOW:
				data[i][1] = "alert-low";
				break;

			case AlertState.NO_ALERT:
				data[i][1] = "";

			} // switch
		}

		for (Enumeration e = deadAgents.elements(); e != null && e.hasMoreElements(); i++) {
			AgentInstance agInst = (AgentInstance) e.nextElement();
			data[i][0] = agInst.getAgentID().getName();
			// System.out.println("agent: " + agentName + " cluster:" +
			// cluster);
			data[i][2] = agInst.getAgentPlatform().getOsName();
			data[i][1] = "alert-dead";
		}

		return new NamedTabularData(colNames, data);

	}

	/**
	 * A method to set the max agent heartbeat interval in seconds.
	 * 
	 * @param maxHeartbeatInterval
	 *            the max agent heartbeat interval in seconds
	 */
	public void setMaxAgentHeartbeatInterval(int maxHeartbeatInterval) {
		if (maxHeartbeatInterval > 0)
			mMaxHeartbeatInterval = maxHeartbeatInterval * 1100; // 10% grace
	}

	/**
	 * A method to check whether the specified agent is alive (has been
	 * discovered). If the time since console instance creation is less than the
	 * max agent heartbeat interval, then this method will be blocked until that
	 * time is passed or the agent is discovered. The default
	 * MaxAgentHeartbeatInterval is 30 seconds.
	 * 
	 * @param agentName
	 *            The TIBCO Hawk agent name
	 * @return true if the agent is alive (has been discovered).
	 */

	public boolean isAgentAlive(String agentName) {
		while (mAgentHashTable.get(agentName) == null) {
			long now = System.currentTimeMillis();
			if ((now - mConsoleInitTime) < mMaxHeartbeatInterval) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ignore) {
				}
			} else
				break;
		}

		return mAgentHashTable.get(agentName) == null ? false : true;
	}

	/**
	 * A method to check whether the specified agent is alive (has been
	 * discovered). This method is similar to isAgentAlive(String agentName)
	 * except that it's not blocked.
	 * 
	 * @param agentName
	 *            The TIBCO Hawk agent name
	 * @return true if the agent is alive (has been discovered).
	 */

	public boolean isAgentAliveNoWait(String agentName) {
		return mAgentHashTable.get(agentName) == null ? false : true;
	}

	/**
	 * Get all TIBCO Hawk agent names.
	 * 
	 * @return It returns an array of TIBCO Hawk agent names for all agents that
	 *         have been discovered.
	 */
	public String[] getAllAgentNames() {
		String[] r = new String[mAgentHashTable.size()];

		int i = 0;
		Enumeration e = mAgentHashTable.keys();
		while (e.hasMoreElements())
			r[i++] = (String) e.nextElement();
		return r;
	}

	/**
	 * Get all TIBCO Hawk MicroAgent names for a specified agent.
	 * 
	 * @param agentName
	 *            The TIBCO Hawk agent name
	 * @param includeInstanceNumber
	 *            indicate whether MicroAgent instance number should be included
	 * @return It returns an array of MicroAgent names for all the MicroAgents
	 *         associated with the specified agent. If instance number is not
	 *         included, duplicated microagent names are removed.
	 */
	public String[] getMicroAgentNames(String agentName, boolean includeInstanceNumber) {
		if (agentName == null || agentName.length() == 0)
			agentName = ContextControl.hostname();

		String[] r = null;
		ArrayList al = null;

		MicroAgentID[] MicroAgentList = null;
		AgentInstance agentInst = (AgentInstance) mAgentHashTable.get(agentName);
		if ((agentInst != null) && ((MicroAgentList = agentInst.getStatusMicroAgents()) != null)) {
			al = new ArrayList();
			for (int j = 0; j < MicroAgentList.length; j++) {
				String maName = MicroAgentList[j].getDisplayName();
				if (includeInstanceNumber) {
					String maInst = MicroAgentList[j].getInstance();
					al.add(maName + ":" + maInst);
				} else {
					if (!al.contains(maName))
						al.add(maName);
				}
			}
		}
		if (al != null) {
			r = new String[al.size()];
			for (int i = 0; i < al.size(); i++)
				r[i] = (String) al.get(i);
		}
		return r;
	}

	/**
	 * Get all TIBCO Hawk MicroAgent names for a specified agent.
	 * 
	 * @param agentName
	 *            The TIBCO Hawk agent name
	 * @return It returns an array of MicroAgent names for all the MicroAgents
	 *         associated with the specified agent. The micragent names include
	 *         instance number.
	 */
	public String[] getMicroAgentNames(String agentName) {
		return getMicroAgentNames(agentName, true);
	}

	/**
	 * Get all TIBCO Hawk MicroAgent info for a specified agent.
	 * 
	 * @param agentName
	 *            The TIBCO Hawk agent name
	 * @return It returns a Table of MicroAgent info for all the MicroAgents
	 *         associated with the specified agent The table returned has
	 *         "MicroAgent Name", "Display Name", and "Instance Number" columns
	 */
	public NamedTabularData getAllMicroAgentInfo(String agentName) {
		if (agentName == null || agentName.length() == 0)
			agentName = ContextControl.hostname();
		if (agentName == null || agentName.length() == 0)
			return null;
		String[][] r = null;
		MicroAgentID[] MicroAgentList = null;
		AgentInstance agentInst = (AgentInstance) mAgentHashTable.get(agentName);
		if ((agentInst != null) && ((MicroAgentList = agentInst.getStatusMicroAgents()) != null)) {
			r = new String[MicroAgentList.length][3];
			for (int j = 0; j < MicroAgentList.length; j++) {
				String maName = MicroAgentList[j].getDisplayName();
				String maInst = MicroAgentList[j].getInstance();
				r[j][0] = maName;
				r[j][1] = MicroAgentList[j].getName();
				r[j][2] = maInst;
			}
		}
		return new NamedTabularData(sMicroAgentViewColNames, r);
	}

	private String impactToString(int impact) {
		switch (impact) {
		case MethodDescriptor.IMPACT_INFO:
			return ("IMPACT_INFO");
		case MethodDescriptor.IMPACT_ACTION:
			return ("IMPACT_ACTION");
		case MethodDescriptor.IMPACT_ACTION_INFO:
			return ("IMPACT_ACTION_INFO");
		default:
			return ("UNKNOWN");
		}
	}

	public MicroAgentID getMicroAgentID(String agentName, String microAgentName) throws HawkConsoleException {
		if (agentName == null || agentName.length() == 0)
			agentName = ContextControl.hostname();
		if (agentName == null || microAgentName == null)
			return null;
		String[][] r = null;
		boolean hasInstance = false;

		int colonPos = microAgentName.lastIndexOf(':');
		if (colonPos > 0) {
			String maInst = microAgentName.substring(colonPos + 1);
			hasInstance = isValidMicroagentInstanceNumber(maInst);
		}

		MicroAgentID ma = null;
		MicroAgentID[] MicroAgentList = null;
		AgentInstance agentInst = (AgentInstance) mAgentHashTable.get(agentName);
		if (agentInst == null) {
			throw new HawkConsoleException(PluginResources.getResources().getFormattedString(
					PluginResources.AGENT_NOT_DISCOVERED, new String[] { agentName }));
		}
		if ((MicroAgentList = agentInst.getStatusMicroAgents()) != null) {
			r = new String[MicroAgentList.length][3];
			for (int j = 0; j < MicroAgentList.length; j++) {
				String maName = MicroAgentList[j].getDisplayName();
				String maInst = MicroAgentList[j].getInstance();
				String realName = MicroAgentList[j].getName();
				if (hasInstance) {
					if (microAgentName.equals(maName + ":" + maInst))
						ma = MicroAgentList[j];
					else if (microAgentName.equals(realName + ":" + maInst))
						ma = MicroAgentList[j];
					if (ma != null)
						break;
				} else {
					if (microAgentName.equals(maName))
						ma = MicroAgentList[j];
					else if (microAgentName.equals(realName))
						ma = MicroAgentList[j];
					// Don't break to get the last one
				}
			}
		}
		return ma;
	}

	/**
	 * Get all TIBCO Hawk method info for a specified MicroAgent.
	 * 
	 * @param agentName
	 *            The TIBCO Hawk agent name
	 * @param microAgentName
	 *            The TIBCO Hawk MicroAgent name
	 * @return It returns a Table of method info for all the methods associated
	 *         with the specified MicroAgents. The table returned has
	 *         "Method Name", "Method Type", "Asyncronous", and "Description"
	 *         columns.
	 */
	public NamedTabularData getAllMethodInfo(String agentName, String microAgentName) {
		return getAllMethodInfo(agentName, microAgentName, true);
	}

	/**
	 * Get all TIBCO Hawk method info for a specified MicroAgent.
	 * 
	 * @param agentName
	 *            The TIBCO Hawk agent name
	 * @param microAgentName
	 *            The TIBCO Hawk MicroAgent name
	 * @param openMethodsOnly
	 *            false if non-open methods are not excluded
	 * @return It returns a Table of method info for all the methods associated
	 *         with the specified MicroAgents. The table returned has
	 *         "Method Name", "Method Type", "Asyncronous", and "Description"
	 *         columns.
	 */
	public NamedTabularData getAllMethodInfo(String agentName, String microAgentName, boolean openMethodsOnly) {
		if (agentName == null || agentName.length() == 0)
			agentName = ContextControl.hostname();
		if (agentName == null || agentName.length() == 0)
			return null;

		if (microAgentName == null)
			return null;

		String[][] r = null;
		MicroAgentID microAgentID = null;
		try {
			microAgentID = getMicroAgentID(agentName, microAgentName);
		} catch (Exception ignore) {
		}

		if (microAgentID == null)
			return null;

		MicroAgentDescriptor microAgentDesc = getMicroAgentDescriptor(microAgentID);
		if (microAgentDesc == null)
			return null;

		MethodDescriptor[] methodDesc = microAgentDesc.getMethodDescriptors();

		int skip = 0;
		for (int n = 0; n < methodDesc.length; n++)
			if ((!methodDesc[n].isOpenMethod()) && openMethodsOnly)
				skip++;
		r = new String[methodDesc.length - skip][4];
		int i = 0;
		for (int n = 0; n < methodDesc.length; n++) {
			if ((!methodDesc[n].isOpenMethod()) && openMethodsOnly)
				continue;
			r[i][0] = methodDesc[n].getName();
			r[i][1] = impactToString(methodDesc[n].getImpact());
			r[i][2] = "" + methodDesc[n].isAsync();
			r[i][3] = methodDesc[n].getDescription();
			i++;
		}

		return new NamedTabularData(sMethodViewColNames, r);
	}

	/**
	 * Get method type for a TIBCO Hawk method of a specified MicroAgent.
	 * 
	 * @param agentName
	 *            The TIBCO Hawk agent name
	 * @param microAgentName
	 *            The TIBCO Hawk MicroAgent name
	 * @return the method type
	 */
	public String getMethodType(String agentName, String microAgentName, String method) throws HawkConsoleException {
		if (agentName == null || agentName.length() == 0)
			agentName = ContextControl.hostname();
		if (agentName == null || agentName.length() == 0)
			return null;

		if (microAgentName == null || method == null)
			return null;

		String[][] r = null;
		MicroAgentID microAgentID = getMicroAgentID(agentName, microAgentName);
		if (microAgentID == null)
			return null;

		MicroAgentDescriptor microAgentDesc = getMicroAgentDescriptor(microAgentID);
		if (microAgentDesc == null)
			return null;

		MethodDescriptor[] methodDesc = microAgentDesc.getMethodDescriptors();

		r = new String[methodDesc.length][4];
		String result = null;

		for (int n = 0; n < methodDesc.length; n++) {
			if (method.equals(methodDesc[n].getName())) {
				result = impactToString(methodDesc[n].getImpact());
				break;
			}
		}

		return result;
	}

	/**
	 * Get MethodDescriptor for a TIBCO Hawk method of a specified MicroAgent.
	 * 
	 * @param agentName
	 *            The TIBCO Hawk agent name
	 * @param microAgentName
	 *            The TIBCO Hawk MicroAgent name
	 * @param methodName
	 *            The TIBCO Hawk method name
	 * @return the method MethodDescriptor instance object
	 */
	public MethodDescriptor getMethodDescriptor(String agentName, String microAgentName, String methodName) {
		if (agentName == null || agentName.length() == 0)
			agentName = ContextControl.hostname();
		if (agentName == null || agentName.length() == 0)
			return null;

		MicroAgentID microAgentID = null;
		try {
			microAgentID = getMicroAgentID(agentName, microAgentName);
		} catch (Exception ignore) {
		}

		if (microAgentID == null)
			return null;

		MicroAgentDescriptor microAgentDesc = getMicroAgentDescriptor(microAgentID);
		return getMethodDescriptor(microAgentDesc, methodName);
	}

	/**
	 * Check whether a TIBCO Hawk method exists for a specified MicroAgent.
	 * 
	 * @param agentName
	 *            The TIBCO Hawk agent name
	 * @param microAgentName
	 *            The TIBCO Hawk MicroAgent name
	 * @param methodName
	 *            The TIBCO Hawk method name
	 * @return true if the TIBCO Hawk method exists for the specified
	 *         MicroAgent.
	 */
	public boolean hasMethod(String agentName, String microAgentName, String methodName) {
		if (getMethodDescriptor(agentName, microAgentName, methodName) == null)
			return false;
		else
			return true;
	}

	/**
	 * Get TIBCO Hawk method detailed description string
	 * 
	 * @param agentName
	 *            The TIBCO Hawk agent name
	 * @param microAgentName
	 *            The TIBCO Hawk MicroAgent name
	 * @param methodName
	 *            The TIBCO Hawk MicroAgent method name
	 * @param indent
	 *            The indent string for showing detailed items
	 * @return It returns a String of detailed method description
	 */
	public String getMethodDescriptionString(String agentName, String microAgentName, String methodName, String indent) {
		if (agentName == null || agentName.length() == 0)
			agentName = ContextControl.hostname();
		if (agentName == null || agentName.length() == 0)
			return null;

		MethodDescriptor mdesc = getMethodDescriptor(agentName, microAgentName, methodName);
		if (mdesc == null)
			return "";

		return mdesc.toFormattedString(indent);

	}

	/**
	 * Get TIBCO Hawk method input parameter value choice info.
	 * 
	 * @param agentName
	 *            The TIBCO Hawk agent name
	 * @param microAgentName
	 *            The TIBCO Hawk MicroAgent name
	 * @param methodName
	 *            The TIBCO Hawk MicroAgent method name
	 * @return It returns a table of input parameter info. The table returned
	 *         has one column for each parameter and only one row is returned.
	 *         The data in each column can be null or a Object[]. If not null,
	 *         it's either the value choices or legal value choices. The
	 *         TableAttr contains a boolean for each paramter to indicate it's a
	 *         legal value choice or not. (Its value is valid only if the
	 *         corresponding data is not null.) The TableAttr also contains
	 *         "_MethodType" for a String method type and "_Async" has "true" or
	 *         "false" to indicate the method is async or not.
	 * 
	 */
	public NamedTabularData getMethodArgumentChoices(String agentName, String microAgentName, String methodName) {
		if (agentName == null || agentName.length() == 0)
			agentName = ContextControl.hostname();
		if (agentName == null || agentName.length() == 0)
			return null;

		MethodDescriptor mdesc = getMethodDescriptor(agentName, microAgentName, methodName);
		if (mdesc == null)
			return null;

		DataDescriptor[] dd = mdesc.getArgumentDescriptors();
		Object[][] r = null;
		if (dd == null)
			dd = new DataDescriptor[0];
		else
			r = new Object[1][dd.length];

		String[] colNames = new String[dd.length];

		NamedTabularData rr = new NamedTabularData(colNames, r);

		for (int n = 0; n < dd.length; n++) {
			colNames[n] = dd[n].getName();

			Object[] legalChoices = null;
			try {
				legalChoices = dd[n].getLegalValueChoices();
			} catch (Exception e) {
				mTrace.log(
						Trace.ERROR,
						TRACE_CATEGORY,
						PluginResources.FAIL_TO_GET_LEGAL_CHOICES,
						PluginResources.getResources().getFormattedString(PluginResources.FAIL_TO_GET_LEGAL_CHOICES,
								new String[] { dd[n].getName(), e.toString() }), e);
			}
			if (legalChoices != null) {
				r[0][n] = legalChoices;
				rr.setTableAttr(colNames[n], new Boolean(true));
				continue;
			} else
				try {
					r[0][n] = dd[n].getValueChoices();
				} catch (Exception e) {
					mTrace.log(
							Trace.ERROR,
							TRACE_CATEGORY,
							PluginResources.FAIL_TO_GET_VALUE_CHOICES,
							PluginResources.getResources().getFormattedString(
									PluginResources.FAIL_TO_GET_VALUE_CHOICES,
									new String[] { dd[n].getName(), e.toString() }), e);
				}

		}
		rr.setTableAttr("_MethodType", impactToString(mdesc.getImpact()));
		rr.setTableAttr("_Async", "" + mdesc.isAsync());

		return rr;
	}

	/**
	 * Get TIBCO Hawk method input parameter types
	 * 
	 * @param agentName
	 *            The TIBCO Hawk agent name
	 * @param microAgentName
	 *            The TIBCO Hawk MicroAgent name
	 * @param methodName
	 *            The TIBCO Hawk MicroAgent method name
	 * @return It returns an array of input parameter types. The array returned
	 *         has one element for each parameter. The data in each elementn is
	 *         a Java Class name for the parameter data type.
	 * 
	 */
	public NamedArray getMethodArgumentTypes(String agentName, String microAgentName, String methodName) {
		if (agentName == null || agentName.length() == 0)
			agentName = ContextControl.hostname();
		if (agentName == null || agentName.length() == 0)
			return null;

		MethodDescriptor mdesc = getMethodDescriptor(agentName, microAgentName, methodName);
		if (mdesc == null)
			return null;

		DataDescriptor[] dd = mdesc.getArgumentDescriptors();
		if (dd == null)
			return null;

		Object[] r = new Object[dd.length];

		String[] colNames = new String[dd.length];

		NamedArray rr = new NamedArray(colNames, r);

		for (int n = 0; n < dd.length; n++) {
			colNames[n] = dd[n].getName();

			try {
				r[n] = dd[n].getType();
			} catch (Exception e) {
				mTrace.log(
						Trace.ERROR,
						TRACE_CATEGORY,
						PluginResources.FAIL_TO_GET_PARAM_TYPES,
						PluginResources.getResources().getFormattedString(PluginResources.FAIL_TO_GET_PARAM_TYPES,
								new String[] { dd[n].getName(), e.toString() }), e);
			}
		}
		return rr;
	}

	/**
	 * Get TIBCO Hawk method output parameter types
	 * 
	 * @param agentName
	 *            The TIBCO Hawk agent name
	 * @param microAgentName
	 *            The TIBCO Hawk MicroAgent name
	 * @param methodName
	 *            The TIBCO Hawk MicroAgent method name
	 * @return It returns an array of output parameter types. The array returned
	 *         has one element for each parameter. The data in each elementn is
	 *         a Java Class name for the parameter data type. The TableAttr also
	 *         contains "_Indexes" of String[] which specifies the method
	 *         indexes.
	 */
	public NamedArray getMethodReturnTypes(String agentName, String microAgentName, String methodName) {
		if (agentName == null || agentName.length() == 0)
			agentName = ContextControl.hostname();
		if (agentName == null || agentName.length() == 0)
			return null;

		MethodDescriptor mdesc = getMethodDescriptor(agentName, microAgentName, methodName);
		if (mdesc == null)
			return null;

		String[] indexes = null;
		String[] colNames = null;
		Object[] r = null;
		DataDescriptor retDataDesc = mdesc.getReturnDescriptor();
		if (retDataDesc instanceof TabularDataDescriptor) {
			indexes = ((TabularDataDescriptor) retDataDesc).getIndexNames();
			colNames = ((TabularDataDescriptor) retDataDesc).getColumnNames();
			r = ((TabularDataDescriptor) retDataDesc).getColumnTypes();
		} else {
			DataDescriptor[] dd = ((CompositeDataDescriptor) retDataDesc).getElementDescriptors();
			if (dd == null)
				return null;

			r = new Object[dd.length];
			colNames = new String[dd.length];
			for (int n = 0; n < dd.length; n++) {
				colNames[n] = dd[n].getName();

				try {
					r[n] = dd[n].getType();
				} catch (Exception e) {
					mTrace.log(
							Trace.ERROR,
							TRACE_CATEGORY,
							PluginResources.FAIL_TO_GET_PARAM_TYPES,
							PluginResources.getResources().getFormattedString(PluginResources.FAIL_TO_GET_PARAM_TYPES,
									new String[] { dd[n].getName(), e.toString() }), e);
				}
			}
		}
		NamedArray rr = new NamedArray(colNames, r);

		if (indexes != null)
			rr.setArrayAttr("_Indexes", indexes);

		return rr;
	}

	/**
	 * Get TIBCO Hawk method index column names
	 * 
	 * @param agentName
	 *            The TIBCO Hawk agent name
	 * @param microAgentName
	 *            The TIBCO Hawk MicroAgent name
	 * @param methodName
	 *            The TIBCO Hawk MicroAgent method name
	 * @return It returns an array of method index column names. The column
	 *         names are ordered by their significance.
	 * 
	 */
	public String[] getMethodIndexNames(String agentName, String microAgentName, String methodName) {
		if (agentName == null || agentName.length() == 0)
			agentName = ContextControl.hostname();
		if (agentName == null || agentName.length() == 0)
			return null;

		MethodDescriptor mdesc = getMethodDescriptor(agentName, microAgentName, methodName);
		if (mdesc == null)
			return null;

		DataDescriptor retDataDesc = mdesc.getReturnDescriptor();
		if (retDataDesc instanceof TabularDataDescriptor) {
			return ((TabularDataDescriptor) retDataDesc).getIndexNames();
		} else
			return null;
	}

	/**
	 * A method to check whether the specified MicroAgent exists in a specified
	 * TIBCO Hawk agent.
	 * 
	 * @param microAgentName
	 *            The name of the TIBCO Hawk MicorAgent to be checked
	 * @param agentName
	 *            The TIBCO Hawk agent name
	 * @return true if the MicroaAgent exists in the specified TIBCO Hawk agent.
	 */
	public boolean hasMicroAgent(String agentName, String microAgentName) {
		MicroAgentID microAgentID = null;
		try {
			microAgentID = getMicroAgentID(agentName, microAgentName);
		} catch (Exception ignore) {
		}

		return microAgentID == null ? false : true;
	}

	public NamedArray getAgentInfo(String agentName, String timeStampFormat) {
		return getAgentInfoByInst((AgentInstance) mAgentHashTable.get(agentName), timeStampFormat);
	}

	NamedArray getAgentInfoByInst(AgentInstance agentInstance, String timeStampFormat) {

		if (agentInstance == null)
			return null;

		AgentID agentID = agentInstance.getAgentID();

		String[] colNames = sAgentInfoColNames;
		String[] r = new String[colNames.length];

		r[0] = agentInstance.getIPAddress();
		r[1] = agentID.getDns();
		r[2] = alertState2Str(agentInstance.getRuleBaseEngineState());
		r[3] = TimeTool.localtime(agentInstance.getStartTime(), timeStampFormat == null ? defaultTimeFormat
				: timeStampFormat);
		r[4] = agentInstance.getAgentPlatform().getOsArch();
		r[5] = agentInstance.getAgentPlatform().getOsName();
		r[6] = agentInstance.getAgentPlatform().getOsVersion();
		StringBuffer rulebases = new StringBuffer();
		RuleBaseStatus[] ruleBaseList = agentInstance.getStatusRuleBases();
		if (ruleBaseList != null)
			for (int j = 0; j < ruleBaseList.length; j++) {
				if (j != 0)
					rulebases.append(", ");
				rulebases.append(ruleBaseList[j].getName());
			}
		r[7] = rulebases.toString();

		return new NamedArray(colNames, r);

	}

	/**
	 * A method to return the domain name
	 */
	public String getDomain() {
		return mDomain;
	}

	/**
	 * A method to check whether the specified security policy class is a valid
	 * one. This method is for internal use only.
	 * 
	 */
	public static boolean isValidSecurityPolicy(String cls) {
		try {
			Class iCls = Class.forName(cls);
			Class sCls = Class.forName("COM.TIBCO.hawk.console.security.HsConsoleInterface");
			return sCls.isAssignableFrom(iCls);
		} catch (Exception ignore) {
			return false;
		}
	}

	/**
	 * A method to return the TIBHawkConsole instance. This method is for
	 * internal use only.
	 * 
	 */
	public TIBHawkConsole getTIBHawkConsole() {
		return mConsole;
	}

	/**
	 * A method to return the active alert cache This method is for internal use
	 * only.
	 * 
	 */
	public Hashtable getActiveAlertCache() {
		return mAlertTable;
	}

	/**
	 * A method to return all discovered Agent AgentInstance's. This method is
	 * for internal use only.
	 * 
	 */
	public AgentInstance[] getAllAgentInstances() {
		AgentInstance[] r = new AgentInstance[mAgentHashTable.size()];

		int i = 0;
		Enumeration e = mAgentHashTable.elements();
		while (e.hasMoreElements())
			r[i++] = (AgentInstance) e.nextElement();
		return r;

	}

	/*
	 * public NamedTabularData getAllAgentInfo() {
	 * 
	 * }
	 */

	/*
	 * public MicroAgentID[] getMicroAgentIDs(AgentInstance agentInstance) {
	 * return agentInstance.getStatusMicroAgents(); }
	 */
	private void removeMicroAgentDescriptor(MicroAgentID microAgentID) {
		if (microAgentID == null)
			return;
		mMicroAgentHashTable.remove(microAgentID.getName() + microAgentID.getChecksum());

	}

	/**
	 * Get MicroAgentDescriptor for the specified MicroAgentID
	 * 
	 * @param microAgentID
	 *            the MicroAgentID specified
	 */
	public MicroAgentDescriptor getMicroAgentDescriptor(MicroAgentID microAgentID) {
		if (microAgentID == null)
			return null;
		MicroAgentDescriptor microAgentDesc = (MicroAgentDescriptor) mMicroAgentHashTable.get(microAgentID.getName()
				+ microAgentID.getChecksum());
		if (microAgentDesc == null) {
			try {
				microAgentDesc = mMicroAgentServer.describe(microAgentID);

				if (microAgentDesc != null && mMicroAgentHashTable.size() < MA_TABLE_MAX_SIZE)
					mMicroAgentHashTable.put(microAgentID.getName() + microAgentID.getChecksum(), microAgentDesc);
			} catch (Exception e) {
				mTrace.log(
						Trace.ERROR,
						PluginResources.FAIL_TO_DESCRIBE_MA,
						PluginResources.getResources()
								.getFormattedString(
										PluginResources.FAIL_TO_DESCRIBE_MA,
										new String[] { microAgentID.getName(), microAgentID.getAgent().toString(),
												e.toString() }));
			}
		}

		return microAgentDesc;
	}

	/**
	 * Get MicroAgentDescriptor for the specified agent name and microagent name
	 * 
	 * @param agentName
	 *            the TIBCO Hawk agent name
	 * @param maName
	 *            the TIBCO Hawk microagent name
	 */
	public MicroAgentDescriptor getMicroAgentDescriptor(String agentName, String maName) throws HawkConsoleException,
			MicroAgentException {
		MicroAgentID microAgentID = getMicroAgentID(agentName, maName);
		return getMicroAgentDescriptor(microAgentID);

	}

	/**
	 * Get MethodDescriptor for the specified MicroAgentDescriptor and method
	 * name
	 * 
	 * @param microAgentDesc
	 *            the TIBCO Hawk MicroAgentDescriptor specified
	 * @param methodName
	 *            the TIBCO Hawk method name
	 */
	public MethodDescriptor getMethodDescriptor(MicroAgentDescriptor microAgentDesc, String methodName) {
		if (microAgentDesc == null)
			return null;

		MethodDescriptor[] methodDesc = microAgentDesc.getMethodDescriptors();

		MethodDescriptor mdesc = null;
		for (int n = 0; n < methodDesc.length; n++) {
			if (methodDesc[n].getName().equals(methodName)) {
				mdesc = methodDesc[n];
				break;
			}
		}
		return mdesc;

	}

	/*
	 * public NamedTabularData getMethodList(MicroAgentDescriptor maDescriptor)
	 * { }
	 * 
	 * 
	 * public NamedTabularData getMethodParameterNames( MethodDescriptor
	 * methodDescriptor) { }
	 * 
	 * public NamedTabularData getChoiceValues( MethodDescriptor
	 * methodDescriptor, String parameterName) { }
	 */

	// ==================================================================

	private void purgeAlerts(String agentName, Vector alerts) {
		long curr = System.currentTimeMillis();
		if (mLastAlertPurgeAgentName != null && mLastAlertPurgeAgentName.equals(agentName)
				&& ((curr - mLastAlertPurgeTime) < mMaxAlertPurgeInterval))
			return;
		mLastAlertPurgeTime = curr;
		mLastAlertPurgeAgentName = agentName;

		HashMap tmp = new HashMap(mMaxAlertPerAgent);
		ArrayList toBeRemoved = new ArrayList();

		for (Enumeration e = alerts.elements(); e.hasMoreElements();) {
			PublisherAlert a = (PublisherAlert) e.nextElement();
			Long id = new Long(a.mAlertID);
			PublisherAlert old = (PublisherAlert) tmp.get(id);
			if (old != null)
				toBeRemoved.add(old);
			tmp.put(id, a);
		}
		if (toBeRemoved.size() > 0) {
			for (Iterator i = toBeRemoved.iterator(); i.hasNext();) {
				PublisherAlert alert = (PublisherAlert) i.next();
				if (mTrace.isLevelOn(Trace.DEBUG))
					mTrace.log(Trace.DEBUG, "Remove dup alert " + alert.mAlertID + " from Agent: " + agentName
							+ " alert: " + alert.mAlertText + " time: " + new Date(alert.mTimeGenerated));
				alerts.remove(alert);
				mAlertPurgeCount++;
				if ((mAlertPurgeCount % mMaxAlertPerAgent) == 0 && mTrace.isLevelOn(Trace.DEBUG))
					mTrace.log(Trace.DEBUG, "Number of alerts purged: " + mAlertPurgeCount);
			}
		}

	}

	/**
	 * Set alert cache purging threshold.
	 * 
	 * @param maxAlertPerAgent
	 *            The threshold number of cached alerts per agent. When the
	 *            number of cached alerts goes beyond the threshold, all
	 *            duplicated alerts (alerts with the same ID) on this agent will
	 *            be purged - only the most recent alerts remain. Alerts with
	 *            different alert ID's will never be purged. The default value s
	 *            50.
	 * @param maxAlertPurgeInterval
	 *            The minimal interval in milliseconds that the purge actions on
	 *            an agent will be taken. This interval is used to prevent too
	 *            frequent purging action on an agent if an agent already has
	 *            more than maxAlertPerAgent threshold number of unique alerts.
	 *            The default value is 5000 ms.
	 * 
	 */
	public void setAlertCachePurgingThreshold(int maxAlertPerAgent, int maxAlertPurgeInterval) {
		if (maxAlertPerAgent >= 20)
			mMaxAlertPerAgent = maxAlertPerAgent;
		if (maxAlertPurgeInterval >= 5)
			mMaxAlertPurgeInterval = maxAlertPurgeInterval;
	}

	static boolean isValidMicroagentInstanceNumber(String instanceNumber) {
		if (instanceNumber == null)
			return false;
		int inst = -1;
		try {
			inst = Integer.parseInt(instanceNumber);
		} catch (Exception any) {
			return false;
		}

		return (inst >= 0 && inst <= 1023) ? true : false;
	}

	/**
	 * This is for internal use only.
	 */
	public void registerPublisherListener(PublisherListener listener) {

		if (listener == null)
			return;

		mPublisherListeners.add(listener);
	}

	/**
	 * This is for internal use only.
	 */
	public void unregisterPublisherListener(PublisherListener listener) {

		if (listener == null)
			return;

		mPublisherListeners.remove(listener);
	}

	/**
	 * This is for internal use only.
	 */
	private List getPublisherListeners() {

		return mPublisherListeners;
	}

	/**
	 * This is for internal use only.
	 */
	public void registerMicroAgentListMonitorListener(MicroAgentListMonitorListener listener) {

		if (listener == null)
			return;

		mMicroAgentListMonitorListeners.add(listener);
	}

	/**
	 * This is for internal use only.
	 */
	public void unregisterMicroAgentListMonitorListener(MicroAgentListMonitorListener listener) {

		if (listener == null)
			return;

		mMicroAgentListMonitorListeners.remove(listener);
	}

	/**
	 * This is for internal use only.
	 */
	private List getMicroAgentListMonitorListeners() {

		return mMicroAgentListMonitorListeners;
	}

	/**
	 * This method parses the Hawk hawkagent.cfg file in the domain home to
	 * return the parameters that can be used to create Hawk Console instance.
	 * 
	 * @param cfgFile
	 *            The Hawk agent config file name
	 * @return a HashMap that contains String values for the following keys:
	 *         HAWK_CONSOLE_PROPERTY_SERVICE, HAWK_CONSOLE_PROPERTY_NETWORK,
	 *         HAWK_CONSOLE_PROPERTY_DAEMON, HAWK_CONSOLE_PROPERTY_EMSTRANSPORT,
	 *         HAWK_CONSOLE_PROPERTY_SECURITY,
	 *         HAWK_CONSOLE_PROPERTY_DOMAIN_HOME, HAWK_AMI_PROPERTY_SERVICE,
	 *         HAWK_AMI_PROPERTY_NETWORK, HAWK_AMI_PROPERTY_DAEMON,
	 *         HAWK_CONSOLE_PROPERTY_AGENT_NAME,
	 *         HAWK_CONSOLE_PROPERTY_AGENT_HEARTBEAT_INTERVAL,
	 *         HAWK_CONSOLE_PROPERTY_CLUSTER_NAME, and parameters in
	 *         HawkConsoleBase.s_ems_ssl_transport_parameters
	 * 
	 * 
	 */
	public static HashMap getConsoleInitParameters(String cfgFile) throws FileNotFoundException, IOException {

		return getConsoleInitParameters(new FileReader(cfgFile));
	}

	public static HashMap getConsoleInitParameters(InputStreamReader isr) throws FileNotFoundException, IOException {

		String traRvService = "7474";
		String traRvNetwork = ";";
		String traRvDaemon = "tcp:7474";
		String securityImpl = null;
		String amiRvService = "7474";
		String amiRvNetwork = ";";
		String amiRvDaemon = "tcp:7474";

		String agentName = null;
		String agentHeartbeatInterval = null;
		String clusterName = null;
		String emsTransport = null;

		HashMap h = new HashMap(10);

		BufferedReader f = new BufferedReader(isr);
		if (f != null) {
			String rvdSessionKey = "-rvd_session";
			String amiSessionKey = "-ami_rvd_session";
			String secImplKey = "-security_policy";
			String agentNameKey = "-agent_name";
			String clusterNameKey = "-cluster";
			String emsTransportKey = "-ems_transport";
			String agentHeartbeatIntervalKey = "-interval";
			String s = null;
			String rvdCfgLine = null;
			String amiCfgLine = null;

			int n = 0;
			String trusted = null;
			while ((s = f.readLine()) != null) {
				s = s.trim();
				if (s.startsWith("#"))
					continue;
				if (s.startsWith("-M com.tibco.tra.tsm.TSM"))
					break;
				if (rvdCfgLine == null && (n = s.indexOf(rvdSessionKey)) >= 0)
					rvdCfgLine = s.substring(n + rvdSessionKey.length()).trim();
				else if (emsTransport == null && (n = s.indexOf(emsTransportKey)) >= 0)
					emsTransport = s.substring(n + emsTransportKey.length()).trim();
				else if (amiCfgLine == null && (n = s.indexOf(amiSessionKey)) >= 0)
					amiCfgLine = s.substring(n + amiSessionKey.length()).trim();
				else if (clusterName == null && (n = s.indexOf(clusterNameKey)) >= 0)
					clusterName = s.substring(n + clusterNameKey.length()).trim();
				else if (agentName == null && (n = s.indexOf(agentNameKey)) >= 0)
					agentName = s.substring(n + agentNameKey.length()).trim();
				else if (agentHeartbeatInterval == null && (n = s.indexOf(agentHeartbeatIntervalKey)) >= 0)
					agentHeartbeatInterval = s.substring(n + agentHeartbeatIntervalKey.length()).trim();
				else if (securityImpl == null && (n = s.indexOf(secImplKey)) >= 0)
					securityImpl = s.substring(n + secImplKey.length()).trim();
				else {
					String[] ss_tbl = s_ems_ssl_transport_parameters;
					for (int i = 0; i < ss_tbl.length; i++) {
						String key = "-" + ss_tbl[i];
						if ((n = s.indexOf(key)) >= 0) {
							String val = s.substring(n + key.length()).trim();
							if (ss_tbl[i].equals("ssl_trusted")) {
								if (trusted == null)
									trusted = val;
								else
									trusted = trusted + ";" + val;
							} else if (ss_tbl[i].equals("ssl_no_verify_hostname")) {
								h.put("ssl_verify_hostname", "disabled");
							} else if (ss_tbl[i].equals("ssl_no_verify_host")) {
								h.put("ssl_verify_host", "disabled");
							} else {
								h.put(ss_tbl[i], val);
							}
						}

					}
				}

			}
			f.close();

			if (trusted != null) {
				h.put("ssl_trusted", trusted);
			}

			if (rvdCfgLine != null) {
				Strtok t = new Strtok(rvdCfgLine);

				traRvService = t.strtok(" ").trim();
				traRvNetwork = t.strtok(" ").trim();
				if (traRvNetwork.equals("\"\"") || traRvNetwork.equals(""))
					traRvNetwork = ";";
				traRvDaemon = t.strtok("").trim(); // get the rest of the line
			}
			if (amiCfgLine != null) {
				Strtok t = new Strtok(amiCfgLine);

				amiRvService = t.strtok(" ").trim();
				amiRvNetwork = t.strtok(" ").trim();
				if (amiRvNetwork.equals("\"\"") || amiRvNetwork.equals(""))
					amiRvNetwork = ";";
				amiRvDaemon = t.strtok(" ").trim();
			}
		}

		h.put(HAWK_CONSOLE_PROPERTY_SERVICE, traRvService);
		h.put(HAWK_CONSOLE_PROPERTY_NETWORK, traRvNetwork);
		h.put(HAWK_CONSOLE_PROPERTY_DAEMON, traRvDaemon);
		h.put(HAWK_CONSOLE_PROPERTY_EMSTRANSPORT, emsTransport);
		h.put(HAWK_CONSOLE_PROPERTY_SECURITY, securityImpl);
		h.put(HAWK_AMI_PROPERTY_SERVICE, amiRvService);
		h.put(HAWK_AMI_PROPERTY_NETWORK, amiRvNetwork);
		h.put(HAWK_AMI_PROPERTY_DAEMON, amiRvDaemon);
		h.put(HAWK_CONSOLE_PROPERTY_AGENT_NAME, agentName);
		h.put(HAWK_CONSOLE_PROPERTY_AGENT_HEARTBEAT_INTERVAL, agentHeartbeatInterval);
		h.put(HAWK_CONSOLE_PROPERTY_CLUSTER_NAME, clusterName);

		return h;

	}

	public int getMethodArguments(Properties props, String agentName, String microAgentName, String methodName) {
		int number = 0;
		MethodDescriptor md = this.getMethodDescriptor(agentName, microAgentName, methodName);
		if (md != null) {
			DataDescriptor[] arguments = md.getArgumentDescriptors();
			if (arguments != null && arguments.length > 0) {
				number = arguments.length;
				if(props!=null){
					for (DataDescriptor dataDescriptor : arguments) {
						props.put(HawkUtil.formatEventName(dataDescriptor.getName()), dataDescriptor.getType());
					}
				}
			}
		}
		return number;
	}

	public int getMethodReturnArgs(Properties props, String agentName, String microAgentName, String methodName) {
		int number = 0;
		MethodDescriptor md = this.getMethodDescriptor(agentName, microAgentName, methodName);
		if (md != null&&md.getReturnDescriptor()!=null) {
			CompositeDataDescriptor dc = (CompositeDataDescriptor) md.getReturnDescriptor();
			DataDescriptor[] dcs = dc.getElementDescriptors();
			if (dcs != null && dcs.length > 0) {
				number = dcs.length;
				for (DataDescriptor dataDescriptor : dcs) {
					props.put(HawkUtil.formatEventName(dataDescriptor.getName()), dataDescriptor.getType());
				}
			}
		}
		return number;
	}

	private ArrayList<String> getArgumentList(String agentName, String microAgentName, String methodName) {
		ArrayList<String> argumentList = new ArrayList<String>();
		MethodDescriptor md = this.getMethodDescriptor(agentName, microAgentName, methodName);
		if (md != null) {
			DataDescriptor[] dcs = md.getArgumentDescriptors();
			if (dcs != null && dcs.length > 0) {
				for (DataDescriptor dataDescriptor : dcs) {
					String type = dataDescriptor.getType();
					String name = dataDescriptor.getName();
					if (!HawkConstants.DESTINATION_PROP_TIMEINTERVAL.equals(name)) {
						argumentList.add(name);
					}
				}
			}
		}
		return argumentList;
	}
	
	public String[] getToolTip(String agentName, String microAgentName, String methodName) {
		String[] toolTips = new String[2];
		toolTips[0] = "";
		toolTips[1] = "";
		MethodDescriptor md = this.getMethodDescriptor(agentName, microAgentName, methodName);
		if (md != null) {
			DataDescriptor[] dcs = md.getArgumentDescriptors();
			if (dcs != null && dcs.length > 0) {
				for (DataDescriptor dataDescriptor : dcs) {
					String type = dataDescriptor.getType();
					String name = dataDescriptor.getName();
					if (!HawkConstants.DESTINATION_PROP_TIMEINTERVAL.equals(name)) {
						toolTips[0]= toolTips[0] + name + "[" + type.substring(type.lastIndexOf(".") + 1) + "];";
						toolTips[1] = toolTips[1]+ name +"=;";
					}
				}
				for (int i = 0; i < toolTips.length; i++) {
					if(toolTips[i].endsWith(";")){
						toolTips[i] = toolTips[i].substring(0,toolTips[i].length()-1);
					}
				}	
			}
		}
		return toolTips;
	}
	
	public boolean isSyncMethod(String agentName, String microAgentName, String methodName){
		boolean isSync = true;
		if(getMethodDescriptor(agentName, microAgentName, methodName)!=null&&getMethodDescriptor(agentName, microAgentName, methodName).isAsync()){
			isSync = false;
		}
		return isSync;
	}

}
