/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tibco.be.monitor.data.structure;

import java.util.Set;

/**
 *
 * Author: Karthikeyan Subramanian / Date: Dec 4, 2009 / Time: 12:15:48 PM
 */
public interface Trie {

    void addEntries(Set<String> classNames);
    
    void addEntries(Set<String> classNames, String group);

    void addEntry(String className);

    void addEntry(String className, String group);

    void assignGroup(String className, String group);

    String getGroup(String className);

    TrieNode getRootNode();
}
