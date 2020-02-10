package com.tibco.cep.studio.ui.editors.domain;

import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

import com.tibco.cep.studio.ui.util.ColorConstants;

/**
 * 
 * @author sasahoo
 *
 */
public class DomainViewerLabelProvider implements ITableLabelProvider,IColorProvider {

	private DomainFormViewer domainFormViewer;
	public DomainViewerLabelProvider(DomainFormViewer domainFormViewer){
		this.domainFormViewer = domainFormViewer;
	}
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
	 */
	public Image getColumnImage(Object element, int columnIndex) {
//		if(columnIndex ==0){
//			return EditorsUIPlugin.getImageDescriptor("icons/description_16x16.png").createImage();
//		}
//		if(columnIndex ==1){
//			return EditorUtils.getImage(DOMAIN_DATA_TYPES.get(domainFormViewer.getDomainDataTypesCombo().getText()));
//		}
		return null;
	}

	public String getColumnText(Object element, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return ((DomainViewerItem) element).entryName;
		case 1:
			return ((DomainViewerItem) element).entryValue;
		default:
			return "Invalid column: " + columnIndex;
		}
	}

	public void addListener(ILabelProviderListener listener) {
	}

	public void dispose() {
	}

	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	public void removeListener(ILabelProviderListener lpl) {
	}

	public Color getBackground(Object element) {
		return ColorConstants.lightYellow;
	}

	public Color getForeground(Object element) {
		return null;
	}

}
