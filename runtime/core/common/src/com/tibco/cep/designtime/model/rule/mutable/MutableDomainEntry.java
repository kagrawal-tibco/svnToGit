package com.tibco.cep.designtime.model.rule.mutable;


import com.tibco.cep.designtime.model.Interval;
import com.tibco.cep.designtime.model.MutationObservableContainer;
import com.tibco.cep.designtime.model.rule.DomainEntry;


/**
 * Describes one entry in the Domain of an IOParameter.
 */
public interface MutableDomainEntry extends DomainEntry, MutationObservableContainer {


    /**
     * Sets the name of this DomainEntry
     *
     * @param name a String that is a valid entity name.
     */
    void setName(String name);


    /**
     * Sets the value of this DomainEntry
     *
     * @param value a String.
     */
    void setValue(String value);


    /**
     * Sets the value of this DomainEntry
     *
     * @param value an Interval.
     */
    void setValue(Interval value);

}
