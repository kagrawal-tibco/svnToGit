package com.tibco.cep.decision.table.migration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.migration.DefaultStudioProjectMigrator;

/**
 * Abstract class to Migrate Decision Table Features from 4.x to 5.1 format
 * 
 * @author vdhumal
 * 
 */
public abstract class DefaultDecisionTableMigrator extends DefaultStudioProjectMigrator {

	@Override
	public abstract int getPriority();

	@Override
	public final void migrateFile(File parentFile, File file, IProgressMonitor monitor) {
		String ext = getFileExtension(file);
		if ("rulefunctionimpl".equalsIgnoreCase(ext)) {
			monitor.subTask(" Attempting Decision Table migration " + file.getName());
			try {
				FileInputStream fileInputStream = new FileInputStream(file);
				Table tableEModel = (Table) CommonIndexUtils.deserializeEObject(fileInputStream);
				if (migrateTable(file, tableEModel)) {
					saveTable(file, tableEModel);
				}
			} catch (Exception e) {
				StudioCorePlugin.log(e);
			}
		}
	}

	/**
	 * Sub classes should implement this method to update the Table Model
	 * 
	 * @param resourceFile
	 * @param tableEModel
	 */
	public abstract boolean migrateTable(File resourceFile, Table tableEModel);

	/**
	 * Saves the Table Model
	 * 
	 * @param resourceFile
	 *            File
	 * @param tableEModel
	 *            Table Model to be saved to File
	 * @throws IOException
	 */
	private void saveTable(File resourceFile, Table tableEModel) throws IOException {
		String resourceLocation = resourceFile.getAbsolutePath();
		XMIResource resource = new XMIResourceImpl(URI.createFileURI(resourceLocation));
		resource.getContents().add(tableEModel);
		InputStream is = null;
		OutputStream os = null;
		File tmpFile = null;
		try {
			is = new FileInputStream(resourceFile);
			if (!resourceFile.getParentFile().exists()) {
				resourceFile.getParentFile().mkdirs();
			}
			tmpFile = new File(resourceFile.getAbsolutePath() + "_tmp");
			os = new FileOutputStream(tmpFile);
			resource.save(os, null);
		} catch (FileNotFoundException e) {
			StudioCorePlugin.log(e);
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (os != null) {
					os.flush();
					os.close();
				}
			} catch (Exception e2) {
				// Do Nothing
			}
		}
		if (tmpFile != null) {
			if (resourceFile.exists()) {
				resourceFile.delete();
			}
			tmpFile.renameTo(resourceFile);
			if (tmpFile.exists()) {
				tmpFile.delete();
			}
		}
	}
}
