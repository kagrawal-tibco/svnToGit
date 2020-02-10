package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmSequenceType;

/**
 * An abstract implementation of {@link XFunction} which handles the name, return type, and arguments.<br>
 * Should be appropriate for most, but not all, functions to subclass.
 */
public abstract class DefaultXFunction implements XFunction
{
    private final ExpandedName mName;
    private final SmSequenceType[] mArgs;
    private final SmSequenceType mReturnType;

    private static final SmSequenceType[] NONE = new SmSequenceType[0];

    public DefaultXFunction(ExpandedName name, SmSequenceType ret, SmSequenceType[] args)
    {
        mName = name;
        mReturnType = ret;
        mArgs = args;
        if (name==null) throw new NullPointerException();
        if (mReturnType==null) throw new NullPointerException();
        if (args==null) throw new NullPointerException();
        for (int i=0;i<args.length;i++)
        {
            if (args[i]==null)
            {
                throw new NullPointerException("Arg: " + i + " is null");
            }
        }
    }

    public DefaultXFunction(ExpandedName name, SmSequenceType ret)
    {
        this(name,ret,NONE);
    }

    public DefaultXFunction(ExpandedName name, SmSequenceType ret, SmSequenceType singleArg)
    {
        this(name,ret,new SmSequenceType[] {singleArg});
    }

    public DefaultXFunction(ExpandedName name, SmSequenceType ret, SmSequenceType firstArg, SmSequenceType secondArg)
    {
        this(name,ret,new SmSequenceType[] {firstArg,secondArg});
    }

    public DefaultXFunction(ExpandedName name, SmSequenceType ret, SmSequenceType firstArg, SmSequenceType secondArg, SmSequenceType thirdArg)
    {
        this(name,ret,new SmSequenceType[] {firstArg,secondArg,thirdArg});
    }

    public final int getNumArgs()
    {
        return mArgs.length;
    }

    public final SmSequenceType getArgType(int argNum, int totalArgs)
    {
        // will throw array index out of bounds...
        return mArgs[argNum];
    }

    public final ExpandedName getName()
    {
        return mName;
    }

    public final SmSequenceType getReturnType()
    {
        return mReturnType;
    }

    /**
     * Default implementation returns <code>false</code>, but can override.
     */
    public boolean hasNonSubTreeTraversal()
    {
        return false;
    }

    /**
     * Default implementation returns <code>true</code>, but can override.
     */
    public boolean isIndependentOfFocus(int numArgs)
    {
        return true;
    }

    /**
     * Default implementation does no checking & returns {@link #getReturnType}, but can override.
     */
    public SmSequenceType typeCheck(SmSequenceType[] argsType, ExprContext context, EvalTypeInfo state, Expr functionCallExpr, Expr[] args)
    {
        return getReturnType();
    }

    /**
     * For debugging/diagnostic purposes, prints the local-name of the fn followed by ().
     */
    public String toString()
    {
        return getName().getLocalName() + "()";
    }
}
