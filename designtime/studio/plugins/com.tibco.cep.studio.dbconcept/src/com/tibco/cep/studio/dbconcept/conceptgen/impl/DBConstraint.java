package com.tibco.cep.studio.dbconcept.conceptgen.impl;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 
 * @author schelwa
 * 
 * This class represent database constraint
 *
 */
public class DBConstraint {
	
	protected String constraintName;

	protected String constraintType;

	protected String rOwner;

	protected String rConstraintName;

	//the table columns/properties for this relationship, it is map of columnname and its position 
	protected Set<String> columns = new LinkedHashSet<String>();

	public DBConstraint(String cName, String cType){
		this.constraintName = cName;
		this.constraintType = cType;
	}
	
	public Set<String> getColumns() {
		return columns;
	}

	public String getConstraintName() {
		return constraintName;
	}

	public String getConstraintType() {
		return constraintType;
	}

	public String getRConstraintName() {
		return rConstraintName;
	}

	public String getROwner() {
		return rOwner;
	}

	public void addColumn(String column) {
		this.columns.add(column);
	}

	public void setConstraintName(String constraintName) {
		this.constraintName = constraintName;
	}

	public void setConstraintType(String constraintType) {
		this.constraintType = constraintType;
	}

	public void setRConstraintName(String constraintName) {
		rConstraintName = constraintName;
	}

	public void setROwner(String owner) {
		rOwner = owner;
	}
}
