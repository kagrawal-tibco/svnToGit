package com.tibco.cep.mapper.xml.xdata.bind.fix;

import java.util.ArrayList;

import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.xpath.ErrorMessage;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;

/**
 * A dialog which shows errors & allows selective choosing of which elements to fix.
 */
public class FormulaErrorBindingFixerChange extends BindingFixerChange
{
    private final ErrorMessage m_em;
    /**
     * For an xpath error.
     */
    protected FormulaErrorBindingFixerChange(TemplateReport report, ErrorMessage em) {
        super(report,em.getFilter()!=null);
        m_em= em;
    }

    public ErrorMessage getErrorMessage()
    {
        return m_em;
    }

    public int getCategory()
    {
        switch (m_em.getType())
        {
            case ErrorMessage.TYPE_ERROR:
                return BindingFixerChange.CATEGORY_ERROR;
            default:
                return BindingFixerChange.CATEGORY_WARNING;

        }
    }

    public String computeMessage()
    {
        return m_em.getMessage();
    }

    public void performMove(ArrayList moveList)
    {
        // can't do anything...
    }

    public void performNonMove()
    {
        if (m_em.getFilter()!=null)
        {
            Expr ne = m_em.getFilter().filter(getTemplateReport().getXPathExpression(), getTemplateReport().getContext().getNamespaceMapper());
            //getTemplateReport().setXPathExpression(ne);
            getTemplateReport().getBinding().setFormula(ne.toExactString());
        }
    }
}
