package com.tibco.cep.loadbalancer.impl.server;

import static com.tibco.cep.util.Helper.$logger;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.tibco.cep.common.exception.LifecycleException;
import com.tibco.cep.common.exception.RecoveryException;
import com.tibco.cep.common.resource.Id;
import com.tibco.cep.common.resource.ResourceProvider;
import com.tibco.cep.loadbalancer.impl.server.core.DefaultKernel;
import com.tibco.cep.loadbalancer.impl.server.membership.ServerMembershipChangeListener;
import com.tibco.cep.loadbalancer.membership.MembershipChangeProvider;
import com.tibco.cep.loadbalancer.server.Server;
import com.tibco.cep.util.annotation.LogCategory;

/*
* Author: Ashwin Jayaprakash / Date: Mar 17, 2010 / Time: 3:33:53 PM
*/

@LogCategory("loadbalancer.core.server")
public class DefaultServer implements Server {
    protected DefaultKernel kernel;

    protected ServerMembershipChangeListener membershipChangeListener;

    protected MembershipChangeProvider membershipChangeProvider;

    protected ResourceProvider resourceProvider;

    protected Logger logger;

    protected Id resourceId;

    public DefaultServer() {
    }

    @Override
    public DefaultKernel getKernel() {
        return kernel;
    }

    public void setKernel(DefaultKernel kernel) {
        this.kernel = kernel;
    }

    @Override
    public MembershipChangeProvider getMembershipChangeProvider() {
        return membershipChangeProvider;
    }

    public void setMembershipChangeProvider(MembershipChangeProvider membershipChangeProvider) {
        this.membershipChangeProvider = membershipChangeProvider;
    }

    @Override
    public ResourceProvider getResourceProvider() {
        return resourceProvider;
    }

    public void setResourceProvider(ResourceProvider resourceProvider) {
        this.resourceProvider = resourceProvider;
    }

    public void setResourceId(Id resourceId) {
        this.resourceId = resourceId;
    }

    @Override
    public Id getResourceId() {
        return resourceId;
    }

    @Override
    public void start() throws LifecycleException {
        logger = (logger == null) ? $logger(resourceProvider, getClass()) : logger;

        logger.log(Level.INFO, String.format("Server [%s] starting", getResourceId()));

        membershipChangeListener = new ServerMembershipChangeListener();
        //Hmmm..cyclic relationship.
        membershipChangeListener.setKernel(kernel);
        try {
            kernel.start();
        }
        catch (Exception e) {
            throw new LifecycleException(e);
        }

        //------------------

        try {
            membershipChangeListener.start();
        }
        catch (Exception e) {
            try {
                kernel.stop();
            }
            catch (Exception e1) {
                logger.log(Level.WARNING,
                        String.format("Error occurred while stopping the kernel [%s]", kernel.getId()), e);
            }

            throw new LifecycleException(e);
        }

        //------------------

        try {
            membershipChangeProvider.start(resourceProvider, membershipChangeListener);
        }
        catch (Exception e) {
            try {
                kernel.stop();
            }
            catch (Exception e1) {
                logger.log(Level.WARNING,
                        String.format("Error occurred while stopping the kernel [%s]", kernel.getId()), e);
            }

            try {
                membershipChangeProvider.stop();
            }
            catch (Exception e1) {
                logger.log(Level.WARNING,
                        String.format("Error occurred while stopping the membership change provider [%s]",
                                membershipChangeProvider.getId()), e);
            }

            throw new LifecycleException(e);
        }

        logger.log(Level.INFO, String.format("Server [%s] started", getResourceId()));
    }

    @Override
    public void stop() throws LifecycleException {
        logger.log(Level.INFO, String.format("Server [%s] stopping", getResourceId()));

        try {
            membershipChangeProvider.stop();
        }
        catch (Exception e) {
            logger.log(Level.WARNING,
                    String.format("Error occurred while stopping the membership change provider [%s]",
                            membershipChangeProvider.getId()), e);
        }

        try {
            membershipChangeListener.stop();
        }
        catch (Exception e) {
            logger.log(Level.WARNING,
                    String.format("Error occurred while stopping the membership change listener for server [%s]",
                            getResourceId()), e);
        }

        try {
            kernel.stop();
        }
        catch (Exception e) {
            logger.log(Level.WARNING, String.format("Error occurred while stopping the kernel [%s]", kernel.getId()),
                    e);
        }

        logger.log(Level.INFO, String.format("Server [%s] stopped", getResourceId()));
    }

    @Override
    public DefaultServer recover(ResourceProvider resourceProvider, Object... params) throws RecoveryException {
        return this;
    }
}
