package com.tibco.cep.mapper.xml.xdata.bind.virt;

import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.BindingElementInfo;
import com.tibco.cep.mapper.xml.xdata.bind.CancelChecker;
import com.tibco.cep.mapper.xml.xdata.bind.ForEachGroupBinding;

/**
 * Matcher for generating {@link VirtualAttributeBinding}.
 */
public class VirtualForEachGroupMatcher implements VirtualXsltMatcher
{
    public static final VirtualXsltMatcher INSTANCE = new VirtualForEachGroupMatcher();

    private VirtualForEachGroupMatcher()
    {
    }

    public boolean matches(Binding at)
    {
        if (!(at instanceof ForEachGroupBinding))
        {
            return false;
        }
        return true;
    }

    public VirtualXsltElement virtualize(Binding at, BindingVirtualizer virtualizer, CancelChecker cancelChecker)
    {
        ForEachGroupBinding eb = (ForEachGroupBinding) at;
        VirtualForEachGroupBinding veb = new VirtualForEachGroupBinding(eb.getElementInfo(),eb.getFormula());
        String gb = eb.getGrouping();
        if (gb!=null)
        {
            int gt = eb.getGroupType();
            VirtualGroupingBinding vgb = new VirtualGroupingBinding(BindingElementInfo.EMPTY_INFO,gb);
            vgb.setGroupType(gt);
            veb.addChild(vgb);
        }
        virtualizer.virtualizeChildren(eb,veb,cancelChecker);
        return veb;
    }
}

