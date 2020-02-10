package com.tibco.be.deployment.administrator;

import com.tibco.administrator.EntityAdapter;
import com.tibco.administrator.consoles.containers.ContainerDetailTracingTab;
import com.tibco.pof.admindomain.ServiceContainerBindingRuntimeConfiguration;
import com.tibco.pof.authorization.AuthenticationContext;
import com.tibco.pof.log.Logger;
import com.tibco.sdk.tools.MTrace;


/**
 * Extends ContainerDetailTracingTab by enabling the tab only for BE.
 */
public class BEServiceInstanceTracingTab extends ContainerDetailTracingTab {


    /**
     * Enables the "Tracing" tab for BE Service Instances.
     *
     * @param context the AuthenticationContext
     * @param entity  the EntityAdapter
     * @return true iif this tab is available, i.e. iif the current Service Instance is a BE engine.
     */
    public boolean isAvailable(AuthenticationContext context, EntityAdapter entity) {
        try {
            final Object o = entity.getEntity();
            if (o instanceof ServiceContainerBindingRuntimeConfiguration) {
                final String softwareName = ((ServiceContainerBindingRuntimeConfiguration) o)
                        .getContainerRuntimeConfiguration()
                        .getOSProcess()
                        .getContainerSoftware()
                        .getName();
                return "be-engine".equals(softwareName);
            }//if
            // No match, so this tab is not available.
            return false;

        } catch (Exception e) {
            Logger.getLogger().debug(MTrace.ERROR, "Administrator", Logger.getExceptionStackTrace(e), null);
            return false;
        }//catch
    }//isAvailable


}//class

