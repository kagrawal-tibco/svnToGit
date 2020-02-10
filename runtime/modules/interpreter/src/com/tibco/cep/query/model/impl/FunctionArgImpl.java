package com.tibco.cep.query.model.impl;

import org.antlr.runtime.tree.CommonTree;

import com.tibco.cep.query.model.FunctionArg;
import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.TypeInfo;

public class FunctionArgImpl extends AbstractRegistryEntryContext implements
        FunctionArg {
    private String name;

    public FunctionArgImpl(ModelContext parentContext, CommonTree tree, String name, TypeInfo typeInfo) {
        super(parentContext);
        this.name = name;
        this.typeInfo = typeInfo;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getContextType() {
        return ModelContext.CTX_TYPE_FUNCTION_ARG;
    }


    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (! (o instanceof FunctionArgImpl)) {
            return false;
        }
        if (this.getClass().isAssignableFrom(o.getClass()) && !this.getClass().equals(o.getClass())) {
            return o.equals(this); // Delegates to most specific class.
        }
        final FunctionArgImpl that = (FunctionArgImpl) o;
        return this.typeInfo.equals(that.typeInfo);
    }


    public int hashCode() {
        return this.typeInfo.hashCode();
    }

}
