package com.tibco.cep.mapper.codegen;

public class XsltCodegenContext {

	private boolean create;
	private boolean createRoot;
	private boolean BPMN;
	
	public XsltCodegenContext(boolean create, boolean createRoot, boolean BPMN) {
		this.create = create;
		this.createRoot = createRoot;
		this.BPMN = BPMN;
	}

	public boolean isCreate() {
		return create;
	}

	public void setCreate(boolean create) {
		this.create = create;
	}

	public boolean isCreateRoot() {
		return createRoot;
	}

	public void setCreateRoot(boolean createRoot) {
		this.createRoot = createRoot;
	}

	public boolean isBPMN() {
		return BPMN;
	}

	public void setBPMN(boolean bPMN) {
		BPMN = bPMN;
	}
	
}
