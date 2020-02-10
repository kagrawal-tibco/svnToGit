package com.tibco.cep.decision.table.model;

import java.util.Vector;

public class TableInfo {
	private Vector<FieldMap> dtPropPathMap ;
	//private Map<String, String> etPropPathMap ;
	private Vector<FieldMap> etPropPathMap ;
	private int noOfDTCustomConditions=0;
	private int noOfDTCustomActions=0;
	private int noOfCustomETConditions=0;
	private int noOfCustomETActions=0;
	public TableInfo(Vector<FieldMap> dtPropPathMap,Vector<FieldMap> etPropPathMap,int noOfDTCustomConditions,int noOfDTCustomActions,int noOfCustomETConditions,
									int noOfCustomETActions ){
		this.dtPropPathMap = dtPropPathMap;
		this.etPropPathMap = etPropPathMap;
		this.noOfDTCustomConditions = noOfDTCustomConditions;
		this.noOfDTCustomActions = noOfDTCustomActions;
		this.noOfCustomETConditions = noOfCustomETConditions;
		this.noOfCustomETActions = noOfCustomETActions;
		
	}

	public Vector<FieldMap> getDtPropPathMap() {
		return dtPropPathMap;
	}

	public void setDtPropPathMap(Vector<FieldMap> dtPropPathMap) {
		this.dtPropPathMap = dtPropPathMap;
	}

	public Vector<FieldMap> getEtPropPathMap() {
		return etPropPathMap;
	}

	public void setEtPropPathMap(Vector<FieldMap> etPropPathMap) {
		this.etPropPathMap = etPropPathMap;
	}

	public int getNoOfDTCustomConditions() {
		return noOfDTCustomConditions;
	}
	public void setNoOfDTCustomConditions(int noOfDTCustomConditions) {
		this.noOfDTCustomConditions = noOfDTCustomConditions;
	}
	public int getNoOfDTCustomActions() {
		return noOfDTCustomActions;
	}
	public void setNoOfDTCustomActions(int noOfDTCustomActions) {
		this.noOfDTCustomActions = noOfDTCustomActions;
	}
	public int getNoOfCustomETConditions() {
		return noOfCustomETConditions;
	}
	public void setNoOfCustomETConditions(int noOfCustomETConditions) {
		this.noOfCustomETConditions = noOfCustomETConditions;
	}
	public int getNoOfCustomETActions() {
		return noOfCustomETActions;
	}
	public void setNoOfCustomETActions(int noOfCustomETActions) {
		this.noOfCustomETActions = noOfCustomETActions;
	}
}
