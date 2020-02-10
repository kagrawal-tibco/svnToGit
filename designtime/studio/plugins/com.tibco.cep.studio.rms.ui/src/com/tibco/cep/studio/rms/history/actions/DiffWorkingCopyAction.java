package com.tibco.cep.studio.rms.history.actions;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareUI;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;

import com.tibco.cep.studio.rms.history.RMSHistoryEditor;
import com.tibco.cep.studio.rms.history.RMSHistoryEditorInput;
import com.tibco.cep.studio.ui.StudioUIPlugin;

/**
 * 
 * @author sasahoo
 *
 */
public class DiffWorkingCopyAction extends Action {
	
	private TableViewer viewer;
	private RMSHistoryEditor editor;
	
	/**
	 * @param editor
	 * @param viewer
	 * @param projectName
	 */
	public DiffWorkingCopyAction(RMSHistoryEditor editor, TableViewer viewer) {
		super("Compare with working copy");
		this.viewer = viewer;
		this.editor = editor;
	}
	
	
	@Override
	public boolean isEnabled() {
		ISelection selection = viewer.getSelection();
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection)selection;
			int selectionSize = structuredSelection.size();
			if (selectionSize != 1) {
				setEnabled(false);
				return false;
			}
		}
		setEnabled(true);
		return true;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		if (!viewer.getSelection().isEmpty() && viewer.getSelection() instanceof IStructuredSelection) {
			StructuredSelection s = (StructuredSelection)viewer.getSelection();
			CompareConfiguration cc = new CompareConfiguration(StudioUIPlugin.getDefault().getPreferenceStore());
			RMSHistoryEditorInput editorInput = (RMSHistoryEditorInput)editor.getEditorInput();
			String localProjectName = editorInput.getLocalProjectName();
			String repoProjectName = editorInput.getRepoProjectName();
			String url = editorInput.getHistoryURL();
			RMSHistoryCompareEditorInput input = new RMSHistoryCompareEditorInput(cc, url, localProjectName, repoProjectName);
			input.setSelection(s);
			CompareUI.openCompareDialog(input);
		}
	}
}