/**
 * 
 */
package com.tibco.cep.query.model.impl;

import org.antlr.runtime.tree.CommonTree;

import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.Projection;

/**
 * @author pdhar
 *
 */
public class ProjectionImpl extends AbstractProjection implements Projection {

    /**
     * @param parentContext
     * @param tree
     */
    public ProjectionImpl(ModelContext parentContext, CommonTree tree) {
        super(parentContext, tree);
    }


    /* (non-Javadoc)
      * @see com.tibco.cep.query.ast.impl.AbstractModelContext#resolveContext()
      */
    public boolean resolveContext() throws Exception {
        return true;
    }

}
