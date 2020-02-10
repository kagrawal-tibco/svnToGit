package com.tibco.cep.query.stream.impl.monitor.model;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Jul 25, 2008
 * Time: 5:00:39 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Info {

    //all info impls implement this interface
    public void register() throws Exception;
    public void unregister() throws Exception;
    public void refresh() throws Exception;
}
