package com.tibco.cep.mapper.xml.xdata.xpath.expr;

import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;

/**
 */
public abstract class AxisExpr extends Expr
{
    private String mAxisWhitespace;

    public AxisExpr(TextRange range, String axisWhitespace, String trailingWhitespace) {
        super(range, trailingWhitespace);
        if (axisWhitespace==null) {
            mAxisWhitespace = "";
        } else {
            mAxisWhitespace = axisWhitespace;
        }
    }

    public final Expr[] getChildren() {
        return NO_CHILDREN;
    }

    /**
     * The node type for this axis, using org.w3c.dom.Node
     */
    public int getPrincipalNodeType() {
        return org.w3c.dom.Node.ELEMENT_NODE;
    }

    public abstract boolean hasNonSubTreeTraversal();
    public abstract boolean isMultiLevel();

    public final String getExprValue() {
        // no content.
        return null;
    }

    public final void format(StringBuffer toBuffer, int style) {
        toBuffer.append(getAxisName());
        if (style==STYLE_TO_EXACT_STRING) {
            toBuffer.append(mAxisWhitespace);
        }
        toBuffer.append("::");
        if (style==STYLE_TO_EXACT_STRING) {
            toBuffer.append(getWhitespace());
        }
    }

    /*protected final XType nameTestIncomplete(EvalTypeInfo info)
    {
        info.addError(new ErrorMessage(ErrorMessage.TYPE_UNCHECKED,"Axis:" + getAxisName(),getTextRange()));
        return info.recordReturnType(this,SimpleXTypes.PREVIOUS_ERROR);
    }*/

    public final String getRepresentationClosure() {
        return mAxisWhitespace;
    }

    public abstract String getAxisName();
}
