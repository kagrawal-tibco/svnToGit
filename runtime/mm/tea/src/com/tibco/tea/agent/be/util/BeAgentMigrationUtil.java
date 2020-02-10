/**
 * 
 */
package com.tibco.tea.agent.be.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.tibco.tea.agent.be.ui.model.impl.BEImpl;
import com.tibco.tea.agent.be.migration.file.generator.MApplication;
import com.tibco.tea.agent.be.migration.file.generator.MInstance;
import com.tibco.tea.agent.be.migration.file.generator.MMachine;
import com.tibco.tea.agent.be.ui.model.BE;

/**
 * @author ssinghal
 *
 */
public class BeAgentMigrationUtil {
	
	public static final String MACHINENAME = 		"machineName";
	public static final String IP = 				"ip";
	public static final String OS = 				"os";
	public static final String BELIST = 			"beList";
	public static final String SSHUSER = 			"sshUser";
	public static final String SSHPASS = 			"sshPass";
	public static final String SSHPORT = 			"sshPort";
	public static final String DEPLOYMENTPATH = 	"deploymentPath";
	
	public static final String CDDPATH = 			"cddPath";
	public static final String EARPATH = 			"earPath";
	public static final String APPNAME = 			"name";
	public static final String INSTANCE = 			"instance";
	
	public static final String INSTANCENAME = 		"instanceName";
	public static final String PU = 				"pu";
	public static final String BEHOME = 			"beHome";
	public static final String JMXPORT = 			"jmxPort";
	public static final String DEPLOYPATH = 		"deployPath";
	public static final String JVMPROPERTIES = 		"jvmProperties";
	public static final String GLOBALVARIABLES = 	"globalVariables";
	
	public static final String BETRA = 			"beTra";
	public static final String BEVERSION = 		"version";
	

	public static Object getValue(Object object, String key) {
		
		if(object instanceof Map){
			return getVal((Map)object, key);
		}else if(object instanceof MApplication){
			return getVal((MApplication)object, key);
		}else if(object instanceof MMachine){
			return getVal((MMachine)object, key);
		}else if(object instanceof MInstance){
			return getVal((MInstance)object, key);
		}else if(object instanceof BE){
			return getVal((BE)object, key);
		}
		return null;
	}

	private static Object getVal(BE be, String key) {
		switch(key){
			case BEHOME :				return be.getBeHome();
			case BETRA :				return be.getBeTra();
			case BEVERSION :				return be.getVersion();
		}
		return null;
	}
	
	private static Object getVal(Map map, String key) {
		switch(key){
			case JMXPORT :	
			case SSHPORT :
				 if(map.get(key) instanceof String)
					 return Integer.parseInt((String) map.get(key));
				 else
					 return map.get(key);
			case INSTANCENAME :		
			case PU :				
			case BEHOME :           
			case MACHINENAME :		
			case DEPLOYPATH :		
			case JVMPROPERTIES :	
			case GLOBALVARIABLES :
			case BELIST :
			case IP :
			case OS :
			case SSHUSER :
			case SSHPASS :
			case DEPLOYMENTPATH :
			case CDDPATH :
			case EARPATH :
			case APPNAME :
			case INSTANCE :
			case BETRA :
			case BEVERSION :
				return map.get(key);
		}
		return null;
	}
	
	private static Object getVal(MMachine machine, String key) {
		switch(key){
			case MACHINENAME :		return machine.getMachineName();
			case IP :				return machine.getIp();
			case OS :				return machine.getOs();
			case BELIST :			return machine.getBeList();
			case SSHUSER :			return machine.getSshUser();
			case SSHPASS :			return machine.getSshPass();
			case SSHPORT :			return machine.getSshPort();
			case DEPLOYMENTPATH :	return machine.getDeploymentPath();
		}
		return null;
	}
	
	private static Object getVal(MApplication application, String key) {
		switch(key){
			case CDDPATH :		return application.getCddPath();
			case EARPATH :		return application.getEarPath();
			case APPNAME :		return application.getName();
			case INSTANCE :		return application.getInstance();
		}
		return null;
	}
	
	private static Object getVal(MInstance instance, String key) {
		switch(key){
			case INSTANCENAME :		return instance.getInstanceName();
			case PU :				return instance.getPu();
			case BEHOME :           return instance.getBeHome();
			case MACHINENAME :		return instance.getMachineName();
			case JMXPORT :			return instance.getJmxPort();
			case DEPLOYPATH :		return instance.getDeployPath();
			case JVMPROPERTIES :	return instance.getJvmProperties();
			case GLOBALVARIABLES :	return instance.getGlobalVariables();
		}
		return null;
	}

	public static List<BE> convertBeUiModelToJava(List<Object> value) {
		
		List<com.tibco.tea.agent.be.ui.model.BE> beList = new ArrayList<BE>();
		for(Object val : value){
			BE be = new BEImpl();
			be.setBeHome( (String)getValue(val, BEHOME) );
			be.setBeTra( (String)getValue(val, BETRA) );
			be.setVersion((String)getValue(val, BEVERSION));
			beList.add(be);
		}
		
		return beList;
	}

}
