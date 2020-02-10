/**
 * 
 */
package com.tibco.cep.decision.table.model.domainmodel.util;

/**
 * it gets notified when DM is updated
 * @author rmishra
 *
 */
public interface DMListener {

	public void notify(DMEvent dmEvent);
	
}
