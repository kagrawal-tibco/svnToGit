package com.tibco.cep.studio.core.migration;

import java.io.File;
import java.util.Map;

import com.tibco.cep.studio.common.configuration.XPATH_VERSION;

public interface IStudioProjectMigrationContext {

	/**
	 * Returns the target location of the imported project
	 * @return
	 */
	public File getProjectLocation();
	
	/**
	 * Returns the http-properties from CDD and PU selected  
	 * @return
	 */
	public Map<String, String> getHttpPropertiesToMigrate();
	
	/**
	 * sets the http-properties from CDD and PU selected  
	 * @return
	 */
	public void setHttpPropertiesToMigrate(Map<String, String> httpProperties);
	
	/**
	 * sets the XPATH version 
	 * @return
	 */
	public XPATH_VERSION getXpathVersion();
	
}
