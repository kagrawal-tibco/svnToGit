/*******************************************************************************
 * Copyright 2001 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.xml.xdata.bind;

import org.xml.sax.SAXException;

import com.tibco.cep.mapper.xml.xdata.NamespaceMapper;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.xml.channel.infoset.XmlContentHandler;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SMDT;

/**
 * The xsl:processing-instruction xslt tag.
 */
public class XslPIBinding extends TemplateContentBinding
{
    private String m_name = "";

    public XslPIBinding(BindingElementInfo info)
    {
        super(info);
    }

    /**
     * Generates an XSLT string from the given binding.
     * @param xslt Buffer which contains the generated XSLT.
     */
    public void toXSLTString(StringBuffer xslt, int indent, NamespaceMapper namespaceMap)
    {
        xslt.append("<xsl:processing-instruction>");
    }

    public TemplateReport createTemplateReport(TemplateReport parent, ExprContext context, SmSequenceType expectedOutput, SmSequenceType outputContext, int outputValidationLevel, TemplateReportFormulaCache templateReportFormulaCache, TemplateReportArguments templateReportArguments) {
        // harmless PI:
        TemplateReport tr = new TemplateReport(this,parent);
        tr.setContext(context);
        tr.setInitialOutputType(expectedOutput);
        tr.setFollowingContext(context);
        tr.setChildContext(context);
        tr.setOutputContext(outputContext);
        tr.setChildOutputContext(outputContext);
        tr.setRemainingOutputType(expectedOutput);

        TemplateReportSupport.traverseChildren(tr,SMDT.STRING,outputValidationLevel,templateReportFormulaCache,templateReportArguments);

        TemplateReportSupport.initIsRecursivelyErrorFree(tr);

        return tr;
    }

    public String getNameAVT()
    {
        return m_name;
    }

    public void setNameAVT(String name)
    {
        m_name = name;
    }

    private static final ExpandedName NAME = ExpandedName.makeName(ReadFromXSLT.XSLT_URI,"processing-instruction");
    private static final ExpandedName NAME_ATTR = ExpandedName.makeName("name");

    public ExpandedName getName() {
        return NAME;
    }

    public void formatFragment(XmlContentHandler handler) throws SAXException
    {
        super.issueNamespaceDeclarations(handler);
        handler.startElement(NAME,null,null);
        handler.attribute(NAME_ATTR,m_name,null);
        super.issueAdditionalAttributes(handler);
        for (int i=0;i<getChildCount();i++) {
            getChild(i).formatFragment(handler);
        }
        handler.endElement(NAME,null,null);
    }

    /**
     * Clones this binding node.
     * @return Binding A binding node of this type.
     */
    public Binding cloneShallow()
    {
        XslPIBinding pi = new XslPIBinding(getElementInfo());
        pi.setNameAVT(getNameAVT());
        return pi;
    }
}
