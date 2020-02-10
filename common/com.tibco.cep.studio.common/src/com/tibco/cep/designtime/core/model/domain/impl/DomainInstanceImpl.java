/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.domain.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.domain.Domain;
import com.tibco.cep.designtime.core.model.domain.DomainEntry;
import com.tibco.cep.designtime.core.model.domain.DomainInstance;
import com.tibco.cep.designtime.core.model.domain.DomainPackage;
import com.tibco.cep.designtime.core.model.domain.util.DomainUtils;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.element.impl.BaseInstanceImpl;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Instance</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.domain.impl.DomainInstanceImpl#getOwnerProperty <em>Owner Property</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.domain.impl.DomainInstanceImpl#getGUID <em>GUID</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.domain.impl.DomainInstanceImpl#getOwnerProjectName <em>Owner Project Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DomainInstanceImpl extends BaseInstanceImpl implements DomainInstance {
	/**
	 * The cached value of the '{@link #getOwnerProperty() <em>Owner Property</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwnerProperty()
	 * @generated
	 * @ordered
	 */
	protected PropertyDefinition ownerProperty;

	/**
	 * The default value of the '{@link #getGUID() <em>GUID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGUID()
	 * @generated
	 * @ordered
	 */
	protected static final String GUID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getGUID() <em>GUID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGUID()
	 * @generated
	 * @ordered
	 */
	protected String guid = GUID_EDEFAULT;

	/**
	 * The default value of the '{@link #getOwnerProjectName() <em>Owner Project Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwnerProjectName()
	 * @generated
	 * @ordered
	 */
	protected static final String OWNER_PROJECT_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOwnerProjectName() <em>Owner Project Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwnerProjectName()
	 * @generated
	 * @ordered
	 */
	protected String ownerProjectName = OWNER_PROJECT_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DomainInstanceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DomainPackage.Literals.DOMAIN_INSTANCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyDefinition getOwnerProperty() {
		if (ownerProperty != null && ownerProperty.eIsProxy()) {
			InternalEObject oldOwnerProperty = (InternalEObject)ownerProperty;
			ownerProperty = (PropertyDefinition)eResolveProxy(oldOwnerProperty);
			if (ownerProperty != oldOwnerProperty) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DomainPackage.DOMAIN_INSTANCE__OWNER_PROPERTY, oldOwnerProperty, ownerProperty));
			}
		}
		return ownerProperty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyDefinition basicGetOwnerProperty() {
		return ownerProperty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOwnerProperty(PropertyDefinition newOwnerProperty) {
		PropertyDefinition oldOwnerProperty = ownerProperty;
		ownerProperty = newOwnerProperty;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomainPackage.DOMAIN_INSTANCE__OWNER_PROPERTY, oldOwnerProperty, ownerProperty));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getGUID() {
		return guid;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGUID(String newGUID) {
		String oldGUID = guid;
		guid = newGUID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomainPackage.DOMAIN_INSTANCE__GUID, oldGUID, guid));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getOwnerProjectName() {
		return ownerProjectName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOwnerProjectName(String newOwnerProjectName) {
		String oldOwnerProjectName = ownerProjectName;
		ownerProjectName = newOwnerProjectName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomainPackage.DOMAIN_INSTANCE__OWNER_PROJECT_NAME, oldOwnerProjectName, ownerProjectName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DomainPackage.DOMAIN_INSTANCE__OWNER_PROPERTY:
				if (resolve) return getOwnerProperty();
				return basicGetOwnerProperty();
			case DomainPackage.DOMAIN_INSTANCE__GUID:
				return getGUID();
			case DomainPackage.DOMAIN_INSTANCE__OWNER_PROJECT_NAME:
				return getOwnerProjectName();
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
			case DomainPackage.DOMAIN_INSTANCE__OWNER_PROPERTY:
				setOwnerProperty((PropertyDefinition)newValue);
				return;
			case DomainPackage.DOMAIN_INSTANCE__GUID:
				setGUID((String)newValue);
				return;
			case DomainPackage.DOMAIN_INSTANCE__OWNER_PROJECT_NAME:
				setOwnerProjectName((String)newValue);
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
			case DomainPackage.DOMAIN_INSTANCE__OWNER_PROPERTY:
				setOwnerProperty((PropertyDefinition)null);
				return;
			case DomainPackage.DOMAIN_INSTANCE__GUID:
				setGUID(GUID_EDEFAULT);
				return;
			case DomainPackage.DOMAIN_INSTANCE__OWNER_PROJECT_NAME:
				setOwnerProjectName(OWNER_PROJECT_NAME_EDEFAULT);
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
			case DomainPackage.DOMAIN_INSTANCE__OWNER_PROPERTY:
				return ownerProperty != null;
			case DomainPackage.DOMAIN_INSTANCE__GUID:
				return GUID_EDEFAULT == null ? guid != null : !GUID_EDEFAULT.equals(guid);
			case DomainPackage.DOMAIN_INSTANCE__OWNER_PROJECT_NAME:
				return OWNER_PROJECT_NAME_EDEFAULT == null ? ownerProjectName != null : !OWNER_PROJECT_NAME_EDEFAULT.equals(ownerProjectName);
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
		result.append(" (GUID: ");
		result.append(guid);
		result.append(", ownerProjectName: ");
		result.append(ownerProjectName);
		result.append(')');
		return result.toString();
	}
	
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof DomainInstance)) {
			return false;
		}
		DomainInstance other = (DomainInstance)obj;
		//Get resource paths
		if (!resourcePath.equals(other.getResourcePath())) {
			return false;
		}
		//Get Property
		PropertyDefinition otherProp = other.getOwnerProperty();
		//Get owner entities
		Entity otherEntity = (Entity)otherProp.eContainer();
		Entity thisEntity = (Entity)ownerProperty.eContainer();
		
		if (!thisEntity.getFullPath().equals(otherEntity.getFullPath())) {
			return false;
		}
		if (!otherProp.getName().equals(ownerProperty.getName())) {
			return false;
		}
		return true;
	}



	private Domain domain;
	
	/**
	 * Get all the {@link DomainEntry} elements belonging
	 * to this {@link Domain}
	 * @generated NOT
	 */
	public EList<DomainEntry> getLocalEntries() {
		if (resourcePath != null) {
			if (domain == null) {
				domain = CommonIndexUtils.getDomain(ownerProjectName, resourcePath);
			}
			return ECollections.unmodifiableEList(domain.getEntries());
		}
		throw new UnsupportedOperationException();
	}

	/**
	 * Get all the {@link DomainEntry} elements belonging
	 * to this {@link Domain} and all its super types.
	 * @generated NOT
	 */
	public EList<DomainEntry> getAllEntries() {
		EList<DomainEntry> allEntries = getLocalEntries();
		if (domain == null) {
			domain = CommonIndexUtils.getDomain(ownerProjectName, resourcePath);
		}
		//Add super domain entries to it
		Domain superDomain = domain.getSuperDomain();
		while (superDomain != null) {
			EList<DomainEntry> superEntries = superDomain.getEntries();
			//Remove duplicates
			for (DomainEntry entry : superEntries) {
				//Search this entry in the main list .
				//if not found add it 
				if (DomainUtils.binarySearch(allEntries, entry) < 0) {
					//Then add it
					allEntries.add(entry);
				}
			}
			superDomain = superDomain.getSuperDomain();
		}
		return allEntries;
	}
} //DomainInstanceImpl
