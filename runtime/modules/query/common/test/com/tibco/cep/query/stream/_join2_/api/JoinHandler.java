package com.tibco.cep.query.stream._join2_.api;

/*
* Author: Ashwin Jayaprakash Date: May 28, 2009 Time: 7:21:19 PM
*/
public interface JoinHandler {
    void batchStart();

    void onJoin(Object[] joinMembers);

    boolean continueBatch();

    void batchEnd();
}