package com.tibco.cep.studio.debug.core.launch.impl;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.debug.core.ILaunchConfiguration;

import com.tibco.be.util.BEProperties;
import com.tibco.cep.studio.debug.core.StudioDebugCorePlugin;
import com.tibco.cep.studio.debug.core.launch.IStudioDebugLaunchConfigurationConstants;
import com.tibco.cep.studio.debug.core.launch.RunProfile;

public class AbstractProfile implements RunProfile {
	
    protected boolean dirty;
    protected boolean isDefault;
    protected RUNTIME_ENV_TYPE type;
	protected ILaunchConfiguration launchConfiguration;
	protected BEProperties env;
	protected String entryPoint;
	protected String name="default";
	protected String vmArgs;
	protected String cmdStartUpOpts;
	protected String traFilePath;
	protected String workingDir=".";	
	protected String beHomePath = System.getProperty("BE_HOME", "c:/tibco/be/4.0");
	protected String earFilePath;
	protected String cddFilePath;
	protected String unitName;
	protected IProgressMonitor monitor;
	
    public AbstractProfile(RUNTIME_ENV_TYPE type, ILaunchConfiguration configuration, IProgressMonitor monitor) {
		this.type = type;
		this.launchConfiguration = configuration;
		if(monitor == null) {
			this.monitor = new NullProgressMonitor();
		} else {
			this.monitor = monitor;
		}
	}
    
    public void init() throws CoreException {
    	IProgressMonitor subMonitor = new SubProgressMonitor(monitor, 1);
		subMonitor.beginTask("Initializing LaunchConfiguration...",1); 
		subMonitor.subTask("Initializing core properties..."); 
    	this.beHomePath = System.getProperty(PROP_BE_HOME);
    	if(beHomePath != null) { 
    		this.name = getLaunchConfiguration().getAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_PROJECT_NAME, "");
    		this.vmArgs = getLaunchConfiguration().getAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_VM_ARGS, "");
    		this.cmdStartUpOpts = getLaunchConfiguration().getAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_CMD_STARTUP_OPTS, "");
    		this.cddFilePath = getLaunchConfiguration().getAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_CDD_FILE, "");
    		this.workingDir = getLaunchConfiguration().getAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_WORKING_DIR, "");
    		this.earFilePath = getLaunchConfiguration().getAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_EAR_FILE, "");
    		this.unitName = getLaunchConfiguration().getAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_UNIT_NAME, "");
    		this.entryPoint = type.getEntryPoint();
    		File beHome = new File(beHomePath);
    		
    		String[] splits = cmdStartUpOpts.split(" ");
    		List<String> splitList = Arrays.asList(splits);
    		int index = splitList.indexOf("--propFile");
			String val = splitList.get(index + 1);
			if(index==-1){
				this.traFilePath = new File(beHome,"bin"+File.separator+TRA_FILE_NAME).getPath(); 
			}
			else{
				if(val.contains(File.separator) || val.contains("/")){
					this.traFilePath = new File(val).getPath(); 
				}
				else{
					this.traFilePath = new File(beHome,"bin"+File.separator+val).getPath();
				}
			}
			
						
    	} else {
    		throw new CoreException(new Status(IStatus.ERROR,StudioDebugCorePlugin.PLUGIN_ID,PROP_BE_HOME+" property not set"));
    	}
    	
    	
    	subMonitor.worked(1);		
		// check for cancellation
		if (monitor.isCanceled()) {
			return;
		}	
    }
    
    protected IProgressMonitor getMonitor() {
		return monitor;
	}
    
    public ILaunchConfiguration getLaunchConfiguration() {
		return launchConfiguration;
	}

	@Override
	public String getVmArgs() {
		return this.vmArgs;
	}

	@Override
	public String getBEHome() {
		return this.beHomePath;
	}

	@Override
	public String getEngineTra() {
		return this.traFilePath;
	}

	@Override
	public String getEntryPoint() {
		return getType().getEntryPoint();
	}

	public String getCddFilePath() {
		return cddFilePath;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public RUNTIME_ENV_TYPE getType() {
		return this.type;
	}

	@Override
	public String getWorkingDir() {
		return this.workingDir;
	}
	
	@Override
	public String getEarFilePath() {
		return this.earFilePath;
	}
	
	public String getUnitName() {
		return unitName;
	}

    @Override
    public String toString() {
        if(name != null) {
            return name;
        } else {
            return super.toString();
        }
    }
	
	@Override
	public String getCmdStartUpOpts() {
		return this.cmdStartUpOpts;
	}

}
