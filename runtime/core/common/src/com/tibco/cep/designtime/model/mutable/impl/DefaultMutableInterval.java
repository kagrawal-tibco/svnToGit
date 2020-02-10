package com.tibco.cep.designtime.model.mutable.impl;


import com.tibco.cep.designtime.model.Interval;
import com.tibco.cep.designtime.model.MutationContext;
import com.tibco.cep.designtime.model.MutationObservable;
import com.tibco.cep.designtime.model.mutable.MutableInterval;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;


public class DefaultMutableInterval implements MutableInterval {


    protected static final ExpandedName XNAME_EXCLUDED = ExpandedName.makeName("excluded");
    protected static final ExpandedName XNAME_LOWER = ExpandedName.makeName("lower");
    protected static final ExpandedName XNAME_UPPER = ExpandedName.makeName("upper");


    private double lowerBound;
    private boolean lowerBoundExcluded;
    private double upperBound;
    private boolean upperBoundExcluded;
    private MutationObservable mutationObservable;


    public DefaultMutableInterval() {
        this(0, true, 0, true);
    }


    public DefaultMutableInterval(double lowerBound, double upperBound) {
        this(lowerBound, false, upperBound, false);
    }


    public DefaultMutableInterval(double lowerBound, boolean lowerBoundExcluded,
                                  double upperBound, boolean upperBoundExcluded) {
        this.lowerBound = lowerBound;
        this.lowerBoundExcluded = lowerBoundExcluded;
        this.upperBound = upperBound;
        this.upperBoundExcluded = upperBoundExcluded;
        this.ensureBoundsOrder();
        this.mutationObservable = new DefaultMutationObservable(this);
    }


    public boolean contains(double value) {
        if (this.isEmpty()) {
            return false;
        }
        this.ensureBoundsOrder();
        return ((this.lowerBound < value) || ((this.lowerBound == value) && !this.lowerBoundExcluded))
                && ((this.upperBound > value) || ((this.upperBound == value) && !this.upperBoundExcluded));
    }


    public boolean contains(Interval interval) {
        return interval.isEmpty() || interval.equals(this.getIntersection(interval));
    }


    public static Interval createDefaultInterval(XiNode node) {
        final double lower = XiChild.getDouble(node, XNAME_LOWER, 0);
        final XiNode lowerNode = XiChild.getChild(node, XNAME_LOWER);
        final boolean lowerExcluded = Boolean.valueOf(lowerNode.getAttributeStringValue(XNAME_EXCLUDED)).booleanValue();

        final double upper = XiChild.getDouble(node, XNAME_UPPER, 0);
        final XiNode upperNode = XiChild.getChild(node, XNAME_UPPER);
        final boolean upperExcluded = Boolean.valueOf(upperNode.getAttributeStringValue(XNAME_EXCLUDED)).booleanValue();

        final MutableInterval interval = new DefaultMutableInterval(lower, lowerExcluded, upper, upperExcluded);
        interval.ensureBoundsOrder();
        return interval;
    }


    public void ensureBoundsOrder() {
        if (this.lowerBound > this.upperBound) {
            // lower > upper => swap
            final double oldLowerBound = this.lowerBound;
            final boolean oldLowerBoundExcluded = this.lowerBoundExcluded;
            this.lowerBound = this.upperBound;
            this.lowerBoundExcluded = this.upperBoundExcluded;
            this.upperBound = oldLowerBound;
            this.upperBoundExcluded = oldLowerBoundExcluded;
        }
    }


    public boolean equals(Interval interval) {
        if (null == interval) {
            return false;
        }
        if (this.isEmpty()) {
            return interval.isEmpty();
        }
        this.ensureBoundsOrder();
        if (interval instanceof MutableInterval) {
            ((MutableInterval) interval).ensureBoundsOrder();
        }
        return (this.lowerBound == interval.getLowerBound())
                && (this.lowerBoundExcluded == interval.getLowerBoundExcluded())
                && (this.upperBound == interval.getUpperBound())
                && (this.upperBoundExcluded == interval.getUpperBoundExcluded());
    }


    public Interval getIntersection(Interval interval) {
        this.ensureBoundsOrder();
        if (interval instanceof MutableInterval) {
            ((MutableInterval) interval).ensureBoundsOrder();
        }

        double lower = interval.getLowerBound();
        boolean lowerExcluded = interval.getLowerBoundExcluded();
        double upper = interval.getUpperBound();
        boolean upperExcluded = interval.getUpperBoundExcluded();

        // Finds lower bound
        if (this.lowerBound > lower) {
            lower = this.lowerBound;
            lowerExcluded = this.lowerBoundExcluded;
        } else if (this.lowerBound == lower) {
            lowerExcluded = lowerExcluded || this.lowerBoundExcluded;
        }

        // Finds upper bound
        if (this.upperBound < upper) {
            upper = this.upperBound;
            upperExcluded = this.upperBoundExcluded;
        } else if (this.upperBound == upper) {
            upperExcluded = upperExcluded || this.upperBoundExcluded;
        }

        // lower > upper means the intersection is empty.
        if (lower > upper) {
            // lower > upper means the intersection is empty => must collapse bounds.
            lower = upper;
            lowerExcluded = true;
            upperExcluded = true;
        } else if (lower == upper) {
            lowerExcluded = lowerExcluded || upperExcluded;
            upperExcluded = lowerExcluded;
        }

        return new DefaultMutableInterval(lower, lowerExcluded, upper, upperExcluded);
    }


    public double getLowerBound() {
        this.ensureBoundsOrder();
        return this.lowerBound;
    }


    public boolean getLowerBoundExcluded() {
        this.ensureBoundsOrder();
        return this.lowerBoundExcluded;
    }


    public MutationObservable getMutationObservable() {
        return this.mutationObservable;
    }


    public double getUpperBound() {
        this.ensureBoundsOrder();
        return this.upperBound;
    }


    public boolean getUpperBoundExcluded() {
        this.ensureBoundsOrder();
        return this.upperBoundExcluded;
    }


    public boolean intersects(Interval interval) {
        return ! this.getIntersection(interval).isEmpty();
    }


    public boolean isEmpty() {
        return (this.upperBoundExcluded || this.lowerBoundExcluded)
                && (this.upperBound == this.lowerBound);
    }


    public void setLowerBound(double value) {
        if (this.lowerBound != value) {
            this.lowerBound = value;
            this.ensureBoundsOrder();
            this.mutationObservable.changeAndNotify(new DefaultMutationContext(MutationContext.MODIFY, this));
        }
    }


    public void setLowerBoundExcluded(boolean excluded) {
        if (this.lowerBoundExcluded != excluded) {
            this.lowerBoundExcluded = excluded;
            this.mutationObservable.changeAndNotify(new DefaultMutationContext(MutationContext.MODIFY, this));
        }
    }


    public void setUpperBound(double value) {
        if (this.upperBound != value) {
            this.upperBound = value;
            this.ensureBoundsOrder();
            this.mutationObservable.changeAndNotify(new DefaultMutationContext(MutationContext.MODIFY, this));
        }
    }


    public void setUpperBoundExcluded(boolean excluded) {
        if (this.upperBoundExcluded != excluded) {
            this.upperBoundExcluded = excluded;
            this.mutationObservable.changeAndNotify(new DefaultMutationContext(MutationContext.MODIFY, this));
        }
    }


    public String toString() {
        this.ensureBoundsOrder();
        return (this.lowerBoundExcluded ? "(" : "[")
                + ((this.lowerBound == (int) this.lowerBound) ? "" + (int) this.lowerBound : "" + this.lowerBound)
                + ".."
                + ((this.upperBound == (int) this.upperBound) ? "" + (int) this.upperBound : "" + this.upperBound)
                + (this.upperBoundExcluded ? ")" : "]");
    }


    public XiNode toXiNode(XiFactory factory, ExpandedName name) {
        this.ensureBoundsOrder();
        final XiNode rootNode = factory.createElement(name);

        final XiNode lowerNode = rootNode.appendElement(XNAME_LOWER, this.lowerBound + "");
        lowerNode.setAttributeStringValue(XNAME_EXCLUDED, this.lowerBoundExcluded + "");

        final XiNode upperNode = rootNode.appendElement(XNAME_UPPER, this.upperBound + "");
        upperNode.setAttributeStringValue(XNAME_EXCLUDED, this.upperBoundExcluded + "");

        return rootNode;
    }


}
