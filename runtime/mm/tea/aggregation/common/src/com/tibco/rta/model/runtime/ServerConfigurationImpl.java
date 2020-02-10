package com.tibco.rta.model.runtime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tibco.rta.model.DataType;

import java.util.ArrayList;
import java.util.Collection;

public class ServerConfigurationImpl implements ServerConfiguration {

	private static final long serialVersionUID = -6033916495744365687L;
	
	protected Collection<ServerConfigurationTuple> serverConfigurationTuples = new ArrayList<ServerConfigurationTuple>();

    protected Collection<DimensionHierarchyConfiguration> hierarchyConfigurations = new ArrayList<DimensionHierarchyConfiguration>();

	protected String category;

    public ServerConfigurationImpl() {
    }

    public ServerConfigurationImpl(String category) {
		this.category = category;
	}

	@Override
	public Collection<ServerConfigurationTuple> getServerConfiguration() {
		return serverConfigurationTuples;
	}
	
	@Override
	public void addServerConfigurationTuple(String propertyName, Object value,
				DataType dataType, String defaultValue, boolean isPublic,
				String category, String parentCategory, String displayName,
				String description) {
		ServerConfigurationTupleImpl serverConfigurationTuple = new ServerConfigurationTupleImpl(propertyName, value,
				dataType, defaultValue, !isPublic,
				category, parentCategory, displayName,
				description);
		serverConfigurationTuples.add(serverConfigurationTuple);
	}

    @Override
    public Collection<DimensionHierarchyConfiguration> getDimensionHierarchyConfiguration() {
        return hierarchyConfigurations;
    }

    @Override
    public void addDimensionHierarchyConfig(String schema, String cube, String name, boolean enabled) {
        DimensionHierarchyConfiguration dimensionHierarchyConfiguration = new DimensionHierarchyConfiguration(schema, cube, name, enabled);
        hierarchyConfigurations.add(dimensionHierarchyConfiguration);
    }

    @Override
	public String getCategory() {
		return category;
	}

    public void setCategory(String category) {
        this.category = category;
    }

    public static class ServerConfigurationTupleImpl implements	ServerConfigurationTuple {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		protected String propertyName;
		protected Object value;
		protected DataType dataType;
		protected String defaultValue;

		protected boolean internal;
		protected String category;
		protected String parentCategory;
		protected String displayName;
		protected String description;

		public ServerConfigurationTupleImpl() {
		}

		// value should be preferably Java Serializable
		public ServerConfigurationTupleImpl(String propertyName, Object value,
				DataType dataType, String defaultValue, boolean internal,
				String category, String parentCategory, String displayName,
				String description) {
			this.propertyName = propertyName;
			this.value = value;
			this.dataType = dataType;
			this.defaultValue = defaultValue;

			this.internal = internal;
			this.category = category;
			this.parentCategory = parentCategory;
			this.displayName = displayName;
			this.description = description;

		}

		@Override
		public String getPropertyName() {
			return propertyName;
		}

		@Override
		public Object getValue() {
			return value;
		}

		@Override
		public DataType getDataType() {
			return dataType;
		}

		@Override
		public String getDefaultValue() {
			return defaultValue;
		}

		@Override
        @JsonIgnore
		public boolean isInternal() {
			return internal;
		}

		@Override
		public String getCategory() {
			return category;
		}

		@Override
		public String getParentCategory() {
			return parentCategory;
		}

		@Override
		public String getDisplayName() {
			return displayName;
		}

		@Override
		public String getDescription() {
			return description;
		}

	}
}
