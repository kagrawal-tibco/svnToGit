package com.tibco.cep.bemm.management.util;

import java.util.List;
import java.util.Map;

import com.tibco.be.util.config.cdd.ClusterConfig;
import com.tibco.be.util.config.cdd.LogConfigConfig;
import com.tibco.be.util.config.cdd.PropertyConfig;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.model.Application;
import com.tibco.cep.bemm.model.Host;
import com.tibco.cep.bemm.model.ProcessingUnit;
import com.tibco.cep.bemm.model.ServiceInstance;
import com.tibco.cep.bemm.model.impl.DeploymentVariables;
import com.tibco.cep.bemm.model.impl.NameValuePair;
import com.tibco.cep.bemm.model.impl.NameValuePairs;
import com.tibco.cep.bemm.persistence.service.BEApplicationsManagementDataStoreService;
import com.tibco.cep.runtime.util.SystemProperty;


/**
 *  @author ssinghal
 * 
 */

public class FetchCddDataUtil {
	
	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(FetchCddDataUtil.class);
	
	public static final String LOG_LOCATION_DIR="LOGDIR";
	public static final String LOG_FILE_NAME="LOGFILENAME";
	public static final String AS_EXTENSION_KEY="-as";
	
	
	public static Map<String, String> getLogLocationMap(Map<String, String> instanceLogLocation, ServiceInstance serviceInstance, String applicationName, 
			BEApplicationsManagementDataStoreService<?> dataStoreService, Map<String, String> traProps) {
		
		try{
			ClusterConfig clusterConfig = dataStoreService.fetchApplicationCDD(applicationName);
			ProcessingUnit processingUnit = serviceInstance.getHost().getApplication().getProcessingUnit(serviceInstance.getPuId());
			
			//get log config
			LogConfigConfig puLogConfig = null;
			for(LogConfigConfig logConfig : clusterConfig.getLogConfigs().getLogConfig()){
				if(logConfig.getId().equals(processingUnit.getLogConfigId())){
					puLogConfig = logConfig;
				}
			}
			
			populateBeLogConfigDir(SystemProperty.TRACE_FILE_DIR.getPropertyName(), traProps, instanceLogLocation, serviceInstance, processingUnit, puLogConfig, 
					getCddValue(clusterConfig, SystemProperty.TRACE_FILE_DIR.getPropertyName()), serviceInstance.getName()+LOG_LOCATION_DIR);
			populateBeLogConfigName(SystemProperty.TRACE_FILE_NAME.getPropertyName(), traProps, instanceLogLocation, serviceInstance, processingUnit, puLogConfig, 
					getCddValue(clusterConfig, SystemProperty.TRACE_FILE_NAME.getPropertyName()), serviceInstance.getName()+LOG_FILE_NAME);
			
			populateAsLogConfig(SystemProperty.AS_LOG_DIR.getPropertyName(), traProps, instanceLogLocation, serviceInstance, processingUnit, 
					getCddValue(clusterConfig, SystemProperty.AS_LOG_DIR.getPropertyName()), serviceInstance.getName()+LOG_LOCATION_DIR + AS_EXTENSION_KEY);
			populateAsLogConfig(SystemProperty.AS_LOG_FILE_NAME.getPropertyName(), traProps, instanceLogLocation, serviceInstance, processingUnit, 
					getCddValue(clusterConfig, SystemProperty.AS_LOG_FILE_NAME.getPropertyName()), serviceInstance.getName()+LOG_FILE_NAME + AS_EXTENSION_KEY);
		
		}catch(Exception e){
			LOGGER.log(Level.ERROR, "exception while populating log locations");
		}finally {
			return instanceLogLocation;
		}
	}
	
	private static String getCddValue(ClusterConfig clusterConfig, String propertyName){
		for(PropertyConfig cddProps : clusterConfig.getPropertyGroup().getProperty()){
			if(cddProps.getName().equals(propertyName)){
				return cddProps.getValue();
			}
		}
		return null;
	}
	
	private static void populateBeLogConfigDir(String propertyName, Map<String, String> traProps, Map<String, String> instanceLogLocation, ServiceInstance serviceInstance,
			ProcessingUnit processingUnit, LogConfigConfig puLogConfig, String cddValue, String mapKey){
		if(getValFromProps(traProps, propertyName)!=null){
			instanceLogLocation.put(mapKey, getValFromProps(traProps, propertyName));
		}else if(getValFromProps(processingUnit.getProperties(), propertyName)!=null){
			instanceLogLocation.put(mapKey, getValFromProps(processingUnit.getProperties(), propertyName));
		}else if(puLogConfig.getFiles().getDir().getMixed().size()!=0){
			if(puLogConfig.getFiles().getDir().getMixed().get(0).getValue()!=null && !puLogConfig.getFiles().getDir().getMixed().get(0).getValue().equals(""))
					instanceLogLocation.put(mapKey, (String)puLogConfig.getFiles().getDir().getMixed().get(0).getValue());
		}else if(cddValue!=null && !cddValue.equals("")){
			instanceLogLocation.put(mapKey, cddValue);
		}
		
		DeploymentVariables beProperties  = serviceInstance.getBEProperties();
		if(null!=beProperties){
			NameValuePairs nameValuePairs = beProperties.getNameValuePairs();
			if(null!=nameValuePairs){
				List<NameValuePair> nameValues = nameValuePairs.getNameValuePair();
		    	if (null!=nameValues){
					for (NameValuePair nameValuePair : nameValues) {
						if(null!=nameValuePair){
							String name=nameValuePair.getName();
							if(propertyName.equals(name)){
								String val=nameValuePair.getDeployedValue();
								if(null!=val && !val.trim().isEmpty()){
									instanceLogLocation.put(mapKey, val);
								}
							}
						}
					}					 
				}
			}				
		}
	}
	
	private static void populateBeLogConfigName(String propertyName, Map<String, String> traProps, Map<String, String> instanceLogLocation, ServiceInstance serviceInstance,
			ProcessingUnit processingUnit, LogConfigConfig puLogConfig, String cddValue, String mapKey){
		if(getValFromProps(traProps, propertyName)!=null){
			instanceLogLocation.put(mapKey, getValFromProps(traProps, propertyName));
		}else if(getValFromProps(processingUnit.getProperties(), propertyName)!=null){
			instanceLogLocation.put(mapKey, getValFromProps(processingUnit.getProperties(), propertyName));
		}else if(puLogConfig.getFiles().getName().getMixed().size()!=0){
			if(puLogConfig.getFiles().getName().getMixed().get(0).getValue()!=null && !puLogConfig.getFiles().getName().getMixed().get(0).getValue().equals(""))
				instanceLogLocation.put(mapKey, (String)puLogConfig.getFiles().getName().getMixed().get(0).getValue());
		}else if(cddValue!=null && !cddValue.equals("")){
			instanceLogLocation.put(mapKey, cddValue);
		}
	}
	
	private static void populateAsLogConfig(String propertyName, Map<String, String> traProps, Map<String, String> instanceLogLocation, ServiceInstance serviceInstance,
			ProcessingUnit processingUnit, String cddValue, String mapKey){
		if(getValFromProps(traProps, propertyName)!=null){
			instanceLogLocation.put(mapKey, getValFromProps(traProps, propertyName));
		}else if(getValFromProps(processingUnit.getProperties(), propertyName)!=null){
			instanceLogLocation.put(mapKey, getValFromProps(processingUnit.getProperties(), propertyName));
		}else if(cddValue!=null && !cddValue.equals("")){
			instanceLogLocation.put(mapKey, cddValue);
		}
	}
	
	private static String getValFromProps(Map<String, String> traProps, String propertyName){
		if(traProps.get(propertyName)!=null && !traProps.get(propertyName).equals("")){
			return traProps.get(propertyName);
		}
		return null;
	}
	
}
