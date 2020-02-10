package com.tibco.rta.service.admin;

import com.tibco.rta.common.ConfigProperty;
import com.tibco.rta.common.GMPActivationListener;
import com.tibco.rta.common.registry.ModelRegistry;
import com.tibco.rta.common.service.ModelChangeListener;
import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.common.service.impl.AbstractStartStopServiceImpl;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.Cube;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.RtaSchemaModelFactory;
import com.tibco.rta.model.mutable.MutableDimensionHierarchy;
import com.tibco.rta.model.serialize.impl.SerializationUtils;
import org.xml.sax.InputSource;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;


/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 3/10/12
 * Time: 11:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class AdminServiceImpl extends AbstractStartStopServiceImpl implements AdminService, AdminServiceImplMBean, GMPActivationListener {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_ADMIN.getCategory());

    private String configDir;

    private volatile boolean initialized;

    private String activationStatus = "PASSIVE";

    private List<ModelChangeListener> modelChangeListeners = new ArrayList<ModelChangeListener>();

    @Override
    public void init(Properties configuration) throws Exception {
        if (!initialized) {
            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Initializing Admin service..");
            }
            super.init(configuration);
            //Load initial configuration like config directory for storing schemas.
            configDir = (String) ConfigProperty.RTA_SCHEMA_STORE.getValue(configuration);
            //Store schemas here.
            //Check if dir exists
            File configDirectory = new File(configDir);
            if (!configDirectory.exists()) {
                throw new Exception(String.format("Config directory [%s] for schemas does not exist", configDir));
            }
            if (!configDirectory.isDirectory() || !configDirectory.canWrite()) {
                throw new Exception(String.format("Config directory [%s] for schemas is not a writeable directory", configDir));
            }
            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Configuration: Schema directory [%s]", configDir);
            }

            loadAllSchemas();
            loadAllSystemSchemas();
            registerMBean(configuration);

            ServiceProviderManager.getInstance().getGroupMembershipService().addActivationListener(this);
            initialized = true;

            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Initializing Admin service Complete.");
            }
        }
    }

    @Override
    public void start() throws Exception {
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Starting Admin service..");
        }
        super.start();
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Starting Admin service Complete.");
        }
    }

    @Override
    public void stop() throws Exception {
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Stopping Admin service..");
        }
        super.stop();
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Stopping Admin service Complete.");
        }
    }

    @Override
    public Collection<RtaSchema> getAllSchemas(String username) {
        return ModelRegistry.INSTANCE.getAllRegistryEntries();
    }

    @Override
    public Collection<RtaSchema> getAllSchemas() {
        return ModelRegistry.INSTANCE.getAllRegistryEntries();
    }

    @Override
    public RtaSchema getSchema(String schemaName) throws Exception {
        if (!initialized) {
            throw new Exception("Service not initialized yet");
        }
        return ModelRegistry.INSTANCE.getRegistryEntry(schemaName);
    }

    @Override
    public void saveSchema(RtaSchema schema) throws Exception {
        if (!initialized) {
            throw new Exception("Service not initialized yet");
        }
        String schemaName = schema.getName();
        RtaSchema existingSchema = ModelRegistry.INSTANCE.getRegistryEntry(schemaName);

        //Create file with name of schema
        if (configDir != null) {
            File configFile = new File(configDir + File.separatorChar + schemaName + ".xml");
            SerializationUtils.serializeSchema(schema, configFile);
            for (ModelChangeListener modelChangeListener : modelChangeListeners) {
                try {
                    if (existingSchema != null) {
                        modelChangeListener.onUpdate(existingSchema);
                    } else {
                        modelChangeListener.onCreate(schema);
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    LOGGER.log(Level.ERROR, "Error while invoking model change listener", e);
                }
            }
        }
    }

    @Override
    public void removeSchema(String schemaName) throws Exception {
        if (!initialized) {
            throw new Exception("Service not initialized yet");
        }
        //Remove from registry
        RtaSchema schema = ModelRegistry.INSTANCE.getRegistryEntry(schemaName);
        ModelRegistry.INSTANCE.removeRegistryEntry(schemaName);
        //Also remove from disk
        if (configDir != null) {
            File configFile = new File(configDir + File.separatorChar + schemaName + ".xml");
            if (!configFile.exists()) {
                throw new Exception(String.format("No schema file found for schema [%s]", configFile));
            }
            boolean deleted = configFile.delete();
            if (!deleted) {
                throw new Exception(String.format("Could not delete schema file [%s]", configFile));
            }
            for (ModelChangeListener modelChangeListener : modelChangeListeners) {
                try {
                    modelChangeListener.onDelete(schema);
                } catch (Exception e) {
                    LOGGER.log(Level.ERROR, "Error while invoking onDelete() model change listener", e);
                }
            }
        }
    }

    @Override
    public void addModelChangeListener(ModelChangeListener modelChangeListener) {
        this.modelChangeListeners.add(modelChangeListener);
    }

    @Override
    public void removeModelChangeListener(ModelChangeListener modelChangeListener) {
        this.modelChangeListeners.remove(modelChangeListener);
    }

    @Override
    public RtaSchema loadSchema(String schemaName) throws Exception {
        RtaSchema schema = null;
        if (configDir != null) {
            File config = new File(configDir);
            File[] fileset = config.listFiles();
            if (fileset != null) {
                for (File schemaFile : fileset) {
                    if (schemaFile.getName().equals(schemaName)) {
                        InputSource schemaFileStream = new InputSource(new FileInputStream(schemaFile));
                        schema = RtaSchemaModelFactory.getInstance().createSchema(schemaFileStream);
                        break;
                    }
                }
            }
        }
        return schema;
    }

    private void loadAllSchemas() throws Exception {
        if (configDir != null) {
            ModelRegistry modelRegistry = ModelRegistry.INSTANCE;
            File config = new File(configDir);
            File[] fileset = config.listFiles();
            if (fileset != null) {
                for (File schemaFile : fileset) {
                    if (schemaFile.isDirectory()) {
                        continue;
                    } else if (!(schemaFile.getAbsolutePath().endsWith(".xml"))) {
                        continue;
                    }

                    if (LOGGER.isEnabledFor(Level.INFO)) {
                        LOGGER.log(Level.INFO, "Loading schema file %s", schemaFile);
                    }
                    InputSource schemaFileStream = new InputSource(new FileInputStream(schemaFile));
                    RtaSchema schema = RtaSchemaModelFactory.getInstance().createSchema(schemaFileStream);
                    modelRegistry.put(schema);
                }
            }
        }
    }

    private void loadAllSystemSchemas() throws Exception {
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Loading system schemas..");
        }
        ModelRegistry modelRegistry = ModelRegistry.INSTANCE;
        InputStream is = getClass().getClassLoader().getResourceAsStream("BETEA_System_Schema.xml");

        RtaSchema systemSchema = RtaSchemaModelFactory.getInstance().createSchema(new InputSource(is));
        modelRegistry.put(systemSchema);
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Loading system schemas [%s] done.", systemSchema.getName());
        }
    }


    private void registerMBean(Properties configuration) throws Exception {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        String mbeanPrefix = (String) ConfigProperty.BE_TEA_AGENT_SERVICE_MBEANS_PREFIX.getValue(configuration);
        ObjectName name = new ObjectName(mbeanPrefix + ".admin:type=AdminService");
        if (!mbs.isRegistered(name)) {
            mbs.registerMBean(this, name);
        }
    }

    @Override
    public String getCurrentActivationStatus() {
        return activationStatus;
    }

    @Override
    public boolean getHierarchyStatus(String schemaName,
                                      String cubeName,
                                      String hierarchyName) {
        if (schemaName == null || schemaName.isEmpty()) {
            throw new IllegalArgumentException("Schema name cannot be null");
        }
        if (cubeName == null || cubeName.isEmpty()) {
            throw new IllegalArgumentException("Cube name cannot be null");
        }
        if (hierarchyName == null || hierarchyName.isEmpty()) {
            throw new IllegalArgumentException("Hierarchy name cannot be null");
        }
        try {
            RtaSchema schema = getSchema(schemaName);
            if (schema == null) {
                throw new IllegalArgumentException("Schema not found");
            }
            Cube cube = schema.getCube(cubeName);
            if (cube == null) {
                throw new IllegalArgumentException("Cube not found");
            }
            DimensionHierarchy dimensionHierarchy = cube.getDimensionHierarchy(hierarchyName);
            if (dimensionHierarchy == null) {
                throw new IllegalArgumentException("Dimension Hierarchy not found");
            }
            return dimensionHierarchy.isEnabled();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void changeHierarchyStatus(String schemaName,
                                      String cubeName,
                                      String hierarchyName,
                                      boolean enabled) {
        if (schemaName == null || schemaName.isEmpty()) {
            throw new IllegalArgumentException("Schema name cannot be null");
        }
        if (cubeName == null || cubeName.isEmpty()) {
            throw new IllegalArgumentException("Cube name cannot be null");
        }
        if (hierarchyName == null || hierarchyName.isEmpty()) {
            throw new IllegalArgumentException("Hierarchy name cannot be null");
        }
        try {
            RtaSchema schema = getSchema(schemaName);
            if (schema == null) {
                throw new IllegalArgumentException("Schema not found");
            }
            Cube cube = schema.getCube(cubeName);
            if (cube == null) {
                throw new IllegalArgumentException("Cube not found");
            }
            DimensionHierarchy dimensionHierarchy = cube.getDimensionHierarchy(hierarchyName);
            if (dimensionHierarchy == null) {
                throw new IllegalArgumentException("Dimension Hierarchy not found");
            }
            if (dimensionHierarchy instanceof MutableDimensionHierarchy) {
                if (LOGGER.isEnabledFor(Level.INFO)) {
                    LOGGER.log(Level.INFO, "Changing status hierarchy [%s] to [%s]", hierarchyName, enabled);
                }
                ((MutableDimensionHierarchy) dimensionHierarchy).setEnabled(enabled);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivate() {
        activationStatus = "ACTIVE";
    }

    @Override
    public void onDeactivate() {
        activationStatus = "PASSIVE";
    }
}
