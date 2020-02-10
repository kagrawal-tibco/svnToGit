/*******************************************************************************
 * Copyright 2001 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.xml.xdata.bind;

import org.xml.sax.SAXException;

import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.xml.channel.infoset.XmlContentHandler;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SMDT;

/**
 * An unsupported tag.
 */
public class UnknownBinding extends TemplateContentBinding
{
    private ExpandedName mName;

    public UnknownBinding(BindingElementInfo info, ExpandedName name)
    {
        super(info);
        mName = name;
    }

    public TemplateReport createTemplateReport(TemplateReport parent, ExprContext context, SmSequenceType expectedOutput, SmSequenceType outputContext, int outputValidationLevel, TemplateReportFormulaCache templateReportFormulaCache, TemplateReportArguments templateReportArguments) {
        // treat as harmless, but mark error:
        TemplateReport tr = new TemplateReport(this,parent);
        tr.setInitialOutputType(expectedOutput);
        tr.setContext(context);
        tr.setChildContext(context);
        tr.setFollowingContext(context);
        tr.setOutputContext(outputContext);
        tr.setChildOutputContext(outputContext);
        tr.setRemainingOutputType(expectedOutput);
        TemplateReportSupport.traverseChildren(tr,SMDT.PREVIOUS_ERROR,outputValidationLevel,templateReportFormulaCache,templateReportArguments);
        tr.setStructuralError("Unrecognized statement '" + getName().getLocalName() + "'");
        tr.setIsRecursivelyErrorFree(false);
        return tr;
    }

    public ExpandedName getName() {
        return mName;
    }

    public Binding cloneShallow() {
        return new UnknownBinding(getElementInfo(),getName());
    }

    public void formatFragment(XmlContentHandler handler) throws SAXException {
        super.issueNamespaceDeclarations(handler);
        handler.startElement(getName(),null,null);
        super.issueAdditionalAttributes(handler);
        for (int i=0;i<getChildCount();i++) {
            getChild(i).formatFragment(handler);
        }
        handler.endElement(getName(),null,null);
    }
}
