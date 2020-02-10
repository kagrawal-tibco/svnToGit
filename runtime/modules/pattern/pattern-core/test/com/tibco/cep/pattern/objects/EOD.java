package com.tibco.cep.pattern.objects;

import com.tibco.cep.impl.common.resource.DefaultId;
import com.tibco.cep.pattern.impl.util.EventProperty;
import com.tibco.cep.pattern.impl.util.EventUniqueId;

import java.util.Date;

/*
* Author: Ashwin Jayaprakash Date: Aug 12, 2009 Time: 4:03:21 PM
*/
public class EOD {
    protected Date date;

    protected DefaultId uniqueId;

    public EOD() {
    }

    public EOD(Date date) {
        this.date = date;
        this.uniqueId = new DefaultId(date, System.identityHashCode(this));
    }

    @EventUniqueId
    public Object getUniqueId() {
        return uniqueId;
    }

    @EventProperty("date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
        this.uniqueId = new DefaultId(date, System.identityHashCode(this));
    }
}
