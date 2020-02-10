package com.tibco.cep.studio.wizard.as.commons.beans.databinding;

import org.eclipse.core.databinding.DataBindingContext;

public class DataBindingProviderGroup implements IDataBindingProvider {

	private IDataBindingProvider[] dataBindingProviders;

	public DataBindingProviderGroup(IDataBindingProvider ... dataBindingProviders) {
		this.dataBindingProviders = dataBindingProviders;
	}

	@Override
	public void bind(DataBindingContext bc, Object... params) {
		for (IDataBindingProvider dataBindingProvider : dataBindingProviders) {
			dataBindingProvider.bind(bc, params);
		}
	}

}
