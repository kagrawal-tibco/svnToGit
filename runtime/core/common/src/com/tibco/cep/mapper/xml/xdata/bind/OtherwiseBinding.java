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

/**
 * Represents an XSLT otherwise operation inside a choice operation.
 * (Not a ControlBinding since it must be inside a {@link ChooseBinding}.
 */
public class OtherwiseBinding extends AbstractBinding
{
    /**
     * Constructor.
     */
    public OtherwiseBinding(BindingElementInfo info) {
        super(info);
    }

    public TemplateReport createTemplateReport(TemplateReport parent, ExprContext context, SmSequenceType expectedOutput, SmSequenceType outputContext, int outputValidationLevel, TemplateReportFormulaCache templateReportFormulaCache, TemplateReportArguments templateReportArguments) {
        TemplateReport ret = new TemplateReport(this,parent);
        ret.setContext(context);
        ret.setChildContext(context);
        ret.setFollowingContext(context);
        ret.setOutputContext(outputContext);
        ret.setChildOutputContext(outputContext);
        ret.setInitialOutputType(expectedOutput);
        if (parent==null || !(parent.getBinding() instanceof ChooseBinding)) {
            ret.setStructuralError("The 'otherwise' statement must appear directly inside a 'choose'.");
        }

        SmSequenceType remainingType = TemplateReportSupport.traverseChildren(ret,expectedOutput, outputValidationLevel, templateReportFormulaCache, templateReportArguments);
        ret.setRemainingOutputType(remainingType);

        ret.setExpectedType(TemplateReportSupport.getMatchedChildTerm(ret));

        TemplateReportSupport.initIsRecursivelyErrorFree(ret);
        return ret;
    }

    public void formatFragment(XmlContentHandler handler) throws SAXException {
        super.issueNamespaceDeclarations(handler);
        handler.startElement(NAME,null,null);
        super.issueAdditionalAttributes(handler);
        for (int i=0;i<getChildCount();i++) {
            getChild(i).formatFragment(handler);
        }
        handler.endElement(NAME,null,null);
    }

    public ExpandedName getName() {
        return NAME;
    }

    private static final ExpandedName NAME = ReadFromXSLT.makeName("otherwise");

    /**
     * Clones this binding node.
     * @return Binding A binding node of this type.
     */
    public Binding cloneShallow() {
        return new OtherwiseBinding(getElementInfo());
    }
}
