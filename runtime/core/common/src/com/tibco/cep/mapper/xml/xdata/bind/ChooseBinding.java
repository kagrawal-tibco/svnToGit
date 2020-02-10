/*******************************************************************************
 * Copyright 2001 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.xml.xdata.bind;

import java.util.ArrayList;

import org.xml.sax.SAXException;

import com.tibco.cep.mapper.util.ResourceBundleManager;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.MessageCode;
import com.tibco.xml.channel.infoset.XmlContentHandler;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmSequenceType;

/**
 * Represents an XSLT choice.
 */
public class ChooseBinding extends ControlBinding
{
    public ChooseBinding(BindingElementInfo info)
    {
        super(info);
    }
    /**
     * Returns the formated name for toString() (from BindingReport).
     * @return String name to show in toString.
     */
    public String getReportFormatName()
    {
        return "choose statement";
    }

    /**
     * Shallow clones this binding node.
     * @return Binding A binding node of this type.
     */
    public Binding cloneShallow() {
        ChooseBinding cb = new ChooseBinding(getElementInfo());
        cb.setFormula(getFormula());
        return cb;
    }

    /**
     * The number of choices inside (not counting otherwise)
     */
    public int getChoiceCount() {
        int ct = getChildCount();
        if (ct==0) {
            return 0;
        }
        if (getChild(ct-1) instanceof OtherwiseBinding) {
            return ct-1;
        }
        return ct;
    }

    public boolean hasOtherwise() {
        int ct = getChildCount();
        if (ct==0) {
            return false;
        }
        if (getChild(ct-1) instanceof OtherwiseBinding) {
            return true;
        }
        return false;
    }

    public TemplateReport createTemplateReport(TemplateReport parent, ExprContext context, SmSequenceType expectedOutput, SmSequenceType outputContext, int outputValidationLevel, TemplateReportFormulaCache templateReportFormulaCache, TemplateReportArguments templateReportArguments) {
        TemplateReport ret = new TemplateReport(this,parent);
        ret.setContext(context);
        ret.setChildContext(context);
        ret.setFollowingContext(context);
        ret.setOutputContext(outputContext);
        ret.setChildOutputContext(outputContext);
        ret.setInitialOutputType(expectedOutput);

        int cc = getChildCount();
        ArrayList choices = new ArrayList();
        boolean hasSeenOtherwise = false;

        boolean anyChoiceHasEffect = false; // set if any choice (or otherwise) actually does anything:
        TemplateReport firstOutputProducingChild = null;
        SmSequenceType longestTypeSoFar = expectedOutput;
        for (int i=0;i<cc;i++) {
            Binding child = getChild(i);
            TemplateReport cr;
            if (child instanceof CommentBinding) {
                cr = child.createTemplateReport(ret, context,expectedOutput, outputContext, outputValidationLevel, templateReportFormulaCache, templateReportArguments);
            } else {
                if (child instanceof WhenBinding) {
                    if (hasSeenOtherwise)
                    {
                        String msg = ResourceBundleManager.getMessage(MessageCode.WHEN_AFTER_OTHERWISE);
                        cr = TemplateReportSupport.generateStructuralErrorReport(ret,expectedOutput,child,context,msg);
                        anyChoiceHasEffect = true; // don't add more errors...
                    }
                    else
                    {
                        cr = child.createTemplateReport(ret, context,expectedOutput, outputContext, outputValidationLevel, templateReportFormulaCache, templateReportArguments);
                        if (longestTypeSoFar==expectedOutput)
                        {
                            // This is the first 'when' we saw, so we don't actually need to do the computation:
                            // (Though if we did, the result would be the same)
                            longestTypeSoFar = cr.getRemainingOutputType(); // quicky optimization...
                        }
                        else
                        {
                            // Subsequent whens (and otherwise), need to call this.
                            longestTypeSoFar = longestTypeSoFar.leastCommonRemainder(cr.getRemainingOutputType());
                        }
                        choices.add(cr.getRemainingOutputType());
                        if (firstOutputProducingChild==null)
                        {
                            firstOutputProducingChild = TemplateReportSupport.findFirstOutputProducingChild(cr);
                        }
                        anyChoiceHasEffect = anyChoiceHasEffect || TemplateReportSupport.checkChildrenForContent(cr);
                    }
                } else {
                    if (child instanceof OtherwiseBinding) {
                        if (hasSeenOtherwise) {
                            String msg = ResourceBundleManager.getMessage(MessageCode.ONLY_1_OTHERWISE);
                            cr = TemplateReportSupport.generateStructuralErrorReport(ret,expectedOutput,child,context,msg);
                            anyChoiceHasEffect = true; // don't add more errors...
                        } else {
                            cr = child.createTemplateReport(ret, context,expectedOutput, outputContext, outputValidationLevel, templateReportFormulaCache, templateReportArguments);
                            choices.add(cr.getRemainingOutputType());
                            hasSeenOtherwise = true;
                            anyChoiceHasEffect = anyChoiceHasEffect || TemplateReportSupport.checkChildrenForContent(cr);
                            longestTypeSoFar = longestTypeSoFar.leastCommonRemainder(cr.getRemainingOutputType());
                            if (firstOutputProducingChild==null)
                            {
                                firstOutputProducingChild = TemplateReportSupport.findFirstOutputProducingChild(cr);
                            }
                        }
                    } else {
                        String msg = ResourceBundleManager.getMessage(MessageCode.ONLY_WHEN_AND_OTHERWISE);
                        cr = TemplateReportSupport.generateStructuralErrorReport(ret,expectedOutput,child,context,msg);
                        anyChoiceHasEffect = true; // don't add more errors...
                    }
                }
            }
        }

        // Bubble-out the preceding missing terms (better to put in front of the 'choose')
        // To do this, find the least number of preceding terms of any when or otherwise & extract only those.

        bubbleOutMissingTerms(ret, expectedOutput);
        if (!hasSeenOtherwise)
        {
            SmSequenceType expectedOutputAfterMissingPreceding = TemplateReportSupport.skipMissingPreceding(expectedOutput,ret.getMissingPrecedingTerms());
            TemplateReportSupport.addMissingCases(ret,longestTypeSoFar,expectedOutputAfterMissingPreceding,true); // true-> add following this choose (some paths didn't generate)
        }
        // Go ahead missing following to each of the branches:
        for (int i=0;i<cc;i++)
        {
            Binding child = getChild(i);
            if (child instanceof WhenBinding || child instanceof OtherwiseBinding)
            {
                SmSequenceType expectedOutputAfter = ret.getChild(i).getRemainingOutputType();
                if (expectedOutputAfter==ret.getChild(i).getInitialOutputType())
                {
                    // nothing in here, so instead use the from preceding:
                    expectedOutputAfter = TemplateReportSupport.skipMissingPreceding(expectedOutput,ret.getMissingPrecedingTerms());
                }
                TemplateReportSupport.addMissingCases(ret.getChild(i),longestTypeSoFar,expectedOutputAfter,false);
            }
        }
        if (!anyChoiceHasEffect)
        {
            TemplateReportSupport.addHasNoEffectError(ret);
        }
        if (templateReportArguments.getCheckForMove())
        {
            TemplateReportSupport.bubbleOutMove(firstOutputProducingChild, parent, ret);
        }

        // A choice can only have 1 'remaining output', so we, for simplicity & sanity, assume that whatever the
        // longest (most produced), in any choice, is the remainder.  The checker cannot check when a term is 'split'
        // between 2 independent choices, but that's 1) virtually impossible to check 2) a really ugly style & shouldn't
        // be allowed.
        //
        ret.setRemainingOutputType(longestTypeSoFar);

        ret.setExpectedType(getChooseMatchedTerm(ret));

        TemplateReportSupport.initIsRecursivelyErrorFree(ret);
        return ret;
    }

    private static SmSequenceType getChooseMatchedTerm(TemplateReport report)
    {
        SmSequenceType sofar = null;
        for (int i=0;i<report.getChildCount();i++)
        {
            SmSequenceType t = report.getChild(i).getExpectedType();
            if (t!=null)
            {
                if (sofar==null || sofar==t)
                {
                    sofar = t;
                }
                else
                {
                    // ambiguous, forget about it for now.
                    return null;
                }

            }
        }
        return sofar;
    }

    /**
     * Note that this can't re-use the same one as used by IfBinding.
     * @param ret
     * @param expectedOutput
     */
    private void bubbleOutMissingTerms(TemplateReport ret, SmSequenceType expectedOutput)
    {
        SmSequenceType[] sofar = null;
        for (int ci=0;ci<getChildCount();ci++) {
            TemplateReport wr = ret.getChild(ci);
            if (wr.getBinding() instanceof WhenBinding || wr.getBinding() instanceof OtherwiseBinding) {
                for (int i=0;i<wr.getChildCount();i++) {
                    TemplateReport tr = wr.getChild(i);
                    if (tr.getMissingPrecedingTerms().length>0) {
                        if (sofar==null) {
                            sofar = tr.getMissingPrecedingTerms();
                        } else {
                            SmSequenceType[] n = tr.getMissingPrecedingTerms();
                            int lc = Math.min(n.length,sofar.length);
                            for (int j=0;j<lc;j++) {
                                if (!sofar[j].equalsType(n[j])) {
                                    lc = j;
                                    break;
                                }
                            }
                            if (lc<sofar.length) {
                                SmSequenceType[] newsofar = new SmSequenceType[lc];
                                for (int j=0;j<newsofar.length;j++) {
                                    newsofar[j] = sofar[j];
                                }
                                sofar = newsofar;
                            }
                        }
                        break;
                    } else {
                        if (tr.getRemainingOutputType()!=expectedOutput) {
                            // no match at all.
                            sofar = new SmSequenceType[0];
                            break;
                        }
                    }
                }
            }
            if (sofar!=null && sofar.length==0) { // no point in checking any more.
                break;
            }
        }
        if (sofar!=null && sofar.length>0) {
            for (int ci=0;ci<getChildCount();ci++) {
                TemplateReport wr = ret.getChild(ci);
                if (wr.getBinding() instanceof WhenBinding || wr.getBinding() instanceof OtherwiseBinding) {
                    for (int i=0;i<wr.getChildCount();i++) {
                        TemplateReport tr = wr.getChild(i);
                        if (tr.getMissingPrecedingTerms().length>0) {
                            SmSequenceType[] o = tr.getMissingPrecedingTerms();
                            tr.removeAllMissingPrecedingTerms();
                            // add those that aren't common back again:
                            for (int j=sofar.length;j<o.length;j++) {
                                tr.addMissingPrecedingTerm(o[j]);
                            }
                            break;
                        }
                    }
                }
            }
            // add common ones to 'choose'
            for (int i=0;i<sofar.length;i++)
            {
                ret.addMissingPrecedingTerm(sofar[i]);
            }
        }
    }

    /*public static XTypeRemainder getRealSkipTo(XType options)
    {
        for (;;)
        {
            XTypeRemainder skipTo = options.skip();
            if (skipTo==null)
            {
                return skipTo;
            }
            if (!XTypeSupport.isEmptySet(skipTo.getMatched()))
            {
                return skipTo;
            }
            options = skipTo.getRemainder();
        }
    }*/

    public void formatFragment(XmlContentHandler handler) throws SAXException {
        super.issueNamespaceDeclarations(handler);
        handler.startElement(NAME,null,null);
        super.issueAdditionalAttributes(handler);
        for (int i=0;i<getChildCount();i++) {
            getChild(i).formatFragment(handler);
        }
        handler.endElement(NAME,null,null);
    }

    public ExpandedName getName() {
        return NAME;
    }

    private static final ExpandedName NAME = ExpandedName.makeName(ReadFromXSLT.XSLT_URI,"choose");
}
