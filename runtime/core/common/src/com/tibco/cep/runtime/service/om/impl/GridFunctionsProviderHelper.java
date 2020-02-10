package com.tibco.cep.runtime.service.om.impl;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.element.impl.ManagedObjectManager;
import com.tibco.cep.runtime.model.element.impl.ManagedObjectSpi;
import com.tibco.cep.runtime.util.SystemProperty;

/*
* Author: Ashwin Jayaprakash / Date: 1/10/12 / Time: 12:00 PM
*/
public class GridFunctionsProviderHelper {
    protected static final Logger logger = LogManagerFactory.getLogManager().getLogger(GridFunctionsProviderHelper.class);

    public static enum NativeGridType {
        coherence("com.tibco.be.functions.cluster.CoherenceFunctionsProvider",
                "com.tibco.cep.runtime.managed.CoherenceManagedObjectSpi"),
        datagrid("com.tibco.be.functions.cluster.ASFunctionsProvider", 
                "com.tibco.cep.runtime.managed.DataGridManagedObjectSpi");

        String defaultFunctionsProvider;

        String managedSpiClassName;

        private NativeGridType(String defaultFunctionsProvider, String managedSpiClassName) {
            this.defaultFunctionsProvider = defaultFunctionsProvider;
            this.managedSpiClassName = managedSpiClassName;
        }

        public String getDefaultFunctionsProvider() {
            return defaultFunctionsProvider;
        }

        public String getManagedSpiClassName() {
            return managedSpiClassName;
        }
    }

    public static enum ManagedSpiType {
        none,
        grid,
        custom;

        public static final String propertyCustomProviderClassName =
                SystemProperty.CLUSTER_LOCKLESS_PROVIDER.getPropertyName() + ".classname";
    }

    static final String managedFunctionsProviderClassName = "com.tibco.be.functions.cluster.ManagedDataGridFunctionsProvider";

    static final String propertyCustomProviderLockFree = SystemProperty.CLUSTER_LOCKLESS_PROVIDER.getPropertyName() + ".lockfree";

    public static void ensurePreReqs() {
        String useOt = System.getProperty(SystemProperty.CLUSTER_USEOBJECTTABLE.getPropertyName());
        if (useOt == null || Boolean.parseBoolean(useOt)) {
            throw new IllegalArgumentException(
                    "This feature [" + SystemProperty.CLUSTER_LOCKLESS_PROVIDER.getPropertyName() + "]" +
                            " is incompatible with [" + SystemProperty.CLUSTER_USEOBJECTTABLE.getPropertyName() + "=true]");
        }
    }

    /**
     * @param gridType
     * @return No static dependencies on "functions" package. Instance of "DataGridFunctionsProvider".
     */
    public static Object $chooseDataGridFunctionsProvider(NativeGridType gridType) {
        String locklessProviderAlias =
                System.getProperty(SystemProperty.CLUSTER_LOCKLESS_PROVIDER.getPropertyName(), ManagedSpiType.none.name());

        ManagedSpiType spiType = null;
        try {
            spiType = ManagedSpiType.valueOf(locklessProviderAlias);
            logger.log(Level.INFO, "Requested lockless provider type [" + locklessProviderAlias + "]");
        }
        catch (IllegalArgumentException e) {
            spiType = ManagedSpiType.none;
            logger.log(Level.WARN,
                    "Requested lockless provider type [" + locklessProviderAlias + "]." +
                            " Using default [" + spiType.name() + "]");
        }

        switch (spiType) {
            case grid:
                try {
                    ensurePreReqs();

                    ManagedObjectSpi spi = (ManagedObjectSpi) Class.forName(gridType.managedSpiClassName).newInstance();
                    ManagedObjectManager.init(spi);

                    return Class.forName(managedFunctionsProviderClassName).newInstance();
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }

            case custom:
                try {
                    ensurePreReqs();

                    String providerClassName = System.getProperty(ManagedSpiType.propertyCustomProviderClassName);

                    if (providerClassName == null) {
                        throw new IllegalArgumentException("Custom lockless provider's fully qualified class name" +
                                " must be provided using  [" + ManagedSpiType.propertyCustomProviderClassName + "]");
                    }
                    else {
                        logger.log(Level.INFO, "Attempting to load custom lockless provider [" + providerClassName + "]");
                    }

                    ManagedObjectSpi spi = (ManagedObjectSpi) Class.forName(providerClassName).newInstance();
                    ManagedObjectManager.init(spi);

                    String lockFreeStr = System.getProperty(propertyCustomProviderLockFree, "true");
                    boolean lockFree = Boolean.parseBoolean(lockFreeStr);
                    if (lockFree) {
                        return Class.forName(managedFunctionsProviderClassName).newInstance();
                    }

                    return Class.forName(managedFunctionsProviderClassName).getConstructor(Boolean.TYPE).newInstance(lockFree);
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }

            case none:
            default:
                try {
                    return Class.forName(gridType.defaultFunctionsProvider).newInstance();
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
        }
    }
}
