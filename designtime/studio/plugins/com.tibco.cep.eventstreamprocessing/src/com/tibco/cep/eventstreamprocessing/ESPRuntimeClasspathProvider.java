package com.tibco.cep.eventstreamprocessing;

import org.osgi.framework.Bundle;

import com.tibco.cep.tpcl.AbstractRuntimeBundleClasspathProvider;
import com.tibco.cep.tpcl.IRuntimeBundleClasspathProvider;

public class ESPRuntimeClasspathProvider extends
		AbstractRuntimeBundleClasspathProvider implements
		IRuntimeBundleClasspathProvider {

	public ESPRuntimeClasspathProvider() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Bundle getBundle() {
		return Activator.getDefault().getBundle();
	}

}
