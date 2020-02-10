/*******************************************************************************
 * Copyright 2001 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.xml.xdata.bind;

import org.xml.sax.SAXException;

import com.tibco.cep.mapper.util.ResourceBundleManager;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.MessageCode;
import com.tibco.xml.channel.infoset.XmlContentHandler;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmSequenceType;

/**
 * Represents an XSLT for-each's sort.
 * (Not a ControlBinding since it must be inside a {@link ForEachBinding} or other looping binding).
 */
public class SortBinding extends AbstractBinding
{
    private String m_lang; // may be null for default.
    private String m_order; // may be null for default.
    private String m_caseOrder; // may be null for default.
    private String m_dataType; // may be null for default.

    public static final String SORT_ORDER_ASCENDING = "ascending";
    public static final String SORT_ORDER_DESCENDING = "descending";

    public static final String CASE_ORDER_LOWER_FIRST = "lower-first";
    public static final String CASE_ORDER_UPPER_FIRST = "upper-first";

    public static final String DATA_TYPE_TEXT = "text";
    public static final String DATA_TYPE_NUMBER = "number";

    public SortBinding(BindingElementInfo info)
    {
        this(info,null);
    }

    public SortBinding(BindingElementInfo ns, String formula)
    {
        super(ns, formula);
    }

    public TemplateReport createTemplateReport(TemplateReport parent, ExprContext context, SmSequenceType expectedOutput, SmSequenceType outputContext, int outputValidationLevel, TemplateReportFormulaCache templateReportFormulaCache, TemplateReportArguments templateReportArguments) {
        TemplateReport tr = new TemplateReport(this,parent);
        tr.setContext(context);
        tr.setChildContext(context);
        tr.setFollowingContext(context);
        tr.setOutputContext(outputContext);
        tr.setChildOutputContext(outputContext);
        tr.setInitialOutputType(expectedOutput);
        tr.setRemainingOutputType(expectedOutput);
        TemplateReportSupport.initParsedFormula(tr,templateReportFormulaCache,templateReportArguments);
        if (!isInProperContext(tr))
        {
            String msg = ResourceBundleManager.getMessage(MessageCode.SORT_IN_FOR_EACH);
            tr.setStructuralError(msg);
        }
        // In the event that it is in the proper context, the container does the remaining positional validation.
        TemplateReportSupport.traverseNoChildren(tr,true,templateReportFormulaCache,templateReportArguments);
        TemplateReportSupport.initIsRecursivelyErrorFree(tr);
        return tr;
    }

    private boolean isInProperContext(TemplateReport tr)
    {
        TemplateReport parent = tr.getParent();
        if (parent==null)
        {
            return false;
        }
        Binding pb = parent.getBinding();
        if (pb instanceof ForEachBinding || pb instanceof ApplyTemplatesBinding || pb instanceof ForEachGroupBinding)
        {
            return true;
        }
        return false;
    }

    public void formatFragment(XmlContentHandler handler) throws SAXException {
        handler.startElement(NAME,null,null);
        String f = getFormula();
        if (f==null)
        {
            f = "";
        }
        handler.attribute(SELECT_ATTR,f,null);
        if (m_order!=null)
        {
            handler.attribute(ORDER_ATTR,m_order,null);
        }
        if (m_dataType!=null)
        {
            handler.attribute(DATA_TYPE_ATTR,m_dataType,null);
        }
        if (m_lang!=null)
        {
            handler.attribute(LANG_ATTR,m_lang,null);
        }
        if (m_caseOrder!=null)
        {
            handler.attribute(CASE_ORDER_ATTR,m_caseOrder,null);
        }
        for (int i=0;i<getChildCount();i++) {
            getChild(i).formatFragment(handler);
        }
        handler.endElement(NAME,null,null);
    }

    public String getLang()
    {
        return m_lang;
    }

    public void setLang(String lang)
    {
        m_lang = lang;
    }

    public String getDataType()
    {
        return m_dataType;
    }

    public void setDataType(String dataType)
    {
        m_dataType = dataType;
    }

    public String getOrder()
    {
        return m_order;
    }

    public void setOrder(String order)
    {
        m_order = order;
    }

    public String getCaseOrder()
    {
        return m_caseOrder;
    }

    public void setCaseOrder(String caseOrder)
    {
        m_caseOrder = caseOrder;
    }

    private static final ExpandedName NAME = ExpandedName.makeName(ReadFromXSLT.XSLT_URI,"sort");

    private static final ExpandedName SELECT_ATTR = ExpandedName.makeName("select");
    private static final ExpandedName LANG_ATTR = ExpandedName.makeName("lang");
    private static final ExpandedName DATA_TYPE_ATTR = ExpandedName.makeName("data-type");
    private static final ExpandedName ORDER_ATTR = ExpandedName.makeName("order");
    private static final ExpandedName CASE_ORDER_ATTR = ExpandedName.makeName("case-order");

    public ExpandedName getName()
    {
        return NAME;
    }

    /**
     * Clones this binding node.
     * @return Binding A binding node of this type.
     */
    public Binding cloneShallow() {
        SortBinding sb = new SortBinding(getElementInfo(),this.getFormula());
        sb.setLang(getLang());
        sb.setDataType(getDataType());
        sb.setOrder(getOrder());
        sb.setCaseOrder(getCaseOrder());
        return sb;
    }
}
