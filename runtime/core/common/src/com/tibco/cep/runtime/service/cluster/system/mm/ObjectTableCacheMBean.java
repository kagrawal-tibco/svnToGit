/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 13/8/2010
 */

package com.tibco.cep.runtime.service.cluster.system.mm;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Nov 20, 2008
 * Time: 2:45:43 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ObjectTableCacheMBean {


    String getObjectTableCacheName();

    String getObjectTableExtIdCacheName();

    int getObjectTableSize();

    int getObjectExtIdTableSize();

    String getTupleById(long id) throws Exception;

    String getTupleByExtId(String extId) throws Exception;

    void registerMBean();
}
