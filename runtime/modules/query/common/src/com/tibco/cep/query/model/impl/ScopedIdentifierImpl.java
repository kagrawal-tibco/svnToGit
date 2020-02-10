package com.tibco.cep.query.model.impl;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.query.exception.ResolveException;
import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.ScopedIdentifier;
import com.tibco.cep.query.model.resolution.ResolutionHelper;

/**
 * Created by IntelliJ IDEA. User: pdhar Date: Oct 11, 2007 Time: 4:06:40 PM To
 * change this template use File | Settings | File Templates.
 */
public class ScopedIdentifierImpl extends AbstractExpression implements ScopedIdentifier {
    private String name;

    private List<ModelContext> identifiedContexts = new ArrayList<ModelContext>();

    protected boolean isResolved = false;

    public ScopedIdentifierImpl(ModelContext parentContext, CommonTree tree, String name) {
        super(parentContext, tree);
        this.name = name;
    }

    /**
     * @return the context typeInfo
     */
    public int getContextType() {
        return CTX_TYPE_IDENTIFIER;
    }

    public String getName() {
        return name;
    }


    public ModelContext getIdentifiedContext() throws ResolveException {
        return this;
    }

    /**
     * @return ModelContext the identified context
     */
    public ModelContext[] getIdentifiedContexts() throws ResolveException {
        if (!isResolved()) {
            throw new ResolveException("Context has not been resolved");
        }
        return identifiedContexts.toArray(new ModelContext[identifiedContexts.size()]);
    }

    private Iterator getIdentifiedContextsByType(int type) throws ResolveException {
        if (!isResolved()) {
            throw new ResolveException("Context has not been resolved");
        }
        List<ModelContext> resultList = new ArrayList<ModelContext>();
        for (Iterator it = identifiedContexts.iterator(); it.hasNext();) {
            ModelContext ctx = (ModelContext) it.next();
            if (ctx.getContextType() == type) {
                resultList.add(ctx);
            }
        }
        return resultList.iterator();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.cep.query.ast.Expression#hasPropertyReference()
     */
    public boolean hasPropertyReference() throws Exception {
        return getIdentifiedContextsByType(ModelContext.CTX_TYPE_ENTITY_PROPERTY).hasNext();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.cep.query.ast.Expression#hasAttributeReference()
     */
    public boolean hasAttributeReference() throws Exception {
        return getIdentifiedContextsByType(ModelContext.CTX_TYPE_ENTITY_ATTRIBUTE).hasNext();
    }

    public boolean hasCatalogFunctionReference() throws Exception {
        return getIdentifiedContextsByType(ModelContext.CTX_TYPE_CATALOG_FUNCTION).hasNext();
    }

    public boolean hasFunctionReference() throws Exception {
        return hasCatalogFunctionReference() || hasRuleFunctionReference()
                || hasAggregateFunctionReference();
    }

    public boolean hasRuleFunctionReference() throws Exception {
        return getIdentifiedContextsByType(ModelContext.CTX_TYPE_RULE_FUNCTION).hasNext();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.cep.query.ast.Expression#hasGroupFunction()
     */
    public boolean hasAggregateFunctionReference() throws Exception {
        return getIdentifiedContextsByType(ModelContext.CTX_TYPE_AGGREGATE_FUNCTION).hasNext();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.cep.query.ast.Identifier#setIdentifiedContext(com.tibco.cep.query.ast.ModelContext)
     */
    public void addIdentifiedContext(ModelContext context) {
        this.identifiedContexts.add(context);
    }


    public boolean isResolved() {
        return null != this.typeInfo;
    }


    public boolean resolveContext() throws Exception {
        this.identifiedContexts = ResolutionHelper.resolveScopedIdentifier(this);
        this.typeInfo = new TypeInfoImpl(this.identifiedContexts);

        if (this.isResolved()) {
            if (this.logger.isEnabledFor(Level.DEBUG)) {
                final StringBuffer sb = new StringBuffer("Resolved ScopedIdentifier: ").append(
                        this.getName()).append(" ->");
                for (ModelContext mc : this.identifiedContexts) {
                    sb.append(" ").append(mc.toString());
                }
                this.logger.log(Level.DEBUG, sb.toString());
            }
            return true;
        }
        else {
            this.logger.log(Level.DEBUG, "Failed to resolve ScopedIdentifier: %s", this.getName());
            return false;
        }
    }


    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ScopedIdentifierImpl)) {
            return false;
        }
        if (this.getClass().isAssignableFrom(o.getClass()) && !this.getClass().equals(o.getClass())) {
            return o.equals(this); // Delegates to most specific class.
        }

        final ScopedIdentifierImpl that = (ScopedIdentifierImpl) o;

        return this.identifiedContexts.equals(that.identifiedContexts)
                && (this.name.equals(that.name));
    }


    public int hashCode() {
        long longHash = this.name.hashCode();
        longHash = 29 * longHash + this.identifiedContexts.hashCode();
        longHash ^= (longHash >>> 32);
        return (int) longHash;
    }


}
