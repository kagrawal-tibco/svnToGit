package com.tibco.cep.persister.membership;

import com.tibco.cep.impl.common.resource.UID;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: pgowrish
 * Date: 4/25/12
 * Time: 11:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class SpaceOwnership implements Serializable {

    private UID memberID;
    private long ownerStartTime;  //time at which ownership began in unix epoch milliseconds

    public SpaceOwnership(UID memberID, long startTime) {

        this.memberID = memberID;
        this.ownerStartTime = startTime;
    }

    public UID getMemberID() {
        return memberID;
    }

    public long getOwnerStartTime() {
        return ownerStartTime;
    }
}
