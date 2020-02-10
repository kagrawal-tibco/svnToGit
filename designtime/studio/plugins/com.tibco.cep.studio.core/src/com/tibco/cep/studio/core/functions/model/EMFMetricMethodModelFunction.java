package com.tibco.cep.studio.core.functions.model;

import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.xml.data.primitive.ExpandedName;

public class EMFMetricMethodModelFunction extends EMFConceptMethodModelFunction {

	public static final String COMPUTE_FUNCTION_NAME = "compute";

	public static final String LOOKUP_FUNCTION_NAME = "lookup";

	public static final String GET_METRIC_EXT_ID_FUNCTION_NAME = "getMetricExtId";

	public static final String DELETE_FUNCTION_NAME = "delete";

	private static String[] AVAILABLE_FUNCTIONS = new String[] {
		COMPUTE_FUNCTION_NAME,
		LOOKUP_FUNCTION_NAME,
		GET_METRIC_EXT_ID_FUNCTION_NAME,
		DELETE_FUNCTION_NAME
	};

	public EMFMetricMethodModelFunction(Concept entity, ExpandedName methodName, String fnName, String[] params, Class[] types, Class returnType) {
		super(entity, methodName, fnName, params, types, returnType);
	}

	@Override
	String getDescription() {
		if (COMPUTE_FUNCTION_NAME.equals(fnName) == true){
			return "Creates or updates an instance of "+getModelName()+" based on group by field values";
		}
		else if (LOOKUP_FUNCTION_NAME.equals(fnName) == true){
			return "Searches for an instance of "+getModelName()+" based on group by field values. " +
					"Returns null, if an instance does not exist for the provided values. " +
					"The returned instance should only be used to read current values, not to modify them.";
		} else if (GET_METRIC_EXT_ID_FUNCTION_NAME.equals(fnName) == true) {
			return "Returns key for a metric. This key can be used as input for C_Lock and C_Unlock functions. ";
		}
		else if (DELETE_FUNCTION_NAME.equals(fnName) == true){
			return "Deletes an instance of "+getModelName()+" based on group by field values. " +
					"Returns null, if an instance does not exist for the provided values. ";
		}
		return super.getDescription();
	}

	public static String[] getAvailableFunctions() {
		return AVAILABLE_FUNCTIONS;
	}

}