package com.tibco.be.util.idgenerators.serial;

import java.io.Serializable;

import com.tibco.be.util.idgenerators.AbstractLongIdentifierGenerator;

/**
 * <code>LongGenerator</code> is an Identifier Generator
 * that generates an incrementing number as a Long object.
 *
 * <p>If the <code>wrap</code> argument passed to the constructor is set to
 * <code>true</code>, the sequence will wrap, returning negative values when
 * {@link Long#MAX_VALUE} reached; otherwise an {@link IllegalStateException}
 * will be thrown.</p>
 *
 * @author Commons-Id team
 * @version $Id$
 */
public class LongGenerator extends AbstractLongIdentifierGenerator implements Serializable {

    /**
     * <code>serialVersionUID</code> is the serializable UID for the binary version of the class.
     */
    private static final long serialVersionUID = 20060122L;

    /** Should the counter wrap. */
    private boolean wrapping;
    /** The counter. */
    private long count = 0;

    /**
     * Constructor.
     *
     * @param wrap should the factory wrap when it reaches the maximum
     *  long value (or throw an exception)
     * @param initialValue  the initial long value to start at
     */
    public LongGenerator(boolean wrap, long initialValue) {
        super();
        this.wrapping = wrap;
        this.count = initialValue;
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

    public Long nextLongIdentifier() {
        long value = 0;
        if (wrapping) {
            synchronized (this) {
                value = count++;
            }
        } else {
            synchronized (this) {
                if (count == Long.MAX_VALUE) {
                    throw new IllegalStateException
                    ("The maximum number of identifiers has been reached");
                }
                value = count++;
            }
        }
        return new Long(value);
    }
}
