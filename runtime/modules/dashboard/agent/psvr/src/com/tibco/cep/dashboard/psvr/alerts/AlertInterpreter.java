package com.tibco.cep.dashboard.psvr.alerts;

import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.dashboard.psvr.mal.model.MALAlert;
import com.tibco.cep.dashboard.psvr.mal.model.MALSeriesConfig;
import com.tibco.cep.dashboard.psvr.plugin.AbstractHandler;
import com.tibco.cep.dashboard.psvr.plugin.AbstractHandlerFactory;
import com.tibco.cep.dashboard.psvr.plugin.IResolver;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.psvr.plugin.ResolverType;

//PORT think if we need to shutdown ?
public class AlertInterpreter extends AbstractHandlerFactory {

	private static AlertInterpreter instance;

	public static final synchronized AlertInterpreter getInstance() {
		if (instance == null) {
			instance = new AlertInterpreter();
		}
		return instance;
	}

	private AlertInterpreter() {
		super("alertinterpreter", "Alert Interpreter", ResolverType.ALERT_EVALUATOR);
	}

	public AlertEvaluator interpret(MALSeriesConfig seriesConfig) throws PluginException {
		MALAlert[] alerts = seriesConfig.getActionRule().getAlert();
		if (alerts == null || alerts.length == 0) {
			return null;
		}
		List<AlertEvaluator> individualEvaluators = new ArrayList<AlertEvaluator>(alerts.length);
		for (MALAlert alert : alerts) {
			AbstractAlertEvaluator evaluator = getEvaluator(alert);
			if (evaluator != null) {
				evaluator.setAlert(alert);
				individualEvaluators.add(evaluator);
			}
		}
		return new AlertEvaluationCoordinator(individualEvaluators);
	}

	private AbstractAlertEvaluator getEvaluator(MALAlert alert) throws PluginException {
		IResolver resolver = getResolver(alert);
		if (resolver != null) {
			List<Class<? extends AbstractHandler>> handlerClazzes = resolver.resolve(alert);
			if (handlerClazzes != null && handlerClazzes.isEmpty() == false) {
				AbstractHandler[] abstractHandlers = instantiateHandlers(handlerClazzes);
				initializeHandlers(resolver.getPlugInID(), abstractHandlers);
				return (AbstractAlertEvaluator) abstractHandlers[0];
			}
		}
		return null;
	}
}
