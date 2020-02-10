package com.tibco.be.util.idgenerators;


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
    Object nextIdentifier();
}
