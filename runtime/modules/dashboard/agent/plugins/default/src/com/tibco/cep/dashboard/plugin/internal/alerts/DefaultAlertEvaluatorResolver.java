package com.tibco.cep.dashboard.plugin.internal.alerts;

import com.tibco.cep.dashboard.plugin.internal.DefaultPlugIn;
import com.tibco.cep.dashboard.psvr.mal.managers.MALRangeAlertManager;
import com.tibco.cep.dashboard.psvr.mal.model.MALElement;
import com.tibco.cep.dashboard.psvr.plugin.ResolverType;
import com.tibco.cep.dashboard.psvr.plugin.SimpleResolverImpl;

public class DefaultAlertEvaluatorResolver extends SimpleResolverImpl {

	public DefaultAlertEvaluatorResolver() {
		super(DefaultPlugIn.PLUGIN_ID, ResolverType.ALERT_EVALUATOR,RangeAlertEvaluator.class);
	}

	@Override
	public boolean isAcceptable(MALElement element) {
		if (element.getDefinitionType().equals(MALRangeAlertManager.DEFINITION_TYPE) == true){
			return true;
		}
		return false;
	}

}