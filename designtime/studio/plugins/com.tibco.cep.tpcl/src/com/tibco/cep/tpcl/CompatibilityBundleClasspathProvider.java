package com.tibco.cep.tpcl;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.core.runtime.Path;
//import org.eclipse.osgi.framework.internal.core.BundleHost;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;

public class CompatibilityBundleClasspathProvider implements IRuntimeBundleClasspathProviderDelegate {

	public static final String RUNTIME_CLASSPATH_PROVIDER = "com.tibco.cep.tpcl.runtimeBundleClasspathProvider";
	public static final String RUNTIME_CLASSPATH_PROVIDER_ATTR = "classpathProvider";
	public static final String EXTERNAL_LIB_PREFIX = "external:";//$NON-NLS-1$
	public static final String REFERENCE_LIB_PREFIX = "reference:file:"; //$NON-NLS-1$
	public static final String PLGUINS_OUTPUT_DIR = "bin"; 
	public static final String VARIABLE_DELIM_STRING = "$"; //$NON-NLS-1$
	public static final char VARIABLE_DELIM_CHAR = '$'; //$NON-NLS-1$
	private static final int BUFFER_SIZE = 2048;

	@Override
	public File[] getClasspath(Bundle bundle) throws BundleException {
//		Bundle bundle = getBundle();
		List<File> files = new ArrayList<>();
//		if(bundle instanceof BundleHost) {
//			BundleHost bhost = (BundleHost) bundle;
//			String[] classPath = bhost.getBundleData().getClassPath();
//			org.eclipse.osgi.framework.internal.core.BundleHost b = (BundleHost) bundle;
//			for (String s : classPath) {
//				if (s.startsWith(".")) {
//					File runtimeJar = ((org.eclipse.osgi.baseadaptor.BaseData) b.getBundleData()).getBundleFile().getFile(s, true);
//					if (runtimeJar != null) {
//						files.add(runtimeJar);
//					}
//					continue;
//				}
//				if (s.startsWith(REFERENCE_LIB_PREFIX)) {
//					Path p = new Path(s.substring(REFERENCE_LIB_PREFIX.length()));
//					String jpath = p.toOSString();
//					if (jpath != null && !jpath.isEmpty()) {
//						files.add(new Path(jpath).toFile());
//						continue;
//					}
//				}
//				if (s.startsWith(EXTERNAL_LIB_PREFIX)) { // RUNTIME
//					String path = substituteVars(s.substring(EXTERNAL_LIB_PREFIX.length()));
//					files.add(new Path(path).toFile());
//					continue;
//				}
//				File file = new File(s);
//				if (file.exists() == true) {
//					String path = file.getPath();
//					if(file.isDirectory() && file.toPath().toString().endsWith(File.separator+PLGUINS_OUTPUT_DIR)){
//						//should happen in dev environment only
////						urls.add(file.toPath().toString());
//					}
//					files.add(new Path(path).toFile());
//				} else {
//					// DEV environment
//					File runtimeJarFile = ((org.eclipse.osgi.baseadaptor.BaseData) b.getBundleData()).getBundleFile().getFile(s, true);
//					if (runtimeJarFile == null) {
//						System.err.println("Could not find runtime jar " + s + " in bundle " + b.getSymbolicName());
//						continue;
//					}
//					files.add(runtimeJarFile);
////					String path = runtimeJarFile.getPath();
////					constructClassPaths(urls, new Path(path).toFile());
//				}
//			}
//
//		}
		
		return (File[]) files.toArray(new File[files.size()]);
	}

	public static String substituteVars(String path) {
		StringBuffer buf = new StringBuffer(path.length());
		StringTokenizer st = new StringTokenizer(path, VARIABLE_DELIM_STRING, true);
		boolean varStarted = false; // indicates we are processing a var
									// subtitute
		String var = null; // the current var key
		while (st.hasMoreElements()) {
			String tok = st.nextToken();
			if (VARIABLE_DELIM_STRING.equals(tok)) {
				if (!varStarted) {
					varStarted = true; // we found the start of a var
					var = "";
				} else {
					// we have found the end of a var
					String prop = null;
					// get the value of the var from system properties
					if (var != null && var.length() > 0)
						prop = System.getProperty(var);
					if (prop == null) {
						try {
							// try using the System.getenv method if it exists
							Method getenv = System.class.getMethod("getenv", new Class[] { String.class }); //$NON-NLS-1$
							prop = (String) getenv.invoke(null, new Object[] { var });
						} catch (Throwable t) {
							// do nothing;
						}
					}
					if (prop != null)
						// found a value; use it
						buf.append(prop);
					else
						// could not find a value append the var name w/o delims
						buf.append(var == null ? "" : var);
					varStarted = false;
					var = null;
				}
			} else {
				if (!varStarted)
					buf.append(tok); // the token is not part of a var
				else
					var = tok; // the token is the var key; save the key to
								// process when we find the end token
			}
		}
		if (var != null)
			// found a case of $var at the end of the path with no trailing $;
			// just append it as is.
			buf.append(VARIABLE_DELIM_CHAR).append(var);
		return buf.toString();
	}

}
