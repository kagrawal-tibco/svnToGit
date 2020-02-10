/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.archive.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.cep.designtime.core.model.archive.ArchiveFactory;
import com.tibco.cep.designtime.core.model.archive.ArchivePackage;
import com.tibco.cep.designtime.core.model.archive.InputDestination;
import com.tibco.cep.designtime.core.model.archive.WORKERS;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Input Destination</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.impl.InputDestinationImpl#getDestinationURI <em>Destination URI</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.impl.InputDestinationImpl#getPreprocessor <em>Preprocessor</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.impl.InputDestinationImpl#isDefault <em>Default</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.impl.InputDestinationImpl#isEnable <em>Enable</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.impl.InputDestinationImpl#getWorkers <em>Workers</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.impl.InputDestinationImpl#getQueueSize <em>Queue Size</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.impl.InputDestinationImpl#getThreadAffinityRuleFunction <em>Thread Affinity Rule Function</em>}</li>
 * </ul>
 * </p>
 *
 * @generated NOT
 */
public class InputDestinationImpl extends EObjectImpl implements InputDestination, Cloneable {
	/**
	 * The default value of the '{@link #getDestinationURI() <em>Destination URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDestinationURI()
	 * @generated
	 * @ordered
	 */
	protected static final String DESTINATION_URI_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDestinationURI() <em>Destination URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDestinationURI()
	 * @generated
	 * @ordered
	 */
	protected String destinationURI = DESTINATION_URI_EDEFAULT;

	/**
	 * The default value of the '{@link #getPreprocessor() <em>Preprocessor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPreprocessor()
	 * @generated
	 * @ordered
	 */
	protected static final String PREPROCESSOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPreprocessor() <em>Preprocessor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPreprocessor()
	 * @generated
	 * @ordered
	 */
	protected String preprocessor = PREPROCESSOR_EDEFAULT;

	/**
	 * The default value of the '{@link #isDefault() <em>Default</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDefault()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DEFAULT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDefault() <em>Default</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDefault()
	 * @generated
	 * @ordered
	 */
	protected boolean default_ = DEFAULT_EDEFAULT;

	/**
	 * The default value of the '{@link #isEnable() <em>Enable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isEnable()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ENABLE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isEnable() <em>Enable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isEnable()
	 * @generated
	 * @ordered
	 */
	protected boolean enable = ENABLE_EDEFAULT;

	/**
	 * The default value of the '{@link #getWorkers() <em>Workers</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWorkers()
	 * @generated
	 * @ordered
	 */
	protected static final WORKERS WORKERS_EDEFAULT = WORKERS.SHARED_QUEUE_AND_THREADS;

	/**
	 * The cached value of the '{@link #getWorkers() <em>Workers</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWorkers()
	 * @generated
	 * @ordered
	 */
	protected WORKERS workers = WORKERS_EDEFAULT;

	/**
	 * The default value of the '{@link #getQueueSize() <em>Queue Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQueueSize()
	 * @generated
	 * @ordered
	 */
	protected static final int QUEUE_SIZE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getQueueSize() <em>Queue Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQueueSize()
	 * @generated
	 * @ordered
	 */
	protected int queueSize = QUEUE_SIZE_EDEFAULT;

	/**
	 * The default value of the '{@link #getThreadAffinityRuleFunction() <em>Thread Affinity Rule Function</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getThreadAffinityRuleFunction()
	 * @generated
	 * @ordered
	 */
	protected static final String THREAD_AFFINITY_RULE_FUNCTION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getThreadAffinityRuleFunction() <em>Thread Affinity Rule Function</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getThreadAffinityRuleFunction()
	 * @generated
	 * @ordered
	 */
	protected String threadAffinityRuleFunction = THREAD_AFFINITY_RULE_FUNCTION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InputDestinationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ArchivePackage.Literals.INPUT_DESTINATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDestinationURI() {
		return destinationURI;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDestinationURI(String newDestinationURI) {
		String oldDestinationURI = destinationURI;
		destinationURI = newDestinationURI;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArchivePackage.INPUT_DESTINATION__DESTINATION_URI, oldDestinationURI, destinationURI));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPreprocessor() {
		return preprocessor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPreprocessor(String newPreprocessor) {
		String oldPreprocessor = preprocessor;
		preprocessor = newPreprocessor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArchivePackage.INPUT_DESTINATION__PREPROCESSOR, oldPreprocessor, preprocessor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isDefault() {
		return default_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDefault(boolean newDefault) {
		boolean oldDefault = default_;
		default_ = newDefault;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArchivePackage.INPUT_DESTINATION__DEFAULT, oldDefault, default_));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isEnable() {
		return enable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEnable(boolean newEnable) {
		boolean oldEnable = enable;
		enable = newEnable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArchivePackage.INPUT_DESTINATION__ENABLE, oldEnable, enable));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WORKERS getWorkers() {
		return workers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWorkers(WORKERS newWorkers) {
		WORKERS oldWorkers = workers;
		workers = newWorkers == null ? WORKERS_EDEFAULT : newWorkers;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArchivePackage.INPUT_DESTINATION__WORKERS, oldWorkers, workers));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getQueueSize() {
		return queueSize;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setQueueSize(int newQueueSize) {
		int oldQueueSize = queueSize;
		queueSize = newQueueSize;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArchivePackage.INPUT_DESTINATION__QUEUE_SIZE, oldQueueSize, queueSize));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getThreadAffinityRuleFunction() {
		return threadAffinityRuleFunction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setThreadAffinityRuleFunction(String newThreadAffinityRuleFunction) {
		String oldThreadAffinityRuleFunction = threadAffinityRuleFunction;
		threadAffinityRuleFunction = newThreadAffinityRuleFunction;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArchivePackage.INPUT_DESTINATION__THREAD_AFFINITY_RULE_FUNCTION, oldThreadAffinityRuleFunction, threadAffinityRuleFunction));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ArchivePackage.INPUT_DESTINATION__DESTINATION_URI:
				return getDestinationURI();
			case ArchivePackage.INPUT_DESTINATION__PREPROCESSOR:
				return getPreprocessor();
			case ArchivePackage.INPUT_DESTINATION__DEFAULT:
				return isDefault() ? Boolean.TRUE : Boolean.FALSE;
			case ArchivePackage.INPUT_DESTINATION__ENABLE:
				return isEnable() ? Boolean.TRUE : Boolean.FALSE;
			case ArchivePackage.INPUT_DESTINATION__WORKERS:
				return getWorkers();
			case ArchivePackage.INPUT_DESTINATION__QUEUE_SIZE:
				return new Integer(getQueueSize());
			case ArchivePackage.INPUT_DESTINATION__THREAD_AFFINITY_RULE_FUNCTION:
				return getThreadAffinityRuleFunction();
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
			case ArchivePackage.INPUT_DESTINATION__DESTINATION_URI:
				setDestinationURI((String)newValue);
				return;
			case ArchivePackage.INPUT_DESTINATION__PREPROCESSOR:
				setPreprocessor((String)newValue);
				return;
			case ArchivePackage.INPUT_DESTINATION__DEFAULT:
				setDefault(((Boolean)newValue).booleanValue());
				return;
			case ArchivePackage.INPUT_DESTINATION__ENABLE:
				setEnable(((Boolean)newValue).booleanValue());
				return;
			case ArchivePackage.INPUT_DESTINATION__WORKERS:
				setWorkers((WORKERS)newValue);
				return;
			case ArchivePackage.INPUT_DESTINATION__QUEUE_SIZE:
				setQueueSize(((Integer)newValue).intValue());
				return;
			case ArchivePackage.INPUT_DESTINATION__THREAD_AFFINITY_RULE_FUNCTION:
				setThreadAffinityRuleFunction((String)newValue);
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
			case ArchivePackage.INPUT_DESTINATION__DESTINATION_URI:
				setDestinationURI(DESTINATION_URI_EDEFAULT);
				return;
			case ArchivePackage.INPUT_DESTINATION__PREPROCESSOR:
				setPreprocessor(PREPROCESSOR_EDEFAULT);
				return;
			case ArchivePackage.INPUT_DESTINATION__DEFAULT:
				setDefault(DEFAULT_EDEFAULT);
				return;
			case ArchivePackage.INPUT_DESTINATION__ENABLE:
				setEnable(ENABLE_EDEFAULT);
				return;
			case ArchivePackage.INPUT_DESTINATION__WORKERS:
				setWorkers(WORKERS_EDEFAULT);
				return;
			case ArchivePackage.INPUT_DESTINATION__QUEUE_SIZE:
				setQueueSize(QUEUE_SIZE_EDEFAULT);
				return;
			case ArchivePackage.INPUT_DESTINATION__THREAD_AFFINITY_RULE_FUNCTION:
				setThreadAffinityRuleFunction(THREAD_AFFINITY_RULE_FUNCTION_EDEFAULT);
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
			case ArchivePackage.INPUT_DESTINATION__DESTINATION_URI:
				return DESTINATION_URI_EDEFAULT == null ? destinationURI != null : !DESTINATION_URI_EDEFAULT.equals(destinationURI);
			case ArchivePackage.INPUT_DESTINATION__PREPROCESSOR:
				return PREPROCESSOR_EDEFAULT == null ? preprocessor != null : !PREPROCESSOR_EDEFAULT.equals(preprocessor);
			case ArchivePackage.INPUT_DESTINATION__DEFAULT:
				return default_ != DEFAULT_EDEFAULT;
			case ArchivePackage.INPUT_DESTINATION__ENABLE:
				return enable != ENABLE_EDEFAULT;
			case ArchivePackage.INPUT_DESTINATION__WORKERS:
				return workers != WORKERS_EDEFAULT;
			case ArchivePackage.INPUT_DESTINATION__QUEUE_SIZE:
				return queueSize != QUEUE_SIZE_EDEFAULT;
			case ArchivePackage.INPUT_DESTINATION__THREAD_AFFINITY_RULE_FUNCTION:
				return THREAD_AFFINITY_RULE_FUNCTION_EDEFAULT == null ? threadAffinityRuleFunction != null : !THREAD_AFFINITY_RULE_FUNCTION_EDEFAULT.equals(threadAffinityRuleFunction);
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
		result.append(" (destinationURI: ");
		result.append(destinationURI);
		result.append(", preprocessor: ");
		result.append(preprocessor);
		result.append(", default: ");
		result.append(default_);
		result.append(", enable: ");
		result.append(enable);
		result.append(", workers: ");
		result.append(workers);
		result.append(", queueSize: ");
		result.append(queueSize);
		result.append(", threadAffinityRuleFunction: ");
		result.append(threadAffinityRuleFunction);
		result.append(')');
		return result.toString();
	}
	
	public Object clone() throws CloneNotSupportedException {
		InputDestination clone = ArchiveFactory.eINSTANCE.createInputDestination();
		clone.setDestinationURI(this.getDestinationURI());
		clone.setEnable(this.isEnable());
		clone.setDefault(this.isDefault());
		clone.setPreprocessor(this.getPreprocessor());
		clone.setQueueSize(this.getQueueSize());
		clone.setWorkers(this.getWorkers());
		return clone;
	}

} //InputDestinationImpl
