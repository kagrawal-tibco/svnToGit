package com.tibco.cep.dashboard.plugin.internal.vizengine;

import com.tibco.cep.dashboard.psvr.biz.BizSessionRequest;
import com.tibco.cep.dashboard.psvr.biz.RequestProcessingException;
import com.tibco.cep.dashboard.psvr.common.NonFatalException;
import com.tibco.cep.dashboard.psvr.mal.model.MALPage;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.vizengine.PagePreProcessor;

public class DefaultPagePreProcessor extends PagePreProcessor {

	@Override
	protected void init() {
		
	}

	@Override
	protected void shutdown() throws NonFatalException {
		
	}

	@Override
	public PreProcessorResults preprocess(MALPage page, BizSessionRequest request, PresentationContext ctx) throws RequestProcessingException {
		return null;
	}

}