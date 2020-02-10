/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.event.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.RuleParticipant;
import com.tibco.cep.designtime.core.model.event.*;
import com.tibco.cep.designtime.core.model.event.AdvisoryEvent;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.event.EventPackage;
import com.tibco.cep.designtime.core.model.event.ImportRegistryEntry;
import com.tibco.cep.designtime.core.model.event.NamespaceEntry;
import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.designtime.core.model.event.TimeEvent;
import com.tibco.cep.designtime.core.model.event.UserProperty;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.designtime.core.model.event.EventPackage
 * @generated
 */
public class EventAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static EventPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EventAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = EventPackage.eINSTANCE;
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
	protected EventSwitch<Adapter> modelSwitch =
		new EventSwitch<Adapter>() {
			@Override
			public Adapter caseEvent(Event object) {
				return createEventAdapter();
			}
			@Override
			public Adapter caseNamespaceEntry(NamespaceEntry object) {
				return createNamespaceEntryAdapter();
			}
			@Override
			public Adapter caseImportRegistryEntry(ImportRegistryEntry object) {
				return createImportRegistryEntryAdapter();
			}
			@Override
			public Adapter caseSimpleEvent(SimpleEvent object) {
				return createSimpleEventAdapter();
			}
			@Override
			public Adapter caseTimeEvent(TimeEvent object) {
				return createTimeEventAdapter();
			}
			@Override
			public Adapter caseUserProperty(UserProperty object) {
				return createUserPropertyAdapter();
			}
			@Override
			public Adapter caseAdvisoryEvent(AdvisoryEvent object) {
				return createAdvisoryEventAdapter();
			}
			@Override
			public Adapter caseEntity(Entity object) {
				return createEntityAdapter();
			}
			@Override
			public Adapter caseRuleParticipant(RuleParticipant object) {
				return createRuleParticipantAdapter();
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
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.event.Event <em>Event</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.event.Event
	 * @generated
	 */
	public Adapter createEventAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.event.NamespaceEntry <em>Namespace Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.event.NamespaceEntry
	 * @generated
	 */
	public Adapter createNamespaceEntryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.event.ImportRegistryEntry <em>Import Registry Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.event.ImportRegistryEntry
	 * @generated
	 */
	public Adapter createImportRegistryEntryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.event.SimpleEvent <em>Simple Event</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.event.SimpleEvent
	 * @generated
	 */
	public Adapter createSimpleEventAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.event.TimeEvent <em>Time Event</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.event.TimeEvent
	 * @generated
	 */
	public Adapter createTimeEventAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.event.UserProperty <em>User Property</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.event.UserProperty
	 * @generated
	 */
	public Adapter createUserPropertyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.event.AdvisoryEvent <em>Advisory Event</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.event.AdvisoryEvent
	 * @generated
	 */
	public Adapter createAdvisoryEventAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.Entity <em>Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.Entity
	 * @generated
	 */
	public Adapter createEntityAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.RuleParticipant <em>Rule Participant</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.RuleParticipant
	 * @generated
	 */
	public Adapter createRuleParticipantAdapter() {
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

} //EventAdapterFactory
