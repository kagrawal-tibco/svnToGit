package com.tibco.be.parser.semantic;

import com.tibco.be.model.functions.Predicate;
import com.tibco.be.parser.tree.NodeType;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Aug 31, 2006
 * Time: 3:05:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class FunctionRec {
    public Predicate function;
    public NodeType returnType;
    public NodeType[] argTypes;
    public Class[] thrownExceptions;
    public boolean isMapper;
    //the real type of the varargs argument that is shown to studio as a list of normal arguments
    public NodeType varargsCodegenArgType;
}
