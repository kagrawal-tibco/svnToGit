package com.tibco.be.deployment.administrator;

import java.util.Iterator;

import com.tibco.administrator.Console;
import com.tibco.administrator.EntityAdapter;
import com.tibco.administrator.consoles.deploymentconfiguration.ContainerDetailServerSettingsTab;
import com.tibco.archive.helpers.ComponentSoftwareReference;
import com.tibco.archive.helpers.StartAsOneOf;
import com.tibco.pof.admindomain.ContainerConfiguration;
import com.tibco.pof.admindomain.ServiceConfiguration;
import com.tibco.pof.admindomain.ServiceContainerBinding;
import com.tibco.pof.authorization.AuthenticationContext;
import com.tibco.pof.log.Logger;
import com.tibco.sdk.tools.MTrace;


/**
 * Extends ContainerDetailServerSettingsTab by enabling the tab for BE.
 */
public class BEConfigurationServerSettingsTab extends ContainerDetailServerSettingsTab {

    protected ContainerConfiguration m_containerConfiguration;
    protected ServiceContainerBinding m_binding;


    public void init(EntityAdapter entityAdapter, Console console, boolean flag) {
        super.init(entityAdapter, console, flag);
        m_binding = (ServiceContainerBinding) entityAdapter.getEntity();
        m_containerConfiguration = (ContainerConfiguration) m_binding.getOSProcess();
    }//init


    /**
     * Enables the "Server Settings" tab for BE Configuration.
     *
     * @param context the AuthenticationContext
     * @param entityAdapter  the EntityAdapter
     * @return true iif this tab is available, i.e. iif the current Service Instance is a BE engine.
     */
    public boolean isAvailable(AuthenticationContext context, EntityAdapter entityAdapter) {
        try {
            final Object entity = entityAdapter.getEntity();
            if (entity instanceof ServiceContainerBinding) {
                ServiceContainerBinding binding = (ServiceContainerBinding) entity;
                return this.isBE(binding.getServiceConfiguration());
            }
        } catch (Exception e) {
            Logger.getLogger().debug(MTrace.ERROR, "Administrator", Logger.getExceptionStackTrace(e), null);
        }//catch
        return false;
    }//isAvailable


    public static boolean isBE(ServiceConfiguration serviceConfiguration){
        try {
            final StartAsOneOf saoo = (StartAsOneOf) serviceConfiguration.getUploadedServiceArchive()
                    .getDeploymentDescriptorByName(StartAsOneOf.DEPLOYMENT_DESCRIPTOR_TYPE,
                            StartAsOneOf.DEPLOYMENT_DESCRIPTOR_NAME);
            // The software name must match the value of BEContainer.CONTAINER_NAME
            for (Iterator it = saoo.getComponentSoftwareReferences().iterator(); it.hasNext();) {
                final ComponentSoftwareReference csr = (ComponentSoftwareReference) it.next();
                if (csr.getComponentSoftwareName().equals("be-engine")) {
                    // Match!
                    return true;
                }//if
            }//for
        } catch (Exception ignored) {}
        return false;
    }


}//class
