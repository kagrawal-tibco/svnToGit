package com.tibco.rta.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public final class DiGraph<T> implements Iterable<T> {

    private final Map<T, Set<T>> graph = new LinkedHashMap<T, Set<T>>();

    public boolean addNode(T node) {
        if (graph.containsKey(node)) {
            return false;
        }

        graph.put(node, new HashSet<T>());
        return true;
    }

    public void addEdge(T start, T dest) {
        if (!graph.containsKey(start) || !graph.containsKey(dest)) {
            throw new NoSuchElementException("Start & destination  must be in the graph.");
        }

        graph.get(start).add(dest);
    }

    public void removeEdge(T start, T dest) {
        if (!graph.containsKey(start) || !graph.containsKey(dest)) {
            throw new NoSuchElementException("Start & destination  must be in the graph.");
        }

        graph.get(start).remove(dest);
    }

    public boolean edgeExists(T start, T end) {
        if (!graph.containsKey(start) || !graph.containsKey(end)) {
            throw new NoSuchElementException("Start & destination  must be in the graph.");
        }

        return graph.get(start).contains(end);
    }

    public Set<T> edgesFrom(T node) {
        Set<T> arcs = graph.get(node);
        if (arcs == null)
            throw new NoSuchElementException("Node does not exist.");

        return Collections.unmodifiableSet(arcs);
    }

    public Iterator<T> iterator() {
        return graph.keySet().iterator();
    }

    public int size() {
        return graph.size();
    }

    public boolean isEmpty() {
        return graph.isEmpty();
    }
}