package com.tibco.rta.client.utils;

import java.util.List;

import com.tibco.rta.Metric;
import com.tibco.rta.RtaException;
import com.tibco.rta.RtaSession;
import com.tibco.rta.impl.MetricKeyImpl;
import com.tibco.rta.model.Attribute;
import com.tibco.rta.model.Cube;
import com.tibco.rta.model.DataType;
import com.tibco.rta.model.Dimension;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.Measurement;
import com.tibco.rta.model.MetadataElement;
import com.tibco.rta.model.MetricFunctionDescriptor;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.rule.RuleDef;
import com.tibco.rta.model.serialize.impl.ModelSerializationConstants;
import com.tibco.rta.query.FilterKeyQualifier;
import com.tibco.rta.query.MetricFieldTuple;
import com.tibco.rta.query.MetricQualifier;
import com.tibco.rta.query.QueryByFilterDef;
import com.tibco.rta.query.QueryDef;
import com.tibco.rta.query.filter.Filter;
import com.tibco.rta.query.filter.LogicalFilter;
import com.tibco.rta.query.filter.NotFilter;
import com.tibco.rta.query.filter.RelationalFilter;
import com.tibco.rta.query.impl.QueryImpl;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 11/5/13
 * Time: 9:23 AM
 * Validations utility class to be used by client side.
 */
public class ModelValidationUtils {

    /**
     * @param query
     * @param session
     * @throws Exception
     */
    public static void validateQuery(QueryImpl query, RtaSession session) throws Exception {
        validateJavaIdentifier(query.getName());

        QueryDef queryDef = null;
        if (query.getQueryByFilterDef() != null) {
            queryDef = query.getQueryByFilterDef();
        } else if (query.getQueryByKeyDef() != null) {
            queryDef = query.getQueryByKeyDef();
        }

        if (queryDef != null) {
            String ownerSchemaName = query.getOwnerSchemaName();
            RtaSchema schema = session.getSchema(ownerSchemaName);

            Cube cube = schema.getCube(queryDef.getCubeName());
            if (cube == null) {
                throw new Exception(String.format("Cube : [%s] not found in schema : [%s]", queryDef.getCubeName(), schema.getName()));
            }

            DimensionHierarchy dh = cube.getDimensionHierarchy(queryDef.getHierarchyName());
            if (dh == null) {
                throw new Exception(String.format("Hierarchy : [%s] not found in cube : [%s]", queryDef.getHierarchyName(), cube.getName()));
            }

            if (queryDef instanceof QueryByFilterDef) {
                Filter filter = ((QueryByFilterDef) queryDef).getFilter();
                if (filter instanceof LogicalFilter) {
                    LogicalFilter logicalFilter = (LogicalFilter) filter;
                    // Get the children
                    Filter[] childFilters = logicalFilter.getFilters();
                    for (Filter childFilter : childFilters) {
                        validateFilter(childFilter, dh, session);
                    }
                }
            }
        }
    }

    public static void validateRule(RuleDef ruleDef) throws Exception {
        validateJavaIdentifier(ruleDef.getName());
    }

    private static void validateJavaIdentifier(String name) throws Exception {
        char[] nameChars = name.toCharArray();
        if (nameChars.length > 0) {
            char firstSymbol = nameChars[0];
            if (!Character.isJavaIdentifierStart(firstSymbol)) {
                throw new Exception(String.format("Not valid Name [%s]", name));
            }
            for (char symbol : nameChars) {
                if (!Character.isJavaIdentifierPart(symbol)) {
                    throw new Exception(String.format("Not valid Name [%s]", name));
                }
            }
        } else {
            throw new Exception(String.format("Not valid Name [%s]", name));
        }
    }

    private static void validateFilter(Filter filter, DimensionHierarchy dh, RtaSession session) throws Exception {
        if (filter instanceof LogicalFilter) {
            LogicalFilter logicalFilter = (LogicalFilter) filter;
            // Get the children
            Filter[] childFilters = logicalFilter.getFilters();
            for (Filter childFilter : childFilters) {
                validateFilter(childFilter, dh, session);
            }
        } else if (filter instanceof NotFilter) {
            Filter baseFilter = ((NotFilter) filter).getBaseFilter();
            validateFilter(baseFilter, dh, session);
        } else if (filter instanceof RelationalFilter) {
            RelationalFilter rFilter = (RelationalFilter) filter;

            if (rFilter.getKeyQualifier() != null) {
                FilterKeyQualifier keyQualifier = rFilter.getKeyQualifier();
                if (keyQualifier.equals(FilterKeyQualifier.DIMENSION_NAME)) {
                    validateDimesion(rFilter, dh);
                } else if (keyQualifier.equals(FilterKeyQualifier.MEASUREMENT_NAME)) {
                    validateMeasurement(rFilter, dh, session);
                }
            }

            if (rFilter.getMetricQualifier() != null) {
                MetricQualifier metricQualifier = rFilter.getMetricQualifier();
                if (metricQualifier.equals(MetricQualifier.DIMENSION_LEVEL)) {
                    validateDimesionLevel(rFilter, dh);
                }
            }

        }
    }

	private static DataType getStorageDataType(MetadataElement metaElem, MetricFunctionDescriptor md) {
		String dataType = metaElem.getProperty(ModelSerializationConstants.ATTR_STORAGE_DATATYPE_NAME);
		if (dataType!= null && dataType.length() != 0) {
			return DataType.valueOf(dataType);
		} else if (metaElem instanceof Attribute) {
			return ((Attribute)metaElem).getDataType();
		} else if(metaElem instanceof Measurement) {
			return md.getMetricDataType();
		}
		return null;
	}

    private static void validateMeasurement(RelationalFilter rFilter, DimensionHierarchy dh, RtaSession session) throws Exception {
        String name = rFilter.getKey();
        String dataType = rFilter.getValue().getClass().getSimpleName();

        Measurement m = dh.getMeasurement(name);
        MetricFunctionDescriptor md = m.getMetricFunctionDescriptor();

        if (dh.getMeasurement(name) == null) {
            throw new Exception(String.format("Measurement : [%s] does not exist in hierarchy : [%s]", name, dh.getName()));
        }

        if (md != null && !dataType.equalsIgnoreCase(getStorageDataType(m, md).name())) {
            if ((md.getMetricDataType().name().equalsIgnoreCase(DataType.DOUBLE.name())) && (dataType.equalsIgnoreCase(DataType.INTEGER.name()) || dataType.equalsIgnoreCase(DataType.LONG.name()))) {

            } else if ((md.getMetricDataType().name().equalsIgnoreCase(DataType.LONG.name())) && (dataType.equalsIgnoreCase(DataType.INTEGER.name()))) {

            } else {
                throw new Exception(String.format("Invalid DataType : [%s] for dimension value : [%s]. Should be [%s]", dataType, rFilter.getValue(), md.getMetricDataType().name()));
            }
        }

    }

    private static void validateDimesion(RelationalFilter rFilter, DimensionHierarchy dh) throws Exception {
        String value = rFilter.getKey();
        Dimension d = dh.getDimension(value);
        Attribute a = d.getAssociatedAttribute();

        if (dh.getDimension(value) == null) {
            throw new Exception(String.format("Dimension : [%s] does not exist in hierarchy : [%s]", value, dh.getName()));
        }

        if (rFilter.getValue() != null) {
            String dataType = rFilter.getValue().getClass().getSimpleName();

            if (!dataType.equalsIgnoreCase(a.getDataType().toString())) {
                throw new Exception(String.format("Invalid DataType : [%s] for measurement value : [%s]. Should be [%s]", dataType, rFilter.getValue(), a.getDataType()));
            }
        }
    }

    private static void validateDimesionLevel(RelationalFilter rFilter, DimensionHierarchy dh) throws Exception {
        Object value = rFilter.getValue();
        if (dh.getDimension((String) value) == null) {
            throw new Exception(String.format("Dimension : [%s] does not exist in hierarchy : [%s]", value, dh.getName()));
        }
    }

    public static void validateAttributeOrderedList(List<MetricFieldTuple> orderByList, Metric metric,
                                                    RtaSession session) throws Exception {
        String ownerSchemaName = ((MetricKeyImpl) metric.getKey()).getSchemaName();
        RtaSchema schema = session.getSchema(ownerSchemaName);

        for (MetricFieldTuple metricFieldTuple : orderByList) {
            if (metricFieldTuple.getKeyQualifier() != null) {
                if (schema.getAttribute(metricFieldTuple.getKey()) == null) {
                    throw new Exception(String.format("Attribute : [%s] not found in schema : [%s]",
                            metricFieldTuple.getKey(), ownerSchemaName));
                }
            }
        }
    }

    public static void validateDimensionOrderedList(List<MetricFieldTuple> orderByList, Metric metric,
                                                    RtaSession session) throws Exception {
        String ownerSchemaName = ((MetricKeyImpl) metric.getKey()).getSchemaName();
        RtaSchema schema = session.getSchema(ownerSchemaName);
        for (MetricFieldTuple metricFieldTuple : orderByList) {
            if (metricFieldTuple.getKeyQualifier() != null) {
                if (schema.getDimension(metricFieldTuple.getKey()) == null) {
                    throw new Exception(String.format("Dimension : [%s] not found in schema : [%s]",
                            metricFieldTuple.getKey(), ownerSchemaName));
                }
            }
        }
    }
}
