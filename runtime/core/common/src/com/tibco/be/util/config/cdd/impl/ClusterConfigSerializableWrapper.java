package com.tibco.be.util.config.cdd.impl;

import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;

import com.tibco.be.util.config.CddTools;
import com.tibco.be.util.config.cdd.AgentClassesConfig;
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

/**
 * @generated NOT
 */
public class ClusterConfigSerializableWrapper implements ClusterConfig,
		Serializable {

	private static final long serialVersionUID = 51000L;

	private ClusterConfig delegate;

	public ClusterConfigSerializableWrapper(ClusterConfig clusterConfig) {
		if (clusterConfig instanceof ClusterConfigSerializableWrapper) {
			this.delegate = ((ClusterConfigSerializableWrapper) clusterConfig).delegate;
		} else {
			this.delegate = clusterConfig;
		}		
	}

	@Override
	public String getId() {
		return this.delegate.getId();
	}

	@Override
	public void setId(String value) {
		this.delegate.setId(value);
	}

	@Override
	public Map<Object, Object> toProperties() {
		return this.delegate.toProperties();
	}

	@Override
	public EClass eClass() {
		return this.delegate.eClass();
	}

	@Override
	public Resource eResource() {
		return this.delegate.eResource();
	}

	@Override
	public EObject eContainer() {
		return this.delegate.eContainer();
	}

	@Override
	public EStructuralFeature eContainingFeature() {
		return this.delegate.eContainingFeature();
	}

	@Override
	public EReference eContainmentFeature() {
		return this.delegate.eContainmentFeature();
	}

	@Override
	public EList<EObject> eContents() {
		return this.delegate.eContents();
	}

	@Override
	public TreeIterator<EObject> eAllContents() {
		return this.delegate.eAllContents();
	}

	@Override
	public boolean eIsProxy() {
		return this.delegate.eIsProxy();
	}

	@Override
	public EList<EObject> eCrossReferences() {
		return this.delegate.eCrossReferences();
	}

	@Override
	public Object eGet(EStructuralFeature feature) {
		return this.delegate.eGet(feature);
	}

	@Override
	public Object eGet(EStructuralFeature feature, boolean resolve) {
		return this.delegate.eGet(feature, resolve);
	}

	@Override
	public void eSet(EStructuralFeature feature, Object newValue) {
		this.delegate.eSet(feature, newValue);
	}

	@Override
	public boolean eIsSet(EStructuralFeature feature) {
		return this.delegate.eIsSet(feature);
	}

	@Override
	public void eUnset(EStructuralFeature feature) {
		this.delegate.eUnset(feature);
	}

	@Override
	public Object eInvoke(EOperation operation, EList<?> arguments)
			throws InvocationTargetException {
		return this.delegate.eInvoke(operation, arguments);
	}

	@Override
	public EList<Adapter> eAdapters() {
		return this.delegate.eAdapters();
	}

	@Override
	public boolean eDeliver() {
		return this.delegate.eDeliver();
	}

	@Override
	public void eNotify(Notification arg0) {
		this.delegate.eNotify(arg0);
	}

	@Override
	public void eSetDeliver(boolean arg0) {
		this.delegate.eSetDeliver(arg0);
	}

	@Override
	public AgentClassesConfig getAgentClasses() {
		return this.delegate.getAgentClasses();
	}

	@Override
	public void setAgentClasses(AgentClassesConfig value) {
		this.delegate.setAgentClasses(value);
	}

	@Override
	public DestinationGroupsConfig getDestinationGroups() {
		return this.delegate.getDestinationGroups();
	}

	@Override
	public void setDestinationGroups(DestinationGroupsConfig value) {
		this.delegate.setDestinationGroups(value);

	}

	@Override
	public FunctionGroupsConfig getFunctionGroups() {
		return this.delegate.getFunctionGroups();
	}

	@Override
	public void setFunctionGroups(FunctionGroupsConfig value) {
		this.delegate.setFunctionGroups(value);
	}

	@Override
	public ProcessGroupsConfig getProcessGroups() {
		return this.delegate.getProcessGroups();
	}

	@Override
	public void setProcessGroups(ProcessGroupsConfig value) {
		this.delegate.setProcessGroups(value);
	}

	@Override
	public LoadBalancerConfigsConfig getLoadBalancerConfigs() {
		return this.delegate.getLoadBalancerConfigs();
	}
	
	@Override
	public void setLoadBalancerConfigs(LoadBalancerConfigsConfig value) {
		this.delegate.setLoadBalancerConfigs(value);
	}
	
	@Override
	public LogConfigsConfig getLogConfigs() {
		return this.delegate.getLogConfigs();
	}

	@Override
	public void setLogConfigs(LogConfigsConfig value) {
		this.delegate.setLogConfigs(value);
	}
	
	@Override
	public String getMessageEncoding() {
		return this.delegate.getMessageEncoding();
	}

	@Override
	public void setMessageEncoding(String value) {
		this.delegate.setMessageEncoding(value);
	}

	@Override
	public OverrideConfig getName() {
		return this.delegate.getName();
	}

	@Override
	public void setName(OverrideConfig value) {
		this.delegate.setName(value);

	}

	@Override
	public ObjectManagementConfig getObjectManagement() {
		return this.delegate.getObjectManagement();
	}

	@Override
	public void setObjectManagement(ObjectManagementConfig value) {
		this.delegate.setObjectManagement(value);
	}

	@Override
	public ProcessingUnitsConfig getProcessingUnits() {
		return this.delegate.getProcessingUnits();
	}

	@Override
	public void setProcessingUnits(ProcessingUnitsConfig value) {
		this.delegate.setProcessingUnits(value);
	}

	@Override
	public PropertyGroupConfig getPropertyGroup() {
		return this.delegate.getPropertyGroup();
	}

	@Override
	public void setPropertyGroup(PropertyGroupConfig value) {
		this.delegate.setPropertyGroup(value);
	}

	@Override
	public RevisionConfig getRevision() {
		return this.delegate.getRevision();
	}

	@Override
	public void setRevision(RevisionConfig value) {
		this.delegate.setRevision(value);
	}

	@Override
	public RulesetsConfig getRulesets() {
		return this.delegate.getRulesets();
	}

	@Override
	public void setRulesets(RulesetsConfig value) {
		this.delegate.setRulesets(value);
	}

	public void writeExternal(DataOutput out) throws IOException {
		out.write(CddTools.serialize(this.delegate));
	}

	public void readExternal(DataInput in) throws IOException {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte b;
		for (;;) {
			try {
				b = in.readByte();
			} catch (EOFException done) {
				break;
			}
			baos.write(b);
		}
		baos.close();
		this.delegate = CddTools.deserialize(baos.toByteArray());
	}

	private void writeObject(ObjectOutputStream out) throws IOException {
		this.writeExternal(out);
	}

	private void readObject(ObjectInputStream in) throws IOException,
			ClassNotFoundException {
		this.readExternal(in);
	}

	@SuppressWarnings("unused")
	private void readObjectNoData() throws ObjectStreamException {
	}
}
