/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.states.impl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import com.tibco.cep.designtime.core.model.states.StateSimple;
import com.tibco.cep.designtime.core.model.states.StatesPackage;
import com.tibco.cep.designtime.core.model.validation.ModelError;
import com.tibco.cep.studio.common.util.TimeOutUnitsUtils;
import com.tibco.cep.studio.common.validation.utils.CommonValidationUtils;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>State Simple</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class StateSimpleImpl extends StateImpl implements StateSimple {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StateSimpleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StatesPackage.Literals.STATE_SIMPLE;
	}

	//Added by Anand - 01/17/2011 to fix BE-10395	
	/**
	 * @generated not
	 */
	@Override
	public EList<ModelError> getModelErrors() {
		EList<ModelError> errorList = super.getModelErrors();
		List<Object> args = new ArrayList<Object>();
		if (TimeOutUnitsUtils.isValid(getTimeoutUnits()) == false) {
			args.clear();
			args.add(this.getName());
			ModelError me = CommonValidationUtils.constructModelError(this, "State.errors.invalidTimeOutUnits", 
					args, false);
			errorList.add(me);
		}
		return errorList;
	}

} //StateSimpleImpl
