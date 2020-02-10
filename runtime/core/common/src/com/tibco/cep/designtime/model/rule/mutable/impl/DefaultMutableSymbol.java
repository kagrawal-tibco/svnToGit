package com.tibco.cep.designtime.model.rule.mutable.impl;


import java.util.HashMap;
import java.util.Map;

import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.cep.designtime.model.ModelUtils;
import com.tibco.cep.designtime.model.MutationContext;
import com.tibco.cep.designtime.model.MutationObservable;
import com.tibco.cep.designtime.model.MutationObserver;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutationContext;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutationNotificationTransmitter;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutationObservable;
import com.tibco.cep.designtime.model.rule.Domain;
import com.tibco.cep.designtime.model.rule.Symbol;
import com.tibco.cep.designtime.model.rule.Symbols;
import com.tibco.cep.designtime.model.rule.mutable.MutableDomain;
import com.tibco.cep.designtime.model.rule.mutable.MutableSymbol;


/**
 *
 */
public class DefaultMutableSymbol implements MutableSymbol {


    private String idName;
    private String type;
    private boolean array;
    private MutableDomain domain;
    private DefaultMutableSymbols parent;
    private MutationObservable mutationObservable;
    private MutationObserver mutationObserver;


    public DefaultMutableSymbol(DefaultMutableSymbols parent, String idname, String typePath, boolean array) {
    	this(parent, idname, typePath, null, array);
    }
    
    public DefaultMutableSymbol(DefaultMutableSymbols parent, String idname, String typePath) {
        this(parent, idname, typePath, null);
    }


    public DefaultMutableSymbol(DefaultMutableSymbols parent, String idname, String typePath, MutableDomain domain) {
    	this(parent, idname, typePath, domain, false);
    }
    
    public DefaultMutableSymbol(DefaultMutableSymbols parent, String idname, String typePath, MutableDomain domain, boolean array) {
        this.parent = parent;
        this.idName = idname;
        this.type = typePath;
        this.array = array;
        if (null == domain) {
            this.domain = new DefaultMutableDomain(this);
        } else {
            this.domain = domain;
        }
        this.mutationObservable = new DefaultMutationObservable(this);
        this.mutationObserver = new DefaultMutationNotificationTransmitter(this.mutationObservable);
        this.mutationObserver.startObserving(this.domain);
    }


    public Domain getDomain() {
        return this.domain;
    }


    public MutationObservable getMutationObservable() {
        return this.mutationObservable;
    }


    public String getName() {
        return this.idName;
    }


    public Symbols getSymbols() {
        return this.parent;
    }


    public String getType() {
        if (null == this.type) { return ""; }
        int indexOfDot = this.type.indexOf(".");
        if (indexOfDot == -1) return this.type;
        return this.type.substring(0, indexOfDot);
    }


    public String getTypeExtension() {
        if (null == this.type) { return ""; }
        int indexOfDot = this.type.lastIndexOf(".");
        if (indexOfDot == -1) {
            return "";
        }
        return this.type.substring(indexOfDot);
    }


    public boolean setName(String name) {
        if (ModelUtils.IsEmptyString(name) || !ModelNameUtil.isValidIdentifier(name)) {
            return false; // Invalid name.
        }
        if (this.idName.equals(name)) {
            // Same name, nothing to do.
        } else if (null == this.parent) {
            this.idName = name; // No parent => no conflict.
        } else {
            final Symbol existingEntry = (Symbol) this.parent.get(name);
            if ((null != existingEntry) && (this != existingEntry)) {
                return false; // Another symbol already exists with that name in the parent => conflict.
            }
            final Map changes = new HashMap();
            changes.put("name", this.idName);
            this.idName = name;
            this.mutationObservable.changeAndNotify(
                    new DefaultMutationContext(MutationContext.RENAME, this, changes));
        }
        return true;
    }


    public void setType(String entityPath) {
        if (((null == entityPath) && (null == this.type))
                || ((null != entityPath) && entityPath.equals(this.type))) {
            return; // Same type, nothing to do.
        }
        final Map changes = new HashMap();
        changes.put("type", this.type);
        setTypeInternal(entityPath);
        if (null != this.parent) {
            this.mutationObservable.changeAndNotify(
                    new DefaultMutationContext(MutationContext.MODIFY, this, changes));
        }//if
    }

    public void setTypeInternal(String entityPath) {
        this.type = entityPath;
    }

    public String getTypeWithExtension() {
        return this.type;
    }


	@Override
	public boolean isArray() {
		return array;
	}
}

