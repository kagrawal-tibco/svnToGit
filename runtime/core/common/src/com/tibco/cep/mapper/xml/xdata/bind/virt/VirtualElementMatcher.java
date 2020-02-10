package com.tibco.cep.mapper.xml.xdata.bind.virt;

import com.tibco.cep.mapper.xml.xdata.bind.AVTUtilities;
import com.tibco.cep.mapper.xml.xdata.bind.AttributeBinding;
import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.BindingManipulationUtils;
import com.tibco.cep.mapper.xml.xdata.bind.BindingNamespaceManipulationUtils;
import com.tibco.cep.mapper.xml.xdata.bind.CancelChecker;
import com.tibco.cep.mapper.xml.xdata.bind.ChooseBinding;
import com.tibco.cep.mapper.xml.xdata.bind.CopyOfBinding;
import com.tibco.cep.mapper.xml.xdata.bind.ElementBinding;
import com.tibco.cep.mapper.xml.xdata.bind.IfBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TextBinding;
import com.tibco.cep.mapper.xml.xdata.bind.ValueOfBinding;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.PrefixToNamespaceResolver;
import com.tibco.xml.data.primitive.QName;
import com.tibco.xml.schema.flavor.XSDL;

public class VirtualElementMatcher implements VirtualXsltMatcher
{
    public static final VirtualXsltMatcher INSTANCE = new VirtualElementMatcher();

    private VirtualElementMatcher()
    {
    }

    public boolean matches(Binding at)
    {
        if (!(at instanceof ElementBinding) && !(at instanceof VirtualElementBinding)) // don't re-virtualize.
        {
            return isImplicitIf(at) || isNilToOptional(at,false); // false->element.
        }
        return true;
    }

    private boolean isImplicitIf(Binding at)
    {
        // Look for <xsl:if test="formula"/>
        //             <element><value-of select="formula"/>
        //          </xsl:if>
        if (at instanceof IfBinding && at.getChildCount()==1 && at.getChild(0) instanceof ElementBinding)
        {
            ElementBinding eb = (ElementBinding) at.getChild(0);
            if (eb instanceof VirtualElementBinding)
            {
                // don't revirtualize.
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

    static boolean isNilToOptional(Binding at, boolean attr)
    {
        if (!(at instanceof IfBinding) || at.getChildCount()!=1)
        {
            return false;
        }
        Binding o1 = at.getChild(0);
        if (!attr)
        {
            if (!(o1 instanceof ElementBinding) || o1 instanceof VirtualElementBinding)
            {
                // don't revirtualize.
                return false;
            }
        }
        else
        {
            if (!(o1 instanceof AttributeBinding) || o1 instanceof VirtualAttributeBinding)
            {
                // don't revirtualize.
                return false;
            }
        }
        int cc = o1.getChildCount();
        if (cc==0)
        {
            return false;
        }
        if (!(o1.getChild(cc-1) instanceof ValueOfBinding))
        {
            return false;
        }
        String f = o1.getChild(cc-1).getFormula();
        String of = at.getFormula();
        if (f==null || of==null)
        {
            return false; //just in case.
        }
        if (!of.startsWith(f))
        {
            return false; // shortcut.
        }
        String pfx;
        try
        {
            pfx = BindingNamespaceManipulationUtils.createNamespaceImporter(at).getPrefixForNamespaceURI(XSDL.INSTANCE_NAMESPACE);
        }
        catch (Exception e)
        {
            return false;
        }
        String mf = makeNilToOptionalString(f,pfx);
        if (!mf.equals(of))
        {
            return false;
        }
        // Finally, we have a match:
        return true;
    }

    /**
     * Creates the if-test formula used in the nil->optional pattern.
     * @param formula The base formula.
     * @param xsiPrefix The prefix for the xsi namespace.
     * @return The if-test formula.
     */
    public static String makeNilToOptionalString(String formula, String xsiPrefix)
    {
        //return formula + "/@" + xsiPrefix + ":nil!=(\"true\",\"1\")";
        // System.out.println("makeNilToOptionalString formula = "+formula);
        // formula = "$Map-Data-1/pfx7:root/pfx7:DOB"
        return "not(" + formula + "/@" + xsiPrefix + ":nil!=(\"true\",\"1\"))";
    }

    public VirtualXsltElement virtualize(Binding at, BindingVirtualizer virtualizer, CancelChecker cancelChecker)
    {
        VirtualElementBinding vel = virtualizePass1(at,virtualizer, cancelChecker);
        if (vel==null)
        {
            return null; // could be cancel.
        }
        if (vel.getHasInlineFormula() && !vel.getInlineIsText())
        {
            // Check for xsi:nil copying.
            // (The pattern is:)
            //
            // <element><attribute stuff/><xsl:copy-of select="(formula)/xsi:nil"/><xsl:value-of select="(formula)"/></element>
            if (vel.getChildCount()>0 && vel.getChild(vel.getChildCount()-1) instanceof CopyOfBinding)
            {
                String cof = vel.getChild(vel.getChildCount()-1).getFormula();
                String f = vel.getFormula();
                if (f!=null && cof!=null)
                {
                    if (cof.startsWith(f))
                    {
                        String r = cof.substring(f.length());
                        if (r.startsWith("/@") && r.endsWith(":nil"))
                        {
                            String pfx = r.substring(2,r.length()-4); // chop off /@ and :nil.
                            try
                            {
                                String ns = BindingNamespaceManipulationUtils.createNamespaceImporter(at).getNamespaceURIForPrefix(pfx);
                                if (XSDL.INSTANCE_NAMESPACE.equals(ns))
                                {
                                    // we have a match!
                                    // So remove the copy of & set copy nil to true.
                                    vel.removeChild(vel.getChildCount()-1);
                                    if (vel.getCopyMode()==VirtualDataComponentCopyMode.OPTIONAL_TO_OPTIONAL)
                                    {
                                        vel.setCopyMode(VirtualDataComponentCopyMode.OPTIONALNIL_TO_OPTIONALNIL);
                                    }
                                    else
                                    {
                                        vel.setCopyMode(VirtualDataComponentCopyMode.NIL_TO_NIL);
                                    }
                                }
                            }
                            catch (PrefixToNamespaceResolver.PrefixNotFoundException pnfe)
                            {
                                // not found, no match.
                            }
                        }
                    }
                }
            }
        }

        return vel;
    }

    private VirtualElementBinding virtualizePass1(Binding at, BindingVirtualizer virtualizer, CancelChecker cancelChecker)
    {
        VirtualDataComponentCopyMode copyMode = VirtualDataComponentCopyMode.REQUIRED_TO_REQUIRED;
        if (isImplicitIf(at))
        {
            copyMode = VirtualDataComponentCopyMode.OPTIONAL_TO_OPTIONAL;
            at = at.getChild(0);
        }
        else
        {
            if (isNilToOptional(at,false))
            {
                copyMode = VirtualDataComponentCopyMode.NIL_TO_OPTIONAL;
                at = at.getChild(0);
            }
        }
        ElementBinding eb = (ElementBinding) at;
        VirtualElementBinding veb;
        if (eb.isExplicitXslRepresentation())
        {
            veb = new VirtualElementBinding(eb.getElementInfo(),eb.getExplicitNamespaceAVT(),eb.getExplicitNameAVT());
        }
        else
        {
            veb = new VirtualElementBinding(eb.getElementInfo(),eb.getName());
        }
        boolean isCollapsing = BindingDisplayCollapseUtils.isCollapsingFinalBinding(eb);
        veb.setHasInlineFormula(isCollapsing);
        veb.setCopyMode(copyMode);
        ExpandedName xsiTypeSub = null;

        boolean hasExplicitNil = false;
        if (!isCollapsing)
        {
            hasExplicitNil = hasExplicitNil(eb);
        }
        veb.setExplicitNil(hasExplicitNil); // (These are mutex, but that's ok)

        for (int i=0;i<eb.getChildCount();i++)
        {
            Binding c = eb.getChild(i);
            boolean isLast = i==eb.getChildCount()-1;
            if (c instanceof VirtualDataComponentBinding)
            {
                RuntimeException re = new RuntimeException();
                re.printStackTrace(System.err); // how did this happen???
            }
            ExpandedName attrSub = BindingManipulationUtils.getXsiTypeSub(c);
            if (xsiTypeSub==null)
            {
                xsiTypeSub = attrSub;
            }
            else
            {
                // rare, weird, duplicate xsi:type, just leave it:
                attrSub = null;
            }
            boolean isExplicitNil = false;
            if (hasExplicitNil && isExplicitNilAttr(c,eb.isExplicitXslRepresentation()))
            {
                isExplicitNil = true;
            }
            if (isLast && isCollapsing)
            {
                String f;
                if (c instanceof ChooseBinding) // Optional to nil.
                {
                    f = c.getChild(0).getChild(0).getFormula();
                    veb.setCopyMode(VirtualDataComponentCopyMode.OPTIONAL_TO_NIL); //WCETODO what if already has surrounding if?
                }
                else
                {
                    f = c.getFormula();
                }
                if (f==null) f = ""; // just fix it if null...
                veb.setFormula(f);
                veb.setHasInlineFormula(true);
                veb.setInlineIsText(c instanceof TextBinding);
            }
            else
            {
                if (attrSub==null && !isExplicitNil)
                {
                    veb.addChild(virtualizer.virtualize(c,cancelChecker));
                }
            }
        }
        veb.setTypeSubstitution(xsiTypeSub);
        return veb;
    }

    private static boolean hasExplicitNil(ElementBinding b)
    {
        boolean explicit = b.isExplicitXslRepresentation();
        for (int i=0;i<b.getChildCount();i++)
        {
            if (isExplicitNilAttr(b.getChild(i),explicit))
            {
                return true;
            }
        }
        return false;
    }

    private static boolean isExplicitNilAttr(Binding b, boolean explicitXsl)
    {
        if (!(b instanceof AttributeBinding))
        {
            return false;
        }
        AttributeBinding ab = (AttributeBinding) b;
        if (explicitXsl)
        {
            // The element is explicit, so this should be too.
            String nsavt = ab.getExplicitNamespaceAVT();
            if (nsavt!=null)
            {
                return false; // only recognize qname version.
            }
            String nameAvt = ab.getExplicitNameAVT();
            if (AVTUtilities.isAVTString(nameAvt))
            {
                return false; // no AVTs.
            }
            QName qn = new QName(nameAvt);
            try
            {
                if (qn.getExpandedName(BindingNamespaceManipulationUtils.createNamespaceImporter(b)).equals(XSDL.ATTR_NIL.getExpandedName()))
                {
                    return checkNilValue(ab);
                }
            }
            catch (Exception e)
            {
                // don't deal w/ it.
            }
            return false;
        }
        else
        {
            // The element is literal, so this should be too.
            if (!ab.isExplicitXslRepresentation())
            {
                ExpandedName tn = ab.getName();
                if (tn.equals(XSDL.ATTR_NIL.getExpandedName()))
                {
                    return checkNilValue(ab);
                }
            }
        }
        return false;
    }

    private static boolean checkNilValue(AttributeBinding ab)
    {
        if (ab.isExplicitXslRepresentation())
        {
            // If only child is a text-tag (i.e. embedded text, not xsl:text())
            if (ab.getChildCount()==1 && ab.getChild(0) instanceof TextBinding && !((TextBinding)ab.getChild(0)).isFullTag())
            {
                return "true".equals(ab.getChild(0).getFormula());
            }
            else
            {
                return false;
            }
        }
        else
        {
            return "true".equals(ab.getFormula()); // only the 'true' string triggers it.
        }
    }
}

