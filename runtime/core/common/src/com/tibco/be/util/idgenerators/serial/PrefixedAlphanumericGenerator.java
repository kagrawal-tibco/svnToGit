package com.tibco.be.util.idgenerators.serial;

/**
 * <code>PrefixedAlphanumericGenerator</code> is an identifier generator
 * that generates an incrementing number in base 36 with a prefix as a String
 * object.
 *
 * <p>All generated ids have the same length (prefixed and padded with 0's
 * on the left), which is determined by the <code>size</code> parameter passed
 * to the constructor.<p>
 *
 * <p>The <code>wrap</code> property determines whether or not the sequence wraps
 * when it reaches the largest value that can be represented in <code>size</code>
 * base 36 digits. If <code>wrap</code> is false and the the maximum representable
 * value is exceeded, an {@link IllegalStateException} is thrown.</p>
 *
 * @author Commons-Id team
 * @version $Id$
 */
public class PrefixedAlphanumericGenerator extends AlphanumericGenerator {

    /** Prefix. */
    private final String prefix;


    /**
     * Create a new prefixed alphanumeric generator with the specified prefix.
     *
     * @param prefix prefix, must not be null
     * @param wrap should the factory wrap when it reaches the maximum
     *  value that can be represented in <code>size</code> base 36 digits
     *  (or throw an exception)
     * @param size the size of the identifier, including prefix length
     * @throws IllegalArgumentException if size less prefix length is not at least one
     * @throws NullPointerException if prefix is <code>null</code>
     */
    public PrefixedAlphanumericGenerator(String prefix, boolean wrap, int size) {
        super(wrap, size - ((prefix == null) ? 0 : prefix.length()));

        if (prefix == null) {
            throw new NullPointerException("prefix must not be null");
        }
        if (size <= prefix.length()) {
            throw new IllegalArgumentException("size less prefix length must be at least one");
        }
        this.prefix = prefix;
    }


    /**
     * Return the prefix for this prefixed alphanumeric generator.
     *
     * @return the prefix for this prefixed alphanumeric generator
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

    public int getSize() {
        return super.getSize() + prefix.length();
    }

    public String nextStringIdentifier() {
        StringBuffer sb = new StringBuffer(prefix);
        sb.append(super.nextStringIdentifier());
        return sb.toString();
    }
}
