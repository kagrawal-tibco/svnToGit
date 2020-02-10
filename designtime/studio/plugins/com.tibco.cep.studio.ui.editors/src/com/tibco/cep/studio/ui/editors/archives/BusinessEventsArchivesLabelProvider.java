package com.tibco.cep.studio.ui.editors.archives;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
/**
 * 
 * @author sasahoo
 *
 */
public class BusinessEventsArchivesLabelProvider extends LabelProvider	implements	ITableLabelProvider {
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
	 */
	public String getColumnText(Object obj, int index) {
		if(obj instanceof BusinessEventsArchiveResource){
			return ((BusinessEventsArchiveResource)obj).getName();
		}
		return obj.toString();
	}
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
	 */
	public Image getColumnImage(Object obj, int index) {
		return EditorsUIPlugin.getImageDescriptor("icons/beArchive16x16.gif").createImage();
	}
}