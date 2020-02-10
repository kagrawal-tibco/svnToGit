package com.tibco.cep.designtime.model.rule.mutable.impl;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.cep.designtime.model.BEModelBundle;
import com.tibco.cep.designtime.model.ModelError;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableArrayList;
import com.tibco.cep.designtime.model.rule.DomainEntry;
import com.tibco.cep.designtime.model.rule.Symbol;
import com.tibco.cep.designtime.model.rule.mutable.MutableDomain;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;


public class DefaultMutableDomain extends DefaultMutableArrayList implements MutableDomain {


    private static final ExpandedName XNAME_DOMAIN_ENTRIES = ExpandedName.makeName("entries");
    public static final ExpandedName XNAME_DOMAIN_ENTRY = ExpandedName.makeName("entry");

    private static final String BUNDLE_ERROR_BAD_ENTRY_TYPE = "DefaultMutableDomain.getModelErrors.badEntryType";
    private static final String BUNDLE_ERROR_DUPLICATE_ENTRY_NAME = "DefaultMutableDomain.getModelErrors.duplicateEntryName";
    private static final String BUNDLE_ERROR_CONFLICTING_ENTRY_VALUES = "DefaultMutableDomain.getModelErrors.conflictingEntryValues";

    private Symbol parent;

    protected long id;


    public DefaultMutableDomain(Symbol parent) {
        this.parent = parent;
    }


    public DomainEntry get(String domainEntryName) {
        if (null != domainEntryName) {
            for (Iterator it = this.iterator(); it.hasNext();) {
                final DomainEntry entry = (DomainEntry) it.next();
                if (domainEntryName.equals(entry.getName())) {
                    return entry;
                }
            }//for
        }//if
        return null;
    }


    public List getModelErrors() {
        final List errors = new ArrayList();
        for (Iterator it = this.iterator(); it.hasNext();) {
            final DomainEntry entry = (DomainEntry) it.next();
            errors.addAll(this.getModelErrors(entry));
        }
        return errors;
    }


    public List getModelErrors(DomainEntry entry) {
        if (null == entry) {
            return new ArrayList(0);
        }
        final List errors = entry.getModelErrors();
        final String entryName = entry.getName();
        final Object entryValue = entry.getValue();
        final BEModelBundle bundle = BEModelBundle.getBundle();

        if ((null != this.parent) && !this.checkType(entry)) {
            errors.add(new ModelError(entry,
                    bundle.formatString(BUNDLE_ERROR_BAD_ENTRY_TYPE, entryName, entryValue, this.parent.getType())));
        }
        for (Iterator it = this.iterator(); it.hasNext();) {
            final DomainEntry otherEntry = (DomainEntry) it.next();
            if (otherEntry != entry) {
                final String otherEntryName = otherEntry.getName();
                final Object otherEntryValue = otherEntry.getValue();
                if (entryName.equals(otherEntryName)) {
                    errors.add(new ModelError(entry,
                            bundle.formatString(BUNDLE_ERROR_DUPLICATE_ENTRY_NAME, entryName)));
                }
                if (!this.checkValues(entry, otherEntry)) {
                    errors.add(new ModelError(entry,
                            bundle.formatString(BUNDLE_ERROR_CONFLICTING_ENTRY_VALUES, entryName, entryValue,
                                    otherEntryName, otherEntryValue)));
                    errors.add(new ModelError(entry, "Duplicate domain entry name: '" + entryName + "'"));
                }
            }//if
        }//for
        return errors;
    }


    protected boolean checkType(DomainEntry entry) {
        if ((null != entry) && (null != this.parent)) {
            final String symbolType = this.parent.getType();
            final Object entryValue = entry.getValue();

            if (RDFTypes.BOOLEAN.getName().equalsIgnoreCase(symbolType)) {
                return (entryValue instanceof String)
                        && (Boolean.TRUE.toString().equals(entryValue) || Boolean.FALSE.toString().equals(entryValue));
            }
        }
        //TODO
        return true;
    }


    protected boolean checkValues(DomainEntry entry1, DomainEntry e2) {
        //TODO
        return true;
    }


    public Symbol getParent() {
        return this.parent;
    }


    public void load(XiNode domainNode) {
        if (null != domainNode) {
            final XiNode entriesNode = XiChild.getChild(domainNode, XNAME_DOMAIN_ENTRIES);
            if (null != entriesNode) {
                for (Iterator it = XiChild.getIterator(entriesNode, XNAME_DOMAIN_ENTRY); it.hasNext();) {
                    final XiNode entryNode = (XiNode) it.next();
                    this.add(DefaultMutableDomainEntry.fromXiNode(entryNode, this));
                }//for
            }//if
        }//if
    }


    public String toString() {
        final StringBuffer result = new StringBuffer();
        boolean first = true;
        for (Iterator it = this.iterator(); it.hasNext();) {
            final DomainEntry entry = (DomainEntry) it.next();
            if (first) {
                first = false;
            } else {
                result.append(",");
            }
            result.append(entry.getName());
        }
        return result.toString();
    }


    public XiNode toXiNode(XiFactory factory, ExpandedName name) {
        final XiNode root = factory.createElement(name);
        final XiNode entries = root.appendElement(XNAME_DOMAIN_ENTRIES);
        for (Iterator it = this.iterator(); it.hasNext();) {
            final DefaultMutableDomainEntry entry = (DefaultMutableDomainEntry) it.next();
            entries.appendChild(entry.toXiNode(factory, XNAME_DOMAIN_ENTRY));
        }
        return root;
    }


}//class
