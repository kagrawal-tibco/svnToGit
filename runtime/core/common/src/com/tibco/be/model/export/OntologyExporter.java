package com.tibco.be.model.export;

import com.tibco.cep.designtime.model.Ontology;

/**
 * @author ishaan
 * @version Jan 28, 2005, 2:54:10 PM
 */

/**
 * Implementations of this interface are used to export the Ontology data model
 * to disk.
 */
public interface OntologyExporter {

    /**
     * Exports the Ontology to the path specified.
     * @param ontology The Ontology to export.
     * @param path The file or directory export path.
     * @throws Exception thrown in case of error during export.
     */
    public void export(Ontology ontology, String path) throws Exception;

    /**
     * The name for the Exporter.
     * @return
     */
    public String getDisplayLabel();

    /**
     * Whether the path supplied to export() should be a directory or a file name.
     * @return true, if the export path is a directory, false if it's a file name.
     */
    public boolean exportToDirectory();
}