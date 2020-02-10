package com.tibco.cep.kernel.core.rete;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: pgowrish
 * Date: 10/23/12
 * Time: 6:02 PM
 * This class will maintain a list of rete listeners and do dispatch for the rete actions.
 */
public class ReteDispatcher implements ReteListener {

    protected ArrayList<ReteListener> m_reteListenerList;

    public ReteDispatcher() {
        this.m_reteListenerList = new ArrayList<ReteListener>();
    }

    public synchronized boolean add(ReteListener listener) {
        return m_reteListenerList.add(listener);
    }

    public ReteListener getReteListener(Class clazz) {
        for(int i=0;i<m_reteListenerList.size();i++) {
            ReteListener listener = m_reteListenerList.get(i);
            if( listener.getClass() == clazz ) {
                return listener;
            }
        }
        return null;
    }

    public synchronized boolean remove(ReteListener listener) {
        return m_reteListenerList.remove(listener);
    }

    public synchronized void removeAll() {
        m_reteListenerList.clear();
    }

    // - Actions

    @Override
    public void on() {
        for(int i=0;i<m_reteListenerList.size();i++) {
            ReteListener listener = m_reteListenerList.get(i);
            if( listener!=null ) {
                listener.on();
            }
        }
    }

    @Override
    public void off() {
        for(int i=0;i<m_reteListenerList.size();i++) {
            ReteListener listener = m_reteListenerList.get(i);
            if( listener!=null ) {
                listener.off();
            }
        }
    }

    @Override
    public boolean isOn() {
        return false;
    }

    @Override
    public void rtcStart(int rtcType, Object context) {
        for(int i=0;i<m_reteListenerList.size();i++) {
            ReteListener listener = m_reteListenerList.get(i);
            if( listener!=null ) {
                listener.rtcStart(rtcType, context);
            }
        }
    }

    @Override
    public void rtcResolved() {
        for(int i=0;i<m_reteListenerList.size();i++) {
            ReteListener listener = m_reteListenerList.get(i);
            if( listener!=null ) {
                listener.rtcResolved();
            }
        }
    }

    @Override
    public void rtcEnd() {
        for(int i=0;i<m_reteListenerList.size();i++) {
            ReteListener listener = m_reteListenerList.get(i);
            if( listener!=null ) {
                listener.rtcEnd();
            }
        }
    }

    @Override
    public void actionStart(int actionType, Object context) {
        for(int i=0;i<m_reteListenerList.size();i++) {
            ReteListener listener = m_reteListenerList.get(i);
            if( listener!=null ) {
                listener.actionStart(actionType, context);
            }
        }
    }

    @Override
    public void actionExecuted() {
        for(int i=0;i<m_reteListenerList.size();i++) {
            ReteListener listener = m_reteListenerList.get(i);
            if( listener!=null ) {
                listener.actionExecuted();
            }
        }
    }

    @Override
    public void actionEnd(int agendaSize) {
        for(int i=0;i<m_reteListenerList.size();i++) {
            ReteListener listener = m_reteListenerList.get(i);
            if( listener!=null ) {
                listener.actionEnd(agendaSize);
            }
        }
    }

    @Override
    public void filterConditionStart(FilterNode filerNode) {
        for(int i=0;i<m_reteListenerList.size();i++) {
            ReteListener listener = m_reteListenerList.get(i);
            if( listener!=null ) {
                listener.filterConditionStart(filerNode);
            }
        }
    }

    @Override
    public void filterConditionEnd(boolean success) {
        for(int i=0;i<m_reteListenerList.size();i++) {
            ReteListener listener = m_reteListenerList.get(i);
            if( listener!=null ) {
                listener.filterConditionEnd(success);
            }
        }
    }

    @Override
    public void joinConditionStart(JoinNode joinNode, boolean leftSearch) {
        for(int i=0;i<m_reteListenerList.size();i++) {
            ReteListener listener = m_reteListenerList.get(i);
            if( listener!=null ) {
                listener.joinConditionStart(joinNode, leftSearch);
            }
        }
    }

    @Override
    public void joinConditionEnd(int numSuccess, int numFailed) {
        for(int i=0;i<m_reteListenerList.size();i++) {
            ReteListener listener = m_reteListenerList.get(i);
            if( listener!=null ) {
                listener.joinConditionEnd(numSuccess, numFailed);
            }
        }
    }
    // - Actions
}
