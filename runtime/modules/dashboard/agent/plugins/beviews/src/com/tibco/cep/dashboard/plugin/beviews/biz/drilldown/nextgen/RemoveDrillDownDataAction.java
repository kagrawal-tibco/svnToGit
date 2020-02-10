package com.tibco.cep.dashboard.plugin.beviews.biz.drilldown.nextgen;

import java.util.Map;
import java.util.Properties;

import com.tibco.cep.dashboard.plugin.beviews.nextgendrilldown.DrillDownDataNodeMerger;
import com.tibco.cep.dashboard.plugin.beviews.nextgendrilldown.DrillDownRequest;
import com.tibco.cep.dashboard.plugin.beviews.nextgenexport.tree.DrillDownDataTree;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.psvr.biz.BizSessionRequest;
import com.tibco.cep.dashboard.security.SecurityToken;
import com.tibco.cep.kernel.service.logging.Level;

public class RemoveDrillDownDataAction extends BaseDrillDownAction {

	private DrillDownDataNodeMerger drillDownDataNodeMerger;

	@Override
	protected void init(String command, Properties properties, Map<String, String> configuration) throws Exception {
		super.init(command, properties, configuration);
		drillDownDataNodeMerger = new DrillDownDataNodeMerger(logger, properties, exceptionHandler, messageGenerator);
	}


	@Override
	protected BizResponse doExecute(SecurityToken token, BizSessionRequest request) {
		//create drilldown request
		DrillDownRequest drillDownRequest = getRequest(token, request);
		if (logger.isEnabledFor(Level.INFO) == true) {
			logger.log(Level.INFO, "Processing "+command+" using "+drillDownRequest);
		}
		DrillDownDataTree drillDownDataTree = (DrillDownDataTree) request.getSession().getAttribute(DrillDownDataTree.class.getName());
		//remove the data from existing dataset in the session
		drillDownDataNodeMerger.removeData(drillDownRequest, drillDownDataTree);
		return handleSuccess("");
	}

}