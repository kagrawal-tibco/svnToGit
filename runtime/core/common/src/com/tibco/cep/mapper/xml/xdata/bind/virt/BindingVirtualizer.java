package com.tibco.cep.mapper.xml.xdata.bind.virt;

import java.util.ArrayList;

import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.CancelChecker;
import com.tibco.cep.mapper.xml.xdata.bind.ForEachBinding;
import com.tibco.cep.mapper.xml.xdata.bind.ForEachGroupBinding;
import com.tibco.cep.mapper.xml.xdata.bind.IfBinding;
import com.tibco.cep.mapper.xml.xdata.bind.SortBinding;
import com.tibco.cep.mapper.xml.xdata.bind.WhenBinding;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprUtilities;

/**
 * Performs basic pattern matching from 'real' xsl elements to a combination of 'real' and 'virtual' ones.<br>
 * Also does character escaping.
 */
public class BindingVirtualizer
{
    private ArrayList m_firstPassMatchers = new ArrayList();
    protected ArrayList m_secondPassMatchers = new ArrayList();

    public static final BindingVirtualizer INSTANCE = new BindingVirtualizer();

    protected BindingVirtualizer()
    {
        // Must come before VirtualElementMatcher...
        m_firstPassMatchers.add(TypeCopyOfMatcher.INSTANCE);

        m_firstPassMatchers.add(VirtualElementMatcher.INSTANCE);
        m_firstPassMatchers.add(VirtualAttributeMatcher.INSTANCE);
        m_firstPassMatchers.add(VirtualForEachGroupMatcher.INSTANCE);
    }

    public Binding virtualize(Binding b, CancelChecker c)
    {
        Binding fpb = virtualizeFirstPass(b,c);
        for (int i=0;i<m_secondPassMatchers.size();i++)
        {
            VirtualXsltMatcher m = (VirtualXsltMatcher) m_secondPassMatchers.get(i);
            if (m.matches(fpb))
            {
                return (Binding) m.virtualize(b,this,c);
            }
        }
        return fpb;
    }

    private Binding virtualizeFirstPass(Binding b, CancelChecker c)
    {
        for (int i=0;i<m_firstPassMatchers.size();i++)
        {
            VirtualXsltMatcher m = (VirtualXsltMatcher) m_firstPassMatchers.get(i);
            if (m.matches(b))
            {
                Binding r = (Binding)m.virtualize(b,this,c);
                if (shouldEscapeFormula(r))
                {
                    r.setFormula(ExprUtilities.escapeXPathCharacterRefs(r.getFormula()));
                }
                return r;
            }
        }
        Binding cloned = b.cloneShallow();
        if (shouldEscapeFormula(cloned))
        {
            cloned.setFormula(ExprUtilities.escapeXPathCharacterRefs(cloned.getFormula()));
        }
        // no match, recurse:
        virtualizeChildren(b,cloned,c);
        return cloned;
    }

    private static boolean shouldEscapeFormula(Binding b)
    {
        if (b instanceof IfBinding || b instanceof WhenBinding || b instanceof ForEachBinding || b instanceof ForEachGroupBinding || b instanceof SortBinding)
        {
            // xpaths.
            return true;
        }
        if (b instanceof VirtualElementBinding)
        {
            VirtualElementBinding veb = (VirtualElementBinding) b;
            if (veb.getHasInlineFormula() && !veb.getInlineIsText())
            {
                return true;
            }
            // none or text.
            return false;
        }
        if (b instanceof VirtualAttributeBinding)
        {
            VirtualAttributeBinding vab = (VirtualAttributeBinding) b;
            if (vab.isExplicitXslRepresentation() && vab.getHasInlineFormula() && !vab.getInlineIsText())
            {
                return true;
            }
            else
            {
                // text or none or AVT; WCETODO handle avts here, too.
                return false;
            }
        }
        return false;
    }

    public void virtualizeChildren(Binding from, Binding to, CancelChecker cancelChecker)
    {
        int cc = from.getChildCount();
        for (int i=0;i<cc;i++)
        {
            if (cancelChecker.hasBeenCancelled())
            {
                break;
            }
            Binding c = from.getChild(i);
            Binding vc = virtualize(c,cancelChecker);
            to.addChild(vc);
        }
    }

    public static Binding normalize(Binding b, Binding parent)
    {
        if (b instanceof VirtualXsltElement)
        {
            if (shouldEscapeFormula(b))
            {
                String nf = ExprUtilities.unescapeXPathCharacterRefs(b.getFormula());
                if (nf!=null && !nf.equals(b.getFormula()))
                {
                    // replace new formula:
                    /*
                     * 1-1VBVQ3  - comment out cloneShallow()
                     * All eventual calls to normalize will be done using the original
                     * binding object.   Shallow objects does not have
                     * children to recurse, loses attribute childs for an element
                     */
                    // b = b.cloneShallow();
                    b.setFormula(nf);
                }
            }
            return ((VirtualXsltElement)b).normalize(parent);
        }
        Binding cloned = b.cloneShallow();
        if (shouldEscapeFormula(cloned))
        {
            cloned.setFormula(ExprUtilities.unescapeXPathCharacterRefs(cloned.getFormula()));
        }
        if (parent!=null)
        {
            parent.addChild(cloned);
        }
        // no match, recurse:
        int cc = b.getChildCount();
        for (int i=0;i<cc;i++)
        {
            Binding c = b.getChild(i);
            normalize(c,cloned);
        }
        return cloned;
    }
}

