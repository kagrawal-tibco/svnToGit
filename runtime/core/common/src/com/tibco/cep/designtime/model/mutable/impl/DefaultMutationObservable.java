package com.tibco.cep.designtime.model.mutable.impl;


import java.util.Observable;

import com.tibco.cep.designtime.model.MutationContext;
import com.tibco.cep.designtime.model.MutationObservable;
import com.tibco.cep.designtime.model.MutationObservableContainer;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Oct 6, 2006
 * Time: 9:10:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultMutationObservable extends Observable implements MutationObservable {


    private boolean isSuspended;
    private MutationObservableContainer mutationObservableContainer;


    public DefaultMutationObservable(MutationObservableContainer mutationObservableContainer) {
        super();
        this.mutationObservableContainer = mutationObservableContainer;
        this.isSuspended = false;
    }


    public void changeAndNotify() {
        this.changeAndNotify(new DefaultMutationContext(MutationContext.MODIFY, this.mutationObservableContainer));
    }


    public void changeAndNotify(MutationContext context) {
        if (!this.isSuspended()) {
            this.setChanged();
            this.notifyObservers(context);
        }
    }


    public boolean isSuspended() {
        return this.isSuspended;
    }


    public void resume() {
        this.isSuspended = false;
    }


    public void suspend() {
        this.isSuspended = true;
    }

}
