package com.tibco.cep.mapper.xml.xdata.bind;

import com.tibco.cep.mapper.util.ResourceBundleManager;
import com.tibco.cep.mapper.xml.xdata.bind.virt.TypeCopyOfBinding;
import com.tibco.cep.mapper.xml.xdata.xpath.ErrorMessage;
import com.tibco.cep.mapper.xml.xdata.xpath.ErrorMessageList;
import com.tibco.cep.mapper.xml.xdata.xpath.MessageCode;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.TypeChecker;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.XPathCheckArguments;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

/**
 * Type checking helper functions.
 */
public final class BindingTypeCheckUtilities
{
    /**
     * A constant indicating exactly that the type check should be performed for an element copy (or assignment) to the same type.
     * @see #typeCheck
     */
    public static final int TYPE_CHECK_COPY=1;

    /**
     * A constant indicating exactly that the type check should be performed for a contents-only copy (or assignment) to the same type.
     * @see #typeCheck
     */
    public static final int TYPE_CHECK_TYPE_COPY=2;

    /**
     * A constant indicating exactly that the type check should be performed for select when used in a for-each (for example).
     * @see #typeCheck
     */
    public static final int TYPE_CHECK_FOR_EACH=3;

    /**
     * A constant indicating exactly that the type check should be performed for select when used in a straight value-of assignment.<br>
     * (i.e. not the attribute or element itself, just its content)
     * @see #typeCheck
     */
    public static final int TYPE_CHECK_VALUE=4;

    public static int getTypeCheckType(Binding binding)
    {
        if (binding instanceof CopyOfBinding) {
            return TYPE_CHECK_COPY;
        }
        if (binding instanceof TypeCopyOfBinding) {
            return TYPE_CHECK_TYPE_COPY;
        }
        if (binding instanceof ForEachBinding || binding instanceof ForEachGroupBinding) {
            return TYPE_CHECK_FOR_EACH;
        }
        return TYPE_CHECK_VALUE;
    }

    /**
     * Performs a type check for the XTypes, reporting error.
     * @param typeCheckType One of {@link #TYPE_CHECK_COPY}, {@link #TYPE_CHECK_FOR_EACH}, or {@link #TYPE_CHECK_TYPE_COPY}.
     * @param expectedType The type expected (i.e. the right hand side)
     * @param computedType The type received (i.e. the left hand side)
     * @param errorMessageTextRange The text range to which the error messages (if any) should apply.
     * @return The list of error messages, if there are no errors, it is an empty list, not null.
     */
    public static ErrorMessageList typeCheck(int typeCheckType, SmSequenceType expectedType, SmSequenceType computedType, final TextRange errorMessageTextRange, XPathCheckArguments checkArguments)
    {
        // Previous errors don't cause further errors:
        if (SmSequenceTypeSupport.isPreviousError(computedType)) {
            return ErrorMessageList.EMPTY_LIST;
        }
        /*SmType expectedSmType;
        if (expectedType.isWildCard()) {
            // how to do these?
            // like an any?
            expectedSmType = com.tibco.xml.schema.flavor.XSDL.ANY_TYPE;
        } else {
            if (typeCheckType==TYPE_CHECK_VALUE) {
                if (expectedType.isChoice()) {
                    // choices are special, we can only do a value type check:
                    XType xexpectedType = expectedType;
                    ErrorMessageList eml = Expr.typeCheckParticle(
                        computedType,
                        xexpectedType,
                        errorMessageTextRange
                    );
                    return eml;
                } else {
                    expectedSmType = expectedType.getType();
                }
            } else {
                //@@wce add boolean check later...
                //return ErrorMessageList.EMPTY_LIST;
                expectedSmType = expectedType.getType();
            }
        }*/

        if (typeCheckType==TYPE_CHECK_FOR_EACH) {
            return typeCheckForEach(expectedType,computedType,errorMessageTextRange);
        } else {
            if (typeCheckType==TYPE_CHECK_VALUE) {
                // do a preliminary check to see if 'typed value' is sensible:
                if (SmSequenceTypeSupport.isDocumentOrNoTypedValueElement(computedType))
                {
                    // can give a better message here:
                    // (Ok, the actual message stinks right now...)
                    return new ErrorMessageList(new ErrorMessage(ErrorMessage.TYPE_WARNING,"Expected simple '" + expectedType + "', got a complex type",errorMessageTextRange));
                }
                //System.out.println("Etype is: " + expectedType);
                SmSequenceType computedTypedValue = computedType.typedValue(checkArguments.getUsesTypedNodes());
                // do full type check:
                return TypeChecker.checkSerialized(computedTypedValue,expectedType,errorMessageTextRange,checkArguments);
            }
            else
            {
                if (typeCheckType==TYPE_CHECK_COPY || typeCheckType==TYPE_CHECK_TYPE_COPY)
                {
                    return checkNodeSet(computedType,errorMessageTextRange);
                }
                else
                {
                    return ErrorMessageList.EMPTY_LIST;
                }
            }
        }
    }

    private static ErrorMessageList checkNodeSet(SmSequenceType computedType, TextRange errorMessageTextRange) {
        if (!SmSequenceTypeSupport.isNodeSet(computedType))
        {
            // Assigned a non-node set to a structure:
            return new ErrorMessageList(new ErrorMessage(ResourceBundleManager.getMessage(MessageCode.EXPECTED_NODE_SET),errorMessageTextRange));
        }
        return ErrorMessageList.EMPTY_LIST;
    }
   private static ErrorMessageList checkAtomicValue(SmSequenceType computedType, TextRange errorMessageTextRange) {
       if (!SmSequenceTypeSupport.isAnySimpleType(computedType))
       {
           // Assigned a non-node set to a structure:
           return new ErrorMessageList(new ErrorMessage(ResourceBundleManager.getMessage(MessageCode.EXPECTED_NODE_SET),errorMessageTextRange));
       }
       return ErrorMessageList.EMPTY_LIST;
   }

    /**
     * Does a type check for an XSLT for-each (the select clause).
     * @param expectedType The expected type to check against.
     * @param computedType The computed type to check.
     * @param errorMessageTextRange The text range of the expression in question, for the error message (if any)
     * @return The error message or null if there is no error.
     */
    private static ErrorMessageList typeCheckForEach(SmSequenceType expectedType, SmSequenceType computedType, TextRange errorMessageTextRange) {
        // only do a cardinality check:
        if (expectedType!=null)
        {
            SmCardinality card = expectedType.quantifier();
            int expectedMin = card.getMinOccurs();
            int expectedMax = card.getMaxOccurs();

            ErrorMessageList eml = TypeChecker.cardinalityCheck(computedType,expectedMin,expectedMax,errorMessageTextRange);
            if (eml.size()>0)
            {
                return eml;
            }
        }
        // Type used by for-each must be either node-set or sequence of atoms.

        // Is computed type a node-set?
        ErrorMessageList eml = checkNodeSet(computedType,errorMessageTextRange);
        if (eml.size()>0) {
            // Not a node set.  A sequence of atomic values?
            ErrorMessageList eml_2 = checkAtomicValue(computedType, errorMessageTextRange);
            if(eml_2.size() > 0) {
               // We will still return error indicating that we expected a node-set; however
               // we actually expected any set of heterogenous nodes/atomic values, etc.
               return eml;
            }
        }
        return ErrorMessageList.EMPTY_LIST;
    }
}

