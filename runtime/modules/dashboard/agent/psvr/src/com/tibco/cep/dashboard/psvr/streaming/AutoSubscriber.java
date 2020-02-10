package com.tibco.cep.dashboard.psvr.streaming;

import java.util.Properties;

import com.tibco.cep.dashboard.management.ManagementConfigurator.MODE;
import com.tibco.cep.dashboard.management.ManagementException;
import com.tibco.cep.dashboard.management.ServiceContext;
import com.tibco.cep.dashboard.management.ServiceDependent;
import com.tibco.cep.dashboard.psvr.data.DataCacheListener;
import com.tibco.cep.dashboard.psvr.data.DataSourceHandler;
import com.tibco.cep.dashboard.psvr.data.DataSourceHandlerCache;
import com.tibco.cep.dashboard.psvr.mal.URIHelper;
import com.tibco.cep.dashboard.psvr.mal.model.MALSeriesConfig;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.kernel.service.logging.Logger;

public class AutoSubscriber extends ServiceDependent {

	private boolean enabled;
	private DataSourceHandlerCache dataSourceHandlerCache;
	private DataCacheListener dataCacheListener;

	private DataSourceUpdateHandlerFactory dataSourceUpdateHandlerFactory;

	protected AutoSubscriber() {
		super("bizsessionprovider","Business Session Provider");
	}

	@Override
	public void init(Logger parentLogger, MODE mode, Properties properties, ServiceContext serviceContext) throws ManagementException {
		enabled = (Boolean) StreamingProperties.AUTO_SUBSCRIBE_ENABLED.getValue(properties);
	}

	@Override
	protected void doStart() throws ManagementException {
		if (enabled == true) {
			dataSourceHandlerCache = DataSourceHandlerCache.getInstance();
			dataCacheListener = new DataCacheListenerImpl();
			dataSourceHandlerCache.addDataCacheListener(dataCacheListener);
			dataSourceUpdateHandlerFactory = DataSourceUpdateHandlerFactory.getInstance();
		}
	}

	@Override
	protected boolean doStop() {
		if (enabled == true && dataSourceHandlerCache != null && dataCacheListener != null) {
			dataSourceHandlerCache.removeDataCacheListener(dataCacheListener);
		}
		return true;
	}

	private class DataCacheListenerImpl implements DataCacheListener {

		@Override
		public void dataSourceHandlerAdded(MALSeriesConfig seriesConfig, DataSourceHandler handler, PresentationContext pCtx) {
			try {
				DataSourceUpdateHandler dataSourceUpdateHandler = dataSourceUpdateHandlerFactory.getDataSourceUpdateHandler(seriesConfig, handler, pCtx);
				dataSourceUpdateHandler.subscribe();
			} catch (Exception e) {
				exceptionHandler.handleException("could not auto subscribe "+URIHelper.getURI(seriesConfig), e);
			}
		}

		@Override
		public void dataSourceHandlerRemoved(MALSeriesConfig seriesConfig, DataSourceHandler handler, PresentationContext pCtx) {
			try {
				DataSourceUpdateHandler dataSourceUpdateHandler = dataSourceUpdateHandlerFactory.getDataSourceUpdateHandler(seriesConfig, handler, pCtx);
				dataSourceUpdateHandler.unsubscribe();
			} catch (Exception e) {
				exceptionHandler.handleException("could not auto unsubscribe "+URIHelper.getURI(seriesConfig), e);
			}
		}

	}

}
