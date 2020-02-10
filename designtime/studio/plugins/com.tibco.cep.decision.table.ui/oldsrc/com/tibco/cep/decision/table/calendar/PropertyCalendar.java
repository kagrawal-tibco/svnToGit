package com.tibco.cep.decision.table.calendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.decision.table.codegen.DTCodegenUtil;
import com.tibco.cep.decision.table.editors.DecisionTableEditor;
import com.tibco.cep.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.studio.tester.core.utils.TesterCoreUtils;

/**
 * 
 * @author sasahoo
 * 
 */
public class PropertyCalendar extends Dialog {

	private Text textField;
	
	private Text otherField;
	
	private DateTime calendar;
	
	private DateTime time;
	
	private static final SimpleDateFormat SDF = new SimpleDateFormat(TesterCoreUtils.DATE_TIME_PATTERN);
	
	final SimpleDateFormat persistenceSdf = new SimpleDateFormat(DTCodegenUtil.CODEGEN_EFFECTIVE_EXPIRY_DATE_FORMAT);
	
	private Calendar dcalendar;
	
	private DatePropertyType datePropertyType;

	public enum DatePropertyType {
		EFFECTIVE("Effective"), TERMINATE("Terminate");

		private String literal;

		DatePropertyType(String literal) {
			this.literal = literal;
		}
		
		private static final DatePropertyType[] VALUES_ARRAY =
			new DatePropertyType[] {
				EFFECTIVE,
				TERMINATE
		};
		
		public static DatePropertyType get(String literal) {
			for (int i = 0; i < VALUES_ARRAY.length; ++i) {
				DatePropertyType result = VALUES_ARRAY[i];
				if (result.toString().equals(literal)) {
					return result;
				}
			}
			return null;
		}
		
		public String getLiteral() {
			return literal;
		}
	}

	/**
	 * 
	 * @param parentShell
	 * @param textField
	 * @param otherField
	 * @param datePropertyType
	 */
	public PropertyCalendar(Shell parentShell, 
			                Text textField, 
			                Text otherField,
			                DatePropertyType datePropertyType) {
		super(parentShell);
		this.textField = textField;
		this.otherField = otherField;
		this.datePropertyType = datePropertyType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets
	 * .Shell)
	 */
	protected void configureShell(Shell newShell) {
		newShell.setText("Calendar"); //$NON-NLS-1$
		super.configureShell(newShell);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets
	 * .Composite)
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
		if (date.equalsIgnoreCase("")) {
			date = getFormattedDateTime();
		}
		try {
			dcalendar = new GregorianCalendar();
			if (SDF.parse(date) != null) {
				dcalendar.setTime(SDF.parse(date));
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
			}
		} catch (ParseException e1) {
			DecisionTableUIPlugin.log(e1);
		}
		return parent;
	}

	private String getFormattedDateTime() {
		return SDF.format(new Date());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse
	 * .swt.widgets.Composite)
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
				boolean dateCompared = isValidDate(SDF.format(dcalendar
						.getTime()), otherField.getText().trim(),
						datePropertyType);
				if (dateCompared) {
					textField.setText(SDF.format(dcalendar.getTime()));
					// get Model form Active Editor
					final IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					if (page == null)
						return;
					final IEditorPart activeEditor = page.getActiveEditor();
					if (!(activeEditor instanceof DecisionTableEditor)) {
						return;
					}
				}
//				} else {
//					textField.setText("");
//					otherField.setText("");
//				}
				close();
			}
		});

	}

	private boolean isValidDate(String eff, 
			                    String term,
			                    DatePropertyType datePropertyType) {
		if (datePropertyType == DatePropertyType.EFFECTIVE) {
			return dateCompare(eff, term);
		}
		if (datePropertyType == DatePropertyType.TERMINATE) {
			return dateCompare(term, eff);
		}
		return false;
	}
	
	public static boolean dateCompare(String eff, String term) {
		try {
			if (!eff.equalsIgnoreCase("") && !term.equalsIgnoreCase("")) {
				Date effDate = SDF.parse(eff);
				Date termDate = SDF.parse(term);
				if (effDate.compareTo(termDate) < 0) {
					return true;
				} else {
//					ProblemEventManager probEvtManager = ProblemEventManager
//							.getInstance();
//					probEvtManager.postProblem("INVALID_DATE", "Invalid date.",
//							"Effective Date:" + eff + " Termination Date:"
//									+ term, "Date", ProblemEvent.ERROR);
					return false;
				}
			}
			if (!eff.equalsIgnoreCase("") && term.equalsIgnoreCase("")) {
				return true;
			}
			if (eff.equalsIgnoreCase("") && !term.equalsIgnoreCase("")) {
				return true;
			}

		} catch (ParseException e1) {
			DecisionTableUIPlugin.log(e1);
		}
		return false;
	}

	protected Point getInitialLocation(Point initialSize) {
		Point result = textField.getDisplay().getCursorLocation();
		return result;
	}
}
