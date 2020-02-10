package com.tibco.cep.studio.wizard.as.internal.wizard.handlers;

import org.eclipse.core.databinding.DataBindingContext;

import com.tibco.cep.studio.wizard.as.commons.beans.databinding.IDataBindingProvider;

public class DoNothingDataBindingProvider implements IDataBindingProvider {

	@Override
	public void bind(DataBindingContext bc, Object... params) {
		// do nothing
	}

}
