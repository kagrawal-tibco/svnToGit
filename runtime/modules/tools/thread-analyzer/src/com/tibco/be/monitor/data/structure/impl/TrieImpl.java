/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.monitor.data.structure.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.tibco.be.monitor.data.structure.Trie;
import com.tibco.be.monitor.data.structure.TrieNode;

/**
 *
 * Author: Karthikeyan Subramanian / Date: Dec 4, 2009 / Time: 12:19:55 PM
 */
public class TrieImpl implements Trie {

    protected final TrieNode root;
    protected final Map<String, TrieNode> nodeMap = new HashMap<String, TrieNode>();

    TrieImpl() {
        root = new TrieNodeImpl();
        // Root node doesn't have a name or group associated with it.
        root.setName(TrieNodeImpl.ROOT);
        root.setGroup(TrieNodeImpl.ROOT);
    }

    @Override
    public void assignGroup(String className, String group) {
        getOrAddNode(className, group);
    }

    private TrieNode getOrAddNode(String className, String group) {
        TrieNode classNode = getTrieNode(className);
        if (classNode != null) {
            // Got the trie node from the map.
            // set the group and return class node.
            if (group != null) {
                classNode.setGroup(group);
            }
            return classNode;
        }
        // Unable to find the class node in the map.
        // find the best possible match for class name.
        classNode = findApproxMatch(className);

        String path = classNode.getPath(".");
        if (path.length() != 0 && (className.length() > (path.length() + 1))) {
            className = className.substring(path.length() + 1);
        }
        return addNodesRecursively(classNode, className, group);
    }

    @Override
    public String getGroup(String className) {
        TrieNode tNode = nodeMap.get(className);
        if (tNode != null) {
            return tNode.getGroup();
        }
        // find best possible match.
        tNode = findApproxMatch(className);
        if (tNode != null) {
            return tNode.getGroup();
        }
        tNode = root.getBestMatch(className);
        return tNode.getGroup();
    }

    private TrieNode createTrieNode(TrieNode parent, String group,
            String className) {
        TrieNode child = new TrieNodeImpl();
        child.setGroup(group);
        child.setName(className);
        // Set parent-child relationship
        child.setParent(parent);
        parent.addChild(child);
        // Add the node to map.
        nodeMap.put(child.getPath("."), child);
        return child;
    }

    @Override
    public void addEntries(Set<String> classNames) {
        for (String className : classNames) {
            addEntry(className);
        }
    }

    @Override
    public void addEntries(Set<String> classNames, String group) {
        for (String className : classNames) {
            addEntry(className, group);
        }
    }

    @Override
    public void addEntry(String className) {
        getOrAddNode(className, null);
    }

    @Override
    public void addEntry(String className, String group) {
        getOrAddNode(className, group);
    }

    private TrieNode findApproxMatch(String className) {
        while (true) {
            if (className.trim().length() == 0) {
                break;
            }
            TrieNode node = nodeMap.get(className);
            if (node != null) {
                return node;
            } else {
                int lastIndex = className.lastIndexOf(".");
                if (lastIndex != -1) {
                    className = className.substring(0, lastIndex);
                } else if (className.length() != 0) {
                    node = nodeMap.get(className);
                    if (node != null) {
                        return node;
                    }
                    break;
                } else {
                    break;
                }
            }
        }
        return root;
    }

    private TrieNode addNodesRecursively(TrieNode baseNode,
            String className, String group) {
        String[] parts = className.split("\\.");
        TrieNode node = baseNode;
        for (String part : parts) {
            node = createTrieNode(node, null, part);
        }
        node.setGroup(group);
        return node;
    }

    private TrieNode getTrieNode(String className) {
        return nodeMap.get(className);
    }

    @Override
    public TrieNode getRootNode() {
        return root;
    }
}
