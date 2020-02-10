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

import com.tibco.cep.decisionproject.ontology.OntologyPackage;
import com.tibco.cep.decisionproject.ontology.RuleSetParticipant;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Rule Set Participant</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.impl.RuleSetParticipantImpl#getPath <em>Path</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.impl.RuleSetParticipantImpl#getParentPath <em>Parent Path</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RuleSetParticipantImpl extends AbstractResourceImpl implements RuleSetParticipant {
	/**
	 * The default value of the '{@link #getPath() <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPath()
	 * @generated
	 * @ordered
	 */
	protected static final String PATH_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getPath() <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPath()
	 * @generated
	 * @ordered
	 */
	protected String path = PATH_EDEFAULT;
	/**
	 * The default value of the '{@link #getParentPath() <em>Parent Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParentPath()
	 * @generated
	 * @ordered
	 */
	protected static final String PARENT_PATH_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getParentPath() <em>Parent Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParentPath()
	 * @generated
	 * @ordered
	 */
	protected String parentPath = PARENT_PATH_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RuleSetParticipantImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OntologyPackage.Literals.RULE_SET_PARTICIPANT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPath() {
		return path;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPath(String newPath) {
		String oldPath = path;
		path = newPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OntologyPackage.RULE_SET_PARTICIPANT__PATH, oldPath, path));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getParentPath() {
		return parentPath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParentPath(String newParentPath) {
		String oldParentPath = parentPath;
		parentPath = newParentPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OntologyPackage.RULE_SET_PARTICIPANT__PARENT_PATH, oldParentPath, parentPath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case OntologyPackage.RULE_SET_PARTICIPANT__PATH:
				return getPath();
			case OntologyPackage.RULE_SET_PARTICIPANT__PARENT_PATH:
				return getParentPath();
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
			case OntologyPackage.RULE_SET_PARTICIPANT__PATH:
				setPath((String)newValue);
				return;
			case OntologyPackage.RULE_SET_PARTICIPANT__PARENT_PATH:
				setParentPath((String)newValue);
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
			case OntologyPackage.RULE_SET_PARTICIPANT__PATH:
				setPath(PATH_EDEFAULT);
				return;
			case OntologyPackage.RULE_SET_PARTICIPANT__PARENT_PATH:
				setParentPath(PARENT_PATH_EDEFAULT);
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
			case OntologyPackage.RULE_SET_PARTICIPANT__PATH:
				return PATH_EDEFAULT == null ? path != null : !PATH_EDEFAULT.equals(path);
			case OntologyPackage.RULE_SET_PARTICIPANT__PARENT_PATH:
				return PARENT_PATH_EDEFAULT == null ? parentPath != null : !PARENT_PATH_EDEFAULT.equals(parentPath);
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
		result.append(" (path: ");
		result.append(path);
		result.append(", parentPath: ");
		result.append(parentPath);
		result.append(')');
		return result.toString();
	}

} //RuleSetParticipantImpl
