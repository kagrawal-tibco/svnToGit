package com.tibco.cep.vcs;

/**
 * User: nprade
 * Date: 6/30/11
 * Time: 3:14 PM
 */
public interface VcsContext {

    String getConnectionUrl();

    VcsConnection makeConnection() throws Exception;

}
