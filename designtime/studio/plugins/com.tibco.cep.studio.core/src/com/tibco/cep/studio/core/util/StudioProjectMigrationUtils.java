package com.tibco.cep.studio.core.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SubProgressMonitor;

import com.tibco.cep.container.cep_containerVersion;
import com.tibco.cep.studio.common.configuration.XPATH_VERSION;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.migration.IStudioProjectMigrationContext;
import com.tibco.cep.studio.core.migration.IStudioProjectMigrator;
import com.tibco.cep.studio.core.migration.StudioProjectMigrationContext;

public class StudioProjectMigrationUtils {

	public static class ProjectVersionComparator implements Comparator<String> {

		@Override
		public int compare(String version1, String version2) {
			String[] split1 = version1.split("\\.");
			String[] split2 = version2.split("\\.");

			if (split1.length == split2.length) {
				for (int i=0; i<split1.length; i++) {
					int ver1 = Integer.parseInt(split1[i]);
					int ver2 = Integer.parseInt(split2[i]);
					if (ver1 == ver2) {
						continue;
					}
					return ver1 > ver2 ? 1 : -1;
				}
			} else if (split1.length > split2.length) {
				for (int i=0; i<split2.length; i++) {
					int ver1 = Integer.parseInt(split1[i]);
					int ver2 = Integer.parseInt(split2[i]);
					if (ver1 == ver2) {
						continue;
					}
					return ver1 > ver2 ? 1 : -1;
				}
				return 1;
			} else if (split1.length < split2.length) {
				for (int i=0; i<split1.length; i++) {
					int ver1 = Integer.parseInt(split1[i]);
					int ver2 = Integer.parseInt(split2[i]);
					if (ver1 == ver2) {
						continue;
					}
					return ver1 > ver2 ? 1 : -1;
				}
				return -1;
			}
			return 0;
		}
		
	}
	
	private static class MigratorComparator implements Comparator<IStudioProjectMigrator> {

		@Override
		public int compare(IStudioProjectMigrator migrator1,
				IStudioProjectMigrator migrator2) {
			return migrator1.getPriority() > migrator2.getPriority() ? 1 : -1;
		}
		
	}
	
	private static final String MIGRATOR_EXT_PT_ID = "studioProjectMigrator";
	private static IStudioProjectMigrator[] fProjectMigrators;
	private static Comparator<? super IStudioProjectMigrator> comparator = new MigratorComparator();

	private synchronized static IStudioProjectMigrator[] getStudioProjectMigrators() {
		if (fProjectMigrators == null) {
			List<IStudioProjectMigrator> participants = new ArrayList<IStudioProjectMigrator>();
			IConfigurationElement[] configurationElementsFor = Platform.getExtensionRegistry().getConfigurationElementsFor(StudioCorePlugin.PLUGIN_ID, MIGRATOR_EXT_PT_ID);
			for (IConfigurationElement configurationElement : configurationElementsFor) {
				String attribute = configurationElement.getAttribute("studioProjectMigrator");
				if (attribute != null) {
					try {
						Object executableExtension = configurationElement.createExecutableExtension("studioProjectMigrator");
						if (executableExtension instanceof IStudioProjectMigrator) {
							participants.add((IStudioProjectMigrator) executableExtension);
						}
					} catch (CoreException e) {
						e.printStackTrace();
					}
				}
			}
			fProjectMigrators = new IStudioProjectMigrator[participants.size()];
			return participants.toArray(fProjectMigrators);
		}
		return fProjectMigrators;
	}

	/** 
	 * Queries all registered studio project migration extensions and allows them to modify
	 * the project/file contents before the new studio project is created.  This is done to allow
	 * add-ons to contribute to the migration in a decoupled manner.
	 * @param project - may be null in a command line context
	 * @param projectLocation
	 * @param targetLocation
	 * @param monitor
	 */
	public static void convertStudioProject(File projectLocation, Map<String, String> httpProperties, XPATH_VERSION xpathVersion, IProgressMonitor monitor) {
		if (monitor == null) {
			monitor = new NullProgressMonitor();
		}
		IStudioProjectMigrator[] studioProjectMigrators = getStudioProjectMigrators();
		IStudioProjectMigrationContext context = new StudioProjectMigrationContext(projectLocation, xpathVersion);
		context.setHttpPropertiesToMigrate(httpProperties);
		
		int size = studioProjectMigrators.length;
		monitor.beginTask("Updating project "+projectLocation.getName(), size);
		monitor.subTask("-- Updating project "+projectLocation.getName());
		
		// re-sort each time, as it is valid for the migrator to programmatically compute the priority
		Arrays.sort(studioProjectMigrators, comparator);
		for (IStudioProjectMigrator studioProjectMigrator : studioProjectMigrators) {
			SubProgressMonitor subMon = new SubProgressMonitor(monitor, 1);
			studioProjectMigrator.migrateProject(context, subMon);
			subMon.done();
			monitor.worked(1);
		}
		monitor.done();
	}

	public static void copyProjectFiles(File projectLocation, File targetLocation, IProgressMonitor monitor) {
		monitor.subTask("-- copying project resources");
		if (projectLocation.getAbsolutePath().equals(targetLocation.getAbsolutePath())) {
			// nothing to do, source and target are the same
			monitor.worked(1);
			return;
		}
		copyFolder(projectLocation, targetLocation, monitor);
		monitor.worked(1);
	}
	
	private static void copyFolder(File srcFolder, File destFolder, IProgressMonitor monitor) {
		monitor.subTask("-- copying "+srcFolder);
		if (srcFolder.isDirectory()) {

			if (!destFolder.exists()) {
				destFolder.mkdir();
			}

			String[] children = srcFolder.list();
			for (String child : children) {
				copyFolder(new File(srcFolder, child), new File(destFolder, child), monitor);
			}
		} else {

			if(destFolder.isDirectory()) {
				copyFile(srcFolder, new File(destFolder, srcFolder.getName()));
			} else {
				copyFile(srcFolder, destFolder);
			}
		}
	}

	private static void copyFile(File srcFile, File destFile) {

		InputStream in = null;
		OutputStream out = null;

		try {
			in = new FileInputStream(srcFile);
			out = new FileOutputStream(destFile);

			byte[] bytes = new byte[1024];
			int len;
			BufferedInputStream buff = new BufferedInputStream(in);

			while ((len = buff.read(bytes)) > 0) {
				out.write(bytes, 0, len);
			}
		}  catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}  catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				in.close();
				out.close();
			} catch(IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

	public static String getCurrentVersion() {
		return cep_containerVersion.version;
	}
	
	public static boolean checkIfFileTransformed(File locationFile, File newFile) {
    	// Better check will be to compare the file contents, or to compare CRC32/MD5 hash
    	if (locationFile.length() == newFile.length()) {
    		return false; 
		}
		return true;
	}
	
	public static boolean deleteDir(File file) {
	      if (file.isDirectory()) {
	         String[] list = file.list();
	         for (int i = 0; i < list.length; i++) {
	            boolean success = deleteDir(new File(file, list[i]));
	            if (!success) {
	               return false;
	            }
	         }
	      }
	      return file.delete();
    }

}
