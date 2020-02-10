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
import java.util.Map;
import java.util.Set;

import com.tangosol.util.Filter;
import com.tangosol.util.MapIndex;
import com.tangosol.util.ValueExtractor;
import com.tangosol.util.filter.IndexAwareFilter;

/*
* Author: Ashwin Jayaprakash Date: Dec 4, 2008 Time: 3:35:19 PM
*/

public class EqualsIndexAwareFilter implements IndexAwareFilter, Externalizable {
    protected Comparable value;

    protected ValueExtractor valueExtractor;

    public EqualsIndexAwareFilter() {
    }

    /**
     * @param value
     * @param valueExtractor The same extractor that was used to create the Index.
     */
    public EqualsIndexAwareFilter(Comparable value, ValueExtractor valueExtractor) {
        this.value = value;
        this.valueExtractor = valueExtractor;
    }

    public Comparable getValue() {
        return value;
    }

    public void setValue(Comparable value) {
        this.value = value;
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

        return Math.max(index.getIndexContents().size() / 4, 1);
    }

    public Filter applyIndex(Map mapIndexes, Set keysToBeFiltered) {
        MapIndex index = (MapIndex) mapIndexes.get(getValueExtractor());

        validate(keysToBeFiltered, index);

        //-------------

        Map indexContents = index.getIndexContents();

        keysToBeFiltered.clear();

        Set keys = (Set) indexContents.get(getValue());
        if (keys == null || keys.isEmpty()) {
            return null;
        }

        keysToBeFiltered.addAll(keys);

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
    }

    public boolean evaluateEntry(Map.Entry entry) {
        return false;
    }

    public boolean evaluate(Object o) {
        return false;
    }

    //-------------

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(getValue());
        out.writeObject(getValueExtractor());
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        setValue((Comparable) in.readObject());
        setValueExtractor((ValueExtractor) in.readObject());
    }
}