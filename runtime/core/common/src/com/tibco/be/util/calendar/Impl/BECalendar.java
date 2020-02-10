package com.tibco.be.util.calendar.Impl;

import java.util.Calendar;
import java.util.Date;

import com.tibco.be.util.calendar.CalendarException;

public class BECalendar extends CalendarImpl {

	
	private static final long serialVersionUID = 1L;
	Calendar cal ;
	
	public BECalendar(boolean recurring,Calendar cal) {
		super(recurring);
		this.cal = cal;
	}

	@Override
	public boolean occursOnDate(Date date) throws CalendarException {
		if(cal != null && date.equals(cal.getTime())){
			return true;
		}
		return false;
	}

	@Override
	public boolean occursNow() {
		return false;
	}

	@Override
	public Date getNextStartTime() {
		return cal.getTime();
	}

	@Override
	public Date getNextEndTime() {
		return cal.getTime();
	}

}
