/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 30/6/2010
 */

package com.tibco.cep.runtime.service.om.impl.coherence.cluster;

import com.tangosol.util.SafeHashMap;

/**
 * User: ssubrama
 * Creation Date: Jul 11, 2008
 * Creation Time: 6:46:59 PM
 * <p/>
 * $LastChangedDate$
 * $Rev$
 * $LastChangedBy$
 * $URL$
 */
public class TSafeHashMap extends SafeHashMap {

    public synchronized Object remove(Object o) {
        //System.out.println("My remove called...");
        super.remove(o);    //To change body of overridden methods use File | Settings | File Templates.
        return null;
    }
}
