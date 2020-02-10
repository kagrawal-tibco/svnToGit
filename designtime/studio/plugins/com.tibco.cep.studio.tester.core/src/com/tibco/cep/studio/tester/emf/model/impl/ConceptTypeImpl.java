/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.tester.emf.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.cep.studio.tester.emf.model.ConceptType;
import com.tibco.cep.studio.tester.emf.model.ModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Concept Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.impl.ConceptTypeImpl#isIsScorecard <em>Is Scorecard</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ConceptTypeImpl extends EntityTypeImpl implements ConceptType {
	/**
	 * The default value of the '{@link #isIsScorecard() <em>Is Scorecard</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsScorecard()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_SCORECARD_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIsScorecard() <em>Is Scorecard</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsScorecard()
	 * @generated
	 * @ordered
	 */
	protected boolean isScorecard = IS_SCORECARD_EDEFAULT;

	/**
	 * This is true if the Is Scorecard attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean isScorecardESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConceptTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.CONCEPT_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIsScorecard() {
		return isScorecard;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsScorecard(boolean newIsScorecard) {
		boolean oldIsScorecard = isScorecard;
		isScorecard = newIsScorecard;
		boolean oldIsScorecardESet = isScorecardESet;
		isScorecardESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.CONCEPT_TYPE__IS_SCORECARD, oldIsScorecard, isScorecard, !oldIsScorecardESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetIsScorecard() {
		boolean oldIsScorecard = isScorecard;
		boolean oldIsScorecardESet = isScorecardESet;
		isScorecard = IS_SCORECARD_EDEFAULT;
		isScorecardESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.CONCEPT_TYPE__IS_SCORECARD, oldIsScorecard, IS_SCORECARD_EDEFAULT, oldIsScorecardESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetIsScorecard() {
		return isScorecardESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ModelPackage.CONCEPT_TYPE__IS_SCORECARD:
				return isIsScorecard();
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
			case ModelPackage.CONCEPT_TYPE__IS_SCORECARD:
				setIsScorecard((Boolean)newValue);
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
			case ModelPackage.CONCEPT_TYPE__IS_SCORECARD:
				unsetIsScorecard();
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
			case ModelPackage.CONCEPT_TYPE__IS_SCORECARD:
				return isSetIsScorecard();
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
		result.append(" (isScorecard: ");
		if (isScorecardESet) result.append(isScorecard); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //ConceptTypeImpl
