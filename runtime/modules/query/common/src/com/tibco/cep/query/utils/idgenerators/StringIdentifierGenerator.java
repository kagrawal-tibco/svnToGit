package com.tibco.cep.query.utils.idgenerators;

/**
 * <code>StringIdentifierGenerator</code> defines a simple interface for
 * String based identifier generation.
 *
 * @author Commons-Id team
 * @version $Id$
 */
public interface StringIdentifierGenerator extends IdentifierGenerator {

    /**
     * Constant representing unlimited identifier length, returned by {@link #maxLength()}
     * when there is no upper bound to the length of an identifier in the sequence
     */
    public static final int INFINITE_MAX_LENGTH = -1;

    /**
     * Gets the next identifier in the sequence.
     *
     * @return the next String identifier in sequence
     */
    String nextStringIdentifier();

    /**
     * Returns the maximum length (number or characters) for an identifier
     * from this sequence.
     *
     * @return the maximum identifier length, or {@link #INFINITE_MAX_LENGTH} if there is no upper bound
     */
    long maxLength();

    /**
     * Returns the minimum length (number of characters) for an identifier
     * from this sequence.
     *
     * @return the minimum identifier length
     */
    long minLength();
}
