package com.tibco.cep.kernel.service;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Sep 4, 2008
 * Time: 10:53:47 AM
 * To change this template use File | Settings | File Templates.
 */
public interface NamedIdGenerator extends IdGenerator {
    String getName();

    void remove();
}
