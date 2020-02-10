package com.tibco.cep.releases.bom.impl;

import com.tibco.cep.releases.bom.Paths;
import com.tibco.cep.util.UnmodifiableSortedSet;

import java.util.Set;
import java.util.SortedSet;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: 6/28/11
 * Time: 4:23 PM
 */
public class PathsImpl
        extends UnmodifiableSortedSet<String>
        implements Paths {


    public PathsImpl(
            Set<String> set) {

        super(set);
    }


    public PathsImpl(
            SortedSet<String> set) {

        super(set);
    }


    @Override
    public boolean containsPath(
            String path) {

        for (final String prefix : this) {
            if (path.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }


}
