package com.tibco.cep.mapper.xml.xdata.bind.fix;

import java.util.ArrayList;

import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReportExtendedError;

/**
 * A dialog which shows errors & allows selective choosing of which elements to fix.
 */
class ExtendedErrorBindingFixerChange extends BindingFixerChange {
    private final TemplateReportExtendedError m_extended;
    private final TemplateReport m_report;

    public ExtendedErrorBindingFixerChange(TemplateReport report, TemplateReportExtendedError extended) {
        super(report,extended.canFix(report));

        m_extended = extended;
        m_report = report;
    }

    public int getCategory()
    {
        return m_extended.getCategory();
    }

    public String computeMessage()
    {
        return m_extended.formatMessage(m_report);
    }

    public void performMove(ArrayList moveList)
    {
    }

    public void performNonMove()
    {
        m_extended.fix(m_report);
    }

    public String toString()
    {
        return m_extended.toString();
    }
}
