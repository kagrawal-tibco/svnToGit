/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.states.impl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.cep.designtime.core.model.states.StateSubmachine;
import com.tibco.cep.designtime.core.model.states.StatesPackage;
import com.tibco.cep.designtime.core.model.validation.ModelError;
import com.tibco.cep.studio.common.validation.utils.CommonValidationUtils;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>State Submachine</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.impl.StateSubmachineImpl#getURI <em>URI</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.impl.StateSubmachineImpl#isCallExplicitly <em>Call Explicitly</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.impl.StateSubmachineImpl#isPreserveForwardCorrelation <em>Preserve Forward Correlation</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class StateSubmachineImpl extends StateCompositeImpl implements StateSubmachine {
	/**
	 * The default value of the '{@link #getURI() <em>URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getURI()
	 * @generated
	 * @ordered
	 */
	protected static final String URI_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getURI() <em>URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getURI()
	 * @generated
	 * @ordered
	 */
	protected String uri = URI_EDEFAULT;

	/**
	 * The default value of the '{@link #isCallExplicitly() <em>Call Explicitly</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCallExplicitly()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CALL_EXPLICITLY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isCallExplicitly() <em>Call Explicitly</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCallExplicitly()
	 * @generated
	 * @ordered
	 */
	protected boolean callExplicitly = CALL_EXPLICITLY_EDEFAULT;

	/**
	 * The default value of the '{@link #isPreserveForwardCorrelation() <em>Preserve Forward Correlation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPreserveForwardCorrelation()
	 * @generated
	 * @ordered
	 */
	protected static final boolean PRESERVE_FORWARD_CORRELATION_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isPreserveForwardCorrelation() <em>Preserve Forward Correlation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPreserveForwardCorrelation()
	 * @generated
	 * @ordered
	 */
	protected boolean preserveForwardCorrelation = PRESERVE_FORWARD_CORRELATION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StateSubmachineImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StatesPackage.Literals.STATE_SUBMACHINE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getURI() {
		return uri;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setURI(String newURI) {
		String oldURI = uri;
		uri = newURI;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StatesPackage.STATE_SUBMACHINE__URI, oldURI, uri));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isCallExplicitly() {
		return callExplicitly;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCallExplicitly(boolean newCallExplicitly) {
		boolean oldCallExplicitly = callExplicitly;
		callExplicitly = newCallExplicitly;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StatesPackage.STATE_SUBMACHINE__CALL_EXPLICITLY, oldCallExplicitly, callExplicitly));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isPreserveForwardCorrelation() {
		return preserveForwardCorrelation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPreserveForwardCorrelation(boolean newPreserveForwardCorrelation) {
		boolean oldPreserveForwardCorrelation = preserveForwardCorrelation;
		preserveForwardCorrelation = newPreserveForwardCorrelation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StatesPackage.STATE_SUBMACHINE__PRESERVE_FORWARD_CORRELATION, oldPreserveForwardCorrelation, preserveForwardCorrelation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case StatesPackage.STATE_SUBMACHINE__URI:
				return getURI();
			case StatesPackage.STATE_SUBMACHINE__CALL_EXPLICITLY:
				return isCallExplicitly();
			case StatesPackage.STATE_SUBMACHINE__PRESERVE_FORWARD_CORRELATION:
				return isPreserveForwardCorrelation();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case StatesPackage.STATE_SUBMACHINE__URI:
				setURI((String)newValue);
				return;
			case StatesPackage.STATE_SUBMACHINE__CALL_EXPLICITLY:
				setCallExplicitly((Boolean)newValue);
				return;
			case StatesPackage.STATE_SUBMACHINE__PRESERVE_FORWARD_CORRELATION:
				setPreserveForwardCorrelation((Boolean)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case StatesPackage.STATE_SUBMACHINE__URI:
				setURI(URI_EDEFAULT);
				return;
			case StatesPackage.STATE_SUBMACHINE__CALL_EXPLICITLY:
				setCallExplicitly(CALL_EXPLICITLY_EDEFAULT);
				return;
			case StatesPackage.STATE_SUBMACHINE__PRESERVE_FORWARD_CORRELATION:
				setPreserveForwardCorrelation(PRESERVE_FORWARD_CORRELATION_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case StatesPackage.STATE_SUBMACHINE__URI:
				return URI_EDEFAULT == null ? uri != null : !URI_EDEFAULT.equals(uri);
			case StatesPackage.STATE_SUBMACHINE__CALL_EXPLICITLY:
				return callExplicitly != CALL_EXPLICITLY_EDEFAULT;
			case StatesPackage.STATE_SUBMACHINE__PRESERVE_FORWARD_CORRELATION:
				return preserveForwardCorrelation != PRESERVE_FORWARD_CORRELATION_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (URI: ");
		result.append(uri);
		result.append(", callExplicitly: ");
		result.append(callExplicitly);
		result.append(", preserveForwardCorrelation: ");
		result.append(preserveForwardCorrelation);
		result.append(')');
		return result.toString();
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */

	@Override
	public EList<ModelError> getModelErrors() {
		
		EList<ModelError> errorList = super.getModelErrors();
		List<Object> args = new ArrayList<Object>();
		// check if sub machine URI is null
		String uri = getURI();
		if (uri == null){
			args.add(this.getName());
			ModelError me = CommonValidationUtils.constructModelError(this, "StateSubmachine.errors.submachinesMustHaveAPathSet", args, false);
			errorList.add(me);
		}
		// if URI is not null then check if reference is dangling
	
		// remove the second segment which is concept
		if (uri != null) {
//			int index = uri.lastIndexOf('/');
//			if (index != -1){
//				String subStatemachineName = uri.substring(index +1);
//				String root = uri.substring(0,index);
//				// remove concept part 
//				index = root.lastIndexOf('/');
//				String folder = "";
//				if (index != -1){
//					folder = root.substring(0,index);
//		    	
//				}
				String smExt = CommonIndexUtils.getFileExtension(ELEMENT_TYPES.STATE_MACHINE);
				if (!uri.endsWith(smExt)){
//					uri = folder + "/" + subStatemachineName +  "." + smExt;
					uri = "/" + uri +  "." + smExt;
				}
//			}
			if (!CommonValidationUtils.resolveReference(uri, this.getOwnerProjectName())) {
				// sub state machine can not be resolved
				args.clear();
				if (this.getOwnerStateMachine() != null) {
					args.add(this.getOwnerStateMachine().getName());
				}
				args.add(this.getName());
				args.add(uri);
				ModelError me = CommonValidationUtils.constructModelError(this, "StateMachine.errors.danglingSubStateMachineReference", args, false);
				errorList.add(me);
			}
		}
		
		// check if sub machine is a concurrent state
		if (isConcurrentState()){
			// sub machine can not be a concurrent state
			args.clear();
			args.add(this.getName());
			ModelError me = CommonValidationUtils.constructModelError(this, "StateSubmachine.errors.submachinesCannotBeConcurrentStates", args, false);
			errorList.add(me);
		}
		//@TODO sub machine can only have the StubState
		// need to implement this once get clear about StubState
		
		return errorList;
	}
	

} //StateSubmachineImpl
