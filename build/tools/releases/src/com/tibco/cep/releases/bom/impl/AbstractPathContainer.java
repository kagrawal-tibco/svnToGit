package com.tibco.cep.releases.bom.impl;

import com.tibco.cep.releases.bom.PathContainer;
import com.tibco.cep.util.UnmodifiableSortedMap;

import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * User: nprade
 * Date: 6/28/11
 * Time: 6:22 PM
 */
abstract class AbstractPathContainer<T extends PathContainer>
        extends UnmodifiableSortedMap<String, T>
        implements PathContainer<T> {


    public AbstractPathContainer(
            Map<String, T> nameToFileSet) {

        super(nameToFileSet);
    }


    public AbstractPathContainer(
            SortedMap<String, T> nameToFileSet) {

        super(nameToFileSet);
    }


    @Override
    public boolean containsPath(
            String path) {

        for (final T t : this.values()) {
            if (t.containsPath(path)) {
                return true;
            }
        }

        return false;
    }


    @Override
    public SortedSet<T> getByBuildPath(
            String path) {

        final TreeSet<T> result = new TreeSet<T>();

        for (final T t : this.values()) {
            if (t.getByBuildPath(path).size() > 0) {
                result.add(t);
            }
        }

        return result;
    }


    @Override
    public SortedSet<T> getByInstalledGaPath(
            String path) {

        final TreeSet<T> result = new TreeSet<T>();

        for (final T t : this.values()) {
            if (t.getByInstalledGaPath(path).size() > 0) {
                result.add(t);
            }
        }

        return result;
    }


    @Override
    public SortedSet<T> getByInstalledHfPath(
            String path) {

        final TreeSet<T> result = new TreeSet<T>();

        for (final T t : this.values()) {
            if (t.getByInstalledHfPath(path).size() > 0) {
                result.add(t);
            }
        }

        return result;
    }


    @Override
    public SortedSet<T> getBySourcePath(
            String path) {

        final TreeSet<T> result = new TreeSet<T>();

        for (final T t : this.values()) {
            if (t.getBySourcePath(path).size() > 0) {
                result.add(t);
            }
        }

        return result;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (String k : this.keySet()) {
            if (first) {
                first = false;
            } else {
                sb.append(",");
            }
            sb.append(k);
        }
        return sb.toString();
    }


}
