package com.tibco.cep.mapper.xml.xdata.bind;

import com.tibco.cep.mapper.util.ResourceBundleManager;
import com.tibco.cep.mapper.xml.xdata.xpath.MessageCode;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;


/**
 * A class which formats errors from TemplateReport errors.
 */
public class TemplateReportErrorFormatter {
    /**
     * Formats an error, if any, for the given template report.
     * @param report The report from which to base the error.
     * @return The error string for this line, or null if none.
     */
    public static String formatError(TemplateReport report, String displayName) {
        if (report.isRecursivelyErrorFree()) {
            return null;
        }
        if (report.getFormulaErrors().getErrorAndWarningMessages().length>0)
        {
            return ResourceBundleManager.getMessage(MessageCode.FORMULA_HAS_ERRORS);
        }
        if (report.getFormulaErrors().getMarkerMessages().length>0)
        {
            return ResourceBundleManager.getMessage(MessageCode.FORMULA_HAS_MARKERS);
        }
        return formatErrorIgnoreFormula(report,displayName);
    }

    public static String formatErrorIgnoreFormula(TemplateReport report, String displayName)
    {
        if (report.getOutputContextError()!=null)
        {
            SmSequenceType ct = report.getComputedType();
            if (ct==null)
            {
                ct = report.getFormulaType(); // in the case of copy-of, this is the situation; the type will be there.
            }
            if (report.getOutputContextError()==TemplateOutputContextError.UNEXPECTED_TEXT)
            {
                // Just out of place:
                return ResourceBundleManager.getMessage(MessageCode.UNEXPECTED_OUTPUT_TEXT,SmSequenceTypeSupport.getDisplayName(ct));
            }
            if (report.getOutputContextError()==TemplateOutputContextError.CARDINALITY)
            {
                // Cardinality exceeded:
                return ResourceBundleManager.getMessage(MessageCode.UNEXPECTED_OUTPUT_EXCEEDS_MAXIMUM,SmSequenceTypeSupport.getDisplayName(ct));
            }
            if (report.getOutputContextError()==TemplateOutputContextError.NO_SCHEMA)
            {
                // Cardinality exceeded:
                return ResourceBundleManager.getMessage(MessageCode.CANNOT_LOAD_SCHEMA_FOR_ELEMENT,report.getBinding().getName().getNamespaceURI());
            }
            if (report.getOutputContextError()==TemplateOutputContextError.NO_COMPONENT_IN_SCHEMA)
            {
                // Cardinality exceeded:
                return ResourceBundleManager.getMessage(MessageCode.NO_SUCH_ELEMENT_IN_SCHEMA,report.getBinding().getName().getLocalName());
            }
            if (report.getOutputContextError()==TemplateOutputContextError.NO_COMPONENT_IN_CONTEXT)
            {
                // Cardinality exceeded:
                return ResourceBundleManager.getMessage(MessageCode.NO_SUCH_ELEMENT_IN_CONTEXT,report.getBinding().getName().getLocalName());
            }
            if (report.getOutputContextError()==TemplateOutputContextError.AMBIGUOUS_COMPONENT_IN_PROJECT)
            {
                // Cardinality exceeded:
                return ResourceBundleManager.getMessage(MessageCode.AMBIGUOUS_ELEMENT_IN_PROJECT,report.getBinding().getName().getNamespaceURI());
            }
            if (report.getOutputContextError()==TemplateOutputContextError.UNEXPECTED_NS_MISMATCH)
            {
                // Just out of place, but only by namespace mismatch. (Not for element/attribute, though, those are
                // fixable).
                String ns1 = "";
                String ns2 = "";
                if (ct!=null) // just in case, check for nulls.
                {
                    SmSequenceType ctp = ct.prime();
                    if (ctp.getName()!=null)
                    {
                        ns1 = ctp.getName().getNamespaceURI();
                        ExpandedName ename = ExpandedName.makeName("*",ctp.getName().getLocalName());
                        SmSequenceType nr2 = report.getInitialOutputType().nameTest(ename).prime();
                        if (nr2.getName()!=null)
                        {
                            ns2 = nr2.getName().getNamespaceURI();
                        }
                    }
                }
                return ResourceBundleManager.getMessage(MessageCode.UNEXPECTED_OUTPUT_NS_MISMATCH,SmSequenceTypeSupport.getDisplayName(ct),ns1,ns2);
            }
            // Just out of place:
            return ResourceBundleManager.getMessage(MessageCode.UNEXPECTED_OUTPUT,SmSequenceTypeSupport.getDisplayName(ct));
        }
        if (report.getReferencedSchemaError()!=null)
        {
            // The referenced schema element, etc., is bad somehow.
            return report.getReferencedSchemaError();
        }
        if (report.getIsMissing()) {
            if (SmSequenceTypeSupport.hasTypedValue(report.getComputedType(),true))
            {
                return ResourceBundleManager.getMessage(MessageCode.VALUE_REQUIRED_FOR,SmSequenceTypeSupport.getDisplayName(report.getComputedType()));
            }
            else
            {
                return ResourceBundleManager.getMessage(MessageCode.ELEMENT_REQUIRED,SmSequenceTypeSupport.getDisplayName(report.getComputedType()));
            }
        }
        // If it has missing children OR if it has no children & has missing output --- (i.e. a comment or element not yet expanded)
        if (report.hasMissingChildren() || (report.getChildCount()==0 && report.hasMissingOutputErrors()))
        {
            return ResourceBundleManager.getMessage(MessageCode.COMPONENTS_INCOMPLETE,displayName);
        }
        if (report.hasMissingOutputErrors())
        {
            return ResourceBundleManager.getMessage(MessageCode.OUTPUT_INCOMPLETE,displayName);
        }
        if (report.containsOutputContextError())
        {
            return ResourceBundleManager.getMessage(MessageCode.OUTPUT_INVALID,displayName);
        }
        if (report.getHasMissingPrecedingTerms())
        {
            return ResourceBundleManager.getMessage(MessageCode.MISSING_PRECEDING_TERMS,displayName);
        }
        TemplateReportExtendedError[] ee = report.getExtendedErrors();
        if (ee!=null && ee.length>0)
        {
            return ee[0].formatMessage(report);
        }
        return ResourceBundleManager.getMessage(MessageCode.COMPONENTS_CONTAIN_ERRORS,displayName);

    }
}
