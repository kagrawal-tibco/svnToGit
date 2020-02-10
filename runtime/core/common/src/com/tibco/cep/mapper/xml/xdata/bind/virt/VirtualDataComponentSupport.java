package com.tibco.cep.mapper.xml.xdata.bind.virt;

import org.xml.sax.SAXException;

import com.tibco.cep.mapper.util.ResourceBundleManager;
import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReportArguments;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReportExtendedError;
import com.tibco.cep.mapper.xml.xdata.bind.fix.BindingFixerChange;
import com.tibco.cep.mapper.xml.xdata.xpath.MessageCode;
import com.tibco.cep.mapper.xml.xdata.xpath.func.TibExtFunctions;
import com.tibco.xml.channel.infoset.XmlContentHandler;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.data.primitive.NamespaceResolver;
import com.tibco.xml.data.primitive.NamespaceToPrefixResolver;
import com.tibco.xml.data.primitive.PrefixToNamespaceResolver;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

/**
 * A set of common methods & error fixers for {@link VirtualDataComponentBinding} subclasses {@link VirtualElementBinding}
 * and {@link VirtualAttributeBinding}
 */
public class VirtualDataComponentSupport
{

    public static abstract class VDCExtendedError implements TemplateReportExtendedError
    {
        private final ExpandedName m_name;

        public VDCExtendedError(ExpandedName name)
        {
            m_name = name;
        }

        public final boolean canFix(TemplateReport onReport)
        {
            return true;
        }

        public final void formatFragment(XmlContentHandler handler, NamespaceToPrefixResolver resolver) throws SAXException
        {
            handler.startElement(m_name,null,null);
            handler.endElement(m_name,null,null);
        }

        public final void registerNamespaces(NamespaceContextRegistry nsContextRegistry)
        {
            nsContextRegistry.getOrAddPrefixForNamespaceURI(m_name.getNamespaceURI());
        }
    }
    /**
     * An extended error that will turn off implicit if.
     */
    public static class TurnOffImplicitIf extends VDCExtendedError
    {
        private static final ExpandedName NAME = TibExtFunctions.makeName("turn-off-implicit-if");

        public static final TurnOffImplicitIf INSTANCE = new TurnOffImplicitIf();

        private TurnOffImplicitIf()
        {
            super(NAME);
        }

        public int getCategory()
        {
            return BindingFixerChange.CATEGORY_WARNING;
        }

        public String formatMessage(TemplateReport report)
        {
            return ResourceBundleManager.getMessage(MessageCode.OUTPUT_REQUIRED_SURROUNDING_IF_NOT_NEEDED);
        }

        public void fix(TemplateReport report)
        {
            VirtualDataComponentBinding veb = (VirtualDataComponentBinding) report.getBinding();
            veb.setCopyMode(veb.getCopyMode().getWithNoIf());
        }
    }

    /**
     * An extended error that will turn off implicit if.
     */
    public static class TurnOffImplicitIfForFormula extends VDCExtendedError
    {
        private static final ExpandedName NAME = TibExtFunctions.makeName("turn-off-implicit-if-formula");

        public static final TurnOffImplicitIfForFormula INSTANCE = new TurnOffImplicitIfForFormula();

        private TurnOffImplicitIfForFormula()
        {
            super(NAME);
        }

        public int getCategory()
        {
            return BindingFixerChange.CATEGORY_WARNING;
        }

        public String formatMessage(TemplateReport report)
        {
            return ResourceBundleManager.getMessage(MessageCode.INPUT_NOT_OPTIONAL_SURROUNDING_IF_NOT_NEEDED);
        }

        public void fix(TemplateReport report)
        {
            VirtualDataComponentBinding veb = (VirtualDataComponentBinding) report.getBinding();
            veb.setCopyMode(veb.getCopyMode().getWithNoIf());
        }
    }

    /**
     * An extended error that will turn off implicit if.
     */
    public static class TurnOffImplicitIfForNonNode extends VDCExtendedError
    {
        private static final ExpandedName NAME = TibExtFunctions.makeName("turn-off-implicit-if-non-node");

        public static final TurnOffImplicitIfForNonNode INSTANCE = new TurnOffImplicitIfForNonNode();

        private TurnOffImplicitIfForNonNode()
        {
            super(NAME);
        }

        public int getCategory()
        {
            return BindingFixerChange.CATEGORY_WARNING;
        }

        public String formatMessage(TemplateReport report)
        {
            return ResourceBundleManager.getMessage(MessageCode.INPUT_NOT_NODE_SET_SURROUNDING_IF_NOT_NEEDED);
        }

        public void fix(TemplateReport report)
        {
            VirtualDataComponentBinding veb = (VirtualDataComponentBinding) report.getBinding();
            veb.setCopyMode(veb.getCopyMode().getWithNoIf());
        }
    }

    /**
     * An extended error that will turn off nillable.
     */
    public static class TurnOffNillable extends VDCExtendedError
    {
        private static final ExpandedName NAME = TibExtFunctions.makeName("turn-off-nillable");

        public static final TurnOffNillable INSTANCE = new TurnOffNillable();

        private TurnOffNillable()
        {
            super(NAME);
        }

        public void fix(TemplateReport report)
        {
            VirtualElementBinding veb = (VirtualElementBinding) report.getBinding();
            veb.setExplicitNil(false);
        }

        public int getCategory()
        {
            return BindingFixerChange.CATEGORY_ERROR;
        }

        public String formatMessage(TemplateReport report)
        {
            return ResourceBundleManager.getMessage(MessageCode.ELEMENT_NOT_NILLABLE);
        }
    }

    /**
     * An extended error that will update an xsl:attribute name="xsi:nil",value-of select="true()"... into a simple attribute.
     */
    public static class UpdateNillable extends VDCExtendedError
    {
        private static final ExpandedName NAME = TibExtFunctions.makeName("update-nillable");

        public static final UpdateNillable INSTANCE = new UpdateNillable();

        private UpdateNillable()
        {
            super(NAME);
        }

        public void fix(TemplateReport report)
        {
            VirtualElementBinding veb = (VirtualElementBinding) report.getBinding();
            veb.setExplicitNil(true);
            int iob = findIndexOfNonstandardXsiNil(report);
            if (iob>=0)
            {
                veb.removeChild(iob);
            }
        }

        public int getCategory()
        {
            return BindingFixerChange.CATEGORY_WARNING;
        }

        public String formatMessage(TemplateReport report)
        {
            return ResourceBundleManager.getMessage(MessageCode.SIMPLIFY_TO_NIL_CONSTANT);
        }
    }

    /**
     * An extended error that will set copy-nil for when both input & output are nillable but copy-nil isn't set.
     */
    public static class SetCopyNil extends VDCExtendedError
    {
        private static final ExpandedName NAME = TibExtFunctions.makeName("set-copy-nil");

        public static final SetCopyNil INSTANCE = new SetCopyNil();

        private SetCopyNil()
        {
            super(NAME);
        }

        public void fix(TemplateReport report)
        {
            VirtualDataComponentBinding veb = (VirtualDataComponentBinding) report.getBinding();
            veb.setCopyMode(veb.getCopyMode().getWithCopyNil());
        }

        public int getCategory()
        {
            return BindingFixerChange.CATEGORY_WARNING;
        }

        public String formatMessage(TemplateReport report)
        {
            return ResourceBundleManager.getMessage(MessageCode.SET_COPY_NIL);
        }
    }

    /**
     * An extended error that will set copy-nil for when both input & output are nillable but copy-nil isn't set.
     */
    public static class ClearCopyNilOutput extends VDCExtendedError
    {
        private static final ExpandedName NAME = TibExtFunctions.makeName("clear-copy-nil-output");

        public static final ClearCopyNilOutput INSTANCE = new ClearCopyNilOutput();

        private ClearCopyNilOutput()
        {
            super(NAME);
        }

        public void fix(TemplateReport report)
        {
            VirtualDataComponentBinding veb = (VirtualDataComponentBinding) report.getBinding();
            veb.setCopyMode(veb.getCopyMode().getWithNonNilOutput());
        }

        public int getCategory()
        {
            return BindingFixerChange.CATEGORY_ERROR;
        }

        public String formatMessage(TemplateReport report)
        {
            return ResourceBundleManager.getMessage(MessageCode.CLEAR_COPY_NIL_OUTPUT_NOT_NILLABLE);
        }
    }

    /**
     * An extended error that will set copy-nil for when both input & output are nillable but copy-nil isn't set.
     */
    public static class ClearCopyNilInput extends VDCExtendedError
    {
        private static final ExpandedName NAME = TibExtFunctions.makeName("clear-copy-nil-input");

        public static final ClearCopyNilInput INSTANCE = new ClearCopyNilInput();

        private ClearCopyNilInput()
        {
            super(NAME);
        }

        public void fix(TemplateReport report)
        {
            VirtualDataComponentBinding veb = (VirtualDataComponentBinding) report.getBinding();
            veb.setCopyMode(veb.getCopyMode().getWithNonNilInput());
        }

        public int getCategory()
        {
            return BindingFixerChange.CATEGORY_WARNING;
        }

        public String formatMessage(TemplateReport report)
        {
            return ResourceBundleManager.getMessage(MessageCode.CLEAR_COPY_NIL_INPUT_NOT_NILLABLE);
        }
    }

    /**
     * An extended error that will remove a formula for structure nodes that shouldn't have one.
     */
    public static class RemoveMisplacedFormula extends VDCExtendedError
    {
        private static final ExpandedName NAME = TibExtFunctions.makeName("remove-misplaced-formula");

        public static final RemoveMisplacedFormula INSTANCE = new RemoveMisplacedFormula();

        private RemoveMisplacedFormula()
        {
            super(NAME);
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
    }

    public static void checkSuperfluousNil(TemplateReport report, VirtualDataComponentCopyMode copyMode)
    {
        SmSequenceType ft = report.getFormulaType();
        boolean inputIsNonNode = !SmSequenceTypeSupport.isNodeSet(ft) && !SmSequenceTypeSupport.isPreviousError(ft);
        if (inputIsNonNode && copyMode.isInputNil())
        {
            report.addExtendedError(ClearCopyNilInput.INSTANCE); // close enough.
            return; // no further errors.
        }
        SmSequenceType ct = report.getComputedType();
        if (ct==null)
        {
            return;
        }
        boolean inputNil = ft.isNillable();
        boolean inputNilKnown = ft.isNillabilityKnown();
        boolean outputNil = ct.isNillable();
        boolean outputNilKnown = ct.isNillabilityKnown();

        if (copyMode.isOutputNil())
        {
            if (!outputNil && outputNilKnown)
            {
                report.addExtendedError(ClearCopyNilOutput.INSTANCE);
                return;
            }
        }
        if (copyMode.isInputNil())
        {
            if (!inputNil && inputNilKnown)
            {
                report.addExtendedError(ClearCopyNilInput.INSTANCE);
            }
        }
        if (!copyMode.isInputNil() && !copyMode.isOutputNil())
        {
            if (inputNil && outputNil && inputNilKnown && outputNilKnown)
            {
                // Suggest setting 'nil'.
                report.addExtendedError(SetCopyNil.INSTANCE);
            }
        }
    }

    public static void checkSuperfluousImplicitIf(TemplateReport report, VirtualDataComponentCopyMode copyMode, TemplateReportArguments arguments)
    {
        if (!copyMode.isInputAndOutputOptional())
        {
            // not relevant.
            return;
        }

        // Check that it's not superfluous.
        SmSequenceType expectedType = report.getExpectedType();
        if (expectedType!=null)
        {
            if (expectedType.quantifier().getMinOccurs()>0)
            {
                // not required:
                report.addExtendedError(TurnOffImplicitIf.INSTANCE);
                return;
            }
        }
        SmSequenceType formulaType = report.getFormulaType();
        if (formulaType!=null)
        {
            if (!SmSequenceTypeSupport.isNodeSet(formulaType) && !SmSequenceTypeSupport.isPreviousError(formulaType))
            {
                // not recommended:
                report.addExtendedError(TurnOffImplicitIfForNonNode.INSTANCE);
                return;
            }
            if (arguments.getCheckExtendedWarnings())
            {
                if (formulaType.quantifier().getMinOccurs()>0)
                {
                    // not required:
                    report.addExtendedError(TurnOffImplicitIfForFormula.INSTANCE);
                    return;
                }
            }
        }
    }

    /**
     * Finds the location, of any non-normalized xsi:nil attribute.
     * @param report
     * @return The index, or -1 for none.
     */
    public static int findIndexOfNonstandardXsiNil(TemplateReport report)
    {
        VirtualElementBinding veb = (VirtualElementBinding) report.getBinding();
        if (!veb.isExplicitXslRepresentation())
        {
            NamespaceResolver ni = report.getChildContext().getNamespaceMapper();
            for (int i=0;i<veb.getChildCount();i++)
            {
                Binding c = veb.getChild(i);
                if (c instanceof VirtualAttributeBinding)
                {
                    VirtualAttributeBinding ab = (VirtualAttributeBinding) c;
                    if (ab.isExplicitXslRepresentation())
                    {
                        try
                        {
                            ExpandedName aname = ab.computeComponentExpandedName(ni);
                            if (aname.getLocalName().equals("nil") && aname.equals(XSDL.ATTR_NIL.getExpandedName()))
                            {
                                if (ab.getHasInlineFormula() && ab.getCopyMode()==VirtualDataComponentCopyMode.REQUIRED_TO_REQUIRED)
                                {
                                    String f = ab.getFormula();
                                    if (f==null)
                                    {
                                        f = "";
                                    }
                                    f = f.trim();
                                    if (ab.getInlineIsText())
                                    {
                                        if (f.equals("true"))
                                        {
                                            return i;
                                        }
                                    }
                                    else
                                    {
                                        if (f.equals("true()") || f.equals("'true'") || f.equals("\"true\""))
                                        {
                                            return i;
                                        }
                                    }
                                }
                            }
                        }
                        catch (PrefixToNamespaceResolver.PrefixNotFoundException e)
                        {
                            // eat it, let it slip through.
                        }
                    }
                }
            }
        }
        return -1;
    }
}

