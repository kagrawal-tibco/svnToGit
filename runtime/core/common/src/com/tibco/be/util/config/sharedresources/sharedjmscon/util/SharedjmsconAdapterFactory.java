/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.sharedjmscon.util;

import com.tibco.be.util.config.sharedresources.sharedjmscon.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.SharedjmsconPackage
 * @generated
 */
public class SharedjmsconAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static SharedjmsconPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SharedjmsconAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = SharedjmsconPackage.eINSTANCE;
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
	protected SharedjmsconSwitch<Adapter> modelSwitch =
		new SharedjmsconSwitch<Adapter>() {
			@Override
			public Adapter caseBwSharedResource(BwSharedResource object) {
				return createBwSharedResourceAdapter();
			}
			@Override
			public Adapter caseConfig(Config object) {
				return createConfigAdapter();
			}
			@Override
			public Adapter caseConnectionAttributes(ConnectionAttributes object) {
				return createConnectionAttributesAdapter();
			}
			@Override
			public Adapter caseJndiProperties(JndiProperties object) {
				return createJndiPropertiesAdapter();
			}
			@Override
			public Adapter caseNamingEnvironment(NamingEnvironment object) {
				return createNamingEnvironmentAdapter();
			}
			@Override
			public Adapter caseRow(Row object) {
				return createRowAdapter();
			}
			@Override
			public Adapter caseSharedJmsConRoot(SharedJmsConRoot object) {
				return createSharedJmsConRootAdapter();
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
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.BwSharedResource <em>Bw Shared Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.BwSharedResource
	 * @generated
	 */
	public Adapter createBwSharedResourceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.Config <em>Config</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.Config
	 * @generated
	 */
	public Adapter createConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.ConnectionAttributes <em>Connection Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.ConnectionAttributes
	 * @generated
	 */
	public Adapter createConnectionAttributesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.JndiProperties <em>Jndi Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.JndiProperties
	 * @generated
	 */
	public Adapter createJndiPropertiesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.NamingEnvironment <em>Naming Environment</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.NamingEnvironment
	 * @generated
	 */
	public Adapter createNamingEnvironmentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.Row <em>Row</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.Row
	 * @generated
	 */
	public Adapter createRowAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.SharedJmsConRoot <em>Shared Jms Con Root</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.SharedJmsConRoot
	 * @generated
	 */
	public Adapter createSharedJmsConRootAdapter() {
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

} //SharedjmsconAdapterFactory
