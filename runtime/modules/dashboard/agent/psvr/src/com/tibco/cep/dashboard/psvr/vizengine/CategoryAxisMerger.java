package com.tibco.cep.dashboard.psvr.vizengine;

import java.util.Map;
import java.util.Set;

import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.psvr.mal.model.MALTwoDimSeriesConfig;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;

public interface CategoryAxisMerger {

	public void merge(Set<CategoryTick> master, Map<CategoryTick, Tuple> buckets, MALTwoDimSeriesConfig seriesConfig, PresentationContext pCtx) throws VisualizationException;

}