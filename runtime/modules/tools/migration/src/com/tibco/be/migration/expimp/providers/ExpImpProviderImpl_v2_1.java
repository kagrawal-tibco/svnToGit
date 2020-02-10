package com.tibco.be.migration.expimp.providers;

import com.tibco.be.migration.BEMigrationProperties;
import com.tibco.be.migration.expimp.ExpImpContext;
import com.tibco.be.migration.expimp.ExpImpProvider;
import com.tibco.be.migration.expimp.ExpImpStats;
import com.tibco.be.migration.expimp.providers.bdb.BDBExporter_v2_1;
import com.tibco.be.migration.expimp.providers.bdb.BdbImporter_v2_1;
import com.tibco.be.migration.expimp.providers.cache.CacheExporter_v2_1;
import com.tibco.be.migration.expimp.providers.db.OracleImporter_v2_1;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Feb 17, 2008
 * Time: 3:12:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class ExpImpProviderImpl_v2_1 implements ExpImpProvider {

    public void doExport(ExpImpContext context, ExpImpStats stats) throws Exception {
        switch(context.getMethod()) {
             case BEMigrationProperties.IMPEXP_METHOD_BDB: {
                BDBExporter_v2_1 exporter = new BDBExporter_v2_1();
                exporter.init(context, stats);
                exporter.exportAll();
                exporter.destroy();
            }
                break;
            case BEMigrationProperties.IMPEXP_METHOD_DB: {
                CacheExporter_v2_1 exporter = new CacheExporter_v2_1();
                exporter.init(context, stats);
                exporter.exportAll();
                exporter.destroy();
            }
                break;
        }

    }

    public void doImport(ExpImpContext context, ExpImpStats stats) throws Exception {
        switch(context.getMethod()) {
            case BEMigrationProperties.IMPEXP_METHOD_BDB: {
                BdbImporter_v2_1 importer = new BdbImporter_v2_1();
                importer.init(context, stats);
                importer.importAll();
                importer.destroy();
            }
                break;
            case BEMigrationProperties.IMPEXP_METHOD_DB: {
                OracleImporter_v2_1 importer = new OracleImporter_v2_1();
                importer.init(context, stats);
                importer.importAll();
                importer.destroy();
            }
                break;
        }
    }

    public String getSupportedVersion() {
        return "2.1.*";
    }

    public boolean supportsExport() {
        return true;
    }

    public boolean supportsImport() {
        return true;
    }

    public boolean supportsVersion(String version) {
        return (version.startsWith("2.1"));
    }
}