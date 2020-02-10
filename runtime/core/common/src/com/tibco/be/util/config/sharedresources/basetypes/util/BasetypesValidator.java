/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.basetypes.util;

import com.tibco.be.util.config.sharedresources.basetypes.*;

import java.math.BigDecimal;

import java.util.Map;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.EObjectValidator;

import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

import org.eclipse.emf.ecore.xml.type.util.XMLTypeUtil;
import org.eclipse.emf.ecore.xml.type.util.XMLTypeValidator;

/**
 * <!-- begin-user-doc -->
 * The <b>Validator</b> for the model.
 * <!-- end-user-doc -->
 * @see com.tibco.be.util.config.sharedresources.basetypes.BasetypesPackage
 * @generated
 */
public class BasetypesValidator extends EObjectValidator {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final BasetypesValidator INSTANCE = new BasetypesValidator();

	/**
	 * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Diagnostic#getSource()
	 * @see org.eclipse.emf.common.util.Diagnostic#getCode()
	 * @generated
	 */
	public static final String DIAGNOSTIC_SOURCE = "com.tibco.be.util.config.sharedresources.basetypes";

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final int GENERATED_DIAGNOSTIC_CODE_COUNT = 0;

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants in a derived class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final int DIAGNOSTIC_CODE_COUNT = GENERATED_DIAGNOSTIC_CODE_COUNT;

	/**
	 * The cached base package validator.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XMLTypeValidator xmlTypeValidator;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BasetypesValidator() {
		super();
		xmlTypeValidator = XMLTypeValidator.INSTANCE;
	}

	/**
	 * Returns the package of this validator switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EPackage getEPackage() {
	  return BasetypesPackage.eINSTANCE;
	}

	/**
	 * Calls <code>validateXXX</code> for the corresponding classifier of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected boolean validate(int classifierID, Object value, DiagnosticChain diagnostics, Map<Object, Object> context) {
		switch (classifierID) {
			case BasetypesPackage.JNDI_PROPERTY_TYPE_OR_GVS_ENUM:
				return validateJndiPropertyTypeOrGvsEnum((JndiPropertyTypeOrGvsEnum)value, diagnostics, context);
			case BasetypesPackage.BOOLEAN_OR_GVS:
				return validateBooleanOrGvs(value, diagnostics, context);
			case BasetypesPackage.DATE_TIME_OR_GVS:
				return validateDateTimeOrGvs(value, diagnostics, context);
			case BasetypesPackage.DECIMAL_OR_GVS:
				return validateDecimalOrGvs(value, diagnostics, context);
			case BasetypesPackage.DOUBLE_OR_GVS:
				return validateDoubleOrGvs(value, diagnostics, context);
			case BasetypesPackage.FLOAT_OR_GVS:
				return validateFloatOrGvs(value, diagnostics, context);
			case BasetypesPackage.GV:
				return validateGv((String)value, diagnostics, context);
			case BasetypesPackage.HEX_BINARY_OR_GVS:
				return validateHexBinaryOrGVs(value, diagnostics, context);
			case BasetypesPackage.INT_OR_GVS:
				return validateIntOrGVs(value, diagnostics, context);
			case BasetypesPackage.JNDI_PROPERTY_TYPE_OR_GVS:
				return validateJndiPropertyTypeOrGvs(value, diagnostics, context);
			case BasetypesPackage.JNDI_PROPERTY_TYPE_OR_GVS_ENUM_OBJECT:
				return validateJndiPropertyTypeOrGvsEnumObject((JndiPropertyTypeOrGvsEnum)value, diagnostics, context);
			case BasetypesPackage.LONG_OR_GVS:
				return validateLongOrGVs(value, diagnostics, context);
			case BasetypesPackage.STRING_OR_GVS:
				return validateStringOrGVs((String)value, diagnostics, context);
			default:
				return true;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateJndiPropertyTypeOrGvsEnum(JndiPropertyTypeOrGvsEnum jndiPropertyTypeOrGvsEnum, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateBooleanOrGvs(Object booleanOrGvs, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validateBooleanOrGvs_MemberTypes(booleanOrGvs, diagnostics, context);
		return result;
	}

	/**
	 * Validates the MemberTypes constraint of '<em>Boolean Or Gvs</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateBooleanOrGvs_MemberTypes(Object booleanOrGvs, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (diagnostics != null) {
			BasicDiagnostic tempDiagnostics = new BasicDiagnostic();
			if (XMLTypePackage.Literals.BOOLEAN.isInstance(booleanOrGvs)) {
				if (xmlTypeValidator.validateBoolean((Boolean)booleanOrGvs, tempDiagnostics, context)) return true;
			}
			if (BasetypesPackage.Literals.GV.isInstance(booleanOrGvs)) {
				if (validateGv((String)booleanOrGvs, tempDiagnostics, context)) return true;
			}
			for (Diagnostic diagnostic : tempDiagnostics.getChildren()) {
				diagnostics.add(diagnostic);
			}
		}
		else {
			if (XMLTypePackage.Literals.BOOLEAN.isInstance(booleanOrGvs)) {
				if (xmlTypeValidator.validateBoolean((Boolean)booleanOrGvs, null, context)) return true;
			}
			if (BasetypesPackage.Literals.GV.isInstance(booleanOrGvs)) {
				if (validateGv((String)booleanOrGvs, null, context)) return true;
			}
		}
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDateTimeOrGvs(Object dateTimeOrGvs, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validateDateTimeOrGvs_MemberTypes(dateTimeOrGvs, diagnostics, context);
		return result;
	}

	/**
	 * Validates the MemberTypes constraint of '<em>Date Time Or Gvs</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDateTimeOrGvs_MemberTypes(Object dateTimeOrGvs, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (diagnostics != null) {
			BasicDiagnostic tempDiagnostics = new BasicDiagnostic();
			if (XMLTypePackage.Literals.DATE_TIME.isInstance(dateTimeOrGvs)) {
				if (xmlTypeValidator.validateDateTime((XMLGregorianCalendar)dateTimeOrGvs, tempDiagnostics, context)) return true;
			}
			if (BasetypesPackage.Literals.GV.isInstance(dateTimeOrGvs)) {
				if (validateGv((String)dateTimeOrGvs, tempDiagnostics, context)) return true;
			}
			for (Diagnostic diagnostic : tempDiagnostics.getChildren()) {
				diagnostics.add(diagnostic);
			}
		}
		else {
			if (XMLTypePackage.Literals.DATE_TIME.isInstance(dateTimeOrGvs)) {
				if (xmlTypeValidator.validateDateTime((XMLGregorianCalendar)dateTimeOrGvs, null, context)) return true;
			}
			if (BasetypesPackage.Literals.GV.isInstance(dateTimeOrGvs)) {
				if (validateGv((String)dateTimeOrGvs, null, context)) return true;
			}
		}
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDecimalOrGvs(Object decimalOrGvs, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validateDecimalOrGvs_MemberTypes(decimalOrGvs, diagnostics, context);
		return result;
	}

	/**
	 * Validates the MemberTypes constraint of '<em>Decimal Or Gvs</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDecimalOrGvs_MemberTypes(Object decimalOrGvs, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (diagnostics != null) {
			BasicDiagnostic tempDiagnostics = new BasicDiagnostic();
			if (XMLTypePackage.Literals.DECIMAL.isInstance(decimalOrGvs)) {
				if (xmlTypeValidator.validateDecimal((BigDecimal)decimalOrGvs, tempDiagnostics, context)) return true;
			}
			if (BasetypesPackage.Literals.GV.isInstance(decimalOrGvs)) {
				if (validateGv((String)decimalOrGvs, tempDiagnostics, context)) return true;
			}
			for (Diagnostic diagnostic : tempDiagnostics.getChildren()) {
				diagnostics.add(diagnostic);
			}
		}
		else {
			if (XMLTypePackage.Literals.DECIMAL.isInstance(decimalOrGvs)) {
				if (xmlTypeValidator.validateDecimal((BigDecimal)decimalOrGvs, null, context)) return true;
			}
			if (BasetypesPackage.Literals.GV.isInstance(decimalOrGvs)) {
				if (validateGv((String)decimalOrGvs, null, context)) return true;
			}
		}
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDoubleOrGvs(Object doubleOrGvs, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validateDoubleOrGvs_MemberTypes(doubleOrGvs, diagnostics, context);
		return result;
	}

	/**
	 * Validates the MemberTypes constraint of '<em>Double Or Gvs</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDoubleOrGvs_MemberTypes(Object doubleOrGvs, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (diagnostics != null) {
			BasicDiagnostic tempDiagnostics = new BasicDiagnostic();
			if (XMLTypePackage.Literals.DOUBLE.isInstance(doubleOrGvs)) {
				if (xmlTypeValidator.validateDouble((Double)doubleOrGvs, tempDiagnostics, context)) return true;
			}
			if (BasetypesPackage.Literals.GV.isInstance(doubleOrGvs)) {
				if (validateGv((String)doubleOrGvs, tempDiagnostics, context)) return true;
			}
			for (Diagnostic diagnostic : tempDiagnostics.getChildren()) {
				diagnostics.add(diagnostic);
			}
		}
		else {
			if (XMLTypePackage.Literals.DOUBLE.isInstance(doubleOrGvs)) {
				if (xmlTypeValidator.validateDouble((Double)doubleOrGvs, null, context)) return true;
			}
			if (BasetypesPackage.Literals.GV.isInstance(doubleOrGvs)) {
				if (validateGv((String)doubleOrGvs, null, context)) return true;
			}
		}
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateFloatOrGvs(Object floatOrGvs, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validateFloatOrGvs_MemberTypes(floatOrGvs, diagnostics, context);
		return result;
	}

	/**
	 * Validates the MemberTypes constraint of '<em>Float Or Gvs</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateFloatOrGvs_MemberTypes(Object floatOrGvs, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (diagnostics != null) {
			BasicDiagnostic tempDiagnostics = new BasicDiagnostic();
			if (XMLTypePackage.Literals.FLOAT.isInstance(floatOrGvs)) {
				if (xmlTypeValidator.validateFloat((Float)floatOrGvs, tempDiagnostics, context)) return true;
			}
			if (BasetypesPackage.Literals.GV.isInstance(floatOrGvs)) {
				if (validateGv((String)floatOrGvs, tempDiagnostics, context)) return true;
			}
			for (Diagnostic diagnostic : tempDiagnostics.getChildren()) {
				diagnostics.add(diagnostic);
			}
		}
		else {
			if (XMLTypePackage.Literals.FLOAT.isInstance(floatOrGvs)) {
				if (xmlTypeValidator.validateFloat((Float)floatOrGvs, null, context)) return true;
			}
			if (BasetypesPackage.Literals.GV.isInstance(floatOrGvs)) {
				if (validateGv((String)floatOrGvs, null, context)) return true;
			}
		}
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateGv(String gv, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validateGv_Pattern(gv, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @see #validateGv_Pattern
	 */
	public static final  PatternMatcher [][] GV__PATTERN__VALUES =
		new PatternMatcher [][] {
			new PatternMatcher [] {
				XMLTypeUtil.createPatternMatcher("([^%]*%%[^%]*%%)+[^%]*")
			}
		};

	/**
	 * Validates the Pattern constraint of '<em>Gv</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateGv_Pattern(String gv, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validatePattern(BasetypesPackage.Literals.GV, gv, GV__PATTERN__VALUES, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateHexBinaryOrGVs(Object hexBinaryOrGVs, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validateHexBinaryOrGVs_MemberTypes(hexBinaryOrGVs, diagnostics, context);
		return result;
	}

	/**
	 * Validates the MemberTypes constraint of '<em>Hex Binary Or GVs</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateHexBinaryOrGVs_MemberTypes(Object hexBinaryOrGVs, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (diagnostics != null) {
			BasicDiagnostic tempDiagnostics = new BasicDiagnostic();
			if (XMLTypePackage.Literals.HEX_BINARY.isInstance(hexBinaryOrGVs)) {
				if (xmlTypeValidator.validateHexBinary((byte[])hexBinaryOrGVs, tempDiagnostics, context)) return true;
			}
			if (BasetypesPackage.Literals.GV.isInstance(hexBinaryOrGVs)) {
				if (validateGv((String)hexBinaryOrGVs, tempDiagnostics, context)) return true;
			}
			for (Diagnostic diagnostic : tempDiagnostics.getChildren()) {
				diagnostics.add(diagnostic);
			}
		}
		else {
			if (XMLTypePackage.Literals.HEX_BINARY.isInstance(hexBinaryOrGVs)) {
				if (xmlTypeValidator.validateHexBinary((byte[])hexBinaryOrGVs, null, context)) return true;
			}
			if (BasetypesPackage.Literals.GV.isInstance(hexBinaryOrGVs)) {
				if (validateGv((String)hexBinaryOrGVs, null, context)) return true;
			}
		}
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateIntOrGVs(Object intOrGVs, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validateIntOrGVs_MemberTypes(intOrGVs, diagnostics, context);
		return result;
	}

	/**
	 * Validates the MemberTypes constraint of '<em>Int Or GVs</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateIntOrGVs_MemberTypes(Object intOrGVs, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (diagnostics != null) {
			BasicDiagnostic tempDiagnostics = new BasicDiagnostic();
			if (XMLTypePackage.Literals.INT.isInstance(intOrGVs)) {
				if (xmlTypeValidator.validateInt((Integer)intOrGVs, tempDiagnostics, context)) return true;
			}
			if (BasetypesPackage.Literals.GV.isInstance(intOrGVs)) {
				if (validateGv((String)intOrGVs, tempDiagnostics, context)) return true;
			}
			for (Diagnostic diagnostic : tempDiagnostics.getChildren()) {
				diagnostics.add(diagnostic);
			}
		}
		else {
			if (XMLTypePackage.Literals.INT.isInstance(intOrGVs)) {
				if (xmlTypeValidator.validateInt((Integer)intOrGVs, null, context)) return true;
			}
			if (BasetypesPackage.Literals.GV.isInstance(intOrGVs)) {
				if (validateGv((String)intOrGVs, null, context)) return true;
			}
		}
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateJndiPropertyTypeOrGvs(Object jndiPropertyTypeOrGvs, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validateJndiPropertyTypeOrGvs_MemberTypes(jndiPropertyTypeOrGvs, diagnostics, context);
		return result;
	}

	/**
	 * Validates the MemberTypes constraint of '<em>Jndi Property Type Or Gvs</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateJndiPropertyTypeOrGvs_MemberTypes(Object jndiPropertyTypeOrGvs, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (diagnostics != null) {
			BasicDiagnostic tempDiagnostics = new BasicDiagnostic();
			if (BasetypesPackage.Literals.GV.isInstance(jndiPropertyTypeOrGvs)) {
				if (validateGv((String)jndiPropertyTypeOrGvs, tempDiagnostics, context)) return true;
			}
			if (BasetypesPackage.Literals.JNDI_PROPERTY_TYPE_OR_GVS_ENUM.isInstance(jndiPropertyTypeOrGvs)) {
				if (validateJndiPropertyTypeOrGvsEnum((JndiPropertyTypeOrGvsEnum)jndiPropertyTypeOrGvs, tempDiagnostics, context)) return true;
			}
			for (Diagnostic diagnostic : tempDiagnostics.getChildren()) {
				diagnostics.add(diagnostic);
			}
		}
		else {
			if (BasetypesPackage.Literals.GV.isInstance(jndiPropertyTypeOrGvs)) {
				if (validateGv((String)jndiPropertyTypeOrGvs, null, context)) return true;
			}
			if (BasetypesPackage.Literals.JNDI_PROPERTY_TYPE_OR_GVS_ENUM.isInstance(jndiPropertyTypeOrGvs)) {
				if (validateJndiPropertyTypeOrGvsEnum((JndiPropertyTypeOrGvsEnum)jndiPropertyTypeOrGvs, null, context)) return true;
			}
		}
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateJndiPropertyTypeOrGvsEnumObject(JndiPropertyTypeOrGvsEnum jndiPropertyTypeOrGvsEnumObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateLongOrGVs(Object longOrGVs, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validateLongOrGVs_MemberTypes(longOrGVs, diagnostics, context);
		return result;
	}

	/**
	 * Validates the MemberTypes constraint of '<em>Long Or GVs</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateLongOrGVs_MemberTypes(Object longOrGVs, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (diagnostics != null) {
			BasicDiagnostic tempDiagnostics = new BasicDiagnostic();
			if (XMLTypePackage.Literals.LONG.isInstance(longOrGVs)) {
				if (xmlTypeValidator.validateLong((Long)longOrGVs, tempDiagnostics, context)) return true;
			}
			if (BasetypesPackage.Literals.GV.isInstance(longOrGVs)) {
				if (validateGv((String)longOrGVs, tempDiagnostics, context)) return true;
			}
			for (Diagnostic diagnostic : tempDiagnostics.getChildren()) {
				diagnostics.add(diagnostic);
			}
		}
		else {
			if (XMLTypePackage.Literals.LONG.isInstance(longOrGVs)) {
				if (xmlTypeValidator.validateLong((Long)longOrGVs, null, context)) return true;
			}
			if (BasetypesPackage.Literals.GV.isInstance(longOrGVs)) {
				if (validateGv((String)longOrGVs, null, context)) return true;
			}
		}
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateStringOrGVs(String stringOrGVs, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validateStringOrGVs_MemberTypes(stringOrGVs, diagnostics, context);
		return result;
	}

	/**
	 * Validates the MemberTypes constraint of '<em>String Or GVs</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateStringOrGVs_MemberTypes(String stringOrGVs, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (diagnostics != null) {
			BasicDiagnostic tempDiagnostics = new BasicDiagnostic();
			if (BasetypesPackage.Literals.GV.isInstance(stringOrGVs)) {
				if (validateGv(stringOrGVs, tempDiagnostics, context)) return true;
			}
			if (XMLTypePackage.Literals.STRING.isInstance(stringOrGVs)) {
				if (xmlTypeValidator.validateString(stringOrGVs, tempDiagnostics, context)) return true;
			}
			for (Diagnostic diagnostic : tempDiagnostics.getChildren()) {
				diagnostics.add(diagnostic);
			}
		}
		else {
			if (BasetypesPackage.Literals.GV.isInstance(stringOrGVs)) {
				if (validateGv(stringOrGVs, null, context)) return true;
			}
			if (XMLTypePackage.Literals.STRING.isInstance(stringOrGVs)) {
				if (xmlTypeValidator.validateString(stringOrGVs, null, context)) return true;
			}
		}
		return false;
	}

	/**
	 * Returns the resource locator that will be used to fetch messages for this validator's diagnostics.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		// TODO
		// Specialize this to return a resource locator for messages specific to this validator.
		// Ensure that you remove @generated or mark it @generated NOT
		return super.getResourceLocator();
	}

} //BasetypesValidator
