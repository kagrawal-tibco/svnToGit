package com.tibco.cep.query.model.impl;


import java.util.Iterator;

import org.antlr.runtime.tree.CommonTree;

import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.RuleParticipant;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.query.model.Entity;
import com.tibco.cep.query.model.EntityRegistry;
import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.NamedContextId;
import com.tibco.cep.query.model.ProjectContext;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Oct 24, 2007
 * Time: 12:18:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class EntityRegistryImpl
        extends AbstractRegistryContext implements EntityRegistry {

    public static NamedContextId CTX_ID = new NamedContextId() {
        public String toString() { return "ENTITY_REGISTRY"; }
    };
    public static final String PATH_SEPARATOR = "/";

    protected boolean isResolved;


    /**
     * ctor
     *
     * @param projectContext
     * @param tree
     */
    protected EntityRegistryImpl(ProjectContext projectContext, CommonTree tree) {
        super(projectContext);
    }


    /**
     * Returns the name of the named context
     *
     * @return String
     */
    public NamedContextId getContextId() {
        return CTX_ID;
    }

    /**
     * @return the context type
     */
    public int getContextType() {
        return ModelContext.CTX_TYPE_ENTITY_REGISTRY;
    }

    /**
     * Returns an array of Entity's.
     *
     * @return Entity[]
     */
    public Entity[] getEntities() {
        final ModelContext[] descendants = this.getDescendantContextsByType(this, ModelContext.CTX_TYPE_ENTITY);
        final int length = descendants.length;
        final Entity[] entities = new Entity[length];
        for (int i=0; i<length; i++) {
            entities[i] = (Entity) descendants[i];
        }
        return entities;
    }

    /**
     * Looks up an Entity by path.
     *
     * @param path String path of the Entity.
     * @return Entity found at the path, or null if not found.
     */
    public Entity getEntityByPath(String path) {
        return (Entity) this.getContextMap().get(path);
    }

    /**
     * Resolves a context by matching known BE objects to unknown identifiers
     *
     * @return boolean true if resolved else false
     * @throws Exception
     */
    public boolean resolveContext() throws Exception {
        this.getContextMap().clear();
        final Ontology ontology = this.getProjectContext().getOntology();
        int count = 0;
        for (Iterator it = ontology.getEntities().iterator(); it.hasNext();) {
            final com.tibco.cep.designtime.model.Entity ontologyEntity =
                    (com.tibco.cep.designtime.model.Entity) it.next();

            if (ontologyEntity instanceof RuleParticipant) {
                final String path = ontologyEntity.getFullPath();
                final EntityImpl e = new EntityImpl(this, path, null);

                this.getContextMap().put(path, e);
                count++;
            }
        }
        this.logger.log(Level.DEBUG, "Registered %d entities", count);
        this.isResolved = true;
        return this.isResolved();
    }

    /**
     * Returns true if the context has been resolved or false
     *
     * @return boolean
     */
    public boolean isResolved() {
        return this.isResolved;
    }
}
