package com.tibco.rta.service.cluster;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 9/4/13
 * Time: 10:59 AM
 * To change this template use File | Settings | File Templates.
 */
public class GroupQuorum {

    /**
     * Configured members.
     */
    private int numberOfConfiguredMembers;

    /**
     * Number of actual members.
     */
    private AtomicInteger actualMemberCount = new AtomicInteger(0);

    public GroupQuorum(int numberOfConfiguredMembers) {
        this.numberOfConfiguredMembers = numberOfConfiguredMembers;
    }

    public int increment() {
        return actualMemberCount.getAndIncrement();
    }

    public int decrement() {
        return actualMemberCount.getAndDecrement();
    }

    public boolean isQuorumPresent() {
        return actualMemberCount.get() >= numberOfConfiguredMembers;
    }

    public int current() {
        return actualMemberCount.get();
    }
}
