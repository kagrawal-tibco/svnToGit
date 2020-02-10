/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.event.impl;

import org.eclipse.emf.ecore.EClass;

import com.tibco.cep.designtime.core.model.event.AdvisoryEvent;
import com.tibco.cep.designtime.core.model.event.EventPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Advisory Event</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public abstract class AdvisoryEventImpl extends EventImpl implements AdvisoryEvent {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AdvisoryEventImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EventPackage.Literals.ADVISORY_EVENT;
	}

} //AdvisoryEventImpl
