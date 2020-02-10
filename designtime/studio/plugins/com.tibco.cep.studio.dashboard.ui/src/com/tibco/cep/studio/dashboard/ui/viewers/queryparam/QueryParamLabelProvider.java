package com.tibco.cep.studio.dashboard.ui.viewers.queryparam;

import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;

import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalQueryParam;

/**
 * Label provider for the AttributeViewer
 * 
 * @see org.eclipse.jface.viewers.LabelProvider
 */
public class QueryParamLabelProvider extends LabelProvider implements ITableLabelProvider, IColorProvider, IFontProvider {

	/**
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object,
	 *      int)
	 */
	public String getColumnText(Object element, int columnIndex) {
		String result = "";
		if (element instanceof LocalQueryParam) {
			try {
				LocalQueryParam localQueryParam = (LocalQueryParam) element;
				switch (columnIndex) {
					case 0:
						if (!localQueryParam.isValid()) {
							result = "!";
						}
						break;
					case 1:
						result = localQueryParam.getName();
						break;
					case 2:
						result = localQueryParam.getDataType();
						break;
					case 3:
						result = localQueryParam.getValue();
						break;
					default:
						break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return result;
	}

	/**
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object,
	 *      int)
	 */
	public Image getColumnImage(Object element, int columnIndex) {
//		try {
//			if (element instanceof LocalElement && columnIndex == 0) {
//				LocalElement localELement = ((LocalElement) element);
//				if (false == localELement.isValid()) {
//					if (localELement.getValidationMessage() instanceof SynValidationErrorMessage) {
//						return DashboardCoreImageRegistry.getImage(DashboardCoreImageRegistry.IMG_KEY_ERROR);
//					}
//					return DashboardCoreImageRegistry.getImage(DashboardCoreImageRegistry.IMG_KEY_INFO);
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.IColorProvider#getForeground(java.lang.Object)
	 */
	public Color getForeground(Object element) {
//		try {
//			if (element instanceof LocalElement) {
//				LocalElement localELement = (LocalElement) element;
//				if (false == localELement.isValid()) {
//					if (localELement.getValidationMessage() instanceof SynValidationErrorMessage) {
//						return Display.getCurrent().getSystemColor(SWT.COLOR_RED);
//					}
//					return Display.getCurrent().getSystemColor(SWT.COLOR_BLUE);
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.IColorProvider#getBackground(java.lang.Object)
	 */
	public Color getBackground(Object element) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.IFontProvider#getFont(java.lang.Object)
	 */
	public Font getFont(Object element) {
		return null;
	}

}
