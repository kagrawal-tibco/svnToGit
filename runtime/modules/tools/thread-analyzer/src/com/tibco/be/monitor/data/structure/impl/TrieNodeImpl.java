/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.monitor.data.structure.impl;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.tibco.be.monitor.data.structure.TrieNode;

/**
 *
 * Author: Karthikeyan Subramanian / Date: Dec 4, 2009 / Time: 12:20:58 PM
 */
public class TrieNodeImpl implements TrieNode, Comparable {

    static String ROOT = "uncategorized";
    static String PATH_DELIMITER = ".";
    protected String group, name;
    protected SortedSet<String> values = new TreeSet<String>();
    protected SortedSet<TrieNode> children = new TreeSet<TrieNode>();
    protected TrieNode parent;

    TrieNodeImpl() {
    }

    @Override
    public String getGroup() {
        return group;
    }

    @Override
    public Set<TrieNode> getChildren() {
        return children;
    }

    @Override
    public TrieNode getParent() {
        return parent;
    }

    @Override
    public void setGroup(String value) {
        this.group = value;
    }

    @Override
    public void addChild(TrieNode child) {
        this.children.add(child);
    }

    @Override
    public void setChildren(Set<TrieNode> children) {
        this.children.clear();
        this.children.addAll(children);
    }

    @Override
    public void setParent(TrieNode parent) {
        this.parent = parent;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void addValue(String value) {
        this.values.add(value);
    }

    @Override
    public void setValues(Set<String> values) {
        values.clear();
        this.values.addAll(values);
    }

    @Override
    public Set<String> getValues() {
        return this.values;
    }

    @Override
    public int getDepth() {
        if (parent == null) {
            return 0;
        }
        return parent.getDepth() + 1;
    }

    @Override
    public String getPath(String delimiter) {
        StringBuilder path = new StringBuilder();
        if (delimiter == null) {
            delimiter = PATH_DELIMITER;
        }
        TrieNode parentNode = this;
        int i = 0;
        while (parentNode != null) {
            if (!(parentNode.getName().equals(ROOT))) {
                if (i++ == 0) {
                    path.insert(0, parentNode.getName());
                } else {
                    path.insert(0, parentNode.getName() + delimiter);
                }
            }
            parentNode = parentNode.getParent();
        }
        return path.toString();
    }

    @Override
    public int compareTo(Object node) {
        TrieNode trNode = (TrieNode) node;
        return name.compareTo(trNode.getName());
    }

    @Override
    public TrieNode getBestMatch(String nodeName) {
        if (nodeName == null || nodeName.length() == 0) {
            return null;
        }
        String[] parts = nodeName.split("\\.");
        if (parts.length != 0) {
            String[] subClasses = parts[0].split("$");
            if (!this.name.equals(ROOT)) {
                if (subClasses[0].matches(this.name.replace("*", "[.]*"))) {
                    if (nodeName.length() <= parts[0].length() + 1) {
                        return this;
                    }
                    String childName = nodeName.substring(parts[0].length() + 1);
                    for (TrieNode child : children) {
                        TrieNode node = child.getBestMatch(childName);
                        if (node != null) {
                            return node;
                        }
                    }
                    return this;
                } else {
                    return null;
                }
            } else {
                for (TrieNode child : children) {
                    TrieNode node = child.getBestMatch(nodeName);
                    if (node != null) {
                        return node;
                    }
                }
                return this;
            }
        }
        return null;
    }

    @Override
    public String getCommonPath(TrieNode node) {
        String path = getPath(".");
        String nodePath = node.getPath(".");
        String[] parts_1 = path.split("\\.");
        String[] parts_2 = nodePath.split("\\.");
        StringBuilder str = new StringBuilder("");
        int size = -1;
        if (parts_1.length < parts_2.length) {
            size = parts_1.length;
        } else {
            size = parts_2.length;
        }
        for (int i = 0; i < size; i++) {
            if (parts_1[i].equals(parts_2[i])) {
                if (i != 0) {
                    str.append(".");
                }
                str.append(parts_1[i]);
            } else {
                break;
            }
        }
        return str.toString();
    }
}
