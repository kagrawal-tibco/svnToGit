package com.tibco.cep.mapper.xml.xdata.bind.virt;

import com.tibco.cep.mapper.xml.xdata.bind.AttributeBinding;
import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.ChooseBinding;
import com.tibco.cep.mapper.xml.xdata.bind.CommentBinding;
import com.tibco.cep.mapper.xml.xdata.bind.CopyOfBinding;
import com.tibco.cep.mapper.xml.xdata.bind.ElementBinding;
import com.tibco.cep.mapper.xml.xdata.bind.OtherwiseBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TextBinding;
import com.tibco.cep.mapper.xml.xdata.bind.ValueOfBinding;
import com.tibco.cep.mapper.xml.xdata.bind.WhenBinding;
import com.tibco.xml.data.primitive.QName;

/**
 * Methods used by the GUI for indicating the element/value-of collapse (in the gui) can take place.
 * (WCETODO rename; is really just virtualization support)
 */
public final class BindingDisplayCollapseUtils
{
    public static boolean isCollapsingFinalBinding(Binding binding)
    {
        if (!(binding instanceof ElementBinding || binding instanceof AttributeBinding)) {
            // only collapse under these...
            return false;
        }
        int sz = binding.getChildCount();
        if (sz<1) {
            return false;
        }
        Binding last = binding.getChild(sz-1);
        if (!isCollapsableBinding(last)) {
            return false;
        }
        for (int i=0;i<sz-1;i++) {
            Binding b = binding.getChild(i);
            if (preventsCollapsableBinding(b))
            {
                return false;
            }
        }
        return true;
    }

    private static boolean preventsCollapsableBinding(Binding b) {
        if (b instanceof ValueOfBinding)
        {
            return true;
        }
        if (b instanceof TextBinding)
        {
            return true;
        }
        if (b instanceof ElementBinding)
        {
            return true;
        }
        if (b instanceof AttributeBinding)
        {
            return false;
        }
        if (b instanceof CopyOfBinding)
        {
            return false;
        }
        if (b instanceof CommentBinding)
        {
            return false;
        }
        // Check for contents:
        for (int i=0;i<b.getChildCount();i++)
        {
            if (preventsCollapsableBinding(b.getChild(i)))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * True if the binding is eligible to be the 'leaf' part of a collapse (i.e. folded into its parent).
     * This is true only for text and value-ofs currently.
     */
    public static boolean isCollapsableBinding(Binding b) {
        if (b instanceof ValueOfBinding || b instanceof TextBinding) {
            return b.getChildCount()==0;
        }
        if (isOptionalToNilPattern(b) && b.getParent() instanceof ElementBinding)
        {
            return true;
        }
        return false;
    }

    private static boolean isOptionalToNilPattern(Binding at)
    {
        // Look for:
        //    <xsl:choose>
        //       <xsl:when test="exists(XXX)"/>
        //          <xsl:value-of select="XXX"/>
        //       </xsl:when>
        //       <xsl:otherwise>
        //          <xsl:attribute name="xsi:nil">true</xsl:attribute>
        //       </xsl:otherwise>
        //    </xsl:choose>
        if (!(at instanceof ChooseBinding) || at.getChildCount()!=2)
        {
            return false;
        }
        Binding o1 = at.getChild(0);
        Binding o2 = at.getChild(1);
        if (!(o1 instanceof WhenBinding) || o1.getChildCount()!=1)
        {
            return false;
        }
        if (!(o2 instanceof OtherwiseBinding) || o2.getChildCount()!=1)
        {
            return false;
        }
        Binding c1 = o1.getChild(0);
        Binding c2 = o2.getChild(0);
        if (!(c1 instanceof ValueOfBinding))
        {
            return false;
        }
        if (!(c2 instanceof AttributeBinding))
        {
            return false;
        }
        String vof = c1.getFormula();
        AttributeBinding av = (AttributeBinding) c2;
        String n = av.getExplicitNameAVT();
        QName qn = new QName(n);
        if (!qn.getLocalName().equals("nil"))
        {
            return false;
        }
        //WCETODO prefix lookup... if (qn.getPrefix())
        if (av.getChildCount()!=1 || !(av.getChild(0) instanceof TextBinding))
        {
            return false;
        }
        if (!"true".equals(av.getChild(0).getFormula()))
        {
            return false;
        }
        // Are the formulas compatible:
        String whenFormula = o1.getFormula().trim();
        if (!whenFormula.startsWith("exists(") || !whenFormula.endsWith(")"))
        {
            return false;
        }
        String rf = whenFormula.substring("exists(".length(),whenFormula.length()-1); // strip off exists().
        if (vof==null)
        {
            return false; // just in case.
        }
        if (!rf.trim().equals(vof.trim()))
        {
            return false;
        }
        // Finally, we have a match:
        return true;
    }
}

