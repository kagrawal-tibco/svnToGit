/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.monitor.thread.util;

import java.util.Collections;
import java.util.Set;

/**
 *
 * @author ksubrama
 * Author: Karthikeyan Subramanian / Date: Nov 18, 2009 / Time: 4:02:52 PM
 */
public class ThreadAnalyzerUtil {

    private ThreadAnalyzerUtil() {
    }

    public static final <I> Set<I> getUnmodifiableSet(Set<I> set) {
        if (set == null || set.size() == 0) {
            return Collections.emptySet();
        }
        return Collections.<I>unmodifiableSet(set);
    }
}
