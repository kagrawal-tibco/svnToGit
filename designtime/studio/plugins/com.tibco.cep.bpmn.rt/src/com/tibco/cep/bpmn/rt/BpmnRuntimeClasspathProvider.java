package com.tibco.cep.bpmn.rt;

import org.osgi.framework.Bundle;

import com.tibco.cep.tpcl.AbstractRuntimeBundleClasspathProvider;
import com.tibco.cep.tpcl.IRuntimeBundleClasspathProvider;

public class BpmnRuntimeClasspathProvider extends AbstractRuntimeBundleClasspathProvider implements IRuntimeBundleClasspathProvider {

	public BpmnRuntimeClasspathProvider() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Bundle getBundle() {
		return BpmnRuntimePluginActivator.getDefault().getBundle();
	}

}
