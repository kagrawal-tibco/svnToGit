package com.tibco.cep.designtime.model.rule;


import java.util.List;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Sep 26, 2006
 * Time: 12:28:19 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Domain extends List {


    /**
     * @param domainEntryName a String.
     * @return the DomainEntry in this Domain which name matches domainEntryName.
     */
    DomainEntry get(String domainEntryName);


    /**
     * Returns a list of ModelError objects describing all errors in the given DomainEntry
     * including errors related to the Domain (duplicate name, colliding values, etc).
     *
     * @param entry the DomainEntry to check.
     * @return a list of ModelError objects describing the errors in the DomainEntry.
     */
    List getModelErrors(DomainEntry entry);


    /**
     * @return a list of ModelError objects describing the errors in this Domain.
     */
    List getModelErrors();


    /**
     * @return the Symbol which has this Domain.
     */
    Symbol getParent();


    /**
     * Serializes this Domain into an XiNode.
     *
     * @param factory the XiFactory used to build the XiNode.
     * @param name    an ExpandedName used as root element name.
     * @return an XiNode that represents this Domain.
     */
    XiNode toXiNode(XiFactory factory, ExpandedName name);
}
