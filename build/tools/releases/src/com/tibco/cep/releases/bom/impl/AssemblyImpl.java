package com.tibco.cep.releases.bom.impl;

import com.tibco.cep.releases.bom.Assembly;
import com.tibco.cep.releases.bom.FileSet;

import java.util.Map;
import java.util.SortedMap;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: 6/28/11
 * Time: 5:51 PM
 */
public class AssemblyImpl
        extends FileSetsImpl
        implements Assembly {

    private final String name;
    private final boolean portSpecific;


    public AssemblyImpl(
            String name,
            Map<String, FileSet> nameToFileSet,
            boolean portSpecific) {

        super(nameToFileSet);
        this.name = name;
        this.portSpecific = portSpecific;
    }


    public AssemblyImpl(
            String name,
            SortedMap<String, FileSet> nameToFileSet,
            boolean portSpecific) {

        super(nameToFileSet);
        this.name = name;
        this.portSpecific = portSpecific;
    }


    @Override
    public int compareTo(
            Assembly o) {

        if (null == o) {
            return 1;
        } else {
            return this.name.compareTo(o.getName());
        }
    }


    @Override
    public String getName() {
        return this.name;
    }


    @Override
    public boolean isPortSpecific() {
        return this.portSpecific;
    }


    @Override
    public String toString() {
        return this.getName() + "@" + this.hashCode();
    }


}
