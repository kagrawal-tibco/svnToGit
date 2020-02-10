package com.tibco.cep.query.stream.impl.rete.integ.filter.coherence;

import java.io.Serializable;

import com.tangosol.util.ValueExtractor;

/*
* This is a dummy implementation. 
* Replace this with real implementation for coherence and active spaces.
* 
* Author: Karthikeyan Subramanian / Date: Apr 7, 2010 / Time: 11:07:48 AM
*/
public class IndexInfo implements Serializable {
    
    private static final long serialVersionUID = 1L;
    protected boolean isOrdered = false;
    protected ValueExtractor extractor;
    protected double selectivity = 1.0;

    public IndexInfo(boolean ordered, ValueExtractor extractor) {
        isOrdered = ordered;
        this.extractor = extractor;
    }

    public ValueExtractor getExtractor() {
        return extractor;
    }

    public void setExtractor(ValueExtractor extractor) {
        this.extractor = extractor;
    }

    public boolean isOrdered() {
        return isOrdered;
    }

    public void setOrdered(boolean ordered) {
        isOrdered = ordered;
    }

    public void setSelectivity(double selectivity) {
        this.selectivity = selectivity;
    }

    public double getSelectivity() {
        return this.selectivity;
    }
}
