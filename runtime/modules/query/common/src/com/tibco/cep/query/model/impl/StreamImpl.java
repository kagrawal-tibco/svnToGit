package com.tibco.cep.query.model.impl;

import org.antlr.runtime.tree.CommonTree;

import com.tibco.cep.query.model.AcceptType;
import com.tibco.cep.query.model.EmitType;
import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.Stream;
import com.tibco.cep.query.model.StreamPolicy;

/**
 * Base implementation of Stream.
 */
public class StreamImpl
        extends AbstractQueryContext
        implements Stream {


    private EmitType emitType;

    private AcceptType acceptType;

    public StreamImpl(ModelContext parentContext, CommonTree tree, EmitType emitType, AcceptType acceptType) {
        super(parentContext, tree);
        
        if (null == emitType) {
            this.emitType = EmitType.FULL;
        } else {
            this.emitType = emitType;
        }

        if (null == acceptType) {
            this.acceptType = AcceptType.ALL;
        } else {
            this.acceptType = acceptType;
        }
    }


    /**
     * @return the context type
     */
    public int getContextType() {
        return ModelContext.CTX_TYPE_STREAM;
    }


    /**
     * @return EmitType that is listened to, if any.
     */
    public EmitType getEmitType() {
        return this.emitType;
    }

    /**
     *
     * @return AcceptType.
     */
    public AcceptType getAcceptType() {
        return acceptType;
    }

    /**
     * @return StreamPolicy that governs the Stream behavior, if any.
     */
    public StreamPolicy getPolicy() {
        ModelContext[] children = this.getDescendantContextsByType(this, ModelContext.CTX_TYPE_STREAM_POLICY);
        if (children.length > 0) {
            return (StreamPolicy) children[0];
        }
        return null;
    }

    public String toString() {
        if(null == this.emitType)
            return contextStr.get(getContextType());
        switch(emitType) {
            case NEW:
                return "Stream : Emit: NEW";
            case FULL:
                return "Stream : Emit: FULL";
            case DEAD:
                return "Stream : Emit: DEAD";
            default:
                return contextStr.get(getContextType());
        }

    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (! (o instanceof StreamImpl)) {
            return false;
        }
        if (this.getClass().isAssignableFrom(o.getClass()) && !this.getClass().equals(o.getClass())) {
            return o.equals(this); // Delegates to most specific class.
        }

        final StreamImpl that = (StreamImpl) o;
        if (this.emitType != that.emitType) {
            return false;
        }
        final StreamPolicy thisPolicy = this.getPolicy();
        final StreamPolicy thatPolicy = that.getPolicy();
        if (null == thisPolicy) {
            return (null == thatPolicy);
        } else {
            return thisPolicy.equals(thatPolicy);
        }
    }


    public int hashCode() {
        long longHash = this.emitType.hashCode();
        longHash = longHash * 29 + this.emitType.hashCode();
        longHash ^= (longHash >>> 32);
        return (int) longHash;
    }


}
