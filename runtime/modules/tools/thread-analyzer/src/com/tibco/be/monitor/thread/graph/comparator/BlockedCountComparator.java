/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tibco.be.monitor.thread.graph.comparator;

import com.tibco.be.monitor.thread.graph.Thread;

/**
 *
 * @author ksubrama
 * Author: Karthikeyan Subramanian / Date: Nov 16, 2009 / Time: 5:00:08 PM
 */
public class BlockedCountComparator extends BaseComparator<Thread> {
    
    static {
        ASC = new BlockedCountComparator(true);
        DESC = new BlockedCountComparator(false);
    }
    private final boolean isAscending;
    private BlockedCountComparator(boolean isAsc) {
        isAscending = isAsc;
    }
    
    @Override
    public int compare(Thread node_1, Thread node_2) {
        if(isAscending) {
            return (int)(getBlockedTime(node_1) - getBlockedTime(node_2));
        } else {
            return (int)(getBlockedTime(node_2) - getBlockedTime(node_1));
        }
    }

    private long getBlockedTime(Thread node_1) {
        return node_1.getInfo().getBlockedCount() +
                node_1.getInfo().getWaitedCount();
    }

}
