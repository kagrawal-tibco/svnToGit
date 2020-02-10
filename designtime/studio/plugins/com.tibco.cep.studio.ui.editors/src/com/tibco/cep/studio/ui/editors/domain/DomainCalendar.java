package com.tibco.cep.studio.ui.editors.domain;

import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.studio.ui.editors.utils.Messages;

/**
 * 
 * @author sasahoo
 * 
 */
public class DomainCalendar extends Dialog {

	private Text textField;
	private DateTime calendar;
	private DateTime time;

	private Calendar dcalendar;

	public DomainCalendar(Shell parentShell, Text textField) {
		super(parentShell);
		this.textField = textField;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	protected void configureShell(Shell newShell) {
		newShell.setText(Messages.getString("domain.calendar.title")); //$NON-NLS-1$
		super.configureShell(newShell);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {

		parent.setLayout(new GridLayout(1, false));
		parent.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
		GridData gd = new GridData();
		gd = new GridData();
		calendar = new DateTime(parent, SWT.CALENDAR | SWT.BORDER);

		GridData gdDateTime = new GridData();
		gdDateTime.horizontalAlignment = SWT.LEFT;
		gdDateTime.verticalAlignment = SWT.LEFT;
		calendar.setLayoutData(gdDateTime);
		calendar.setBackground(new Color(Display.getCurrent(), 255, 255, 255));

		time = new DateTime(parent, SWT.TIME | SWT.LONG);
		gd = new GridData(SWT.BEGINNING, SWT.LEFT, false, false);
		gd.heightHint = 25;
		gd.widthHint = 225;
		time.setLayoutData(gd);

		String date = textField.getText();
		try {
			dcalendar = new GregorianCalendar();
			dcalendar.setTime(DomainUtils.BE_DATE_FORMAT.parse(date));
			calendar.setDay(dcalendar.get(Calendar.DAY_OF_MONTH));
			calendar.setMonth(dcalendar.get(Calendar.MONTH));
			calendar.setYear(dcalendar.get(Calendar.YEAR));
			
			if (dcalendar.get(Calendar.AM_PM) == 1) {
				// add 12 to set the time to PM
				time.setHours(dcalendar.get(Calendar.HOUR) + 12);
			} else {
				time.setHours(dcalendar.get(Calendar.HOUR));
			}
			
			time.setMinutes(dcalendar.get(Calendar.MINUTE));
			time.setSeconds(dcalendar.get(Calendar.SECOND));
			
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		return parent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
	 */
	protected void createButtonsForButtonBar(Composite parent) {
		GridLayout glayout = new GridLayout(2, false);
		parent.setLayout(glayout);
		parent.setBackground(Display.getDefault().getSystemColor(
				SWT.COLOR_WHITE));
		Button ok = new Button(parent, SWT.FLAT);
		GridData gd = new GridData(SWT.END, SWT.CENTER, false, false);
		gd.widthHint = 100;
		gd.heightHint = 25;
		ok.setText("Ok"); //$NON-NLS-1$
		ok.setLayoutData(gd);

		Button cancel = new Button(parent, SWT.FLAT);
		gd = new GridData(SWT.END, SWT.CENTER, false, false);
		gd.widthHint = 100;
		gd.heightHint = 25;
		cancel.setText("Cancel"); //$NON-NLS-1$
		cancel.setLayoutData(gd);

		cancel.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				close();
			}
		});

		ok.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				dcalendar = new GregorianCalendar(calendar.getYear(), calendar
						.getMonth(), calendar.getDay(), time.getHours(), time
						.getMinutes(), time.getSeconds());

				textField.setText(DomainUtils.BE_DATE_FORMAT.format(dcalendar.getTime()));
				close();
			}
		});

	}

	protected Point getInitialLocation(Point initialSize) {
		Point result = textField.getDisplay().getCursorLocation();
		return result;
	}
}
