package com.tibco.cep.runtime.model.element;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Apr 21, 2008
 * Time: 2:30:54 PM
 * To change this template use File | Settings | File Templates.
 */
public interface VersionedObject {

    void incrementVersion();
    Comparable getVersionIndicator();
    int getVersion();
}
