/**
 * 
 */
package com.tibco.cep.studio.ui.editors.utils.kstreams;

import java.util.Map;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * @author shivkumarchelwa
 *
 */
public class TopologyLabelProvider extends LabelProvider implements ITableLabelProvider {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.
	 * Object, int)
	 */
	@Override
	public Image getColumnImage(Object arg0, int arg1) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object,
	 * int)
	 */
	@Override
	public String getColumnText(Object element, int columnIndex) {
		Transformation t = (Transformation) element;
		if (columnIndex == 0) {
			return t.getType();
		} else if (columnIndex == 1) {
			StringBuilder builder = new StringBuilder();
			for (Map.Entry<String, Object> entry : t.getInputs().entrySet()) {
				Object value = entry.getValue();
				if (value == null) {
					continue;
				}
				if (builder.length() > 0)
					builder.append(",");
				builder.append(String.format("%s=%s", entry.getKey(), entry.getValue()));
			}
			return builder.toString();
		}
		return null;
	}

}
