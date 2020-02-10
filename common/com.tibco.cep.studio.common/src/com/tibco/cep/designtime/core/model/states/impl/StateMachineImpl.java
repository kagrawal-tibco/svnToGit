/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.states.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.states.StateAnnotationLink;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StateTransition;
import com.tibco.cep.designtime.core.model.states.StatesPackage;
import com.tibco.cep.designtime.core.model.validation.ModelError;
import com.tibco.cep.studio.common.util.TimeOutUnitsUtils;
import com.tibco.cep.studio.common.validation.utils.CommonValidationUtils;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>State Machine</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.impl.StateMachineImpl#getStateTransitions <em>State Transitions</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.impl.StateMachineImpl#getAnnotationLinks <em>Annotation Links</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.impl.StateMachineImpl#isFwdCorrelates <em>Fwd Correlates</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.impl.StateMachineImpl#isMain <em>Main</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.impl.StateMachineImpl#getOwnerConceptPath <em>Owner Concept Path</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class StateMachineImpl extends StateCompositeImpl implements StateMachine {
	/**
	 * The cached value of the '{@link #getStateTransitions() <em>State Transitions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStateTransitions()
	 * @generated
	 * @ordered
	 */
	protected EList<StateTransition> stateTransitions;

	/**
	 * The cached value of the '{@link #getAnnotationLinks() <em>Annotation Links</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAnnotationLinks()
	 * @generated
	 * @ordered
	 */
	protected EList<StateAnnotationLink> annotationLinks;

	/**
	 * The default value of the '{@link #isFwdCorrelates() <em>Fwd Correlates</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFwdCorrelates()
	 * @generated
	 * @ordered
	 */
	protected static final boolean FWD_CORRELATES_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isFwdCorrelates() <em>Fwd Correlates</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFwdCorrelates()
	 * @generated
	 * @ordered
	 */
	protected boolean fwdCorrelates = FWD_CORRELATES_EDEFAULT;

	/**
	 * The default value of the '{@link #isMain() <em>Main</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMain()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MAIN_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isMain() <em>Main</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMain()
	 * @generated
	 * @ordered
	 */
	protected boolean main = MAIN_EDEFAULT;

	/**
	 * The default value of the '{@link #getOwnerConceptPath() <em>Owner Concept Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwnerConceptPath()
	 * @generated
	 * @ordered
	 */
	protected static final String OWNER_CONCEPT_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOwnerConceptPath() <em>Owner Concept Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwnerConceptPath()
	 * @generated
	 * @ordered
	 */
	protected String ownerConceptPath = OWNER_CONCEPT_PATH_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StateMachineImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StatesPackage.Literals.STATE_MACHINE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<StateTransition> getStateTransitions() {
		if (stateTransitions == null) {
			stateTransitions = new EObjectContainmentEList<StateTransition>(StateTransition.class, this, StatesPackage.STATE_MACHINE__STATE_TRANSITIONS);
		}
		return stateTransitions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<StateAnnotationLink> getAnnotationLinks() {
		if (annotationLinks == null) {
			annotationLinks = new EObjectContainmentEList<StateAnnotationLink>(StateAnnotationLink.class, this, StatesPackage.STATE_MACHINE__ANNOTATION_LINKS);
		}
		return annotationLinks;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isFwdCorrelates() {
		return fwdCorrelates;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFwdCorrelates(boolean newFwdCorrelates) {
		boolean oldFwdCorrelates = fwdCorrelates;
		fwdCorrelates = newFwdCorrelates;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StatesPackage.STATE_MACHINE__FWD_CORRELATES, oldFwdCorrelates, fwdCorrelates));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isMain() {
		return main;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMain(boolean newMain) {
		boolean oldMain = main;
		main = newMain;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StatesPackage.STATE_MACHINE__MAIN, oldMain, main));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getOwnerConceptPath() {
		return ownerConceptPath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOwnerConceptPath(String newOwnerConceptPath) {
		String oldOwnerConceptPath = ownerConceptPath;
		ownerConceptPath = newOwnerConceptPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StatesPackage.STATE_MACHINE__OWNER_CONCEPT_PATH, oldOwnerConceptPath, ownerConceptPath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Concept getOwnerConcept() {
		if (ownerConceptPath != null) {
			return CommonIndexUtils.getConcept(ownerProjectName, ownerConceptPath);
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case StatesPackage.STATE_MACHINE__STATE_TRANSITIONS:
				return ((InternalEList<?>)getStateTransitions()).basicRemove(otherEnd, msgs);
			case StatesPackage.STATE_MACHINE__ANNOTATION_LINKS:
				return ((InternalEList<?>)getAnnotationLinks()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case StatesPackage.STATE_MACHINE__STATE_TRANSITIONS:
				return getStateTransitions();
			case StatesPackage.STATE_MACHINE__ANNOTATION_LINKS:
				return getAnnotationLinks();
			case StatesPackage.STATE_MACHINE__FWD_CORRELATES:
				return isFwdCorrelates();
			case StatesPackage.STATE_MACHINE__MAIN:
				return isMain();
			case StatesPackage.STATE_MACHINE__OWNER_CONCEPT_PATH:
				return getOwnerConceptPath();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case StatesPackage.STATE_MACHINE__STATE_TRANSITIONS:
				getStateTransitions().clear();
				getStateTransitions().addAll((Collection<? extends StateTransition>)newValue);
				return;
			case StatesPackage.STATE_MACHINE__ANNOTATION_LINKS:
				getAnnotationLinks().clear();
				getAnnotationLinks().addAll((Collection<? extends StateAnnotationLink>)newValue);
				return;
			case StatesPackage.STATE_MACHINE__FWD_CORRELATES:
				setFwdCorrelates((Boolean)newValue);
				return;
			case StatesPackage.STATE_MACHINE__MAIN:
				setMain((Boolean)newValue);
				return;
			case StatesPackage.STATE_MACHINE__OWNER_CONCEPT_PATH:
				setOwnerConceptPath((String)newValue);
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
			case StatesPackage.STATE_MACHINE__STATE_TRANSITIONS:
				getStateTransitions().clear();
				return;
			case StatesPackage.STATE_MACHINE__ANNOTATION_LINKS:
				getAnnotationLinks().clear();
				return;
			case StatesPackage.STATE_MACHINE__FWD_CORRELATES:
				setFwdCorrelates(FWD_CORRELATES_EDEFAULT);
				return;
			case StatesPackage.STATE_MACHINE__MAIN:
				setMain(MAIN_EDEFAULT);
				return;
			case StatesPackage.STATE_MACHINE__OWNER_CONCEPT_PATH:
				setOwnerConceptPath(OWNER_CONCEPT_PATH_EDEFAULT);
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
			case StatesPackage.STATE_MACHINE__STATE_TRANSITIONS:
				return stateTransitions != null && !stateTransitions.isEmpty();
			case StatesPackage.STATE_MACHINE__ANNOTATION_LINKS:
				return annotationLinks != null && !annotationLinks.isEmpty();
			case StatesPackage.STATE_MACHINE__FWD_CORRELATES:
				return fwdCorrelates != FWD_CORRELATES_EDEFAULT;
			case StatesPackage.STATE_MACHINE__MAIN:
				return main != MAIN_EDEFAULT;
			case StatesPackage.STATE_MACHINE__OWNER_CONCEPT_PATH:
				return OWNER_CONCEPT_PATH_EDEFAULT == null ? ownerConceptPath != null : !OWNER_CONCEPT_PATH_EDEFAULT.equals(ownerConceptPath);
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
		result.append(" (fwdCorrelates: ");
		result.append(fwdCorrelates);
		result.append(", main: ");
		result.append(main);
		result.append(", ownerConceptPath: ");
		result.append(ownerConceptPath);
		result.append(')');
		return result.toString();
	}

	/**
	 * @generated not
	 */
	@Override
	public EList<ModelError> getModelErrors() {
		EList<ModelError> errorList = super.getModelErrors();
		List<Object> args = new ArrayList<Object>();
		if (getStateTransitions() == null || getStateTransitions().size() == 0) {
			args.clear();
			args.add(this.getName());
			ModelError me = CommonValidationUtils.constructModelError(this, "StateMachine.errors.incomplete", 
					args, false);
			errorList.add(me);
		}
		//Added by Anand - 01/17/2011 to fix BE-10395		
		if (TimeOutUnitsUtils.isValid(getTimeoutUnits()) == false) {
			args.clear();
			args.add(this.getName());
			ModelError me = CommonValidationUtils.constructModelError(this, "StateMachine.errors.invalidTimeOutUnits", 
					args, false);
			errorList.add(me);
		}
		return errorList;
	}

} //StateMachineImpl
