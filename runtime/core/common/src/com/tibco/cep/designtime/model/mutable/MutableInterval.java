package com.tibco.cep.designtime.model.mutable;

import com.tibco.cep.designtime.model.Interval;
import com.tibco.cep.designtime.model.MutationObservableContainer;


/**
 * Represents an Interval of double's.
 */
public interface MutableInterval extends Interval, MutationObservableContainer {


    /**
     * Swaps the bounds if the lower bound is greater than the upper bound.
     */
    void ensureBoundsOrder();


    void setLowerBound(double value);


    void setLowerBoundExcluded(boolean excluded);


    void setUpperBound(double value);


    void setUpperBoundExcluded(boolean excluded);


}
