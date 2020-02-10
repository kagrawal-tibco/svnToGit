package com.tibco.cep.designtime.model.rule;


import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Sep 26, 2006
 * Time: 12:30:01 PM
 * To change this template use File | Settings | File Templates.
 */
public interface DomainEntry {


    /**
     * Returns a list of ModelError objects describing the errors in this DomainEntry
     * considered out of any Domain. For a complete list of errors in a DomainEntry within a Domain
     * use Domain.getModelErrors(DomainEntry)
     *
     * @return a list of ModelError objects describing the errors in this DomainEntry.
     */
    List getModelErrors();


    /**
     * @return the name of this DomainEntry.
     */
    String getName();


    /**
     * @return the Domain that contains this DomainEntry.
     */
    Domain getParent();


    /**
     * @return a String or an Interval.
     */
    Object getValue();

}
