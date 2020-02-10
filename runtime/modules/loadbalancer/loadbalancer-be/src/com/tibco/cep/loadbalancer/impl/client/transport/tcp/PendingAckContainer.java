package com.tibco.cep.loadbalancer.impl.client.transport.tcp;

import java.util.concurrent.ConcurrentHashMap;

/*
* Author: Ashwin Jayaprakash / Date: Mar 25, 2010 / Time: 1:18:40 PM
*/

public class PendingAckContainer {
    protected Object receivedFromSenderId;

    protected ConcurrentHashMap<Object, AckHandle> negAcksToSend;

    protected ConcurrentHashMap<Object, AckHandle> pendingAcks;

    protected ConcurrentHashMap<Object, AckHandle> receivedButUnsentAcks;

    protected volatile long lastWorkedAtMillis;

    protected volatile long lastSuspectedAtMillis;

    public PendingAckContainer() {
        this.negAcksToSend = new ConcurrentHashMap<Object, AckHandle>();
        this.pendingAcks = new ConcurrentHashMap<Object, AckHandle>();
        this.receivedButUnsentAcks = new ConcurrentHashMap<Object, AckHandle>();
    }

    public Object getReceivedFromSenderId() {
        return receivedFromSenderId;
    }

    public void setReceivedFromSenderId(Object receivedFromSenderId) {
        this.receivedFromSenderId = receivedFromSenderId;
    }

    public ConcurrentHashMap<Object, AckHandle> getNegAcksToSend() {
        return negAcksToSend;
    }

    public ConcurrentHashMap<Object, AckHandle> getPendingAcks() {
        return pendingAcks;
    }

    public ConcurrentHashMap<Object, AckHandle> getReceivedButUnsentAcks() {
        return receivedButUnsentAcks;
    }

    public long getLastWorkedAtMillis() {
        return lastWorkedAtMillis;
    }

    public void setLastWorkedAtMillis(long lastWorkedAtMillis) {
        this.lastWorkedAtMillis = lastWorkedAtMillis;
        this.lastSuspectedAtMillis = 0;
    }

    public long getLastSuspectedAtMillis() {
        return lastSuspectedAtMillis;
    }

    public void setLastSuspectedAtMillis(long lastSuspectedAtMillis) {
        this.lastSuspectedAtMillis = lastSuspectedAtMillis;
        this.lastWorkedAtMillis = 0;
    }

    public void addToPendingAcks(Object uniqueIdOfMessage, AckHandle ackHandle) {
        pendingAcks.put(uniqueIdOfMessage, ackHandle);
    }

    public AckHandle removeFromPendingAcks(Object uniqueIdOfMessage) {
        return pendingAcks.remove(uniqueIdOfMessage);
    }

    public void addToReceivedButUnsentAcks(Object uniqueIdOfMessage, AckHandle ackHandle) {
        receivedButUnsentAcks.put(uniqueIdOfMessage, ackHandle);
    }

    public AckHandle removeFromReceivedButUnsentAcks(Object uniqueIdOfMessage) {
        return receivedButUnsentAcks.remove(uniqueIdOfMessage);
    }

    public void addToNegAcksToSend(Object uniqueIdOfMessage, AckHandle ackHandle) {
        negAcksToSend.put(uniqueIdOfMessage, ackHandle);
    }

    public ReceivedMessageHandle removeFromNegAcksToSend(Object uniqueIdOfMessage) {
        return negAcksToSend.remove(uniqueIdOfMessage);
    }
}
