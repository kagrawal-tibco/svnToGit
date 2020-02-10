package com.tibco.cep.studio.decision.table.calendar;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.eclipse.nebula.widgets.nattable.util.GUIHelper;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.studio.decision.table.ui.DecisionTableUIPlugin;


public abstract class DateTimeCalendar extends AbstractDateTimeCalendarDialog { 

	private static final int OP_MATCHES = 	0;
	private static final int OP_BEFORE = 	1;
	private static final int OP_AFTER = 	2;
	private static final int OP_WITHIN_RANGE = 	3;
	private static final int OP_OUTSIDE_RANGE = 4;

	private static final String[] dateOperations = new String[OP_OUTSIDE_RANGE+1];
	static {
		dateOperations[OP_MATCHES] 		= "Matches";
		dateOperations[OP_BEFORE] 		= "Before";
		dateOperations[OP_AFTER] 		= "After";
		dateOperations[OP_WITHIN_RANGE] = "Within Range";
		dateOperations[OP_OUTSIDE_RANGE] = "Outside Range";
	}
	
	private Group startDateCalGroup;
	private Group endDateCalGroup;

	/**
	 * @param parentShell
	 */
	public DateTimeCalendar(Shell parentShell, SimpleDateFormat sdf) {
		super(parentShell, sdf);
		setShellStyle(getShellStyle() | SWT.RESIZE);//for resize only
	}

	/**
	 * @param date
	 */
	protected void initializeDateTime(String date){
		if(date==null || date.equalsIgnoreCase("")){
			date = getFormattedDateTime();
		}
		initialDateTimeExpr = date;
		try {
			String[] split = null;
			int rangeOp = date.indexOf("&&");
			if (rangeOp != -1) {
				split = date.split("&&");
			} else {
				rangeOp = date.indexOf("||");
				if (rangeOp != -1) {
					split = date.split("\\|\\|");
				}
			}
			if (split != null && split.length > 1) {
				String startDateStr = split[0];
				char firstOp = getOp(startDateStr);
				if (firstOp != 0) {
					startDateStr = startDateStr.substring(startDateStr.indexOf(firstOp)+1).trim();
				}

				String endDateStr = split[1];
				char op = getOp(endDateStr.trim());
				if (op != 0) {
					endDateStr = endDateStr.substring(endDateStr.indexOf(op)+1).trim();
				}
				
				startDateCalendar = new GregorianCalendar();
				startDateCalendar.setTime(sdf.parse(startDateStr));
				
				endDateCalendar = new GregorianCalendar();
				endDateCalendar.setTime(sdf.parse(endDateStr));
				
				if (firstOp == '<') {
					dateOpCombo.select(OP_OUTSIDE_RANGE);
				} else {
					dateOpCombo.select(OP_WITHIN_RANGE);
				}

			} else {
				char op = getOp(date);
				if (op != 0) {
					date = date.substring(date.indexOf(op)+1).trim();
				}
				startDateCalendar = new GregorianCalendar();
				startDateCalendar.setTime(sdf.parse(date));
				
				endDateCalendar = new GregorianCalendar();
				endDateCalendar.setTime(sdf.parse(date));
				if (op == '<') {
					dateOpCombo.select(OP_BEFORE);
				} else if (op == '>') {
					dateOpCombo.select(OP_AFTER);
				} else{
					dateOpCombo.select(OP_MATCHES);
				}
			}

			setCalendar(startDateCalWidget, startDateTimeWidget, startDateCalendar, valText);
			setCalendar(endDateCalendarWidget, endDateTimeWidget, endDateCalendar, endDateValText);
			resetConfiguration(startDateCalendar);
		} catch (ParseException e1) {
			DecisionTableUIPlugin.warn(e1.getMessage());
//			MessageDialog.openError(shell, "Error", "Enter valid date time");
			return;
		}
	}

	private char getOp(String date) {
		if (date == null || date.trim().length() == 0) {
			return 0;
		}
		if (date.startsWith(">")) {
			return '>';
		}
		if (date.startsWith("<")) {
			return '<';
		}
		return 0;
	}

	@Override
	protected void setWidgetDateTime(String dateTime) {
		dateTime = this.formattedDateTimeExpr;
		
	}

	public String getWidgetDateTime() {
		return this.formattedDateTimeExpr;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
	}
	
	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(final Composite parent) {
		parent.setLayout(new GridLayout(3, false));
		parent.setBackground(GUIHelper.COLOR_WHITE);
		
		GridData gd = new GridData();
		Composite dateOpComposite = new Composite(parent, SWT.NULL);
		dateOpComposite.setLayout(new GridLayout(3, false));
		dateOpComposite.setBackground(COLOR_WHITE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 3;
		dateOpComposite.setLayoutData(gd);

		Label label = new Label(dateOpComposite,SWT.NULL);
		label.setText("Comparison");

		dateOpCombo = new Combo(dateOpComposite,SWT.READ_ONLY);
		dateOpCombo.setItems(dateOperations);
		dateOpCombo.select(OP_MATCHES);

		startDateCalGroup = new Group(parent, SWT.BORDER);
		GridLayout grpLayout = new GridLayout();
		grpLayout.marginHeight = grpLayout.marginWidth = 0;
		startDateCalGroup.setLayout(grpLayout);
		startDateCalGroup.setLayoutData(new GridData(GridData.FILL_BOTH));
		Composite borderComp = new Composite(startDateCalGroup, SWT.NULL);
		borderComp.setLayout(new GridLayout());
		borderComp.setLayoutData(new GridData(GridData.FILL_BOTH));
		borderComp.setBackground(GUIHelper.COLOR_WHITE);
		Composite valueComposite = new Composite(borderComp,SWT.NONE);
		valueComposite.setLayout(new GridLayout(2, false));
		valueComposite.setBackground(GUIHelper.COLOR_WHITE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		valueComposite.setLayoutData(gd);

		startDatelabel = new Label(valueComposite,SWT.NONE);
		startDatelabel.setText("Date");

		valText = new Text(valueComposite,SWT.BORDER);
		valText.setText("");
		valText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		valText.setEditable(false);

		Group calendarGrp = new Group(borderComp,SWT.NONE);
		calendarGrp.setText("Date");
		calendarGrp.setLayout(new GridLayout(1,false));
		gd = new GridData(GridData.FILL_BOTH);
		calendarGrp.setLayoutData(gd);

		startDateCalWidget = new DateTime(calendarGrp, SWT.CALENDAR | SWT.BORDER);
		gd = new GridData();
		gd.horizontalAlignment = SWT.LEFT;
		gd.verticalAlignment = SWT.LEFT;
		gd.widthHint = 225; // give it a little extra room to allow clicking on calendar to give it focus
		startDateCalWidget.setLayoutData(gd);

		startDateTimeWidget = new DateTime(calendarGrp, SWT.TIME | SWT.LONG);
		gd = new GridData(GridData.FILL_BOTH);
		startDateTimeWidget.setLayoutData(gd);
		
		MouseListener startListener = new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
//				if (!isStartDateCalendarSelected()) {
					setFirstCalendar();
//				}
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}
		};
		
		valueComposite.addMouseListener(startListener);
		borderComp.addMouseListener(startListener);
		calendarGrp.addMouseListener(startListener);
		startDateCalGroup.addMouseListener(startListener);
		startDateCalWidget.addMouseListener(startListener);
		startDateTimeWidget.addMouseListener(startListener);

		endDateCalGroup = new Group(parent, SWT.BORDER);
		grpLayout = new GridLayout();
		grpLayout.marginHeight = grpLayout.marginWidth = 0;
		Composite borderComp2 = new Composite(endDateCalGroup, SWT.NULL);
		borderComp2.setLayout(new GridLayout());
		borderComp2.setLayoutData(new GridData(GridData.FILL_BOTH));
		borderComp2.setBackground(GUIHelper.COLOR_WHITE);
		endDateCalGroup.setLayout(grpLayout);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.exclude = true;
		endDateCalGroup.setLayoutData(gridData);
		endDateCalGroup.setVisible(false);
		
		MouseListener endListener = new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
//				if (!isEndDateCalendarSelected()) {
					setSecondCalendar();
//				}
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}
		};
		
		Composite enddateOpComposite = new Composite(borderComp2,SWT.NONE);
		enddateOpComposite.setLayout(new GridLayout(2, false));
		gd = new GridData(GridData.FILL_HORIZONTAL);
		enddateOpComposite.setLayoutData(gd);
		enddateOpComposite.setBackground(GUIHelper.COLOR_WHITE);
		
		endDatelabel = new Label(enddateOpComposite,SWT.NONE);
		endDatelabel.setText("End");

		endDateValText = new Text(enddateOpComposite,SWT.BORDER);
		endDateValText.setEditable(false);
		endDateValText.setText("");
		endDateValText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Group endDatecalendarGrp = new Group(borderComp2,SWT.NONE);
		endDatecalendarGrp.setText("End Date");
		endDatecalendarGrp.setLayout(new GridLayout(1,false));
		gd = new GridData(GridData.FILL_BOTH);
		endDatecalendarGrp.setLayoutData(gd);

		endDateCalendarWidget = new DateTime(endDatecalendarGrp, SWT.CALENDAR | SWT.BORDER);
		gd = new GridData();
		gd.horizontalAlignment = SWT.LEFT;
		gd.verticalAlignment = SWT.LEFT;
		gd.widthHint = 225; // give it a little extra room to allow clicking on calendar to give it focus
		endDateCalendarWidget.setLayoutData(gd);

		endDateTimeWidget = new DateTime(endDatecalendarGrp, SWT.TIME | SWT.LONG);
		gd = new GridData(GridData.FILL_BOTH);
		endDateTimeWidget.setLayoutData(gd);

		enddateOpComposite.addMouseListener(endListener);
		borderComp2.addMouseListener(endListener);
		endDatecalendarGrp.addMouseListener(endListener);
		endDateCalGroup.addMouseListener(endListener);
		endDateCalendarWidget.addMouseListener(endListener);
		endDateTimeWidget.addMouseListener(endListener);

		Group configGrp = new Group(parent,SWT.BORDER);
		configGrp.setText("Configuration");
		configGrp.setLayout(new GridLayout(1,false));
		gd = new GridData(GridData.FILL_BOTH);
		configGrp.setLayoutData(gd);
		createConfigurationGroup(configGrp);

		dateOpCombo.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				if(isBetweenOrRange()){
					endDateCalGroup.setVisible(true);
					GridData gd = (GridData) endDateCalGroup.getLayoutData();
					gd.exclude = false;
					endDateCalGroup.setLayoutData(gd);
					if (isOutsideRange()) {
						startDatelabel.setText("Before");
						endDatelabel.setText("After");
					} else {
						startDatelabel.setText("Start");
						endDatelabel.setText("End");
					}
					startDatelabel.getParent().pack();
					endDatelabel.getParent().pack();
					updateStartDateWidgets(startDateCalendar);
					updateEndDateWidgets(endDateCalendar);
					setFirstCalendar();
				} else {
					endDateCalGroup.setVisible(false);
					GridData gd = (GridData) endDateCalGroup.getLayoutData();
					gd.exclude = true;
					endDateCalGroup.setLayoutData(gd);
					startDatelabel.setText("Date");
					updateStartDateWidgets(startDateCalendar);
				}
				startDateCalGroup.pack();
				endDateCalGroup.pack();
				parent.getShell().pack(true);
			}
		});
		
		return parent;
	}

	/**
	 * @return
	 */
	public boolean isBetweenOrRange(){
		if (isWithinRange() || isOutsideRange()) {
			return true;
		}
		return false;
	}
	/**
	 * @return
	 */
	public boolean isWithinRange(){
		if (dateOpCombo.getSelectionIndex() == OP_WITHIN_RANGE) {
			return true;
		}
		return false;
	}
	/**
	 * @return
	 */
	public boolean isOutsideRange(){
		if (dateOpCombo.getSelectionIndex() == OP_OUTSIDE_RANGE) {
			return true;
		}
		return false;
	}

	public boolean isBefore(){
		if (dateOpCombo.getSelectionIndex() == OP_BEFORE) {
			return true;
		}
		return false;
	}

	public boolean isAfter(){
		if (dateOpCombo.getSelectionIndex() == OP_AFTER) {
			return true;
		}
		return false;
	}

	/**
	 * @param parent
	 */
	private void createConfigurationGroup(Composite parent){
		
		SelectionListener spinnerListener = new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (isBetweenOrRange()) {
					if (startDateCalendarSelected) {
						setStartDateFromSpinners();
					} else {
						setEndDateFromSpinners();
					}
				} else {
					setStartDateFromSpinners();
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		};
		
		SelectionListener scaleListener = new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (isBetweenOrRange()) {
					if (startDateCalendarSelected) {
						setStartDateFromScales();
					} else {
						setEndDateFromScales();
					}
				} else {
					setStartDateFromScales();
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		};
		
		Composite composite = new Composite(parent,SWT.BORDER);
		composite.setLayout(new GridLayout(5, false));
		GridData gd = new GridData(GridData.FILL_BOTH);
		composite.setLayoutData(gd);

		//Years
		Label label = new Label(composite,SWT.NONE);
		label.setText("Years:");
		
		label = new Label(composite,SWT.NONE);
		label.setText(String.valueOf(minimumYear));
		yrscale = new Scale(composite,SWT.HORIZONTAL);
		yrscale.setMinimum(0);
		yrscale.setMaximum(80);
		yrscale.setIncrement(1);
		yrscale.setPageIncrement(1);
		yrscale.addSelectionListener(scaleListener);
		label = new Label(composite,SWT.NONE);
		label.setText(String.valueOf(maximumYear));

		yrspinner = new Spinner(composite,SWT.BORDER);
		yrspinner.setMinimum(1750);
		yrspinner.setMaximum(3000);
		yrspinner.setIncrement(1);
		yrspinner.addSelectionListener(spinnerListener);

		//Months
		label = new Label(composite,SWT.NONE);
		label.setText("Months:");
		label = new Label(composite,SWT.NONE);
		label.setText("1");
		monthsscale = new Scale(composite,SWT.HORIZONTAL);
		monthsscale.setMinimum(0);
		monthsscale.setMaximum(11);
		monthsscale.setIncrement(1);
		monthsscale.setPageIncrement(1);
		monthsscale.addSelectionListener(scaleListener);
		label = new Label(composite,SWT.NONE);
		label.setText("12");
		monspinner = new Spinner(composite,SWT.BORDER);
		monspinner.setMinimum(1);
		monspinner.setMaximum(12);
		monspinner.setIncrement(1);
		monspinner.addSelectionListener(spinnerListener);

		//Days
		label = new Label(composite,SWT.NONE);
		label.setText("Days:");
		label = new Label(composite,SWT.NONE);
		label.setText("0");
		daysscale = new Scale(composite,SWT.HORIZONTAL);
		daysscale.setMinimum(0);
		daysscale.setMaximum(31);
		daysscale.setIncrement(1);
		daysscale.setPageIncrement(1);
		daysscale.addSelectionListener(scaleListener);
		label = new Label(composite,SWT.NONE);
		label.setText("31");
		dayspinner = new Spinner(composite,SWT.BORDER);
		dayspinner.setMinimum(0);
		dayspinner.setMaximum(31);
		dayspinner.setIncrement(1);
		dayspinner.addSelectionListener(spinnerListener);

		//Hours
		label = new Label(composite,SWT.NONE);
		label.setText("Hours:");
		label = new Label(composite,SWT.NONE);
		label.setText("0");
		hrssscale = new Scale(composite,SWT.HORIZONTAL);
		hrssscale.setMinimum(0);
		hrssscale.setMaximum(23);
		hrssscale.setIncrement(1);
		hrssscale.setPageIncrement(1);
		hrssscale.addSelectionListener(scaleListener);
		label = new Label(composite,SWT.NONE);
		label.setText("23");
		hrsspinner = new Spinner(composite,SWT.BORDER);
		hrsspinner.setMinimum(0);
		hrsspinner.setMaximum(23);
		hrsspinner.setIncrement(1);
		hrsspinner.addSelectionListener(spinnerListener);

		//Minutes
		label = new Label(composite,SWT.NONE);
		label.setText("Minutes:");
		label = new Label(composite,SWT.NONE);
		label.setText("0");
		minsscale = new Scale(composite,SWT.HORIZONTAL);
		minsscale.setMinimum(0);
		minsscale.setMaximum(59);
		minsscale.setIncrement(1);
		minsscale.setPageIncrement(1);
		minsscale.addSelectionListener(scaleListener);
		label = new Label(composite,SWT.NONE);
		label.setText("59");
		minspinner = new Spinner(composite,SWT.BORDER);
		minspinner.setMinimum(0);
		minspinner.setMaximum(59);
		minspinner.setIncrement(1);
		minspinner.addSelectionListener(spinnerListener);

		//Seconds
		label = new Label(composite,SWT.NONE);
		label.setText("Seconds:");
		label = new Label(composite,SWT.NONE);
		label.setText("0");
		secsscale = new Scale(composite,SWT.HORIZONTAL);
		secsscale.setMinimum(0);
		secsscale.setMaximum(59);
		secsscale.setIncrement(1);
		secsscale.setPageIncrement(1);
		secsscale.addSelectionListener(scaleListener);
		
		label = new Label(composite,SWT.NONE);
		label.setText("59");
		secspinner = new Spinner(composite,SWT.BORDER);
		secspinner.setMinimum(0);
		secspinner.setMaximum(59);
		secspinner.setIncrement(1);
		secspinner.addSelectionListener(spinnerListener);
		
		Composite buttonscomposite = new Composite(parent,SWT.NONE);
		buttonscomposite.setLayout(new GridLayout(3, false));
		label = new Label(buttonscomposite,SWT.NONE);
		gd = new GridData();
//		gd.widthHint = 120;
		label.setLayoutData(gd);

		resetButton = new Button(buttonscomposite,SWT.NONE);
		gd = new GridData();
		resetButton.setText("Reset");
		resetButton.setLayoutData(gd);

		setToCurrentDateButton = new Button(buttonscomposite,SWT.NONE);
		gd = new GridData();
		setToCurrentDateButton.setText("Set To Current Date");
		setToCurrentDateButton.setLayoutData(gd);
		
		resetButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				initializeDateTime(initialDateTimeExpr);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		setToCurrentDateButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (isStartDateCalendarSelected()) {
					updateStartDateWidgets(new GregorianCalendar());
				} else if (isEndDateCalendarSelected()) {
					updateEndDateWidgets(new GregorianCalendar());
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		
	}
	
	protected Calendar getDateTimeFromSpinners() {
		GregorianCalendar cal = new GregorianCalendar();
		int years = yrspinner.getSelection();
		int months = monspinner.getSelection();
		int days = dayspinner.getSelection();
		int hrs = hrsspinner.getSelection();
		int mins = minspinner.getSelection();
		int secs = secspinner.getSelection();
//		if (adjustYears) {
//			years += minimumYear;
//		}
//		if (adjustMonths) {
			months -= 1;
//		}
		cal.set(years, months, days, hrs, mins, secs);
		return cal;
	}

	protected Calendar getDateTimeFromScales() {
		GregorianCalendar cal = new GregorianCalendar();
		int years = yrscale.getSelection()+minimumYear;
		int months = monthsscale.getSelection();
		int days = daysscale.getSelection();
		int hrs = hrssscale.getSelection();
		int mins = minsscale.getSelection();
		int secs = secsscale.getSelection();
		cal.set(years, months, days, hrs, mins, secs);
		return cal;
	}
	
	protected void setEndDateFromSpinners() {
		Calendar dateTimeFromScales = getDateTimeFromSpinners();
		updateEndDateWidgets(dateTimeFromScales);
		resetScales(dateTimeFromScales);
	}

	protected void setStartDateFromSpinners() {
		Calendar dateTimeFromScales = getDateTimeFromSpinners();
		updateStartDateWidgets(dateTimeFromScales);
		resetScales(dateTimeFromScales);
	}

	protected void setEndDateFromScales() {
		Calendar dateTimeFromScales = getDateTimeFromScales();
		updateEndDateWidgets(dateTimeFromScales);
		resetSpinners(dateTimeFromScales);
	}

	protected void setStartDateFromScales() {
		Calendar dateTimeFromScales = getDateTimeFromScales();
		updateStartDateWidgets(dateTimeFromScales);
		resetSpinners(dateTimeFromScales);
	}

	private void updateEndDateWidgets(Calendar dateTimeFromScales) {
		setCalendar(endDateCalendarWidget, endDateTimeWidget, dateTimeFromScales, endDateValText);
//		resetConfiguration(dateTimeFromScales, adjustYears, adjustMonths);
	}

	private void updateStartDateWidgets(Calendar dateTimeFromScales) {
		setCalendar(startDateCalWidget, startDateTimeWidget, dateTimeFromScales, valText);
//		resetConfiguration(dateTimeFromScales, adjustYears, adjustMonths);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createContents(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createContents(Composite parent) {
		// create the top level composite for the dialog
		Composite composite = new Composite(parent, 0);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.verticalSpacing = 0;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		applyDialogFont(composite);
		// initialize the dialog units
		initializeDialogUnits(composite);
		// create the dialog area and button bar
		dialogArea = createDialogArea(composite);
		buttonBar = createButtonBar(composite);

		initializeDateTime(currentDateTime);
		setFirstCalendar();
		return composite;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createButtonBar(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createButtonBar(Composite parent) {
		Composite singleCalendarComposite = new Composite(parent, SWT.NONE);
		// create a layout with spacing and margins appropriate for the font size.
		GridLayout layout = new GridLayout();
		layout.numColumns = 3; // this is incremented by createButton
		singleCalendarComposite.setLayout(layout);
		GridData data = new GridData(SWT.END, SWT.CENTER, true, false);
		data.horizontalSpan = 3;
		singleCalendarComposite.setLayoutData(data);

		// Add the buttons to the button bar.
		createButtonsForButtonBar(singleCalendarComposite);

		return singleCalendarComposite;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
	 */
	protected void createButtonsForButtonBar(Composite parent) {
		GridLayout glayout = new GridLayout(2, false);
		parent.setLayout(glayout);

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
				Calendar gcalendar = new GregorianCalendar(startDateCalWidget.getYear(), startDateCalWidget
						.getMonth(), startDateCalWidget.getDay(), startDateTimeWidget.getHours(), startDateTimeWidget
						.getMinutes(), startDateTimeWidget.getSeconds());
				try {
					if (isBefore()) {
						setBeforeDateTime(sdf.format(gcalendar.getTime()));
					} else if (isAfter()) {
						setAfterDateTime(sdf.format(gcalendar.getTime()));
					} else {
						setFormattedDateTimeExpr(sdf.format(gcalendar.getTime()));
					}
					setWidgetDateTime(getFormattedDateTimeExpr());
				} catch(Exception ex) {
					DecisionTableUIPlugin.log(ex);
				}
				close();
			}
		});
	}

	/**
	 * 
	 */
	private void setFirstCalendar(){
		Calendar firstcalendar = getCalendar(getCalendar(), getTime());
		setFirstcalendar(firstcalendar);
		getValText().setText(sdf.format(firstcalendar.getTime()));
		if(isBetweenOrRange()){
			setStartDateCalendarSelected(true);
			setEndDateCalendarSelected(false);
			startDateCalGroup.setBackground(GUIHelper.COLOR_LIST_SELECTION);
			endDateCalGroup.setBackground(COLOR_WHITE);
			resetConfiguration(firstcalendar);
		}else{
			resetConfiguration(firstcalendar);
		}
	}

	/**
	 * 
	 */
	private void setSecondCalendar(){
		Calendar secondcalendar = getCalendar(endDateCalendarWidget, endDateTimeWidget);
		setSecondcalendar(secondcalendar);
		endDateValText.setText(sdf.format(secondcalendar.getTime()));
		if(isBetweenOrRange()){
			setStartDateCalendarSelected(false);
			setEndDateCalendarSelected(true);
			startDateCalGroup.setBackground(COLOR_WHITE);
			endDateCalGroup.setBackground(GUIHelper.COLOR_LIST_SELECTION);
			resetConfiguration(secondcalendar);
		}else{
			resetConfiguration(secondcalendar);
		}
	}
	
	private Calendar getCalendar(DateTime calendar, DateTime time){
		return new GregorianCalendar(calendar.getYear(), 
				calendar.getMonth(), 
				calendar.getDay(), 
				time.getHours(), 
				time.getMinutes(), 
				time.getSeconds());
	}

	public void setRangeDateTime(String startDateTime, String endDateTime) {
		if (isWithinRange()) {
			this.formattedDateTimeExpr = MessageFormat.format(BETWEEN_DATETIME_EXPR, startDateTime, endDateTime);
		} else {
			this.formattedDateTimeExpr = MessageFormat.format(OUTSIDE_DATETIME_EXPR, startDateTime, endDateTime);
		}
	}

	public void setOutsideRangeDateTime(String startDateTime, String endDateTime) {
		this.formattedDateTimeExpr = MessageFormat.format(OUTSIDE_DATETIME_EXPR, startDateTime, endDateTime);
	}
	
}
