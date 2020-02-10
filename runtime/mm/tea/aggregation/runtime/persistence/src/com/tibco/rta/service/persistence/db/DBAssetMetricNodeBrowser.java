package com.tibco.rta.service.persistence.db;

import com.tibco.rta.Fact;
import com.tibco.rta.KeyFactory;
import com.tibco.rta.common.registry.ModelRegistry;
import com.tibco.rta.impl.MetricKeyImpl;
import com.tibco.rta.log.Level;
import com.tibco.rta.model.Cube;
import com.tibco.rta.model.Dimension;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.TimeDimension;
import com.tibco.rta.runtime.model.impl.MetricNodeImpl;

public class DBAssetMetricNodeBrowser extends DBAbstractMetricNodeBrowser {
	Dimension lastDimension;
	private boolean isDistinctAsset = false;

	/**
	 * To get Matching Assets
	 */
	public DBAssetMetricNodeBrowser(DatabasePersistenceService pServ, String schemaName, String cubeName, String hierarchyName, Fact fact) throws Exception {
		super(pServ, schemaName, cubeName, hierarchyName);
		isDistinctAsset = false;
		RtaSchema schema = ModelRegistry.INSTANCE.getRegistryEntry(schemaName);
		Cube cube = schema.getCube(cubeName);
		DimensionHierarchy dh = cube.getDimensionHierarchy(hierarchyName);
		StringBuilder queryBldr = new StringBuilder("SELECT * FROM ");
		queryBldr.append(tableName);
		// get Last level of Dimension hierarchy
		// lastDimension = dh.getDimension(dh.getDepth() - 1);
		// lastDimension = dh.getDimension(level);
		// if (lastDimension != null){
		// queryBldr.append(" WHERE " + DBConstant.DIMENSION_LEVEL_NAME_FIELD + " ='");
		// queryBldr.append(lastDimension.getName() + "'");
		// }
		//
		String prefix = " AND ";
		queryBldr.append(" WHERE ");
		boolean isFirst = true;
		for (Dimension dm: dh.getDimensions()) {

			Object value = fact.getAttribute(dm.getAssociatedAttribute().getName());
			// only if the query is not for delete do we want to match the time dimension. For deletes, let delete everything across all time slots.

			if (dm instanceof TimeDimension) {
				TimeDimension td = (TimeDimension) dm;
				long timestamp = 0;
				if (value == null) {
					timestamp = System.currentTimeMillis();
				} else if (value instanceof Long) {
					timestamp = (Long) value;
				}
				value = td.getTimeUnit().getTimeDimensionValue(timestamp);
				if (isFirst) {
					queryBldr.append(dm.getName() + " = " + value);
					isFirst = false;
				} else {
					queryBldr.append(prefix + dm.getName() + " = " + value);
				}
			}

			// BALA: A null value indicates, dont care. Take null or not null values. Dont matter..
			// else if (value == null) {
			// //null value implies dont care, so take all values...
			// queryBldr.append(prefix + dm.getName() + " IS NOT NULL");
			// }
			else if (value == null) {
				// BALA: value indicates, dont care. Take null or not null values. Dont matter.. so do not add an and clause
				continue;
			} else {
				if (value instanceof String) {
					if (isFirst) {
						queryBldr.append(dm.getName() + " = '" + value + "'");
						isFirst = false;
					} else {
						queryBldr.append(prefix + dm.getName() + " = '" + value + "'");
					}
				} else {
					if (isFirst) {
						queryBldr.append(prefix + dm.getName() + " = " + value);
						isFirst = false;
					} else {
						queryBldr.append(prefix + dm.getName() + " = " + value);
					}
				}
			}

			// if (dm.getName().equals(dimensionName) && dimensionValue != null) {
			// if (dimensionValue instanceof String) {
			// queryBldr.append(prefix + dimensionName + " = '" + dimensionValue + "'");
			// } else {
			// queryBldr.append(prefix + dimensionName + " = " + dimensionValue);
			// }
			// } else {
			// queryBldr.append(prefix + dm.getName() + " IS NOT NULL");
			// }
			// // prefix = " AND ";
		}
		executeQuery(pServ, queryBldr.toString());
	}
	
//	/**
//	 * To get Distinct Assets
//	 */
//	public DBAssetMetricNodeBrowser(DatabasePersistenceService pServ, String schemaName, String cubeName,
//			String hierarchyName) throws Exception {
//		super(pServ, schemaName, cubeName, hierarchyName);
//		isDistinctAsset = true;
//		RtaSchema schema = ModelRegistry.INSTANCE.getRegistryEntry(schemaName);
//		Cube cube = schema.getCube(cubeName);
//		DimensionHierarchy dh = cube.getDimensionHierarchy(hierarchyName);
//		StringBuilder queryBldr = new StringBuilder("SELECT DISTINCT ");
//		String prefix = "";
//		for (Dimension dm : dh.getDimensions()) {
//			if (!(dm instanceof TimeDimension)) {
//				queryBldr.append(prefix + dm.getName());
//				prefix = ",";
//			}
//		}
//		queryBldr.append(" FROM " + tableName);
//		// get Last level of Dimension hierarchy
//		lastDimension = dh.getDimension(dh.getDepth() - 1);
//		if (lastDimension != null){
//			queryBldr.append(" WHERE " + DBConstant.DIMENSION_LEVEL_NAME_FIELD + " ='");
//			queryBldr.append(lastDimension.getName() + "'");
//		}
//		executeQuery(pServ, queryBldr.toString());
//	}	
	
	/* 
	 * Override this method, as ResultSet can have distinct selected columns.
	 * (non-Javadoc)
	 * @see com.tibco.rta.service.persistence.db.DBAbstractMetricNodeBrowser#fetchMetricNodeFromRS()
	 */
	@Override
	MetricNodeImpl fetchMetricNodeFromRS() throws Exception {
		if (!isDistinctAsset) {
			return super.fetchMetricNodeFromRS();
		}
		try {
			if (rs == null || !rs.next()) {
				return null;
			}
			RtaSchema schema = ModelRegistry.INSTANCE.getRegistryEntry(schemaName);
			Cube cube = schema.getCube(cubeName);
			DimensionHierarchy hierarchy = cube.getDimensionHierarchy(hierarchyName);
			MetricKeyImpl key = (MetricKeyImpl) KeyFactory.newMetricKey(schemaName, cubeName, hierarchyName, lastDimension.getName());
			for (Dimension dm : hierarchy.getDimensions()) {
				String dimName = dm.getName();
				if (!(dm instanceof TimeDimension)) {
					Object value = pService.getDimensionValue(dm, rs, dimName);					
					key.addDimensionValueToKey(dimName, value);
				} else {
					// add null values to rest of the dimensions
					key.addDimensionValueToKey(dimName, null);
				}
			}
			return new MetricNodeImpl(key);
		} catch (Exception e) {
			LOGGER.log(Level.ERROR, "An error occurred, while fetching Distinct Asset metric node from RS.", e);
		}
		return null;

	}
}
