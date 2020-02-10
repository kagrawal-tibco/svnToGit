package com.tibco.cep.mapper.xml.xdata.xpath.expr;

import java.math.BigDecimal;

import com.tibco.cep.mapper.util.ResourceBundleManager;
import com.tibco.cep.mapper.xml.xdata.xpath.ErrorMessage;
import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprTypeCode;
import com.tibco.cep.mapper.xml.xdata.xpath.MessageCode;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.util.RuntimeWrapException;
import com.tibco.xml.data.primitive.XmlAtomicValueParseException;
import com.tibco.xml.data.primitive.values.XsDouble;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.schema.xtype.SmSequenceTypeFactory;

/**
 */
public final class NumberExpr extends Expr
{
    private final String mLiteralString; // encodes a number. (??? maybe change to hold a # directly)

    public NumberExpr(String literalString, TextRange range, String trailingWhitespace) {
        super(range, trailingWhitespace);
        mLiteralString = literalString;
    }

    public SmSequenceType evalType(ExprContext context, EvalTypeInfo info) {
        // see if this is going to be lossy:
        checkNumber(mLiteralString,info,getTextRange());
       SmSequenceType retVal = null;
       try {
          retVal = info.recordReturnType(this,SmSequenceTypeFactory.createAtomic(XSDL.DOUBLE,
                                                                                       XsDouble.compile(mLiteralString)));
       }
       catch (XmlAtomicValueParseException e) {
          throw new RuntimeWrapException(e);
       }
       return retVal;
    }

    static void checkNumber(String nvalue, EvalTypeInfo info, TextRange range)
    {
        if (!info.getXPathCheckArguments().getIncludePrimitiveTypeConversions()) // disable based on this flag.
        {
            return;
        }
        BigDecimal bd = new BigDecimal(nvalue);
        double val = bd.doubleValue();
        String fdouble = XsDouble.legacyForm(val); // not legacy, 1.0
        BigDecimal bd2 = new BigDecimal(fdouble);
        BigDecimal scaled = bd2.setScale(bd.scale(),BigDecimal.ROUND_HALF_UP);
        if (!bd.equals(scaled))
        {
            String msg = ResourceBundleManager.getMessage(ResourceBundleManager.getMessage(MessageCode.LOSS_OF_PRECISION_ROUNDED,""+val));
            info.addError(new ErrorMessage(ErrorMessage.TYPE_WARNING,msg,range));
        }
    }

    public String getExprValue() {
        return mLiteralString;
    }

    public int getExprTypeCode() {
        return ExprTypeCode.EXPR_NUMBER;
    }

    public Expr[] getChildren() {
        return NO_CHILDREN;
    }

    public void format(StringBuffer toBuffer, int style) {
        toBuffer.append(mLiteralString);
        if (style==STYLE_TO_EXACT_STRING) {
            toBuffer.append(getWhitespace());
        }
    }
}
