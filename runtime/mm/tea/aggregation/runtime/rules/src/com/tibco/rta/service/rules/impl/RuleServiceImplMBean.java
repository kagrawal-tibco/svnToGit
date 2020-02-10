package com.tibco.rta.service.rules.impl;

import javax.management.openmbean.TabularData;

public interface RuleServiceImplMBean {
	
	/**
	 * Return attributes associated with rule
	 * 
	 * @return attributeList
	 */
	public TabularData getRuleStatistics();
		
}
