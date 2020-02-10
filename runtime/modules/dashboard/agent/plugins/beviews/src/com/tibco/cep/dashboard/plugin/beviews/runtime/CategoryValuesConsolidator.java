package com.tibco.cep.dashboard.plugin.beviews.runtime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.tibco.cep.dashboard.psvr.data.DataChangeListener;
import com.tibco.cep.dashboard.psvr.data.DataException;
import com.tibco.cep.dashboard.psvr.data.DataSourceHandler;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALSeriesConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALVisualization;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationException;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * @author apatil
 *
 */
@SuppressWarnings({"rawtypes" , "unchecked"})
public final class CategoryValuesConsolidator {

	public static enum DIFFERENCE {
		NONE, ADD, ORDER, DELETE
	};

	private Logger logger;

	private MALComponent component;

	//Object can be DataColumn (chart) or ChangeableInteger (table)

	private Map categoryValuesMap;

	private ArrayList<DataSourceHandler> dataSrcHandlers;

	private DataChangeListener dataChangeListener;

	CategoryValuesConsolidator(Logger logger, MALComponent component, PresentationContext pCtx) throws DataException, PluginException, VisualizationException {
		this.logger = logger;
		this.component = component;
		this.dataSrcHandlers = new ArrayList<DataSourceHandler>();
		dataChangeListener = new CVConsolidatorDataChangeListener();
		registerDataChangeListeners(component, pCtx);
	}

	void registerDataChangeListeners(MALComponent component, PresentationContext pCtx) throws DataException, PluginException, VisualizationException {
		for (MALVisualization visualization : component.getVisualization()) {
			for (MALSeriesConfig seriesConfig : visualization.getSeriesConfig()) {
				DataSourceHandler dataSourceHandler = pCtx.getDataSourceHandler(seriesConfig);
				dataSrcHandlers.add(dataSourceHandler);
				dataSourceHandler.addDataChangeListener(dataChangeListener);
			}
		}
	}

	public void setCategoryValues(Map categoryDataColumns) {
		if (logger.isEnabledFor(Level.DEBUG) == true) {
			logger.log(Level.DEBUG, "Setting " + categoryDataColumns + " for " + component + "...");
		}
		this.categoryValuesMap = categoryDataColumns;
	}

	public Map getCategoryValuesMap() {
		return categoryValuesMap;
	}

	public DIFFERENCE compare(Collection categoryValues){
        // we check if the category values map is empty
        if (categoryValuesMap == null || categoryValuesMap.size() == 0) {
            // category values map is empty, check if the category values list if empty
            if (categoryValues == null || categoryValues.size() == 0) {
                // both the map and the list are empty , return NO_DIFFERENCE
                if (logger.isEnabledFor(Level.DEBUG) == true) {
                    logger.log(Level.DEBUG, "No difference found between " + categoryValuesMap + " and " + categoryValues + " for " + component + "...");
                }
                return DIFFERENCE.NONE;
            }
            if (logger.isEnabledFor(Level.DEBUG) == true) {
                logger.log(Level.DEBUG, "Found new values in " + categoryValues + " compared to " + categoryValuesMap + " for " + component + "...");
            }
            // list is not empty but the map is , so we return ADD_DIFFERENCE
            return DIFFERENCE.ADD;
        }
        // we have a non-empty category values map
        // we check each key in the map with the category values list
        List masterCategoryValues = new LinkedList(categoryValuesMap.keySet());
        if (masterCategoryValues.containsAll(categoryValues) == false) {
            if (logger.isEnabledFor(Level.DEBUG) == true) {
                logger.log(Level.DEBUG, "Found new values in " + categoryValues + " compared to " + categoryValuesMap + " for " + component + "...");
            }
            return DIFFERENCE.ADD;
        }
        masterCategoryValues.retainAll(categoryValues);
        if (areCollectionsSame(masterCategoryValues, categoryValues) == false) {
            if (logger.isEnabledFor(Level.DEBUG) == true) {
                logger.log(Level.DEBUG, "Found values in " + categoryValues + " in a different order compared to " + categoryValuesMap + " for " + component + "...");
            }
            return DIFFERENCE.ORDER;
        }
//         I have commented out the DELETE_DIFFERENCE logic , since it has loopholes
//         we could be working with a multi-series chart and each series could not non
//         overlapping values, in which case , we would return an invalid DELETE_DIFFERENCE
//         till we might a better way of doing this, we will avoid sending out a
//         DELETE_DIFFERENCE

/*         Iterator categoryValuesMapKeysIter = categoryDataColumns.keySet().iterator();
         while (categoryValuesMapKeysIter.hasNext()) {
        	 Object categoryValue = (Object) categoryValuesMapKeysIter.next();
        	 if (categoryValues.contains(categoryValue) == false) {
        		 //we have a value in
        		 if (logger.isEnabledFor(Level.DEBUG) == true) {
        			 logger.log(Level.DEBUG, "Found deleted value "+categoryValue+" compared to "+categoryDataColumns+" for "+component+"...");
        		 }
        		 return DIFFERENCE.DELETE;
        	 }
        }
*/

        if (logger.isEnabledFor(Level.DEBUG) == true) {
            logger.log(Level.DEBUG, "No difference found between " + categoryValuesMap + " and " + categoryValues + " for " + component + "...");
        }
        return DIFFERENCE.NONE;

    }

	private boolean areCollectionsSame(List masterCategoryValues, Collection categoryValues) {
		if (masterCategoryValues.size() != categoryValues.size()) {
			return false;
		}
		int i = 0;
		Iterator categoryValuesIter = categoryValues.iterator();
		while (categoryValuesIter.hasNext()) {
			Object categoryValue = categoryValuesIter.next();
			if (categoryValue.equals(masterCategoryValues.get(i)) == false) {
				return false;
			}
			i++;
		}
		return true;
	}

	public Map merge(Map categoryDataColumnsToBeMerged) {
		if (categoryValuesMap == null) {
			categoryValuesMap = categoryDataColumnsToBeMerged;
		} else if (categoryValuesMap instanceof TreeMap) {
			// since the map is of type treemap, the keys will get automatically
			// sorted
			if (logger.isEnabledFor(Level.DEBUG) == true) {
				logger.log(Level.DEBUG, "Merging " + categoryDataColumnsToBeMerged + " into Sort Based Map" + categoryValuesMap);
			}
			categoryValuesMap.putAll(categoryDataColumnsToBeMerged);
		} else {
			List recipientKeys = new LinkedList(categoryValuesMap.keySet());
			List mergingKeys = new LinkedList(categoryDataColumnsToBeMerged.keySet());
			if (logger.isEnabledFor(Level.DEBUG) == true) {
				logger.log(Level.DEBUG, "Attempting to merge " + mergingKeys + " into " + recipientKeys);
			}
			List mergedKeys = mergeKeys(recipientKeys, mergingKeys);
			if (logger.isEnabledFor(Level.DEBUG) == true) {
				logger.log(Level.DEBUG, "Merged " + mergingKeys + " into " + recipientKeys);
			}
			Map mergedCategoryValuesMap = new HashMap(categoryValuesMap);
			categoryValuesMap.clear();
			Iterator mergedKeysIter = mergedKeys.iterator();
			while (mergedKeysIter.hasNext()) {
				Object mergedKey = mergedKeysIter.next();
				Object mergedValue = mergedCategoryValuesMap.get(mergedKey);
				if (mergedValue == null) {
					mergedValue = categoryDataColumnsToBeMerged.get(mergedKey);
				}
				categoryValuesMap.put(mergedKey, mergedValue);
			}
		}
		return categoryValuesMap;
	}

	private List mergeKeys(List recipientKeys, List mergingKeys) {
		// we will handle an unique case where recipientKeys and mergingKeys are
		// the same except they are out of sequence
		if (recipientKeys.containsAll(mergingKeys) && recipientKeys.size() == mergingKeys.size()) {
			// we are working with the same set across the the whole chart, just
			// return the mergingkeys list
			return mergingKeys;
		}
		List mergedKeys = new LinkedList(recipientKeys);
		Iterator mergingKeysIter = mergingKeys.iterator();
		int missingKeyIndex = 0;
		while (mergingKeysIter.hasNext()) {
			Object mergingKey = mergingKeysIter.next();
			boolean mergeNotNeeded = mergedKeys.contains(mergingKey);
			if (mergeNotNeeded == true) {
				// check the sequence
				int rcptKeysIdx = recipientKeys.indexOf(mergingKey);
				int mergingKeysIdx = mergingKeys.indexOf(mergingKey);
				if (rcptKeysIdx != mergingKeysIdx) {
					mergedKeys.remove(mergingKey);
					mergeNotNeeded = false;
				}
			}
			if (mergeNotNeeded == false) {
				int injectionIndex = -1;
				int adjacentKeyIndex = missingKeyIndex - 1;
				if (adjacentKeyIndex >= 0) {
					Object keyBeforeMissingKey = mergingKeys.get(adjacentKeyIndex);
					injectionIndex = mergedKeys.indexOf(keyBeforeMissingKey) + 1;
					if (injectionIndex != -1 && logger.isEnabledFor(Level.DEBUG) == true) {
						logger.log(Level.DEBUG, "Found " + keyBeforeMissingKey + " as the key before " + mergingKey + ", injection index is " + injectionIndex);
					}
				}
				if (injectionIndex == -1) {
					adjacentKeyIndex = missingKeyIndex + 1;
					if (adjacentKeyIndex < mergingKeys.size()) {
						Object keyAfterMissingKey = mergingKeys.get(adjacentKeyIndex);
						injectionIndex = mergedKeys.indexOf(keyAfterMissingKey);
						if (injectionIndex != -1 && logger.isEnabledFor(Level.DEBUG) == true) {
							logger.log(Level.DEBUG, "Found " + keyAfterMissingKey + " as the key after " + mergingKey + ", injection index is " + injectionIndex);
						}
					}
				}
				if (injectionIndex < 0 || injectionIndex >= mergedKeys.size()) {
					if (logger.isEnabledFor(Level.DEBUG) == true) {
						logger.log(Level.DEBUG, "Appending " + mergingKey + " to end of recipient key list...");
					}
					mergedKeys.add(mergingKey);
				} else {
					mergedKeys.add(injectionIndex, mergingKey);
				}
				if (logger.isEnabledFor(Level.DEBUG) == true) {
					logger.log(Level.DEBUG, "Recipient key list is " + mergedKeys + "...");
				}
			} else {
				if (logger.isEnabledFor(Level.DEBUG) == true) {
					logger.log(Level.DEBUG, "Found " + mergingKey + ", hence skipping it...");
				}
			}
			missingKeyIndex++;
		}
		return mergedKeys;
	}

	void destroy() {
		unregisterDataChangeListeners();
		dataSrcHandlers.clear();
		dataSrcHandlers = null;
		dataChangeListener = null;
		if (categoryValuesMap != null) {
			categoryValuesMap.clear();
			categoryValuesMap = null;
		}
		this.component = null;
		this.logger = null;
	}

	void unregisterDataChangeListeners() {
		for (DataSourceHandler handler : dataSrcHandlers) {
			handler.removeDataChangeListener(dataChangeListener);
		}
	}

	private class CVConsolidatorDataChangeListener implements DataChangeListener {

		@Override
		public void refreshed(String dataSourceUniqueName) {
			if (categoryValuesMap != null) {
				categoryValuesMap.clear();
			}
		}

		@Override
		public void thresholdApplied(String dataSourceUniqueName) {
			if (categoryValuesMap != null) {
				categoryValuesMap.clear();
			}
		}

	}

}