/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tibco.be.monitor.thread.graph.comparator;

import com.tibco.be.monitor.thread.graph.Node;
import com.tibco.be.monitor.thread.graph.Resource;
import com.tibco.be.monitor.thread.graph.Thread;

/**
 *
 * @author ksubrama
 * Author: Karthikeyan Subramanian / Date: Nov 16, 2009 / Time: 5:00:08 PM
 */
public class DependencyComparator extends BaseComparator<Node> {
    private final boolean isAscending;

    static {
        ASC = new DependencyComparator(true);
        DESC = new DependencyComparator(true);
    }

    private DependencyComparator(boolean isAsc) {
        isAscending = isAsc;
    }
    
    @Override
    public int compare(Node node_1, Node node_2) {
        if(isAscending) {
            return getBlockedTime(node_1) - getBlockedTime(node_2);
        } else {
            return getBlockedTime(node_2) - getBlockedTime(node_1);
        }
    }

    private int getBlockedTime(Node node) {
        if(node instanceof Thread) {
            Thread thread = (Thread)node;
            int i = 0;
            for(Resource res : thread.getResourcesUsed()) {
                i += res.getWaiters().size();
            }
            return i;
        } else if(node instanceof Resource) {
            Resource res = (Resource)node;
            return res.getWaiters().size();
        }
        return 0;
    }

}
