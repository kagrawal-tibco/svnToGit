package com.tibco.be.util.idgenerators;

/**
 * Abstract superclass for StringIdentifierGenerator implementations.
 *
 * @author Commons-Id team
 * @version $Id$
 */
public abstract class AbstractStringIdentifierGenerator implements StringIdentifierGenerator {

    /**
     * Maximum length for a numeric string representing a long value.
     */
    protected static final int MAX_LONG_NUMERIC_VALUE_LENGTH =
    Long.toString(Long.MIN_VALUE).length();

    /**
     * Number of alphanumeric characters.
     */
    protected static final int ALPHA_NUMERIC_CHARSET_SIZE = 36;

    /**
     * Maximum length for an alphanumeric string representing a long value.
     */
    protected static final int MAX_LONG_ALPHANUMERIC_VALUE_LENGTH =
    Long.toString(Long.MAX_VALUE, com.tibco.be.util.idgenerators.AbstractStringIdentifierGenerator.ALPHA_NUMERIC_CHARSET_SIZE).length();

    /**
     * Maximum length for a numeric string representing an integer value.
     */
    protected static final int MAX_INT_NUMERIC_VALUE_LENGTH =
    Integer.toString(Integer.MIN_VALUE).length();

    /**
     * Maximum length for an alphanumeric string representing an integer value.
     */
    protected static final int MAX_INT_ALPHANUMERIC_VALUE_LENGTH =
    Integer.toString(Integer.MAX_VALUE, com.tibco.be.util.idgenerators.AbstractStringIdentifierGenerator.ALPHA_NUMERIC_CHARSET_SIZE).length();

    /**
     * Default size of an alphanumeric identifier.
     */
    protected static final int DEFAULT_ALPHANUMERIC_IDENTIFIER_SIZE = 15;

    /**
     * Constructor.
     */
    protected AbstractStringIdentifierGenerator() {
        super();
    }

    public abstract String nextStringIdentifier();

    /**
     * Returns the maximum length (number or characters) for an identifier
     * from this sequence.
     *
     * <p>The default implementation returns {@link #INFINITE_MAX_LENGTH}. Implementations
     * with bounded length identifiers should override this method to
     * return the maximum length of a generated identifier.</p>
     *
     * @return {@inheritDoc}
     */
    public long maxLength() {
        return INFINITE_MAX_LENGTH;
    }

    /**
     * Returns the minimum length (number of characters) for an identifier
     * from this sequence.
     *
     * <p>The default implementation returns 0. Implementations
     * with identifiers having a postive minimum length should override this
     * method to return the maximum length of a generated identifier.</p>
     *
     * @return {@inheritDoc}
     */
    public long minLength() {
        return 0;
    }

    public Object nextIdentifier() {
        return nextStringIdentifier();
    }
}
