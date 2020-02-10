package com.tibco.cep.studio.core.jdt;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.ClasspathContainerInitializer;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;

import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.TibExtClassPathContainer;

public class TibExtClasspathContainerInitializer extends ClasspathContainerInitializer {

	public TibExtClasspathContainerInitializer() {
	}

	@Override
	public void initialize(IPath containerPath, IJavaProject project) throws CoreException {

		List<IClasspathEntry> projectLibs = getLibPaths(project);
		TibExtClassPathContainer container = new TibExtClassPathContainer(containerPath, projectLibs.toArray(new IClasspathEntry[0]));
		if (container.isValid()) {
			JavaCore.setClasspathContainer(containerPath, new IJavaProject[] { project }, new IClasspathContainer[] { container }, null);
		}

	}

	private List<IClasspathEntry> getLibPaths(IJavaProject project) {
		List<IClasspathEntry> entries = new ArrayList<IClasspathEntry>();
		String beHome = System.getProperty("BE_HOME");
		if (beHome == null || beHome.length() == 0) {
			return entries;
		}
		IPath extPath = new Path(beHome).append("lib").append("ext");
		IPath tibPath = extPath.append("tibco");
		File extDir = new File(tibPath.toOSString());
		if (extDir.exists()) {
			String[] list = extDir.list();
			for (String extLib : list) {
				IPath path = tibPath.append(extLib);
				if (path.isAbsolute()) {
					entries.add(JavaCore.newLibraryEntry(path, null, null));
				} else {
					StudioCorePlugin.log(new Status(IStatus.WARNING, StudioCorePlugin.PLUGIN_ID, "Path for External Tibco Library must be absolute: ["+project.getElementName()+"] "+extLib));
				}
			}
		}
		IPath tpclPath = extPath.append("tpcl");
		File tpclDir = new File(tpclPath.toOSString());
		if (tpclDir.exists()) {
			String[] list = tpclDir.list();
			for (String tpclLib : list) {
				if (tpclLib.contains("jackson")) {
					// jackson libraries are required for JSON support
					IPath path = tpclPath.append(tpclLib);
					if (path.isAbsolute()) {
						entries.add(JavaCore.newLibraryEntry(path, null, null));
					} else {
						StudioCorePlugin.log(new Status(IStatus.WARNING, StudioCorePlugin.PLUGIN_ID, "Path for External Tibco Library must be absolute: ["+project.getElementName()+"] "+tpclLib));
					}
				}
			}
		}		
		// apache jars are required for HTTP channels
		IPath apachePath = tpclPath.append("apache");
		File apacheDir = new File(apachePath.toOSString());
		if (apacheDir.exists()) {
			String[] list = apacheDir.list();
			for (String apacheLib : list) {
				IPath path = apachePath.append(apacheLib);
				if (!"jar".equalsIgnoreCase(path.getFileExtension())) {
					continue;
				}
				if (path.isAbsolute()) {
					entries.add(JavaCore.newLibraryEntry(path, null, null));
				} else {
					StudioCorePlugin.log(new Status(IStatus.WARNING, StudioCorePlugin.PLUGIN_ID, "Path for External Tibco Library must be absolute: ["+project.getElementName()+"] "+apacheLib));
				}
			}
		}
		String asHome = System.getProperty("AS_HOME");
		if (asHome == null || asHome.length() == 0) {
			// attempt to lookup relative to BE_HOME
			IPath asDirPath = new Path(beHome).removeLastSegments(2).append("as");
			File asDir = new File(asDirPath.toOSString());
			if (asDir.exists()) {
				IPath asCommonPath = findFile("as-common.jar", asDir);
				if (asCommonPath != null) {
					entries.add(JavaCore.newLibraryEntry(asCommonPath, null, null));
				}
			}
			return entries;
		}
		return entries;
	}

	private IPath findFile(String fileName, File asDir) {
		File[] files = asDir.listFiles();
		for (File file : files) {
			// look for nested 'lib' directory
			if (file.isDirectory()) {
				IPath p = findFile(fileName, file);
				if (p != null) {
					return p;
				}
			} else if (fileName.equals(file.getName())) {
				return new Path(file.getAbsolutePath());
			}
		}

		return null;
	}

}
