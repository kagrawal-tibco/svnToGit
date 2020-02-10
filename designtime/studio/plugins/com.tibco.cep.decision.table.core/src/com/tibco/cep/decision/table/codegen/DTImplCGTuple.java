package com.tibco.cep.decision.table.codegen;

import com.tibco.cep.decision.table.language.MergedTable;
import com.tibco.cep.decision.table.model.dtmodel.Table;

public class DTImplCGTuple {
	private Table table = null;
	private MergedTable mergedTable = null;
	private String message = null;
	private byte[] classBytes = null;
	//private Class loadedClass = null;
	//fully qualified name
	private String fqn = null;
	private boolean hasError = false;
	
	public DTImplCGTuple(Table table) {
		this.table = table;
	}
	public DTImplCGTuple(Table table, MergedTable mergedTable) {
		this.table = table;
		this.mergedTable = mergedTable;
	}
	public DTImplCGTuple(Table table, MergedTable mergedTable, String message,
			byte[] classBytes, String fqn, boolean hasError) {
		this.table = table;
		this.mergedTable = mergedTable;
		this.message = message;
		this.classBytes = classBytes;
		this.fqn = fqn;
		this.hasError = hasError;
	}


	public MergedTable getMergedTable() {
		return mergedTable;
	}
	public void setMergedTable(MergedTable mergedTable) {
		this.mergedTable = mergedTable;
	}
	public Table getTable() {
		return table;
	}
	public void setTable(Table table) {
		this.table = table;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setErrorMessage(String errorMessage) {
		setMessage(errorMessage);
		setHasError(true);
	}
	public byte[] getClassBytes() {
		return classBytes;
	}
	public void setClassBytes(byte[] classBytes) {
		this.classBytes = classBytes;
	}
//	public Class getLoadedClass() {
//		return loadedClass;
//	}
//	public void setLoadedClass(Class loadedClass) {
//		this.loadedClass = loadedClass;
//	}
	public String getFqn() {
		return fqn;
	}
	public void setFqn(String fqn) {
		this.fqn = fqn;
	}
	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}
	public boolean hasError() {
		return hasError;
	}
}
