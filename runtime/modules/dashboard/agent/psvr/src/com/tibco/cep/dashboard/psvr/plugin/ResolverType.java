package com.tibco.cep.dashboard.psvr.plugin;

public enum ResolverType {

	COMPONENT_RENDERNG("componentrender"),
	COMPONENT_HANDLER("componenthandler"),
	VISUALIZATION_HANDLER("visualizationhandler"),
	DATASOURCE_HANDLER("datasourcehandler"),
	ACTIONCONFIG_GENERATOR("actionconfiggenerator"),
	DATAFORMAT_HANDLER("dataformathandler"),
	LAYOUT_GENERATOR("layoutgenerator"),
	DATASOURCE_UPDATE_HANDLER("datasourceupdatehandler"),
	ALERT_EVALUATOR("alertevaluator"),
	PUB_SUB_HANDLER("pubsubhandler"),
	PAGE_PREPROCESSOR("pagepreprocessor"),
	EXTERNAL_REFERENCE_HANDLER("externalreferencehandler");

	private String userFriendlyName;

	private ResolverType(String userFriendlyName) {
		this.userFriendlyName = userFriendlyName;
	}

	@Override
	public String toString() {
		return userFriendlyName;
	}
}