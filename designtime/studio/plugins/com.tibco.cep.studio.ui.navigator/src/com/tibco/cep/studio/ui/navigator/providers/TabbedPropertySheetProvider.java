package com.tibco.cep.studio.ui.navigator.providers;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.navigator.resources.plugin.WorkbenchNavigatorPlugin;
import org.eclipse.ui.navigator.IDescriptionProvider;
import org.eclipse.ui.navigator.INavigatorContentService;

import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;

/**
 *  Defines a label provider for the title bar in the tabbed properties view.
 * @author sasahoo
 *
 */
@SuppressWarnings("restriction")
public class TabbedPropertySheetProvider extends LabelProvider {

	private ILabelProvider labelProvider;

	private IDescriptionProvider descriptionProvider;

	/**
	 * Constructor for CommonNavigatorTitleProvider.
	 */
	public TabbedPropertySheetProvider() {
		super();
		IWorkbenchPart part = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart();

		INavigatorContentService contentService = (INavigatorContentService) part
				.getAdapter(INavigatorContentService.class);

		if (contentService != null) {
			labelProvider = contentService.createCommonLabelProvider();
			descriptionProvider = contentService
					.createCommonDescriptionProvider();
		} else {
			WorkbenchNavigatorPlugin.log(
					"Could not acquire INavigatorContentService from part (\"" //$NON-NLS-1$
							+ part.getTitle() + "\").", null); //$NON-NLS-1$
		}
	}

	/**
	 * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
	 */
	public Image getImage(Object object) {
		if(object instanceof IStructuredSelection){
			Object obj = ((IStructuredSelection)object).getFirstElement();
			if(obj instanceof IFile){
				IFile file =  (IFile)obj;
				if(file.getFileExtension()!= null){
					if(file.getFileExtension().equals("concept"))
						return StudioUIPlugin.getDefault().getImage("icons/concept.png");
					if(file.getFileExtension().equals("event"))
						return StudioUIPlugin.getDefault().getImage("icons/event.png");
					if(file.getFileExtension().equals(CommonIndexUtils.TIME_EXTENSION))
						return StudioUIPlugin.getDefault().getImage("icons/time-event.gif");
					if(file.getFileExtension().equals("channel"))
						return StudioUIPlugin.getDefault().getImage("icons/channel.png");
					if(file.getFileExtension().equals("scorecard"))
						return StudioUIPlugin.getDefault().getImage("icons/scorecard.png");
					if(file.getFileExtension().equals("rule"))
						return StudioUIPlugin.getDefault().getImage("icons/rules.png");
					if(file.getFileExtension().equals("rulefunction")){
						if(StudioResourceUtils.isVirtual(file)){
							return StudioUIPlugin.getDefault().getImage("icons/virtualrulefunction.gif");
						}
						return StudioUIPlugin.getDefault().getImage("icons/rule-function.png");
					}
					if(file.getFileExtension().equals("rulefunctionimpl"))
						return StudioUIPlugin.getDefault().getImage("icons/new_table_16x16.png");
					if(file.getFileExtension().equals("statemachine"))
						return StudioUIPlugin.getDefault().getImage("icons/state_machine.png");
					if(file.getFileExtension().equals(IndexUtils.EAR_EXTENSION))
						return StudioUIPlugin.getDefault().getImage("icons/enterpriseArchive16x16.gif");
					if(file.getFileExtension().equals("projectview"))
						return StudioUIPlugin.getDefault().getImage("icons/project_diagram.png");
					if(file.getFileExtension().equals("conceptview"))
						return StudioUIPlugin.getDefault().getImage("icons/concepts_diagram.png");
					if(file.getFileExtension().equals("eventview"))
						return StudioUIPlugin.getDefault().getImage("icons/event_diagram.png");
				}
				return PlatformUI.getWorkbench().getSharedImages().getImage(
						ISharedImages.IMG_OBJ_FILE); 
			}
			if(obj instanceof IFolder){
				return PlatformUI.getWorkbench().getSharedImages().getImage(
						ISharedImages.IMG_OBJ_FOLDER); 
			}
			if(obj instanceof IProject){
				return StudioUIPlugin.getDefault().getImage("icons/designer_project.gif");
			}
		}
		
		return labelProvider != null ? labelProvider.getImage(object) : null;
	}

	/**
	 * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
	 */
	public String getText(Object object) {
		return descriptionProvider != null ? descriptionProvider.getDescription(object) : null;
	}
}
