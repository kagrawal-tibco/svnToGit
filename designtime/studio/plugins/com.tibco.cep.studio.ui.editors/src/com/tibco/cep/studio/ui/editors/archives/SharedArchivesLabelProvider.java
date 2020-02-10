package com.tibco.cep.studio.ui.editors.archives;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.tibco.cep.designtime.core.model.archive.SharedArchive;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;

/**
 * 
 * @author sasahoo
 *
 */
public class SharedArchivesLabelProvider extends LabelProvider	implements	ITableLabelProvider {
	public String getColumnText(Object obj, int index) {
		if(obj instanceof SharedArchive){
			return ((SharedArchive)obj).getName();
		}
		return obj.toString();
	}
	public Image getColumnImage(Object obj, int index) {
		return EditorsUIPlugin.getDefault().getImage("icons/sharedArchive16x16.gif");
	}
}