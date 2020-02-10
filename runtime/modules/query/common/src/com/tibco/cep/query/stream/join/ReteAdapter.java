package com.tibco.cep.query.stream.join;

import java.util.Collection;

import com.tibco.cep.query.stream.monitor.ResourceId;

/*
 * Author: Ashwin Jayaprakash Date: Oct 19, 2007 Time: 12:56:53 PM
 */

public interface ReteAdapter {
    public ResourceId getResourceId();

    public void assertObject(Object object, boolean executeRules) throws Exception;

    public void assertObjects(Collection<? extends Object> object, boolean executeRules)
            throws Exception;

    public void modifyObject(Object object, boolean executeRules) throws Exception;

    public void modifyObjects(Collection<? extends Object> object, boolean executeRules)
            throws Exception;

    public void retractObject(Object object, boolean executeRules) throws Exception;

    public void retractObjects(Collection<? extends Object> object, boolean executeRules)
            throws Exception;
}
