package com.tibco.cep.query.model.impl;

import org.antlr.runtime.tree.CommonTree;

import com.tibco.be.util.idgenerators.IdentifierGenerator;
import com.tibco.be.util.idgenerators.serial.PrefixedAlphanumericGenerator;
import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.NamedContextId;
import com.tibco.cep.query.model.NamedSelectContext;
import com.tibco.cep.query.model.ProjectContext;
import com.tibco.cep.query.model.QueryContext;
import com.tibco.cep.query.model.SelectContext;

public class NamedSelectContextImpl
        extends SelectContextImpl
        implements NamedSelectContext {

    protected PrefixedAlphanumericGenerator m_prefixedGenerator;
    private static final String ID_GEN_PREFIX = "ALIAS" ;

//    public NamedSelectContextImpl(QueryServiceProvider qsp,boolean isDistinct, int line, int pos) throws Exception {
//        super(NamedSelectContext.PARENT_CONTEXT, null, isDistinct, false, line, pos);
//        this.projectContext = (null == qsp) ? null : qsp.getProjectContext();
//    }


    public NamedSelectContextImpl(ProjectContext projectContext, String alias, CommonTree node) throws Exception {
        super(NamedSelectContext.PARENT_CONTEXT, alias, false, node);
        this.projectContext = projectContext;
    }

    /**
     * Returns the name of the named context
     * @return String
     */
    public NamedContextId getContextId() {
        return CTX_ID;
    }

    public String toString() {
        return getContextId().toString();
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


    public ModelContext getModelContextByAlias(String alias) {
        final ModelContext[] children = this.getDescendantContextsByType(ModelContext.CTX_TYPE_SELECT);
        for (int i=0; i<children.length; i++) {
            final SelectContext select = (SelectContext) children[i];
            final ModelContext ctx = select.getContextMap().get(alias);
            if (null != ctx) {
                return ctx;
            }
        }
        return null;
    }

    public ModelContext getNameContextById(NamedContextId Id) {
        return getRootContext().getContextMap().get(Id);
    }

    public QueryContext getContext() {
        return this;
    }
}
