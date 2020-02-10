package com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes;

import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.studio.dashboard.core.model.ISynPropertyEnumProvider;
import com.tibco.cep.studio.dashboard.core.model.XSD.components.SynXSDRestrictionSimpleTypeDefinition;

/**
 * SynPrimitiveType is a convenience abstract class for all other primitive types.
 *
 * This is for the convenience of using the 'instanceof' operator to qickly determine if the property is primitive as opposed to sequentially checking through a list of types
 *
 * @see com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynBooleanType
 * @see com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynByteType
 * @see com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynCharacterType
 * @see com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynDateType
 * @see com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynDoubleType
 * @see com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynFloatType
 * @see com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynIntType
 * @see com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynLongType
 * @see com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynShortType
 * @see com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynStringType
 *
 */
public abstract class SynPrimitiveType extends SynXSDRestrictionSimpleTypeDefinition {

	public SynPrimitiveType(Class<?> javaPrimitiveCLass) {
		this(javaPrimitiveCLass, false);
	}

	/**
	 * Assumption: maxExclusive is greater than or equal to minExclusive.
	 *
	 * @param minExclusive
	 *            : must be a valid number, null, or "".
	 * @param maxExclusive
	 *            : must be a valid number, null, or "".
	 */
	public SynPrimitiveType(Class<?> javaPrimitiveCLass, String minExclusive, String maxExclusive) {
		super(javaPrimitiveCLass.getName(), SynAtomicTypeFactory.getAtomicType(javaPrimitiveCLass), minExclusive, maxExclusive);
	}

	public SynPrimitiveType(Class<?> javaPrimitiveCLass, boolean allowsNegative) {
		super(javaPrimitiveCLass.getName(), SynAtomicTypeFactory.getAtomicType(javaPrimitiveCLass), allowsNegative);
	}

	/**
	 * A convenience constructor for an enum of primitive values
	 *
	 * @param javaType
	 */
	public SynPrimitiveType(Class<?> javaPrimitiveCLass, String[] enumValues) {
		super(javaPrimitiveCLass.getName(), SynAtomicTypeFactory.getAtomicType(javaPrimitiveCLass));
		ArrayList<Object> l = new ArrayList<Object>(enumValues.length);
		for (int i = 0; i < enumValues.length; i++) {
			l.add(enumValues[i]);
		}
		setEnumerations(l);
	}

	/**
	 * A convenience constructor for an enum of primitive values
	 *
	 * @param javaType
	 */
	public SynPrimitiveType(Class<?> javaPrimitiveCLass, List<Object> enumValues) {
		super(javaPrimitiveCLass.getName(), SynAtomicTypeFactory.getAtomicType(javaPrimitiveCLass));
		setEnumerations(enumValues);
	}

	public SynPrimitiveType(Class<?> javaPrimitiveCLass, ISynPropertyEnumProvider enumProvider) {
		super(javaPrimitiveCLass.getName(), SynAtomicTypeFactory.getAtomicType(javaPrimitiveCLass));
		setEnumerations(enumProvider);
	}

}