package com.tibco.be.ws.rt.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.tibco.cep.designtime.core.model.domain.DomainInstance;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 3/4/12
 * Time: 7:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class SimpleSymbolChildNode extends AbstractSymbolChildNode<PropertyDefinition> {

    public SimpleSymbolChildNode(PropertyDefinition wrappedEntity) {
        super(wrappedEntity);
        setAlias(wrappedEntity.getName());
        setType(wrappedEntity.getType().getName());
        
        List<String> domainModelPaths = new ArrayList<String>();
        EList<DomainInstance> domainInstances = wrappedEntity.getDomainInstances();
        if (domainInstances != null && domainInstances.size() > 0) {
        	for (DomainInstance dInstance : domainInstances) {
        		domainModelPaths.add(dInstance.getResourcePath());
        	}
        }
        if (domainModelPaths.size() > 0) {
        	setDomainModelPath(domainModelPaths.toArray(new String[domainModelPaths.size()]));
        }
    }
}
