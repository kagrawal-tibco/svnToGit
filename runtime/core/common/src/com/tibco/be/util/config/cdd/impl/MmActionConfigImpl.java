/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.MmActionConfig;
import com.tibco.be.util.config.cdd.MmExecuteCommandConfig;
import com.tibco.be.util.config.cdd.MmSendEmailConfig;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Mm Action Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.MmActionConfigImpl#getMmExecuteCommand <em>Mm Execute Command</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.MmActionConfigImpl#getMmSendEmail <em>Mm Send Email</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MmActionConfigImpl extends EObjectImpl implements MmActionConfig {
	/**
	 * The cached value of the '{@link #getMmExecuteCommand() <em>Mm Execute Command</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMmExecuteCommand()
	 * @generated
	 * @ordered
	 */
	protected EList<MmExecuteCommandConfig> mmExecuteCommand;

	/**
	 * The cached value of the '{@link #getMmSendEmail() <em>Mm Send Email</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMmSendEmail()
	 * @generated
	 * @ordered
	 */
	protected EList<MmSendEmailConfig> mmSendEmail;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MmActionConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getMmActionConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<MmExecuteCommandConfig> getMmExecuteCommand() {
		if (mmExecuteCommand == null) {
			mmExecuteCommand = new EObjectContainmentEList<MmExecuteCommandConfig>(MmExecuteCommandConfig.class, this, CddPackage.MM_ACTION_CONFIG__MM_EXECUTE_COMMAND);
		}
		return mmExecuteCommand;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<MmSendEmailConfig> getMmSendEmail() {
		if (mmSendEmail == null) {
			mmSendEmail = new EObjectContainmentEList<MmSendEmailConfig>(MmSendEmailConfig.class, this, CddPackage.MM_ACTION_CONFIG__MM_SEND_EMAIL);
		}
		return mmSendEmail;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.MM_ACTION_CONFIG__MM_EXECUTE_COMMAND:
				return ((InternalEList<?>)getMmExecuteCommand()).basicRemove(otherEnd, msgs);
			case CddPackage.MM_ACTION_CONFIG__MM_SEND_EMAIL:
				return ((InternalEList<?>)getMmSendEmail()).basicRemove(otherEnd, msgs);
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
			case CddPackage.MM_ACTION_CONFIG__MM_EXECUTE_COMMAND:
				return getMmExecuteCommand();
			case CddPackage.MM_ACTION_CONFIG__MM_SEND_EMAIL:
				return getMmSendEmail();
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
			case CddPackage.MM_ACTION_CONFIG__MM_EXECUTE_COMMAND:
				getMmExecuteCommand().clear();
				getMmExecuteCommand().addAll((Collection<? extends MmExecuteCommandConfig>)newValue);
				return;
			case CddPackage.MM_ACTION_CONFIG__MM_SEND_EMAIL:
				getMmSendEmail().clear();
				getMmSendEmail().addAll((Collection<? extends MmSendEmailConfig>)newValue);
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
			case CddPackage.MM_ACTION_CONFIG__MM_EXECUTE_COMMAND:
				getMmExecuteCommand().clear();
				return;
			case CddPackage.MM_ACTION_CONFIG__MM_SEND_EMAIL:
				getMmSendEmail().clear();
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
			case CddPackage.MM_ACTION_CONFIG__MM_EXECUTE_COMMAND:
				return mmExecuteCommand != null && !mmExecuteCommand.isEmpty();
			case CddPackage.MM_ACTION_CONFIG__MM_SEND_EMAIL:
				return mmSendEmail != null && !mmSendEmail.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //MmActionConfigImpl
