package com.tibco.cep.loadbalancer.impl.membership.file;

import static com.tibco.cep.util.Helper.$configProperty;
import static com.tibco.cep.util.Helper.$eval;
import static com.tibco.cep.util.Helper.$logger;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.tibco.cep.common.exception.LifecycleException;
import com.tibco.cep.common.resource.Id;
import com.tibco.cep.common.resource.ResourceProvider;
import com.tibco.cep.loadbalancer.endpoint.Endpoint;
import com.tibco.cep.loadbalancer.endpoint.EndpointContainer;
import com.tibco.cep.loadbalancer.membership.MembershipPublisher;
import com.tibco.cep.loadbalancer.util.Helper;
import com.tibco.cep.util.annotation.LogCategory;

/*
* Author: Ashwin Jayaprakash / Date: Mar 23, 2010 / Time: 3:44:33 PM
*/

@LogCategory("loadbalancer.core.membership.file")
public abstract class AbstractFileMembershipPublisher<E extends Endpoint, C extends EndpointContainer<E>>
        implements MembershipPublisher<E, C> {
    /**
     * {@value}.
     */
    public static final String PROPERTY_PUBLISH_DIR = "${this.id}.file.publishdir";

    protected Id id;

    protected File publishToDirectory;

    protected ResourceProvider resourceProvider;

    protected Logger logger;

    protected File actualFile;

    protected C container;

    public AbstractFileMembershipPublisher() {
    }

    protected AbstractFileMembershipPublisher(Id id) {
        this.id = id;
    }

    @Override
    public void setId(Id id) {
        this.id = id;
    }

    @Override
    public Id getId() {
        return id;
    }

    public ResourceProvider getResourceProvider() {
        return resourceProvider;
    }

    public void setResourceProvider(ResourceProvider resourceProvider) {
        this.resourceProvider = resourceProvider;

        this.logger = $logger(resourceProvider, getClass());
    }

    public File getPublishToDirectory() {
        return publishToDirectory;
    }

    public void setPublishToDirectory(File publishToDirectory) {
        this.publishToDirectory = publishToDirectory;
    }

    public C getContainer() {
        return container;
    }

    public void setContainer(C container) {
        this.container = container;
    }

    private void initActualFileIfRequired() {
        if (publishToDirectory == null) {
            String publishDirFlag = $eval(resourceProvider, PROPERTY_PUBLISH_DIR, "this", this).toString();

            String publishDirString = $configProperty(resourceProvider, publishDirFlag);

            if (publishDirString == null) {
                String msg =
                        String.format("The directory to publish members [%s] must be provided for the publisher [%s]",
                                publishDirFlag, getId());

                throw new IllegalArgumentException(msg);
            }

            publishToDirectory = new File(publishDirString);

            logger.log(Level.INFO,
                    String.format("[%s] started publishing Members to [%s]", getId(),
                            publishToDirectory.getAbsolutePath()));
        }

        if (actualFile == null) {
            String s = Helper.$hashMD5(container.getId());

            actualFile = new File(publishToDirectory, s);

            actualFile.deleteOnExit();
        }
    }

    @Override
    public void publish(C container) throws LifecycleException {
        this.container = container;

        initActualFileIfRequired();

        logger.log(Level.INFO, String.format("[%s] publishing Member [%s] to [%s]", getId(), container.getId(),
                actualFile.getAbsolutePath()));

        try {
            writeToFile(container, actualFile);
        }
        catch (IOException e) {
            String x = String.format("Error occured [%s] while publishing Member [%s] to [%s]", getId(),
                    container.getId(), actualFile.getAbsolutePath());

            throw new LifecycleException(x, e);
        }

        logger.log(Level.INFO, String.format("[%s] published Member [%s] to [%s]", getId(), container.getId(),
                actualFile.getAbsolutePath()));
    }

    @Override
    public void refreshPublication() throws LifecycleException {
        initActualFileIfRequired();

        try {
            writeToFile(container, actualFile);
        }
        catch (IOException e) {
            String x = String.format("Error occured [%s] while refreshing Member [%s] to [%s]", getId(),
                    container.getId(), actualFile.getAbsolutePath());

            throw new LifecycleException(x, e);
        }

        logger.log(Level.INFO,
                String.format("[%s] refreshed publication of Member [%s] to [%s]", getId(), container.getId(),
                        publishToDirectory.getAbsolutePath()));
    }

    protected abstract void writeToFile(C container, File file) throws IOException;

    @Override
    public void unpublish() throws LifecycleException {
        logger.log(Level.INFO, String.format("[%s] unpublishing Member [%s] from [%s]", getId(), container.getId(),
                actualFile.getAbsolutePath()));

        boolean success = false;
        for (int i = 0; i < 10 && success == false; i++) {
            success = actualFile.delete();
        }

        if (success) {
            logger.log(Level.INFO,
                    String.format("[%s] unpublished Member [%s] from [%s]", getId(), container.getId(),
                            actualFile.getAbsolutePath()));
        }
        else {
            throw new LifecycleException(
                    String.format("[%s] was unable to unpublish Member [%s] from [%s]", getId(), container.getId(),
                            actualFile.getAbsolutePath()));
        }
    }
}