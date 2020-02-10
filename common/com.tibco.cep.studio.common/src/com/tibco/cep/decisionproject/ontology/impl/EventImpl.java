/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decisionproject.ontology.impl;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.decisionproject.ontology.AbstractResource;
import com.tibco.cep.decisionproject.ontology.ArgumentResource;
import com.tibco.cep.decisionproject.ontology.Event;
import com.tibco.cep.decisionproject.ontology.OntologyPackage;
import com.tibco.cep.decisionproject.ontology.Property;
import com.tibco.cep.decisionproject.ontology.Rule;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Event</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.impl.EventImpl#getAlias <em>Alias</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.impl.EventImpl#getTtl <em>Ttl</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.impl.EventImpl#getTtlUnits <em>Ttl Units</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.impl.EventImpl#getSuperEventPath <em>Super Event Path</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.impl.EventImpl#getDefaultDestinationName <em>Default Destination Name</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.impl.EventImpl#getExpiryAction <em>Expiry Action</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.impl.EventImpl#getUserProperty <em>User Property</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.impl.EventImpl#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EventImpl extends ParentResourceImpl implements Event {
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
	 * The default value of the '{@link #getTtl() <em>Ttl</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTtl()
	 * @generated
	 * @ordered
	 */
	protected static final int TTL_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getTtl() <em>Ttl</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTtl()
	 * @generated
	 * @ordered
	 */
	protected int ttl = TTL_EDEFAULT;

	/**
	 * The default value of the '{@link #getTtlUnits() <em>Ttl Units</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTtlUnits()
	 * @generated
	 * @ordered
	 */
	protected static final int TTL_UNITS_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getTtlUnits() <em>Ttl Units</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTtlUnits()
	 * @generated
	 * @ordered
	 */
	protected int ttlUnits = TTL_UNITS_EDEFAULT;

	/**
	 * The default value of the '{@link #getSuperEventPath() <em>Super Event Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSuperEventPath()
	 * @generated
	 * @ordered
	 */
	protected static final String SUPER_EVENT_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSuperEventPath() <em>Super Event Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSuperEventPath()
	 * @generated
	 * @ordered
	 */
	protected String superEventPath = SUPER_EVENT_PATH_EDEFAULT;

	/**
	 * The default value of the '{@link #getDefaultDestinationName() <em>Default Destination Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultDestinationName()
	 * @generated
	 * @ordered
	 */
	protected static final String DEFAULT_DESTINATION_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDefaultDestinationName() <em>Default Destination Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultDestinationName()
	 * @generated
	 * @ordered
	 */
	protected String defaultDestinationName = DEFAULT_DESTINATION_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getExpiryAction() <em>Expiry Action</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExpiryAction()
	 * @generated
	 * @ordered
	 */
	protected Rule expiryAction;

	/**
	 * The cached value of the '{@link #getUserProperty() <em>User Property</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUserProperty()
	 * @generated
	 * @ordered
	 */
	protected EList<Property> userProperty;

	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final int TYPE_EDEFAULT = -1;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected int type = TYPE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	protected EventImpl() {
		super();
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OntologyPackage.Literals.EVENT;
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
			eNotify(new ENotificationImpl(this, Notification.SET, OntologyPackage.EVENT__ALIAS, oldAlias, alias));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getTtl() {
		return ttl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTtl(int newTtl) {
		int oldTtl = ttl;
		ttl = newTtl;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OntologyPackage.EVENT__TTL, oldTtl, ttl));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getTtlUnits() {
		return ttlUnits;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTtlUnits(int newTtlUnits) {
		int oldTtlUnits = ttlUnits;
		ttlUnits = newTtlUnits;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OntologyPackage.EVENT__TTL_UNITS, oldTtlUnits, ttlUnits));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSuperEventPath() {
		return superEventPath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSuperEventPath(String newSuperEventPath) {
		String oldSuperEventPath = superEventPath;
		superEventPath = newSuperEventPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OntologyPackage.EVENT__SUPER_EVENT_PATH, oldSuperEventPath, superEventPath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDefaultDestinationName() {
		return defaultDestinationName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDefaultDestinationName(String newDefaultDestinationName) {
		String oldDefaultDestinationName = defaultDestinationName;
		defaultDestinationName = newDefaultDestinationName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OntologyPackage.EVENT__DEFAULT_DESTINATION_NAME, oldDefaultDestinationName, defaultDestinationName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Rule getExpiryAction() {
		return expiryAction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExpiryAction(Rule newExpiryAction, NotificationChain msgs) {
		Rule oldExpiryAction = expiryAction;
		expiryAction = newExpiryAction;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OntologyPackage.EVENT__EXPIRY_ACTION, oldExpiryAction, newExpiryAction);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExpiryAction(Rule newExpiryAction) {
		if (newExpiryAction != expiryAction) {
			NotificationChain msgs = null;
			if (expiryAction != null)
				msgs = ((InternalEObject)expiryAction).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OntologyPackage.EVENT__EXPIRY_ACTION, null, msgs);
			if (newExpiryAction != null)
				msgs = ((InternalEObject)newExpiryAction).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OntologyPackage.EVENT__EXPIRY_ACTION, null, msgs);
			msgs = basicSetExpiryAction(newExpiryAction, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OntologyPackage.EVENT__EXPIRY_ACTION, newExpiryAction, newExpiryAction));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Property> getUserProperty() {
		if (userProperty == null) {
			userProperty = new EObjectContainmentEList<Property>(Property.class, this, OntologyPackage.EVENT__USER_PROPERTY);
		}
		return userProperty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(int newType) {
		int oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OntologyPackage.EVENT__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case OntologyPackage.EVENT__EXPIRY_ACTION:
				return basicSetExpiryAction(null, msgs);
			case OntologyPackage.EVENT__USER_PROPERTY:
				return ((InternalEList<?>)getUserProperty()).basicRemove(otherEnd, msgs);
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
			case OntologyPackage.EVENT__ALIAS:
				return getAlias();
			case OntologyPackage.EVENT__TTL:
				return new Integer(getTtl());
			case OntologyPackage.EVENT__TTL_UNITS:
				return new Integer(getTtlUnits());
			case OntologyPackage.EVENT__SUPER_EVENT_PATH:
				return getSuperEventPath();
			case OntologyPackage.EVENT__DEFAULT_DESTINATION_NAME:
				return getDefaultDestinationName();
			case OntologyPackage.EVENT__EXPIRY_ACTION:
				return getExpiryAction();
			case OntologyPackage.EVENT__USER_PROPERTY:
				return getUserProperty();
			case OntologyPackage.EVENT__TYPE:
				return new Integer(getType());
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
			case OntologyPackage.EVENT__ALIAS:
				setAlias((String)newValue);
				return;
			case OntologyPackage.EVENT__TTL:
				setTtl(((Integer)newValue).intValue());
				return;
			case OntologyPackage.EVENT__TTL_UNITS:
				setTtlUnits(((Integer)newValue).intValue());
				return;
			case OntologyPackage.EVENT__SUPER_EVENT_PATH:
				setSuperEventPath((String)newValue);
				return;
			case OntologyPackage.EVENT__DEFAULT_DESTINATION_NAME:
				setDefaultDestinationName((String)newValue);
				return;
			case OntologyPackage.EVENT__EXPIRY_ACTION:
				setExpiryAction((Rule)newValue);
				return;
			case OntologyPackage.EVENT__USER_PROPERTY:
				getUserProperty().clear();
				getUserProperty().addAll((Collection<? extends Property>)newValue);
				return;
			case OntologyPackage.EVENT__TYPE:
				setType(((Integer)newValue).intValue());
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
			case OntologyPackage.EVENT__ALIAS:
				setAlias(ALIAS_EDEFAULT);
				return;
			case OntologyPackage.EVENT__TTL:
				setTtl(TTL_EDEFAULT);
				return;
			case OntologyPackage.EVENT__TTL_UNITS:
				setTtlUnits(TTL_UNITS_EDEFAULT);
				return;
			case OntologyPackage.EVENT__SUPER_EVENT_PATH:
				setSuperEventPath(SUPER_EVENT_PATH_EDEFAULT);
				return;
			case OntologyPackage.EVENT__DEFAULT_DESTINATION_NAME:
				setDefaultDestinationName(DEFAULT_DESTINATION_NAME_EDEFAULT);
				return;
			case OntologyPackage.EVENT__EXPIRY_ACTION:
				setExpiryAction((Rule)null);
				return;
			case OntologyPackage.EVENT__USER_PROPERTY:
				getUserProperty().clear();
				return;
			case OntologyPackage.EVENT__TYPE:
				setType(TYPE_EDEFAULT);
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
			case OntologyPackage.EVENT__ALIAS:
				return ALIAS_EDEFAULT == null ? alias != null : !ALIAS_EDEFAULT.equals(alias);
			case OntologyPackage.EVENT__TTL:
				return ttl != TTL_EDEFAULT;
			case OntologyPackage.EVENT__TTL_UNITS:
				return ttlUnits != TTL_UNITS_EDEFAULT;
			case OntologyPackage.EVENT__SUPER_EVENT_PATH:
				return SUPER_EVENT_PATH_EDEFAULT == null ? superEventPath != null : !SUPER_EVENT_PATH_EDEFAULT.equals(superEventPath);
			case OntologyPackage.EVENT__DEFAULT_DESTINATION_NAME:
				return DEFAULT_DESTINATION_NAME_EDEFAULT == null ? defaultDestinationName != null : !DEFAULT_DESTINATION_NAME_EDEFAULT.equals(defaultDestinationName);
			case OntologyPackage.EVENT__EXPIRY_ACTION:
				return expiryAction != null;
			case OntologyPackage.EVENT__USER_PROPERTY:
				return userProperty != null && !userProperty.isEmpty();
			case OntologyPackage.EVENT__TYPE:
				return type != TYPE_EDEFAULT;
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
				case OntologyPackage.EVENT__ALIAS: return OntologyPackage.ARGUMENT_RESOURCE__ALIAS;
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
				case OntologyPackage.ARGUMENT_RESOURCE__ALIAS: return OntologyPackage.EVENT__ALIAS;
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
		result.append(", ttl: ");
		result.append(ttl);
		result.append(", ttlUnits: ");
		result.append(ttlUnits);
		result.append(", superEventPath: ");
		result.append(superEventPath);
		result.append(", defaultDestinationName: ");
		result.append(defaultDestinationName);
		result.append(", type: ");
		result.append(type);
		result.append(')');
		return result.toString();
	}
	/* (non-Javadoc)
	 * @see com.tibco.cep.decisionproject.ontology.ParentResource#addChild(com.tibco.cep.decisionproject.ontology.AbstractResource)
	 */
	public boolean addChild(AbstractResource abstractResource) {
		if (!(abstractResource instanceof Property)) {
			return false;
		}
		if (userProperty == null){
			userProperty = new EObjectContainmentEList<Property>(Property.class, this, OntologyPackage.CONCEPT__PROPERTY);
		}
	
		return userProperty.add((Property)abstractResource);
	}
	/* (non-Javadoc)
	 * @see com.tibco.cep.decisionproject.model.impl.ParentResourceImpl#getChildren()
	 */
	@Override
	public Iterator<Property> getChildren() {
		return getUserProperty().iterator();
	}

} //EventImpl
