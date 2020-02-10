/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.sharedjndiconfig.util;

import com.tibco.be.util.config.sharedresources.sharedjndiconfig.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.SharedjndiconfigPackage
 * @generated
 */
public class SharedjndiconfigAdapterFactory extends AdapterFactoryImpl {
	/**
     * The cached model package.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	protected static SharedjndiconfigPackage modelPackage;

	/**
     * Creates an instance of the adapter factory.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public SharedjndiconfigAdapterFactory() {
        if (modelPackage == null)
        {
            modelPackage = SharedjndiconfigPackage.eINSTANCE;
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
        if (object == modelPackage)
        {
            return true;
        }
        if (object instanceof EObject)
        {
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
	protected SharedjndiconfigSwitch<Adapter> modelSwitch =
		new SharedjndiconfigSwitch<Adapter>()
        {
            @Override
            public Adapter caseBwSharedResource(BwSharedResource object)
            {
                return createBwSharedResourceAdapter();
            }
            @Override
            public Adapter caseConfig(Config object)
            {
                return createConfigAdapter();
            }
            @Override
            public Adapter caseOptionalJndiProperties(OptionalJndiProperties object)
            {
                return createOptionalJndiPropertiesAdapter();
            }
            @Override
            public Adapter caseRow(Row object)
            {
                return createRowAdapter();
            }
            @Override
            public Adapter caseSharedJndiConfigRoot(SharedJndiConfigRoot object)
            {
                return createSharedJndiConfigRootAdapter();
            }
            @Override
            public Adapter defaultCase(EObject object)
            {
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
     * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.BwSharedResource <em>Bw Shared Resource</em>}'.
     * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.BwSharedResource
     * @generated
     */
	public Adapter createBwSharedResourceAdapter() {
        return null;
    }

	/**
     * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.Config <em>Config</em>}'.
     * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.Config
     * @generated
     */
	public Adapter createConfigAdapter() {
        return null;
    }

	/**
     * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.OptionalJndiProperties <em>Optional Jndi Properties</em>}'.
     * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.OptionalJndiProperties
     * @generated
     */
	public Adapter createOptionalJndiPropertiesAdapter() {
        return null;
    }

	/**
     * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.Row <em>Row</em>}'.
     * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.Row
     * @generated
     */
	public Adapter createRowAdapter() {
        return null;
    }

	/**
     * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.SharedJndiConfigRoot <em>Shared Jndi Config Root</em>}'.
     * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.SharedJndiConfigRoot
     * @generated
     */
	public Adapter createSharedJndiConfigRootAdapter() {
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

} //SharedjndiconfigAdapterFactory
