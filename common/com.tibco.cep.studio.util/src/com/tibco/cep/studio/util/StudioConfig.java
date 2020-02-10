package com.tibco.cep.studio.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.tibco.be.util.BEProperties;

public class StudioConfig {
	
	public static final String UI_PROP_LOG_FILENAME = "ui.log.fileName";
	public static final String UI_PROP_REDIRECT_HTTPLOG = "ui.redirect.httplog";

	public static final String STUDIO_CONFIG_PARAMETER = "studio.wrapper.tra.file";
	private static final String STUDIO_CONFIG_DEFAULT_PATH = "/studio/eclipse/configuration/studio.tra";
	private static boolean initialized = false;
	
	private BEProperties beProperties;
	private String uiConfigFile;
	private static StudioConfig instance = null;
	
	public static StudioConfig getInstance() {
		if(instance == null) {
			instance = new StudioConfig();
		}
		
		return instance;
	}
	
	private StudioConfig() {
		init();
	}
	
	public void init() {
		if (!initialized) {
			String beHome = System.getProperty("BE_HOME");
			if (beHome == null)
				beHome = "";
			String studioConfigDefault = beHome + STUDIO_CONFIG_DEFAULT_PATH;
			String configPath = System.getProperty(STUDIO_CONFIG_PARAMETER, studioConfigDefault);
			initBEProperties(configPath);
			initialized = true;
		}
	}
	
	/**
	 * Substitutes the environment variables (i.e. %BE_HOME%) of the property
	 * with the actual values
	 * @param property - the name of the property in the studio.tra file
	 * @return the substituted string
	 */
	public static String substituteEnvVars(String propertyName) {
		String propertyValue = StudioConfig.getInstance().getProperty(propertyName);
		if (propertyValue == null) {
			return "";
		}
		while (propertyValue.indexOf('%') != -1) {
			int i1 = propertyValue.indexOf('%');
			int i2 = propertyValue.indexOf('%', i1+1);
			if (i2 == -1) {
				break;
			}
			String var = propertyValue.substring(i1+1, i2);
			String varSubst = System.getProperty(var);
			if (("PATH".equals(var) || "%PATH%".equals(var)) && propertyName.endsWith("PATH")) {
				// use system's setting to avoid recursive substitutions
				varSubst = System.getenv("PATH");
			}
			if (varSubst == null) {
				if ("PATH".equals(var) || "%PATH%".equals(var)) {
					varSubst = null;
				} else {
					varSubst = StudioConfig.getInstance().getProperty("tibco.env."+var);
				}
			}
			if (varSubst == null) {
				varSubst = "";
			}
			propertyValue = propertyValue.replaceAll("%"+var+"%", varSubst);
		}
		return propertyValue;
	}

	protected void initBEProperties(String configPath)
	{
		if (configPath != null) {
			try {
				File file = new File(configPath);
				if (file.exists()) {
					beProperties = new BEProperties();
					beProperties.load(new FileInputStream(file));
					beProperties.updateSystemProperties();
					beProperties.substituteTibcoEnvironmentVariables();
					beProperties.substituteTibcoEnvironmentVariables();
					uiConfigFile = configPath;
				}
			} catch (IOException e) {
				//TODO Handle better
				e.printStackTrace();
			}
		}
	}
	
	public BEProperties getRoot() {
		return beProperties;
	}
	
	public BEProperties getBranch(final String prefix) {
		if (beProperties != null) {
			return beProperties.getBranch(prefix);
		}
		return null;
	}
	
	public BEProperties getStartsWith(final String startsWith) {
		if (beProperties != null) {
			return beProperties.getStartsWith(startsWith);
		}
		return null;
	}
	
	public String getProperty(final String key) {
		if (beProperties != null) {
			return beProperties.getProperty(key);
		}
		return null;
	}
	
	public String getProperty(final String key, String defaultValue) {
		if (beProperties != null) {
			return beProperties.getProperty(key, defaultValue);
		}
		return defaultValue;
	}
	
	public String getConfigFile()
	{
		return uiConfigFile;
	}
}
