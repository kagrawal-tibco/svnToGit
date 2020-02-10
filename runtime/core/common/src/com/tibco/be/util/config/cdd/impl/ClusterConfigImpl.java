/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd.impl;

import java.util.Map;
import java.util.Properties;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.be.util.config.cdd.AgentClassesConfig;
import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.ClusterConfig;
import com.tibco.be.util.config.cdd.DestinationGroupsConfig;
import com.tibco.be.util.config.cdd.FunctionGroupsConfig;
import com.tibco.be.util.config.cdd.LoadBalancerConfigsConfig;
import com.tibco.be.util.config.cdd.LogConfigsConfig;
import com.tibco.be.util.config.cdd.ObjectManagementConfig;
import com.tibco.be.util.config.cdd.OverrideConfig;
import com.tibco.be.util.config.cdd.ProcessGroupsConfig;
import com.tibco.be.util.config.cdd.ProcessingUnitsConfig;
import com.tibco.be.util.config.cdd.PropertyGroupConfig;
import com.tibco.be.util.config.cdd.RevisionConfig;
import com.tibco.be.util.config.cdd.RulesetsConfig;
import com.tibco.cep.runtime.util.SystemProperty;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Cluster Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.ClusterConfigImpl#getAgentClasses <em>Agent Classes</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.ClusterConfigImpl#getDestinationGroups <em>Destination Groups</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.ClusterConfigImpl#getFunctionGroups <em>Function Groups</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.ClusterConfigImpl#getLoadBalancerConfigs <em>Load Balancer Configs</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.ClusterConfigImpl#getLogConfigs <em>Log Configs</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.ClusterConfigImpl#getMessageEncoding <em>Message Encoding</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.ClusterConfigImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.ClusterConfigImpl#getObjectManagement <em>Object Management</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.ClusterConfigImpl#getProcessGroups <em>Process Groups</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.ClusterConfigImpl#getProcessingUnits <em>Processing Units</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.ClusterConfigImpl#getPropertyGroup <em>Property Group</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.ClusterConfigImpl#getRevision <em>Revision</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.ClusterConfigImpl#getRulesets <em>Rulesets</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ClusterConfigImpl extends ArtifactConfigImpl implements ClusterConfig {
	
	/**
	 * The cached value of the '{@link #getAgentClasses() <em>Agent Classes</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAgentClasses()
	 * @generated
	 * @ordered
	 */
	protected AgentClassesConfig agentClasses;

	/**
	 * The cached value of the '{@link #getDestinationGroups() <em>Destination Groups</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDestinationGroups()
	 * @generated
	 * @ordered
	 */
	protected DestinationGroupsConfig destinationGroups;

	/**
	 * The cached value of the '{@link #getFunctionGroups() <em>Function Groups</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFunctionGroups()
	 * @generated
	 * @ordered
	 */
	protected FunctionGroupsConfig functionGroups;

	/**
	 * The cached value of the '{@link #getLoadBalancerConfigs() <em>Load Balancer Configs</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLoadBalancerConfigs()
	 * @generated
	 * @ordered
	 */
	protected LoadBalancerConfigsConfig loadBalancerConfigs;

	/**
	 * The cached value of the '{@link #getLogConfigs() <em>Log Configs</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLogConfigs()
	 * @generated
	 * @ordered
	 */
	protected LogConfigsConfig logConfigs;

	/**
	 * The default value of the '{@link #getMessageEncoding() <em>Message Encoding</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMessageEncoding()
	 * @generated
	 * @ordered
	 */
	protected static final String MESSAGE_ENCODING_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMessageEncoding() <em>Message Encoding</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMessageEncoding()
	 * @generated
	 * @ordered
	 */
	protected String messageEncoding = MESSAGE_ENCODING_EDEFAULT;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig name;

	/**
	 * The cached value of the '{@link #getObjectManagement() <em>Object Management</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObjectManagement()
	 * @generated
	 * @ordered
	 */
	protected ObjectManagementConfig objectManagement;

	/**
	 * The cached value of the '{@link #getProcessGroups() <em>Process Groups</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProcessGroups()
	 * @generated
	 * @ordered
	 */
	protected ProcessGroupsConfig processGroups;

	/**
	 * The cached value of the '{@link #getProcessingUnits() <em>Processing Units</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProcessingUnits()
	 * @generated
	 * @ordered
	 */
	protected ProcessingUnitsConfig processingUnits;

	/**
	 * The cached value of the '{@link #getPropertyGroup() <em>Property Group</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPropertyGroup()
	 * @generated
	 * @ordered
	 */
	protected PropertyGroupConfig propertyGroup;

	/**
	 * The cached value of the '{@link #getRevision() <em>Revision</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRevision()
	 * @generated
	 * @ordered
	 */
	protected RevisionConfig revision;

	/**
	 * The cached value of the '{@link #getRulesets() <em>Rulesets</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRulesets()
	 * @generated
	 * @ordered
	 */
	protected RulesetsConfig rulesets;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ClusterConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getClusterConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AgentClassesConfig getAgentClasses() {
		return agentClasses;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAgentClasses(AgentClassesConfig newAgentClasses, NotificationChain msgs) {
		AgentClassesConfig oldAgentClasses = agentClasses;
		agentClasses = newAgentClasses;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.CLUSTER_CONFIG__AGENT_CLASSES, oldAgentClasses, newAgentClasses);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAgentClasses(AgentClassesConfig newAgentClasses) {
		if (newAgentClasses != agentClasses) {
			NotificationChain msgs = null;
			if (agentClasses != null)
				msgs = ((InternalEObject)agentClasses).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.CLUSTER_CONFIG__AGENT_CLASSES, null, msgs);
			if (newAgentClasses != null)
				msgs = ((InternalEObject)newAgentClasses).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.CLUSTER_CONFIG__AGENT_CLASSES, null, msgs);
			msgs = basicSetAgentClasses(newAgentClasses, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.CLUSTER_CONFIG__AGENT_CLASSES, newAgentClasses, newAgentClasses));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DestinationGroupsConfig getDestinationGroups() {
		return destinationGroups;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDestinationGroups(DestinationGroupsConfig newDestinationGroups, NotificationChain msgs) {
		DestinationGroupsConfig oldDestinationGroups = destinationGroups;
		destinationGroups = newDestinationGroups;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.CLUSTER_CONFIG__DESTINATION_GROUPS, oldDestinationGroups, newDestinationGroups);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDestinationGroups(DestinationGroupsConfig newDestinationGroups) {
		if (newDestinationGroups != destinationGroups) {
			NotificationChain msgs = null;
			if (destinationGroups != null)
				msgs = ((InternalEObject)destinationGroups).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.CLUSTER_CONFIG__DESTINATION_GROUPS, null, msgs);
			if (newDestinationGroups != null)
				msgs = ((InternalEObject)newDestinationGroups).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.CLUSTER_CONFIG__DESTINATION_GROUPS, null, msgs);
			msgs = basicSetDestinationGroups(newDestinationGroups, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.CLUSTER_CONFIG__DESTINATION_GROUPS, newDestinationGroups, newDestinationGroups));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FunctionGroupsConfig getFunctionGroups() {
		return functionGroups;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFunctionGroups(FunctionGroupsConfig newFunctionGroups, NotificationChain msgs) {
		FunctionGroupsConfig oldFunctionGroups = functionGroups;
		functionGroups = newFunctionGroups;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.CLUSTER_CONFIG__FUNCTION_GROUPS, oldFunctionGroups, newFunctionGroups);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFunctionGroups(FunctionGroupsConfig newFunctionGroups) {
		if (newFunctionGroups != functionGroups) {
			NotificationChain msgs = null;
			if (functionGroups != null)
				msgs = ((InternalEObject)functionGroups).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.CLUSTER_CONFIG__FUNCTION_GROUPS, null, msgs);
			if (newFunctionGroups != null)
				msgs = ((InternalEObject)newFunctionGroups).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.CLUSTER_CONFIG__FUNCTION_GROUPS, null, msgs);
			msgs = basicSetFunctionGroups(newFunctionGroups, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.CLUSTER_CONFIG__FUNCTION_GROUPS, newFunctionGroups, newFunctionGroups));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LogConfigsConfig getLogConfigs() {
		return logConfigs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLogConfigs(LogConfigsConfig newLogConfigs, NotificationChain msgs) {
		LogConfigsConfig oldLogConfigs = logConfigs;
		logConfigs = newLogConfigs;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.CLUSTER_CONFIG__LOG_CONFIGS, oldLogConfigs, newLogConfigs);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLogConfigs(LogConfigsConfig newLogConfigs) {
		if (newLogConfigs != logConfigs) {
			NotificationChain msgs = null;
			if (logConfigs != null)
				msgs = ((InternalEObject)logConfigs).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.CLUSTER_CONFIG__LOG_CONFIGS, null, msgs);
			if (newLogConfigs != null)
				msgs = ((InternalEObject)newLogConfigs).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.CLUSTER_CONFIG__LOG_CONFIGS, null, msgs);
			msgs = basicSetLogConfigs(newLogConfigs, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.CLUSTER_CONFIG__LOG_CONFIGS, newLogConfigs, newLogConfigs));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getMessageEncoding() {
		return messageEncoding;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMessageEncoding(String newMessageEncoding) {
		String oldMessageEncoding = messageEncoding;
		messageEncoding = newMessageEncoding;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.CLUSTER_CONFIG__MESSAGE_ENCODING, oldMessageEncoding, messageEncoding));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetName(OverrideConfig newName, NotificationChain msgs) {
		OverrideConfig oldName = name;
		name = newName;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.CLUSTER_CONFIG__NAME, oldName, newName);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(OverrideConfig newName) {
		if (newName != name) {
			NotificationChain msgs = null;
			if (name != null)
				msgs = ((InternalEObject)name).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.CLUSTER_CONFIG__NAME, null, msgs);
			if (newName != null)
				msgs = ((InternalEObject)newName).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.CLUSTER_CONFIG__NAME, null, msgs);
			msgs = basicSetName(newName, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.CLUSTER_CONFIG__NAME, newName, newName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ObjectManagementConfig getObjectManagement() {
		return objectManagement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetObjectManagement(ObjectManagementConfig newObjectManagement, NotificationChain msgs) {
		ObjectManagementConfig oldObjectManagement = objectManagement;
		objectManagement = newObjectManagement;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.CLUSTER_CONFIG__OBJECT_MANAGEMENT, oldObjectManagement, newObjectManagement);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setObjectManagement(ObjectManagementConfig newObjectManagement) {
		if (newObjectManagement != objectManagement) {
			NotificationChain msgs = null;
			if (objectManagement != null)
				msgs = ((InternalEObject)objectManagement).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.CLUSTER_CONFIG__OBJECT_MANAGEMENT, null, msgs);
			if (newObjectManagement != null)
				msgs = ((InternalEObject)newObjectManagement).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.CLUSTER_CONFIG__OBJECT_MANAGEMENT, null, msgs);
			msgs = basicSetObjectManagement(newObjectManagement, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.CLUSTER_CONFIG__OBJECT_MANAGEMENT, newObjectManagement, newObjectManagement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProcessGroupsConfig getProcessGroups() {
		return processGroups;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProcessGroups(ProcessGroupsConfig newProcessGroups, NotificationChain msgs) {
		ProcessGroupsConfig oldProcessGroups = processGroups;
		processGroups = newProcessGroups;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.CLUSTER_CONFIG__PROCESS_GROUPS, oldProcessGroups, newProcessGroups);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProcessGroups(ProcessGroupsConfig newProcessGroups) {
		if (newProcessGroups != processGroups) {
			NotificationChain msgs = null;
			if (processGroups != null)
				msgs = ((InternalEObject)processGroups).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.CLUSTER_CONFIG__PROCESS_GROUPS, null, msgs);
			if (newProcessGroups != null)
				msgs = ((InternalEObject)newProcessGroups).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.CLUSTER_CONFIG__PROCESS_GROUPS, null, msgs);
			msgs = basicSetProcessGroups(newProcessGroups, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.CLUSTER_CONFIG__PROCESS_GROUPS, newProcessGroups, newProcessGroups));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProcessingUnitsConfig getProcessingUnits() {
		return processingUnits;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProcessingUnits(ProcessingUnitsConfig newProcessingUnits, NotificationChain msgs) {
		ProcessingUnitsConfig oldProcessingUnits = processingUnits;
		processingUnits = newProcessingUnits;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.CLUSTER_CONFIG__PROCESSING_UNITS, oldProcessingUnits, newProcessingUnits);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProcessingUnits(ProcessingUnitsConfig newProcessingUnits) {
		if (newProcessingUnits != processingUnits) {
			NotificationChain msgs = null;
			if (processingUnits != null)
				msgs = ((InternalEObject)processingUnits).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.CLUSTER_CONFIG__PROCESSING_UNITS, null, msgs);
			if (newProcessingUnits != null)
				msgs = ((InternalEObject)newProcessingUnits).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.CLUSTER_CONFIG__PROCESSING_UNITS, null, msgs);
			msgs = basicSetProcessingUnits(newProcessingUnits, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.CLUSTER_CONFIG__PROCESSING_UNITS, newProcessingUnits, newProcessingUnits));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LoadBalancerConfigsConfig getLoadBalancerConfigs() {
		return loadBalancerConfigs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLoadBalancerConfigs(LoadBalancerConfigsConfig newLoadBalancerConfigs, NotificationChain msgs) {
		LoadBalancerConfigsConfig oldLoadBalancerConfigs = loadBalancerConfigs;
		loadBalancerConfigs = newLoadBalancerConfigs;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.CLUSTER_CONFIG__LOAD_BALANCER_CONFIGS, oldLoadBalancerConfigs, newLoadBalancerConfigs);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLoadBalancerConfigs(LoadBalancerConfigsConfig newLoadBalancerConfigs) {
		if (newLoadBalancerConfigs != loadBalancerConfigs) {
			NotificationChain msgs = null;
			if (loadBalancerConfigs != null)
				msgs = ((InternalEObject)loadBalancerConfigs).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.CLUSTER_CONFIG__LOAD_BALANCER_CONFIGS, null, msgs);
			if (newLoadBalancerConfigs != null)
				msgs = ((InternalEObject)newLoadBalancerConfigs).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.CLUSTER_CONFIG__LOAD_BALANCER_CONFIGS, null, msgs);
			msgs = basicSetLoadBalancerConfigs(newLoadBalancerConfigs, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.CLUSTER_CONFIG__LOAD_BALANCER_CONFIGS, newLoadBalancerConfigs, newLoadBalancerConfigs));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyGroupConfig getPropertyGroup() {
		return propertyGroup;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPropertyGroup(PropertyGroupConfig newPropertyGroup, NotificationChain msgs) {
		PropertyGroupConfig oldPropertyGroup = propertyGroup;
		propertyGroup = newPropertyGroup;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.CLUSTER_CONFIG__PROPERTY_GROUP, oldPropertyGroup, newPropertyGroup);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPropertyGroup(PropertyGroupConfig newPropertyGroup) {
		if (newPropertyGroup != propertyGroup) {
			NotificationChain msgs = null;
			if (propertyGroup != null)
				msgs = ((InternalEObject)propertyGroup).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.CLUSTER_CONFIG__PROPERTY_GROUP, null, msgs);
			if (newPropertyGroup != null)
				msgs = ((InternalEObject)newPropertyGroup).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.CLUSTER_CONFIG__PROPERTY_GROUP, null, msgs);
			msgs = basicSetPropertyGroup(newPropertyGroup, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.CLUSTER_CONFIG__PROPERTY_GROUP, newPropertyGroup, newPropertyGroup));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RevisionConfig getRevision() {
		return revision;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRevision(RevisionConfig newRevision, NotificationChain msgs) {
		RevisionConfig oldRevision = revision;
		revision = newRevision;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.CLUSTER_CONFIG__REVISION, oldRevision, newRevision);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRevision(RevisionConfig newRevision) {
		if (newRevision != revision) {
			NotificationChain msgs = null;
			if (revision != null)
				msgs = ((InternalEObject)revision).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.CLUSTER_CONFIG__REVISION, null, msgs);
			if (newRevision != null)
				msgs = ((InternalEObject)newRevision).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.CLUSTER_CONFIG__REVISION, null, msgs);
			msgs = basicSetRevision(newRevision, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.CLUSTER_CONFIG__REVISION, newRevision, newRevision));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RulesetsConfig getRulesets() {
		return rulesets;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRulesets(RulesetsConfig newRulesets, NotificationChain msgs) {
		RulesetsConfig oldRulesets = rulesets;
		rulesets = newRulesets;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.CLUSTER_CONFIG__RULESETS, oldRulesets, newRulesets);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRulesets(RulesetsConfig newRulesets) {
		if (newRulesets != rulesets) {
			NotificationChain msgs = null;
			if (rulesets != null)
				msgs = ((InternalEObject)rulesets).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.CLUSTER_CONFIG__RULESETS, null, msgs);
			if (newRulesets != null)
				msgs = ((InternalEObject)newRulesets).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.CLUSTER_CONFIG__RULESETS, null, msgs);
			msgs = basicSetRulesets(newRulesets, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.CLUSTER_CONFIG__RULESETS, newRulesets, newRulesets));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.CLUSTER_CONFIG__AGENT_CLASSES:
				return basicSetAgentClasses(null, msgs);
			case CddPackage.CLUSTER_CONFIG__DESTINATION_GROUPS:
				return basicSetDestinationGroups(null, msgs);
			case CddPackage.CLUSTER_CONFIG__FUNCTION_GROUPS:
				return basicSetFunctionGroups(null, msgs);
			case CddPackage.CLUSTER_CONFIG__LOAD_BALANCER_CONFIGS:
				return basicSetLoadBalancerConfigs(null, msgs);
			case CddPackage.CLUSTER_CONFIG__LOG_CONFIGS:
				return basicSetLogConfigs(null, msgs);
			case CddPackage.CLUSTER_CONFIG__NAME:
				return basicSetName(null, msgs);
			case CddPackage.CLUSTER_CONFIG__OBJECT_MANAGEMENT:
				return basicSetObjectManagement(null, msgs);
			case CddPackage.CLUSTER_CONFIG__PROCESS_GROUPS:
				return basicSetProcessGroups(null, msgs);
			case CddPackage.CLUSTER_CONFIG__PROCESSING_UNITS:
				return basicSetProcessingUnits(null, msgs);
			case CddPackage.CLUSTER_CONFIG__PROPERTY_GROUP:
				return basicSetPropertyGroup(null, msgs);
			case CddPackage.CLUSTER_CONFIG__REVISION:
				return basicSetRevision(null, msgs);
			case CddPackage.CLUSTER_CONFIG__RULESETS:
				return basicSetRulesets(null, msgs);
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
			case CddPackage.CLUSTER_CONFIG__AGENT_CLASSES:
				return getAgentClasses();
			case CddPackage.CLUSTER_CONFIG__DESTINATION_GROUPS:
				return getDestinationGroups();
			case CddPackage.CLUSTER_CONFIG__FUNCTION_GROUPS:
				return getFunctionGroups();
			case CddPackage.CLUSTER_CONFIG__LOAD_BALANCER_CONFIGS:
				return getLoadBalancerConfigs();
			case CddPackage.CLUSTER_CONFIG__LOG_CONFIGS:
				return getLogConfigs();
			case CddPackage.CLUSTER_CONFIG__MESSAGE_ENCODING:
				return getMessageEncoding();
			case CddPackage.CLUSTER_CONFIG__NAME:
				return getName();
			case CddPackage.CLUSTER_CONFIG__OBJECT_MANAGEMENT:
				return getObjectManagement();
			case CddPackage.CLUSTER_CONFIG__PROCESS_GROUPS:
				return getProcessGroups();
			case CddPackage.CLUSTER_CONFIG__PROCESSING_UNITS:
				return getProcessingUnits();
			case CddPackage.CLUSTER_CONFIG__PROPERTY_GROUP:
				return getPropertyGroup();
			case CddPackage.CLUSTER_CONFIG__REVISION:
				return getRevision();
			case CddPackage.CLUSTER_CONFIG__RULESETS:
				return getRulesets();
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
			case CddPackage.CLUSTER_CONFIG__AGENT_CLASSES:
				setAgentClasses((AgentClassesConfig)newValue);
				return;
			case CddPackage.CLUSTER_CONFIG__DESTINATION_GROUPS:
				setDestinationGroups((DestinationGroupsConfig)newValue);
				return;
			case CddPackage.CLUSTER_CONFIG__FUNCTION_GROUPS:
				setFunctionGroups((FunctionGroupsConfig)newValue);
				return;
			case CddPackage.CLUSTER_CONFIG__LOAD_BALANCER_CONFIGS:
				setLoadBalancerConfigs((LoadBalancerConfigsConfig)newValue);
				return;
			case CddPackage.CLUSTER_CONFIG__LOG_CONFIGS:
				setLogConfigs((LogConfigsConfig)newValue);
				return;
			case CddPackage.CLUSTER_CONFIG__MESSAGE_ENCODING:
				setMessageEncoding((String)newValue);
				return;
			case CddPackage.CLUSTER_CONFIG__NAME:
				setName((OverrideConfig)newValue);
				return;
			case CddPackage.CLUSTER_CONFIG__OBJECT_MANAGEMENT:
				setObjectManagement((ObjectManagementConfig)newValue);
				return;
			case CddPackage.CLUSTER_CONFIG__PROCESS_GROUPS:
				setProcessGroups((ProcessGroupsConfig)newValue);
				return;
			case CddPackage.CLUSTER_CONFIG__PROCESSING_UNITS:
				setProcessingUnits((ProcessingUnitsConfig)newValue);
				return;
			case CddPackage.CLUSTER_CONFIG__PROPERTY_GROUP:
				setPropertyGroup((PropertyGroupConfig)newValue);
				return;
			case CddPackage.CLUSTER_CONFIG__REVISION:
				setRevision((RevisionConfig)newValue);
				return;
			case CddPackage.CLUSTER_CONFIG__RULESETS:
				setRulesets((RulesetsConfig)newValue);
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
			case CddPackage.CLUSTER_CONFIG__AGENT_CLASSES:
				setAgentClasses((AgentClassesConfig)null);
				return;
			case CddPackage.CLUSTER_CONFIG__DESTINATION_GROUPS:
				setDestinationGroups((DestinationGroupsConfig)null);
				return;
			case CddPackage.CLUSTER_CONFIG__FUNCTION_GROUPS:
				setFunctionGroups((FunctionGroupsConfig)null);
				return;
			case CddPackage.CLUSTER_CONFIG__LOAD_BALANCER_CONFIGS:
				setLoadBalancerConfigs((LoadBalancerConfigsConfig)null);
				return;
			case CddPackage.CLUSTER_CONFIG__LOG_CONFIGS:
				setLogConfigs((LogConfigsConfig)null);
				return;
			case CddPackage.CLUSTER_CONFIG__MESSAGE_ENCODING:
				setMessageEncoding(MESSAGE_ENCODING_EDEFAULT);
				return;
			case CddPackage.CLUSTER_CONFIG__NAME:
				setName((OverrideConfig)null);
				return;
			case CddPackage.CLUSTER_CONFIG__OBJECT_MANAGEMENT:
				setObjectManagement((ObjectManagementConfig)null);
				return;
			case CddPackage.CLUSTER_CONFIG__PROCESS_GROUPS:
				setProcessGroups((ProcessGroupsConfig)null);
				return;
			case CddPackage.CLUSTER_CONFIG__PROCESSING_UNITS:
				setProcessingUnits((ProcessingUnitsConfig)null);
				return;
			case CddPackage.CLUSTER_CONFIG__PROPERTY_GROUP:
				setPropertyGroup((PropertyGroupConfig)null);
				return;
			case CddPackage.CLUSTER_CONFIG__REVISION:
				setRevision((RevisionConfig)null);
				return;
			case CddPackage.CLUSTER_CONFIG__RULESETS:
				setRulesets((RulesetsConfig)null);
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
			case CddPackage.CLUSTER_CONFIG__AGENT_CLASSES:
				return agentClasses != null;
			case CddPackage.CLUSTER_CONFIG__DESTINATION_GROUPS:
				return destinationGroups != null;
			case CddPackage.CLUSTER_CONFIG__FUNCTION_GROUPS:
				return functionGroups != null;
			case CddPackage.CLUSTER_CONFIG__LOAD_BALANCER_CONFIGS:
				return loadBalancerConfigs != null;
			case CddPackage.CLUSTER_CONFIG__LOG_CONFIGS:
				return logConfigs != null;
			case CddPackage.CLUSTER_CONFIG__MESSAGE_ENCODING:
				return MESSAGE_ENCODING_EDEFAULT == null ? messageEncoding != null : !MESSAGE_ENCODING_EDEFAULT.equals(messageEncoding);
			case CddPackage.CLUSTER_CONFIG__NAME:
				return name != null;
			case CddPackage.CLUSTER_CONFIG__OBJECT_MANAGEMENT:
				return objectManagement != null;
			case CddPackage.CLUSTER_CONFIG__PROCESS_GROUPS:
				return processGroups != null;
			case CddPackage.CLUSTER_CONFIG__PROCESSING_UNITS:
				return processingUnits != null;
			case CddPackage.CLUSTER_CONFIG__PROPERTY_GROUP:
				return propertyGroup != null;
			case CddPackage.CLUSTER_CONFIG__REVISION:
				return revision != null;
			case CddPackage.CLUSTER_CONFIG__RULESETS:
				return rulesets != null;
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
		result.append(" (messageEncoding: ");
		result.append(messageEncoding);
		result.append(')');
		return result.toString();
	}

	/**
	 * @generated NOT
	 */
	@Override
	public Map<Object, Object> toProperties() {
		final Properties properties = new Properties();
		
		// OM
        if (this.objectManagement != null){
        	properties.putAll(this.objectManagement.toProperties());
        }

        // Message encoding
        if (this.messageEncoding != null){
        	properties.put(SystemProperty.MESSAGE_ENCODING.getPropertyName(), this.messageEncoding);
        }

        // Properties
        if (null != this.propertyGroup) {
            properties.putAll(this.propertyGroup.toProperties());
        }

    	return properties;		
	}
	
	
} //ClusterConfigImpl
