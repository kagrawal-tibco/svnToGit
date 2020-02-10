package com.tibco.be.deployment.administrator;

import java.io.IOException;

import com.tibco.pof.admindomain.build.MutableAdminDomain;
import com.tibco.pof.administrator.model.AdministratorPlugInFile;
import com.tibco.pof.administrator.model.InstallationPlugIn;
import com.tibco.pof.administrator.model.build.MutableAdministratorModel;
import com.tibco.pof.administrator.model.build.MutableConsoleDescriptor;
import com.tibco.pof.administrator.model.helpers.ConsoleFactory;
import com.tibco.pof.administrator.model.impl.AdministratorModelImpl;
import com.tibco.pof.administrator.model.impl.ApplicationsModule;
import com.tibco.util.plugin.PluginException;


/**
 * Registers the Administrator plugins.
 */
public class BEInstallationPlugIn implements InstallationPlugIn {


    public static final String GENERAL_TAB_NAME = "BEServiceInstanceGeneralTab";
    public static final String GENERAL_TAB_CLASS = "com.tibco.be.deployment.administrator.BEServiceInstanceGeneralTab";

    public static final String TRACING_TAB_NAME = "BEServiceInstanceTracingTab";
    public static final String TRACING_TAB_CLASS = "com.tibco.be.deployment.administrator.BEServiceInstanceTracingTab";

    public static final String SERVER_SETTINGS_TAB_NAME = "BEConfigurationServerSettingsTab";
    public static final String SERVER_SETTINGS_TAB_CLASS = "com.tibco.be.deployment.administrator.BEConfigurationServerSettingsTab";


    public void install(MutableAdminDomain domain, AdministratorPlugInFile pluginBeingInstalled,
                        String previousInstalledImplementationVersionNumber)
            throws PluginException, IOException, IllegalAccessException,
            ClassNotFoundException, InstantiationException {

        final MutableAdministratorModel model = ConsoleFactory.getMutableAdministratorModel(domain);

        // Service Instances

        final ApplicationsModule appModule = (ApplicationsModule) model
                .getConsoleDescriptorByName(AdministratorModelImpl.APPLICATIONS_MODULE_NAME);
        MutableConsoleDescriptor configurationConsole = (MutableConsoleDescriptor) appModule
                .getConsoleDescriptorByName(AdministratorModelImpl.CONTAINERS_CONSOLE_NAME);
        final MutableConsoleDescriptor containers = (MutableConsoleDescriptor) model
                .getApplicationConfigurationConsoleDescriptorByName("Deployment");

        containers.removeTabDescriptor(GENERAL_TAB_NAME);
        containers.addTabDescriptor(GENERAL_TAB_NAME, GENERAL_TAB_CLASS, 1, pluginBeingInstalled);
        configurationConsole.removeTabDescriptor(GENERAL_TAB_NAME);
        configurationConsole.addTabDescriptor(GENERAL_TAB_NAME, GENERAL_TAB_CLASS, 1, pluginBeingInstalled);

        containers.removeTabDescriptor(TRACING_TAB_NAME);
        containers.addTabDescriptor(TRACING_TAB_NAME, TRACING_TAB_CLASS, 2, pluginBeingInstalled);
        configurationConsole.removeTabDescriptor(TRACING_TAB_NAME);
        configurationConsole.addTabDescriptor(TRACING_TAB_NAME, TRACING_TAB_CLASS, 2, pluginBeingInstalled);


        // Server Settings

        configurationConsole = (MutableConsoleDescriptor) model
                .getApplicationConfigurationConsoleDescriptorByName(AdministratorModelImpl.CONFIGURATION_CONSOLE_NAME);

        configurationConsole.removeTabDescriptor(SERVER_SETTINGS_TAB_NAME);
        configurationConsole.addTabDescriptor(SERVER_SETTINGS_TAB_NAME, SERVER_SETTINGS_TAB_CLASS, 3, pluginBeingInstalled);

    }//install


    public void uninstall(MutableAdminDomain domain, AdministratorPlugInFile pluginBeingUninstalled)
            throws ClassNotFoundException, PluginException, InstantiationException, IllegalAccessException {
    }//uninstall


}//class


