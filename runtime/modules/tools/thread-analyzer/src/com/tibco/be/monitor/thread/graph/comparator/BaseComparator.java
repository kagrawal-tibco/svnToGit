/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tibco.be.monitor.thread.graph.comparator;

import java.util.Comparator;

/**
 *
 * @author ksubrama
 * Author: Karthikeyan Subramanian / Date: Nov 24, 2009 / Time: 4:20:58 PM
 */
public abstract class BaseComparator<T> implements Comparator<T> {

    protected static Comparator ASC, DESC;

    @SuppressWarnings("unchecked")
    public static final <T> Comparator<T> getComparator(boolean isAsc) {
        if(isAsc) {
            return (Comparator<T>)ASC;
        } else {
            return (Comparator<T>)DESC;
        }
    }

    @Override
    public abstract int compare(T node_1, T node_2);

}
