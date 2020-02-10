/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.basetypes.impl;

import com.tibco.be.util.config.sharedresources.basetypes.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.emf.ecore.util.Diagnostician;

import org.eclipse.emf.ecore.xml.type.XMLTypeFactory;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class BasetypesFactoryImpl extends EFactoryImpl implements BasetypesFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static BasetypesFactory init() {
		try {
			BasetypesFactory theBasetypesFactory = (BasetypesFactory)EPackage.Registry.INSTANCE.getEFactory("http://tibco.com/be/basetypes"); 
			if (theBasetypesFactory != null) {
				return theBasetypesFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new BasetypesFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BasetypesFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case BasetypesPackage.JNDI_PROPERTY_TYPE_OR_GVS_ENUM:
				return createJndiPropertyTypeOrGvsEnumFromString(eDataType, initialValue);
			case BasetypesPackage.BOOLEAN_OR_GVS:
				return createBooleanOrGvsFromString(eDataType, initialValue);
			case BasetypesPackage.DATE_TIME_OR_GVS:
				return createDateTimeOrGvsFromString(eDataType, initialValue);
			case BasetypesPackage.DECIMAL_OR_GVS:
				return createDecimalOrGvsFromString(eDataType, initialValue);
			case BasetypesPackage.DOUBLE_OR_GVS:
				return createDoubleOrGvsFromString(eDataType, initialValue);
			case BasetypesPackage.FLOAT_OR_GVS:
				return createFloatOrGvsFromString(eDataType, initialValue);
			case BasetypesPackage.GV:
				return createGvFromString(eDataType, initialValue);
			case BasetypesPackage.HEX_BINARY_OR_GVS:
				return createHexBinaryOrGVsFromString(eDataType, initialValue);
			case BasetypesPackage.INT_OR_GVS:
				return createIntOrGVsFromString(eDataType, initialValue);
			case BasetypesPackage.JNDI_PROPERTY_TYPE_OR_GVS:
				return createJndiPropertyTypeOrGvsFromString(eDataType, initialValue);
			case BasetypesPackage.JNDI_PROPERTY_TYPE_OR_GVS_ENUM_OBJECT:
				return createJndiPropertyTypeOrGvsEnumObjectFromString(eDataType, initialValue);
			case BasetypesPackage.LONG_OR_GVS:
				return createLongOrGVsFromString(eDataType, initialValue);
			case BasetypesPackage.STRING_OR_GVS:
				return createStringOrGVsFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case BasetypesPackage.JNDI_PROPERTY_TYPE_OR_GVS_ENUM:
				return convertJndiPropertyTypeOrGvsEnumToString(eDataType, instanceValue);
			case BasetypesPackage.BOOLEAN_OR_GVS:
				return convertBooleanOrGvsToString(eDataType, instanceValue);
			case BasetypesPackage.DATE_TIME_OR_GVS:
				return convertDateTimeOrGvsToString(eDataType, instanceValue);
			case BasetypesPackage.DECIMAL_OR_GVS:
				return convertDecimalOrGvsToString(eDataType, instanceValue);
			case BasetypesPackage.DOUBLE_OR_GVS:
				return convertDoubleOrGvsToString(eDataType, instanceValue);
			case BasetypesPackage.FLOAT_OR_GVS:
				return convertFloatOrGvsToString(eDataType, instanceValue);
			case BasetypesPackage.GV:
				return convertGvToString(eDataType, instanceValue);
			case BasetypesPackage.HEX_BINARY_OR_GVS:
				return convertHexBinaryOrGVsToString(eDataType, instanceValue);
			case BasetypesPackage.INT_OR_GVS:
				return convertIntOrGVsToString(eDataType, instanceValue);
			case BasetypesPackage.JNDI_PROPERTY_TYPE_OR_GVS:
				return convertJndiPropertyTypeOrGvsToString(eDataType, instanceValue);
			case BasetypesPackage.JNDI_PROPERTY_TYPE_OR_GVS_ENUM_OBJECT:
				return convertJndiPropertyTypeOrGvsEnumObjectToString(eDataType, instanceValue);
			case BasetypesPackage.LONG_OR_GVS:
				return convertLongOrGVsToString(eDataType, instanceValue);
			case BasetypesPackage.STRING_OR_GVS:
				return convertStringOrGVsToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JndiPropertyTypeOrGvsEnum createJndiPropertyTypeOrGvsEnumFromString(EDataType eDataType, String initialValue) {
		JndiPropertyTypeOrGvsEnum result = JndiPropertyTypeOrGvsEnum.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertJndiPropertyTypeOrGvsEnumToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object createBooleanOrGvsFromString(EDataType eDataType, String initialValue) {
		if (initialValue == null) return null;
		Object result = null;
		RuntimeException exception = null;
		try {
			result = XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.BOOLEAN, initialValue);
			if (result != null && Diagnostician.INSTANCE.validate(eDataType, result, null, null)) {
				return result;
			}
		}
		catch (RuntimeException e) {
			exception = e;
		}
		try {
			result = createGvFromString(BasetypesPackage.Literals.GV, initialValue);
			if (result != null && Diagnostician.INSTANCE.validate(eDataType, result, null, null)) {
				return result;
			}
		}
		catch (RuntimeException e) {
			exception = e;
		}
		if (result != null || exception == null) return result;
    
		throw exception;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertBooleanOrGvsToString(EDataType eDataType, Object instanceValue) {
		if (instanceValue == null) return null;
		if (XMLTypePackage.Literals.BOOLEAN.isInstance(instanceValue)) {
			try {
				String value = XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.BOOLEAN, instanceValue);
				if (value != null) return value;
			}
			catch (Exception e) {
				// Keep trying other member types until all have failed.
			}
		}
		if (BasetypesPackage.Literals.GV.isInstance(instanceValue)) {
			try {
				String value = convertGvToString(BasetypesPackage.Literals.GV, instanceValue);
				if (value != null) return value;
			}
			catch (Exception e) {
				// Keep trying other member types until all have failed.
			}
		}
		throw new IllegalArgumentException("Invalid value: '"+instanceValue+"' for datatype :"+eDataType.getName());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object createDateTimeOrGvsFromString(EDataType eDataType, String initialValue) {
		if (initialValue == null) return null;
		Object result = null;
		RuntimeException exception = null;
		try {
			result = XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.DATE_TIME, initialValue);
			if (result != null && Diagnostician.INSTANCE.validate(eDataType, result, null, null)) {
				return result;
			}
		}
		catch (RuntimeException e) {
			exception = e;
		}
		try {
			result = createGvFromString(BasetypesPackage.Literals.GV, initialValue);
			if (result != null && Diagnostician.INSTANCE.validate(eDataType, result, null, null)) {
				return result;
			}
		}
		catch (RuntimeException e) {
			exception = e;
		}
		if (result != null || exception == null) return result;
    
		throw exception;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDateTimeOrGvsToString(EDataType eDataType, Object instanceValue) {
		if (instanceValue == null) return null;
		if (XMLTypePackage.Literals.DATE_TIME.isInstance(instanceValue)) {
			try {
				String value = XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.DATE_TIME, instanceValue);
				if (value != null) return value;
			}
			catch (Exception e) {
				// Keep trying other member types until all have failed.
			}
		}
		if (BasetypesPackage.Literals.GV.isInstance(instanceValue)) {
			try {
				String value = convertGvToString(BasetypesPackage.Literals.GV, instanceValue);
				if (value != null) return value;
			}
			catch (Exception e) {
				// Keep trying other member types until all have failed.
			}
		}
		throw new IllegalArgumentException("Invalid value: '"+instanceValue+"' for datatype :"+eDataType.getName());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object createDecimalOrGvsFromString(EDataType eDataType, String initialValue) {
		if (initialValue == null) return null;
		Object result = null;
		RuntimeException exception = null;
		try {
			result = XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.DECIMAL, initialValue);
			if (result != null && Diagnostician.INSTANCE.validate(eDataType, result, null, null)) {
				return result;
			}
		}
		catch (RuntimeException e) {
			exception = e;
		}
		try {
			result = createGvFromString(BasetypesPackage.Literals.GV, initialValue);
			if (result != null && Diagnostician.INSTANCE.validate(eDataType, result, null, null)) {
				return result;
			}
		}
		catch (RuntimeException e) {
			exception = e;
		}
		if (result != null || exception == null) return result;
    
		throw exception;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDecimalOrGvsToString(EDataType eDataType, Object instanceValue) {
		if (instanceValue == null) return null;
		if (XMLTypePackage.Literals.DECIMAL.isInstance(instanceValue)) {
			try {
				String value = XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.DECIMAL, instanceValue);
				if (value != null) return value;
			}
			catch (Exception e) {
				// Keep trying other member types until all have failed.
			}
		}
		if (BasetypesPackage.Literals.GV.isInstance(instanceValue)) {
			try {
				String value = convertGvToString(BasetypesPackage.Literals.GV, instanceValue);
				if (value != null) return value;
			}
			catch (Exception e) {
				// Keep trying other member types until all have failed.
			}
		}
		throw new IllegalArgumentException("Invalid value: '"+instanceValue+"' for datatype :"+eDataType.getName());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object createDoubleOrGvsFromString(EDataType eDataType, String initialValue) {
		if (initialValue == null) return null;
		Object result = null;
		RuntimeException exception = null;
		try {
			result = XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.DOUBLE, initialValue);
			if (result != null && Diagnostician.INSTANCE.validate(eDataType, result, null, null)) {
				return result;
			}
		}
		catch (RuntimeException e) {
			exception = e;
		}
		try {
			result = createGvFromString(BasetypesPackage.Literals.GV, initialValue);
			if (result != null && Diagnostician.INSTANCE.validate(eDataType, result, null, null)) {
				return result;
			}
		}
		catch (RuntimeException e) {
			exception = e;
		}
		if (result != null || exception == null) return result;
    
		throw exception;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDoubleOrGvsToString(EDataType eDataType, Object instanceValue) {
		if (instanceValue == null) return null;
		if (XMLTypePackage.Literals.DOUBLE.isInstance(instanceValue)) {
			try {
				String value = XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.DOUBLE, instanceValue);
				if (value != null) return value;
			}
			catch (Exception e) {
				// Keep trying other member types until all have failed.
			}
		}
		if (BasetypesPackage.Literals.GV.isInstance(instanceValue)) {
			try {
				String value = convertGvToString(BasetypesPackage.Literals.GV, instanceValue);
				if (value != null) return value;
			}
			catch (Exception e) {
				// Keep trying other member types until all have failed.
			}
		}
		throw new IllegalArgumentException("Invalid value: '"+instanceValue+"' for datatype :"+eDataType.getName());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object createFloatOrGvsFromString(EDataType eDataType, String initialValue) {
		if (initialValue == null) return null;
		Object result = null;
		RuntimeException exception = null;
		try {
			result = XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.FLOAT, initialValue);
			if (result != null && Diagnostician.INSTANCE.validate(eDataType, result, null, null)) {
				return result;
			}
		}
		catch (RuntimeException e) {
			exception = e;
		}
		try {
			result = createGvFromString(BasetypesPackage.Literals.GV, initialValue);
			if (result != null && Diagnostician.INSTANCE.validate(eDataType, result, null, null)) {
				return result;
			}
		}
		catch (RuntimeException e) {
			exception = e;
		}
		if (result != null || exception == null) return result;
    
		throw exception;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertFloatOrGvsToString(EDataType eDataType, Object instanceValue) {
		if (instanceValue == null) return null;
		if (XMLTypePackage.Literals.FLOAT.isInstance(instanceValue)) {
			try {
				String value = XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.FLOAT, instanceValue);
				if (value != null) return value;
			}
			catch (Exception e) {
				// Keep trying other member types until all have failed.
			}
		}
		if (BasetypesPackage.Literals.GV.isInstance(instanceValue)) {
			try {
				String value = convertGvToString(BasetypesPackage.Literals.GV, instanceValue);
				if (value != null) return value;
			}
			catch (Exception e) {
				// Keep trying other member types until all have failed.
			}
		}
		throw new IllegalArgumentException("Invalid value: '"+instanceValue+"' for datatype :"+eDataType.getName());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String createGvFromString(EDataType eDataType, String initialValue) {
		return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertGvToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object createHexBinaryOrGVsFromString(EDataType eDataType, String initialValue) {
		if (initialValue == null) return null;
		Object result = null;
		RuntimeException exception = null;
		try {
			result = XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.HEX_BINARY, initialValue);
			if (result != null && Diagnostician.INSTANCE.validate(eDataType, result, null, null)) {
				return result;
			}
		}
		catch (RuntimeException e) {
			exception = e;
		}
		try {
			result = createGvFromString(BasetypesPackage.Literals.GV, initialValue);
			if (result != null && Diagnostician.INSTANCE.validate(eDataType, result, null, null)) {
				return result;
			}
		}
		catch (RuntimeException e) {
			exception = e;
		}
		if (result != null || exception == null) return result;
    
		throw exception;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertHexBinaryOrGVsToString(EDataType eDataType, Object instanceValue) {
		if (instanceValue == null) return null;
		if (XMLTypePackage.Literals.HEX_BINARY.isInstance(instanceValue)) {
			try {
				String value = XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.HEX_BINARY, instanceValue);
				if (value != null) return value;
			}
			catch (Exception e) {
				// Keep trying other member types until all have failed.
			}
		}
		if (BasetypesPackage.Literals.GV.isInstance(instanceValue)) {
			try {
				String value = convertGvToString(BasetypesPackage.Literals.GV, instanceValue);
				if (value != null) return value;
			}
			catch (Exception e) {
				// Keep trying other member types until all have failed.
			}
		}
		throw new IllegalArgumentException("Invalid value: '"+instanceValue+"' for datatype :"+eDataType.getName());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object createIntOrGVsFromString(EDataType eDataType, String initialValue) {
		if (initialValue == null) return null;
		Object result = null;
		RuntimeException exception = null;
		try {
			result = XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.INT, initialValue);
			if (result != null && Diagnostician.INSTANCE.validate(eDataType, result, null, null)) {
				return result;
			}
		}
		catch (RuntimeException e) {
			exception = e;
		}
		try {
			result = createGvFromString(BasetypesPackage.Literals.GV, initialValue);
			if (result != null && Diagnostician.INSTANCE.validate(eDataType, result, null, null)) {
				return result;
			}
		}
		catch (RuntimeException e) {
			exception = e;
		}
		if (result != null || exception == null) return result;
    
		throw exception;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertIntOrGVsToString(EDataType eDataType, Object instanceValue) {
		if (instanceValue == null) return null;
		if (XMLTypePackage.Literals.INT.isInstance(instanceValue)) {
			try {
				String value = XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.INT, instanceValue);
				if (value != null) return value;
			}
			catch (Exception e) {
				// Keep trying other member types until all have failed.
			}
		}
		if (BasetypesPackage.Literals.GV.isInstance(instanceValue)) {
			try {
				String value = convertGvToString(BasetypesPackage.Literals.GV, instanceValue);
				if (value != null) return value;
			}
			catch (Exception e) {
				// Keep trying other member types until all have failed.
			}
		}
		throw new IllegalArgumentException("Invalid value: '"+instanceValue+"' for datatype :"+eDataType.getName());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object createJndiPropertyTypeOrGvsFromString(EDataType eDataType, String initialValue) {
		if (initialValue == null) return null;
		Object result = null;
		RuntimeException exception = null;
		try {
			result = createGvFromString(BasetypesPackage.Literals.GV, initialValue);
			if (result != null && Diagnostician.INSTANCE.validate(eDataType, result, null, null)) {
				return result;
			}
		}
		catch (RuntimeException e) {
			exception = e;
		}
		try {
			result = createJndiPropertyTypeOrGvsEnumFromString(BasetypesPackage.Literals.JNDI_PROPERTY_TYPE_OR_GVS_ENUM, initialValue);
			if (result != null && Diagnostician.INSTANCE.validate(eDataType, result, null, null)) {
				return result;
			}
		}
		catch (RuntimeException e) {
			exception = e;
		}
		if (result != null || exception == null) return result;
    
		throw exception;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertJndiPropertyTypeOrGvsToString(EDataType eDataType, Object instanceValue) {
		if (instanceValue == null) return null;
		if (BasetypesPackage.Literals.GV.isInstance(instanceValue)) {
			try {
				String value = convertGvToString(BasetypesPackage.Literals.GV, instanceValue);
				if (value != null) return value;
			}
			catch (Exception e) {
				// Keep trying other member types until all have failed.
			}
		}
		if (BasetypesPackage.Literals.JNDI_PROPERTY_TYPE_OR_GVS_ENUM.isInstance(instanceValue)) {
			try {
				String value = convertJndiPropertyTypeOrGvsEnumToString(BasetypesPackage.Literals.JNDI_PROPERTY_TYPE_OR_GVS_ENUM, instanceValue);
				if (value != null) return value;
			}
			catch (Exception e) {
				// Keep trying other member types until all have failed.
			}
		}
		throw new IllegalArgumentException("Invalid value: '"+instanceValue+"' for datatype :"+eDataType.getName());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JndiPropertyTypeOrGvsEnum createJndiPropertyTypeOrGvsEnumObjectFromString(EDataType eDataType, String initialValue) {
		return createJndiPropertyTypeOrGvsEnumFromString(BasetypesPackage.Literals.JNDI_PROPERTY_TYPE_OR_GVS_ENUM, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertJndiPropertyTypeOrGvsEnumObjectToString(EDataType eDataType, Object instanceValue) {
		return convertJndiPropertyTypeOrGvsEnumToString(BasetypesPackage.Literals.JNDI_PROPERTY_TYPE_OR_GVS_ENUM, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object createLongOrGVsFromString(EDataType eDataType, String initialValue) {
		if (initialValue == null) return null;
		Object result = null;
		RuntimeException exception = null;
		try {
			result = XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.LONG, initialValue);
			if (result != null && Diagnostician.INSTANCE.validate(eDataType, result, null, null)) {
				return result;
			}
		}
		catch (RuntimeException e) {
			exception = e;
		}
		try {
			result = createGvFromString(BasetypesPackage.Literals.GV, initialValue);
			if (result != null && Diagnostician.INSTANCE.validate(eDataType, result, null, null)) {
				return result;
			}
		}
		catch (RuntimeException e) {
			exception = e;
		}
		if (result != null || exception == null) return result;
    
		throw exception;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertLongOrGVsToString(EDataType eDataType, Object instanceValue) {
		if (instanceValue == null) return null;
		if (XMLTypePackage.Literals.LONG.isInstance(instanceValue)) {
			try {
				String value = XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.LONG, instanceValue);
				if (value != null) return value;
			}
			catch (Exception e) {
				// Keep trying other member types until all have failed.
			}
		}
		if (BasetypesPackage.Literals.GV.isInstance(instanceValue)) {
			try {
				String value = convertGvToString(BasetypesPackage.Literals.GV, instanceValue);
				if (value != null) return value;
			}
			catch (Exception e) {
				// Keep trying other member types until all have failed.
			}
		}
		throw new IllegalArgumentException("Invalid value: '"+instanceValue+"' for datatype :"+eDataType.getName());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String createStringOrGVsFromString(EDataType eDataType, String initialValue) {
		if (initialValue == null) return null;
		String result = null;
		RuntimeException exception = null;
		try {
			result = createGvFromString(BasetypesPackage.Literals.GV, initialValue);
			if (result != null && Diagnostician.INSTANCE.validate(eDataType, result, null, null)) {
				return result;
			}
		}
		catch (RuntimeException e) {
			exception = e;
		}
		try {
			result = (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
			if (result != null && Diagnostician.INSTANCE.validate(eDataType, result, null, null)) {
				return result;
			}
		}
		catch (RuntimeException e) {
			exception = e;
		}
		if (result != null || exception == null) return result;
    
		throw exception;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertStringOrGVsToString(EDataType eDataType, Object instanceValue) {
		if (instanceValue == null) return null;
		if (BasetypesPackage.Literals.GV.isInstance(instanceValue)) {
			try {
				String value = convertGvToString(BasetypesPackage.Literals.GV, instanceValue);
				if (value != null) return value;
			}
			catch (Exception e) {
				// Keep trying other member types until all have failed.
			}
		}
		if (XMLTypePackage.Literals.STRING.isInstance(instanceValue)) {
			try {
				String value = XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
				if (value != null) return value;
			}
			catch (Exception e) {
				// Keep trying other member types until all have failed.
			}
		}
		throw new IllegalArgumentException("Invalid value: '"+instanceValue+"' for datatype :"+eDataType.getName());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BasetypesPackage getBasetypesPackage() {
		return (BasetypesPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static BasetypesPackage getPackage() {
		return BasetypesPackage.eINSTANCE;
	}

} //BasetypesFactoryImpl
