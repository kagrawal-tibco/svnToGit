package com.tibco.cep.studio.core.functions.model;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.tibco.be.model.functions.FunctionDomain;
import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.xml.data.primitive.ExpandedName;

public class EMFEventModelFunction_Create extends EMFOntologyModelFunction {

    private ExpandedName fFunctionName;
    private String fJavaFunctionName;
    private Class [] fParameterTypes;
    private String [] fParameterNames;
    private Entity[] fEntityArguments;

    EMFEventModelFunction_Create(Event model, ExpandedName functionName, String javaFunctionName) {
        super(model);
        this.fFunctionName=functionName;
        this.fJavaFunctionName=javaFunctionName;
        setFunctionDomains(new FunctionDomain[]{ACTION});
    }

    @Override
	String getDescription() {
        return "Creates a new event of type " + getModel().getFullPath() ;
	}

	@Override
	public Entity[] getEntityArguments() {
		return new Entity[0];
	}

	@Override
	public Entity getEntityReturnType() {
		return getModel();
	}

	@Override
	public boolean requiresAssert() {
		return true;
	}

    Class[] getParameterTypes() {
        putArgs(false);
        return fParameterTypes;
    }

    @Override
	String[] getParameterNames() {
        putArgs(false);
        return fParameterNames;
	}

	@Override
	public String code() {
        return this.getModelClass() + "." + fJavaFunctionName;
	}

	@Override
	public boolean doesModify() {
		return false;
	}

	@Override
	public Class[] getArguments() {
        putArgs(false);
        return fParameterTypes;
	}

	@Override
	public String getDocumentation() {
		return code();
	}

	@Override
	public ExpandedName getName() {
		return fFunctionName;
	}

	@Override
	public Class getReturnClass() {
        return com.tibco.cep.kernel.model.entity.Event.class;  //todo - change this to simple Event?
	}

	@Override
	public Class[] getThrownExceptions() {
		return new Class[0];
	}

	@Override
	public boolean isTimeSensitive() {
		return false;
	}


    private static int getRDFTermTypeFlag(PROPERTY_TYPES type) {
        if(type == PROPERTY_TYPES.BOOLEAN) {
            return RDFTypes.BOOLEAN_TYPEID;
        } else if(type == PROPERTY_TYPES.INTEGER) {
            return RDFTypes.INTEGER_TYPEID;
        } else if(type == PROPERTY_TYPES.LONG) {
            return RDFTypes.LONG_TYPEID;
        } else if(type == PROPERTY_TYPES.DOUBLE) {
            return RDFTypes.DOUBLE_TYPEID;
        } else if(type == PROPERTY_TYPES.DATE_TIME) {
            return RDFTypes.DATETIME_TYPEID;
        } else {
            return RDFTypes.STRING_TYPEID;
        }
    }
    private static class ArgumentDescriptor {
        String name;
        Class clazz;
        Entity entity;
    }
    void putArgs(boolean refresh) {

    	try {
    		if (refresh || (fParameterTypes == null)) {
    			final List<ArgumentDescriptor> args = new ArrayList<ArgumentDescriptor>();
    			
//    			ArrayList propNames= new ArrayList();
//    			List propClasses= new ArrayList();
    			EList<PropertyDefinition> propDefs = ((Event) getModel()).getAllUserProperties();

    			for(Iterator<PropertyDefinition> it = propDefs.iterator(); it.hasNext();) {
    				final ArgumentDescriptor arg = new ArgumentDescriptor();
    				PropertyDefinition pd = it.next();
    				arg.name = pd.getName();
    				arg.clazz = argumentsMap1[(getRDFTermTypeFlag(pd.getType()))];
    				args.add(arg);
//    				propNames.add(pd.getName());
//    				propClasses.add(argumentsMap1[(getRDFTermTypeFlag(pd.getType()))]);
    			}
    			final ArgumentDescriptor extIdArg = new ArgumentDescriptor();
                extIdArg.name = "extId";
                extIdArg.clazz = java.lang.String.class;
                args.add(0, extIdArg);
                final ArgumentDescriptor payLoadArg = new ArgumentDescriptor();
                payLoadArg.name = "payload";
                payLoadArg.clazz = java.lang.String.class;
                args.add(1, payLoadArg);
//                
//    			fParameterNames = new String[propNames.size()+2];
//    			fParameterNames[0]= "extId";
//    			fParameterNames[1]="payload";
//
//    			int i=2;
//    			for (Iterator it=propNames.iterator(); it.hasNext();) {
//    				fParameterNames[i++]= (String) it.next();
//    			}
//    			fParameterTypes = new Class[propClasses.size()+2];
//    			fParameterTypes[0] = java.lang.String.class;
//    			fParameterTypes[1] = java.lang.String.class;
//
//    			for (i=0; i < propClasses.size();i++) {
//    				fParameterTypes[i+2] = (Class) propClasses.get(i);
//    			}
                final int argNum = args.size();
                fParameterNames = new String[argNum];
                fParameterTypes = new Class[argNum];
                fEntityArguments = new Entity[argNum];
                int i=0;
                for (ArgumentDescriptor arg : args) {
                    fParameterNames[i] = arg.name;
                    fParameterTypes[i] = arg.clazz;
                    fEntityArguments[i] = arg.entity;
                    i++;
                }
    			//System.out.println(functionName + " returning " + fParameterTypes.length + " parameters");
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
}
