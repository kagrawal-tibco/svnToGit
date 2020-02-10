/**
 * 
 */
package com.tibco.cep.runtime.util;

import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.cep.repo.GlobalVariables;

/**
 * @author mgujrath
 *
 */
public class GvCommonUtils {

	public static boolean isGlobalVar(String str) {
		if (str==null)
			return false;

		if (str.startsWith("%%") && str.endsWith("%%")) {
			String[] tokens = str.split("%%");
			if (tokens.length==2)
				return true;
		}
		return false;
	}

	public static String stripGvMarkers(String variable) {
		int firstIndex = variable.indexOf("%%");
		String stripVal = variable.substring(firstIndex + 2);
		if(stripVal.indexOf("%%")!=-1){
			stripVal = stripVal.substring(0, stripVal.indexOf("%%"));
		}
		return	stripVal;
	}

	public static String getStringValueIfExists(GlobalVariables gvs, String name){
		if(isGlobalVar(name)){
			GlobalVariableDescriptor gv=gvs.getVariable(stripGvMarkers(name));
			return gv.getValueAsString();
		}
		return name;
	}

}
