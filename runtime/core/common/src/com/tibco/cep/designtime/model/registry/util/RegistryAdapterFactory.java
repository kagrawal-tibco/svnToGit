/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.model.registry.util;

import com.tibco.cep.designtime.model.registry.*;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

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
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.designtime.model.registry.RegistryPackage
 * @generated
 */
public class RegistryAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static RegistryPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RegistryAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = RegistryPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RegistrySwitch<Adapter> modelSwitch =
		new RegistrySwitch<Adapter>() {
			@Override
			public Adapter caseAddOn(AddOn object) {
				return createAddOnAdapter();
			}
			@Override
			public Adapter caseFileExtensionMapItemType(FileExtensionMapItemType object) {
				return createFileExtensionMapItemTypeAdapter();
			}
			@Override
			public Adapter caseFileExtensionMapType(FileExtensionMapType object) {
				return createFileExtensionMapTypeAdapter();
			}
			@Override
			public Adapter caseRegistry(Registry object) {
				return createRegistryAdapter();
			}
			@Override
			public Adapter caseSupportedElementTypes(SupportedElementTypes object) {
				return createSupportedElementTypesAdapter();
			}
			@Override
			public Adapter caseSupportedExtensions(SupportedExtensions object) {
				return createSupportedExtensionsAdapter();
			}
			@Override
			public Adapter caseTnsEntityExtensions(TnsEntityExtensions object) {
				return createTnsEntityExtensionsAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.model.registry.AddOn <em>Add On</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.model.registry.AddOn
	 * @generated
	 */
	public Adapter createAddOnAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.model.registry.FileExtensionMapItemType <em>File Extension Map Item Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.model.registry.FileExtensionMapItemType
	 * @generated
	 */
	public Adapter createFileExtensionMapItemTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.model.registry.FileExtensionMapType <em>File Extension Map Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.model.registry.FileExtensionMapType
	 * @generated
	 */
	public Adapter createFileExtensionMapTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.model.registry.Registry <em>Registry</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.model.registry.Registry
	 * @generated
	 */
	public Adapter createRegistryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.model.registry.SupportedElementTypes <em>Supported Element Types</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.model.registry.SupportedElementTypes
	 * @generated
	 */
	public Adapter createSupportedElementTypesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.model.registry.SupportedExtensions <em>Supported Extensions</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.model.registry.SupportedExtensions
	 * @generated
	 */
	public Adapter createSupportedExtensionsAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.model.registry.TnsEntityExtensions <em>Tns Entity Extensions</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.model.registry.TnsEntityExtensions
	 * @generated
	 */
	public Adapter createTnsEntityExtensionsAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //RegistryAdapterFactory
