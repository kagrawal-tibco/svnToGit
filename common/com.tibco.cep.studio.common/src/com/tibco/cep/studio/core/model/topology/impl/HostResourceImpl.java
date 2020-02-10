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

import com.tibco.cep.studio.core.model.topology.HostResource;
import com.tibco.cep.studio.core.model.topology.Software;
import com.tibco.cep.studio.core.model.topology.StartPuMethod;
import com.tibco.cep.studio.core.model.topology.TopologyPackage;
import com.tibco.cep.studio.core.model.topology.UserCredentials;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Host Resource</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.HostResourceImpl#getHostname <em>Hostname</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.HostResourceImpl#getIp <em>Ip</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.HostResourceImpl#getUserCredentials <em>User Credentials</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.HostResourceImpl#getOsType <em>Os Type</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.HostResourceImpl#getSoftware <em>Software</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.HostResourceImpl#getStartPuMethod <em>Start Pu Method</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.HostResourceImpl#getId <em>Id</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class HostResourceImpl extends EObjectImpl implements HostResource {
	/**
	 * The default value of the '{@link #getHostname() <em>Hostname</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHostname()
	 * @generated
	 * @ordered
	 */
	protected static final String HOSTNAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getHostname() <em>Hostname</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHostname()
	 * @generated
	 * @ordered
	 */
	protected String hostname = HOSTNAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getIp() <em>Ip</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIp()
	 * @generated
	 * @ordered
	 */
	protected static final String IP_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIp() <em>Ip</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIp()
	 * @generated
	 * @ordered
	 */
	protected String ip = IP_EDEFAULT;

	/**
	 * The cached value of the '{@link #getUserCredentials() <em>User Credentials</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUserCredentials()
	 * @generated
	 * @ordered
	 */
	protected UserCredentials userCredentials;

	/**
	 * The default value of the '{@link #getOsType() <em>Os Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOsType()
	 * @generated
	 * @ordered
	 */
	protected static final String OS_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOsType() <em>Os Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOsType()
	 * @generated
	 * @ordered
	 */
	protected String osType = OS_TYPE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSoftware() <em>Software</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSoftware()
	 * @generated
	 * @ordered
	 */
	protected Software software;

	/**
	 * The cached value of the '{@link #getStartPuMethod() <em>Start Pu Method</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartPuMethod()
	 * @generated
	 * @ordered
	 */
	protected StartPuMethod startPuMethod;

	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected String id = ID_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected HostResourceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TopologyPackage.Literals.HOST_RESOURCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getHostname() {
		return hostname;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHostname(String newHostname) {
		String oldHostname = hostname;
		hostname = newHostname;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TopologyPackage.HOST_RESOURCE__HOSTNAME, oldHostname, hostname));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIp(String newIp) {
		String oldIp = ip;
		ip = newIp;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TopologyPackage.HOST_RESOURCE__IP, oldIp, ip));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UserCredentials getUserCredentials() {
		return userCredentials;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetUserCredentials(UserCredentials newUserCredentials, NotificationChain msgs) {
		UserCredentials oldUserCredentials = userCredentials;
		userCredentials = newUserCredentials;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TopologyPackage.HOST_RESOURCE__USER_CREDENTIALS, oldUserCredentials, newUserCredentials);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUserCredentials(UserCredentials newUserCredentials) {
		if (newUserCredentials != userCredentials) {
			NotificationChain msgs = null;
			if (userCredentials != null)
				msgs = ((InternalEObject)userCredentials).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TopologyPackage.HOST_RESOURCE__USER_CREDENTIALS, null, msgs);
			if (newUserCredentials != null)
				msgs = ((InternalEObject)newUserCredentials).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TopologyPackage.HOST_RESOURCE__USER_CREDENTIALS, null, msgs);
			msgs = basicSetUserCredentials(newUserCredentials, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TopologyPackage.HOST_RESOURCE__USER_CREDENTIALS, newUserCredentials, newUserCredentials));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getOsType() {
		return osType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOsType(String newOsType) {
		String oldOsType = osType;
		osType = newOsType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TopologyPackage.HOST_RESOURCE__OS_TYPE, oldOsType, osType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Software getSoftware() {
		return software;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSoftware(Software newSoftware, NotificationChain msgs) {
		Software oldSoftware = software;
		software = newSoftware;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TopologyPackage.HOST_RESOURCE__SOFTWARE, oldSoftware, newSoftware);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSoftware(Software newSoftware) {
		if (newSoftware != software) {
			NotificationChain msgs = null;
			if (software != null)
				msgs = ((InternalEObject)software).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TopologyPackage.HOST_RESOURCE__SOFTWARE, null, msgs);
			if (newSoftware != null)
				msgs = ((InternalEObject)newSoftware).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TopologyPackage.HOST_RESOURCE__SOFTWARE, null, msgs);
			msgs = basicSetSoftware(newSoftware, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TopologyPackage.HOST_RESOURCE__SOFTWARE, newSoftware, newSoftware));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StartPuMethod getStartPuMethod() {
		return startPuMethod;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStartPuMethod(StartPuMethod newStartPuMethod, NotificationChain msgs) {
		StartPuMethod oldStartPuMethod = startPuMethod;
		startPuMethod = newStartPuMethod;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TopologyPackage.HOST_RESOURCE__START_PU_METHOD, oldStartPuMethod, newStartPuMethod);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStartPuMethod(StartPuMethod newStartPuMethod) {
		if (newStartPuMethod != startPuMethod) {
			NotificationChain msgs = null;
			if (startPuMethod != null)
				msgs = ((InternalEObject)startPuMethod).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TopologyPackage.HOST_RESOURCE__START_PU_METHOD, null, msgs);
			if (newStartPuMethod != null)
				msgs = ((InternalEObject)newStartPuMethod).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TopologyPackage.HOST_RESOURCE__START_PU_METHOD, null, msgs);
			msgs = basicSetStartPuMethod(newStartPuMethod, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TopologyPackage.HOST_RESOURCE__START_PU_METHOD, newStartPuMethod, newStartPuMethod));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(String newId) {
		String oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TopologyPackage.HOST_RESOURCE__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TopologyPackage.HOST_RESOURCE__USER_CREDENTIALS:
				return basicSetUserCredentials(null, msgs);
			case TopologyPackage.HOST_RESOURCE__SOFTWARE:
				return basicSetSoftware(null, msgs);
			case TopologyPackage.HOST_RESOURCE__START_PU_METHOD:
				return basicSetStartPuMethod(null, msgs);
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
			case TopologyPackage.HOST_RESOURCE__HOSTNAME:
				return getHostname();
			case TopologyPackage.HOST_RESOURCE__IP:
				return getIp();
			case TopologyPackage.HOST_RESOURCE__USER_CREDENTIALS:
				return getUserCredentials();
			case TopologyPackage.HOST_RESOURCE__OS_TYPE:
				return getOsType();
			case TopologyPackage.HOST_RESOURCE__SOFTWARE:
				return getSoftware();
			case TopologyPackage.HOST_RESOURCE__START_PU_METHOD:
				return getStartPuMethod();
			case TopologyPackage.HOST_RESOURCE__ID:
				return getId();
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
			case TopologyPackage.HOST_RESOURCE__HOSTNAME:
				setHostname((String)newValue);
				return;
			case TopologyPackage.HOST_RESOURCE__IP:
				setIp((String)newValue);
				return;
			case TopologyPackage.HOST_RESOURCE__USER_CREDENTIALS:
				setUserCredentials((UserCredentials)newValue);
				return;
			case TopologyPackage.HOST_RESOURCE__OS_TYPE:
				setOsType((String)newValue);
				return;
			case TopologyPackage.HOST_RESOURCE__SOFTWARE:
				setSoftware((Software)newValue);
				return;
			case TopologyPackage.HOST_RESOURCE__START_PU_METHOD:
				setStartPuMethod((StartPuMethod)newValue);
				return;
			case TopologyPackage.HOST_RESOURCE__ID:
				setId((String)newValue);
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
			case TopologyPackage.HOST_RESOURCE__HOSTNAME:
				setHostname(HOSTNAME_EDEFAULT);
				return;
			case TopologyPackage.HOST_RESOURCE__IP:
				setIp(IP_EDEFAULT);
				return;
			case TopologyPackage.HOST_RESOURCE__USER_CREDENTIALS:
				setUserCredentials((UserCredentials)null);
				return;
			case TopologyPackage.HOST_RESOURCE__OS_TYPE:
				setOsType(OS_TYPE_EDEFAULT);
				return;
			case TopologyPackage.HOST_RESOURCE__SOFTWARE:
				setSoftware((Software)null);
				return;
			case TopologyPackage.HOST_RESOURCE__START_PU_METHOD:
				setStartPuMethod((StartPuMethod)null);
				return;
			case TopologyPackage.HOST_RESOURCE__ID:
				setId(ID_EDEFAULT);
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
			case TopologyPackage.HOST_RESOURCE__HOSTNAME:
				return HOSTNAME_EDEFAULT == null ? hostname != null : !HOSTNAME_EDEFAULT.equals(hostname);
			case TopologyPackage.HOST_RESOURCE__IP:
				return IP_EDEFAULT == null ? ip != null : !IP_EDEFAULT.equals(ip);
			case TopologyPackage.HOST_RESOURCE__USER_CREDENTIALS:
				return userCredentials != null;
			case TopologyPackage.HOST_RESOURCE__OS_TYPE:
				return OS_TYPE_EDEFAULT == null ? osType != null : !OS_TYPE_EDEFAULT.equals(osType);
			case TopologyPackage.HOST_RESOURCE__SOFTWARE:
				return software != null;
			case TopologyPackage.HOST_RESOURCE__START_PU_METHOD:
				return startPuMethod != null;
			case TopologyPackage.HOST_RESOURCE__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
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
		result.append(" (hostname: ");
		result.append(hostname);
		result.append(", ip: ");
		result.append(ip);
		result.append(", osType: ");
		result.append(osType);
		result.append(", id: ");
		result.append(id);
		result.append(')');
		return result.toString();
	}

} //HostResourceImpl
