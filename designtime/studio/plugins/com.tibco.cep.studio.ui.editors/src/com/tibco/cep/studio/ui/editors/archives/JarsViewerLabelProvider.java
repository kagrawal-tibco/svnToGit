package com.tibco.cep.studio.ui.editors.archives;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

/**
 * 
 * @author sasahoo
 *
 */
public class JarsViewerLabelProvider extends LabelProvider	implements	ITableLabelProvider {
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
	 */
	public String getColumnText(Object obj, int index) {
		return obj.toString();
	}
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
	 */
	public Image getColumnImage(Object obj, int index) {
		return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FILE); 
	}
}