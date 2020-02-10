package com.tibco.be.util;

import java.util.ArrayList;
import java.util.List;

import com.tibco.be.model.functions.FunctionDomain;
import com.tibco.be.util.BECustomFunctionHelper.ParamTypeInfo;

public class FunctionInfo {
	public ParamTypeInfo getReturnType() {
		return returnType;
	}

//	public ParamTypeInfo[] getParamTypes() {
//		return paramTypes;
//	}

	public String getFnName() {
		return fnName;
	}

	public boolean isDeprecated() {
		return deprecated;
	}

	public FunctionDomain[] getDomain() {
		return domain;
	}

	/**
	 * @param beCustomFunctionImpls
	 */
	FunctionInfo() {
	}

	ParamTypeInfo returnType;
//	ParamTypeInfo[] paramTypes;
	private String fnName;
	private ArrayList<ParamTypeInfo> args;
	private boolean deprecated;
	private FunctionDomain[] domain;
			
	public void setName(String fnName) {
		this.fnName = fnName;
	}

	public List<ParamTypeInfo> getArgs() {
		if (this.args == null) {
			this.args = new ArrayList<ParamTypeInfo>();
		}
		return args;
	}

	public void setReturnType(ParamTypeInfo rtInfo) {
		this.returnType = rtInfo;
	}

	public void setMapperEnabled(boolean value) {
		
	}

	public void setDeprecated(boolean value) {
		this.deprecated = value;
	}

	public void setDomain(FunctionDomain[] fndomain) {
		this.domain = fndomain;
	}
	
}