/*******************************************************************************
 * Copyright 2001 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.xml.xdata.bind;

import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.xml.channel.infoset.XmlContentHandler;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmSequenceType;

/**
 * An XSLT stylesheet tag.
 */
public class ScriptBinding extends ControlBinding
{
    /*private static final int IMPLEMENTS_PREFIX                  = 0;
    private static final int LANGUAGE                           = 1;

    private static final String IMPLEMENTS_PREFIX_ATTRIBUTE     = "@implements-prefix";
    private static final String LANGAUGE_ATTRIBUTE              = "@language";
    private static final String SRC_ATTRIBUTE                   = "@src";
    private static final String ARCHIVE_ATTRIBUTE               = "@archive";*/

    /**
     * Constructor.
     */
    public ScriptBinding(BindingElementInfo info)
    {
        super(info,"[script]");
    }

    /**
     * Initializes a script binding with the given attribute list.
     */
    public ScriptBinding setScriptBindingAttributes(Attributes atts, String defaultNamespace)
    {
        /* ???
        for (int i=0; i < atts.getLength(); i++)
        {
            String namespaceURI = atts.getURI(i);
            if (namespaceURI == null || namespaceURI.length() == 0) {
                namespaceURI = defaultNamespace;
            }
            this.addChild(new AttributeBinding(ExpandedName.makeName(namespaceURI, "@" + atts.getQName(i)), atts.getValue(i)));
        }
        return this;*/
        return null;
    }

    /*
     * Generates an XSLT string from the given binding.
     * @param xslt Buffer which contains the generated XSLT.
     *
    public void toXSLTString(StringBuffer xslt, int indent, NamespaceMapper namespaceMap)
    {
        String xslPrefix = namespaceMap.registerOrGetPrefixForNamespaceURI(XSLT_URI, "xsl");
        xslt.append("<");
        xslt.append(xslPrefix);
        xslt.append(":script");

        Binding child;
        int i=0;
        // Deal with the first attribute.
        for (; i < this.getChildCount(); i++)
        {
            child = this.getChild(i);
            if (child instanceof AttributeBinding)
            {
                xslt.append(' ');
                ((AttributeBinding)child).toXSLTSimpleString(xslt);
                break;
            }
        }
        // Deal with the rest of the attributes.
        for (++i; i < this.getChildCount(); i++)
        {
            tabXSLT(xslt, 2);
            child = this.getChild(i);
            if (child instanceof AttributeBinding) {
                ((AttributeBinding)child).toXSLTSimpleString(xslt);
            }
        }

        String formula = this.getFormula();
        if (formula != null)
        {
            xslt.append(">");
            boolean hasNewLines = (formula.indexOf('\n') != -1 || formula.indexOf('\r') != -1);
            if (hasNewLines) {
                xslt.append("\n");
            }
            xslt.append(this.getFormula());
            if (hasNewLines) {
                xslt.append("\n");
            }
            xslt.append("</");
            xslt.append(xslPrefix);
            xslt.append(":script>");
        }
        else {
            xslt.append("/>");
        }

//        xslt.append('>');
//        if (traverseChildrenForXSLT(xslt, indent, namespaceMap)) {
//            tabXSLT(xslt, indent);
//        }
    }*/

    public void formatFragment(XmlContentHandler handler) throws SAXException {
    }

    public boolean hasTextContent()
    {
        return true;
    }

    public Binding cloneShallow() {
        return new ScriptBinding(getElementInfo());
    }


    /*
     * LAMb: When we split the bindings to have be virtual (result of virtualize) and normal (or literal, the
     * result of normalize), then these methods should be put into the virtual version, and the values
     * should be stored on instance vars. The virtual version should have no children?
     */
    public String getImplementsPrefix()
    {
        com.tibco.cep.mapper.xml.xdata.bind.Binding implementsPrefix = null;//@this.getChild(IMPLEMENTS_PREFIX_ATTRIBUTE);
        return implementsPrefix.getFormula();
    }

    public String getLanguage()
    {
        com.tibco.cep.mapper.xml.xdata.bind.Binding language = null;//@this.getChild(LANGAUGE_ATTRIBUTE);
        return language.getFormula();
    }

    public String getSrc()
    {
        com.tibco.cep.mapper.xml.xdata.bind.Binding source = null;//@@this.getChild(SRC_ATTRIBUTE);
        if (source == null) {
            return null;
        }
        return source.getFormula();
    }

    public TemplateReport createTemplateReport(TemplateReport parent, ExprContext context, SmSequenceType expectedOutput, SmSequenceType outputContext, int outputValidationLevel, TemplateReportFormulaCache templateReportFormulaCache, TemplateReportArguments templateReportArguments) {
        throw new RuntimeException();
    }

    private static final ExpandedName NAME = ExpandedName.makeName(ReadFromXSLT.XSLT_URI,"script");

    public ExpandedName getName() {
        return NAME;
    }

    public String[] getArchive()
    {
        com.tibco.cep.mapper.xml.xdata.bind.Binding archiveUrisBinding = null;//@@this.getChild(ARCHIVE_ATTRIBUTE);
        if (archiveUrisBinding == null) {
            return EMPTY_STRINGS;
        }
        String archiveString = archiveUrisBinding.getFormula();
        StringTokenizer toks = new StringTokenizer(archiveString," ");
        List archiveUris = new LinkedList();
        while(toks.hasMoreTokens()) {
            archiveUris.add(toks.nextToken());
        }
        return (String[]) archiveUris.toArray(EMPTY_STRINGS);
    }
}
