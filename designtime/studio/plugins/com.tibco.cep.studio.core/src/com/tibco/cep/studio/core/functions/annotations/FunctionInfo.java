package com.tibco.cep.studio.core.functions.annotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.tibco.be.model.functions.BEFunction;
import com.tibco.be.model.functions.FunctionDomain;


/**
 * @author Pranab Dhar Function Information
 */
public class FunctionInfo {
	private String name;
	private List<ParamTypeInfo> args = new ArrayList<ParamTypeInfo>();
	private ParamTypeInfo returnType;
	private List<FunctionDomain> domains = new ArrayList<FunctionDomain>();
	private boolean deprecated;
	private boolean mapperEnabled;
	private BEFunction annotation;
	


	public FunctionInfo() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ParamTypeInfo> getArgs() {
		return args;
	}

	public ParamTypeInfo getReturnType() {
		return returnType;
	}

	public void setReturnType(ParamTypeInfo rtInfo) {
		this.returnType = rtInfo;

	}

	public void setDomain(FunctionDomain[] functionDomains) {
		this.domains = Arrays.asList(functionDomains);
	}
	
	public boolean isAction() {
		return domains.contains(FunctionDomain.ACTION);
	}
	public boolean isCondition() {
		return domains.contains(FunctionDomain.CONDITION);
	}
	public boolean isQuery() {
		return domains.contains(FunctionDomain.QUERY);
	}
	
	public boolean isProcess() {
		return domains.contains(FunctionDomain.PROCESS);
	}
	public boolean isBui() {
		return domains.contains(FunctionDomain.BUI);
	}

	public void setDeprecated(boolean value) {
		this.deprecated = value;
		
	}
	public boolean isDeprecated() {
		return deprecated;
	}

	public void setMapperEnabled(boolean value) {
		this.mapperEnabled = value;
		
	}
	
	public boolean isMapperEnabled() {
		return mapperEnabled;
	}

	public void setAnnotation(BEFunction fnAnnotation) {
		this.annotation = fnAnnotation;
		
	}
	public BEFunction getAnnotation() {
		return annotation;
	}
}