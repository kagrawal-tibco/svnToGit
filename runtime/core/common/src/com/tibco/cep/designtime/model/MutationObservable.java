package com.tibco.cep.designtime.model;


import java.util.Observer;


/**
 *
 */
public interface MutationObservable {


    void addObserver(Observer o);


    int countObservers();


    void deleteObserver(Observer o);


    void deleteObservers();


    /**
     * @return true iif transmission of notifications is suspended.
     */
    boolean isSuspended();


    /**
     * Resumes transmission of the notifications if it was suspended, else no effect.
     */
    void resume();


    /**
     * Suspends transmission of the notifications if it was not suspended yet, else no effect.
     * Subsequent notifications will be lost until resume() is called.
     */
    void suspend();


    /**
     * Sets the status of this object to changed and notifies all observers.
     */
    void changeAndNotify();


    /**
     * Sets the status of this object to changed and notifies all observers.
     * @param context a MutationContext that describes the mutation.
     */
    void changeAndNotify(MutationContext context);


}
