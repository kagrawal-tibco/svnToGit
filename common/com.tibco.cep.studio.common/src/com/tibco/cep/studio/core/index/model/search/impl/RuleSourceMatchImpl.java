/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model.search.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.cep.studio.core.index.model.search.RuleSourceMatch;
import com.tibco.cep.studio.core.index.model.search.SearchPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Rule Source Match</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.index.model.search.impl.RuleSourceMatchImpl#getOffset <em>Offset</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.search.impl.RuleSourceMatchImpl#getLength <em>Length</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.search.impl.RuleSourceMatchImpl#getContainingRule <em>Containing Rule</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RuleSourceMatchImpl extends EObjectImpl implements RuleSourceMatch {
	/**
	 * The default value of the '{@link #getOffset() <em>Offset</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOffset()
	 * @generated
	 * @ordered
	 */
	protected static final int OFFSET_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getOffset() <em>Offset</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOffset()
	 * @generated
	 * @ordered
	 */
	protected int offset = OFFSET_EDEFAULT;

	/**
	 * The default value of the '{@link #getLength() <em>Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLength()
	 * @generated
	 * @ordered
	 */
	protected static final int LENGTH_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getLength() <em>Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLength()
	 * @generated
	 * @ordered
	 */
	protected int length = LENGTH_EDEFAULT;

	/**
	 * The cached value of the '{@link #getContainingRule() <em>Containing Rule</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContainingRule()
	 * @generated
	 * @ordered
	 */
	protected EObject containingRule;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RuleSourceMatchImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SearchPackage.Literals.RULE_SOURCE_MATCH;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getOffset() {
		return offset;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOffset(int newOffset) {
		int oldOffset = offset;
		offset = newOffset;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SearchPackage.RULE_SOURCE_MATCH__OFFSET, oldOffset, offset));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getLength() {
		return length;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLength(int newLength) {
		int oldLength = length;
		length = newLength;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SearchPackage.RULE_SOURCE_MATCH__LENGTH, oldLength, length));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getContainingRule() {
		if (containingRule != null && containingRule.eIsProxy()) {
			InternalEObject oldContainingRule = (InternalEObject)containingRule;
			containingRule = eResolveProxy(oldContainingRule);
			if (containingRule != oldContainingRule) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SearchPackage.RULE_SOURCE_MATCH__CONTAINING_RULE, oldContainingRule, containingRule));
			}
		}
		return containingRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetContainingRule() {
		return containingRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setContainingRule(EObject newContainingRule) {
		EObject oldContainingRule = containingRule;
		containingRule = newContainingRule;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SearchPackage.RULE_SOURCE_MATCH__CONTAINING_RULE, oldContainingRule, containingRule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SearchPackage.RULE_SOURCE_MATCH__OFFSET:
				return getOffset();
			case SearchPackage.RULE_SOURCE_MATCH__LENGTH:
				return getLength();
			case SearchPackage.RULE_SOURCE_MATCH__CONTAINING_RULE:
				if (resolve) return getContainingRule();
				return basicGetContainingRule();
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
			case SearchPackage.RULE_SOURCE_MATCH__OFFSET:
				setOffset((Integer)newValue);
				return;
			case SearchPackage.RULE_SOURCE_MATCH__LENGTH:
				setLength((Integer)newValue);
				return;
			case SearchPackage.RULE_SOURCE_MATCH__CONTAINING_RULE:
				setContainingRule((EObject)newValue);
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
			case SearchPackage.RULE_SOURCE_MATCH__OFFSET:
				setOffset(OFFSET_EDEFAULT);
				return;
			case SearchPackage.RULE_SOURCE_MATCH__LENGTH:
				setLength(LENGTH_EDEFAULT);
				return;
			case SearchPackage.RULE_SOURCE_MATCH__CONTAINING_RULE:
				setContainingRule((EObject)null);
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
			case SearchPackage.RULE_SOURCE_MATCH__OFFSET:
				return offset != OFFSET_EDEFAULT;
			case SearchPackage.RULE_SOURCE_MATCH__LENGTH:
				return length != LENGTH_EDEFAULT;
			case SearchPackage.RULE_SOURCE_MATCH__CONTAINING_RULE:
				return containingRule != null;
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
		result.append(" (offset: ");
		result.append(offset);
		result.append(", length: ");
		result.append(length);
		result.append(')');
		return result.toString();
	}

} //RuleSourceMatchImpl
