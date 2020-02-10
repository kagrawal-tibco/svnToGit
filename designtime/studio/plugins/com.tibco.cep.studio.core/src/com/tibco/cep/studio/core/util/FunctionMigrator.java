/**
 * 
 */
package com.tibco.cep.studio.core.util;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import com.tibco.cep.studio.common.configuration.XPATH_VERSION;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.migration.IStudioProjectMigrationContext;
import com.tibco.cep.studio.core.migration.IStudioProjectMigrator;
import com.tibco.cep.studio.core.migration.StudioProjectMigrationContext;
import com.tibco.cep.studio.core.utils.ModelUtils;

/**
 * Base Function Migrator class covering most of the common migration functions
 * 
 * @author vpatil
 */
public abstract class FunctionMigrator implements IStudioProjectMigrator {
	protected static final String CRLF = "\r\n";
	protected static final String TAB = "\t";
	protected static final String COMMENTS = "//";
	protected IStudioProjectMigrationContext context;
	
	public void migrateFunctionCalls(File studioProjDir) {
		context = new StudioProjectMigrationContext(studioProjDir, XPATH_VERSION.XPATH_20);
		File[] files = studioProjDir.listFiles();
		for (File file : files) {
			processFile(file);
		}
	}
	
	private void processFile(File file) {
		if (file.isDirectory()) {
			migrateFunctionCalls(file);
		}
		String fileName = file.getName();
		int idx = fileName.lastIndexOf('.');
		if (idx < 0) {
			return;
		}
		String ext = fileName.substring(idx+1);
		if (isValidEntity(ext)) {
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(file);
				StringBuilder sb = new StringBuilder();
				int c = -1;
				while ((c = fis.read()) != -1) {
					// Since it is a char read cast it
					char character = (char) c;
					sb.append(character);
				}
				createReplaceEdits(sb, file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					fis.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	protected abstract void createReplaceEdits(StringBuilder sb, File file);
	
	protected void writeFile(StringBuilder sb, File file) {
		System.out.println("Saving converted file: "+file.getName());
		byte[] bytes;
		FileOutputStream fos = null;
		try {
			bytes = sb.toString().getBytes(ModelUtils.DEFAULT_ENCODING);
			fos = new FileOutputStream(file);
			fos.write(bytes);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.flush();
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public void migrateProject(IStudioProjectMigrationContext context,
			IProgressMonitor monitor) {
		try {
			this.context = context;
			File projectLocation = context.getProjectLocation();
			monitor.subTask("Migrate Function Calls...");
			// find resources to migrate
			List<File> resources = new LinkedList<File>();
			search(projectLocation, resources);
			for (File resource : resources) {
				monitor.subTask(resource.getAbsolutePath().substring(context.getProjectLocation().getAbsolutePath().length())+"...");
				processFile(resource);
			}
		} catch (CoreException e) {
			StudioCorePlugin.getDefault().getLog().log(e.getStatus());
			throw new RuntimeException(e);
		}
	}
	
	private void search(File location, List<File> hits) throws CoreException {
		File[] files = location.listFiles(new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				if (pathname.isDirectory()) {
					return true;
				}
				if (pathname.isFile()) {
					String ext = pathname.getName().substring(pathname.getName().lastIndexOf(".") + 1);
					if (IndexUtils.isEntityType(ext) || IndexUtils.isImplementationType(ext)
							|| IndexUtils.isRuleType(ext)) {
						return true;
					}
				}
				return false;
			}

		});
		if (files == null) {
			return;
		}
		for (File file : files) {
			if (file.isDirectory()) {
				search(file, hits);
			}
			else if (file.isFile()){
				hits.add(file);
			}
		}
	}
	
	protected boolean isValidEntity(String extension) {
		return IndexUtils.isEntityType(extension) || IndexUtils.isImplementationType(extension)	|| IndexUtils.isRuleType(extension);
	}
}
