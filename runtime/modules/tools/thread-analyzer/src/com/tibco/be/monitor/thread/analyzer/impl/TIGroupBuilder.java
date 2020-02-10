/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tibco.be.monitor.thread.analyzer.impl;

import java.lang.management.ThreadInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tibco.be.monitor.thread.analyzer.ThreadInfoGroup;
import com.tibco.be.monitor.thread.report.Reporter;

/**
 *
 * @author ksubrama
 * Author: Karthikeyan Subramanian / Date: Nov 16, 2009 / Time: 5:23:38 PM
 */
class TIGroupBuilder {

    private TIGroupBuilder() {
    }

    public static final List<ThreadInfoGroup> buildGroups(ThreadInfo[] dump) {
        Map<String, Set<ThreadInfo>> groups =
                new HashMap<String, Set<ThreadInfo>>();
        for(ThreadInfo tInfo : dump) {
            String key = Reporter.getStackTraceString(
                    tInfo.getStackTrace());
            Set<ThreadInfo> tInfoSet = groups.get(key);
            if(tInfoSet == null) {
                tInfoSet = new HashSet<ThreadInfo>();
                groups.put(key, tInfoSet);
            }
            tInfoSet.add(tInfo);
        }
        List<ThreadInfoGroup> result = new ArrayList<ThreadInfoGroup>(
                groups.size());
        for(Set<ThreadInfo> grouping : groups.values()) {
            result.add(ThreadInfoGroupImpl.getThreadInfoGroup(grouping));
        }
        return result;
    }
}
