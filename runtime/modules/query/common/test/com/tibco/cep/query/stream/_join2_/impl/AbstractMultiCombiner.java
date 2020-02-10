package com.tibco.cep.query.stream._join2_.impl;

import com.tibco.cep.query.stream._join2_.api.Combiner;
import com.tibco.cep.query.stream._join2_.api.Source;

/*
* Author: Ashwin Jayaprakash Date: Jun 1, 2009 Time: 8:26:12 PM
*/
public abstract class AbstractMultiCombiner<L, R> implements Combiner<L, R> {
    protected final boolean leftToRight;

    protected final Source[] joinSources;

    /**
     * @param leftToRight If <code>false</code>, then right-to-left.
     * @param joinSources
     */
    public AbstractMultiCombiner(boolean leftToRight, Source... joinSources) {
        this.leftToRight = leftToRight;
        this.joinSources = joinSources;
    }

    public Source[] getJoinSources() {
        return joinSources;
    }

    protected Object[] doCombine(Object[] left, Object[] right) {
        Object[] result = new Object[left.length + right.length];
        int i = 0;

        for (int j = 0; j < left.length; j++, i++) {
            result[i] = left[j];
        }

        for (int j = 0; j < right.length; j++, i++) {
            result[i] = right[j];
        }

        return result;
    }

    //---------------

    public static class LeftArrayRightSimpleCombiner
            extends AbstractMultiCombiner<Object[], Object> {
        protected Object[] holder;

        public LeftArrayRightSimpleCombiner(boolean leftToRight, Source... joinSources) {
            super(leftToRight, joinSources);

            this.holder = new Object[1];
        }

        public Object[] combine(Object[] lhs, Object rhs) {
            return theCombiner(lhs, rhs);
        }

        private Object[] theCombiner(Object[] lhs, Object rhs) {
            Object[] retVal = null;

            holder[0] = rhs;
            if (leftToRight) {
                retVal = doCombine(lhs, holder);
            }
            else {
                retVal = doCombine(holder, lhs);
            }

            return retVal;
        }
    }

    public static class LeftSimpleRightArrayCombiner
            extends AbstractMultiCombiner<Object, Object[]> {
        protected Object[] holder;

        public LeftSimpleRightArrayCombiner(boolean leftToRight, Source... joinSources) {
            super(leftToRight, joinSources);

            this.holder = new Object[1];
        }

        public Object[] combine(Object lhs, Object[] rhs) {
            return theCombiner(lhs, rhs);
        }

        private Object[] theCombiner(Object lhs, Object[] rhs) {
            Object[] retVal = null;

            holder[0] = lhs;
            if (leftToRight) {
                retVal = doCombine(holder, rhs);
            }
            else {
                retVal = doCombine(rhs, holder);
            }

            return retVal;
        }
    }

    public static class LeftArrayRightArrayCombiner
            extends AbstractMultiCombiner<Object[], Object[]> {
        public LeftArrayRightArrayCombiner(boolean leftToRight, Source... joinSources) {
            super(leftToRight, joinSources);
        }

        public Object[] combine(Object[] lhs, Object[] rhs) {
            if (leftToRight) {
                return new Object[]{lhs, rhs};
            }

            return new Object[]{rhs, lhs};
        }
    }
}
