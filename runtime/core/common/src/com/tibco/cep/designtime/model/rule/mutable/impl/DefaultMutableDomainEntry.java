package com.tibco.cep.designtime.model.rule.mutable.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tibco.cep.designtime.model.BEModelBundle;
import com.tibco.cep.designtime.model.Interval;
import com.tibco.cep.designtime.model.ModelError;
import com.tibco.cep.designtime.model.MutationContext;
import com.tibco.cep.designtime.model.MutationObservable;
import com.tibco.cep.designtime.model.MutationObserver;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableInterval;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutationContext;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutationNotificationTransmitter;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutationObservable;
import com.tibco.cep.designtime.model.rule.Domain;
import com.tibco.cep.designtime.model.rule.mutable.MutableDomainEntry;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;


public class DefaultMutableDomainEntry implements MutableDomainEntry {


    private static final ExpandedName XNAME_NAME = ExpandedName.makeName("name");
    private static final ExpandedName XNAME_VALUE = ExpandedName.makeName("value");
    private static final ExpandedName XNAME_VALUE_SINGLE = ExpandedName.makeName("single");
    private static final ExpandedName XNAME_VALUE_INTERVAL = ExpandedName.makeName("interval");

    private static final String BUNDLE_ERROR_NO_NAME = "DefaultMutableDomainEntry.getModelErrors.noName";
    private static final String BUNDLE_ERROR_NO_VALUE = "DefaultMutableDomainEntry.getModelErrors.noValue";
    private static final String BUNDLE_ERROR_BAD_ENTRY_TYPE = "DefaultMutableDomainEntry.getModelErrors.badEntryType";

    private DefaultMutableDomain parent;
    private String name;
    private Object value;
    private MutationObservable mutationObservable;
    private MutationObserver mutationObserver;


    public DefaultMutableDomainEntry(DefaultMutableDomain parent, String name, Interval value) {
        this.parent = parent;
        this.name = name;
        this.value = value;
        this.mutationObservable = new DefaultMutationObservable(this);
        this.mutationObserver = new DefaultMutationNotificationTransmitter(this.mutationObservable);
    }


    public DefaultMutableDomainEntry(DefaultMutableDomain parent, String name, String value) {
        this.parent = parent;
        this.name = name;
        this.value = value;
        this.mutationObservable = new DefaultMutationObservable(this);
        this.mutationObserver = new DefaultMutationNotificationTransmitter(this.mutationObservable);
    }


    public MutationObservable getMutationObservable() {
        return this.mutationObservable;
    }


    public List getModelErrors() {
        final List errors = new ArrayList();
        final BEModelBundle bundle = BEModelBundle.getBundle();
        if ((null == name) || "".equals(name)) {
            errors.add(new ModelError(this, bundle.getString(BUNDLE_ERROR_NO_NAME)));
        }
        if (null == value) {
            errors.add(new ModelError(this, bundle.formatString(BUNDLE_ERROR_NO_VALUE, this.name)));
        }
        return errors;
    }


    public String getName() {
        return this.name;
    }


    public Domain getParent() {
        return this.parent;
    }


    public Object getValue() {
        return this.value;
    }


    public Interval getValueAsInterval() {
        if ((null == this.value) || !(this.value instanceof Interval)) {
            return null;
        }
        return (Interval) this.value;
    }


    public String getValueAsSingle() {
        if ((null == this.value) || (this.value instanceof Interval)) {
            return null;
        }
        return (String) this.value;
    }


    public void setName(String name) {
        final Map changes = new HashMap();
        changes.put("name", this.name);

        this.name = name;

        this.mutationObservable.changeAndNotify(new DefaultMutationContext(MutationContext.RENAME, this, changes));
    }


    public void setValue(String value) {
        final Map changes = new HashMap();
        changes.put("value", this.value);

        this.value = value;

        this.mutationObservable.changeAndNotify(new DefaultMutationContext(MutationContext.MODIFY, this, changes));
    }


    public void setValue(Interval value) {
        final Map changes = new HashMap();
        changes.put("value", this.value);

        this.value = value;

        this.mutationObserver.startObserving(value);
        this.mutationObservable.changeAndNotify(new DefaultMutationContext(MutationContext.MODIFY, this, changes));
    }


    public XiNode toXiNode(XiFactory factory, ExpandedName name) {
        final XiNode root = factory.createElement(name);
        XiChild.appendString(root, XNAME_NAME, this.getName());
        final XiNode valueNode = root.appendElement(XNAME_VALUE);
        if (this.value instanceof String) {
            XiChild.appendString(valueNode, XNAME_VALUE_SINGLE, (String) this.value);
        } else if (this.value instanceof DefaultMutableInterval) {
            valueNode.appendChild(((DefaultMutableInterval) this.value).toXiNode(factory, XNAME_VALUE_INTERVAL));
        }
        return root;
    }


    public static DefaultMutableDomainEntry fromXiNode(XiNode domainEntryNode, DefaultMutableDomain parent) {
        final String name = XiChild.getString(domainEntryNode, XNAME_NAME);
        final XiNode valueNode = XiChild.getChild(domainEntryNode, XNAME_VALUE);

        XiNode node = XiChild.getChild(valueNode, XNAME_VALUE_SINGLE);
        if (null != node) {
            return new DefaultMutableDomainEntry(parent, name, node.getStringValue());
        }

        node = XiChild.getChild(valueNode, XNAME_VALUE_INTERVAL);
        if (null != node) {
            final Interval interval = DefaultMutableInterval.createDefaultInterval(node);
            return new DefaultMutableDomainEntry(parent, name, interval);
        }

        return null;
    }
}
