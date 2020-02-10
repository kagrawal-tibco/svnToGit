package com.tibco.rta.service.persistence.as;

import com.tibco.as.space.ASException;
import com.tibco.as.space.Space;
import com.tibco.as.space.Tuple;
import com.tibco.as.space.browser.Browser;
import com.tibco.as.space.browser.BrowserDef;
import com.tibco.as.space.browser.BrowserDef.BrowserType;
import com.tibco.as.space.browser.BrowserDef.DistributionScope;
import com.tibco.as.space.browser.BrowserDef.TimeScope;
import com.tibco.rta.Fact;
import com.tibco.rta.MetricKey;
import com.tibco.rta.common.registry.ModelRegistry;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.Cube;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.runtime.model.MetricNode;

class FactNodeBrowser implements com.tibco.rta.query.Browser<Fact> {
	
    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_PERSISTENCE.getCategory());

	
	MetricNode metricNode;
	Space metricFactSpace;
	Space factSpace;
	Browser browser;
	ASPersistenceService pServ;
	Fact fact;
	boolean isStopped;
	
	FactNodeBrowser (ASPersistenceService pServ, MetricNode metricNode) throws Exception {
		this.pServ = pServ;
		this.metricNode = metricNode;
		
		metricFactSpace = pServ.getSchemaManager().getOrCreateMetricFactSchema(metricNode);
		
		MetricKey mKey = (MetricKey) metricNode.getKey();
		
		RtaSchema schema = ModelRegistry.INSTANCE.getRegistryEntry(mKey.getSchemaName());
		Cube cube= schema.getCube(mKey.getCubeName());
		DimensionHierarchy dh = cube.getDimensionHierarchy(mKey.getDimensionHierarchyName());

//		Measurement measurement = cube.getMeasurement();
		factSpace = pServ.getSchemaManager().getOrCreateFactSchema(dh.getOwnerSchema());
		
		BrowserType browserType = BrowserType.GET;
		BrowserDef def = BrowserDef.create();
		def.setDistributionScope(DistributionScope.ALL);
		def.setTimeScope(TimeScope.SNAPSHOT);

		String query = null;
		
		StringBuilder strBldr = new StringBuilder();
//		strBldr.append (ASPersistenceService.DIMHR_NAME + " = \"" + dh.getName() + "\"");
		MetricKey mn = (MetricKey) metricNode.getKey();
		strBldr.append (ASPersistenceService.DIMENSION_LEVEL_NAME_FIELD + " = \"" + mn.getDimensionLevelName() + "\"");

		for (int i=0; i<mn.getDimensionNames().size(); i++) {
			String dimName = mn.getDimensionNames().get(i);
			Object value = mn.getDimensionValue(dimName);
			if (value instanceof String) {
				strBldr.append(" and " + dimName + " = \"" + value + "\"");
			} else if (value != null) {
				strBldr.append(" and " + dimName + " = " + value);
			} else {
				strBldr.append(" and " + dimName + " is null ");
			}
		}
		
		query = strBldr.toString();
		if (LOGGER.isEnabledFor(Level.DEBUG)) {
			LOGGER.log(Level.DEBUG, "Making AS query: %s", query);
		}
		browser = metricFactSpace.browse(browserType, def, query);
		
	}

	@Override
	public boolean hasNext() {
		try {
			if (isStopped) {
				return false;
			}
			Tuple factKeyTuple = browser.next();
			if (factKeyTuple != null) {
				createFactNode(factKeyTuple);
				return true;
			} else {
				browser.stop();
				isStopped = true;
				return false;
			}
		} catch (ASException e) {
			LOGGER.log(Level.ERROR, "Error while getting next element from browser", e);
		}
		return false;
	}

	@Override
	public Fact next() {
		if (isStopped) {
			return null;
		}
		return fact;
	}

	@Override
	public void remove() {
		//no-op
	}

	@Override
	public void stop() {
		try {
			browser.stop();
		} catch (ASException e) {
			// TODO Auto-generated catch block
			LOGGER.log(Level.ERROR, "Error while stopping the fact node broser", e);
		}
		isStopped = true;
	}
	
	private Fact createFactNode(Tuple factKeyTuple) {
		try {
			if (factKeyTuple != null) {
				String factKey = factKeyTuple.getString(ASPersistenceService.FACT_KEY_FIELD);
				if (factKey != null) {
					fact = pServ.getFactFromFactTuple(factSpace, factKey);
				}
			}
			return fact;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
