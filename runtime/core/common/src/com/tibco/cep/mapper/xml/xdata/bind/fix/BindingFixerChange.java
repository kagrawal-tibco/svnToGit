package com.tibco.cep.mapper.xml.xdata.bind.fix;

import java.util.ArrayList;

import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.bind.virt.MarkerBinding;

/**
 * An update to a binding (for fixing errors/warnings)
 */
public abstract class BindingFixerChange
{
    private final TemplateReport mReport;
    private String mMsg; // computed on demand.
    private final boolean mCanApply;
    private boolean mDoApply = true;

    public static final int CATEGORY_ERROR = 0;
    public static final int CATEGORY_WARNING = 1;
    public static final int CATEGORY_OPTIMIZATION = 2;

    /**
     * For an xpath error.
     */
    protected BindingFixerChange(TemplateReport report, boolean canApply) {
        mReport = report;
        mMsg = null;
        mCanApply = canApply;
    }

    /**
     * Indicates if this is a {@link #CATEGORY_ERROR} or {@link #CATEGORY_WARNING} or {@link #CATEGORY_OPTIMIZATION}.
     */
    public abstract int getCategory();

    public final String getMessage() {
        synchronized (this)
        {
            if (mMsg==null)
            {
                mMsg = computeMessage();
            }
            return mMsg;
        }
    }

    public final boolean getDoApply()
    {
        return mCanApply && mDoApply;
    }

    public final void setDoApply(boolean val)
    {
        mDoApply = val;
    }

    public final boolean canApply()
    {
        return mCanApply;
    }

    public final TemplateReport getTemplateReport()
    {
        return mReport;
    }

    public final void applyMove(ArrayList moveList)
    {
        if (!mCanApply || !mDoApply) {
            return;
        }
        performMove(moveList);
    }

    public final void applyNonMove()
    {
        if (!mCanApply || !mDoApply) {
            return;
        }
        performNonMove();
    }

    /**
     * Does the move-apply regardless of it it selected.
     * (If this is not a move, do nothing, work should instead be done in {@link #performNonMove}.
     * @param moveList The move list; moves are done in 2 stages.
     */
    public abstract void performMove(ArrayList moveList);
    public abstract void performNonMove();

    public abstract String computeMessage();

    static class MarkerAddition
    {
        public TemplateReport m_on;
        public int m_precedingIndex;
        public MarkerBinding m_marker;
    }
}
