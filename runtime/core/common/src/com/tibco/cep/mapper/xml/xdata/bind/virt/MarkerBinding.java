/*******************************************************************************
 * Copyright 2001 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.xml.xdata.bind.virt;

import java.util.Iterator;
import java.util.Set;

import org.xml.sax.SAXException;

import com.tibco.cep.mapper.xml.xdata.bind.AbstractBinding;
import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReportArguments;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReportFormulaCache;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReportSupport;
import com.tibco.cep.mapper.xml.xdata.bind.XsTypeDeclaration;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.func.TibExtFunctions;
import com.tibco.xml.channel.infoset.XmlContentHandler;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContext;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.data.primitive.NamespaceToPrefixResolver;
import com.tibco.xml.data.primitive.PrefixToNamespaceResolver;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

/**
 * Holds a string representation of an xml comment.
 */
public class MarkerBinding extends AbstractBinding implements VirtualXsltElement
{
    /**
     * The content, not including the leading & trailing <!-- --> stuff
     */
    private SmSequenceType mType;

    public MarkerBinding(SmSequenceType type)
    {
        mType = type;
        if (type==null)
        {
            throw new NullPointerException("nulltype");
        }
    }

    private static final ExpandedName NAME = TibExtFunctions.makeName("marker");
    public ExpandedName getName()
    {
        return NAME;
    }

    public SmSequenceType getMarkerType()
    {
        return mType;
    }

    public void setMarkerType(SmSequenceType t)
    {
        if (t==null)
        {
            throw new NullPointerException("nulltype");
        }
        mType = t;
    }


    public TemplateReport createTemplateReport(TemplateReport parent, ExprContext context, SmSequenceType expectedOutput, SmSequenceType outputContext, int outputValidationLevel, TemplateReportFormulaCache templateReportFormulaCache, TemplateReportArguments templateReportArguments) {
        // marker comment:
        TemplateReport ret = new TemplateReport(this,parent);
        ret.setContext(context);
        ret.setChildContext(context);
        ret.setFollowingContext(context);
        ret.setOutputContext(outputContext);
        ret.setChildOutputContext(outputContext);
        ret.setInitialOutputType(expectedOutput);

        SmSequenceType commentType = mType;
        ret.setComputedType(commentType);
        SmSequenceType baseTerm = SmSequenceTypeSupport.stripOccursAndParens(commentType);
        TemplateReportSupport.checkSchemaComponentErrors(ret,baseTerm);

        SmSequenceType contentType = SmSequenceTypeSupport.createAttributeAndChildAndTypedValueSequenceExcludeMagic(commentType);
        ret.setChildOutputContext(contentType); // same as here.

        TemplateReportSupport.initForOutputMatch(ret, commentType, true, templateReportArguments); // true -> because this is a marker comment, we want it to consume the input entirely (in the case of repeats, etc.)

        if (getChildCount()>0)
        {
            SmSequenceType leftoverContentType = TemplateReportSupport.traverseChildren(ret,contentType,outputValidationLevel,templateReportFormulaCache,templateReportArguments);
            TemplateReportSupport.addMissingEndingTerms(ret,leftoverContentType,templateReportArguments);
        }
        if (ret.getExpectedType()!=null) {
            // If we are not inside another marker comment already...
            if (parent==null || !(parent.getBinding() instanceof MarkerBinding)) {
                // then indicate missing errors.
                // (otherwise, we don't need to mark these errors as it is overkill & potentially wrong ---
                // the parent will have been marked missing if it is required, if it isn't, then the contents
                // can't be required until the user actually makes one of the parent)
                if (ret.getExpectedType().quantifier().getMinOccurs()>0)
                {
                    // it is required, we need it:
                    ret.setIsMissing(true);
                }
            }
        }
        TemplateReportSupport.initIsRecursivelyErrorFree(ret);
        return ret;
    }

    public Binding normalize(Binding parent)
    {
        // this normalizes to nothing.
        return null;
    }

    public void formatFragment(XmlContentHandler handler) throws SAXException
    {
        handler.startElement(NAME,null,null);
        handler.text(mType.formatAsSequenceType(asXiNode()),false);
        handler.endElement(NAME,null,null);
    }

    public XmlTypedValue getApplicationObjectTypedValue()
    {
        return new XsTypeDeclaration(mType);
    }

    public void declareNamespaces(final Set set)
    {
        set.add(NAME.getNamespaceURI());
        mType.registerAllNamespaces(new NamespaceContextRegistry()
        {
            public String getOrAddPrefixForNamespaceURI(String namespace)
            {
                set.add(namespace);
                return "abc";
            }

            public String getOrAddPrefixForNamespaceURI(String namespace, String suggestedPrefix)
            {
                set.add(namespace);
                return "abc";
            }

            public Iterator getLocalPrefixes()
            {
                return null;
            }

            public Iterator getPrefixes()
            {
                return null;
            }

            public NamespaceContext snapshot()
            {
                return null;
            }

            public String getPrefixForNamespaceURI(String s) throws NamespaceToPrefixResolver.NamespaceNotFoundException
            {
                return null;
            }

            public String getNamespaceURIForPrefix(String s) throws PrefixToNamespaceResolver.PrefixNotFoundException
            {
                return null;
            }
        });
    }

    /**
     * Clones this binding node.
     * @return Binding A binding node of this type.
     */
    public Binding cloneShallow()
    {
        return new MarkerBinding(mType);
    }
}
