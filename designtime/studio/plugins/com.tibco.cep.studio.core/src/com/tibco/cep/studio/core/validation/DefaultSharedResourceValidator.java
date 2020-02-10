package com.tibco.cep.studio.core.validation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;

import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.cep.studio.core.util.GvUtil;

/*
 @author ssailapp
 @date Feb 17, 2011
 */

public abstract class DefaultSharedResourceValidator extends
		DefaultResourceValidator {

	protected abstract String getMessageString(String key, Object... arguments);

	/**
	 * @param resource
	 * @param glbVars
	 * @param val
	 * @param emptyMsg
	 * @param invalidMsg
	 * @param severity
	 */
	protected boolean validateStringField(IResource resource,
			Map<String, GlobalVariableDescriptor> glbVars, String val,
			String emptyMsg, String invalidMsg, int severity) {
		if (val != null) {
			if (GvUtil.isGlobalVar(val)) {
				String stripVal = val.substring(val.indexOf("%%") + 2);
				stripVal = stripVal.substring(0, stripVal.indexOf("%%"));
				if (!glbVars.keySet().contains(stripVal)) {
					reportProblem(resource, getMessageString(invalidMsg, val),
							severity);
					return false;
				}
			} else if (val.trim().equals("")) {
				reportProblem(resource, getMessageString(emptyMsg, val),
						severity);
				return false;
			}
		} else {
			reportProblem(resource, getMessageString(emptyMsg, val), severity);
			return false;
		}
		return true;
	}

	/**
	 * @param resource
	 * @param glbVars
	 * @param val
	 * @param emptyMsg
	 * @param invalidMsg
	 * @param severity
	 */
	protected void validateGvField(IResource resource,
			Map<String, GlobalVariableDescriptor> glbVars, String val,
			String emptyMsg, String invalidMsg, int severity) {
		if (val.trim().equals("")) {
			reportProblem(resource, getMessageString(emptyMsg, val), severity);
		} else if (GvUtil.getGvDefinedValue(resource.getProject(), val) == null) {
			reportProblem(resource, getMessageString(invalidMsg, val), severity);
		}
	}

	/**
	 * @param resource
	 * @param glbVars
	 * @param val
	 * @param invalidMsg
	 * @param severity
	 */
	protected void validateStringField(IResource resource,
			Map<String, GlobalVariableDescriptor> glbVars, String val,
			String invalidMsg, int severity) {
		if (val != null) {
			if (GvUtil.isGlobalVar(val)) {
				String stripVal = val.substring(val.indexOf("%%") + 2);
				stripVal = stripVal.substring(0, stripVal.indexOf("%%"));
				if (!glbVars.keySet().contains(stripVal)) {
					reportProblem(resource, getMessageString(invalidMsg, val),
							severity);
				}
			}
		} else {
			reportProblem(resource, getMessageString(invalidMsg, val), severity);
		}
	}

	/**
	 * @param resource
	 * @param glbVars
	 * @param val
	 * @param emptyMsg
	 * @param invalidMsg
	 * @param severity
	 * @param isPositive
	 * @param isNatural
	 */
	protected void validateNumericField(IResource resource,
			Map<String, GlobalVariableDescriptor> glbVars, String val,
			String emptyMsg, String invalidMsg, int severity,
			boolean isPositive, boolean isNatural) {
		boolean valid = validateStringField(resource, glbVars, val, emptyMsg,
				invalidMsg, severity);
		if (valid) {
			validateNumericField(resource, glbVars, val, invalidMsg, severity,
					isPositive, isNatural);
		}
	}

	protected void validateNumericField(IResource resource,
			Map<String, GlobalVariableDescriptor> glbVars, String val,
			String invalidMsg, int severity, boolean isPositive,
			boolean isNatural) {
		boolean valid = validateNumericField(val, glbVars, isPositive,
				isNatural);
		if (!valid)
			reportProblem(resource, getMessageString(invalidMsg, val), severity);
	}
	
	protected void validateFloatField(IResource resource,
			Map<String, GlobalVariableDescriptor> glbVars, String val,
			String invalidMsg, int severity, boolean isPositive,
			boolean isNatural) {
		boolean valid = validateFloatField(val, glbVars, isPositive,
				isNatural);
		if (!valid)
			reportProblem(resource, getMessageString(invalidMsg, val), severity);
	}

	protected void validateNumericConfigField(IResource resource,
			Map<String, GlobalVariableDescriptor> glbVars, String propName,
			String val, String invalidMsg, int severity, boolean isPositive,
			boolean isNatural) {
		boolean valid = validateNumericField(val, glbVars, isPositive,
				isNatural);
		if (!valid)
			reportProblem(resource, getMessageString(invalidMsg, propName),
					IMarker.SEVERITY_ERROR);
		
	}

	protected boolean validateNumericField(String val,
			Map<String, GlobalVariableDescriptor> glbVars, boolean isPositive,
			boolean isNatural) {
		if (val == null)
			return true;
		if (!val.trim().equals("")) {
			// Check for service settable vars
			if (GvUtil.isServiceSettable(glbVars, val)) {
				return true;
			}
			String value = GvUtil.getGvDefinedValue(glbVars, val);
			try {
				int intValue = Integer.parseInt(value);
				if (isNatural) {
					if (intValue > 0)
						return true;
					else
						return false;
				}
				if(!isPositive){
					return true;
				}
				if (isPositive && intValue >= 0)
					return true;
				return false;
			} catch (Exception e) {
				return false;
			}

		}
		return true;
	}
	
	protected boolean validateFloatField(String val,
			Map<String, GlobalVariableDescriptor> glbVars, boolean isPositive,
			boolean isNatural) {
		if (val == null)
			return true;
		if (!val.trim().equals("")) {
			// Check for service settable vars
			if (GvUtil.isServiceSettable(glbVars, val)) {
				return true;
			}
			String value = GvUtil.getGvDefinedValue(glbVars, val);
			try {
				float floatValue = Float.parseFloat(value);
				if (isNatural) {
					if (floatValue > 0)
						return true;
					else
						return false;
				}
				if(!isPositive){
					return true;
				}
				if (isPositive && floatValue >= 0)
					return true;
				return false;
			} catch (Exception e) {
				return false;
			}

		}
		return true;
	}

	@SuppressWarnings("serial")
	protected Set<String> getDuplicateNames(ArrayList<String> names) {
		final Set<String> dupNames = new HashSet<String>();
		Set<String> set = new HashSet<String>() {
			@Override
			public boolean add(String name) {
				if (contains(name)) {
					dupNames.add(name);
				}
				return super.add(name);
			}
		};
		for (String name : names) {
			set.add(name);
		}
		return dupNames;
	}
	
	public void reportValidateProblem(IResource resource, String message, int severity) {
		reportProblem(resource, message, SEVERITY_ERROR);
	}
}
