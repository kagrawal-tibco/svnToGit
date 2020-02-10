package com.tibco.be.util.packaging.descriptors.impl;

import com.tibco.be.util.packaging.Constants;
import com.tibco.be.util.packaging.descriptors.DeploymentDescriptor;
import com.tibco.be.util.packaging.descriptors.MutableServiceArchive;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;


public class DeploymentDescriptorFactory
        extends AbstractDeploymentDescriptor
        implements DeploymentDescriptor {


    protected String mDDFactoryClassName;
    protected String mDDXsdFileName;


    public DeploymentDescriptorFactory() {
        super(Constants.DD.XNames.DEPLOYMENT_DESCRIPTOR_FACTORY.localName);
        this.mDDXsdFileName = null;
    }


    public DeploymentDescriptorFactory(ExpandedName xName, String mDDFactoryClassName) {
        super(xName.getExpandedForm());
        this.mDDXsdFileName = null;
        this.mDDFactoryClassName = mDDFactoryClassName;
    }



    @Override
    public String getDDFactoryClassName() {
        return this.mDDFactoryClassName;
    }


    public String getDeploymentDescriptorXsdFileName() {
        return this.mDDXsdFileName;
    }


    
    @Override
    public ExpandedName getTypeXName() {
        return Constants.DD.XNames.DEPLOYMENT_DESCRIPTOR_FACTORY;
    }


    @Override
    public void onAdd(MutableServiceArchive mutableservicearchive) throws IllegalArgumentException {
    }


    public void setDeploymentDescriptorXsdFileName(String s) {
        this.mDDXsdFileName = s;
    }


    @Override
    public String toString() {
        return this.getName() + "=" + this.getDDFactoryClassName();
    }


    @Override
    public XiNode toXiNode(XiFactory xifactory) {
        final XiNode rootNode = super.toXiNode(xifactory);

        if (null != this.getDDFactoryClassName()) {
            final XiNode node = xifactory.createElement(Constants.DD.XNames.DEPLOYMENT_DESCRIPTOR_FACTORY_CLASS_NAME);
            node.setStringValue(this.getDDFactoryClassName());
            rootNode.appendChild(node);
        }

        if (null != this.getDeploymentDescriptorXsdFileName()) {
            final XiNode node = xifactory.createElement(Constants.DD.XNames.DEPLOYMENT_DESCRIPTOR_XSD_FILE_NAME);
            node.setStringValue(this.getDeploymentDescriptorXsdFileName());
            rootNode.appendChild(node);
        }

        return rootNode;
    }

}
