package com.tibco.cep.designtime.model.mutable.impl;


import java.util.Observable;

import com.tibco.cep.designtime.model.MutationContext;
import com.tibco.cep.designtime.model.MutationObservable;


/**
 * An implementation of MutationObserver which, when it receives a notification,
 * forces a notification of a given MutationObservable.
 */
public class DefaultMutationNotificationTransmitter extends AbstractMutationObserver {


    private MutationObservable observable;


    /**
     * Constructor.
     * @param nextObservableInChain the MutationObservable, not null, to update when this object is notified.
     */
    public DefaultMutationNotificationTransmitter(MutationObservable nextObservableInChain) {
        if (null == nextObservableInChain) {
            throw new IllegalArgumentException();
        }
        this.observable = nextObservableInChain;
    }


    /**
     * Receives a Notification from a MutationObservable.
     * @param observable the Observable which called this method.
     * @param object an Object. If it is a Mutation Context, it will be transmitted to the MutationObservable given in the constructor.
     */
    public void update(Observable observable, Object object) {
        if (!this.isSuspended()) {
            if (object instanceof MutationContext) {
                this.observable.changeAndNotify((MutationContext) object);
            } else {
                this.observable.changeAndNotify();
            }
        }
    }

}
