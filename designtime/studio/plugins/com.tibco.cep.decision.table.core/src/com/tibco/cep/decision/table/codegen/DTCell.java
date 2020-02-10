package com.tibco.cep.decision.table.codegen;

import java.util.List;

import com.tibco.be.parser.RuleError;
import com.tibco.be.parser.tree.RootNode;

public class DTCell {
	private JavaFragment[] javaBodies = null;
	private List<RootNode> rootNodes = null;
	private List<RuleError> errors = null;
	//public Expression exp;
	
	public DTCell() {}
	
	public List<RuleError> getErrors() {
		return errors;
	}
	
	public List<RootNode> getRootNodes() {
		return rootNodes;
	}

	public JavaFragment[] getJavaBodies() {
		return javaBodies;
	}

	public void setJavaBodies(JavaFragment[] bodies) {
		javaBodies = bodies;
	}

	public void setRootNodes(List<RootNode> rootNodes) {
		this.rootNodes = rootNodes;
	}

	public void setErrors(List<RuleError> errors) {
		this.errors = errors;
	}
}