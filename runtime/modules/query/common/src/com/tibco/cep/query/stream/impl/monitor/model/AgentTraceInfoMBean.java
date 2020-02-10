package com.tibco.cep.query.stream.impl.monitor.model;

import java.sql.Timestamp;

/*
* Author: Ashwin Jayaprakash Date: Apr 8, 2009 Time: 4:19:51 PM
*/
public interface AgentTraceInfoMBean {
    Timestamp getMeasurementStartTime();

    Timestamp getMeasurementEndTime();

    int getNewEntities();

    int getModifiedEntities();

    int getDeletedEntities();

    Throwable getError();

    double getTps();
}
