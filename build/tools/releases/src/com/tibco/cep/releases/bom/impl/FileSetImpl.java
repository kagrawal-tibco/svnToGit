package com.tibco.cep.releases.bom.impl;

import com.tibco.cep.releases.bom.FileSet;
import com.tibco.cep.releases.bom.FileSetType;
import com.tibco.cep.releases.bom.Paths;

/**
 * User: nprade
 * Date: 6/28/11
 * Time: 1:36 PM
 */
public class FileSetImpl
        extends AbstractNamedComponent
        implements FileSet {

    private final Paths buildPaths;
    private final Paths installedGaPaths;
    private final Paths sourcePaths;
    private final Paths installedHfPaths;
    private final FileSetType type;


    public FileSetImpl(
            String name,
            FileSetType type,
            Paths buildPaths,
            Paths installedGaPaths,
            Paths installedHfPaths,
            Paths SourcePaths) {

        super(name);
        this.buildPaths = buildPaths;
        this.installedGaPaths = installedGaPaths;
        this.installedHfPaths = installedHfPaths;
        this.sourcePaths = SourcePaths;
        this.type = type;
    }


    @Override
    public int compareTo(
            FileSet o) {

        if (null == o) {
            return 1;
        } else {
            return this.getName().compareTo(o.getName());
        }
    }


    @Override
    public boolean containsPath(
            String path) {

        return this.buildPaths.containsPath(path)
                || this.installedGaPaths.containsPath(path)
                || this.installedHfPaths.containsPath(path)
                || this.sourcePaths.containsPath(path);
    }


    @Override
    public Paths getBuildPaths() {
        return this.buildPaths;
    }


    @Override
    public Paths getInstalledGaPaths() {
        return this.installedGaPaths;
    }


    @Override
    public String getInstalledGaPathFromSourcePath(
            String path) {

        for (String sourcePath : this.sourcePaths) {
            if (path.startsWith(sourcePath)) {
                return this.installedGaPaths.first() + path.substring(sourcePath.length());
            }
        }

        return null;
    }


    @Override
    public Paths getInstalledHfPaths() {
        return this.installedHfPaths;
    }


    @Override
    public String getInstalledHfPathFromSourcePath(
            String path) {

        for (String sourcePath : this.sourcePaths) {
            if (path.startsWith(sourcePath)) {
                return this.installedHfPaths.first() + path.substring(sourcePath.length());
            }
        }

        return null;
    }


    @Override
    public Paths getSourcePaths() {
        return this.sourcePaths;
    }


    @Override
    public FileSetType getType() {
        return this.type;
    }


    @Override
    public String toString() {
        return this.getName() + "@" + this.hashCode();
    }


}
