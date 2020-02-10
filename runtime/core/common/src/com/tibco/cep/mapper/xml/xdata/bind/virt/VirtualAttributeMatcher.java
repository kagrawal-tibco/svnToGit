package com.tibco.cep.mapper.xml.xdata.bind.virt;

import com.tibco.cep.mapper.xml.xdata.bind.AttributeBinding;
import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.CancelChecker;
import com.tibco.cep.mapper.xml.xdata.bind.IfBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TextBinding;
import com.tibco.cep.mapper.xml.xdata.bind.ValueOfBinding;
import com.tibco.xml.data.primitive.ExpandedName;

/**
 * Matcher for generating {@link com.tibco.cep.mapper.xml.xdata.bind.virt.VirtualAttributeBinding}.
 */
public class VirtualAttributeMatcher implements VirtualXsltMatcher
{
    public static final VirtualXsltMatcher INSTANCE = new VirtualAttributeMatcher();

    private VirtualAttributeMatcher()
    {
    }

    public boolean matches(Binding at)
    {
        if (!(at instanceof AttributeBinding) && !(at instanceof VirtualAttributeBinding)) // don't re-virtualize.
        {
            return isImplicitIf(at) || VirtualElementMatcher.isNilToOptional(at,true);
        }
        return true;
    }

    private boolean isImplicitIf(Binding at)
    {
        if (at instanceof IfBinding && at.getChildCount()==1 && at.getChild(0) instanceof AttributeBinding)
        {
            AttributeBinding eb = (AttributeBinding) at.getChild(0);
            if (eb instanceof VirtualAttributeBinding) // don't revirtualize
            {
                return false;
            }
            String iff = at.getFormula();
            if (eb.getChildCount()>0 && eb.getChild(eb.getChildCount()-1) instanceof ValueOfBinding)
            {
                ValueOfBinding vb = (ValueOfBinding) eb.getChild(eb.getChildCount()-1);
                String f = vb.getFormula();
                if (iff!=null && iff.equals(f))
                {
                    return true;
                }
            }
        }
        return false;
    }

    public VirtualXsltElement virtualize(Binding at, BindingVirtualizer virtualizer, CancelChecker cancelChecker)
    {
        VirtualDataComponentCopyMode copyMode;
        if (isImplicitIf(at))
        {
            at = at.getChild(0);
            copyMode = VirtualDataComponentCopyMode.OPTIONAL_TO_OPTIONAL;
        }
        else
        {
            if (VirtualElementMatcher.isNilToOptional(at,true))
            {
                at = at.getChild(0);
                copyMode = VirtualDataComponentCopyMode.NIL_TO_OPTIONAL;
            }
            else
            {
                copyMode = VirtualDataComponentCopyMode.REQUIRED_TO_REQUIRED;
            }
        }
        AttributeBinding eb = (AttributeBinding) at;
        VirtualAttributeBinding veb;
        if (!eb.isExplicitXslRepresentation())
        {
            veb = new VirtualAttributeBinding(eb.getElementInfo(),eb.getName());
            veb.setFormula(at.getFormula()); // the AVT
        }
        else
        {
            veb = new VirtualAttributeBinding(eb.getElementInfo(),eb.getExplicitNamespaceAVT(),eb.getExplicitNameAVT());
        }
        boolean isCollapsing = BindingDisplayCollapseUtils.isCollapsingFinalBinding(eb);
        veb.setHasInlineFormula(isCollapsing);
        veb.setCopyMode(copyMode);
        for (int i=0;i<at.getChildCount();i++)
        {
            Binding c = at.getChild(i);
            if (BindingDisplayCollapseUtils.isCollapsableBinding(c) && isCollapsing)
            {
                String f = c.getFormula();
                if (f==null) f="";
                veb.setFormula(f);
                veb.setHasInlineFormula(true);
                veb.setInlineIsText(c instanceof TextBinding);
            }
            else
            {
                veb.addChild(virtualizer.virtualize(c,cancelChecker));
            }
        }
        return veb;
    }

    public Binding normalize(Binding at)
    {
        return at;
    }

    public static boolean checkElement(Binding at, ExpandedName elname)
    {
        if (at.getName().equals(elname))
        {
            return true;
        }
        return false;
    }

    public static Binding getChildElement(Binding at, ExpandedName elname)
    {
        int cc = at.getChildCount();
        for (int i=0;i<cc;i++)
        {
            if (checkElement(at.getChild(i),elname))
            {
                return at.getChild(i);
            }
        }
        return null;
    }
}

