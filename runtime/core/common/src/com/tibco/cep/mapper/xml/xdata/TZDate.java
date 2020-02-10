/*
 * Created by IntelliJ IDEA.
 * User: dimitris
 * Date: Apr 25, 2002
 * Time: 11:45:23 AM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package com.tibco.cep.mapper.xml.xdata;

import java.io.Serializable;
import java.util.Date;

/**
 * Extends java.util.Date and keeps the original string along with the parsed date
 * information.
 */
class TZDate extends Date implements Serializable {
    private static final long serialVersionUID = -5235977388257759597L;
    private String dateStr;

    public TZDate(long date, String dateStr) {
        super(date);
        this.dateStr = dateStr;
    }

    public String getDateStr() {
        return dateStr;
    }
}
