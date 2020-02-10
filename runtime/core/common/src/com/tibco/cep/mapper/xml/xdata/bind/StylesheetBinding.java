/*******************************************************************************
 * Copyright 2001 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.xml.xdata.bind;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

import org.xml.sax.SAXException;

import com.tibco.cep.mapper.util.ResourceBundleManager;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.MessageCode;
import com.tibco.xml.channel.infoset.XmlContentHandler;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmSequenceType;

/**
 * An XSLT stylesheet tag.
 */
public class StylesheetBinding extends ControlBinding
{
    /**
     * Represents the read in namespaces.
     */
    private Set mExcludePrefixes;
    private boolean mIsTransform;
    private Binding[] mLeadingComments = EMPTY;
    private Binding[] mTrailingComments = EMPTY;
    private static final Binding[] EMPTY = new Binding[0];

    public StylesheetBinding(BindingElementInfo info)
    {
        super(info,"[stylesheet]");
        mExcludePrefixes = new HashSet();
    }

    public StylesheetBinding(BindingElementInfo info, String excludePrefixes, Binding[] leadingComments, Binding[] trailingComments) {
        this(info);
        setExcludedPrefixes(excludePrefixes);
        mLeadingComments = leadingComments;
        mTrailingComments = trailingComments;
    }

    public StylesheetBinding(BindingElementInfo info, Set excludePrefixes, Binding[] leadingComments, Binding[] trailingComments) {
        this(info);
        mExcludePrefixes = excludePrefixes;
        mLeadingComments = leadingComments;
        mTrailingComments = trailingComments;
    }

    public void formatFragment(XmlContentHandler handler) throws SAXException {
        for (int i=0;i<mLeadingComments.length;i++)
        {
            mLeadingComments[i].formatFragment(handler);
        }
        super.issueNamespaceDeclarations(handler);
        if (mIsTransform) {
            handler.startElement(TRANSFORM_NAME,null,null);
        } else {
            handler.startElement(NAME,null,null);
        }
        handler.attribute(ATTR_VERSION,"1.0",null);
        if (mExcludePrefixes!=null && mExcludePrefixes.size()>0)
        {
            StringBuffer sb = new StringBuffer();
            Iterator i = mExcludePrefixes.iterator();
            boolean any = false;
            while (i.hasNext())
            {
                if (any)
                {
                    sb.append(' ');
                }
                any = true;
                String it = (String) i.next();
                sb.append(it);
            }
            handler.attribute(ATTR_EXCLUDE_PREFIXES,sb.toString(),null);
        }
        super.issueAdditionalAttributes(handler);
        for (int i=0;i<getChildCount();i++) {
            getChild(i).formatFragment(handler);
        }
        if (mIsTransform) {
            handler.endElement(TRANSFORM_NAME,null,null);
        } else {
            handler.endElement(NAME,null,null);
        }
        for (int i=0;i<mTrailingComments.length;i++)
        {
            mTrailingComments[i].formatFragment(handler);
        }
    }



    public Binding cloneShallow() {
        StylesheetBinding sb = new StylesheetBinding(getElementInfo(),mExcludePrefixes,mLeadingComments,mTrailingComments);
        sb.setIsTransformTag(isTransformTag());
        return sb;
    }

    public String getExcludedPrefixes() {
        Iterator i = mExcludePrefixes.iterator();
        StringBuffer sb = new StringBuffer();
        boolean any = false;
        while (i.hasNext()) {
            if (any) {
                sb.append(" ");
            }
            sb.append((String)i.next());
            any = true;
        }
        return sb.toString();
    }

    public Set getExcludedPrefixesAsSet() {
        return mExcludePrefixes;
    }

    public void setExcludedPrefixes(String excludePrefixes)
    {
        if (excludePrefixes!=null) {
            StringTokenizer st = new StringTokenizer(excludePrefixes," ");
            while (st.hasMoreTokens()) {
                String t = st.nextToken();
                mExcludePrefixes.add(t);
            }
        }
        else
        {
            mExcludePrefixes.clear();
        }
    }

    /**
     * Utility function, retrieves the template from the children.
     * @return
     */
    public TemplateBinding getTemplate(int index) {
        for (int i=0;i<getChildCount();i++) {
            com.tibco.cep.mapper.xml.xdata.bind.Binding b = getChild(i);
            if (b instanceof TemplateBinding) {
                if (index==0) {
                    return (TemplateBinding)b;
                } else {
                    index--;
                }
            }
        }
        return null;
    }

    public boolean isTransformTag() {
        return mIsTransform;
    }

    public void setIsTransformTag(boolean val) {
        mIsTransform = val;
    }

    public void setLeadingComments(Binding[] before)
    {
        mLeadingComments = before;
    }

    public void setTrailingComments(Binding[] before)
    {
        mTrailingComments = before;
    }

    private static final ExpandedName NAME = ReadFromXSLT.makeName("stylesheet");
    private static final ExpandedName TRANSFORM_NAME = ReadFromXSLT.makeName("transform");
    private static final ExpandedName ATTR_VERSION = ExpandedName.makeName("version");
    private static final ExpandedName ATTR_EXCLUDE_PREFIXES = ExpandedName.makeName("exclude-result-prefixes");

    public ExpandedName getName() {
        return NAME;
    }

    public TemplateReport createTemplateReport(TemplateReport parent, ExprContext context, SmSequenceType expectedOutput, SmSequenceType outputContext, int outputValidationLevel, TemplateReportFormulaCache templateReportFormulaCache, TemplateReportArguments templateReportArguments) {
        // don't treat these as part of the report..., they can't appear in a template.
        TemplateReport tr = new TemplateReport(this,parent);
        tr.setContext(context);
        tr.setChildContext(context);
        tr.setFollowingContext(context);
        tr.setOutputContext(outputContext);
        tr.setChildOutputContext(outputContext);
        tr.setInitialOutputType(expectedOutput);
        tr.setRemainingOutputType(expectedOutput);
        tr.setStructuralError(ResourceBundleManager.getMessage(MessageCode.STYLESHEET_NOT_ALLOWED_HERE));
        return tr;
    }
}
