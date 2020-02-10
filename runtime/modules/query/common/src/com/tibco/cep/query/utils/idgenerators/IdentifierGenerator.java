package com.tibco.cep.query.utils.idgenerators;

import com.tibco.cep.query.exception.DuplicateAliasException;

/**
 * <code>IdentifierGenerator</code> defines a simple interface for
 * identifier generation.
 *
 * @author Commons-Id team
 * @version $Id$
 */
public interface IdentifierGenerator {

    /**
     * Gets the next identifier in the sequence.
     *
     * @return the next identifier in sequence
     */
    Object nextIdentifier() throws DuplicateAliasException;
}
