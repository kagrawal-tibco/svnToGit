package com.tibco.cep.decision.table.calendar;

import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.decision.table.ui.DecisionTableUIPlugin;

/**
 * 
 * @author sasahoo
 *
 */
public abstract class DateTimeCalendar extends AbstractDateTimeCalendarDialog { 

	/**
	 * @param parentShell
	 */
	public DateTimeCalendar(Shell parentShell) {
		super(parentShell);
		this.shell = parentShell;
//		setShellStyle(getShellStyle() | SWT.SHELL_TRIM);//for max and min buttons
		setShellStyle(getShellStyle() | SWT.RESIZE);//for resize only
	}

	/**
	 * @param date
	 */
	protected void initializeDateTime(String date){
		if(date==null || date.equalsIgnoreCase("")){
			date = getFormattedDateTime();
		}
		orgdate = date;
		try {
			firstcalendar = new GregorianCalendar();
			firstcalendar.setTime(sdf.parse(date));

			secondcalendar = new GregorianCalendar();
			secondcalendar.setTime(sdf.parse(date));

			setCalendar(calendar, time, firstcalendar, valText);
			setCalendar(endDateCalendar, endDateTime, secondcalendar, endDateValText);
			resetConfiguration(firstcalendar,false);
		} catch (ParseException e1) {
			DecisionTableUIPlugin.warn(e1.getMessage());
//			MessageDialog.openError(shell, "Error", "Enter valid date time");
			return;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setSize(627, 535);
		this.newShell = newShell;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		parent.setLayout(new GridLayout(3, false));
		parent.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
		GridData gd = new GridData();
		{
			Composite dateOpComposite = new Composite(parent,SWT.NONE);
			dateOpComposite.setLayout(new GridLayout(3, false));
			dateOpComposite.setBackground(COLOR_WHITE);
			gd = new GridData(SWT.BEGINNING, SWT.LEFT, false, false);
			gd.heightHint = 35;
			gd.widthHint = 260;
			dateOpComposite.setLayoutData(gd);

			Label label = new Label(dateOpComposite,SWT.NONE);
			label.setText("Comparision");
			label.setBackground(COLOR_WHITE);

			dateOpCombo = new Combo(dateOpComposite,SWT.READ_ONLY);
			dateOpCombo.setItems(new String[]{"Matches","Before","After","Between"});
			dateOpCombo.setText("Matches");
			dateOpCombo.setBackground(COLOR_WHITE);
			dateOpCombo.addModifyListener(new CalendarOperations(this));
			betweenDateOpCombo = new Combo(dateOpComposite,SWT.READ_ONLY);
			betweenDateOpCombo.setItems(new String[]{"Before","After"});
			gd =new GridData();
			gd.widthHint = 50;
			betweenDateOpCombo.setLayoutData(gd);
		}
		{
			Composite valueComposite = new Composite(parent,SWT.NONE);
			valueComposite.setBackground(COLOR_WHITE);
			valueComposite.setLayout(new GridLayout(2, false));
			gd = new GridData(SWT.BEGINNING, SWT.LEFT, false, false);
			gd.heightHint = 35;
			gd.widthHint = 230;
			valueComposite.setLayoutData(gd);

			startDatelabel = new Label(valueComposite,SWT.NONE);
			startDatelabel.setText("Date");
			startDatelabel.setBackground(COLOR_WHITE);

			valText = new Text(valueComposite,SWT.BORDER);
			valText.setText("");
			valText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			valText.setEditable(false);
		}
		{
			enddateOpComposite = new Composite(parent,SWT.NONE);
			enddateOpComposite.setLayout(new GridLayout(2, false));
			enddateOpComposite.setBackground(COLOR_WHITE);
			gd = new GridData(SWT.BEGINNING, SWT.LEFT, false, false);
			gd.heightHint = 35;
			gd.widthHint = 225;
			enddateOpComposite.setLayoutData(gd);

			endDatelabel = new Label(enddateOpComposite,SWT.NONE);
			endDatelabel.setText("End");
			endDatelabel.setBackground(COLOR_WHITE);

			endDateValText = new Text(enddateOpComposite,SWT.BORDER);
			endDateValText.setEditable(false);
			endDateValText.setText("");
			endDateValText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		}
		{
			Group configGrp = new Group(parent,SWT.NONE);
			configGrp.setBackground(COLOR_WHITE);
			configGrp.setText("Configuration");
			configGrp.setLayout(new GridLayout(1,false));
			gd = new GridData(SWT.BEGINNING, SWT.LEFT, false, false);
			gd.heightHint = 380;
			gd.widthHint = 350;
			configGrp.setLayoutData(gd);
			createConfigurationGroup(configGrp);
		}
		{
			calendarGrp = new Group(parent,SWT.NONE);
			calendarGrp.setBackground(COLOR_WHITE);
			calendarGrp.setText("Date");
			calendarGrp.setLayout(new GridLayout(1,false));
			gd = new GridData(SWT.BEGINNING, SWT.LEFT, false, false);
			gd.heightHint = 240;
			gd.widthHint = 240;
			calendarGrp.setLayoutData(gd);

			calendar = new DateTime(calendarGrp, SWT.CALENDAR | SWT.BORDER);
			gd = new GridData();
			gd.horizontalAlignment = SWT.LEFT;
			gd.verticalAlignment = SWT.LEFT;
			gd.widthHint = 225;
			calendar.setLayoutData(gd);
			calendar.setBackground(new Color(Display.getCurrent(), 255, 255, 255));

			time = new DateTime(calendarGrp, SWT.TIME | SWT.LONG);
			gd = new GridData(SWT.BEGINNING, SWT.LEFT, false, false);
			gd.heightHint = 25;
			gd.widthHint = 225;
			time.setLayoutData(gd);
			calendar.addSelectionListener(new CalendarController(this));
			calendar.addMouseListener(new CalendarController(this));
			calendar.addMouseTrackListener(new CalendarController(this));

			time.addSelectionListener(new CalendarController(this));
			time.addMouseListener(new CalendarController(this));
			time.addMouseTrackListener(new CalendarController(this));
			
			calendarGrp.addMouseListener(new CalendarController(this));
			calendarGrp.addMouseTrackListener(new CalendarController(this));

		}
		{
			endDatecalendarGrp = new Group(parent,SWT.NONE);
			endDatecalendarGrp.setBackground(COLOR_WHITE);
			endDatecalendarGrp.setText("End Date");
			endDatecalendarGrp.setLayout(new GridLayout(1,false));
			gd = new GridData(SWT.BEGINNING, SWT.LEFT, false, false);
			gd.heightHint = 240;
			gd.widthHint = 240;
			endDatecalendarGrp.setLayoutData(gd);

			endDateCalendar = new DateTime(endDatecalendarGrp, SWT.CALENDAR | SWT.BORDER);
			gd = new GridData();
			gd.horizontalAlignment = SWT.LEFT;
			gd.verticalAlignment = SWT.LEFT;
			gd.widthHint = 225;
			endDateCalendar.setLayoutData(gd);
			endDateCalendar.setBackground(new Color(Display.getCurrent(), 255, 255, 255));

			endDateTime = new DateTime(endDatecalendarGrp, SWT.TIME | SWT.LONG);
			gd = new GridData(SWT.BEGINNING, SWT.LEFT, false, false);
			gd.heightHint = 25;
			gd.widthHint = 225;
			endDateTime.setLayoutData(gd);
			
			endDateCalendar.addSelectionListener(new CalendarController(this));
			endDateCalendar.addMouseListener(new CalendarController(this));
			endDateCalendar.addMouseTrackListener(new CalendarController(this));

			endDateTime.addSelectionListener(new CalendarController(this));
			endDateTime.addMouseListener(new CalendarController(this));
			endDateTime.addMouseTrackListener(new CalendarController(this));
			
			endDatecalendarGrp.addMouseListener(new CalendarController(this));
			endDatecalendarGrp.addMouseTrackListener(new CalendarController(this));
		}	
		initializeDateTime(currentDateTime);
		enddateOpComposite.setVisible(false);
		endDatecalendarGrp.setVisible(false);
		betweenDateOpCombo.setVisible(false);
		return parent;
	}

	/**
	 * @return
	 */
	public boolean isBetweenOrRange(){
		if (dateOpCombo.getText().equalsIgnoreCase("Between") || dateOpCombo.getText().equalsIgnoreCase("Within Range")) {
			return true;
		}
		return false;
	}

	public boolean isBefore(){
		if (dateOpCombo.getText().equalsIgnoreCase("Before")) {
			return true;
		}
		return false;
	}

	public boolean isAfter(){
		if (dateOpCombo.getText().equalsIgnoreCase("After")) {
			return true;
		}
		return false;
	}

	/**
	 * @param parent
	 */
	private void createConfigurationGroup(Composite parent){
		Composite composite = new Composite(parent,SWT.NONE);
		composite.setBackground(COLOR_WHITE);
		composite.setLayout(new GridLayout(7, false));
		GridData gd = new GridData();

		//Years
		Label label = new Label(composite,SWT.NONE);
		label.setText("Years:");
		label.setBackground(COLOR_WHITE);
		label = new Label(composite,SWT.NONE);
		label.setBackground(COLOR_WHITE);
		label = new Label(composite,SWT.NONE);
		label.setBackground(COLOR_WHITE);

		Composite indecbcomposite = new Composite(composite,SWT.NONE);
		indecbcomposite.setBackground(COLOR_WHITE);
		indecbcomposite.setLayout(new GridLayout(2, false));
		incbutton = new Button(indecbcomposite,SWT.NONE);
		incbutton.setText("+");
		incbutton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				int yrscnt = yrspinner.getSelection() +1;
				if (yrspinner.getMaximum() < yrscnt) {
					yrspinner.setMaximum(yrscnt);
				}
				yrspinner.setSelection(yrscnt);
				if (isBetweenOrRange()) {
					if (startDateCalendarSelected) {
						startDateYears = yrspinner.getSelection();
					} else {
						endDateYears = yrspinner.getSelection();
					}
				} else {
					years = yrspinner.getSelection();
				}
			}
		});
		gd = new GridData();
		gd.widthHint = 20;
		incbutton.setLayoutData(gd);
		decbutton = new Button(indecbcomposite,SWT.NONE);
		decbutton.setText("-");
		gd = new GridData();
		gd.widthHint = 20;
		decbutton.setLayoutData(gd);
		decbutton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				int yrscnt = yrspinner.getSelection() - 1;
				yrspinner.setSelection(yrscnt);
				if (isBetweenOrRange()) {
					if (startDateCalendarSelected) {
						startDateYears = yrspinner.getSelection();
					} else {
						endDateYears = yrspinner.getSelection();
					}
				} else {
					years = yrspinner.getSelection();
				}
			}
		});
		label = new Label(composite,SWT.NONE);
		label.setBackground(COLOR_WHITE);
		label = new Label(composite,SWT.NONE);
		label.setBackground(COLOR_WHITE);
		yrspinner = new Spinner(composite,SWT.BORDER);
		yrspinner.setMinimum(0);
		yrspinner.setMaximum(12);
		yrspinner.setIncrement(1);
		yrspinner.setBackground(COLOR_WHITE);
		yrspinner.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (isBetweenOrRange()) {
					if (startDateCalendarSelected) {
						startDateYears = yrspinner.getSelection();
					} else {
						endDateYears = yrspinner.getSelection();
					}
				} else {
					years = yrspinner.getSelection();
				}
			}
		});

		//Months
		label = new Label(composite,SWT.NONE);
		label.setText("Months:");
		label.setBackground(COLOR_WHITE);
		label = new Label(composite,SWT.NONE);
		label.setBackground(COLOR_WHITE);
		label = new Label(composite,SWT.NONE);
		label.setText("0");
		label.setBackground(COLOR_WHITE);
		monthsscale = new Scale(composite,SWT.HORIZONTAL);
		monthsscale.setMinimum(0);
		monthsscale.setMaximum(12);
		monthsscale.setIncrement(1);
		monthsscale.setPageIncrement(1);
		monthsscale.setBackground(COLOR_WHITE);
		monthsscale.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (isBetweenOrRange()) {
					if (startDateCalendarSelected) {
						startDateMonths = monthsscale.getSelection();
					} else {
						endDateMonths = monthsscale.getSelection();
					}
				} else {
					months = monthsscale.getSelection();
				}
				monthsscale.setToolTipText(Integer.toString(monthsscale.getSelection()));
				monspinner.setSelection(monthsscale.getSelection());
			}
		});
		label = new Label(composite,SWT.NONE);
		label.setText("12");
		label.setBackground(COLOR_WHITE);
		label = new Label(composite,SWT.NONE);
		label.setBackground(COLOR_WHITE);
		monspinner = new Spinner(composite,SWT.BORDER);
		monspinner.setMinimum(0);
		monspinner.setMaximum(12);
		monspinner.setIncrement(1);
		monspinner.setBackground(COLOR_WHITE);
		monspinner.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (isBetweenOrRange()) {
					if (startDateCalendarSelected) {
						startDateMonths = monspinner.getSelection();
					} else {
						endDateMonths = monspinner.getSelection();
					}
				} else {
					months = monspinner.getSelection();
				}
				monthsscale.setSelection(monspinner.getSelection());
			}
		});

		//Week
		label = new Label(composite,SWT.NONE);
		label.setText("Weeks:");
		label.setBackground(COLOR_WHITE);
		label = new Label(composite,SWT.NONE);
		label.setBackground(COLOR_WHITE);
		label = new Label(composite,SWT.NONE);
		label.setText("0");
		label.setBackground(COLOR_WHITE);
		weeksscale = new Scale(composite,SWT.HORIZONTAL);
		weeksscale.setMinimum(0);
		weeksscale.setMaximum(52);
		weeksscale.setIncrement(1);
		weeksscale.setPageIncrement(1);
		weeksscale.setBackground(COLOR_WHITE);
		weeksscale.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (isBetweenOrRange()) {
					if (startDateCalendarSelected) {
						startDateWeeks = weeksscale.getSelection();
					} else {
						endDateWeeks = weeksscale.getSelection();
					}
				} else {
					weeks = weeksscale.getSelection();
				}
				weeksscale.setToolTipText(Integer.toString(weeksscale.getSelection()));
				weekspinner.setSelection(weeksscale.getSelection());
			}
		});

		label = new Label(composite,SWT.NONE);
		label.setText("52");
		label.setBackground(COLOR_WHITE);
		label = new Label(composite,SWT.NONE);
		label.setBackground(COLOR_WHITE);
		weekspinner = new Spinner(composite,SWT.BORDER);
		weekspinner.setMinimum(0);
		weekspinner.setMaximum(52);
		weekspinner.setIncrement(1);
		weekspinner.setBackground(COLOR_WHITE);
		weekspinner.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (isBetweenOrRange()) {
					if (startDateCalendarSelected) {
						startDateWeeks = weekspinner.getSelection();
					} else {
						endDateWeeks = weekspinner.getSelection();
					}
				} else {
					weeks = weekspinner.getSelection();
				}
				weeksscale.setSelection(weekspinner.getSelection());
			}
		});

		//Days
		label = new Label(composite,SWT.NONE);
		label.setText("Days:");
		label.setBackground(COLOR_WHITE);
		label = new Label(composite,SWT.NONE);
		label.setBackground(COLOR_WHITE);
		label = new Label(composite,SWT.NONE);
		label.setText("0");
		label.setBackground(COLOR_WHITE);
		daysscale = new Scale(composite,SWT.HORIZONTAL);
		daysscale.setMinimum(0);
		daysscale.setMaximum(31);
		daysscale.setIncrement(1);
		daysscale.setPageIncrement(1);
		daysscale.setBackground(COLOR_WHITE);
		daysscale.addListener(SWT.Selection, new Listener() {
			/* (non-Javadoc)
			 * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
			 */
			public void handleEvent(Event event) {
				if (isBetweenOrRange()) {
					if (startDateCalendarSelected) {
						startDateDays = daysscale.getSelection();
					} else {
						endDateDays = daysscale.getSelection();
					}
				} else {
					days = daysscale.getSelection();
				}
				daysscale.setToolTipText(Integer.toString(daysscale.getSelection()));
				dayspinner.setSelection(daysscale.getSelection());
			}
		});
		label = new Label(composite,SWT.NONE);
		label.setText("31");
		label.setBackground(COLOR_WHITE);
		label = new Label(composite,SWT.NONE);
		label.setBackground(COLOR_WHITE);
		dayspinner = new Spinner(composite,SWT.BORDER);
		dayspinner.setMinimum(0);
		dayspinner.setMaximum(31);
		dayspinner.setIncrement(1);
		dayspinner.setBackground(COLOR_WHITE);
		dayspinner.addListener(SWT.Selection, new Listener() {
			/* (non-Javadoc)
			 * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
			 */
			public void handleEvent(Event event) {
				if (isBetweenOrRange()) {
					if (startDateCalendarSelected) {
						startDateDays = dayspinner.getSelection();
					} else {
						endDateDays = dayspinner.getSelection();
					}
				} else {
					days = dayspinner.getSelection();
				}
				daysscale.setSelection(dayspinner.getSelection());
			}
		});

		//Hours
		label = new Label(composite,SWT.NONE);
		label.setText("Hours:");
		label.setBackground(COLOR_WHITE);
		label = new Label(composite,SWT.NONE);
		label.setBackground(COLOR_WHITE);
		label = new Label(composite,SWT.NONE);
		label.setText("0");
		label.setBackground(COLOR_WHITE);
		hrssscale = new Scale(composite,SWT.HORIZONTAL);
		hrssscale.setMinimum(0);
		hrssscale.setMaximum(24);
		hrssscale.setIncrement(1);
		hrssscale.setPageIncrement(1);
		hrssscale.setBackground(COLOR_WHITE);
		hrssscale.addListener(SWT.Selection, new Listener() {
			/* (non-Javadoc)
			 * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
			 */
			public void handleEvent(Event event) {
				if (isBetweenOrRange()) {
					if (startDateCalendarSelected) {
						startDateHours = hrssscale.getSelection();
					} else {
						endDateHours = hrssscale.getSelection();
					}
				} else {
					hours = hrssscale.getSelection();
				}
				hrssscale.setToolTipText(Integer.toString(hrssscale.getSelection()));
				hrsspinner.setSelection(hrssscale.getSelection());
			}
		});
		label = new Label(composite,SWT.NONE);
		label.setText("24");
		label.setBackground(COLOR_WHITE);
		label = new Label(composite,SWT.NONE);
		label.setBackground(COLOR_WHITE);
		hrsspinner = new Spinner(composite,SWT.BORDER);
		hrsspinner.setMinimum(0);
		hrsspinner.setMaximum(24);
		hrsspinner.setIncrement(1);
		hrsspinner.setBackground(COLOR_WHITE);
		hrsspinner.addListener(SWT.Selection, new Listener() {
			/* (non-Javadoc)
			 * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
			 */
			public void handleEvent(Event event) {
				if (isBetweenOrRange()) {
					if (startDateCalendarSelected) {
						startDateHours = hrsspinner.getSelection();
					} else {
						endDateHours = hrsspinner.getSelection();
					}
				} else {
					hours = hrsspinner.getSelection();
				}
				hrssscale.setSelection(hrsspinner.getSelection());
			}
		});

		//Minutes
		label = new Label(composite,SWT.NONE);
		label.setText("Minutes:");
		label.setBackground(COLOR_WHITE);
		label = new Label(composite,SWT.NONE);
		label.setBackground(COLOR_WHITE);
		label = new Label(composite,SWT.NONE);
		label.setText("0");
		label.setBackground(COLOR_WHITE);
		minsscale = new Scale(composite,SWT.HORIZONTAL);
		minsscale.setMinimum(0);
		minsscale.setMaximum(60);
		minsscale.setIncrement(1);
		minsscale.setPageIncrement(1);
		minsscale.setBackground(COLOR_WHITE);
		minsscale.addListener(SWT.Selection, new Listener() {
			/* (non-Javadoc)
			 * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
			 */
			public void handleEvent(Event event) {
				if (isBetweenOrRange()) {
					if (startDateCalendarSelected) {
						startDateMins = minsscale.getSelection();
					} else {
						endDateMins = minsscale.getSelection();
					}
				} else {
					mins = minsscale.getSelection();
				}
				minsscale.setToolTipText(Integer.toString(minsscale.getSelection()));
				minspinner.setSelection(minsscale.getSelection());
			}
		});
		label = new Label(composite,SWT.NONE);
		label.setText("60");
		label.setBackground(COLOR_WHITE);
		label = new Label(composite,SWT.NONE);
		label.setBackground(COLOR_WHITE);
		minspinner = new Spinner(composite,SWT.BORDER);
		minspinner.setMinimum(0);
		minspinner.setMaximum(60);
		minspinner.setIncrement(1);
		minspinner.setBackground(COLOR_WHITE);
		minspinner.addListener(SWT.Selection, new Listener() {
			/* (non-Javadoc)
			 * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
			 */
			public void handleEvent(Event event) {
				if (isBetweenOrRange()) {
					if (startDateCalendarSelected) {
						startDateMins = minspinner.getSelection();
					} else {
						endDateMins = minspinner.getSelection();
					}
				} else {
					mins = minspinner.getSelection();
				}
				minsscale.setSelection(minspinner.getSelection());
			}
		});

		//Seconds
		label = new Label(composite,SWT.NONE);
		label.setText("Seconds:");
		label.setBackground(COLOR_WHITE);
		label = new Label(composite,SWT.NONE);
		label.setBackground(COLOR_WHITE);
		label = new Label(composite,SWT.NONE);
		label.setText("0");
		label.setBackground(COLOR_WHITE);
		secsscale = new Scale(composite,SWT.HORIZONTAL);
		secsscale.setMinimum(0);
		secsscale.setMaximum(60);
		secsscale.setIncrement(1);
		secsscale.setPageIncrement(1);
		secsscale.setBackground(COLOR_WHITE);
		secsscale.addListener(SWT.Selection, new Listener() {
			/* (non-Javadoc)
			 * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
			 */
			public void handleEvent(Event event) {
				if (isBetweenOrRange()) {
					if (startDateCalendarSelected) {
						startDateSecs = secsscale.getSelection();
					} else {
						endDateSecs = secsscale.getSelection();
					}
				} else {
					secs = secsscale.getSelection();
				}
				secsscale.setToolTipText(Integer.toString(secsscale.getSelection()));
				secspinner.setSelection(secsscale.getSelection());
			}
		});
		label = new Label(composite,SWT.NONE);
		label.setText("60");
		label.setBackground(COLOR_WHITE);
		label = new Label(composite,SWT.NONE);
		label.setBackground(COLOR_WHITE);
		secspinner = new Spinner(composite,SWT.BORDER);
		secspinner.setMinimum(0);
		secspinner.setMaximum(60);
		secspinner.setIncrement(1);
		secspinner.setBackground(COLOR_WHITE);
		secspinner.addListener(SWT.Selection, new Listener() {
			/* (non-Javadoc)
			 * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
			 */
			public void handleEvent(Event event) {
				if (isBetweenOrRange()) {
					if (startDateCalendarSelected) {
						startDateSecs = secspinner.getSelection();
					} else {
						endDateSecs = secspinner.getSelection();
					}
				} else {
					secs = secspinner.getSelection();
				}
				secsscale.setSelection(secspinner.getSelection());
			}
		});

		Composite buttonscomposite = new Composite(parent,SWT.NONE);
		buttonscomposite.setBackground(COLOR_WHITE);
		buttonscomposite.setLayout(new GridLayout(3, false));
		label = new Label(buttonscomposite,SWT.NONE);
		label.setBackground(COLOR_WHITE);
		gd = new GridData();
		gd.widthHint = 120;
		label.setLayoutData(gd);

		resetbutton = new Button(buttonscomposite,SWT.NONE);
		gd = new GridData();
		gd.widthHint = 100;
		gd.heightHint = 25;
		resetbutton.setText("Reset");
		resetbutton.setLayoutData(gd);

		showCalendarbutton = new Button(buttonscomposite,SWT.NONE);
		gd = new GridData();
		gd.widthHint = 100;
		gd.heightHint = 25;
		showCalendarbutton.setText("Show Date");
		showCalendarbutton.setLayoutData(gd);
		
		resetbutton.addSelectionListener(new CalendarConfigurationController(this));
		showCalendarbutton.addSelectionListener(new CalendarConfigurationController(this));
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
		doubleCalendarButtonBar = createButtonBar2(composite);
		doubleCalendarButtonBar.setVisible(false);
		return composite;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createButtonBar(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createButtonBar2(Composite parent) {
		doubleCalendarComposite = new Composite(parent, SWT.NONE);
		// create a layout with spacing and margins appropriate for the font size.
		GridLayout layout = new GridLayout();
		layout.numColumns = 1; // this is incremented by createButton
		layout.makeColumnsEqualWidth = true;
		layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
		doubleCalendarComposite.setLayout(layout);
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_END | GridData.VERTICAL_ALIGN_CENTER);
		doubleCalendarComposite.setLayoutData(data);
		doubleCalendarComposite.setFont(parent.getFont());

		// Add the buttons to the button bar.
		createButtonsForCalendarBetweenOrRangeButtonBar(doubleCalendarComposite);

		return doubleCalendarComposite;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createButtonBar(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createButtonBar(Composite parent) {
		new Composite(parent,SWT.NONE).setBackground(COLOR_WHITE);
		singleCalendarComposite = new Composite(parent, SWT.NONE);
		// create a layout with spacing and margins appropriate for the font size.
		GridLayout layout = new GridLayout();
		layout.numColumns = 1; // this is incremented by createButton
		layout.makeColumnsEqualWidth = true;
		layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
		singleCalendarComposite.setLayout(layout);
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_END | GridData.VERTICAL_ALIGN_CENTER);
		singleCalendarComposite.setLayoutData(data);
		singleCalendarComposite.setFont(parent.getFont());

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
		parent.setBackground(COLOR_WHITE);

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
				Calendar gcalendar = new GregorianCalendar(calendar.getYear(), calendar
						.getMonth(), calendar.getDay(), time.getHours(), time
						.getMinutes(), time.getSeconds());
				try {
					if (isBefore()) {
						setBeforeDateTime(sdf.format(gcalendar.getTime()));
					} else if (isAfter()) {
						setAfterDateTime(sdf.format(gcalendar.getTime()));
					} else {
						setDateTime(sdf.format(gcalendar.getTime()));
					}
					setWidgetDateTime(getDateTime());
				} catch(Exception ex) {
					DecisionTableUIPlugin.log(ex);
				}
				close();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
	 */
	protected void createButtonsForCalendarBetweenOrRangeButtonBar(Composite parent) {
		GridLayout glayout = new GridLayout(2, false);
		parent.setLayout(glayout);
		parent.setBackground(COLOR_WHITE);

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
				if (isBetweenOrRange()) {
					Calendar startGCalendar = null;
					Calendar endGCalendar = null;
					startGCalendar = new GregorianCalendar(calendar.getYear(), calendar
							.getMonth(), calendar.getDay(), time.getHours(), time
							.getMinutes(), time.getSeconds());

					endGCalendar = new GregorianCalendar(endDateCalendar.getYear(), endDateCalendar
							.getMonth(), endDateCalendar.getDay(), endDateTime.getHours(), endDateTime
							.getMinutes(), endDateTime.getSeconds());
					try {
						setBetweenDateTime(sdf.format(startGCalendar.getTime()), sdf.format(endGCalendar.getTime()));
						setWidgetDateTime(getDateTime());
					} catch(Exception ex) {
						DecisionTableUIPlugin.log(ex);
					}
				}
				close();
			}
		});
	}
}
