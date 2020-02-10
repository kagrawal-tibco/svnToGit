package com.tibco.cep.designtime.model;


import java.util.Observer;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Oct 8, 2006
 * Time: 12:27:44 PM
 * To change this template use File | Settings | File Templates.
 */
public interface MutationObserver extends Observer {


    /**
     * @return true iif reception of notifications is suspended.
     */
    boolean isSuspended();


    /**
     * Resumes reception of the notifications if it was suspended, else no effect.
     */
    void resume();


    /**
     * If o is a MutationObservableContainer, registers this MutationObserver with o, else no effect.
     * @param o an Object.
     */
    void startObserving(Object o);


    /**
     * If o is a MutationObservableContainer, unregisters this MutationObserver with o, else no effect.
     * @param o an Object.
     */
    void stopObserving(Object o);


    /**
     * Suspends reception of the notifications if it was not suspended yet, else no effect.
     * Subsequent notifications will be lost until resume() is called.
     */
    void suspend();


}
