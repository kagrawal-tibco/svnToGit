package com.tibco.be.util.packaging.descriptors;

import java.io.OutputStream;
import java.util.Date;


public interface MutableServiceArchive
        extends ServiceArchive {


    void addDeploymentDescriptor(DeploymentDescriptor deploymentDescriptor);

    void removeDeploymentDescriptor(DeploymentDescriptor deploymentDescriptor);

    void save(OutputStream outputStream) throws Exception;

    void setCreationDate(Date date);

    void setDescription(String description);

    void setName(String name);

    void setOwner(String owner);

//    void setServiceInstanceVars(String name, NameValuePairs nameValuePairs);

    void setVersion(String version);

    void setApplicationArchive(ApplicationArchive applicationarchive);

    void setLastModificationDate(Date date);
}