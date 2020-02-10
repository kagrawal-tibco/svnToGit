package com.jidesoft.decision;

import java.util.List;
import java.util.Vector;

import com.tibco.cep.decision.table.model.FieldMap;
import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.designtime.core.model.domain.DomainInstance;

public class UserObject {
	
	private Vector<FieldMap> propPathMap;
	
	/**
	 * Domain instances associated with a property
	 */
	private List<DomainInstance> domainInstances;
	
	private Column column;
	
	public UserObject(){
		
	}
	public UserObject(Vector <FieldMap> propPathMap, 
			          List<DomainInstance> domainInstances){
		this.propPathMap = propPathMap;
		this.domainInstances = domainInstances;
	}
	
	public UserObject(List<DomainInstance> domainInstances){		
		this.domainInstances = domainInstances;
	}
	
	public Vector<FieldMap> getPropPathMap() {
		return propPathMap;
	}
	
	public List<DomainInstance> getDomainInstances() {
		return domainInstances;
	}
	
	public Column getColumn() {
		return column;
	}
	
	public void setColumn(Column column) {
		this.column = column;
	}

}
