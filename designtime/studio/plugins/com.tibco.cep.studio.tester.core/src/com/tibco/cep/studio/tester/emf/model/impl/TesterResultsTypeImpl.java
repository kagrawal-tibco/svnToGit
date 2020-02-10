/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.tester.emf.model.impl;

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

import com.tibco.cep.studio.tester.emf.model.ExecutionObjectType;
import com.tibco.cep.studio.tester.emf.model.ModelPackage;
import com.tibco.cep.studio.tester.emf.model.TesterResultsType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Tester Results Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.impl.TesterResultsTypeImpl#getRunName <em>Run Name</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.impl.TesterResultsTypeImpl#getExecutionObject <em>Execution Object</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.impl.TesterResultsTypeImpl#getProject <em>Project</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TesterResultsTypeImpl extends EObjectImpl implements TesterResultsType {
	/**
	 * The default value of the '{@link #getRunName() <em>Run Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRunName()
	 * @generated
	 * @ordered
	 */
	protected static final String RUN_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRunName() <em>Run Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRunName()
	 * @generated
	 * @ordered
	 */
	protected String runName = RUN_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getExecutionObject() <em>Execution Object</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExecutionObject()
	 * @generated
	 * @ordered
	 */
	protected EList<ExecutionObjectType> executionObject;

	/**
	 * The default value of the '{@link #getProject() <em>Project</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProject()
	 * @generated
	 * @ordered
	 */
	protected static final String PROJECT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getProject() <em>Project</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProject()
	 * @generated
	 * @ordered
	 */
	protected String project = PROJECT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TesterResultsTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.TESTER_RESULTS_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getRunName() {
		return runName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRunName(String newRunName) {
		String oldRunName = runName;
		runName = newRunName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.TESTER_RESULTS_TYPE__RUN_NAME, oldRunName, runName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ExecutionObjectType> getExecutionObject() {
		if (executionObject == null) {
			executionObject = new EObjectContainmentEList<ExecutionObjectType>(ExecutionObjectType.class, this, ModelPackage.TESTER_RESULTS_TYPE__EXECUTION_OBJECT);
		}
		return executionObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getProject() {
		return project;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProject(String newProject) {
		String oldProject = project;
		project = newProject;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.TESTER_RESULTS_TYPE__PROJECT, oldProject, project));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.TESTER_RESULTS_TYPE__EXECUTION_OBJECT:
				return ((InternalEList<?>)getExecutionObject()).basicRemove(otherEnd, msgs);
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
			case ModelPackage.TESTER_RESULTS_TYPE__RUN_NAME:
				return getRunName();
			case ModelPackage.TESTER_RESULTS_TYPE__EXECUTION_OBJECT:
				return getExecutionObject();
			case ModelPackage.TESTER_RESULTS_TYPE__PROJECT:
				return getProject();
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
			case ModelPackage.TESTER_RESULTS_TYPE__RUN_NAME:
				setRunName((String)newValue);
				return;
			case ModelPackage.TESTER_RESULTS_TYPE__EXECUTION_OBJECT:
				getExecutionObject().clear();
				getExecutionObject().addAll((Collection<? extends ExecutionObjectType>)newValue);
				return;
			case ModelPackage.TESTER_RESULTS_TYPE__PROJECT:
				setProject((String)newValue);
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
			case ModelPackage.TESTER_RESULTS_TYPE__RUN_NAME:
				setRunName(RUN_NAME_EDEFAULT);
				return;
			case ModelPackage.TESTER_RESULTS_TYPE__EXECUTION_OBJECT:
				getExecutionObject().clear();
				return;
			case ModelPackage.TESTER_RESULTS_TYPE__PROJECT:
				setProject(PROJECT_EDEFAULT);
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
			case ModelPackage.TESTER_RESULTS_TYPE__RUN_NAME:
				return RUN_NAME_EDEFAULT == null ? runName != null : !RUN_NAME_EDEFAULT.equals(runName);
			case ModelPackage.TESTER_RESULTS_TYPE__EXECUTION_OBJECT:
				return executionObject != null && !executionObject.isEmpty();
			case ModelPackage.TESTER_RESULTS_TYPE__PROJECT:
				return PROJECT_EDEFAULT == null ? project != null : !PROJECT_EDEFAULT.equals(project);
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
		result.append(" (runName: ");
		result.append(runName);
		result.append(", project: ");
		result.append(project);
		result.append(')');
		return result.toString();
	}

} //TesterResultsTypeImpl
