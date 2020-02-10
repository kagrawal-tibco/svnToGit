package com.tibco.be.bemm.functions;

import static com.tibco.be.model.functions.FunctionDomain.*;
import com.tibco.be.bemm.functions.RemoteMetricsCollector.COLLECTOR_TYPE;
import com.tibco.be.util.config.topology.BemmDeploymentUnit;
import com.tibco.be.util.config.topology.BemmMappedHost;
import com.tibco.be.util.config.topology.BemmPU;
import com.tibco.be.util.config.topology.TopologyNS;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.management.Domain;
import com.tibco.cep.runtime.management.DomainKey;
import com.tibco.cep.runtime.management.ManagementTable;
import com.tibco.cep.runtime.management.impl.DefaultManagementCentral;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.service.loader.BEClassLoader;
import com.tibco.cep.runtime.service.management.exception.JMXConnClientException;
import com.tibco.cep.runtime.service.management.jmx.connectors.JMXConnClient;
import com.tibco.cep.runtime.service.management.jmx.connectors.JMXConnUtil;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.util.FQName;
import com.tibco.cep.security.authen.utils.MD5Hashing;
import com.tibco.util.StringUtilities;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import java.io.IOException;
import java.util.*;

@com.tibco.be.model.functions.BEPackage(
        catalog = "BEMM",
        category = "BEMM.topology",
        synopsis = "Functions for browsing the Business Event run-time topology")
public class TopologyHelper {

    static enum DOMAIN_TYPE {
        Site, Cluster, Machine, Process, Agent, MCacheObjects, CacheObject, Concept, Event
    }

    private static TopologyHelper instance;

    //we use a getInstance method to force the creation of the TopologyHelper
    //in a BE engine thread. That will guarantee to give us current rule session
    private static synchronized TopologyHelper getInstance(){
        if (instance == null){
            instance = new TopologyHelper();
        }
        return instance;
    }

    private Map<String,TimerTask> pingerTasks;

    private Map<String,ProcessPinger> processPingers;

    private Map<String,JMXConnClient> efqpToJmxCc;

    private Timer pingerTimer;

    private long delay = -1L;

    private Logger logger;

    private BEClassLoader classLoader;

    private ChannelManager channelManager;

    private TopologyHelper(boolean testing){
        efqpToJmxCc = new HashMap<String, JMXConnClient>();
        pingerTasks = new HashMap<String, TimerTask>();
        processPingers = new HashMap<String, ProcessPinger>();
        pingerTimer = new Timer("topologypinger",true);
        delay = 30000L;
        logger = LogManagerFactory.getLogManager().getLogger(this.getClass());
    }

    private TopologyHelper(){
        efqpToJmxCc = new HashMap<String, JMXConnClient>();
        pingerTasks = new HashMap<String, TimerTask>();
        processPingers = new HashMap<String, ProcessPinger>();
        pingerTimer = new Timer("topologypinger",true);
        RuleServiceProvider currRuleServiceProvider = RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider();
        delay = currRuleServiceProvider.getGlobalVariables().getVariableAsLong("HealthCheckFreq", 30000);
        logger = currRuleServiceProvider.getLogger(this.getClass());
        classLoader = (BEClassLoader) currRuleServiceProvider.getClassLoader();
        channelManager = currRuleServiceProvider.getChannelManager();
    }

    private String[] internalGetRoot(){
        final Set<String> siteNames = getLoader().getSiteNames();
        final String[] sns = new String[siteNames.size()];

        int i=0;
        for (String sn : siteNames) {   //multiple sites support
            sns[i++] = sn + "," + DOMAIN_TYPE.Site;
        }

        return sns;
    }

    private String[] internalGetChildren(String[] elementPath) {
        final String clusterName = (elementPath.length <2 || elementPath[1] == null) ? null
                                   : elementPath[1].trim();

        elementPath = reparse(elementPath);
        List<String> children = new ArrayList<String>();
        try {
            boolean addMCacheObjects = false;
            ManagementTable managementTable =
                    ClusterInfoProvider.getInstance().getManagementTable(clusterName);

            if(managementTable == null){
                return new String[0];
            }

            DOMAIN_TYPE type = getType(elementPath);
            if (type != DOMAIN_TYPE.MCacheObjects) {
                Collection<Domain> domains = managementTable.getDomains();
                for (Domain domain : domains) {
                    if (domain == null){
                        continue;
                    }
                    FQName domainName = domain.safeGet(DomainKey.FQ_NAME);
                    String[] componentNames = domainName.getComponentNames();
                    switch (type) {
                        case Site:
                            if (componentNames.length == 2) {
                                String clustName = componentNames[0];
                                clustName = clustName + "," + DOMAIN_TYPE.Cluster;
                                if (children.contains(clustName) == false) {
                                    children.add(clustName);
                                }
                            }
                            break;
                        case Cluster:
                            if (indexOf(elementPath, componentNames) != -1 && componentNames.length == 2) {
                                String procAndMachineName = componentNames[1];
                                String machineName = procAndMachineName.substring(procAndMachineName.indexOf('@') + 1);
                                machineName = machineName + "," + DOMAIN_TYPE.Machine;
                                if (children.contains(machineName) == false) {
                                    children.add(machineName);
                                }
                                addMCacheObjects = true;
                            }
                            break;
                        case Machine:
                            if (indexOf(elementPath, componentNames) != -1 && componentNames.length == 2) {
                                String procAndMachineName = componentNames[1];
                                String processId = procAndMachineName.substring(0, procAndMachineName.indexOf('@'));
                                processId = processId + "," + DOMAIN_TYPE.Process;
                                if (children.contains(processId) == false) {
                                    children.add(processId);
                                }
                            }
                            break;
                        case Process:
                            if (indexOf(elementPath, componentNames) != -1 && componentNames.length == 4) {
                                String agentName;
                                int index = componentNames[2].indexOf(TopologyNS.CACHESERVER_SUFFIX);
                                if (index != -1) {
                                    agentName = componentNames[2].substring(0, index) + "#" + componentNames[3];
                                } else {
                                    agentName = componentNames[2] + "#" + componentNames[3];
                                }
                                agentName = agentName + "," + DOMAIN_TYPE.Agent;
                                if (children.contains(agentName) == false) {
                                    children.add(agentName);
                                }
                            }
                            break;
                        case Agent:
                            children = Collections.emptyList();
                        default:
                            children = Collections.emptyList();
                    }
                }
            }
            if (!children.isEmpty()) {
                Collections.sort(children);
            }
            //we need to add monitored cache objects to the system
            if ( addMCacheObjects ) {
                children.add("Monitored Objects,"+DOMAIN_TYPE.MCacheObjects);
            }
        } catch (RuntimeException e) {
            if (logger.isEnabledFor(Level.DEBUG)){
                logger.log(Level.DEBUG, "No agents detected in cluster: "+clusterName, e);
            }
            else {
                logger.log(Level.WARN, "No agents detected in cluster: "+clusterName);
            }
        }
        return children.toArray(new String[children.size()]);
    }

    private String[] internalGetProperties(String[] entityFqPath) {
        return getCacheProvider().equals(TopologyLoader.CACHE_MODE.IN_MEMORY) ?
                    getPropsInMemMode(entityFqPath) :
                    getPropsInCacheMode(entityFqPath);
    }

    private String[] getPropsInMemMode(String[] entityFqPath) {
        final DOMAIN_TYPE entityType = getType(entityFqPath);

        //We can only retrieve the MBS of the agent's parent process,
        //not of the process itself.
        if (entityType == DOMAIN_TYPE.Agent)
            entityFqPath = Arrays.copyOf(entityFqPath, 4);

        final MBeanServerConnection mbs = getMbeanServer(entityFqPath, entityType);

        String[] props = entityType == DOMAIN_TYPE.Agent ?
                                       new String[] {"","","","","","",""} :
                                       new String[] {"","","",""};

        Collection<Domain> domains = getDomains(mbs);

        if(domains == null || domains.isEmpty()) {
            logger.log(Level.DEBUG, "Returning empty properties for entity '%s'.",
                                    StringUtilities.join(entityFqPath,"/"));
            return props;
        }

        //Unlike in the Cache Mode case, we do not need to iterate over all domains
        //to see which one matches this entity. Since we are getting the domain using
        //JMX, we immediately get the right set of domains. Since every set of domains
        //contains(repeats) the properties we need, it suffices to retrieve broader set,
        //which is the set that contains DomainKey.DESCRIPTION_CSV.

        Domain domain = (Domain)domains.toArray()[0];

        if(domain.safeGet(DomainKey.DESCRIPTION_CSV) == null)
            domain = (Domain)domains.toArray()[1];

        props[0] = domain.safeGet(DomainKey.HOST_IP_ADDRESS);
        props[1] = domain.safeGet(DomainKey.HOST_PROCESS_ID);
        props[2] = domain.safeGet(DomainKey.JMX_PROPS_CSV);
        props[3] = domain.safeGet(DomainKey.HAWK_PROPS_CSV);

        if (entityType == DOMAIN_TYPE.Agent) {
            String cacheMode = domain.safeGet(DomainKey.DESCRIPTION_CSV);
            cacheMode = cacheMode.split("=")[1].toLowerCase();
            cacheMode = cacheMode.startsWith("cache") ? "cache" : cacheMode;
            props[4] = cacheMode;

            final String[] compNames = ((FQName)domain.safeGet(DomainKey.FQ_NAME)).getComponentNames();

            props[5] = compNames[compNames.length-2];   //AGENT_NAME
            props[6] = compNames[compNames.length-1];   //AGENT_ID
        }

        return props;
    }

    private MBeanServerConnection getMbeanServer(String[] entityFqPath, DOMAIN_TYPE entityType) {
        final String eFqpStr = StringUtilities.join(entityFqPath,"/");
        JMXConnClient jmxcc;
        MBeanServerConnection mbs = null;

        try {
            jmxcc = efqpToJmxCc.get(eFqpStr);

            if (jmxcc != null) {
                mbs = jmxcc.getMBeanServerConnection();
                try {
                    if (mbs!=null &&
                            mbs.isRegistered(new ObjectName(
                                    DefaultManagementCentral.OBJ_NAME_MNGMT_TABLE))) {
                        return mbs;
                    }
                } catch (Exception ignore) { /*if an error occurred, it's handled below*/ }
            }

            //if code gets here it's because MBS could not be retrieved.
            //Close JMX CC and remove it from map.
            try {
                if (jmxcc != null && jmxcc.getJMXConnector() != null) {
                    jmxcc.getJMXConnector().close();
                }
            } catch (IOException e) {
                logger.log(Level.DEBUG, "Exception occurred while trying to close " +
                        "invalid JMX Client Connection to entity: %s", entityFqPath);
            }

            efqpToJmxCc.remove(eFqpStr);

            // ----------------  Create new connection to MBean Server

            String ip="";
            int jmxPort=-1;

            final String[] hostRsrcPath = Arrays.copyOf(entityFqPath, 3);
            final String[] puRsrcPath = Arrays.copyOf(entityFqPath, 4);

            final Object host = getLoader().getEntityObject(hostRsrcPath);

            if (host instanceof BemmMappedHost) {
                final BemmMappedHost mh = (BemmMappedHost) host;
                ip = mh.getHostResource().getIpAddress();

                //this if goes here and not earlier because we need the ip bellow
                if (entityType == DOMAIN_TYPE.Machine) {
                    //Since we do not have the JMX port available at the Machine level,
                    //iterate over all predefined PUs and test every jmx port to see
                    //if there is any there is any BE engine running in this host.
                    OUTER_LOOP : for (Object du : mh.getMappedDUs()) {
                        for (Object puObj : ((BemmDeploymentUnit) du).getMappedPus()){
                            jmxPort = Integer.parseInt(((BemmPU) puObj).getJmxPort());

                            mbs = getJmxCc(eFqpStr, ip, jmxPort).getMBeanServerConnection();
                            if (mbs!= null) {
                                break OUTER_LOOP;
                            }
                        }
                    }
                }
            }

            //When it's not an entity of type 'machine' we don't need to iterate.
            // This code speeds things up a bit.
            if (entityType == DOMAIN_TYPE.Process) {
                final Object pu = getLoader().getEntityObject(puRsrcPath);
                if (pu instanceof BemmPU) {
                    jmxPort = Integer.parseInt(((BemmPU) pu).getJmxPort());
                    mbs = getJmxCc(eFqpStr, ip, jmxPort).getMBeanServerConnection();
                }

                if (mbs != null)
                    mbs = getJmxCc(eFqpStr, ip, jmxPort).getMBeanServerConnection();
            }
        } catch (JMXConnClientException e) {
            return null;
        }

        return mbs;
    }

    //connect to MBean
    private JMXConnClient getJmxCc(String entityFqPath, String ip, int jmxPort) {
        JMXConnClient jmxcc =  efqpToJmxCc.get(entityFqPath);

        if (jmxcc != null)
            return jmxcc;

        final String[] credentials = getSysCredentials(ip, jmxPort);
        jmxcc = new JMXConnClient(ip, jmxPort, credentials[0], credentials[1],true);
        efqpToJmxCc.put(entityFqPath, jmxcc);

        return jmxcc;
    }



    private Collection<Domain> getDomains(MBeanServerConnection mbsc) {
        if (mbsc == null)
            return null;

        try {
            return (Collection<Domain>) mbsc.invoke(
                    new ObjectName(DefaultManagementCentral.OBJ_NAME_MNGMT_TABLE),
                    "getDomains",
                    new Object[]{},
                    new String[]{});
        } catch (Exception e) {
            logger.log(Level.DEBUG, "Exception occurred while getting domains.");
            return null;
        }
    }

    private String[] getSysCredentials(String ip, int port) {
        //get hashed credentials which are used to authenticate system operations
        final String user = (ip == null || ip.trim().equals("")) ?
                JMXConnUtil.HOST.DEFAULT_IP : ip;

        return new String[] { MD5Hashing.getMD5Hash(user), MD5Hashing.getMD5Hash(user+":"+port)} ;
    }

    private String[] getPropsInCacheMode(String[] elementPath) {
        elementPath = reparse(elementPath);
        List<String> children = new ArrayList<String>();
        try {
            String clusterUrl = null;
            if(elementPath.length > 1) clusterUrl = elementPath[1];
            ManagementTable managementTable = ClusterInfoProvider.getInstance().getManagementTable(clusterUrl);
            if(managementTable == null){
                return new String[0];
            }
            Collection<Domain> domains = managementTable.getDomains();
            for (Domain domain : domains) {
                if (domain == null){
                    continue;
                }
                FQName domainName = domain.safeGet(DomainKey.FQ_NAME);
                String[] componentNames = domainName.getComponentNames();
                int idx = indexOf(elementPath, componentNames);
                if (idx != -1) {
                    Object ipAddress = domain.safeGet(DomainKey.HOST_IP_ADDRESS);
                        if (ipAddress == null) {
                        ipAddress = "";
                    }
                    children.add(ipAddress.toString());

                    Object hostProcessID = domain.safeGet(DomainKey.HOST_PROCESS_ID);
                    if (hostProcessID == null){
                        hostProcessID = "";
                    }
                    children.add(hostProcessID.toString());

                    Object managementURI = domain.safeGet(DomainKey.JMX_PROPS_CSV);
                    if (managementURI == null){
                        managementURI = "";
                    }
                    children.add(managementURI.toString());

                    Object hawkProps = domain.safeGet(DomainKey.HAWK_PROPS_CSV);
                    if (hawkProps == null){
                        hawkProps = "";
                    }
                    children.add(hawkProps.toString());

                    if (getType(elementPath) == DOMAIN_TYPE.Agent) {
                        Object description = domain.safeGet(DomainKey.DESCRIPTION_CSV);
                        String[] pairs = description.toString().split(",");
                        for (String pair : pairs) {
                            if (pair.startsWith("nodeType")) {
                                String[] keyValue = pair.split("=");
                                if ("CacheServer".equalsIgnoreCase(keyValue[1])){
                                    keyValue[1] = "cache";
                                }
                                children.add(keyValue[1]);
                            }
                        }
                    }
                    break;
                }
            }
        } catch (RuntimeException e) {
            if (logger.isEnabledFor(Level.DEBUG)){
                logger.log(Level.DEBUG, "could not retrieve property information for "+StringUtilities.join(elementPath, "/"), e);
            }
            else {
                logger.log(Level.WARN, "could not retrieve property information for "+StringUtilities.join(elementPath, "/"));
            }
        }
        return children.toArray(new String[children.size()]);
    }

    private boolean internalRegisterPinger(final String[] elementPath,final String pingFailedEventURI){
        return getCacheProvider().equals(TopologyLoader.CACHE_MODE.IN_MEMORY) ?
                registerPingerInMemMode(elementPath, pingFailedEventURI):
                registerPingerInCacheMode(elementPath, pingFailedEventURI);
    }

    private boolean registerPingerInMemMode(final String[] elementPath,final String pingFailedEventURI){
        final String elPathStr = StringUtilities.join(elementPath,"/");
        final DOMAIN_TYPE type = getType(elementPath);

        TimerTask timerTask = pingerTasks.get(elPathStr);
        boolean success;

        if (timerTask == null){
            switch (type) {
                case Machine:
                    timerTask = registerMachinePinger(elementPath, pingFailedEventURI, elPathStr);
                    break;

                case Process:
                    timerTask = registerProcessPinger(elementPath,pingFailedEventURI, elPathStr);
                    break;

                default:
                    timerTask = null; //not needed - just to make the code more readable
                    break;
            }

            if (timerTask != null) {
                pingerTasks.put(elPathStr, timerTask);
                pingerTimer.schedule(timerTask,delay,delay);
            } else {
                //This messy hack is a consequence of the messy legacy code it depends upon
                if (type == DOMAIN_TYPE.Agent) {
                    //if the pinger is set for the parent process, then is set for all its agents as well;
                     timerTask = pingerTasks.get(elPathStr.substring(0, elPathStr.lastIndexOf("/")));
                }
            }
        }
        success = timerTask != null;

        if (success)
            logger.log(Level.DEBUG, "Health pinger is ACTIVE for %s '%s' running in memory mode", type.toString(), elPathStr);
        else
            logger.log(Level.DEBUG, "Health pinger is INACTIVE for %s '%s' running in memory mode", type.toString(), elPathStr);

        return success;
    }

    private boolean registerPingerInCacheMode(final String[] elementPath,final String pingFailedEventURI){
        boolean success = true;
        final String elPathStr = StringUtilities.join(elementPath,"/");
        TimerTask timerTask = pingerTasks.get(elPathStr);

        if (timerTask == null){
            DOMAIN_TYPE type = getType(elementPath);
            switch (type) {
                case Machine:
                    timerTask = registerMachinePinger(elementPath,pingFailedEventURI, elPathStr);
                    break;

                case Process:
                    if ((timerTask = registerProcessPinger(elementPath,pingFailedEventURI, elPathStr)) == null)
                        success = false;
                    break;

                default:
                    break;
            }
            if (timerTask != null) {
                pingerTasks.put(elPathStr, timerTask);
                pingerTimer.schedule(timerTask,delay,delay);
                logger.log(Level.INFO, "ESTABLISHED health pinger for %s '%s'", type.toString(), elPathStr);
            } else {
                logger.log(Level.WARN, "Could NOT establish health pinger for %s '%s'", type.toString(), elPathStr);
            }
        }
        return success;
    }

    private TimerTask registerMachinePinger(String[] elementPath,
                                            final String pingFailedEventURI,
                                            final String elPathStr) {

        final String[] props = internalGetProperties(elementPath);

        if (props[0].isEmpty())
            return null;

        final TopologyEntityProperties properties =
                new TopologyEntityProperties(logger,elementPath[elementPath.length-1], props);

        final String hawkHome = System.getProperty("tibco.env.HAWK_HOME");
        TimerTask timerTask = null;

        if (properties.parse(COLLECTOR_TYPE.HAWK) && hawkHome != null && hawkHome.trim().length() != 0) {
            final MachinePinger machinePinger = new MachinePinger(properties);
            machinePinger.setMonitoredEntityName(elementPath[elementPath.length-1]);
            machinePinger.setLogger(logger);
            machinePinger.setDomainName(properties.getHawkDomain());

            timerTask = new TimerTask() {

                @Override
                public void run() {
                    if (!machinePinger.initialized){
                        try {
                            machinePinger.init();
                            machinePinger.initialized = true;
                        } catch (Exception e) {
                            this.cancel();
                            return;
                        }
                    }
                    if (machinePinger.ping() == false){
                        try {
                            SimpleEvent event = (SimpleEvent) classLoader.createEntity(pingFailedEventURI);
                            event.setProperty("entityFQName", elPathStr.replaceAll("/", ":"));
                            channelManager.sendEvent(event, event.getDestinationURI(), null);
                            this.cancel();
                        } catch (Exception e) {
                            //cannot do much here other then log the exception
                            logger.log(Level.WARN, "Could not notify that '"+elPathStr+"' is not responding...");
                        }
                    }
                }
            };
        }
        return timerTask;
    }


    private TimerTask registerProcessPinger(String[] elementPath,
                                            final String pingFailedEventURI,
                                            final String elPathStr) {

        final String[] props = internalGetProperties(elementPath);

        if (props[0].isEmpty())
            return null;

        final TopologyEntityProperties properties =
                new TopologyEntityProperties(logger,elementPath[elementPath.length-1], props);

        TimerTask timerTask = null;

        if (properties.parse(COLLECTOR_TYPE.JMX)) {
            final ProcessPinger processPinger = new ProcessPinger(properties);
            processPinger.monitoredEntityName = elementPath[elementPath.length-1];
            processPinger.logger = logger;
            try {
                processPinger.init();
                timerTask = new TimerTask() {

                    @Override
                    public void run() {
                        if (processPinger.ping() == false){
                            try {
                                processPinger.closeConnection();
                                SimpleEvent event = (SimpleEvent) classLoader.createEntity(pingFailedEventURI);
                                event.setProperty("entityFQName", elPathStr.replaceAll("/", ":"));
                                channelManager.sendEvent(event, event.getDestinationURI(), null);
                                this.cancel();
                            } catch (Exception e) {
                                //cannot do much here other then log the exception
                                logger.log(Level.WARN, "Could not notify that "+elPathStr+" is not responding...");
                            }
                        }

                    }

                };
                processPingers.put(elPathStr, processPinger);

            } catch (Exception ex) {
                processPinger.closeConnection();
                return null;
            }
        }
        return timerTask;
    }


    private void internalUnregisterPinger(String[] pathElements) {
        String key = StringUtilities.join(pathElements,"/");
        ProcessPinger pinger = processPingers.get(key);
        if(pinger != null){
            pinger.closeConnection();
            processPingers.remove(key);
        }
        TimerTask timerTask = pingerTasks.get(key);
        if (timerTask != null){
            timerTask.cancel();
            pingerTasks.remove(key);
        }

        //Remove JMX Connections - Only used in In-Memory mode
        if (getCacheProvider().equals(TopologyLoader.CACHE_MODE.IN_MEMORY)) {
            JMXConnClient jmcc = efqpToJmxCc.get(key);
            if (jmcc != null) {
                try {
                    jmcc.getJMXConnector().close();
                } catch (Exception e) {
                    logger.log(Level.DEBUG, "Exception occurred while trying to close " +
                            "invalid JMX Client Connection to entity: %s", key);
                }

                efqpToJmxCc.remove(key);
            }
        }
    }

    private DOMAIN_TYPE getType(String[] pathElements){
        DOMAIN_TYPE type = null;
        switch (pathElements.length){
            case 1 :
                //if (pathElements[0].equals("Site") == true){
                    type = DOMAIN_TYPE.Site;
                //}
                break;
            case 2 :
                type = DOMAIN_TYPE.Cluster;
                break;
            case 3 :
                if (pathElements[2].equals("Monitored Objects") == true){
                    type = DOMAIN_TYPE.MCacheObjects;
                }
                else {
                    type = DOMAIN_TYPE.Machine;
                }
                break;
            case 4 :
                if (pathElements[2].equals("Monitored Objects") == true){
                    type = DOMAIN_TYPE.CacheObject;
                }
                else {
                    type = DOMAIN_TYPE.Process;
                }
                break;
            case 5 :
            case 6 :
                type = DOMAIN_TYPE.Agent;
                break;
        }
        if (type == null){
            throw new IllegalArgumentException("Invalid monitored entity ["+StringUtilities.join(pathElements,"/")+"]");
        }
        return type;
    }

    private String internalGetPUPort(String[] elementPath){
        elementPath = reparse(elementPath);
        String jmx_props = null;
        try {
            String clusterUrl = null;
            if(elementPath.length > 1) clusterUrl = elementPath[1];
            ManagementTable managementTable = ClusterInfoProvider.getInstance().getManagementTable(clusterUrl);
            if(managementTable == null){
                return null;
            }
            Collection<Domain> domains = managementTable.getDomains();
            for (Domain domain : domains) {
                if (domain == null){
                    continue;
                }
                FQName domainName = domain.safeGet(DomainKey.FQ_NAME);
                String[] componentNames = domainName.getComponentNames();
                int idx = indexOf(elementPath, componentNames);
                if (idx != -1) {
                    Object managementURI = domain.safeGet(DomainKey.JMX_PROPS_CSV);
                    if (managementURI != null){
                        jmx_props = managementURI.toString();
                        break;
                    }
                }
            }
            if(jmx_props == null) return null;
            String port = null;
            String[] props = jmx_props.split(",");
            for(int i=0;i<props.length;i++){
                if(props[i]!=null){
                    if(props[i].startsWith("port")){
                        port = props[i].split("=")[1];
                        break;
                    }
                }
            }
            return port;
        } catch (RuntimeException e) {
            if (logger.isEnabledFor(Level.DEBUG)){
                logger.log(Level.DEBUG, "could not retrieve JMX port information for "+StringUtilities.join(elementPath, "/"), e);
            }
            else {
                logger.log(Level.WARN, "could not retrieve JMX port information for "+StringUtilities.join(elementPath, "/"));
            }
            return null;
        }
    }

    private String[] reparse(String[] elementPath) {
        if (getType(elementPath) == DOMAIN_TYPE.Agent){
            int elementPathCnt = elementPath.length;
            int intactPathLength = elementPathCnt-1;
            String[] reparsedPath = new String[elementPathCnt+1];
            System.arraycopy(elementPath, 0, reparsedPath, 0, intactPathLength);
            String[] split = elementPath[intactPathLength].split("#");
            reparsedPath[intactPathLength] = split[0];
            if (split.length > 1){
                reparsedPath[elementPathCnt] = split[1];
            }
            return reparsedPath;
        }
        return elementPath;
    }


    private int indexOf(String[] pathElements,String[] componentNames) {
        int idx = -1;
        int i = 1;
        int componentNameIdx = 0;
        for (i = 1; i < pathElements.length; i++) {
            String pathElement = pathElements[i];
            String componentName = (componentNames.length > componentNameIdx) ? componentNames[componentNameIdx] : null;
            if (componentName == null){
                return -1;
            }
            if (componentName.length() > pathElement.length()) {
                idx = componentName.indexOf(pathElement);
            }
            else {
                idx = pathElement.indexOf(componentName);
            }
            if (idx == -1){
                return idx;
            }
            if (i != 2){
                componentNameIdx++;
            }
        }
        return i-2;
    }

    /**
     * @deprecated
     * @param componentNames
     * @param pathElements
     * @return
     */
    @SuppressWarnings("unused")
    private DOMAIN_TYPE findType(String[] componentNames, String[] pathElements) {
        if (pathElements.length == 1 && pathElements[0].equals("Site") == true) {
            return DOMAIN_TYPE.Site;
        }
        if (pathElements.length == 3 && pathElements[2].equals("Monitored Objects") == true) {
            return DOMAIN_TYPE.MCacheObjects;
        }
        DOMAIN_TYPE type = null;
        int compNamesIdx = 0;
        for (int i = 1; i < pathElements.length; i++) {
            String name = pathElements[i];
            type = null;
            for (int j = compNamesIdx; j < componentNames.length; j++) {
                if (j == 0 && componentNames[j].equals(name) == true) {
                    type = DOMAIN_TYPE.Cluster;
                    compNamesIdx++;
                }
                if (j == 1 && componentNames[j].endsWith(name) == true) {
                    type = DOMAIN_TYPE.Machine;
                }
                if (j == 1 && componentNames[j].startsWith(name) == true) {
                    type = DOMAIN_TYPE.Process;
                    compNamesIdx++;
                }
                if (j == 2 && componentNames.length > 2) {
                    String[] agentElements = name.split("#");
                    if (agentElements.length == 2) {
                        if (componentNames[2].equals(agentElements[0]) == true && componentNames[3].equals(agentElements[1]) == true) {
                            type = DOMAIN_TYPE.Agent;
                            compNamesIdx++;
                        }
                    }
                }
            }
            if (type == null) {
                break;
            }

        }
        return type;
    }
    @com.tibco.be.model.functions.BEFunction(
        name = "getPUPort",
        synopsis = "This function returns the jmx port used by the PU",
        signature = "String getPUPort(String[] elementPath)",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = ""),
        version = "4.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the jmx port used by the PU",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static String getPUPort(String[] elementPath) {
        return getInstance().internalGetPUPort(elementPath);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getRoot",
        synopsis = "This function reads the Business Event run time topology and\nreturns the top level element which is generally a 'site'\nnode",
        signature = "String getRoot()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String[]", desc = ""),
        version = "3.0.2 - AS of 5.1.0 we added support for multiple sites",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the root nodes of the topology - entities of type site",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static String[] getRoot() {
        return getInstance().internalGetRoot();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getChildren",
        synopsis = "This function returns all the children with their type for a\ngiven node. The returned String is of the form 'name,type'\nwhere type can be $1Cluster$1 or $1Machine$1 or $1Process$1 or $1Agent$1 or\n$1Concept$1 or $1Event$1",
        signature = "String[] getChildren(String[] elementPath)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "elementPath", type = "String[]", desc = "The path including name of the node whose children are requested")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String[]", desc = ""),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns all the children under a given node in the topology",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )

    public static String[] getChildren(String[] elementPath) {
        return getInstance().internalGetChildren(elementPath);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getProperties",
        synopsis = "This function returns all the properties for a given node. The\nreturned String array contains the property value(s) in a\npre-defined sequence viz.\n<ol>\n<li>Host Machine IP Address</li>\n<li>Host Process ID</li>\n<li>ManagementURL</li>\n<li>Agent Type - Is valid only for agent nodes</li>\n</ol>",
        signature = "String[] getProperties(String[] elementPath)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "elementPath", type = "String[]", desc = "The path including name of the node whose properties are requested")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String[]", desc = ""),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns all the properties associated with a node in the\ntopology",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static String[] getProperties(String[] elementPath) {
        return getInstance().internalGetProperties(elementPath);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "registerPinger",
        synopsis = "This function registers a pinger to a entity. Only machine and \nprocess level pingers are registered. Machine level pingers are \nHAWK based where as process lebel pingers are JMX based.",
        signature = "boolean registerPinger(String[] elementPath,String pingFailedEventURI)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pingFailedEventURI", type = "The", desc = "URI of the event to fire when the ping fails")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = ""),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Registers a pinger to a entity",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static boolean registerPinger(String[] elementPath,String pingFailedEventURI) {
        return getInstance().internalRegisterPinger(elementPath, pingFailedEventURI);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "unregisterPinger",
        synopsis = "This function unregisters a pinger attached to a an entity.",
        signature = "void unregisterPinger(String[] elementPath)",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Unregisters a pinger attached to a an entity.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )

    public static void unregisterPinger(String[] elementPath) {
        getInstance().internalUnregisterPinger(elementPath);
    }

    static boolean isDomainElement(String type){
        for (DOMAIN_TYPE domainType : DOMAIN_TYPE.values()) {
            if (domainType.toString().equalsIgnoreCase(type) == true){
                return true;
            }
            else if (type.equalsIgnoreCase("inference") == true){
                return true;
            }
            else if (type.equalsIgnoreCase("query") == true){
                return true;
            }
            else if (type.equalsIgnoreCase("cache") == true){
                return true;
            }
        }
        return false;
    }

    private static TopologyLoader getLoader() {
        return TopologyLoader.getInstance();
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "getTopologyChildren",
            synopsis = "This function returns the array of children for this topology node",
            signature = "getTopologyChildren(String parent)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "parent", type = "String", desc = "The parent node")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String[]", desc = ""),
            version = "3.0.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the array of children for this topology node",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
        public static String[] getTopologyChildren(String parent) {
            return getLoader().getTopologyChildren(parent);
        }

        @com.tibco.be.model.functions.BEFunction(
            name = "startProcessUnit",
            synopsis = "This function starts the process unit on the specified machine",
            signature = "String startProcessUnit(String machine, String puid)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "machineFqPath", type = "String", desc = "The machine's FQ name: /site/clustername/machine_name"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "puStIdFqPath", type = "String", desc = "The PU's FQ name: /site/clustername/machine_name/puid")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = ""),
            version = "3.0.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Starts the process unit on the machine specified",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
        public static String startProcessUnit(String machineFqPath, String puStIdFqPath, String username,
                                              String encodedPwd, String uiUserName, String encodedUiPwd ) {
            return getLoader().startProcessUnit(machineFqPath, puStIdFqPath, username, encodedPwd, uiUserName, encodedUiPwd);
        }

        @com.tibco.be.model.functions.BEFunction(
            name = "deployProcessUnit",
            synopsis = "This function deploys the process unit on the specified machine",
            signature = "boolean deployProcessUnit(String machine)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "machineFqPath", type = "String", desc = "The machine's FQ name: /site/clustername/machine_name")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = ""),
            version = "3.0.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Deploy the process unit on the machine specified",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
        //username, password are the credentials to login to the remote host
        //uiUserName, uiPwd are the credentials the user used to login the MM UI.
        public static String deployDeploymentUnits(String machineFqPath, String dusStIdsTokenStr, String username,
                                                   String encodedPwd, String uiUserName, String encodedUiPwd) {
            return getLoader().deployDeploymentUnits(machineFqPath, dusStIdsTokenStr, username, encodedPwd, uiUserName, encodedUiPwd);
        }

        @com.tibco.be.model.functions.BEFunction(
            name = "executeCommand",
            synopsis = "This function executes a command on the machine specified",
            signature = "boolean executeCommand(String machine, String command)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "machineFqPath", type = "String", desc = "The machine's FQ name: /site/clustername/machine_name"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "command", type = "String", desc = "The command to be executed")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = ""),
            version = "4.0.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Execute a command on the machine specified",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
        //username, password are the credentials to login to the remote host
        //uiUserName, uiPwd are the credentials the user used to login the MM UI.
        public static String executeCommand(String machineFqPath, String command, String username,
                                            String encodedPwd, String uiUserName, String encodedUiPwd ) {
            return getLoader().executeCommand(machineFqPath, command, username, encodedPwd, uiUserName, encodedUiPwd);
        }

        @com.tibco.be.model.functions.BEFunction(
            name = "getCacheProvider",
            synopsis = "This function determines and returns the cache provider",
            signature = "getCacheProvider()",
            params = {
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Return provider as tibco or oracle"),
            version = "4.0.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Return provider as tibco or oracle",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
        public static String getCacheProvider() {
            return getLoader().getCacheProvider();
        }

        @com.tibco.be.model.functions.BEFunction(
            name = "getTopoXMLContent",
            synopsis = "This function returns the content of topology xml",
            signature = "getTopoXMLContent()",
            params = {
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = ""),
            version = "4.0.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the content of topology xml",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
        public static String getTopoXMLContent() {
            return getLoader().getTopoXMLContent();
        }

        @com.tibco.be.model.functions.BEFunction(
            name = "getDeployTime",
            synopsis = "This function returns deploy time",
            signature = "getDeployTime()",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "clustName", type = "String", desc = "Cluster Name"),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "hostName", type = "String", desc = "Host Name"),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "duNameAndPuStId", type = "String", desc = "DU name and PU id")

            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = ""),
            version = "4.0.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the deploy time",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
        public static synchronized String getDeployTime(String clustName, String hostName, String duNameAndPuStId) {
            return MMDeployTime.getDeployTime(clustName, hostName, duNameAndPuStId);
        }

        @com.tibco.be.model.functions.BEFunction(
                name = "getDeploymentGV",
                synopsis = "This function returns deployment global variables",
                signature = "getDeploymentGV()",
                params = {
                        @com.tibco.be.model.functions.FunctionParamDescriptor(name = "machine", type = "String", desc = "Machine"),
                        @com.tibco.be.model.functions.FunctionParamDescriptor(name = "duNameAndPuStId", type = "String", desc = "DU name and PU id")

                },
                freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Returns deployment global variables"),
                version = "4.0.0",
                see = "",
                mapper = @com.tibco.be.model.functions.BEMapper(),
                description = "Returns deployment global variables",
                cautions = "",
                fndomain = {ACTION},
                example = ""
            )
        public static String getDeploymentGV(String machine, String dusStIdsTokenStr) {
            return MMGVHelper.getInstance().getDeploymentGV(machine, dusStIdsTokenStr);
        }

        @com.tibco.be.model.functions.BEFunction(
                name = "setDeploymentGV",
                synopsis = "This function sets the deployment global variables",
                signature = "setDeploymentGV()",
                params = {
                        @com.tibco.be.model.functions.FunctionParamDescriptor(name = "machineFqPath", type = "String", desc = "Cluster Name"),
                        @com.tibco.be.model.functions.FunctionParamDescriptor(name = "gvsXml", type = "String", desc = "Global Variables xml")

                },
                freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Sets the deployment global variables"),
                version = "4.0.0",
                see = "",
                mapper = @com.tibco.be.model.functions.BEMapper(),
                description = "Sets the deployment global variables",
                cautions = "",
                fndomain = {ACTION},
                example = ""
            )
        public static String setDeploymentGV(String machineFqPath, String gvsXml) {
            return MMGVHelper.getInstance().setDeploymentGV(machineFqPath, gvsXml);
        }


//	public static void main(String[] args) {
//		TopologyHelper h = new TopologyHelper(true);
//		TopologyHelper.instance = h;
//		//System.out.println(indexOf("Site:BEMMCluster:anpatil-T61:6120:BEMMClusterQuery#2".split(":"),"BEMMCluster:6120@anpatil-T61:BEMMClusterQuery:2".split(":")));
//		//System.out.println(indexOf("Site:BEMMCluster:anpatil-T61:6120:BEMMClusterQuery#2".split(":"),"BEMMCluster:1040@anpatil-T61".split(":")));
//		//System.out.println(indexOf("Site:BEMMCluster:anpatil-T61:6120:BEMMClusterQuery#2".split(":"),"BEMMCluster:6120@anpatil-T61".split(":")));
//		//System.out.println(indexOf("Site:BEMMCluster:anpatil-T61:6120:BEMMClusterQuery#2".split(":"),"BEMMCluster:1040@anpatil-T61:BEMMCluster$CacheServer:1".split(":")));
//		//System.out.println(indexOf("Site:BEMMCluster:anpatil-T61:6120:BEMMClusterQuery#2".split(":"),"BEMMCluster:1040@anpatil-T61:BEMMCluster$CacheServer:1".split(":")));
//		System.out.println(h.indexOf(h.reparse("Site:BEMMCluster:anpatil-T61:6120:BEMMClusterQuery#2".split(":")),"BEMMCluster:6120@anpatil-T61:BEMMCluster:3".split(":")));
//	}
}
