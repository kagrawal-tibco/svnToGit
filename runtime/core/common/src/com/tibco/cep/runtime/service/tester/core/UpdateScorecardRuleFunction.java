package com.tibco.cep.runtime.service.tester.core;

import java.util.Map;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.impl.EntityImpl;

/**
 * 
 * @author sasahoo
 *
 */
public class UpdateScorecardRuleFunction implements RuleFunction {

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
		Map<Entity, Map<String, Object>> modifiedScorecardMap = (Map<Entity, Map<String, Object>>) objects[0];
		update(modifiedScorecardMap);
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.kernel.model.rule.RuleFunction#invoke(java.util.Map)
	 */
	@Override
	public Object invoke(Map maps) {
		update(maps);
		return null;
	}
	
	private void update(Map maps) {
		for (Object key : maps.keySet()) {
			Map<String, Object> changedPropertiesMap =  (Map<String, Object>)maps.get(key);
			EntityImpl entity = (EntityImpl) key;
			if(entity instanceof ConceptImpl) {
				ConceptImpl concept = (ConceptImpl)entity;
				try {
					for(String propertyName: changedPropertiesMap.keySet()) {
						Object propertyValue = changedPropertiesMap.get(propertyName);
						LOGGER.log(Level.DEBUG, "Manipulate property value----> " + propertyValue);
						LOGGER.log(Level.DEBUG, "Manipulate property name----> " + concept.getProperty(propertyName).getName());
						concept.setPropertyValue(concept.getProperty(propertyName).getName(), propertyValue);
					}
				} catch (Exception e) {
					LOGGER.log(Level.INFO, "Exception in RF");
				}
			}
		}
	}
}