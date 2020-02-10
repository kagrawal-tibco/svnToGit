package com.tibco.be.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

import com.tibco.be.model.functions.BEFunction;
import com.tibco.be.model.functions.FunctionParamDescriptor;
import com.tibco.be.model.functions.FunctionsCatalogVisitor;
import com.tibco.be.model.functions.FunctionsCategory;
import com.tibco.be.model.functions.Predicate;
import com.tibco.be.model.functions.impl.FunctionsCatalog;
import com.tibco.be.model.functions.impl.JavaStaticFunction;
import com.tibco.be.model.functions.impl.ModelJavaFunction;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.mapper.xml.xdata.xpath20.InvokeJavaCustomMethodXFunction;
import com.tibco.cep.mapper.xml.xdata.xpath20.InvokeJavaMethodXFunction;
import com.tibco.cep.mapper.xml.xdata.xpath20.JavaCustomFunctionEntry;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.xml.xmodel.XsltVersion;
import com.tibco.xml.xmodel.xpath.func.CustomFunctions;
import com.tibco.xml.xmodel.xpath.func.CustomFunctions.CustomFuncEntry;
import com.tibco.xml.xmodel.xpath.func.FunctionNamespace;
import com.tibco.xml.xmodel.xpath.func.FunctionResolver;


public class BECustomFunctionHelper {
	
	public static final String BE_CUSTOM_NAMESPACE = "www.tibco.com/be/custom/";
	private static Logger logger;

    // Initialize this class //
    static {
        logger = LogManagerFactory.getLogManager().getLogger(BECustomFunctionHelper.class);
    }

	public void updateCustomFunctions(FunctionsCatalog customRegistry) {
    	if ("true".equalsIgnoreCase(System.getProperty("xpath.disable.customfunctions", "false"))) {
            return;
    	}
		Iterator<FunctionNamespace> namespaces = FunctionResolver.getInstance(XsltVersion.DEFAULT_VERSION).getNamespaces();
		while (namespaces.hasNext()) {
			FunctionNamespace functionNamespace = (FunctionNamespace) namespaces
					.next();
			if (functionNamespace.getNamespaceURI() != null && functionNamespace.getNamespaceURI().startsWith(BECustomFunctionHelper.BE_CUSTOM_NAMESPACE)) {
				namespaces.remove();
			}
		}
    	try {
    		ArrayList<CustomFuncEntry> customFuncs = CustomFunctions.getInstance().getCustomFuncs();
    		ArrayList<CustomFuncEntry> toBeRemoved = new ArrayList<CustomFunctions.CustomFuncEntry>();
    		for (CustomFuncEntry customFuncEntry : customFuncs) {
				if (customFuncEntry.getNamespace().startsWith(BE_CUSTOM_NAMESPACE)) {
					toBeRemoved.add(customFuncEntry);
				}
			}
    		customFuncs.removeAll(toBeRemoved);
    		updateFunctionNamespaces(customRegistry);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}

	}

	private void updateFunctionNamespaces(
			FunctionsCatalog customRegistry) {
		try {
			Iterator<FunctionsCategory> catalogs = customRegistry.catalogs();
			while (catalogs.hasNext()) {
				FunctionsCategory functionsCategory = (FunctionsCategory) catalogs.next();
				processCustomCatalog(functionsCategory);
			}
		} catch (Exception e) {
			logger.log(Level.ERROR, e.getMessage());
		}
	}

	protected void processCustomCatalog(final FunctionsCategory catalog) {
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
				String catNS = BE_CUSTOM_NAMESPACE + funcName;//category.getName().getExpandedForm();
    			FunctionNamespace fnNamespace = FunctionResolver.getInstance(XsltVersion.DEFAULT_VERSION).getNamespace(catNS);
    			if (fnNamespace == null) {
    				fnNamespace = new FunctionNamespace(catNS);
    				fnNamespace.setSuggestedPrefix("be");
    				fnNamespace.setBuiltIn(false);
    				FunctionResolver.getInstance(XsltVersion.DEFAULT_VERSION).addFunctionNamespace(fnNamespace);
    			}
				
				Iterator<Predicate> allFunctions = category.allFunctions();
				while (allFunctions.hasNext()) {
					Predicate predicate = (Predicate) allFunctions.next();
					try {
						// wrap each call in a try/catch, so that individual methods don't abort the entire catalog registration 
						if (predicate instanceof JavaStaticFunction) {
							registerPredicate(funcName, fnNamespace, ((JavaStaticFunction) predicate).getMethod());
						} else if (predicate instanceof ModelJavaFunction) {
							processModelJavaFunction(funcName, fnNamespace, (ModelJavaFunction) predicate);
						}
					} catch (Exception e) {
						logger.log(Level.TRACE, "Skipping registration of custom function '"+predicate.getName()+"' for use in the mapper.  Reason: "+e.getMessage());
					}
				}
				return true;
			}

			private void processModelJavaFunction(String funcName, FunctionNamespace fnNamespace,
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
					ParamTypeInfo paramInfo = new ParamTypeInfo();
					paramInfo.setName(param.name());
					paramInfo.setTypeClassName(param.type());
					fnInfo.getArgs().add(paramInfo);
				}
				ParamTypeInfo rtInfo = new ParamTypeInfo();
				rtInfo.setTypeClassName(fnAnnotation.freturn().type());
				fnInfo.setReturnType(rtInfo);
				
				String implClass = null;//modelFn.getCategoryInfo().getImplClass();
				registerJavaModelFunction(funcName, fnNamespace, fnInfo);
			}
		});
	}

	public class ParamTypeInfo {

		private String name;
		private String typeName;

		public String getName() {
			return name;
		}

		public String getTypeClassName() {
			return typeName;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setTypeClassName(String type) {
			this.typeName = type;
		}
		
	}
	private void registerPredicate(String category, FunctionNamespace ns, Method method) {
		if (!isValidMapperFunction(method)) {
			logger.log(Level.TRACE, "Skipping registration of custom function '"+method.getName()+"' for use in the mapper.  Mapper functions cannot return the type "+method.getReturnType().getCanonicalName());
			return;
		}
		InvokeJavaMethodXFunction javaXFunc = new InvokeJavaMethodXFunction(ns.getNamespaceURI(), method);
		javaXFunc.setFunctionNamespace(ns);
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
		CustomFunctions.getInstance().getCustomFuncs().add(new JavaCustomFunctionEntry(javaXFunc, category));
	}

    private void registerJavaModelFunction(String category, FunctionNamespace ns, FunctionInfo fnInfo) {
//    	if (!isValidMapperFunction(method)) {
//    		logger.log(Level.TRACE, "Skipping registration of custom function '"+method.getName()+"' for use in the mapper.  Mapper functions cannot return the type "+method.getReturnType().getCanonicalName());
//    		return;
//    	}
    	InvokeJavaCustomMethodXFunction javaXFunc = new InvokeJavaCustomMethodXFunction(ns.getNamespaceURI(), fnInfo);
    	javaXFunc.setFunctionNamespace(ns);
    	try {
    		javaXFunc.generateDragString("BE");
    	} catch (Exception e) {
    		logger.log(Level.TRACE, "Skipping registration of custom function '"+fnInfo.getFnName()+"' for use in the mapper.  Reason: "+e.getMessage());
    		return;
    	}
    	try {
    		javaXFunc.getReturnType();
    	} catch (Exception e) {
    		logger.log(Level.TRACE, "Skipping registration of custom function '"+fnInfo.getFnName()+"' for use in the mapper.  Reason: "+e.getMessage());
    		return;
    	}
    	ns.add(javaXFunc);
    	CustomFunctions.getInstance().getCustomFuncs().add(new JavaCustomFunctionEntry(javaXFunc, category));
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


}
