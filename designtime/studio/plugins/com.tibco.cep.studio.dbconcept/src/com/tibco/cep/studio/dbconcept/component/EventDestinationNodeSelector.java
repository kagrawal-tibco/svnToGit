package com.tibco.cep.studio.dbconcept.component;

import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Shell;

import com.tibco.cep.studio.dbconcept.utils.Messages;
import com.tibco.cep.studio.ui.navigator.model.ChannelDestinationNode;

/**
 * 
 * @author majha
 *
 */
public class EventDestinationNodeSelector extends FileSelector {

	public EventDestinationNodeSelector(Shell parent, String currentProjectName,
			Set<String> ext) {
		super(parent, currentProjectName, ext);
	}

	@Override
	protected String getMessage() {
		return Messages.getString("Event_Destination_Selector_Dialog_Title");
	}

	@Override
	protected String getSelectionErrorMsg() {
		return Messages.getString("Event_Destination_Selector_Dialog_Message");
	}

	@Override
	protected String getSelectionMsgFormat() {
		return Messages.getString("Event_Destination_Selector_Message_format");
	}

	@Override
	protected String getTitle() {
		return Messages.getString("Event_Destination_Selector_Error_Message");
	}

	@Override
	protected boolean isFileSerachInSubDirectoryAllowed() {
		return true;
	}
	
	@Override
	protected String getLocationURI(Object selection) {
		if((selection instanceof ChannelDestinationNode)){
			ChannelDestinationNode file = (ChannelDestinationNode)selection;
			TreeViewer treeViewer2 = getTreeViewer();
			TreePath[] paths = ((TreeSelection)treeViewer2.getSelection()).getPathsFor(selection);
			String path = "/";
			IFile lastSegment = (IFile)paths[0].getParentPath().getLastSegment();
			IPath projectRelativePath = lastSegment.getProjectRelativePath();
			path = path+projectRelativePath.toString()+"/"+file.getName();
			
			return path;
		}
		return null;
	}

}
