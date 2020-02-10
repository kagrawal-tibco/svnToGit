package com.tibco.cep.decision.table.handler;

import java.util.List;

public class Mapper {

	private List<Integer> conditionMappingList;
	private List<Integer> actionMappingList;
	public Mapper(){
		
	}
	public Mapper(List<Integer> conditionMappingList ,List<Integer> actionMappingList) {
			// TODO Auto-generated constructor stub
		this.conditionMappingList = conditionMappingList;
		this.actionMappingList = actionMappingList;
	}
	public List<Integer> getConditionMappingList() {
		return conditionMappingList;
	}
	public void setConditionMappingList(List<Integer> conditionMappingList) {
		this.conditionMappingList = conditionMappingList;
	}
	public List<Integer> getActionMappingList() {
		return actionMappingList;
	}
	public void setActionMappingList(List<Integer> actionMappingList) {
		this.actionMappingList = actionMappingList;
	}
	
	
}
