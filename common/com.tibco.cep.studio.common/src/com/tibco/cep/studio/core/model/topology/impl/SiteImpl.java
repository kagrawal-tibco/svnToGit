/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.model.topology.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.cep.studio.core.model.topology.Clusters;
import com.tibco.cep.studio.core.model.topology.HostResources;
import com.tibco.cep.studio.core.model.topology.Site;
import com.tibco.cep.studio.core.model.topology.TopologyPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Site</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.SiteImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.SiteImpl#getClusters <em>Clusters</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.SiteImpl#getHostResources <em>Host Resources</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.SiteImpl#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SiteImpl extends EObjectImpl implements Site {
	/**
	 * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String DESCRIPTION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected String description = DESCRIPTION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getClusters() <em>Clusters</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClusters()
	 * @generated
	 * @ordered
	 */
	protected Clusters clusters;

	/**
	 * The cached value of the '{@link #getHostResources() <em>Host Resources</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHostResources()
	 * @generated
	 * @ordered
	 */
	protected HostResources hostResources;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SiteImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TopologyPackage.Literals.SITE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDescription(String newDescription) {
		String oldDescription = description;
		description = newDescription;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TopologyPackage.SITE__DESCRIPTION, oldDescription, description));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Clusters getClusters() {
		return clusters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetClusters(Clusters newClusters, NotificationChain msgs) {
		Clusters oldClusters = clusters;
		clusters = newClusters;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TopologyPackage.SITE__CLUSTERS, oldClusters, newClusters);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setClusters(Clusters newClusters) {
		if (newClusters != clusters) {
			NotificationChain msgs = null;
			if (clusters != null)
				msgs = ((InternalEObject)clusters).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TopologyPackage.SITE__CLUSTERS, null, msgs);
			if (newClusters != null)
				msgs = ((InternalEObject)newClusters).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TopologyPackage.SITE__CLUSTERS, null, msgs);
			msgs = basicSetClusters(newClusters, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TopologyPackage.SITE__CLUSTERS, newClusters, newClusters));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HostResources getHostResources() {
		return hostResources;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetHostResources(HostResources newHostResources, NotificationChain msgs) {
		HostResources oldHostResources = hostResources;
		hostResources = newHostResources;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TopologyPackage.SITE__HOST_RESOURCES, oldHostResources, newHostResources);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHostResources(HostResources newHostResources) {
		if (newHostResources != hostResources) {
			NotificationChain msgs = null;
			if (hostResources != null)
				msgs = ((InternalEObject)hostResources).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TopologyPackage.SITE__HOST_RESOURCES, null, msgs);
			if (newHostResources != null)
				msgs = ((InternalEObject)newHostResources).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TopologyPackage.SITE__HOST_RESOURCES, null, msgs);
			msgs = basicSetHostResources(newHostResources, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TopologyPackage.SITE__HOST_RESOURCES, newHostResources, newHostResources));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TopologyPackage.SITE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TopologyPackage.SITE__CLUSTERS:
				return basicSetClusters(null, msgs);
			case TopologyPackage.SITE__HOST_RESOURCES:
				return basicSetHostResources(null, msgs);
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
			case TopologyPackage.SITE__DESCRIPTION:
				return getDescription();
			case TopologyPackage.SITE__CLUSTERS:
				return getClusters();
			case TopologyPackage.SITE__HOST_RESOURCES:
				return getHostResources();
			case TopologyPackage.SITE__NAME:
				return getName();
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
			case TopologyPackage.SITE__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case TopologyPackage.SITE__CLUSTERS:
				setClusters((Clusters)newValue);
				return;
			case TopologyPackage.SITE__HOST_RESOURCES:
				setHostResources((HostResources)newValue);
				return;
			case TopologyPackage.SITE__NAME:
				setName((String)newValue);
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
			case TopologyPackage.SITE__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case TopologyPackage.SITE__CLUSTERS:
				setClusters((Clusters)null);
				return;
			case TopologyPackage.SITE__HOST_RESOURCES:
				setHostResources((HostResources)null);
				return;
			case TopologyPackage.SITE__NAME:
				setName(NAME_EDEFAULT);
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
			case TopologyPackage.SITE__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case TopologyPackage.SITE__CLUSTERS:
				return clusters != null;
			case TopologyPackage.SITE__HOST_RESOURCES:
				return hostResources != null;
			case TopologyPackage.SITE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
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
		result.append(" (description: ");
		result.append(description);
		result.append(", name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //SiteImpl
