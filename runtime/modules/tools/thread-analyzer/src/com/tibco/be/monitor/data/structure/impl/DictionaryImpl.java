/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tibco.be.monitor.data.structure.impl;

import java.util.List;
import java.util.Map;

import com.tibco.be.monitor.data.structure.Dictionary;
import com.tibco.be.monitor.data.structure.Trie;
import com.tibco.be.monitor.data.structure.TrieNode;
import com.tibco.be.monitor.service.spi.DictionaryService;
import com.tibco.be.monitor.thread.util.ServiceLoaderUtil;
/**
 *
 * Author: Karthikeyan Subramanian / Date: Dec 4, 2009 / Time: 2:02:19 PM
 */
public class DictionaryImpl implements Dictionary {
    
    private static final Dictionary INSTANCE = new DictionaryImpl();
    // Internal data structure used by dictionary
    private final Trie trie = new TrieImpl();

    public DictionaryImpl() {
        init();
    }

    public static final Dictionary getDictionary() {
        return INSTANCE;
    }

    @Override
    public void addEntry(String className, String group) {
        className = className.trim();
        trie.assignGroup(className, group);
    }

    @Override
    public void assignGroup(String group, List<String> classes) {
        for(String cName: classes) {
            trie.assignGroup(cName.trim(), group);
        }
    }

    @Override
    public String getGroup(String className) {
        return trie.getGroup(className);
    }

    @Override
    public void addEntry(String className) {
        trie.addEntry(className);
    }

    private void init() {
        List<DictionaryService> services = ServiceLoaderUtil.getDictionaryServices();
        for (DictionaryService service : services) {
            Map<String, List<String>> groupMap = service.getGroups();
            for (String grp : groupMap.keySet()) {
                List<String> classes = groupMap.get(grp);
                assignGroup(grp, classes);
            }
        }
    }

    @Override
    public TrieNode getRootNode() {
        return trie.getRootNode();
    }
}
