/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.table.model.domainmodel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.decision.table.model.domainmodel.Domain;
import com.tibco.cep.decision.table.model.domainmodel.DomainEntry;
import com.tibco.cep.decision.table.model.domainmodel.DomainModelPackage;
import com.tibco.cep.decision.table.model.dtmodel.ResourceType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Domain</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.decision.table.model.domainmodel.impl.DomainImpl#isModified <em>Modified</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.domainmodel.impl.DomainImpl#getDomainEntry <em>Domain Entry</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.domainmodel.impl.DomainImpl#getType <em>Type</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.domainmodel.impl.DomainImpl#getResourceType <em>Resource Type</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.domainmodel.impl.DomainImpl#getResource <em>Resource</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.domainmodel.impl.DomainImpl#isOverride <em>Override</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.domainmodel.impl.DomainImpl#getDbRef <em>Db Ref</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DomainImpl extends EObjectImpl implements Domain {
	/**
	 * The default value of the '{@link #isModified() <em>Modified</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isModified()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MODIFIED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isModified() <em>Modified</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isModified()
	 * @generated
	 * @ordered
	 */
	protected boolean modified = MODIFIED_EDEFAULT;

	/**
	 * The cached value of the '{@link #getDomainEntry() <em>Domain Entry</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDomainEntry()
	 * @generated
	 * @ordered
	 */
	protected EList<DomainEntry> domainEntry;

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
	 * The default value of the '{@link #getResourceType() <em>Resource Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResourceType()
	 * @generated
	 * @ordered
	 */
	protected static final ResourceType RESOURCE_TYPE_EDEFAULT = ResourceType.UNDEFINED;

	/**
	 * The cached value of the '{@link #getResourceType() <em>Resource Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResourceType()
	 * @generated
	 * @ordered
	 */
	protected ResourceType resourceType = RESOURCE_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getResource() <em>Resource</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResource()
	 * @generated
	 * @ordered
	 */
	protected static final String RESOURCE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getResource() <em>Resource</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResource()
	 * @generated
	 * @ordered
	 */
	protected String resource = RESOURCE_EDEFAULT;

	/**
	 * The default value of the '{@link #isOverride() <em>Override</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isOverride()
	 * @generated
	 * @ordered
	 */
	protected static final boolean OVERRIDE_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isOverride() <em>Override</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isOverride()
	 * @generated
	 * @ordered
	 */
	protected boolean override = OVERRIDE_EDEFAULT;

	/**
	 * The default value of the '{@link #getDbRef() <em>Db Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDbRef()
	 * @generated
	 * @ordered
	 */
	protected static final String DB_REF_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDbRef() <em>Db Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDbRef()
	 * @generated
	 * @ordered
	 */
	protected String dbRef = DB_REF_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DomainImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DomainModelPackage.Literals.DOMAIN;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isModified() {
		return modified;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setModified(boolean newModified) {
		boolean oldModified = modified;
		modified = newModified;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomainModelPackage.DOMAIN__MODIFIED, oldModified, modified));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<DomainEntry> getDomainEntry() {
		if (domainEntry == null) {
			domainEntry = new EObjectContainmentEList<DomainEntry>(DomainEntry.class, this, DomainModelPackage.DOMAIN__DOMAIN_ENTRY);
		}
		return domainEntry;
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
			eNotify(new ENotificationImpl(this, Notification.SET, DomainModelPackage.DOMAIN__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ResourceType getResourceType() {
		return resourceType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setResourceType(ResourceType newResourceType) {
		ResourceType oldResourceType = resourceType;
		resourceType = newResourceType == null ? RESOURCE_TYPE_EDEFAULT : newResourceType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomainModelPackage.DOMAIN__RESOURCE_TYPE, oldResourceType, resourceType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getResource() {
		return resource;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setResource(String newResource) {
		String oldResource = resource;
		resource = newResource;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomainModelPackage.DOMAIN__RESOURCE, oldResource, resource));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isOverride() {
		return override;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOverride(boolean newOverride) {
		boolean oldOverride = override;
		override = newOverride;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomainModelPackage.DOMAIN__OVERRIDE, oldOverride, override));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDbRef() {
		return dbRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDbRef(String newDbRef) {
		String oldDbRef = dbRef;
		dbRef = newDbRef;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomainModelPackage.DOMAIN__DB_REF, oldDbRef, dbRef));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DomainModelPackage.DOMAIN__DOMAIN_ENTRY:
				return ((InternalEList<?>)getDomainEntry()).basicRemove(otherEnd, msgs);
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
			case DomainModelPackage.DOMAIN__MODIFIED:
				return isModified() ? Boolean.TRUE : Boolean.FALSE;
			case DomainModelPackage.DOMAIN__DOMAIN_ENTRY:
				return getDomainEntry();
			case DomainModelPackage.DOMAIN__TYPE:
				return getType();
			case DomainModelPackage.DOMAIN__RESOURCE_TYPE:
				return getResourceType();
			case DomainModelPackage.DOMAIN__RESOURCE:
				return getResource();
			case DomainModelPackage.DOMAIN__OVERRIDE:
				return isOverride() ? Boolean.TRUE : Boolean.FALSE;
			case DomainModelPackage.DOMAIN__DB_REF:
				return getDbRef();
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
			case DomainModelPackage.DOMAIN__MODIFIED:
				setModified(((Boolean)newValue).booleanValue());
				return;
			case DomainModelPackage.DOMAIN__DOMAIN_ENTRY:
				getDomainEntry().clear();
				getDomainEntry().addAll((Collection<? extends DomainEntry>)newValue);
				return;
			case DomainModelPackage.DOMAIN__TYPE:
				setType((String)newValue);
				return;
			case DomainModelPackage.DOMAIN__RESOURCE_TYPE:
				setResourceType((ResourceType)newValue);
				return;
			case DomainModelPackage.DOMAIN__RESOURCE:
				setResource((String)newValue);
				return;
			case DomainModelPackage.DOMAIN__OVERRIDE:
				setOverride(((Boolean)newValue).booleanValue());
				return;
			case DomainModelPackage.DOMAIN__DB_REF:
				setDbRef((String)newValue);
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
			case DomainModelPackage.DOMAIN__MODIFIED:
				setModified(MODIFIED_EDEFAULT);
				return;
			case DomainModelPackage.DOMAIN__DOMAIN_ENTRY:
				getDomainEntry().clear();
				return;
			case DomainModelPackage.DOMAIN__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case DomainModelPackage.DOMAIN__RESOURCE_TYPE:
				setResourceType(RESOURCE_TYPE_EDEFAULT);
				return;
			case DomainModelPackage.DOMAIN__RESOURCE:
				setResource(RESOURCE_EDEFAULT);
				return;
			case DomainModelPackage.DOMAIN__OVERRIDE:
				setOverride(OVERRIDE_EDEFAULT);
				return;
			case DomainModelPackage.DOMAIN__DB_REF:
				setDbRef(DB_REF_EDEFAULT);
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
			case DomainModelPackage.DOMAIN__MODIFIED:
				return modified != MODIFIED_EDEFAULT;
			case DomainModelPackage.DOMAIN__DOMAIN_ENTRY:
				return domainEntry != null && !domainEntry.isEmpty();
			case DomainModelPackage.DOMAIN__TYPE:
				return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
			case DomainModelPackage.DOMAIN__RESOURCE_TYPE:
				return resourceType != RESOURCE_TYPE_EDEFAULT;
			case DomainModelPackage.DOMAIN__RESOURCE:
				return RESOURCE_EDEFAULT == null ? resource != null : !RESOURCE_EDEFAULT.equals(resource);
			case DomainModelPackage.DOMAIN__OVERRIDE:
				return override != OVERRIDE_EDEFAULT;
			case DomainModelPackage.DOMAIN__DB_REF:
				return DB_REF_EDEFAULT == null ? dbRef != null : !DB_REF_EDEFAULT.equals(dbRef);
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
		result.append(" (modified: ");
		result.append(modified);
		result.append(", type: ");
		result.append(type);
		result.append(", resourceType: ");
		result.append(resourceType);
		result.append(", resource: ");
		result.append(resource);
		result.append(", override: ");
		result.append(override);
		result.append(", dbRef: ");
		result.append(dbRef);
		result.append(')');
		return result.toString();
	}

} //DomainImpl
