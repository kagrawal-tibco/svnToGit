package com.tibco.cep.mapper.xml.xdata.bind.fix;

import java.util.ArrayList;

import com.tibco.cep.mapper.util.ResourceBundleManager;
import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.BindingElementInfo;
import com.tibco.cep.mapper.xml.xdata.bind.BindingManipulationUtils;
import com.tibco.cep.mapper.xml.xdata.bind.ChooseBinding;
import com.tibco.cep.mapper.xml.xdata.bind.IfBinding;
import com.tibco.cep.mapper.xml.xdata.bind.OtherwiseBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.bind.WhenBinding;
import com.tibco.cep.mapper.xml.xdata.bind.virt.MarkerBinding;
import com.tibco.cep.mapper.xml.xdata.xpath.MessageCode;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SmSequenceTypeFactory;

/**
 * A dialog which shows errors & allows selective choosing of which elements to fix.
 */
class AddOtherwiseBindingFixerChange extends BindingFixerChange
{
    protected AddOtherwiseBindingFixerChange(TemplateReport report) {
        super(report,true);
    }

    public String computeMessage()
    {
        SmSequenceType seq = SmSequenceTypeFactory.createSequence(getTemplateReport().getMissingFollowingTerms());
        return ResourceBundleManager.getMessage(MessageCode.ADD_OTHERWISE,seq.toString());
    }

    public final void performMove(ArrayList moveList)
    {
    }

    public int getCategory()
    {
        return CATEGORY_WARNING;
    }

    public final void performNonMove()
    {
        OtherwiseBinding added = new OtherwiseBinding(BindingElementInfo.EMPTY_INFO);
        TemplateReport report = getTemplateReport();
        for (int i=0;i<report.getMissingFollowingTerms().length;i++)
        {
            Binding mb = new MarkerBinding(report.getMissingFollowingTerms()[i]);
            added.addChild(mb);
        }
        Binding b = report.getBinding();
        if (b instanceof ChooseBinding) {
            b.addChild(added);
        } else {
            if (b instanceof IfBinding) {
                // Change it into a choose:
                ChooseBinding cb = new ChooseBinding(b.getElementInfo());
                WhenBinding wb = new WhenBinding(BindingElementInfo.EMPTY_INFO,b.getFormula());
                BindingManipulationUtils.copyBindingContents(b,wb);
                cb.addChild(wb);
                cb.addChild(added);
                BindingManipulationUtils.replaceInParent(b,cb);
            } else {
                // shouldn't happen.
            }
        }
    }
}
