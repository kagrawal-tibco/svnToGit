package com.tibco.be.bemm.functions;

import static com.tibco.be.model.functions.FunctionDomain.*;
import com.tibco.be.util.config.cdd.*;
import com.tibco.be.util.config.factories.ClusterConfigFactory;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.util.SystemProperty;
import org.eclipse.emf.ecore.EObject;

import java.util.HashMap;
import java.util.List;

/**
 * @author Nick Xu
 *
 */
public class ActionConfigurationProvider {
	
	private final String site = "site";
	private final String cluster = "site/cluster";
	private final String machine = "site/cluster/machine";
	private final String process = "site/cluster/machine/process";
	private final String agent = "site/cluster/machine/process/*";
	private static ActionConfigurationProvider instance;
	private HashMap<String,MmActionConfig> alertsMap;
	private HashMap<String,MmActionConfig> healthLevelMap;
	private Logger logger;
	
	public ActionConfigurationProvider(){
		this.alertsMap = new HashMap<String,MmActionConfig>();
		this.healthLevelMap = new HashMap<String,MmActionConfig>();
        RuleServiceProvider currRuleServiceProvider = RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider();
        logger = currRuleServiceProvider.getLogger(this.getClass());
        ClusterConfig config = (ClusterConfig)currRuleServiceProvider.getProperties().get("com.tibco.be.config");
        if(config == null){
        	try {
				config = new ClusterConfigFactory().newConfig(currRuleServiceProvider.getProperties().getProperty(SystemProperty.CLUSTER_CONFIG_PATH.getPropertyName()));
			} 
        	catch (Exception e) {
			}
        }
        if(config == null){
   		 logger.log(Level.ERROR,"Cannot get CLUSTER CONFIG object!");
		 return;
        }
        init(config);
	}
	
	private void init(ClusterConfig config){
		MmAgentClassConfig mmConfig = null;
		for (EObject o : config.getAgentClasses().eContents()) {
			if(o instanceof MmAgentClassConfig){
				mmConfig = (MmAgentClassConfig)o;
			}
		}
		if(mmConfig == null){
			return;
		}
		for (MmActionConfigConfig actionConfig : mmConfig.getMmActionConfigSet().getMmActionConfig()) {
			MmAlertConfig alertConfig = actionConfig.getMmTriggerCondition().getMmAlert();
			MmHealthLevelConfig healthConfig = actionConfig.getMmTriggerCondition().getMmHealthLevel();
			if(alertConfig != null){
				this.alertsMap.put(alertConfig.getPath()+":"+alertConfig.getSeverity(), actionConfig.getMmAction());
			}
			if(healthConfig != null){
				this.healthLevelMap.put(healthConfig.getPath()+":"+healthConfig.getValue(), actionConfig.getMmAction());
			}
		}
	}
	
	private String getPath(String fqname, String type){
		String[] parts = fqname.split(":");
		String path = null;
		switch(parts.length){
		case 1: path = this.site;
		break;
		case 2: path = this.cluster;
		break;
		case 3: path = this.machine;
		break;
		case 4: path = this.process;
		break;
		case 5: path = this.process + "/" + type;
		break;
		default: path = null;
		}
		return path;		
	}
	private String getWildCastString(String s1){
		int i = s1.lastIndexOf('/');
		if(i >= 0){
			String s2 = s1.substring(0, i) + "/*";
			return s2;
		}
		return this.agent;
	}
	
	private MmActionConfig getAlertActionConfig(String actionKey){
		if(actionKey == null){
			return null;
		}
		MmActionConfig actionConfig = null;
		actionConfig = this.alertsMap.get(actionKey);
		return actionConfig;
	}
	
	private MmActionConfig getHealthActionConfig(String actionKey){
		if(actionKey == null){
			return null;
		}
		MmActionConfig actionConfig = null;
		actionConfig = this.healthLevelMap.get(actionKey);
		return actionConfig;
	}
	
	private void executeAction(MmActionConfig actionConfig, String emailMsg){
		if(actionConfig == null) return;
		List<MmSendEmailConfig> emailConfigList = actionConfig.getMmSendEmail();
		List<MmExecuteCommandConfig> commandConfigList = actionConfig.getMmExecuteCommand();
		if(emailConfigList != null){
			for(int i=0;i<emailConfigList.size();i++){
				try {
					EmailNotification.getInstance().sendEmail(emailConfigList.get(i),emailMsg);
				} 
				catch (NoClassDefFoundError e) {
					logger.log(Level.ERROR, "The mail.jar is not present in the lib path!");
					if(logger.isEnabledFor(Level.DEBUG)){
						e.printStackTrace();
					}
				}
				catch (Exception e) {
					logger.log(Level.DEBUG, "Send email failed with message: " + e.getMessage());
					if(logger.isEnabledFor(Level.DEBUG)){
						e.printStackTrace();
					}
				}
			}
		}
		if(commandConfigList != null){
			for(int i=0;i<commandConfigList.size();i++){
				LocalCommandHandler commandHandler = new LocalCommandHandler(commandConfigList.get(i));
				try {
					commandHandler.executeCommand();
				} catch (Exception e) {
					logger.log(Level.DEBUG, "Execute command failed with message: " + e.getMessage());
				}
			}
		}		
	}
	
    private static synchronized ActionConfigurationProvider getInstance(){
        if (instance == null){
            instance = new ActionConfigurationProvider();
        }
        return instance;
    }
    
    private void internalOnAlertAction(String fqname, String type, String severity, String msg){
    	String path = this.getPath(fqname, type);
    	if(path == null) return;
    	String actionKey = path+":"+severity;
    	MmActionConfig actionConfig = this.getAlertActionConfig(actionKey);
    	if(actionConfig == null){
    		actionKey = this.getWildCastString(path)+":"+severity;
    		actionConfig = this.getAlertActionConfig(actionKey);
    	}
    	this.executeAction(actionConfig, msg);
    }
    
    private void internalOnHealthLevelAction(String fqname, String type, String healthLevel){
    	String path = this.getPath(fqname, type);
    	if(path == null) return;
    	String actionKey = path+":"+healthLevel;
    	MmActionConfig actionConfig = this.getHealthActionConfig(actionKey);
    	if(actionConfig == null){
    		actionKey = this.getWildCastString(path)+":"+healthLevel;
    		actionConfig = this.getHealthActionConfig(actionKey);
    	}
    	String msg = "The Health Level for "+fqname+" has been set to "+healthLevel;
    	this.executeAction(actionConfig, msg);
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "executeActionOnAlert",
        synopsis = "Execute any configured action for an entity when receiving alert",
        signature = "void executeActionOnAlert(String fqname, String entityType, String severity, String alertMsg)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "fqname", type = "String", desc = "The entity fq name"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "entityType", type = "String", desc = "The entity type"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "severity", type = "String", desc = "The severity of the received alert")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "4.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Execute any configured action associate with the alert with severity",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static void executeActionOnAlert(String fqname, String entityType, String severity, String alertMsg){
        ActionConfigurationProvider.getInstance().internalOnAlertAction(fqname, entityType, severity, alertMsg);
    }    
    
    @com.tibco.be.model.functions.BEFunction(
        name = "executeActionOnAlert",
        synopsis = "Execute any configured action on health level value of an entity",
        signature = "void executeActionOnHealthLevel(String fqname, String entityType, String healthLevel)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "fqname", type = "String", desc = "The entity fq name"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "entityType", type = "String", desc = "The entity type")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "4.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Execute any configured action on health level value of an entity",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static void executeActionOnHealthLevel(String fqname, String entityType, String healthLevel){
        ActionConfigurationProvider.getInstance().internalOnHealthLevelAction(fqname, entityType, healthLevel);
    } 


}
