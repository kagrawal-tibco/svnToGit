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

package com.tibco.cep.runtime.service.om.impl.coherence.tangosol;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Sep 25, 2006
 * Time: 10:24:05 AM
 * To change this template use File | Settings | File Templates.
 */
public class EntityIdRange {
    private long m_rangeStart;
    private long m_rangeEnd;


    public EntityIdRange(long _rstart, long _rend) {
        this.m_rangeStart = _rstart;
        this.m_rangeEnd = _rend;
    }

    public long getStart() {
        return this.m_rangeStart;
    }

    public long getEnd() {
        return this.m_rangeEnd;
    }
}
