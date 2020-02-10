package com.tibco.cep.query.stream.impl.rete;

import java.util.Collection;
import java.util.LinkedList;

import com.tibco.cep.query.stream.core.ControllableResource;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.runtime.service.loader.BEClassLoader;

/*
* Author: Ashwin Jayaprakash Date: Mar 26, 2008 Time: 5:07:34 PM
*/
public class ReteEntityClassHierarchyHelper implements ControllableResource {
    protected BEClassLoader beClassLoader;

    protected ResourceId resourceId;

    public ReteEntityClassHierarchyHelper(BEClassLoader beClassLoader) {
        this.beClassLoader = beClassLoader;
        this.resourceId = new ResourceId(getClass().getName());
    }

    public ResourceId getResourceId() {
        return resourceId;
    }

    public void start() {
    }

    public void stop() {
        beClassLoader = null;

        resourceId.discard();
        resourceId = null;
    }

    //----------

    /**
     * @param reteEntityClass
     * @return All sub-classes including the Class in the parameter.
     */
    public Class[] getSubClasses(Class reteEntityClass) {
        LinkedList<Class> result = new LinkedList<Class>();

        Collection classes = beClassLoader.getClasses();
        for (Object obj : classes) {
            Class klass = (Class) obj;

            if (reteEntityClass.isAssignableFrom(klass)) {
                result.add(klass);
            }
        }

        Class[] array = result.toArray(new Class[result.size()]);
        return array;
    }
}
