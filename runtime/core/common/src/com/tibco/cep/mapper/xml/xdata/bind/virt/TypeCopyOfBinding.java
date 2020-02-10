/*******************************************************************************
 * Copyright 2001 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.xml.xdata.bind.virt;

import org.xml.sax.SAXException;

import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.BindingElementInfo;
import com.tibco.cep.mapper.xml.xdata.bind.CopyOfBinding;
import com.tibco.cep.mapper.xml.xdata.bind.DataComponentBinding;
import com.tibco.cep.mapper.xml.xdata.bind.ElementBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateOutputContextError;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReportArguments;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReportFormulaCache;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReportSupport;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.func.TibExtFunctions;
import com.tibco.cep.mapper.xml.xdata.xpath.type.XTypeComparator;
import com.tibco.xml.channel.infoset.XmlContentHandler;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.PrefixToNamespaceResolver;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SMDT;
import com.tibco.xml.schema.xtype.SmSequenceTypeFactory;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

/**
 * Represents a type-copy-of operation which is a simple, inline XSLT template;
 */
public class TypeCopyOfBinding extends DataComponentBinding implements VirtualXsltElement
{
    private boolean m_includeNsCopy = true;

    public TypeCopyOfBinding(BindingElementInfo info, ExpandedName name)
    {
        super(info, name);
    }

    public TypeCopyOfBinding(BindingElementInfo info, String nsavtformula, String nameavtformula)
    {
        super(info, nsavtformula, nameavtformula);
    }

    /**
     * Clones this binding node.
     * @return Binding A binding node of this type.
     */
    public Binding cloneShallow()
    {
        TypeCopyOfBinding b = new TypeCopyOfBinding(getElementInfo(),getName());
        b.setExplicitXslRepresentation(isExplicitXslRepresentation());
        b.setExplicitNamespaceAVT(getExplicitNamespaceAVT());
        b.setExplicitNameAVT(getExplicitNameAVT());
        b.setIncludeNamespaceCopies(getIncludeNamespaceCopies());
        b.setFormula(getFormula());
        return b;
    }

    public Binding normalize(Binding parent)
    {
        Binding eb;
        if (isExplicitXslRepresentation())
        {
            eb = new ElementBinding(getElementInfo(), getExplicitNamespaceAVT(), getExplicitNameAVT());
        }
        else
        {
            eb = new ElementBinding(getElementInfo(), getName());
        }
        if (m_includeNsCopy)
        {
            Binding ns = new CopyOfBinding(BindingElementInfo.EMPTY_INFO, getFormula() + "/ancestor-or-self::*/namespace::node()");
            eb.addChild(ns);
        }
        Binding febattrs = new CopyOfBinding(BindingElementInfo.EMPTY_INFO, getFormula() + "/@*");
        Binding febels = new CopyOfBinding(BindingElementInfo.EMPTY_INFO, getFormula() + "/node()");
        eb.addChild(febattrs);
        eb.addChild(febels);
        if (parent!=null)
        {
            parent.addChild(eb);
        }
        return eb;
    }

    private static final ExpandedName NAME = TibExtFunctions.makeName("type-copy-of");

    public ExpandedName getName()
    {
        return m_isExplicitXsd ? NAME : m_name;
    }

    public TemplateReport createTemplateReport(TemplateReport parent, ExprContext context, SmSequenceType expectedOutput, SmSequenceType outputContext, int outputValidationLevel, TemplateReportFormulaCache templateReportFormulaCache, TemplateReportArguments templateReportArguments)
    {
        TemplateReport ret = new TemplateReport(this,parent);
        ExprContext updatedContext = TemplateReportSupport.createContextForNamespaceDecls(this,context);
        ret.setContext(updatedContext);
        ret.setChildContext(updatedContext);
        ret.setFollowingContext(context);
        ret.setOutputContext(outputContext);
        ret.setInitialOutputType(expectedOutput);

        ExpandedName ename;
        try
        {
            ename = super.computeComponentExpandedName(updatedContext.getNamespaceMapper());
        }
        catch (PrefixToNamespaceResolver.PrefixNotFoundException pnfe)
        {
            ename = WILDCARD_NAME;
        }
        SmSequenceType element = SmSequenceTypeSupport.getElementInContext(ename,
                                                                           outputContext,
                                                                           context.getOutputSmComponentProvider());
        SmSequenceType contentType;

        if (element.getParticleTerm()==null)
        {
            // If we don't resolve to anything, we don't actually know what it is, so we should just treat it as a
            // previous error for the purposes of things inside the element:
            contentType = SMDT.PREVIOUS_ERROR;
        }
        else
        {
            contentType = SmSequenceTypeSupport.createAttributeAndChildAndTypedValueSequence(element);
        }
        ret.setChildOutputContext(contentType);

        ret.setComputedType(element); // WCETODO maybe look at formula for type sub, too?

        TemplateReportSupport.initForOutputMatch(ret, element, false, templateReportArguments);

        if (ret.getOutputContextError()!=null)
        {
            TemplateOutputContextError oce = ret.getOutputContextError();
            // go to previous missed items & see if it fits there....
            doExtendedMatch(parent, ret, element, templateReportArguments);

            if (ret.getRenameTo()!=null)
            {
                // Because of the apparent rename, rerun children against previous-error; once the rename is fixed,
                // then we'll get new & exciting errors:
                contentType = SMDT.PREVIOUS_ERROR;
                ret.setChildOutputContext(contentType);

// jtb question -- is this change correct?
               SmSequenceType renamedType =
                       SmSequenceTypeFactory.createElement(ret.getRenameTo(),null, contentType.isNillable());
//                     SmSequenceTypeFactory.createElement(ret.getRenameTo(),null);
// end jtb change...
                TemplateReportSupport.initForOutputMatch(ret, renamedType, false, templateReportArguments);

                ret.setOutputContextError(oce); // reset this (because we are still a rename)... sort of hacky.
            }
        }
        TemplateReportSupport.initParsedFormula(ret,templateReportFormulaCache,templateReportArguments);

        if (ret.getComputedType()!=null && ret.getFormulaType()!=null && ret.getOutputContextError()==null)
        {
            // further check of content match:
            // Do a further check that the types are actually equivalent (they could, if fact, be same-name-but-different):
            SmSequenceType computedContent = SmSequenceTypeSupport.createAttributeAndChildAndTypedValueSequenceExcludeMagic(ret.getComputedType().prime());
            SmSequenceType formulaContent = SmSequenceTypeSupport.createAttributeAndChildAndTypedValueSequenceExcludeMagic(ret.getFormulaType().prime());
            XTypeComparator.Properties props = new XTypeComparator.Properties();
            props.setLenientCardinality(true);
            props.setLenientPrimitives(true); // be lenient on these for now; ideally would be settable somewhere...
            XTypeComparator.Result r = XTypeComparator.compareEquivalenceResult(formulaContent,computedContent,props);
            if (r.getEquality()!=XTypeComparator.EQUALS && r.getEquality()!=XTypeComparator.LEFT_ASSIGNABLE_TO_RIGHT)
            {
                // failed.
                ret.addExtendedError(new CopyOfBinding.BadCopyOf(r.getReasonString()));
            }
        }

        TemplateReportSupport.traverseNoChildren(ret,false,templateReportFormulaCache,templateReportArguments);
        TemplateReportSupport.initIsRecursivelyErrorFree(ret);

        return ret;
    }

    private void doExtendedMatch(TemplateReport parent, TemplateReport report, SmSequenceType output, TemplateReportArguments arguments)
    {
        // gets previous missed terms (i.e. out of order):
        if (arguments.getCheckForMove())
        {
            if (TemplateReportSupport.extendedMatchForReorder(parent, report, output, false))
            {
                return;
            }
        }

        if (arguments.getCheckForRenameNamespace())
        {
            // Next, check for a simple namespace change (i.e. renamed externally)
            SmSequenceType nnsOutputType = SmSequenceTypeFactory.createElement(
                    ExpandedName.makeName("*",output.getName().getLocalName()),null, output.isNillable());
            if (TemplateReportSupport.extendedMatchForNamespaceChange(report, nnsOutputType,false))
            {
                return;
            }
        }
        // WCETODO, maybe add more here.
    }

    /**
     * Sets if copying namespaces is part of the type-copy.<br>
     * Really this should always be on, but because CA1 didn't have it, kind of stuck with this option.<br>
     * Default is <code>true</code>
     */
    public void setIncludeNamespaceCopies(boolean includeNsCopies)
    {
        m_includeNsCopy = includeNsCopies;
    }

    public boolean getIncludeNamespaceCopies()
    {
        return m_includeNsCopy;
    }

    public void formatFragment(XmlContentHandler handler) throws SAXException
    {
        handler.comment("Type copy-of not yet formatted...");
    }
}
