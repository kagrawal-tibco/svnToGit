package com.tibco.be.migration.expimp;

import java.util.Iterator;
import java.util.LinkedHashMap;

import com.tibco.be.migration.expimp.providers.ExpImpProviderImpl_v1_4;
import com.tibco.be.migration.expimp.providers.ExpImpProviderImpl_v2_0;
import com.tibco.be.migration.expimp.providers.ExpImpProviderImpl_v2_1;
import com.tibco.be.migration.expimp.providers.ExpImpProviderImpl_v2_2;
import com.tibco.be.migration.expimp.providers.ExpImpProviderImpl_v3_0;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Feb 17, 2008
 * Time: 3:04:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class ExpImpProviderFactory {

    private static ExpImpProviderFactory providerFactory = null;

    static Object [][] version2ProviderTable = { {"1.4.*", ExpImpProviderImpl_v1_4.class, null},
            {"2.0.*", ExpImpProviderImpl_v2_0.class, ExpImpProviderImpl_v2_0.class},
            {"2.1.*", ExpImpProviderImpl_v2_1.class, ExpImpProviderImpl_v2_1.class},
            {"2.2.*", ExpImpProviderImpl_v2_2.class, ExpImpProviderImpl_v2_2.class},
            {"3.0.*", ExpImpProviderImpl_v3_0.class, ExpImpProviderImpl_v3_0.class}
    };

    LinkedHashMap providers = new LinkedHashMap();

    public synchronized static ExpImpProviderFactory getInstance() {

        if (providerFactory == null) {
            providerFactory = new ExpImpProviderFactory();
            providerFactory.registerDefaultProviders();
        }
        return providerFactory;


    }

    private void registerDefaultProviders() {

        for (int i=0; i < version2ProviderTable.length; i++) {
            Object[] columns = version2ProviderTable[i];
            String versionKey = (String) columns[0];
            Class exportCls = (Class) columns[1];
            Class importCls = (Class) columns[2];

            ExpImpProviderPair pair = new ExpImpProviderPair();
            pair.version = versionKey;

            if (exportCls != null) {
                try {
                    pair.exportProvider = (ExpImpProvider) exportCls.newInstance();

                } catch (Exception e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
            if (importCls != null) {
                try {
                    pair.importProvider = (ExpImpProvider) importCls.newInstance();

                } catch (Exception e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }


            providers.put(versionKey, pair);

        }

    }

    public ExpImpProvider getExportProvider(String version) throws Exception {
        Iterator values = providers.values().iterator();
        while (values.hasNext()) {
            ExpImpProviderPair pair = (ExpImpProviderPair) values.next();
            if ((pair.exportProvider != null) && (pair.exportProvider.supportsExport())) {
                if (pair.exportProvider.supportsVersion(version)) {
                    return pair.exportProvider;
                }
            }
        }
        return null;
    }

    public ExpImpProvider getImportProvider(String version) throws Exception {
        Iterator values = providers.values().iterator();
        while (values.hasNext()) {
            ExpImpProviderPair pair = (ExpImpProviderPair) values.next();
            if ((pair.importProvider != null) && (pair.importProvider.supportsImport())) {
                if (pair.importProvider.supportsVersion(version)) {
                    return pair.importProvider;
                }
            }
        }
        return null;

    }

    private static class ExpImpProviderPair {
        public String version;
        public ExpImpProvider exportProvider;
        public ExpImpProvider importProvider;
    }
}
