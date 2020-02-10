/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tibco.cep.runtime.util.scheduler.test.util;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 *
 * Author: Karthikeyan Subramanian / Date: Jan 27, 2010 / Time: 12:20:25 PM
 */
public class CustomLogFormatter extends Formatter {

    public CustomLogFormatter() {
    }

    @Override
    public String format(LogRecord record) {
        StringBuilder str = new StringBuilder();
        str.append(record.getThreadID() + "\t");
        str.append(record.getMillis() + "\t");
        str.append(record.getMessage() + "\n");
        return str.toString();
    }

}
