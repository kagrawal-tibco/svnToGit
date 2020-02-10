package com.tibco.be.util.packaging.descriptors.impl;
/*
 * User: nprade
 * Date: Feb 4, 2010
 * Time: 3:50:56 PM
 */

import java.util.Iterator;

import com.tibco.be.util.packaging.Constants.DD;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.XmlAtomicValueCastException;
import com.tibco.xml.data.primitive.values.XsInt;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;


public class NameValuePairInteger
        extends NameValuePairImpl {


    private boolean isMaxInclusiveSet, isMinInclusiveSet;
    private Integer maxInclusive, minInclusive;


    public NameValuePairInteger() {
        super();
        this.init();
    }


    public NameValuePairInteger(String name) {
        super(name);
        this.init();
    }


    public NameValuePairInteger(String name, int value) {
        super(name, String.valueOf(value));
        this.init();
    }


    public NameValuePairInteger(String name, int value, String description, boolean requiresConfiguration) {/*  */
        super(name, String.valueOf(value), description, requiresConfiguration);
        this.init();
    }


    @Override
    public void fromXiNode(XiNode node)
            throws XmlAtomicValueCastException {
        super.fromXiNode(node);
        for (final Iterator children = node.getChildren(); children.hasNext();) {
            final XiNode child = (XiNode) children.next();
            if (DD.XNames.MAX_INCLUSIVE.equals(child.getName())) {
                this.setMaxInclusive(child.getTypedValue().getAtom(0).castAsInt());
            }
            if (DD.XNames.MIN_INCLUSIVE.equals(child.getName())) {
                this.setMinInclusive(child.getTypedValue().getAtom(0).castAsInt());
            }
        }
    }


    public Integer getMaxInclusive() {
        return this.maxInclusive;
    }


    public Integer getMinInclusive() {
        return this.minInclusive;
    }


    @Override
    public ExpandedName getTypeXName() {
        return DD.XNames.NAME_VALUE_PAIR_INTEGER;
    }


    private void init() {
        this.isMaxInclusiveSet = false;
        this.isMinInclusiveSet = false;
        this.maxInclusive = null;
        this.minInclusive = null;                 
    }

    
    public void setMaxInclusive(Integer maxInclusive) {
        this.isMaxInclusiveSet = (maxInclusive != null);
        this.maxInclusive = maxInclusive;
    }


    public void setMinInclusive(Integer minInclusive) {
        this.isMinInclusiveSet = (minInclusive != null);
        this.minInclusive = minInclusive;
    }


    public void setValue(int value) {
        super.setValue(String.valueOf(value));
    }


    @Override
    public void setValue(String value) {
        this.setValue(Integer.valueOf(value));
    }


    @Override
    public XiNode toXiNode(XiFactory factory) {
        final XiNode rootNode = super.toXiNode(factory);
        if (this.isMinInclusiveSet) {
            final XiNode node = factory.createElement(DD.XNames.MIN_INCLUSIVE);
            node.setTypedValue(new XsInt(this.minInclusive));
            rootNode.appendChild(node);
        }
        if (this.isMaxInclusiveSet) {
            final XiNode node = factory.createElement(DD.XNames.MAX_INCLUSIVE);
            node.setTypedValue(new XsInt(this.maxInclusive));
            rootNode.appendChild(node);
        }
        return rootNode;
    }


}