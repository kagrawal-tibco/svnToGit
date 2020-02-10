package com.tibco.cep.mapper.xml.xdata.xpath20;

import java.util.List;

import com.tibco.xml.xmodel.xpath.func.CustomFunctions.CustomFuncEntry;
import com.tibco.xml.xmodel.xpath.func.XFunction;
import com.tibco.xml.xmodel.xpath.type.XType;

public class JavaCustomFunctionEntry extends CustomFuncEntry {

	private InvokeJavaXFunction javaXFunc;
	private String category;

	public JavaCustomFunctionEntry(InvokeJavaXFunction javaXFunc, String category) {
		this.javaXFunc = javaXFunc;
		this.category = category;
	}

	@Override
	public String[] getArgumentTypes() {
		XType[] args = javaXFunc.getArgs();
		String[] argTypes = new String[args.length];
		for (int i=0; i<args.length; i++) {
			argTypes[i] = args[i].getLocalName();
		}
		
		return argTypes;
	}

	@Override
	public String getCategory() {
		return category;
	}

	@Override
	public String getCategoryHelp() {
		return super.getCategoryHelp();
	}

	@Override
	public String getFunctionName() {
		return javaXFunc.getFunctionName();
	}

	@Override
	public String getHelpText() {
		// TODO Auto-generated method stub
		return super.getHelpText();
	}

	@Override
	public List<String[]> getExamples() {
		return super.getExamples();
	}

	@Override
	public String getNamespace() {
		return javaXFunc.getNamespace();
	}

	@Override
	public String getReturnType() {
		return javaXFunc.getReturnType().toString();
	}

	@Override
	public XFunction getXFunction() {
		return javaXFunc;
	}

	@Override
	public String[] getParamNames() {
		return super.getParamNames();
	}

	@Override
	public String getSuggestedPrefix() {
		return this.category;//"BE";
	}

	@Override
	public int getMinNumberOfArgs() {
		return javaXFunc.getMinimumNumArgs();
	}
	
}
