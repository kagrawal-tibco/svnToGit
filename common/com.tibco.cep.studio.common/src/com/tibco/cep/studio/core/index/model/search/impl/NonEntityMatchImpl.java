/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model.search.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.cep.studio.core.index.model.search.NonEntityMatch;
import com.tibco.cep.studio.core.index.model.search.SearchPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Non Entity Match</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.index.model.search.impl.NonEntityMatchImpl#getProjectName <em>Project Name</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.search.impl.NonEntityMatchImpl#getFilePath <em>File Path</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.search.impl.NonEntityMatchImpl#getMatch <em>Match</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class NonEntityMatchImpl extends EObjectImpl implements NonEntityMatch {
	/**
	 * The default value of the '{@link #getProjectName() <em>Project Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProjectName()
	 * @generated
	 * @ordered
	 */
	protected static final String PROJECT_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getProjectName() <em>Project Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProjectName()
	 * @generated
	 * @ordered
	 */
	protected String projectName = PROJECT_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getFilePath() <em>File Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFilePath()
	 * @generated
	 * @ordered
	 */
	protected static final String FILE_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFilePath() <em>File Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFilePath()
	 * @generated
	 * @ordered
	 */
	protected String filePath = FILE_PATH_EDEFAULT;

	/**
	 * The default value of the '{@link #getMatch() <em>Match</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMatch()
	 * @generated
	 * @ordered
	 */
	protected static final Object MATCH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMatch() <em>Match</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMatch()
	 * @generated
	 * @ordered
	 */
	protected Object match = MATCH_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NonEntityMatchImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SearchPackage.Literals.NON_ENTITY_MATCH;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProjectName(String newProjectName) {
		String oldProjectName = projectName;
		projectName = newProjectName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SearchPackage.NON_ENTITY_MATCH__PROJECT_NAME, oldProjectName, projectName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFilePath(String newFilePath) {
		String oldFilePath = filePath;
		filePath = newFilePath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SearchPackage.NON_ENTITY_MATCH__FILE_PATH, oldFilePath, filePath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getMatch() {
		return match;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMatch(Object newMatch) {
		Object oldMatch = match;
		match = newMatch;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SearchPackage.NON_ENTITY_MATCH__MATCH, oldMatch, match));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SearchPackage.NON_ENTITY_MATCH__PROJECT_NAME:
				return getProjectName();
			case SearchPackage.NON_ENTITY_MATCH__FILE_PATH:
				return getFilePath();
			case SearchPackage.NON_ENTITY_MATCH__MATCH:
				return getMatch();
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
			case SearchPackage.NON_ENTITY_MATCH__PROJECT_NAME:
				setProjectName((String)newValue);
				return;
			case SearchPackage.NON_ENTITY_MATCH__FILE_PATH:
				setFilePath((String)newValue);
				return;
			case SearchPackage.NON_ENTITY_MATCH__MATCH:
				setMatch(newValue);
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
			case SearchPackage.NON_ENTITY_MATCH__PROJECT_NAME:
				setProjectName(PROJECT_NAME_EDEFAULT);
				return;
			case SearchPackage.NON_ENTITY_MATCH__FILE_PATH:
				setFilePath(FILE_PATH_EDEFAULT);
				return;
			case SearchPackage.NON_ENTITY_MATCH__MATCH:
				setMatch(MATCH_EDEFAULT);
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
			case SearchPackage.NON_ENTITY_MATCH__PROJECT_NAME:
				return PROJECT_NAME_EDEFAULT == null ? projectName != null : !PROJECT_NAME_EDEFAULT.equals(projectName);
			case SearchPackage.NON_ENTITY_MATCH__FILE_PATH:
				return FILE_PATH_EDEFAULT == null ? filePath != null : !FILE_PATH_EDEFAULT.equals(filePath);
			case SearchPackage.NON_ENTITY_MATCH__MATCH:
				return MATCH_EDEFAULT == null ? match != null : !MATCH_EDEFAULT.equals(match);
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
		result.append(" (projectName: ");
		result.append(projectName);
		result.append(", filePath: ");
		result.append(filePath);
		result.append(", match: ");
		result.append(match);
		result.append(')');
		return result.toString();
	}

} //NonEntityMatchImpl
