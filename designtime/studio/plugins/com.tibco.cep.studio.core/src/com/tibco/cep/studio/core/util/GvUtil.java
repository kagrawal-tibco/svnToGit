package com.tibco.cep.studio.core.util;

import java.util.Map;

import org.eclipse.core.resources.IProject;

import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.cep.studio.core.validation.DefaultResourceValidator;

public class GvUtil {
	public static boolean isGlobalVar(String str) {
		if (str==null)
			return false;
		str = str.trim();
		if (str.startsWith("%%") && str.endsWith("%%")) {
			String[] tokens = str.split("%%");
			if (tokens.length==2)
				return true;
		}
		return false;
	}

	/**
	 * Whether value can be set at runtime.
	 * @param glbVars
	 * @param variable
	 * @return
	 */
	public static boolean isServiceSettable(Map<String, GlobalVariableDescriptor> glbVars, String variable) {
		if (GvUtil.isGlobalVar(variable)) {
			GlobalVariableDescriptor descriptor = glbVars.get(stripGvMarkers(variable));
			return descriptor != null && descriptor.isServiceSettable();
		}
		return false;
	}
	
	public static String getGvDefinedValue(IProject project, String variable) {
		if(project!=null && variable != null){
			return getGvDefinedValue(project.getName(), variable);
		}else{
			return "";
		}
	}
	public static String getGvDefinedValue(String projectName, String variable) {
		Map<String, GlobalVariableDescriptor> glbVars = DefaultResourceValidator.getGlobalVariableNameValues(projectName);
		return (getGvDefinedValue(glbVars, variable));
	}
	
	public static String getGvDefinedValue(Map<String, GlobalVariableDescriptor> glbVars, String variable) {
		String value = "";
		if (isGlobalVar(variable)) {
			GlobalVariableDescriptor descriptor = glbVars.get(stripGvMarkers(variable));
			if (descriptor == null) {
				return null;
			}
			value = descriptor.getValueAsString();
			if (value == null)
				return null;
			if (PasswordUtil.isEncodedString(value))
				value = PasswordUtil.getDecodedString(value);
			return (value);
		} else {
			String[] tokens = variable.split("%%");
			if (tokens.length > 0) {
				if (getCount(variable, "%%")%2 != 0)
					return null;
				boolean gvNext = variable.startsWith("%%") && !tokens[0].equals("");
				for (String token: tokens) {
					if (gvNext) {
						String gvVal = glbVars.get(token).getValueAsString();
						if (gvVal == null)
							return null;
						value += gvVal;
					} else {
						value += token;
					}
					gvNext = !gvNext;
				}
			}
		}
		return value;
	}
	
	public static String getGvType(String projectName, String variable){
		Map<String, GlobalVariableDescriptor> glbVars = DefaultResourceValidator.getGlobalVariableNameValues(projectName);
		if (isGlobalVar(variable)) {
			GlobalVariableDescriptor descriptor = glbVars.get(stripGvMarkers(variable));
			if (descriptor == null) {
				return null;
			}
			String type = descriptor.getType();
			return type;
		}
		return null;
		
	}
	
	public static String getGvVariable(String variable) {
		if (isGlobalVar(variable)) {
			return (stripGvMarkers(variable));
		} else {
			String[] tokens = variable.split("%%");
			if (tokens.length > 0) {
				if (getCount(variable, "%%")%2 != 0)
					return null;
				boolean gvNext = variable.startsWith("%%") && !tokens[0].equals("");
				for (String token: tokens) {
					if (gvNext) {
						return (token);
					} else {
					}
					gvNext = !gvNext;
				}
			}
		}
		return variable;
	}
	
	private static int getCount(String str, String substr) {
		int lastIndex = -(substr.length());
		int count =0;

		while (lastIndex != -1 && lastIndex != 0) {
			lastIndex = str.indexOf(substr, lastIndex+substr.length());
			if( lastIndex != -1){
				count ++;
			}
		}
		return count;
	}

	public static boolean validateGvField(final String gvFieldValue, boolean isRequired, boolean isInt) {
    	return true;	//TODO
    }
    
	public static String stripGvMarkers(String variable) {
		int firstIndex = variable.indexOf("%%");
		String stripVal = variable.substring(firstIndex + 2);
		if(stripVal.indexOf("%%")!=-1){
			stripVal = stripVal.substring(0, stripVal.indexOf("%%"));
		}
		return	stripVal;
	}
	
	public static String getPossibleGV(String projectName, String value) {
		if (projectName != null && GvUtil.isGlobalVar(value)) {
            value = GvUtil.getGvDefinedValue(projectName, value);
        }
		return value;
	}
	public static String getPossibleGV(IProject project, String value) {
        if(project == null) return getPossibleGV((String)null, value);
        else return getPossibleGV(project.getName(), value);
	}

	public static int getPossibleGVAsInt(String projectName, String value) throws NumberFormatException {
		return Integer.parseInt(getPossibleGV(projectName,  value));
	}
	public static int getPossibleGVAsInt(IProject project, String value) throws NumberFormatException {
		if(project == null) return getPossibleGVAsInt((String)null, value);
		else return getPossibleGVAsInt(project.getName(), value);
	}
}