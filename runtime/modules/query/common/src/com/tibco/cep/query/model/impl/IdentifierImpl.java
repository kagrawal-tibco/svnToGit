package com.tibco.cep.query.model.impl;


import org.antlr.runtime.tree.CommonTree;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.query.model.Identifier;
import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.TypedContext;
import com.tibco.cep.query.model.resolution.ResolutionHelper;
import com.tibco.cep.query.model.validation.validators.IdentifierValidator;


/**
 * Created by IntelliJ IDEA. User: pdhar Date: Jul 10, 2007 Time: 4:52:26 PM To
 * change this template use File ||
 */
public class IdentifierImpl extends AbstractExpression implements Identifier {
    protected String name;

    protected ModelContext identifiedContext;

    public IdentifierImpl(ModelContext parentContext, CommonTree tree, String name) {
        super(parentContext, tree);
        this.name = name;
    }

    /**
     * @return the context type
     */
    public int getContextType() {
        return CTX_TYPE_IDENTIFIER;
    }

    public String getName() {
        return name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.cep.query.ast.Expression#hasPropertyReference()
     */
    public boolean hasPropertyReference() {
        return identifiedContext.getContextType() == ModelContext.CTX_TYPE_ENTITY_PROPERTY;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.cep.query.ast.Expression#hasAttributeReference()
     */
    public boolean hasAttributeReference() {
        return identifiedContext.getContextType() == ModelContext.CTX_TYPE_ENTITY_ATTRIBUTE;
    }

    public boolean hasCatalogFunctionReference() {
        return identifiedContext.getContextType() == ModelContext.CTX_TYPE_CATALOG_FUNCTION;
    }

    public boolean hasFunctionReference() {
        return hasCatalogFunctionReference() || hasRuleFunctionReference()
                || hasAggregateFunctionReference();
    }

    public boolean hasRuleFunctionReference() {
        return identifiedContext.getContextType() == ModelContext.CTX_TYPE_RULE_FUNCTION;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.cep.query.ast.Expression#hasGroupFunction()
     */
    public boolean hasAggregateFunctionReference() {
        return identifiedContext.getContextType() == ModelContext.CTX_TYPE_AGGREGATE_FUNCTION;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.cep.query.ast.Identifier#getIdentifiedContext()
     */
    public ModelContext getIdentifiedContext() {
        return this.identifiedContext;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.cep.query.ast.Identifier#setIdentifiedContext(com.tibco.cep.query.ast.ModelContext)
     */
    public void setIdentifiedContext(ModelContext context) throws Exception {
        this.identifiedContext = context;
        if (null != context) {
            this.typeInfo = ((TypedContext) context).getTypeInfo();
        }
    }

    public boolean isResolved() {
        return (identifiedContext != null);
    }

    public boolean resolveContext() throws Exception {
        this.setIdentifiedContext(ResolutionHelper.resolveIdentifier(this));
        if (this.isResolved()) {
            this.logger.log(Level.DEBUG, "Resolved Identifier: %s", this.getName());
            return true;
        }
        else {
            this.logger.log(Level.DEBUG, "Failed to resolve Identifier: %s", this.getName());
            return false;
        }
    }


    public String toString() {
        return "{" + this.getName() + "->" + this.getIdentifiedContext() + "}";
    }

    public void validate() throws Exception {
        IdentifierValidator validator = new IdentifierValidator(); 
        validator.validate(this);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (! (o instanceof IdentifierImpl)) {
            if(null == this.identifiedContext) {
                 return false;
            } else {
                return this.getIdentifiedContext().equals(o);
            }
        }
        if (this.getClass().isAssignableFrom(o.getClass()) && !this.getClass().equals(o.getClass())) {
            return o.equals(this); // Delegates to most specific class.
        }
        final IdentifierImpl that = (IdentifierImpl) o;
        return ((null == this.identifiedContext) && (null == that.identifiedContext))
                || ((null != this.identifiedContext) && this.identifiedContext.equals(that.identifiedContext));
    }

    public int hashCode() {
        return (null == this.identifiedContext) ? 0 : this.identifiedContext.hashCode();
    }
}
