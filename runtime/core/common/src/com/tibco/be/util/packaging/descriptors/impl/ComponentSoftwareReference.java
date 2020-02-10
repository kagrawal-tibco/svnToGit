package com.tibco.be.util.packaging.descriptors.impl;
/*
 * User: nprade
 * Date: Feb 4, 2010
 * Time: 7:29:17 PM
 */


import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.tibco.be.util.packaging.Constants;
import com.tibco.be.util.packaging.VersionNumber;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.XmlAtomicValueCastException;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;


public class ComponentSoftwareReference
        extends AbstractDeploymentDescriptor{

    private ArrayList<String> keywords;
    private long minimumSoftwareVersion, minimumTraVersion;
    private String configVersion;
    private String softwareName;


    public ComponentSoftwareReference() {
        super();
        super.setConfigurableAtDeployment(false);
        this.minimumSoftwareVersion = 0L;
        this.minimumTraVersion = 0L;
        this.configVersion = null;
        this.keywords = new ArrayList<String>();
    }


    public ComponentSoftwareReference(
            String softwareName,
            long minimumSoftwareVersion,
            List<String> keywords) {
        this(softwareName, minimumSoftwareVersion, 0L, keywords);
    }


    public ComponentSoftwareReference(
            String softwareName,
            String minimumSoftwareVersion,
            List<String> keywords) {
        this(softwareName, VersionNumber.getVersionNumber(minimumSoftwareVersion), keywords);
    }


    public ComponentSoftwareReference(
            String softwareName,
            long minimumSoftwareVersion,
            long minimumTraVersion,
            List<String> keywords) {
        super(softwareName);
        super.setConfigurableAtDeployment(false);
        this.configVersion = null;
        this.minimumSoftwareVersion = minimumSoftwareVersion;
        this.minimumTraVersion = minimumTraVersion;
        this.softwareName = softwareName;
        this.keywords = new ArrayList<String>();
        if (keywords != null) {
            this.keywords.addAll(keywords);
            for (final String keyword : keywords) {
                if (keyword.indexOf(" ") != -1) {
                    throw new IllegalArgumentException("Cannot have a space character in a keyword.");
                }
            }
        }
    }


    public ComponentSoftwareReference(
            String softwareName,
            String minimumSoftwareVersion,
            String minimumTraVersion,
            List<String> keywords) {
        this(softwareName, VersionNumber.getVersionNumber(minimumSoftwareVersion),
                VersionNumber.getVersionNumber(minimumTraVersion), keywords);
    }


    public void fromXiNode(XiNode node)
            throws XmlAtomicValueCastException {
        super.fromXiNode(node);

        for (final Iterator children = node.getChildren(); children.hasNext(); ) {
            final XiNode child = (XiNode) children.next();
            final ExpandedName name = child.getName();
            if (Constants.DD.XNames.COMPONENT_SOFTWARE_NAME.equals(name)) {
                this.softwareName = child.getStringValue();
                this.name = this.softwareName;
            }
            if (Constants.DD.XNames.MINIMUM_COMPONENT_SOFTWARE_VERSION.equals(name)) {
                this.minimumSoftwareVersion = VersionNumber.getVersionNumber(child.getStringValue());
            }
            if (Constants.DD.XNames.MINIMUM_TRA_VERSION.equals(name)) {
                this.minimumTraVersion = VersionNumber.getVersionNumber(child.getStringValue());
            }
            if (Constants.DD.XNames.MINIMUM_COMPONENT_SOFTWARE_VERSION_NUMBER.equals(name)) {
                this.minimumSoftwareVersion = child.getTypedValue().getAtom(0).castAsLong();
            }
            if (Constants.DD.XNames.CONFIG_VERSION.equals(name)) {
                this.setConfigVersion(child.getStringValue());
            }
            if (Constants.DD.XNames.KEYWORD.equals(name)) {
                this.keywords.add(child.getStringValue());
            }
        }
    }


    public String getComponentSoftwareName() {
        return this.softwareName;
    }


    public long getConfigVersionNumber() {
        if (!((null == this.configVersion) || this.configVersion.isEmpty())) {
            return VersionNumber.getVersionNumber(this.configVersion);
        } else {
            return this.getMinimumComponentSoftwareVersionNumber();
        }
    }


    @Override
    public String getDDFactoryClassName() {
        return "com.tibco.archive.helpers.ComponentSoftwareReference";
    }


    public List<String> getKeywords() {
        return Collections.unmodifiableList(this.keywords);
    }


    public long getMinimumComponentSoftwareVersionNumber() {
        return this.minimumSoftwareVersion;
    }


    public long getMinimumTRAVersionNumber() {
        return this.minimumTraVersion;
    }


    public ExpandedName getTypeXName() {
        return Constants.DD.XNames.COMPONENT_SOFTWARE_REFERENCE;
    }


    public void setConfigVersion(String s) {
        VersionNumber.getVersionNumber(s);
        this.configVersion = s;
    }


    public String toString() {
        final StringBuffer stringbuffer = new StringBuffer();
        boolean notEmpty = (this.getComponentSoftwareName() != null);
        if (notEmpty) {
            stringbuffer.append(this.getComponentSoftwareName());
        }
        if (this.getMinimumComponentSoftwareVersionNumber() > 0L) {
            if (notEmpty) {
                stringbuffer.append(':');
            }
            stringbuffer.append(VersionNumber.getVersionNumber(this.getMinimumComponentSoftwareVersionNumber()));
            notEmpty = true;
        }
        if (this.getKeywords().size() > 0) {
            if (notEmpty) {
                stringbuffer.append(':');
            }
            stringbuffer.append(this.getKeywords());
        }
        return stringbuffer.toString();
    }


    public XiNode toXiNode(XiFactory factory) {
        final XiNode rootNode = factory.createElement(this.getTypeXName());
        String s;
        XiNode node;

        s = this.getComponentSoftwareName();
        if (null != s) {
            node = factory.createElement(Constants.DD.XNames.COMPONENT_SOFTWARE_NAME);
            node.setStringValue(s);
            rootNode.appendChild(node);
        }

        node = factory.createElement(Constants.DD.XNames.MINIMUM_COMPONENT_SOFTWARE_VERSION);
        node.setStringValue(VersionNumber.getVersionNumber(this.getMinimumComponentSoftwareVersionNumber()));
        rootNode.appendChild(node);

        node = factory.createElement(Constants.DD.XNames.MINIMUM_TRA_VERSION);
        node.setStringValue(VersionNumber.getVersionNumber(this.getMinimumTRAVersionNumber()));
        rootNode.appendChild(node);

        final long configVersion = this.getConfigVersionNumber();
        if (configVersion != 0L) {
            node = factory.createElement(Constants.DD.XNames.CONFIG_VERSION);
            node.setStringValue(VersionNumber.getVersionNumber(getConfigVersionNumber()));
            rootNode.appendChild(node);
        }

        for (final String keyword : this.keywords) {
            node = factory.createElement(Constants.DD.XNames.KEYWORD);
            node.setStringValue(keyword);
            rootNode.appendChild(node);
        }

        return rootNode;
    }


}
