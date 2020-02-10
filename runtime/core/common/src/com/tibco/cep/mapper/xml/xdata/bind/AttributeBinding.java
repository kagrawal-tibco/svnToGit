/*******************************************************************************
 * Copyright 2001 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.xml.xdata.bind;

import java.util.Map;

import org.xml.sax.SAXException;

import com.tibco.cep.mapper.xml.xdata.xpath.ErrorMessageList;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.func.TibExtFunctions;
import com.tibco.xml.channel.infoset.XmlContentHandler;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.PrefixToNamespaceResolver;
import com.tibco.xml.data.primitive.QName;
import com.tibco.xml.data.primitive.XmlNodeKind;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.SmSequenceTypeRemainder;
import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.schema.xtype.SMDT;
import com.tibco.xml.schema.xtype.SmSequenceTypeFactory;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

/**
 * Represents XML attributes.
 */
public class AttributeBinding extends DataComponentBinding
{
    /**
     * Constructor.
     */
    public AttributeBinding(BindingElementInfo info, ExpandedName name) {
        super(info,name);
    }

    public AttributeBinding(BindingElementInfo info, String optionalNsAVT, String nameAVT) {
        super(info,optionalNsAVT,nameAVT);
    }

    public TemplateReport createTemplateReport(TemplateReport parent, ExprContext context, SmSequenceType expectedOutput, SmSequenceType outputContext, int outputValidationLevel, TemplateReportFormulaCache templateReportFormulaCache, TemplateReportArguments templateReportArguments) {
        TemplateReport ret = new TemplateReport(this,parent);
        ExprContext updatedContext = TemplateReportSupport.createContextForNamespaceDecls(this,context);
        ret.setContext(updatedContext);
        ret.setChildContext(updatedContext);
        ret.setFollowingContext(context);
        ret.setOutputContext(outputContext);
        ret.setChildOutputContext(outputContext); // same as here.
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
        SmSequenceType magicType = getMagicAttribute(ename);
        SmSequenceType outputAttributeType;
        if (magicType==null)
        {
            outputAttributeType = SmSequenceTypeSupport.getAttributeInContext(ename,outputContext,context.getOutputSmComponentProvider());
        }
        else
        {
            outputAttributeType = magicType;
        }
        TemplateReportSupport.checkSchemaComponentErrors(ret,outputAttributeType);

        SmSequenceType content = outputAttributeType.typedValue(true); // true-> because we want typed nodes values here, not just strings.
        ret.setComputedType(outputAttributeType);

        if (magicType==null)
        {
            TemplateReportSupport.initForOutputMatch(ret, outputAttributeType, false, templateReportArguments);
        }
        else
        {
            // Magic attributes don't contribute anything.
            ret.setRemainingOutputType(ret.getInitialOutputType());
            ret.setOutputContextError(null);
        }

        if (ret.getOutputContextError()!=null)
        {
            // If we didn't compute the expected type, this is an output context error:
            // go to previous missed items & see if it fits there....
            doExtendedMatch(parent,ret,outputAttributeType,templateReportArguments);
        }

        SmSequenceType leftoverType;
        if (!isExplicitXslRepresentation())
        {
            leftoverType = analyzeAVT(ret,templateReportArguments);
            TemplateReportSupport.traverseNoChildren(ret,false,templateReportFormulaCache,templateReportArguments);
        }
        else
        {
            SmSequenceType remainingContentType = analyzeVirtual(ret,templateReportFormulaCache,content, templateReportArguments);
            if (remainingContentType==null)
            {
                // This is bad, analyzeVirtual probably should return previous_error directly, not null.
                // Fixes 1-1P55Z5.
                remainingContentType = SMDT.PREVIOUS_ERROR;
            }
            leftoverType = TemplateReportSupport.traverseChildren(ret,remainingContentType,outputValidationLevel,templateReportFormulaCache,templateReportArguments);
        }
        if (leftoverType!=null)
        {
            TemplateReportSupport.addMissingEndingTerms(ret,leftoverType,templateReportArguments);
        }

        TemplateReportSupport.initIsRecursivelyErrorFree(ret);

        return ret;
    }

    public boolean renamePrefixUsages(Map oldToNewPrefixMap)
    {
        if (!isExplicitXslRepresentation() && getFormula()!=null)
        {
            if ("type".equals(getName().getLocalName()) && XSDL.INSTANCE_NAMESPACE.equals(getName().getNamespaceURI()))
            {
                // xsi:type.
                QName qn = new QName(getFormula());
                if (qn.getPrefix()!=null)
                {
                    String np = (String) oldToNewPrefixMap.get(qn.getPrefix());
                    if (np!=null)
                    {
                        QName f = new QName(np,qn.getLocalName());
                        setFormula(f.toString());
                        return true;
                    }
                }
            }
        }
        if (isExplicitXslRepresentation())
        {
            String nm = getExplicitNameAVT();
            if (AVTUtilities.isAVTString(nm)) //WCETODO should handle these.
            {
                return false; //
            }
            QName qn = new QName(nm);
            String pfx = qn.getPrefix();
            String npfx = (String) oldToNewPrefixMap.get(pfx);
            boolean anyChange = false;
            if (npfx!=null)
            {
                setExplicitNameAVT(new QName(npfx,qn.getLocalName()).toString());
                anyChange = true;
            }
            // check for xsi:type:
            if (qn.getLocalName().equals("type"))
            {
                String ns;
                try
                {
                    ns = asXiNode().getNamespaceURIForPrefix(qn.getPrefix());
                }
                catch (PrefixToNamespaceResolver.PrefixNotFoundException p)
                {
                    ns = null;
                }
                if (XSDL.INSTANCE_NAMESPACE.equals(ns))
                {
                    // It is xsi:type:
                    if (getChildCount()==1 && getChild(0) instanceof TextBinding)
                    {
                        TextBinding tb = (TextBinding) getChild(0);
                        if (tb.getFormula()!=null)
                        {
                            QName qnf = new QName(tb.getFormula());
                            if (qnf.getPrefix()!=null && oldToNewPrefixMap.get(qnf.getPrefix())!=null)
                            {
                                String np = (String) oldToNewPrefixMap.get(qnf.getPrefix());
                                tb.setFormula(new QName(np,qnf.getLocalName()).toString());
                                anyChange = true;
                            }
                        }
                    }
                }
            }
            return anyChange;
        }
        return false;
    }

    /**
     * Hook for derived 'virtual' xslt elements to plug-in to.
     * @return The remaining type, or null for error (this is bad, probably should have returned previous error)
     */
    protected SmSequenceType analyzeVirtual(TemplateReport report, TemplateReportFormulaCache formulaCache, SmSequenceType contentType, TemplateReportArguments arguments)
    {
        // default does nothing.
        return contentType;
    }

    /**
     * Tests for xsi:type, xsi:schemaLocation, and noNamespaceSchemaLocation, but <b>not</b> xsi:nil since it is declared.
     * @param name The name
     * @return The type if it is a magic attribute, null otherwise.
     */
    private static SmSequenceType getMagicAttribute(ExpandedName name)
    {
        if (XSDL.INSTANCE_NAMESPACE.equals(name.getNamespaceURI()))
        {
            String ln = name.getLocalName();
            if (XSDL.ATTR_TYPE.getName().equals(ln))
            {
                return SMDT.XSI_TYPE_ATTRIBUTE;
            }
            if (XSDL.ATTR_SCHEMA_LOCATION.getName().equals(ln))
            {
                return SMDT.XSI_SCHEMA_LOCATION;
            }
            if (XSDL.ATTR_NO_NAMESPACE_SCHEMA_LOCATION.getName().equals(ln))
            {
                return SMDT.XSI_NO_NAMESPACE_SCHEMA_LOCATION;
            }
            if (XSDL.ATTR_NIL.getName().equals(ln))
            {
                return SMDT.XSI_NIL_ATTRIBUTE;
            }
        }
        return null;
    }

    private SmSequenceType analyzeAVT(TemplateReport report, TemplateReportArguments args)
    {
        TemplateReportSupport.initParsedAVTFormula(report,false);
        if (report.getExpectedType()!=null && report.getFormulaType()!=null) {
            ErrorMessageList eml = BindingTypeCheckUtilities.typeCheck(
                    BindingTypeCheckUtilities.TYPE_CHECK_VALUE, // do a value type-check.
                    report.getExpectedType().typedValue(true), // true-> want fully typed nodes, not just strings.
                    report.getFormulaType(),
                    report.getXPathExpression().getTextRange(),
                    args.getXPathCheckArguments());
            report.addFormulaErrors(eml);
        }
        if (report.getExpectedType()==null)
        {
            return SMDT.PREVIOUS_ERROR;
        }
        SmSequenceTypeRemainder r = report.getExpectedType().getRemainingAfterType(SMDT.STRING,true);
        if (r==null)
        {
            //WCETODO can this happen? error?
            return SMDT.PREVIOUS_ERROR;
        }
        return r.getRemainder();
    }

    private void doExtendedMatch(TemplateReport parent, TemplateReport report, SmSequenceType attrType, TemplateReportArguments args) {
        // gets previous missed terms (i.e. out of order):
        if (args.getCheckForMove())
        {
            if (TemplateReportSupport.extendedMatchForReorder(parent,report,attrType,false))
            {
                return;
            }
        }
        if (args.getCheckForRenameNamespace())
        {
            // Next, check for a simple namespace change (i.e. renamed externally)
            SmSequenceType nnat = SmSequenceTypeFactory.createAttribute(ExpandedName.makeName("*",attrType.getName().getLocalName()),null);
            if (TemplateReportSupport.extendedMatchForNamespaceChange(report, nnat, false))
            {
                return;
            }
        }
    }

    /**
     * Formats as a regular xml attribute, called by {@link ElementBinding} when appropriate.<br>
     * Results are undefined for when this is a {@link #isExplicitXslRepresentation}.
     */
    public void formatLiteralFragment(XmlContentHandler handler) throws SAXException
    {
        handler.attribute(getName(),getFormula()==null ? "" : getFormula(),null);
    }

    public void formatFragment(XmlContentHandler handler) throws SAXException {
        if (!isExplicitXslRepresentation())
        {
            // (If this was in a legal context --- i.e. under an element, it would have been written with formatLiteralFragment.

            // This isn't legal XML..., write it out in a custom namespace so we can read it back in:
            if (isNameMalformed())
            {
                handler.startElement(NAME,null,null);
                String ns = m_name.getNamespaceURI();
                if (ns==null)
                {
                    ns = "";
                }
                handler.attribute(NAMESPACE_ATTR,ns,null);
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
                handler.startElement(ILLEGAL_NAME,null,null);
                QName value = getName().getQName(BindingNamespaceManipulationUtils.createNamespaceImporter(this),XmlNodeKind.ELEMENT);
                handler.attribute(NAME_ATTR,value.toString(),null);
                String f = getFormula();
                if (f==null)
                {
                    f = "";
                }
                handler.attribute(ILLEGAL_VALUE_ATTR,f,null);
                for (int i=0;i<getChildCount();i++)
                {
                    getChild(i).formatFragment(handler);
                }

                handler.endElement(ILLEGAL_NAME,null,null);
            }
        }
        else
        {
            super.issueNamespaceDeclarations(handler);
            handler.startElement(NAME,null,null);
            if (m_explicitNamespaceAVT!=null)
            {
                handler.attribute(NAMESPACE_ATTR,m_explicitNamespaceAVT,null);
            };
            handler.attribute(NAME_ATTR,m_explicitNameAVT,null);
            super.issueAdditionalAttributes(handler);
            for (int i=0;i<getChildCount();i++) {
                getChild(i).formatFragment(handler);
            }

            handler.endElement(NAME,null,null);
        }
    }

    /**
     * Clones this binding node.
     * @return Binding A binding node of this type.
     */
    public Binding cloneShallow() {
        AttributeBinding ab = new AttributeBinding(getElementInfo(),getName());
        ab.setExplicitXslRepresentation(isExplicitXslRepresentation());
        ab.setFormula(getFormula());
        ab.setExplicitNamespaceAVT(getExplicitNamespaceAVT());
        ab.setExplicitNameAVT(getExplicitNameAVT());
        return ab;
    }

    public ExpandedName getName() {
        return m_isExplicitXsd ? NAME : m_name;
    }

    private static final ExpandedName ILLEGAL_NAME = TibExtFunctions.makeName("illegal-literal-attribute");
    public static final ExpandedName NAME = ReadFromXSLT.makeName("attribute");
    protected static final ExpandedName NAME_ATTR = ExpandedName.makeName("name");
    protected static final ExpandedName ILLEGAL_VALUE_ATTR = ExpandedName.makeName("value");
    protected static final ExpandedName NAMESPACE_ATTR = ExpandedName.makeName("namespace");
}
