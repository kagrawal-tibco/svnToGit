/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decisionproject.ontology.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.cep.decisionproject.ontology.ArgumentResource;
import com.tibco.cep.decisionproject.ontology.OntologyPackage;
import com.tibco.cep.decisionproject.ontology.PrimitiveHolder;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Primitive Holder</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.impl.PrimitiveHolderImpl#getAlias <em>Alias</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.impl.PrimitiveHolderImpl#getPrimitiveType <em>Primitive Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PrimitiveHolderImpl extends AbstractResourceImpl implements PrimitiveHolder {
	/**
	 * The default value of the '{@link #getAlias() <em>Alias</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAlias()
	 * @generated
	 * @ordered
	 */
	protected static final String ALIAS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAlias() <em>Alias</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAlias()
	 * @generated
	 * @ordered
	 */
	protected String alias = ALIAS_EDEFAULT;

	/**
	 * The default value of the '{@link #getPrimitiveType() <em>Primitive Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrimitiveType()
	 * @generated
	 * @ordered
	 */
	protected static final int PRIMITIVE_TYPE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getPrimitiveType() <em>Primitive Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrimitiveType()
	 * @generated
	 * @ordered
	 */
	protected int primitiveType = PRIMITIVE_TYPE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PrimitiveHolderImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OntologyPackage.Literals.PRIMITIVE_HOLDER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAlias(String newAlias) {
		String oldAlias = alias;
		alias = newAlias;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OntologyPackage.PRIMITIVE_HOLDER__ALIAS, oldAlias, alias));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getPrimitiveType() {
		return primitiveType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPrimitiveType(int newPrimitiveType) {
		int oldPrimitiveType = primitiveType;
		primitiveType = newPrimitiveType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OntologyPackage.PRIMITIVE_HOLDER__PRIMITIVE_TYPE, oldPrimitiveType, primitiveType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case OntologyPackage.PRIMITIVE_HOLDER__ALIAS:
				return getAlias();
			case OntologyPackage.PRIMITIVE_HOLDER__PRIMITIVE_TYPE:
				return new Integer(getPrimitiveType());
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
			case OntologyPackage.PRIMITIVE_HOLDER__ALIAS:
				setAlias((String)newValue);
				return;
			case OntologyPackage.PRIMITIVE_HOLDER__PRIMITIVE_TYPE:
				setPrimitiveType(((Integer)newValue).intValue());
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
			case OntologyPackage.PRIMITIVE_HOLDER__ALIAS:
				setAlias(ALIAS_EDEFAULT);
				return;
			case OntologyPackage.PRIMITIVE_HOLDER__PRIMITIVE_TYPE:
				setPrimitiveType(PRIMITIVE_TYPE_EDEFAULT);
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
			case OntologyPackage.PRIMITIVE_HOLDER__ALIAS:
				return ALIAS_EDEFAULT == null ? alias != null : !ALIAS_EDEFAULT.equals(alias);
			case OntologyPackage.PRIMITIVE_HOLDER__PRIMITIVE_TYPE:
				return primitiveType != PRIMITIVE_TYPE_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == ArgumentResource.class) {
			switch (derivedFeatureID) {
				case OntologyPackage.PRIMITIVE_HOLDER__ALIAS: return OntologyPackage.ARGUMENT_RESOURCE__ALIAS;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == ArgumentResource.class) {
			switch (baseFeatureID) {
				case OntologyPackage.ARGUMENT_RESOURCE__ALIAS: return OntologyPackage.PRIMITIVE_HOLDER__ALIAS;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
		result.append(" (alias: ");
		result.append(alias);
		result.append(", primitiveType: ");
		result.append(primitiveType);
		result.append(')');
		return result.toString();
	}

} //PrimitiveHolderImpl
