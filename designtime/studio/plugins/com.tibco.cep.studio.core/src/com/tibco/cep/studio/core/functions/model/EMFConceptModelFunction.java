package com.tibco.cep.studio.core.functions.model;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.tibco.be.model.functions.FunctionDomain;
import com.tibco.be.model.functions.impl.ConceptModelFunction;
import com.tibco.be.parser.codegen.CGConstants;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.xml.data.primitive.ExpandedName;

public class EMFConceptModelFunction extends EMFOntologyModelFunction implements ConceptModelFunction {

    protected ExpandedName fFunctionName;
    protected String fJavaFunctionName;
    protected Class [] fParameterTypes;
    protected String [] fParameterNames;
    protected Entity [] fEntityArguments;

    public EMFConceptModelFunction(Concept model, ExpandedName functionName) {
        this(model, functionName, functionName.getLocalName());
    }

    public EMFConceptModelFunction(Concept model, ExpandedName functionName, String javaFunctionName) {
        super(model);
        this.fFunctionName=functionName;
        this.fJavaFunctionName=javaFunctionName;
        setFunctionDomains(new FunctionDomain[]{ACTION});
    }

    @Override
	String getDescription() {
        return "Creates a new instance of type <" + getModel().getFullPath() + ">";
	}

	@Override
	public Entity[] getEntityArguments() {
        putArgs(false);
        return fEntityArguments;
	}

	@Override
	public Entity getEntityReturnType() {
		return getModel();
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
	public boolean requiresAssert() {
		return true;
	}
	
	public boolean isVarargsCodegen() { return CGConstants.HDPROPS; }
	public Class getVarargsCodegenArgType() {
    	if(CGConstants.HDPROPS) return Object[].class;
    	else return null; 
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
        return com.tibco.cep.runtime.model.element.Concept.class;
	}

	@Override
	public Class[] getThrownExceptions() {
		return new Class[0];
	}

	@Override
	public boolean isTimeSensitive() {
		return false;
	}

	
	private static class ArgumentDescriptor {
        String name;
        Class clazz;
        Entity entity;
    }

    protected void putArgs(boolean refresh) {
        try {
            if (refresh || (fParameterTypes == null)) {
            	final List<ArgumentDescriptor> args = new ArrayList<ArgumentDescriptor>();
                boolean oversizeArgs = com.tibco.cep.studio.core.utils.ModelUtils.areConceptConstructorArgsOversize((Concept)getModel());
                if(!oversizeArgs)  {
//                    List propNames= new ArrayList();
//                    List modelTypes = new ArrayList();
//                    
//                    List propClasses= new ArrayList();
                    Collection propDefs = ((Concept) getModel()).getPropertyDefinitions(false);
//                    // TODO : review this change
//                    Collection propDefs = ((Concept) getModel()).getProperties();
    
                    for(Iterator it = propDefs.iterator(); it.hasNext();) {
                    	final ArgumentDescriptor arg = new ArgumentDescriptor();
                        PropertyDefinition pd = (PropertyDefinition)it.next();
                        Class propClass= pd.isArray() ?  argumentsMap2[pd.getType().getValue()] : argumentsMap1[pd.getType().getValue()];
                        arg.name = pd.getName();
                        arg.clazz = propClass;
//                        propClasses.add(propClass);
//                        propNames.add(pd.getName());
                        if (pd.getType() == PROPERTY_TYPES.CONCEPT || pd.getType() == PROPERTY_TYPES.CONCEPT_REFERENCE) {
                        	Concept concept = IndexUtils.getConcept(pd.getOwnerProjectName(), pd.getConceptTypePath());
//                            modelTypes.add(concept);
                            arg.entity = concept;
                        }
                        args.add(arg);
                    }
    
                    // Parameter Names
//                    fParameterNames = new String[propNames.size()+1];
//                    fParameterNames[0]= "extId";
                    final ArgumentDescriptor extIdArg = new ArgumentDescriptor();
                    extIdArg.name = "extId";
                    extIdArg.clazz =  java.lang.String.class;
                    args.add(0, extIdArg);
//                    int i=1;
//                    for (Iterator it=propNames.iterator(); it.hasNext();) {
//                    	fParameterNames[i++]= (String) it.next();
//                    }
//                    // Parameter Types
//                    fParameterTypes = new Class[propClasses.size()+1];
//                    fParameterTypes[0] = java.lang.String.class;
//    
//                    for (i=0; i < propClasses.size();i++) {
//                    	fParameterTypes[i+1] = (Class) propClasses.get(i);
//                    }
//    
//                    // Entity Arguments
//                    fEntityArguments = new Entity [modelTypes.size()];
//                    for (i=0; i < fEntityArguments.length;i++) {
//                        fEntityArguments[i] = (Entity) modelTypes.get(i);
//                    }
                } else {
//                    fEntityArguments = new Entity[0];
//                    fParameterNames = new String[] {"extId"};
//                    fParameterTypes = new Class[] {java.lang.String.class};
                    final ArgumentDescriptor extIdArg = new ArgumentDescriptor();
                    extIdArg.name = "extId";
                    extIdArg.clazz =  java.lang.String.class;
                    args.add(0, extIdArg);
                }
             // Parameter Names
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
