package com.tibco.cep.mapper.xml.xdata.bind;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.tibco.cep.mapper.xml.xdata.NamespaceMapper;
import com.tibco.cep.mapper.xml.xdata.bind.virt.TypeCopyOfMatcher;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprUtilities;
import com.tibco.cep.mapper.xml.xdata.xpath.Lexer;
import com.tibco.cep.mapper.xml.xdata.xpath.Parser;
import com.tibco.cep.mapper.xml.xdata.xpath.Token;
import com.tibco.cep.mapper.xml.xdata.xpath.Utilities;
import com.tibco.xml.schema.SmSequenceType;

/**
 * Misc. binding related helper functions, should probably be re-organized.
 */
public final class BindUtilities
{
    /**
     * Change child formulas given a change in parent context (i.e. a for-each was inserted above some value-ofs; fix 'em)
     */
    public static void updateNestedFormula(Binding binding, ExprContext oldContext, ExprContext newContext, String newParentFormula, List changed)
    {
        boolean hadFormula = binding.getFormula()!=null;
        if (hadFormula) {
            String updatedFormula = updateFormula(oldContext,newContext,binding.getFormula(),newParentFormula);
            if (!updatedFormula.equals(binding.getFormula()))
            {
                binding.setFormula(updatedFormula);
                changed.add(binding);
            }
        }
        if (isContextChangingBinding(binding))
        {
            return;
        }
        updateNestedChildFormula(binding,oldContext,newContext,newParentFormula,changed);
    }

    /**
     * Same as {@link #updateNestedFormula} except that this does not update the formula on the initial (root) binding
     * passed in, but only its descendants.
     */
    public static void updateNestedChildFormula(Binding binding, ExprContext oldContext, ExprContext newContext, String newParentFormula, List changed)
    {
        // update all children.
        Binding[] children = binding.getChildren();
        for (int i=0;i<children.length;i++) {
            updateNestedFormula(children[i],oldContext,newContext,newParentFormula,changed);
        }
    }

    /**
     * Indicates if the children of this binding have a different '.' than the binding itself.<br>
     * (for-each/for-each-group do this)
     */
    public static boolean isContextChangingBinding(Binding b)
    {
        return b instanceof ForEachBinding || b instanceof ForEachGroupBinding;
    }

    public static String updateFormula(ExprContext oldContext, ExprContext newContext, String formula, String newParentFormula)
    {
        if (newParentFormula!=null && formula.startsWith(newParentFormula))
        {
            // shortcut that works some, but not all of the time.
            // This is here to preserve filters which wouldn't otherwise be preserved (they'll be lost if they are inside a concat or something, for now...)
            if (formula.equals(newParentFormula))
            {
                return ".";
            }
            if (formula.startsWith(newParentFormula + "/"))
            {
                return formula.substring(newParentFormula.length()+1);
            }
        }
        String absFormula = Utilities.makeAbsoluteXPath(oldContext.getInput(),formula,newContext.getNamespaceMapper());
        return Utilities.makeRelativeXPath(newContext,"",absFormula,null);
    }

    public static boolean hasUpdateableNestedChildren(Binding binding, ExprContext oldContext, ExprContext newContext, String newParentFormula)
    {
        boolean hadFormula = binding.getFormula()!=null;
        if (hadFormula)
        {
            String updatedFormula = updateFormula(oldContext,newContext,binding.getFormula(),newParentFormula);
            if (!updatedFormula.equals(binding.getFormula()))
            {
                return true;
            }
        }
        if (isContextChangingBinding(binding))
        {
            return false;
        }
        Binding[] children = binding.getChildren();
        for (int i=0;i<children.length;i++)
        {
            if (hasUpdateableNestedChildren(children[i],oldContext,newContext,newParentFormula))
            {
                return true;
            }
        }
        return false;
    }

    public static TemplateReport[] getReportPathFor(TemplateEditorConfiguration config, TemplateReportFormulaCache formulaCache, Binding[] bindingTree)
    {
        TemplateReport report = TemplateReport.create(config,formulaCache,new TemplateReportArguments());
        return getReportPathFor(report,bindingTree);
    }

    public static TemplateReport[] getReportPathFor(TemplateReport report, Binding[] bindingTree)
    {
        ArrayList temp = new ArrayList();
        temp.add(report); // the root.
        for (int i=1;i<bindingTree.length;i++) {
            Binding b = bindingTree[i];
            TemplateReport c = report.getChildFor(b);
            if (c==null) {
                throw new NullPointerException("Null report, on " + report.getBinding().getName() + " child " + b.getName());
            }
            temp.add(c);
            report = c;
        }
        TemplateReport[] reportTree = (TemplateReport[]) temp.toArray(new TemplateReport[temp.size()]);
        return reportTree;
    }

    public interface DropCallback {
        /**
         * Called to ask if a copy-of should be performed
         * @param isTypeCopyOf
         * @param hasContent
         * @return
         */
        boolean doCopyOf(boolean isTypeCopyOf, boolean hasContent);

        /**
         * Notifies that a substitution is required before a drop can take place.
         */
        Binding requiresSubstitutionBeforeDrop(String formula);


        /**
         * Notifies that autofill should (possibly) be done here.
         * @param candidate The candidate for autofill.
         * @param childInputType The left hand side '.' for the xslt (inside the bindings).
         * @param nm The namespace mapper
         */
        void doAutofill(Binding candidate, SmSequenceType childInputType, NamespaceMapper nm);
    }

    /**
     * Returns changed bindings (excluding the root).
     * @param xpath The xpath to drop, or null (or "") to mean delete existing formula.
     */
    public static Binding[] drop(TemplateEditorConfiguration config, TemplateReportFormulaCache formulaCache, Binding[] bindingTree, String xpath, DropCallback callback) {
        // If there are any markers, just do a simple insert:
        if (xpath!=null && xpath.length()==0) {
            xpath = null;
        }
        if (xpath!=null) {
            Token[] t = Lexer.lex(xpath);
            for (int i=0;i<t.length;i++) {
                if (t[i].getType()==Token.TYPE_MARKER_STRING) {
                    Binding last = bindingTree[bindingTree.length-1];
                    last.setFormula(xpath);
                    return new Binding[] {last};
                }
            }
        }
        // Otherwise, things get interesting.

        // First, look at the parent structure & see if there are any repetitions to match:
        int max = bindingTree.length;
        TemplateReport initialReport = null;
        ArrayList changedList = new ArrayList();
        for (;;) {
            TemplateReport[] reportTree = getReportPathFor(config,formulaCache,bindingTree);
            initialReport = reportTree[reportTree.length-1];
            boolean updatedContext;
            if (xpath!=null) {
                String[] pieces = Utilities.getAsArray(xpath);
                if (pieces!=null && pieces.length>1 && reportTree.length>1) {
                    String remaining = Utilities.fromArray(pieces,pieces.length-1);
                    updatedContext = autoUpdateStructureMaps(reportTree,reportTree.length-2,remaining,changedList);
                } else {
                    updatedContext = false;
                }
            } else {
                updatedContext = false;
            }
            if (!updatedContext) {
                break;
            }
            if (max--==0) {
                // This shouldn't be needed, but if there's some sort of bug inside, the
                // last thing we want is an infinite loop.
                break;
            }
        }
        TemplateReport[] reportTree = getReportPathFor(config,formulaCache,bindingTree);
        String modFormula = computeDropFormula(reportTree[reportTree.length-1],xpath);

        // set remaining (leaf).
        Binding leafBinding = bindingTree[bindingTree.length-1];
        leafBinding.setFormula(modFormula);
        // rerun report:
        reportTree = getReportPathFor(config,formulaCache,bindingTree);
        TemplateReport leafReport = reportTree[reportTree.length-1];
        boolean didCopyOf = false;
        if (xpath!=null) {
            /*if (isValidCopyOf(leafReport) && elementCopyMakesSense(leafReport)) {
                if (callback.doCopyOf(false,leafReport.getHasChildContent())) {
                    Binding newBinding = insertCopyOf(leafBinding,modFormula);
                    bindingTree[bindingTree.length-1] = newBinding;
                    didCopyOf = true;
                }
            } else {
                if (isValidTypeCopy(reportTree[reportTree.length-1]) && typeCopyMakesSense(leafReport)) {
                    if (callback.doCopyOf(true,leafReport.getHasChildContent())) {
                        Binding newBinding = insertTypeCopy(bindingTree[bindingTree.length-1],modFormula);
                        bindingTree[bindingTree.length-1] = newBinding;
                    }
                } else {
                    // no more work.
                }
            }*/
        }
        // WCETODO -- helper function for this: hasFixedElement (or something like that)
        /*
        if (!didCopyOf && (XTypeSupport.isChoice(leafReport.getExpectedType()) || XTypeSupport.isWildcard(leafReport.getExpectedType()))) {
            // undo this, can't set a formula.
            leafBinding.setFormula(null);
            Binding newBinding = callback.requiresSubstitutionBeforeDrop(modFormula);
            if (newBinding!=null) {
                changedList.add(newBinding);
            }
        } else {*
            // rerun report again:
            reportTree = getReportPathFor(config,sp,bindingTree);
            updateNestedFormula(reportTree[reportTree.length-1],initialReport.getChildContext(),modFormula,changedList);
        }
        if (xpath!=null) { // if we dropped a formula, do autofill
            // run one more time for autofill:
            reportTree = getReportPathFor(config,sp,bindingTree);
            BindingReport lastReport = reportTree[reportTree.length-1];
            callback.doAutofill(lastReport.getBinding(),lastReport.getChildContext().getInput(),config.getVirtualBinding().getNamespaceMap());
        }
        //}*/
        return (Binding[]) changedList.toArray(new Binding[0]);
    }

    /**
     * Takes a binding & replaces it (in its parent binding) with a copy-of.
     * @param b The binding to replace
     * @param formula The formula to set on the new copy-of binding.
     * @return The new copy-of binding
     */
    public static Binding insertCopyOf(Binding b, String formula) {
        Binding p = b.getParent();
        if (p==null) {
            // shouldn't ever happen.
            return b;
        }
        int index = p.getIndexOfChild(b);
        p.removeChild(index);
        CopyOfBinding tcp = new CopyOfBinding(BindingElementInfo.EMPTY_INFO,formula);
        p.addChild(index,tcp);
        return tcp;
    }

    /**
     * Updates 1 parent structure with a repetition matching.
     * @param reportTree A 'path' of TemplateReports.
     * @param at
     * @param xpath
     * @param changed
     * @return True if any update was made, false otherwise.
     */
    private static boolean autoUpdateStructureMaps(TemplateReport[] reportTree, int at, String xpath, List changed) {
        if (at<0) {
            return false;
        }
        TemplateReport pr = reportTree[at];
        if (pr==null) {
            throw new NullPointerException("Null report");
        }
        // Once we hit an already filled in, context setting formula, stop.
        /*
        if (pr.getBinding().getFormula()!=null && pr.getBinding().isTagGenerator()) {
            return false;
        }
        String remainingPath = xpath;
        boolean updated = false;
        if (pr.getBinding().getFormula()==null && pr.getBinding().isTagGenerator()) {
            String modFormula = computeDropFormula(pr,xpath);
            if (modFormula!=null) {
                String[] pieces = Utilities.getAsArray(modFormula);
                XOccurrence expectedCard = pr.getExpectedType().quantifier();
                boolean isStructureMapping = expectedCard.getMaxOccurs()>1;
                if (pieces!=null && isStructureMapping) {
                    for (int i=pieces.length;i>=1;i--) {
                        String ppath = Utilities.fromArray(pieces,i);
                        Expr e = new Parser(Lexer.lex(ppath)).getExpression();
                        ExprContext context = pr.getContext();
                        EvalTypeInfo info = new EvalTypeInfo();
                        XType xtype = e.evalType(context,info);
                        XOccurrence gotCard = xtype.quantifier();
                        if (gotCard.getMaxOccurs()>1) {
                            // guess that this is it.
                            String localModFormula = computeDropFormula(pr,ppath);
                            pr.getBinding().setFormula(localModFormula);
                            changed.add(pr.getBinding());
                            updated = true;
                            XType ni = xtype.prime();
                            ExprContext newContext = context.createWithInput(ni);
                            pr.setHasChildContent(true); // hack, makes call below work:
                            updateNestedChildren(pr,context,newContext,localModFormula,changed);
                            if (i>1) {
                                remainingPath = Utilities.fromArray(pieces,i-1);
                            } else {
                                return true;
                            }
                            break;
                        }
                    }
                }
            }
        }
        if (autoUpdateStructureMaps(reportTree,at-1,remainingPath,changed)) {
            return true;
        }
        return updated;*/
        return false;
    }

    private static String computeDropFormula(TemplateReport r, String path) {
        if (path==null) {
            // can be clearing it.
            return null;
        }
        throw new RuntimeException();/*
        ExprContext context = r.getContext();
        if (context==null) {
            System.out.println("Context is null on " + r.getBinding().getName());
        }
        XType targetType = r.getExpectedType();
        String rel = Utilities.makeRelativeXPath(context,"",path,targetType);
        if (rel==null) {
            return null;
        }
        return substituteActualFilters(rel,r.getBinding());*/
    }

    public static void renameVariable(Binding b, String oldName, String newName, Set takenVariableNames) {
        String f = b.getFormula();
        if (f!=null) {
            Expr e = Parser.parse(f);
            Expr ne = ExprUtilities.renameVariable(e,oldName,newName,takenVariableNames);
            String nf = ne.toExactString();
            b.setFormula(nf);
        }
        Binding[] c = b.getChildren();
        for (int i=0;i<c.length;i++) {
            Binding sb = c[i];
            renameVariable(sb,oldName,newName,takenVariableNames);
        }
    }

    /**
     * Checks to see if this binding performs a type-copy-of operations.<br>
     * This is done by matching the simple pattern:
     * <code>
     * <pre>
     *  <(element)>
     *    <xsl:for-each select="formula/@*">
     *      <copy-of ".">
     *    </xsl:for-each>
     *    <xsl:for-each select="formula/node()">
     *      <copy-of ".">
     *    </xsl:for-each>
     *  </(element)>
     * </pre>
     * </code>
     * (or one w/ a third for-each to copy namespaces).
     * where formula is the thing selected for copying....
     * @return true if this binding performs a type-copy-of.
     */
    public static boolean isTypeCopyOf(Binding binding)
    {
        return TypeCopyOfMatcher.INSTANCE.matches(binding);
    }


    /**
     * Find all global variables used by this binding.
     * @param b The binding.
     * @return A list (never null) of global variables expressed as Strings.
     */
    public static List findGlobalVars(Binding b)
    {
        ArrayList temp = new ArrayList();
        findGlobalVars(b,temp);
        return temp;
    }

    /**
     * Same as {@link #findGlobalVars}, but where the result list is passed in.
     * @param b The binding
     * @param addToList
     */
    public static void findGlobalVars(Binding b, List addToList)
    {
        String f = b.getFormula();
        // (Currently doesn't consider AVTs & does mistakenly consider non-formulas...)
        if (f!=null && f.indexOf("$_globalVariables")>=0) // quicky performance optimization
        {
            Expr e = Parser.parse(f);
            ExprUtilities.getGlobalVariables(e,addToList);
        }
        else if(f != null && f.indexOf("$") == -1){
           //If formula does not start with $_globalVariables or $, then it
          //is xpath formula on a nested field, find the evaluation context
          //and then concatenate with the formula.
           String evaluationContext  = getEvaluationContext(b);
           if(evaluationContext != null){
               f = evaluationContext + "/" + f;
               Expr e = Parser.parse(f);
               ExprUtilities.getGlobalVariables(e,addToList);
            }
        }
        for (int i=0;i<b.getChildCount();i++)
        {
            findGlobalVars(b.getChild(i),addToList);
        }
    }
   /**
    * Recursive method that gets the EvaluationContext of a
    * XPath formula. This applies to xpath formula on nested
    * fields.
    * @param binding
    * @return
    */
   private static String getEvaluationContext(Binding binding) {
       Binding parentBinding = binding.getParent();
       if(parentBinding == null){
           return binding.getFormula();
       }else{
           String bindName = parentBinding.getName().toString();
           if(bindName.equals("root")){
               return binding.getFormula();
           }else{
               return getEvaluationContext(parentBinding);
           }
       }
   }

    /*
     * Indicates if the report shows that a type-copy on this node makes sense
     * (i.e. the expected & computed types match, or the right side is a super type of the left),
     * @param report The report describing the binding to check.
     * @return True if it is a valid type copy.
     *
    public static boolean isValidTypeCopy(TemplateReport report) {
        return isValidTypeCopy(report.getExpectedType(),report.getComputedType());
    }

    public static boolean isValidTypeCopy(XType expectedType, XType computedType) {
        XType ptType = computedType;
        SmParticleTerm pt = ptType.getParticleTerm();
        if (pt instanceof SmElement) {
            SmType t2 = expectedType.getElementOverrideType();
            if (t2!=null && ptType.getElementOverrideType()!=null) {
                // Check for same type:
                // (WCETODO make more sophisticated --- check for subtyping)
                if (isSameType(ptType.getElementOverrideType(),t2)) {
                    return true;
                }
                // Check for right hand side is an any:
                throw new RuntimeException();/*
                if (expectedType.isAnyType()) {
                    return true;
                }*
            }
        }
        /*if (pt != null && expectedType.recursiveTypeEquals(new ParticleTermXType(pt))) {
            return true;
        }*
        return false;
    }*/
}

