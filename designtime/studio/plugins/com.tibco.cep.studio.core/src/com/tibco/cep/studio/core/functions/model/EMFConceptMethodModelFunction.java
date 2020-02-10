package com.tibco.cep.studio.core.functions.model;

import com.tibco.be.model.functions.FunctionDomain;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.model.ModelUtils;
import com.tibco.xml.data.primitive.ExpandedName;
import static com.tibco.be.model.functions.FunctionDomain.*;

public class EMFConceptMethodModelFunction extends EMFOntologyModelFunction {

	protected String[] fParameterNames;
	protected Class[] fParameterTypes;
	protected Entity[] fEntityArguments;
    protected String fnName;
    protected Class fReturnType;
    protected ExpandedName fMethodName;
    protected String fConceptClassNm;

    public EMFConceptMethodModelFunction(Concept entity, ExpandedName methodName, String fnName, String params[], Class[] types, Class returnType) {
        super(entity);
        this.fConceptClassNm = ModelUtils.convertPathToPackage(entity.getFullPath());
        this.fnName =  fnName;
        this.fParameterNames = params;
        this.fParameterTypes = types;
        this.fReturnType = returnType;
        this.fMethodName = methodName;
        this.fEntityArguments = new Entity[params.length];
        setFunctionDomains(new FunctionDomain[]{ACTION});
    }

	@Override
	String getDescription() {
        return "Invoke method - " + this.getModel().getName();
	}

	@Override
	public Entity[] getEntityArguments() {
		return fEntityArguments;
	}

	@Override
	public Entity getEntityReturnType() {
		return null;
	}

	@Override
	String[] getParameterNames() {
        return this.fParameterNames;
	}

	@Override
	public String code() {
        return this.getModelClass() + "." + fnName;
	}

	@Override
	public boolean doesModify() {
        return true;
	}

	@Override
	public Class[] getArguments() {
        return this.fParameterTypes;
	}

	@Override
	public String getDocumentation() {
		return code();
	}

	@Override
	public ExpandedName getName() {
		return fMethodName;
	}

	@Override
	public Class getReturnClass() {
		return fReturnType;
	}

	@Override
	public Class[] getThrownExceptions() {
		return new Class[0];
	}

	@Override
	public boolean isTimeSensitive() {
		return false;
	}

	@Override
	public boolean requiresAsync() {
		return false;
	}

}
