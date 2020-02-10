package com.tibco.cep.releases;

import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: 7/8/11
 * Time: 1:57 PM
 */
public interface InstalledFilesChanges
        extends SortedMap<String, SortedSet<String>> {

    Set<String> getAssemblyNames();

    SortedSet<String> getFilePaths(String assemblyName);

}
