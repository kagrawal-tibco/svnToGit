/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 *  All Rights Reserved.
 *
 *  This software is confidential and proprietary information of
 *  TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.om.impl;

import com.tangosol.net.Invocable;
import com.tangosol.net.InvocationService;
import com.tibco.cep.runtime.service.cluster.gmp.MemberConfiguration;
import com.tibco.cep.runtime.service.cluster.gmp.MemberConfigurationRetriever;

/*
* Author: Suresh Subramani / Date: Oct 5, 2010 / Time: 7:29:11 PM
*/
@Deprecated
public class CoherenceMemberConfigurationRetriever extends MemberConfigurationRetriever implements Invocable {
    private MemberConfiguration memberConfig;

    @Override
    public void init(InvocationService invocationService) {
    }

    @Override
    public void run() {
        memberConfig = fetchLocalMemberConfiguration();
    }

    @Override
    public Object getResult() {
        return memberConfig;
    }
}
