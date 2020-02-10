package com.tibco.be.util.idgenerators.serial;

/**
 * <code>PrefixedNumericGenerator</code> is an Identifier Generator
 * that generates an incrementing number with a prefix as a String object.
 *
 * <p>If the <code>wrap</code> argument passed to the constructor is set to
 * <code>true</code>, the sequence will wrap, returning negative values when
 * {@link Long#MAX_VALUE} reached; otherwise an {@link IllegalStateException}
 * will be thrown.</p>
 *
 * @author Commons-Id team
 * @version $Id$
 */
public class PrefixedNumericGenerator extends NumericGenerator {

    /** Prefix. */
    private final String prefix;


    /**
     * Create a new prefixed numeric generator with the specified prefix.
     *
     * @param prefix prefix, must not be null
     * @param wrap should the factory wrap when it reaches the maximum
     *  long value (or throw an exception)
     * @param initialValue the initial long value to start at
     * @throws NullPointerException if prefix is <code>null</code>
     */
    public PrefixedNumericGenerator(String prefix, boolean wrap, long initialValue) {
        super(wrap, initialValue);

        if (prefix == null) {
            throw new NullPointerException("prefix must not be null");
        }
        this.prefix = prefix;
    }


    /**
     * Return the prefix for this prefixed numeric generator.
     *
     * @return the prefix for this prefixed numeric generator
     */
    public String getPrefix() {
        return prefix;
    }

    public long maxLength() {
        return super.maxLength() + prefix.length();
    }

    public long minLength() {
        return super.minLength() + prefix.length();
    }

    public String nextStringIdentifier() {
        StringBuffer sb = new StringBuffer(prefix);
        sb.append(super.nextStringIdentifier());
        return sb.toString();
    }
}
