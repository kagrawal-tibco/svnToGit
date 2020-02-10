package com.tibco.cep.container.standalone.hawk.methods.engine;

import COM.TIBCO.hawk.ami.AmiConstants;
import COM.TIBCO.hawk.ami.AmiException;
import COM.TIBCO.hawk.ami.AmiMethod;
import COM.TIBCO.hawk.ami.AmiParameter;
import COM.TIBCO.hawk.ami.AmiParameterList;

import com.tibco.cep.container.standalone.hawk.HawkRuleAdministrator;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;

public class GetLoggerNamesWithLevels extends AmiMethod {

	protected HawkRuleAdministrator m_hma;

	public GetLoggerNamesWithLevels(HawkRuleAdministrator hma) {
		super("getLoggerNamesWithLevels", "Gets the list of loggers with their current log level", AmiConstants.METHOD_TYPE_INFO);
		this.m_hma = hma;
	}

	public AmiParameterList getArguments() {
		return null;
	}

	public AmiParameterList getReturns() {
		AmiParameterList returns = new AmiParameterList();
		addTo(returns, "", "");
		return returns;
	}

	@SuppressWarnings("unchecked")
	private void addTo(AmiParameterList list, String loggerName, String logLevel) {
		list.addElement(new AmiParameter("Name", "Name of the logger", loggerName));
		list.addElement(new AmiParameter("Level", "Curent Logger level", logLevel));
	}

	public String[] getIndexName() {
		return new String[] { "Name" };
	}

	public AmiParameterList onInvoke(AmiParameterList inParams) throws AmiException {
		Logger[] loggers = LogManagerFactory.getLogManager().getLoggers();
		AmiParameterList values = new AmiParameterList(loggers.length);
		for (Logger logger : loggers) {
			addTo(values, logger.getName(), logger.getLevel().toString());
		}
		return values;
	}

}