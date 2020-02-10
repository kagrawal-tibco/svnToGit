/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.DestinationConfig;
import com.tibco.be.util.config.cdd.OverrideConfig;
import com.tibco.be.util.config.cdd.ThreadingModelConfig;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Destination Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.DestinationConfigImpl#getUri <em>Uri</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.DestinationConfigImpl#getPreProcessor <em>Pre Processor</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.DestinationConfigImpl#getThreadingModel <em>Threading Model</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.DestinationConfigImpl#getThreadCount <em>Thread Count</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.DestinationConfigImpl#getQueueSize <em>Queue Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.DestinationConfigImpl#getThreadAffinityRuleFunction <em>Thread Affinity Rule Function</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DestinationConfigImpl extends ArtifactConfigImpl implements DestinationConfig {
	/**
	 * The default value of the '{@link #getUri() <em>Uri</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUri()
	 * @generated
	 * @ordered
	 */
	protected static final String URI_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUri() <em>Uri</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUri()
	 * @generated
	 * @ordered
	 */
	protected String uri = URI_EDEFAULT;

	/**
	 * The default value of the '{@link #getPreProcessor() <em>Pre Processor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPreProcessor()
	 * @generated
	 * @ordered
	 */
	protected static final String PRE_PROCESSOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPreProcessor() <em>Pre Processor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPreProcessor()
	 * @generated
	 * @ordered
	 */
	protected String preProcessor = PRE_PROCESSOR_EDEFAULT;

	/**
	 * The default value of the '{@link #getThreadingModel() <em>Threading Model</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getThreadingModel()
	 * @generated
	 * @ordered
	 */
	protected static final ThreadingModelConfig THREADING_MODEL_EDEFAULT = ThreadingModelConfig.SHARED_QUEUE;

	/**
	 * The cached value of the '{@link #getThreadingModel() <em>Threading Model</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getThreadingModel()
	 * @generated
	 * @ordered
	 */
	protected ThreadingModelConfig threadingModel = THREADING_MODEL_EDEFAULT;

	/**
	 * This is true if the Threading Model attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean threadingModelESet;

	/**
	 * The cached value of the '{@link #getThreadCount() <em>Thread Count</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getThreadCount()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig threadCount;

	/**
	 * The cached value of the '{@link #getQueueSize() <em>Queue Size</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQueueSize()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig queueSize;

	/**
	 * The default value of the '{@link #getThreadAffinityRuleFunction() <em>Thread Affinity Rule Function</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getThreadAffinityRuleFunction()
	 * @generated
	 * @ordered
	 */
	protected static final String THREAD_AFFINITY_RULE_FUNCTION_EDEFAULT = "";

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
	protected DestinationConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getDestinationConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getUri() {
		return uri;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUri(String newUri) {
		String oldUri = uri;
		uri = newUri;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.DESTINATION_CONFIG__URI, oldUri, uri));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPreProcessor() {
		return preProcessor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPreProcessor(String newPreProcessor) {
		String oldPreProcessor = preProcessor;
		preProcessor = newPreProcessor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.DESTINATION_CONFIG__PRE_PROCESSOR, oldPreProcessor, preProcessor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ThreadingModelConfig getThreadingModel() {
		return threadingModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setThreadingModel(ThreadingModelConfig newThreadingModel) {
		ThreadingModelConfig oldThreadingModel = threadingModel;
		threadingModel = newThreadingModel == null ? THREADING_MODEL_EDEFAULT : newThreadingModel;
		boolean oldThreadingModelESet = threadingModelESet;
		threadingModelESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.DESTINATION_CONFIG__THREADING_MODEL, oldThreadingModel, threadingModel, !oldThreadingModelESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetThreadingModel() {
		ThreadingModelConfig oldThreadingModel = threadingModel;
		boolean oldThreadingModelESet = threadingModelESet;
		threadingModel = THREADING_MODEL_EDEFAULT;
		threadingModelESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, CddPackage.DESTINATION_CONFIG__THREADING_MODEL, oldThreadingModel, THREADING_MODEL_EDEFAULT, oldThreadingModelESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetThreadingModel() {
		return threadingModelESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getThreadCount() {
		return threadCount;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetThreadCount(OverrideConfig newThreadCount, NotificationChain msgs) {
		OverrideConfig oldThreadCount = threadCount;
		threadCount = newThreadCount;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.DESTINATION_CONFIG__THREAD_COUNT, oldThreadCount, newThreadCount);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setThreadCount(OverrideConfig newThreadCount) {
		if (newThreadCount != threadCount) {
			NotificationChain msgs = null;
			if (threadCount != null)
				msgs = ((InternalEObject)threadCount).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.DESTINATION_CONFIG__THREAD_COUNT, null, msgs);
			if (newThreadCount != null)
				msgs = ((InternalEObject)newThreadCount).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.DESTINATION_CONFIG__THREAD_COUNT, null, msgs);
			msgs = basicSetThreadCount(newThreadCount, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.DESTINATION_CONFIG__THREAD_COUNT, newThreadCount, newThreadCount));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getQueueSize() {
		return queueSize;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetQueueSize(OverrideConfig newQueueSize, NotificationChain msgs) {
		OverrideConfig oldQueueSize = queueSize;
		queueSize = newQueueSize;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.DESTINATION_CONFIG__QUEUE_SIZE, oldQueueSize, newQueueSize);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setQueueSize(OverrideConfig newQueueSize) {
		if (newQueueSize != queueSize) {
			NotificationChain msgs = null;
			if (queueSize != null)
				msgs = ((InternalEObject)queueSize).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.DESTINATION_CONFIG__QUEUE_SIZE, null, msgs);
			if (newQueueSize != null)
				msgs = ((InternalEObject)newQueueSize).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.DESTINATION_CONFIG__QUEUE_SIZE, null, msgs);
			msgs = basicSetQueueSize(newQueueSize, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.DESTINATION_CONFIG__QUEUE_SIZE, newQueueSize, newQueueSize));
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
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.DESTINATION_CONFIG__THREAD_AFFINITY_RULE_FUNCTION, oldThreadAffinityRuleFunction, threadAffinityRuleFunction));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.DESTINATION_CONFIG__THREAD_COUNT:
				return basicSetThreadCount(null, msgs);
			case CddPackage.DESTINATION_CONFIG__QUEUE_SIZE:
				return basicSetQueueSize(null, msgs);
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
			case CddPackage.DESTINATION_CONFIG__URI:
				return getUri();
			case CddPackage.DESTINATION_CONFIG__PRE_PROCESSOR:
				return getPreProcessor();
			case CddPackage.DESTINATION_CONFIG__THREADING_MODEL:
				return getThreadingModel();
			case CddPackage.DESTINATION_CONFIG__THREAD_COUNT:
				return getThreadCount();
			case CddPackage.DESTINATION_CONFIG__QUEUE_SIZE:
				return getQueueSize();
			case CddPackage.DESTINATION_CONFIG__THREAD_AFFINITY_RULE_FUNCTION:
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
			case CddPackage.DESTINATION_CONFIG__URI:
				setUri((String)newValue);
				return;
			case CddPackage.DESTINATION_CONFIG__PRE_PROCESSOR:
				setPreProcessor((String)newValue);
				return;
			case CddPackage.DESTINATION_CONFIG__THREADING_MODEL:
				setThreadingModel((ThreadingModelConfig)newValue);
				return;
			case CddPackage.DESTINATION_CONFIG__THREAD_COUNT:
				setThreadCount((OverrideConfig)newValue);
				return;
			case CddPackage.DESTINATION_CONFIG__QUEUE_SIZE:
				setQueueSize((OverrideConfig)newValue);
				return;
			case CddPackage.DESTINATION_CONFIG__THREAD_AFFINITY_RULE_FUNCTION:
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
			case CddPackage.DESTINATION_CONFIG__URI:
				setUri(URI_EDEFAULT);
				return;
			case CddPackage.DESTINATION_CONFIG__PRE_PROCESSOR:
				setPreProcessor(PRE_PROCESSOR_EDEFAULT);
				return;
			case CddPackage.DESTINATION_CONFIG__THREADING_MODEL:
				unsetThreadingModel();
				return;
			case CddPackage.DESTINATION_CONFIG__THREAD_COUNT:
				setThreadCount((OverrideConfig)null);
				return;
			case CddPackage.DESTINATION_CONFIG__QUEUE_SIZE:
				setQueueSize((OverrideConfig)null);
				return;
			case CddPackage.DESTINATION_CONFIG__THREAD_AFFINITY_RULE_FUNCTION:
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
			case CddPackage.DESTINATION_CONFIG__URI:
				return URI_EDEFAULT == null ? uri != null : !URI_EDEFAULT.equals(uri);
			case CddPackage.DESTINATION_CONFIG__PRE_PROCESSOR:
				return PRE_PROCESSOR_EDEFAULT == null ? preProcessor != null : !PRE_PROCESSOR_EDEFAULT.equals(preProcessor);
			case CddPackage.DESTINATION_CONFIG__THREADING_MODEL:
				return isSetThreadingModel();
			case CddPackage.DESTINATION_CONFIG__THREAD_COUNT:
				return threadCount != null;
			case CddPackage.DESTINATION_CONFIG__QUEUE_SIZE:
				return queueSize != null;
			case CddPackage.DESTINATION_CONFIG__THREAD_AFFINITY_RULE_FUNCTION:
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
		result.append(" (uri: ");
		result.append(uri);
		result.append(", preProcessor: ");
		result.append(preProcessor);
		result.append(", threadingModel: ");
		if (threadingModelESet) result.append(threadingModel); else result.append("<unset>");
		result.append(", threadAffinityRuleFunction: ");
		result.append(threadAffinityRuleFunction);
		result.append(')');
		return result.toString();
	}

} //DestinationConfigImpl
