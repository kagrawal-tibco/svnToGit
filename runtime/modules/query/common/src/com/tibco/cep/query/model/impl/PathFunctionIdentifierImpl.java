package com.tibco.cep.query.model.impl;

import org.antlr.runtime.tree.CommonTree;

import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.PathFunctionIdentifier;

/**
 * Created by IntelliJ IDEA. User: pdhar Date: Oct 15, 2007 Time: 10:44:59 PM To
 * change this template use File | Settings | File Templates.
 */
public class PathFunctionIdentifierImpl extends AbstractFunctionIdentifierImpl implements
        PathFunctionIdentifier {
    public PathFunctionIdentifierImpl(ModelContext parentContext, CommonTree tree, String name) {
        super(parentContext, tree, name);
    }

    /**
     * @return the context typeInfo
     */
    public int getContextType() {
        return CTX_TYPE_PATH_FUNCTION_IDENTIFIER;
    }
}
