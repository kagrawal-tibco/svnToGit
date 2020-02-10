package com.tibco.cep.mapper.xml.xdata.bind.fix;

import java.util.ArrayList;

import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;

/**
 * A change for structural errors {@link TemplateReport#getStructuralError}.<br>
 * If the error has an associated fixing move, it performs that as well.
 */
class StructuralBindingFixerChange extends BindingFixerChange
{
    private final TemplateReport m_report;

    public StructuralBindingFixerChange(TemplateReport report)
    {
        super(report,report.getStructuralErrorMoveTo()!=null);

        m_report = report;
    }

    public String computeMessage()
    {
        return m_report.getStructuralError();
    }

    public int getCategory()
    {
        return CATEGORY_ERROR;
    }

    public void performMove(ArrayList moveList)
    {
        if (getTemplateReport().getStructuralErrorMoveTo()!=null)
        {
            // move it:
            // (These are done in a separate pass because they require ordering)
            moveList.add(getTemplateReport());
        }
    }

    public void performNonMove()
    {
    }
}
