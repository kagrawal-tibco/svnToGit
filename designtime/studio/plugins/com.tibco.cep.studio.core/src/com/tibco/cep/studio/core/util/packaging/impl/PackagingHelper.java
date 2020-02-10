package com.tibco.cep.studio.core.util.packaging.impl;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

import com.tibco.be.parser.semantic.FunctionsCatalogManager;
import com.tibco.cep.studio.common.configuration.ConfigurationFactory;
import com.tibco.cep.studio.common.configuration.CoreJavaLibEntry;
import com.tibco.cep.studio.common.configuration.CustomFunctionLibEntry;
import com.tibco.cep.studio.common.configuration.LIBRARY_PATH_TYPE;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.common.configuration.ThirdPartyLibEntry;
import com.tibco.cep.studio.core.BEClassPathContainer;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectConfigurationManager;
import com.tibco.cep.studio.core.repo.emf.EMFProject;

/*
* Author: Ashwin Jayaprakash / Date: Dec 10, 2009 / Time: 3:16:42 PM
*/

public class PackagingHelper {
    /**
     * Prepends the URLs from the given ClassLoader to the classpath specified.
     *
     * @param classpath
     * @param cl
     * @return
     */
    public static String prependURLPaths(String classpath, URLClassLoader cl) {
        String pathSep = System.getProperty("path.separator");
        boolean dirStartsWithSlash = System.getProperty("user.dir").startsWith("/");
        StringBuilder builder = new StringBuilder();

        URL[] urls = cl.getURLs();
        for (URL url : urls) {
            String s = url.getPath();
            int length = s.length();
            if (length > 1 && s.startsWith("/") && dirStartsWithSlash == false) {
                s = s.substring(1, length);
            }

            if (builder.length() > 0) {
                builder.append(pathSep);
            }
            builder.append(s);
        }

        if (builder.length() > 0) {
            builder.append(pathSep);
            builder.append(classpath);

            classpath = builder.toString();
        }

        return classpath;
    }
    
    /**
     * Get project classpath from the {@link StudioProjectConfiguration} associated and append java class path
     * with this {@link EMFProject}
	 * @param config
	 * @return
	 */
	public static <E extends EMFProject> String getProjectClassPath(E project) {
		StringBuilder sb = new StringBuilder();
		sb.append(System.getProperty("java.class.path")).append(File.pathSeparator);
		String key = project.getRepoPath().replaceAll("\\\\", "/");
		key += "_extended.build.class.path";
		String extCP = (System.getProperty(key) != null)? System.getProperty(key) : System.getenv(key);
		if (extCP != null) {
			// this could be set via the CodeGenerationHelper.buildEar custom function
			sb.append(extCP).append(File.pathSeparator);
		}
		sb.append(getProjectPath(project));
		final String classpath = sb.toString();
		return classpath;
	}
	
	/**
	 * Get project related classpath only from the {@link StudioProjectConfiguration} associated
	 * @param project
	 * @return
	 */
	public static<E extends EMFProject> String getProjectPath(E project){
		StudioProjectConfiguration projectConfiguration = project.getProjectConfiguration();
		return getProjectPath(projectConfiguration);
	}

	public static String getProjectPath(StudioProjectConfiguration projectConfiguration) {
		StringBuilder sb = new StringBuilder();
		List<CoreJavaLibEntry> entries = projectConfiguration.getCoreInternalLibEntries();
		for (CoreJavaLibEntry coreJavaLibEntry : entries) {
			String path =coreJavaLibEntry.getPath(coreJavaLibEntry.isVar());
			sb.append(path).append(File.pathSeparator);
		}
		List<ThirdPartyLibEntry> tpentries = projectConfiguration.getThirdpartyLibEntries();
		for (ThirdPartyLibEntry thirdPartyLibEntry : tpentries) {
			String path =thirdPartyLibEntry.getPath(thirdPartyLibEntry.isVar());
			sb.append(path).append(File.pathSeparator);
		}
		EList<CustomFunctionLibEntry> cfEntries = projectConfiguration.getCustomFunctionLibEntries();
		for (CustomFunctionLibEntry customFunctionLibEntry : cfEntries) {
			String path =customFunctionLibEntry.getPath(customFunctionLibEntry.isVar());
			sb.append(path).append(File.pathSeparator);
		}
		loadJavaBuildPath(projectConfiguration.getName(), sb);
		return sb.toString();
	}
	
	private static void loadJavaBuildPath(String projName, StringBuilder sb) {
		try {
			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projName);
			IJavaProject javaProj = JavaCore.create(project);
			if (javaProj.exists()) {
				IClasspathEntry[] classpath = javaProj.getRawClasspath();
				addEntries(javaProj, classpath, sb);
			}
		} catch (JavaModelException e) {
			// should not prevent the build, print the exception and continue
			e.printStackTrace();
		}
	}

	private static void addEntries(IJavaProject javaProj, IClasspathEntry[] classpath, StringBuilder sb) throws JavaModelException {
		for (IClasspathEntry entry : classpath) {
			IPath path = entry.getPath();
			if (entry.getEntryKind() == IClasspathEntry.CPE_CONTAINER) {
				if ("org.eclipse.jdt.launching.JRE_CONTAINER".equals(path.toString())
						|| BEClassPathContainer.BE_CLASSPATH_CONTAINER.equals(path.toString())) {
					// these have already been added - skip
					continue;
				}
					
				IClasspathEntry[] classpathEntries = JavaCore.getClasspathContainer(path, javaProj).getClasspathEntries();
				addEntries(javaProj, classpathEntries, sb);
			} else if (entry.getEntryKind() == IClasspathEntry.CPE_SOURCE) {
				IPath srcPath = javaProj.getProject().getLocation().append(path.removeFirstSegments(1));
				sb.append(srcPath.toString()).append(File.pathSeparator);
			} else {
				sb.append(path.toString()).append(File.pathSeparator);
			}
		}
		
	}

	public static void loadExtendedClasspathOntologyFunctions(String extendedClasspath, EMFProject project) throws Exception {
		FunctionsCatalogManager.getInstance().loadFunctionCatalogFromClasspath(extendedClasspath,project);
		FunctionsCatalogManager.getInstance().loadFunctionCatalogFromClasspath(project.getName(), extendedClasspath);
	}
	
	public static void addExtendedClasspathEntires(String extendedClasspath,
			String projectName) {
		if (extendedClasspath == null || projectName == null) {
			return;
		}

		StudioProjectConfiguration studioProjectConfiguration = StudioProjectConfigurationManager
				.getInstance().getProjectConfiguration(projectName);
		if (studioProjectConfiguration == null) {
			return;
		}

		String classpathEntries[] = extendedClasspath.split(File.pathSeparator);
		for (String classpathEntry : classpathEntries) {
			if (classpathEntry.endsWith(".jar")) {
				if (new File(classpathEntry).exists() && !isEntryAdded(classpathEntry, studioProjectConfiguration)) {
					try {
						JarFile archiveFile = new JarFile(classpathEntry);
						ZipEntry zipentry = archiveFile
								.getEntry("functions.catalog");
						if (zipentry == null) {
							// Not a catalog function jar so add to third party
							// entries
							ThirdPartyLibEntry entry = ConfigurationFactory.eINSTANCE
									.createThirdPartyLibEntry();
							entry.setEntryType(LIBRARY_PATH_TYPE.THIRD_PARTY_LIBRARY);
							entry.setVar(false);// All commandline classpaths
												// are required to be absolute;
							entry.setPath(classpathEntry);
							studioProjectConfiguration
									.getThirdpartyLibEntries().add(entry);
						}
					} catch (IOException e) {
					} catch (Exception e) {
					}
				}
			}
		}
	}
	
	private static boolean isEntryAdded(String filePathToCheck, StudioProjectConfiguration configuration) {
		EList<ThirdPartyLibEntry> entries = configuration.getThirdpartyLibEntries();
		for (ThirdPartyLibEntry bpe : entries) {		
			String filePath = bpe.getPath(bpe.isVar());
			if (filePath.equals(filePathToCheck)) {
				return true;
			}
		}
		
		return false;
	}
}
