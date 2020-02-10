package com.tibco.cep.studio.ui.dialog;

import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.FilteredResourcesSelectionDialog;

/**
 * 
 * @author sasahoo
 *
 */
public class StudioFilteredElementSelector extends FilteredResourcesSelectionDialog  {

//--------  Use following code to use this dialog ---------------
//  Set<String> extensions = new HashSet<String>();
//	extensions.add(CommonIndexUtils.EVENT_EXTENSION);
//	StudioFilteredElementSelector picker = new StudioFilteredElementSelector(Display.getDefault().getActiveShell(), false, project, IResource.FILE, "Select Event", extensions);
//	if (picker.open() == Dialog.OK) {
//		Object file = picker.getFirstResult();
//	}

	
	/**
	 * @param shell
	 * @param multi
	 * @param container
	 * @param typesMask
	 * @param extensions
	 */
	public StudioFilteredElementSelector(Shell shell, boolean multi,
			IContainer container, int typesMask, String title, Set<String> extensions) {
		super(shell, multi, container, typesMask);
		addListFilter(new StudioResourceWorkingSetFilter(extensions));
		setInitialPattern("?", FULL_SELECTION);
		setTitle(title);
	}
}
