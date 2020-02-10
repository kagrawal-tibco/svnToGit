package com.tibco.cep.mapper.xml.xdata.xpath.expr;

import com.tibco.cep.mapper.util.ResourceBundleManager;
import com.tibco.cep.mapper.xml.xdata.xpath.ErrorMessage;
import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprTypeCode;
import com.tibco.cep.mapper.xml.xdata.xpath.MessageCode;
import com.tibco.cep.mapper.xml.xdata.xpath.RenamespaceNameTestXPathFilter;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.cep.mapper.xml.xdata.xpath.XPathFilter;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.QName;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SMDT;
import com.tibco.xml.schema.xtype.SmSequenceTypeFactory;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

/**
 */
public final class NameTestExpr extends NodeTestExpr
{
    private final String mName;

    private static final ExpandedName STAR_STAR = ExpandedName.makeName("*","*");

    public NameTestExpr(String name, TextRange range, String trailingWhitespace) {
        super(range, trailingWhitespace);
        mName = name;
    }

    public SmSequenceType evalType(ExprContext context, EvalTypeInfo info) {
        ExpandedName ename = getExpandedName(context);
        if (ename!=null)
        {
            SmSequenceType ty = context.getInput().nameTest(ename);
            if (SmSequenceTypeSupport.isVoid(ty))
            {
                // See if we're in the mode where we're supposed to 'guess' namespaces (used for cool editor tricks, etc.)
                if (info.isRecordingNamespaceResolutions())
                {
                    // Ok, we're in that mode, try matching w/o regard to namespace:

                    // (Note: this probably goes away in favor of mechanism below --- this was an old mechanism)
                    QName qn = new QName(mName);
                    ExpandedName un = ExpandedName.makeName("*",qn.getLocalName());
                    SmSequenceType ty2 = context.getInput().nameTest(un);
                    if (!SmSequenceTypeSupport.isVoid(ty2))
                    {
                        // Aha, did match more, record what namespace it should have been:
                        ExpandedName ename2 = ty2.prime().getName();
                        if (ename2!=null)
                        {
                            info.recordNamespaceResolution(this,ename2.getNamespaceURI());
                        }
                        return ty2;
                    }
                }
                XPathFilter fix = null;
                if (info.getXPathCheckArguments().getInputDataChecking())
                {
                    if (info.getXPathCheckArguments().getCheckForNameTestRenameNamespace())
                    {
                        QName qn = new QName(mName);
                        ExpandedName un = ExpandedName.makeName("*",qn.getLocalName());
                        SmSequenceType ty2 = context.getInput().nameTest(un);
                        if (!SmSequenceTypeSupport.isVoid(ty2))
                        {
                            // Aha, did match more, add fix for it:
                            ExpandedName ename2 = ty2.prime().getName();

                            // Check for case where (1) incoming name has no prefix,
                            // (2) default namespace is declared in resolver -- that is the case
                            // or else we wouldn't have entered the very first code block,
                            // (3) incoming name cannot be resolved to that default namespace -- that
                            // is the case or else we wouldn't be in this code block, and
                            // (4) incoming name CAN be resolved to no namespace, i.e. a local element declaration
                            if(mName.indexOf(':') < 0 // check (1)
                                    && ename2 != null
                                    && ename2.getNamespaceURI() == null) { // check (4)
                               return ty2;
                           }
                            if (ename2!=null)
                            {
                                fix = new RenamespaceNameTestXPathFilter(getTextRange(),ename.getNamespaceURI(),ename2.getNamespaceURI());
                            }
                        }
                    }
                    String msg = ResourceBundleManager.getMessage(ResourceBundleManager.getMessage(MessageCode.NO_MATCHING),mName);
                    ErrorMessage me = new ErrorMessage(msg,getTextRange());
                    me.setFilter(fix);
                    info.addError(me);
                }

                // already recorded error, don't suffer more later:
                return SMDT.PREVIOUS_ERROR;
            }
            return ty;
        } else {
            String ns = info.getNamespaceResolution(this);
            if (ns!=null)
            {
                QName qn = new QName(mName);
                //WCETODO unhardcode.
                //WCETODO make fixer more standard.
                info.addError(new ErrorMessage(ErrorMessage.TYPE_ERROR,"Missing namespace for " + mName,ErrorMessage.CODE_MISSING_PREFIX,ns,qn.getPrefix(),getTextRange()));
            }
            else
            {
                //WCETODO unhardcode.
                info.addError(new ErrorMessage("Undefined prefix in name " + mName,getTextRange()));
            }
            return SMDT.PREVIOUS_ERROR;
        }
    }

    private ExpandedName getExpandedName(ExprContext ec)
    {
        // '*' really means match any namespace and any local-name, whereas the wildcard matching on
        // XType wants, logically *:*.
        if ("*".equals(mName))
        {
            return STAR_STAR;
        }
        QName qn = new QName(mName);
        ExpandedName ename = qn.getExpandedName(ec.getNamespaceMapper());
        return ename;
    }

    public ExprContext assertBooleanValue(ExprContext ec, boolean isTrue)
    {
        if (isTrue)
        {
            // boolean is true if there are any results, so insist on some results!
            ExpandedName ename = getExpandedName(ec);
            if (ename!=null)
            {
                SmSequenceType type = ec.getInput().nameTest(ename);
                //WCETODO robusticize this:
                SmSequenceType nt = SmSequenceTypeSupport.createWithItemRequired(type);
                return ec.createWithInput(nt);
            }
            return ec; // can't add
        }
        else
        {
            // insist on no results:
            QName qn = new QName(mName);
            ExpandedName ename = qn.getExpandedName(ec.getNamespaceMapper());
            if (ename!=null)
            {
                SmSequenceType type = ec.getInput().nameTest(ename);
                //WCETODO robusticize this:
                SmSequenceType nt = SmSequenceTypeFactory.createRepeats(type,SmCardinality.NONE).simplify(false);
                return ec.createWithInput(nt);
            }
            return ec; // can't add
        }
    }

    public ExprContext assertEvaluatesTo(ExprContext ec, SmSequenceType type, SmCardinality optionalSpecifiedCardinality)
    {
        ExpandedName ename = getExpandedName(ec);
        if (ename!=null)
        {
            SmSequenceType t = ec.getInput().assertNameTest(ename,type);
            SmSequenceType r;
            if (optionalSpecifiedCardinality!=null)
            {
                SmSequenceType st = SmSequenceTypeSupport.stripOccurs(type);
                if (optionalSpecifiedCardinality.equals(SmCardinality.EXACTLY_ONE))
                {
                    r = st;
                }
                else
                {
                    r = SmSequenceTypeFactory.createRepeats(st,optionalSpecifiedCardinality);
                }
            }
            else
            {
                r = t;
            }
            return ec.createWithInput(r);
        }
        else
        {
            return ec;
        }
    }

    public ExprContext assertMatch(ExprContext ec)
    {
        ExpandedName ename = getExpandedName(ec);
        if (ename!=null)
        {
            SmSequenceType t = SmSequenceTypeSupport.getElementInContext(ename,
                                                                         ec.getInput(),
                                                                         ec.getInputSmComponentProvider());
            return ec.createWithInput(t);
        }
        else
        {
            return ec;
        }
    }

    public ExprContext assertTypedValueEvaluatesTo(ExprContext ec, SmSequenceType type)
    {
        ExpandedName ename = getExpandedName(ec);
        if (ename!=null)
        {
            SmSequenceType typed = ec.getInput().nameTest(ename).assertTypedValue(type);
            SmSequenceType t2 = ec.getInput().assertNameTest(ename,typed);
            return ec.createWithInput(t2);
        }
        else
        {
            return ec;
        }
    }

    public int getExprTypeCode() {
        return ExprTypeCode.EXPR_NAME_TEST;
    }

    public String getExprValue() {
        return mName;
    }

    public String getTestString() {
        return mName;
    }

    public void format(StringBuffer toBuffer, int style) {
		if (style== STYLE_TO_STRING_NO_PREFIX) {
			QName qname = new QName(mName);
			toBuffer.append(qname.getLocalName());
		}
	    else {
		    toBuffer.append(mName);
	    }
        if (style==STYLE_TO_EXACT_STRING) {
            toBuffer.append(getWhitespace());
        }
    }
}
