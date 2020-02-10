package com.tibco.cep.decision.table.legacy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import com.tibco.cep.decision.table.legacy.validator.LegacyOntologyValidator;
import com.tibco.cep.decisionproject.persistence.impl.DecisionProjectLoader;
import com.tibco.cep.security.authz.utils.IOUtils;
import com.tibco.cep.studio.common.legacy.DecisionTableEntryStore;
import com.tibco.cep.studio.ui.util.StudioWorkbenchConstants;

/**
 * 
 * @author aathalye
 * @author sasahoo
 *
 */
public class ImportDecisionTablesOperation extends WorkspaceModifyOperation {
	
	//Base path of the Decision project
	private String dpPath;
	//Base path of the designer project
	private String designerProjectPath; 
	//Ontlogy Validator
	private LegacyOntologyValidator validator;
	
	public ImportDecisionTablesOperation(String dpPath, 
			                      String designerProjectPath,
			                      LegacyOntologyValidator validator) {
		this.dpPath = dpPath;
		this.designerProjectPath = designerProjectPath;
		this.validator = validator;
	}
	@Override
	protected void execute(IProgressMonitor monitor) throws CoreException, InvocationTargetException, InterruptedException {
		monitor.beginTask("Importing decision project", 100);
		DecisionTableEntryStore dtStore = validator.getDtStore();
		String dpBaseDir = getDPBaseDirectory();
		if (dpBaseDir == null) {
			throw new RuntimeException("Invalid directory for decision project");
		}
		monitor.worked(5);
		try {
			fetchingDecisionTables(dtStore.getDtPaths(), dpBaseDir, new SubProgressMonitor(monitor, 80));
			//Reset DP
			DecisionProjectLoader.getInstance().setDecisionProject(null);
			monitor.subTask("Decision Project import successful");
			monitor.worked(15);
		} catch (Exception e) {
			throw new InvocationTargetException(e);
		}
	}
	
	private void fetchingDecisionTables(List<String> dtPaths, 
			                            String dpBaseDir,
			                            IProgressMonitor monitor) throws Exception {
		monitor.beginTask("Fetching existing decision tables(this may take several seconds)",(dtPaths.size() * 2) + 15);
		StringBuilder sb = new StringBuilder();
		String extension = StudioWorkbenchConstants._DT_EXTENSION;
		@SuppressWarnings("unused")
		int path = 1;
		for (String dtPath : dtPaths ) {
			monitor.subTask("fetching " + dtPath);
			monitor.worked(1);
			path++;
			sb.append(dpBaseDir);
			sb.append(File.separator);
			sb.append(dtPath);
			sb.append(extension);
			File oldDTFile = new File(sb.toString());
			monitor.subTask("Attempting migration if needed for " + oldDTFile.getAbsolutePath());
			FileInputStream oldFile = new FileInputStream(oldDTFile);
			sb.delete(0, sb.length());
			
			sb.append(designerProjectPath);
			sb.append(File.separator);
			sb.append(dtPath);
			sb.append(extension);
			FileOutputStream newFile = new FileOutputStream(sb.toString());
			
			sb.delete(0, sb.length());
			IOUtils.writeBytes(oldFile, newFile);
		}
	}
	
		
	private String getDPBaseDirectory() {
		int lastIndex = dpPath.lastIndexOf(File.separator);
		String baseDir = null;
		if (lastIndex != -1) {
			baseDir = dpPath.substring(0, lastIndex);
		}
		return baseDir;
	}
}
