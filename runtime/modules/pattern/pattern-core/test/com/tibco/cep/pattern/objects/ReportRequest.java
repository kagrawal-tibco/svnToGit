package com.tibco.cep.pattern.objects;

import com.tibco.cep.pattern.impl.util.EventProperty;
import com.tibco.cep.pattern.impl.util.EventUniqueId;

/*
* Author: Ashwin Jayaprakash Date: Aug 12, 2009 Time: 4:03:21 PM
*/
public class ReportRequest {
    protected String accountId;

    public ReportRequest() {
    }

    public ReportRequest(String accountId) {
        this.accountId = accountId;
    }

    @EventUniqueId
    @EventProperty("accountId")
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
