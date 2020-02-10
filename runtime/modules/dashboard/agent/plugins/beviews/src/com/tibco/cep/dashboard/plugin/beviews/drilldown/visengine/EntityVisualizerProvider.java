package com.tibco.cep.dashboard.plugin.beviews.drilldown.visengine;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.tibco.cep.dashboard.common.data.TupleSchema;
import com.tibco.cep.dashboard.common.data.TupleSchemaField;
import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.dashboard.plugin.beviews.BEViewsProperties;
import com.tibco.cep.dashboard.plugin.beviews.data.SOURCE_AGGREGATE_VISIBLE_FIELDS;
import com.tibco.cep.dashboard.plugin.beviews.data.TupleSchemaFactory;
import com.tibco.cep.dashboard.plugin.beviews.mal.EntityCache;
import com.tibco.cep.dashboard.plugin.beviews.mal.EntityUtils;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.METRIC_AGGR_TYPE;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.PropertyMap;
import com.tibco.cep.designtime.core.model.SimpleProperty;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.kernel.service.logging.Logger;

//PATCH extract plug-in based factories
public class EntityVisualizerProvider {

	private static EntityVisualizerProvider instance;

	public static final synchronized EntityVisualizerProvider getInstance() {
		if (instance == null) {
			instance = new EntityVisualizerProvider();
		}
		return instance;
	}

	private Properties properties;

	@SuppressWarnings("unused")
	private Logger logger;

	@SuppressWarnings("unused")
	private ExceptionHandler exceptionHandler;

	@SuppressWarnings("unused")
	private MessageGenerator messageGenerator;

	private boolean initialized;

	private SOURCE_AGGREGATE_VISIBLE_FIELDS sourceAggregateVisibleFields;

	private boolean includeDependentSystemFields;

	private boolean includeSystemFields;

	private Map<String, EntityVisualizer> visualizers;

	private EntityVisualizerProvider() {
	}

	public void init(Properties properties, Logger logger, ExceptionHandler exceptionHandler, MessageGenerator messageGenerator) {
		this.properties = properties;
		this.logger = logger;
		this.exceptionHandler = exceptionHandler;
		this.messageGenerator = messageGenerator;
		this.sourceAggregateVisibleFields = SOURCE_AGGREGATE_VISIBLE_FIELDS.valueOf(String.valueOf(BEViewsProperties.DRILLDOWN_AGGREGATE_VISIBLE_COLUMNS.getValue(this.properties)).toUpperCase());
		this.includeDependentSystemFields = (Boolean) BEViewsProperties.DRILLDOWN_AGGREGATE_SYS_DEPENDENT_FIELDS.getValue(this.properties);
		this.includeSystemFields = (Boolean) BEViewsProperties.SEARCH_INCLUDE_SYSTEM_FIELDS.getValue(this.properties);
		visualizers = new HashMap<String, EntityVisualizer>();
		initialized = true;
	}

	public EntityVisualizer getEntityVisualizer(Entity entity) {
		if (initialized == false) {
			throw new IllegalStateException("Not initialized");
		}
		EntityVisualizer visualizer = visualizers.get(entity.getGUID());
		if (visualizer == null) {
			synchronized (this) {
				visualizer = visualizers.get(entity.getGUID());
				if (visualizer == null) {
					visualizer = new DefaultEntityVisualizer(entity);
					visualizers.put(entity.getGUID(), visualizer);
				}
			}
		}
		return visualizer;
	}

	public EntityVisualizer getEntityVisualizerById(String id) {
		return getEntityVisualizer(EntityCache.getInstance().getEntity(id));
	}

	public EntityVisualizer getEntityVisualizerByFullPath(String fullPath) {
		return getEntityVisualizer(EntityCache.getInstance().getEntityByFullPath(fullPath));
	}

	public void shutdown() {
		visualizers.clear();
		initialized = false;
	}

	private class DefaultEntityVisualizer implements EntityVisualizer {

		private Entity entity;
		private LinkedHashMap<String, String> displayableFields;

		DefaultEntityVisualizer(Entity entity) {
			this.entity = entity;
			this.displayableFields = new LinkedHashMap<String, String>();
			// we need to include incoming fields when the DRILLDOWN_AGGREGATE_VISIBLE_COLUMNS is set to ALL or INCOMING [aka !COMPUTED]
			boolean includeIncomingFields = sourceAggregateVisibleFields.compareTo(SOURCE_AGGREGATE_VISIBLE_FIELDS.COMPUTED) != 0;
			// we need to include computed fields when the DRILLDOWN_AGGREGATE_VISIBLE_COLUMNS is set to ALL or COMPUTED [aka !INCOMING]
			boolean includeComputedFields = sourceAggregateVisibleFields.compareTo(SOURCE_AGGREGATE_VISIBLE_FIELDS.INCOMING) != 0;
			if (entity instanceof Concept) {
				if (includeSystemFields == true) {
					//get a dynamic tuple for the entity
					TupleSchema tupleSchema = TupleSchemaFactory.getInstance().getDynamicBaseTupleSchema(entity.getGUID());
					for (TupleSchemaField field : tupleSchema.getSystemFields()) {
						if (field.isArtificalField() == true) {
							//skip artificial fields , they are meant for internal usage and never exposed
							continue;
						}
						displayableFields.put(field.getFieldName(), field.getFieldName());
					};
				}
				Concept concept = (Concept) entity;
				for (PropertyDefinition field : concept.getAllProperties()) {
					if (field.isArray() == true) {
						//skip array based field
						continue;
					}
					if (field.getHistorySize() > 0) {
						//skip history enabled field
						continue;
					}
					if (field.getType().compareTo(PROPERTY_TYPES.CONCEPT) == 0 || field.getType().compareTo(PROPERTY_TYPES.CONCEPT_REFERENCE) == 0){
						//skip field which are object types
						continue;
					}
					if (EntityUtils.isDVM(entity) == true && "true".equalsIgnoreCase(getPropertyValue(field, "parentidentifier")) == true && "true".equalsIgnoreCase(getPropertyValue(field, "system")) == true) {
						//skip the system parent id field in DVM's
						continue;
					}
					if ("true".equalsIgnoreCase(getPropertyValue(field, "incoming")) == true) {
						//we are dealing with incoming field, check if it is allowed
						if (includeIncomingFields == true) {
							//incoming field is allowed, check if the field is system dependent field
							if ("true".equalsIgnoreCase(getPropertyValue(field, "system")) == true) {
								//we are dealing with a system dependent incoming field, is it allowed ?
								if (includeDependentSystemFields == true) {
									//system dependent incoming field is allowed
									this.displayableFields.put(field.getName(), field.getName());
								}
								else {
									//system dependent incoming field is not allowed, skip it
									continue;
								}
							}
							else {
								//incoming field is allowed
								this.displayableFields.put(field.getName(), field.getName());
							}
						}
						else {
							//incoming field is not allowed, skip it
							continue;
						}
					}
					if ("true".equalsIgnoreCase(getPropertyValue(field, "system")) == true) {
						//we are dealing with dependent system field, check if it is allowed
						if (includeComputedFields == true && includeDependentSystemFields == true) {
							//dependent system field is allowed
							this.displayableFields.put(field.getName(), field.getName());
						}
						else {
							//dependent system field is not allowed, skip it
							continue;
						}
					}
					if (getPropertyValue(field, "userdefined") == null && field.isGroupByField() == false && field.getAggregationType().compareTo(METRIC_AGGR_TYPE.SET) != 0){
						//we are dealing with normal aggregate field , check if it is allowed
						if (includeComputedFields == false) {
							//normal aggregate field is not allowed, skip it
							continue;
						}
					}
					//we are dealing with a normal allowed field
					this.displayableFields.put(field.getName(), field.getName());
				}
			}
		}

		private String getPropertyValue(PropertyDefinition field, String name) {
			PropertyMap extendedPropertiesHolder = field.getExtendedProperties();
			if (extendedPropertiesHolder == null) {
				return null;
			}
			List<Entity> extendedPropertyList = extendedPropertiesHolder.getProperties();
			for (Entity propertyObject : extendedPropertyList) {
				if (propertyObject instanceof SimpleProperty) {
					SimpleProperty property = (SimpleProperty) propertyObject;
					if (property.getName().equals(name)) {
						return property.getValue();
					}
				}
			}
			return null;
		}

		@Override
		public String getName() {
			if (EntityUtils.isDVM(entity) == true) {
				Entity parent = EntityUtils.getParent(entity);
				return parent.getName()+" Source";
			}
			return entity.getName();
		}

		@Override
		public Map<String, String> getDisplayableFields() {
			return displayableFields;
		}

		@Override
		public String getDisplayableName(String fieldName) {
			return displayableFields.get(fieldName);
		}

		@Override
		public String getName(String fieldDisplayName) {
			return displayableFields.get(fieldDisplayName);
		}

		@Override
		public String getTypeId() {
			return entity.getGUID();
		}

	}
}