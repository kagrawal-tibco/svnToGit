package com.tibco.rta.model.runtime;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.tibco.rta.model.DataType;

import java.io.Serializable;
import java.util.Collection;

/**
 * 
 * Represents a Server Configuration. Server ConfigProperty(s) are copied into this value object and exchanged 
 * between the client and server via see @RuntimeService
 *
 */

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = ServerConfigurationImpl.class, name = "serverConfiguration")})
public interface ServerConfiguration extends Serializable {

	Collection<ServerConfigurationTuple> getServerConfiguration();

    Collection<DimensionHierarchyConfiguration> getDimensionHierarchyConfiguration();
	
	void addServerConfigurationTuple(String propertyName, Object value,
			DataType dataType, String defaultValue, boolean isPublic,
			String category, String parentCategory, String displayName,
			String description);

    void addDimensionHierarchyConfig(String schema, String cube, String name, boolean enabled);

	String getCategory();

    @JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
    @JsonSubTypes({
        @JsonSubTypes.Type(value = ServerConfigurationImpl.ServerConfigurationTupleImpl.class, name = "tuple")})
	public interface ServerConfigurationTuple extends Serializable {
		String getPropertyName();

		Object getValue();
		
		DataType getDataType();

		String getDefaultValue();

		boolean isInternal();

		String getCategory();

		String getParentCategory();

		String getDisplayName();

		String getDescription();
	}


}
