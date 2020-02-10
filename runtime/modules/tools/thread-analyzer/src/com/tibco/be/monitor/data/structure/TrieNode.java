/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tibco.be.monitor.data.structure;

import java.util.Set;

/**
 *
 * Author: Karthikeyan Subramanian / Date: Dec 4, 2009 / Time: 12:16:12 PM
 */
public interface TrieNode {
    
    void setGroup(String group);
    
    String getGroup();

    void setName(String name);

    String getName();
    
    void addValue(String value);

    void setValues(Set<String> value);

    Set<String> getValues();
    
    void addChild(TrieNode child);

    void setChildren(Set<TrieNode> children);
    
    Set<TrieNode> getChildren();

    void setParent(TrieNode parent);

    TrieNode getParent();

    String getPath(String delimiter);

    int getDepth();

    TrieNode getBestMatch(String name);

    String getCommonPath(TrieNode node);
}
