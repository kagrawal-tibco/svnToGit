/*******************************************************************************
 * Copyright 2001 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.xml.xdata.bind;

import java.util.HashSet;

import org.xml.sax.SAXException;

import com.tibco.cep.mapper.util.ResourceBundleManager;
import com.tibco.cep.mapper.xml.xdata.bind.fix.BindingFixerChange;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.MessageCode;
import com.tibco.cep.mapper.xml.xdata.xpath.func.TibExtFunctions;
import com.tibco.util.RuntimeWrapException;
import com.tibco.xml.channel.infoset.XmlContentHandler;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.data.primitive.NamespaceResolver;
import com.tibco.xml.data.primitive.NamespaceToPrefixResolver;
import com.tibco.xml.data.primitive.PrefixToNamespaceResolver;
import com.tibco.xml.data.primitive.QName;
import com.tibco.xml.schema.SmComponent;
import com.tibco.xml.schema.SmComponentProviderEx;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmGlobalComponentNotFoundException;
import com.tibco.xml.schema.SmNamespace;
import com.tibco.xml.schema.SmNamespaceProvider;
import com.tibco.xml.schema.SmParticleTerm;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.SmSequenceTypeRemainder;
import com.tibco.xml.schema.SmSupport;
import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.SmWildcard;
import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.schema.xtype.SMDT;
import com.tibco.xml.schema.xtype.SmSequenceTypeFactory;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

/**
 * Represents an XML Element.
 */
public class ElementBinding extends DataComponentBinding
{
    public ElementBinding(BindingElementInfo info, ExpandedName expandedName)
    {
        super(info,expandedName);
    }

    public ElementBinding(BindingElementInfo info, String optionalNamespaceAVT, String nameAVT)
    {
        super(info,optionalNamespaceAVT,nameAVT);
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

        TemplateReportSupport.checkNamespaceDeclsAndExtraAttributes(ret,templateReportArguments);

        ExpandedName ename;
        try
        {
            ename = super.computeComponentExpandedName(updatedContext.getNamespaceMapper());
        }
        catch (PrefixToNamespaceResolver.PrefixNotFoundException pnfe)
        {
            ename = WILDCARD_NAME;
        }
        SmSequenceType element = SmSequenceTypeSupport.getElementInContext(ename,outputContext,context.getOutputSmComponentProvider());
        SmSequenceType contentType;
        SmSequenceType subbedElementType; // The substituted element type.

        boolean isNilled = isNilled(ret);

        int myOutputValidationLevel;
        int myChildOutputValidationLevel;
        if (element.getParticleTerm()==null)
        {
            // If we don't resolve to anything, we don't actually know what it is, so we should just treat it as a
            // previous error for the purposes of things inside the element:
            contentType = SMDT.PREVIOUS_ERROR;
            subbedElementType = element;

            // We can't really validate underneath, so give up:
            myChildOutputValidationLevel = SmWildcard.SKIP;
            myOutputValidationLevel = outputValidationLevel;
        }
        else
        {
            TemplateReportSupport.checkSchemaComponentErrors(ret,element);

            SmElement pt = (SmElement)element.getParticleTerm();
            SmSequenceType typeSub = findTypeSubstitution(context.getOutputSchemaProvider(),ret,pt);
            if (typeSub!=null)
            {
                typeCheckSubstitutedType(ret,typeSub);
            }
            subbedElementType = typeSub!=null ? typeSub : element;
            if (!isNilled)
            {
                if (SmSequenceTypeSupport.isAny(subbedElementType))
                {
                    contentType = SMDT.ANY_TYPE_CHILD_CONTENT;
                }
                else
                {
                    contentType = SmSequenceTypeSupport.createAttributeAndChildAndTypedValueSequenceExcludeMagic(subbedElementType);
                }
            }
            else
            {
                // when nilled, only attributes allowed.
                contentType = SmSequenceTypeSupport.stripMagicAttributes(subbedElementType.attributeAxis());
            }
            myChildOutputValidationLevel = SmWildcard.STRICT;
            myOutputValidationLevel = outputValidationLevel;
        }
        ret.setChildOutputContext(contentType);

        ret.setComputedType(subbedElementType);
        checkForAbstractElement(ret);
        checkForAbstractType(ret);

        SmSequenceType outputTypeMatchTest = element.getParticleTerm()==
                null ? SmSequenceTypeFactory.createElement(ename,null,element.isNillable()) : element;

        TemplateReportSupport.initForOutputMatch(ret, outputTypeMatchTest, false, templateReportArguments);
        if (ret.getExpectedType()!=null)
        {
            SmSequenceType t = SmSequenceTypeSupport.stripOccursAndParens(ret.getExpectedType());
            if (t.getParticleTerm() instanceof SmWildcard)
            {
                myOutputValidationLevel = ((SmWildcard)t.getParticleTerm()).getProcessContents();
            }
        }
        if (element.getParticleTerm()==null && ret.getRenameTo()==null)
        {
            // Attempt to see why it wasn't found:
            if (myOutputValidationLevel==SmWildcard.STRICT) // (If we're not already gone...)
            {
               try {
                  refineOutputContextError(context.getOutputSmComponentProvider(),
                                           context.getOutputSchemaProvider(),
                                           ret,ename);
               }
               catch (SmGlobalComponentNotFoundException e) {
                  throw new RuntimeWrapException(e);
               }
            }
        }

        if (ret.getOutputContextError()!=null)
        {
            // go to previous missed items & see if it fits there....
            doExtendedMatch(parent,templateReportFormulaCache, ret, expectedOutput, outputTypeMatchTest,templateReportArguments);

            if (ret.getRenameTo()!=null)
            {
                TemplateOutputContextError oce = ret.getOutputContextError();
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
        // Analyze children:
        SmSequenceType leftoverContentType = TemplateReportSupport.traverseChildren(ret,contentType,myChildOutputValidationLevel,templateReportFormulaCache,templateReportArguments);

        // In order to handle a very particular case of choose/otherwise with content models of + (i.e. one or more),
        // need a trivial second pass (which does a fixup):
        TemplateReportSupport.fixupExtraOtherwiseOnChildren(ret);

        SmSequenceTypeRemainder remainder = analyzeVirtual(ret,templateReportFormulaCache,leftoverContentType, templateReportArguments);
        if (remainder==null)
        {
            remainder = new SmSequenceTypeRemainder(SMDT.VOID,SMDT.VOID,leftoverContentType);
        }

        TemplateReportSupport.addMissingEndingTerms(ret,remainder.getSkipped(),templateReportArguments);
        TemplateReportSupport.addMissingEndingTerms(ret,remainder.getRemainder(),templateReportArguments);
        TemplateReportSupport.initIsRecursivelyErrorFree(ret);

        return ret;
    }

    /**
     * Checks the computed type for abstract-element, and, if found, adds errors.
     * @param report
     */
    private void checkForAbstractElement(TemplateReport report)
    {
        SmSequenceType ct = report.getComputedType();
        SmParticleTerm pt = ct.getParticleTerm();
        if (!(pt instanceof SmElement))
        {
            return;
        }
        SmElement el = (SmElement) pt;
        if (el.isAbstract())
        {
            // Shame on you!
            report.addExtendedError(AbstractElement.INSTANCE);
            return;
        }
    }

    /**
     * Checks the computed type for abstract-element, and, if found, adds errors.
     * @param report
     */
    private void checkForAbstractType(TemplateReport report)
    {
        SmType t = SmSequenceTypeSupport.getSchemaType(report.getComputedType());
        if (t==null)
        {
            return;
        }
        if (t.isAbstract())
        {
            // Shame on you!
            report.addExtendedError(AbstractType.INSTANCE);
            return;
        }
    }

    private void typeCheckSubstitutedType(TemplateReport report, SmSequenceType subbedType)
    {
        SmType elOverride = subbedType.getElementOverrideType();
        SmElement el = (SmElement) subbedType.getParticleTerm();
        SmType baseType = el.getType();
        if (!SmSupport.isEqualOrDerived(elOverride,baseType))
        {
            // yikes, report this:
            report.addExtendedError(BAD_TYPE_SUBSTITUTION_ERROR);
        }
    }

    protected boolean isNilled(TemplateReport report)
    {
        return false; // override.
    }

    protected static abstract class TemplateReportSchemaError implements TemplateReportExtendedError
    {
        private ExpandedName m_name;

        public TemplateReportSchemaError(ExpandedName name)
        {
            m_name = name;
        }

        public boolean canFix(TemplateReport onReport)
        {
            return false;
        }

        public void fix(TemplateReport report)
        {
        }

        public int getCategory()
        {
            return BindingFixerChange.CATEGORY_ERROR;
        }

        public void formatFragment(XmlContentHandler handler, NamespaceToPrefixResolver resolver) throws SAXException
        {
            handler.startElement(m_name,null,null);
            handler.endElement(m_name,null,null);
        }

        public void registerNamespaces(NamespaceContextRegistry nsContextRegistry)
        {
            nsContextRegistry.getOrAddPrefixForNamespaceURI(m_name.getNamespaceURI());
        }
    }

    protected static final TemplateReportExtendedError CANT_FIND_SCHEMA_FOR_TYPE_ERROR = new TemplateReportSchemaError(TibExtFunctions.makeName("noSchemaForType"))
    {
        public String formatMessage(TemplateReport report)
        {
            return ResourceBundleManager.getMessage(MessageCode.CANNOT_LOAD_SCHEMA_FOR_TYPE_SUBSTITUTION);
        }
    };

    protected static final TemplateReportExtendedError BAD_TYPE_SUBSTITUTION_ERROR = new TemplateReportSchemaError(TibExtFunctions.makeName("notASubtype"))
    {
        public String formatMessage(TemplateReport report)
        {
            String name = report.getComputedType().getParticleTerm().getName();
            return ResourceBundleManager.getMessage(MessageCode.BAD_TYPE_SUBSTITUTION,name);
        }
    };

    protected static final TemplateReportExtendedError CANT_FIND_TYPE_IN_NAMESPACE_ERROR = new TemplateReportSchemaError(TibExtFunctions.makeName("noSchemaForType"))
    {
        public String formatMessage(TemplateReport report)
        {
            return ResourceBundleManager.getMessage(MessageCode.NO_SUCH_TYPE_FOR_TYPE_SUBSTITUTION);
        }
    };

    /**
     * Finds an 'xsi:type' substitution and returns the XType, or null if none.
     */
    protected SmSequenceType findTypeSubstitution(SmNamespaceProvider scp, TemplateReport ret, SmElement outer)
    {
        int cc = getChildCount();
        for (int i=0;i<cc;i++)
        {
            Binding ch = getChild(i);
            if (ch instanceof AttributeBinding)
            {
                AttributeBinding ab = (AttributeBinding) ch;
                NamespaceResolver r = BindingNamespaceManipulationUtils.createChildNamespaceResolver(ret.getContext().getNamespaceMapper(),ab);
                if (!ab.isExplicitXslRepresentation())
                {
                    if (ab.getName().equals(XSDL.ATTR_TYPE.getExpandedName()))
                    {
                        return findTypeSubstitutionForName(scp,r,ret,outer,ab.getFormula());
                    }
                }
                else
                {
                    try
                    {
                        ExpandedName ename = ab.computeComponentExpandedName(r);
                        if (ename.equals(XSDL.ATTR_TYPE.getExpandedName()))
                        {
                            if (ab.getChildCount()==1 && ab.getChild(0) instanceof TextBinding)
                            {
                                String txt = ab.getChild(0).getFormula();
                                return findTypeSubstitutionForName(scp,r,ret,outer,txt);
                            }
                        }
                    }
                    catch (PrefixToNamespaceResolver.PrefixNotFoundException pnfe)
                    {
                        return null;
                    }
                }
            }
        }
        return null;
    }

    protected SmSequenceType findTypeSubstitutionForName(SmNamespaceProvider scp, NamespaceResolver r, TemplateReport ret, SmElement outer, String qnameform)
    {
        if (qnameform==null)
        {
            return null;
        }
        if (AVTUtilities.isAVTString(qnameform))
        {
            return null;
        }
        QName qn = new QName(qnameform);
        try
        {
            ExpandedName typeName = qn.getExpandedName(r);
            return findTypeSubstitutionForName(scp,ret,outer,typeName);
        }
        catch (Exception e)
        {
            // WCETODO add better error;
            ret.addExtendedError(CANT_FIND_SCHEMA_FOR_TYPE_ERROR);
            return null;
        }
    }

    protected SmSequenceType findTypeSubstitutionForName(SmNamespaceProvider scp, TemplateReport ret, SmElement outer, ExpandedName typeName)
    {
        SmNamespace ns = scp.getNamespace(typeName.getNamespaceURI());
        if (ns==null)
        {
            ret.addExtendedError(CANT_FIND_SCHEMA_FOR_TYPE_ERROR);
            return null;
        }
        SmType tt = (SmType)(ns.getComponent(SmComponent.TYPE_TYPE, typeName.getLocalName()));
        if (tt==null)
        {
            ret.addExtendedError(CANT_FIND_TYPE_IN_NAMESPACE_ERROR);
            return null;
        }
        return SmSequenceTypeFactory.create(outer,tt);
    }

    /**
     * Called to improve the error message of an output-context error WHEN (and only when) the schema component couldn't be resolved.<br>
     * It refines the message by (at least trying to) indicate why it wasn't found.
     */
    private void refineOutputContextError(SmComponentProviderEx scpEx,
                                          SmNamespaceProvider nsProvider,
                                          TemplateReport ret,
                                          ExpandedName elName)
    throws SmGlobalComponentNotFoundException
    {
        String nss = elName.getNamespaceURI();
        if (nss==null || nss.length()==0)
        {
            // no-namespace, must be locally defined.
            ret.setOutputContextError(TemplateOutputContextError.NO_COMPONENT_IN_CONTEXT);
            return; // no namespace, context error.
        }
        SmSequenceType t = ret.getOutputContext().parentAxis();
        ExpandedName n = t.prime().getName();
        if (n!=null && n.getNamespaceURI()!=null && n.getNamespaceURI().equals(nss))
        {
            // Check for ambiguity (can still happen for crazy substitution group crap:)
            if (SmSequenceTypeSupport.isAmbiguousElementInContext(scpEx,elName))
            {
                ret.setOutputContextError(TemplateOutputContextError.AMBIGUOUS_COMPONENT_IN_PROJECT);
            }
            else
            {
                ret.setOutputContextError(TemplateOutputContextError.NO_COMPONENT_IN_CONTEXT);
            }
            return;
        }
        SmNamespace ns = nsProvider.getNamespace(nss);
        if (ns==null)
        {
            // Refine the error:
            ret.setOutputContextError(TemplateOutputContextError.NO_SCHEMA);
            return;
        }
//       SmElement tt = SmSequenceTypeSupport.getElement(ns, elName.getLocalName());
        SmElement tt = scpEx.getElement(elName);
        if (tt==null)
        {
            if (SmSequenceTypeSupport.isAmbiguousElementInContext(tt))
            {
                ret.setOutputContextError(TemplateOutputContextError.AMBIGUOUS_COMPONENT_IN_PROJECT);
            }
            else
            {
                ret.setOutputContextError(TemplateOutputContextError.NO_COMPONENT_IN_SCHEMA);
            }
            return;
        }
        // no more refinement.
    }

    /**
     * Hook for derived 'virtual' xslt elements to plug-in to.
     */
    protected SmSequenceTypeRemainder analyzeVirtual(TemplateReport report, TemplateReportFormulaCache formulaCache, SmSequenceType contentType, TemplateReportArguments arguments)
    {
        // default does nothing.
        return null;
    }

    /**
     * Used in extended error checking to detect rename.
     */
    private TemplateReport createRenameTemplateReport(TemplateReport parent, ExprContext context, SmSequenceType expectedOutput, SmSequenceType outputContext, int outputValidationLevel, TemplateReportFormulaCache formulaCache, TemplateReportArguments templateReportArguments)
    {
        TemplateReport ret = new TemplateReport(this,parent);
        ret.setContext(context);
        ret.setChildContext(context);
        ret.setFollowingContext(context);
        ret.setOutputContext(outputContext);
        ret.setInitialOutputType(expectedOutput);

        SmSequenceType element = expectedOutput; // assume I am that output.
        SmSequenceType contentType;
        SmSequenceType primeEl = element.prime();
        if (primeEl.getParticleTerm()==null)
        {
            // If we don't resolve to anything, we don't actually know what it is, so we should just treat it as a
            // previous error for the purposes of things inside the element:
            contentType = SMDT.PREVIOUS_ERROR;
        }
        else
        {
            contentType = SmSequenceTypeSupport.createAttributeAndChildAndTypedValueSequenceExcludeMagic(element);
        }
        ret.setChildOutputContext(contentType);

        ret.setComputedType(element);

        ret.setRemainingOutputType(SMDT.PREVIOUS_ERROR); // this doesn't matter, the 'report' here is just a holder.

        SmSequenceType leftoverContentType = TemplateReportSupport.traverseChildren(ret,contentType,outputValidationLevel,formulaCache,templateReportArguments);
        TemplateReportSupport.addMissingEndingTerms(ret,leftoverContentType,templateReportArguments);
        TemplateReportSupport.initIsRecursivelyErrorFree(ret);

        return ret;
    }

    public ExpandedName getName()
    {
        return m_isExplicitXsd ? NAME : m_name;
    }

    private void doExtendedMatch(TemplateReport parent, TemplateReportFormulaCache formulaCache, TemplateReport report, SmSequenceType expectedOutput, SmSequenceType outputType, TemplateReportArguments arguments)
    {
        // gets previous missed terms (i.e. out of order):
        if (arguments.getCheckForMove())
        {
            if (TemplateReportSupport.extendedMatchForReorder(parent, report, outputType, false))
            {
                return;
            }
        }

        if (arguments.getCheckForRenameNamespace())
        {
            // Next, check for a simple namespace change (i.e. renamed externally)
            SmSequenceType nnsOutputType =
                    SmSequenceTypeFactory.createElement(
                            ExpandedName.makeName("*", outputType.getName().getLocalName()),
                            null,
                            outputType.isNillable());
            if (TemplateReportSupport.extendedMatchForNamespaceChange(report, nnsOutputType,false))
            {
                return;
            }
        }
        if (arguments.getCheckForRenameGeneral())
        {
            // WCETODO --- add 'switched to an attribute' check.

            // Next, ignore the name completely & try matching child content:
            SmSequenceType remainingType = expectedOutput;
            double bestMatchingTerms = 0;
            SmSequenceType bestTerm = null;
            for (;;) {
                // First try any namespace name:
                SmSequenceTypeRemainder remainder = remainingType.skip();
                // add skipped...
                if (remainder==null)
                {
                    // can't match...
                    break;
                }
                SmSequenceType matched = remainder.getMatched();
                TemplateReportArguments newArgs = new TemplateReportArguments();
                newArgs.setCheckForMove(false); // take a swing at it:
                newArgs.setCheckForRename(false);
                TemplateReport tempReport = createRenameTemplateReport(null,report.getContext(),matched,matched,SmWildcard.STRICT,formulaCache,newArgs);
                double numMatchingTerms = countMatchingTerms(tempReport);
                if (numMatchingTerms>bestMatchingTerms && SmSequenceTypeSupport.getElementOrAttributeName(matched)!=null)
                {
                    bestMatchingTerms = numMatchingTerms;
                    bestTerm = matched;
                }
                remainingType = remainder.getRemainder();
            }
            if (bestTerm!=null)
            {
                ExpandedName name = SmSequenceTypeSupport.getElementOrAttributeName(bestTerm);
                if (name!=null)
                {
                    report.setRenameTo(name);
                    return;
                }
            }
        }
    }

    /**
     * Counts the matching terms using a weighted system where anys receive a lower point total than non-anys.
     * @param report The report
     * @return The matching score (1 point per match, any matches count at .5)
     */
    private double countMatchingTerms(TemplateReport report)
    {
        int ct = 0;
        if (report.getComputedType()!=null && (!(report.getBinding() instanceof ValueOfBinding) && !(report.getBinding() instanceof TextBinding))) {
            if ((report.getOutputContextError()==null && report.getExpectedType()!=null) || report.getMoveToPreceding()!=null)
            {
                if (SmSequenceTypeSupport.isPreviousError(report.getExpectedType()))
                {
                    // no score!
                }
                else
                {
                    if (SmSequenceTypeSupport.stripOccursAndParens(report.getExpectedType()).getName()==null)
                    {
                        // A wildcard match, count as half only.
                        ct+= 0.5;
                    }
                    else
                    {
                        // it matched:
                        ct++;
                    }
                }
            }
        }
        int cc = report.getChildCount();
        for (int i=0;i<cc;i++) {
            ct += countMatchingTerms(report.getChild(i));
        }
        return ct;
    }

    public void formatFragment(XmlContentHandler handler) throws SAXException {
        super.issueNamespaceDeclarations(handler);

        // preserve even malformed names:
        boolean nameMalformed = isNameMalformed();
        if (nameMalformed)
        {
            handler.startElement(NAME,null,null);
            String namespace = m_name.getNamespaceURI();
            if (namespace==null)
            {
                namespace = "";
            }
            handler.attribute(NAMESPACE_ATTR,namespace,null);
            handler.attribute(NAME_ATTR,m_name.getLocalName(),null);
            handler.attribute(MALFORMED_ATTR,"true",null);
            super.issueAdditionalAttributes(handler);
            for (int i=0;i<getChildCount();i++)
            {
                getChild(i).formatFragment(handler);
            }
            handler.endElement(NAME,null,null);
        }
        else
        {
            if (isExplicitXslRepresentation())
            {
                handler.startElement(NAME,null,null);
                if (m_explicitNamespaceAVT!=null)
                {
                    handler.attribute(NAMESPACE_ATTR,m_explicitNamespaceAVT,null);
                }
                handler.attribute(NAME_ATTR,m_explicitNameAVT,null);
                super.issueAdditionalAttributes(handler);
                for (int i=0;i<getChildCount();i++) {
                    getChild(i).formatFragment(handler);
                }
                handler.endElement(NAME,null,null);
            }
            else
            {
                ExpandedName ename = getName();
                handler.startElement(ename,null,null);
                // two passes, one over attributes (first), one over elements:
                int endOfAttrs = getChildCount();
                HashSet uniqueNames = null; // lazily allocate.
                for (int i=0;i<getChildCount();i++)
                {
                    if (getChild(i) instanceof AttributeBinding)
                    {
                        AttributeBinding ab = (AttributeBinding) getChild(i);
                        if (!ab.isExplicitXslRepresentation() && ab.getChildCount()==0)
                        {
                            if (uniqueNames==null)
                            {
                                uniqueNames = new HashSet();
                            }
                            // Make sure the name is unique, too, o.w. write it out as illegal.
                            if (!uniqueNames.contains(ab.getName()))
                            {
                                uniqueNames.add(ab.getName());
                                ab.formatLiteralFragment(handler);
                                continue;
                            }
                        }
                    }
                    endOfAttrs = i;
                    break;
                }
                // Starting from the last legally written out attribute, write out elements, comments, illegal-attributes, etc.
                for (int i=endOfAttrs;i<getChildCount();i++)
                {
                    getChild(i).formatFragment(handler);
                }
                handler.endElement(ename,null,null);
            }
        }
    }

    /**
     * An extended error that indicates an abstract element (no auto-fix available)
     */
    static class AbstractElement implements TemplateReportExtendedError
    {
        private static final ExpandedName NAME = TibExtFunctions.makeName("abstract-element");

        public static final AbstractElement INSTANCE = new AbstractElement();

        private AbstractElement()
        {
        }

        public boolean canFix(TemplateReport onReport)
        {
            return false;
        }

        public void fix(TemplateReport report)
        {
        }

        public int getCategory()
        {
            return BindingFixerChange.CATEGORY_ERROR;
        }

        public String formatMessage(TemplateReport report)
        {
            return ResourceBundleManager.getMessage(MessageCode.ABSTRACT_ELEMENT);
        }

        public void formatFragment(XmlContentHandler handler, NamespaceToPrefixResolver resolver) throws SAXException
        {
            handler.startElement(NAME,null,null);
            handler.endElement(NAME,null,null);
        }

        public void registerNamespaces(NamespaceContextRegistry nsContextRegistry)
        {
            nsContextRegistry.getOrAddPrefixForNamespaceURI(NAME.getNamespaceURI());
        }
    }

    /**
     * An extended error that indicates an abstract element (no auto-fix available)
     */
    static class AbstractType implements TemplateReportExtendedError
    {
        private static final ExpandedName NAME = TibExtFunctions.makeName("abstract-type");

        public static final AbstractType INSTANCE = new AbstractType();

        private AbstractType()
        {
        }

        public boolean canFix(TemplateReport onReport)
        {
            return false;
        }

        public int getCategory()
        {
            return BindingFixerChange.CATEGORY_ERROR;
        }

        public void fix(TemplateReport report)
        {
        }

        public String formatMessage(TemplateReport report)
        {
            return ResourceBundleManager.getMessage(ResourceBundleManager.getMessage(MessageCode.ABSTRACT_TYPE));
        }

        public void formatFragment(XmlContentHandler handler, NamespaceToPrefixResolver resolver) throws SAXException
        {
            handler.startElement(NAME,null,null);
            handler.endElement(NAME,null,null);
        }

        public void registerNamespaces(NamespaceContextRegistry nsContextRegistry)
        {
            nsContextRegistry.getOrAddPrefixForNamespaceURI(NAME.getNamespaceURI());
        }
    }

    private static final ExpandedName NAME = ReadFromXSLT.makeName("element");
    protected static final ExpandedName NAME_ATTR = ExpandedName.makeName("name");
    protected static final ExpandedName NAMESPACE_ATTR = ExpandedName.makeName("namespace");

    /**
     * Clones this binding node.
     * @return Binding A binding node of this type.
     */
    public Binding cloneShallow()
    {
        ElementBinding eb = new ElementBinding(getElementInfo(),getName());
        eb.setExplicitXslRepresentation(isExplicitXslRepresentation());
        eb.setExplicitNamespaceAVT(getExplicitNamespaceAVT());
        eb.setExplicitNameAVT(getExplicitNameAVT());
        return eb;
    }
}
