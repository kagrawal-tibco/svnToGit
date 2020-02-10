package com.tibco.cep.runtime.management;

import com.tibco.cep.runtime.service.Service;

/*
* Author: Ashwin Jayaprakash Date: Jan 27, 2009 Time: 5:05:54 PM
*/
public interface ManagementCentral extends Service {
    ManagementTable getManagementTable();

    MetricTable getMetricTable();
}
