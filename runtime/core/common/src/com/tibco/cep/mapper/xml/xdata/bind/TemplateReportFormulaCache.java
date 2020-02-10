package com.tibco.cep.mapper.xml.xdata.bind;

import java.util.HashMap;

import com.tibco.cep.mapper.xml.xdata.xpath.ExprAndErrors;
import com.tibco.cep.mapper.xml.xdata.xpath.Parser;

/**
 * A performance optimizing utility used by TemplateReport to cache formula
 */
public final class TemplateReportFormulaCache
{
    private HashMap m_cached = new HashMap();

    public ExprAndErrors getParsedFormula(String formula)
    {
        synchronized (this)
        {
            ExprAndErrors e = (ExprAndErrors) m_cached.get(formula);
            if (e!=null)
            {
                return e;
            }
            e = Parser.parseWithErrors(formula);
            m_cached.put(formula,e);
            return e;
        }
    }

    public void clear()
    {
        m_cached.clear();
    }
}
