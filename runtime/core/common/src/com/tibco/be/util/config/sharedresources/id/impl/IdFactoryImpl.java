/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.id.impl;

import com.tibco.be.util.config.sharedresources.basetypes.BasetypesFactory;
import com.tibco.be.util.config.sharedresources.basetypes.BasetypesPackage;

import com.tibco.be.util.config.sharedresources.id.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.emf.ecore.util.Diagnostician;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class IdFactoryImpl extends EFactoryImpl implements IdFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static IdFactory init() {
		try {
			IdFactory theIdFactory = (IdFactory)EPackage.Registry.INSTANCE.getEFactory(IdPackage.eNS_URI);
			if (theIdFactory != null) {
				return theIdFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new IdFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IdFactoryImpl() {
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
			case IdPackage.DESIGNER: return createDesigner();
			case IdPackage.IDENTITY: return createIdentity();
			case IdPackage.ID_ROOT: return createIdRoot();
			case IdPackage.NODE: return createNode();
			case IdPackage.REPOSITORY: return createRepository();
			case IdPackage.RESOURCE_DESCRIPTIONS: return createResourceDescriptions();
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
			case IdPackage.FILE_TYPE_ENUM:
				return createFileTypeEnumFromString(eDataType, initialValue);
			case IdPackage.OBJECT_TYPE_ENUM:
				return createObjectTypeEnumFromString(eDataType, initialValue);
			case IdPackage.FILE_TYPE:
				return createFileTypeFromString(eDataType, initialValue);
			case IdPackage.FILE_TYPE_ENUM_OBJECT:
				return createFileTypeEnumObjectFromString(eDataType, initialValue);
			case IdPackage.OBJECT_TYPE:
				return createObjectTypeFromString(eDataType, initialValue);
			case IdPackage.OBJECT_TYPE_ENUM_OBJECT:
				return createObjectTypeEnumObjectFromString(eDataType, initialValue);
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
			case IdPackage.FILE_TYPE_ENUM:
				return convertFileTypeEnumToString(eDataType, instanceValue);
			case IdPackage.OBJECT_TYPE_ENUM:
				return convertObjectTypeEnumToString(eDataType, instanceValue);
			case IdPackage.FILE_TYPE:
				return convertFileTypeToString(eDataType, instanceValue);
			case IdPackage.FILE_TYPE_ENUM_OBJECT:
				return convertFileTypeEnumObjectToString(eDataType, instanceValue);
			case IdPackage.OBJECT_TYPE:
				return convertObjectTypeToString(eDataType, instanceValue);
			case IdPackage.OBJECT_TYPE_ENUM_OBJECT:
				return convertObjectTypeEnumObjectToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Designer createDesigner() {
		DesignerImpl designer = new DesignerImpl();
		return designer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Identity createIdentity() {
		IdentityImpl identity = new IdentityImpl();
		return identity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IdRoot createIdRoot() {
		IdRootImpl idRoot = new IdRootImpl();
		return idRoot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node createNode() {
		NodeImpl node = new NodeImpl();
		return node;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Repository createRepository() {
		RepositoryImpl repository = new RepositoryImpl();
		return repository;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ResourceDescriptions createResourceDescriptions() {
		ResourceDescriptionsImpl resourceDescriptions = new ResourceDescriptionsImpl();
		return resourceDescriptions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FileTypeEnum createFileTypeEnumFromString(EDataType eDataType, String initialValue) {
		FileTypeEnum result = FileTypeEnum.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertFileTypeEnumToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ObjectTypeEnum createObjectTypeEnumFromString(EDataType eDataType, String initialValue) {
		ObjectTypeEnum result = ObjectTypeEnum.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertObjectTypeEnumToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object createObjectTypeFromString(EDataType eDataType, String initialValue) {
		if (initialValue == null) return null;
		Object result = null;
		RuntimeException exception = null;
		try {
			result = BasetypesFactory.eINSTANCE.createFromString(BasetypesPackage.Literals.GV, initialValue);
			if (result != null && Diagnostician.INSTANCE.validate(eDataType, result, null, null)) {
				return result;
			}
		}
		catch (RuntimeException e) {
			exception = e;
		}
		try {
			result = createObjectTypeEnumFromString(IdPackage.Literals.OBJECT_TYPE_ENUM, initialValue);
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
	public String convertObjectTypeToString(EDataType eDataType, Object instanceValue) {
		if (instanceValue == null) return null;
		if (BasetypesPackage.Literals.GV.isInstance(instanceValue)) {
			try {
				String value = BasetypesFactory.eINSTANCE.convertToString(BasetypesPackage.Literals.GV, instanceValue);
				if (value != null) return value;
			}
			catch (Exception e) {
				// Keep trying other member types until all have failed.
			}
		}
		if (IdPackage.Literals.OBJECT_TYPE_ENUM.isInstance(instanceValue)) {
			try {
				String value = convertObjectTypeEnumToString(IdPackage.Literals.OBJECT_TYPE_ENUM, instanceValue);
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
	public ObjectTypeEnum createObjectTypeEnumObjectFromString(EDataType eDataType, String initialValue) {
		return createObjectTypeEnumFromString(IdPackage.Literals.OBJECT_TYPE_ENUM, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertObjectTypeEnumObjectToString(EDataType eDataType, Object instanceValue) {
		return convertObjectTypeEnumToString(IdPackage.Literals.OBJECT_TYPE_ENUM, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object createFileTypeFromString(EDataType eDataType, String initialValue) {
		if (initialValue == null) return null;
		Object result = null;
		RuntimeException exception = null;
		try {
			result = BasetypesFactory.eINSTANCE.createFromString(BasetypesPackage.Literals.GV, initialValue);
			if (result != null && Diagnostician.INSTANCE.validate(eDataType, result, null, null)) {
				return result;
			}
		}
		catch (RuntimeException e) {
			exception = e;
		}
		try {
			result = createFileTypeEnumFromString(IdPackage.Literals.FILE_TYPE_ENUM, initialValue);
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
	public String convertFileTypeToString(EDataType eDataType, Object instanceValue) {
		if (instanceValue == null) return null;
		if (BasetypesPackage.Literals.GV.isInstance(instanceValue)) {
			try {
				String value = BasetypesFactory.eINSTANCE.convertToString(BasetypesPackage.Literals.GV, instanceValue);
				if (value != null) return value;
			}
			catch (Exception e) {
				// Keep trying other member types until all have failed.
			}
		}
		if (IdPackage.Literals.FILE_TYPE_ENUM.isInstance(instanceValue)) {
			try {
				String value = convertFileTypeEnumToString(IdPackage.Literals.FILE_TYPE_ENUM, instanceValue);
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
	public FileTypeEnum createFileTypeEnumObjectFromString(EDataType eDataType, String initialValue) {
		return createFileTypeEnumFromString(IdPackage.Literals.FILE_TYPE_ENUM, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertFileTypeEnumObjectToString(EDataType eDataType, Object instanceValue) {
		return convertFileTypeEnumToString(IdPackage.Literals.FILE_TYPE_ENUM, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IdPackage getIdPackage() {
		return (IdPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static IdPackage getPackage() {
		return IdPackage.eINSTANCE;
	}

} //IdFactoryImpl
