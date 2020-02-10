package com.tibco.cep.common.resource;

/*
* Author: Ashwin Jayaprakash Date: Jul 13, 2009 Time: 1:22:22 PM
*/

public interface ResourceProvider {
    <R extends Resource> R fetchResource(Class<R> type);

    <R extends Resource> R fetchResource(Class<R> type, Id id);

    //-----------

    /**
     * @param type
     * @param resource
     * @return <code>false</code> if there is already an existing resource.
     */
    <R extends Resource> boolean registerResource(Class<R> type, R resource);

    /**
     * @param id
     * @param resource
     * @return <code>false</code> if there is already an existing resource.
     */
    boolean registerResource(Id id, Resource resource);

    //-----------

    <R extends Resource> R unregisterResource(Class<R> type);

    Resource unregisterResource(Id id);

    //-----------

    void discard();
}
