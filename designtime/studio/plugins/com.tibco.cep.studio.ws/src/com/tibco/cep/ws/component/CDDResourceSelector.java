package com.tibco.cep.ws.component;

import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.swt.widgets.Shell;

import com.tibco.cep.ws.utils.Messages;

/**
 * 
 * @author majha
 *
 */
public class CDDResourceSelector extends FileSelector {

	public CDDResourceSelector(Shell parent, String currentProjectName,
			Set<String> ext) {
		super(parent, currentProjectName, ext);
	}

	@Override
	protected String getMessage() {
		return Messages.getString("CDD_Resource_Selector_Dialog_Message");
	}

	@Override
	protected String getSelectionErrorMsg() {
		return Messages.getString("CDD_Resource_Selector_Error_Message");
	}

	@Override
	protected String getSelectionMsgFormat() {
		return Messages.getString("CDD_Resource_Selector_Message_format");
	}

	@Override
	protected String getTitle() {
		return Messages.getString("CDD_Resource_Selector_Dialog_Title");
	}

	@Override
	protected boolean isFileSerachInSubDirectoryAllowed() {
		return true;
	}

	@Override
	protected String getLocationURI(Object selection) {
		if((selection instanceof IFile)){
			IFile file = (IFile)selection;
			return "/"+ file.getProjectRelativePath();
		}
		return null;
	}
	
}
