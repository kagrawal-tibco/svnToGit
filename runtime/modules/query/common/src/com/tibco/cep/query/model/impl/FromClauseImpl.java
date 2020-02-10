package com.tibco.cep.query.model.impl;

import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;

import com.tibco.cep.query.model.Aliased;
import com.tibco.cep.query.model.AliasedIdentifier;
import com.tibco.cep.query.model.Entity;
import com.tibco.cep.query.model.FromClause;
import com.tibco.cep.query.model.Identifier;
import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.NamedContextId;

public class FromClauseImpl extends AbstractQueryContext implements FromClause {

    public FromClauseImpl(ModelContext context, CommonTree tree) {
        super(context, tree);
    }


    /**
     * Returns the name of the named context
     * 
     * @return String
     */
    public NamedContextId getContextId() {
        return CTX_ID;
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.cep.query.ModelContext#getContextType()
     */
    public int getContextType() {
        return ModelContext.CTX_TYPE_FROM;
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.cep.query.FromClause#getAlias(java.lang.String)
     */
    public String getAliasByEntityPath(String path) throws Exception {
        for (ModelContext context : this.getDirectDescendantContextsByType(ModelContext.CTX_TYPE_IDENTIFIER)) {
            final AliasedIdentifier id = (AliasedIdentifier) context;
            if (id.getName().equals(path)) {
                return id.getAlias();
            }
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.cep.query.FromClause#getEntity(java.lang.String)
     */
    public String getEntityPathByAlias(String alias) throws Exception {
        for (ModelContext context : this.getChildren()) {
            final Aliased id = (Aliased) context;
            if (id.getAlias().equals(alias) && id instanceof Identifier) {
                final Identifier idf = (Identifier) id;
                if (idf instanceof Entity) {
                    return idf.getName();
                }
            }
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.cep.query.ast.FromClause#getEntity(java.lang.String)
     */
    public Entity getEntityByAlias(String alias) throws Exception {
        for (ModelContext context : this.getChildren()) {
            if (context instanceof Aliased) {
                final Aliased id = (Aliased) context;
                if (id.getAlias().equals(alias)
                        && (id instanceof Identifier)
                        && (((Identifier) id).getIdentifiedContext() instanceof Entity)) {
                    return (Entity) ((Identifier) id).getIdentifiedContext();
                }
            }
        }
        return null;
    }

    public Aliased getAliasedByAlias(String alias) throws Exception {
        for (ModelContext context : this.getChildren()) {
            if (((Aliased) context).getAlias().equals(alias)) {
                return (Aliased) context;
            }
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.cep.query.FromClause#containsAlias(java.lang.String)
     */
    public boolean containsAlias(String alias) throws Exception {
        for (ModelContext context : this.getChildren()) {
            if (context instanceof Aliased) {
                final Aliased id = (Aliased) context;
                if (id.getAlias().equals(alias))
                    return true;
            }
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.cep.query.FromClause#containsEntity(java.lang.String)
     */
    public boolean containsEntity(String entityPath) throws Exception {
        for (ModelContext context : this.getDirectDescendantContextsByType(ModelContext.CTX_TYPE_IDENTIFIER)) {
            final ModelContext c = ((AliasedIdentifier) context).getIdentifiedContext();
            if ((c instanceof Entity) && ((Entity) c).getEntityPath().equals(entityPath)) {
                return true;
            }
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.cep.query.FromClause#getAllAliases()
     */
    public String[] getAllEntityAliases(boolean includePseudoAliases) throws Exception {
        final List<String> aliases = new ArrayList<String>();
        for (ModelContext aQc : this.getDirectDescendantContextsByType(ModelContext.CTX_TYPE_IDENTIFIER)) {
            final AliasedIdentifier ai = (AliasedIdentifier) aQc;
            if (ai.getIdentifiedContext() instanceof Entity) {
                if(ai.isPseudoAliased() && !includePseudoAliases ){
                    continue;
                }
                aliases.add(ai.getAlias());
            }
        }
        return aliases.toArray(new String[aliases.size()]);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.cep.query.FromClause#getAllEntities()
     */
    public String[] getAllEntityPaths() throws Exception {
        final List<String> entities = new ArrayList<String>();
        for (ModelContext aQc : this.getDirectDescendantContextsByType(ModelContext.CTX_TYPE_IDENTIFIER)) {
            AliasedIdentifier ai = (AliasedIdentifier) aQc;
            if (ai.getIdentifiedContext() instanceof Entity) {
                entities.add(((Entity) ai.getIdentifiedContext()).getEntityPath());
            }
        }
        return entities.toArray(new String[entities.size()]);
    }
}
