package com.tibco.cep.dashboard.plugin.beviews.biz.querymgr;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.plugin.beviews.biz.search.export.ExportDataAction;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.DrilldownProvider;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QuerySpec;
import com.tibco.cep.dashboard.plugin.beviews.export.ExportContentHolder;
import com.tibco.cep.dashboard.plugin.beviews.querymgr.export.QueryExportContentHolder;
import com.tibco.cep.dashboard.plugin.beviews.search.BizSessionSearchStore;
import com.tibco.cep.dashboard.psvr.biz.BizSessionRequest;

public class ExportQueryContentAction extends ExportDataAction {

	@Override
	protected ExportContentHolder getContentHolder(BizSessionRequest request) throws Exception {
		int exportDepth = parseInt(request,"exportdepth", "Depth");
		int exportTypeCount = parseInt(request,"exporttypecount", "Per Type Count");
		int exportAllCount = parseInt(request,"exportallcount", "Overall Count");

		BizSessionSearchStore store = BizSessionSearchStore.getInstance(request.getSession());
		//get current query spec in the query manager
		QuerySpec querySpec = store.getQueryManagerModel().getQuerySpec();

		//initialize the query export content holder
		QueryExportContentHolder exportContentHolder = new QueryExportContentHolder(logger,exceptionHandler,messageGenerator);
		exportContentHolder.setExportDepth(exportDepth);
		exportContentHolder.setExportTypeCount(exportTypeCount);
		exportContentHolder.setExportAllCount(exportAllCount);
		exportContentHolder.setQuerySpec(querySpec);
		exportContentHolder.setDrilldownProvider(DrilldownProvider.getInstance(request.getSession()));

		exportContentHolder.execute();

		return exportContentHolder;
	}

	private int parseInt(BizSessionRequest request, String parameterName, String exposedName){
		String value = request.getParameter(parameterName);
		if (StringUtil.isEmptyOrBlank(value) == true){
			throw new IllegalArgumentException(exposedName+" cannot be null");
		}
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(exposedName+" is invalid");
		}
	}

}