/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.rule.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.cep.designtime.core.model.domain.Domain;
import com.tibco.cep.designtime.core.model.rule.RulePackage;
import com.tibco.cep.designtime.core.model.rule.Symbol;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Symbol</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.impl.SymbolImpl#getIdName <em>Id Name</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.impl.SymbolImpl#getType <em>Type</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.impl.SymbolImpl#getTypeExtension <em>Type Extension</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.impl.SymbolImpl#getDomain <em>Domain</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.impl.SymbolImpl#isArray <em>Array</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SymbolImpl extends EObjectImpl implements Symbol {
	/**
	 * The default value of the '{@link #getIdName() <em>Id Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIdName()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIdName() <em>Id Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIdName()
	 * @generated
	 * @ordered
	 */
	protected String idName = ID_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final String TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected String type = TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getTypeExtension() <em>Type Extension</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypeExtension()
	 * @generated
	 * @ordered
	 */
	protected static final String TYPE_EXTENSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTypeExtension() <em>Type Extension</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypeExtension()
	 * @generated
	 * @ordered
	 */
	protected String typeExtension = TYPE_EXTENSION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getDomain() <em>Domain</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDomain()
	 * @generated
	 * @ordered
	 */
	protected Domain domain;

	/**
	 * The default value of the '{@link #isArray() <em>Array</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isArray()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ARRAY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isArray() <em>Array</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isArray()
	 * @generated
	 * @ordered
	 */
	protected boolean array = ARRAY_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SymbolImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RulePackage.Literals.SYMBOL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getIdName() {
		return idName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIdName(String newIdName) {
		String oldIdName = idName;
		idName = newIdName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulePackage.SYMBOL__ID_NAME, oldIdName, idName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(String newType) {
		String oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulePackage.SYMBOL__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTypeExtension() {
		return typeExtension;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTypeExtension(String newTypeExtension) {
		String oldTypeExtension = typeExtension;
		typeExtension = newTypeExtension;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulePackage.SYMBOL__TYPE_EXTENSION, oldTypeExtension, typeExtension));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Domain getDomain() {
		return domain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDomain(Domain newDomain, NotificationChain msgs) {
		Domain oldDomain = domain;
		domain = newDomain;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, RulePackage.SYMBOL__DOMAIN, oldDomain, newDomain);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDomain(Domain newDomain) {
		if (newDomain != domain) {
			NotificationChain msgs = null;
			if (domain != null)
				msgs = ((InternalEObject)domain).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - RulePackage.SYMBOL__DOMAIN, null, msgs);
			if (newDomain != null)
				msgs = ((InternalEObject)newDomain).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - RulePackage.SYMBOL__DOMAIN, null, msgs);
			msgs = basicSetDomain(newDomain, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulePackage.SYMBOL__DOMAIN, newDomain, newDomain));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isArray() {
		return array;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setArray(boolean newArray) {
		boolean oldArray = array;
		array = newArray;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulePackage.SYMBOL__ARRAY, oldArray, array));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RulePackage.SYMBOL__DOMAIN:
				return basicSetDomain(null, msgs);
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
			case RulePackage.SYMBOL__ID_NAME:
				return getIdName();
			case RulePackage.SYMBOL__TYPE:
				return getType();
			case RulePackage.SYMBOL__TYPE_EXTENSION:
				return getTypeExtension();
			case RulePackage.SYMBOL__DOMAIN:
				return getDomain();
			case RulePackage.SYMBOL__ARRAY:
				return isArray();
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
			case RulePackage.SYMBOL__ID_NAME:
				setIdName((String)newValue);
				return;
			case RulePackage.SYMBOL__TYPE:
				setType((String)newValue);
				return;
			case RulePackage.SYMBOL__TYPE_EXTENSION:
				setTypeExtension((String)newValue);
				return;
			case RulePackage.SYMBOL__DOMAIN:
				setDomain((Domain)newValue);
				return;
			case RulePackage.SYMBOL__ARRAY:
				setArray((Boolean)newValue);
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
			case RulePackage.SYMBOL__ID_NAME:
				setIdName(ID_NAME_EDEFAULT);
				return;
			case RulePackage.SYMBOL__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case RulePackage.SYMBOL__TYPE_EXTENSION:
				setTypeExtension(TYPE_EXTENSION_EDEFAULT);
				return;
			case RulePackage.SYMBOL__DOMAIN:
				setDomain((Domain)null);
				return;
			case RulePackage.SYMBOL__ARRAY:
				setArray(ARRAY_EDEFAULT);
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
			case RulePackage.SYMBOL__ID_NAME:
				return ID_NAME_EDEFAULT == null ? idName != null : !ID_NAME_EDEFAULT.equals(idName);
			case RulePackage.SYMBOL__TYPE:
				return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
			case RulePackage.SYMBOL__TYPE_EXTENSION:
				return TYPE_EXTENSION_EDEFAULT == null ? typeExtension != null : !TYPE_EXTENSION_EDEFAULT.equals(typeExtension);
			case RulePackage.SYMBOL__DOMAIN:
				return domain != null;
			case RulePackage.SYMBOL__ARRAY:
				return array != ARRAY_EDEFAULT;
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
		result.append(" (idName: ");
		result.append(idName);
		result.append(", type: ");
		result.append(type);
		result.append(", typeExtension: ");
		result.append(typeExtension);
		result.append(", array: ");
		result.append(array);
		result.append(')');
		return result.toString();
	}

} //SymbolImpl
