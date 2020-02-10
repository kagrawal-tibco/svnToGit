/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.aemetaservices.util;

import com.tibco.be.util.config.sharedresources.aemetaservices.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.be.util.config.sharedresources.aemetaservices.AemetaservicesPackage
 * @generated
 */
public class AemetaservicesAdapterFactory extends AdapterFactoryImpl {
	/**
     * The cached model package.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	protected static AemetaservicesPackage modelPackage;

	/**
     * Creates an instance of the adapter factory.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public AemetaservicesAdapterFactory() {
        if (modelPackage == null)
        {
            modelPackage = AemetaservicesPackage.eINSTANCE;
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
	protected AemetaservicesSwitch<Adapter> modelSwitch =
		new AemetaservicesSwitch<Adapter>()
        {
            @Override
            public Adapter caseAeMetaServicesRoot(AeMetaServicesRoot object)
            {
                return createAeMetaServicesRootAdapter();
            }
            @Override
            public Adapter caseCertType(CertType object)
            {
                return createCertTypeAdapter();
            }
            @Override
            public Adapter caseIdentityType(IdentityType object)
            {
                return createIdentityTypeAdapter();
            }
            @Override
            public Adapter caseSsl(Ssl object)
            {
                return createSslAdapter();
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
     * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.sharedresources.aemetaservices.AeMetaServicesRoot <em>Ae Meta Services Root</em>}'.
     * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.be.util.config.sharedresources.aemetaservices.AeMetaServicesRoot
     * @generated
     */
	public Adapter createAeMetaServicesRootAdapter() {
        return null;
    }

	/**
     * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.sharedresources.aemetaservices.CertType <em>Cert Type</em>}'.
     * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.be.util.config.sharedresources.aemetaservices.CertType
     * @generated
     */
	public Adapter createCertTypeAdapter() {
        return null;
    }

	/**
     * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.sharedresources.aemetaservices.IdentityType <em>Identity Type</em>}'.
     * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.be.util.config.sharedresources.aemetaservices.IdentityType
     * @generated
     */
	public Adapter createIdentityTypeAdapter() {
        return null;
    }

	/**
     * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.sharedresources.aemetaservices.Ssl <em>Ssl</em>}'.
     * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.be.util.config.sharedresources.aemetaservices.Ssl
     * @generated
     */
	public Adapter createSslAdapter() {
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

} //AemetaservicesAdapterFactory
