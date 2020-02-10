package com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes;

import java.util.List;

/**
 * SynNumericType is a convenience abstract class for all numeric primitive types.
 *
 * This is for the convenience of using the 'instanceof' operator to qickly determine if the property is primitive as opposed to sequentially checking through a list of types
 *
 * @see com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynByteType
 * @see com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynDoubleType
 * @see com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynFloatType
 * @see com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynIntType
 * @see com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynLongType
 * @see com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynShortType
 *
 */
public abstract class SynNumericType extends SynPrimitiveType {

	public SynNumericType(Class<?> javaPrimitiveCLass) {
		super(javaPrimitiveCLass);
	}

	/**
	 * Assumption: maxExclusive is greater than or equal to minExclusive.
	 *
	 * @param minExclusive
	 *            : must be a valid number, null, or "".
	 * @param maxExclusive
	 *            : must be a valid number, null, or "".
	 */
	public SynNumericType(Class<?> javaPrimitiveCLass, String minExclusive, String maxExclusive) {
		super(javaPrimitiveCLass, minExclusive, maxExclusive);

	}

	/**
	 * @param javaPrimitiveCLass
	 * @param allowsNegative
	 */
	public SynNumericType(Class<?> javaPrimitiveCLass, boolean allowsNegative) {
		super(javaPrimitiveCLass, allowsNegative);
	}

	/**
	 * @param javaPrimitiveCLass
	 * @param enumValues
	 */
	public SynNumericType(Class<?> javaPrimitiveCLass, String[] enumValues) {
		super(javaPrimitiveCLass, enumValues);
	}

	/**
	 * @param javaPrimitiveCLass
	 * @param enumValues
	 */
	public SynNumericType(Class<?> javaPrimitiveCLass, List<Object> enumValues) {
		super(javaPrimitiveCLass, enumValues);

	}

}
