package com.tibco.cep.dashboard.plugin.beviews.vizengine;

import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.plugin.beviews.mal.MALUtils;
import com.tibco.cep.dashboard.psvr.data.DataException;
import com.tibco.cep.dashboard.psvr.mal.model.MALCategoryGuidelineConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.types.SortEnum;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.util.ChangeableInteger;
import com.tibco.cep.dashboard.psvr.vizengine.CategoryTick;
import com.tibco.cep.dashboard.psvr.vizengine.CategoryTickComparator;
import com.tibco.cep.dashboard.psvr.vizengine.CategoryTickCreator;
import com.tibco.cep.dashboard.psvr.vizengine.CategoryTicksGenerator;
import com.tibco.cep.kernel.service.logging.Logger;

public class CategoryTicksGeneratorFactory {

	private static final CategoryTickCreator CATEGORY_TICK_CREATOR = new DefaultTickCreator();

	public static CategoryTicksGenerator create(Logger logger, MALComponent component, PresentationContext pCtx) throws DataException, PluginException {
		MALCategoryGuidelineConfig categoryGuideLineConfig = MALUtils.getCategoryGuideLineConfig(component);
		boolean duplicatesAllowed = categoryGuideLineConfig == null ? false : categoryGuideLineConfig.getDuplicatesAllowed();
		SortEnum sortEnum = categoryGuideLineConfig == null ? null : categoryGuideLineConfig.getSortOrder();
		if (sortEnum == null || sortEnum.getType() == SortEnum.UNSORTED_TYPE) {
			return new CategoryTicksGenerator(duplicatesAllowed, null, CATEGORY_TICK_CREATOR);
		}
		if (sortEnum.getType() == SortEnum.ASCENDING_TYPE) {
			return new CategoryTicksGenerator(duplicatesAllowed, new CategoryTickComparator(true), CATEGORY_TICK_CREATOR);
		}
		return new CategoryTicksGenerator(duplicatesAllowed, new CategoryTickComparator(false), CATEGORY_TICK_CREATOR);
	}

	private static class DefaultTickCreator implements CategoryTickCreator {

		@Override
		public CategoryTick getCategoryTick(String tickLabel, String tickValue, Tuple tuple) {
			return new IncrementingKeyCategoryTick(tickValue, tickLabel, tickValue);
		}

		@Override
		public void updateCollidingTick(CategoryTick tick, String tickValue, Tuple tuple) {
			IncrementingKeyCategoryTick incrementingKeyCategoryTick = (IncrementingKeyCategoryTick) tick;
			incrementingKeyCategoryTick.incrementId();
		}

		class IncrementingKeyCategoryTick extends CategoryTick {

			private String idPrefix;
			private ChangeableInteger idCounter;

			public IncrementingKeyCategoryTick(String idPrefix, String displayValue, String value) {
				super(idPrefix, displayValue, value);
				this.idPrefix = idPrefix;
				this.idCounter = new ChangeableInteger(0);
			}

			public void incrementId(){
				idCounter.increment();
				setId(idPrefix+idCounter);
			}
		}

	}

}