package com.tibco.cep.query.model.impl;


import org.antlr.runtime.tree.CommonTree;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.query.exception.ResolveException;
import com.tibco.cep.query.model.Aliased;
import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.PathIdentifier;
import com.tibco.cep.query.model.resolution.ResolutionHelper;

/**
 * IdentifierImpl representing a path.
 */
public class PathIdentifierImpl
        extends IdentifierImpl implements PathIdentifier {


    public PathIdentifierImpl(ModelContext parentContext, CommonTree tree, String name) {
        super(parentContext, tree, name);
    }

    public boolean resolveContext() throws Exception {
        final Aliased ctx = ResolutionHelper.lookupAliasedByEntityPath(this, this.getName());
        if (null == ctx) {
            throw new ResolveException("Could not find scoped entity for path: " + this.getName());
        }
        this.setIdentifiedContext((ModelContext) ctx);
        if (this.isResolved()) {
            this.logger.log(Level.DEBUG, "Resolved PathIdentifier: %s -> %s",
                    this.getName(), this.getIdentifiedContext());
            return true;
        } else {
            this.logger.log(Level.DEBUG, "Failed to resolved PathIdentifier: %s", this.getName());
            return false;
        }
    }



}
