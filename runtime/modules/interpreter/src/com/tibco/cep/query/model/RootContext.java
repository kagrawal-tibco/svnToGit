/**
 * 
 */
package com.tibco.cep.query.model;

import com.tibco.be.util.idgenerators.IdentifierGenerator;


/**
 * @author pdhar
 */
public interface RootContext extends ModelContext {
    
    public static final ModelContext PARENT_CONTEXT = null;

    /**
     * Returns the ID generator
     * @return IdentifierGenerator
     */
    IdentifierGenerator getIdGenerator();
}
