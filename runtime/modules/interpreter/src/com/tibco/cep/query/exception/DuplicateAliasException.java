package com.tibco.cep.query.exception;

import com.tibco.cep.query.model.ModelContext;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Jun 11, 2007
 * Time: 8:34:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class DuplicateAliasException extends SemanticException {

    /**
     * Constructs a new exception with <code>null</code> as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */

    public DuplicateAliasException(ModelContext qc) {
        super(qc,"DuplicateAliasException.0");
      // super(Messages.getString("DuplicateAliasException.0"),line); //$NON-NLS-1$
    }

}
