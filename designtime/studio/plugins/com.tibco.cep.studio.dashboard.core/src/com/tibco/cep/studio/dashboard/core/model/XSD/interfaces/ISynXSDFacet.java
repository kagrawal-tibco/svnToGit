package com.tibco.cep.studio.dashboard.core.model.XSD.interfaces;

/**
 * @
 *
 */
public interface ISynXSDFacet extends ISynXSDSchemaComponent {

    public static final String CONSTRAINT_MIN_EXCLUSIVE = "minExclusive";

    public static final String CONSTRAINT_MIN_INCLUSIVE = "minInclusive";

    public static final String CONSTRAINT_MAX_EXCLUSIVE = "maxExclusive";

    public static final String CONSTRAINT_MAX_INCLUSIVE = "maxInclusive";

    public static final String CONSTRAINT_TOTAL_DIGITS = "totalDigits";

    public static final String CONSTRAINT_FRACTION_DIGITS = "fractionDigits";

    public static final String CONSTRAINT_LEGTH = "length";

    public static final String CONSTRAINT_MIN_LENGTH = "minLength";

    public static final String CONSTRAINT_MAX_LENGTH = "maxLength";

    public static final String CONSTRAINT_ENUMERATION = "enumeration";

    public static final String CONSTRAINT_WHITE_SPACE = "whiteSpace";

    public static final String CONSTRAINT_PATTERN = "pattern";

    public static final String FUNDAMENTAL_EQUAL = "equal";

    public static final String FUNDAMENTAL_IS_ORDERED = "ordered";

    public static final String FUNDAMENTAL_IS_BOUNDED = "bounded";

    public static final String FUNDAMENTAL_CARDINALITY = "cardinality";

    public static final String FUNDAMENTAL_IS_NUMERIC = "numeric";

    /**
     * Returns the value of the facet
     *
     * @return String for numeric and String type including boolean; List for
     *         enumeration
     */
    public Object getValue();

    public void setValue(Object value);

    /**
     * Returns whether the value is restricted frm being re-declared in a
     * restriction/extension
     *
     * @return
     */
    public boolean isFixed();

    public void setFixed(boolean fixed);

    /**
     * Returns the string representation of the value
     *
     * @return
     */
    public String toString();
}
