package com.tibco.cep.studio.debug.core.model;

import java.io.File;
import java.util.List;

import com.tibco.cep.studio.debug.core.launch.RunProfile;

public interface RuleDebugProfile extends RunProfile {
	
	public static final String JAR_FILE_NAME = "be.jar";
	/**
	 * 
	 * @param rules
	 */
    public void setRules(List<String> rules);
    
    /**
     * 
     * @return
     */
    public List<String> getRules();
    
    /**
     * 
     * @return
     */
    File getBeJarPath();  

	
}
