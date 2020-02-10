package com.tibco.cep.decision.table.calendar;

import java.text.SimpleDateFormat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

/**
 * 
 * @author sasahoo
 *
 */
interface IDateTimeConstants {
	public static final Color COLOR_WHITE = Display.getDefault().getSystemColor(SWT.COLOR_WHITE);
	public static final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss"; 
	public static final SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_PATTERN);
	public static final Color COLOR_INFO_BACKGROUND = Display.getDefault().getSystemColor(SWT.COLOR_INFO_BACKGROUND);
	public static final Color COLOR_WIDGET_BACKGROUND = Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND);
}
