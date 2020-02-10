package com.tibco.cep.query.model.impl;

import org.antlr.runtime.tree.CommonTree;

import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.StreamPolicy;
import com.tibco.cep.query.model.StreamPolicyBy;
import com.tibco.cep.query.model.WhereClause;
import com.tibco.cep.query.model.Window;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Oct 4, 2007
 * Time: 4:51:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class StreamPolicyImpl
        extends AbstractQueryContext
        implements StreamPolicy {

    public StreamPolicyImpl(StreamImpl stream, CommonTree tree) {
        super(stream, tree);
    }


    /**
     * Gets the BY clause used by the policy.
     *
     * @return StreamPolicyBy
     */
    public StreamPolicyBy getByClause() {
        for (ModelContext ctx: this.childContext) {
            if (ctx instanceof StreamPolicyBy) {
                return (StreamPolicyBy) ctx;
            }
        }
        return null;
    }

    /**
     * @return the context type
     */
    public int getContextType() {
        return ModelContext.CTX_TYPE_STREAM_POLICY;
    }


    /**
     * Gets the WHERE clause used by the policy.
     *
     * @return WhereClause.
     */
    public WhereClause getWhereClause() {
        for (ModelContext ctx: this.childContext) {
            if (ctx instanceof WhereClause) {
                return (WhereClause) ctx;
            }
        }
        return null;
    }


    /**
     * Gets the Window specification.
     *
     * @return Window
     */
    public Window getWindow() {
        for (ModelContext ctx: this.childContext) {
            if (ctx instanceof Window) {
                return (Window) ctx;
            }
        }
        return null;
    }


    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (! (o instanceof StreamPolicyImpl)) {
            return false;
        }
        if (this.getClass().isAssignableFrom(o.getClass()) && !this.getClass().equals(o.getClass())) {
            return o.equals(this); // Delegates to most specific class.
        }

        final StreamPolicyImpl that = (StreamPolicyImpl) o;
        final StreamPolicyBy thisBy = this.getByClause();
        final StreamPolicyBy thatBy = that.getByClause();
        final WhereClause thisWhere = this.getWhereClause();
        final WhereClause thatWhere = that.getWhereClause();
        final Window thisWindow = this.getWindow();
        final Window thatWindow = that.getWindow();
        return (((thisBy == null) && (thatBy == null)) || ((thisBy != null) && thisBy.equals(thatBy)))
                && (((thisWhere == null) && (thatWhere == null)) || ((thisWhere) != null) && thisWhere.equals(thatWhere))
                && (((thisWindow == null) && (thatWindow == null)) || ((thisWindow != null) && thisWindow.equals(thatWindow)));
    }


    public int hashCode() {
        final StreamPolicyBy thisBy = this.getByClause();
        final WhereClause thisWhere = this.getWhereClause();
        final Window thisWindow = this.getWindow();
        long longHash = (thisBy == null) ? 0 : thisBy.hashCode();
        longHash = 29 * longHash + ((thisWhere == null) ? 0 : thisWhere.hashCode());
        longHash ^= (longHash >>> 32);
        longHash = 29 * longHash + ((thisWindow == null) ? 0 : thisWindow.hashCode());
        longHash ^= (longHash >>> 32);
        return (int) longHash;
    }


}
