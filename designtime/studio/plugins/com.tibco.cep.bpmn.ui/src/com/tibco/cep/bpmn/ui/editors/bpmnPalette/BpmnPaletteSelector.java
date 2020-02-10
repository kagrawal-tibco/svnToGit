package com.tibco.cep.bpmn.ui.editors.bpmnPalette;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;

import com.tibco.cep.bpmn.core.BpmnCoreConstants;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.Messages;
import com.tibco.cep.studio.ui.filter.StudioProjectsOnly;
import com.tibco.cep.studio.ui.wizards.AbstractResourceElementSelector;
import com.tibco.cep.studio.ui.wizards.OnlyFileInclusionFilter;

/**
 * 
 * @author mgoel
 *
 */

public class BpmnPaletteSelector extends AbstractResourceElementSelector implements  ISelectionStatusValidator {
	
	private IFile file;
	private String palettePath;

	public BpmnPaletteSelector(Shell parent,IProject project) {
		super(parent);
		setTitle(Messages.getString("Select_Palette"));
		setMessage(Messages.getString("Select_Palette"));
		addFilter(new StudioProjectsOnly(project.getName()));
		Set<String> extensions = new HashSet<String>();
		extensions.add(BpmnCoreConstants.PALETTE_FILE_EXTN);
		addFilter(new OnlyFileInclusionFilter(extensions));
		setValidator(this);
		setInput(ResourcesPlugin.getWorkspace().getRoot());
	}

	@Override
	public IStatus validate(Object[] selection) {
		if (selection != null && selection.length == 1) {
			if (selection[0] instanceof IFile) {
				file = (IFile)selection[0];
				String projectName = file.getProject().getName();
				palettePath = file.getFullPath().makeRelative().toPortableString();
				palettePath = palettePath.substring(palettePath.indexOf(projectName)+projectName.length());
				palettePath.replace("\\", "/");
				String statusMessage = MessageFormat.format(
						Messages.getString("Palette_Selector_Message_format"),
						new Object[] { (palettePath != null ? palettePath: "") });
				return new Status(Status.OK,BpmnUIPlugin.PLUGIN_ID, Status.OK, statusMessage, null);
			}
		}
		return new Status(Status.ERROR, BpmnUIPlugin.PLUGIN_ID,
				Status.ERROR, Messages.getString("Palette_Selector_Error_Message"), null);
	}

}
