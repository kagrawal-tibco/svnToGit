package com.tibco.cep.studio.ui.navigator.providers;

import org.eclipse.core.resources.IFile;
import org.eclipse.swt.graphics.Image;

import com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource;
import com.tibco.cep.designtime.core.model.archive.EnterpriseArchive;
import com.tibco.cep.designtime.core.model.archive.ProcessArchive;
import com.tibco.cep.designtime.core.model.archive.SharedArchive;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.navigator.model.ArchiveNode;

public class ArchiveResourceLabelProvider extends EntityLabelProvider {

	public Image getImage(Object element) {
		
		if (element instanceof ArchiveNode) {
			element = ((ArchiveNode)element).getArchiveResource();
		} 
		if (element instanceof EnterpriseArchive) {
			return StudioUIPlugin.getDefault().getImage("icons/enterpriseArchive16x16.gif");
		} else if (element instanceof BusinessEventsArchiveResource) {
			return StudioUIPlugin.getDefault().getImage("icons/beArchive16x16.gif");
		} else if (element instanceof SharedArchive) {
			return StudioUIPlugin.getDefault().getImage("icons/sharedArchive16x16.gif");
		} else if (element instanceof ProcessArchive) {
			return StudioUIPlugin.getDefault().getImage("icons/processArchive16x16.gif");
		} else if (element instanceof IFile) {
			return StudioUIPlugin.getDefault().getImage("icons/enterpriseArchive16x16.gif");
		}
		return super.getImage(element);
	}

}
