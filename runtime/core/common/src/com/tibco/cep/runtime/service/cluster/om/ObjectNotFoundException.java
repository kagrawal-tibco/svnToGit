/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.om;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Apr 14, 2008
 * Time: 4:33:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class ObjectNotFoundException extends Exception{
    public ObjectNotFoundException() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public ObjectNotFoundException(Throwable cause) {
        super(cause);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public ObjectNotFoundException(String message) {
        super(message);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public ObjectNotFoundException(String message, Throwable cause) {
        super(message, cause);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
