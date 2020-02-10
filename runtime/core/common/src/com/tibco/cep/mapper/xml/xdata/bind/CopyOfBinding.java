/*******************************************************************************
 * Copyright 2001 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.xml.xdata.bind;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.xml.sax.SAXException;

import com.tibco.cep.mapper.util.ResourceBundleManager;
import com.tibco.cep.mapper.xml.xdata.bind.fix.BindingFixerChange;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprUtilities;
import com.tibco.cep.mapper.xml.xdata.xpath.MessageCode;
import com.tibco.cep.mapper.xml.xdata.xpath.Parser;
import com.tibco.cep.mapper.xml.xdata.xpath.func.TibExtFunctions;
import com.tibco.cep.mapper.xml.xdata.xpath.type.XTypeComparator;
import com.tibco.xml.channel.infoset.XmlContentHandler;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.data.primitive.NamespaceToPrefixResolver;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SmSequenceTypeFactory;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

/**
 * Represents an XSLT copy-of operation.
 */
public class CopyOfBinding extends ControlBinding
{
    // disabled.public String mGeneratesOutput;
    private Boolean m_copyNamespaces; // may be null.

    public CopyOfBinding(BindingElementInfo info, String formula)
    {
        super(info, formula);
    }

   public TemplateReport createTemplateReport(TemplateReport parent,
                                              ExprContext context,
                                              SmSequenceType expectedOutput,
                                              SmSequenceType outputContext,
                                              int outputValidationLevel,
                                              TemplateReportFormulaCache templateReportFormulaCache,
                                              TemplateReportArguments templateReportArguments) {
      return createTemplateReport(parent, context, expectedOutput,
                                  outputContext, outputValidationLevel,
                                  templateReportFormulaCache,
                                  templateReportArguments, true);
   }

   public TemplateReport createTemplateReport(TemplateReport parent,
                                              ExprContext context,
                                              SmSequenceType expectedOutput,
                                              SmSequenceType outputContext,
                                              int outputValidationLevel,
                                              TemplateReportFormulaCache templateReportFormulaCache,
                                              TemplateReportArguments templateReportArguments,
                                              boolean addToParent) {
      TemplateReport retVal = null;
      ArrayList failedMatches = null;
      while(expectedOutput != null && retVal == null) {
         retVal = new TemplateReport(this,parent,false);
         retVal.setContext(context);
         retVal.setOutputContext(outputContext);
         retVal.setChildContext(context); // n/a
         retVal.setChildOutputContext(outputContext); // n/a
         retVal.setFollowingContext(context); // no change.
         retVal.setInitialOutputType(expectedOutput);

         TemplateReportSupport.initParsedFormula(retVal,templateReportFormulaCache,templateReportArguments);
         // In certain case, the LHS often has a badly placed wildcard (one that doesn't exclude the proper namespaces),
         // in that case, the formulaType would contain something like:
         // MatchedElement | MatchedElement --- where one of the MatchedElements corresponds to the possibility that the
         // wildcard matches and the other corresponds to an exact element.
         // Because this happens quite a bit apparently (DBS and Barnes and Noble), the solution is
         // to explicitly account for that type of situation (below) and remove the wildcard matched types in the case
         // where there is both an actual and a wildcard match.
         // Without this fix, the output type of 'MatchedElement | MatchedElement' wouldn't properly validate in context.
         SmSequenceType fixedFormulaType = SmSequenceTypeSupport.stripDuplicateWildcardMatchedTypes(retVal.getFormulaType());
         retVal.setFormulaType(fixedFormulaType);
         TemplateReportSupport.traverseNoChildren(retVal,true,templateReportFormulaCache,templateReportArguments);

         TemplateReportSupport.initForOutputMatch(retVal, fixedFormulaType, false, templateReportArguments);

         if (retVal.getOutputContextError()!=null)
         {
             // go to previous missed items & see if it fits there....
             doExtendedMatch(parent,retVal,fixedFormulaType,templateReportArguments);
         }
         else
         {
             if (retVal.getExpectedType()!=null && fixedFormulaType!=null)
             {
                 // Do a further check that the types are actually equivalent (they could, if fact, be same-name-but-different):
                 SmSequenceType formulaTypePrime = fixedFormulaType.prime();
                 SmSequenceType expectedTypePrime = retVal.getExpectedType().prime();
                 XTypeComparator.Properties props = new XTypeComparator.Properties();
                 props.setLenientCardinality(true);
                 props.setLenientPrimitives(true); // be lenient on these for now; ideally would be settable somewhere...
                 XTypeComparator.Result tt = XTypeComparator.compareEquivalenceResult(formulaTypePrime,expectedTypePrime,props);
                 if (tt.getEquality()!=XTypeComparator.EQUALS && tt.getEquality()!=XTypeComparator.LEFT_ASSIGNABLE_TO_RIGHT)
                 {
                     // failed.
                     retVal.addExtendedError(new BadCopyOf(tt.getReasonString()));
                 }
             }
         }
         if(retVal.getExtendedErrors().length > 0) {
            if(failedMatches == null) {
               failedMatches = new ArrayList();
            }
            failedMatches.add(retVal);
            retVal = null;
            expectedOutput = expectedOutput.getSecondChildComponent();
         }
      }
      if(failedMatches != null) {
         if(retVal == null) {
            // An error condition.  Use the first failed match & it will
            // display as an error; then, the user can repair it.
            retVal = (TemplateReport)(failedMatches.get(0));
         }
         else {
            for(Iterator it = failedMatches.iterator(); it.hasNext();) {
               // To our new TemplateReport for the CopyOfBinding, add each
               // failed match as a missing preceeding term.
               TemplateReport tr = (TemplateReport)(it.next());
               retVal.addMissingPrecedingTerm(tr.getExpectedType());
            }
         }
      }
      if(addToParent) {
         parent.addChild(retVal);
      }

        TemplateReportSupport.initIsRecursivelyErrorFree(retVal);
        return retVal;
    }

    public static class BadCopyOf implements TemplateReportExtendedError
    {
        private static final ExpandedName NAME = TibExtFunctions.makeName("bad-copy-of");
        private String m_optionalReason; // If not null, the reason for the copy-of being mismatch.

        public static final TemplateReportExtendedError INSTANCE = new BadCopyOf(null);

        public BadCopyOf(String optionalReason)
        {
            m_optionalReason = optionalReason;
        }

        public boolean canFix(TemplateReport onReport)
        {
            return false;
        }

        public void fix(TemplateReport report)
        {
        }

        public String formatMessage(TemplateReport report)
        {
            String msg = ResourceBundleManager.getMessage(MessageCode.BAD_COPY_OF);
            if (m_optionalReason!=null && m_optionalReason.length()>0)
            {
                msg = msg + " : " + m_optionalReason;
            }
            return msg;
        }

        public int getCategory()
        {
            return BindingFixerChange.CATEGORY_ERROR;
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

        /**
         * For diagnostic/debugging purposes only.
         */
        public String toString()
        {
            return "Bad Copy of, reason: " + m_optionalReason;
        }
    }

    public boolean renamePrefixUsages(Map oldToNewPrefixMap)
    {
        String formula = getFormula();
        if (formula==null)
        {
            return false;
        }
        Expr parseFormula = Parser.parse(formula);
        Expr newFormula = ExprUtilities.renamePrefixes(parseFormula,oldToNewPrefixMap);
        String newStr = newFormula.toExactString();
        if (newStr.equals(formula))
        {
            return false;
        }
        setFormula(newStr);
        return true;
    }

    private void doExtendedMatch(TemplateReport parent, TemplateReport report,
                                 SmSequenceType formulaType,
                                 TemplateReportArguments arguments)
    {
        // gets previous missed terms (i.e. out of order):
        if (arguments.getCheckForMove())
        {
            if (TemplateReportSupport.extendedMatchForReorder(parent, report, formulaType, false))
            {
                return;
            }
        }
        if (arguments.getCheckForRenameNamespace())
        {
            // Give a better message if the copy-of won't work because of namespace issues:
            SmSequenceType outputType = report.getFormulaType();
            if (outputType!=null) // sanity.
            {
                SmSequenceType otp = outputType.prime();
                if (otp.getName()!=null) // This can happen; may not compute to anything, so we need a name.
                {
                    SmSequenceType nnsOutputType = SmSequenceTypeFactory.createElement(
                            ExpandedName.makeName("*",otp.getName().getLocalName()),
                            null, 
                            otp.isNillable());
                    if (TemplateReportSupport.extendedMatchForNamespaceChangeNonDataComponent(report,nnsOutputType,false))
                    {
                        return;
                    }
                }
            }
        }
        // currently, no other extended matches.
    }

    public void formatFragment(XmlContentHandler handler) throws SAXException {
        handler.startElement(NAME,null,null);
        String f = getFormula();
        if (f==null) f = "";
        handler.attribute(SELECT_ATTR,f,null);
        if (getCopyNamespaces()!=null)
        {
            handler.attribute(COPY_NAMESPACES_ATTR,getCopyNamespaces().booleanValue() ? "yes" : "no",null);
        }
        for (int i=0;i<getChildCount();i++)
        {
            getChild(i).formatFragment(handler);
        }
        handler.endElement(NAME,null,null);
    }

    private static final ExpandedName NAME = ReadFromXSLT.makeName("copy-of");
    private static final ExpandedName SELECT_ATTR = ExpandedName.makeName("select");
    private static final ExpandedName COPY_NAMESPACES_ATTR = ExpandedName.makeName("copy-namespaces");

    /**
     * @return Either True, False, or null (for not set, which is equivalent to True)
     */
    public Boolean getCopyNamespaces()
    {
        return m_copyNamespaces;
    }

    public void setCopyNamespaces(Boolean copyNamespaces)
    {
        m_copyNamespaces = copyNamespaces;
    }

    public ExpandedName getName()
    {
        return NAME;
    }

    /**
     * Clones this binding node.
     * @return Binding A binding node of this type.
     */
    public Binding cloneShallow()
    {
        CopyOfBinding r = new CopyOfBinding(getElementInfo(),this.getFormula());
        r.setCopyNamespaces(getCopyNamespaces());
        return r;
    }
}
