package com.tibco.cep.runtime.session.impl;

import com.tibco.cep.kernel.core.rete.FilterNode;
import com.tibco.cep.kernel.core.rete.JoinNode;
import com.tibco.cep.kernel.core.rete.ReteListener;

/*
* Author: Ashwin Jayaprakash Date: Feb 21, 2009 Time: 6:51:12 PM
*/
public abstract class AbstractReteListener implements ReteListener {
    public void on() {
    }

    public void off() {
    }

    public boolean isOn() {
        return false;
    }

    public void rtcStart(int rtcType, Object context) {
    }

    public void rtcResolved() {
    }

    public void rtcEnd() {
    }

    public void actionStart(int actionType, Object context) {
    }

    public void actionExecuted() {
    }

    public void actionEnd(int agendaSize) {
    }

    public void filterConditionStart(FilterNode filerNode) {
    }

    public void filterConditionEnd(boolean success) {
    }

    public void joinConditionStart(JoinNode joinNode, boolean leftSearch) {
    }

    public void joinConditionEnd(int numSuccess, int numFailed) {
    }
}
