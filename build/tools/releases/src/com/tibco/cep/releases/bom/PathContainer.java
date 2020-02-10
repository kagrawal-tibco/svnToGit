package com.tibco.cep.releases.bom;

import java.util.SortedMap;
import java.util.SortedSet;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: 6/28/11
 * Time: 6:19 PM
 */
public interface PathContainer<T>
        extends SortedMap<String, T> {

    boolean containsPath(String path);

    SortedSet<T> getByBuildPath(String path);

    SortedSet<T> getByInstalledGaPath(String path);

    SortedSet<T> getByInstalledHfPath(String path);

    SortedSet<T> getBySourcePath(String path);

}
