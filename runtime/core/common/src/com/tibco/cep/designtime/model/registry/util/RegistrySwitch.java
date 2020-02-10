/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.model.registry.util;

import com.tibco.cep.designtime.model.registry.*;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

import com.tibco.cep.designtime.model.registry.AddOn;
import com.tibco.cep.designtime.model.registry.FileExtensionMapItemType;
import com.tibco.cep.designtime.model.registry.FileExtensionMapType;
import com.tibco.cep.designtime.model.registry.Registry;
import com.tibco.cep.designtime.model.registry.RegistryPackage;
import com.tibco.cep.designtime.model.registry.SupportedElementTypes;
import com.tibco.cep.designtime.model.registry.SupportedExtensions;
import com.tibco.cep.designtime.model.registry.TnsEntityExtensions;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.designtime.model.registry.RegistryPackage
 * @generated
 */
public class RegistrySwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static RegistryPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RegistrySwitch() {
		if (modelPackage == null) {
			modelPackage = RegistryPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @parameter ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case RegistryPackage.ADD_ON: {
				AddOn addOn = (AddOn)theEObject;
				T result = caseAddOn(addOn);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RegistryPackage.FILE_EXTENSION_MAP_ITEM_TYPE: {
				FileExtensionMapItemType fileExtensionMapItemType = (FileExtensionMapItemType)theEObject;
				T result = caseFileExtensionMapItemType(fileExtensionMapItemType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RegistryPackage.FILE_EXTENSION_MAP_TYPE: {
				FileExtensionMapType fileExtensionMapType = (FileExtensionMapType)theEObject;
				T result = caseFileExtensionMapType(fileExtensionMapType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RegistryPackage.REGISTRY: {
				Registry registry = (Registry)theEObject;
				T result = caseRegistry(registry);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RegistryPackage.SUPPORTED_ELEMENT_TYPES: {
				SupportedElementTypes supportedElementTypes = (SupportedElementTypes)theEObject;
				T result = caseSupportedElementTypes(supportedElementTypes);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RegistryPackage.SUPPORTED_EXTENSIONS: {
				SupportedExtensions supportedExtensions = (SupportedExtensions)theEObject;
				T result = caseSupportedExtensions(supportedExtensions);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RegistryPackage.TNS_ENTITY_EXTENSIONS: {
				TnsEntityExtensions tnsEntityExtensions = (TnsEntityExtensions)theEObject;
				T result = caseTnsEntityExtensions(tnsEntityExtensions);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Add On</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Add On</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAddOn(AddOn object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>File Extension Map Item Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>File Extension Map Item Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFileExtensionMapItemType(FileExtensionMapItemType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>File Extension Map Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>File Extension Map Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFileExtensionMapType(FileExtensionMapType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Registry</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Registry</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRegistry(Registry object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Supported Element Types</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Supported Element Types</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSupportedElementTypes(SupportedElementTypes object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Supported Extensions</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Supported Extensions</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSupportedExtensions(SupportedExtensions object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Tns Entity Extensions</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Tns Entity Extensions</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTnsEntityExtensions(TnsEntityExtensions object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //RegistrySwitch
