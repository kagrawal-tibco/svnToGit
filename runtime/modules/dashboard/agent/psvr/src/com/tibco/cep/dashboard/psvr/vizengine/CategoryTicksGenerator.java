package com.tibco.cep.dashboard.psvr.vizengine;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.MALFieldMetaInfo;
import com.tibco.cep.dashboard.psvr.mal.URIHelper;
import com.tibco.cep.dashboard.psvr.mal.model.MALDataConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALTwoDimSeriesConfig;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.vizengine.formatters.DataConfigVisitor;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

public class CategoryTicksGenerator {

	private boolean duplicatesAllowed;

	private CategoryTickCreator tickCreator;

	private Comparator<CategoryTick> comparator;

	private Set<CategoryTick> ticks;

	public CategoryTicksGenerator(boolean duplicatesAllowed, Comparator<CategoryTick> comparator, CategoryTickCreator tickCreator) {
		this.duplicatesAllowed = duplicatesAllowed;
		this.tickCreator = tickCreator;
		this.comparator = comparator;
		if (this.comparator == null) {
			ticks = new LinkedHashSet<CategoryTick>();
		} else {
			ticks = new TreeSet<CategoryTick>(this.comparator);
		}
	}

	public Map<CategoryTick,Tuple> generate(Logger logger, MALTwoDimSeriesConfig seriesConfig, DataConfigVisitor dataConfigVisitor, List<Tuple> tuples, CategoryAxisMerger merger, PresentationContext pCtx) throws MALException, ElementNotFoundException, VisualizationException {
		//get the category data config
		MALDataConfig tickDataConfig = seriesConfig.getCategoryDataConfig();
		// get the category field from the category data configuration
		MALFieldMetaInfo tickSrcFld = tickDataConfig == null ? null : pCtx.resolveFieldRef(tickDataConfig.getExtractor().getSourceField());
		Map<CategoryTick, Tuple> buckets = this.comparator == null ? new LinkedHashMap<CategoryTick, Tuple>() : new TreeMap<CategoryTick, Tuple>(comparator);
		Set<CategoryTick> seriesTicks = this.comparator == null ? new LinkedHashSet<CategoryTick>() : new TreeSet<CategoryTick>(comparator);
		for (Tuple tuple : tuples) {
			//register the tuple with category tick generator, this creates the tick and also determines which tuple to use
			String seriesCfgId = seriesConfig.getId();
			// default the tick label to series config id
			String tickLabel = seriesCfgId;
			// default the tick value to series config id
			String tickValue = seriesCfgId;
			if (tickDataConfig != null) {
				//get the real label for the tuple using the tick data config
				tickLabel = DataConfigHandler.getInstance().getDisplayValue(tickDataConfig, tuple, dataConfigVisitor, pCtx);
				//get the real value for the tuple using the tick source field
				tickValue = tuple.getFieldValueByID(tickSrcFld.getId()).toString();
			}
			// create the category tick
			CategoryTick tick = tickCreator.getCategoryTick(tickLabel, tickValue, tuple);
			if (logger.isEnabledFor(Level.DEBUG) == true) {
				logger.log(Level.DEBUG, "Created "+tick+" for "+tuple+" for "+URIHelper.getURI(seriesConfig));
			}
			// update the category tick , if it is colliding and allow duplicates is on
			while (duplicatesAllowed == true && buckets.containsKey(tick) == true) {
				tickCreator.updateCollidingTick(tick, tickValue, tuple);
				if (logger.isEnabledFor(Level.DEBUG) == true) {
					logger.log(Level.DEBUG, "Updated "+tick+" for "+tuple+" for "+URIHelper.getURI(seriesConfig));
				}
			}
			seriesTicks.add(tick);
			Tuple existingTuple = buckets.get(tick);
			if (existingTuple != null) {
				if (tuple.getTimestamp() >= existingTuple.getTimestamp()) {
					//the incoming tuple is newer then the existing tuple, replace it
					buckets.put(tick, tuple);
				}
			}
			else {
				buckets.put(tick, tuple);
			}
		}
		if (merger != null) {
			merger.merge(ticks, buckets, seriesConfig, pCtx);
		}
		else {
			ticks.addAll(seriesTicks);
		}
		return buckets;
	}

	public Set<CategoryTick> getTicks() {
		return ticks;
	}

}