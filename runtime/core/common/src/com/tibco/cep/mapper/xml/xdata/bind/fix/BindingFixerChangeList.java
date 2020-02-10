package com.tibco.cep.mapper.xml.xdata.bind.fix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.BindingManipulationUtils;
import com.tibco.cep.mapper.xml.xdata.bind.CommentBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReportExtendedError;
import com.tibco.cep.mapper.xml.xdata.bind.virt.MarkerBinding;
import com.tibco.cep.mapper.xml.xdata.xpath.ErrorMessage;
import com.tibco.cep.mapper.xml.xdata.xpath.ErrorMessageList;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

/**
 * The error list for bindings.
 */
public class BindingFixerChangeList {
    private ArrayList mChanges = new ArrayList();
    private boolean mIncludeOtherwise;
    private boolean m_includeFormulaErrors = true;
    private boolean m_includeOutputErrors = true;
    private boolean m_includeValueRequired = true;
    private boolean m_includeExtendedErrors = true;
    private boolean m_includeStructural = true;
    private boolean m_includeMissingOptionalTerms = true;
    private boolean m_autoCreateMissingRequired = true;

    private boolean m_includeWarnings = true;

    public BindingFixerChangeList()
    {
    }

    /**
     * Adds to list IF it qualifies under the warning/optimization level.
     * @param c
     */
    private void addToList(BindingFixerChange c)
    {
        if (!m_includeWarnings && c.getCategory()!=BindingFixerChange.CATEGORY_ERROR)
        {
            return;
        }
        mChanges.add(c);
    }

    public int size() {
        return mChanges.size();
    }

    public void clear() {
        mChanges.clear();
    }

    public void run(TemplateReport report) {
        clear();
        addErrors(report);
    }

    /**
     * If set, will record warnings in addition to errors.<br>
     * By default this is on.<br>
     * This cuts across many of the other settings; this is essentially independent.
     * @param val
     */
    public void setIncludeWarnings(boolean val)
    {
        m_includeWarnings = val;
    }

    public boolean getIncludeWarnings()
    {
        return m_includeWarnings;
    }

    /**
     * If set, will repair include otherwise (advanced) things.<br>
     * By default this is off.
     * @param val
     */
    public void setIncludeOtherwiseCheck(boolean val)
    {
        mIncludeOtherwise = val;
    }

    /**
     * If set will list formula errors.<br>
     * By default, this is <b>on</b>.
     * @param val
     */
    public void setIncludeFormulaErrors(boolean val)
    {
        m_includeFormulaErrors = val;
    }

    /**
     * If set will list output (out of place) errors.<br>
     * By default, this is <b>on</b>.
     * @param val
     */
    public void setIncludeOutputErrors(boolean val)
    {
        m_includeOutputErrors = val;
    }

    /**
     * If set will list output (out of place) errors.<br>
     * By default, this is <b>on</b>.
     */
    public void setIncludeValueRequired(boolean val)
    {
        m_includeValueRequired = val;
    }

    /**
     * If set will list gross validation (out of place) errors.<br>
     * By default, this is <b>on</b>.
     */
    public void setIncludeStructuralErrors(boolean val)
    {
        m_includeStructural = val;
    }

    /**
     * If set, missing required elements will be added.<br>
     * By default, this is <b>on</b>.
     */
    public void setAutocreateMissingRequired(boolean val)
    {
        m_autoCreateMissingRequired = val;
    }

    /**
     * If set will list all extended errors.<br>
     * By default, this is <b>on</b>.
     */
    public void setIncludeExtendedErrors(boolean val)
    {
        m_includeExtendedErrors = val;
    }

    /**
     * If set, will report missing required AND optional terms, otherwise only reports missing required terms.<br>
     * Reporting missing optional terms is useful for inserting markers.
     * By default, this is <b>on</b>.
     */
    public void setIncludeMissingOptionalTerms(boolean val)
    {
        m_includeMissingOptionalTerms = val;
    }

    public BindingFixerChange getChange(int index) {
        return (BindingFixerChange) mChanges.get(index);
    }

    public void applyChanges() {
        int c = mChanges.size();
        ArrayList moveTos = new ArrayList();
        for (int i=0;i<c;i++)
        {
            BindingFixerChange ch = (BindingFixerChange) mChanges.get(i);
            ch.applyMove(moveTos);
        }
        // do moves & adds now:
        HashMap destToGroup = new HashMap();
        for (int i=0;i<moveTos.size();i++) {
            // group by destination:
            Object item = moveTos.get(i);
            TemplateReport moveTo;
            if (item instanceof TemplateReport)
            {
                TemplateReport moveFrom = (TemplateReport)item;
                moveTo = moveFrom.getMoveToPreceding();
                if (moveTo==null)
                {
                    moveTo = moveFrom.getStructuralErrorMoveTo();
                }
            }
            else
            {
                BindingFixerChange.MarkerAddition a = (BindingFixerChange.MarkerAddition) item;
                moveTo = a.m_on;
            }
            if (!destToGroup.containsKey(moveTo))
            {
                destToGroup.put(moveTo,new ArrayList());
            }
            ArrayList al = (ArrayList) destToGroup.get(moveTo);
            al.add(item);
        }
        // For each destination, do the moves in order:
        Iterator it = destToGroup.values().iterator();
        while (it.hasNext()) {
            ArrayList al = (ArrayList) it.next();
            Object[] from = al.toArray();
            Arrays.sort(from,new Comparator() {
                public int compare(Object o1, Object o2)
                {
                    int f1i = getIndex(o1);
                    int f2i = getIndex(o2);
                    // Because -1 indicates move to the item itself, for sorting purposes, it need to be highest.
                    if (f1i==-1) f1i = Integer.MAX_VALUE;
                    if (f2i==-1) f2i = Integer.MAX_VALUE;
                    return f1i<f2i ? -1 : 1;
                }

                private int getIndex(Object object)
                {
                    if (object instanceof TemplateReport)
                    {
                        TemplateReport tr = (TemplateReport) object;
                        return tr.getMoveToPrecedingIndex();
                    }
                    else
                    {
                        BindingFixerChange.MarkerAddition ma = (BindingFixerChange.MarkerAddition) object;
                        return ma.m_precedingIndex;
                    }
                }
            });
            // from is now ordered for adding:
            Object f0 = from[0];
            Binding to;
            if (f0 instanceof TemplateReport)
            {
                TemplateReport tr = (TemplateReport) f0;
                if (tr.getMoveToPreceding()==null)
                {
                    to = tr.getStructuralErrorMoveTo().getBinding(); // other type of move to.
                }
                else
                {
                    to = tr.getMoveToPreceding().getBinding(); // same for all.
                }
            }
            else
            {
                BindingFixerChange.MarkerAddition ma = (BindingFixerChange.MarkerAddition) f0;
                to = ma.m_on.getBinding();
            }
            Binding toParent = to.getParent();
            int ioc = toParent.getIndexOfChild(to);
            for (int i=0;i<from.length;i++) {
                // Move any comments (those right above this)
                TemplateReport prev = from[i] instanceof TemplateReport ? BindingManipulationUtils.getPreviousSibling((TemplateReport)from[i]) : null;
                while (prev!=null) {
                    if (prev.getBinding() instanceof CommentBinding)
                    {
                        Binding b = prev.getBinding();
                        Binding fromp = b.getParent();
                        // remove from old spot:
                        int ioc2 = fromp.getIndexOfChild(b);
                        fromp.removeChild(ioc2);
                        // attach to new spot:
                        toParent.addChild(ioc++,b);
                    }
                    else
                    {
                        // no more comments:
                        break;
                    }
                    prev = BindingManipulationUtils.getPreviousSibling(prev);
                }
                // Move the item itself:
                Binding b = from[i] instanceof TemplateReport ? ((TemplateReport)from[i]).getBinding() : ((BindingFixerChange.MarkerAddition) from[i]).m_marker;
                Binding fromp = b.getParent();
                // remove from old spot:
                if (from[i] instanceof TemplateReport)
                {
                    int ioc2 = fromp.getIndexOfChild(b);
                    fromp.removeChild(ioc2);
                }
                // attach to new spot:
                toParent.addChild(ioc++,b);
                if (f0 instanceof TemplateReport && ((TemplateReport)f0).getMoveToPrecedingIndex()==-1)
                {
                    // was a marker, remove it:
                    int toioc = toParent.getIndexOfChild(to);
                    if (toioc>0)
                    {
                        // (May add multiple & have already been removed)
                        fromp.removeChild(toioc);
                    }
                }
            }
        }
        // Now do non-moves:
        for (int i=0;i<c;i++)
        {
            BindingFixerChange ch = (BindingFixerChange) mChanges.get(i);
            ch.applyNonMove();
        }
    }

    public boolean hasAnySelectedChanges() {
        for (int i=0;i<mChanges.size();i++) {
            BindingFixerChange c = getChange(i);
            if (c.canApply() && c.getDoApply()) {
                return true;
            }
        }
        return false;
    }

    private void addErrors(TemplateReport report) {
        // Add missing preceeding terms: (these don't don't towards recursively error free, so must check first)
        SmSequenceType[] mp = report.getMissingPrecedingTerms();
        for (int i=0;i<mp.length;i++) {
            if (report.getPrecedingMovedFrom(i)==null)
            { // if there's a move, then that will show up there.
                if (m_includeMissingOptionalTerms || mp[i].quantifier().getMinOccurs()>0)
                {
                    BindingFixerChange c = new MissingOutputBindingFixerChange(report,mp[i],true,i);
                    addToList(c);
                }
            }
        }

        // Don't traverse down if we don't need to:
        if (report.isRecursivelyErrorFree()) {
            return; // skip traversing.
        }

        if (m_includeFormulaErrors)
        {
            ErrorMessageList eml = report.getFormulaErrors();
            ErrorMessage[] em = eml.getMessages();
            for (int i=0;i<em.length;i++) {
                ErrorMessage m = em[i];
                BindingFixerChange c = new FormulaErrorBindingFixerChange(report,m);
                addToList(c);
            }
        }
        // Recurse:
        if (!(report.getBinding() instanceof MarkerBinding))
        {
            // only traverse inside non marker-comments:
            int cc = report.getChildCount();
            for (int i=0;i<cc;i++) {
                addErrors(report.getChild(i));
            }
        }
        if (m_includeOutputErrors)
        {
            if (report.getOutputContextError()!=null) {
                // garbage element (or maybe move):
                addToList(new UnexpectedOutputBindingFixerChange(report));
            }
        }
        if (m_includeValueRequired)
        {
            if (report.getIsMissing())
            {
                if (report.getMovedFrom()==null)
                {
                    // If there wasn't a move from, mark it as an error, otherwise don't issue it (redundant error)
                    boolean isStructure = report.getComputedType()== null ? false : !SmSequenceTypeSupport.hasTypedValue(report.getComputedType(),true);
                    boolean autoCreate = m_autoCreateMissingRequired && isStructure && report.getComputedType().prime().getParticleTerm() instanceof SmElement;
                    addToList(new ValueRequiredBindingFixerChange(report,isStructure,autoCreate));
                }
            }
        }
        if (m_includeExtendedErrors)
        {
            if (report.hasExtendedErrors())
            {
                TemplateReportExtendedError[] ee = report.getExtendedErrors();
                for (int i=0;i<ee.length;i++)
                {
                    addToList(new ExtendedErrorBindingFixerChange(report,ee[i]));
                }
            }
        }
        if (m_includeStructural)
        {
            if (report.getStructuralError()!=null)
            {
                addToList(new StructuralBindingFixerChange(report));
            }
        }

        // Add missing following terms:
        SmSequenceType[] me = report.getMissingEndingTerms();
        for (int i=0;i<me.length;i++) {
            SmSequenceType met = me[i];
            if (m_includeMissingOptionalTerms || met.quantifier().getMinOccurs()>0)
            {
                BindingFixerChange c = new MissingOutputBindingFixerChange(report,met,false,-1); // -1 n/a for ending terms.
                addToList(c);
            }
        }
        if (mIncludeOtherwise) {
            // If we had any, add an 'otherwise' maker:
            if (report.getMissingFollowingTerms().length>0)
            {
                boolean addIt;
                if (m_includeMissingOptionalTerms)
                {
                    // If we're adding all, including optionals, then we don't need to check, just add it:
                    addIt = true;
                }
                else
                {
                    // See if there were any non-optional terms, if so, then add it, otherwise not.
                    SmSequenceType[] terms = report.getMissingFollowingTerms();
                    boolean foundNonOptional = false;
                    for (int i=0;i<terms.length;i++)
                    {
                        foundNonOptional = terms[i].quantifier().getMinOccurs()>0;
                        if (foundNonOptional)
                        {
                            break;
                        }
                    }
                    addIt = foundNonOptional;
                }
                if (addIt)
                {
                    BindingFixerChange c = new AddOtherwiseBindingFixerChange(report);
                    addToList(c);
                }
            }
        }
    }

    /**
     * For diagnostic/debugging purposes only.
     */
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("changes {\n");
        for (int i=0;i<mChanges.size();i++)
        {
            sb.append("   " + mChanges.get(i).toString() + "\n");
        }
        sb.append("}");
        return sb.toString();
    }
}
