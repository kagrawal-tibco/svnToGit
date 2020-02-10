package com.tibco.be.migration.expimp.providers.cache;

import java.util.Properties;

import com.tangosol.io.ExternalizableLite;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.runtime.session.RuleServiceProvider;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Feb 21, 2008
 * Time: 10:53:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConceptStore extends EntityStore {

    public ConceptStore(RuleServiceProvider rsp, String masterCacheName, Entity entity, Properties cacheConfig) throws Exception {
        super(rsp, masterCacheName, entity, cacheConfig);
    }

    public ConceptStore(RuleServiceProvider rsp, String masterCacheName, Class implclass, Properties cacheConfig) throws Exception {
        super(rsp, masterCacheName, implclass, cacheConfig);
    }


    public void put(com.tibco.cep.runtime.model.element.Concept instance) {
        Thread.currentThread().setContextClassLoader((ClassLoader) getTypeManager());
        this.m_addedObjects.put(new Long(instance.getId()), (ExternalizableLite) instance);

    }


    public com.tibco.cep.runtime.model.element.Concept getConcept(long id) {
        Thread.currentThread().setContextClassLoader((ClassLoader) getTypeManager());
        return (com.tibco.cep.runtime.model.element.Concept) entityCache.get(new Long(id));
    }


    public void remove(long id) {
        Thread.currentThread().setContextClassLoader((ClassLoader) getTypeManager());
        this.m_deletedObjects.add(new Long(id));
    }

    Concept  getConceptModel() {
        return (Concept) getModelEntity();
    }

}
