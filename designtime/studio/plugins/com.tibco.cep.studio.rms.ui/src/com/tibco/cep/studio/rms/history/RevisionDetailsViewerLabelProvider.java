package com.tibco.cep.studio.rms.history;

import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableFontProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.tibco.cep.studio.rms.artifacts.ArtifactsType;
import com.tibco.cep.studio.rms.history.model.RevisionDetailsItem;
import com.tibco.cep.studio.rms.ui.utils.RMSUIUtils;
import com.tibco.cep.studio.ui.util.ColorConstants;

/**
 * 
 * @author sasahoo
 *
 */
public class RevisionDetailsViewerLabelProvider implements ITableLabelProvider, ITableFontProvider, IColorProvider {
	
	private String resourcePath;
	private TableViewer viewer;
	
	public RevisionDetailsViewerLabelProvider(TableViewer viewer, String resourcePath) {
		this.resourcePath = resourcePath;
		this.viewer = viewer;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
	 */
	public Image getColumnImage(Object element, int columnIndex) {
		Image image = null;
		if (columnIndex == 0) {
			String operation = ((RevisionDetailsItem) element).getOperation();
			image = RMSHistoryRevisionUtils.getRevisionDetailsActionImageColumn(operation);
			if (isSelectedResourceItem(element)) {
				return image;
			} else {
				return new Image(Display.getDefault(), image, SWT.IMAGE_DISABLE);
			}
		}
		if (columnIndex == 1) {
			String type = ((RevisionDetailsItem) element).getArtifactType();
			image = RMSUIUtils.getArtifactImage(ArtifactsType.get(type));
			if (isSelectedResourceItem(element)) {
				return image;
			} else {
				return new Image(Display.getDefault(), image, SWT.IMAGE_DISABLE);
			}
		}
		return image;
	}

	public String getColumnText(Object element, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return ((RevisionDetailsItem) element).getOperation();
		case 1:
			return ((RevisionDetailsItem) element).getArtifactPath();
		case 2:
			return ((RevisionDetailsItem) element).getApprovalStatus();
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
		return null;
	}

	public Color getForeground(Object element) {
		if (!isSelectedResourceItem(element)) {
			return ColorConstants.gray;
		}
		return null;
	}

	@Override
	public Font getFont(Object element, int columnIndex) {
		if (isSelectedResourceItem(element)) {
			Font font = viewer.getTable().getFont();
			FontData[] fontData = font.getFontData();
			for(int i = 0; i < fontData.length; ++i) {
//				fontData[i].setHeight(12);
			    fontData[i].setStyle(SWT.BOLD);
			}
			switch (columnIndex) {
			case 0:
				return  new Font(Display.getDefault(), fontData);
			case 1:
				return  new Font(Display.getDefault(), fontData);
			case 2:
				return  new Font(Display.getDefault(), fontData);
			default:
				return null;
			}
		}
		return null;
	}
	
	/**
	 * @param element
	 * @return
	 */
	private boolean isSelectedResourceItem(Object element) {
		RevisionDetailsItem item = (RevisionDetailsItem) element;
		if (item.getArtifactPath().equals(resourcePath)) {
			return true;
		}
		return false;
	}
	
	/**
	 * @param resourcePath
	 */
	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}
}