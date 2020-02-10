/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.model.registry.impl;

import com.tibco.cep.designtime.model.registry.*;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import com.tibco.cep.designtime.model.registry.AddOn;
import com.tibco.cep.designtime.model.registry.AddOnType;
import com.tibco.cep.designtime.model.registry.ElementTypes;
import com.tibco.cep.designtime.model.registry.FileExtensionMapItemType;
import com.tibco.cep.designtime.model.registry.FileExtensionMapType;
import com.tibco.cep.designtime.model.registry.FileExtensionTypes;
import com.tibco.cep.designtime.model.registry.Registry;
import com.tibco.cep.designtime.model.registry.RegistryFactory;
import com.tibco.cep.designtime.model.registry.RegistryPackage;
import com.tibco.cep.designtime.model.registry.SupportedElementTypes;
import com.tibco.cep.designtime.model.registry.SupportedExtensions;
import com.tibco.cep.designtime.model.registry.TnsEntityExtensions;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class RegistryFactoryImpl extends EFactoryImpl implements RegistryFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static RegistryFactory init() {
		try {
			RegistryFactory theRegistryFactory = (RegistryFactory)EPackage.Registry.INSTANCE.getEFactory("http:///www.tibco.com/be/addon_registry.xsd"); 
			if (theRegistryFactory != null) {
				return theRegistryFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new RegistryFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RegistryFactoryImpl() {
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
			case RegistryPackage.ADD_ON: return createAddOn();
			case RegistryPackage.FILE_EXTENSION_MAP_ITEM_TYPE: return createFileExtensionMapItemType();
			case RegistryPackage.FILE_EXTENSION_MAP_TYPE: return createFileExtensionMapType();
			case RegistryPackage.REGISTRY: return createRegistry();
			case RegistryPackage.SUPPORTED_ELEMENT_TYPES: return createSupportedElementTypes();
			case RegistryPackage.SUPPORTED_EXTENSIONS: return createSupportedExtensions();
			case RegistryPackage.TNS_ENTITY_EXTENSIONS: return createTnsEntityExtensions();
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
			case RegistryPackage.ADD_ON_TYPE:
				return createAddOnTypeFromString(eDataType, initialValue);
			case RegistryPackage.ELEMENT_TYPES:
				return createElementTypesFromString(eDataType, initialValue);
			case RegistryPackage.FILE_EXTENSION_TYPES:
				return createFileExtensionTypesFromString(eDataType, initialValue);
			case RegistryPackage.ADD_ON_TYPE_OBJECT:
				return createAddOnTypeObjectFromString(eDataType, initialValue);
			case RegistryPackage.ELEMENT_TYPES_OBJECT:
				return createElementTypesObjectFromString(eDataType, initialValue);
			case RegistryPackage.FILE_EXTENSION_TYPES_OBJECT:
				return createFileExtensionTypesObjectFromString(eDataType, initialValue);
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
			case RegistryPackage.ADD_ON_TYPE:
				return convertAddOnTypeToString(eDataType, instanceValue);
			case RegistryPackage.ELEMENT_TYPES:
				return convertElementTypesToString(eDataType, instanceValue);
			case RegistryPackage.FILE_EXTENSION_TYPES:
				return convertFileExtensionTypesToString(eDataType, instanceValue);
			case RegistryPackage.ADD_ON_TYPE_OBJECT:
				return convertAddOnTypeObjectToString(eDataType, instanceValue);
			case RegistryPackage.ELEMENT_TYPES_OBJECT:
				return convertElementTypesObjectToString(eDataType, instanceValue);
			case RegistryPackage.FILE_EXTENSION_TYPES_OBJECT:
				return convertFileExtensionTypesObjectToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AddOn createAddOn() {
		AddOnImpl addOn = new AddOnImpl();
		return addOn;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FileExtensionMapItemType createFileExtensionMapItemType() {
		FileExtensionMapItemTypeImpl fileExtensionMapItemType = new FileExtensionMapItemTypeImpl();
		return fileExtensionMapItemType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FileExtensionMapType createFileExtensionMapType() {
		FileExtensionMapTypeImpl fileExtensionMapType = new FileExtensionMapTypeImpl();
		return fileExtensionMapType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Registry createRegistry() {
		RegistryImpl registry = new RegistryImpl();
		return registry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SupportedElementTypes createSupportedElementTypes() {
		SupportedElementTypesImpl supportedElementTypes = new SupportedElementTypesImpl();
		return supportedElementTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SupportedExtensions createSupportedExtensions() {
		SupportedExtensionsImpl supportedExtensions = new SupportedExtensionsImpl();
		return supportedExtensions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TnsEntityExtensions createTnsEntityExtensions() {
		TnsEntityExtensionsImpl tnsEntityExtensions = new TnsEntityExtensionsImpl();
		return tnsEntityExtensions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AddOnType createAddOnTypeFromString(EDataType eDataType, String initialValue) {
		AddOnType result = AddOnType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertAddOnTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ElementTypes createElementTypesFromString(EDataType eDataType, String initialValue) {
		ElementTypes result = ElementTypes.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertElementTypesToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FileExtensionTypes createFileExtensionTypesFromString(EDataType eDataType, String initialValue) {
		FileExtensionTypes result = FileExtensionTypes.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertFileExtensionTypesToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AddOnType createAddOnTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createAddOnTypeFromString(RegistryPackage.Literals.ADD_ON_TYPE, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertAddOnTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertAddOnTypeToString(RegistryPackage.Literals.ADD_ON_TYPE, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ElementTypes createElementTypesObjectFromString(EDataType eDataType, String initialValue) {
		return createElementTypesFromString(RegistryPackage.Literals.ELEMENT_TYPES, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertElementTypesObjectToString(EDataType eDataType, Object instanceValue) {
		return convertElementTypesToString(RegistryPackage.Literals.ELEMENT_TYPES, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FileExtensionTypes createFileExtensionTypesObjectFromString(EDataType eDataType, String initialValue) {
		return createFileExtensionTypesFromString(RegistryPackage.Literals.FILE_EXTENSION_TYPES, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertFileExtensionTypesObjectToString(EDataType eDataType, Object instanceValue) {
		return convertFileExtensionTypesToString(RegistryPackage.Literals.FILE_EXTENSION_TYPES, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RegistryPackage getRegistryPackage() {
		return (RegistryPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static RegistryPackage getPackage() {
		return RegistryPackage.eINSTANCE;
	}

} //RegistryFactoryImpl
