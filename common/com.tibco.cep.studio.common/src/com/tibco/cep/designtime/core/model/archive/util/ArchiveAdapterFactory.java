/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.archive.util;

import com.tibco.cep.designtime.core.model.archive.*;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.archive.AdapterArchive;
import com.tibco.cep.designtime.core.model.archive.AdvancedEntitySetting;
import com.tibco.cep.designtime.core.model.archive.ArchivePackage;
import com.tibco.cep.designtime.core.model.archive.ArchiveResource;
import com.tibco.cep.designtime.core.model.archive.BEArchiveResource;
import com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource;
import com.tibco.cep.designtime.core.model.archive.EnterpriseArchive;
import com.tibco.cep.designtime.core.model.archive.InputDestination;
import com.tibco.cep.designtime.core.model.archive.ProcessArchive;
import com.tibco.cep.designtime.core.model.archive.SharedArchive;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage
 * @generated
 */
public class ArchiveAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ArchivePackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArchiveAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = ArchivePackage.eINSTANCE;
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
	protected ArchiveSwitch<Adapter> modelSwitch =
		new ArchiveSwitch<Adapter>() {
			@Override
			public Adapter caseArchiveResource(ArchiveResource object) {
				return createArchiveResourceAdapter();
			}
			@Override
			public Adapter caseEnterpriseArchive(EnterpriseArchive object) {
				return createEnterpriseArchiveAdapter();
			}
			@Override
			public Adapter caseBEArchiveResource(BEArchiveResource object) {
				return createBEArchiveResourceAdapter();
			}
			@Override
			public Adapter caseBusinessEventsArchiveResource(BusinessEventsArchiveResource object) {
				return createBusinessEventsArchiveResourceAdapter();
			}
			@Override
			public Adapter caseInputDestination(InputDestination object) {
				return createInputDestinationAdapter();
			}
			@Override
			public Adapter caseAdvancedEntitySetting(AdvancedEntitySetting object) {
				return createAdvancedEntitySettingAdapter();
			}
			@Override
			public Adapter caseSharedArchive(SharedArchive object) {
				return createSharedArchiveAdapter();
			}
			@Override
			public Adapter caseProcessArchive(ProcessArchive object) {
				return createProcessArchiveAdapter();
			}
			@Override
			public Adapter caseAdapterArchive(AdapterArchive object) {
				return createAdapterArchiveAdapter();
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
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.archive.ArchiveResource <em>Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.archive.ArchiveResource
	 * @generated
	 */
	public Adapter createArchiveResourceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.archive.EnterpriseArchive <em>Enterprise Archive</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.archive.EnterpriseArchive
	 * @generated
	 */
	public Adapter createEnterpriseArchiveAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.archive.BEArchiveResource <em>BE Archive Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.archive.BEArchiveResource
	 * @generated
	 */
	public Adapter createBEArchiveResourceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource <em>Business Events Archive Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource
	 * @generated
	 */
	public Adapter createBusinessEventsArchiveResourceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.archive.InputDestination <em>Input Destination</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.archive.InputDestination
	 * @generated
	 */
	public Adapter createInputDestinationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.archive.AdvancedEntitySetting <em>Advanced Entity Setting</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.archive.AdvancedEntitySetting
	 * @generated
	 */
	public Adapter createAdvancedEntitySettingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.archive.SharedArchive <em>Shared Archive</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.archive.SharedArchive
	 * @generated
	 */
	public Adapter createSharedArchiveAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.archive.ProcessArchive <em>Process Archive</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.archive.ProcessArchive
	 * @generated
	 */
	public Adapter createProcessArchiveAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.archive.AdapterArchive <em>Adapter Archive</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.archive.AdapterArchive
	 * @generated
	 */
	public Adapter createAdapterArchiveAdapter() {
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

} //ArchiveAdapterFactory
