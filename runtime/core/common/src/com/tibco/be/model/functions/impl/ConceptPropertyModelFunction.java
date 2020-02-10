package com.tibco.be.model.functions.impl;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;

import com.tibco.be.model.functions.FunctionDomain;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.ModelUtils;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.xml.data.primitive.ExpandedName;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Mar 17, 2007
 * Time: 7:03:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConceptPropertyModelFunction extends ModelFunctionImpl{

    String fnName;
    ExpandedName propertyName;
    String conceptClassNm;
    boolean isSet;
    String parameterNames[];
    Class parameterTypes[];
    Entity [] entityArguments;
    PropertyDefinition pd;

    public ConceptPropertyModelFunction(PropertyDefinition pd, ExpandedName propertyName, String fnName, boolean isSet ) {
        super(pd);
        this.pd = pd;
        conceptClassNm = ModelUtils.convertPathToPackage(pd.getOwner().getFullPath());
        this.fnName = conceptClassNm + "." + fnName;
        this.propertyName = propertyName;
        this.isSet = isSet;
        setFunctionDomains(new FunctionDomain[]{ ACTION });

        Class c = argumentsMap1[pd.getType()].isAssignableFrom(com.tibco.cep.runtime.model.element.Concept.class) ? com.tibco.cep.kernel.model.entity.Entity.class : argumentsMap1[pd.getType()];
        if (isSet) {
            if (pd.isArray()) {
                parameterNames = new String[] {"thisInstance", pd.getName(), "index"};
                parameterTypes = new Class[] {java.lang.Object.class, c, int.class};
                entityArguments = new Entity[3];
            }
            else {
                parameterNames = new String[] {"thisInstance", pd.getName()};
                parameterTypes = new Class[] {java.lang.Object.class, c};
                entityArguments = new Entity[2];
            }
        }
        else {
                parameterNames = new String[] {"thisInstance" };
                parameterTypes = new Class[] {java.lang.Object.class};
                entityArguments = new Entity[1];

        }

    }

    String getDescription() {
        if (isSet) {
            return "Set property " + this.getModel().getName() ;
        }
        return "return property value " + this.getModel().getName();
    }

    public Entity[] getEntityArguments() {
        return entityArguments;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Entity getEntityReturnType() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    String[] getParameterNames() {

        return parameterNames;
    }

    public String code() {
        return this.getModelClass() + "." + fnName;
    }

    public boolean doesModify() {
        return isSet;
    }

    public Class [] getArguments() {
        return parameterTypes;
    }

    public String getDocumentation() {
        return code();
    }

    public ExpandedName getName() {
        return propertyName;
    }

    public Class getReturnClass() {
        if (isSet) return void.class;
        return pd.isArray() ? argumentsMap2[pd.getType()] : argumentsMap1[pd.getType()];
    }

    public Class [] getThrownExceptions() {
        return new Class[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isTimeSensitive() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }


    public boolean isValidInCondition() {
        return !isSet;
    }

    public boolean requiresAsync() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }


}
