/**
 * 
 */
package com.tibco.cep.runtime.service.deploy;

import java.util.Set;

import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.deploy.template.operation.RuleTemplateDeploymentOperationDescriptor;

/**
 * @author vpatil
 *
 */
public interface Deployable {

	public void deploy(Set<RuleTemplateDeploymentOperationDescriptor> operations) throws Exception;
	
	public Cluster getCluster();
}
