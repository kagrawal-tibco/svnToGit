package com.tibco.be.parser.semantic;

import com.tibco.be.parser.tree.NodeType;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Aug 1, 2004
 * Time: 11:48:41 PM
 * To change this template use File | Settings | File Templates.
 */
public interface FunctionLookup {
    /**
     * If a single identifier is passed as funcName, all functions with that name as their unqualified name (short / local name) are returned.
     * @param funcName this may be either fully specified (Category.name) or a single identifier (no preceeding dots)
     * @return returns all functions with the given name or null if none are found
     */
    public FunctionRec lookupFunction(String funcName, NodeType[] argTypes);
}
