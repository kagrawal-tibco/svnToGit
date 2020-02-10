/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import com.tibco.cep.designtime.core.model.DOMAIN_DATA_TYPES;
import com.tibco.cep.designtime.core.model.HISTORY_POLICY;
import com.tibco.cep.designtime.core.model.METRIC_AGGR_TYPE;
import com.tibco.cep.designtime.core.model.METRIC_TYPE;
import com.tibco.cep.designtime.core.model.ModelFactory;
import com.tibco.cep.designtime.core.model.ModelPackage;
import com.tibco.cep.designtime.core.model.ObjectProperty;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.PropertyMap;
import com.tibco.cep.designtime.core.model.SimpleProperty;
import com.tibco.cep.designtime.core.model.TIMEOUT_UNITS;
import com.tibco.cep.designtime.core.model.TIME_UNITS;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ModelFactoryImpl extends EFactoryImpl implements ModelFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ModelFactory init() {
		try {
			ModelFactory theModelFactory = (ModelFactory)EPackage.Registry.INSTANCE.getEFactory(ModelPackage.eNS_URI);
			if (theModelFactory != null) {
				return theModelFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ModelFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelFactoryImpl() {
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
			case ModelPackage.SIMPLE_PROPERTY: return createSimpleProperty();
			case ModelPackage.OBJECT_PROPERTY: return createObjectProperty();
			case ModelPackage.PROPERTY_MAP: return createPropertyMap();
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
			case ModelPackage.PROPERTY_TYPES:
				return createPROPERTY_TYPESFromString(eDataType, initialValue);
			case ModelPackage.TIMEOUT_UNITS:
				return createTIMEOUT_UNITSFromString(eDataType, initialValue);
			case ModelPackage.METRIC_TYPE:
				return createMETRIC_TYPEFromString(eDataType, initialValue);
			case ModelPackage.METRIC_AGGR_TYPE:
				return createMETRIC_AGGR_TYPEFromString(eDataType, initialValue);
			case ModelPackage.HISTORY_POLICY:
				return createHISTORY_POLICYFromString(eDataType, initialValue);
			case ModelPackage.DOMAIN_DATA_TYPES:
				return createDOMAIN_DATA_TYPESFromString(eDataType, initialValue);
			case ModelPackage.TIME_UNITS:
				return createTIME_UNITSFromString(eDataType, initialValue);
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
			case ModelPackage.PROPERTY_TYPES:
				return convertPROPERTY_TYPESToString(eDataType, instanceValue);
			case ModelPackage.TIMEOUT_UNITS:
				return convertTIMEOUT_UNITSToString(eDataType, instanceValue);
			case ModelPackage.METRIC_TYPE:
				return convertMETRIC_TYPEToString(eDataType, instanceValue);
			case ModelPackage.METRIC_AGGR_TYPE:
				return convertMETRIC_AGGR_TYPEToString(eDataType, instanceValue);
			case ModelPackage.HISTORY_POLICY:
				return convertHISTORY_POLICYToString(eDataType, instanceValue);
			case ModelPackage.DOMAIN_DATA_TYPES:
				return convertDOMAIN_DATA_TYPESToString(eDataType, instanceValue);
			case ModelPackage.TIME_UNITS:
				return convertTIME_UNITSToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SimpleProperty createSimpleProperty() {
		SimplePropertyImpl simpleProperty = new SimplePropertyImpl();
		return simpleProperty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ObjectProperty createObjectProperty() {
		ObjectPropertyImpl objectProperty = new ObjectPropertyImpl();
		return objectProperty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyMap createPropertyMap() {
		PropertyMapImpl propertyMap = new PropertyMapImpl();
		return propertyMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PROPERTY_TYPES createPROPERTY_TYPESFromString(EDataType eDataType, String initialValue) {
		PROPERTY_TYPES result = PROPERTY_TYPES.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPROPERTY_TYPESToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TIMEOUT_UNITS createTIMEOUT_UNITSFromString(EDataType eDataType, String initialValue) {
		TIMEOUT_UNITS result = TIMEOUT_UNITS.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertTIMEOUT_UNITSToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public METRIC_TYPE createMETRIC_TYPEFromString(EDataType eDataType, String initialValue) {
		METRIC_TYPE result = METRIC_TYPE.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertMETRIC_TYPEToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public METRIC_AGGR_TYPE createMETRIC_AGGR_TYPEFromString(EDataType eDataType, String initialValue) {
		METRIC_AGGR_TYPE result = METRIC_AGGR_TYPE.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertMETRIC_AGGR_TYPEToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HISTORY_POLICY createHISTORY_POLICYFromString(EDataType eDataType, String initialValue) {
		HISTORY_POLICY result = HISTORY_POLICY.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertHISTORY_POLICYToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DOMAIN_DATA_TYPES createDOMAIN_DATA_TYPESFromString(EDataType eDataType, String initialValue) {
		DOMAIN_DATA_TYPES result = DOMAIN_DATA_TYPES.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDOMAIN_DATA_TYPESToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TIME_UNITS createTIME_UNITSFromString(EDataType eDataType, String initialValue) {
		TIME_UNITS result = TIME_UNITS.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertTIME_UNITSToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelPackage getModelPackage() {
		return (ModelPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ModelPackage getPackage() {
		return ModelPackage.eINSTANCE;
	}

} //ModelFactoryImpl
