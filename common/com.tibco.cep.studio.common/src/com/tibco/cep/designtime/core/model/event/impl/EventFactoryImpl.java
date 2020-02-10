/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.event.impl;

import com.tibco.cep.designtime.core.model.event.*;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import com.tibco.cep.designtime.core.model.event.EVENT_SCHEDULE_TYPE;
import com.tibco.cep.designtime.core.model.event.EVENT_TYPE;
import com.tibco.cep.designtime.core.model.event.EventFactory;
import com.tibco.cep.designtime.core.model.event.EventPackage;
import com.tibco.cep.designtime.core.model.event.ImportRegistryEntry;
import com.tibco.cep.designtime.core.model.event.NamespaceEntry;
import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.designtime.core.model.event.TimeEvent;
import com.tibco.cep.designtime.core.model.event.UserProperty;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class EventFactoryImpl extends EFactoryImpl implements EventFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EventFactory init() {
		try {
			EventFactory theEventFactory = (EventFactory)EPackage.Registry.INSTANCE.getEFactory(EventPackage.eNS_URI);
			if (theEventFactory != null) {
				return theEventFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new EventFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EventFactoryImpl() {
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
			case EventPackage.NAMESPACE_ENTRY: return createNamespaceEntry();
			case EventPackage.IMPORT_REGISTRY_ENTRY: return createImportRegistryEntry();
			case EventPackage.SIMPLE_EVENT: return createSimpleEvent();
			case EventPackage.TIME_EVENT: return createTimeEvent();
			case EventPackage.USER_PROPERTY: return createUserProperty();
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
			case EventPackage.EVENT_TYPE:
				return createEVENT_TYPEFromString(eDataType, initialValue);
			case EventPackage.EVENT_SCHEDULE_TYPE:
				return createEVENT_SCHEDULE_TYPEFromString(eDataType, initialValue);
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
			case EventPackage.EVENT_TYPE:
				return convertEVENT_TYPEToString(eDataType, instanceValue);
			case EventPackage.EVENT_SCHEDULE_TYPE:
				return convertEVENT_SCHEDULE_TYPEToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NamespaceEntry createNamespaceEntry() {
		NamespaceEntryImpl namespaceEntry = new NamespaceEntryImpl();
		return namespaceEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ImportRegistryEntry createImportRegistryEntry() {
		ImportRegistryEntryImpl importRegistryEntry = new ImportRegistryEntryImpl();
		return importRegistryEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SimpleEvent createSimpleEvent() {
		SimpleEventImpl simpleEvent = new SimpleEventImpl();
		return simpleEvent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TimeEvent createTimeEvent() {
		TimeEventImpl timeEvent = new TimeEventImpl();
		return timeEvent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UserProperty createUserProperty() {
		UserPropertyImpl userProperty = new UserPropertyImpl();
		return userProperty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EVENT_TYPE createEVENT_TYPEFromString(EDataType eDataType, String initialValue) {
		EVENT_TYPE result = EVENT_TYPE.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertEVENT_TYPEToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EVENT_SCHEDULE_TYPE createEVENT_SCHEDULE_TYPEFromString(EDataType eDataType, String initialValue) {
		EVENT_SCHEDULE_TYPE result = EVENT_SCHEDULE_TYPE.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertEVENT_SCHEDULE_TYPEToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EventPackage getEventPackage() {
		return (EventPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static EventPackage getPackage() {
		return EventPackage.eINSTANCE;
	}

} //EventFactoryImpl
