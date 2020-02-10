package com.tibco.cep.dashboard.plugin.beviews.biz.drilldown;

import com.tibco.cep.dashboard.plugin.beviews.biz.search.export.ExportDataAction;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.export.DrilldownExportContentHolder;
import com.tibco.cep.dashboard.plugin.beviews.export.ExportContentHolder;
import com.tibco.cep.dashboard.psvr.biz.BizSession;
import com.tibco.cep.dashboard.psvr.biz.BizSessionRequest;

/**
 * @author himanshu
 * 
 */
public class ExportDrilldownContentAction extends ExportDataAction {

	@Override
	protected ExportContentHolder getContentHolder(BizSessionRequest request) throws Exception {
		BizSession session = request.getSession();
		return (DrilldownExportContentHolder) session.getAttribute("drilldowncontent");
	}
}
