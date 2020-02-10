package com.tibco.cep.decision.table.utils;
/**
 * @author rmishra
 * @author aathalye
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandLineArgumentParser {
	public static final String OPERATION_TYPE = "-o";
	public static final String EAR_LOCATION = "-ear";
	public static final String DP_LOCATION= "-dp";
	public static final String DT_LOCATION = "-dt";
	public static final String ACL_LOCATION = "-acl";
	public static final String DM_LOCATION = "-dm";
	public static final String EXCEL_LOCATION= "-xl";
	
	public static final String PROJECT_BASE_DIR = "-projectDir";
	public static final String STYLE = "-style";
	public static final String DELETE_ORIG = "-delete";
	
	public static final String VIRTUAL_RULE_FUNCTION_PATH= "-vrf";
	public static final String NAME= "-n";
	public static final String TARGET_DIR= "-d";	
	private Map<String, String> cmdArgMap;
	
	public CommandLineArgumentParser(){
		cmdArgMap = new HashMap<String, String>();
	}

	public void parseCommandLineArguments(String [] args){
		if (args == null || args.length == 0) return;		
		// remove all the blank inputs
		List<String> list = new ArrayList<String>();
		for (String arg : args){
			if (!"".equals(arg.trim())){
				list.add(arg);
			}
		}
		int index = 0;
		int argLength = list.size();
		args = new String[argLength];
		args = list.toArray(args);
		for (String arg : args){			
			if (OPERATION_TYPE.equalsIgnoreCase(arg)){
				if (argLength > (index + 1)){
					cmdArgMap.put(OPERATION_TYPE, args[index+1]);
				}
			} else if (EAR_LOCATION.equalsIgnoreCase(arg)){
				if (argLength > (index + 1)){
					cmdArgMap.put(EAR_LOCATION, args[index+1]);
				}
			} else if (DP_LOCATION.equalsIgnoreCase(arg)){
				if (argLength > (index + 1)){
					cmdArgMap.put(DP_LOCATION, args[index+1]);	
				}
			} else if (DT_LOCATION.equalsIgnoreCase(arg)){
				if (argLength > (index + 1)){
					cmdArgMap.put(DT_LOCATION, args[index+1]);	
				}
			} else if (ACL_LOCATION.equalsIgnoreCase(arg)){
				if (argLength > (index + 1)){
					cmdArgMap.put(ACL_LOCATION, args[index+1]);
				}
			} else if (DM_LOCATION.equalsIgnoreCase(arg)){
				if (argLength > (index + 1)){
					cmdArgMap.put(DM_LOCATION, args[index+1]);
				}
			} else if (EXCEL_LOCATION.equalsIgnoreCase(arg)){
				if (argLength > (index + 1)){
					cmdArgMap.put(EXCEL_LOCATION, args[index+1]);
				}
			} else if (NAME.equalsIgnoreCase(arg)){
				if (argLength > (index + 1)){
					cmdArgMap.put(NAME, args[index+1]);
				}
			} else if (VIRTUAL_RULE_FUNCTION_PATH.equalsIgnoreCase(arg)){
				if (argLength > (index + 1)){
					cmdArgMap.put(VIRTUAL_RULE_FUNCTION_PATH, args[index+1]);
				}
			} else if (TARGET_DIR.equalsIgnoreCase(arg)){
				if (argLength > (index + 1)){
					cmdArgMap.put(TARGET_DIR, args[index+1]);
				}
			} else if (PROJECT_BASE_DIR.equalsIgnoreCase(arg)){
				if (argLength > (index + 1)){
					cmdArgMap.put(PROJECT_BASE_DIR, args[index+1]);
				}
			} else if (STYLE.equalsIgnoreCase(arg)){
				if (argLength > (index + 1)){
					cmdArgMap.put(STYLE, args[index+1]);
				}
			} else if (DELETE_ORIG.equalsIgnoreCase(arg)){
				if (argLength > (index + 1)){
					cmdArgMap.put(DELETE_ORIG, args[index+1]);
				}
			} 
			index ++;
		}
		
	}

	public Map<String, String> getCmdArgMap() {
		return cmdArgMap;
	}
}
