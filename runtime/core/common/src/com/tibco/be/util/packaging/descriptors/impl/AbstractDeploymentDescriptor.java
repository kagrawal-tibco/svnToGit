package com.tibco.be.util.packaging.descriptors.impl;

import java.util.Iterator;

import com.tibco.be.util.packaging.Constants;
import com.tibco.be.util.packaging.descriptors.DeploymentDescriptor;
import com.tibco.be.util.packaging.descriptors.MutableServiceArchive;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.XmlAtomicValueCastException;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;


public abstract class AbstractDeploymentDescriptor
        implements DeploymentDescriptor {


    protected String description;
    protected String name;
    protected boolean requiresConfiguration;
    protected boolean configurableAtDeployment;


    public AbstractDeploymentDescriptor() {
        this.configurableAtDeployment = true;
        this.requiresConfiguration = true;
    }


    public AbstractDeploymentDescriptor(String name) {
        this();
        this.name = name;
    }


    public AbstractDeploymentDescriptor(String name, String description, boolean requiresConfiguration) {
        this();
        this.name = name;
        this.description = description;
        this.requiresConfiguration = requiresConfiguration;
    }


    @Override
    public int compareTo(Object obj) {
        if (!(obj instanceof DeploymentDescriptor)) {
            throw new ClassCastException("Cannot compare class " + obj.getClass().getName()
                    + " to a DeploymentDescriptor.");
        } else {
            return this.getName().compareTo(((DeploymentDescriptor) obj).getName());
        }
    }


    @Override
    public void fromXiNode(XiNode node)
            throws XmlAtomicValueCastException {
        if (node == null) {
            throw new IllegalArgumentException("A node must be specified.");
        }
        for (final Iterator children = node.getChildren(); children.hasNext();) {
            final XiNode child = (XiNode) children.next();
            final ExpandedName name = child.getName();
            if (Constants.DD.XNames.NAME.equals(name)) {
                this.name = child.getStringValue();
            } else if (Constants.DD.XNames.DESCRIPTION.equals(name)) {
                this.description = child.getStringValue();
            } else if (Constants.DD.XNames.REQUIRES_CONFIGURATION.equals(name)) {
                this.requiresConfiguration = Boolean.valueOf(child.getStringValue());
            } else if (Constants.DD.XNames.CONFIGURABLE_AT_DEPLOYMENT.equals(name)) {
                this.configurableAtDeployment = Boolean.valueOf(child.getStringValue());
            }
        }
    }


    @Override
    public String getAdministratorComponentClassName() {
        return null;
    }


    @Override
    public String getDescription() {
        return this.description;
    }


    @Override
    public String getName() {
        return this.name;
    }


    @Override
    public Object getRuntime() {
        throw new UnsupportedOperationException();
    }


    @Override
    public boolean isConfigurableAtDeployment() {
        return this.configurableAtDeployment;
    }


    @Override
    public boolean isRequiresConfiguration() {
        return this.requiresConfiguration;
    }


    public void onAdd(MutableServiceArchive msa)
            throws IllegalArgumentException {

//        mServiceArchive = mutableservicearchive;
        if (null == msa) {
            throw new IllegalArgumentException("A ServiceArchive must be specified.");
        }

        final ExpandedName typeXName = this.getTypeXName();
        for (final DeploymentDescriptor deploymentdescriptor : msa.getDeploymentDescriptors(typeXName)) {
            final String ddName = deploymentdescriptor.getName();
            final String thisName = this.getName();
            if (       ((null == thisName) && (null == ddName))
                    || ((null != thisName) && thisName.equals(ddName))) {
                throw new IllegalArgumentException("Service archive " + msa.getName()
                        + " already has a deployment descriptor of the same name " + thisName
                        + " and type " + typeXName);
            }
        }

        DeploymentDescriptorFactory ddf = (DeploymentDescriptorFactory) msa.getDeploymentDescriptorByName(
                        Constants.DD.XNames.DEPLOYMENT_DESCRIPTOR_FACTORY, typeXName.getExpandedForm());
        if (null == ddf) {
            ddf = new DeploymentDescriptorFactory(typeXName, this.getDDFactoryClassName());
//            final String xsdFileNameStem = this.getXsdFileNameStem();
//            if (null != xsdFileNameStem) {
//                ddf.setDeploymentDescriptorXsdFileName(xsdFileNameStem + ".xsd");
//            }
            msa.addDeploymentDescriptor(ddf);
        }
    }


    @Override
    public void setConfigurableAtDeployment(boolean configurableAtDeployment) {
        this.configurableAtDeployment = configurableAtDeployment;
    }


    @Override
    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public void setRequiresConfiguration(boolean requiresConfiguration) {
        this.requiresConfiguration = requiresConfiguration;
    }

    @Override
    public XiNode toXiNode(XiFactory factory) {
        if (null == factory) {
            throw new IllegalArgumentException("A factory must be specified.");
        }

        final XiNode rootNode = factory.createElement(this.getTypeXName());
        XiNode node;
        String s;

        s = this.name;
        if ((null == s)) {
            //throw new IllegalArgumentException("No name specified..");
        	s=""; //setting an empty string to XiNode Name info for no name specified. - BE-24235
        }
        node = factory.createElement(Constants.DD.XNames.NAME);
        node.setStringValue(s);
        rootNode.appendChild(node);

        s = this.getDescription();
        if (!((null == s) || s.isEmpty())) {
            node = factory.createElement(Constants.DD.XNames.DESCRIPTION);
            node.setStringValue(s);
            rootNode.appendChild(node);
        }

        node = factory.createElement(Constants.DD.XNames.REQUIRES_CONFIGURATION);
        node.setStringValue(String.valueOf(this.requiresConfiguration));
        rootNode.appendChild(node);

        if (!this.isConfigurableAtDeployment()) {
            node = factory.createElement(Constants.DD.XNames.DISABLE_CONFIGURE_AT_DEPLOYMENT);
            node.setStringValue(String.valueOf(Boolean.TRUE));
            rootNode.appendChild(node);
        }

        return rootNode;
    }
}
