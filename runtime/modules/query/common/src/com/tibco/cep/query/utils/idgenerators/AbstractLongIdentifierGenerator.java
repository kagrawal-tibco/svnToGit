package com.tibco.cep.query.utils.idgenerators;



/**
 * Abstract superclass for LongIdentifierGenerator implementations.
 *
 * @author Commons-Id team
 * @version $Id$
 */
public abstract class AbstractLongIdentifierGenerator implements LongIdentifierGenerator {

    /**
     * Constructor.
     */
    protected AbstractLongIdentifierGenerator() {
        super();
    }

    /**
     * Returns the maximum value of an identifier from this generator.
     *
     * <p>The default implementation returns Long.MAX_VALUE. Implementations
     * whose identifiers are bounded below Long.MAX_VALUE should override this method to
     * return the maximum value of a generated identifier.</p>
     *
     * @return {@inheritDoc}
     */
    public long maxValue() {
        return Long.MAX_VALUE;
    }

    /**
     * Returns the minimum value of an identifier from this generator.
     *
     * <p>The default implementation returns Long.MIN_VALUE. Implementations
     * whose identifiers are bounded above Long.MIN_VALUE should override this method to
     * return the minimum value of a generated identifier.</p>
     *
     * @return {@inheritDoc}
     */
    public long minValue() {
        return Long.MIN_VALUE;
    }

    public Object nextIdentifier() {
        return this.nextLongIdentifier();
    }

    public abstract Long nextLongIdentifier();
}
