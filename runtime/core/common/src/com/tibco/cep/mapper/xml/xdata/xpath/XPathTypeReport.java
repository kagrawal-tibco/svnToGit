package com.tibco.cep.mapper.xml.xdata.xpath;

import com.tibco.xml.schema.SmSequenceType;

/**
 * A type report.
 */
public class XPathTypeReport
{
    public XPathTypeReport() {
        this(ErrorMessageList.EMPTY_LIST);
    }

    public XPathTypeReport(ErrorMessageList errs) {
        this.errors = errs;
        this.xtype = null;
    }

    public XPathTypeReport(SmSequenceType type, ErrorMessageList errs) {
        this.errors = errs;
        this.xtype = type;
    }

    public final ErrorMessageList errors;
    public final SmSequenceType xtype; // (maybe replace type/min/max by this alone, for now, its duplicate info)
}
