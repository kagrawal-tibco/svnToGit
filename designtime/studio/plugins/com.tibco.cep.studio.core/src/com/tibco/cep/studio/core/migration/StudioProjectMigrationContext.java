package com.tibco.cep.studio.core.migration;

import java.io.File;
import java.util.Map;

import com.tibco.cep.studio.common.configuration.XPATH_VERSION;

public class StudioProjectMigrationContext implements IStudioProjectMigrationContext {

	private File fProjectLocation;
	private Map<String, String> httpProperties;
	private XPATH_VERSION xpathversion;

	public StudioProjectMigrationContext(File projectLocation, XPATH_VERSION xpathversion) {
		this.fProjectLocation = projectLocation;
		this.xpathversion = xpathversion;
	}

	@Override
	public File getProjectLocation() {
		return fProjectLocation;
	}

	@Override
	public Map<String, String> getHttpPropertiesToMigrate() {
		return this.httpProperties;
	}

	public void setHttpPropertiesToMigrate(Map<String, String> httpProperties) {
		this.httpProperties = httpProperties;
	}

	@Override
	public XPATH_VERSION getXpathVersion() {
		return this.xpathversion;
	}

}
