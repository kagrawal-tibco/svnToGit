package com.tibco.cep.datamodeling;

import org.osgi.framework.Bundle;

import com.tibco.cep.tpcl.AbstractRuntimeBundleClasspathProvider;
import com.tibco.cep.tpcl.IRuntimeBundleClasspathProvider;

public class DataModelingRuntimeClasspathProvider extends
		AbstractRuntimeBundleClasspathProvider implements
		IRuntimeBundleClasspathProvider {

	public DataModelingRuntimeClasspathProvider() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Bundle getBundle() {
		return Activator.getDefault().getBundle();
	}

}
