package com.tibco.cep.dashboard.psvr.data;

/**
 * @author anpatil
 *
 */
public interface DataChangeListener {
	
	public void thresholdApplied(String dataSourceUniqueName);
	
	public void refreshed(String dataSourceUniqueName);

}
