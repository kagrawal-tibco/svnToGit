package com.tibco.cep.studio.dbconcept.component;

import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.swt.widgets.Shell;

import com.tibco.cep.studio.dbconcept.utils.Messages;

/**
 * 
 * @author majha
 *
 */
public class JdbcResourceSelector extends FileSelector {

	public JdbcResourceSelector(Shell parent, String currentProjectName,
			Set<String> ext) {
		super(parent, currentProjectName, ext);
	}

	@Override
	protected String getMessage() {
		return Messages.getString("JDBC_Resource_Selector_Dialog_Title");
	}

	@Override
	protected String getSelectionErrorMsg() {
		return Messages.getString("JDBC_Resource_Selector_Dialog_Message");
	}

	@Override
	protected String getSelectionMsgFormat() {
		return Messages.getString("JDBC_Resource_Selector_Message_format");
	}

	@Override
	protected String getTitle() {
		return Messages.getString("JDBC_Resource_Selector_Error_Message");
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
