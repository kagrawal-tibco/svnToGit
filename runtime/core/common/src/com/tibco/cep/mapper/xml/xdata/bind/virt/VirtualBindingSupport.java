package com.tibco.cep.mapper.xml.xdata.bind.virt;

import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.BindingElementInfo;
import com.tibco.cep.mapper.xml.xdata.bind.ValueOfBinding;

public class VirtualBindingSupport
{
    public static void setXPathOn(Binding b, String formula)
    {
        if (b instanceof VirtualDataComponentBinding)
        {
            VirtualDataComponentBinding veb = (VirtualDataComponentBinding) b;
            veb.setHasInlineFormula(true);
            veb.setInlineIsText(false); // it's xpath.
            b.setFormula(formula);
        }
        else
        {
            b.addChild(new ValueOfBinding(BindingElementInfo.EMPTY_INFO,formula));
        }
    }
}

