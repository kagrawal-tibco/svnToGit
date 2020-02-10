package com.tibco.cep.mapper.xml.xdata.bind.virt;

import org.xml.sax.SAXException;

import com.tibco.cep.mapper.util.ResourceBundleManager;
import com.tibco.cep.mapper.xml.nsutils.NamespaceManipulationUtils;
import com.tibco.cep.mapper.xml.xdata.bind.AttributeBinding;
import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.BindingElementInfo;
import com.tibco.cep.mapper.xml.xdata.bind.BindingNamespaceManipulationUtils;
import com.tibco.cep.mapper.xml.xdata.bind.BindingTypeCheckUtilities;
import com.tibco.cep.mapper.xml.xdata.bind.ChooseBinding;
import com.tibco.cep.mapper.xml.xdata.bind.CopyOfBinding;
import com.tibco.cep.mapper.xml.xdata.bind.ElementBinding;
import com.tibco.cep.mapper.xml.xdata.bind.IfBinding;
import com.tibco.cep.mapper.xml.xdata.bind.OtherwiseBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReportArguments;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReportExtendedError;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReportFormulaCache;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReportSupport;
import com.tibco.cep.mapper.xml.xdata.bind.TextBinding;
import com.tibco.cep.mapper.xml.xdata.bind.ValueOfBinding;
import com.tibco.cep.mapper.xml.xdata.bind.WhenBinding;
import com.tibco.cep.mapper.xml.xdata.bind.fix.BindingFixerChange;
import com.tibco.cep.mapper.xml.xdata.xpath.ErrorMessageList;
import com.tibco.cep.mapper.xml.xdata.xpath.MessageCode;
import com.tibco.cep.mapper.xml.xdata.xpath.func.TibExtFunctions;
import com.tibco.xml.channel.infoset.XmlContentHandler;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.data.primitive.NamespaceToPrefixResolver;
import com.tibco.xml.data.primitive.QName;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmNamespaceProvider;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.SmSequenceTypeRemainder;
import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.schema.xtype.SMDT;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

public class VirtualElementBinding extends ElementBinding implements VirtualXsltElement, VirtualDataComponentBinding
{
    public static final ExpandedName NIL_ATTR = ExpandedName.makeName("nil");

    private boolean m_explicitNil; // If set, m_hasInline is n/a --- equivalent to false.
    private boolean m_hasInline;
    private boolean m_inlineIsText; // if not, is value of.
    private VirtualDataComponentCopyMode m_copyMode = VirtualDataComponentCopyMode.REQUIRED_TO_REQUIRED; // Only meaningful when m_inlineIsText is false and m_hasInline is true.
    // Indicates the element/value-of, etc., pattern to create the appropriate mapping.
    private ExpandedName m_optionalTypeSubstitution; // may be null.

    public VirtualElementBinding(BindingElementInfo info, ExpandedName name)
    {
        super(info,name);
    }

    public VirtualElementBinding(BindingElementInfo info, String optNamespaceAVT, String nameAVT)
    {
        super(info,optNamespaceAVT,nameAVT);
    }

    private static final ExpandedName VNAME = TibExtFunctions.makeName("velement");
    private static final ExpandedName VLITERAL = ExpandedName.makeName("literal");

    public void formatFragment(XmlContentHandler handler) throws SAXException {
        super.issueNamespaceDeclarations(handler);

        handler.startElement(VNAME,null,null);

        if (m_isExplicitXsd)
        {
            if (m_explicitNamespaceAVT!=null)
            {
                handler.attribute(NAMESPACE_ATTR,m_explicitNamespaceAVT,null);
            }
            handler.attribute(NAME_ATTR,m_explicitNameAVT,null);
        }
        else
        {
            handler.attribute(VLITERAL,"true",null);
            QName qn;
            try
            {
                qn = getName().getQName(asXiNode());
            }
            catch (Exception e)
            {
                // This shouldn't be happening but it is, and
                // don't crash in printing...
                qn = new QName("<unknown>",getName().getLocalName());
            }
            handler.attribute(NAME_ATTR,qn.toString(),null);
        }
        super.issueAdditionalAttributes(handler);

        if (m_explicitNil)
        {
            handler.attribute(NIL_ATTR,"true",null);
        }
        else
        {
            if (m_hasInline)
            {
                String f = getFormula();
                if (f==null)
                {
                    // just in case.
                    f = "";
                }
                if (m_inlineIsText)
                {
                    handler.attribute(INLINE_TEXT_ATTR,f,null);
                }
                else
                {
                    handler.attribute(INLINE_VALUE_OF_ATTR,f,null);
                    if (m_copyMode!=VirtualDataComponentCopyMode.REQUIRED_TO_REQUIRED)
                    {
                        handler.attribute(COPY_MODE_ATTR,m_copyMode.toString(),null);
                    }
                }
            }
        }
        super.issueAdditionalAttributes(handler);
        for (int i=0;i<getChildCount();i++)
        {
            getChild(i).formatFragment(handler);
        }
        handler.endElement(VNAME,null,null);
    }

    protected SmSequenceType findTypeSubstitution(SmNamespaceProvider scp, TemplateReport ret, SmElement outer)
    {
        if (m_optionalTypeSubstitution==null)
        {
            return super.findTypeSubstitution(scp,ret,outer);
        }
        return findTypeSubstitutionForName(scp,ret,outer,m_optionalTypeSubstitution);
    }

    /**
     * An extended error that will remove a formula for structure nodes that shouldn't have one.
     */
    private static class RemoveMisplacedFormula implements TemplateReportExtendedError
    {
        private static final ExpandedName NAME = TibExtFunctions.makeName("remove-misplaced-formula");

        public static final RemoveMisplacedFormula INSTANCE = new RemoveMisplacedFormula();

        private RemoveMisplacedFormula()
        {
        }

        public boolean canFix(TemplateReport onReport)
        {
            return true;
        }

        public void fix(TemplateReport report)
        {
            VirtualDataComponentBinding veb = (VirtualDataComponentBinding) report.getBinding();
            veb.setHasInlineFormula(false);
        }

        public int getCategory()
        {
            return BindingFixerChange.CATEGORY_ERROR;
        }

        public String formatMessage(TemplateReport report)
        {
            return ResourceBundleManager.getMessage(MessageCode.NO_FORMULA_EXPECTED_ON_STRUCTURE);
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
     * Hook for derived 'virtual' xslt elements to plug-in to.
     */
    protected SmSequenceTypeRemainder analyzeVirtual(TemplateReport report, TemplateReportFormulaCache formulaCache, SmSequenceType from, TemplateReportArguments arguments)
    {
        if (isNilled(report))
        {
            // Does it even make sense?
            SmSequenceType ct = report.getComputedType();
            boolean knownType = ct!=null && ct.getParticleTerm()!=null;
            if (knownType && !ct.isNillable())
            {
                report.addExtendedError(VirtualDataComponentSupport.TurnOffNillable.INSTANCE);
            }
        }
        else
        {
            // non-nil case:
            if (!m_explicitNil && m_hasInline)
            {
                if (!m_inlineIsText)
                {
                    analyzeInlineFormula(report,formulaCache,arguments);
                    VirtualDataComponentSupport.checkSuperfluousImplicitIf(report,m_copyMode,arguments);
                    VirtualDataComponentSupport.checkSuperfluousNil(report,m_copyMode);
                    if (!SmSequenceTypeSupport.isPreviousError(from) && !SmSequenceTypeSupport.isNone(from))
                    {
                        SmSequenceTypeRemainder r = from.getRemainingAfterType(SMDT.STRING,false);
                        if (r!=null)
                        {
                            return r;
                        }
                        else
                        {
                            report.addExtendedError(RemoveMisplacedFormula.INSTANCE);
                        }
                    }
                }
            }
        }
        checkNonstandardXsiNil(report);

        // default does nothing.
        return null;
    }

    /**
     * Check for storing xsi:nil is attribute w/ hardcoded formula; suggest a fix for these.
     */
    private void checkNonstandardXsiNil(TemplateReport report)
    {
        int idx = VirtualDataComponentSupport.findIndexOfNonstandardXsiNil(report);
        if (idx>=0)
        {
            report.addExtendedError(VirtualDataComponentSupport.UpdateNillable.INSTANCE);
        }
    }


    protected boolean isNilled(TemplateReport report)
    {
        return m_explicitNil;
    }

    private void analyzeInlineFormula(TemplateReport report, TemplateReportFormulaCache formulaCache, TemplateReportArguments arguments)
    {
        TemplateReportSupport.initParsedFormula(report,formulaCache,arguments);
        SmSequenceType formulaType = report.getFormulaType();
        if (m_copyMode.isInputOptional())
        {
            formulaType = SmSequenceTypeSupport.createWithItemRequired(formulaType);
        }
        if (report.getExpectedType()!=null && report.getFormulaType()!=null)
        {
            SmSequenceType checkAgainstType;
            if (report.getComputedType().isMixed())
            {
                // There is no typed value of mixed & we don't want to type check against string* or the mixedness in
                // the case of a simple formula; just use a string.
                checkAgainstType = SMDT.STRING;
            }
            else
            {
                checkAgainstType = report.getComputedType().typedValue(true);
            }
            // If it is the empty set, we won't check because that'll be a different (unexpected formula) kind of error anyway.
            if (!SmSequenceTypeSupport.isVoid(checkAgainstType))
            {
            	if (!SmSequenceTypeSupport.isAny(report.getComputedType())) { // BE-15354 : Mapping to complex (any) types are supported in certain contexts (BPMN), so do not perform value type-check in this case
            		ErrorMessageList eml = BindingTypeCheckUtilities.typeCheck(
            				BindingTypeCheckUtilities.TYPE_CHECK_VALUE, // do a value type-check.
            				checkAgainstType,
            				formulaType,
            				report.getXPathExpression().getTextRange(),
            				arguments.getXPathCheckArguments()
            				);
            		report.addFormulaErrors(eml);
            	}
            }
        }
    }

    public Binding normalize(Binding parent)
    {
        ElementBinding eb;
        if (isExplicitXslRepresentation())
        {
            eb = new ElementBinding(getElementInfo(),getExplicitNamespaceAVT(),getExplicitNameAVT());
        }
        else
        {
            eb = new ElementBinding(getElementInfo(),getName());
        }
        Binding addFormulaTo = eb;
        VirtualDataComponentCopyMode useCopyMode = VirtualDataComponentCopyMode.REQUIRED_TO_REQUIRED;
        if (!m_inlineIsText && m_hasInline && !m_explicitNil)
        {
            useCopyMode = m_copyMode;
        }
        Binding retBinding;
        if (useCopyMode.isInputAndOutputOptional())
        {
            // There's an implicit 'if', wrap it:
            IfBinding ib = new IfBinding(BindingElementInfo.EMPTY_INFO,getFormula());
            ib.addChild(eb);
            retBinding = ib;
        }
        else
        {
            if (useCopyMode==VirtualDataComponentCopyMode.OPTIONAL_TO_NIL)
            {
                // The remainder of processing is done later.
                retBinding = eb;
            }
            else
            {
                if (useCopyMode==VirtualDataComponentCopyMode.NIL_TO_OPTIONAL)
                {
                    NamespaceContextRegistry ni = BindingNamespaceManipulationUtils.createNamespaceImporter(parent==null ? eb : parent);
                    String pfx = ni.getOrAddPrefixForNamespaceURI(XSDL.INSTANCE_NAMESPACE,"xsi");
                    String test = VirtualElementMatcher.makeNilToOptionalString(getFormula(),pfx);
                    IfBinding cb = new IfBinding(BindingElementInfo.EMPTY_INFO,test);
                    cb.addChild(eb);
                    retBinding = cb;
                }
                else
                {
                    // required->required or nil->nil requires no surrounding statements.
                    retBinding = eb;
                }
            }
        }

        if (parent!=null)
        {
            parent.addChild(retBinding);
        }
        NamespaceContextRegistry ni = BindingNamespaceManipulationUtils.createNamespaceImporter(eb);
        Binding typeSub = normalizeTypeSubstitution(ni);
        if (typeSub!=null)
        {
            eb.addChild(typeSub);
        }
        Binding nil = normalizeNil(ni);
        if (nil!=null)
        {
            eb.addChild(nil);
        }
        for (int i=0;i<getChildCount();i++)
        {
            Binding c = getChild(i);
            BindingVirtualizer.normalize(c,eb);
        }
        if (useCopyMode==VirtualDataComponentCopyMode.OPTIONAL_TO_NIL)
        {
            ChooseBinding cb = new ChooseBinding(BindingElementInfo.EMPTY_INFO);
            eb.addChild(cb);
            WhenBinding wb = new WhenBinding(BindingElementInfo.EMPTY_INFO,"exists(" + getFormula() + ")");
            OtherwiseBinding ob = new OtherwiseBinding(BindingElementInfo.EMPTY_INFO);
            ob.addChild(buildExplicitNilAttribute(ni));

            cb.addChild(wb);
            cb.addChild(ob);

            // Below, the inline formula will get added there:
            addFormulaTo = wb;
        }
        if (m_hasInline && !m_explicitNil)
        {
            Binding uninline = computeNormalInline();
            if (useCopyMode==VirtualDataComponentCopyMode.NIL_TO_NIL || useCopyMode==VirtualDataComponentCopyMode.OPTIONALNIL_TO_OPTIONALNIL)
            {
                String pfx = ni.getOrAddPrefixForNamespaceURI(XSDL.INSTANCE_NAMESPACE,"xsi");
                eb.addChild(new CopyOfBinding(BindingElementInfo.EMPTY_INFO,getFormula()+"/@" + pfx + ":nil"));
            }
            addFormulaTo.addChild(uninline);
        }
        return retBinding;
    }

    private Binding computeNormalInline()
    {
        if (m_inlineIsText)
        {
            return new TextBinding(BindingElementInfo.EMPTY_INFO,getFormula());
        }
        else
        {
            return new ValueOfBinding(BindingElementInfo.EMPTY_INFO,getFormula());
        }
    }

    private Binding normalizeNil(NamespaceContextRegistry ni)
    {
        if (m_explicitNil)
        {
            return buildNilAttribute(ni);
        }
        else
        {
            return null;
        }
    }

    private Binding buildNilAttribute(NamespaceContextRegistry ni)
    {
        AttributeBinding ab;
        if (isExplicitXslRepresentation())
        {
            ab = buildExplicitNilAttribute(ni);
        }
        else
        {
            // or another...
            ExpandedName tname = XSDL.ATTR_NIL.getExpandedName();
            ab = new AttributeBinding(BindingElementInfo.EMPTY_INFO,tname);
            ab.setFormula("true"); // AVT.
        }
        return ab;
    }

    private static AttributeBinding buildExplicitNilAttribute(NamespaceContextRegistry ni)
    {
        AttributeBinding ab;
        // store it one way:
        String pfx = ni.getOrAddPrefixForNamespaceURI(XSDL.INSTANCE_NAMESPACE,"xsi");
        ab = new AttributeBinding(BindingElementInfo.EMPTY_INFO,null,new QName(pfx,XSDL.ATTR_NIL.getName()).toString());
        ab.addChild(new TextBinding(BindingElementInfo.EMPTY_INFO,"true"));
        return ab;
    }

    /**
     * Does the type substitution part of normalization.
     * @return The normal type substitution binding, or null for none.
     */
    private Binding normalizeTypeSubstitution(NamespaceContextRegistry ni)
    {
        if (m_optionalTypeSubstitution!=null)
        {
            AttributeBinding ab;
            String formula = NamespaceManipulationUtils.getOrAddQNameFromExpandedName(m_optionalTypeSubstitution,ni).toString();
            if (isExplicitXslRepresentation())
            {
                // store it one way:
                String pfx = ni.getOrAddPrefixForNamespaceURI(XSDL.INSTANCE_NAMESPACE,"xsi");
                ab = new AttributeBinding(BindingElementInfo.EMPTY_INFO,null,new QName(pfx,XSDL.ATTR_TYPE.getName()).toString());
                ab.addChild(new TextBinding(BindingElementInfo.EMPTY_INFO,formula));
            }
            else
            {
                // or another...
                ExpandedName tname = XSDL.ATTR_TYPE.getExpandedName();
                ab = new AttributeBinding(BindingElementInfo.EMPTY_INFO,tname);
                ab.setFormula(formula);
            }
            return ab;
        }
        else
        {
            return null;
        }
    }

    public Binding cloneShallow()
    {
        VirtualElementBinding veb;
        if (isExplicitXslRepresentation())
        {
            veb = new VirtualElementBinding(getElementInfo(),getExplicitNamespaceAVT(),getExplicitNameAVT());
        }
        else
        {
            veb = new VirtualElementBinding(getElementInfo(),getName());
        }
        veb.setHasInlineFormula(getHasInlineFormula());
        veb.setInlineIsText(getInlineIsText());
        veb.setCopyMode(getCopyMode());
        veb.setFormula(getFormula());
        veb.setTypeSubstitution(getTypeSubstitution());
        veb.setExplicitXslRepresentation(isExplicitXslRepresentation());
        veb.setExplicitNil(isExplicitNil());
        return veb;
    }

    public void setTypeSubstitution(ExpandedName optionalTypeName)
    {
        m_optionalTypeSubstitution = optionalTypeName;
    }

    /**
     * Gets the type (xsi:type) substitution name, if any.
     * @return The type substitution name, or null, for none.
     */
    public ExpandedName getTypeSubstitution()
    {
        return m_optionalTypeSubstitution;
    }

    public void setExplicitNil(boolean explicitNil)
    {
        m_explicitNil = explicitNil;
    }

    public boolean isExplicitNil()
    {
        return m_explicitNil;
    }

    public VirtualDataComponentCopyMode getCopyMode()
    {
        return m_copyMode;
    }

    public void setCopyMode(VirtualDataComponentCopyMode copyMode)
    {
        if (copyMode==null)
        {
            throw new NullPointerException();
        }
        m_copyMode = copyMode;
    }

    public void setHasInlineFormula(boolean inlineFormula)
    {
        m_hasInline = inlineFormula;
    }

    public boolean getHasInlineFormula()
    {
        return m_hasInline;
    }

    /**
     * Sets if the inline is either xsl:text (true) or xsl:value-of (false)
     */
    public void setInlineIsText(boolean inlineIsText)
    {
        m_inlineIsText = inlineIsText;
    }

    /**
     * Gets if the inline is either xsl:text (true) or xsl:value-of (false).<br>
     * Not applicable if not {@link #getHasInlineFormula}.
     */
    public boolean getInlineIsText()
    {
        return m_inlineIsText;
    }

    public boolean getCanHaveInline()
    {
        return true; // always true for elements.
    }
}

