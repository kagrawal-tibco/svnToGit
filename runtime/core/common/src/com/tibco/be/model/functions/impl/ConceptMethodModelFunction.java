package com.tibco.be.model.functions.impl;

import com.tibco.be.model.functions.FunctionDomain;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.ModelUtils;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.xml.data.primitive.ExpandedName;
import static com.tibco.be.model.functions.FunctionDomain.*;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Mar 26, 2007
 * Time: 2:39:25 AM
 * To change this template use File | Settings | File Templates.
 */
public class ConceptMethodModelFunction extends ModelFunctionImpl {

    String fnName;
    String conceptClassNm;
    String parameterNames[];
    Class parameterTypes[];
    Entity entityArguments[];
    Class returnType;
    ExpandedName methodName;

    public ConceptMethodModelFunction(Concept entity, ExpandedName methodName, String fnName, String params[], Class[] types, Class returnType) {
        super(entity);
        conceptClassNm = ModelUtils.convertPathToPackage(entity.getFullPath());
        this.fnName =  fnName;
        this.parameterNames = params;
        this.parameterTypes = types;
        this.entityArguments = new Entity[params.length];
        this.returnType = returnType;
        this.methodName = methodName;
        setFunctionDomains(new FunctionDomain[]{ ACTION });
    }

    public String getDescription() {
        return "Invoke method - " + this.getModel().getName();
    }

    public Entity[] getEntityArguments() {
        return entityArguments;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Entity getEntityReturnType() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String[] getParameterNames() {
        return this.parameterNames;
    }

    public String code() {
        return this.getModelClass() + "." + fnName;
    }

    public boolean doesModify() {
        return true;
    }

    public Class [] getArguments() {
        return this.parameterTypes;
    }

    public String getDocumentation() {
        return code();
    }

    public ExpandedName getName() {
        return methodName;
    }

    public Class getReturnClass() {
        return this.returnType;
    }

    public Class [] getThrownExceptions() {
        return new Class[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isTimeSensitive() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

   

    public boolean requiresAsync() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

   
    
}
