package com.tibco.be.util.packaging.descriptors.impl;
/*
 * User: nprade
 * Date: Feb 8, 2010
 * Time: 5:17:47 PM
 */


import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.tibco.be.util.packaging.Constants;
import com.tibco.be.util.packaging.descriptors.ServiceArchive;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.XmlAtomicValueCastException;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;


public class Modules
        extends AbstractDeploymentDescriptor {


    private List<String> pathNames;


    public Modules() {
        super(Constants.DD.XNames.MODULES.localName);
        this.pathNames = new ArrayList<String>();
    }


    public Modules(List<String> pathNames) {
        this();
        this.pathNames.addAll(pathNames);
    }


    public void addModule(ServiceArchive servicearchive) {
        if (!this.pathNames.contains(servicearchive.getFileName())) {
            this.pathNames.add(servicearchive.getFileName());
        }
    }


    @Override
    public void fromXiNode(XiNode node)
            throws XmlAtomicValueCastException {
        super.fromXiNode(node);
        for (final Iterator iterator = node.getChildren(); iterator.hasNext();) {
            final XiNode child = (XiNode) iterator.next();
            if (Constants.DD.XNames.PATH_NAME.equals(child.getName())) {
                this.pathNames.add(child.getStringValue());
            }
        }
    }

    
    @Override
    public String getDDFactoryClassName() {
        return "com.tibco.archive.helpers.Modules";
    }


    public List getPathNames() {
        return Collections.unmodifiableList(this.pathNames);
    }


    @Override
    public ExpandedName getTypeXName() {
        return Constants.DD.XNames.MODULES;
    }


    @Override
    public boolean isConfigurableAtDeployment() {
        return false;
    }


    @Override
    public boolean isRequiresConfiguration() {
        return false;
    }


    public void removeModule(ServiceArchive servicearchive) {
        this.pathNames.remove(servicearchive.getFileName());
    }


    @Override
    public String toString() {
        return this.getName() + "=" + this.getPathNames();
    }


    @Override
    public XiNode toXiNode(XiFactory factory) {
        final XiNode rootNode = super.toXiNode(factory);

        for (final String pathName : this.pathNames) {
            final XiNode node = factory.createElement(Constants.DD.XNames.PATH_NAME);
            node.setStringValue(pathName);
            rootNode.appendChild(node);
        }
        return rootNode;
    }


}