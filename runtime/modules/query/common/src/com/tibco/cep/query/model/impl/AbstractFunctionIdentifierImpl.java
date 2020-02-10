package com.tibco.cep.query.model.impl;


import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.query.model.Expression;
import com.tibco.cep.query.model.FunctionIdentifier;
import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.TypeInfo;
import com.tibco.cep.query.model.resolution.ResolutionHelper;
import com.tibco.cep.query.model.validation.validators.FunctionValidator;

/**
 * Created by IntelliJ IDEA. User: nprade Date: Oct 15, 2007 Time: 6:54:07 PM To
 * change this template use File | Settings | File Templates.
 */
public abstract class AbstractFunctionIdentifierImpl extends IdentifierImpl implements
        FunctionIdentifier {

    public AbstractFunctionIdentifierImpl(ModelContext parentContext, CommonTree tree, String name) {
        super(parentContext, tree, name);
    }


    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractFunctionIdentifierImpl)) {
            return false;
        }
        if (this.getClass().isAssignableFrom(o.getClass()) && !this.getClass().equals(o.getClass())) {
            return o.equals(this); // Delegates to most specific class.
        }
        final AbstractFunctionIdentifierImpl that = (AbstractFunctionIdentifierImpl) o;
        if ( this.childContext.equals(that.childContext)
            && ( ((null == this.identifiedContext) && (null != that.identifiedContext))
                || ((null != this.identifiedContext) && !this.identifiedContext.equals(that.identifiedContext)) ) ) {
            return false;
        }

        try {
            final TypeInfo thisTypeInfo = this.getTypeInfo();
            return ((null != thisTypeInfo)  && thisTypeInfo.equals(that.getTypeInfo()))
                    || ((null == thisTypeInfo) && (null == that.getTypeInfo()));
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * Return the function argument at a specified index
     * 
     * @param index
     * @return Expression
     */
    public Expression getArgument(int index) throws Exception {
        int i=0;
        for (ModelContext ctx : this.childContext) {
            if (ctx instanceof Expression) {
                if (i++ == index) {
                    return (Expression) ctx;
                }
            }
        }
        return null;
    }

    /**
     * Returns the number of arguments
     * 
     * @return int
     */
    public int getArgumentCount() throws Exception {
        int count = 0;
        for (ModelContext ctx : this.childContext) {
            if (ctx instanceof Expression) {
                count++;
            }
        }
        return count;
    }

    /**
     * Returns the array of function argument Expressions from left to right
     * 
     * @return Expression[]
     */
    public Expression[] getArguments() throws Exception {
        final List<Expression> expressions = new ArrayList<Expression>();
        for (ModelContext ctx : this.childContext) {
            if (ctx instanceof Expression) {
                expressions.add((Expression) ctx);
            }
        }
        return expressions.toArray(new Expression[expressions.size()]);
    }


    public int hashCode() {
        if (null == this.identifiedContext) {
            return 0;
        }
        long longHash = this.identifiedContext.hashCode();
        longHash = longHash * 29 + this.childContext.hashCode();
        longHash ^= (longHash >>> 32);
        return (int) longHash;
    }

    
    /**
     * Resolves the path function context
     * 
     * @return true if resolved or else false
     * @throws Exception
     */
    public boolean resolveContext() throws Exception {
        setIdentifiedContext(ResolutionHelper.resolveIdentifier(this));
        if (super.isResolved()) {
            this.logger.log(Level.DEBUG, "Resolved function: %s", this.getName());
            return true;
        }
        else {
            this.logger.log(Level.DEBUG, "Failed to resolve function: %s", this.getName());
            return false;
        }
    }

    @Override
    public void validate() throws Exception {
        super.validate();

        new FunctionValidator().validate(this);
    }
}
