package com.tibco.cep.runtime.managed;

import com.tibco.cep.kernel.model.entity.Mutable;
import com.tibco.cep.runtime.model.element.VersionedObject;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.impl.EntityImpl;

/*
* Author: Ashwin Jayaprakash / Date: 1/12/12 / Time: 5:32 PM
*/
public class SpiHelper {
    public static final int DEFAULT_VERSION = 1;

    public static final boolean DEFAULT_MODIFIED = false;

    public static final boolean DEFAULT_DELETED = false;

    public static int extractVersion(EntityImpl entity) {
        return (entity instanceof VersionedObject) ? ((VersionedObject) entity).getVersion() : DEFAULT_VERSION;
    }

    public static boolean checkIfDeleted(Mutable entity) {
        return ((ConceptImpl) entity).isDeleted();
    }

    public static boolean checkIfModified(Mutable entity) {
        return ((ConceptImpl) entity).isModified();
    }
}
