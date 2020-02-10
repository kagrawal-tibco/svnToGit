package com.tibco.rta.service.persistence.as;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.tibco.as.space.ASException;
import com.tibco.as.space.FieldDef;
import com.tibco.as.space.FieldDef.FieldType;
import com.tibco.as.space.IndexDef;
import com.tibco.as.space.Space;
import com.tibco.as.space.SpaceDef;
import com.tibco.rta.MetricKey;
import com.tibco.rta.as.kit.ASResourceFactory;
import com.tibco.rta.common.ConfigProperty;
import com.tibco.rta.common.registry.ModelRegistry;
import com.tibco.rta.common.service.ModelChangeListener;
import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.Attribute;
import com.tibco.rta.model.Cube;
import com.tibco.rta.model.DataType;
import com.tibco.rta.model.Dimension;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.FunctionDescriptor.FunctionParam;
import com.tibco.rta.model.Measurement;
import com.tibco.rta.model.MetadataElement;
import com.tibco.rta.model.MetricFunctionDescriptor;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.runtime.model.MetricNode;

class SchemaManager implements ModelChangeListener {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_PERSISTENCE.getCategory());

    Map<String, Space> schemaSpaceMap = new HashMap<String, Space>();
    ASResourceFactory resourceFactory;

    ASPersistenceService pService;
    Properties config;
    boolean usePK;

	private boolean shouldSeed;
    @SuppressWarnings("unchecked")
    SchemaManager(ASPersistenceService pService) {
    	usePK = System.getProperty("use_pk", "true").equals("true");
        this.pService = pService;
        this.resourceFactory = pService.getResourceFactory();
        //TODO Very ugly
        this.config = ServiceProviderManager.getInstance().getConfiguration();
        boolean is_cache_node = Boolean.valueOf((String) ConfigProperty.IS_CACHE_NODE.getValue(config));
        boolean is_seeder = Boolean.valueOf((String)ConfigProperty.IS_DATA_SEEDER.getValue(config));
        shouldSeed = is_seeder || is_cache_node;			// if cache node then always seed otherwise use is_seeder property
    }

    synchronized void createSchema(RtaSchema schema) throws Exception {
        getOrCreateFactSchema(schema);
        getOrCreateRuleSchema(schema);
        for (Cube cube : schema.getCubes()) {
            for (DimensionHierarchy dh : cube.getDimensionHierarchies()) {
                getOrCreateMetricFactSchema(schema, cube, dh);
                getOrCreateMetricSchema(schema, cube, dh);
                getOrCreateProcessedFactsSchema(schema, cube, dh);
                getOrCreateRuleMetricSchema(schema, cube, dh);
            }
            
        }
    }

    private Space getOrCreateRuleSchema(RtaSchema schema) {
		String spaceName = makeRuleSchemaName(schema.getName());
		Space space = null;
		if ((space = schemaSpaceMap.get(spaceName)) != null) {
			if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "Schema Already exists for Rule Info: %s", spaceName);
            }
			return space;
		}
        SpaceDef spacedef = ASResourceFactory.configureSpace(spaceName, config);
        addNonNullableField(spacedef, ASPersistenceService.SESSION_RULE_NAME_FIELD, FieldType.STRING);
        addNullableField(spacedef, ASPersistenceService.SESSION_RULE_DETAIL_FIELD, FieldType.STRING);
        return space;
    }	

	private String makeRuleSchemaName(String schemaName) {
		return removeSplChars(ASPersistenceService.RULE_TABLE_NAME + schemaName);
	}

	Space getOrCreateProcessedFactsSchema(RtaSchema schema, Cube cube,
                                          DimensionHierarchy dh) throws ASException {
        String spaceName = makeCompletedFactsSchemaName(schema.getName(), cube.getName(), dh.getName());
        Space space = null;
        if ((space = schemaSpaceMap.get(spaceName)) != null) {
            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "Schema Already exists for Processed Facts : %s", spaceName);
            }
            return space;
        }

        SpaceDef spacedef = ASResourceFactory.configureSpace(spaceName, config);
        FieldType ft = FieldType.STRING;

        FieldDef fd = FieldDef.create("factid", ft);
        fd.setNullable(true);
        spacedef.putFieldDef(fd);
        spacedef.setKey("factid");

        fd = FieldDef.create("schema", ft);
        fd.setNullable(true);
        spacedef.putFieldDef(fd);

        fd = FieldDef.create("cube", ft);
        fd.setNullable(true);
        spacedef.putFieldDef(fd);

        fd = FieldDef.create("hierarchy", ft);
        fd.setNullable(true);
        spacedef.putFieldDef(fd);

        fd = FieldDef.create("measurement", ft);
        fd.setNullable(true);
        spacedef.putFieldDef(fd);

        space = resourceFactory.defineSpace(spaceName, spacedef, shouldSeed , true);
        schemaSpaceMap.put(spaceName, space);
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Schema Created for Processed Facts : %s", spaceName);
        }
        return space;

    }

    Space getOrCreateFactSchema(RtaSchema schema) throws ASException {
        return getOrCreateFactSchema(schema.getName());
    }

    Space getOrCreateFactSchema(String schemaName) throws ASException {
        RtaSchema schema = ModelRegistry.INSTANCE.getRegistryEntry(schemaName);
        
        String spaceName = makeFactSpaceName(schemaName);
        Space space = null;
        if ((space = schemaSpaceMap.get(spaceName)) != null) {
            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "Schema Already exists for Fact : %s", spaceName);
            }
            return space;
        }

        SpaceDef spacedef = resourceFactory.configureSpace(spaceName, config);

        // for (Attribute attr : cube.getMeasurement().getAttributes()) {
        // FieldType ft = getASDataType(attr.getDataType());
        // FieldDef fd = FieldDef.create(attr.getName(), ft);
        // fd.setNullable(true);
        // spacedef.putFieldDef(fd);
        // // spacedef.setKey(attr.getName());
        // }
        // spacedef.putFieldDef(FieldDef.create(KEY_FIELD, FieldType.STRING));
        // spacedef.setKey(KEY_FIELD);

        addNonNullableField(spacedef, ASPersistenceService.FACT_KEY_FIELD, FieldType.STRING);
        spacedef.setKey(ASPersistenceService.FACT_KEY_FIELD);

        addNullableField(spacedef, ASPersistenceService.OWNER_SCHEMA_FIELD, FieldType.STRING);

        for(Attribute attribute : schema.getAttributes()){
        	if (LOGGER.isEnabledFor(Level.DEBUG)) {
        		LOGGER.log(Level.DEBUG, "Attribute: "+ attribute.getName()+ "-> datatype: "+ attribute.getDataType());
        	}
        	addNullableField(spacedef, attribute.getName(), getASDataType(attribute.getDataType()));
        }
		addNonNullableField(spacedef, ASPersistenceService.UPDATED_DATE_TIME_FIELD, FieldType.DATETIME);
		addNonNullableField(spacedef, ASPersistenceService.CREATED_DATE_TIME_FIELD, FieldType.DATETIME);
		
        space = resourceFactory.defineSpace(spaceName, spacedef, shouldSeed, true);
        schemaSpaceMap.put(spaceName, space);
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Schema Created for Fact : %s", spaceName);
        }
        return space;
    }

    void addNullableField(SpaceDef spcaeDef, String fieldName, FieldType fieldType){
    	 FieldDef ownerSchemaField = FieldDef.create(fieldName, fieldType);
         ownerSchemaField.setNullable(true);
         spcaeDef.putFieldDef(ownerSchemaField);
    }
    
    void addNonNullableField(SpaceDef spcaeDef, String fieldName, FieldType fieldType){
   	 	FieldDef ownerSchemaField = FieldDef.create(fieldName, fieldType);
        ownerSchemaField.setNullable(false);
        spcaeDef.putFieldDef(ownerSchemaField);
   }
    
    Space getOrCreateMetricSchema(String schemaName, String cubeName,
                                  String dhName) throws ASException {

        RtaSchema schema = ModelRegistry.INSTANCE.getRegistryEntry(schemaName);

        return getOrCreateMetricSchema(schema, schema.getCube(cubeName), schema
                .getCube(cubeName).getDimensionHierarchy(dhName));

    }

    Space getOrCreateMetricFactSchema(MetricNode metricNode) throws Exception {
        MetricKey mKey = (MetricKey) metricNode.getKey();
        RtaSchema schema = ModelRegistry.INSTANCE.getRegistryEntry(mKey
                .getSchemaName());
        Cube cube = schema.getCube(mKey.getCubeName());
        DimensionHierarchy dh = cube.getDimensionHierarchy(mKey.getDimensionHierarchyName());
        return getOrCreateMetricFactSchema(schema, cube, dh);
    }

    Space getOrCreateMetricFactSchema(RtaSchema schema, Cube cube, DimensionHierarchy dh)
            throws Exception {
        String spaceName = makeMetricFactSpaceName(schema, cube, dh);
        Space space = null;
        if ((space = schemaSpaceMap.get(spaceName)) != null) {
            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "Schema Already exists for METRIC_FACT : %s", spaceName);
            }
            return space;
        }

        SpaceDef spacedef = ASResourceFactory.configureSpace(spaceName, config);

        Iterator<Dimension> i = dh.getDimensions()
                .iterator();
        List<String> dimNames = new ArrayList<String>();
        while (i.hasNext()) {
            Dimension d = i.next();
            Attribute attr = d.getAssociatedAttribute();
            FieldType ft = getASDataType(attr.getDataType());
            // TODO:Dimension value can be derived from attribute. hence its
            // datatype could be different?
            FieldDef fd = FieldDef.create(d.getName(), ft);
            fd.setNullable(true);
            spacedef.putFieldDef(fd);
            dimNames.add(d.getName());
        }

        FieldDef fd = FieldDef.create(ASPersistenceService.DIMENSION_LEVEL_NAME_FIELD,
                FieldType.STRING);
        fd.setNullable(true);
        spacedef.putFieldDef(fd);

        // value
        fd = FieldDef.create(ASPersistenceService.FACT_KEY_FIELD, FieldType.STRING);
        fd.setNullable(true);
        spacedef.putFieldDef(fd);
        

        // key
        if (usePK) {
            fd = FieldDef.create(ASPersistenceService.PK_KEY, FieldType.STRING);
            fd.setNullable(true);
            spacedef.putFieldDef(fd);

        	spacedef.setKey(ASPersistenceService.PK_KEY);
		} else {
			String[] keyFields = new String[dimNames.size() + 2];
			keyFields[0] = ASPersistenceService.DIMENSION_LEVEL_NAME_FIELD;
			for (int ii=0; ii<dimNames.size(); ii++) {
				keyFields[ii+1] = dimNames.get(ii);
			}
			keyFields[dimNames.size() + 1] = ASPersistenceService.FACT_KEY_FIELD;

			spacedef.setKey(keyFields);
		}

        space = resourceFactory.defineSpace(spaceName, spacedef, shouldSeed, true);
        schemaSpaceMap.put(spaceName, space);

        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Schema Created for Metric_Fact : %s", spaceName);
        }
        return space;

    }
    

	Space getOrCreateRuleMetricSchema(String schemaName, String cubeName, String hierarchyName) throws Exception {
		RtaSchema schema = ModelRegistry.INSTANCE.getRegistryEntry(schemaName);
		return getOrCreateRuleMetricSchema(schema, schema.getCube(cubeName), schema.getCube(cubeName)
				.getDimensionHierarchy(hierarchyName));
	}

    private Space getOrCreateRuleMetricSchema(RtaSchema schema, Cube cube, DimensionHierarchy dh) throws Exception{
    	String spaceName = makeRuleMetricSchemaName(schema, cube, dh);
    	Space space = null;
        if ((space = schemaSpaceMap.get(spaceName)) != null) {
            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "Schema Already exists for RULE METRIC : %s", spaceName);
            }
            return space;
        }
        SpaceDef spacedef = ASResourceFactory.configureSpace(spaceName, config);
        addNonNullableField(spacedef, ASPersistenceService.METRIC_KEY_FIELD, FieldType.STRING);     
        addNonNullableField(spacedef, ASPersistenceService.RULE_NAME_FIELD, FieldType.STRING);     
        addNonNullableField(spacedef, ASPersistenceService.RULE_ACTION_NAME_FIELD, FieldType.STRING);     
        addNullableField(spacedef, ASPersistenceService.RULE_SET_COUNT_FIELD, FieldType.INTEGER);     
        addNullableField(spacedef, ASPersistenceService.RULE_SCHEDULED_TIME_FIELD, FieldType.LONG);     
        addNullableField(spacedef, ASPersistenceService.RULE_LAST_FIRED_TIME_FIELD, FieldType.LONG);
        addNullableField(spacedef, ASPersistenceService.DIMENSION_LEVEL_NAME_FIELD, FieldType.STRING);
        addNullableField(spacedef, ASPersistenceService.RULE_SET_CONDITION_KEY_FIELD, FieldType.STRING);
        addNullableField(spacedef, ASPersistenceService.RULE_CLEAR_CONDITION_KEY_FIELD, FieldType.STRING);
        
        spacedef.setKey(ASPersistenceService.METRIC_KEY_FIELD, ASPersistenceService.RULE_NAME_FIELD, ASPersistenceService.RULE_ACTION_NAME_FIELD);
		IndexDef indexDef = IndexDef.create(ASPersistenceService.RULE_SCHEDULED_TIME_FIELD + "_index");
		indexDef.setFieldNames(ASPersistenceService.RULE_SCHEDULED_TIME_FIELD);
		//spacedef.addIndexDef(indexDef);
        
        for (Dimension dim : dh.getDimensions()) {
			addNullableField(spacedef, dim.getName(), getASDataType(dim.getAssociatedAttribute().getDataType()));
        }
		for (Measurement measurement : dh.getMeasurements()) {
			MetricFunctionDescriptor md = measurement.getMetricFunctionDescriptor();
			if (md.isMultiValued()) {
				addNullableField(spacedef, measurement.getName(), FieldType.BLOB);
			} else {
				//addNullableField(spacedef, measurement.getName(), getASDataType(md.getMetricDataType()));
				addNullableField(spacedef, measurement.getName(), getASDataType(measurement.getDataType()));
			}
			for (FunctionParam param : md.getFunctionContexts()) {
				addNullableField(spacedef, measurement.getName() + ASPersistenceService.SEP + param.getName(),
						getASDataType(param.getDataType()));
			}
		}
		if(resourceFactory.isCreateIndex()) {
			List<IndexDef> indexes = new ArrayList<IndexDef>();
			indexes.add(indexDef);
	        space = resourceFactory.defineSpace(spaceName, spacedef, indexes, shouldSeed, true);			
		}
		else {
        space = resourceFactory.defineSpace(spaceName, spacedef, shouldSeed, true);        
		}
        
        schemaSpaceMap.put(spaceName, space);

        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Schema Created for RULE Metric : %s", spaceName);
        }
        return space;
    	
    }

	List<Space> getAllSchemas() {
		return new ArrayList<Space>(schemaSpaceMap.values());
	}

	private Space getOrCreateMetricSchema(RtaSchema schema, Cube cube, DimensionHierarchy dh) throws ASException {
		String spaceName = makeMetricSchemaName(schema, cube, dh);
		Space space = null;
		if ((space = schemaSpaceMap.get(spaceName)) != null) {
			if (LOGGER.isEnabledFor(Level.DEBUG)) {
				LOGGER.log(Level.DEBUG, "Schema Already exists for Metric : %s", spaceName);
			}
			return space;
		}
		SpaceDef spacedef = ASResourceFactory.configureSpace(spaceName, config);
		
		Iterator<Dimension> i = dh.getDimensions().iterator();
		List<String> dimNames = new ArrayList<String>();
		List<IndexDef> dimIndexes = new ArrayList<IndexDef>();
		for(Dimension dimension : dh.getDimensions()){
			// TODO:Dimension value can be derived from attribute. hence its
			// datatype could be different?;
			dimNames.add(dimension.getName());
			addNullableField(spacedef, dimension.getName(), getASDataType(dimension.getAssociatedAttribute().getDataType()));

			// add index
			IndexDef indexDef = IndexDef.create(dimension.getName() + "_index");
			indexDef.setFieldNames(dimension.getName());
			dimIndexes.add(indexDef);
		}

		addNullableField(spacedef, ASPersistenceService.DIMENSION_LEVEL_NAME_FIELD, FieldType.STRING);
		
		if (usePK) {
			addNonNullableField(spacedef, ASPersistenceService.METRIC_KEY_FIELD, FieldType.STRING);
			spacedef.setKey(ASPersistenceService.METRIC_KEY_FIELD);

		} else {
			String[] kf1 = new String[dimNames.size() + 1];
			kf1[0] = ASPersistenceService.DIMENSION_LEVEL_NAME_FIELD;
			for (int ii = 0; ii < dimNames.size(); ii++) {
				kf1[ii + 1] = dimNames.get(ii);
			}
			spacedef.setKey(kf1);
		}

		for (Measurement measurement : dh.getMeasurements()) {
			MetricFunctionDescriptor md = measurement.getMetricFunctionDescriptor();
			FieldType ft = null;
			if (!md.isMultiValued()) {
				//ft = getASDataType(md.getMetricDataType());
				DataType dataType = measurement.getDataType();
				if(LOGGER.isEnabledFor(Level.INFO)) {
					LOGGER.log(Level.DEBUG, "dh: " +dh.getName()+", measurement "+ measurement.getName()+ "-> datatype: "+dataType+ "");
				}
				ft = getASDataType(measurement.getDataType());
			} else {
				// multi-valued will be stored as Tuple {value1=v1,
				// value2=v2...}
				ft = FieldType.BLOB;
			}
			addNullableField(spacedef, measurement.getName(), ft);
			for (FunctionParam param : md.getFunctionContexts()) {
				addNullableField(spacedef, measurement.getName() + ASPersistenceService.SEP + param.getName(),
						getASDataType(param.getDataType()));
			}
		}
		addNonNullableField(spacedef, ASPersistenceService.UPDATED_DATE_TIME_FIELD, FieldType.DATETIME);
		addNonNullableField(spacedef, ASPersistenceService.CREATED_DATE_TIME_FIELD, FieldType.DATETIME);
		
		if(resourceFactory.isCreateIndex()) {
			space = resourceFactory.defineSpace(spaceName, spacedef, dimIndexes, shouldSeed, true);	
		}
		else {
		space = resourceFactory.defineSpace(spaceName, spacedef, shouldSeed, true);
		}
		
		schemaSpaceMap.put(spaceName, space);
		if (LOGGER.isEnabledFor(Level.INFO)) {
			LOGGER.log(Level.INFO, "Schema created for metric : %s", spaceName);
		}
		return space;

	}

    private String makeMetricSchemaName(RtaSchema schema, Cube cube,
                                        DimensionHierarchy dh) {
        return makeMetricSchemaName(schema.getName(), cube.getName(),
                dh.getName());
    }

    private String makeMetricSchemaName(String schemaName, String cubeName, String dhName) {
        String spaceName = ASPersistenceService.METRIC_SPACE_PREFIX
                + schemaName + ASPersistenceService.SEP + cubeName + ASPersistenceService.SEP + dhName;
        spaceName = removeSplChars(spaceName);
        return spaceName;
    }
    
    private String makeRuleMetricSchemaName(String schemaName, String cubeName, String dhName) {
        String spaceName = ASPersistenceService.RULE_METRIC_SPACE_PREFIX
                + schemaName + ASPersistenceService.SEP + cubeName + ASPersistenceService.SEP + dhName;
        spaceName = removeSplChars(spaceName);
        return spaceName;
    }


    private String makeFactSpaceName(String schemaName) {
        String spaceName = ASPersistenceService.FACT_SPACE_PREFIX + schemaName;
        spaceName = removeSplChars(spaceName);
        return spaceName;
    }

    private String makeMetricFactSpaceName(RtaSchema schema, Cube cube, DimensionHierarchy dh) {
        return makeMetricFactSpaceName(schema.getName(), cube.getName(), dh.getName());
    }
    
    private String makeRuleMetricSchemaName(RtaSchema schema, Cube cube, DimensionHierarchy dh) {
        return makeRuleMetricSchemaName(schema.getName(), cube.getName(), dh.getName());
    }

    private String makeMetricFactSpaceName(String schemaName, String cubeName, String dhName) {
        return removeSplChars(ASPersistenceService.METRICS_FACT_SPACE_PREFIX
                + schemaName + ASPersistenceService.SEP + cubeName + ASPersistenceService.SEP + dhName);
    }

    private FieldType getASDataType(DataType dataType) {
        FieldType ft = null;
        if (dataType == null) {
            return FieldType.BOOLEAN;
        }
        switch (dataType) {
            case DOUBLE:
                ft = FieldType.DOUBLE;
                break;
            case INTEGER:
                ft = FieldType.INTEGER;
                break;
            case LONG:
                ft = FieldType.LONG;
                break;
            case STRING:
                ft = FieldType.STRING;
                break;
            case BOOLEAN:
            	ft = FieldType.BOOLEAN;
        }
        return ft;
    }

    private String removeSplChars(String str) {
        return str.replaceAll("-", "_");
    }

    @Override
    public void onCreate(MetadataElement metadataElement) {
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Creating Tibco Data Grid Schema..");
        }
        try {
            if (metadataElement instanceof RtaSchema) {
                RtaSchema schema = (RtaSchema) metadataElement;
                createSchema(schema);
            }
            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Creating Tibco Data Grid Schema Complete.");
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "Error while creating Tibco DataGrid Schema.", e);
        }
    }

    @Override
    public void onUpdate(MetadataElement metadataElement) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onDelete(MetadataElement metadataElement) {
        // TODO Auto-generated method stub

    }

    Space getMetricSchema(String schemaName, String cubeName,
                          String dimensionHierarchyName) throws Exception {
        String spaceName = makeMetricSchemaName(schemaName, cubeName, dimensionHierarchyName);
        Space space = null;
        if ((space = schemaSpaceMap.get(spaceName)) == null) {
            throw new Exception(String.format("Metric Space Schema not defined for schema : %s, cube : %s, hierarchy : %s", schemaName, cubeName
                    , dimensionHierarchyName));
        }
        return space;
    }
    
	Space getRuleMetricSchema(String schemaName, String cubeName, String dimensionHierarchyName) throws Exception {
		String spaceName = makeRuleMetricSchemaName(schemaName, cubeName, dimensionHierarchyName);
		Space space = null;
		if ((space = schemaSpaceMap.get(spaceName)) == null) {
			throw new Exception(String.format(
					"Rule Metric Space Schema not defined for schema : %s, cube : %s, hierarchy : %s", schemaName, cubeName,
					dimensionHierarchyName));
		}
		return space;
	}

    Space getFactSchema(String schemaName) throws Exception {
        String factSchemaName = makeFactSpaceName(schemaName);
        Space space = null;
        if ((space = schemaSpaceMap.get(factSchemaName)) == null) {
            throw new Exception(String.format("Fact Space Schema not defined for schema : %s", schemaName));
        }
        return space;
    }
    
    Space getRuleMetricSchema(MetricKey mKey) throws Exception {
    	RtaSchema schema = ModelRegistry.INSTANCE.getRegistryEntry(mKey.getSchemaName());
        Cube cube = schema.getCube(mKey.getCubeName());
        DimensionHierarchy dh = cube.getDimensionHierarchy(mKey.getDimensionHierarchyName());
        String spaceName = makeRuleMetricSchemaName(schema, cube, dh);
        Space space = null;
        if ((space = schemaSpaceMap.get(spaceName)) == null) {
            throw new Exception(String.format("Rule Metric Space Schema not defined for schema : %s, cube : %s ", schema.getName(), cube.getName()));
        }
        return space;
    }

    Space getMetricFactSchema(MetricNode metricNode) throws Exception {
        MetricKey mKey = (MetricKey) metricNode.getKey();
        RtaSchema schema = ModelRegistry.INSTANCE.getRegistryEntry(mKey
                .getSchemaName());
        Cube cube = schema.getCube(mKey.getCubeName());
//		Measurement measurement = schema.getMeasurement(mKey.getMeasurementName());
        DimensionHierarchy dh = cube.getDimensionHierarchy(mKey.getDimensionHierarchyName());
        String spaceName = makeMetricFactSpaceName(schema, cube, dh);
        Space space = null;
        if ((space = schemaSpaceMap.get(spaceName)) == null) {
            throw new Exception(String.format("MetricFact Space Schema not defined for schema : %s, cube : %s ", schema.getName(), cube.getName()));
        }
        return space;
    }

    Space getProcessedFactsSchema(String schemaName, String cubeName,
                                  String hierarhyName) throws Exception {
        String spaceName = makeCompletedFactsSchemaName(schemaName, cubeName, hierarhyName);
        Space space = null;
        if ((space = schemaSpaceMap.get(spaceName)) == null) {
            throw new Exception(String.format("Processed Facts Space Schema not defined %s ", spaceName));
        }
        return space;
    }

    private String makeCompletedFactsSchemaName(String schemaName, String cubeName, String dhName) {
        String spaceName = ASPersistenceService.PROCESSED_FACTS +
                schemaName + ASPersistenceService.SEP + cubeName + ASPersistenceService.SEP + dhName;
        spaceName = removeSplChars(spaceName);
        return spaceName;
    }

	Space getOrCreateSessionSchema() throws ASException {
		String spaceName = ASPersistenceService.SESSION_INFO_SPACE_NAME;
		Space space = schemaSpaceMap.get(spaceName);
		if(space != null){
			return space;
		}
		SpaceDef spaceDef = ASResourceFactory.configureSpace(spaceName, config);
		addNonNullableField(spaceDef, ASPersistenceService.SESSION_ID_FIELD, FieldType.STRING);
		addNonNullableField(spaceDef, ASPersistenceService.SESSION_NAME_FIELD, FieldType.STRING);
		addNullableField(spaceDef, ASPersistenceService.SESSION_RULE_NAME_FIELD, FieldType.STRING);
		addNullableField(spaceDef, ASPersistenceService.SESSION_RULE_DETAIL_FIELD, FieldType.STRING);
		addNullableField(spaceDef, ASPersistenceService.SESSION_QUERY_NAME_FIELD, FieldType.STRING);
		addNullableField(spaceDef, ASPersistenceService.SESSION_QUERY_DETAIL_FIELD, FieldType.STRING);
		spaceDef.setKey(ASPersistenceService.SESSION_ID_FIELD, ASPersistenceService.SESSION_QUERY_NAME_FIELD);
		space = resourceFactory.defineSpace(spaceName, spaceDef, shouldSeed, true);
		schemaSpaceMap.put(spaceName, space);
		if (LOGGER.isEnabledFor(Level.INFO)) {
			LOGGER.log(Level.INFO, "Schema created for metric : %s", spaceName);
		}
		return space;
	}
}
