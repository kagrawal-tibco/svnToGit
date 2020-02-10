package com.tibco.cep.releases.bom.impl;

import com.tibco.cep.releases.bom.FileSet;
import com.tibco.cep.releases.bom.FileSets;
import com.tibco.cep.releases.bom.Paths;
import com.tibco.cep.util.UnmodifiableSortedMap;

import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: 6/28/11
 * Time: 2:05 PM
 */
public class FileSetsImpl
        extends UnmodifiableSortedMap<String, FileSet>
        implements FileSets {


    public FileSetsImpl(
            Map<String, FileSet> nameToFileSet) {

        super(nameToFileSet);
    }


    public FileSetsImpl(
            SortedMap<String, FileSet> nameToFileSet) {

        super(nameToFileSet);
    }


    @Override
    public boolean containsPath(
            String path) {

        for (final FileSet f : this.values()) {
            if (f.containsPath(path)) {
                return true;
            }
        }

        return false;
    }


    @Override
    public SortedSet<FileSet> getByBuildPath(
            String path) {

        final TreeSet<FileSet> result = new TreeSet<FileSet>();

        for (final FileSet f : this.values()) {
            final SortedSet<String> paths = f.getBuildPaths();
            for (String pathPrefix : paths) {
                if (path.startsWith(pathPrefix)) {
                    result.add(f);
                }
            }
        }

        return result;
    }


    @Override
    public SortedSet<FileSet> getByInstalledGaPath(
            String path) {

        final TreeSet<FileSet> result = new TreeSet<FileSet>();

        for (final FileSet f : this.values()) {
            final Paths paths = f.getInstalledGaPaths();
            for (String pathPrefix : paths) {
                if (path.startsWith(pathPrefix)) {
                    result.add(f);
                }
            }
        }

        return result;
    }

    @Override
    public SortedSet<FileSet> getByInstalledHfPath(
            String path) {

        final TreeSet<FileSet> result = new TreeSet<FileSet>();

        for (FileSet f : this.values()) {
            final Paths paths = f.getInstalledHfPaths();
            for (String pathPrefix : paths) {
                if (path.startsWith(pathPrefix)) {
                    result.add(f);
                }
            }
        }

        return result;
    }


    @Override
    public SortedSet<FileSet> getBySourcePath(
            String path) {

        final TreeSet<FileSet> result = new TreeSet<FileSet>();

        for (final FileSet f : this.values()) {
            final SortedSet<String> paths = f.getSourcePaths();
            for (String pathPrefix : paths) {
                if (path.startsWith(pathPrefix)) {
                    result.add(f);
                }
            }
        }

        return result;
    }


}
