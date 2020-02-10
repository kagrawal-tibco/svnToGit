package com.tibco.cep.query.stream.cache;

import com.tibco.cep.query.stream.core.ControllableResource;

/*
 * Author: Ashwin Jayaprakash Date: Mar 17, 2008 Time: 7:43:14 PM
 */

public interface SharedObjectSourceRepository extends ControllableResource {
    public SharedObjectSource getDefaultSource();

    public SharedObjectSource getSource(String name);

    public void purgeObject(Object key, String sourceName);

    public void softPurgeObject(Object key, String sourceName);

    public Cache getDeadPoolCache();

    public Cache getPrimaryCache();
}
