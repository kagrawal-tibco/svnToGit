package com.tibco.be.util.idgenerators.serial;

import java.io.Serializable;

import com.tibco.be.util.idgenerators.AbstractStringIdentifierGenerator;

/**
 * <code>AlphanumericGenerator</code> is an identifier generator
 * that generates an incrementing number in base 36 as a String
 * object.
 *
 * <p>All generated ids have the same length (padding with 0's on the left),
 * which is determined by the <code>size</code> parameter passed to the constructor.<p>
 *
 * <p>The <code>wrap</code> property determines whether or not the sequence wraps
 * when it reaches the largest value that can be represented in <code>size</code>
 * base 36 digits. If <code>wrap</code> is false and the the maximum representable
 * value is exceeded, an IllegalStateException is thrown</p>
 *
 * @author Commons-Id team
 * @version $Id$
 */
public class AlphanumericGenerator extends AbstractStringIdentifierGenerator implements Serializable {

    /**
     * <code>serialVersionUID</code> is the serializable UID for the binary version of the class.
     */
    private static final long serialVersionUID = 20060120L;

    /**
     * Should the counter wrap.
     */
    private boolean wrapping = true;

    /**
     * The counter.
     */
    private char[] count = null;

    /**
     * 'z' char
     */
    private static final char Z_CHAR = 'z';

    /**
     * '9' char
     */
    private static final char NINE_CHAR = '9';

    /**
     * Constructor with a default size for the alphanumeric identifier.
     *
     * @param wrap should the factory wrap when it reaches the maximum
     *  long value (or throw an exception)
     */
    public AlphanumericGenerator(boolean wrap) {
        this(wrap, AbstractStringIdentifierGenerator.DEFAULT_ALPHANUMERIC_IDENTIFIER_SIZE);
    }

    /**
     * Constructor.
     *
     * @param wrap should the factory wrap when it reaches the maximum
     *  long value (or throw an exception)
     * @param size  the size of the identifier
     */
    public AlphanumericGenerator(boolean wrap, int size) {
        super();
        this.wrapping = wrap;
        if (size < 1) {
            throw new IllegalArgumentException("The size must be at least one");
        }
        this.count = new char[size];
        for (int i = 0; i < size; i++) {
            count[i] = '0';  // zero
        }
    }

    /**
     * Construct with a counter, that will start at the specified
     * alphanumeric value.</p>
     *
     * @param wrap should the factory wrap when it reaches the maximum
     * value (or throw an exception)
     * @param initialValue the initial value to start at
     */
    public AlphanumericGenerator(boolean wrap, String initialValue) {
        super();
        this.wrapping = wrap;
        this.count = initialValue.toCharArray();

        for (int i = 0; i < this.count.length; i++) {
            char ch = this.count[i];
            if (ch >= '0' && ch <= '9') continue;
            if (ch >= 'a' && ch <= 'z') continue;

            throw new IllegalArgumentException(
                    "character " + this.count[i] + " is not valid");
        }
    }

    public long maxLength() {
        return this.count.length;
    }

    public long minLength() {
        return this.count.length;
    }

    /**
     * Getter for property wrap.
     *
     * @return <code>true</code> if this generator is set up to wrap.
     *
     */
    public boolean isWrap() {
        return wrapping;
    }

    /**
     * Sets the wrap property.
     *
     * @param wrap value for the wrap property
     *
     */
    public void setWrap(boolean wrap) {
        this.wrapping = wrap;
    }

    /**
     * Returns the (constant) size of the strings generated by this generator.
     *
     * @return the size of generated identifiers
     */
    public int getSize() {
        return this.count.length;
    }

    public synchronized String nextStringIdentifier() {
        for (int i = count.length - 1; i >= 0; i--) {
            switch (count[i]) {
                case AlphanumericGenerator.Z_CHAR:  // z
                    if (i == 0 && !wrapping) {
                        throw new IllegalStateException
                        ("The maximum number of identifiers has been reached");
                    }
                    count[i] = '0';
                    break;

                case AlphanumericGenerator.NINE_CHAR:  // 9
                    count[i] = 'a';
                    i = -1;
                    break;

                default:
                    count[i]++;
                    i = -1;
                    break;
            }
        }
        return new String(count);
    }
}
