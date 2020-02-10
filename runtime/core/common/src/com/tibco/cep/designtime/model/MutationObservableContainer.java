package com.tibco.cep.designtime.model;


/**
 * Something that is the object of a mutation.
 */
public interface MutationObservableContainer {


    /**
     * Provides an Observable that will notify its observers of mutations.
     * Observers will receive a MutationContext argument.
     *
     * @return an Observable that notifies its observers of mutations.
     */
    MutationObservable getMutationObservable();

}
