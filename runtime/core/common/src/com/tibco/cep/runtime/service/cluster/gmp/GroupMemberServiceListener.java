/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 *  All Rights Reserved.
 *
 *  This software is confidential and proprietary information of
 *  TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.gmp;

public interface GroupMemberServiceListener {
    enum Status {
        JOINING,
        JOINED,
        LEAVING,
        LEFT

    }

    void memberJoined(GroupMember member);

    void memberLeft(GroupMember member);

    void memberStatusChanged(GroupMember member, Status oldStatus, Status newStatus);

}
