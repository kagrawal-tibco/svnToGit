package com.tibco.cep.studio.dashboard.ui.viewers;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

/**
 * @
 *
 */
public class TempPreference {

    public static boolean KEY_SHOW_SYSTEM_FIELDS_boolean = false;
    
	public static final Color COLOR_ERROR = Display.getCurrent().getSystemColor(SWT.COLOR_RED);
	public static final Color COLOR_DEFAULT = Display.getCurrent().getSystemColor(SWT.COLOR_BLACK);
	public static final Color COLOR_GRAYED_OUT = Display.getCurrent().getSystemColor(SWT.COLOR_GRAY);    
}
