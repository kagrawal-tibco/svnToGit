package com.tibco.cep.studio.dbconcept.component;

import org.eclipse.core.resources.IFolder;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.tibco.cep.studio.dbconcept.utils.Messages;

public class ConceptFolderSelector extends FolderSelector {

	public ConceptFolderSelector(Shell parent, String currentProjectName) {
		super(parent, currentProjectName);
	}

	@Override
	protected String getMessage() {
		return Messages.getString("Concept_Folder_Selector_Dialog_Title");
	}

	@Override
	protected String getSelectionErrorMsg() {
		return Messages.getString("Concept_Folder_Selector_Dialog_Message");
	}

	@Override
	protected String getSelectionMsgFormat() {
		return Messages.getString("Concept_Folder_Selector_Message_format");
	}

	@Override
	protected String getTitle() {
		return Messages.getString("Concept_Folder_Selector_Error_Message");
	}

	@Override
	protected String getLocationURI(Object selection) {
		if((selection instanceof IFolder)){
			IFolder file = (IFolder)selection;
			return "/"+ file.getProjectRelativePath();
		}
		return null;
	}
	
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createMkDirButtonForButtonBar(parent);
		super.createButtonsForButtonBar(parent);
	}
	
}
