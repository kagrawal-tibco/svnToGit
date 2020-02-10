package com.tibco.be.util.packaging.descriptors.impl;
/*
 * User: nprade
 * Date: Feb 4, 2010
 * Time: 4:35:41 PM
 */


import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.tibco.be.util.packaging.Constants;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.XmlAtomicValueCastException;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;


public class StartAsOneOf
        extends AbstractDeploymentDescriptor {


    protected List<ComponentSoftwareReference> componentSoftwareReferences;


    public StartAsOneOf() {
        super(Constants.DD.XNames.START_AS_ONE_OF.localName);
        super.setConfigurableAtDeployment(false);
        this.componentSoftwareReferences = new ArrayList<ComponentSoftwareReference>();
    }


    public void addComponentSoftwareReference(ComponentSoftwareReference csr) {
        this.componentSoftwareReferences.add(csr);
    }


    @Override
    public void fromXiNode(XiNode node)
            throws XmlAtomicValueCastException {
        super.fromXiNode(node);
        for (final Iterator children = node.getChildren(); children.hasNext();) {
            final XiNode child = (XiNode) children.next();
            if (Constants.DD.XNames.COMPONENT_SOFTWARE_REFERENCE.equals(child.getName())) {
                final ComponentSoftwareReference csr = new ComponentSoftwareReference();
                csr.fromXiNode(child);
                this.addComponentSoftwareReference(csr);
            }
        }
    }


    @Override
    public boolean isConfigurableAtDeployment() {
        return false;
    }


    public List<ComponentSoftwareReference> getComponentSoftwareReferences() {
        return Collections.unmodifiableList(this.componentSoftwareReferences);
    }


    @Override
    public String getDDFactoryClassName() {
        return "com.tibco.archive.helpers.StartAsOneOf";
    }


    @Override
    public ExpandedName getTypeXName() {
        return Constants.DD.XNames.START_AS_ONE_OF;
    }


    @Override
    public boolean isRequiresConfiguration() {
        return true;
    }


    public void removeComponentSoftwareReference(ComponentSoftwareReference csr) {
        this.componentSoftwareReferences.remove(csr);
    }


    @Override
    public String toString() {
        return this.getName() + "=" + this.componentSoftwareReferences;
    }


    @Override
    public XiNode toXiNode(XiFactory factory) {
        if (this.componentSoftwareReferences.size() == 0) {
            throw new IllegalArgumentException("At least one ComponentSoftwareReference must be specified.");
        }
        final XiNode node = super.toXiNode(factory);
        for (final ComponentSoftwareReference csr : this.componentSoftwareReferences) {
            node.appendChild(csr.toXiNode(factory));
        }
        return node;
    }


}