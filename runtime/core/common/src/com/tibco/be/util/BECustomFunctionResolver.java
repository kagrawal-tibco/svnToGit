package com.tibco.be.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;

import com.tibco.xml.xquery.FunctionGroup;
import com.tibco.xml.xquery.extensions.JavaDispatch;
import com.tibco.xml.xquery.extensions.JavaFunctionGroup;
import com.tibco.be.model.functions.BEFunction;
import com.tibco.be.model.functions.FunctionParamDescriptor;
import com.tibco.be.model.functions.FunctionsCatalogVisitor;
import com.tibco.be.model.functions.FunctionsCategory;
import com.tibco.be.model.functions.Predicate;
import com.tibco.be.model.functions.impl.FunctionsCatalog;
import com.tibco.be.model.functions.impl.JavaStaticFunction;
import com.tibco.be.model.functions.impl.ModelJavaFunction;
import com.tibco.be.util.BECustomFunctionHelper.ParamTypeInfo;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.mapper.xml.xdata.xpath.FunctionNamespace;
import com.tibco.cep.mapper.xml.xdata.xpath.FunctionResolver;
import com.tibco.cep.mapper.xml.xdata.xpath.func.DefaultFunctionNamespace;
import com.tibco.cep.mapper.xml.xdata.xpath.func.InvokeJavaCustomMethodXFunction;
import com.tibco.cep.mapper.xml.xdata.xpath.func.InvokeJavaMethodXFunction;
import com.tibco.cep.runtime.model.element.Concept;

public class BECustomFunctionResolver implements FunctionResolver {

	private JavaFunctionGroup customGroup = new JavaFunctionGroup();
	private HashMap<String, DefaultFunctionNamespace> namespaces = new HashMap<String, DefaultFunctionNamespace>();
	private FunctionsCatalog registry;
    private static Logger logger;

    // Initialize this class //
    static {
        logger = LogManagerFactory.getLogManager().getLogger(BECustomFunctionResolver.class);
    }
    
	public BECustomFunctionResolver(FunctionsCatalog customRegistry) {
		this.registry = customRegistry;
		init();
	}
	public static final String BE_CUSTOM_NAMESPACE = "www.tibco.com/be/custom/";
	
	private void init() {
		if (registry == null) {
			return;
		}
		try {
			Iterator catalogNames = registry.catalogNames();
			while (catalogNames.hasNext()) {
				String catName = (String) catalogNames.next();
				FunctionsCategory catalog = registry.getCatalog(catName);
				if (catalog == null) {
					return;
				}
				processCustomCatalog(catalog);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	protected void processCustomCatalog(FunctionsCategory catalog) {
		catalog.accept(new FunctionsCatalogVisitor() {
			
			@Override
			public boolean visit(FunctionsCategory category) {
				if (!category.allFunctions().hasNext()) {
					return true;
				}
				String funcName = category.getName().getExpandedForm();
				funcName = funcName.replaceAll("\\{", "");
				funcName = funcName.replaceAll("\\}", "-");
				funcName = funcName.replaceAll(" ", "");
				funcName = funcName.replaceAll("\\.", "-");
				DefaultFunctionNamespace ns = namespaces.get(funcName);
				if (ns == null) {
					String funcNS = BE_CUSTOM_NAMESPACE+funcName;
					ns = new DefaultFunctionNamespace(funcNS);
					ns.setBuiltIn(false);
					ns.setSuggestedPrefix(funcName);
					namespaces.put(funcNS, ns);
				}
				
				Iterator<Predicate> allFunctions = category.allFunctions();
				while (allFunctions.hasNext()) {
					Predicate predicate = (Predicate) allFunctions.next();
					if (predicate instanceof JavaStaticFunction) {
						registerPredicate(ns, (JavaStaticFunction) predicate);
					} else if (predicate instanceof ModelJavaFunction) {
						processModelJavaFunction(funcName, ns, (ModelJavaFunction)predicate);
					}
				}
				return true;
			}
		});
	}

	private void processModelJavaFunction(String funcName, DefaultFunctionNamespace fnNamespace,
			ModelJavaFunction modelFn) {
		BEFunction fnAnnotation = modelFn.getAnnotation();
		if (fnAnnotation == null)
			return;
		if (!fnAnnotation.enabled().value())
			return;
		String fnName = fnAnnotation.name();
		FunctionInfo fnInfo = new FunctionInfo();
		fnInfo.setName(fnName);
		fnInfo.setDomain(fnAnnotation.fndomain());
		fnInfo.setDeprecated(fnAnnotation.deprecated().value());
		fnInfo.setMapperEnabled(fnAnnotation.mapper().enabled().value());
		for (FunctionParamDescriptor param : fnAnnotation.params()) {
			ParamTypeInfo paramInfo = new BECustomFunctionHelper().new ParamTypeInfo();
			paramInfo.setName(param.name());
			paramInfo.setTypeClassName(param.type());
			fnInfo.getArgs().add(paramInfo);
		}
		ParamTypeInfo rtInfo = new BECustomFunctionHelper().new ParamTypeInfo();
		rtInfo.setTypeClassName(fnAnnotation.freturn().type());
		fnInfo.setReturnType(rtInfo);

		registerJavaModelFunction(funcName, fnNamespace, fnInfo);
	}
	
    private void registerJavaModelFunction(String category, DefaultFunctionNamespace ns, FunctionInfo fnInfo) {
//    	if (!isValidMapperFunction(method)) {
//    		logger.log(Level.TRACE, "Skipping registration of custom function '"+method.getName()+"' for use in the mapper.  Mapper functions cannot return the type "+method.getReturnType().getCanonicalName());
//    		return;
//    	}
    	InvokeJavaCustomMethodXFunction javaXFunc = new InvokeJavaCustomMethodXFunction(ns.getNamespaceURI(), fnInfo);
//    	javaXFunc.setFunctionNamespace(ns);
    	try {
    		javaXFunc.generateDragString("BE");
    	} catch (Exception e) {
    		logger.log(Level.TRACE, "Skipping registration of custom function '"+fnInfo.getFnName()+"' for use in the mapper.  Reason: "+e.getMessage());
    		return;
    	}
    	try {
    		javaXFunc.getReturnType(fnInfo);
    	} catch (Exception e) {
    		logger.log(Level.TRACE, "Skipping registration of custom function '"+fnInfo.getFnName()+"' for use in the mapper.  Reason: "+e.getMessage());
    		return;
    	}
		ns.add(javaXFunc);
		Class<?> clazz = fnInfo.getClass().getDeclaringClass();
		JavaDispatch jd = new JavaDispatch(clazz);
		customGroup.addJavaNamespace(ns.getNamespaceURI(), jd);		
    }
    
	private void registerPredicate(DefaultFunctionNamespace ns, JavaStaticFunction predicate) {
		Method method = ((JavaStaticFunction)predicate).getMethod();
		if (!isValidMapperFunction(method)) {
			logger.log(Level.TRACE, "Skipping registration of custom function '"+method.getName()+"' for use in the mapper.  Mapper functions cannot return the type "+method.getReturnType().getCanonicalName());
			return;
		}
		InvokeJavaMethodXFunction javaXFunc = new InvokeJavaMethodXFunction(ns.getNamespaceURI(), method);
		try {
			javaXFunc.generateDragString("BE");
		} catch (Exception e) {
			logger.log(Level.TRACE, "Skipping registration of custom function '"+method.getName()+"' for use in the mapper.  Reason: "+e.getMessage());
			return;
		}
		try {
			javaXFunc.getReturnType();
		} catch (Exception e) {
			logger.log(Level.TRACE, "Skipping registration of custom function '"+method.getName()+"' for use in the mapper.  Reason: "+e.getMessage());
			return;
		}
		ns.add(javaXFunc);
		Class<?> clazz = method.getDeclaringClass();
		JavaDispatch jd = new JavaDispatch(clazz);
		customGroup.addJavaNamespace(ns.getNamespaceURI(), jd);		
	}

	private boolean isValidMapperFunction(Method method) {
		Class<?> returnType = method.getReturnType();
		if (returnType.isArray()) {
			return false; // ? really?
		}
		if (returnType.equals(Object.class)) {
			return false;
		}
		if (returnType.equals(void.class)) {
			return false;
		}
		if (returnType.equals(Concept.class)) {
			return false;
		}
		if (returnType.equals(Object[].class)) {
			return false;
		}
		if (returnType.equals(Concept[].class)) {
			return false;
		}
		return true;
	}

	@Override
	public FunctionGroup getAsFunctionGroup() {
		return customGroup;
	}

	@Override
	public FunctionNamespace getNamespace(String namespaceURI) {
		return namespaces.get(namespaceURI);
	}

	@Override
	public Iterator getNamespaces() {
		return namespaces.values().iterator();
	}

}
