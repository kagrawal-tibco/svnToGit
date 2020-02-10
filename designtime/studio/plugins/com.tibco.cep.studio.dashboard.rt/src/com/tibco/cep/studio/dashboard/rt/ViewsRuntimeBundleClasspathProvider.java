package com.tibco.cep.studio.dashboard.rt;

import org.osgi.framework.Bundle;

import com.tibco.cep.tpcl.AbstractRuntimeBundleClasspathProvider;

public class ViewsRuntimeBundleClasspathProvider extends AbstractRuntimeBundleClasspathProvider {

	public ViewsRuntimeBundleClasspathProvider() {
	}

	@Override
	public Bundle getBundle() {
		return DashboardRuntimePlugInActivator.getDefault().getBundle();
	}
	
//	@Override
//	public String[] getClasspath() throws BundleException {
//		String[] classpath = super.getClasspath();
//		LinkedList<String> classpathList = new LinkedList<String>();
//		for (String classpathEntry : classpath) {
//			if (classpathEntry.toLowerCase().indexOf("be-dashboardagent.jar") == -1) {
//				classpathList.add(classpathEntry);
//			}
//		}
//		return classpathList.toArray(new String[classpathList.size()]);
//	}

}
