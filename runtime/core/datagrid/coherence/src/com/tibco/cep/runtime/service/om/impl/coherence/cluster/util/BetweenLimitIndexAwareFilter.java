/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 11/8/2010
 */

package com.tibco.cep.runtime.service.om.impl.coherence.cluster.util;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import com.tangosol.util.Filter;
import com.tangosol.util.MapIndex;
import com.tangosol.util.ValueExtractor;
import com.tangosol.util.filter.IndexAwareFilter;

/*
* Author: Ashwin Jayaprakash Date: Dec 4, 2008 Time: 3:35:19 PM
*/

public class BetweenLimitIndexAwareFilter implements IndexAwareFilter, Externalizable {
    /**
     * Start - inclusive.
     */
    protected Comparable startValue;

    /**
     * End - exclusive.
     */
    protected Comparable endValue;

    protected int maxResults;

    protected ValueExtractor valueExtractor;

    public BetweenLimitIndexAwareFilter() {
    }

    /**
     * @param startValue     Inclusive.
     * @param endValue       Exclusive or can be same as the start-value.
     * @param maxResults
     * @param valueExtractor The same extractor that was used to create the Index. The Index must be
     *                       <code>ordered</code>.
     */
    public BetweenLimitIndexAwareFilter(Comparable startValue, Comparable endValue, int maxResults,
                                        ValueExtractor valueExtractor) {
        this.startValue = startValue;
        this.endValue = endValue;
        this.maxResults = maxResults;
        this.valueExtractor = valueExtractor;
    }

    public Comparable getStartValue() {
        return startValue;
    }

    public void setStartValue(Comparable startValue) {
        this.startValue = startValue;
    }

    public Comparable getEndValue() {
        return endValue;
    }

    public void setEndValue(Comparable endValue) {
        this.endValue = endValue;
    }

    public int getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    public ValueExtractor getValueExtractor() {
        return valueExtractor;
    }

    public void setValueExtractor(ValueExtractor valueExtractor) {
        this.valueExtractor = valueExtractor;
    }

    public int calculateEffectiveness(Map mapIndexes, Set keysToBeFiltered) {
        MapIndex index = (MapIndex) mapIndexes.get(getValueExtractor());

        if (index == null) {
            return keysToBeFiltered.size() * 1000;
        }

        //Ordered.
        return Math.max(index.getIndexContents().size() / 4, 1);
    }

    public Filter applyIndex(Map mapIndexes, Set keysToBeFiltered) {
        MapIndex index = (MapIndex) mapIndexes.get(getValueExtractor());

        validate(keysToBeFiltered, index);

        //-------------

        SortedMap indexContents = (SortedMap) index.getIndexContents();

        SortedMap keysGreaterOrEqualStart = indexContents.tailMap(getStartValue());
        if (keysGreaterOrEqualStart == null || keysGreaterOrEqualStart.isEmpty()) {
            keysToBeFiltered.clear();
            return null;
        }

        SortedMap keysLessEnd = getEndValue().equals(getStartValue()) ?
                keysGreaterOrEqualStart : keysGreaterOrEqualStart.headMap(getEndValue());

        if (keysLessEnd == null || keysLessEnd.isEmpty()) {
        	keysToBeFiltered.clear();
            return null;
        }

        //------------

        //if the alternative for loop would be bigger than the argument to retainAll, call retainAll
        if(keysToBeFiltered.size() > keysLessEnd.size() && (keysToBeFiltered.size() <= maxResults || keysLessEnd.size() <= maxResults)) {
        	keysToBeFiltered.retainAll(keysLessEnd.keySet());
        } else {
		    int hitCount = 0;
		    for(Iterator iter = keysToBeFiltered.iterator(); iter.hasNext();) {
		    	Object key = iter.next();
		    	//keys and values are the same?
		    	if(hitCount <= maxResults && keysLessEnd.containsKey(key)) {
		    		hitCount++;
		    	} else {
		    		iter.remove();
		    	}
		    }
        }
        return null;
    }
    
    /**
     * @param keysToBeFiltered
     * @param index
     * @throws RuntimeException
     */
    private void validate(Set keysToBeFiltered, MapIndex index) {
        if (index == null) {
            keysToBeFiltered.clear();

            throw new RuntimeException("Index not available for the "
                    + ValueExtractor.class.getSimpleName() + " [" + getValueExtractor() + "]");
        }

        if (index.isOrdered() == false) {
            throw new RuntimeException("Index is not ordered for the "
                    + ValueExtractor.class.getSimpleName() + " [" + getValueExtractor() + "]");
        }
    }

    public boolean evaluateEntry(Map.Entry entry) {
        return false;
    }

    public boolean evaluate(Object o) {
        return false;
    }

    //-------------

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(getStartValue());
        out.writeObject(getEndValue());
        out.writeInt(getMaxResults());
        out.writeObject(getValueExtractor());
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        setStartValue((Comparable) in.readObject());
        setEndValue((Comparable) in.readObject());
        setMaxResults(in.readInt());
        setValueExtractor((ValueExtractor) in.readObject());
    }
}
