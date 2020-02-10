/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.table.model.domainmodel.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.decision.table.model.domainmodel.Domain;
import com.tibco.cep.decision.table.model.domainmodel.DomainEntry;
import com.tibco.cep.decision.table.model.domainmodel.DomainModelPackage;
import com.tibco.cep.decision.table.model.domainmodel.EntryValue;
import com.tibco.cep.decision.table.model.domainmodel.RangeInfo;
import com.tibco.cep.decision.table.model.domainmodel.SingleValue;
import com.tibco.cep.decisionproject.ontology.AccessControlCandidate;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.decision.table.model.domainmodel.DomainModelPackage
 * @generated
 */
public class DomainModelAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static DomainModelPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DomainModelAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = DomainModelPackage.eINSTANCE;
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
	protected DomainModelSwitch<Adapter> modelSwitch =
		new DomainModelSwitch<Adapter>() {
			@Override
			public Adapter caseDomainEntry(DomainEntry object) {
				return createDomainEntryAdapter();
			}
			@Override
			public Adapter caseRangeInfo(RangeInfo object) {
				return createRangeInfoAdapter();
			}
			@Override
			public Adapter caseSingleValue(SingleValue object) {
				return createSingleValueAdapter();
			}
			@Override
			public Adapter caseEntryValue(EntryValue object) {
				return createEntryValueAdapter();
			}
			@Override
			public Adapter caseDomain(Domain object) {
				return createDomainAdapter();
			}
			@Override
			public Adapter caseAccessControlCandidate(AccessControlCandidate object) {
				return createAccessControlCandidateAdapter();
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
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.decision.table.model.domainmodel.DomainEntry <em>Domain Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.decision.table.model.domainmodel.DomainEntry
	 * @generated
	 */
	public Adapter createDomainEntryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.decision.table.model.domainmodel.RangeInfo <em>Range Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.decision.table.model.domainmodel.RangeInfo
	 * @generated
	 */
	public Adapter createRangeInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.decision.table.model.domainmodel.SingleValue <em>Single Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.decision.table.model.domainmodel.SingleValue
	 * @generated
	 */
	public Adapter createSingleValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.decision.table.model.domainmodel.EntryValue <em>Entry Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.decision.table.model.domainmodel.EntryValue
	 * @generated
	 */
	public Adapter createEntryValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.decision.table.model.domainmodel.Domain <em>Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.decision.table.model.domainmodel.Domain
	 * @generated
	 */
	public Adapter createDomainAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.decisionproject.ontology.AccessControlCandidate <em>Access Control Candidate</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.decisionproject.ontology.AccessControlCandidate
	 * @generated
	 */
	public Adapter createAccessControlCandidateAdapter() {
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

} //DomainModelAdapterFactory
