package com.tibco.cep.query.model.impl;


import com.tibco.be.util.idgenerators.IdentifierGenerator;
import com.tibco.be.util.idgenerators.serial.PrefixedAlphanumericGenerator;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.query.model.EntityRegistry;
import com.tibco.cep.query.model.FunctionRegistry;
import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.ProjectContext;
import com.tibco.cep.query.model.visitor.HierarchicalContextVisitor;
import com.tibco.cep.query.model.visitor.impl.ContextResolutionVisitor;
import com.tibco.cep.runtime.session.RuleServiceProvider;

import java.util.HashMap;
import java.util.Map;

/*
 * User: nprade
 * Date: Aug 29, 2007
 * Time: 5:21:24 PM
 * To change this template use File | Settings | File Templates.
 */

public class ProjectContextImpl
        extends AbstractModelContext
        implements ProjectContext {

    private RuleServiceProvider rsp;
    private FunctionRegistryImpl functionRegistry;
    private Map<Object,  ModelContext> contextMap = new HashMap<Object,  ModelContext>();
    protected PrefixedAlphanumericGenerator m_prefixedGenerator;
    private static final String ID_GEN_PREFIX = "$PC$";
    private EntityRegistry entityRegistry;


    public ProjectContextImpl(
            RuleServiceProvider rsp)
            throws Exception{
        this(rsp, true);
    }


    public ProjectContextImpl(
            RuleServiceProvider rsp,
            boolean isQueryContext)
            throws Exception {

        super(null);

        this.rsp = rsp;

        this.functionRegistry = new FunctionRegistryImpl(this, null);
        if (isQueryContext) {
            this.functionRegistry.accept(new ContextResolutionVisitor(HierarchicalContextVisitor.VISIT_CHILDREN));
        } else {
            this.functionRegistry.resolveContext(false);
        }

        this.entityRegistry = new EntityRegistryImpl(this, null);
        this.entityRegistry.accept(new ContextResolutionVisitor(HierarchicalContextVisitor.VISIT_CHILDREN));
    }


    /**
     * @return null
     */
    public Map<Object,ModelContext> getContextMap() {
        return this.contextMap;
    }


    /**
     * @return int ModelContext.CTX_TYPE_PROJECT
     */
    public int getContextType() {
        return ModelContext.CTX_TYPE_PROJECT;
    }


    public EntityRegistry getEntityRegistry() {
        return this.entityRegistry;
    }

    public FunctionRegistry getFunctionRegistry() {
        return this.functionRegistry;
    }


    public Ontology getOntology() {
        return rsp.getProject().getOntology();
    }


    public ProjectContext getProjectContext() {
        return this;
    }


    public RuleServiceProvider getRuleServiceProvider() {
        return rsp;
    }


    /**
     * Returns the logger
     *
     * @return Logger
     */
    public Logger getLogger() {
        return this.logger;
    }

    /**
     * Returns the ID generator
     *
     * @return IdentifierGenerator
     */
    public IdentifierGenerator getIdGenerator() {
       if(m_prefixedGenerator == null) {
            m_prefixedGenerator = new PrefixedAlphanumericGenerator(ID_GEN_PREFIX,false,ID_GEN_PREFIX.length()+8);
        }
        return m_prefixedGenerator;
    }


    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (! (o instanceof ProjectContextImpl)) {
            return false;
        }
        if (this.getClass().isAssignableFrom(o.getClass()) && !this.getClass().equals(o.getClass())) {
            return o.equals(this); // Delegates to most specific class.
        }
        final ProjectContextImpl that = (ProjectContextImpl) o;
        return (this.rsp == that.rsp)
                && (this.contextMap.equals(that.contextMap))
                && (this.functionRegistry.equals(that.functionRegistry))
                && (this.entityRegistry.equals(that.entityRegistry));
    }


    public int hashCode() {
        long longHash = this.rsp.hashCode();
        longHash = longHash * 29 + this.contextMap.hashCode();
        longHash ^= (longHash >>> 32);
        longHash = longHash * 29 + this.functionRegistry.hashCode();
        longHash ^= (longHash >>> 32);
        longHash = longHash * 29 + this.entityRegistry.hashCode();
        longHash ^= (longHash >>> 32);
        return (int) longHash;
    }
}
