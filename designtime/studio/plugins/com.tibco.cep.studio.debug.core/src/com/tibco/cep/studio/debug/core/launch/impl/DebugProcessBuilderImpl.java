package com.tibco.cep.studio.debug.core.launch.impl;

import java.util.Properties;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;

import com.tibco.cep.studio.debug.core.launch.DebugProfile;
import com.tibco.cep.studio.debug.core.launch.RuntimeEnvironment;
import com.tibco.cep.studio.debug.core.model.impl.RuntimeEnvironmentImpl;

public class DebugProcessBuilderImpl extends AbstractProcessBuilder implements DebugProcessBuilder {

    public DebugProcessBuilderImpl(ILaunch launch, ILaunchConfiguration config, boolean isDebugSession, IProgressMonitor monitor){
        super(launch,config,monitor);
        setDebugSession(isDebugSession);
    }

    public DebugProfile getProfile() {
        return (DebugProfile) profile;
    }
    
    protected Properties getProfileProperties(boolean isDebugSession) {
        Properties prop = new Properties();
        if(isDebugSession) {
            prop.setProperty("tibco.clientVar.Debugger/sessionCounter",String.valueOf(0));
            prop.setProperty("tibco.clientVar.Debugger/profile/name",getProfile().getName());
            prop.setProperty("tibco.clientVar.Debugger/profile/beHome",getProfile().getBEHome());
            prop.setProperty("tibco.clientVar.Debugger/profile/workingDir",getProfile().getWorkingDir());
            prop.setProperty("tibco.clientVar.Debugger/profile/repoUrl",getProfile().getEarFilePath());
            prop.setProperty("tibco.clientVar.Debugger/profile/hostName",getHostName());
            prop.setProperty("tibco.clientVar.Debugger/profile/portNumber",getPort());
        }
        prop.setProperty("tibco.repourl",  getProfile().getEarFilePath());
        return prop;
    }

    protected RuntimeEnvironment getRuntimeEnvironment(Properties traProperties){
        return new RuntimeEnvironmentImpl(traProperties,getProfile());
    }
}
