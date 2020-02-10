package com.tibco.cep.studio.dashboard.ui.dialogs;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;

import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.ui.filter.StudioProjectsOnly;
import com.tibco.cep.studio.ui.forms.components.FileInclusionFilter;
import com.tibco.cep.studio.ui.wizards.AbstractResourceElementSelector;

public class ImageSelectionDialog extends AbstractResourceElementSelector implements ISelectionStatusValidator {

	private static final String DEFAULT_MESSAGE = "Select an image...";

	private List<String> allowedImageTypes;

	public ImageSelectionDialog(Shell parent, String projectName, String[] allowedImageTypes) {
		super(parent);
		this.allowedImageTypes = Arrays.asList(allowedImageTypes);
		setTitle("Image Selector");
        setMessage(DEFAULT_MESSAGE);
        setAllowMultiple(false);
        addFilter(new StudioProjectsOnly(projectName));
        addFilter(new FileInclusionFilter(new HashSet<String>(this.allowedImageTypes)));
        setValidator(this);
        setInput(ResourcesPlugin.getWorkspace().getRoot());
	}

	@Override
	public IStatus validate(Object[] selection) {
		if (selection != null) {
			Object firstSelection = selection[0];
			if (firstSelection instanceof IResource) {
				String ext = ((IResource) firstSelection).getFileExtension();
				if (ext != null && allowedImageTypes.contains(ext.toLowerCase()) == true){
					return new Status(IStatus.OK, DashboardUIPlugin.PLUGIN_ID, null);
				}
			}
		}
		return new Status(IStatus.ERROR, DashboardUIPlugin.PLUGIN_ID, DEFAULT_MESSAGE);
	}

}
