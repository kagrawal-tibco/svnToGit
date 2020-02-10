package com.tibco.cep.query.model.impl;

import com.tibco.be.model.functions.FunctionsCategory;
import com.tibco.be.model.functions.Predicate;
import com.tibco.be.model.functions.impl.FunctionsCatalog;
import com.tibco.be.model.functions.impl.JavaStaticFunction;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.query.model.*;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.TypeManager.TypeDescriptor;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import org.antlr.runtime.tree.CommonTree;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class FunctionRegistryImpl
        extends AbstractRegistryContext
        implements FunctionRegistry {

    public static NamedContextId CTX_ID = new NamedContextId() {
        public String toString() { return "FUNCTION_REGISTRY"; }
    };

    private static final String catalog_path_separator = "/";
    protected boolean isResolved;

    public static Map<String,Class> AGGREGATE_FUNCTION_MAP = new HashMap<String,Class>();

    static {
         AGGREGATE_FUNCTION_MAP.put(Function.AGGREGATE_FUNCTION_PENDING_COUNT, null);
         AGGREGATE_FUNCTION_MAP.put(Function.AGGREGATE_FUNCTION_COUNT, com.tibco.cep.query.stream.impl.aggregate.CountAggregator.class);
         AGGREGATE_FUNCTION_MAP.put(Function.AGGREGATE_FUNCTION_AVG, com.tibco.cep.query.stream.impl.aggregate.AverageAggregator.class);
         AGGREGATE_FUNCTION_MAP.put(Function.AGGREGATE_FUNCTION_MIN, com.tibco.cep.query.stream.impl.aggregate.MinMaxAggregator.class);
         AGGREGATE_FUNCTION_MAP.put(Function.AGGREGATE_FUNCTION_MAX, com.tibco.cep.query.stream.impl.aggregate.MinMaxAggregator.class);
         AGGREGATE_FUNCTION_MAP.put(Function.AGGREGATE_FUNCTION_SUM, com.tibco.cep.query.stream.impl.aggregate.DoubleSumAggregator.class);
    }

    public FunctionRegistryImpl(ModelContext parentContext, CommonTree tree) {
        super(parentContext);
    }

    public boolean resolveContext() throws Exception {
        return this.resolveContext(true);
    }

    @Override
    public boolean resolveContext(
            boolean isQueryContext)
            throws Exception {

        this.getContextMap().clear();
        RuleServiceProvider rsp = this.getProjectContext().getRuleServiceProvider();
        Ontology ontology = this.getProjectContext().getOntology();
        TypeManager tm = rsp.getTypeManager();
        int count=0;
        for (Iterator it = tm.getTypeDescriptors(TypeManager.TYPE_RULEFUNCTION).iterator(); it.hasNext(); count++) {
            final TypeDescriptor td = (TypeDescriptor) it.next();
            Class implClass = td.getImplClass();
            if(!RuleFunction.class.isAssignableFrom(implClass)) {
                continue;
            }
            com.tibco.cep.designtime.model.rule.RuleFunction drf = ontology.getRuleFunction(td.getURI());

            if (isQueryContext
                    && (drf.getValidity() != com.tibco.cep.designtime.model.rule.RuleFunction.Validity.QUERY)) {
                continue;
            }

            RuleFunction rf = (RuleFunction) implClass.newInstance();
            RuleFunction.ParameterDescriptor[] pd = rf.getParameterDescriptors();

            final FunctionImpl fn = new FunctionImpl(this, null,
                    td.getExpandedName().getLocalName(), td.getURI(),
                    td.getImplClass(),
                    Function.FunctionType.FUNCTION_TYPE_RULE);
            for (int j = 0; j < pd.length; j++) {
                if (j == pd.length - 1) {
                    // this is the return value descriptor
                    // function return types should be java primitives and ontology runtime types
                    fn.setTypeInfo(new TypeInfoImpl(pd[j].getType()));
                }
                else {
                    // this is an argument
                    final FunctionArg farg = new FunctionArgImpl(fn, null,
                            pd[j].getName(), new TypeInfoImpl(pd[j].getType()));
                }
            }
            //rsp.getLogger().logDebug("Registering function :"+fn.getPath());
            this.getContextMap().put(fn.getPath(), fn);
        }
        this.logger.log(Level.DEBUG, "Registered %d rule functions", count);

        Map<String,Predicate> catalogFnMap = getCatalogFunctionMap();
        int fnCount = 0;
        for (final String element : catalogFnMap.keySet()) {
            Predicate function = catalogFnMap.get(element);
            Class implClass;
            if (function instanceof JavaStaticFunction) {
                implClass = ((JavaStaticFunction) function).getMethod().getDeclaringClass();
            }
            else {
                implClass = null;
            }

            if (function.isValidInQuery() || !isQueryContext) {
                final FunctionImpl fn = new FunctionImpl(this, null,
                        function.getName().getLocalName(), element, implClass,
                        Function.FunctionType.FUNCTION_TYPE_CATALOG);
                fn.setTypeInfo(new TypeInfoImpl(function.getReturnClass()));
                Class[] exceptions = function.getThrownExceptions();
                for (int i = 0; i < exceptions.length; i++) {
                    fn.addExceptions(exceptions[i]);
                }
                Class[] params = function.getArguments();
                for (int i = 0; i < params.length; i++) {
                    fn.addFunctionArgument(fn.nextIdentifier().toString(), params[i]);
                }
                this.getContextMap().put(fn.getPath(), fn);
                fnCount++;
                //rsp.getLogger().logDebug("Registering function :"+fn.getPath());
            }
        }
        this.logger.log(Level.DEBUG, "Registered %d catalog function%s out of %d.",
                fnCount, ((fnCount > 1) ? "s" : ""), catalogFnMap.size());

        if (isQueryContext) {
            // Add Aggregate Functions
            final TypeInfo typeInfoForObject = new TypeInfoImpl(Object.class);
            for (int f = 0; f < Function.AGGREGATE_FUNCTION_NAMES.length; f++) {
                String path = Function.AGGREGATE_FUNCTION_NAMES[f];
                if (!path.startsWith(Function.PATH_SEPARATOR)) {
                    path = Function.PATH_SEPARATOR + path;
                }
                final Class implClass = AGGREGATE_FUNCTION_MAP.get(Function.AGGREGATE_FUNCTION_NAMES[f]);
                final FunctionImpl fn = new FunctionImpl(this, null,
                        Function.AGGREGATE_FUNCTION_NAMES[f], path, implClass,
                        Function.FunctionType.FUNCTION_TYPE_AGGREGATE);

//              RuntimeExpression rte = new RuntimeExpressionImpl(fn, null);
                fn.addFunctionArgument(fn.nextIdentifier().toString(), typeInfoForObject);
                fn.addExceptions(Exception.class);

                // min/max function can work on comparable data types
                if (Function.AGGREGATE_FUNCTION_MIN.equals(Function.AGGREGATE_FUNCTION_NAMES[f]) ||
                        Function.AGGREGATE_FUNCTION_MAX.equals(Function.AGGREGATE_FUNCTION_NAMES[f])) {
                    fn.setTypeInfo(new TypeInfoImpl(Comparable.class));
                }
                else {
                    // count,avg,sum always return a Number value
                    fn.setTypeInfo(new TypeInfoImpl(Number.class));
                }

                this.getContextMap().put(fn.getPath(), fn);
            }
            this.logger.log(Level.DEBUG, "Registered %d aggregate functions", Function.AGGREGATE_FUNCTION_NAMES.length);
        }

        this.isResolved = true;

        return this.isResolved();
    }


    private Map<String,Predicate> getCatalogFunctionMap() throws Exception {
        FunctionsCatalog fncatalog = FunctionsCatalog.getINSTANCE();
        final Map<String,Predicate> fnMap = new LinkedHashMap<String,Predicate>();
        for (Iterator iter = fncatalog.catalogNames(); iter.hasNext();) {
            String fncatName = (String) iter.next();
            FunctionsCategory fncat = fncatalog.getCatalog(fncatName);
            final String fnCatalogPath = "";
            for (Iterator nodes = fncat.all(); nodes.hasNext();) {
                Object element = (Object) nodes.next();
                if(element instanceof FunctionsCategory) {
                    recurseFunctionCategories((FunctionsCategory) element, fnCatalogPath, fnMap);
                } else if(element instanceof Predicate) {
                    final String functionName = fnCatalogPath +((Predicate) element).getName().getLocalName();
                    fnMap.put(functionName, (Predicate) element);
                }
            }
        }
        return fnMap;
    }


    /**
     * Recursively walk the function catalog to get the functions and their category path
     * All functions are represented in the following notation Class /CatA/CatB/functionA(arg1,arg2,...)
     * @param cat
     * @param parentCatalog
     * @param fnMap
     */
    private void recurseFunctionCategories(FunctionsCategory cat, String parentCatalog, Map fnMap) {
        final String fnCatalogPath= parentCatalog + catalog_path_separator + cat.getName().getLocalName();
        for(Iterator iter = cat.all();iter.hasNext();) {
            Object element = (Object) iter.next();
            if(element instanceof FunctionsCategory) {
                recurseFunctionCategories((FunctionsCategory) element, fnCatalogPath, fnMap);
            } else if(element instanceof Predicate) {
                String functionName = fnCatalogPath + catalog_path_separator
                        + ((Predicate) element).getName().getLocalName();
                fnMap.put(functionName,(Predicate) element);
            }
        }

    }
    /**
     * Returns the name of the named context
     * @return String
     */
    public NamedContextId getContextId() {
        return CTX_ID;
    }

    public int getContextType() {
        return ModelContext.CTX_TYPE_FUNCTION_REGISTRY;
    }


    public Function getFunctionByPath(String path) {
        return (Function) this.getContextMap().get(path);
    }


    public Function[] getAggregateFunctions() {
        return (Function[]) this.getDescendantContextsByType(this, ModelContext.CTX_TYPE_AGGREGATE_FUNCTION);
    }


    public Function[] getCatalogFunctions() {
        return (Function[]) this.getDescendantContextsByType(this, ModelContext.CTX_TYPE_CATALOG_FUNCTION);
    }


    public Function[] getRuleFunctions() {
        return (Function[]) this.getDescendantContextsByType(this, ModelContext.CTX_TYPE_RULE_FUNCTION);
    }


    public boolean isResolved() {
       return this.isResolved;
   }




//	public Method resolveMethod(String classNameAlias, String methodName,
//			Class[] paramTypes) throws EngineImportException {
//		Class clazz = null;
//		try {
//			clazz = resolveClass(classNameAlias);
//		} catch (ClassNotFoundException e) {
//			throw new EngineImportException("Could not load class by name '"
//					+ classNameAlias + "' ", e);
//		}
//
//		try {
//			return StaticMethodResolver.resolveMethod(clazz, methodName,
//					paramTypes);
//		} catch (NoSuchMethodException e) {
//			throw new EngineImportException("Could not find method named '"
//					+ methodName + "' in class '" + classNameAlias + "' ", e);
//		}
//	}

    /**
     * Finds a class by class name using the auto-import information provided.
     *
     * @param className
     *            is the class name to find
     * @return class
     * @throws ClassNotFoundException
     *             if the class cannot be loaded
     */
//	protected Class resolveClass(String classGetter)
//			throws ClassNotFoundException {
//		// Attempt to retrieve the class with the name as-is
//		try {
//			return Class.forName(classGetter);
//		} catch (ClassNotFoundException e) {
//		}
//
//		// Try all the imports
//		for (String importName : imports) {
//			boolean isClassName = isClassName(importName);
//
//			// Import is a class name
//			if (isClassName) {
//				if (importName.endsWith(classGetter)) {
//					return Class.forName(importName);
//				}
//			} else {
//				// Import is a package name
//				String prefixedClassName = getPackageName(importName) + '.'
//						+ classGetter;
//				try {
//					return Class.forName(prefixedClassName);
//				} catch (ClassNotFoundException e) {
//				}
//			}
//		}
//
//		// No import worked, the class isn't resolved
//		throw new ClassNotFoundException("Unknown class " + classGetter);
//	}


}
