/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.model.topology.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.cep.studio.core.model.topology.ProcessingUnitConfig;
import com.tibco.cep.studio.core.model.topology.TopologyPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Processing Unit Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.ProcessingUnitConfigImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.ProcessingUnitConfigImpl#getJmxport <em>Jmxport</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.ProcessingUnitConfigImpl#getPuid <em>Puid</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.ProcessingUnitConfigImpl#isUseAsEngineName <em>Use As Engine Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProcessingUnitConfigImpl extends EObjectImpl implements ProcessingUnitConfig {
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
	 * The default value of the '{@link #getJmxport() <em>Jmxport</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJmxport()
	 * @generated
	 * @ordered
	 */
	protected static final String JMXPORT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getJmxport() <em>Jmxport</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJmxport()
	 * @generated
	 * @ordered
	 */
	protected String jmxport = JMXPORT_EDEFAULT;

	/**
	 * The default value of the '{@link #getPuid() <em>Puid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPuid()
	 * @generated
	 * @ordered
	 */
	protected static final String PUID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPuid() <em>Puid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPuid()
	 * @generated
	 * @ordered
	 */
	protected String puid = PUID_EDEFAULT;

	/**
	 * The default value of the '{@link #isUseAsEngineName() <em>Use As Engine Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUseAsEngineName()
	 * @generated
	 * @ordered
	 */
	protected static final boolean USE_AS_ENGINE_NAME_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isUseAsEngineName() <em>Use As Engine Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUseAsEngineName()
	 * @generated
	 * @ordered
	 */
	protected boolean useAsEngineName = USE_AS_ENGINE_NAME_EDEFAULT;

	/**
	 * This is true if the Use As Engine Name attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean useAsEngineNameESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProcessingUnitConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TopologyPackage.Literals.PROCESSING_UNIT_CONFIG;
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
			eNotify(new ENotificationImpl(this, Notification.SET, TopologyPackage.PROCESSING_UNIT_CONFIG__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getJmxport() {
		return jmxport;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setJmxport(String newJmxport) {
		String oldJmxport = jmxport;
		jmxport = newJmxport;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TopologyPackage.PROCESSING_UNIT_CONFIG__JMXPORT, oldJmxport, jmxport));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPuid() {
		return puid;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPuid(String newPuid) {
		String oldPuid = puid;
		puid = newPuid;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TopologyPackage.PROCESSING_UNIT_CONFIG__PUID, oldPuid, puid));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isUseAsEngineName() {
		return useAsEngineName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUseAsEngineName(boolean newUseAsEngineName) {
		boolean oldUseAsEngineName = useAsEngineName;
		useAsEngineName = newUseAsEngineName;
		boolean oldUseAsEngineNameESet = useAsEngineNameESet;
		useAsEngineNameESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TopologyPackage.PROCESSING_UNIT_CONFIG__USE_AS_ENGINE_NAME, oldUseAsEngineName, useAsEngineName, !oldUseAsEngineNameESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetUseAsEngineName() {
		boolean oldUseAsEngineName = useAsEngineName;
		boolean oldUseAsEngineNameESet = useAsEngineNameESet;
		useAsEngineName = USE_AS_ENGINE_NAME_EDEFAULT;
		useAsEngineNameESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, TopologyPackage.PROCESSING_UNIT_CONFIG__USE_AS_ENGINE_NAME, oldUseAsEngineName, USE_AS_ENGINE_NAME_EDEFAULT, oldUseAsEngineNameESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetUseAsEngineName() {
		return useAsEngineNameESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TopologyPackage.PROCESSING_UNIT_CONFIG__ID:
				return getId();
			case TopologyPackage.PROCESSING_UNIT_CONFIG__JMXPORT:
				return getJmxport();
			case TopologyPackage.PROCESSING_UNIT_CONFIG__PUID:
				return getPuid();
			case TopologyPackage.PROCESSING_UNIT_CONFIG__USE_AS_ENGINE_NAME:
				return isUseAsEngineName();
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
			case TopologyPackage.PROCESSING_UNIT_CONFIG__ID:
				setId((String)newValue);
				return;
			case TopologyPackage.PROCESSING_UNIT_CONFIG__JMXPORT:
				setJmxport((String)newValue);
				return;
			case TopologyPackage.PROCESSING_UNIT_CONFIG__PUID:
				setPuid((String)newValue);
				return;
			case TopologyPackage.PROCESSING_UNIT_CONFIG__USE_AS_ENGINE_NAME:
				setUseAsEngineName((Boolean)newValue);
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
			case TopologyPackage.PROCESSING_UNIT_CONFIG__ID:
				setId(ID_EDEFAULT);
				return;
			case TopologyPackage.PROCESSING_UNIT_CONFIG__JMXPORT:
				setJmxport(JMXPORT_EDEFAULT);
				return;
			case TopologyPackage.PROCESSING_UNIT_CONFIG__PUID:
				setPuid(PUID_EDEFAULT);
				return;
			case TopologyPackage.PROCESSING_UNIT_CONFIG__USE_AS_ENGINE_NAME:
				unsetUseAsEngineName();
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
			case TopologyPackage.PROCESSING_UNIT_CONFIG__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case TopologyPackage.PROCESSING_UNIT_CONFIG__JMXPORT:
				return JMXPORT_EDEFAULT == null ? jmxport != null : !JMXPORT_EDEFAULT.equals(jmxport);
			case TopologyPackage.PROCESSING_UNIT_CONFIG__PUID:
				return PUID_EDEFAULT == null ? puid != null : !PUID_EDEFAULT.equals(puid);
			case TopologyPackage.PROCESSING_UNIT_CONFIG__USE_AS_ENGINE_NAME:
				return isSetUseAsEngineName();
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
		result.append(" (id: ");
		result.append(id);
		result.append(", jmxport: ");
		result.append(jmxport);
		result.append(", puid: ");
		result.append(puid);
		result.append(", useAsEngineName: ");
		if (useAsEngineNameESet) result.append(useAsEngineName); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //ProcessingUnitConfigImpl
