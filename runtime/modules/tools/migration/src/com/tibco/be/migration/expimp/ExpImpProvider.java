package com.tibco.be.migration.expimp;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Feb 17, 2008
 * Time: 2:16:18 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ExpImpProvider {

    /**
     * Does the provider support the following version.
     * This question is only asked after it has successfully answered either Export or Import
     * @param version
     * @return A boolean value indicating yes or no
     */
    boolean supportsVersion(String version);

    /**
     * In general does it support Export
     * @return
     */
    boolean supportsExport();

    /**
     * In general does it support Import
     * @return
     */
    boolean supportsImport();

    /**
     * Do an export to CSV files, the format is well-defined and documented,
     * Please above for the export format or the XLS file accompanying the
     * directory structure.
     * @param context - The Context underwhich to export
     * @param stats - Populate the stats accoording to the export facility
     * @throws Exception
     */
    void doExport(ExpImpContext context, ExpImpStats stats) throws Exception;

    /**
     * Do an import from a directory containing CSV files. The context provides to
     * which version it needs to be migrated. 
     * @param context
     * @param stats
     * @throws Exception
     */
    void doImport(ExpImpContext context, ExpImpStats stats) throws Exception;

    String getSupportedVersion();


}
