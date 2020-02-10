package com.tibco.cep.dashboard.plugin.beviews.common.query;

import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.MALFieldMetaInfo;
import com.tibco.cep.dashboard.psvr.mal.model.MALSourceElement;
import com.tibco.cep.dashboard.psvr.mal.model.MALTwoDimSeriesConfig;

public class SeriesDataGenerationConfiguration {
	
	private static ThreadLocal<SeriesDataGenerationConfiguration> threadLocalComponent = new ThreadLocal<SeriesDataGenerationConfiguration>();
	
	private MALTwoDimSeriesConfig seriesConfig;

	private MALSourceElement sourceElement;

	private MALFieldMetaInfo categoryField;
	
	public SeriesDataGenerationConfiguration(MALTwoDimSeriesConfig seriesConfig, MALSourceElement sourceElement, MALFieldMetaInfo categoryField) throws MALException, ElementNotFoundException{
		this.seriesConfig = seriesConfig;
		this.sourceElement = sourceElement;
		this.categoryField = categoryField;
	}
	
	public final MALTwoDimSeriesConfig getSeriesConfig() {
		return seriesConfig;
	}

	public final MALSourceElement getSourceElement() {
		return sourceElement;
	}
	
	public final MALFieldMetaInfo getCategoryField() {
		return categoryField;
	}

	public void attachToCurrentThread() {
		threadLocalComponent.set(this);
	}
	
	public void deattachFromCurrentThread() {
		threadLocalComponent.remove();
	}
	
	public static SeriesDataGenerationConfiguration getInstanceInCurrentThread() {
		return threadLocalComponent.get();
	}
	
}