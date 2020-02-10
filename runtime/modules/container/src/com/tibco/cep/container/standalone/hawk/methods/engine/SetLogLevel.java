package com.tibco.cep.container.standalone.hawk.methods.engine;

import COM.TIBCO.hawk.ami.AmiConstants;
import COM.TIBCO.hawk.ami.AmiErrors;
import COM.TIBCO.hawk.ami.AmiException;
import COM.TIBCO.hawk.ami.AmiMethod;
import COM.TIBCO.hawk.ami.AmiParameter;
import COM.TIBCO.hawk.ami.AmiParameterList;

import com.tibco.cep.container.standalone.hawk.HawkRuleAdministrator;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;

public class SetLogLevel extends AmiMethod {

	protected HawkRuleAdministrator m_hma;

	public SetLogLevel(HawkRuleAdministrator hma) {
		super("setLogLevel", "Sets Log Level", AmiConstants.METHOD_TYPE_ACTION);
		m_hma = hma;
	}

	@SuppressWarnings("unchecked")
	public AmiParameterList getArguments() {
		AmiParameterList args = new AmiParameterList();
		args.addElement(new AmiParameter("Logger Name", "Name of a logger or a search pattern", ""));
		args.addElement(new AmiParameter("Level", "Logger level to set", ""));
		return args;
	}

	public AmiParameterList getReturns() {
		return null;
	}

	public AmiParameterList onInvoke(AmiParameterList inParams) throws AmiException {
		try {
			LogManagerFactory.getLogManager().setLevel(inParams.getString(0), inParams.getString(1));
		} catch (IllegalArgumentException ex) {
			throw new AmiException(AmiErrors.AMI_REPLY_ERR, ex.getMessage());
		}
		return new AmiParameterList();
	}

}

