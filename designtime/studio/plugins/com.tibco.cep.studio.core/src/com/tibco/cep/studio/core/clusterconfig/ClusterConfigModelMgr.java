package com.tibco.cep.studio.core.clusterconfig;



import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;

import com.tibco.be.util.config.ConfigNS;
import com.tibco.be.util.config.ConfigNS.Elements;
import com.tibco.be.util.config.cdd.BackingStoreConfig;
import com.tibco.cep.designtime.model.registry.AddOn;
import com.tibco.cep.rt.AddonUtil;
import com.tibco.cep.runtime.util.SystemProperty;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.AgentClass;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.CacheAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.CacheOm;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.CacheOm.BackingStore;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.CacheOm.Connection;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.CacheOm.DomainObject;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.CacheOm.DomainObject.DomainObjectCompositeIndex;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.CacheOm.DomainObject.DomainObjectProperty;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.CacheOm.DomainObjects;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ClusterInfo;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DashInfProcAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DashInfProcAgent.AgentRulesGrp;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DashInfProcAgent.AgentRulesGrpElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DashInfProcQueryAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DashInfProcQueryAgent.AgentBaseFunctionsGrp;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DashInfProcQueryAgent.AgentDestinationsGrp;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DashInfProcQueryAgent.AgentDestinationsGrpElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DashInfProcQueryAgent.AgentFunctionsGrpElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DashboardAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.Destination;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DestinationElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DestinationElementsList;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DestinationsGrp;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.Function;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.FunctionElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.FunctionElementsList;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.FunctionsGrp;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.InfAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.LiveViewAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.LiveViewAgent.EntitySetConfig;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.LiveViewAgent.EntitySetConfig.EntityConfig;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.LiveViewAgent.LDMConnection;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.LoadBalancerAdhocConfig;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.LoadBalancerConfig;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.LoadBalancerPairConfig;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.LogConfig;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.LogConfigsList;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.MMAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.MMAgent.ActionConfigList;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.MMAgent.ActionConfigList.ActionConfig;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.MMAgent.ActionConfigList.ActionConfig.Alert;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.MMAgent.ActionConfigList.ActionConfig.ExecCommand;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.MMAgent.ActionConfigList.ActionConfig.HealthLevel;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.MMAgent.ActionConfigList.ActionConfig.SendEmail;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.MMAgent.AlertConfigList;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.MMAgent.AlertConfigList.AlertConfig;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.MMAgent.RuleConfigList;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.MMAgent.RuleConfigList.ClusterMember;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.MMAgent.RuleConfigList.ClusterMember.SetProperty;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.MMAgent.RuleConfigList.ClusterMember.SetProperty.ChildClusterMember;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.MMAgent.RuleConfigList.ClusterMember.SetProperty.Notification;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ObjectManagement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.Process;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ProcessAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ProcessAgent.AgentProcessesGrp;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ProcessAgent.AgentProcessesGrpElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ProcessElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ProcessElementsList;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ProcessesGrp;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ProcessingUnit;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ProcessingUnit.ProcessingUnitSecurityConfig;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.Property;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.PropertyElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.PropertyElementList;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.PropertyGroup;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.QueryAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.Rule;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.RuleElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.RuleElementsList;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.RulesGrp;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.SecurityConfig;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.util.IdUtil;
import com.tibco.cep.studio.core.util.PersistenceUtil;

/*
@author ssailapp
@date Nov 29, 2009 12:11:16 PM
 */

public class ClusterConfigModelMgr {

    private String path;
    private ArrayList<String> attrPath;
    private ClusterConfigModel model;
    private IClusterConfigModelModifier modifier;
    private NamedNodeMap rootAttr;
    public IProject project;
    private String editorFileName;
    private final String  strCopy="copy_";
    public ClusterConfigModelMgr(IResource resource) {
        this(resource.getProject(), resource.getLocation().toString());
    }
    //Flag to make sure the editor doesn't becomes dirty at the initialization of Backing store tab
    private boolean first=true;

    public ClusterConfigModelMgr(IProject project, String path) {
        this(project, path, null, null);
    }
    
    public ClusterConfigModelMgr(IProject project, String path, IClusterConfigModelModifier modifier, String editorFileName) {
        this.project = project;
        this.path = path;
        this.modifier = modifier;
        this.editorFileName = editorFileName;
    }

    public void parseModel() {
        initModel();
        ClusterConfigModelParser.loadModel(path, this);
        //System.out.println("Done initializing cluster model.");
    }

    public boolean refreshModel(String docString) {
    	String curString = saveModel(false);
    	if (curString.equals(docString))
    		return false;
    		
        initModel();
        ClusterConfigModelParser.loadModelFromString(docString, this);
        return true;
        //System.out.println("Done refreshing cluster model.");
    }

    public void initModel() {
        model = new ClusterConfigModel();
        model.clusterInfo = model.new ClusterInfo();
        model.revision = model.new Revision();
        model.om = model.new ObjectManagement();
        model.ruleElementsList = model.new RuleElementsList();
        model.functionElementsList = model.new FunctionElementsList();
        model.destinationElementsList = model.new DestinationElementsList();
        model.processElementsList = model.new ProcessElementsList();
        model.logConfigsList = model.new LogConfigsList();
        model.agentClasses = new ArrayList<AgentClass>();
        model.procUnits = new ArrayList<ProcessingUnit>();
        model.loadBalancerPairConfigs = model.new LoadBalancerPairConfigs();
        model.loadBalancerAdhocConfigs = model.new LoadBalancerAdhocConfigs();
        model.properties = model.new PropertyElementList();
    }

    public String saveModel(boolean updateVersion) {
        if (updateVersion) {
            incrementVersion();
        }
        Document doc = ClusterConfigModelParser.getSaveDocument(rootAttr);
        ClusterConfigModelParser.saveModelParts(doc, this);
        String docString = PersistenceUtil.getDocumentString(doc);
        return docString;
    }

    private void incrementVersion() {
        String versionStr = getRevisionVersion();
        int version = 0;
        try {
            version = new Integer(versionStr).intValue();
        } catch (Exception e) {
        }
        version++;
        updateRevision(Elements.VERSION.localName, new Integer(version).toString());
    }

    public void initAttrPath() {
        attrPath = new ArrayList<String>();
    }

    public String[] getAttrPath() {
        return (attrPath.toArray(new String[0]));
    }

    public void setRootAttributes(NamedNodeMap map) {
        rootAttr = map;
    }

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void modified() {
        setRevision("date", getDateTime());
        if (modifier != null) {
        	modifier.modified();
        }
    }

    public void initializeModel(String path, Map<String,String> attrMap, String key, String value) {
        if (attrMap != null) {
            for (Map.Entry<String, String> attr: attrMap.entrySet()) {
                if (attr.getKey().equals("id")) {
                    attrPath.add(attr.getValue());
                }
            }
        }

        if (path.equalsIgnoreCase(Elements.REVISION.localName)) {
            setRevision(key, value);
        } else if (path.startsWith(Elements.OBJECT_MANAGEMENT.localName)) {
            setOm(path, key, value);
        } else if (path.startsWith(Elements.RULESETS.localName)) {
            setRuleGroup(path, attrPath, key, value);
        } else if (path.startsWith(Elements.DESTINATION_GROUPS.localName)) {
            setDestinations(path, attrPath, key, value);
        } else if (path.startsWith(Elements.FUNCTION_GROUPS.localName)) {
            setFunctions(path, attrPath, key, value);
        } else if (path.startsWith(ProcessesGrp.ELEMENT_PROCESS_GROUPS)) {
            setProcesses(path, attrPath, key, value);
        } else if (path.startsWith(Elements.AGENT_CLASSES.localName)) {
            setAgentClasses(path, attrPath, key, value);
        }
    }


    // ************ Revision ***************
    private void setRevision(String key, String value) {
        model.revision.revValues.put(key, value);
    }

    public String getRevisionVersion() {
        String version = getStringValue(model.revision.revValues.get(Elements.VERSION.localName));
        return version;
    }

    public String getRevisionAuthor() {
        return getStringValue(model.revision.revValues.get(Elements.AUTHOR.localName));
    }

    public String getRevisionDate() {
        return getStringValue(model.revision.revValues.get(Elements.DATE.localName));
    }

    public String getRevisionComment() {
        return getStringValue(model.revision.revValues.get(Elements.COMMENT.localName));
    }

    public LinkedHashMap<String,String> getRevisionMap() {
        return model.revision.revValues;
    }

    public void updateRevision(String key, String value) {
        if (!value.equals(model.revision.revValues.get(key))) {
            setRevision(key, value);
            modified();
        }
    }

    //************* Cluster Info **************

    public ClusterInfo getClusterInfo() {
        return model.clusterInfo;
    }

    public void setClusterName(String value) {
        model.clusterInfo.name = value;
    }

    public String getClusterName() {
        if (model.clusterInfo.name == null || model.clusterInfo.name.trim().equals("")) {
            if (modifier != null) {
            	model.clusterInfo.name = editorFileName.replace(".cdd", "");
            } else {
            	return "UnKnown";
            }
        }
        return model.clusterInfo.name;
    }

    public boolean updateClusterName(String name) {
        if (!name.equals(model.clusterInfo.name)) {
            model.clusterInfo.name = name;
            modified();
            return true;
        }
        return false;
    }

    public void setMessageEncoding(String value) {
        model.clusterInfo.messageEncoding = value;
    }

    public String getMessageEncoding() {
        return model.clusterInfo.messageEncoding;
    }

    public boolean updateMessageEncoding(String messageEncoding) {
        if (!messageEncoding.equals(model.clusterInfo.messageEncoding)) {
            model.clusterInfo.messageEncoding = messageEncoding;
            modified();
            return true;
        }
        return false;
    }

    // ************ Object Management *************
    private void setOm(String path, String key, String value) {
        if (key.equals(Elements.MEMORY_MANAGER.localName)) {
            model.om.memOm = model.new MemoryOm();
            model.om.activeOm = ObjectManagement.MEMORY_MGR;
        } else if (key.equals(Elements.CACHE_MANAGER.localName)) {
            model.om.cacheOm = model.new CacheOm();
            model.om.activeOm = ObjectManagement.CACHE_MGR;
            initDomainObjects();
        } 
//        else if (key.equals(Elements.BDB_MANAGER.localName)) {
//            model.om.bdbOm = model.new BdbOm();
//            model.om.activeOm = ObjectManagement.BDB_MGR;
//            initBdbManagerProperties();
//        } 
        else if (path.startsWith("object-management.berkeley-db-manager")) {
            model.om.bdbOm.bdbProps.put(key, value);
        }
    }

    public String getOmMgr() {
        return model.om.activeOm;
    }

    public void setProvider(CacheOm cacheOm, String provider) {
    	if (!provider.equalsIgnoreCase(CacheOm.PROVIDER_TIBCO) &&
				!provider.equalsIgnoreCase(CacheOm.PROVIDER_COHERENCE)) {
			cacheOm.provider = CacheOm.PROVIDER_TIBCO;
			modified();
    	} else {
    		cacheOm.provider = provider;
    	}
    }
    
    public String getProvider() {
        return model.om.cacheOm.provider;
    }
    
    public boolean updateProvider(String provider) {
        if (!provider.equals(model.om.cacheOm.provider)) {
            model.om.cacheOm.provider = provider;
            boolean isOracleCacheOm = provider.equals(CacheOm.PROVIDER_ORACLE) || provider.equals(CacheOm.PROVIDER_COHERENCE);
			if (isOracleCacheOm) {
				String option = getBackingStoreValue(BackingStore.PERSISTENCE_OPTION);
				ArrayList<String> validPersistenceOptions = getBackingStorePersistenceOptions(isOracleCacheOm);
				if (!validPersistenceOptions.contains(option)) {
					updateBackingStoreValue(BackingStore.PERSISTENCE_OPTION, BackingStoreConfig.PERSISTENCE_OPTION_NONE);
				}
				String type = getBackingStoreValue(ConfigNS.Elements.TYPE.localName);
				ArrayList<String> validTypes = getBackingStoreTypes(isOracleCacheOm);
				if (!validTypes.contains(type)) {
					updateBackingStoreValue(ConfigNS.Elements.TYPE.localName, BackingStoreConfig.TYPE_ORACLE);
				}
			}
            modified();
            return true;
        }
        return false;
    }

    public PropertyElementList getCacheManagerProperties() {
        if (model.om.cacheOm.cacheProps == null)
            model.om.cacheOm.cacheProps = model.new PropertyElementList();
        return (model.om.cacheOm.cacheProps);
    }

//    public void initBdbManagerProperties() {
//        model.om.bdbOm.bdbProps = new LinkedHashMap<String, String>();
//        model.om.bdbOm.bdbProps.put(ConfigNS.Elements.CHECKPOINT_INTERVAL.localName, "30");
//        model.om.bdbOm.bdbProps.put(ConfigNS.Elements.CHECKPOINT_OPS_LIMIT.localName, "1000");
//        model.om.bdbOm.bdbProps.put(ConfigNS.Elements.PROPERTY_CACHE_SIZE.localName, "10000");
//        model.om.bdbOm.bdbProps.put(ConfigNS.Elements.DELETE_RETRACTED.localName, "true");
//        model.om.bdbOm.bdbProps.put(ConfigNS.Elements.SKIP_RECOVERY.localName, "false");
//        model.om.bdbOm.bdbProps.put(ConfigNS.Elements.DB_DIR.localName, "");
//    }

    public LinkedHashMap<String, String> getBdbManagerProperties() {
        return (model.om.bdbOm.bdbProps);
    }

    public boolean setOmMgr(String omMgr) {
        if (!omMgr.equals(model.om.activeOm)) {
            setOm("", model.om.omTypes.get(omMgr), "");
            modified();
            return true;
        }
        return false;
    }

    public boolean updateCacheOmValues(String key, String value) {
        if (!model.om.cacheOm.values.get(key).equals(value)) {
            model.om.cacheOm.values.put(key, value);
            modified();
            return true;
        }
        return false;
    }

    public boolean updateCacheOmObjectTableSize(String value) {
        if (!model.om.cacheOm.objectTableSize.equals(value)) {
            model.om.cacheOm.objectTableSize = value;
            modified();
            return true;
        }
        return false;
    }
    
    public ArrayList<String> getBackingStoreCacheLoaderClasses() {
        ArrayList<String> classes = new ArrayList<String>();
        classes.add(BackingStore.CACHE_LOADER_TIBCO);
        classes.add(BackingStore.CACHE_LOADER_ORACLE);
        return classes;
    }

    public ArrayList<String> getBackingStoreTypes(boolean isOracleCacheOm) {
        ArrayList<String> types = new ArrayList<String>();
        types.add(BackingStoreConfig.TYPE_ORACLE);
        types.add(BackingStoreConfig.TYPE_SQLSERVER);
        types.add(BackingStoreConfig.TYPE_DB2);
        types.add(BackingStoreConfig.TYPE_MYSQL);
        types.add(BackingStoreConfig.TYPE_POSTGRES);
        if (!isOracleCacheOm) {
        	types.add(BackingStoreConfig.TYPE_BDB);
        }
        return types;
    }

    public ArrayList<String> getBackingStorePersistenceOptions(boolean isOracleCacheOm) {
        ArrayList<String> types = new ArrayList<String>();
        types.add(BackingStoreConfig.PERSISTENCE_OPTION_NONE);
        types.add(BackingStoreConfig.PERSISTENCE_OPTION_SHARED_ALL);
        if (!isOracleCacheOm) {
        	types.add(BackingStoreConfig.PERSISTENCE_OPTION_SHARED_NOTHING);
        }
        return types;
    }
    
    public ArrayList<String> getBackingStoreStrategies() {
        ArrayList<String> strategies = new ArrayList<String>();
        strategies.add(BackingStore.STRATEGY_ORACLE);
        strategies.add(BackingStore.STRATEGY_JDBC);
        return strategies;
    }

    public ArrayList<String> getBackingStorePersistencePolicies() {
        ArrayList<String> strategies = new ArrayList<String>();
        strategies.add(BackingStoreConfig.PERSISTENCE_POLICY_ASYNC);
        strategies.add(BackingStoreConfig.PERSISTENCE_POLICY_SYNC);
        return strategies;
    }
    
    public String getBackingStoreValue(String key) {
        return (model.om.cacheOm.bs.values.get(key));
    }
    
    public boolean updateBackingStoreValue(String key, String value) {
    	//BE-24888 Adding check for default value set when cache is selected from wizard for object management type. This will not mark editor dirty when default value is changed to "none". 
    	if(BackingStore.PERSISTENCE_OPTION.equals(key)&&model.om.cacheOm.bs.values.get(key).equals("%%UseBackingStore%%")){
    		model.om.cacheOm.bs.values.put(key, value);
    		return true;
    	}
        if (!model.om.cacheOm.bs.values.get(key).equals(value)) {
            model.om.cacheOm.bs.values.put(key, value);
            modified();
            return true;
        }
        return false;
    }

    public boolean updateBackingStoreConnection(Connection connection, String key, String value) {
        if (!connection.values.get(key).equals(value)) {
            connection.values.put(key, value);
            modified();
            return true;
        }
        return false;
    }

    public DomainObjects initDomainObjects() {
        //model.om.cacheOm.domainObjects = model.om.cacheOm.new DomainObjects();
        /*
        ArrayList<String> concepts = ClusterConfigProjectUtils.getProjectConcepts(project);
        for (String concept: concepts) {
            DomainObject domainObj = model.om.cacheOm.new DomainObject();
            domainObj.values.put(ConfigNS.Elements.URI.localName, concept);
            model.om.cacheOm.domainObjects.domainObjOverrides.overrides.add(domainObj);
        }
        ArrayList<String> events = ClusterConfigProjectUtils.getProjectEvents(project);
        for (String event: events) {
            DomainObject domainObj = model.om.cacheOm.new DomainObject();
            domainObj.values.put(ConfigNS.Elements.URI.localName, event);
            model.om.cacheOm.domainObjects.domainObjOverrides.overrides.add(domainObj);
        }
        */
        return (model.om.cacheOm.domainObjects);
    }


	public void initializeDomainObject(DomainObject domainObj) {
		String uri = domainObj.values.get(Elements.URI.localName);
		if (!uri.trim().equals("")) {
			domainObj.entity = ClusterConfigProjectUtils.getEntityElementForPath(project, uri);
		}
		boolean isModified = initializeEntityPropertiesFromProject(domainObj);
		if (isModified)
			modified();
	}
    
	private boolean initializeEntityPropertiesFromProject(DomainObject domainObj) {
		boolean isModified = false;
		if (domainObj.entity != null) {
			ArrayList<String> props = ClusterConfigProjectUtils.getEntityElementProperties(domainObj.entity);
			int origSize = domainObj.props.size(); 
			for (String prop: props) {
				if (!domainObj.props.containsKey(prop)) {
					DomainObjectProperty doObjProps = domainObj.new DomainObjectProperty();
					domainObj.props.put(prop, doObjProps);
					isModified = true;
				}
			}
			
			Iterator<Map.Entry<String, DomainObjectProperty>> itr = domainObj.props.entrySet().iterator();
			while (itr.hasNext()) {
				Map.Entry<String, DomainObjectProperty> entry = itr.next();
				if (!props.contains(entry.getKey())) {
					itr.remove();
					//Delete the property from compositeIndexes if it is removed
					for(DomainObjectCompositeIndex compositeIndex : domainObj.compIdxList){
					    compositeIndex.removeProperty(entry.getKey());
					}
					isModified = true;
				}
			}
			
			
			
			if (domainObj.props.size() != origSize)
				isModified = true;
		}
		return isModified;
	}

	public String getDomainObjectModeDisplayString(String key) {
        if (key.equals(CacheOm.DO_MODE_MODEL_CACHE))
            return CacheOm.DO_MODE_UI_CACHE;
        else if (key.equals(CacheOm.DO_MODE_MODEL_MEMORY))
            return CacheOm.DO_MODE_UI_MEMORY;
        else if (key.equals(CacheOm.DO_MODE_MODEL_CACHE_MEMORY))
            return CacheOm.DO_MODE_UI_CACHE_MEMORY;
        return "";
    }

    public String getDomainObjectModeModelString(String key) {
        if (key.equals(CacheOm.DO_MODE_UI_CACHE))
            return CacheOm.DO_MODE_MODEL_CACHE;
        else if (key.equals(CacheOm.DO_MODE_UI_MEMORY))
            return CacheOm.DO_MODE_MODEL_MEMORY;
        else if (key.equals(CacheOm.DO_MODE_UI_CACHE_MEMORY))
            return CacheOm.DO_MODE_MODEL_CACHE_MEMORY;
        return "";
    }

    public boolean isCacheMode(String mode, boolean isUi) {
    	if (isUi && (mode.equals(CacheOm.DO_MODE_UI_CACHE) || mode.equals(CacheOm.DO_MODE_UI_CACHE_MEMORY)))
    		return true;
    	else if (!isUi && (mode.equals(CacheOm.DO_MODE_MODEL_CACHE) || mode.equals(CacheOm.DO_MODE_MODEL_CACHE_MEMORY)))
    		return true;
    	return false;
    }
    
    public String getDomainObjectsValue(String key) {
        return model.om.cacheOm.domainObjects.domainObjDefault.values.get(key);
    }

    public DomainObject getDomainObjectForPath(String entityPath) {
    	// returns if it is found, else creates it and returns the object
    	for (DomainObject domainObj: model.om.cacheOm.domainObjects.domainObjOverrides.overrides) {
    		if (domainObj.values.get(Elements.URI.localName).equals(entityPath)) {
    			return domainObj;
    		}
    	}
	    return (addDomainObject(entityPath));
    }
    
    private DomainObject addDomainObject(String entityPath) {
    	DomainObject domainObj = model.om.cacheOm.new DomainObject();
    	domainObj.values.put(Elements.URI.localName, entityPath);
    	String defMode = model.om.cacheOm.domainObjects.domainObjDefault.values.get(Elements.DEFAULT_MODE.localName);
        domainObj.values.put(ConfigNS.Elements.MODE.localName, defMode);
        /*
        String defBsEnabled = model.om.cacheOm.bs.values.get(Elements.ENABLED.localName);
        domainObj.bs.values.put(Elements.ENABLED.localName, defBsEnabled);
        */
    	return domainObj;
    }
    
    public DomainObject addDomainObject(EntityElement ee) {
        if (model.om.cacheOm != null && model.om.activeOm.equals(ObjectManagement.CACHE_MGR)) {
            DomainObject domainObj = model.om.cacheOm.new DomainObject(ee);
            String defMode = model.om.cacheOm.domainObjects.domainObjDefault.values.get(Elements.DEFAULT_MODE.localName);
            domainObj.values.put(ConfigNS.Elements.MODE.localName, defMode);
            /*String defBsEnabled = model.om.cacheOm.bs.values.get(Elements.ENABLED.localName);
            domainObj.bs.values.put(Elements.ENABLED.localName, defBsEnabled);*/
            if((model.om.cacheOm.bs.values.get(BackingStore.PERSISTENCE_OPTION).equals(BackingStoreConfig.PERSISTENCE_OPTION_SHARED_ALL)||model.om.cacheOm.bs.values.get(BackingStore.PERSISTENCE_OPTION).equals(BackingStoreConfig.PERSISTENCE_OPTION_SHARED_NOTHING)))
    			
			{
            	domainObj.bs.values.put(Elements.ENABLED.localName, "true");
			}
			else{
				domainObj.bs.values.put(Elements.ENABLED.localName, "false");
			}
            initializeEntityPropertiesFromProject(domainObj);
            //domainObj.values.put(ConfigNS.Elements.URI.localName, ee.getFolder() + ee.getName());
            model.om.cacheOm.domainObjects.domainObjOverrides.overrides.add(domainObj);
            modified();
            return domainObj;
        }
        return null;
    }
    
    public boolean updateDomainObjectsValue(String key, String value) {
        if (!model.om.cacheOm.domainObjects.domainObjDefault.values.get(key).equals(value)) {
            model.om.cacheOm.domainObjects.domainObjDefault.values.put(key, value);
            modified();
            return true;
        }
        return false;
    }

    public boolean updateDomainObject(DomainObject domainObj, String key, String value) {
        if (!domainObj.values.get(key).equals(value)) {
            domainObj.values.put(key, value);
            modified();
            return true;
        }
        return false;
    }

    public boolean updateDomainObject(String entityPath, LinkedHashMap<String, String> entityProps, LinkedHashMap<String,LinkedHashMap<String, String>> propProps) {
    	DomainObject domainObj = getDomainObjectForPath(entityPath);
		boolean updated = false;
    	for (Map.Entry<String, String> entry: entityProps.entrySet()) {
			if (entry.getKey().equals(Elements.ENABLED.localName) || 
					entry.getKey().equals(Elements.TABLE_NAME.localName)) {
				domainObj.bs.values.put(entry.getKey(), entry.getValue());
				updated = true;
			} else {
				if (!model.om.cacheOm.domainObjects.domainObjDefault.values.get(entry.getKey()).equals(entry.getValue())) {
					domainObj.values.put(entry.getKey(), entry.getValue());
					updated = true;
				}
			}
		}
		
    	for (Map.Entry<String, LinkedHashMap<String, String>> entry: propProps.entrySet()) {
			String propName = entry.getKey();
			DomainObjectProperty domainObjProp = domainObj.new DomainObjectProperty();
			if(domainObj.props.get(propName)!=null){//BE-17849
				domainObjProp.values.putAll(domainObj.props.get(propName).values);	
			}
			domainObjProp.values.putAll(entry.getValue());
			domainObj.props.put(propName, domainObjProp);
			updated = true;
		}
    	
		if (updated)
			addUpdatedDomainObject(domainObj);
		return updated;
    }
    
    public boolean updateDomainObjectBackingStore(DomainObject domainObj, String key, String value) {
        if (!domainObj.bs.values.get(key).equals(value)) {
            domainObj.bs.values.put(key, value);
            modified();
            return true;
        }
        return false;
    }
    
    public void addUpdatedDomainObject(DomainObject newDomainObj) {
    	if (!model.om.cacheOm.domainObjects.domainObjOverrides.overrides.contains(newDomainObj)) {
    		model.om.cacheOm.domainObjects.domainObjOverrides.overrides.add(newDomainObj);
    	}
    }

    public boolean removeDomainObject(DomainObject domainObj) {
        if (model.om.cacheOm != null && model.om.activeOm.equals(ObjectManagement.CACHE_MGR)) {
            model.om.cacheOm.domainObjects.domainObjOverrides.overrides.remove(domainObj);
            modified();
            return true;
        }
        return false;
    }
    
    public void updateCacheOmProperties(PropertyElementList propList) {
        model.om.cacheOm.cacheProps = propList;
        modified();
    }

    public boolean updateCacheOmSecurityEnabled(boolean enabled) {
    	if (model.om.cacheOm.securityConfig.securityEnabled != enabled){
    		model.om.cacheOm.securityConfig.securityEnabled = enabled;
            modified();
            return true;
    	}
    	return false;
    }
        
    public boolean updateSecurityControllerValues(SecurityConfig securityConfig, String key, String value) {
        String currValue = securityConfig.securityControllerValues.get(key);
    	if ((currValue == null && value != null) || !currValue.equals(value)) {
        	securityConfig.securityControllerValues.put(key, value);
            modified();
            return true;
        }
        return false;    	
    }

    public boolean updateSecurityRequesterValues(SecurityConfig securityConfig, String key, String value) {
    	String currValue = securityConfig.securityRequesterValues.get(key);
    	if ((currValue == null && value != null) || !currValue.equals(value)) {
        	securityConfig.securityRequesterValues.put(key, value);
            modified();
            return true;
        }
        return false;    	
    }

    public boolean updateDbConceptsMap(String key, String value) {
        if (!model.om.dbConcepts.values.get(key).equals(value)) {
            model.om.dbConcepts.values.put(key, value);
            modified();
            return true;
        }
        return false;
    }

    public void updateBdbOmProperties(String key, String value) {
        if (!model.om.bdbOm.bdbProps.get(key).equals(value)) {
            model.om.bdbOm.bdbProps.put(key, value);
            modified();
        }
    }

//    public String getBdbStringValue(String key) {
//        if (model.om.bdbOm.bdbProps == null)
//            initBdbManagerProperties();
//        return getStringValue(model.om.bdbOm.bdbProps.get(key));
//    }

    public void updateBdbStringValue(String key, String value) {
        if (!value.equalsIgnoreCase(model.om.bdbOm.bdbProps.get(key).toString())) {
            model.om.bdbOm.bdbProps.put(key, value);
            modified();
        }
    }

    // ************* Rule Groups *******************
    private void setRuleGroup(String path, ArrayList<String> id, String key, String value) {
        if (path.equals("rulesets") && id.size()==1) {
            initRulesGroup(id.get(0));
        } else if (path.equals("rulesets.rules")) {
            for (RuleElement ruleElement: model.ruleElementsList.ruleElements) {
                if (! (ruleElement instanceof RulesGrp))
                    continue;
                RulesGrp rulesGrp = (RulesGrp) ruleElement;
                if (rulesGrp.id.equals(id.get(0))) {
                    if (key.equals("uri")) {
                        initRule(rulesGrp, value, false);
                    } else if (key.equals("ref")) {
                        initRule(rulesGrp, value, true);
                    }
                }
            }
        }
    }

    private Rule initRule(RulesGrp rulesGrp, String value, boolean isRef) {
    	Boolean isRuleTemplate = false;
    	if(!isRef){
    		isRuleTemplate = ClusterConfigProjectUtils.isProjectRuleTemplate(this.project, value);
    	}
        Rule rule = model.new Rule(rulesGrp, value, isRef,isRuleTemplate);
        rulesGrp.rules.add(rule);
        return rule;
    }

    private Rule initRule(DashInfProcAgent agent, String value) {
    	Boolean isRuleTemplate = ClusterConfigProjectUtils.isProjectRuleTemplate(this.project, value);
        Rule rule = model.new Rule(agent, value, isRuleTemplate);
        agent.agentRulesGrpObj.agentRules.add(rule);
        return rule;
    }

    private RulesGrp initRulesGroup(String name) {
        RulesGrp rulesGrp = model.new RulesGrp();
        rulesGrp.id = name;
        model.ruleElementsList.ruleElements.add(rulesGrp);
        return rulesGrp;
    }

    public String updateRulesGroupName(RulesGrp rulesGrp, String newName) {
    	if (rulesGrp != null && !rulesGrp.id.equals(newName)) {
    		String existingGroupNames[] = getAllGroups();
    		ArrayList<String> names = new ArrayList<String>(Arrays.asList(existingGroupNames));
    		if (!names.contains(newName)) {
    			rulesGrp.id = newName;
    			modified();
    			return "true";
    		}
    		return "trueError";
    	}
    	return "false";
    }

    public String[] getAllGroups(){
    	
    	String rulesArray[]=getRulesGroupNames();
    	String destinationsArray[]=getDestinationsGroupNames();
    	String processesArray[]=getProcessesGroupNames();
    	String functionsArray[]=getFunctionsGroupNames();
    	
    	
    	Object[] dest1=ArrayUtils.addAll(rulesArray, destinationsArray);
    	Object[] dest2=ArrayUtils.addAll(processesArray, functionsArray);
    	Object[] dest3=ArrayUtils.addAll(dest1, dest2);
    	Object[] returnArray=ArrayUtils.addAll(dest3, getLogConfigsName().toArray());		
    	
    	return (String[]) returnArray;
    	
    }
    
    public RulesGrp addRulesGroupName(String name) {
        RulesGrp rulesGrp = initRulesGroup(name);
        modified();
        return rulesGrp;
    }

    public boolean removeRulesGroup(RulesGrp rulesGrp) {
        if (model.ruleElementsList.ruleElements.contains(rulesGrp)) {
            model.ruleElementsList.ruleElements.remove(rulesGrp);
            modified();
            return true;
        }
        return false;
    }

    public ArrayList<RuleElement> getRulesGroup() {
        return (model.ruleElementsList.ruleElements);
    }

    public String[] getRulesGroupNames() {
        ArrayList<String> rulesGrpNames = new ArrayList<String>();
        for (RuleElement ruleElement: model.ruleElementsList.ruleElements) {
            if (! (ruleElement instanceof RulesGrp))
                continue;
            RulesGrp rulesGrp = (RulesGrp) ruleElement;
            rulesGrpNames.add(rulesGrp.id);
        }
        return rulesGrpNames.toArray(new String[0]);
    }

    public RuleElement[] getRules(RulesGrp rulesGrp) {
        return rulesGrp.rules.toArray(new RuleElement[0]);
    }

    public ArrayList<String> getRuleNames(RulesGrp rulesGrp, boolean excludeRefs) {
        ArrayList<String> ruleNames = new ArrayList<String>();
        for (RuleElement ruleElement: rulesGrp.rules) {
            if (ruleElement instanceof Rule) {
                Rule rule = (Rule) ruleElement;
                if (excludeRefs && rule.isRef)
                    continue;
                ruleNames.add(rule.uri);
            } else if (!excludeRefs && (ruleElement instanceof RulesGrp)) {
                RulesGrp rulesGrpRef = (RulesGrp) ruleElement;
                ruleNames.add(rulesGrpRef.id);
            }
        }
        return ruleNames;
    }

    private RulesGrp getRulesGrpRef(String name) {
        if (name == null)
            return null;
        for (RuleElement ruleElement: model.ruleElementsList.ruleElements) {
            if (! (ruleElement instanceof RulesGrp))
                continue;
            RulesGrp rulesGrp = (RulesGrp) ruleElement;
            if (name.equals(rulesGrp.id))
                return rulesGrp;
        }
        return null;
    }

    public RuleElementsList getRulesGroupList() {
        return model.ruleElementsList;
    }

    public RulesGrp addRulesGrp() {
    	
    	/*Check here is existing name is present or not*/
    	
    	
    	
        String namesArr[] = getRulesGroupNames();
        ArrayList<String> names = new ArrayList<String>(Arrays.asList(namesArr));
        String newName = IdUtil.generateSequenceId("Rules_Collection", names);
        RulesGrp rulesGrp = addRulesGroupName(newName);
        return rulesGrp;
    }

    public Rule addRules(RulesGrp rulesGrp, ArrayList<String> names) {
        Rule rule;
        Rule firstRule = null;
        for (String name: names) {
            if (name.startsWith("/")) {
                rule = initRule(rulesGrp, name, false);
            } else {
                rule = initRule(rulesGrp, name, true);
            }
            modified();
            if (firstRule == null)
                firstRule = rule;
        }
        return firstRule;
    }

    public boolean removeRule(Rule rule) {
        if (rule.parent instanceof RulesGrp) {
            RulesGrp rulesGrp = (RulesGrp) rule.parent;
            if (rulesGrp.rules.contains(rule)) {
                rulesGrp.rules.remove(rule);
                modified();
                return true;
            }
        }
        if (rule.parent instanceof DashInfProcAgent) {
            DashInfProcAgent agent = (DashInfProcAgent) rule.parent;
            if (agent.agentRulesGrpObj.agentRules.contains(rule)) {
                agent.agentRulesGrpObj.agentRules.remove(rule);
                modified();
                return true;
            }
        }
        return false;
    }

    public boolean updateRuleName(Rule rule, String ruleUri) {
        if (rule != null) {
            if (!rule.uri.equalsIgnoreCase(ruleUri)) {
                rule.uri = ruleUri;
                modified();
                return true;
            }
        }
        return false;
    }
    


    // **************** Destinations *****************
    private void setDestinations(String path, ArrayList<String> id, String key, String value) {
        if (path.equals(ConfigNS.Elements.DESTINATION_GROUPS.localName) && id.size()==1) {
            initDestinationsGroup(id.get(0));
        } else if (path.equals("destination-groups.destinations")) {
            for (DestinationElement destinationElement: model.destinationElementsList.destinationElements) {
                if (! (destinationElement instanceof DestinationsGrp))
                    continue;
                DestinationsGrp destinationsGrp = (DestinationsGrp) destinationElement;
                if (destinationsGrp.id.equals(id.get(0))) {
                    if (key.equals("destination")) {
                        initDestination(destinationsGrp, id.get(id.size()-1), false);
                    } else {
                        initDestination(destinationsGrp, value, true);
                    }
                    break;
                }
            }
        } else if (path.equals("destination-groups.destinations.destination")) {
            for (DestinationElement destinationElement: model.destinationElementsList.destinationElements) {
                if (! (destinationElement instanceof DestinationsGrp))
                    continue;
                DestinationsGrp destinationsGrp = (DestinationsGrp) destinationElement;
                if (destinationsGrp.id.equals(id.get(0))) {
                    for (DestinationElement destinationElement2: destinationsGrp.destinations) {
                        if (! (destinationElement2 instanceof Destination))
                            continue;
                        Destination destination = (Destination) destinationElement2;
                        if (destination.id.equals(id.get(id.size()-1))) {
                            destination.destinationVal.put(key, value);
                            break;
                        }
                    }
                }
            }
        }
    }

    public Destination initDestination(DashInfProcQueryAgent agent, String value) {
        Destination destination = model.new Destination(agent, value);
        addDestinationToList(agent.agentDestinationsGrpObj.agentDestinations, destination);
        return destination;
    }

    public ArrayList<DestinationElement> getDestinationsGroup() {
        return model.destinationElementsList.destinationElements;
    }

    public DestinationElement[] getDestinations(DestinationsGrp destinationsGrp) {
        return destinationsGrp.destinations.toArray(new DestinationElement[0]);
    }

    public String[] getDestinationsGroupNames() {
        ArrayList<String> destinationGrpNames = new ArrayList<String>();
        for (DestinationElement destinationElement: model.destinationElementsList.destinationElements) {
            if (! (destinationElement instanceof DestinationsGrp))
                continue;
            DestinationsGrp destinationsGrp = (DestinationsGrp) destinationElement;
            destinationGrpNames.add(destinationsGrp.id);
        }
        return destinationGrpNames.toArray(new String[0]);
    }

    public ArrayList<String> getDestinationNames(DestinationsGrp destinationsGrp, boolean excludeRefs) {
        ArrayList<String> destinationNames = new ArrayList<String>();
        for (DestinationElement destinationElement: destinationsGrp.destinations) {
            if (destinationElement instanceof Destination) {
                Destination destination = (Destination) destinationElement;
                if (excludeRefs && destination.isRef)
                    continue;
                destinationNames.add(destination.id);
            } else if (!excludeRefs && (destinationElement instanceof DestinationsGrp)) {
                DestinationsGrp destinationsGrpRef = (DestinationsGrp) destinationElement;
                destinationNames.add(destinationsGrpRef.id);
            }
        }
        return destinationNames;
    }

    public ArrayList<String> getDestinationUris(DestinationsGrp destinationsGrp, boolean excludeRefs) {
        ArrayList<String> destinationNames = new ArrayList<String>();
        for (DestinationElement destinationElement: destinationsGrp.destinations) {
            if (destinationElement instanceof Destination) {
                Destination destination = (Destination) destinationElement;
                if (excludeRefs && destination.isRef)
                    continue;
                destinationNames.add(destination.destinationVal.get(Elements.URI.localName));
            } else if (!excludeRefs && (destinationElement instanceof DestinationsGrp)) {
                DestinationsGrp destinationsGrpRef = (DestinationsGrp) destinationElement;
                destinationNames.add(destinationsGrpRef.id);
            }
        }
        return destinationNames;
    }

    private DestinationsGrp getDestinationsGrpRef(String name) {
        if (name == null)
            return null;
        for (DestinationElement destinationElement: model.destinationElementsList.destinationElements) {
            if (! (destinationElement instanceof DestinationsGrp))
                continue;
            DestinationsGrp destinationsGrp = (DestinationsGrp) destinationElement;
            if (name.equals(destinationsGrp.id))
                return destinationsGrp;
        }
        return null;
    }

    public DestinationsGrp addDestinationsGroupName(String name) {
        DestinationsGrp destinationsGrp = initDestinationsGroup(name);
        modified();
        return destinationsGrp;
    }

    private DestinationsGrp initDestinationsGroup(String name) {
        DestinationsGrp destinationsGrp = model.new DestinationsGrp();
        destinationsGrp.id = name;
        model.destinationElementsList.destinationElements.add(destinationsGrp);
        return destinationsGrp;
    }

    public String updateDestinationsGroupName(DestinationsGrp destinationsGrp, String newName) {
        if (destinationsGrp != null && !destinationsGrp.id.equals(newName)) {
        	
        	String existingGroupNames[]=getAllGroups();
        	ArrayList<String> names = new ArrayList<String>(Arrays.asList(existingGroupNames));
        	if(!names.contains(newName)){
        		 destinationsGrp.id = newName;
                 modified();
                 return "true";
            }
        		return "trueError";
        
        }
        return "false";
    }

    public boolean updateDestinationName(Destination destination, String newName) {
        if (destination != null) {
            if (!destination.id.equalsIgnoreCase(newName)) {
                destination.id = newName;
                modified();
                return true;
            }
        }
        return false;
    }

    public boolean removeDestination(Destination destination) {
        if (destination.parent instanceof DestinationsGrp) {
            DestinationsGrp destinationGrp = (DestinationsGrp) destination.parent;
            if (destinationGrp.destinations.contains(destination)) {
                destinationGrp.destinations.remove(destination);
                modified();
                return true;
            }
        }
        if (destination.parent instanceof DashInfProcQueryAgent) {
            DashInfProcQueryAgent agent = (DashInfProcQueryAgent) destination.parent;
            if (agent.agentDestinationsGrpObj.agentDestinations.contains(destination)) {
                agent.agentDestinationsGrpObj.agentDestinations.remove(destination);
                modified();
                return true;
            }
        }
        return false;
    }

    public boolean removeDestinationsGroup(DestinationsGrp destinationsGrp) {
        if (model.destinationElementsList.destinationElements.contains(destinationsGrp)) {
            model.destinationElementsList.destinationElements.remove(destinationsGrp);
            modified();
            return true;
        }
        return false;
    }

    private DestinationsGrp getDestinationsGroupRef(String name) {
        if (name == null)
            return null;
        for (DestinationElement destinationElement: model.destinationElementsList.destinationElements) {
            if (! (destinationElement instanceof DestinationsGrp))
                continue;
            DestinationsGrp destinationsGrp = (DestinationsGrp) destinationElement;
            if (name.equals(destinationsGrp.id))
                return destinationsGrp;
        }
        return null;
    }

    public DestinationElementsList getDestinationsGroupList() {
        return model.destinationElementsList;
    }

    private Destination initDestination(DestinationsGrp destinationsGrp, String epId, boolean isRef) {
        Destination destination = model.new Destination(destinationsGrp, epId, isRef);
        addDestinationToList(destinationsGrp.destinations, destination);
        return destination;
    }

    // This is needed since we need to order "ref" elements first, and then "destination" elements. Refer CR BE-9596.
    private void addDestinationToList(ArrayList<DestinationElement> destinations, DestinationElement newDestination) {
        if (newDestination instanceof Destination && !((Destination)newDestination).isRef) {
            destinations.add(newDestination);
        } else {
            int pos = 0;
            for (DestinationElement destinationElement: destinations) {
                if (destinationElement instanceof Destination) {
                    if (((Destination)destinationElement).isRef)
                        pos++;
                    else
                        break;
                } else if (destinationElement instanceof AgentDestinationsGrpElement) {
                    pos++;
                }
            }
            destinations.add(pos, newDestination);
        }
    }

    public DestinationsGrp addDestinationsGrp() {
        String namesArr[] = getDestinationsGroupNames();
        ArrayList<String> names = new ArrayList<String>(Arrays.asList(namesArr));
        String newName = IdUtil.generateSequenceId("Destinations_Collection", names);
        DestinationsGrp destinationsGrp = addDestinationsGroupName(newName);
        return destinationsGrp;
    }

    public Destination addDestinations(DestinationsGrp destinationsGrp, ArrayList<String> names) {
        Destination destination, firstDestination = null;
        for (String name: names) {
            if (name.startsWith("/")) {
                String uniqId = IdUtil.generateUniqueName(name);
                int idx = uniqId.lastIndexOf("/");
                if (idx != -1 && idx+1 < uniqId.length())
                    uniqId = uniqId.substring(idx+1);
                destination = initDestination(destinationsGrp, uniqId, false);
                destination.destinationVal.put(Elements.URI.localName, name);
                destination.destinationVal.put(Elements.QUEUE_SIZE.localName, "0");
            } else {
                destination = initDestination(destinationsGrp, name, true);
            }
            modified();
            if (firstDestination == null)
            	firstDestination = destination;
        }
        return firstDestination;
    }
    public String copiedCollectionsName(Object selObj,String str){
    	
    	String grpNameArr[] = null;
	    if(selObj instanceof DestinationsGrp)
	   		 grpNameArr=getDestinationsGroupNames();
	   	else if(selObj instanceof RulesGrp)
	   		grpNameArr=getRulesGroupNames();
	   	else if(selObj instanceof FunctionsGrp)
	   		grpNameArr=getFunctionsGroupNames();
	   	else if(selObj instanceof ProcessesGrp)
	   		grpNameArr=getProcessesGroupNames();
	   	else
	   		grpNameArr=new String[0];
	    
    	String objName=strCopy +str;
    	int count=1;
			while (nameExists(grpNameArr,objName)) {
				objName = strCopy + str + count++;
			}
			return objName;
    }
    
    private boolean nameExists(String[] grpNameArr,String str){
    	
	    for(String destName:grpNameArr){
	    	if(str.equals(destName))
	    		return true;
	    }
     return false;
    }
    
    private boolean destChldNameExists(String str){
    	
        for (DestinationElement destinationElement: model.destinationElementsList.destinationElements) {
            if (! (destinationElement instanceof DestinationsGrp))
                continue;
            DestinationsGrp destinationsGrp = (DestinationsGrp) destinationElement;
            ArrayList<DestinationElement> destElt = destinationsGrp.destinations;
            for(DestinationElement destElement:destElt){
            	  if (destElement instanceof Destination) {
		            	Destination dest = (Destination) destElement;
		            	if(dest.isRef)
		            		continue;
		            	if(str.equals(dest.id))
		            		return true;
            	  }
            }
            
        }
        return false;
    }
    
    public String copiedDestChldName(String id){
    	int count=1;
      String destChldName=strCopy+id;
      while(destChldNameExists(destChldName)){
    	destChldName=strCopy+id+count++;
    	}
    return destChldName;
    }
    
    
    public void updateDestinationVal(Destination destination, String key, String value) {
        if (!destination.destinationVal.get(key).equalsIgnoreCase(value)) {
            destination.destinationVal.put(key, value);
            modified();
        }
    }
    
    public Destination copyDestClass(Destination destOriginal,Destination destCopy){
    	
        destCopy.destinationVal.put(Elements.PRE_PROCESSOR.localName, destOriginal.destinationVal.get(Elements.PRE_PROCESSOR.localName));
        destCopy.destinationVal.put(Elements.QUEUE_SIZE.localName, destOriginal.destinationVal.get(Elements.QUEUE_SIZE.localName));
        destCopy.destinationVal.put(Elements.THREAD_COUNT.localName,  destOriginal.destinationVal.get(Elements.THREAD_COUNT.localName));
        destCopy.destinationVal.put(Elements.THREADING_MODEL.localName, destOriginal.destinationVal.get(Elements.THREADING_MODEL.localName));
        destCopy.destinationVal.put(Elements.URI.localName, destOriginal.destinationVal.get(Elements.URI.localName));
        destCopy.destinationVal.put(Elements.THREAD_AFFINITY_RULE_FUNCTION.localName, destOriginal.destinationVal.get(Elements.THREAD_AFFINITY_RULE_FUNCTION.localName));
        
        return destCopy;
    }
    // **************** Functions *****************
    private void setFunctions(String path, ArrayList<String> id, String key, String value) {
        if (path.equals(Elements.FUNCTION_GROUPS.localName) && id.size()==1) {
            initFunctionsGrp(id.get(0));
        } else if (path.equals("function-groups.functions")) {
            for (FunctionElement functionElement: model.functionElementsList.functionElements) {
                if (! (functionElement instanceof FunctionsGrp))
                    continue;
                FunctionsGrp functionsGrp = (FunctionsGrp) functionElement;
                if (functionsGrp.id.equals(id.get(0))) {
                    if (key.equals(Elements.URI.localName)) {
                        initFunction(functionsGrp, value, false);
                    } else if (key.equals(Elements.REF.localName)) {
                        initFunction(functionsGrp, value, true);
                    }
                }
            }
        }
    }

    private FunctionsGrp initFunctionsGrp(String name) {
        FunctionsGrp functionsGrp = model.new FunctionsGrp();
        functionsGrp.id = name;
        model.functionElementsList.functionElements.add(functionsGrp);
        return functionsGrp;
    }

    public Function initFunction(FunctionsGrp functionsGrp, String funcName, boolean isRef) {
        Function function = model.new Function(functionsGrp, funcName, isRef);
        functionsGrp.functions.add(function);
        return function;
    }

    private Function initFunction(DashInfProcQueryAgent agent, String funcName, boolean isStartup) {
        Function function = model.new Function(agent, funcName, isStartup);
        if (isStartup)
            agent.agentStartupFunctionsGrpObj.agentFunctions.add(function);
        else
            agent.agentShutdownFunctionsGrpObj.agentFunctions.add(function);
        return function;
    }

    public void updateFunctionsGroupName(String oldName, String newName) {
        for (FunctionElement functionElement: model.functionElementsList.functionElements) {
            if (! (functionElement instanceof FunctionsGrp))
                continue;
            FunctionsGrp functionsGrp = (FunctionsGrp) functionElement;
            if (functionsGrp.id.equals(oldName)) {
                functionsGrp.id = newName;
                modified();
                break;
            }
        }
    }

    public String updateFunctionsGroupName(FunctionsGrp functionsGrp, String newName) {
        if (functionsGrp != null && !functionsGrp.id.equals(newName)) {
        	String existingGroupNames[]=getAllGroups();
        	ArrayList<String> names = new ArrayList<String>(Arrays.asList(existingGroupNames));
        	if(!names.contains(newName)){

                functionsGrp.id = newName;
                modified();
                return "true";
            }
        		return "trueError";
            }
        return "false";
    }

    public FunctionsGrp addFunctionsGroupName(String name) {
        FunctionsGrp functionsGrp = initFunctionsGrp(name);
        modified();
        return functionsGrp;
    }

    public String[] getFunctionsGroupNames() {
        ArrayList<String> funcGrpNames = new ArrayList<String>();
        for (FunctionElement functionElement: model.functionElementsList.functionElements) {
            if (! (functionElement instanceof FunctionsGrp))
                continue;
            FunctionsGrp functionsGrp = (FunctionsGrp) functionElement;
            funcGrpNames.add(functionsGrp.id);
        }
        return funcGrpNames.toArray(new String[0]);
    }

    public FunctionsGrp addFunctionsGrp() {
        String namesArr[] = getFunctionsGroupNames();
        ArrayList<String> names = new ArrayList<String>(Arrays.asList(namesArr));
        String newName = IdUtil.generateSequenceId("Functions_Collection", names);
        FunctionsGrp functionsGrp = addFunctionsGroupName(newName);
        return functionsGrp;
    }

    public Function addFunction(FunctionsGrp functionsGrp, ArrayList<String> funcNames) {
        Function function;
        Function firstFunction = null;
        for (String funcName: funcNames) {
            if (funcName.startsWith("/")) {
                function = addFunction(functionsGrp, funcName, false);
            } else {
                function = addFunction(functionsGrp, funcName, true);
            }
            if (firstFunction == null)
                firstFunction = function;
        }
        return firstFunction;
    }

    public Function addFunction(FunctionsGrp functionsGrp, String funcName, boolean isRef) {
        Function function = initFunction(functionsGrp, funcName, isRef);
        modified();
        return function;
    }

    public boolean removeFunction(Function function) {
        if (function.parent instanceof FunctionsGrp) {
            FunctionsGrp functionsGrp = (FunctionsGrp) function.parent;
            if (functionsGrp.functions.contains(function)) {
                functionsGrp.functions.remove(function);
                modified();
                return true;
            }
        }
        if (function.parent instanceof DashInfProcQueryAgent) {
            DashInfProcQueryAgent agent = (DashInfProcQueryAgent) function.parent;
            if (function.isStartup && agent.agentStartupFunctionsGrpObj.agentFunctions.contains(function)) {
                agent.agentStartupFunctionsGrpObj.agentFunctions.remove(function);
                modified();
                return true;
            } else if (!function.isStartup && agent.agentShutdownFunctionsGrpObj.agentFunctions.contains(function)) {
                agent.agentShutdownFunctionsGrpObj.agentFunctions.remove(function);
                modified();
                return true;
            }
        }
        return false;
    }

    public boolean removeFunctionsGroup(FunctionsGrp functionsGrp) {
        if (model.functionElementsList.functionElements.contains(functionsGrp)) {
            model.functionElementsList.functionElements.remove(functionsGrp);
            modified();
            return true;
        }
        return false;
    }

    public ArrayList<FunctionElement> getFunctionsGroup(boolean onlyGroups) {
        return (model.functionElementsList.functionElements);
    }

    public String[] getFunctionsGrpNames() {
        ArrayList<String> functionsGrpNames = new ArrayList<String>();
        for (FunctionElement functionElement: model.functionElementsList.functionElements) {
            if (! (functionElement instanceof FunctionsGrp))
                continue;
            FunctionsGrp functionsGrp = (FunctionsGrp) functionElement;
            functionsGrpNames.add(functionsGrp.id);
        }
        return functionsGrpNames.toArray(new String[0]);
    }

    public FunctionElement[] getFunctions(FunctionsGrp functionsGrp) {
        return functionsGrp.functions.toArray(new FunctionElement[0]);
    }

    public ArrayList<String> getFunctionNames(FunctionsGrp functionsGrp, boolean excludeRefs) {
        ArrayList<String> funcNames = new ArrayList<String>();
        for (FunctionElement functionElement: functionsGrp.functions) {
            if (functionElement instanceof Function) {
                Function function = (Function) functionElement;
                if (excludeRefs && function.isRef)
                    continue;
                funcNames.add(function.uri);
            } else if (!excludeRefs && (functionElement instanceof FunctionsGrp)) {
                FunctionsGrp functionsGrpRef = (FunctionsGrp) functionElement;
                funcNames.add(functionsGrpRef.id);
            }
        }
        return funcNames;
    }

    private FunctionsGrp getFunctionsGrpRef(String name) {
        if (name == null)
            return null;
        for (FunctionElement functionElement: model.functionElementsList.functionElements) {
            if (! (functionElement instanceof FunctionsGrp))
                continue;
            FunctionsGrp functionsGrp = (FunctionsGrp) functionElement;
            if (name.equals(functionsGrp.id))
                return functionsGrp;
        }
        return null;
    }

    public FunctionElementsList getFunctionsGroupList() {
        return model.functionElementsList;
    }

    public boolean updateFunctionName(Function function, String newName) {
        if (function != null) {
            if (function.uri != null) {
                if (!function.uri.equalsIgnoreCase(newName)) {
                    function.uri = newName;
                    modified();
                    return true;
                }
            }
        }
        return false;
    }

    public boolean moveUpFunction(FunctionElement functionElement) {
        ArrayList<FunctionElement> functions = getFunctionSiblings(functionElement);
        int pos = functions.indexOf(functionElement);
        if (pos != -1) {
            functions.remove(functionElement);
            functions.add(pos-1, functionElement);
            modified();
            return true;
        }
        return false;
    }

    public boolean moveDownFunction(FunctionElement functionElement) {
        ArrayList<FunctionElement> functions = getFunctionSiblings(functionElement);
        int pos = functions.indexOf(functionElement);
        if (pos != -1) {
            functions.remove(functionElement);
            functions.add(pos+1, functionElement);
            modified();
            return true;
        }
        return false;
    }

    private int getPosition(FunctionElement function) {
        ArrayList<FunctionElement> functions = getFunctionSiblings(function);
        int pos = functions.indexOf(function);
        return pos;
    }

    public boolean canMoveUpFunction(FunctionElement function) {
        int pos = getPosition(function);
        return (pos > 0);
    }

    public boolean canMoveDownFunction(FunctionElement function) {
        int pos = getPosition(function);
        return (pos < getFunctionSiblings(function).size()-1);
    }

    private ArrayList<FunctionElement> getFunctionSiblings(FunctionElement functionElement) {
        if (functionElement instanceof Function) {
            Function function = (Function) functionElement;
            if (function.parent instanceof FunctionsGrp) {
                FunctionsGrp parentGrp = (FunctionsGrp) function.parent;
                if (parentGrp != null)
                    return ((FunctionsGrp)parentGrp).functions;
            } else if (function.parent instanceof DashInfProcQueryAgent) {
                DashInfProcQueryAgent agent = (DashInfProcQueryAgent) function.parent;
                AgentBaseFunctionsGrp agentFunctionsGrp = null;
                if (function.isStartup) {
                    agentFunctionsGrp = agent.agentStartupFunctionsGrpObj;
                } else {
                    agentFunctionsGrp = agent.agentShutdownFunctionsGrpObj;
                }
                if (agentFunctionsGrp != null) {
                    return (agentFunctionsGrp.agentFunctions);
                }
            }
        } else if (functionElement instanceof FunctionsGrp) {
            return model.functionElementsList.functionElements;
        } else if (functionElement instanceof AgentFunctionsGrpElement) {
            AgentFunctionsGrpElement agentFunctionsGrpElement = (AgentFunctionsGrpElement) functionElement;
            DashInfProcQueryAgent agent = (DashInfProcQueryAgent) agentFunctionsGrpElement.parentAgent;
            AgentBaseFunctionsGrp agentFunctionsGrp = null;
            if (agentFunctionsGrpElement.isStartup) {
                agentFunctionsGrp = agent.agentStartupFunctionsGrpObj;
            } else {
                agentFunctionsGrp = agent.agentShutdownFunctionsGrpObj;
            }
            if (agentFunctionsGrp != null) {
                return (agentFunctionsGrp.agentFunctions);
            }
        }
        return null;
    }
    
    // ***************** Processes ****************
    
    private void setProcesses(String path, ArrayList<String> id, String key, String value) {
        if (path.equals(ProcessesGrp.ELEMENT_PROCESS_GROUPS) && id.size()==1) {
            initProcessesGrp(id.get(0));
        } else if (path.equals(ProcessesGrp.ELEMENT_PROCESS_GROUPS+"."+ProcessesGrp.ELEMENT_PROCESSES)) {
            for (ProcessElement processElement: model.processElementsList.processElements) {
                if (! (processElement instanceof ProcessesGrp))
                    continue;
                ProcessesGrp processesGrp = (ProcessesGrp) processElement;
                if (processesGrp.id.equals(id.get(0))) {
                    if (key.equals(Elements.URI.localName)) {
                        initProcess(processesGrp, value, false);
                    } else if (key.equals(Elements.REF.localName)) {
                        initProcess(processesGrp, value, true);
                    }
                }
            }
        }    
    }
    
    private ProcessesGrp initProcessesGrp(String name) {
        ProcessesGrp processesGrp = model.new ProcessesGrp();
        processesGrp.id = name;
        model.processElementsList.processElements.add(processesGrp);
        return processesGrp;
    }
    
    public ProcessElement[] getProcesses(ProcessesGrp processesGrp) {
        return processesGrp.processes.toArray(new ProcessElement[0]);
    }
    
    public ProcessElementsList getProcessesGroupList() {
        return (model.processElementsList);
    }
    
    private Process initProcess(ProcessAgent agent, String value) {
    	Process process = model.new Process(agent, value);
        agent.agentProcessesGrpObj.agentProcesses.add(process);
        return process;
    }

    private ProcessesGrp initProcessesGroup(String name) {
    	ProcessesGrp processesGrp = model.new ProcessesGrp();
        processesGrp.id = name;
        model.processElementsList.processElements.add(processesGrp);
        return processesGrp;
    }
    
    private void initAgentProcessesGrp(ProcessAgent agent, String processesGrpName) {
        ProcessesGrp processesGrp = getProcessesGrpRef(processesGrpName);
        if (processesGrp != null) {
            AgentProcessesGrpElement agentProcesssGrp = agent.new AgentProcessesGrpElement(agent, processesGrp);
            agent.agentProcessesGrpObj.agentProcesses.add(agentProcesssGrp);
        }
    }
    
    public ProcessesGrp addProcessesGrp() {
        String namesArr[] = getProcessesGroupNames();
        ArrayList<String> names = new ArrayList<String>(Arrays.asList(namesArr));
        String newName = IdUtil.generateSequenceId("Processes_Collection", names);
        ProcessesGrp processesGrp = addProcessesGroupName(newName);
        return processesGrp;
    }
    
    public ProcessesGrp addProcessesGroupName(String name) {
    	ProcessesGrp processesGrp = initProcessesGroup(name);
        modified();
        return processesGrp;
    }
    
    public Process addProcesses(ProcessesGrp processesGrp, ArrayList<String> names) {
        Process process, firstProcess = null;
        for (String name: names) {
            if (name.startsWith("/")) {
                process = initProcess(processesGrp, name, false);
            } else {
                process = initProcess(processesGrp, name, true);
            }
            modified();
            if (firstProcess == null)
            	firstProcess = process;
        }
        return firstProcess;
    }
    
    private Process initProcess(ProcessesGrp processesGrp, String epId, boolean isRef) {
        Process process =model.new Process(processesGrp, epId, isRef);
        processesGrp.processes.add(process);
        return process;
    }

    private ProcessesGrp getProcessesGrpRef(String name) {
        if (name == null)
            return null;
        for (ProcessElement processElement: model.processElementsList.processElements) {
            if (! (processElement instanceof ProcessesGrp))
                continue;
            ProcessesGrp processsGrp = (ProcessesGrp) processElement;
            if (name.equals(processsGrp.id))
                return processsGrp;
        }
        return null;
    }
    
    public boolean updateProcessName(Process process, String uri) {
        if (process != null) {
            if (!process.uri.equalsIgnoreCase(uri)) {
            	process.uri = uri;
                modified();
                return true;
            }
        }
        return false;
    }
    
    public String updateProcessesGroupName(ProcessesGrp processesGrp, String newName) {
        if (processesGrp != null && !processesGrp.id.equals(newName)) {
        	
        	String existingGroupNames[]=getAllGroups();
        	ArrayList<String> names = new ArrayList<String>(Arrays.asList(existingGroupNames));
        	if(!names.contains(newName)){
        		 processesGrp.id = newName;
                 modified();
                 return "true";
            }
        		return "trueError";
           
        }
        return "false";
    }

    public ArrayList<String> getProcessNames(ProcessesGrp processesGrp, boolean excludeRefs) {
        ArrayList<String> processNames = new ArrayList<String>();
        for (ProcessElement processElement: processesGrp.processes) {
            if (processElement instanceof Process) {
                Process process = (Process) processElement;
                if (excludeRefs && process.isRef)
                    continue;
                processNames.add(process.uri);
            } else if (!excludeRefs && (processElement instanceof ProcessesGrp)) {
            	ProcessesGrp processesGrpRef = (ProcessesGrp) processElement;
                processNames.add(processesGrpRef.id);
            }
        }
        return processNames;
    }
    
    public boolean removeProcessesGroup(ProcessesGrp processesGrp) {
        if (model.processElementsList.processElements.contains(processesGrp)) {
            model.processElementsList.processElements.remove(processesGrp);
            modified();
            return true;
        }
        return false;
    }
    
    public boolean removeAgentProcessessGrp(AgentProcessesGrpElement processesGrp) {
    	ProcessAgent agent = processesGrp.parentAgent;
        if (agent.agentProcessesGrpObj.agentProcesses.contains(processesGrp)) {
            agent.agentProcessesGrpObj.agentProcesses.remove(processesGrp);
            modified();
            return true;
        }
        return false;
    }

    public boolean removeProcess(Process process) {
        if (process.parent instanceof ProcessesGrp) {
        	ProcessesGrp processesGrp = (ProcessesGrp) process.parent;
            if (processesGrp.processes.contains(process)) {
                processesGrp.processes.remove(process);
                modified();
                return true;
            }
        }
        if (process.parent instanceof ProcessAgent) {
        	ProcessAgent agent = (ProcessAgent) process.parent;
            if (agent.agentProcessesGrpObj.agentProcesses.contains(process)) {
                agent.agentProcessesGrpObj.agentProcesses.remove(process);
                modified();
                return true;
            }
        }
        return false;
    }

    
    // ***************** Log Configs ****************

    public LogConfigsList getLogConfigsList() {
        return (model.logConfigsList);
    }

    public ArrayList<LogConfig> getLogConfigs() {
        return (getLogConfigsList().logConfigs);
    }

    public ArrayList<String> getLogConfigsName() {
        ArrayList<String> configs = new ArrayList<String>();
        for (LogConfig config: model.logConfigsList.logConfigs) {
            configs.add(config.id);
        }
        return configs;
    }

    public LogConfig getLogConfig(String logConfigName) {
        for (LogConfig config: model.logConfigsList.logConfigs) {
            if (logConfigName.equals(config.id))
                return config;
        }
        return null;
    }

    public boolean getLogFilesEnabled(String logConfigName) {
        LogConfig config = getLogConfig(logConfigName);
        return getLogFilesEnabled(config);
    }

    public boolean getLogFilesEnabled(LogConfig config) {
        if (config != null) {
            String value = config.files.get(Elements.ENABLED.localName);
            return (new Boolean(value).booleanValue());
        }
        return false;
    }

    public boolean getLogFilesAppend(LogConfig config) {
        String value = config.files.get(Elements.APPEND.localName);
        return (new Boolean(value).booleanValue());
    }

    public boolean getLogTerminalEnabled(String logConfigName) {
        LogConfig config = getLogConfig(logConfigName);
        return getLogTerminalEnabled(config);
    }

    public boolean getLogTerminalEnabled(LogConfig config) {
        if (config != null) {
            String value = config.terminal.get(Elements.ENABLED.localName);
            return (new Boolean(value).booleanValue());
        }
        return false;
    }

    public boolean getLogLayoutEnabled(String logConfigName) {
        LogConfig config = getLogConfig(logConfigName);
        return getLogLayoutEnabled(config);
    }

    public boolean getLogLayoutEnabled(LogConfig config) {
        if (config != null) {
            String value = config.layout.get(Elements.ENABLED.localName);
            return (new Boolean(value).booleanValue());
        }
        return false;
    }

    protected LogConfig initLogConfig(String logConfigName) {
        LogConfig logConfig = model.new LogConfig();
        logConfig.id = logConfigName;
        logConfig.roles = "*:info";
        logConfig.files.put(Elements.ENABLED.localName, "");
        logConfig.files.put(Elements.DIR.localName, "");
        logConfig.files.put(Elements.NAME.localName, "");
        logConfig.files.put(Elements.MAX_NUMBER.localName, "");
        logConfig.files.put(Elements.MAX_SIZE.localName, "");
        logConfig.files.put(Elements.APPEND.localName, "false");
        logConfig.terminal.put(Elements.ENABLED.localName, "");
        logConfig.terminal.put(Elements.SYS_OUT_REDIRECT.localName, "false");
        logConfig.terminal.put(Elements.SYS_ERR_REDIRECT.localName, "false");
        logConfig.terminal.put(Elements.ENCODING.localName, "");
        logConfig.layout.put(Elements.ENABLED.localName, "false");
        logConfig.layout.put(Elements.CLASS.localName, "");
        logConfig.layout.put(Elements.ARG.localName, "");
        model.logConfigsList.logConfigs.add(logConfig);
        return logConfig;
    }
    
    public LogConfig copyLogConfig(LogConfig logConfig,String name){
    	 LogConfig copiedlogConfig = model.new LogConfig();
    	 copiedlogConfig.id = name;
    	 copiedlogConfig.roles =logConfig.roles;
    	 copiedlogConfig.enabled=logConfig.enabled;
    	copiedlogConfig.files.put(Elements.ENABLED.localName, logConfig.files.get(Elements.ENABLED.localName));
    	copiedlogConfig.files.put(Elements.DIR.localName,  logConfig.files.get(Elements.DIR.localName));
    	copiedlogConfig.files.put(Elements.NAME.localName, logConfig.files.get(Elements.NAME.localName));
    	copiedlogConfig.files.put(Elements.MAX_NUMBER.localName,  logConfig.files.get(Elements.MAX_NUMBER.localName));
    	copiedlogConfig.files.put(Elements.MAX_SIZE.localName, logConfig.files.get(Elements.MAX_SIZE.localName));
    	copiedlogConfig.files.put(Elements.APPEND.localName,  logConfig.files.get(Elements.APPEND.localName));
    	copiedlogConfig.terminal.put(Elements.ENABLED.localName, logConfig.terminal.get(Elements.ENABLED.localName));
    	copiedlogConfig.terminal.put(Elements.SYS_OUT_REDIRECT.localName, logConfig.terminal.get(Elements.SYS_OUT_REDIRECT.localName));
    	copiedlogConfig.terminal.put(Elements.SYS_ERR_REDIRECT.localName, logConfig.terminal.get(Elements.SYS_ERR_REDIRECT.localName));
    	copiedlogConfig.terminal.put(Elements.ENCODING.localName,  logConfig.terminal.get(Elements.ENCODING.localName));
    	copiedlogConfig.layout.put(Elements.ENABLED.localName, logConfig.layout.get(Elements.ENABLED.localName));
    	copiedlogConfig.layout.put(Elements.CLASS.localName,  logConfig.layout.get(Elements.CLASS.localName));
    	copiedlogConfig.layout.put(Elements.ARG.localName,  logConfig.layout.get(Elements.ARG.localName));
        model.logConfigsList.logConfigs.add(copiedlogConfig);
        modified();
        System.out.println(copiedlogConfig.terminal.get(Elements.SYS_OUT_REDIRECT.localName));
        return copiedlogConfig;
        
    }
    
    public LogConfig addLogConfig(String logConfigName) {
        LogConfig logConfig = initLogConfig(logConfigName);
        modified();
        return logConfig;
    }

    public String updateLogConfigName(LogConfig logConfig, String newName) {
        if (!newName.equalsIgnoreCase(logConfig.id)) {
        	
        	String namesArray[]=getAllGroups();
        	ArrayList<String> names=new ArrayList<String>();
        	
        	for(String str:namesArray){
        		names.add(str);
        	}
        	if(!names.contains(newName)){
        		 logConfig.id = newName;
                 modified();
                 return "true";
            }
        		return "trueError";
        }
        return "false";
    }

    public void updateLogConfigEnable(LogConfig logConfig, boolean en) {
        if (logConfig != null && logConfig.enabled ^ en) {
            logConfig.enabled = en;
            modified();
        }
    }

    public void updateLogConfigRoles(LogConfig logConfig, String newRoles) {
        if (logConfig != null && !logConfig.roles.equalsIgnoreCase(newRoles)) {
            logConfig.roles = newRoles;
            modified();
        }
    }

    public boolean removeLogConfig(String logConfigName) {
        LogConfig logConfig = getLogConfig(logConfigName);
        return removeLogConfig(logConfig);
    }

    public boolean removeLogConfig(LogConfig logConfig) {
        if (logConfig != null) {
            model.logConfigsList.logConfigs.remove(logConfig);
            modified();
            return true;
        }
        return false;
    }

    public void updateLogConfigFilesEnable(LogConfig logConfig, boolean en) {
        if (logConfig == null)
            return;
        String value = logConfig.files.get(Elements.ENABLED.localName);
        if (new Boolean(value).booleanValue() ^ en) {
            logConfig.files.put(Elements.ENABLED.localName, new Boolean(en).toString());
            modified();
        }
    }

    public void updateLogConfigTerminalEnable(LogConfig config, boolean en) {
        if (config == null)
            return;
        String value = config.terminal.get(Elements.ENABLED.localName);
        if (new Boolean(value).booleanValue() ^ en) {
            config.terminal.put(Elements.ENABLED.localName, new Boolean(en).toString());
            modified();
        }
    }

    public void updateLogConfigLayoutEnable(LogConfig config, boolean en) {
        if (config == null)
            return;
        String value = config.layout.get(Elements.ENABLED.localName);
        if (new Boolean(value).booleanValue() ^ en) {
            config.layout.put(Elements.ENABLED.localName, new Boolean(en).toString());
            modified();
        }
    }

    public void updateLogConfigFilesValue(LogConfig config, String key, String value) {
        if (config != null && !config.files.get(key).equalsIgnoreCase(value)) {
            config.files.put(key, value);
            modified();
        }
    }

    public void updateLogConfigTerminalValue(LogConfig config, String key, String value) {
        if (config != null && !config.terminal.get(key).equalsIgnoreCase(value)) {
            config.terminal.put(key, value);
            modified();
        }
    }

    public void updateLogConfigLayoutValue(LogConfig config, String key, String value) {
        if (config != null && !config.layout.get(key).equalsIgnoreCase(value)) {
            config.layout.put(key, value);
            modified();
        }
    }

    // ************ Agent Classes ***************
    private void setAgentClasses(String path, ArrayList<String> id, String key, String value) {
        if (path.equals(Elements.AGENT_CLASSES.localName) && id.size()==1) {
            AgentClass agentClass = null;
            if (key.equals(Elements.INFERENCE_AGENT_CLASS.localName)) {
                agentClass = model.new InfAgent(id.get(0));
            } else if (key.equals(Elements.QUERY_AGENT_CLASS.localName)) {
                agentClass = model.new QueryAgent(id.get(0));
            } else if (key.equals(Elements.CACHE_AGENT_CLASS.localName)) {
                agentClass = model.new CacheAgent(id.get(0));
            } else if (key.equals(Elements.DASHBOARD_AGENT_CLASS.localName)) {
                agentClass = model.new DashboardAgent(id.get(0));
            } else if (key.equals(Elements.PROCESS_AGENT_CLASS.localName)) {
            	agentClass = model.new ProcessAgent(id.get(0));
            } else if (key.equals(Elements.LIVEVIEW_AGENT_CLASS.localName)) {
            	agentClass = model.new LiveViewAgent(id.get(0));
            }
            if (agentClass != null)
                model.agentClasses.add(agentClass);
            
        } else if (path.startsWith("agent-classes.inference-agent-class")) {
            InfAgent agentClass = (InfAgent) getAgentClass(id.get(0));
            if (agentClass != null) {
                if (path.equals("agent-classes.inference-agent-class")) {
                    if (key.equals(Elements.CONCURRENT_RTC.localName)) {
                        agentClass.concRtc = new Boolean(value).booleanValue();
                    } else if (key.equals(Elements.CHECK_FOR_DUPLICATES.localName)) {
                        agentClass.checkForDuplicates = new Boolean(value).booleanValue();
                    }
                } else if (path.equals("agent-classes.inference-agent-class.local-cache.eviction")) {
                    if (key.equals("max-size")) {
                        agentClass.localCacheMaxSize = value;
                    } else if (key.equals("max-time")) {
                        agentClass.localCacheEvictionTime = value;
                    }
                } else if (path.equals("agent-classes.inference-agent-class.shared-queue")) {
                    if (key.equals(Elements.SIZE.localName)) {
                        agentClass.sharedQueueSize = value;
                    } else if (key.equals(Elements.WORKERS.localName)) {
                        agentClass.sharedQueueWorkers = value;
                    }
                } else if (path.equals("agent-classes.inference-agent-class.load")) {
                    if (key.equals(Elements.MAX_ACTIVE.localName)) {
                        agentClass.maxActive = value;
                    }
                } else if (path.equals("agent-classes.inference-agent-class.businessworks")) {
                	agentClass.bwRepoUrl = value;
                } else if (path.equals("agent-classes.inference-agent-class.rules")) {
                    processAgentRuleGroups(agentClass, key, value);
                } else if (path.equals("agent-classes.inference-agent-class.destinations")) {
                    processAgentDestinationGroups(agentClass, key, value);
                } else if (path.equals("agent-classes.inference-agent-class.startup")) {
                    processAgentFunctions(agentClass, key, value, true);
                } else if (path.equals("agent-classes.inference-agent-class.shutdown")) {
                    processAgentFunctions(agentClass, key, value, false);
                }
            }
            
        } else if (path.startsWith("agent-classes.process-agent-class")) {
        	ProcessAgent agentClass = (ProcessAgent) getAgentClass(id.get(0));
        	if(agentClass != null) {
        		if (path.equals("agent-classes.process-agent-class.load")) {
                    if (key.equals(Elements.MAX_ACTIVE.localName)) {
                        agentClass.maxActive = value;
                    }
        		} else if (path.equals("agent-classes.process-agent-class.process-engine.process")) {
        			processAgentProcessGroups(agentClass, key, value);
        		} else if (path.equals("agent-classes.process-agent-class.process-engine.rules")) {
        			processAgentRuleGroups(agentClass, key, value);
        		} else if (path.equals("agent-classes.process-agent-class.process-engine.destinations")) {
        			processAgentDestinationGroups(agentClass, key, value);
        		} else if (path.equals("agent-classes.process-agent-class.process-engine.startup")) {
        			processAgentFunctions(agentClass, key, value, true);
        		} else if (path.equals("agent-classes.process-agent-class.process-engine.shutdown")) {
        			processAgentFunctions(agentClass, key, value, false);
        		} else if (path.equals("agent-classes.process-agent-class.process-engine.job-manager")) {
                    if (key.equals("job-pool-queue-size")) {
                        agentClass.jobPoolQueueSize = value;
                    } else if (key.equals("job-pool-thread-count")) {
                        agentClass.jobPoolThreadCount = value;
                    }
        		}  
        	}
        	
        } else if (path.startsWith("agent-classes.query-agent-class")) {
            QueryAgent agentClass = (QueryAgent) getAgentClass(id.get(0));
            if (agentClass != null) {
                if (path.equals("agent-classes.query-agent-class.local-cache.eviction")) {
                    if (key.equals("max-size")) {
                        agentClass.localCacheMaxSize = value;
                    } else if (key.equals("max-time")) {
                        agentClass.localCacheEvictionTime = value;
                    }
                } else if (path.equals("agent-classes.query-agent-class.shared-queue")) {
                    if (key.equals(Elements.SIZE.localName)) {
                        agentClass.sharedQueueSize = value;
                    } else if (key.equals(Elements.WORKERS.localName)) {
                        agentClass.sharedQueueWorkers = value;
                    }
                } else if (path.equals("agent-classes.query-agent-class.load")) {
                    if (key.equals(Elements.MAX_ACTIVE.localName)) {
                        agentClass.maxActive = value;
                    }
                } else if (path.equals("agent-classes.query-agent-class.destinations")) {
                    processAgentDestinationGroups(agentClass, key, value);
                } else if (path.equals("agent-classes.query-agent-class.startup")) {
                    processAgentFunctions(agentClass, key, value, true);
                } else if (path.equals("agent-classes.query-agent-class.shutdown")) {
                    processAgentFunctions(agentClass, key, value, false);
                }
            }
            
        } else if (path.startsWith("agent-classes.dashboard-agent-class")) {
            DashboardAgent agentClass = (DashboardAgent) getAgentClass(id.get(0));
            if (agentClass != null) {
                if (path.equals("agent-classes.dashboard-agent-class")) {
                } else if (path.equals("agent-classes.dashboard-agent-class.rules")) {
                    processAgentRuleGroups(agentClass, key, value);
                } else if (path.equals("agent-classes.dashboard-agent-class.destinations")) {
                    processAgentDestinationGroups(agentClass, key, value);
                } else if (path.equals("agent-classes.dashboard-agent-class.startup")) {
                    processAgentFunctions(agentClass, key, value, true);
                } else if (path.equals("agent-classes.dashboard-agent-class.shutdown")) {
                    processAgentFunctions(agentClass, key, value, false);
                }
            }
            
        } else if (path.startsWith("agent-classes.liveview-agent-class")) {
        	LiveViewAgent agentClass = (LiveViewAgent) getAgentClass(id.get(0));
        	if (agentClass != null) {
        		if (path.equals("agent-classes.liveview-agent-class.load")) {
                    if (key.equals(Elements.MAX_ACTIVE.localName)) {
                        agentClass.maxActive = value;
                    }
        		} else if (path.equals("agent-classes.liveview-agent-class.ldm-connection")) {
        			processLDMConnection(agentClass.ldmConnection, key, value);
        		} else if (path.equals("agent-classes.liveview-agent-class.entity-set")) {
        			processEntitySet(agentClass.entitySetConfig, key, value);
        		} else if (path.equals("agent-classes.liveview-agent-class.entity-set.entity")) {
        			processEntityConfig(agentClass.entitySetConfig, key, value);
        		} else if (path.equals("agent-classes.liveview-agent-class.startup")) {
                    processAgentFunctions(agentClass, key, value, true);
                } else if (path.equals("agent-classes.liveview-agent-class.shutdown")) {
                    processAgentFunctions(agentClass, key, value, false);
                } else if (key.equals(Elements.PUBLISHER_QUEUE_SIZE.localName)) {
                	agentClass.publisherQueueSize = value;
                } else if (key.equals(Elements.PUBLISHER_THREAD_COUNT.localName)) {
                	agentClass.publisherThreadCount = value;
                }
        	}
        }
    }

    private void processAgentRuleGroups(DashInfProcAgent agentClass, String key, String value) {
        if (key.equals("uri")) {
            initRule(agentClass, value);
        } else if (key.equals("ref")) {
            initAgentRulesGrp(agentClass, value);
        }
    }

    private void processAgentDestinationGroups(DashInfProcQueryAgent agentClass, String key, String value) {
        if (key.equals("destination")) {
            initDestination(agentClass, value);
        } else if (key.equals("ref")) {
            initAgentDestinationsGrp(agentClass, value);
        }
    }

    private void processAgentFunctions(DashInfProcQueryAgent agentClass, String key, String value, boolean isStartup) {
        if (key.equals("uri")) {
            initFunction(agentClass, value, isStartup);
        } else if (key.equals("ref")) {
            initAgentFunctionsGrp(agentClass, isStartup, value);
        }
    }

    private void processAgentProcessGroups(ProcessAgent agentClass, String key, String value) {
        if (key.equals("uri")) {
            initProcess(agentClass, value);
        } else if (key.equals("ref")) {
            initAgentProcessesGrp(agentClass, value);
        }
    }
    
    public ArrayList<AgentClass> getAgentClasses() {
    	return model.agentClasses;
    }

    public ArrayList<AgentClass> getAgentClasses(String type) {
        ArrayList<AgentClass> agentClasses = new ArrayList<AgentClass>();
        for (AgentClass agentClass: model.agentClasses) {
            if (agentClass.type.equals(type))
                agentClasses.add(agentClass);
        }
        return agentClasses;
    }

    public ArrayList<String> getAgentClassNames() {
        ArrayList<String> classNames = new ArrayList<String>();
        for (AgentClass agtClass: model.agentClasses) {
            classNames.add(agtClass.name);
        }
        return classNames;
    }

    public AgentClass getAgentClass(String agentClassName) {
        for (AgentClass agentClass: model.agentClasses) {
            if (agentClassName.equals(agentClass.name))
                return agentClass;
        }
        return null;
    }

    public RuleElement[] getAgentRules(AgentRulesGrp agentRulesGrp) {
        return agentRulesGrp.agentRules.toArray(new RuleElement[0]);
    }

    public ArrayList<String> getAgentRulesGrpNames(DashInfProcAgent infAgent, boolean excludeRefs) {
        return (getAgentRulesGrpNames(infAgent.agentRulesGrpObj, excludeRefs));
    }

    public ArrayList<String> getAgentRulesGrpNames(AgentRulesGrp agentRulesGrp, boolean excludeRefs) {
        ArrayList<String> names = new ArrayList<String>();
        if (agentRulesGrp != null) {
            for (RuleElement ruleElement: agentRulesGrp.agentRules) {
                if (ruleElement instanceof Rule) {
                    Rule rule = (Rule) ruleElement;
                    if (excludeRefs && rule.isRef)
                        continue;
                    names.add(rule.uri);
                } else if (!excludeRefs && (ruleElement instanceof AgentRulesGrpElement)) {
                    names.add(((AgentRulesGrpElement)ruleElement).rulesGrp.id);
                }
            }
        }
        return names;
    }

    public DestinationsGrp[] getAgentDestinationGrp(DashInfProcQueryAgent agent) {
        if (agent.agentDestinationsGrpObj != null) {
            return (agent.agentDestinationsGrpObj.agentDestinations.toArray(new DestinationsGrp[0]));
        }
        return new DestinationsGrp[0];
    }

    public DestinationElement[] getAgentDestinations(AgentDestinationsGrp agentDestinationsGrp) {
        return agentDestinationsGrp.agentDestinations.toArray(new DestinationElement[0]);
    }

    public ArrayList<String> getAgentDestinationsGrpNames(AgentDestinationsGrp agentDestinationsGrp, boolean excludeRefs) {
        ArrayList<String> names = new ArrayList<String>();
        if (agentDestinationsGrp != null) {
            for (DestinationElement destinationElement: agentDestinationsGrp.agentDestinations) {
                if (destinationElement instanceof Destination) {
                    Destination destination =(Destination)destinationElement;
                    if (excludeRefs && destination.isRef)
                        continue;
                    names.add(destination.id);
                } else if (!excludeRefs && destinationElement instanceof AgentDestinationsGrpElement) {
                    names.add(((AgentDestinationsGrpElement)destinationElement).destinationsGrp.id);
                }
            }
        }
        return names;
    }

    public ArrayList<String> getAgentDestinationsGrpUris(AgentDestinationsGrp agentDestinationsGrp, boolean excludeRefs) {
        ArrayList<String> names = new ArrayList<String>();
        if (agentDestinationsGrp != null) {
            for (DestinationElement destinationElement: agentDestinationsGrp.agentDestinations) {
                if (destinationElement instanceof Destination) {
                    Destination destination =(Destination)destinationElement;
                    if (excludeRefs && destination.isRef)
                        continue;
                    names.add(destination.destinationVal.get(Elements.URI.localName));
                } else if (!excludeRefs && (destinationElement instanceof AgentDestinationsGrpElement)) {
                    names.add(((AgentDestinationsGrpElement)destinationElement).destinationsGrp.id);
                }
            }
        }
        return names;
    }

    public FunctionElement[] getAgentFunctions(AgentBaseFunctionsGrp agentBaseFunctionsGrp) {
        return agentBaseFunctionsGrp.agentFunctions.toArray(new FunctionElement[0]);
        /*
        ArrayList<Object> funcObjs = new ArrayList<Object>();
        for (Function function: agentBaseFunctionsGrp.agentFunctions) {
            funcObjs.add(function);
        }
        for (AgentFunctionsGrpElement funcGrpRef: agentBaseFunctionsGrp.agentFunctionsGrpsRef) {
            funcObjs.add(funcGrpRef);
        }
        return funcObjs.toArray(new Object[0]);
        */
    }

    /*
    public FunctionsGrp[] getAgentShutdownFunctions(InfQueryAgent agent) {
        if (agent.agentShutdownFunctionsGrpObj != null) {
            return (agent.agentShutdownFunctionsGrpObj.agentFunctionsGrpsRef.toArray(new FunctionsGrp[0]));
        }
        return new FunctionsGrp[0];
    }
    */

    /*
    public Object[] getAgentShutdownFunctions(InfQueryAgent.AgentShutdownFunctionsGrp agentShutdownFunctionsGrp) {
        ArrayList<Object> funcObjs = new ArrayList<Object>();
        for (Function function: agentShutdownFunctionsGrp.agentFunctions) {
            funcObjs.add(function);
        }
        for (AgentFunctionsGrpElement funcGrpRef: agentShutdownFunctionsGrp.agentFunctionsGrpsRef) {
            funcObjs.add(funcGrpRef);
        }
        return funcObjs.toArray(new Object[0]);
    }
    */

    public ArrayList<String> getAgentStartupFunctionsGrpNames(DashInfProcQueryAgent agent) {
        return (getAgentFunctionsGrpNames(agent.agentStartupFunctionsGrpObj));
    }

    /*
    public String[] getAgentStartupFunctionsGrpNames(AgentStartupFunctionsGrp agentStartupFunctionsGrp) {
        ArrayList<String> names = new ArrayList<String>();
        if (agentStartupFunctionsGrp != null) {
            for (FunctionsGrp functionsGrp: agentStartupFunctionsGrp.agentFunctionsGrpsRef) {
                names.add(functionsGrp.id);
            }
            return names.toArray(new String[0]);
        }
        return new String[0];
    }
    */

    public ArrayList<String> getAgentShutdownFunctionsGrpNames(DashInfProcQueryAgent agent) {
        return (getAgentFunctionsGrpNames(agent.agentShutdownFunctionsGrpObj));
    }

    /*
    public String[] getAgentShutdownFunctionsGrpNames(AgentShutdownFunctionsGrp agentShutdownFunctionsGrp) {
        ArrayList<String> names = new ArrayList<String>();
        if (agentShutdownFunctionsGrp != null) {
            for (FunctionsGrp functionsGrp: agentShutdownFunctionsGrp.agentFunctionsGrpsRef) {
                names.add(functionsGrp.id);
            }
            return names.toArray(new String[0]);
        }
        return new String[0];
    }
    */

    public boolean updateAgentInfQueryRef(MMAgent mmAgent, String ref, boolean isInf) {
        if (isInf && !mmAgent.infAgentRef.equals(ref)) {
            mmAgent.infAgentRef = ref;
            modified();
            return true;
        } else if (!isInf && !mmAgent.queryAgentRef.equals(ref)) {
            mmAgent.queryAgentRef = ref;
            modified();
            return true;
        }
        return false;
    }

    public ArrayList<String> getAlertConfigListIds(AlertConfigList alertConfigList) {
        ArrayList<String> ids = new ArrayList<String>();
        for (AlertConfig config: alertConfigList.alertConfigs) {
            ids.add(config.id);
        }
        return ids;
    }

    public boolean updateAlertConfigCondition(AlertConfig config, String key, String value) {
        if (config != null && !config.condition.values.get(key).equals(value)) {
            config.condition.values.put(key, value);
            modified();
            return true;
        }
        return false;
    }

    public boolean updateAlertConfigId(AlertConfig config, String value) {
        if (config != null && !config.id.equals(value)) {
            config.id = value;
            modified();
            return true;
        }
        return false;
    }

    public ArrayList<String> getRuleConfigListIds(RuleConfigList ruleConfigList) {
        ArrayList<String> ids = new ArrayList<String>();
        for (ClusterMember member: ruleConfigList.clusterMembers) {
            ids.add(member.id);
        }
        return ids;
    }

    public ArrayList<String> getClusterMemberIds(ClusterMember clusterMember) {
        ArrayList<String> ids = new ArrayList<String>();
        for (SetProperty property: clusterMember.setProperties) {
            //names.add(getSetPropertyChildName(property));
            ids.add(property.id);
        }
        return ids;
    }

    public boolean updateClusterMemberId(ClusterMember member, String value) {
        if (member != null && !member.id.equals(value)) {
            member.id = value;
            modified();
            return true;
        }
        return false;
    }

    public String getSetPropertyChildName(SetProperty property) {
        if (property.setPropertyChild instanceof ChildClusterMember) {
            return ((ChildClusterMember)property.setPropertyChild).path;
        } else if (property.setPropertyChild instanceof Notification) {
            for (LinkedHashMap<String,String> map: ((Notification)property.setPropertyChild).properties) {
                if (map.get(Elements.NAME.localName).equals("severity"))
                    return ("Notification - " + map.get("value"));
            }
        }
        return ("SetProperty Child");
    }

    public boolean updateSetProperty(SetProperty property, String key, String value) {
        if (property == null)
            return false;
        if (key.equals(Elements.NAME.localName) && !property.name.equals(value)) {
            property.name = value;
            modified();
            return true;
        } else if (key.equals("value") && !property.value.equals(value)) {
            property.value = value;
            modified();
            return true;
        } else if (key.equals("id") && !property.id.equals(value)) {
            property.id = value;
            modified();
            return true;
        } else if (key.equals("tolerance") && !property.setPropertyChild.tolerance.equals(value)) {
            property.setPropertyChild.tolerance = value;
            modified();
            return true;
        } else if (key.equals("pathrange")) {
            if (property.setPropertyChild instanceof ChildClusterMember && !((ChildClusterMember)property.setPropertyChild).path.equals(value)) {
                ((ChildClusterMember)property.setPropertyChild).path = value;
                modified();
                return true;
            } else if (property.setPropertyChild instanceof Notification && !((Notification)property.setPropertyChild).range.equals(value)) {
                ((Notification)property.setPropertyChild).range = value;
                modified();
                return true;
            }
        }
        return false;
    }

    public boolean updateSetPropertyChildType(SetProperty property, boolean isChildClusterMember) {
        if (property.setPropertyChild instanceof ChildClusterMember && !isChildClusterMember) {
            if (property.notification != null)
                property.setPropertyChild = property.notification;
            else
                property.setPropertyChild = property.new Notification();
            modified();
            return true;
        } else if (property.setPropertyChild instanceof Notification && isChildClusterMember) {
            if (property.childClusterMember != null)
                property.setPropertyChild = property.childClusterMember;
            else
                property.setPropertyChild = property.new ChildClusterMember();
            modified();
            return true;
        }
        return false;
    }

    public boolean updateActionConfigId(ActionConfig config, String value) {
        if (config != null && !config.id.equals(value)) {
            config.id = value;
            modified();
            return true;
        }
        return false;
    }

    public boolean updateActionConfigConditionType(ActionConfig config, boolean isAlert) {
        if (config.triggerCondition.trigger instanceof Alert && !isAlert) {
            if (config.triggerCondition.healthLevel != null)
                config.triggerCondition.trigger = config.triggerCondition.healthLevel;
            else
                config.triggerCondition.trigger = config.new HealthLevel();
            modified();
            return true;
        } else if (config.triggerCondition.trigger instanceof HealthLevel && isAlert) {
            if (config.triggerCondition.alert != null)
                config.triggerCondition.trigger = config.triggerCondition.alert;
            else
                config.triggerCondition.trigger = config.new Alert();
            modified();
            return true;
        }
        return false;
    }

    public boolean updateActionConfigCondition(ActionConfig config, String key, String value) {
        if (config == null)
            return false;
        if (key.equals("path") && !config.triggerCondition.trigger.path.equals(value)) {
            config.triggerCondition.trigger.path = value;
            modified();
            return true;
        } else if (key.equals("sevval")) {
            if (config.triggerCondition.trigger instanceof Alert && !((Alert)config.triggerCondition.trigger).severity.equals(value)) {
                ((Alert)config.triggerCondition.trigger).severity = value;
                modified();
                return true;
            } else if (config.triggerCondition.trigger instanceof HealthLevel && !((HealthLevel)config.triggerCondition.trigger).value.equals(value)) {
                ((HealthLevel)config.triggerCondition.trigger).value = value;
                modified();
                return true;
            }
        }
        return false;
    }

    public boolean updateActionConfigActionType(ActionConfig config, boolean isExecCommand) {
        if (config.action.actionElement instanceof ExecCommand && !isExecCommand) {
            if (config.action.sendEmail != null)
                config.action.actionElement = config.action.sendEmail;
            else
                config.action.actionElement = config.new SendEmail();
            modified();
            return true;
        } else if (config.action.actionElement instanceof SendEmail && isExecCommand) {
            if (config.action.execCommand != null)
                config.action.actionElement = config.action.execCommand;
            else
                config.action.actionElement = config.new ExecCommand();
            modified();
            return true;
        }
        return false;
    }

    public ArrayList<String> getActionConfigListIds(ActionConfigList actionConfigList) {
        ArrayList<String> ids = new ArrayList<String>();
        for (ActionConfig config: actionConfigList.actionConfigs) {
            ids.add(config.id);
        }
        return ids;
    }

    public boolean updateActionConfigExecCommand(ActionConfig config, String cmd) {
        if (config != null && !((ExecCommand)config.action.actionElement).command.equals(cmd)) {
            ((ExecCommand)config.action.actionElement).command = cmd;
            modified();
            return true;
        }
        return false;
    }

    public boolean updateActionConfigSendEmail(ActionConfig config, String key, String value) {
        if (config == null)
            return false;
        if (!((SendEmail)config.action.actionElement).values.get(key).equals(value)) {
            ((SendEmail)config.action.actionElement).values.put(key, value);
            modified();
            return true;
        }
        return false;
    }

    public boolean updateClusterMemberPath(ClusterMember member, String path) {
        if (!member.path.equals(path)) {
            member.path = path;
            modified();
            return true;
        }
        return false;
    }

    public AlertConfig addAlertConfig(AlertConfigList alertConfigList) {
        String newId = IdUtil.generateSequenceId("alert-config", getAlertConfigListIds(alertConfigList));
        AlertConfig config = alertConfigList.new AlertConfig(alertConfigList, newId);
        alertConfigList.alertConfigs.add(config);
        modified();
        return config;
    }

    public ClusterMember addClusterMember(RuleConfigList ruleConfigList) {
        String newId = IdUtil.generateSequenceId("cluster-member", getRuleConfigListIds(ruleConfigList));
        ClusterMember member = ruleConfigList.new ClusterMember(ruleConfigList, newId);
        ruleConfigList.clusterMembers.add(member);
        modified();
        return member;
    }

    public SetProperty addSetProperty(ClusterMember clusterMember) {
        String newId = IdUtil.generateSequenceId("set-property", getClusterMemberIds(clusterMember));
        SetProperty property = clusterMember.new SetProperty(clusterMember, newId);
        clusterMember.setProperties.add(property);
        modified();
        return property;
    }

    public ActionConfig addActionConfig(ActionConfigList actionConfigList) {
        String newId = IdUtil.generateSequenceId("action-config", getActionConfigListIds(actionConfigList));
        ActionConfig config = actionConfigList.new ActionConfig(actionConfigList, newId);
        actionConfigList.actionConfigs.add(config);
        modified();
        return config;
    }

    public void removeAlertConfig(AlertConfig config) {
        config.parent.alertConfigs.remove(config);
        modified();
    }

    public void removeClusterMember(ClusterMember member) {
        member.parent.clusterMembers.remove(member);
        modified();
    }

    public void removeSetProperty(SetProperty property) {
        property.parent.setProperties.remove(property);
        modified();
    }

    public void removeActionConfig(ActionConfig config) {
        config.parent.actionConfigs.remove(config);
        modified();
    }

    public AgentClass addAgent(String name, String type) {
        if (name==null || type==null)
            return null;
        AgentClass agent = null;
        if (type.equals(AgentClass.AGENT_TYPE_INFERENCE)) {
            agent = model.new InfAgent(name);
            ((InfAgent)agent).agentRulesGrpObj = ((InfAgent)agent).new AgentRulesGrp((InfAgent)agent);
            ((InfAgent)agent).agentDestinationsGrpObj = ((InfAgent)agent).new AgentDestinationsGrp((InfAgent)agent);
            ((InfAgent)agent).agentStartupFunctionsGrpObj = ((InfAgent)agent).new AgentStartupFunctionsGrp((InfAgent)agent);
            ((InfAgent)agent).agentShutdownFunctionsGrpObj = ((InfAgent)agent).new AgentShutdownFunctionsGrp((InfAgent)agent);
        } else if (type.equals(AgentClass.AGENT_TYPE_CACHE)) {
            agent = model.new CacheAgent(name);
        } else if (type.equals(AgentClass.AGENT_TYPE_QUERY)) {
            agent = model.new QueryAgent(name);
            ((QueryAgent)agent).agentDestinationsGrpObj = ((QueryAgent)agent).new AgentDestinationsGrp((QueryAgent)agent);
            ((QueryAgent)agent).agentStartupFunctionsGrpObj = ((QueryAgent)agent).new AgentStartupFunctionsGrp((QueryAgent)agent);
            ((QueryAgent)agent).agentShutdownFunctionsGrpObj = ((QueryAgent)agent).new AgentShutdownFunctionsGrp((QueryAgent)agent);
        } else if (type.equals(AgentClass.AGENT_TYPE_DASHBOARD)) {
            agent = model.new DashboardAgent(name);
        } else if (type.equals(AgentClass.AGENT_TYPE_MM)) {
            agent = model.new MMAgent(name);
        } else if (type.equals(AgentClass.AGENT_TYPE_PROCESS)) {
            agent = model.new ProcessAgent(name);
        } else if (type.equals(AgentClass.AGENT_TYPE_LIVEVIEW)) {
            agent = model.new LiveViewAgent(name);
        }
        if (agent != null) {
            model.agentClasses.add(agent);
            modified();
        }
        return agent;
    }

    public boolean removeAgent(String name) {
        if (name==null)
            return false;
        AgentClass agent = getAgentClass(name);
        if (agent == null)
            return false;
        model.agentClasses.remove(agent);
        modified();
        return true;
    }

    public boolean removeAgent(AgentClass agent) {
        if (agent==null)
            return false;
        if (model.agentClasses.contains(agent)) {
            model.agentClasses.remove(agent);
            modified();
            return true;
        }
        return false;
    }

    /*
    public boolean removeAgentRulesGrp(InfAgent agent, String rulesGrpName) {
        for (RulesGrp rulesGrp: agent.agentRulesGrpObj.agentRules) {
            if (rulesGrp.id.equalsIgnoreCase(rulesGrpName)) {
                agent.agentRulesGrpObj.agentRules.remove(rulesGrp);
                modified();
                return true;
            }
        }
        return false;
    }
    */

    public boolean removeAgentRulesGrp(AgentRulesGrpElement ruleElement) {
        DashInfProcAgent agent = ruleElement.parentAgent;
        if (agent.agentRulesGrpObj.agentRules.contains(ruleElement)) {
            agent.agentRulesGrpObj.agentRules.remove(ruleElement);
            modified();
            return true;
        }
        return false;
    }

    public boolean removeAgentDestinationsGrp(AgentDestinationsGrpElement destinationElement) {
        DashInfProcQueryAgent agent = destinationElement.parentAgent;
        if (agent.agentDestinationsGrpObj.agentDestinations.contains(destinationElement)) {
            agent.agentDestinationsGrpObj.agentDestinations.remove(destinationElement);
            modified();
            return true;
        }
        return false;
    }

    public boolean removeAgentFunctionsGrp(AgentFunctionsGrpElement functionsGrp) {
        DashInfProcQueryAgent agent = functionsGrp.parentAgent;
        AgentBaseFunctionsGrp baseFunctionsGrp;
        if (functionsGrp.isStartup) {
            baseFunctionsGrp = agent.agentStartupFunctionsGrpObj;
        } else {
            baseFunctionsGrp = agent.agentShutdownFunctionsGrpObj;
        }
        if (baseFunctionsGrp != null && baseFunctionsGrp.agentFunctions.contains(functionsGrp)) {
            baseFunctionsGrp.agentFunctions.remove(functionsGrp);
            modified();
            return true;
        }
        return false;
    }

    public boolean updateAgentName(AgentClass agent, String name) {
        if (agent != null) {
            if (!name.equals(agent.name)) {
                updateProcUnitAgentNames(agent.name, name); //update other objects before assigning new name
                agent.name = name;
                modified();
                return true;
            }
        }
        return false;
    }

    public boolean updateAgentRulesGroup(DashInfProcAgent agent, RulesGrp rulesGrp) {
        if (agent != null) {
            agent.agentRulesGrpObj.agentRules.add(rulesGrp);
            modified();
            return true;
        }
        return false;
    }

    public void updateAgentRulesGroup(InfAgent agent, String rulesGrpName) {
        RulesGrp rulesGrp = getRulesGrpRef(rulesGrpName);
        updateAgentRulesGroup(agent, rulesGrp);
    }

    public boolean updateAgentDestinationsGroup(DashInfProcQueryAgent agent, DestinationsGrp destinationsGrp) {
        if (agent != null) {
            agent.agentDestinationsGrpObj.agentDestinations.add(destinationsGrp);
            modified();
            return true;
        }
        return false;
    }

    public void updateAgentDestinationsGroup(DashInfProcQueryAgent agent, String destinationsGrpName) {
        DestinationsGrp destinationsGrp = getDestinationsGroupRef(destinationsGrpName);
        updateAgentDestinationsGroup(agent, destinationsGrp);
    }

    public boolean updateAgentStartupFunctionsGroup(DashInfProcQueryAgent agent, FunctionsGrp functionsGrp) {
        if (agent != null) {
            AgentFunctionsGrpElement agentFunctionsGrpElement = agent.new AgentFunctionsGrpElement(agent, functionsGrp, true);
            agent.agentStartupFunctionsGrpObj.agentFunctions.add(agentFunctionsGrpElement);
            modified();
            return true;
        }
        return false;
    }

    public void updateAgentStartupFunctionsGroup(DashInfProcQueryAgent agent, String functionsGrpName) {
        FunctionsGrp functionsGrp = getFunctionsGrpRef(functionsGrpName);
        updateAgentStartupFunctionsGroup(agent, functionsGrp);
    }

    public boolean updateAgentShutdownFunctionsGroup(DashInfProcQueryAgent agent, FunctionsGrp functionsGrp) {
        if (agent != null) {
            AgentFunctionsGrpElement agentFunctionsGrpElement = agent.new AgentFunctionsGrpElement(agent, functionsGrp, false);
            agent.agentShutdownFunctionsGrpObj.agentFunctions.add(agentFunctionsGrpElement);
            modified();
            return true;
        }
        return false;
    }

    public void updateAgentShutdownFunctionsGroup(DashInfProcQueryAgent agent, String functionsGrpName) {
        FunctionsGrp functionsGrp = getFunctionsGrpRef(functionsGrpName);
        updateAgentShutdownFunctionsGroup(agent, functionsGrp);
    }

    public boolean updateAgentBwRepoUrl(InfAgent agent, String url) {
        if (agent != null) {
            if (!agent.bwRepoUrl.equals(url)) {
                agent.bwRepoUrl = url;
                modified();
                return true;
            }
        }
        return false;
    }

    public void updateAgentConcRtc(InfAgent agent, boolean en) {
        if (agent != null) {
            if (agent.concRtc ^ en) {
                agent.concRtc = en;
                modified();
            }
        }
    }

    public void updateAgentCheckDuplicates(InfAgent agent, boolean en) {
        if (agent != null) {
            if (agent.checkForDuplicates ^ en) {
                agent.checkForDuplicates = en;
                modified();
            }
        }
    }
    
    public boolean updateAgentLocalCacheSize(DashInfProcQueryAgent agent, String size) {
        if (agent != null) {
        	if (!agent.localCacheMaxSize.equals(size)) {
        		agent.localCacheMaxSize = size;
        		modified();
        		return true;
        	}
        }
        return false;
    }

    public boolean updateAgentLocalCacheTime(DashInfProcQueryAgent agent, String time) {
        if (agent != null) {
        	if (!agent.localCacheEvictionTime.equals(time)) {
        		agent.localCacheEvictionTime = time;
        		modified();
        		return true;
        	}
        }
        return false;
    }
    
    public boolean updateAgentSharedQueueSize(DashInfProcQueryAgent agent, String size) {
        if (agent != null) {
        	if (!agent.sharedQueueSize.equals(size)) {
        		agent.sharedQueueSize = size;
        		modified();
        		return true;
        	}
        }
        return false;
    }
    
    public boolean updateAgentSharedQueueWorkers(DashInfProcQueryAgent agent, String workers) {
        if (agent != null) {
        	if (!agent.sharedQueueWorkers.equals(workers)) {
        		agent.sharedQueueWorkers = workers;
        		modified();
        		return true;
        	}
        }
        return false;
    }    

    public boolean updateAgentMaxActive(DashInfProcQueryAgent agent, String maxActive) {
        if (agent != null) {
            if (!agent.maxActive.equals(maxActive)) {
                agent.maxActive = maxActive;
                modified();
                return true;
            }
        }
        return false;
    }
    
    public boolean updateAgentJobQueueSize(ProcessAgent agent, String size) {
        if (agent != null) {
        	if (!agent.jobPoolQueueSize.equals(size)) {
        		agent.jobPoolQueueSize = size;
        		modified();
        		return true;
        	}
        }
        return false;
    }

    public boolean updateAgentThreadPoolSize(ProcessAgent agent, String size) {
        if (agent != null) {
        	if (!agent.jobPoolThreadCount.equals(size)) {
        		agent.jobPoolThreadCount = size;
        		modified();
        		return true;
        	}
        }
        return false;
    }

    public boolean updateAgentProperty(AgentClass agent, String propertyName, String value) {
    	boolean propertyFound = false;
    	for (PropertyElement propElement: agent.properties.uiList) {
    		if (propElement instanceof Property) {
    			Property prop = (Property) propElement;
    			if (prop.fieldMap.get("name").equals(propertyName)) {
    				if (!prop.fieldMap.get("value").equals(value)) {
	    				prop.fieldMap.put("value", value);
	        			modified();
	        			return true;
    				}
    				propertyFound = true;
    			}
    		}
    	}
    	// if the property doesn't exist, then we will need to add it to the uiList
    	if (!propertyFound) {
	    	Property property = model.new Property();
	    	property.fieldMap.put("name", propertyName);
	    	property.fieldMap.put("value", value);
	    	agent.properties.uiList.add(property);
	    	modified();
	    	return true;
    	}
    	return false;
    }
    
    public void updateAgentProperties(AgentClass agent, PropertyElementList propList) {
        agent.properties = propList;
        modified();
    }

    private void initAgentRulesGrp(DashInfProcAgent agent, String rulesGrpName) {
        RulesGrp rulesGrp = getRulesGrpRef(rulesGrpName);
        if (rulesGrp != null) {
            AgentRulesGrpElement agentRulesGrp = agent.new AgentRulesGrpElement(agent, rulesGrp);
            agent.agentRulesGrpObj.agentRules.add(agentRulesGrp);
        }
    }

    private void initAgentFunctionsGrp(DashInfProcQueryAgent agent, boolean isStartup, String functionsGrpName) {
        FunctionsGrp functionsGrp = getFunctionsGrpRef(functionsGrpName);
        if (functionsGrp != null) {
            AgentFunctionsGrpElement agentFunctionsGrpElement = agent.new AgentFunctionsGrpElement(agent, functionsGrp, isStartup);
            if (isStartup) {
                agent.agentStartupFunctionsGrpObj.agentFunctions.add(agentFunctionsGrpElement);
            } else {
                agent.agentShutdownFunctionsGrpObj.agentFunctions.add(agentFunctionsGrpElement);
            }
        }
    }

    private void initAgentDestinationsGrp(DashInfProcQueryAgent agent, String destinationsGrpName) {
        DestinationsGrp destinationsGrp = getDestinationsGrpRef(destinationsGrpName);
        if (destinationsGrp != null) {
            AgentDestinationsGrpElement agentDestinationsGrp = agent.new AgentDestinationsGrpElement(agent, destinationsGrp);
            addDestinationToList(agent.agentDestinationsGrpObj.agentDestinations, agentDestinationsGrp);
        }
    }

    public RuleElement addAgentRules(DashInfProcAgent agent, ArrayList<String> names) {
        RuleElement rule, firstRule = null;
        for (String name: names) {
            if (name.startsWith("/")) {
                rule = addAgentRule(agent, name);
            } else {
                rule = addAgentRulesGrpElement(agent, name);
            }
            if (firstRule == null)
                firstRule = rule;
        }
        return firstRule;
    }

    public Rule addAgentRule(DashInfProcAgent agent, String name) {
        Rule rule = initRule(agent, name);
        modified();
        return rule;
    }

    public AgentRulesGrpElement addAgentRulesGrpElement(DashInfProcAgent agent, String name) {
        RulesGrp rulesGrp = getRulesGrpRef(name);
        AgentRulesGrpElement agentRulesGrpElement = agent.new AgentRulesGrpElement(agent, rulesGrp);
        agent.agentRulesGrpObj.agentRules.add(agentRulesGrpElement);
        modified();
        return agentRulesGrpElement;
    }

    public DestinationElement addAgentDestinations(DashInfProcQueryAgent agent, ArrayList<String> names) {
        DestinationElement destination, firstDestination = null;
        for (String name: names) {
            if (name.startsWith("/")) {
                String uniqId = IdUtil.generateUniqueName(name);
                int idx = uniqId.lastIndexOf("/");
                if (idx != -1 && idx+1 < uniqId.length())
                    uniqId = uniqId.substring(idx+1);
                destination =addAgentDestination(agent, uniqId);
                ((Destination)destination).destinationVal.put(Elements.URI.localName, name);
            } else {
                destination =addAgentDestinationsGrpElement(agent, name);
            }
            if (firstDestination == null)
                firstDestination = destination;
        }
        return firstDestination;
    }

    public Destination addAgentDestination(DashInfProcQueryAgent agent, String name) {
        Destination destination = initDestination(agent, name);
        modified();
        return destination;
    }

    public AgentDestinationsGrpElement addAgentDestinationsGrpElement(DashInfProcQueryAgent agent, String name) {
        DestinationsGrp destinationsGrp = getDestinationsGrpRef(name);
        AgentDestinationsGrpElement agentDestinationsGrpElement = agent.new AgentDestinationsGrpElement(agent, destinationsGrp);
        addDestinationToList(agent.agentDestinationsGrpObj.agentDestinations, agentDestinationsGrpElement);
        modified();
        return agentDestinationsGrpElement;
    }

    public FunctionsGrp addAgentStartupFunctionsGrp(DashInfProcQueryAgent agent) {
        ArrayList<String> names = getStartupFunctionsGrpNames(agent);
        String newName = IdUtil.generateSequenceId("Startup_Functions_Group", names);
        FunctionsGrp funcGrp = addAgentFunctionsGrp(agent, newName, true);
        return funcGrp;
    }


    public Function addAgentFunction(DashInfProcQueryAgent agent, String name, boolean isStartup) {
        Function function = initFunction(agent, name, isStartup);
        modified();
        return function;
    }

    public FunctionsGrp addAgentFunctionsGrp(DashInfProcQueryAgent agent, String name, boolean isStartup) {
        FunctionsGrp functionsGrp = getFunctionsGrpRef(name);
        AgentFunctionsGrpElement agentFunctionsGrpElement = agent.new AgentFunctionsGrpElement(agent, functionsGrp, isStartup);
        if (isStartup)
            agent.agentStartupFunctionsGrpObj.agentFunctions.add(agentFunctionsGrpElement);
        else
            agent.agentShutdownFunctionsGrpObj.agentFunctions.add(agentFunctionsGrpElement);
        modified();
        return functionsGrp;
    }

    public FunctionElement addAgentFunctions(DashInfProcQueryAgent agent, ArrayList<String> names, boolean isStartup) {
        FunctionElement function, firstFunction = null;
        for (String name: names) {
            if (name.startsWith("/")) {
                function = addAgentFunction(agent, name, isStartup);
            } else {
                function = addAgentFunctionsGrp(agent, name, isStartup);
            }
            if (firstFunction == null)
                firstFunction = function;
        }
        return firstFunction;
    }

    private ArrayList<String> getStartupFunctionsGrpNames(DashInfProcQueryAgent agent) {
        return getAgentFunctionsGrpNames(agent.agentStartupFunctionsGrpObj);
    }

    public FunctionsGrp addAgentShutdownFunctionsGrp(DashInfProcQueryAgent agent) {
        ArrayList<String> names = getShutdownFunctionsGrpNames(agent);
        String newName = IdUtil.generateSequenceId("Shutdown_Functions_Group", names);
        FunctionsGrp funcGrp = addAgentFunctionsGrp(agent, newName, false);
        return funcGrp;
    }

    public ArrayList<String> getAgentFunctionsGrpNames(AgentBaseFunctionsGrp agentFunctionsGrp) {
        return getAgentFunctionNames(agentFunctionsGrp, false);
    }

    public ArrayList<String> getAgentFunctionNames(AgentBaseFunctionsGrp agentFunctionsGrp, boolean excludeRefs) {
        ArrayList<String> funcObjs = new ArrayList<String>();
        for (FunctionElement functionElement: agentFunctionsGrp.agentFunctions) {
            if (functionElement instanceof Function) {
                Function function = (Function) functionElement;
                if (excludeRefs && function.isRef)
                    continue;
                funcObjs.add(function.uri);
            } else if (!excludeRefs && (functionElement instanceof AgentFunctionsGrpElement)) {
                AgentFunctionsGrpElement funcGrpRef = (AgentFunctionsGrpElement) functionElement;
                if (funcGrpRef != null)
                    funcObjs.add(funcGrpRef.functionsGrp.id);
            }
        }
        return funcObjs;
    }

    private ArrayList<String> getShutdownFunctionsGrpNames(DashInfProcQueryAgent agent) {
        return getAgentFunctionsGrpNames(agent.agentShutdownFunctionsGrpObj);
    }

    public ProcessElement addAgentProcesses(ProcessAgent agent, ArrayList<String> names) {
        ProcessElement process, firstProcess = null;
        for (String name: names) {
            if (name.startsWith("/")) {
                process = addAgentProcess(agent, name);
            } else {
                process = addAgentProcessesGrpElement(agent, name);
            }
            if (firstProcess == null)
                firstProcess = process;
        }
        return firstProcess;
    }
    
    private ProcessElement addAgentProcessesGrpElement(ProcessAgent agent, String name) {
        ProcessesGrp processesGrp = getProcessesGrpRef(name);
        AgentProcessesGrpElement agentProcessesGrpElement = agent.new AgentProcessesGrpElement(agent, processesGrp);
        agent.agentProcessesGrpObj.agentProcesses.add(agentProcessesGrpElement);
        modified();
        return agentProcessesGrpElement;
	}

    private ProcessElement addAgentProcess(ProcessAgent agent, String name) {
    	Process process = initProcess(agent, name);
    	modified();
    	return process;
    }
    
    public ArrayList<String> getAgentProcessesGrpNames(ProcessAgent processAgent, boolean excludeRefs) {
        return (getAgentProcessesGrpNames(processAgent.agentProcessesGrpObj, excludeRefs));
    }

    public ProcessElement[] getAgentProcesses(AgentProcessesGrp agentProcessesGrp) {
        return agentProcessesGrp.agentProcesses.toArray(new ProcessElement[0]);
    }
    
    public ArrayList<String> getAgentProcessesGrpNames(AgentProcessesGrp agentProcessesGrp, boolean excludeRefs) {
        ArrayList<String> names = new ArrayList<String>();
        if (agentProcessesGrp != null) {
            for (ProcessElement processElement: agentProcessesGrp.agentProcesses) {
                if (processElement instanceof Process) {
                    Process process = (Process) processElement;
                    if (excludeRefs && process.isRef)
                        continue;
                    names.add(process.uri);
                } else if (!excludeRefs && (processElement instanceof AgentProcessesGrpElement)) {
                    names.add(((AgentProcessesGrpElement)processElement).processesGrp.id);
                }
            }
        }
        return names;
    }

    public String[] getProcessesGroupNames() {
        ArrayList<String> processesGrpNames = new ArrayList<String>();
        for (ProcessElement processElement: model.processElementsList.processElements) {
            if (! (processElement instanceof ProcessesGrp))
                continue;
            ProcessesGrp processesGrp = (ProcessesGrp) processElement;
            processesGrpNames.add(processesGrp.id);
        }
        return processesGrpNames.toArray(new String[0]);
    }
    
    // **************** Processing Units *****************

	public ArrayList<ProcessingUnit> getProcessingUnits() {
        return (model.procUnits);
    }

    public ArrayList<String> getProcessingUnitsName() {
        ArrayList<String> names = new ArrayList<String>();
        for (ProcessingUnit procUnit: model.procUnits) {
            names.add(procUnit.name);
        }
        return names;
    }

    public ProcessingUnit getProcessingUnit(String procUnitName) {
        for (ProcessingUnit procUnit: model.procUnits) {
            if (procUnitName.equals(procUnit.name))
                return procUnit;
        }
        return null;
    }

    public String getProcUnitLogConfig(ProcessingUnit procUnit) {
        if (procUnit.logConfig != null)
            return (procUnit.logConfig.id);
        return ("");
    }

    public String[] getProcUnitAgentNames(ProcessingUnit procUnit) {
        ArrayList<String> agentNames = new ArrayList<String>();
        if (procUnit != null && procUnit.agentClasses != null) {
            for (Map<String, String> map: procUnit.agentClasses) {
                String name = map.get(ProcessingUnit.AGENT_REF);
                if (name != null && !name.trim().equals(""))
                    agentNames.add(name);
            }
        }
        return (agentNames.toArray(new String[0]));
    }

    public ProcessingUnit initProcessingUnit(String name) {
        ProcessingUnit procUnit = model.new ProcessingUnit();
        procUnit.name = name;
        model.procUnits.add(procUnit);
        return procUnit;
    }

    public PropertyElementList getProcUnitProperties(ProcessingUnit procUnit) {
        return procUnit.properties;
    }

    public ProcessingUnit addProcessingUnit(String name) {
        ProcessingUnit procUnit = initProcessingUnit(name);
        modified();
        return procUnit;
    }

    public boolean removeProcessingUnit(String name) {
        ProcessingUnit procUnit = getProcessingUnit(name);
        return removeProcessingUnit(procUnit);
    }

    public boolean removeProcessingUnit(ProcessingUnit procUnit) {
        if (procUnit != null) {
            model.procUnits.remove(procUnit);
            modified();
            return true;
        }
        return false;
    }

    public boolean updateProcUnitName(ProcessingUnit procUnit, String newName) {
        if (!newName.equalsIgnoreCase(procUnit.name)) {
            procUnit.name = newName;
            modified();
            return true;
        }
        return false;
    }

    public int updateProcUnitLogConfig(ProcessingUnit procUnit, String logConfigName) {
        LogConfig logConfig = getLogConfig(logConfigName);
        if (logConfig == null)
            return -1;
        if (procUnit.logConfig != logConfig) {
            procUnit.logConfig = logConfig;
            modified();
            return 1;
        }
        return 0;
    }

    public boolean updateProcUnitHotDeploy(ProcessingUnit procUnit, boolean hotDeploy) {
        if (procUnit == null)
            return false;
        if (procUnit.hotDeploy ^ hotDeploy) {
            procUnit.hotDeploy = hotDeploy;
            modified();
            return true;
        }
        return false;
    }

    public boolean updateProcUnitCacheStorage(ProcessingUnit procUnit, boolean enableCacheStorage) {
        if (procUnit == null)
            return false;
        if (procUnit.enableCacheStorage ^ enableCacheStorage) {
            procUnit.enableCacheStorage = enableCacheStorage;
            modified();
            return true;
        }
        return false;
    }

    public boolean forceEnableCacheStorageForProcessingUnits() {
    	boolean updated = false;
    	for (ProcessingUnit procUnit: model.procUnits) {
    		if (isCachePU(procUnit) && !procUnit.enableCacheStorage) {
    			procUnit.enableCacheStorage = true;
    			updated = true;
    		}
    	}
    	return updated;
    }
    
    public boolean updateProcUnitDbConcepts(ProcessingUnit procUnit, boolean enableDbConcepts) {
        if (procUnit == null)
            return false;
        if (procUnit.enableDbConcepts ^ enableDbConcepts) {
            procUnit.enableDbConcepts = enableDbConcepts;
            modified();
            return true;
        }
        return false;
    }

    public void updateProcUnitProperties(ProcessingUnit procUnit, PropertyElementList propList) {
        procUnit.properties = propList;
        modified();
    }

    private void updateProcUnitAgentNames(String oldName, String newName) {
        if (oldName == null)
            return;
        for (ProcessingUnit procUnit: getProcessingUnits()) {
            for (LinkedHashMap<String, String> map: procUnit.agentClasses) {
                String curName = map.get(ProcessingUnit.AGENT_REF);
                if (oldName.equals(curName)) {
                    map.put(ProcessingUnit.AGENT_REF, newName);
                }
            }
        }
    }

	public boolean isCachePU(ProcessingUnit procUnit) {
		AgentClass agentClass = getPUAgentClass(procUnit);
		if (agentClass != null && agentClass instanceof CacheAgent)
			return true;
		return false;
	}
	
	public boolean isDashboardPU(ProcessingUnit procUnit) {
		if (procUnit == null || procUnit.agentClasses.size() == 0)
			return false;
		for (LinkedHashMap<String, String> agentMap: procUnit.agentClasses) {
			AgentClass agentClass = getAgentClass(agentMap.get(ProcessingUnit.AGENT_REF));
			if (agentClass != null) {
				if (!(agentClass instanceof DashboardAgent)) {
					return false;
				}
			} else {
				return false;
			}
		}
		return true;
	}
	
	private AgentClass getPUAgentClass(ProcessingUnit procUnit) {
		if (procUnit == null || procUnit.agentClasses.size() != 1)
			return null;
		LinkedHashMap<String, String> agentMap = procUnit.agentClasses.get(0);
		if (agentMap != null) {
			AgentClass agentClass = getAgentClass(agentMap.get(ProcessingUnit.AGENT_REF));
			return agentClass;
		}
		return null;
	}

    public void removeProcUnitAgent(ProcessingUnit procUnit, String agentName) {
        for (Map<String, String> map: procUnit.agentClasses) {
            if (agentName.equalsIgnoreCase(map.get(ProcessingUnit.AGENT_REF))) {
                procUnit.agentClasses.remove(map);
                modified();
                break;
            }
        }
    }

    public boolean updateProcUnitHttpProperties(ProcessingUnit procUnit, String key, String value) {
        if (procUnit == null)
            return false;
        if (!procUnit.httpProperties.properties.get(key).equals(value)) {
            procUnit.httpProperties.properties.put(key, value);
            modified();
            return true;
        }
        return false;
    }

    public boolean updateProcUnitHttpSslProperties(ProcessingUnit procUnit, String key, String value) {
        if (procUnit == null)
            return false;
        if (!procUnit.httpProperties.ssl.properties.get(key).equals(value)) {
            procUnit.httpProperties.ssl.properties.put(key, value);
            modified();
            return true;
        }
        return false;
    }

    public boolean updateProcUnitSecurityRole(ProcessingUnitSecurityConfig procUnitSecurityConfig, String securityRole) {
    	if (!procUnitSecurityConfig.securityRole.equals(securityRole)) {
    		procUnitSecurityConfig.securityRole = securityRole;
    		modified();
    		return true;
    	}
    	return false;
    }

    public boolean updateSecurityControllerOverrides(ProcessingUnitSecurityConfig securityConfig, String key, Boolean override) {
        Boolean currOverride = securityConfig.securityControllerOverrides.get(key);
    	if ((currOverride == null && override != null) || !currOverride.equals(override)) {
        	securityConfig.securityControllerOverrides.put(key, override);
            modified();
            return true;
        }
        return false;    	
    }

    public boolean updateSecurityRequestorOverrides(ProcessingUnitSecurityConfig securityConfig, String key, Boolean override) {
    	Boolean currOverride = securityConfig.securityRequesterOverrides.get(key);
    	if ((currOverride == null && override != null) || !currOverride.equals(override)) {
        	securityConfig.securityRequesterOverrides.put(key, override);
            modified();
            return true;
        }
        return false;    	
    }

    public PropertyElementList getPropertyElementList() {
        return (model.properties);
    }

    public void updateProperties(PropertyElementList propList) {
        model.properties = propList;
        modified();
    }
    
    //  ***************** Migration *****************
    
	public boolean addSecurityAuthProperties() {
		/*
    	<property-group name="auth">
		    <property name="be.auth.type" value="file"/>
		    <property name="be.auth.file.location" value="%%BE_HOME%%/mm/config/users.pwd"/>
		    <property name="java.security.auth.login.config" value="%%BE_HOME%%/mm/config/jaas-config.config"/>
    	</property-group>
		 */
		
		String beHome = System.getProperty("BE_HOME");
		if (beHome == null)
			beHome = "";
		beHome = beHome.replace("\\", "/");
		
		PropertyGroup authGroupFound = null;
		for (PropertyElement propElement: model.properties.propertyList) {
			if (propElement instanceof PropertyGroup) {
				PropertyGroup propGrp = (PropertyGroup) propElement;
				if (propGrp.name.equals("auth")) {
					authGroupFound = propGrp;
					break;
				}
			}
		}
		
		if (authGroupFound==null) {
			PropertyGroup authGrp = model.new PropertyGroup();
			authGrp.name="auth";
			
			Property authType = model.new Property();
			authType.fieldMap.put("name", "be.mm.auth.type");
			authType.fieldMap.put("value", "file");
			authGrp.propertyList.add(authType);
			
			Property authFileLocation = model.new Property();
			authFileLocation.fieldMap.put("name", "be.mm.auth.file.location");
			authFileLocation.fieldMap.put("value", beHome + "/mm/config/users.pwd");
			authGrp.propertyList.add(authFileLocation);
			
			Property authLoginConfig = model.new Property();
			authLoginConfig.fieldMap.put("name", "java.security.auth.login.config");
			authLoginConfig.fieldMap.put("value", beHome + "/mm/config/jaas-config.config");
			authGrp.propertyList.add(authLoginConfig);
				
			model.properties.propertyList.add(authGrp);
		}
		else{
			for (PropertyElement propElement :authGroupFound.propertyList){
				if(propElement instanceof Property){
					Property prop = (Property)propElement;
					String key = prop.fieldMap.get("name");
					if(key!=null){
						
						if(key.equals("be.mm.auth.file.location")){
							prop.fieldMap.put("value", beHome + "/mm/config/users.pwd");
						}
						else if(key.equals("java.security.auth.login.config")){
							prop.fieldMap.put("value", beHome + "/mm/config/jaas-config.config");
						} 
					} 
					
				}
			}
		}
		return true;
	}
    
	public boolean migrateBackingStoreTo510() { 
		String enabledStr = getBackingStoreValue(Elements.ENABLED.localName);
		boolean updated = false;
		if (enabledStr != null) {
			boolean enabled = new Boolean(enabledStr).booleanValue();
			if (enabled) {
				model.om.cacheOm.bs.values.put(BackingStore.PERSISTENCE_OPTION, BackingStoreConfig.PERSISTENCE_OPTION_SHARED_ALL);
			} else {
				model.om.cacheOm.bs.values.put(BackingStore.PERSISTENCE_OPTION, BackingStoreConfig.PERSISTENCE_OPTION_NONE);
			}
			model.om.cacheOm.bs.values.remove(Elements.ENABLED.localName);
			updated = true;
		}
		
		String type = getBackingStoreValue(Elements.TYPE.localName);
		if (type.equals("oracle")) {
			model.om.cacheOm.bs.values.put(Elements.TYPE.localName,	BackingStoreConfig.TYPE_ORACLE);
			updated = true;
		} else if (type.equals("sqlserver")) {
			model.om.cacheOm.bs.values.put(Elements.TYPE.localName,	BackingStoreConfig.TYPE_SQLSERVER);
			updated = true;
		} else if (type.equals("db2")) {
			model.om.cacheOm.bs.values.put(Elements.TYPE.localName,	BackingStoreConfig.TYPE_DB2);
			updated = true;
		}
		
		return updated;
	}
	
	public boolean migrateASPropertiesTo512(){
		boolean updated = false;
		//check cluster level properties or pu level properties
		
		//Create a list of propertie to be migrated 
		Map<String,String> propertiesToMigrate = new HashMap();
		propertiesToMigrate.put(SystemProperty.AS_LISTEN_URL.getPropertyName(), Elements.LISTEN_URL.localName);
		propertiesToMigrate.put(SystemProperty.AS_DISCOVER_URL.getPropertyName(), Elements.DISCOVERY_URL.localName);
		propertiesToMigrate.put(SystemProperty.AS_REMOTE_LISTEN_URL.getPropertyName(), Elements.REMOTE_LISTEN_URL.localName);
		propertiesToMigrate.put(SystemProperty.AS_PROTOCOL_TIMEOUT.getPropertyName(),Elements.PROTOCOL_TIMEOUT.localName);
		propertiesToMigrate.put(SystemProperty.AS_READ_TIMEOUT.getPropertyName(),Elements.READ_TIMEOUT.localName);
		propertiesToMigrate.put(SystemProperty.AS_WRITE_TIMEOUT.getPropertyName(),Elements.WRITE_TIMEOUT.localName);
		propertiesToMigrate.put(SystemProperty.AS_LOCK_TTL.getPropertyName(),Elements.LOCK_TIMEOUT.localName);
		propertiesToMigrate.put(SystemProperty.AS_SHUTDOWN_WAIT_MILLIS.getPropertyName(),Elements.SHUTDOWN_WAIT.localName);
		propertiesToMigrate.put(SystemProperty.AS_WORKERTHREADS_COUNT.getPropertyName(),Elements.WORKERTHREADS_COUNT.localName);
		propertiesToMigrate.put(SystemProperty.PROP_TUPLE_EXPLICIT.getPropertyName(),Elements.EXPLICIT_TUPLE.localName);
		
		updated |= migrateCacheOMValues(model.properties.propertyList,propertiesToMigrate);
		
		if( model.om.cacheOm.bs.values.get(BackingStore.PERSISTENCE_OPTION).equals(BackingStoreConfig.PERSISTENCE_OPTION_SHARED_NOTHING)
						|| model.om.cacheOm.bs.values.get(BackingStore.PERSISTENCE_OPTION).equals(BackingStoreConfig.PERSISTENCE_OPTION_SHARED_NOTHING_ALT)){
			model.om.cacheOm.values.put(Elements.EXPLICIT_TUPLE.localName,"true");
			updated = true;
		}

		if(model.om.cacheOm.domainObjects.domainObjDefault.values.get(Elements.DEFAULT_MODE.localName).equals(CacheOm.DO_MODE_MODEL_CACHE_MEMORY)) {
			model.om.cacheOm.domainObjects.domainObjDefault.values.put(Elements.DEFAULT_MODE.localName,CacheOm.DO_MODE_MODEL_CACHE);
			updated = true;
		}
		
		for(DomainObject domainObj: model.om.cacheOm.domainObjects.domainObjOverrides.overrides) {
			if(domainObj.values.get(Elements.MODE.localName).equals(CacheOm.DO_MODE_MODEL_CACHE_MEMORY)) {
				domainObj.values.put(Elements.MODE.localName,CacheOm.DO_MODE_MODEL_CACHE);
				updated = true;
			}
		}
		
		return updated;
	}
	
	private boolean migrateCacheOMValues(ArrayList<PropertyElement> propertyList, Map<String,String> propertiesToMigrate){
		boolean updated = false;
		
		Iterator<PropertyElement> propertyItr = propertyList.iterator();
		
		while (propertyItr.hasNext()) {
			PropertyElement propElement = propertyItr.next();
			if (propElement instanceof PropertyGroup) {
				PropertyGroup propGrp = (PropertyGroup) propElement;
				updated |= migrateCacheOMValues(propGrp.propertyList,propertiesToMigrate);
			}
			if(propElement instanceof Property){
				Property property = (Property)  propElement;
				String key = property.fieldMap.get("name");
				if(propertiesToMigrate.get(key)!=null){
					model.om.cacheOm.values.put(propertiesToMigrate.get(key),property.fieldMap.get("value"));
					propertyItr.remove();
					updated = true;
				}
			}
		}
		return updated;
	}
	
    //  ***************** Load Balancer *****************
	
	public ArrayList<LoadBalancerPairConfig> getLoadBalancerPairConfigs() {
		return model.loadBalancerPairConfigs.configs;
	}
	
	public ArrayList<LoadBalancerAdhocConfig> getLoadBalancerAdhocConfigs() {
		return model.loadBalancerAdhocConfigs.configs;
	}
	
	public boolean updateLoadBalancerConfigValue(LoadBalancerConfig config, String key, String value) {
        if (!config.values.get(key).equals(value)) {
        	config.values.put(key, value);
            modified();
            return true;
        }
        return false;
	}
	
	
	public ArrayList<String> getLoadBalancerPairConfigNames() {
		return getLoadBalancerConfigNames(model.loadBalancerPairConfigs.configs);
	}
	
	public ArrayList<String> getLoadBalancerAdhocConfigNames() {
		return getLoadBalancerConfigNames(model.loadBalancerAdhocConfigs.configs);
	}
	
	public ArrayList<String> getLoadBalancerConfigNames(ArrayList<?> configs) {
		ArrayList<String> names = new ArrayList<String>();
		for (Object config: configs) {
			names.add(((LoadBalancerConfig)config).values.get(LoadBalancerConfig.ELEMENT_NAME));
		}
		return names;
	}
	
	public LoadBalancerPairConfig addLoadBalancerPairConfig(String name) {
		LoadBalancerPairConfig config = model.new LoadBalancerPairConfig();
		config.values.put(LoadBalancerPairConfig.ELEMENT_NAME, name);
		model.loadBalancerPairConfigs.configs.add(config);
		modified();
		return config;
	}

	public LoadBalancerAdhocConfig addLoadBalancerAdhocConfig(String name) {
		LoadBalancerAdhocConfig config = model.new LoadBalancerAdhocConfig();
		config.values.put(LoadBalancerAdhocConfig.ELEMENT_NAME, name);
		model.loadBalancerAdhocConfigs.configs.add(config);
		modified();
		return config;
	}
	
	public boolean removeLoadBalancerConfig(LoadBalancerConfig config) { 
		if (model.loadBalancerPairConfigs.configs.contains(config)) {
			model.loadBalancerPairConfigs.configs.remove(config);
			modified();
			return true;
		} else if (model.loadBalancerAdhocConfigs.configs.contains(config)) {
			model.loadBalancerAdhocConfigs.configs.remove(config);
			modified();
			return true;
		}
		return false;
	}
	
	public boolean migrateLoadBalancer() {
		return true;
	}
	
	 public void updateLoadBalancerProperties(LoadBalancerConfig loadBalancerConfig, PropertyElementList propList) {
		 	loadBalancerConfig.properties = propList;
	        modified();
	}
	 public PropertyElementList getLoadBalancerProperties(LoadBalancerConfig loadBalancerConfig) {
	        return loadBalancerConfig.properties;
	    }
	
    //  ***************** Generic *****************

    public String[] getListNames(List<?> list, String fieldName) {
        ArrayList<String> names = new ArrayList<String>();
        for (Object obj: list) {
            try {
                Field field = obj.getClass().getField(fieldName);
                String value = (String) field.get(obj);
                if (value != null) {
                    names.add(value);
                }
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return names.toArray(new String[0]);
    }

    /*
    public void setEditorModified(Object object) {
    	try {
    		ArrayList<Value> args = new ArrayList<Value>();
    		Method method = object.getClass().getMethod("modified", args.toArray(new Class[0]));
    		method.invoke(object, args.toArray(new Object[0]));
    	} catch (SecurityException e) {
    		e.printStackTrace();
    	} catch (IllegalArgumentException e) {
    		e.printStackTrace();
    	} catch (IllegalAccessException e) {
    		e.printStackTrace();
    	} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
    }
    */
    
    public String getStringValue(String value) {
        if (value != null)
            return value;
        return ("");
    }

    public boolean getBooleanValue(String value) {
        if (value != null && !value.trim().equals(""))
            return new Boolean(value).booleanValue();
        return false;
    }

    public ClusterConfigModel getModel() {
        return model;
    }
    
    public ArrayList<String> getAgentClassContributions() {
    	ArrayList<String> agents = new ArrayList<String>();
    	for (AddOn addOn: AddonUtil.getInstalledAddOns()) {
			if (!addOn.getCddAgentClass().isEmpty())
				agents.add(addOn.getCddAgentClass());
    	}
    	return agents;
    }
    
    public boolean isQueryAgentEnabled() {
    	if (getAgentClassContributions().contains(AgentClass.AGENT_TYPE_QUERY))
    		return true;
    	return false;
    }
    
    public boolean isDashboardAgentEnabled() {
    	if (getAgentClassContributions().contains(AgentClass.AGENT_TYPE_DASHBOARD))
    		return true;
    	return false;
    }
    
    public boolean isProcessAgentEnabled() {
    	if (getAgentClassContributions().contains(AgentClass.AGENT_TYPE_PROCESS))
    		return true;
    	return false;
    }
    
    public boolean isLivewViewAgentEnabled() {
    	if (getAgentClassContributions().contains(AgentClass.AGENT_TYPE_LIVEVIEW))
    		return true;
    	return false;
    }

    public boolean updateLDMConnectionProperty(LDMConnection property, String key, String value) {
        if (property == null)
            return false;
        if (key.equals(Elements.LDM_URL.localName) && !property.ldmUrl.equals(value)) {
            property.ldmUrl = value;
            modified();
            return true;
        } else if (key.equals(Elements.SECURITY_USER_NAME.localName) && !property.userName.equals(value)) {
            property.userName = value;
            modified();
            return true;
        } else if (key.equals(Elements.SECURITY_USER_PASSWORD.localName) && !property.userPassword.equals(value)) {
            property.userPassword = value;
            modified();
            return true;
        } else if (key.equals(Elements.INITIAL_SIZE.localName) && !property.initialSize.equals(value)) {
            property.initialSize = value;
            modified();
            return true;
        } else if (key.equals(Elements.MAX_SIZE.localName) && !property.maxSize.equals(value)) {
            property.maxSize = value;
            modified();
            return true;
        }
        return false;
    }
    
    public boolean updateBaseLVAgentProperty(LiveViewAgent property, String key, String value) {
        if (property == null)
            return false;
        if (key.equals(Elements.PUBLISHER_QUEUE_SIZE.localName) && !property.publisherQueueSize.equals(value)) {
            property.publisherQueueSize = value;
            modified();
            return true;
        } else if (key.equals(Elements.PUBLISHER_THREAD_COUNT.localName) && !property.publisherThreadCount.equals(value)) {
            property.publisherThreadCount = value;
            modified();
            return true;
        } else if (key.equals(Elements.MAX_ACTIVE.localName) && !property.maxActive.equals(value)) {
        	property.maxActive = value;
        	modified();
        	return true;
        }
        return false;
    }
    
    public boolean updateEntityConfigProperty(EntityConfig property, String key, String value) {
        if (property == null)
            return false;
        if (key.equals(Elements.URI.localName) && !property.entityUri.equals(value)) {
            property.entityUri = value;
            modified();
            return true;
        } else if (key.equals(Elements.FILTER.localName) && !property.filter.equals(value)) {
            property.filter = value;
            modified();
            return true;
        } else if (key.equals(Elements.ENABLE_TRIMMING.localName) && !property.enableTableTrimming.equals(value)) {
        	property.enableTableTrimming = value;
        	modified();
            return true;
        } else if (key.equals(Elements.TRIMMING_FIELD.localName) && !property.trimmingField.equals(value)) {
        	property.trimmingField = value;
        	modified();
            return true;
        } else if (key.equals(Elements.TRIMMING_RULE.localName) && !property.trimmingRule.equals(value)) {
        	property.trimmingRule = value;
        	modified();
            return true;
        }
        return false;
    }
    
    public boolean updateEntitySetConfigProperty(EntitySetConfig property, String key, String value) {
        if (property == null)
            return false;
        if (key.equals(Elements.OUTPUT_PATH.localName) && !property.outputPath.equals(value)) {
            property.outputPath = value;
            modified();
            return true;
        } else if (key.equals(Elements.GENERATE_LV_FILES.localName) && !property.generateLVFiles.equals(value)) {
            property.generateLVFiles = value;
            modified();
            return true;
        }
        return false;
    }
    
    public ArrayList<String> getEntityFilterSet(EntitySetConfig entitySet) {
        ArrayList<String> entityFilters = new ArrayList<String>();
        for (EntityConfig entityConfig : entitySet.entityElements) {
        	entityFilters.add(entityConfig.id);
        }
        
        return entityFilters;
    }
    
    public EntityConfig addEntityConfig(EntitySetConfig entitySetConfig) {
    	EntityConfig entityConfig = createEntityConfig(entitySetConfig);
    	modified();
    	
    	return entityConfig;
    }
    
    public void removeEntityConfig(EntityConfig config) {
        config.parent.entityElements.remove(config);
        modified();
    }
    
    public ArrayList<String> getEntitySetConfigIds(EntitySetConfig entitySetConfig) {
        ArrayList<String> ids = new ArrayList<String>();
        for (EntityConfig config: entitySetConfig.entityElements) {
            ids.add(config.id);
        }
        return ids;
    }
    
    private void processLDMConnection(LDMConnection ldmConnection, String key, String value) {
    	if (key.equals(Elements.LDM_URL.localName)) {
			ldmConnection.ldmUrl = value;
		} else if (key.equals(Elements.SECURITY_USER_NAME.localName)) {
			ldmConnection.userName = value;
		} else if (key.equals(Elements.SECURITY_USER_PASSWORD.localName)) {
			ldmConnection.userPassword = value;
		} else if (key.equals(Elements.INITIAL_SIZE.localName)) {
			ldmConnection.initialSize = value;
		} else if (key.equals(Elements.MAX_SIZE.localName)) {
			ldmConnection.maxSize = value;
		}
    }
    
    private void processEntitySet(EntitySetConfig entitySetConfig, String key, String value) {
    	if (key.equals(Elements.GENERATE_LV_FILES.localName)) {
    		entitySetConfig.generateLVFiles = value;
    	} else if (key.equals(Elements.OUTPUT_PATH.localName)) {
    		entitySetConfig.outputPath = value;
    	}
    }
    
    private void processEntityConfig(EntitySetConfig entitySetConfig, String key, String value) {
    	if (key.equals(Elements.URI.localName)) {
    		EntityConfig entityConfig = createEntityConfig(entitySetConfig);
    		entityConfig.entityUri = value;
    	} else if (key.equals(Elements.FILTER.localName)) {
    		EntityConfig entityConfig = entitySetConfig.entityElements.get(entitySetConfig.entityElements.size() - 1);
    		entityConfig.filter = value;
    	} else if (key.equals(Elements.ENABLE_TRIMMING.localName)) {
    		EntityConfig entityConfig = entitySetConfig.entityElements.get(entitySetConfig.entityElements.size() - 1);
    		entityConfig.enableTableTrimming = value;
    	} else if (key.equals(Elements.TRIMMING_FIELD.localName)) {
    		EntityConfig entityConfig = entitySetConfig.entityElements.get(entitySetConfig.entityElements.size() - 1);
    		entityConfig.trimmingField = value;
    	} else if (key.equals(Elements.TRIMMING_RULE.localName)) {
    		EntityConfig entityConfig = entitySetConfig.entityElements.get(entitySetConfig.entityElements.size() - 1);
    		entityConfig.trimmingRule = value;
    	}
    }
    
    private EntityConfig createEntityConfig(EntitySetConfig entitySetConfig) {
    	String newId = IdUtil.generateSequenceId("entity-filter-config", getEntitySetConfigIds(entitySetConfig));
    	EntityConfig entityConfig = entitySetConfig.new EntityConfig(entitySetConfig, newId);
    	entitySetConfig.entityElements.add(entityConfig);
    	
    	return entityConfig;
    }
    
    public Map<String, Map<String,String>> getEntityURIList() {
    	Map<String, Map<String,String>> entityUriToTrimmingMap = new HashMap<String, Map<String, String>>();
		
		for (AgentClass agent: getAgentClasses()) {
			if (agent.type.equals(AgentClass.AGENT_TYPE_LIVEVIEW)) {
				LiveViewAgent lvAgent = ((LiveViewAgent) agent);
				
				for (EntityConfig ec : lvAgent.entitySetConfig.entityElements) {
					Map<String, String> trimmingMap = new HashMap<String, String>();
					trimmingMap.put(Elements.ENABLE_TRIMMING.localName, ec.enableTableTrimming);
					trimmingMap.put(Elements.TRIMMING_FIELD.localName, ec.trimmingField);
					trimmingMap.put(Elements.TRIMMING_RULE.localName, ec.trimmingRule);
					
					entityUriToTrimmingMap.put(ec.entityUri, trimmingMap);
				}
				break;
			}
		}
		return entityUriToTrimmingMap;
	}
    
    public boolean isSharedNothingStorage() {
    	return ClusterConfigModelParser.isSharedNothingStorage(getModel().om.cacheOm);
    }
    
    public Map<String, Map<String,String>> getOverideDomainModelMap() {
    	return ClusterConfigModelParser.overideDomainModelMap(getModel().om.cacheOm);
    }
    
    public String getLDMProjectOutputPath() {
    	String outputPath = null;
    	for (AgentClass agent: getAgentClasses()) {
			if (agent.type.equals(AgentClass.AGENT_TYPE_LIVEVIEW)) {
				LiveViewAgent lvAgent = ((LiveViewAgent) agent);
				outputPath = lvAgent.entitySetConfig.outputPath;
				break;
			}
    	}
    	return outputPath;
    }
}
