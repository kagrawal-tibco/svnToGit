/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 27/8/2010
 */

package com.tibco.cep.runtime.service.cluster.om;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Apr 21, 2008
 * Time: 4:43:42 PM
 * To change this template use File | Settings | File Templates.
 */
public interface RuleFunctionService {

    public Object invokeFunction(String functionURI, Object[] args);

}
