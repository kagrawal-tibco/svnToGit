package com.tibco.cep.studio.core.migration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.Columns;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;

/**
 * Migration for segregation of condition & action columns for 5.2 format.
 * @author aathalye
 * @author vdhumal
 *
 */
public class DecisionTableColumnOrderMigrator extends DefaultStudioProjectMigrator {

	@Override
	protected void migrateFile(File parentFile, File file, IProgressMonitor monitor) {
		String ext = getFileExtension(file);
		if ("rulefunctionimpl".equalsIgnoreCase(ext)) {
			try {
				processTable(parentFile, file);
			} catch (Exception e) {
				StudioCorePlugin.log(e);
			}
		}				
	}
	
	@Override
	public int getPriority() {
		return 2;
	}
	
	private void processTable(File parentFile, File file) throws Exception {
		FileInputStream fileInputStream = new FileInputStream(file);
		Table tableEModel = (Table) CommonIndexUtils.deserializeEObject(fileInputStream);
		reorderColumns(tableEModel.getDecisionTable());
		reorderColumns(tableEModel.getExceptionTable());
		saveTable(file, tableEModel);
	}
	
	private void reorderColumns(TableRuleSet tableRuleSet) {
		Columns tableColumns = tableRuleSet.getColumns();
		List<Column> reorderedColumns = new ArrayList<Column>();
		if (tableColumns != null) {
			for (Column column : tableColumns.getColumn()) {
				if (column.getColumnType().isConditon()) {
					reorderedColumns.add(column);
				}			
			}
			for (Column column : tableColumns.getColumn()) {
				if (column.getColumnType().isAction()) {
					reorderedColumns.add(column);
				}			
			}
			tableColumns.getColumn().clear();
			tableColumns.getColumn().addAll(reorderedColumns);
		}	
	}
	
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
