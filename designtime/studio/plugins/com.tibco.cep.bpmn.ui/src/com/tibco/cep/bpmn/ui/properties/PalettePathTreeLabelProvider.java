package com.tibco.cep.bpmn.ui.properties;


import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.studio.common.configuration.BuildPathEntry;
import com.tibco.cep.studio.common.configuration.impl.BpmnPalettePathEntryImpl;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.ui.StudioUIPlugin;

public class PalettePathTreeLabelProvider extends LabelProvider {

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
	 */
	@Override
	public Image getImage(Object element) {
		if (element instanceof DesignerProject){
			return StudioUIPlugin.getDefault().getImage("icons/archive_obj.gif");
		} else if (element instanceof BpmnPalettePathEntryImpl){
			return BpmnUIPlugin.getDefault().getImage("icons/palette.gif");
		}else if (element instanceof BuildPathEntry){
			return StudioUIPlugin.getDefault().getImage("icons/archive_obj.gif");
		} 
		return null;
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object element) {
		if (element instanceof DesignerProject){
			DesignerProject dp = (DesignerProject)element;
			String archivePath = dp.getArchivePath();				
			return archivePath;
		} else if (element instanceof BuildPathEntry) {
			return ((BuildPathEntry) element).getPath();
		}
		return null;
	}

}