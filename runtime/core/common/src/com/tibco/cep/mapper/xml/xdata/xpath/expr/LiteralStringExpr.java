package com.tibco.cep.mapper.xml.xdata.xpath.expr;

import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprTypeCode;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.cep.mapper.xml.xdata.xpath.Token;
import com.tibco.xml.data.primitive.values.XsString;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.schema.xtype.SmSequenceTypeFactory;

/**
 */
public final class LiteralStringExpr extends Expr
{
    private final String mLiteralString;
    private final boolean mSingleQuotes; // if not null, formats using single quotes, otherwise, double quotes.

    public LiteralStringExpr(String literalString, TextRange range, String trailingWhitespace, boolean singleQuotes) {
        super(range, trailingWhitespace);
        mLiteralString = literalString;
        mSingleQuotes = singleQuotes;
    }

    public String getExprValue() {
        return mLiteralString;
    }

    public String getRepresentationClosure() {
        return mSingleQuotes ? "single-quotes" : null;
    }

    public SmSequenceType evalType(ExprContext context, EvalTypeInfo info)
    {
/* Commented out 3/22/04 by jtb (see defect 1-4TMPPX); the call to CharacterEscapeUtils.getFirstEscapeCharacterIssue
shows a false positive for an unescaped ampersand.  The method is only looking to see
that every ampersand is matched with a semi-colon; however, our UI forces unescaped
characters upon our users, and the resulting, unescaped ampersands look like errors to
the getFirstEscapeCharacterIssue method.

        if (info.getXPathCheckArguments().getHasXMLEscapeLiterals())
        {
            // check the string for issues...
            int idx = CharacterEscapeUtils.getFirstEscapeCharacterIssue(mLiteralString);
            if (idx>=0)
            {
                TextRange etr = getTextRange();
                int spos = etr.getStartPosition()+1+idx;
                // Because an expression may or may not have been parsed, it may not have correct end position,
                // so make sure start>end with a simple check:
                int epos = Math.max(etr.getEndPosition()-1,spos);
                TextRange tr = new TextRange(spos,epos);
                info.addError(new ErrorMessage(ErrorMessage.TYPE_ERROR,Xmlui_MMessageBundle.getMessage(MessageCode.ILLEGAL_CHARACTER_ESCAPE),tr));
            }
        }
*/
        return info.recordReturnType(this,SmSequenceTypeFactory.createAtomic(XSDL.STRING,
                                                                             new XsString(mLiteralString)));
    }

    public int getExprTypeCode() {
        return ExprTypeCode.EXPR_LITERAL_STRING;
    }

    public Expr[] getChildren() {
        return NO_CHILDREN;
    }

    public void format(StringBuffer toBuffer, int style) {
        if (style==STYLE_TO_EXACT_STRING) {
            if (!mSingleQuotes) {
                toBuffer.append(Token.formatLiteralString(mLiteralString));
            } else {
                toBuffer.append(Token.formatLiteralStringSingleQuoteDefault(mLiteralString));
            }
            toBuffer.append(getWhitespace());
        } else {
            toBuffer.append(Token.formatLiteralString(mLiteralString));
        }
    }
}
