package com.tibco.be.bemm.functions;

import COM.TIBCO.hawk.console.hawkeye.AgentManager;
import COM.TIBCO.hawk.console.hawkeye.ConsoleInitializationException;
import COM.TIBCO.hawk.console.hawkeye.TIBHawkConsole;
import COM.TIBCO.hawk.talon.*;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.event.SimpleEvent;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class HAWKMetricTypeHandler extends MetricTypeHandler {

    static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }


	protected static enum SUPPORTED_OPERATING_SYSTEMS {
		AIX,HPUX,LINUX,SOLARIS,WINDOWS,UNKNOWN
	};

	protected static final String SYS_INFO_AGENT_ID = "COM.TIBCO.hawk.microagent.SysInfo";

	protected static final String OS_METHOD_NAME = "getOperatingSystem";

	protected SUPPORTED_OPERATING_SYSTEMS operatingSystem;

	protected boolean initialized;

	protected String domainName;

	protected AgentManager agentManager;

	protected boolean directedSearch;

	protected MicroAgentID microAgentID;

	protected HAWKMethodInvoker methodInvoker;

	public HAWKMetricTypeHandler() {
		super();
		initialized = false;
		directedSearch = true;
	}

	void setAgentManager(AgentManager agentManager) {
		this.agentManager = agentManager;
	}

	void setDomainName(String domainName){
		this.domainName = domainName;
	}

	void setDirectedSearch(boolean directedSearch) {
		this.directedSearch = directedSearch;
	}

	@Override
	protected void init() throws IOException {
		Thread t = new Thread(new Runnable(){

			public void run() {
				try {
					hawkInit();
				} catch (IOException e) {
					logger.log(Level.WARN, "could not initialize "+type+" handler for "+monitoredEntityName,e);
				} finally {
					initialized = true;
				}

			}},monitoredEntityName+"."+type+".initializer");

		t.start();
	}

	protected void hawkInit() throws IOException {
		logger.log(Level.DEBUG, type+"::Initializing handler...");
		//we will adjust the delay to be 50% of what it is to have a reading before the chart data is read
		delay = (long)(0.50 * delay);
		MicroAgentID sysAgentID = findMicroAgentId(SYS_INFO_AGENT_ID);
		if (sysAgentID != null) {
			HAWKMethodInvoker methodInvocation = new HAWKMethodInvoker(logger,agentManager,sysAgentID, OS_METHOD_NAME,null, -1);
			MicroAgentData returnedData = invoke(sysAgentID, methodInvocation);
			if (returnedData != null && returnedData.getData() instanceof CompositeData){
				CompositeData cData = (CompositeData) returnedData.getData();
				String os = (String) cData.getValue("OS Name");
				logger.log(Level.DEBUG, type+"::Found "+os+" as the operating system...");
				if (os.toLowerCase().indexOf("aix") != -1){
					operatingSystem = SUPPORTED_OPERATING_SYSTEMS.AIX;
				}
				else if (os.toLowerCase().indexOf("hp-ux") != -1){
					operatingSystem = SUPPORTED_OPERATING_SYSTEMS.HPUX;
				}
				else if (os.toLowerCase().indexOf("linux") != -1){
					operatingSystem = SUPPORTED_OPERATING_SYSTEMS.LINUX;
				}
				else if (os.toLowerCase().indexOf("solaris") != -1 || os.toLowerCase().indexOf("sunos") != -1){
					operatingSystem = SUPPORTED_OPERATING_SYSTEMS.SOLARIS;
				}
				else if (os.toLowerCase().indexOf("windows") != -1){
					operatingSystem = SUPPORTED_OPERATING_SYSTEMS.WINDOWS;
				}
				else {
					logger.log(Level.WARN, "Unknown operating system ["+os+"] on "+domainName+"/"+monitoredEntityName);
					operatingSystem = SUPPORTED_OPERATING_SYSTEMS.UNKNOWN;
				}
			}
			else {
				logger.log(Level.WARN, "Received invalid data from "+SYS_INFO_AGENT_ID+"."+OS_METHOD_NAME+" on "+domainName+"/"+monitoredEntityName);
				operatingSystem = SUPPORTED_OPERATING_SYSTEMS.UNKNOWN;
			}
		}
		else {
			//logger.logWarn("Could not find "+SYS_INFO_AGENT_ID+" on "+monitoredEntityName);
			operatingSystem = SUPPORTED_OPERATING_SYSTEMS.UNKNOWN;
		}
	}

	protected SimpleEvent[] populate(EventCreator eventCreator) throws IOException {
		if (initialized == false){
			return null;
		}
		SimpleEvent event = null;
		if (microAgentID != null && methodInvoker != null) {
			try {
				MicroAgentData returnedData = invoke(microAgentID, methodInvoker);
				if (returnedData != null) {
					Map<String, Object> parsedData = new HashMap<String, Object>();
					parseData(returnedData.getData(), parsedData);
					if (parsedData.isEmpty() == false) {
						event = eventCreator.create();
						if (event != null){
							for (String propName : parsedData.keySet()) {
								try {
									event.setProperty(propName, parsedData.get(propName));
								} catch (NoSuchFieldException e) {
									logger.log(Level.WARN, "could not find property named " + propName + " in " + event.getExpandedName());
									return null;
								} catch (Exception e) {
									if (logger.isEnabledFor(Level.DEBUG) == true) {
										logger.log(Level.DEBUG, "could not set value for property named " + propName + " in " + event.getExpandedName(), e);
									}
									else {
										logger.log(Level.WARN, "could not set value for property named " + propName + " in " + event.getExpandedName());
									}
									return null;
								}
							}
						}
						parsedData.clear();
					}
				}
			} catch (Throwable e) {
				logger.log(Level.ERROR, "Error reading data from "+microAgentID+" on "+monitoredEntityName,e);
			}
		}
		if (event == null) {
			return null;
		}
		return new SimpleEvent[] { event };
	}

	protected abstract void parseData(Object data, Map<String, Object> parsedData);

	protected MicroAgentID findMicroAgentId(String agentID) {
		if (agentManager == null){
			return null;
		}
		try {
			if (directedSearch == false) {
				//search the entire hawk domain
				return findMicroAgentIdGlobally(agentID);
			}
			logger.log(Level.DEBUG, "Attempting to find %s on %s/%s...", agentID, domainName, monitoredEntityName);
			long sTime = System.currentTimeMillis();
			//do a specific search using the full monitored entity name e.g. foo.na.tibco.com
			MicroAgentID[] mircoAgentIDs = agentManager.getMicroAgentIDs(monitoredEntityName, null, domainName, agentID, 1);
			long eTime = System.currentTimeMillis();
			logger.log(Level.DEBUG, "Finding %s on %s/%s took %d msec(s)...", agentID, domainName, monitoredEntityName, (eTime - sTime));
			if (mircoAgentIDs == null || mircoAgentIDs.length == 0) {
				if (monitoredEntityName.indexOf(".") != -1) {
					String hostName = monitoredEntityName.substring(0,monitoredEntityName.indexOf("."));
					logger.log(Level.DEBUG, "Attempting to find %s on %s/%s...", agentID, domainName, hostName);
					sTime = System.currentTimeMillis();
					//do a specific search using the part of monitored entity name foo
					mircoAgentIDs = agentManager.getMicroAgentIDs(hostName, null, domainName, agentID, 1);
					eTime = System.currentTimeMillis();
					logger.log(Level.DEBUG, "Finding %s on %s/%s took %d msec(s)...", agentID, domainName, hostName, (eTime - sTime));
				}
				if (mircoAgentIDs == null || mircoAgentIDs.length == 0) {
					//search the entire hawk domain
					return findMicroAgentIdGlobally(agentID);
				}
			}
			logger.log(Level.INFO, "Found MicroAgent[%s] on %s/%s...", agentID, domainName, monitoredEntityName);
			return mircoAgentIDs[0];
		} catch (MicroAgentException e) {
			logger.log(Level.WARN, "could not find MicroAgent[" + agentID + "] on " + domainName+"/"+ monitoredEntityName+"...");
			return null;
		}
	}

	private MicroAgentID findMicroAgentIdGlobally(String agentID) throws MicroAgentException {
		logger.log(Level.DEBUG, "Attempting to find all %s(s) in %s...",agentID,domainName);
		long sTime = System.currentTimeMillis();
		//get all the agents which have the specified HMA
		MicroAgentID[] mircoAgentIDs = agentManager.getMicroAgentIDs(null, null, domainName, agentID, -1);
		long eTime = System.currentTimeMillis();
		logger.log(Level.DEBUG, "Finding all %s(s) on %s took %d msec(s)...",agentID,domainName,(eTime-sTime));
		if (mircoAgentIDs != null && mircoAgentIDs.length != 0) {
			for (MicroAgentID microAgentID : mircoAgentIDs) {
				//check the agent name with the full monitored entity name e.g. foo.na.tibco.com
				if (microAgentID.getAgent().getName().contains(monitoredEntityName) == true) {
					logger.log(Level.DEBUG, "Found MicroAgent[%s] on %s/%s...", agentID, domainName, monitoredEntityName);
					return microAgentID;
				}
			}
			if (monitoredEntityName.indexOf(".") != -1) {
				//check the agent name with the full monitored entity name e.g. foo
				String hostName = monitoredEntityName.substring(0,monitoredEntityName.indexOf("."));
				for (MicroAgentID microAgentID : mircoAgentIDs) {
					if (microAgentID.getAgent().getName().contains(hostName) == true) {
						logger.log(Level.INFO, "Found MicroAgent[%s] on %s/%s...", agentID, domainName, hostName);
						return microAgentID;
					}
				}
			}
		}
		logger.log(Level.WARN, "could not find MicroAgent[%s] on %s/%s...", agentID, domainName, monitoredEntityName);
		return null;
	}

	protected MicroAgentData invoke(MicroAgentID mircoAgentID,HAWKMethodInvoker methodInvoker){
		try {
			MicroAgentData returnedData = methodInvoker.invoke();
			if (returnedData == null) {
				logger.log(Level.WARN, "Invocation of " + mircoAgentID + "." + methodInvoker.getMethodName() + " on " + domainName+"/"+ monitoredEntityName + " returned no data");
				return null;
			}
			Object data = returnedData.getData();
			if (data instanceof MicroAgentException){
				logger.log(Level.WARN, "Invocation of " + mircoAgentID + "." + methodInvoker.getMethodName() + " on " + domainName+"/"+ monitoredEntityName + " failed",(MicroAgentException) data);
				return null;
			}
			return returnedData;
		} catch (MicroAgentException e) {
			logger.log(Level.WARN, "Invocation of " + SYS_INFO_AGENT_ID + "." + OS_METHOD_NAME + " on " + domainName+"/"+ monitoredEntityName + " failed",e);
			e.printStackTrace();
            return null;
		}
	}

	protected Object getValue(DataElement[] dataRow, String name) {
		for (int i = 0; i < dataRow.length; i++) {
			DataElement dataElement = dataRow[i];
			if (dataElement.getName().equals(name) == true) {
				return dataElement.getValue();
			}
		}
		return null;
	}

	protected static void internalMain(String[] args,Class clazz,String type) {
		if (args.length < 4) {
			System.out.println("Invalid arguments...");
			System.out.println("java "+clazz.getName()+" <hawk/administrator domain name> <machine name> <daemon> <service> <network>");
			System.exit(-1);
		}
		String daemon = args[2];
		String service = args[3];
		String network = (args.length > 4) ? args[4] : null;
		Logger logger = LogManagerFactory.getLogManager().getLogger(com.tibco.be.bemm.functions.HAWKMetricTypeHandler.class);
		logger.setLevel(Level.DEBUG);
		HAWKMetricTypeHandler handler = null;
		long oneMin = 1*60*1000L;
		try {
			handler = (HAWKMetricTypeHandler) clazz.newInstance();
			handler.type = type;
			//assign default logger
			handler.logger = logger;
			handler.domainName = args[0];
			handler.monitoredEntityName = args[1];
			handler.logger.log(Level.DEBUG, type+"::Initializing TIBHawkConsole using "+handler.domainName+" as hawk domain ,"+service+" as service,"+network+" as network,"+daemon+" as daemon");
			TIBHawkConsole console = new TIBHawkConsole(handler.domainName, service, network, daemon);
			handler.agentManager = console.getAgentManager();
			handler.logger.log(Level.DEBUG, type+"::Initializing agent manager...");
			handler.agentManager.initialize();
			handler.directedSearch = false;
			handler.hawkInit();
			handler.initialized = true;
			handler.logger.log(Level.DEBUG, type+"::Sleeping for "+oneMin+" msecs to allow HAWK to fetch data...");
			Thread.sleep(oneMin);
			SimpleEvent[] events = handler.populate(new TestingEventCreator());
			if (events != null) {
				for (int i = 0; i < events.length; i++) {
					SimpleEvent event = events[i];
					handler.logger.log(Level.DEBUG, type+"::"+event);
				}
			}
			else {
				handler.logger.log(Level.DEBUG, type+"::No event population occurred...");
			}
		} catch (ConsoleInitializationException e) {
			logger.log(Level.ERROR, "could not initialize TIBHawkConsole", e);
			System.exit(-1);
		} catch (InstantiationException e) {
			logger.log(Level.ERROR, "could not create an instance of "+clazz.getName(), e);
			System.exit(-1);
		} catch (IllegalAccessException e) {
			logger.log(Level.ERROR, "could not create an access "+clazz.getName(), e);
			System.exit(-1);
		} catch (IOException e) {
			if (handler.initialized == false){
				logger.log(Level.ERROR, "could not initialize "+clazz.getName(), e);
			}
			else {
				logger.log(Level.ERROR, "could not fetch request attributes from HAWK", e);
			}
			System.exit(-1);
		} catch (InterruptedException e) {
			logger.log(Level.ERROR, "could not sleep for "+oneMin, e);
			System.exit(-1);
		} finally {
			if (handler != null && handler.agentManager != null){
				handler.agentManager.shutdown();
			}
		}
	}
}