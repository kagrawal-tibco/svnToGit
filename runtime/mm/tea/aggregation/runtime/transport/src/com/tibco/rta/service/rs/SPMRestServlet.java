package com.tibco.rta.service.rs;

import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.service.cluster.GroupMembershipService;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;

import javax.servlet.ServletException;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 3/2/14
 * Time: 12:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class SPMRestServlet extends HttpServletDispatcher {

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            GroupMembershipService groupMembershipService = ServiceProviderManager.getInstance().getGroupMembershipService();
            groupMembershipService.signalPrimary();
        } catch (Exception e) {
            log("", e);
        }
    }
}
