package com.tibco.cep.bpmn.runtime.templates;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.tibco.cep.bpmn.runtime.model.VersionInfo;
import com.tibco.cep.designtime.model.process.ProcessModel;

/*
* Author: Suresh Subramani / Date: 12/2/11 / Time: 2:15 PM
*/
public class DefaultVersionInfo implements VersionInfo {

    private ProcessModel processModel;  //Design time processModel;

    public DefaultVersionInfo(ProcessModel processModel) {
        this.processModel = processModel;
    }

    @Override
    public int getRevision() {
        return processModel.getRevision();
    }

    @Override
    public String getOriginalAuthor() {
        return processModel.getOriginalAuthor();
    }

    @Override
    public Calendar getDeployedDate() {

        try {
            return parseDate(processModel.getDeployedDate(), ProcessModel.DATE_TIME_PATTERN);
        } catch (ParseException e) {
            return Calendar.getInstance();
        }
    }

    @Override
    public Calendar getLastModifiedDate() {
        try {
            return parseDate(processModel.getLastModified(), ProcessModel.DATE_TIME_PATTERN);
        } catch (ParseException e) {
            return Calendar.getInstance();
        }
    }

    @Override
    public String getLastModifiedAuthor() {
        return processModel.getLastModifiedAuthor();
    }

    public static Calendar parseDate(String date,String format) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat(format);
		Calendar cal = new GregorianCalendar();
        df.setCalendar(cal);
        df.parse(date);
        df.setCalendar(null);
        return cal;
	}
}
