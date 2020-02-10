/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tibco.be.monitor.data.structure;

import java.util.List;

/**
 *
 * Author: Karthikeyan Subramanian / Date: Dec 4, 2009 / Time: 1:13:24 PM
 */
public interface Dictionary {

    /**
     * Add the entry to the dictionary
     * @param entry entry
     * @param group group to which the entry belongs
     */
    void addEntry(String entry, String group);

    /**
     * Add the class to the dictionary
     * @param entry entry
     */
    void addEntry(String entry);

    /**
     * Assign the group to the list of classes
     * @param group group name
     * @param entries list of entries
     */
    void assignGroup(String group, List<String> entries);

    /**
     * Return the group name for the given entry
     * @param entry entry
     * @return group name
     */
    String getGroup(String entry);

    TrieNode getRootNode();
}
