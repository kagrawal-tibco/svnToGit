package com.tibco.cep.dashboard.management;

import com.tibco.cep.dashboard.common.utils.CopyUtil;
import com.tibco.cep.repo.ChangeListener;
import com.tibco.cep.runtime.session.RuleServiceProvider;

public class GlobalVariablesChangeListener implements ChangeListener {

	private RuleServiceProvider rsp;
	private ManagementProperties properties;
	
	public GlobalVariablesChangeListener(RuleServiceProvider rsp, ManagementProperties properties) {
		this.rsp = rsp;
		this.properties = properties;
	}
	@Override
	public void notify(ChangeEvent e) {
		CopyUtil.copyGlobalVariablesToProperties(rsp, properties);
	}
}

