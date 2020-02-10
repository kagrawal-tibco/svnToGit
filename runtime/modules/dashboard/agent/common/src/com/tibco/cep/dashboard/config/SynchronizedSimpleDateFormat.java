package com.tibco.cep.dashboard.config;

import java.text.AttributedCharacterIterator;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class SynchronizedSimpleDateFormat extends SimpleDateFormat {
	
	//INFO We have to use a global MUTEX to synchronize simple date format operations since simple date format internally uses cached parser [see java.text.SimpleDateFormat.initialize(Locale)]
	private static final Object MUTEX = new Object();

	private static final long serialVersionUID = -5185629281165783744L;

	protected SimpleDateFormat dateFormat;

	public SynchronizedSimpleDateFormat(String formatPattern) {
		this.dateFormat = new SimpleDateFormat(formatPattern);
	}

	public SynchronizedSimpleDateFormat(DateFormat dateFormat) {
		this.dateFormat = (SimpleDateFormat) dateFormat;
	}

	@Override
	public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
		synchronized (MUTEX) {
			return dateFormat.format(date, toAppendTo, fieldPosition);
		}
	}

	@Override
	public Date parse(String source, ParsePosition pos) {
		synchronized (MUTEX) {
			return dateFormat.parse(source, pos);
		}
	}

	@Override
	public String toPattern() {
		return dateFormat.toPattern();
	}

	@Override
	public Object clone() {
		return new SynchronizedSimpleDateFormat(dateFormat.toPattern());
	}

	@Override
	public Calendar getCalendar() {
		return dateFormat.getCalendar();
	}

	@Override
	public NumberFormat getNumberFormat() {
		return dateFormat.getNumberFormat();
	}

	@Override
	public TimeZone getTimeZone() {
		return dateFormat.getTimeZone();
	}

	@Override
	public boolean isLenient() {
		return dateFormat.isLenient();
	}

	@Override
	public void setCalendar(Calendar newCalendar) {
		dateFormat.setCalendar(newCalendar);
	}

	@Override
	public void setLenient(boolean lenient) {
		dateFormat.setLenient(lenient);
	}

	@Override
	public void setNumberFormat(NumberFormat newNumberFormat) {
		dateFormat.setNumberFormat(newNumberFormat);
	}

	@Override
	public void setTimeZone(TimeZone zone) {
		dateFormat.setTimeZone(zone);
	}

	@Override
	public boolean equals(Object obj) {
		return dateFormat.equals(obj);
	}

	@Override
	public int hashCode() {
		return dateFormat.hashCode();
	}

	@Override
	public void applyLocalizedPattern(String pattern) {
		dateFormat.applyLocalizedPattern(pattern);
	}

	@Override
	public void applyPattern(String pattern) {
		dateFormat.applyPattern(pattern);
	}

	@Override
	public AttributedCharacterIterator formatToCharacterIterator(Object obj) {
		return dateFormat.formatToCharacterIterator(obj);
	}

	@Override
	public Date get2DigitYearStart() {
		return dateFormat.get2DigitYearStart();
	}

	@Override
	public DateFormatSymbols getDateFormatSymbols() {
		return dateFormat.getDateFormatSymbols();
	}

//	@Override
//	public Date parse(String source) throws ParseException {
//		return dateFormat.parse(source);
//	}
//
//	@Override
//	public Object parseObject(String source, ParsePosition pos) {
//		return dateFormat.parseObject(source, pos);
//	}
//
//	@Override
//	public Object parseObject(String source) throws ParseException {
//		return dateFormat.parseObject(source);
//	}

	@Override
	public void set2DigitYearStart(Date startDate) {
		dateFormat.set2DigitYearStart(startDate);
	}

	@Override
	public void setDateFormatSymbols(DateFormatSymbols newFormatSymbols) {
		dateFormat.setDateFormatSymbols(newFormatSymbols);
	}

	@Override
	public String toLocalizedPattern() {
		return dateFormat.toLocalizedPattern();
	}

	@Override
	public String toString() {
		return dateFormat.toString();
	}

}
