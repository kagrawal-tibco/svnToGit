/*******************************************************************************
 * Copyright 2001 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.xml.xdata.bind;

import org.xml.sax.SAXException;

import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.xml.channel.infoset.XmlContentHandler;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.XmlNodeKind;
import com.tibco.xml.schema.SmSequenceType;

/**
 * Holds a string representation of an xml comment.
 */
public class CommentBinding extends TemplateContentBinding
{
    /**
     * The content, not including the leading & trailing <!-- --> stuff
     */
    private String mComment;

    public CommentBinding(String comment) {
        super(BindingElementInfo.EMPTY_INFO);
        mComment = comment;
    }

    public String getComment() {
        return mComment;
    }

    public void setComment(String comment) {
        mComment = comment;
    }

    private static final ExpandedName NAME = TemplateReport.makeName("source-comment");
    private static final ExpandedName COMMENT_NODE_NAME = ExpandedName.makeName("");
    public ExpandedName getName() {
        return NAME;
    }

    public ExpandedName getApplicationObjectName()
    {
        return COMMENT_NODE_NAME;
    }

    public XmlNodeKind getApplicationObjectNodeKind()
    {
        return XmlNodeKind.COMMENT;
    }

    public TemplateReport createTemplateReport(TemplateReport parent, ExprContext context, SmSequenceType expectedOutput, SmSequenceType outputContext, int outputValidationLevel, TemplateReportFormulaCache templateReportFormulaCache, TemplateReportArguments templateReportArguments) {

        // harmless comment:
        TemplateReport tr = new TemplateReport(this,parent);
        tr.setContext(context);
        tr.setChildContext(context);
        tr.setOutputContext(outputContext);
        tr.setChildOutputContext(outputContext);
        tr.setInitialOutputType(expectedOutput);
        tr.setRemainingOutputType(expectedOutput);
        tr.setFollowingContext(context);

        // No children allowed, but in case they get there (somehow) deal with it:
        TemplateReportSupport.traverseNoChildren(tr,false,templateReportFormulaCache,templateReportArguments);

        tr.setIsRecursivelyErrorFree(true);
        return tr;
    }

    public void formatFragment(XmlContentHandler handler) throws SAXException {
        handler.comment(mComment);
    }

    /**
     * Clones this binding node.
     * @return Binding A binding node of this type.
     */
    public Binding cloneShallow() {
        return new CommentBinding(mComment);
    }
}
