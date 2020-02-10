package com.tibco.cep.runtime.service.tester.core;

import java.util.Map;

import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.impl.EntityImpl;

/**
 * 
 * @author smarathe
 *
 */
public class ManipulateEntityRuleFunction implements RuleFunction {

	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(ManipulateEntityRuleFunction.class);
	@Override
	public ParameterDescriptor[] getParameterDescriptors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSignature() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.kernel.model.rule.RuleFunction#invoke(java.lang.Object[])
	 */
	@Override
	public Object invoke(Object[] objects) {
		EntityImpl entity = (EntityImpl) objects[0];
		Map<String, String> changedPropertiesMap = (Map<String, String>) objects[1];
		if(entity instanceof ConceptImpl) {
			ConceptImpl concept = (ConceptImpl)entity;
			try {
				for(String propertyName: changedPropertiesMap.keySet()) {
					String propertyValue = changedPropertiesMap.get(propertyName);
					LOGGER.log(Level.DEBUG, "Manipulate property value----> " + propertyValue);
					LOGGER.log(Level.DEBUG, "Manipulate property name----> " + concept.getProperty(propertyName).getName());
					concept.setPropertyValue(concept.getProperty(propertyName).getName(), propertyValue);
				}
			} catch (Exception e) {
				LOGGER.log(Level.INFO, "Exception in RF" + getCustomStackTrace(e));
			}
		}
		return null;
	}
	
	public String getCustomStackTrace(Throwable aThrowable) {
		final StringBuilder result = new StringBuilder( "Exception: " );
		result.append(aThrowable.toString());
		final String newLine = System.getProperty("line.separator");
		result.append(newLine);
		for (StackTraceElement element : aThrowable.getStackTrace() ){
			result.append( element );
			result.append( newLine );
		}
		return result.toString();
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.kernel.model.rule.RuleFunction#invoke(java.util.Map)
	 */
	@Override
	public Object invoke(Map maps) {
		return null;
	}
}
