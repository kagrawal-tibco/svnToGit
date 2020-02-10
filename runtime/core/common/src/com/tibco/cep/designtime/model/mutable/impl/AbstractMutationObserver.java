package com.tibco.cep.designtime.model.mutable.impl;


import com.tibco.cep.designtime.model.MutationObservableContainer;
import com.tibco.cep.designtime.model.MutationObserver;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Oct 8, 2006
 * Time: 12:44:37 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractMutationObserver implements MutationObserver {


    private boolean suspended;


    public AbstractMutationObserver() {
        this.suspended = false;
    }


    public boolean isSuspended() {
        return this.suspended;
    }


    public void resume() {
        this.suspended = false;
    }


    public void startObserving(Object o) {
        if (o instanceof MutationObservableContainer) {
            ((MutationObservableContainer) o).getMutationObservable().addObserver(this);
        }
    }


    public void stopObserving(Object o) {
        if (o instanceof MutationObservableContainer) {
            ((MutationObservableContainer) o).getMutationObservable().deleteObserver(this);
        }
    }


    public void suspend() {
        this.suspended = true;
    }


}
