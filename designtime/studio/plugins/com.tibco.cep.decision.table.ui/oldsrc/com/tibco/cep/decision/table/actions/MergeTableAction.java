package com.tibco.cep.decision.table.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.compare.CompareUI;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.structuremergeviewer.DiffContainer;
import org.eclipse.compare.structuremergeviewer.DiffNode;
import org.eclipse.compare.structuremergeviewer.Differencer;
import org.eclipse.compare.structuremergeviewer.IDiffElement;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.Columns;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decision.table.preferences.PreferenceConstants;
import com.tibco.cep.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.decision.table.utils.DecisionTableUtil;
import com.tibco.cep.decisionproject.ontology.AbstractResource;
import com.tibco.cep.decisionproject.ontology.Implementation;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.ui.compare.model.AbstractResourceNode;
import com.tibco.cep.studio.ui.compare.util.CompareUtils;
import com.tibco.cep.studio.ui.util.Messages;

/**
 * Action to automatically merge two Decision Tables.
 * If conflicts arise, the compare editor will be
 * opened to resolve the conflicts
 * 
 * @author rhollom
 * 
 */
public class MergeTableAction extends AbstractHandler implements IObjectActionDelegate {

	private static final int STATUS_OK = 0;
	private static final int STATUS_CONFLICT = 1;
	private static final int STATUS_CANCELED = 2;
	private ISelection selection;
	@SuppressWarnings("unused")
	private AbstractResource template;
	private boolean commandLineMode = false;
	private String projectName;
	private Implementation impl1;
	private Implementation impl2;
	
	private static final String CLASS = MergeTableAction.class.getName();

	/**
	 * @param template
	 * @param impl1
	 * @param impl2
	 * @param file
	 * @param projectName
	 */
	public MergeTableAction(AbstractResource template, 
			                Implementation impl1, 
			                Implementation impl2, 
			                IFile file, 
			                String projectName) {
		this.template = template;
		this.impl1 = impl1;
		this.impl2 = impl2;
		this.selection = new StructuredSelection(new Object[] {file} );
		this.projectName = projectName;
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		if (selection instanceof StructuredSelection) {
			StructuredSelection ss = (StructuredSelection) selection;
			try {
				impl1 = processTable((Table)impl1);
				impl2 = processTable((Table)impl2);
			} catch (OperationCanceledException e) {
				return impl1;
			}
			Table targetTable = (Table) impl1;
			Table srcTable = (Table) impl2;
			
			if (!compareColumns(targetTable, srcTable)) {
				return impl1;
			}
			
			DiffNode differences = CompareUtils.findDifferences(targetTable, srcTable, true);
			
			if (differences == null) {
				// no differences found
				if (commandLineMode) {
					DecisionTableUIPlugin.debug(CLASS,"No differences found");
				} else {
					MessageDialog.openInformation(Display.getDefault().getActiveShell(), Messages.getString("Compare.title"), Messages.getString("Merge.noDifferences"));
				}
				return impl1;
			}
			if (!commandLineMode) {
				saveTemporaryTable(targetTable);
			}
			int status = mergeDifferences(targetTable, srcTable, differences, STATUS_OK);
			if (status == STATUS_CONFLICT) {
				if (commandLineMode) {
					throw new ExecutionException("Conflict Detected, unable to perform merge");
				}
				
				// The files conflict so open the compare window
				TableCompareEditorInput input = new TableCompareEditorInput(impl2);
				input.setSelection(ss);
				CompareUI.openCompareEditor(input);
				return null;
				
			} else if (status == STATUS_OK) {
				// balance columns, if one was removed
				// only do this in a non-conflict case, as
				// conflict merges might still have additional columns
				balanceColumns(targetTable, srcTable);

				// write table to disk
				writeTable(targetTable);
				removeTemporaryTable(targetTable);
				
				if (commandLineMode) {
					System.out.print("Merge complete.  Saving table...");					
				} 
				if (!commandLineMode) {					
					DecisionTableUIPlugin.debug(CLASS,"done.");
				}
				
				return targetTable;
			}
		}
		return impl1;
	}

	/**
	 * @param targetTable
	 * @param srcTable
	 */
	private void balanceColumns(Table targetTable, Table srcTable) {
		if (targetTable.getDecisionTable() != null && srcTable.getDecisionTable() != null) {
			Columns targetColumns = targetTable.getDecisionTable().getColumns();
			Columns srcColumns = srcTable.getDecisionTable().getColumns();
			balanceColumns(targetTable.getDecisionTable(), srcTable.getDecisionTable(), targetColumns, srcColumns);
		}
		if (targetTable.getExceptionTable() != null && srcTable.getExceptionTable() != null) {
			Columns targetColumns = targetTable.getExceptionTable().getColumns();
			Columns srcColumns = srcTable.getExceptionTable().getColumns();
			balanceColumns(targetTable.getExceptionTable(), srcTable.getExceptionTable(), targetColumns, srcColumns);
		}
	}

	/*
	 * Compare the columns of the tables and ensure that they
	 * are equivalent.  If not, warn/prompt the user and
	 * potentially exit the merge
	 */
	private boolean compareColumns(Table targetTable, Table srcTable) {
		if (targetTable.getDecisionTable() != null && srcTable.getDecisionTable() != null) {
			Columns columns = targetTable.getDecisionTable().getColumns();
			Columns columns2 = srcTable.getDecisionTable().getColumns();
			if (!columnsAreEqual(columns, columns2) || !columnsAreEqual(columns2, columns)) {
				// warn user
				if (commandLineMode) {
					DecisionTableUIPlugin.debug(CLASS, "Warning: Columns do not match.  This could result in unintended results.");
				} else {
					boolean autoMergeColumns = DecisionTableUIPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.COMPARE_MERGE_AUTOMERGE_COLUMNS);
					if (!autoMergeColumns) {
						MessageDialog.openInformation(Display.getDefault().getActiveShell(), Messages.getString("Merge.title"), Messages.getString("Merge.mismatchedColumns.noMerge"));
						return false;
					}
					return MessageDialog.openConfirm(Display.getDefault().getActiveShell(), Messages.getString("Merge.title"), Messages.getString("Merge.mismatchedColumns"));
				}
			}
		}
		
		if (targetTable.getExceptionTable() != null && srcTable.getExceptionTable() != null) {
			Columns columns = targetTable.getExceptionTable().getColumns();
			Columns columns2 = srcTable.getExceptionTable().getColumns();
			if (!columnsAreEqual(columns, columns2) || !columnsAreEqual(columns2, columns)) {
				// warn user
				if (commandLineMode) {
					DecisionTableUIPlugin.debug(CLASS, "Warning: Columns do not match.  This could result in unintended results.");
				} else {
					return MessageDialog.openConfirm(Display.getDefault().getActiveShell(), Messages.getString("Merge.title"), Messages.getString("Merge.mismatchedColumns"));
				}
			}
		}
		
		return true;
	}

	private boolean balanceColumns(TableRuleSet targetTableRuleSet, TableRuleSet srcTableRuleSet, Columns targetColumns, Columns srcColumns) {
		if (srcColumns == null && targetColumns == null) return true;
	
		List<Column> targetColumnsList = new ArrayList<Column>();
		if (targetColumns != null){
			targetColumnsList = targetColumns.getColumn();
		}
		List<Column> srcColumnsList = new ArrayList<Column>();
		if (srcColumns != null){
			srcColumnsList = srcColumns.getColumn();
		}
		/*
		if (targetColumnsList == null && srcColumnsList == null) {
			return true;  // there should not be an issue in this case
		}
		*/
		if (targetColumnsList.size() == srcColumnsList.size()) {
			return true;
		}
		for (Column srcColumn : srcColumnsList) {
			String id = srcColumn.getId();
			boolean foundId = false;
			for (Column targetColumn : targetColumnsList) {
				if (id.equals(targetColumn.getId())) {
					foundId = true;
					break;
				}
			}
			if (!foundId) {
				// the src column was not found in the target table, add it
				// This shouldn't happen, as this type of balance is done
				// on the fly, but protect against it
				targetColumnsList.add(srcColumn);
				return false;
			}
		}
		for (Column targetColumn : targetColumnsList) {
			String id = targetColumn.getId();
			boolean foundId = false;
			for (Column srcColumn : srcColumnsList) {
				if (id.equals(srcColumn.getId())) {
					foundId = true;
					break;
				}
			}
			if (!foundId) {
				// the target column was not found in the src table, remove it (?)
				// this should only be done if the target column is not currently
				// in use, which is unfortunately difficult to determine efficiently
				if (!inUse(targetTableRuleSet, targetColumn)) {
					targetColumnsList.remove(targetColumn);
				}
				return false;
			}
		}
		return false;
	}

	// TODO : consider refactoring for make more efficient
	private boolean inUse(TableRuleSet ruleSet, Column targetColumn) {
		String targetColId = targetColumn.getId();
		EList<TableRule> rules = ruleSet.getRule();
		for (TableRule tableRule : rules) {
			if (!tableRule.isModified()) {
				// only check modified rules, as unmodified ones should have been merged (?)
				continue;
			}
			EList<TableRuleVariable> conditions = tableRule.getCondition();
			for (TableRuleVariable condition : conditions) {
				if (condition.getColId().equals(targetColId)) {
					// column is in use
					return true;
				}
			}
		}
		return false;
	}

	private boolean columnsAreEqual(Columns columns, Columns columns2) {
		if (columns == null && columns2 == null) {
			return true;  // there should not be an issue in this case
		}
		if (columns != null && columns2 == null) return false;
		
		if (columns == null && columns2 != null) return false;
		
		EList<Column> columns1List = columns.getColumn();
		EList<Column> columns2List = columns2.getColumn();
		if (columns1List == null && columns2List == null) {
			return true;  // there should not be an issue in this case
		}
		if (columns1List.size() != columns2List.size()) {
			return false;
		}
		for (Column column : columns1List) {
			String id = column.getId();
			boolean foundId = false;
			for (Column column2 : columns2List) {
				if (id.equals(column2.getId())) {
					foundId = true;
					break;
				}
			}
			if (!foundId) {
				return false;
			}
		}
		return true;
	}

	private void removeTemporaryTable(Table table) {
		String fileLoc = getTempFileLocation(table);
		File tempFile = new File(fileLoc);
		if (tempFile.exists()) {
			tempFile.delete();
		}
	}

	private void saveTemporaryTable(Table table) {
		String fileLoc = getTempFileLocation(table);
		URI uri = table.eResource().getURI();
		String fileString = uri.toFileString();
		FileInputStream in = null;
		FileOutputStream out = null;
		try {
			in = new FileInputStream(fileString);
			File manifestFile = new File(fileLoc);
			if (!manifestFile.exists()) {
				if (!manifestFile.getParentFile().exists()) {
					manifestFile.getParentFile().mkdirs();
				}
				if (!manifestFile.createNewFile()) {
					System.err.println("Unable to write backup table");
				}
			}
			out = new FileOutputStream(fileLoc);
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0){
				out.write(buf, 0, len);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param table
	 * @return
	 */
	private String getTempFileLocation(Table table) {
		IPath path = DecisionTableUIPlugin.getDefault().getStateLocation();
		String fileLoc = path.toOSString()+File.separator+"tempTables"+File.separator+table.getName();
		return fileLoc;
	}

	/**
	 * @param table
	 */
	private void writeTable(Table table) {
		try {
			ModelUtils.saveEObject(table); // G11N encoding changes
//			table.eResource().save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private int mergeDifferences(Table table1, Table table2,
			DiffNode differences, int status) {
		IDiffElement[] children = differences.getChildren();
		for (IDiffElement diffElement : children) {
			if (hasAddedRemoved(diffElement)) {
				int ret = processAddedRemoved((DiffNode)diffElement);
				if (ret == STATUS_CONFLICT) {
					status = ret;
				} else if (ret == STATUS_CANCELED) {
					return ret;
				}
			}
			int ret = mergeDifferences(table1, table2, (DiffNode) diffElement, status);
			if (ret == STATUS_CONFLICT) {
				status = ret;
			} else if (ret == STATUS_CANCELED) {
				return ret;
			}
		}
		return status;
	}

	private int processAddedRemoved(DiffNode diffNode) {
		ITypedElement left = diffNode.getLeft();
//		ITypedElement right = diffNode.getRight();
		IDiffElement[] children = diffNode.getChildren();
		int status = STATUS_OK;
		for (IDiffElement diffElement : children) {
			if (left instanceof AbstractResourceNode) {
				((AbstractResourceNode)left).setDirty(true);
			}
			if (diffElement instanceof DiffNode && 
					(diffElement.getKind() == Differencer.ADDITION || diffElement.getKind() == Differencer.DELETION)) {
				try {
					((DiffNode) diffElement).copy(false);
				} catch (IllegalArgumentException e) {
					status = STATUS_CONFLICT;
				}
			}
		}
		return status;
	}

	/**
	 * @param diffElement
	 * @return
	 */
	private boolean hasAddedRemoved(IDiffElement diffElement) {
		if (diffElement instanceof DiffContainer) {
			IDiffElement[] children = ((DiffContainer) diffElement).getChildren();
			for (IDiffElement child : children) {
				if (child.getKind() == Differencer.ADDITION
						|| child.getKind() == Differencer.DELETION) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * @param ss
	 * @return
	 */
	protected StructuredSelection reloadDirtyTables(StructuredSelection ss) {
		Object[] selections = ss.toArray();
		List<Table> processedTables = new ArrayList<Table>();
		for (Object object : selections) {
			if (object instanceof Table) {
				Table table = (Table) object;
				if (table.isDirty()) {
					Table tbl = processTable(table);
					// tbl is the table that we actually want to use
					tbl.getDecisionTable(); // so that the tables are lazily loaded
					processedTables.add(tbl);
				} else {
					table.getDecisionTable(); // so that the tables are lazily loaded
					processedTables.add(table);
				}
			}
		}
		return new StructuredSelection(processedTables);
	}

	private Table processTable(Table table) throws OperationCanceledException {
		// if the table is open in an editor, prompt to save. If the user opts
		// not
		// to save, then use a temporary representation of the table to compare,
		// rather
		// than reloading the table. Otherwise, the existing table in the editor
		// will
		// be overwritten with the reloaded one, and will cause problems when/if
		// the table
		// is saved.
		IEditorPart openEditor = DecisionTableUtil.findOpenEditor(table);
		if (openEditor != null && openEditor.isDirty()) {
			if (!openEditor.getSite().getPage().saveEditor(openEditor, true)) {
				// canceled, do not do the compare
				throw new OperationCanceledException();
			}
			if (openEditor.isDirty()) {
				DecisionTableUIPlugin.debug(getClass().getName(),
						com.tibco.cep.decision.table.ui.utils.Messages.getString("History_LoadTable"));
				return DecisionTableUtil.loadTemporaryTable(table, projectName);
			}
		}
		if (table.isDirty()) {
			DecisionTableUIPlugin.debug(getClass().getName(),
					com.tibco.cep.decision.table.ui.utils.Messages.getString("History_ReloadTable"));
			return DecisionTableUtil.reloadTable(table, projectName);
		}
		return table;
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {

	}

	public void run(IAction action) {
		try {
			execute(null);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}
	
	public boolean isCommandLineMode() {
		return commandLineMode;
	}

	public void setCommandLineMode(boolean commandLineMode) {
		this.commandLineMode = commandLineMode;
	}
}