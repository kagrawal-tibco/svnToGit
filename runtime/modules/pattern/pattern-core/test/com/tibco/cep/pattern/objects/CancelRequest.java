package com.tibco.cep.pattern.objects;

import com.tibco.cep.pattern.impl.util.EventProperty;
import com.tibco.cep.pattern.impl.util.EventUniqueId;

/*
* Author: Ashwin Jayaprakash Date: Sep 2, 2009 Time: 5:33:45 PM
*/
public class CancelRequest {
    protected String accountId;

    public CancelRequest() {
    }

    public CancelRequest(String accountId) {
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
