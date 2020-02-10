package com.tibco.cep.dashboard.plugin.beviews.runtime;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.tibco.cep.dashboard.plugin.beviews.mal.MALUtils;
import com.tibco.cep.dashboard.psvr.mal.ViewsConfigHelper;
import com.tibco.cep.dashboard.psvr.mal.model.MALCategoryGuidelineConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.types.SortEnum;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationException;
import com.tibco.cep.dashboard.security.SecurityToken;

class TokenRoleComponentSet {

	@SuppressWarnings("unused")
	private SecurityToken token;

	private Map<String, MasterCategorySet> compIdToMasterCategorySetMap;

//	private ViewsConfigHelper viewsConfigHelper;
//
//	private Map<String, MasterCategorySetSyncWorker> compIdToWorkerMap;
//
//	private ElementChangeListenerImpl elementChangeListener;

	TokenRoleComponentSet(SecurityToken token, ViewsConfigHelper viewsConfigHelper) {
		super();
		this.token = token;
		this.compIdToMasterCategorySetMap = new ConcurrentHashMap<String, MasterCategorySet>();
//		this.compIdToWorkerMap = new HashMap<String, TokenRoleComponentSet.MasterCategorySetSyncWorker>();
//		this.viewsConfigHelper = viewsConfigHelper;
//		this.elementChangeListener = new ElementChangeListenerImpl();
//		this.viewsConfigHelper.addElementChangeListener(elementChangeListener);
	}

	MasterCategorySet getMasterCategorySet(MALComponent component, PresentationContext pCtx) throws VisualizationException {
		String componentId = component.getId();
		MasterCategorySet masterCategorySet = compIdToMasterCategorySetMap.get(componentId);
		if (masterCategorySet == null) {
			MALCategoryGuidelineConfig categoryGuideLineConfig = MALUtils.getCategoryGuideLineConfig(component);
			switch (categoryGuideLineConfig.getSortOrder().getType()) {
				case SortEnum.ASCENDING_TYPE:
					masterCategorySet = new SortedMasterCategorySet(component.toString(), true);
					break;
				case SortEnum.DESCENDING_TYPE:
					masterCategorySet = new SortedMasterCategorySet(component.toString(), false);
					break;
				case SortEnum.UNSORTED_TYPE:
					masterCategorySet = new MasterCategorySet(component.toString());
					break;
				default:
					throw new IllegalArgumentException(component.toString()+"'s category guideline config uses unknown sort configuration");
			}
			compIdToMasterCategorySetMap.put(componentId, masterCategorySet);
		}
		return masterCategorySet;
//		String componentId = component.getId();
//		MasterCategorySetSyncWorker worker = compIdToWorkerMap.get(componentId);
//		if (worker == null) {
//			//determine which type of master category set to create
//			MasterCategorySet masterCategorySet = null;
//			MALCategoryGuidelineConfig categoryGuideLineConfig = MALUtils.getCategoryGuideLineConfig(component);
//			switch (categoryGuideLineConfig.getSortOrder().getType()) {
//				case SortEnum.ASCENDING_TYPE:
//					masterCategorySet = new SortedMasterCategorySet(component.toString(), true);
//					break;
//				case SortEnum.DESCENDING_TYPE:
//					masterCategorySet = new SortedMasterCategorySet(component.toString(), false);
//					break;
//				case SortEnum.UNSORTED_TYPE:
//					masterCategorySet = new MasterCategorySet(component.toString());
//					break;
//				default:
//					throw new IllegalArgumentException(component.toString()+"'s category guideline config uses unknown sort configuration");
//			}
//			//create the worker
//			worker = new MasterCategorySetSyncWorker(masterCategorySet, getDataSourceHandlers(component, pCtx));
//			compIdToWorkerMap.put(componentId, worker);
//		}
//		return worker.masterCategorySet;
	}

	void clear(){
//		for (MasterCategorySetSyncWorker worker : compIdToWorkerMap.values()) {
//			worker.unregisterDataChangeListeners();
//		}
//		compIdToWorkerMap.clear();
//		viewsConfigHelper.removeElementChangeListener(elementChangeListener);
		for (MasterCategorySet set : compIdToMasterCategorySetMap.values()) {
			set.clear();
		}
		compIdToMasterCategorySetMap.clear();
	}

	/*private LinkedList<DataSourceHandler> getDataSourceHandlers(MALComponent component, PresentationContext pCtx) throws VisualizationException {
		LinkedList<DataSourceHandler> dataSourceHandlers = new LinkedList<DataSourceHandler>();
		for (MALVisualization visualization : component.getVisualization()) {
			for (MALSeriesConfig seriesConfig : visualization.getSeriesConfig()) {
				DataSourceHandler dataSourceHandler = pCtx.getDataSourceHandler(seriesConfig);
				dataSourceHandlers.add(dataSourceHandler);
			}
		}
		return dataSourceHandlers;
	}

	class ElementChangeListenerImpl implements ElementChangeListener {

		@Override
		public void prepareForChange(MALElement element) {
			MasterCategorySetSyncWorker worker = compIdToWorkerMap.get(element.getId());
			if (worker != null) {
				worker.unregisterDataChangeListeners();
			}
		}

		@Override
		public void preOp(String parentPath, MALElement child, MALElement replacement, OPERATION operation) {
			//do nothing
		}

		@Override
		public void postOp(String parentPath, MALElement child, MALElement replacement, OPERATION operation) {
			//do nothing
		}

		@Override
		public void changeAborted(MALElement element) {
			MasterCategorySetSyncWorker worker = compIdToWorkerMap.get(element.getId());
			if (worker != null) {
				try {
					worker.registerDataChangeListeners(getDataSourceHandlers((MALComponent) element, new PresentationContext(token)));
				} catch (Exception e) {
					throw new RuntimeException("could not register data change listeners on "+element,e);
				}
			}
		}

		@Override
		public void changeComplete(MALElement element) {
			MasterCategorySetSyncWorker worker = compIdToWorkerMap.get(element.getId());
			if (worker != null) {
				try {
					worker.registerDataChangeListeners(getDataSourceHandlers((MALComponent) element, new PresentationContext(token)));
				} catch (Exception e) {
					throw new RuntimeException("could not register data change listeners on "+element,e);
				}
			}
		}
	}

	class MasterCategorySetSyncWorker implements DataChangeListener {

		MasterCategorySet masterCategorySet;

		List<DataSourceHandler> dataSourceHandlers;

		MasterCategorySetSyncWorker(MasterCategorySet masterCategorySet, List<DataSourceHandler> dataSourceHandlers) {
			this.masterCategorySet = masterCategorySet;
			registerDataChangeListeners(dataSourceHandlers);
		}

		void registerDataChangeListeners(List<DataSourceHandler> dataSourceHandlers){
			this.dataSourceHandlers = dataSourceHandlers;
			for (DataSourceHandler dataSourceHandler : dataSourceHandlers) {
				dataSourceHandler.addDataChangeListener(this);
			}
		}

		void unregisterDataChangeListeners(){
			for (DataSourceHandler dataSourceHandler : dataSourceHandlers) {
				dataSourceHandler.removeDataChangeListener(this);
			}
			this.dataSourceHandlers = Collections.emptyList();
		}

		@Override
		public void thresholdApplied(String dataSourceUniqueName) {
			//we applied a threshold which means existing set of data has changed (category field wise)
			masterCategorySet.clear();
		}

		@Override
		public void refreshed(String dataSourceUniqueName) {
			//masterCategorySet.clear();
			//do nothing, since we are only refreshing which means existing set of data is same (category field wise)
		}


	}*/
}
