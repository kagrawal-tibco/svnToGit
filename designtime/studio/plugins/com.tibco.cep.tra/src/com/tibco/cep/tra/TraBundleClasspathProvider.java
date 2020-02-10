package com.tibco.cep.tra;

import org.osgi.framework.Bundle;

import com.tibco.cep.tpcl.AbstractRuntimeBundleClasspathProvider;
import com.tibco.cep.tpcl.IRuntimeBundleClasspathProvider;

public class TraBundleClasspathProvider extends
		AbstractRuntimeBundleClasspathProvider implements
		IRuntimeBundleClasspathProvider {

	public TraBundleClasspathProvider() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Bundle getBundle() {
		return Activator.getDefault().getBundle();
	}

}
