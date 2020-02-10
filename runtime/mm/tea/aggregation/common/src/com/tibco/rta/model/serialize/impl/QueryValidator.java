package com.tibco.rta.model.serialize.impl;

import com.tibco.rta.common.registry.ModelRegistry;
import com.tibco.rta.model.Cube;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.Measurement;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.query.QueryDef;

public class QueryValidator {

	public static QueryValidator INSTANCE = new QueryValidator();

	private QueryValidator() {
	}

	public void validateDimension(String dimensionName, RtaSchema schema, QueryDef queryDef) throws Exception {
		if (dimensionName.equals("")) {
			throw new Exception("Invalid Dimension:Dimension name is not specified for query:" + queryDef.getName());
		}
		if (!dimensionName.equals("root")) {
			if (schema.getDimension(dimensionName) == null) {
				throw new Exception("Invalid Dimension:" + dimensionName + " not deifned in schema:" + schema.getName() + "\nSpecify valid dimension name for query:" + queryDef.getName());
			}
		}
	}

	public void validateMeasurement(String measurementName, RtaSchema schema, QueryDef queryDef) throws Exception {
		if (measurementName.equals("")) {
			throw new Exception("Invalid Measurement:Measurement Name is not specified for query:" + queryDef.getName());
		}
		Measurement measurement = schema.getMeasurement(measurementName);
		if (measurement == null) {
			throw new Exception("Invalid Measurement:Measurement:" + measurementName + " is not defined inm schema:" + schema.getName() + "\nSpecify valid measurement name for query:" + queryDef.getName());
		}
	}

	public void validateHierarchy(String hierarchyName, Cube cube, QueryDef queryDef) throws Exception {
		if (hierarchyName.equals("")) {
			throw new Exception("Invalid Hierarchy:Dimension hierarchy Name is not specified for query:" + queryDef.getName());
		}
		DimensionHierarchy hierar = cube.getDimensionHierarchy(hierarchyName);
		if (hierar == null) {
			throw new Exception("Invalid Hierarchy:" + hierarchyName + " not defined in Cube:" + cube.getName() + "\nSpecify valid dimension hierarchy for query:" + queryDef.getName());
		}

	}

	public RtaSchema validateSchema(String schemaName, QueryDef queryDef) throws Exception {
		RtaSchema schema = null;
		if (schemaName.equals("")) {
			throw new Exception("Invalid Schema:Schema Name is not specified for for query:" + queryDef.getName());
		}
		ModelRegistry instance = ModelRegistry.INSTANCE;
		schema = instance.getRegistryEntry(schemaName);

		if (schema == null) {
			throw new Exception("Invalid Schema:Schema " + schemaName + " is not defined\nSpecify correct schema name forquery:" + queryDef.getName());
		}
		return schema;
	}

	public Cube validateCube(String cubeName, RtaSchema schema, QueryDef queryDef) throws Exception {
		if (cubeName.equals("")) {
			throw new Exception("Invalid Cube:Cube Name is not specified for query:" + queryDef.getName());
		}
		Cube cube = schema.getCube(cubeName);

		if (cube == null) {
			throw new Exception("Invalid Cube:" + cubeName + " not defined in Schema:" + schema.getName() + "\nSpecify valid cube name for query:" + queryDef.getName());
		}
		return cube;
	}
}
