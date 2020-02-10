package com.tibco.cep.releases.impl;

import com.tibco.cep.releases.InstalledFilesChanges;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: 7/8/11
 * Time: 1:32 PM
 */
public class InstalledFileChangesImpl
        extends TreeMap<String, SortedSet<String>>
        implements InstalledFilesChanges {


    public InstalledFileChangesImpl() {
        super();
    }


    public InstalledFileChangesImpl(
            Comparator<? super String> comparator) {

        super(comparator);
    }


    public InstalledFileChangesImpl(
            Map<? extends String, ? extends SortedSet<String>> m) {

        super(m);
    }


    public InstalledFileChangesImpl(
            SortedMap<String, ? extends SortedSet<String>> m) {

        super(m);
    }


    @Override
    public Set<String> getAssemblyNames() {

        return this.keySet();
    }


    @Override
    public SortedSet<String> getFilePaths(
            String assemblyName) {

        return this.get(assemblyName);
    }


}
