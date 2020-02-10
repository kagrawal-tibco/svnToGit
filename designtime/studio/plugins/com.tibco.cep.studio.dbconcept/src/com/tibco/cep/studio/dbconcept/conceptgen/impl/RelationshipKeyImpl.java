package com.tibco.cep.studio.dbconcept.conceptgen.impl;

import com.tibco.cep.studio.dbconcept.conceptgen.RelationshipKey;

public class RelationshipKeyImpl implements RelationshipKey {
	
	private String parentKey;
	private String childKey;
	private boolean useForSelect;
	
	/**
	 * @param parentKey
	 * @param childKey
	 * @param useForSelect
	 */
	public RelationshipKeyImpl(String parentKey, String childKey, boolean useForSelect) {
		this.parentKey = parentKey;
		this.childKey = childKey;
		this.useForSelect = useForSelect;
	}

	public String getParentKey(){
		return parentKey;
	}
	
	public String getChildKey(){
		return childKey;
	}
	
	public boolean isUsedForSelect(){
		return useForSelect;
	}
}
