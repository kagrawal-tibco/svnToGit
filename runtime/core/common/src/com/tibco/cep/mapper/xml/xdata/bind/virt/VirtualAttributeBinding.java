package com.tibco.cep.mapper.xml.xdata.bind.virt;

import org.xml.sax.SAXException;

import com.tibco.cep.mapper.xml.xdata.bind.AVTUtilities;
import com.tibco.cep.mapper.xml.xdata.bind.AttributeBinding;
import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.BindingElementInfo;
import com.tibco.cep.mapper.xml.xdata.bind.BindingNamespaceManipulationUtils;
import com.tibco.cep.mapper.xml.xdata.bind.BindingTypeCheckUtilities;
import com.tibco.cep.mapper.xml.xdata.bind.ElementBinding;
import com.tibco.cep.mapper.xml.xdata.bind.IfBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReportArguments;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReportFormulaCache;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReportSupport;
import com.tibco.cep.mapper.xml.xdata.bind.TextBinding;
import com.tibco.cep.mapper.xml.xdata.bind.ValueOfBinding;
import com.tibco.cep.mapper.xml.xdata.xpath.ErrorMessageList;
import com.tibco.cep.mapper.xml.xdata.xpath.func.TibExtFunctions;
import com.tibco.xml.channel.infoset.XmlContentHandler;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.data.primitive.QName;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.SmSequenceTypeRemainder;
import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.schema.xtype.SMDT;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

public class VirtualAttributeBinding extends AttributeBinding implements VirtualXsltElement, VirtualDataComponentBinding
{
    // All the these are only relevant for

    // DGH: Not sure why we can't use getFormula(). This is the source of one integrity issue.
//    private boolean m_hasInline;
    private boolean m_inlineIsText; // if not, is value of.
    private VirtualDataComponentCopyMode m_copyMode = VirtualDataComponentCopyMode.REQUIRED_TO_REQUIRED;

    /**
     * Constructor.
     */
    public VirtualAttributeBinding(BindingElementInfo info, ExpandedName name)
    {
        super(info, name);
    }

    public VirtualAttributeBinding(BindingElementInfo info, String optionalNsAVT, String nameAVT)
    {
        super(info, optionalNsAVT, nameAVT);
    }

    private static final ExpandedName VNAME = TibExtFunctions.makeName("vattribute");
    private static final ExpandedName VLITERAL = TibExtFunctions.makeName("literal");
    private static final ExpandedName AVT_ATTR = TibExtFunctions.makeName("value");

    public void formatFragment(XmlContentHandler handler) throws SAXException
    {
        super.issueNamespaceDeclarations(handler);
        handler.startElement(VNAME, null, null);

        if (m_isExplicitXsd)
        {
            handler.attribute(VLITERAL, "true", null);
            if (m_explicitNamespaceAVT != null)
            {
                handler.attribute(NAMESPACE_ATTR, m_explicitNamespaceAVT, null);
            }
            handler.attribute(NAME_ATTR, m_explicitNameAVT, null);
        }
        else
        {
            QName qn = getName().getQName(asXiNode());
            handler.attribute(NAME_ATTR, qn.toString(), null);
        }

        if (!m_isExplicitXsd)
        {
            String f = getFormula();
            if (f == null)
            {
                f = "";
            }
            handler.attribute(AVT_ATTR, f, null);
        }
        if ((null != getFormula()) && m_isExplicitXsd)
        {
            if (m_inlineIsText)
            {
                handler.attribute(INLINE_TEXT_ATTR, getFormula(), null);
            }
            else
            {
                handler.attribute(INLINE_VALUE_OF_ATTR, getFormula(), null);
                if (m_copyMode != VirtualDataComponentCopyMode.REQUIRED_TO_REQUIRED)
                {
                    handler.attribute(COPY_MODE_ATTR, m_copyMode.toString(), null);
                }
            }
        }
        super.issueAdditionalAttributes(handler);
        for (int i = 0; i < getChildCount(); i++)
        {
            getChild(i).formatFragment(handler);
        }
        handler.endElement(VNAME, null, null);
    }

    /**
     * Hook for derived 'virtual' xslt elements to plug-in to.
     */
    protected SmSequenceType analyzeVirtual(TemplateReport report, TemplateReportFormulaCache formulaCache, SmSequenceType from, TemplateReportArguments arguments)
    {
        if (null != getFormula())
        {
            if (!m_inlineIsText)
            {
                analyzeInlineFormula(report, formulaCache, arguments);
                VirtualDataComponentSupport.checkSuperfluousImplicitIf(report, m_copyMode, arguments);
                VirtualDataComponentSupport.checkSuperfluousNil(report, m_copyMode);
                SmSequenceTypeRemainder r = from.getRemainingAfterType(SMDT.STRING, false);
                if (r != null)
                {
                    return r.getRemainder();
                }
            }
        }

        // default does nothing.
        return null;
    }

    private void analyzeInlineFormula(TemplateReport report, TemplateReportFormulaCache formulaCache, TemplateReportArguments args)
    {
        TemplateReportSupport.initParsedFormula(report, formulaCache, args);
        SmSequenceType formulaType = report.getFormulaType();
        if (m_copyMode.isInputAndOutputOptional())
        {
            // If there's an if, adjust the type accordingly:
            formulaType = SmSequenceTypeSupport.createWithItemRequired(formulaType);
        }
        if (report.getExpectedType() != null && report.getFormulaType() != null)
        {
            ErrorMessageList eml = BindingTypeCheckUtilities.typeCheck(
                    BindingTypeCheckUtilities.TYPE_CHECK_VALUE, // do a value type-check.
                    report.getComputedType().typedValue(true),
                    formulaType,
                    report.getXPathExpression().getTextRange(),
                    args.getXPathCheckArguments()
            );
            report.addFormulaErrors(eml);
        }
    }

    private static AttributeBinding copyToNativeRepresentation(VirtualAttributeBinding src)
    {
        AttributeBinding ab;

        if (src.isExplicitXslRepresentation())
        {
            ab = new AttributeBinding(src.getElementInfo(), src.getExplicitNamespaceAVT(), src.getExplicitNameAVT());

            for (int i = 0; i < src.getChildCount(); i++)
            {
                Binding c = src.getChild(i);

                // DGH: Changed second parameter from this to ab (looks like was the wrong parent).
                BindingVirtualizer.normalize(c, ab);
            }

            if (null != src.getFormula())
            {
                if (src.m_inlineIsText)
                {
                    ab.addChild(new TextBinding(BindingElementInfo.EMPTY_INFO, src.getFormula()));
                }
                else
                {
                    // DGH: The following line is not accurate because it fails to take into account
                    // that the formula is an AVT and not an XPath expression.
                    ab.addChild(new ValueOfBinding(BindingElementInfo.EMPTY_INFO, src.getFormula()));
                }
            }
            else
            {
                // DGH: Do nothing
            }
        }
        else
        {
            ab = new AttributeBinding(src.getElementInfo(), src.getName());

            ab.setFormula(src.getFormula());
        }

        return ab;
    }

    private static AttributeBinding convertToExplicitXslRepresentation(VirtualAttributeBinding src)
    {
        AttributeBinding ab;

        if (src.isExplicitXslRepresentation())
        {
            ab = new AttributeBinding(src.getElementInfo(), src.getExplicitNamespaceAVT(), src.getExplicitNameAVT());

            for (int i = 0; i < src.getChildCount(); i++)
            {
                // DGH: Changed second parameter from this(src) to ab (looks like was the wrong parent).
                BindingVirtualizer.normalize(src.getChild(i), ab);
            }

            // DGH: If we are explicit, then use formula directly because it is an XPath expression.
            if (null != src.getFormula())
            {
                if (src.m_inlineIsText)
                {
                    ab.addChild(new TextBinding(BindingElementInfo.EMPTY_INFO, src.getFormula()));
                }
                else
                {
                    // DGH: The following line is not accurate because it fails to take into account
                    // that the formula is an AVT and not an XPath expression.
                    ab.addChild(new ValueOfBinding(BindingElementInfo.EMPTY_INFO, src.getFormula()));
                }
            }
            else
            {
                // DGH: Do nothing
            }
        }
        else
        {
            ab = new AttributeBinding(src.getElementInfo(), src.getName().getNamespaceURI(), src.getName().getLocalName());

            // DGH: The formula is an Attribute Value Template, so it must be converted to an XPath expression.

            if (null != src.getFormula())
            {
                String xpath = AVTUtilities.parseAsExpr(src.getFormula()).toExactString();

                if (src.m_inlineIsText)
                {
                    ab.addChild(new TextBinding(BindingElementInfo.EMPTY_INFO, xpath));
                }
                else
                {
                    ab.addChild(new ValueOfBinding(BindingElementInfo.EMPTY_INFO, xpath));
                }
            }
            else
            {
                // DGH: Do nothing
            }
        }

        return ab;
    }

    public Binding normalize(Binding parent)
    {
        Binding retBinding;
        if (m_copyMode == VirtualDataComponentCopyMode.REQUIRED_TO_REQUIRED)
        {
            if (parent instanceof ElementBinding)
            {
                ElementBinding component = (ElementBinding)parent;

                if (component.isExplicitXslRepresentation())
                {
                    retBinding = convertToExplicitXslRepresentation(this);
                }
                else
                {
                    retBinding = copyToNativeRepresentation(this);
                }
            }
            else
            {
                retBinding = copyToNativeRepresentation(this);
            }
        }
        else if (m_copyMode == VirtualDataComponentCopyMode.OPTIONAL_TO_OPTIONAL)
        {
            if (null != getFormula())
            {
                if (m_inlineIsText)
                {
                    retBinding = copyToNativeRepresentation(this);
                }
                else
                {
                    AttributeBinding ab = convertToExplicitXslRepresentation(this);

                    IfBinding ib = new IfBinding(BindingElementInfo.EMPTY_INFO, getFormula());
                    ib.addChild(ab);
                    retBinding = ib;
                }
            }
            else
            {
                retBinding = copyToNativeRepresentation(this);
            }
        }
        else
        {
            if (null != getFormula())
            {
                if (m_inlineIsText)
                {
                    retBinding = copyToNativeRepresentation(this);
                }
                else
                {
                    AttributeBinding ab = convertToExplicitXslRepresentation(this);

                    NamespaceContextRegistry ni = BindingNamespaceManipulationUtils.createNamespaceImporter(parent == null ? ab : parent);
                    String xsi = ni.getOrAddPrefixForNamespaceURI(XSDL.INSTANCE_NAMESPACE);
                    IfBinding ib = new IfBinding(BindingElementInfo.EMPTY_INFO, VirtualElementMatcher.makeNilToOptionalString(getFormula(), xsi));
                    ib.addChild(ab);
                    retBinding = ib;
                }
            }
            else
            {
                retBinding = copyToNativeRepresentation(this);
            }
        }
        if (parent != null)
        {
            parent.addChild(retBinding);
        }

        return retBinding;
        /*
        AttributeBinding ab;
        if (!isExplicitXslRepresentation())
        {
            // literal attribute:
            ab = new AttributeBinding(getElementInfo(),getName());
            ab.setFormula(getFormula());
        }
        else
        {
            ab = new AttributeBinding(getElementInfo(),getExplicitNamespaceAVT(),getExplicitNameAVT());
        }
        Binding retBinding;
        if (!m_inlineIsText && m_hasInline && m_copyMode!=VirtualDataComponentCopyMode.REQUIRED_TO_REQUIRED)
        {
            if (m_copyMode==VirtualDataComponentCopyMode.OPTIONAL_TO_OPTIONAL)
            {
                // There's an implicit 'if', wrap it:
                IfBinding ib = new IfBinding(BindingElementInfo.EMPTY_INFO,getFormula());
                ib.addChild(ab);
                retBinding = ib;
            }
            else
            {
                // nil->optional
                NamespaceImporter ni = BindingNamespaceManipulationUtils.createNamespaceImporter(parent==null ? ab : parent);
                String xsi = ni.getOrAddPrefixForNamespaceURI(XSDL.INSTANCE_NAMESPACE);
                IfBinding ib = new IfBinding(BindingElementInfo.EMPTY_INFO,VirtualElementMatcher.makeNilToOptionalString(getFormula(),xsi));
                ib.addChild(ab);
                retBinding = ib;
            }
        }
        else
        {
            retBinding = ab;
        }
        if (parent!=null)
        {
            parent.addChild(retBinding);
        }
        for (int i=0;i<getChildCount();i++)
        {
            Binding c = getChild(i);
            BindingVirtualizer.normalize(c,this);
        }
        if (m_hasInline)
        {
            Binding uninline;
            if (m_inlineIsText)
            {
                uninline = new TextBinding(BindingElementInfo.EMPTY_INFO,getFormula());
            }
            else
            {
                uninline = new ValueOfBinding(BindingElementInfo.EMPTY_INFO,getFormula());
            }
            ab.addChild(uninline);
        }
        return retBinding;
        */
    }

    public Binding cloneShallow()
    {
        VirtualAttributeBinding veb = new VirtualAttributeBinding(getElementInfo(), getName());
        veb.setHasInlineFormula(getHasInlineFormula());
        veb.setInlineIsText(getInlineIsText());
        veb.setCopyMode(getCopyMode());
        veb.setFormula(getFormula());
        veb.setExplicitXslRepresentation(isExplicitXslRepresentation());
        veb.setExplicitNamespaceAVT(getExplicitNamespaceAVT());
        veb.setExplicitNameAVT(getExplicitNameAVT());
        return veb;
    }

    public void setHasInlineFormula(boolean inlineFormula)
    {
//      m_hasInline = inlineFormula;
    }

    public boolean getHasInlineFormula()
    {
        return (null != getFormula());
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

    public void setCopyMode(VirtualDataComponentCopyMode cm)
    {
        if (cm == null)
        {
            throw new NullPointerException();
        }
        m_copyMode = cm;
    }

    public VirtualDataComponentCopyMode getCopyMode()
    {
        return m_copyMode;
    }

    public boolean getCanHaveInline()
    {
        // only allowed for xsl:attribute, otherwise, for AVTs, can't be done.
        return isExplicitXslRepresentation();
    }
}

