/*******************************************************************************
 * Copyright 2001 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.xml.xdata.bind.virt;

import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.CancelChecker;
import com.tibco.cep.mapper.xml.xdata.bind.CopyOfBinding;
import com.tibco.cep.mapper.xml.xdata.bind.ElementBinding;
import com.tibco.xml.data.primitive.ExpandedName;

/**
 * Represents a type-copy-of operation which is a simple, inline XSLT template;
 */
public class TypeCopyOfMatcher implements VirtualXsltMatcher
{
    public static VirtualXsltMatcher INSTANCE = new TypeCopyOfMatcher();

    private TypeCopyOfMatcher()
    {
    }

    public boolean matches(Binding at)
    {
        if (!(at instanceof ElementBinding))
        {
            return false;
        }
        if (at.getChildCount()!=2 && at.getChildCount()!=3)
        {
            return false;
        }
        Binding b1 = at.getChild(0);
        Binding b2 = at.getChild(1);
        Binding optb3 = at.getChildCount()==3 ? at.getChild(2) : null;
        if (!(b1 instanceof CopyOfBinding) || !(b2 instanceof CopyOfBinding))
        {
            return false;
        }
        if (optb3!=null && !(optb3 instanceof CopyOfBinding))
        {
            return false;
        }
        String f1 = b1.getFormula();
        String f2 = b2.getFormula();
        String optf3 = optb3==null ? null : optb3.getFormula();
        if (f1==null || f2==null)
        {
            return false;
        }
        String nsSuffix = "/ancestor-or-self::*/namespace::node()";
        String attrSuffix = "/@*";
        String elemSuffix = "/node()";
        if (optf3!=null)
        {
            // 3 copy case:
            if (!f1.endsWith(nsSuffix))
            {
                return false;
            }
            if (!f2.endsWith(attrSuffix))
            {
                return false;
            }
            if (!optf3.endsWith(elemSuffix))
            {
                return false;
            }
            String f1base = f1.substring(0,f1.length()-nsSuffix.length());
            String f2base = f2.substring(0,f2.length()-attrSuffix.length());
            String f3base = optf3.substring(0,optf3.length()-elemSuffix.length());
            if (!f1base.equals(f2base) || !f1base.equals(f3base))
            {
                return false;
            }
            return true;
        }
        else
        {
            if (!f1.endsWith(attrSuffix))
            {
                return false;
            }
            if (!f2.endsWith(elemSuffix))
            {
                return false;
            }
            String f1base = f1.substring(0,f1.length()-attrSuffix.length());
            String f2base = f2.substring(0,f2.length()-elemSuffix.length());
            if (!f1base.equals(f2base))
            {
                return false;
            }
            return true;
        }
    }

    public VirtualXsltElement virtualize(Binding at, BindingVirtualizer virtualizer, CancelChecker cancelChecker)
    {
        ElementBinding eb = (ElementBinding) at;
        ExpandedName ename = eb.getName();
        String copyAttrFormula;
        String actualFormula;
        boolean includeNsCopy = eb.getChildCount()==3;
        if (includeNsCopy)
        {
            // If there are 3 copies, the middle one is attributes:
            copyAttrFormula = eb.getChild(1).getFormula();
        }
        else
        {
            // If there are 2 copies, the first one is attributes:
            copyAttrFormula = eb.getChild(0).getFormula();
        }
        actualFormula = copyAttrFormula.substring(0,copyAttrFormula.length()-"/@*".length());

        TypeCopyOfBinding tcob = new TypeCopyOfBinding(eb.getElementInfo(),ename);
        tcob.setExplicitXslRepresentation(eb.isExplicitXslRepresentation());
        tcob.setExplicitNamespaceAVT(eb.getExplicitNamespaceAVT());
        tcob.setExplicitNameAVT(eb.getExplicitNameAVT());
        tcob.setIncludeNamespaceCopies(includeNsCopy);
        tcob.setFormula(actualFormula);
        return tcob;
    }
}
