/**
 * 
 */
package com.tibco.cep.decision.table.model.domainmodel.util;

import com.tibco.cep.decision.table.model.domainmodel.Domain;
import com.tibco.cep.decision.table.model.domainmodel.DomainEntry;

/**
 * it's event for any Domain Model update
 * @author rmishra
 *
 */
public class DMEvent {
	private DMEventType dmEventType;
	private Domain source;
	private DomainEntry oldVal;
	private DomainEntry newVal;
	/**
	 *  * 
	 * @param dmEventType
	 * @param oldVal
	 * @param newVal
	 * @param source --> source of the event
	 */
	public DMEvent(DMEventType dmEventType, DomainEntry oldEntry , DomainEntry newEntry ,Domain source){
		this.dmEventType = dmEventType;
		this.source = source;
		this.oldVal = oldEntry;
		this.newVal = newEntry;
	}
	public DMEventType getDmEventType() {
		return dmEventType;
	}
	public Domain getSource() {
		return source;
	}
	public DomainEntry getOldVal() {
		return oldVal;
	}
	public DomainEntry getNewVal() {
		return newVal;
	}

}
