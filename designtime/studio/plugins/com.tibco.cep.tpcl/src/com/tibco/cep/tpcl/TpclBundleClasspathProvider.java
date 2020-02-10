package com.tibco.cep.tpcl;

import org.osgi.framework.Bundle;

public class TpclBundleClasspathProvider extends
		AbstractRuntimeBundleClasspathProvider implements
		IRuntimeBundleClasspathProvider {

	public TpclBundleClasspathProvider() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Bundle getBundle() {
		return Activator.getDefault().getBundle();
	}

}
