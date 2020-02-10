package com.tibco.cep.studio.core.clusterconfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.tibco.be.util.config.ConfigNS;
import com.tibco.be.util.config.ConfigNS.Elements;
import com.tibco.be.util.config.cdd.BackingStoreConfig;
import com.tibco.cep.studio.core.index.model.EntityElement;

/*
@author ssailapp
@date Nov 29, 2009 11:54:57 AM
 */

public class ClusterConfigModel {

	public class Revision {
        LinkedHashMap<String, String> revValues;
        public Revision() {
            revValues = new LinkedHashMap<String, String>();
            revValues.put(ConfigNS.Elements.VERSION.localName, "1");
            revValues.put(ConfigNS.Elements.AUTHOR.localName, "");
            revValues.put(ConfigNS.Elements.DATE.localName, "");
            revValues.put(ConfigNS.Elements.COMMENT.localName, "");
        }
    }
    Revision revision;

    public class ClusterInfo {
    	public String name;
    	public String messageEncoding;
        public ClusterInfo() {
            name = "";
            messageEncoding = "UTF-8";
        }
    }
    ClusterInfo clusterInfo;

    public class ObjectManagement {
        public static final String MEMORY_MGR = "In Memory";
        public static final String CACHE_MGR = "Cache";
//        public static final String BDB_MGR = "Berkeley DB";

        public BdbOm bdbOm;
        public CacheOm cacheOm;
        public MemoryOm memOm;
        public String activeOm;
        public DbConcepts dbConcepts;
        public LinkedHashMap<String, String> omTypes;

        public ObjectManagement() {
            bdbOm = new BdbOm();
            cacheOm = new CacheOm();
            memOm = new MemoryOm();
            activeOm = MEMORY_MGR;
            dbConcepts = new DbConcepts();
            
            omTypes = new LinkedHashMap<String, String>();
            omTypes.put(ObjectManagement.MEMORY_MGR, Elements.MEMORY_MANAGER.localName);
            omTypes.put(ObjectManagement.CACHE_MGR, Elements.CACHE_MANAGER.localName);
//            omTypes.put(ObjectManagement.BDB_MGR, Elements.BDB_MANAGER.localName);

        }
    }
    public ObjectManagement om;

    public class ObjectManager {
    }

    public class BdbOm extends ObjectManager {
        LinkedHashMap<String, String> bdbProps;
        public BdbOm() {
            bdbProps = new LinkedHashMap<String, String>();
        }
    }

    public class SecurityConfig {
    	
    	public LinkedHashMap<String, String> securityControllerValues;
    	public LinkedHashMap<String, String> securityRequesterValues;
    	
    	public SecurityConfig() {
    		securityControllerValues = new LinkedHashMap<String, String>();
    		securityRequesterValues = new LinkedHashMap<String, String>();
    	}    	 
    }
        
    public class CacheOm extends ObjectManager {
    	public static final String PROVIDER_TIBCO = "TIBCO";
    	public static final String PROVIDER_ORACLE = "Oracle";
    	public static final String PROVIDER_COHERENCE = "Coherence";
        
        public static final String DO_MODE_UI_CACHE = "Cache Only";
        public static final String DO_MODE_UI_MEMORY = "Memory Only";
        public static final String DO_MODE_UI_CACHE_MEMORY = "Cache+Memory";

        public static final String DO_MODE_MODEL_CACHE = "cache";
        public static final String DO_MODE_MODEL_MEMORY = "memory";
        public static final String DO_MODE_MODEL_CACHE_MEMORY = "cacheAndMemory";
        
        public static final String PROVIDER_TIBCO_V2 = "v2";
        public static final String PROVIDER_TIBCO_V3 = "v3";
        
        public String provider;
        public String providerVersion;
        public BackingStore bs;
        public DomainObjects domainObjects;
        public PropertyElementList cacheProps;
        public LinkedHashMap<String, String> values;
        public String objectTableSize;
        public CacheOmSecurityConfig securityConfig = null;
        
        public class BackingStore {
        	public Connection primaryConnection;
            public LinkedHashMap<String, String> values;

            public static final String CACHE_LOADER_ORACLE = "com.tibco.cep.runtime.service.om.impl.coherence.cluster.backingstore.BECoherenceJdbcStore";
            public static final String CACHE_LOADER_TIBCO = "com.tibco.cep.runtime.service.dao.impl.tibas.backingstore.BEDataGridJdbcStore";
            
            public static final String STRATEGY_ORACLE = "oracle";
            public static final String STRATEGY_JDBC = "jdbc";

            public static final String PERSISTENCE_OPTION = "persistence-option";
            
            public BackingStore() {
                primaryConnection = new Connection(true);
                values = new LinkedHashMap<String, String>();
                values.put(PERSISTENCE_OPTION, BackingStoreConfig.PERSISTENCE_OPTION_NONE);
                values.put(Elements.CACHE_ASIDE.localName, "true");
                values.put(Elements.CACHE_LOADER_CLASS.localName, "");
                values.put(Elements.ENFORCE_POOLS.localName, "false");
                values.put(Elements.STRATEGY.localName, STRATEGY_JDBC);
                values.put(Elements.TYPE.localName, BackingStoreConfig.TYPE_ORACLE);
                values.put("data-store-path", "");
                values.put("persistence-policy", BackingStoreConfig.PERSISTENCE_POLICY_ASYNC);
                values.put(Elements.PARALLEL_RECOVERY.localName, "true");
            }
        }

        public class Connection {
        	public boolean isPrimary = true;
            public LinkedHashMap<String, String> values;

            public Connection(boolean isPrimary) {
                this.isPrimary = isPrimary;
                values = new LinkedHashMap<String, String>();
                values.put(Elements.URI.localName, "");
                values.put(Elements.INITIAL_SIZE.localName, "10");
                values.put(Elements.MAX_SIZE.localName, "10");
                values.put(Elements.MIN_SIZE.localName, "10");
            }
        }

        public class DomainObjects {
        	public DomainObjectDefault domainObjDefault;
            public DomainObjectOverrides domainObjOverrides;
            public DomainObjects() {
                domainObjDefault = new DomainObjectDefault();
                domainObjOverrides = new DomainObjectOverrides();
            }
        }
        
        public class DomainObjectDefault {
        	public LinkedHashMap<String, String> values;
            public DomainObjectDefault() {
                values = new LinkedHashMap<String, String>();
                values.put(Elements.DEFAULT_MODE.localName, "cache");
            	values.put(Elements.CHECK_FOR_VERSION.localName, "true");
            	values.put(Elements.CONSTANT.localName, "false");
                values.put(Elements.ENABLE_TRACKING.localName, "true");
            	values.put(Elements.EVICT_ON_UPDATE.localName, "false");
            	values.put(Elements.CACHE_LIMITED.localName, "false");
                values.put(Elements.SUBSCRIBE.localName, "false");
                values.put(Elements.PRE_LOAD_ENABLED.localName, "false");
                values.put(Elements.PRE_LOAD_FETCH_SIZE.localName, "0");
                values.put(Elements.PRE_LOAD_HANDLES.localName, "false");
                values.put(Elements.CONCEPT_TTL_VALUE.localName, "-1");
            }
        }

        public class DomainObjectOverrides {
        	public ArrayList<DomainObject> overrides;
            public DomainObjectOverrides() {
                overrides = new ArrayList<DomainObject>();
            }
        }

        public class DomainObject {
        	public EntityElement entity;
            public LinkedHashMap<String, String> values;
            public DomainObjectBackingStore bs;
        	public LinkedHashMap<String, DomainObjectProperty> props;
        	public List<DomainObjectCompositeIndex> compIdxList;
        	
            public DomainObject() {
            	this(null);
            }
            
            public DomainObject(EntityElement entity) {
                this.entity = entity;
            	values = new LinkedHashMap<String, String>();
            	values.put(Elements.CACHE_LIMITED.localName, "default");
            	values.put(Elements.CHECK_FOR_VERSION.localName, "default");
            	values.put(Elements.CONSTANT.localName, "default");
            	values.put(Elements.ENABLE_TRACKING.localName, "default");
            	values.put(Elements.EVICT_ON_UPDATE.localName, "default");
            	values.put(Elements.MODE.localName, "cache");
                values.put(Elements.PRE_LOAD_ENABLED.localName, "default");
                values.put(Elements.PRE_LOAD_FETCH_SIZE.localName, "0");
                values.put(Elements.PRE_LOAD_HANDLES.localName, "default");
                values.put(Elements.PRE_PROCESSOR.localName, "");
                values.put(Elements.SUBSCRIBE.localName, "default");
                values.put(Elements.CONCEPT_TTL_VALUE.localName, "-1");
               
                if (entity != null)
                	values.put(Elements.URI.localName, entity.getFolder() + entity.getName());
                else 	
                	values.put(Elements.URI.localName, "");
                bs = new DomainObjectBackingStore();
        		props = new LinkedHashMap<String, DomainObjectProperty>();
        		compIdxList = new ArrayList<DomainObjectCompositeIndex>();
            }
            
            public class DomainObjectBackingStore {
            	public LinkedHashMap<String, String> values;
                
            	public DomainObjectBackingStore() {
            		values = new LinkedHashMap<String, String>();
            		values.put(Elements.ENABLED.localName, "true");
            		values.put(Elements.TABLE_NAME.localName, "");
            	}
            }

            public class DomainObjectProperty {
                public String keys[] = new String[] {Elements.INDEX.localName,"encryption", Elements.MAX_SIZE.localName, Elements.REVERSE_REFERENCES.localName};
        		public LinkedHashMap<String, String> values;
        		public DomainObjectProperty () {
        			values = new LinkedHashMap<String, String>();
        			values.put(Elements.INDEX.localName, "false");
        			values.put("encryption", "false");
        			values.put(Elements.MAX_SIZE.localName, "");
            		values.put(Elements.REVERSE_REFERENCES.localName, "true");
        		}
        	}
            
            public class DomainObjectCompositeIndex {
                public String name;
                public String type;
                public List<String> values;

               public DomainObjectCompositeIndex() {
                    this.values = new ArrayList<String>();
                }
                
                public void removeProperty(String propertyName){
                      values.remove(propertyName);
                }
            }
        }

    	public class CacheOmSecurityConfig extends SecurityConfig {
        	public boolean securityEnabled = false;
        	
        	public CacheOmSecurityConfig() {
        		securityEnabled = false;
        	}
    	}
    	
        public CacheOm() {
            provider = PROVIDER_TIBCO;
            providerVersion = PROVIDER_TIBCO_V2;
        	bs = new BackingStore();
            domainObjects = new DomainObjects();
            values = new LinkedHashMap<String, String>();
            values.put(Elements.CACHE_AGENT_QUORUM.localName, "");
            values.put(Elements.BACKUP_COPIES.localName, "1");
            values.put(Elements.ENTITY_CACHE_SIZE.localName, "10000");
            //AS related configurations
            values.put(Elements.DISCOVERY_URL.localName,"");
            values.put(Elements.LISTEN_URL.localName,"");
            values.put(Elements.REMOTE_LISTEN_URL.localName,"");
            values.put(Elements.PROTOCOL_TIMEOUT.localName,"-1");
            values.put(Elements.READ_TIMEOUT.localName,"60000");
            values.put(Elements.WRITE_TIMEOUT.localName,"60000");
            values.put(Elements.LOCK_TIMEOUT.localName,"-1");
            values.put(Elements.SHUTDOWN_WAIT.localName,"8500");
            values.put(Elements.WORKERTHREADS_COUNT.localName,"4");
            values.put(Elements.EXPLICIT_TUPLE.localName,"false");
//            values.put(Elements.STATE_KEEPER_COUNT.localName,"1");
//            values.put(Elements.PROXY_COUNT.localName,"1");
//            values.put(Elements.BASE_DATA_PATH.localName,"");
            objectTableSize = "100000";
            securityConfig = new CacheOmSecurityConfig();
        }
    }

    public class MemoryOm extends ObjectManager {
    }

    public class DbConcepts {
        public LinkedHashMap<String, String> values;
        public ArrayList<String> dburis;
        public DbConcepts() {
            values = new LinkedHashMap<String, String>();
            values.put(Elements.CHECK_INTERVAL.localName, "60");
            values.put(Elements.INACTIVITY_TIMEOUT.localName, "0");
            values.put(Elements.INITIAL_SIZE.localName, "5");
            values.put(Elements.MAX_SIZE.localName, "5");
            values.put(Elements.MIN_SIZE.localName, "5");
            values.put(Elements.PROPERTY_CHECK_INTERVAL.localName, "900");
            values.put(Elements.RETRY_COUNT.localName, "-1");
            values.put(Elements.WAIT_TIMEOUT.localName, "1");

            dburis = new ArrayList<String>();
        }
    }

    public class Property extends PropertyElement {
        public Map <String, String> fieldMap;
        public Property() {
            super();
            fieldMap = new LinkedHashMap<String, String>();
            //presentation.type = PropertyPresentation.TYPE_STRING;
        }
    }

    public class PropertyGroup extends PropertyElement {
    	public ArrayList<PropertyElement> propertyList;
        public String name = "";
        public String comment = "";
        public PropertyGroup(String name) {
            this();
            this.name = name;
        }

        public PropertyGroup() {
            super();
            propertyList = new ArrayList<PropertyElement>();
            //presentation.type = PropertyPresentation.TYPE_GROUP;
        }
    }

    public class PropertyElement {
        //PropertyPresentation presentation;
        public PropertyElement() {
            //presentation = new PropertyPresentation();
        }
    }

    public class PropertyElementList {
    	public ArrayList<PropertyElement> propertyList;
    	public ArrayList<PropertyElement> uiList;
        public PropertyElementList() {
            propertyList = new ArrayList<PropertyElement>();
            uiList = new ArrayList<PropertyElement>();
        }
    }
    public PropertyElementList properties;

    public class Rule extends RuleElement {
    	public Object parent;
        public String uri;
        public boolean isRef;
        public boolean isTemplate;
        public Rule(RulesGrp parentGrp, String uri, boolean isRef, boolean isTemplate) {
            this.parent = parentGrp;
            this.uri = uri;
            this.isRef = isRef;
            this.isTemplate = isTemplate;
        }

        public Rule(DashInfProcAgent agent, String uri, boolean isTemplate) {
            this.parent = agent;
            this.uri = uri;
            this.isRef = false;
            this.isTemplate = isTemplate;
        }
    }

    public class RulesGrp extends RuleElement {
    	public String id;
    	public ArrayList<RuleElement> rules;
        public RulesGrp() {
            rules = new ArrayList<RuleElement>();
        }
    }

    public class RuleElement {
    }

    public class RuleElementsList {
        public ArrayList<RuleElement> ruleElements;
        public RuleElementsList() {
            ruleElements = new ArrayList<RuleElement>();
        }
    }
    RuleElementsList ruleElementsList;

    public class Function extends FunctionElement {
    	public Object parent;
        public String uri;
        public boolean isRef;
        public boolean isStartup;
        public Function(FunctionsGrp parentGrp, String uri, boolean isRef) {
            this.parent = parentGrp;
            this.uri = uri;
            this.isRef = isRef;
        }

        public Function(DashInfProcQueryAgent agent, String uri, boolean isStartup) {
            this.parent = agent;
            this.uri = uri;
            this.isRef = false;
            this.isStartup = isStartup;
        }
    }

    public class FunctionsGrp extends FunctionElement {
    	public String id;
        public ArrayList<FunctionElement> functions;
        public FunctionsGrp() {
            functions = new ArrayList<FunctionElement>();
        }
    }

    public class FunctionElement {
    }

    public class FunctionElementsList {
    	public ArrayList<FunctionElement> functionElements;
        public FunctionElementsList() {
            functionElements = new ArrayList<FunctionElement>();
        }
    }
    public FunctionElementsList functionElementsList;

    public class Destination extends DestinationElement {
        public static final String THREAD_MODEL_SHARED_QUEUE = "shared-queue";
        public static final String THREAD_MODEL_CALLER = "caller";
        public static final String THREAD_MODEL_WORKERS = "workers";

        public Object parent;
        public String id;
        public boolean isRef;
        public Map<String, String> destinationVal;
        public Destination(DestinationsGrp parentGrp, String id, boolean isRef) {
            this.parent = parentGrp;
            this.isRef = isRef;
            initDestination(id);
        }

        public Destination(DashInfProcQueryAgent agent, String id) {
            this.parent = agent;
            this.isRef = false;
            initDestination(id);
        }

        private void initDestination(String id) {
            this.id = id;
            destinationVal = new LinkedHashMap<String, String>();
            destinationVal.put(Elements.PRE_PROCESSOR.localName, "");
            destinationVal.put(Elements.QUEUE_SIZE.localName, "");
            destinationVal.put(Elements.THREAD_COUNT.localName, "");
            destinationVal.put(Elements.THREADING_MODEL.localName, THREAD_MODEL_SHARED_QUEUE);
            destinationVal.put(Elements.THREAD_AFFINITY_RULE_FUNCTION.localName, "");
            destinationVal.put(Elements.URI.localName, "");
        }
    }

    public class DestinationsGrp extends DestinationElement {
        public String id;
        public ArrayList<DestinationElement> destinations;

        public DestinationsGrp() {
            destinations = new ArrayList<DestinationElement>();
        }
    }

    public class DestinationElement {
    }

    public class DestinationElementsList {
        public ArrayList<DestinationElement> destinationElements;
        public DestinationElementsList() {
            destinationElements = new ArrayList<DestinationElement>();
        }
    }
    DestinationElementsList destinationElementsList;

    public class Process extends ProcessElement {
        public Object parent;
        public String uri;
        public boolean isRef;
        
        public Process(ProcessesGrp parentGrp, String uri, boolean isRef) {
            this.parent = parentGrp;
            this.uri = uri;
            this.isRef = isRef;
        }

        public Process(ProcessAgent agent, String uri) {
            this.parent = agent;
            this.uri = uri;
            this.isRef = false;
        }
    }

    public class ProcessesGrp extends ProcessElement {
        public String id;
        public ArrayList<ProcessElement> processes;
        
        public static final String ELEMENT_PROCESS_GROUPS = "process-groups";
        public static final String ELEMENT_PROCESSES = "processes";
        
        public ProcessesGrp() {
        	processes = new ArrayList<ProcessElement>();
        }
    }

    public class ProcessElement {
    }

    public class ProcessElementsList {
        public ArrayList<ProcessElement> processElements;
        public ProcessElementsList() {
        	processElements = new ArrayList<ProcessElement>();
        }
    }
    ProcessElementsList processElementsList;

    
    public class LogConfig {
    	public String id;
        public boolean enabled;
        public String roles;
        public Map<String, String> files;
        public Map<String, String> terminal;
        public Map<String, String> layout;
        public LogConfig() {
            files = new LinkedHashMap<String, String>();
            terminal = new LinkedHashMap<String, String>();
            layout = new LinkedHashMap<String, String>();
            roles = "";
        }
    }

    public class LogConfigsList {
    	public ArrayList<LogConfig> logConfigs;
        public LogConfigsList() {
            logConfigs = new ArrayList<LogConfig>();
        }
    }
    public LogConfigsList logConfigsList;
   

    public class AgentClass {
        public String type;
        public String name;
        public PropertyElementList properties;

        public static final String AGENT_TYPE_CACHE = "Cache";
        public static final String AGENT_TYPE_DASHBOARD = "Dashboard";
        public static final String AGENT_TYPE_INFERENCE = "Inference";
        public static final String AGENT_TYPE_MM = "Monitoring & Management";
        public static final String AGENT_TYPE_PROCESS = "Process";
        public static final String AGENT_TYPE_QUERY = "Query";
        public static final String AGENT_TYPE_LIVEVIEW = "LiveView";

        public AgentClass(String name, String type) {
            this.name = name;
            this.type = type;
            properties = new PropertyElementList();
        }
    }

    public class CacheAgent extends AgentClass {
        public CacheAgent(String name) {
            super(name, AGENT_TYPE_CACHE);
        }
        PropertyGroup props;
    }

    public class DashboardAgent extends DashInfProcAgent {
        public DashboardAgent(String name) {
            super(name, AGENT_TYPE_DASHBOARD);
        }
    }

    public class MMAgent extends AgentClass {
    	public String infAgentRef;
        public String queryAgentRef;
        public AlertConfigList alertConfigList;
        public RuleConfigList ruleConfigList;
        public ActionConfigList actionConfigList;

        public MMAgent(String name) {
            super(name, AGENT_TYPE_MM);
            infAgentRef = "";
            queryAgentRef = "";
            alertConfigList = new AlertConfigList(this);
            ruleConfigList = new RuleConfigList(this);
            actionConfigList = new ActionConfigList(this);
        }

        public class AlertConfigList {
            public ArrayList<AlertConfig> alertConfigs;
            MMAgent agent;
            public AlertConfigList(MMAgent agent) {
                this.agent = agent;
                alertConfigs = new ArrayList<AlertConfig>();
            }

            public class AlertConfig {
            	public Condition condition;
                public Projection projection;
                public AlertConfigList parent;
                public String id;
                public class Condition {
                	public LinkedHashMap<String, String> values;
                    public Condition() {
                        values = new LinkedHashMap<String, String>();
                        values.put("path", "");
                        values.put("name", "");
                        values.put("threshold", "");
                        values.put("reference", "");
                    }
                }

                public class Projection {
                    public ArrayList<LinkedHashMap<String, String>> properties;
                    public Projection() {
                        properties = new ArrayList<LinkedHashMap<String, String>>();
                    }
                }

                public AlertConfig(AlertConfigList parent, String id) {
                    this.parent = parent;
                    this.id = id;
                    condition = new Condition();
                    projection = new Projection();
                }
            }
        }

        public class RuleConfigList {
        	public ArrayList<ClusterMember> clusterMembers;
            public MMAgent agent;
            public RuleConfigList(MMAgent agent) {
                this.agent = agent;
                clusterMembers = new ArrayList<ClusterMember>();
            }

            public class ClusterMember {
            	public String path = "";
                public ArrayList<SetProperty> setProperties;
                public RuleConfigList parent;
                public String id;
                public ClusterMember(RuleConfigList parent, String id) {
                    this.parent = parent;
                    this.id = id;
                    setProperties = new ArrayList<SetProperty>();
                }

                public class SetProperty {
                	public SetPropertyChildElement setPropertyChild;
                    public String name = "healthLevel", value = "";
                    public ClusterMember parent;
                    public ChildClusterMember childClusterMember;
                    public Notification notification;
                    public String id;
                    public SetProperty(ClusterMember parent, String id) {
                        this.parent = parent;
                        this.id = id;
                        childClusterMember = new ChildClusterMember();
                        notification = new Notification();
                        setPropertyChild = childClusterMember;
                    }

                    public class SetPropertyChildElement {
                    	public ArrayList<LinkedHashMap<String, String>> properties;
                        public String tolerance = "";
                        public SetPropertyChildElement() {
                            properties = new ArrayList<LinkedHashMap<String, String>>();
                        }
                    }

                    public class ChildClusterMember extends SetPropertyChildElement {
                    	public String path = "";
                        public ChildClusterMember() {
                            super();
                        }
                    }

                    public class Notification extends SetPropertyChildElement {
                    	public String range = "";
                        public Notification() {
                            super();
                        }
                    }
                }
            }
        }

        public class ActionConfigList {
        	public ArrayList<ActionConfig> actionConfigs;
            public MMAgent agent;
            public ActionConfigList(MMAgent agent) {
                this.agent = agent;
                actionConfigs = new ArrayList<ActionConfig>();
            }

            public class ActionConfig {
            	public TriggerCondition triggerCondition;
                public Action action;
                public ActionConfigList parent;
                public String id;

                public ActionConfig(ActionConfigList parent, String id) {
                    this.parent = parent;
                    this.id = id;
                    triggerCondition = new TriggerCondition();
                    action = new Action();
                }

                public class TriggerCondition {
                	public TriggerConditionElement trigger;
                    public Alert alert;
                    public HealthLevel healthLevel;
                    public TriggerCondition() {
                        alert = new Alert();
                        healthLevel = new HealthLevel();
                        trigger = alert;
                    }
                }

                public class TriggerConditionElement {
                	public String path = "";
                }

                public class Alert extends TriggerConditionElement {
                	public String severity = "";
                }

                public class HealthLevel extends TriggerConditionElement {
                	public String value = "";
                }

                public class Action {
                	public ActionElement actionElement;
                    public ExecCommand execCommand;
                    public SendEmail sendEmail;
                    public Action() {
                        execCommand = new ExecCommand();
                        sendEmail = new SendEmail();
                        actionElement = execCommand;
                    }
                }

                public class ActionElement {
                }

                public class ExecCommand extends ActionElement {
                	public String command = "";
                }

                public class SendEmail extends ActionElement {
                	public LinkedHashMap<String, String> values;
                    public SendEmail() {
                        values = new LinkedHashMap<String, String>();
                        values.put("to", "");
                        values.put("cc", "");
                        values.put("subject", "");
                        values.put("msg", "");
                    }
                }
            }
        }
    }

    public class DashInfProcQueryAgent extends AgentClass {
    	public AgentDestinationsGrp agentDestinationsGrpObj;
        public AgentStartupFunctionsGrp agentStartupFunctionsGrpObj;
        public AgentShutdownFunctionsGrp agentShutdownFunctionsGrpObj;
        public String maxActive;
        public String sharedQueueSize;
        public String sharedQueueWorkers;
        public String localCacheMaxSize;
        public String localCacheEvictionTime;
        
        public DashInfProcQueryAgent(String name, String type) {
            super(name, type);
            agentDestinationsGrpObj = new AgentDestinationsGrp(this);
            agentStartupFunctionsGrpObj = new AgentStartupFunctionsGrp(this);
            agentShutdownFunctionsGrpObj = new AgentShutdownFunctionsGrp(this);
            maxActive = "";
            sharedQueueSize = "1024";
            sharedQueueWorkers = "10";
            localCacheMaxSize = "1024";
            localCacheEvictionTime = "900";
        }

        public class AgentDestinationsGrpElement extends DestinationElement {
        	public DashInfProcQueryAgent parentAgent;
        	public DestinationsGrp destinationsGrp;
            public AgentDestinationsGrpElement(DashInfProcQueryAgent agent, DestinationsGrp destinationsGrp) {
                this.parentAgent = agent;
                this.destinationsGrp = destinationsGrp;
            }
        }

        public class AgentDestinationsGrp {
        	public DashInfProcQueryAgent parentAgent;
            public ArrayList<DestinationElement> agentDestinations;
            public AgentDestinationsGrp(DashInfProcQueryAgent agent) {
                agentDestinations = new ArrayList<DestinationElement>();
                parentAgent = agent;
            }
        }

        public class AgentFunctionsGrpElement extends FunctionElement {
        	public DashInfProcQueryAgent parentAgent;
        	public FunctionsGrp functionsGrp;
            boolean isStartup;
            public AgentFunctionsGrpElement(DashInfProcQueryAgent agent, FunctionsGrp functionsGrp, boolean isStartup) {
                this.parentAgent = agent;
                this.functionsGrp = functionsGrp;
                this.isStartup = isStartup;
            }
        }

        public class AgentBaseFunctionsGrp {
        	public DashInfProcQueryAgent parentAgent;
            public ArrayList<FunctionElement> agentFunctions;
            public AgentBaseFunctionsGrp(DashInfProcQueryAgent agent) {
                parentAgent = agent;
                agentFunctions = new ArrayList<FunctionElement>();
            }
        }

        public class AgentStartupFunctionsGrp extends AgentBaseFunctionsGrp {
            public AgentStartupFunctionsGrp(DashInfProcQueryAgent agent) {
                super(agent);
            }
        }

        public class AgentShutdownFunctionsGrp extends AgentBaseFunctionsGrp {
            public AgentShutdownFunctionsGrp(DashInfProcQueryAgent agent) {
                super(agent);
            }
        }
    }

    public class DashInfProcAgent extends DashInfProcQueryAgent {
    	public AgentRulesGrp agentRulesGrpObj;
        public DashInfProcAgent(String name, String type) {
            super(name, type);
            agentRulesGrpObj = new AgentRulesGrp(this);
        }

        public class AgentRulesGrpElement extends RuleElement {
        	public DashInfProcAgent parentAgent;
            public RulesGrp rulesGrp;
            public AgentRulesGrpElement(DashInfProcAgent agent, RulesGrp rulesGrp) {
                parentAgent = agent;
                this.rulesGrp = rulesGrp;
            }
        }

        public class AgentRulesGrp {
        	public DashInfProcAgent parentAgent;
            public ArrayList<RuleElement> agentRules;
            public AgentRulesGrp(DashInfProcAgent agent) {
                parentAgent = agent;
                agentRules = new ArrayList<RuleElement>();
            }
        }
    }
   
    public class InfAgent extends DashInfProcAgent {
    	public boolean concRtc;
    	public boolean checkForDuplicates;
    	public String bwRepoUrl;
    	
        public InfAgent(String name) {
            this(name, AGENT_TYPE_INFERENCE);
        }

        public InfAgent(String name, String type) {
            super(name, type);
            bwRepoUrl = "";
        }
    }

    public class ProcessAgent extends DashInfProcAgent {

    	public AgentProcessesGrp agentProcessesGrpObj;
        public String jobPoolQueueSize;
        public String jobPoolThreadCount;
    	//public InferenceEngine infEngine;

        public ProcessAgent(String name) {
            super(name, AGENT_TYPE_PROCESS);
            agentProcessesGrpObj = new AgentProcessesGrp(this);
            //infEngine = new InferenceEngine("inf-agent-0");
            jobPoolQueueSize = "";
            jobPoolThreadCount = "";
        }

        public class AgentProcessesGrpElement extends ProcessElement {
        	public ProcessAgent parentAgent;
            public ProcessesGrp processesGrp;
            public AgentProcessesGrpElement(ProcessAgent agent, ProcessesGrp processesGrp) {
                parentAgent = agent;
                this.processesGrp = processesGrp;
            }
        }

        public class AgentProcessesGrp {
            public ProcessAgent parentAgent;
            ArrayList<ProcessElement> agentProcesses;
            public AgentProcessesGrp(ProcessAgent agent) {
                parentAgent = agent;
                agentProcesses = new ArrayList<ProcessElement>();
            }
        }
        
        public class InferenceEngine extends InfAgent {
			public InferenceEngine(String name) {
				super(name);
			}
        }
    }

    public class QueryAgent extends DashInfProcQueryAgent {
        public QueryAgent(String name) {
            super(name, AGENT_TYPE_QUERY);
        }
    }

    ArrayList<AgentClass> agentClasses;

    public class ProcessingUnit {
        public static final String AGENT_REF = "ref";
        public static final String AGENT_KEY = "key";
        public static final String AGENT_PRIORITY = "priority";

        public String name;
        public LogConfig logConfig;
        public boolean hotDeploy;
        public boolean enableCacheStorage;
        public boolean enableDbConcepts;
        public ArrayList<LinkedHashMap<String, String>> agentClasses;
        public HttpProperties httpProperties;
        public PropertyElementList properties;
        public ProcessingUnitSecurityConfig securityConfig = null;
        
        public class ProcessingUnitSecurityConfig extends SecurityConfig {
        	public static final String ROLE_REQUESTER = "Requester";
        	public static final String ROLE_CONTROLLER = "Controller";
        	
        	public String securityRole = null;
        	public LinkedHashMap<String, Boolean> securityControllerOverrides;
        	public LinkedHashMap<String, Boolean> securityRequesterOverrides;
        	
        	public ProcessingUnitSecurityConfig() {
        		securityRole = ROLE_REQUESTER;
        		securityControllerOverrides = new LinkedHashMap<String, Boolean>();
        		securityRequesterOverrides = new LinkedHashMap<String, Boolean>();
        	}        	
        }
        
        public ProcessingUnit() {
            agentClasses = new ArrayList<LinkedHashMap<String,String>>();
            httpProperties = new HttpProperties(this);
            properties = new PropertyElementList();
            hotDeploy = false;
            enableCacheStorage = false;
            enableDbConcepts = false;
            securityConfig = new ProcessingUnitSecurityConfig();
        }

        public String getName(){
            return name;
        }
    }
    ArrayList<ProcessingUnit> procUnits;

    public class LoadBalancerConfig {
    	
    	public static final String ELEMENT_LOAD_BALANCER_CONFIGS = "load-balancer-configs";
    	public static final String ELEMENT_PAIR_CONFIG = "pair-config";
    	public static final String ELEMENT_PAIR_CONFIGS = "pair-configs";
    	public static final String ELEMENT_ADHOC_CONFIG = "adhoc-config";
    	public static final String ELEMENT_ADHOC_CONFIGS = "adhoc-configs";
    	public static final String ELEMENT_CONFIG = "config";
    	
    	public static final String ELEMENT_NAME = "name";
    	public static final String ELEMENT_CHANNEL = "channel";
    	
    	public PropertyElementList properties;
    	public HashMap<String, String> values;
    	

    	public LoadBalancerConfig() {
    		properties = new PropertyElementList();
    		values = new HashMap<String, String>();
    		values.put(ELEMENT_NAME, "");
    		values.put(ELEMENT_CHANNEL, "");
    	}
    }
    
    public class LoadBalancerPairConfig extends LoadBalancerConfig {
    	public static final String ELEMENT_KEY = "key";
    	public static final String ELEMENT_ROUTER = "router";
    	public static final String ELEMENT_RECEIVER = "receiver";
    	    	
    	public LoadBalancerPairConfig() {
    		super();
    		values.put(ELEMENT_KEY, "");
    		values.put(ELEMENT_ROUTER, "");
    		values.put(ELEMENT_RECEIVER, "");
    	}
    }
    
    public class LoadBalancerPairConfigs {
    	public ArrayList<LoadBalancerPairConfig> configs;	
    	
    	public LoadBalancerPairConfigs() {
    		configs = new ArrayList<LoadBalancerPairConfig>();
    	}
    }
    
    public class LoadBalancerAdhocConfig extends LoadBalancerConfig {
    	public LoadBalancerAdhocConfig() {
    		super();
    	}
    }
    
    public class LoadBalancerAdhocConfigs {
    	public ArrayList<LoadBalancerAdhocConfig> configs;	
    	
    	public LoadBalancerAdhocConfigs() {
    		configs = new ArrayList<LoadBalancerAdhocConfig>();
    	}
    }

    public LoadBalancerPairConfigs loadBalancerPairConfigs;
    public LoadBalancerAdhocConfigs loadBalancerAdhocConfigs;
    
    public class HttpProperties {
        public LinkedHashMap<String, String> properties;
        public HttpPropertiesSsl ssl;
        public ProcessingUnit procUnit;

        public HttpProperties(ProcessingUnit procUnit) {
            this.procUnit = procUnit;
            properties = new LinkedHashMap<String,String>();
            properties.put(Elements.CONNECTION_TIMEOUT.localName, "60000");
            properties.put(Elements.ACCEPT_COUNT.localName, "-1");
            properties.put(Elements.CONNECTION_LINGER.localName, "-1");
            properties.put(Elements.SOCKET_OUTPUT_BUFFER_SIZE.localName, "9000");
            properties.put(Elements.MAX_PROCESSORS.localName, "-1");
            properties.put(Elements.TCP_NO_DELAY.localName, "true");
            properties.put(Elements.DOCUMENT_ROOT.localName, "");
            properties.put(Elements.DOCUMENT_PAGE.localName, "");
            properties.put(Elements.STALE_CONNECTION_CHECK.localName, "false");
            ssl = new HttpPropertiesSsl();
        }

        public class HttpPropertiesSsl {
        	public LinkedHashMap<String, String> properties;
            public ArrayList<String> ciphers;
            public ArrayList<String> protocols;

            public HttpPropertiesSsl() {
                properties = new LinkedHashMap<String, String>();
                properties.put(Elements.KEY_MANAGER_ALGORITHM.localName, "");
                properties.put(Elements.TRUST_MANAGER_ALGORITHM.localName, "");
                ciphers = new ArrayList<String>();
                protocols = new ArrayList<String>();
            }
        }
    }
    
    
    public class LiveViewAgent extends DashInfProcAgent {
    	public String publisherQueueSize;
    	public String publisherThreadCount;
    	
    	public LDMConnection ldmConnection;
    	public EntitySetConfig entitySetConfig;
    	
    	public LiveViewAgent(String name) {
			super(name, AGENT_TYPE_LIVEVIEW);
			
			publisherQueueSize = "";
			publisherThreadCount = "";
			
			ldmConnection = new LDMConnection(this);
			entitySetConfig = new EntitySetConfig(this);
		}
    	
    	public class LDMConnection {
    		public String ldmUrl, userName, userPassword;
    		public String initialSize, maxSize;
    		
    		public LiveViewAgent agent;
    		public LinkedHashMap<String, String> values;
    		
    		public LDMConnection(LiveViewAgent lvAgent) {
    			this.agent = lvAgent;
    			
    			this.ldmUrl = "";
    			this.userName = "";
    			this.userPassword = "";
    			this.initialSize = "";
    			this.maxSize = "";
    			
    			values = new LinkedHashMap<String, String>();
    			values.put(Elements.LDM_URL.localName, "");
    			values.put(Elements.SECURITY_USER_NAME.localName, "");
    			values.put(Elements.SECURITY_USER_PASSWORD.localName, "");
    			values.put(Elements.INITIAL_SIZE.localName, "");
    			values.put(Elements.MAX_SIZE.localName, "");
			}
    	}
    	
    	public class EntitySetConfig {
    		public String outputPath;
    		public String generateLVFiles;
    		
    		public LiveViewAgent agent;
    		public List<EntityConfig> entityElements;
    		
    		public EntitySetConfig(LiveViewAgent lvAgent) {
    			this.outputPath = "";
    			this.generateLVFiles = "";
    			this.agent = lvAgent;
				this.entityElements = new ArrayList<EntityConfig>();
			}
    		
    		public class EntityConfig {
    			public String id;
    			public String entityUri;
    			public String filter;
    			public String trimmingRule;
    			public String trimmingField;
    			public String enableTableTrimming;
    			
    			public EntitySetConfig parent;
    			public LinkedHashMap<String, String> values;
    			
    			public EntityConfig(EntitySetConfig entitySetConfig, String id) {
					this.parent = entitySetConfig;
					this.id = id;
					
					this.entityUri = "";
					this.filter = "";
					
					this.trimmingRule = "";
	    			this.trimmingField = "";
	    			this.enableTableTrimming = "";
					
					values = new LinkedHashMap<String, String>();
					values.put(Elements.URI.localName, "");
					values.put(Elements.FILTER.localName, "");
					values.put(Elements.ENABLE_TRIMMING.localName, "false");
					values.put(Elements.TRIMMING_FIELD.localName, "");
					values.put(Elements.TRIMMING_RULE.localName, "");
				}
    		}
    	}
    }
}
