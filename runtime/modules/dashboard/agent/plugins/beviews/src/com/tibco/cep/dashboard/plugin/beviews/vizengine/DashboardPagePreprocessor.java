package com.tibco.cep.dashboard.plugin.beviews.vizengine;

import com.tibco.cep.dashboard.psvr.biz.BizSessionRequest;
import com.tibco.cep.dashboard.psvr.biz.RequestProcessingException;
import com.tibco.cep.dashboard.psvr.common.NonFatalException;
import com.tibco.cep.dashboard.psvr.mal.model.MALPage;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.vizengine.PagePreProcessor;

public class DashboardPagePreprocessor extends PagePreProcessor {
	
	@Override
	protected void init() {
	}

	@Override
	public PreProcessorResults preprocess(MALPage page, BizSessionRequest request, PresentationContext ctx) throws RequestProcessingException {
		return null;
	}

	@Override
	protected void shutdown() throws NonFatalException {
	}

}
