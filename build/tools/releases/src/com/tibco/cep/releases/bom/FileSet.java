package com.tibco.cep.releases.bom;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: 6/28/11
 * Time: 1:31 PM
 */
public interface FileSet
        extends NamedComponent, Comparable<FileSet> {

    boolean containsPath(String path);

    Paths getBuildPaths();

    Paths getInstalledGaPaths();

    String getInstalledGaPathFromSourcePath(String path);

    Paths getInstalledHfPaths();

    String getInstalledHfPathFromSourcePath(String path);

    Paths getSourcePaths();

    FileSetType getType();

}
