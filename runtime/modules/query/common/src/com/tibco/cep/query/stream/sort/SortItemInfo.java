package com.tibco.cep.query.stream.sort;

import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;


/*
 * Author: Ashwin Jayaprakash Date: Oct 4, 2007 Time: 12:57:09 PM
 */

public class SortItemInfo {
    protected int firstX;

    protected int firstXOffset;

    protected final boolean ascending;

    private boolean initFirstXBeforeUse;

    protected ExpressionEvaluator firstXEval;

    protected ExpressionEvaluator firstXOffsetEval;

    /**
     * @param ascending
     * @param firstX
     * @param firstXOffset
     */
    public SortItemInfo(boolean ascending, int firstX, int firstXOffset) {
        this.ascending = ascending;
        this.firstX = firstX;
        this.firstXOffset = firstXOffset;

        //Assume that we don't have to.
        this.initFirstXBeforeUse = false;
    }

    public SortItemInfo(boolean ascending, ExpressionEvaluator firstX,
                        ExpressionEvaluator firstXOffset) {
        this(ascending, Integer.MAX_VALUE, 0);

        this.firstXEval = firstX;
        this.firstXOffsetEval = firstXOffset;
        this.initFirstXBeforeUse = true;
    }

    /**
     * @param ascending
     */
    public SortItemInfo(boolean ascending) {
        this(ascending, Integer.MAX_VALUE, 0);
    }

    public boolean isAscending() {
        return ascending;
    }

    private void initIfRequired(Context context) {
        if (initFirstXBeforeUse) {
            firstX = firstXEval
                    .evaluateInteger(context.getGlobalContext(), context.getQueryContext(), null);

            firstXOffset = firstXOffsetEval
                    .evaluateInteger(context.getGlobalContext(), context.getQueryContext(), null);

            initFirstXBeforeUse = false;
        }
    }

    public int getFirstX(Context context) {
        initIfRequired(context);

        return firstX;
    }

    public int getFirstXOffset(Context context) {
        initIfRequired(context);

        return firstXOffset;
    }
}
