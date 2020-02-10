package com.tibco.be.util.idgenerators;

/**
 * <code>LongIdentifier</code> defines a simple interface for
 * Long based identifier generation.
 *
 * @author Commons-Id team
 * @version $Id$
 */
public interface LongIdentifierGenerator extends IdentifierGenerator {

    /**
     * Gets the next identifier in the sequence.
     *
     * @return the next Long identifier in sequence
     */
    Long nextLongIdentifier();

    /**
     * Returns the maximum value of an identifier from this generator.
     *
     * @return the maximum identifier value
     */
    long maxValue();

    /**
     * Returns the minimum value of an identifier from this generator.
     *
     * @return the minimum identifier value
     */
    long minValue();

}
