package com.tibco.cep.mapper.xml.xdata.bind.fix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.tibco.cep.mapper.xml.xdata.bind.AttributeBinding;
import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.BindingElementInfo;
import com.tibco.cep.mapper.xml.xdata.bind.BindingManipulationUtils;
import com.tibco.cep.mapper.xml.xdata.bind.BindingNamespaceManipulationUtils;
import com.tibco.cep.mapper.xml.xdata.bind.ControlBinding;
import com.tibco.cep.mapper.xml.xdata.bind.CopyOfBinding;
import com.tibco.cep.mapper.xml.xdata.bind.DataComponentBinding;
import com.tibco.cep.mapper.xml.xdata.bind.ElementBinding;
import com.tibco.cep.mapper.xml.xdata.bind.ForEachBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReportArguments;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReportFormulaCache;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReportSupport;
import com.tibco.cep.mapper.xml.xdata.bind.virt.TypeCopyOfBinding;
import com.tibco.cep.mapper.xml.xdata.bind.virt.VirtualElementBinding;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprUtilities;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.data.primitive.QName;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmModelGroup;
import com.tibco.xml.schema.SmParticle;
import com.tibco.xml.schema.SmParticleTerm;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.SmWildcard;
import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.schema.xtype.SmSequenceTypeFactory;


/**
 * A utility class which updates bindings based on {@link com.tibco.cep.mapper.xml.xdata.bind.fix.XsdContentModelChangeEvent}.<br>
 * Wants to run on virtualize, markered, xslt.
 * (NOTE: This means that for every little change, you need to rerun the entire report.... for an optimization,
 * check to see if report depends on the change before re-running)
 */
public final class BindingContentModelChangeUpdater
{
    /**
     * Updates a template (with report handy) for a schema content model change.<br>
     * Only updates the INPUT side (doesn't change formula)
     */
    public static boolean update(TemplateReport report, XsdContentModelChangeEvent ce, boolean actuallyDoUpdates)
    {
        if (ce.getType()==XsdContentModelChangeEvent.NODES_MOVED)
        {
            return updateMove(report,ce,actuallyDoUpdates);
        }
        if (ce.getType()==XsdContentModelChangeEvent.NODE_RENAMED)
        {
            return updateRenamed(report,ce,actuallyDoUpdates);
        }
        return false; // don't know how to do that one.
    }

    /**
     * Updates a template (with report handy) input (formulas) for a content model change.
     */
    public static boolean updateFormulas(TemplateReport binding, XsdContentModelChangeEvent ce, boolean actuallyDoUpdates)
    {
        if (ce.getType()==XsdContentModelChangeEvent.NODE_RENAMED)
        {
            return updateFormulasForRenamed(binding,ce,actuallyDoUpdates);
        }
        return false; // don't know how to do that.
    }

    /**
     * Updates a template (with report handy) for renamed.
     */
    private static boolean updateFormulasForRenamed(TemplateReport report, XsdContentModelChangeEvent ce, boolean actuallyDoUpdates)
    {
        SmParticleTerm[] term = findParticleTermPathFor((SmElement)ce.getOriginalComponent(),ce.getPath());
        if (term==null || term.length==0)
        {
            return false;
        }
        if (term.length<1)
        {
            return false;
        }
        if (!(term[term.length-1] instanceof SmElement))
        {
            return false;
        }
        SmElement containingElement = (SmElement) term[term.length-1];
        // (I think that's the renamed element)
        ExpandedName newName = ce.getNewName();
        return updateFormulasForRenamedInternal(report,containingElement,newName,ce.getNewNamePath());
    }

    private static boolean updateFormulasForRenamedInternal(TemplateReport report, SmElement element, ExpandedName newName, ExpandedName[] newNamePath)
    {
        Expr e = report.getXPathExpression();
        boolean changed = false;
        if (e!=null)
        {
            ExprContext ec = report.getContext();
            NamespaceContextRegistry ni = BindingNamespaceManipulationUtils.createNamespaceImporter(report.getBinding());
            Expr ne;
            if (newName!=null)
            {
                ne = ExprUtilities.renameElement(e,ec,element,newName,ni);
            }
            else
            {
                ne = ExprUtilities.renameElement(e,ec,element,newNamePath,ni);
            }
            String nes = ne.toExactString();
            if (!nes.equals(report.getBinding().getFormula()))
            {
                changed = true;
                report.getBinding().setFormula(nes);
            }
        }
        for (int i=0;i<report.getChildCount();i++)
        {
            TemplateReport ch = report.getChild(i);
            boolean anyc = updateFormulasForRenamedInternal(ch,element,newName,newNamePath);
            if (anyc)
            {
                changed = true;
            }
        }
        return changed;
    }

    /**
     * Updates for a move (up or down) in content model terms.
     */
    private static boolean updateMove(TemplateReport report, XsdContentModelChangeEvent ce, boolean actuallyDoUpdates)
    {
        SmParticleTerm[] term = findParticleTermPathFor((SmElement)ce.getOriginalComponent(),ce.getPath());
        if (term==null || term.length==0)
        {
            return false;
        }
        if (term.length<2)
        {
            return false;
        }

        // assuming a move:
        SmParticleTerm leafTerm = term[term.length-1];
        if (!(leafTerm instanceof SmModelGroup))
        {
            return false;
        }
        SmModelGroup leaf = (SmModelGroup) leafTerm;
        SmParticleTerm termabove = term[term.length-2];
        if (!(termabove instanceof SmElement))
        {
            return false;
        }
        SmElement containingElement = (SmElement) termabove;

        // Find all uses of the containing element & prepare to update:
        TemplateReport[] candidateSet = getAllUsesOfElement(report,containingElement);

        // Sort out the moved child indexes:
        int[] ci = (int[]) ce.getChildIndexes().clone();
        Arrays.sort(ci);
        int jump = ce.getMoveOffset();
        boolean hadAny = false;

        TemplateReportFormulaCache trfc = new TemplateReportFormulaCache();
        TemplateReportArguments args = new TemplateReportArguments();
        args.setRecordingMissing(false);
        // WCETODO --- add more pruning flags for this scenario.

        for (int candidateIndex=0;candidateIndex<candidateSet.length;candidateIndex++)
        {
            TemplateReport candidate = candidateSet[candidateIndex];
            ArrayList allMoves = new ArrayList();
            for (int index=0;index<ci.length;index++)
            {
                int childIndex = ci[index];
                int actualJump = computeActualJump(jump,ci,childIndex);
                Move m = updateCandidateForMove(leaf, childIndex, candidate, trfc, args, actualJump, actuallyDoUpdates);
                if (m!=null)
                {
                    if (actuallyDoUpdates)
                    {
                        allMoves.add(m);
                    }
                    hadAny = true;
                }
            }
            if (jump>0)
            {
                // Go forward:
                for (int i=0;i<allMoves.size();i++)
                {
                    Move m = (Move) allMoves.get(i);
                    applyMove(m);
                }
            }
            else
            {
                // Go in reverse (otherwise moved ones come in backwards)
                for (int i=allMoves.size()-1;i>=0;i--)
                {
                    Move m = (Move) allMoves.get(i);
                    applyMove(m);
                }
            }
        }
        return hadAny;
    }

    private static int computeActualJump(int jump, int[] steps, int offset)
    {
        int jumpedSteps = 0;
        int at = offset;
        while (jumpedSteps<Math.abs(jump))
        {
            if (jump<0)
            {
                at--;
            }
            else
            {
                at++;
            }
            if (!isMoved(steps, at))
            {
                jumpedSteps++;
            }
        }
        int actualJump = at-offset;
        return actualJump;
    }

    private static boolean isMoved(int[] previousSteps, int at)
    {
        for (int i=0;i<previousSteps.length;i++)
        {
            if (previousSteps[i]==at)
            {
                return true;
            }
        }
        return false;
    }

    static class Move
    {
        public int m_moveTo;
        public ArrayList m_move = new ArrayList();
    }

    private static Move updateCandidateForMove(SmModelGroup leaf, int childIndex, TemplateReport report, TemplateReportFormulaCache trfc, TemplateReportArguments args, int jump, boolean actuallyDoUpdates)
    {
        SmSequenceType atts = report.getComputedType().attributeAxis();
        // Recreate the template report here, only counting the 'skipped ones'.

        // This is some fancy footwork --- essentially what's going on is that we're looking for 3 positions in the bindings
        // corresponding to 1) The start of the original moved type 2) The end of the original moved type 3) The start
        // of the location it should move to.
        //
        // In order to 'find' a SmModelGroup location in bindings, we do something somewhat clever:
        // Create an XType that corresponds to all-the-stuff-leading up to that location (using createRange),
        // then re-run that section of the TemplateReport using this new XType as the desired output.
        // The last binding that 'matches' anything will be the one with that SmModelGroup location.
        //
        // With these positions calculated, all that's left is pretty simple tree manipulation:
        SmSequenceType skipTypes = SmSequenceTypeFactory.createSimplifiedSequence(atts,SmSequenceTypeFactory.createRange(null,leaf,0,childIndex));
        TemplateReport lastSkipped = getLastMatchingBinding(report, skipTypes, trfc, args);
        SmSequenceType skipTypesAndMatch = SmSequenceTypeFactory.createSimplifiedSequence(atts,SmSequenceTypeFactory.createRange(null,leaf,0,childIndex+1));
        TemplateReport lastSkipAndMatch = getLastMatchingBinding(report,skipTypesAndMatch,trfc,args);

        int moveToOffset = childIndex + jump + (jump>0 ? 1 : 0); // need to skip self.
        SmSequenceType moveToType = SmSequenceTypeFactory.createSimplifiedSequence(atts,SmSequenceTypeFactory.createRange(null,leaf,0,moveToOffset));
        TemplateReport moveToLocation = getLastMatchingBinding(report,moveToType,trfc,args);

        int istart = getIndexOfChildAfter(lastSkipped);
        int iend = getIndexOfChildAfter(lastSkipAndMatch);
        if (istart>=iend)
        {
            return null;
        }
        Move m = new Move();
        m.m_moveTo = getIndexOfChildAfter(moveToLocation);//==null ? null : moveToLocation.getBinding();
        for (int i=istart;i<iend;i++)
        {
            Binding b = report.getBinding().getChild(i);
            if (b==null)
            {
                throw new NullPointerException();
            }
            m.m_move.add(b);
        }
        return m;
    }

    private static void applyMove(Move m)
    {
        for (int i=m.m_move.size()-1;i>=0;i--)
        {
            Binding b = (Binding) m.m_move.get(i);
            int after = m.m_moveTo;
            Binding p = b.getParent();
            p.addChild(after,b.cloneDeep());
        }
        for (int i=0;i<m.m_move.size();i++)
        {
            Binding b = (Binding) m.m_move.get(i);
            Binding p = b.getParent();
            int ioc = p.getIndexOfChild(b);
            p.removeChild(ioc);
        }
    }

    /**
     * Gets the index of this in its parent+1, if this is null, returns 0
     */
    private static int getIndexOfChildAfter(TemplateReport report)
    {
        if (report==null)
        {
            return 0;
        }
        return report.getParent().getIndexOfChild(report)+1;
    }

    private static TemplateReport getLastMatchingBinding(TemplateReport report, SmSequenceType expectedOutput, TemplateReportFormulaCache trfc, TemplateReportArguments args)
    {
        // Rerun report at this level:
        TemplateReport fakeReport = new TemplateReport(report.getBinding(),null);
        fakeReport.setContext(report.getContext());
        fakeReport.setChildContext(report.getChildContext());
        fakeReport.setChildOutputContext(report.getChildOutputContext());
        fakeReport.setInitialOutputType(expectedOutput);//??
        TemplateReportSupport.traverseChildren(fakeReport,expectedOutput,SmWildcard.STRICT,trfc,args);
        // find last matching one:
        return getLastMatchingBinding(fakeReport);
    }

    private static TemplateReport getLastMatchingBinding(TemplateReport skippedReport)
    {
        for (int i=skippedReport.getChildCount()-1;i>=0;i--)
        {
            TemplateReport found = skippedReport.getChild(i);
            if (found.getComputedType()!=null && found.getOutputContextError()==null)
            {
                return found;
            }
        }
        return null; // not found.
    }

    private static TemplateReport[] getAllUsesOfElement(TemplateReport root, SmElement element)
    {
        ArrayList temp = new ArrayList();
        getAllUsesOfElementInternal(root,element,temp);
        return (TemplateReport[]) temp.toArray(new TemplateReport[temp.size()]);
    }

    private static void getAllUsesOfElementInternal(TemplateReport at, SmElement element, ArrayList al)
    {
        SmSequenceType ct = at.getComputedType();
        if (ct!=null && ct.getParticleTerm()==element)
        {
            al.add(at);
        }
        int cc = at.getChildCount();
        for (int i=0;i<cc;i++)
        {
            getAllUsesOfElementInternal(at.getChild(i),element,al);
        }
    }

    private static boolean updateRenamed(TemplateReport report, XsdContentModelChangeEvent ce, boolean actuallyDoUpdates)
    {
        SmParticleTerm[] term = findParticleTermPathFor((SmElement)ce.getOriginalComponent(),ce.getPath());
        if (term==null || term.length==0)
        {
            return false;
        }
        if (term.length<1)
        {
            return false;
        }
        if (!(term[term.length-1] instanceof SmElement))
        {
            return false;
        }
        SmElement containingElement = (SmElement) term[term.length-1];

        // Find all uses of the containing element & prepare to update:
        /*
        TemplateReport[] candidateSet = getAllUsesOfElement(report,containingElement);

        ExpandedName oldName = ce.getOldName();
        ExpandedName newName = ce.getNewName();
        boolean hadAny = false;
        for (int i=0;i<candidateSet.length;i++)
        {
            TemplateReport candidate = candidateSet[i];
            if (candidate.getBinding() instanceof ElementBinding)
            {
                ElementBinding eb = (ElementBinding) candidate.getBinding();
                if (!eb.isExplicitXslRepresentation() && eb.getName().equals(oldName))
                {
                    hadAny = true;
                    if (actuallyDoUpdates)
                    {
                        eb.setLiteralName(newName);
                    }
                }
            }
        }*/
        HashMap map = new HashMap();
        if (ce.getNewName()!=null)
        {
            map.put(containingElement,ce.getNewName());
        }
        else
        {
            map.put(containingElement,ce.getNewNamePath());
        }
        massElementRename(map,report);
        return true;
    }

    public static void massElementRename(Map oldNameToNewName, TemplateReport report)
    {
        massElementRenameInternal(oldNameToNewName,report,new HashSet());
    }

    /**
     * Used for AE metadata migration... had noble thoughts of being for refactoring, but there's no refactoring in
     * Designer/XA & this code is now too wedded to that horrible mistake we call AE metadata migration.
     * @param movedOutOfSet Keeps track of items
     */
    private static void massElementRenameInternal(Map oldNameToNewName, TemplateReport report, Set movedOutOfSet)
    {
        boolean moveControlsOut = true; //WCETODO unhardcode, make property of name map.
        if (report.getBinding() instanceof ElementBinding)
        {
            massElementRenameElement(report, oldNameToNewName, moveControlsOut, movedOutOfSet);
        }
        if (report.getBinding() instanceof CopyOfBinding && report.getFormulaType()!=null)
        {
            massElementRenameCopyOf(report, oldNameToNewName, moveControlsOut);
        }
        // ... and recurse.
        int cc = report.getChildCount();
        for (int i=0;i<cc;i++)
        {
            massElementRenameInternal(oldNameToNewName,report.getChild(i),movedOutOfSet);
        }
    }

    private static void massElementRenameElement(TemplateReport report, Map oldNameToNewName, boolean moveControlsOut, Set movedOutOfSet)
    {
        SmElement el = (SmElement) report.getComputedType().getParticleTerm();
        Object nn = oldNameToNewName.get(el);
        //if (el.getName().equals(""))
        if (nn!=null)
        {
            if (nn instanceof ExpandedName)
            {
                ElementBinding eb = ((ElementBinding)report.getBinding());
                eb.setLiteralName((ExpandedName)nn);
            }
            else
            {
                ExpandedName[] ar = (ExpandedName[]) nn;

                // God, this is <really> hard:
                ExpandedName first = ar[0];
                ElementBinding eb = ((ElementBinding)report.getBinding());
                eb.setLiteralName(first);
                Binding[] oldChildren = eb.getChildren();
                eb.removeAllChildren();

                // now make sure it's either been re-parented or do so:
                Binding addTo = eb;
                for (int i=1;i<ar.length;i++)
                {
                    ElementBinding ceb = new ElementBinding(BindingElementInfo.EMPTY_INFO,ar[i]);
                    addTo.addChild(ceb);
                    addTo = ceb;
                }
                for (int i=0;i<oldChildren.length;i++)
                {
                    addTo.addChild(oldChildren[i]);
                }

                if (moveControlsOut)
                {
                    moveControlsOut(eb, addTo, movedOutOfSet);
                }
                checkItemMultiplePolicy(eb);
                //System.out.println("Here with:" + eb.getName() + " rseult is:" + eb);
            }
        }
    }

    /**
     * In the case where there was a multiple policy, i.e.:
     *
     * SomeElement
     * SomeElement
     *
     * ... and SomeElement became a
     * @param binding
     */
    private static void checkItemMultiplePolicy(Binding binding)
    {
        Binding previous = BindingManipulationUtils.getPrecedingSibling(binding);
        if (previous!=null && (binding instanceof ElementBinding || binding instanceof TypeCopyOfBinding))
        {
            if (previous.getName().equals(binding.getName()))
            {
                // Aha!  Adjust for this:
                Binding[] ch = binding.getChildren();
                binding.removeAllChildren();
                for (int i=0;i<ch.length;i++)
                {
                    previous.addChild(ch[i]);
                }
                BindingManipulationUtils.removeFromParent(binding);
            }
        }
    }

    private static void massElementRenameCopyOf(TemplateReport report, Map oldNameToNewName, boolean moveControlsOut)
    {
        boolean isCopyContents = isTypeCopyOfNode(report);
        if (isCopyContents)
        {
            // This is part of a type-copy-of, skip it.
            return;
        }
        SmParticleTerm t = report.getFormulaType().prime().getParticleTerm();
        boolean isTypeDifferent = false; // Do the formula type & expected type differ (in terms of pointers?)
        Object nn = null;
        if (t instanceof SmElement)
        {
            nn = oldNameToNewName.get(t);
        }
        if (nn==null)
        {
            // Try matching on the expected type (which can have the correct pointer in cases where the left-hand side
            // is set up differently.
            SmSequenceType et = report.getExpectedType();
            if (et!=null)
            {
                SmParticleTerm eterm = et.prime().getParticleTerm();
                if (eterm instanceof SmElement)
                {
                    // Try looking that up.
                    nn = oldNameToNewName.get(eterm);
                    // yes, they differ.
                    isTypeDifferent = true;
                }
            }

        }
        if (nn==null)
        {
            // not found, oh well.
            return;
        }
        if (nn instanceof ExpandedName)
        {
            // If there's a copy of where the LHS name is different than the RHS (this can happen when, for example,
            // a save-as on the schema has been done or other such stuff)...
            ExpandedName nn2 = report.getFormulaType().prime().getName();
            ExpandedName newName = (ExpandedName) nn;
            if (nn2!=null && !newName.equals(nn2))
            {
                // In that case switch to type copy of:
                //
                // (This is required for the repo in bug: 1-1K57NU)
                //System.xout.println("Instance of copy-of rename with:" + nn2 + " and " + newName);
                ForEachBinding feb = new ForEachBinding(BindingElementInfo.EMPTY_INFO,report.getBinding().getFormula());
                Binding nr = new TypeCopyOfBinding(BindingElementInfo.EMPTY_INFO,newName);
                feb.addChild(nr);
                nr.setFormula(".");
                BindingManipulationUtils.replaceInParent(report.getBinding(),feb);
            }
        }
        // If this is only a rename, don't do anything; the copy-of doesn't contain the name.
        if (nn instanceof ExpandedName[])
        {
            // Move in the copy-of:
            if (moveControlsOut)
            {
                ExpandedName[] ar = (ExpandedName[]) nn;
                Binding addTo = null;
                Binding root = null;
                for (int i=0;i<ar.length-1;i++)
                {
                    ElementBinding ceb = new ElementBinding(BindingElementInfo.EMPTY_INFO,ar[i]);
                    if (addTo==null)
                    {
                        addTo = ceb;
                        root = addTo;
                    }
                    else
                    {
                        addTo.addChild(ceb);
                    }
                }
                // Move copy-of down:
                BindingManipulationUtils.replaceInParent(report.getBinding(),root);
                Binding b = report.getBinding();
                if (isTypeDifferent)
                {
                    // This can happen when, for example, the user has a copy-map
                    // from an activity returning a single instance of an (AE) element to
                    // a sequence of that type --- before it worked because there was no 'item' child, but now
                    // it doesn't; must switch to type-copy-of.
                    TypeCopyOfBinding tc = new TypeCopyOfBinding(BindingElementInfo.EMPTY_INFO,ar[ar.length-1]);
                    tc.setFormula(b.getFormula());
                    b = tc.normalize(null);
                }
                addTo.addChild(b);
                checkItemMultiplePolicy(root);
            }
        }
    }

    public static boolean isTypeCopyOfNode(TemplateReport report)
    {
        if (report.getBinding().getFormula()==null)
        {
            return false;
        }
        if (report.getBinding().getFormula().equals("./node()"))
        {
            TemplateReport prev = TemplateReportSupport.getPrecedingSibling(report);
            if (prev!=null && prev.getBinding() instanceof CopyOfBinding && prev.getBinding().getFormula().equals("./@*"))
            {
                // this is a copy-contents copy-of... exclude.
                return true;
            }
        }
        return false;
    }

    private static void moveControlsOut(ElementBinding eb, Binding addTo, Set movedOutOfSet)
    {
        Binding bp = findContainingControl(eb);
        Binding moveMe = addTo;
        if (bp!=eb && bp.getParent()!=null)
        {
            // This is for the case where 'xyz*' is replaced by 'xyz/item*', so the for-each around
            // xyz* needs to be moved in around item* but inside of xyz:
            Binding itemEl = addTo;
            Binding seqContainerEl = itemEl.getParent();

            // For cases such as a choose/when or multiple-policy, there may, in fact, be multiple
            // nodes around which we want to 'pull out' the sequence container.  However, we only want
            // to pull it out once, so a set, movedOutOfSet, keeps track of those that have already been
            // pulled out.
            if (!movedOutOfSet.contains(bp))
            {
                // Not yet pulled out, do so:
                BindingManipulationUtils.moveAround(bp, seqContainerEl);
                // record it:
                movedOutOfSet.add(bp);
            }
            else
            {
                // Already pulled out, just delete the seq container (preserving children)
                BindingManipulationUtils.removeSaveChildren(seqContainerEl);
            }
            moveMe = bp;
        }
        // For multiple policy (i.e. more than 1 or the item in the output), need to do another fixup:
        Binding n = getPrecedingNodeWithSameElementOutput(eb,eb.getName());
        if (n!=null)
        {
            // Ok, there was multiple policy:
            // Move the old content node (i.e. item) under the previous 'grouping' element & delete the new grouping element.
            BindingManipulationUtils.removeFromParent(moveMe);
            n.addChild(moveMe);
            BindingManipulationUtils.removeFromParent(eb);
        }
    }

    private static Binding getPrecedingNodeWithSameElementOutput(Binding searchFrom, ExpandedName name)
    {
        Binding p = searchFrom.getParent();
        if (p==null)
        {
            return null;
        }
        int ioc = p.getIndexOfChild(searchFrom);
        for (int i=ioc-1;i>=0;i--)
        {
            Binding preceding = p.getChild(i);
            Binding f = startsWithElementInControls(preceding,name);
            if (f!=null)
            {
                return f;
            }
        }
        return null;
    }

    private static Binding startsWithElementInControls(Binding searchFrom, ExpandedName name)
    {
        if (searchFrom instanceof ControlBinding)
        {
            if (searchFrom.getChildCount()>0)
            {
                return startsWithElementInControls(searchFrom.getChild(0),name);
            }
        }
        if (searchFrom instanceof ElementBinding)
        {
            if (name.equals(searchFrom.getName()))
            {
                return searchFrom;
            }
        }
        return null;
    }

    public static void massTypeRename(Map oldNameToNewName, TemplateReport report)
    {
        if (report.getBinding() instanceof AttributeBinding)
        {
            AttributeBinding ab = (AttributeBinding) report.getBinding();
            if (!ab.isExplicitXslRepresentation())
            {
                ExpandedName aname;
                try
                {
                    aname = ab.computeComponentExpandedName(report.getContext().getNamespaceMapper());
                }
                catch (Exception e)
                {
                    aname = null;
                }
                if (aname!=null && aname.equals(XSDL.ATTR_TYPE.getExpandedName()))
                {
                    QName qn = new QName(ab.getFormula());
                    ExpandedName tname = qn.getExpandedName(report.getContext().getNamespaceMapper());
                    if (tname!=null)
                    {
                        ExpandedName nn = (ExpandedName) oldNameToNewName.get(tname);
                        if (nn!=null)
                        {
                            QName qn2 = nn.getQName(report.getContext().getNamespaceMapper());
                            ab.setFormula(qn2.toString());
                        }
                    }
                }
            }
        }
        if (report.getBinding() instanceof VirtualElementBinding)
        {
            /* TODO
            VirtualElementBinding veb = (VirtualElementBinding) report.getBinding();
            ExpandedName ts = veb.getTypeSubstitution();
            System.out.println("Ts is " + ts);*/
        }
        int cc = report.getChildCount();
        for (int i=0;i<cc;i++)
        {
            massTypeRename(oldNameToNewName,report.getChild(i));
        }
    }

    private static Binding findContainingControl(Binding at)
    {
        Binding p = at.getParent();
        if (p!=null && !(p instanceof DataComponentBinding))
        {
            return findContainingControl(p);
        }
        else
        {
            return at;
        }
    }

    private static String formatParticleTermPath(SmParticleTerm[] term)
    {
        StringBuffer sb = new StringBuffer();
        for (int i=0;i<term.length;i++)
        {
            if (i>0)
            {
                sb.append(", ");
            }
            sb.append(term[i].getName());
        }
        return sb.toString();
    }

    public static SmParticleTerm[] findParticleTermPathFor(SmParticleTerm at, XsdContentModelPath path)
    {
        ArrayList list = new ArrayList();
        SmParticleTerm tip = findParticleTermPathFor(at,path,list);
        if (tip==null)
        {
            return null;
        }
        return (SmParticleTerm[]) list.toArray(new SmParticleTerm[0]);
    }

    private static SmParticleTerm findParticleTermPathFor(SmParticleTerm at, XsdContentModelPath path, ArrayList addTo)
    {
        if (path==null)
        {
            addTo.add(at);
            return at;
        }
        SmParticleTerm f = findParticleTermPathFor(at,path.getParentPath(),addTo);
        if (f==null)
        {
            return null; // not found.
        }
        if (path.isModelGroupStep() && f instanceof SmModelGroup)
        {
            // good! Both the 'directions' and the model have a model group here.
            SmModelGroup mg = (SmModelGroup) f;
            SmParticleTerm term = getChildParticleTerm(mg,path.getModelGroupIndex());
            if (term!=null)
            {
                addTo.add(term);
                return term;
            }
            return null;
        }
        else
        {
            if (!path.isModelGroupStep() && f instanceof SmElement)
            {
                // good
                SmElement el = (SmElement) f;
                SmParticleTerm t;
                if (path.isStepInContentModel())
                {
                    t = el.getType().getContentModel();
                }
                else
                {
                    t = el.getType().getAttributeModel();
                }
                if (t!=null)
                {
                    addTo.add(t);
                }
                return t;
            }
            else
            {
                // Content model doesn't match change, return.
                return null;
            }
        }
    }

    private static SmParticleTerm getChildParticleTerm(SmModelGroup group, int index)
    {
        Iterator i = group.getParticles();
        int cd = index;
        while (i.hasNext() && cd>0)
        {
            i.next();
            cd--;
        }
        if (cd==0 && i.hasNext())
        {
            SmParticle p = (SmParticle) i.next();
            return p.getTerm();
        }
        return null;
    }
}
