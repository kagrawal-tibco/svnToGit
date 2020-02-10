package com.tibco.cep.loadbalancer.impl.transport;

import com.tibco.cep.loadbalancer.impl.message.SimpleSpecialMessage;

/*
* Author: Ashwin Jayaprakash / Date: Aug 30, 2010 / Time: 3:30:37 PM
*/

public class MembershipChangeMessage extends SimpleSpecialMessage {
    protected Object memberId;

    protected boolean joiningOrLeaving;

    public MembershipChangeMessage() {
    }

    public MembershipChangeMessage(Object uniqueId, Object senderId, Object memberId, boolean joiningOrLeaving) {
        super(uniqueId, senderId);

        this.memberId = memberId;
        this.joiningOrLeaving = joiningOrLeaving;
    }

    public Object getMemberId() {
        return memberId;
    }

    /**
     * @return true if joining. false if leaving.
     */
    public boolean isJoining() {
        return joiningOrLeaving;
    }

    //----------------

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "{uniqueId=" + uniqueId + ", senderId=" + senderId
                + ", memberId=" + memberId + ", joiningOrLeaving=" + joiningOrLeaving + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MembershipChangeMessage)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        MembershipChangeMessage that = (MembershipChangeMessage) o;

        if (joiningOrLeaving != that.joiningOrLeaving) {
            return false;
        }
        if (!memberId.equals(that.memberId)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + memberId.hashCode();
        result = 31 * result + (joiningOrLeaving ? 1 : 0);

        return result;
    }
}