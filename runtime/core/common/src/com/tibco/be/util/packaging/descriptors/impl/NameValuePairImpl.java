package com.tibco.be.util.packaging.descriptors.impl;
/*
 * User: nprade
 * Date: Feb 4, 2010
 * Time: 3:49:13 PM
 */

import java.util.Iterator;

import com.tibco.be.util.packaging.Constants;
import com.tibco.be.util.packaging.descriptors.NameValuePair;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.XmlAtomicValueCastException;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;


public class NameValuePairImpl
        extends AbstractDeploymentDescriptor
        implements NameValuePair {


    protected String value;


    public NameValuePairImpl() {
        super();
    }


    public NameValuePairImpl(String name) {
        super(name);
    }


    public NameValuePairImpl(String name, String value) {
        this(name);
        this.value = value;
    }


    public NameValuePairImpl(String name, String value, String description, boolean deploymentSettable) {
        super(name, description, deploymentSettable);
        this.value = value;
    }


    @Override
    public void fromXiNode(XiNode node)
            throws XmlAtomicValueCastException {
        super.fromXiNode(node);
        for (final Iterator children = node.getChildren(); children.hasNext();) {
            final XiNode child = (XiNode) children.next();
            if (Constants.DD.XNames.VALUE.equals(child.getName())) {
                this.value = child.getStringValue();
            }
        }
    }


    @Override
    public String getDDFactoryClassName() {
        throw new UnsupportedOperationException();
    }


    @Override
    public String getValue() {
        return this.value;
    }


    @Override
    public ExpandedName getTypeXName() {
        return Constants.DD.XNames.NAME_VALUE_PAIR;
    }


    @Override
    public boolean isRequiresConfiguration() {
        return (null != this.getValue())
                || super.isRequiresConfiguration();
    }


//    public void setConstraint(String s)
//            throws NotSupportedException {
//        mEnumeration.clear();
//        if (null == s || s.equals("")) {
//            return;
//        }
//        StringTokenizer stringtokenizer = new StringTokenizer(s, ",");
//        do {
//            if (!stringtokenizer.hasMoreTokens()) {


    //                break;
//            }
//            String s1 = stringtokenizer.nextToken();
//            String s2 = s1.trim();
//            if (!"".equals(s2)) {
//                mEnumeration.add(s2);
//            }
//        } while (true);
//    }
//


    @Override
    public void setValue(String value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return this.name + "=" + this.value;
    }


    @Override
    public XiNode toXiNode(XiFactory factory) {
        final XiNode rootNode = super.toXiNode(factory);

        final String value = this.getValue();
        if (null != value) {
            final XiNode node = factory.createElement(Constants.DD.XNames.VALUE);
            node.setStringValue(value);
            rootNode.appendChild(node);
        }

        return rootNode;
    }

}