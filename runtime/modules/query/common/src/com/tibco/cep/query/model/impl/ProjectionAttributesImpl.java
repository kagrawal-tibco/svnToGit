package com.tibco.cep.query.model.impl;

import java.util.ArrayList;
import java.util.Iterator;

import org.antlr.runtime.tree.CommonTree;

import com.tibco.cep.query.model.Aliased;
import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.NamedContextId;
import com.tibco.cep.query.model.Projection;
import com.tibco.cep.query.model.ProjectionAttributes;
import com.tibco.cep.query.model.validation.validators.ProjectionAttributesValidator;

/**
 * @author pdhar
 *
 */
public class ProjectionAttributesImpl extends AbstractQueryContext
        implements ProjectionAttributes {



    /**
     * @param context
     */
    public ProjectionAttributesImpl(ModelContext context, CommonTree tree) {
        super(context, tree);
    }


    /* (non-Javadoc)
      * @see com.tibco.cep.query.ModelContext#getContextType()
      */
    public int getContextType() {
        return ModelContext.CTX_TYPE_PROJECTION_ATTRIBUTES;
    }

    /**
     * Returns the name of the named context
     * @return String
     */
    public NamedContextId getContextId() {
        return CTX_ID;
    }



    /* (non-Javadoc)
      * @see com.tibco.cep.query.ProjectionAttributes#getAllNamedAliases()
      */
    public Aliased[] getAllNamedAliases() {
        final ArrayList l = new ArrayList();
        for (Iterator it = this.getChildrenIterator(); it.hasNext(); ) {
            final Aliased a = (Aliased) it.next();
            if (!a.isPseudoAliased()) {
                l.add(a);
            }
        }
        return (Aliased[]) l.toArray(new Aliased[l.size()]);
    }


    /* (non-Javadoc)
      * @see com.tibco.cep.query.ProjectionAttributes#getAllPseudoAliases()
      */
    public Aliased[] getAllPseudoAliases() {
        final ArrayList l = new ArrayList();
        for (Iterator it = this.getChildrenIterator(); it.hasNext(); ) {
            final Aliased a = (Aliased) it.next();
            if (a.isPseudoAliased()) {
                l.add(a);
            }
        }
        return (Aliased[]) l.toArray(new Aliased[l.size()]);
    }


    /* (non-Javadoc)
      * @see com.tibco.cep.query.ProjectionAttributes#getProjectionContextIterator()
      */
    public Iterator getProjectionIterator() {
        return childContext.iterator();
    }


    /* (non-Javadoc)
      * @see com.tibco.cep.query.ProjectionAttributes#getAllProjectionContexts()
      */
    public Projection[] getAllProjections() {
        return childContext.toArray(new Projection[childContext.size()]);
    }


    /* (non-Javadoc)
      * @see com.tibco.cep.query.ProjectionAttributes#getProjectionElementContext(java.lang.String)
      */
    public Projection getProjection(String alias) {
        if (null != alias) {
            for (Iterator it = this.getChildrenIterator(); it.hasNext(); ) {
                final Projection p = (Projection) it.next();
                if (alias.equals(p.getAlias())) {
                    return p;
                }
            }
        }
        return null;
    }


    /* (non-Javadoc)
      * @see com.tibco.cep.query.ProjectionAttributes#getProjectionElementContextIterator()
      */
    public Iterator getProjectionElementIterator() {
        return this.getChildrenIterator();
    }


    /* (non-Javadoc)
      * @see com.tibco.cep.query.ProjectionAttributes#getProjectionElementCount()
      */
    public int getProjectionElementCount() {
        return this.getChildCount();
    }

    protected Projection[] getProjectionContexts() {
        return (Projection[]) getChildren();
    }

    public void validate() throws Exception {
        new ProjectionAttributesValidator().validate(this);
    }


}
