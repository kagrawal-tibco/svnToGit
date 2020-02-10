package com.tibco.cep.mapper.xml.xdata.bind.fix;

import java.util.ArrayList;

import com.tibco.cep.mapper.util.ResourceBundleManager;
import com.tibco.cep.mapper.xml.xdata.DefaultNamespaceMapper;
import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.BindingManipulationUtils;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.xpath.MessageCode;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

/**
 * A change for when a value is required (such as an element or a text() content node)
 */
class ValueRequiredBindingFixerChange extends BindingFixerChange
{
    private boolean m_isStructure;
    private boolean m_canCreate;

    /**
     * For an xpath error.
     */
    protected ValueRequiredBindingFixerChange(TemplateReport report, boolean isStructure, boolean canCreate)
    {
        super(report,canCreate);
        m_isStructure = isStructure;
        m_canCreate = canCreate;
    }

    public String computeMessage()
    {
        if (m_isStructure)
        {
            return ResourceBundleManager.getMessage(MessageCode.ELEMENT_REQUIRED,SmSequenceTypeSupport.getDisplayName(getTemplateReport().getComputedType()));
        }
        else
        {
            return ResourceBundleManager.getMessage(MessageCode.VALUE_REQUIRED_FOR,SmSequenceTypeSupport.getDisplayName(getTemplateReport().getComputedType()));
        }
    }

    public int getCategory()
    {
        return CATEGORY_ERROR;
    }

    public void performMove(ArrayList moveList)
    {
    }

    public void performNonMove()
    {
        if (m_canCreate)
        {
            SmSequenceType t = getTemplateReport().getComputedType();
            Binding b = BindingManipulationUtils.createAppropriateAttributeOrElementOrMarkerBinding(t,new DefaultNamespaceMapper()); // mapper n/a here.
            Binding on = getTemplateReport().getBinding();
            if (on.getParent()==null)
            {
                // NOTE: this CAN happen if there is a move, or something like that, that causes this to be not-relevant, so don't crash.
                //
                return;
            }
            BindingManipulationUtils.replaceInParent(getTemplateReport().getBinding(),b);
        }
    }
}
