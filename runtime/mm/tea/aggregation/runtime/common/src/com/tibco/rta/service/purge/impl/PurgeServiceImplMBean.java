package com.tibco.rta.service.purge.impl;

import javax.management.openmbean.TabularDataSupport;


public interface PurgeServiceImplMBean {

	/**
	 * returns details of Purge Jobs per timer wise
	 * 
	 * @return tabular data containing details per purge timer
	 */
	public TabularDataSupport getPurgeJobDetails();
}
