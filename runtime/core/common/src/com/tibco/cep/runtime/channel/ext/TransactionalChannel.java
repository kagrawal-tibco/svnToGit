package com.tibco.cep.runtime.channel.ext;

import com.tibco.cep.runtime.channel.Channel;

/**
 *
 * TODO this is never used
 *
 * @version 2.0
 * @since 2.0
 */
public interface TransactionalChannel extends Channel {


     /**
      * Commits the current transaction.
      *
      * TODO when is the "current" transaction started?
      *
     * @throws Exception TODO what is thrown when?
     */
    public void commitTransaction() throws Exception;

    /**
     * Rolls back the current transaction.
     *
     * TODO see above
     *
     * @throws Exception TODO what is thrown when?
     */
    public void rollbackTransaction() throws Exception;

}
