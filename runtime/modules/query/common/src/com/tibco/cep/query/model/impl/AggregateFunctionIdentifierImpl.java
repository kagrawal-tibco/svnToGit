package com.tibco.cep.query.model.impl;

import org.antlr.runtime.tree.CommonTree;

import com.tibco.cep.query.model.AggregateFunctionIdentifier;
import com.tibco.cep.query.model.Function;
import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.validation.validators.AggregateFunctionValidator;

/**
 * Created by IntelliJ IDEA. User: pdhar Date: Oct 15, 2007 Time: 10:46:23 PM To
 * change this template use File | Settings | File Templates.
 */
public class AggregateFunctionIdentifierImpl
        extends AbstractFunctionIdentifierImpl
        implements AggregateFunctionIdentifier {


    public AggregateFunctionIdentifierImpl(ModelContext parentContext, CommonTree tree, String name) {
        super(parentContext, tree, name);
    }


    /**
     * @return the context typeInfo
     */
    public int getContextType() {
        return CTX_TYPE_AGGREGATE_FUNCTION_IDENTIFIER;
    }


    @Override
    public void validate() throws Exception {
        new AggregateFunctionValidator().validate(this);
    }


    /**
     * Resolves the path function context
     *
     * @return true if resolved or else false
     * @throws Exception
     */
    public boolean resolveContext() throws Exception {
        if (super.resolveContext()) {
            if (this.name.equalsIgnoreCase(Function.AGGREGATE_FUNCTION_COUNT)
                    || this.name.equalsIgnoreCase(Function.AGGREGATE_FUNCTION_PENDING_COUNT)) {
                this.setTypeInfo(Integer.class.getName());
            } else if (this.name.equalsIgnoreCase(Function.AGGREGATE_FUNCTION_AVG)
                    || this.name.equalsIgnoreCase(Function.AGGREGATE_FUNCTION_SUM)) {
                this.setTypeInfo(Double.class.getName());
            } else {
                this.setTypeInfo(this.getArgument(0).getTypeInfo());
            }
            return true;
        }
        return false;
    }


}
