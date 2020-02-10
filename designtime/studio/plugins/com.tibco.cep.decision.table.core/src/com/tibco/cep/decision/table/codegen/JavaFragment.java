package com.tibco.cep.decision.table.codegen;

public class JavaFragment {
	private int id;
	private String body;
	
	public JavaFragment(String body, int id) {
		this.body = body;
		this.id = id;
	}
	
	String getBody() {
		return body;
	}
	
	public int getId() {
		return id;
	}
}
