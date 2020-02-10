package com.tibco.cep.vcs;

/**
 * User: nprade
 * Date: 6/27/11
 * Time: 4:24 PM
 */
public interface VcsConnection {

    @SuppressWarnings({"RedundantThrows"})
    void close() throws Exception;

}
