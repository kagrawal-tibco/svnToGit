package com.tibco.be.bemm.functions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.management.CacheInfo;
import com.tibco.cep.runtime.management.CacheTable;
import com.tibco.cep.runtime.model.event.SimpleEvent;

public class CachedObjectsMetricHandler extends MetricTypeHandler {

	private CacheTable cacheTable;
	private String clusterName = null;

	public CachedObjectsMetricHandler() {
		super();
	}

	protected void init() throws IOException {
		cacheTable = ClusterInfoProvider.getInstance().getCacheTable(clusterName);
	}

    protected void setClusterName (String clusterName) {
        this.clusterName = clusterName;
    }

	@Override
	protected SimpleEvent[] populate(EventCreator eventCreator) throws IOException {
		Collection<CacheInfo> cacheInfos = cacheTable.getCacheInfos();
		List<SimpleEvent> events = new ArrayList<SimpleEvent>();
		for (CacheInfo cacheInfo : cacheInfos) {
			SimpleEvent event = eventCreator.create();
			if (event != null){
				String propertyName = null;
				try {
					propertyName = "cObjectName";
					String[] nameElements = cacheInfo.getName().toString().split("\\$");
					String cacheInfoName = nameElements[nameElements.length-1];
					event.setProperty(propertyName, cacheInfoName);
					
					propertyName = "size";
					event.setProperty(propertyName, cacheInfo.getSize());
					
					propertyName = "numberOfGets";
					event.setProperty(propertyName, cacheInfo.getNumberOfGets());
					
					propertyName = "numberofPuts";
					event.setProperty(propertyName, cacheInfo.getNumberOfPuts());
					
					propertyName = "avgGetTimeMillis";
					event.setProperty(propertyName, cacheInfo.getAvgGetTimeMillis());
					
					propertyName = "avgPutTimeMillis";
					event.setProperty(propertyName, cacheInfo.getAvgPutTimeMillis());

					propertyName = "hitRatio";
					event.setProperty(propertyName, cacheInfo.getHitRatio());
					
					propertyName = "maxSize";
					event.setProperty(propertyName, cacheInfo.getMaxSize());

					propertyName = "minSize";
					event.setProperty(propertyName, cacheInfo.getMinSize());
					
					propertyName = "expiryDelayMillis";
					event.setProperty(propertyName, cacheInfo.getExpiryDelayMillis());
					
					
				} catch (NoSuchFieldException e) {
					logger.log(Level.WARN, "could not find property named "+propertyName+" in "+event.getExpandedName());
					return null;
				} catch (IOException e) {
					throw e;					
				} catch (Exception e) {
					logger.log(Level.WARN, "could not set value for property named "+propertyName+" in "+event.getExpandedName());
					return null;
				}
				events.add(event);
			}
		}
		return events.toArray(new SimpleEvent[events.size()]);
	}

}
