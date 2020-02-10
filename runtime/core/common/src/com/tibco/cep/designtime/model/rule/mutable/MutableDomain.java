package com.tibco.cep.designtime.model.rule.mutable;


import com.tibco.cep.designtime.model.MutationObservableContainer;
import com.tibco.cep.designtime.model.rule.Domain;
import com.tibco.xml.datamodel.XiNode;


/**
 * List of DomainEntry.
 */
public interface MutableDomain extends Domain, MutationObservableContainer {


    /**
     * Deserializes the XiNode into this Domain.
     *
     * @param domainNode an XiNode obtained from Domain.toXiNode
     */
    public void load(XiNode domainNode);


}
