package com.tibco.cep.dashboard.psvr.streaming;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tibco.cep.dashboard.psvr.data.DataCacheMXBean;
import com.tibco.cep.dashboard.psvr.data.DataCacheMXBeanImpl;
import com.tibco.cep.dashboard.psvr.data.DataException;
import com.tibco.cep.dashboard.psvr.data.DataSourceHandlerRuntimeInfo;
import com.tibco.cep.dashboard.session.DashboardSession;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.session.impl.RuleSessionManagerImpl;

public class DataCacheUpdatersMXBeanImpl implements DataCacheUpdatersMXBean {

	private DataCacheMXBean dataCacheMXBean;

	public DataCacheUpdatersMXBeanImpl() {
		dataCacheMXBean = new DataCacheMXBeanImpl();
	}

	@Override
	public int getDataSourceUpdaterCount() {
		return DataSourceUpdateHandlerFactory.getInstance().handlers().size();
	}

	@Override
	public DataSourceUpdaterRuntimeInfo[] getDataSourceUpdaters() {
		Collection<DataSourceUpdateHandler> handlers = DataSourceUpdateHandlerFactory.getInstance().handlers();
		DataSourceUpdaterRuntimeInfo[] handlerInfos = new DataSourceUpdaterRuntimeInfo[handlers.size()];
		int i = 0;
		for (DataSourceUpdateHandler handler : handlers) {
			DataSourceUpdaterRuntimeInfo runtimeInfo = getRuntimeInfo(handler);
			handlerInfos[i] = runtimeInfo;
			i++;
		}
		return handlerInfos;
	}

	@Override
	public DataSourceUpdaterRuntimeInfo searchDataSourceUpdatersById(String id) {
		DataSourceUpdateHandler[] updateHandlers = search(id, null, null);
		if (updateHandlers != null && updateHandlers.length != 0) {
			return getRuntimeInfo(updateHandlers[0]);
		}
		return null;
	}

	@Override
	public DataSourceUpdaterRuntimeInfo[] searchDataSourceUpdatersByReferencer(String referencer) {
		DataSourceUpdateHandler[] updateHandlers = search(null, referencer, null);
		DataSourceUpdaterRuntimeInfo[] updateHandlerRuntimeInfos = new DataSourceUpdaterRuntimeInfo[updateHandlers.length];
		for (int i = 0; i < updateHandlers.length; i++) {
			updateHandlerRuntimeInfos[i] = getRuntimeInfo(updateHandlers[i]);
		}
		return updateHandlerRuntimeInfos;
	}

	@Override
	public DataSourceUpdaterRuntimeInfo[] searchDataSourceUpdatersBySource(String source) {
		DataSourceUpdateHandler[] updateHandlers = search(null, null, source);
		DataSourceUpdaterRuntimeInfo[] updateHandlerRuntimeInfos = new DataSourceUpdaterRuntimeInfo[updateHandlers.length];
		for (int i = 0; i < updateHandlers.length; i++) {
			updateHandlerRuntimeInfos[i] = getRuntimeInfo(updateHandlers[i]);
		}
		return updateHandlerRuntimeInfos;

	}

	@Override
	public int purgeAll() {
		Collection<DataSourceUpdateHandler> handlers = DataSourceUpdateHandlerFactory.getInstance().handlers();
		return reset(handlers.toArray(new DataSourceUpdateHandler[handlers.size()]), true);
	}

	@Override
	public void purgeById(String id) {
		reset(search(id, null, null), true);
	}

	@Override
	public int purgeByReferencer(String referencer) {
		return reset(search(null, referencer, null), true);
	}

	@Override
	public int purgeBySource(String source) {
		return reset(search(null, null, source), true);
	}

	@Override
	public int resetAll() {
		Collection<DataSourceUpdateHandler> handlers = DataSourceUpdateHandlerFactory.getInstance().handlers();
		int resetResult = reset(handlers.toArray(new DataSourceUpdateHandler[handlers.size()]), false);			
		return resetResult;
	}

	@Override
	public void resetById(String id) {
		reset(search(id, null, null), false);
	}

	@Override
	public int resetByReferencer(String referencer) {
		return reset(search(null, referencer, null), false);
	}

	@Override
	public int resetBySource(String source) {
		return reset(search(null, null, source), false);
	}

	@Override
	public int resetStatsAll() {
		Collection<DataSourceUpdateHandler> handlers = DataSourceUpdateHandlerFactory.getInstance().handlers();
		return resetStats(handlers.toArray(new DataSourceUpdateHandler[handlers.size()]));
	}

	@Override
	public void resetStatsById(String id) {
		resetStats(search(id, null, null));
	}

	@Override
	public int resetStatsByReferencer(String referencer) {
		return resetStats(search(null, referencer, null));
	}

	@Override
	public int resetStatsBySource(String source) {
		return resetStats(search(null, null, source));
	}

	private DataSourceUpdateHandler[] search(String id, String referencer, String source) {
		DataSourceHandlerRuntimeInfo[] dataSourceHandlerRuntimeInfos = null;
		if (id != null) {
			DataSourceHandlerRuntimeInfo dataSourceHandlerRuntimeInfo = dataCacheMXBean.searchDataSourceHandlersById(id);
			if (dataSourceHandlerRuntimeInfo != null) {
				dataSourceHandlerRuntimeInfos = new DataSourceHandlerRuntimeInfo[] { dataSourceHandlerRuntimeInfo };
			}
		} else if (referencer != null) {
			dataSourceHandlerRuntimeInfos = dataCacheMXBean.searchDataSourceHandlersByReferencer(referencer);
		} else if (source != null) {
			dataSourceHandlerRuntimeInfos = dataCacheMXBean.searchDataSourceHandlersBySource(source);
		}
		if (dataSourceHandlerRuntimeInfos == null){
			return new DataSourceUpdateHandler[0];
		}
		List<DataSourceUpdateHandler> updateHandlers = new ArrayList<DataSourceUpdateHandler>(dataSourceHandlerRuntimeInfos.length);
		for (DataSourceHandlerRuntimeInfo dataSourceHandlerRuntimeInfo : dataSourceHandlerRuntimeInfos) {
			DataSourceUpdateHandler[] handlers = DataSourceUpdateHandlerFactory.getInstance().getPreCreatedHandlers(dataSourceHandlerRuntimeInfo.getUniqueName());
			if (handlers.length != 0) {
				updateHandlers.add(handlers[0]);
			}
		}
		return updateHandlers.toArray(new DataSourceUpdateHandler[updateHandlers.size()]);
	}

	private DataSourceUpdaterRuntimeInfo getRuntimeInfo(DataSourceUpdateHandler handler) {
		DataSourceUpdaterRuntimeInfo runtimeInfo = new DataSourceUpdaterRuntimeInfo();
		runtimeInfo.setSubscriptionName(handler.subscriptionName);
		runtimeInfo.setSource(handler.dataSourceHandler.getSourceElement().getName());
		// PATCH find a way to set the actual condition
		runtimeInfo.setSubscription(handler.dataSourceHandler.getQuery());
		runtimeInfo.setSubscribed(handler.subscribed);
		runtimeInfo.setState(handler.state.toString());
		Set<Channel> channels = new HashSet<Channel>(handler.interestedChannels);
		String[] subscribers = new String[channels.size()];
		int i = 0;
		for (Channel channel : channels) {
			subscribers[i] = channel.getToken().getUserID() + "/" + channel.getName();
			i++;
		}
		runtimeInfo.setSubscribers(subscribers);
		runtimeInfo.setAverageUpdateProcessingTime(handler.updateProcessingTime.getAverage());
		runtimeInfo.setPeakUpdateProcessingTime(handler.updateProcessingTime.getHighestPeak());

		runtimeInfo.setAverageResetProcessingTime(handler.resetProcessingTime.getAverage());
		runtimeInfo.setPeakResetProcessingTime(handler.resetProcessingTime.getHighestPeak());
		return runtimeInfo;
	}

	private int reset(DataSourceUpdateHandler[] handlers, boolean purge) {
		int i = 0;
		// Fix for BE-19076	{Support:Enel} JMX Operation "Agent - id - Dashboard - datacacheupdater - resetAll" does not work
		RuleSession[] ruleSessions = RuleServiceProviderManager.getInstance().getDefaultProvider().getRuleRuntime().getRuleSessions();
		for(RuleSession session:ruleSessions) {
			if(session instanceof DashboardSession) {
				RuleSessionManager.currentRuleSessions.set(session);				
				break;
			}
		}
		for (DataSourceUpdateHandler dataSourceUpdateHandler : handlers) {
			try {
				dataSourceUpdateHandler.resetData(purge);
				i++;
			} catch (DataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		RuleSessionManager.currentRuleSessions.remove();
		return i;
	}

	private int resetStats(DataSourceUpdateHandler[] handlers) {
		int i = 0;
		for (DataSourceUpdateHandler dataSourceUpdateHandler : handlers) {
			dataSourceUpdateHandler.updateProcessingTime.reset();
			dataSourceUpdateHandler.resetProcessingTime.reset();
			i++;
		}
		return i;
	}

	@Override
	public double getAverageResetProcessingTime() {
		double sum = 0.0;
		int count = 0;
		Collection<DataSourceUpdateHandler> handlers = DataSourceUpdateHandlerFactory.getInstance().handlers();
		if (handlers.isEmpty() == true){
			return 0.0;
		}
		for (DataSourceUpdateHandler dataSourceUpdateHandler : handlers) {
			sum = sum + dataSourceUpdateHandler.resetProcessingTime.getAverage();
			count++;
		}
		return sum/count;
	}

	@Override
	public double getAverageUpdateProcessingTime() {
		double sum = 0.0;
		int count = 0;
		Collection<DataSourceUpdateHandler> handlers = DataSourceUpdateHandlerFactory.getInstance().handlers();
		if (handlers.isEmpty() == true){
			return 0.0;
		}
		for (DataSourceUpdateHandler dataSourceUpdateHandler : handlers) {
			sum = sum + dataSourceUpdateHandler.updateProcessingTime.getAverage();
			count++;
		}
		return sum/count;		
	}

	@Override
	public DataSourceUpdaterRuntimeInfo getPeakResetProcessingTimeUpdater() {
		DataSourceUpdateHandler highestHandler = null;
		Collection<DataSourceUpdateHandler> handlers = DataSourceUpdateHandlerFactory.getInstance().handlers();
		if (handlers.isEmpty() == true){
			return null;
		}
		for (DataSourceUpdateHandler handler : handlers) {
			if (highestHandler == null || handler.resetProcessingTime.getAverage() > highestHandler.resetProcessingTime.getAverage()) {
				highestHandler = handler;
			}
		}
		return getRuntimeInfo(highestHandler);		
	}

	@Override
	public DataSourceUpdaterRuntimeInfo getPeakUpdateProcessingTimeUpdater() {
		DataSourceUpdateHandler highestHandler = null;
		Collection<DataSourceUpdateHandler> handlers = DataSourceUpdateHandlerFactory.getInstance().handlers();
		if (handlers.isEmpty() == true){
			return null;
		}
		for (DataSourceUpdateHandler handler : handlers) {
			if (highestHandler == null || handler.updateProcessingTime.getAverage() > highestHandler.updateProcessingTime.getAverage()) {
				highestHandler = handler;
			}
		}
		return getRuntimeInfo(highestHandler);			
		
	}
}
