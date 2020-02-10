package com.tibco.be.oracle;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Feb 27, 2009
 * Time: 1:25:57 PM
 * To change this template use File | Settings | File Templates.
 */
public interface OraclePoolConnectionListener {
    void reconnected();
    void switched(boolean toSecondary);
}
