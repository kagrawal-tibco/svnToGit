/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 11/8/2010
 */

package com.tibco.cep.runtime.service.om.impl.coherence.cluster.util;

import com.tangosol.util.SafeHashMap;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Jan 21, 2009
 * Time: 7:12:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class BESafeHashMap extends SafeHashMap {
    public BESafeHashMap() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public BESafeHashMap(int i, float v, float v1) {
        super(i, v, v1);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public synchronized Object remove(Object o) {
        super.remove(o);    //To change body of overridden methods use File | Settings | File Templates.
        return null;
    }

}
