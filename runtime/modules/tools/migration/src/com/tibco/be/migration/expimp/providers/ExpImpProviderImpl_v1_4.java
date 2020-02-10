package com.tibco.be.migration.expimp.providers;

import com.tibco.be.migration.expimp.ExpImpContext;
import com.tibco.be.migration.expimp.ExpImpProvider;
import com.tibco.be.migration.expimp.ExpImpStats;
import com.tibco.be.migration.expimp.providers.bdb.BDBExporter_v1_4;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Feb 17, 2008
 * Time: 3:02:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class ExpImpProviderImpl_v1_4 implements ExpImpProvider {

    public void doExport(ExpImpContext context, ExpImpStats stats) throws Exception {
        BDBExporter_v1_4 exporter = new BDBExporter_v1_4();
        exporter.init(context, stats);
        exporter.exportAll();
        exporter.destroy();

    }

    public void doImport(ExpImpContext context, ExpImpStats stats) throws Exception {

        throw new Exception("Unsupported operation. Please Migrate to the latest BE version.");
    }

    public String getSupportedVersion() {
        return "1.4.*";
    }

    public boolean supportsExport() {
        return true;
    }

    public boolean supportsImport() {
        return false;
    }

    public boolean supportsVersion(String version) {
        return (version.startsWith("1.4"));
    }
}
