package com.tibco.be.util.packaging.descriptors;
/*
 * User: nprade
 * Date: Feb 4, 2010
 * Time: 3:37:35 PM
 */


public interface DeploymentDescriptor
        extends XmlSerializable, Comparable {


    String getAdministratorComponentClassName();

    String getDescription();

    String getName();

    Object getRuntime();

    boolean isConfigurableAtDeployment();

    boolean isRequiresConfiguration();

    public void onAdd(MutableServiceArchive msa);

    void setConfigurableAtDeployment(boolean flag);

    void setDescription(String s);

    void setRequiresConfiguration(boolean requiresConfiguration);

    String getDDFactoryClassName();
}