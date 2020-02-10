package com.tibco.cep.studio.core.functions.model;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;

import com.tibco.be.model.functions.FunctionDomain;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.model.ModelUtils;
import com.tibco.xml.data.primitive.ExpandedName;

public class EMFConceptPropertyModelFunction extends EMFOntologyModelFunction {

    private String fFnName;
    private ExpandedName fPropertyName;
    private String fConceptClassNm;
    private boolean isSet;
    private String fParameterNames[];
    private Class fParameterTypes[];
    private Entity fEntityArguments[];
    private PropertyDefinition fPropDef;

    public EMFConceptPropertyModelFunction(PropertyDefinition pd, ExpandedName propertyName, String fnName, boolean isSet ) {
    	super(pd);
    	this.fPropDef = pd;
    	fConceptClassNm = ModelUtils.convertPathToPackage(pd.getOwner().getFullPath());
    	this.fFnName = fConceptClassNm + "." + fnName;
    	this.fPropertyName = propertyName;
    	this.isSet = isSet;
    	setFunctionDomains(new FunctionDomain[]{ACTION});

    	Class c = argumentsMap1[fPropDef.getType().getValue()].isAssignableFrom(com.tibco.cep.runtime.model.element.Concept.class) ? com.tibco.cep.kernel.model.entity.Entity.class : argumentsMap1[pd.getType().getValue()];
    	if (isSet) {
    		if (pd.isArray()) {
    			fParameterNames = new String[] {"thisInstance", pd.getName(), "index"};
    			fParameterTypes = new Class[] {java.lang.Object.class, c, int.class};
    			fEntityArguments = new Entity[3];
    		}
    		else {
    			fParameterNames = new String[] {"thisInstance", pd.getName()};
    			fParameterTypes = new Class[] {java.lang.Object.class, c};
    			fEntityArguments = new Entity[2];
    		}
    	}
    	else {
    		fParameterNames = new String[] {"thisInstance" };
    		fParameterTypes = new Class[] {java.lang.Object.class};
    		fEntityArguments = new Entity[1];
    	}

    }

	@Override
	String getDescription() {
        if (isSet) {
            return "Set property " + this.getModel().getName() ;
        }
        return "return property value " + this.getModel().getName();
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
		return fParameterNames;
	}

	@Override
	public String code() {
        return this.getModelClass() + "." + fFnName;
	}

	@Override
	public boolean doesModify() {
		return isSet;
	}

	@Override
	public Class[] getArguments() {
		return fParameterTypes;
	}

	@Override
	public String getDocumentation() {
		return code();
	}

	@Override
	public ExpandedName getName() {
		return fPropertyName;
	}

	@Override
	public Class getReturnClass() {
        if (isSet) return void.class;
        return fPropDef.isArray() ? argumentsMap2[fPropDef.getType().getValue()] : argumentsMap1[fPropDef.getType().getValue()];
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
	public boolean isValidInCondition() {
		return !isSet;
	}



}
