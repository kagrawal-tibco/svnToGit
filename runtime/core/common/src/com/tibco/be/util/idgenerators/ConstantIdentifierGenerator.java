package com.tibco.be.util.idgenerators;



/**
 * <code>StringIdentifierGenerator</code> that always returns the same
 * string.  Use with {@link CompositeIdentifierGenerator} to embed constant
 * string identifiers, or to add prefixes or suffixes.
 * <p>
 * Null constant values are not allowed. The default
 * (assumed by the argumentless constructor) is an empty string.</p>
 *
 * @author Commons-Id team
 * @version $Id$
 */
public class ConstantIdentifierGenerator extends
    AbstractStringIdentifierGenerator {

    /**
     * Constant string value always returned by this generator.
     */
    private final String identifier;

    /**
     * Factory method to create a new <code>ConstantIdentifierGenerator</code>
     * with the given constant value.  Does not allow null values.
     *
     * @param identifier constant value returned by the newly created generator.
     *      Must not be null.
     * @return a new ConstantIdentifierGenerator
     * @throws IllegalArgumentException if identifier is null
     */
    public static StringIdentifierGenerator getInstance(String identifier) {
        return new com.tibco.be.util.idgenerators.ConstantIdentifierGenerator(identifier);
    }

    /**
     * Creates a new ConstantIdentifierGenerator using the
     * default (empty string) constant value.
     *
     */
    public ConstantIdentifierGenerator() {
        super();
        this.identifier = "";
    }

    /**
     * Creates a new ConstantIdentifierGenerator using the
     * given constant value.
     * <p>
     * Null constant values are not allowed.</p>
     *
     * @param identifier constant value returned by the generator.  Must not
     *   be null.
     * @throws java.lang.IllegalArgumentException if identifier is null
     */
    public ConstantIdentifierGenerator(String identifier) throws IllegalArgumentException {
        super();
        if (identifier == null) {
            throw new IllegalArgumentException
                ("Constant identifier value must not be null");
        }
        this.identifier = identifier;
    }

    public String nextStringIdentifier() {
        return identifier;
    }

    /**
     * Returns the length of the constant string returned by this generator.
     * If the constant is null or an empty string, 0 is returned.
     *
     * @return the length of the constant string returned by this generator
     */
    public long maxLength() {
       if (identifier == null) {
           return 0;
       } else {
           return identifier.length();
       }
    }

    /**
     * Returns the length of the constant string returned by this generator.
     * If the constant is null or an empty string, 0 is returned.
     *
     * @return the length of the constant string returned by this generator
     */
    public long minLength() {
        return maxLength();
    }
}
