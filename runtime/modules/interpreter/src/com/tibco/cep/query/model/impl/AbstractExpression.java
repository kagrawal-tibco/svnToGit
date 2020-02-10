package com.tibco.cep.query.model.impl;


import java.util.ArrayList;
import java.util.Iterator;

import org.antlr.runtime.tree.CommonTree;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.query.exception.ResolveException;
import com.tibco.cep.query.exception.SemanticException;
import com.tibco.cep.query.model.AggregateFunctionIdentifier;
import com.tibco.cep.query.model.Expression;
import com.tibco.cep.query.model.Identifier;
import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.PathFunctionIdentifier;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Jul 15, 2007
 * Time: 9:44:14 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractExpression extends AbstractTypedQueryContext implements Expression {

    protected boolean hasPropertyReference = false;
    protected boolean hasAttributeRefernce = false;
    protected boolean hasGroupFunction = false;

    protected AbstractExpression(ModelContext parentContext, CommonTree tree) {
        super(parentContext, tree);
    }


    private void lookupReferences(ModelContext context,int contextType,ArrayList<ModelContext> list) throws Exception {
        if (context instanceof Identifier) {
            Identifier id = (Identifier) context;
            if (id.getIdentifiedContext().getContextType() == contextType) {
                list.add(context);
            }
            return;
        } else {
            while (context.hasChildren()) {
                for (Iterator iter = context.getChildrenIterator(); iter.hasNext();) {
                    ModelContext element = (ModelContext) iter.next();
                    lookupReferences(element, contextType, list);
                }
            }
        }
    }

    /* (non-Javadoc)
      * @see com.tibco.cep.query.ast.Expression#hasAttributeReference()
      */
    public boolean hasAttributeReference() throws Exception {
        ArrayList<ModelContext> list = new ArrayList<ModelContext>();
        lookupReferences(this, ModelContext.CTX_TYPE_ENTITY_ATTRIBUTE, list);
        lookupReferences(this, ModelContext.CTX_TYPE_ENTITY_ATTRIBUTE_PROXY, list);
        return list.size() > 0;
    }

    /* (non-Javadoc)
      * @see com.tibco.cep.query.ast.Expression#hasAggregateFunctionReference()
      */
    public boolean hasAggregateFunctionReference() throws Exception {
        ArrayList<ModelContext> list = new ArrayList<ModelContext>();
        lookupReferences(this, ModelContext.CTX_TYPE_AGGREGATE_FUNCTION_IDENTIFIER, list);
        for(ModelContext c: list) {
            AggregateFunctionIdentifier afi =  (AggregateFunctionIdentifier) c;
            if(afi.getIdentifiedContext().getContextType() == ModelContext.CTX_TYPE_AGGREGATE_FUNCTION) {
                return true;
            }
        }
        return false;
    }

/* (non-Javadoc)
      * @see com.tibco.cep.query.ast.Expression#hasRuleFunctionReference()
      */
    public boolean hasRuleFunctionReference() throws Exception {
        ArrayList<ModelContext> list = new ArrayList<ModelContext>();
        lookupReferences(this, ModelContext.CTX_TYPE_PATH_FUNCTION_IDENTIFIER, list);
        for(ModelContext c: list) {
            PathFunctionIdentifier pfi = (PathFunctionIdentifier) c;
            if(pfi.getIdentifiedContext().getContextType() == ModelContext.CTX_TYPE_RULE_FUNCTION) {
                return true;
            }
        }
        return false;
    }

/* (non-Javadoc)
      * @see com.tibco.cep.query.ast.Expression#hasCatalogFunctionReference()
      */
    public boolean hasCatalogFunctionReference() throws Exception {
        ArrayList<ModelContext> list = new ArrayList<ModelContext>();
        lookupReferences(this, ModelContext.CTX_TYPE_PATH_FUNCTION_IDENTIFIER, list);
        for(ModelContext c: list) {
            PathFunctionIdentifier pfi = (PathFunctionIdentifier) c;
            if(pfi.getIdentifiedContext().getContextType() == ModelContext.CTX_TYPE_CATALOG_FUNCTION) {
                return true;
            }
        }
        return false;
    }

/* (non-Javadoc)
      * @see com.tibco.cep.query.ast.Expression#hasFunctionReference()
      */
    public boolean hasFunctionReference() throws Exception {
        // TODO Auto-generated method stub
        return false;
    }

/* (non-Javadoc)
      * @see com.tibco.cep.query.ast.Expression#hasPropertyReference()
      */
    public boolean hasPropertyReference() throws Exception {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean resolveContext() throws Exception {
        this.logger.log(Level.DEBUG, "Need to resolve context for %s: %s",
                this.getClass().getName(), this.getText());
        return this.isResolved();
    }


    public ModelContext getIdentifiedContext() throws ResolveException {
        return null;
    }

    public void validate() throws Exception {
        throw new SemanticException(this,"AbstractExpression validate should not be used for validation for this context:"+this);
    }


    public String getExpressionText() {
        return this.tree.getText();
    }
}
