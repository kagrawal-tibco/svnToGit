package com.tibco.cep.studio.ui.editors.globalvar;

import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.swt.widgets.Display;

public class GVStyler extends Styler {

	private int foregroundColor = -1;
	public GVStyler(int foregroundColor) {
		this.foregroundColor = foregroundColor;
	}
	
	@Override
	public void applyStyles(TextStyle textStyle) {
		textStyle.foreground = Display.getDefault().getSystemColor(foregroundColor);
	}

}
