package com.tibco.cep.rt;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

import com.tibco.cep.tpcl.IRuntimeBundleClasspathProvider;

public class CoreLibPathProvider {
	public static final String EXTERNAL_LIB_PREFIX = "external:";//$NON-NLS-1$
	private static final String REFERENCE_LIB_PREFIX = "reference:file:"; //$NON-NLS-1$
	public static final String VARIABLE_DELIM_STRING = "$"; //$NON-NLS-1$
	public static final char VARIABLE_DELIM_CHAR = '$'; //$NON-NLS-1$
	
	private static final String RUNTIME_CLASSPATH_PROVIDER = "com.tibco.cep.tpcl.runtimeBundleClasspathProvider";
	private static final String RUNTIME_CLASSPATH_PROVIDER_ATTR = "classpathProvider";
	
	/**
	 * @return
	 * @throws Exception
	 */
	public static String getCoreLibPathsAsString() throws Exception {
		URL[] paths = getCoreLibPaths(null);
		StringBuilder pathBuilder = new StringBuilder();
	      for(int i=0;i<paths.length;i++){
	    	  if(i>0)
	    		  pathBuilder.append(File.pathSeparator);
	    	  File f = new File(paths[i].toURI());
	    	  pathBuilder.append(f.getCanonicalPath());
	      }
	      return pathBuilder.toString();
	}
	
	/**
	 * @param loader
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("restriction")
	public static URL[] getCoreLibPaths(ClassLoader loader) throws Exception {
		Set<URL> urls = new LinkedHashSet<URL>();
		IRuntimeBundleClasspathProvider[] providers = getRuntimeBundleClasspathProvider();
		for(IRuntimeBundleClasspathProvider provider:providers){
			
			Bundle o = provider.getBundle();
			File[] paths = provider.getClasspath();
			if (paths == null) {
				return urls.toArray(new URL[0]);
			}
			for (File file : paths) {
			//	System.out.println("Adding "+file);
				addCoreInternalLibEntry(urls, file.getAbsolutePath());
			}
//			org.eclipse.osgi.framework.internal.core.BundleHost b = (org.eclipse.osgi.framework.internal.core.BundleHost) o;
//			
//			if(loader != null && !provider.getClass().getClassLoader().equals(loader)) {
//				//continue;
//			}
//			for (String s : paths) {
//				if (s.startsWith(".")) {
//					File runtimeJar = ((org.eclipse.osgi.baseadaptor.BaseData) b.getBundleData()).getBundleFile().getFile(s, true);
//					if (runtimeJar != null) {
//						IPath fPath = new Path(runtimeJar.getPath());
//						if (fPath.getFileExtension() != null && fPath.getFileExtension().equalsIgnoreCase("jar")) {
//							addCoreInternalLibEntry(urls, new Path(runtimeJar.getPath()).toPortableString());
//						} else {
//							fPath = fPath.append("bin");
//							addCoreInternalLibEntry(urls, fPath.toPortableString());
//						}
//					}
//					continue;
//				}
//				if (s.startsWith(REFERENCE_LIB_PREFIX)) {
//					Path p = new Path(s.substring(REFERENCE_LIB_PREFIX.length()));
//					String jpath = p.toOSString();
//					if (jpath != null && !jpath.isEmpty()) {
//						addCoreInternalLibEntry(urls, new Path(jpath).toPortableString());
//						continue;
//					}
//				}
//				if (s.startsWith(EXTERNAL_LIB_PREFIX)) { // RUNTIME
//					String path = substituteVars(s.substring(EXTERNAL_LIB_PREFIX.length()));
//					addCoreInternalLibEntry(urls, new Path(path).toPortableString());
//					continue;
//				}
//				File file = new File(s);
//				if (file.exists() == true) {
//					String path = file.getPath();
//					addCoreInternalLibEntry(urls, new Path(path).toPortableString());
//				} else {
//					// DEV environment
//					File runtimeJarFile = ((org.eclipse.osgi.baseadaptor.BaseData) b.getBundleData()).getBundleFile().getFile(s, true);
//					if (runtimeJarFile == null) {
//						System.err.println("Could not find runtime jar " + s + " in bundle " + b.getSymbolicName());
//						continue;
//					}
//					String path = runtimeJarFile.getPath();
//					addCoreInternalLibEntry(urls, new Path(path).toPortableString());
//				}
//			}
		}
		return urls.toArray(new URL[0]);
	}
	
	/**
	 * @return
	 * @throws CoreException
	 */
	private static IRuntimeBundleClasspathProvider[] getRuntimeBundleClasspathProvider() throws CoreException {
		List<IRuntimeBundleClasspathProvider> providers = new ArrayList<IRuntimeBundleClasspathProvider>();
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		if (reg != null) {
			IConfigurationElement[] extensions = reg.getConfigurationElementsFor(RUNTIME_CLASSPATH_PROVIDER);
			for (int i = 0; i < extensions.length; i++) {
				IConfigurationElement element = extensions[i];
				final Object o = element.createExecutableExtension(RUNTIME_CLASSPATH_PROVIDER_ATTR);
				if (o != null && o instanceof IRuntimeBundleClasspathProvider) {
					providers.add((IRuntimeBundleClasspathProvider) o);
				}
			}
		}
		return providers.toArray(new IRuntimeBundleClasspathProvider[0]);
	}

	/**
	 * @param urls
	 * @param jarPath
	 * @throws MalformedURLException
	 */
	public static void addCoreInternalLibEntry(Collection<URL> urls, String jarPath) throws MalformedURLException {
		File nativeFile = new File(jarPath);
		if (nativeFile.exists()) {
			if(!checkExistingEntry(urls, nativeFile.toURI().toURL())){
				urls.add(nativeFile.toURI().toURL());
			}
			
		}
	}

	
	/**
	 * @param urls
	 * @param url
	 * @return
	 */
	private static boolean checkExistingEntry(Collection<URL> urls, URL url) {
		return urls.contains(url);
	}
	
	/**
	 * @param path
	 * @return
	 */
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
