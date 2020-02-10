package com.tibco.cep.kernel.service;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Sep 27, 2006
 * Time: 2:09:16 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IdGenerator {

    public long nextEntityId();

    public long lastEntityId();

    public void setMinEntityId(long min);

    public void setMaxEntityId(long max);

    /**
     * this is used when type-id is encoded in the id that is returned. for use with "no objecttable"
     */
    public long nextEntityId(Class clz);
}
