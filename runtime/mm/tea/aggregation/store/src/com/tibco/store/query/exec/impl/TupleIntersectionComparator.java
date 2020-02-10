package com.tibco.store.query.exec.impl;

import com.tibco.store.query.exec.IntersectionComparator;
import com.tibco.store.persistence.model.MemoryTuple;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 5/12/13
 * Time: 10:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class TupleIntersectionComparator implements IntersectionComparator<MemoryTuple> {

    private List<String> interestAttributes;

    public TupleIntersectionComparator() {
        this.interestAttributes = new ArrayList<String>();
    }

    @Override
    public void addInterestAttribute(String attribute) {
        interestAttributes.add(attribute);
    }

    @Override
    public boolean intersect(MemoryTuple entry1, MemoryTuple entry2) {
        boolean result = true;
        for (String interestAttribute : interestAttributes) {
            //compare values from entry1 with entry2
            Object entry1Val = entry1.getAttributeValue(interestAttribute);
            Object entry2Val = entry2.getAttributeValue(interestAttribute);
            if (entry1Val == null) {
                result = false;
            } else {
                if (!entry1Val.equals(entry2Val)) {
                    result = false;
                }
            }
        }
        return result;
    }
}
