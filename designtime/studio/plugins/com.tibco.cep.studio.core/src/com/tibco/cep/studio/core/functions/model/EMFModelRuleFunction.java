package com.tibco.cep.studio.core.functions.model;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;

import java.util.ArrayList;
import java.util.List;

import com.tibco.be.model.functions.FunctionDomain;
import com.tibco.be.model.functions.impl.ModelRuleFunction;
import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.model.rdf.primitives.RDFUberType;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.designtime.core.model.rule.Validity;
import com.tibco.cep.designtime.model.ModelUtils;
import com.tibco.cep.studio.core.index.model.Folder;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.xml.data.primitive.ExpandedName;

public class EMFModelRuleFunction extends EMFOntologyModelFunction implements ModelRuleFunction {

    protected ExpandedName functionName;
    protected String javaFunctionName;
    protected Class [] parameterTypes;
    protected String [] parameterNames;
    protected Entity [] entityArguments;

    public EMFModelRuleFunction(RuleFunction model, ExpandedName functionName) {
        this(model, functionName, functionName.getLocalName());
    }

    public EMFModelRuleFunction(RuleFunction model, ExpandedName functionName, String javaFunctionName) {
        super(model);
        this.functionName=functionName;
        this.javaFunctionName=javaFunctionName;
        setFunctionDomains(new FunctionDomain[]{ACTION});
    }

    @Override
	String getDescription() {
        return "Invokes the Rule Function " +  ModelUtils.convertPathToPackage(getModel().getFullPath()) + "()";
	}

	@Override
	protected String getFSName() {
        String uri = getName().getNamespaceURI();
        if(!ModelUtils.IsEmptyString(uri)) return super.getFSName();

        return getName().getLocalName();
	}

	@Override
	public Entity[] getEntityArguments() {
        putArgs(false);
        return entityArguments;
    }

	@Override
	public Entity getEntityReturnType() {
        RuleFunction rf = (RuleFunction) getModel();

        String retType = rf.getReturnType();
        if(retType == null || !retType.startsWith(String.valueOf(Folder.FOLDER_SEPARATOR_CHAR))) return null;

//        Ontology o = rf.getOntology();
    	Entity e = IndexUtils.getEntity(getModel().getOwnerProjectName(), retType);
        return e;
    }

    public Class[] getParameterTypes() {
        putArgs(false);
        return parameterTypes;
    }

    @Override
	String[] getParameterNames() {
        putArgs(false);
        return parameterNames;
    }

	@Override
	public String code() {
        return this.getModelClass() + "." + javaFunctionName;
	}

	@Override
    public String getModelClass() {
        return com.tibco.cep.studio.core.utils.ModelUtils.ruleFnClassFSName((RuleFunction)getModel());
    }

    @Override
	public boolean doesModify() {
        return !isValidInCondition();
	}

	@Override
	public Class[] getArguments() {
        putArgs(false);
        return parameterTypes;
    }

	@Override
	public String getDocumentation() {
		return code();
	}

	@Override
	public ExpandedName getName() {
		return functionName;
	}

	@Override
	public Class getReturnClass() {
        Class cls = void.class;

        RuleFunction rf = (RuleFunction) getModel();
        String returnType = rf.getReturnType();
        if(returnType == null || void.class.getName().equals(returnType)) return cls;
        int idx = returnType.indexOf('[');
        boolean array = false;
        if (idx > 0) {
        	array = true;
        	returnType = returnType.substring(0, idx);
        }
        
        RDFUberType type = RDFTypes.getType(returnType);
        if(type != null) {
            if(RDFTypes.OBJECT.equals(type)) {
                cls = java.lang.Object.class; // special casing this cuz I can't remember in which maps, lists etc this should go
            }
            else {
                cls = array ? (Class) RDF_2_PRIMITIVE_ARG_MAP2.get(type) : (Class) RDF_2_PRIMITIVE_ARG_MAP.get(type);
            }
        }
        else {
        	Entity e = IndexUtils.getEntity(getModel().getOwnerProjectName(), returnType);
            if(e instanceof Concept) cls = array ? (Class) RDF_2_PRIMITIVE_ARG_MAP2.get(RDFTypes.BASE_CONCEPT) : (Class) RDF_2_PRIMITIVE_ARG_MAP.get(RDFTypes.BASE_CONCEPT);
            else if(e instanceof Event) cls = array ? (Class) RDF_2_PRIMITIVE_ARG_MAP2.get(RDFTypes.BASE_EVENT) : (Class) RDF_2_PRIMITIVE_ARG_MAP.get(RDFTypes.BASE_EVENT);
            else {
                assert(false);
                cls = null; // shouldn't happen
            }
        }

        return cls;
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
        RuleFunction rf = (RuleFunction) getModel();
        return (rf.getValidity() != Validity.ACTION);
    }

	@Override
	public boolean isValidInQuery() {
        RuleFunction rf = (RuleFunction) getModel();
        return (rf.getValidity() == Validity.QUERY);
    }
	
	private static class ArgumentDescriptor {
        String name;
        Class clazz;
        Entity entity;
    }


    /**
     *
     * @param refresh
     */
    public void putArgs(boolean refresh) {
        try {
            if (refresh || (parameterTypes == null)) {

                final List<ArgumentDescriptor> args = new ArrayList<ArgumentDescriptor>();

                RuleFunction rf = (RuleFunction) getModel();
//                final EList<Symbol> symbols = rf.getSymbols().getSymbolList();

                for(Symbol symbol: rf.getSymbols().getSymbolList()) {
//                	final Symbol symbol = (Symbol) it.next();
                	final String identifier = symbol.getIdName();
                	final String type = symbol.getType();
                    

                    final ArgumentDescriptor arg = new ArgumentDescriptor();
                    arg.name = identifier;

                    RDFUberType rdfType = RDFTypes.getType(type);

                    if(rdfType != null) {
                        arg.clazz = symbol.isArray() ? (Class)RDF_2_PRIMITIVE_ARG_MAP2.get(rdfType) : (Class)RDF_2_PRIMITIVE_ARG_MAP.get(rdfType);
                    }
                    else {
                    	Entity e = IndexUtils.getEntity(getModel().getOwnerProjectName(), type);
                        arg.entity = e;
                        if(e instanceof Concept) {
                            arg.clazz = symbol.isArray() ? (Class) RDF_2_PRIMITIVE_ARG_MAP2.get(RDFTypes.BASE_CONCEPT) : (Class) RDF_2_PRIMITIVE_ARG_MAP.get(RDFTypes.BASE_CONCEPT);
                        }
                        else if(e instanceof Event) {
                            arg.clazz = symbol.isArray() ? (Class) RDF_2_PRIMITIVE_ARG_MAP2.get(RDFTypes.BASE_EVENT) : (Class) RDF_2_PRIMITIVE_ARG_MAP.get(RDFTypes.BASE_EVENT);
                        }
                    }

                    args.add(arg);
                }

                // Parameter Names
                final int argNum = args.size();
                parameterNames = new String[argNum];
                parameterTypes = new Class[argNum];
                entityArguments = new Entity[argNum];
                int i=0;
                for (ArgumentDescriptor arg : args) {
                    parameterNames[i] = arg.name;
                    parameterTypes[i] = arg.clazz;
                    entityArguments[i] = arg.entity;
                    i++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
