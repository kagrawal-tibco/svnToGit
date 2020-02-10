package com.tibco.cep.studio.ui.dialog;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;

import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.util.Messages;

/*
@author ssailapp
@date Mar 4, 2010 3:03:55 PM
 */

public class SharedResourceSelectionDialog extends ElementTreeSelectionDialog implements ISelectionStatusValidator {

	private boolean fileSelection = false;
	
	public SharedResourceSelectionDialog(Shell parent, ILabelProvider labelProvider, ITreeContentProvider contentProvider, IProject project, boolean fileSelection) {
		super(parent, labelProvider, contentProvider);
		setTitle("Resource Selection");
		setMessage("Select a resource: ");
		setInput(project);
		setValidator(this);
		if (!fileSelection)
			setDoubleClickSelects(false);
		this.fileSelection = fileSelection;
	}

	@Override
	public IStatus validate(Object[] selection) {
		if (selection != null && selection.length != 0) {
			if (selection.length != 1) {
				return new Status(Status.ERROR, StudioUIPlugin.PLUGIN_ID, Status.ERROR, Messages.getString("selection.select_single_resource"), null);
			} 
			if (fileSelection) {
				Object obj = selection[0];
				if (! (obj instanceof IFile)) {
	                return new Status(Status.ERROR, StudioUIPlugin.PLUGIN_ID, Status.ERROR, Messages.getString("selection.select_file"), null);
	            } 
			}
			return new Status(Status.OK, StudioUIPlugin.PLUGIN_ID, Status.ERROR, "", null);
        }
        return new Status(Status.ERROR, StudioUIPlugin.PLUGIN_ID, Status.ERROR, Messages.getString("selection.select_resource"), null);
	}

}
