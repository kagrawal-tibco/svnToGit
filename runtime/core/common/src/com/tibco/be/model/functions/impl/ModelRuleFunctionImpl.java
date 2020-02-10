package com.tibco.be.model.functions.impl;


import static com.tibco.be.model.functions.FunctionDomain.ACTION;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tibco.be.model.functions.FunctionDomain;
import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.model.rdf.primitives.RDFUberType;
import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.Folder;
import com.tibco.cep.designtime.model.ModelUtils;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.rule.RuleFunction;
import com.tibco.cep.designtime.model.rule.Symbol;
import com.tibco.cep.designtime.model.rule.Symbols;
import com.tibco.xml.data.primitive.ExpandedName;

/**
 * @author ishaan
 * @version Jan 28, 2005, 1:34:20 PM
 */
public class ModelRuleFunctionImpl extends ModelFunctionImpl implements ModelRuleFunction {
    protected ExpandedName functionName;
    protected String javaFunctionName;
    protected Class [] parameterTypes;
    protected String [] parameterNames;
    protected Entity [] entityArguments;


    protected Class[] getParameterTypes() {
        putArgs(false);
        return parameterTypes;
    }

    protected String[] getParameterNames() {
        putArgs(false);
        return parameterNames;
    }

    protected String getFSName() {
        String uri = getName().getNamespaceURI();
        if(!ModelUtils.IsEmptyString(uri)) return super.getFSName();

        return getName().getLocalName();
    }

    /**
     *
     * @return a String
     */
    public String getDescription() {
        return "Invokes the Rule Function " +  ModelUtils.convertPathToPackage(model.getFullPath()) + "()";
    }

    /**
     *
     * @param model
     */
    public ModelRuleFunctionImpl(RuleFunction model, ExpandedName functionName) {
        this(model, functionName, functionName.getLocalName());
    }

    /**
     *
     * @param model
     * @param functionName
     * @param javaFunctionName
     */
    public ModelRuleFunctionImpl(RuleFunction model, ExpandedName functionName, String javaFunctionName) {
        super(model);
        this.functionName=functionName;
        this.javaFunctionName=javaFunctionName;
        setFunctionDomains(new FunctionDomain[]{ ACTION });
    }

    /**
     *
     * @return an ExpandedName
     */
    public ExpandedName getName() {
        return functionName;
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

                Ontology o = model.getOntology();
                RuleFunction rf = (RuleFunction) model;
                final Symbols symbols = rf.getArguments();

                for(Iterator it = symbols.values().iterator(); it.hasNext();) {
                    final Symbol symbol = (Symbol) it.next();
                    final String identifier = symbol.getName();
                    final String type = symbol.getType();

                    final ArgumentDescriptor arg = new ArgumentDescriptor();
                    arg.name = identifier;

                    RDFUberType rdfType = RDFTypes.getType(type);

                    if(rdfType != null) {
                        arg.clazz = (Class) RDF_2_PRIMITIVE_ARG_MAP.get(rdfType);
                    }
                    else {
                        Entity e = o.getEntity(type);
                        arg.entity = e;
                        if(e instanceof Concept) {
                            arg.clazz = (Class) RDF_2_PRIMITIVE_ARG_MAP.get(RDFTypes.BASE_CONCEPT);
                        }
                        else if(e instanceof Event) {
                            arg.clazz = (Class) RDF_2_PRIMITIVE_ARG_MAP.get(RDFTypes.BASE_EVENT);
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

    /**
     * @return a Class
     */
    public Class getReturnClass() {
        Class cls = void.class;

        RuleFunction rf = (RuleFunction) model;
        String returnType = rf.getReturnType();
        if(returnType == null || void.class.getName().equals(returnType)) return cls;
        
        RDFUberType type = RDFTypes.getType(returnType);
        if(type != null) {
            if(RDFTypes.OBJECT.equals(type)) {
                cls = java.lang.Object.class; // special casing this cuz I can't remember in which maps, lists etc this should go
            }
            else {
                cls = (Class) RDF_2_PRIMITIVE_ARG_MAP.get(type);
            }
        }
        else {
            Ontology o = rf.getOntology();
            Entity e = o.getEntity(rf.getReturnType());
            if(e instanceof Concept) cls = (Class) RDF_2_PRIMITIVE_ARG_MAP.get(RDFTypes.BASE_CONCEPT);
            else if(e instanceof Event) cls = (Class) RDF_2_PRIMITIVE_ARG_MAP.get(RDFTypes.BASE_EVENT);
            else {
                assert(false);
                cls = null; // shouldn't happen
            }
        }

        return cls;
    }

    /**
     *
     * @return a Class[]
     */
    public Class[] getThrownExceptions() {
        return new Class[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     *
     * @return a Class[]
     */
    public Class[] getArguments() {
        putArgs(false);
        return parameterTypes;
    }

    /**
     *
     * @return a String
     */
    public String code() {
        return this.getModelClass() + "." + javaFunctionName;
    }

    /**
     * 
     * @return a String
     */ 
    public String getModelClass() {
        return ModelNameUtil.ruleFnClassFSName((RuleFunction)model);
    }
    
    /**
     *
     * @return a boolean
     */
    public boolean isValidInCondition() {
        RuleFunction rf = (RuleFunction) model;
        return (rf.getValidity() != RuleFunction.Validity.ACTION);

    }



    /**
     *
     * @return a boolean
     */
    public boolean isTimeSensitive() {
        return false;
    }

    /**
     *
     * @return a boolean
     */
    public boolean requiresAsync() {
        return false;
    }

    /**
     *
     * @return a String
     */
    public String getDocumentation() {
        return code();
    }

    /**
     *
     * @return a boolean
     */
    public boolean doesModify() {
        return !isValidInCondition();
    }

    public Entity[] getEntityArguments() {
        putArgs(false);
        return entityArguments;
    }

    public Entity getEntityReturnType() {
        RuleFunction rf = (RuleFunction) model;

        String retType = rf.getReturnType();
        if(retType == null || !retType.startsWith(String.valueOf(Folder.FOLDER_SEPARATOR_CHAR))) return null;

        Ontology o = rf.getOntology();
        return o.getEntity(retType);
    }
}
