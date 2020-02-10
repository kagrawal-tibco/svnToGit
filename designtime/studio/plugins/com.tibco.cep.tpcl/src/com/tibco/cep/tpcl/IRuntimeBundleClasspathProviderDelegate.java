package com.tibco.cep.tpcl;

import java.io.File;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;

public interface IRuntimeBundleClasspathProviderDelegate {
	File[] getClasspath(Bundle bundle) throws BundleException;
}
