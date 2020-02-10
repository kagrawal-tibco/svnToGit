package com.tibco.cep.mapper.xml.xdata.xpath.expr;

import com.tibco.cep.mapper.util.ResourceBundleManager;
import com.tibco.cep.mapper.xml.xdata.xpath.ErrorMessage;
import com.tibco.cep.mapper.xml.xdata.xpath.ErrorMessageList;
import com.tibco.cep.mapper.xml.xdata.xpath.MessageCode;
import com.tibco.cep.mapper.xml.xdata.xpath.Parser;
import com.tibco.cep.mapper.xml.xdata.xpath.ReplacementXPathFilter;
import com.tibco.cep.mapper.xml.xdata.xpath.SurroundWithFnXPathFilter;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.cep.mapper.xml.xdata.xpath.XPathFilter;
import com.tibco.xml.channel.simpletypecompiler.SimpleTypeCompilerException;
import com.tibco.xml.data.primitive.NamespaceToPrefixResolver;
import com.tibco.xml.data.primitive.PrefixToNamespaceResolver;
import com.tibco.xml.data.primitive.SerializationContext;
import com.tibco.xml.data.primitive.XmlAtomicValue;
import com.tibco.xml.data.primitive.XmlItem;
import com.tibco.xml.data.primitive.XmlSequence;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmParticle;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.xtype.SMDT;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

/**
 * A set of type checking functions.<br>
 * All the functions here properly handle previous error types.
 */
public final class TypeChecker
{
    private TypeChecker()
    {
    }

    static
    {
        //??ResourceBundleManager.addXMLResourceBundle();
    }

    /**
     * Does a type-check for an assignment where the value is being converted to an XML serialized form, then re-parsed/validated
     * into the new value.<br>
     * i.e. in XSLT, a 'value-of' ultimately converts the typed value into a string value, then reparses it against the target
     * schema.
     * @param got The actual type.
     * @param expected The signature type.
     * @param args Various switches controlling warning levels, etc.
     * @return The error messages, never null, maybe empty.
     */
    public static ErrorMessageList checkSerialized(SmSequenceType got, SmSequenceType expected, TextRange range, XPathCheckArguments args)
    {
        return checkGeneric(got,expected,range,args,true);
    }

    /**
     * Does a type-check for an assignment according to XPath assignment conversion rules.<br>
     * As opposed to {@link #checkSerialized}, there is no implicit <code>typed->string->new typed</code> conversions, just
     * <code>type->new type</code>.
     * @param got The actual type.
     * @param expected The signature type.
     * @param args Various switches controlling warning levels, etc.
     * @return The error messages, never null, maybe empty.
     */
    public static ErrorMessageList checkConversion(SmSequenceType got, SmSequenceType expected, TextRange range, XPathCheckArguments args)
    {
        return checkGeneric(got,expected,range,args,false);
    }

    /**
     * Dynamic switchable version of {@link #checkSerialized} and {@link #checkConversion}.
     * @param serialized If true, will be {@link #checkSerialized} semantics, otherwise {@link #checkConversion}.
     */
    public static ErrorMessageList checkGeneric(SmSequenceType got, SmSequenceType expected, TextRange range, XPathCheckArguments args, boolean serialized)
    {
        if (SmSequenceTypeSupport.isPreviousError(got))
        {
            // No more errors...
            return ErrorMessageList.EMPTY_LIST;
        }
        if (!args.getIncludePrimitiveTypeConversions())
        {
            return ErrorMessageList.EMPTY_LIST;
        }
        // Do boolean first because, the conversion rules are bizarre:
        // (Must be done before constant check because strings are true if length>0, not if value is 'true' or 'false'.
        if (!serialized && SmSequenceTypeSupport.isPrimitiveBoolean(expected))
        {
            return checkTypeBooleanGeneric(got,range,serialized);
        }

        // First check for constant:
        if (expected.getSimpleType()!=null)
        {
            ErrorMessageList eml = checkValueConstant(got,expected.getSimpleType(),range,null);
            if (eml.size()>0)
            {
                return eml;
            }
        }
        ErrorMessageList eml = cardinalityCheck(got,expected,range);
        if (eml.size()>0)
        {
            return eml;
        }
        if (!SmSequenceTypeSupport.isNodeSet(expected))
        {
            // Need a 2.0 versus 1.0 flag here....
            if (SmSequenceTypeSupport.isPrimitiveBoolean(expected))
            {
                return checkTypeBooleanGeneric(got,range,serialized);
            }

            if (!serialized)
            {
                // for XPath assignment, when converting to a primitive (other than boolean), will always use typed value accessor first:
                got = got.typedValue(args.getUsesTypedNodes());

                //WCETODO need to make this a little more reflective of relative when the LHS is in 1.0 mode, that's really an
                // untyped version of a number, etc., not a number.
            }
            if (SmSequenceTypeSupport.isNumber(expected))
            {
                return checkTypeNumber(got,expected,range,args,serialized);
            }
            if (SmSequenceTypeSupport.isHexBinary(expected))
            {
                return checkTypeHexBinary(got,range);
            }
            if (SmSequenceTypeSupport.isBase64Binary(expected))
            {
                return checkTypeBase64Binary(got,range);
            }
            if (SmSequenceTypeSupport.isDateTime(expected))
            {
                return checkDateTime(got,range);
            }
            if (SmSequenceTypeSupport.isDate(expected))
            {
                return checkDate(got,range);
            }
            // no checking for string.
            return ErrorMessageList.EMPTY_LIST;
        }
        // no checking for nodes right now (todo - done elsewhere already, integrate).
        return ErrorMessageList.EMPTY_LIST;
    }

    private static ErrorMessageList checkTypeBase64Binary(SmSequenceType type, TextRange range)
    {
        SmSequenceType etype = type.encodedValue();
        if (SmSequenceTypeSupport.getPrimitiveSchemaType(type)==null || SmSequenceTypeSupport.isBase64Binary(etype) || SmSequenceTypeSupport.isString(etype)) // allow strings to go...
        {
            return ErrorMessageList.EMPTY_LIST;
        }
        return addConversionWarning("base64-binary",etype,range);
    }

    private static ErrorMessageList checkDateTime(SmSequenceType type, TextRange range)
    {
        SmSequenceType etype = type.encodedValue();
        if (SmSequenceTypeSupport.getPrimitiveSchemaType(type)==null || SmSequenceTypeSupport.isDateTime(etype) || SmSequenceTypeSupport.isString(etype)) // allow strings to go...
        {
            return ErrorMessageList.EMPTY_LIST;
        }
        return addConversionWarning("date-time",etype,range);
    }

    private static ErrorMessageList checkDate(SmSequenceType type, TextRange range)
    {
        SmSequenceType etype = type.encodedValue();
        if (SmSequenceTypeSupport.getPrimitiveSchemaType(type)==null || SmSequenceTypeSupport.isDate(etype) || SmSequenceTypeSupport.isString(etype)) // allow strings to go...
        {
            return ErrorMessageList.EMPTY_LIST;
        }
        return addConversionWarning("date",etype,range);
    }

    private static ErrorMessageList checkTypeHexBinary(SmSequenceType type, TextRange range)
    {
        SmSequenceType etype = type.encodedValue();
        if (SmSequenceTypeSupport.getPrimitiveSchemaType(type)==null || SmSequenceTypeSupport.isHexBinary(etype) || SmSequenceTypeSupport.isString(etype)) // allow strings to go...
        {
            return ErrorMessageList.EMPTY_LIST;
        }
        return addConversionWarning("hex-binary",etype,range);
    }

    private static ErrorMessageList addConversionWarning(SmSequenceType expected, SmSequenceType got, TextRange range)
    {
        return addConversionWarning(getPrimitiveTypeName(expected),got,range,null);
    }

    private static ErrorMessageList addConversionWarning(SmSequenceType expected, SmSequenceType got, TextRange range, String fixingXPathFn)
    {
        return addConversionWarning(getPrimitiveTypeName(expected),got,range,fixingXPathFn);
    }

    private static ErrorMessageList addConversionWarning(String expected, SmSequenceType got, TextRange range)
    {
        return addConversionWarning(expected,got,range,null);
    }

    private static ErrorMessageList addConversionWarning(String expected, SmSequenceType got, TextRange range, String fixingXPathFn)
    {
        String gname = getPrimitiveTypeName(got);
        if (range==null)
        {
            range = new TextRange(0,0);
        }
        String msg = ResourceBundleManager.getMessage(MessageCode.CANNOT_CONVERT_PRIMITIVE_AS_STRING_GOT_EXPECTED,gname,expected);
        ErrorMessage em = new ErrorMessage(ErrorMessage.TYPE_WARNING,msg,range);
        if (fixingXPathFn!=null)
        {
            if (range.getLength()>0)
            {
                em.setFilter(new SurroundWithFnXPathFilter(range,fixingXPathFn));
            }
        }

        return new ErrorMessageList(em);
    }

    private static String getPrimitiveTypeName(SmSequenceType got)
    {
        SmType t = SmSequenceTypeSupport.stripOccursAndParens(got).getSimpleType();
        String name;
        if (t==null)
        {
            // bug, shouldn't happen.
            name = "<<unknown: " + got;
        }
        else
        {
            name = t.getName();
        }
        return name;
    }

    /*private static boolean checkNumber(XType got, List errors, Expr exprForError)
    {
        return checkNumber(got,SimpleXTypes.ANY_NUMBER,errors,exprForError);
    }*/

    /**
     * Checks number primitive ignoring cardinality, previous error issues.
     */
    private static ErrorMessageList checkTypeNumber(SmSequenceType got, SmSequenceType expected, TextRange range, XPathCheckArguments args, boolean serialized)
    {
        // Since we're doing double duty on serialized versus unserialized (rules are pretty much the same), use
        // encoded values here; they give better, more accurate warnings.
        SmSequenceType gotEncoded = got.encodedValue();
        if (SmSequenceTypeSupport.isNumber(gotEncoded))
        {
            if (serialized)
            {
                //
                boolean gotIsInteger = SmSequenceTypeSupport.isIntegerNumber(gotEncoded);
                boolean expectedIsInteger = SmSequenceTypeSupport.isIntegerNumber(expected);
                boolean isXPathNumber = SmSequenceTypeSupport.isNumber(got); // if the 'got' is actually an XPath number,
                // don't issue warning because it's formatted using reasonable rounding rules & because, in XPath 1.0,
                // numbers are treated as doubles, we would have way too many warnings on this. However, those that
                // are just floats encoded as strings will ALWAYS fail because they are encoded canonically w/ exponents.
                if (!gotIsInteger && expectedIsInteger && !isXPathNumber)
                {
                    // oh, big problem!, we're serializing through a string from a float (etc.) to an int, so we're probably
                    // going to have an issue.
                    return addConversionWarning(expected,gotEncoded,range,"round");
                }
            }
            return ErrorMessageList.EMPTY_LIST;
        }
        if (SmSequenceTypeSupport.isBoolean(gotEncoded))
        {
            return addConversionWarning(expected,gotEncoded,range);
        }
        if (SmSequenceTypeSupport.isBinary(gotEncoded))
        {
            return addConversionWarning(expected,gotEncoded,range);
        }
        if (!serialized)
        {
            if (args.getIncludeAutoCast())
            {
                return addConversionWarning("number",range);
            }
        }
        return ErrorMessageList.EMPTY_LIST;
    }

    private static ErrorMessageList checkValueConstant(SmSequenceType got, SmType type, TextRange exprForError, SerializationContext sc)
    {
       XmlSequence sequence = got.getConstantValue();
       if(sequence != null) {
          XmlItem firstItem = sequence.getItem(0);
          XmlTypedValue value = firstItem.getTypedValue();
          if (value!=null)
           {
               try
               {
                   if (sc==null)
                   {
                       //WCETODO bogus right now; won't properly validate qnames, but don't want to NPE.
                       // SerializationContext seems to have too much stuff --- function namespaces???
                       sc = DEFAULT_CONTEXT;
                   }
                   type.validate(value,sc);
                   // passed if it gets here...
               }
               catch (SimpleTypeCompilerException ce)
               {
                   return addWarning(ce.getMessage(),exprForError);
               }
           }
       }
       return ErrorMessageList.EMPTY_LIST;
    }

    /*
    private static boolean isNumber(SmType type) {
        if (type==null) return false; // node set.
        String vt = type.getValueType();
        if (vt==null) return false;
        // @@ incorrect:
        return vt.equals(SmType.VALUE_TYPE_INTEGER) || vt.equals(SmType.VALUE_TYPE_FLOAT) || vt.equals(SmType.VALUE_TYPE_DECIMAL);
    }

    private static boolean isDateTime(SmType type) {
        if (type==null) return false; // node set.
        String vt = type.getValueType();
        if (vt==null) return false;
        return vt.equals(SmType.VALUE_TYPE_DATETIME);
    }

    private static boolean isString(SmType type) {
        if (type==null) return false; // node set.
        String vt = type.getValueType();
        if (vt==null) return false;
        return vt.equals(SmType.VALUE_TYPE_STRING);
    }

    private static boolean isBinary(SmType type) {
        if (type==null) return false; // node set.
        String vt = type.getValueType();
        if (vt==null) return false;
        return vt.equals(SmType.VALUE_TYPE_BINARY);
    }*/

    /**
     * Type checks a boolean against a got type assuming string-conversion semantics.<br>
     * So, for example the string type 'true' will be converted to a boolean by the boolean(stringarg) function.<br>
     * @return true if there were no errors (warnings allowed).
     *
    public static ErrorMessageList checkBoolean(XType type, TextRange exprForError)
    {
        if (XTypeSupport.isPreviousError(type))
        {
            return ErrorMessageList.EMPTY_LIST;
        }
        return checkTypeBoolean(type,exprForError);
    }*/

    private static ErrorMessageList checkTypeBooleanGeneric(SmSequenceType got, TextRange exprForError, boolean serialized)
    {
        if (serialized)
        {
            return checkTypeBooleanSerialized(got,exprForError);
        }
        else
        {
            return checkTypeBooleanConversion(got,exprForError);
        }
    }

    private static ErrorMessageList checkTypeBooleanSerialized(SmSequenceType got, TextRange exprForError)
    {
        if (SmSequenceTypeSupport.isNodeSet(got))
        {
            return checkMustBeOptional(got,exprForError,true);
        }
        if (SmSequenceTypeSupport.isPrimitiveBoolean(got))
        {
            return ErrorMessageList.EMPTY_LIST;
        }
        if (SmSequenceTypeSupport.isString(got))
        {
            // It's serialized, so we aren't checking a constant...
            return ErrorMessageList.EMPTY_LIST;
        }
        if (SmSequenceTypeSupport.isNumber(got))
        {
            //WCETODO this actually probably should be an error/warning (unless it is 1 or 0)... maybe add more selective test.
            return ErrorMessageList.EMPTY_LIST;
        }
        // TODO more checks.
        return ErrorMessageList.EMPTY_LIST;
    }

    /**
     * Type checks a boolean against the boolean() conversion function.<br>
     * So, for example the string type 'false' will be converted to a boolean by a string-length and yield 'true'
     * (cool, huh).
     */
    private static ErrorMessageList checkTypeBooleanConversion(SmSequenceType type,
                                                               TextRange exprForError)
    {
        if (SmSequenceTypeSupport.isPreviousError(type))
        {
            return ErrorMessageList.EMPTY_LIST;
        }
        if (SmSequenceTypeSupport.isNodeSet(type))
        {
            return checkMustBeOptional(type,exprForError,true);
        }
        if (SmSequenceTypeSupport.isString(type))
        {
            if (type.getConstantValue()!=null)
            {
                XPathFilter filter = null;
                XmlSequence seq = type.getConstantValue();
                XmlItem item = seq.getItem(0);
                if(item instanceof XmlAtomicValue) {
                   String value = ((XmlAtomicValue)item).castAsString();
                   if ("true".equals(value) || "false".equals(value)) {
                       // Replace 'true' or 'false' with true() or false()
                       filter = new ReplacementXPathFilter(exprForError,Parser.parse(value + "()"));
                   }
                }
/*
                if (type.getConstantValue().equals("true") || type.getConstantValue().equals("false"))
                {
                    String fixingXPathFn = type.getConstantValue();

                    // Replace 'true' or 'false' with true() or false()
                    filter = new ReplacementXPathFilter(exprForError,Parser.parse(fixingXPathFn + "()"));
                }
*/
                return addWarning(ResourceBundleManager.getMessage(MessageCode.BOOLEAN_FROM_STRING_ALWAYS_TRUE),exprForError,filter);
            }
            return ErrorMessageList.EMPTY_LIST; // add more checking.
        }
        if (SmSequenceTypeSupport.isNumber(type))
        {
            return ErrorMessageList.EMPTY_LIST; // add more checking.
        }
        if (SmSequenceTypeSupport.isBooleanOrChoiceOfBooleans(type))
        {
            if (type.getConstantValue()!=null)
            {
//                 String v = type.getConstantValue();
//               if ( "true".equals(v))
                if (SMDT.BOOLEAN.equals(type))
                {
                    return addWarning(ResourceBundleManager.getMessage(MessageCode.BOOLEAN_ALWAYS_TRUE),exprForError);
                }
            }
            return ErrorMessageList.EMPTY_LIST; // add more checking.
        }
        return addWarning(ResourceBundleManager.getMessage(MessageCode.BOOLEAN_ALWAYS_TRUE),exprForError);
    }

    /**
     * A check that a type is optional for converting into booleans (WCETODO doc better; maybe rename)
     */
    public static ErrorMessageList checkMustBeOptional(SmSequenceType type, TextRange exprForError, boolean expectedResult)
    {
        SmCardinality card = type.quantifier();
        if (card.getMinOccurs()>0)
        {
            if (expectedResult)
            {
                return addWarning(ResourceBundleManager.getMessage(MessageCode.BOOLEAN_ALWAYS_TRUE),exprForError);
            }
            else
            {
                return addWarning(ResourceBundleManager.getMessage(MessageCode.BOOLEAN_ALWAYS_FALSE),exprForError);
            }
        }
        return ErrorMessageList.EMPTY_LIST;
    }

    /**
     * Auto-cast is a lower-level warning than the other conversion warnings --- maybe eliminate altogether, currently
     * doesn't show up anywhere.
     * @param autocastFn
     * @param optionalExpr
     * @return
     */
    private static ErrorMessageList addConversionWarning(String autocastFn, TextRange optionalExpr)
    {
        if (optionalExpr==null)
        {
            optionalExpr = new TextRange(0,0);
        }
        String msg = ResourceBundleManager.getMessage(MessageCode.AUTOCAST_INFORMATION,autocastFn);
        return new ErrorMessageList(new ErrorMessage(ErrorMessage.TYPE_AUTOCAST,msg,autocastFn,optionalExpr));
    }

    private static ErrorMessageList addWarning(String msg, TextRange optionalRange)
    {
        return addWarning(msg,optionalRange,null);
    }

    private static ErrorMessageList addWarning(String msg, TextRange optionalRange, XPathFilter fixingFilter)
    {
        if (optionalRange==null)
        {
            optionalRange = new TextRange(0,0);
        }
        ErrorMessage em = new ErrorMessage(ErrorMessage.TYPE_WARNING,msg,optionalRange);
        em.setFilter(fixingFilter);
        return new ErrorMessageList(em);
    }

    private static ErrorMessageList cardinalityCheck(SmSequenceType got, SmSequenceType expected, TextRange expr)
    {
        if (SmSequenceTypeSupport.isPreviousError(expected) || SmSequenceTypeSupport.isPreviousError(got))
        {
            return ErrorMessageList.EMPTY_LIST;
        }
        SmCardinality expectedc = expected.quantifier();
        return cardinalityCheck(got,expectedc.getMinOccurs(),expectedc.getMaxOccurs(),expr);
    }

    public static ErrorMessageList cardinalityCheck(SmSequenceType type, int expectedMin, int expectedMax, TextRange range)
    {
        if (SmSequenceTypeSupport.isPreviousError(type))
        {
            return ErrorMessageList.EMPTY_LIST;
        }
        SmCardinality gotc = type.quantifier();
        if (!gotc.isKnown())
        {
            // since not known, don't issue any warnings:
            return ErrorMessageList.EMPTY_LIST;
        }
        int gmax = gotc.getMaxOccurs();
        if (gmax==0)
        {
            return addWarning(ResourceBundleManager.getMessage(MessageCode.NULL_CONTENT),range);
        }
        if (gmax>expectedMax)
        {
            if (expectedMax==1)
            {
                return addWarning(ResourceBundleManager.getMessage(MessageCode.REPEATING_IN_NON_REPEATING),range);
            }
            return addWarning(ResourceBundleManager.getMessage(MessageCode.EXCEEDS_MAXIMUM_OCCURRENCE),range);
        }
        if (gmax<=1 && expectedMax==SmParticle.UNBOUNDED && expectedMin>1)
        {
            return addWarning(ResourceBundleManager.getMessage(MessageCode.EXPECTED_MORE_THAN_ONE),range);
        }
        return ErrorMessageList.EMPTY_LIST;
    }

    /**
     * Although we allow more 0,1, it is non-sense to pass in something that can never have more than 1; check for that.
     */
    public static ErrorMessageList checkMoreThanOne(SmSequenceType got, TextRange expr)
    {
        if (SmSequenceTypeSupport.isPreviousError(got))
        {
            return ErrorMessageList.EMPTY_LIST;
        }
        SmCardinality q = got.quantifier();
        if (q.getMaxOccurs()<=1)
        {
            // WCETODO -- maybe slightly different message than above.
            return addWarning(ResourceBundleManager.getMessage(MessageCode.EXPECTED_MORE_THAN_ONE),expr);
        }
        return ErrorMessageList.EMPTY_LIST;
    }

    private static SerializationContext DEFAULT_CONTEXT = new SerializationContext()
    {
        public String getDefaultNamespaceForElementAndTypeNames()
        {
            return null;
        }

        public String getDefaultNamespaceForFunctionNames()
        {
            return null;
        }

        public String getPrefixForNamespaceURI(String namespaceURI) throws NamespaceToPrefixResolver.NamespaceNotFoundException
        {
            return null;
        }

        public String getNamespaceURIForPrefix(String prefix) throws PrefixToNamespaceResolver.PrefixNotFoundException
        {
            return null;
        }

        public String getURI(String prefix)
        {
            return null;
        }
    };
}
