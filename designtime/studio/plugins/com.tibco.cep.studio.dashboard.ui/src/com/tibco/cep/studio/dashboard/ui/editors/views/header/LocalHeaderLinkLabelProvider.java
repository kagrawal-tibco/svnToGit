package com.tibco.cep.studio.dashboard.ui.editors.views.header;

import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;

import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalHeaderLink;

public class LocalHeaderLinkLabelProvider  extends LabelProvider implements ITableLabelProvider, IColorProvider, IFontProvider {
	@Override
	public String getColumnText(Object element, int columnIndex) {
		String result = "";
		if (element instanceof LocalHeaderLink) {
			try {
				LocalHeaderLink localAttribute = (LocalHeaderLink) element;
				switch (columnIndex) {
					case 0:
						result = localAttribute.getURLName();
						break;
					case 1:
						result = localAttribute.getURLLink();
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

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	@Override
	public Color getForeground(Object element) {
		return null;
	}

	@Override
	public Color getBackground(Object element) {
		return null;
	}

	@Override
	public Font getFont(Object element) {
		return null;
	}

}
