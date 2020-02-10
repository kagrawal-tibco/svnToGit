package com.tibco.cep.kernel.model.knowledgebase;

import com.tibco.cep.kernel.core.base.EntitySharingLevel;

/**
 * @version 2.0.0
 * @since 2.0.0
 */
public interface TypeInfo {

    /**
     * Get the Class for this TypeInfo
     * @return the Class for this TypeInfo
     */
    Class getType();

    /**
     * Tell whether this type has any rule associate to it
     * @return true if there is rule associated to it, false otherwise
     */
    boolean hasRule();

    /**
     * Specify which WorkingMemory this TypeInfo is associated to.
     * @return  the WorkingMemory it is assoicated to.
     */
    WorkingMemory getWorkingMemory();
    
    //static sharing level of the type.  May be SHARED or UNSHARED
    EntitySharingLevel getLocalSharingLevel();
    void setLocalSharingLevel(EntitySharingLevel lev);
    //static combined sharing level of the type and its subtypes.  May be be SHARED, UNSHARED or MIXED
    EntitySharingLevel getRecursiveSharingLevel();
    void setRecursiveSharingLevel(EntitySharingLevel lev);
}
