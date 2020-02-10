package com.tibco.cep.studio.debug.core.launch.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunchConfiguration;

import com.tibco.cep.studio.debug.core.model.RuleDebugProfile;

public class RuleDebugProfileImpl extends AbstractProfile implements RuleDebugProfile{

	private List<String> rules = new ArrayList<String>();;
	
	
	public RuleDebugProfileImpl(ILaunchConfiguration configuration,IProgressMonitor monitor) {
		super(RUNTIME_ENV_TYPE.ENV_RULE,configuration, monitor);
	}


    public List<String> getRules() {
        return rules;
    }

    public void setRules(List<String> rules) {
        this.rules = rules;
    }

    

    public File getBeJarPath() {
//    	File workingDir = new File(getWorkingDir());
        File jarFile = null;
//    	if (workingDir.isDirectory()) {
//            String url = getProjectNode().getUrl();
//            url = BEToolsUtil.normalizeUrl(url);
//            if (url.endsWith(BEToolsUtil.FILE_SEPARATOR_NORMALIZED)) {
//                url = url.substring(0,url.lastIndexOf(BEToolsUtil.FILE_SEPARATOR_NORMALIZED));
//            }
//            String projectName = url.substring(url.lastIndexOf(BEToolsUtil.FILE_SEPARATOR_NORMALIZED)+1);
//            File projectDir = new File(workingDir,projectName);
//            jarFile = new File(projectDir, JAR_FILE_NAME);
//        } else {
//            throw new RuntimeException(workingDir.getPath()+" is not a directory.");
//        }
        return jarFile;
    }
	
		
}
