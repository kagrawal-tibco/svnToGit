package com.tibco.cep.query.model.impl;

import org.antlr.runtime.tree.CommonTree;

import com.tibco.cep.query.model.CaptureType;
import com.tibco.cep.query.model.EmitType;
import com.tibco.cep.query.model.GroupPolicy;
import com.tibco.cep.query.model.ModelContext;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Oct 22, 2007
 * Time: 2:56:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class GroupPolicyImpl extends AbstractQueryContext implements GroupPolicy {

    private EmitType emitType;
    private CaptureType captureType;

    /**
     * ctor
     *
     * @param parentContext
     * @param captureType
     * @param emitType
     * @param tree
     */
    public GroupPolicyImpl(ModelContext parentContext, CaptureType captureType, EmitType emitType, CommonTree tree) {
        super(parentContext, tree);
        this.captureType = captureType;
        this.emitType = emitType;
    }


    public CaptureType getCaptureType() {
        return this.captureType;
    }


    /**
     * @return the context type
     */
    public int getContextType() {
        return ModelContext.CTX_TYPE_GROUP_POLICY;
    }


    public EmitType getEmitType() {
        return this.emitType;
    }


    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (! (o instanceof GroupPolicyImpl)) {
            return false;
        }
        if (this.getClass().isAssignableFrom(o.getClass()) && !this.getClass().equals(o.getClass())) {
            return o.equals(this); // Delegates to most specific class.
        }
        final GroupPolicyImpl that = (GroupPolicyImpl) o;
        return (this.captureType == that.captureType)
            && (this.emitType == that.emitType);
    }


    public int hashCode() {
        long longHash = (null == this.emitType) ? 0 : this.emitType.hashCode();
        longHash = 29 * longHash + ((null == this.captureType) ? 0 : this.captureType.hashCode());
        longHash ^= (longHash >>> 32);
        return (int) longHash;
    }

}
