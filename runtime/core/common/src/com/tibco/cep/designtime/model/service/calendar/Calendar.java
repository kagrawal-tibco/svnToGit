package com.tibco.cep.designtime.model.service.calendar;


import com.tibco.cep.designtime.model.Entity;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Sep 25, 2006
 * Time: 6:58:02 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Calendar extends Entity {


    int NON_RECURRING_TYPE = 0;
    int RECURRING_TYPE = 1;


    int getType();
}
