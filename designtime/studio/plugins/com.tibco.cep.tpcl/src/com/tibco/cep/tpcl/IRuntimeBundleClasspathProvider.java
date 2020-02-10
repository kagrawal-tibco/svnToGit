package com.tibco.cep.tpcl;

import java.io.File;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;

public interface IRuntimeBundleClasspathProvider {
	File[] getClasspath() throws BundleException;
	Bundle getBundle();
}
