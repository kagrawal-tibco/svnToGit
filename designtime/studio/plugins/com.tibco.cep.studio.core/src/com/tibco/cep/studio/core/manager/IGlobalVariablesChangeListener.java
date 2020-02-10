package com.tibco.cep.studio.core.manager;

import com.tibco.cep.repo.provider.GlobalVariablesProvider;

public interface IGlobalVariablesChangeListener {
	
	public void globalVariablesChanged(GlobalVariablesProvider provider, String projectName);

}
