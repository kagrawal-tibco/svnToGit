package com.tibco.cep.studio.decision.table.calendar;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

public abstract class AbstractDateTimeCalendarDialog extends Dialog implements IDateTimeConstants{

	protected DateTime startDateCalWidget;
	protected DateTime startDateTimeWidget;
	protected Calendar startDateCalendar;
	protected Calendar endDateCalendar;
	protected Text valText;
	protected Scale yrscale;
	protected Spinner yrspinner;
	protected Scale monthsscale;
	protected Spinner monspinner;
	protected Scale daysscale;
	protected Spinner dayspinner;
	protected Scale hrssscale;
	protected Spinner hrsspinner;
	protected Scale minsscale;
	protected Spinner minspinner;
	protected Scale secsscale;
	protected Spinner secspinner;
	protected Combo dateOpCombo;
	protected Label endDatelabel;
	protected Label startDatelabel;
	protected Text endDateValText;
	protected DateTime endDateCalendarWidget;
	protected DateTime endDateTimeWidget;
	protected Button incbutton;
	protected Button decbutton;
	protected Button resetButton;
	protected Button setToCurrentDateButton;

	protected String initialDateTimeExpr;

	protected boolean startDateCalendarSelected = true;
	protected boolean endDateCalendarSelected = false;

	protected String formattedDateTimeExpr;
	protected String currentDateTime;
	
	protected int minimumYear = 1970;
	protected int maximumYear = 2050;
	
	public static final String BETWEEN_DATETIME_EXPR = "> {0} && < {1}";
	public static final String OUTSIDE_DATETIME_EXPR = "< {0} || > {1}";
	public static final String BEFORE_DATETIME_EXPR = "< {0}";
	public static final String AFTER_DATETIME_EXPR = "> {0}";
	
	// The default date/time format, received in the constructor.
    final public SimpleDateFormat sdf;

	/**
	 * @param dateTime
	 */
	protected abstract void setWidgetDateTime(String dateTime);
	
	protected AbstractDateTimeCalendarDialog(Shell parentShell, SimpleDateFormat sdf) {
		super(parentShell);
		this.sdf = sdf;
	}

	/**
	 * @param calendar
	 * @param time
	 * @param gcalandar
	 * @param text
	 */
	protected void setCalendar(DateTime calendar, DateTime time, Calendar gcalandar,Text text){
		calendar.setDay(gcalandar.get(Calendar.DAY_OF_MONTH));
		calendar.setMonth(gcalandar.get(Calendar.MONTH));
		calendar.setYear(gcalandar.get(Calendar.YEAR));
		if (gcalandar.get(Calendar.AM_PM) == 1) {
			// add 12 to set the time to PM
			time.setHours(gcalandar.get(Calendar.HOUR)+12);
		} else {
			time.setHours(gcalandar.get(Calendar.HOUR));
		}
		time.setMinutes(gcalandar.get(Calendar.MINUTE));
		time.setSeconds(gcalandar.get(Calendar.SECOND));
		text.setText(sdf.format(gcalandar.getTime()));
	}

	public void resetSpinners(Calendar calendar) {
		int year = calendar.get(Calendar.YEAR);
		yrspinner.setSelection(year);
		int month = calendar.get(Calendar.MONTH);
		month += 1;
		monspinner.setSelection(month);
		dayspinner.setSelection(calendar.get(Calendar.DAY_OF_MONTH));
		hrsspinner.setSelection(calendar.get(Calendar.HOUR_OF_DAY));
		minspinner.setSelection(calendar.get(Calendar.MINUTE));
		secspinner.setSelection(calendar.get(Calendar.SECOND));
	}
	
	public void resetScales(Calendar calendar) {
		int year = calendar.get(Calendar.YEAR);
		year -= minimumYear;
		yrscale.setSelection(year);
		int month = calendar.get(Calendar.MONTH);
		monthsscale.setSelection(month);
		daysscale.setSelection(calendar.get(Calendar.DAY_OF_MONTH));
		hrssscale.setSelection(calendar.get(Calendar.HOUR_OF_DAY));
		minsscale.setSelection(calendar.get(Calendar.MINUTE));
		secsscale.setSelection(calendar.get(Calendar.SECOND));
	}
	
	/**
	 * @param calendar
	 * @param isBetweenOrRange
	 */
	public void resetConfiguration(Calendar calendar){
		resetSpinners(calendar);
		resetScales(calendar);

	}
	
	
	protected String getFormattedDateTime() {
		return sdf.format(new Date());
	}
	
	public String getFormattedDateTimeExpr() {
		return formattedDateTimeExpr;
	}

	public void setFormattedDateTimeExpr(String dateTime) {
		this.formattedDateTimeExpr = dateTime;
	}

	public void setBeforeDateTime(String dateTime) {
		this.formattedDateTimeExpr = MessageFormat.format(BEFORE_DATETIME_EXPR, dateTime);
	}

	public void setAfterDateTime(String dateTime) {
		this.formattedDateTimeExpr = MessageFormat.format(AFTER_DATETIME_EXPR, dateTime);
	}
	
	public String getCurrentDateTime() {
		return currentDateTime;
	}

	public void setCurrentDateTime(String currentDate) {
		this.currentDateTime = currentDate;
	}

	public DateTime getCalendar() {
		return startDateCalWidget;
	}

	public void setCalendar(DateTime calendar) {
		this.startDateCalWidget = calendar;
	}

	public DateTime getTime() {
		return startDateTimeWidget;
	}

	public void setTime(DateTime time) {
		this.startDateTimeWidget = time;
	}

	public Calendar getFirstcalendar() {
		return startDateCalendar;
	}

	public void setFirstcalendar(Calendar firstcalendar) {
		this.startDateCalendar = firstcalendar;
	}

	public Calendar getSecondcalendar() {
		return endDateCalendar;
	}

	public void setSecondcalendar(Calendar secondcalendar) {
		this.endDateCalendar = secondcalendar;
	}

	public Text getValText() {
		return valText;
	}

	public void setValText(Text valText) {
		this.valText = valText;
	}
	public boolean isStartDateCalendarSelected() {
		return startDateCalendarSelected;
	}

	public void setStartDateCalendarSelected(boolean startDateCalendarSelected) {
		this.startDateCalendarSelected = startDateCalendarSelected;
	}

	public boolean isEndDateCalendarSelected() {
		return endDateCalendarSelected;
	}

	public void setEndDateCalendarSelected(boolean endDateCalendarSelected) {
		this.endDateCalendarSelected = endDateCalendarSelected;
	}

}
