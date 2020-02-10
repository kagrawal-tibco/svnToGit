package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmSequenceType;

/**
 * Describes the signature of an XPath function.<br>
 * Used for type-checking and optimization, etc.
 */
public interface XFunction
{
    /**
     * Gets the minimum number of arguments this function can take.<br>
     * Must be greater or equal to zero and less than or equal to {@link #getNumArgs}.
     */
    public int getMinimumNumArgs();

    /**
     * Gets the maximum number of arguments for this function, excluding {@link #getLastArgRepeats}.<br>
     * If {@link #getLastArgRepeats} is <code>true</code>, then function can actually take an unbounded number of
     * arguments.<br>
     * This number must, obviously, be greater than or equal to {@link #getMinimumNumArgs}.
     */
    public int getNumArgs();

    /**
     *
     * @param argNum The argument number, which is zero based.  If this value is not < {@link #getNumArgs}, an implementation should throw a {@link IndexOutOfBoundsException}
     * @param totalArgs The total number of arguments being passed in to the function usage.<br>
     * Typically, most functions would ignore this, but it's possible for a function to have a signature such as
     * <code>myFunction(String)</code> for 1 argument, and <code>myFunction(int, String)</code> for two arguments.
     * @return The type of the argument, never null.
     */
    public SmSequenceType getArgType(int argNum, int totalArgs);

    /**
     * True if the last argument can actually be multiple arguments, as in concat(...) which takes an arbitrary # of arguments.<br>
     * Note, this must be <code>false</code> if {@link #getNumArgs} is zero.
     * @return true If the final argument can repeat, false otherwise.
     */
    public boolean getLastArgRepeats();

    public ExpandedName getName();

    /**
     * Gets the static return type of this function.<br>
     * Note that {@link #typeCheck} also returns the return-type given the input types,
     * it is free to return something more specific given knowlege about the inputs.  This function
     * is for getting it independent of the input types, which may, in some cases, result in a very general answer.
     * @return The return type, never null.
     */
    public SmSequenceType getReturnType();

    /**
     * Determines if this function implicitly climbs up above '.' (i.e. using parent axis).
     * This function does <b>NOT</b> check the argument expressions themselves; those need to be checked separately
     * @return
     */
    public boolean hasNonSubTreeTraversal();

    /**
     * Determines if this function implicitly uses '.' or position() <b>or last()</b>. (Need to rename)
     * This function does <b>NOT</b> check the argument expressions themselves; those need to be checked separately.
     * @param numArgs The number of arguments passed in (some functions behave differently depending on the # of arguments passed in)
     * @return true If this function does not depend on '.' or on position().
     */
    public boolean isIndependentOfFocus(int numArgs);

    /**
     * Gives the function a chance to do further type checking...
     * The function should record any additional errors on the EvalTypeInfo.<br>
     * This function won't be called <b>unless</b> the arguments already meet the length & type constraints
     * defined by getNumArgs(), etc., so an implementation can safely get args[0] if it has 1 or more arguments.
     * By default, it can just return getReturnType().
     */
    public SmSequenceType typeCheck(SmSequenceType[] argsType, ExprContext context, EvalTypeInfo state, Expr functionCallExpr, Expr[] args);
}
