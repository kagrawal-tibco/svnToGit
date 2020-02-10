package com.tibco.rta.common.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import com.tibco.rta.common.ConfigProperty;
import com.tibco.rta.common.registry.ModelRegistry;
import com.tibco.rta.common.service.RuntimeService;
import com.tibco.rta.model.Cube;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.runtime.ServerConfiguration;
import com.tibco.rta.model.runtime.ServerConfigurationImpl;

public class RuntimeServiceImpl extends AbstractStartStopServiceImpl implements RuntimeService {

	
	@Override
	public void init(Properties configuration) throws Exception {
		super.init(configuration);
	}
	
	@Override
	public Collection<ServerConfiguration> getRuntimeConfiguration() {
		
		
		Collection<ServerConfiguration> rtc = new ArrayList<ServerConfiguration>();
		ServerConfiguration serverConfiguration = new ServerConfigurationImpl("Configuration Properties");
		for (ConfigProperty cf : ConfigProperty.getPublicValues()) {
			Object value = cf.getValue(configuration);
            serverConfiguration.addServerConfigurationTuple(cf.getPropertyName(), value, cf.getDataType(), cf.getDefaultValue(),
                        cf.isPublic(), cf.getCategory(), cf.getParentCategory(),
                        cf.getDisplayName(), cf.getDescription());
		}

//		serverConfiguration.addServerConfigurationTuple("rta.server.version", RuntimeComponentVersion.getFullVersion(), DataType.STRING,
//				"", true, "/Engine/Version", "", "SPM Server Version", "SPM Server Version");
		rtc.add(serverConfiguration);

        ServerConfiguration dhConfiguration = new ServerConfigurationImpl("Dimension Hierarchies");
		for (RtaSchema schema : ModelRegistry.INSTANCE.getAllRegistryEntries()) {
			for (Cube cube : schema.getCubes()) {
				for (DimensionHierarchy dh : cube.getDimensionHierarchies()) {
                    dhConfiguration.addDimensionHierarchyConfig(schema.getName(), cube.getName(), dh.getName(), dh.isEnabled());
				}
			}
		}
		rtc.add(dhConfiguration);
		return rtc;
	}
}
