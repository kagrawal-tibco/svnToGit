package com.tibco.cep.bpmn.runtime.activity.tasks;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewConstructor;
import javassist.CtNewMethod;
import javassist.LoaderClassPath;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtension;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.runtime.activity.InitContext;
import com.tibco.cep.bpmn.runtime.activity.Task;
import com.tibco.cep.bpmn.runtime.activity.TaskResult;
import com.tibco.cep.bpmn.runtime.activity.results.DefaultResult;
import com.tibco.cep.bpmn.runtime.activity.results.ExceptionResult;
import com.tibco.cep.bpmn.runtime.agent.Job;
import com.tibco.cep.bpmn.runtime.model.JobContext;
import com.tibco.cep.bpmn.runtime.utils.ModelTypeHelper;
import com.tibco.cep.bpmn.runtime.utils.TaskFunctionModel;
import com.tibco.cep.bpmn.runtime.utils.Variables;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.service.loader.BEClassLoader;
import com.tibco.cep.runtime.session.RuleServiceProvider;

public class ProcessJavaTask extends AbstractTask implements Task {
	private static ThreadLocal<JobContext> currentJobContext  = new ThreadLocal<>();
	private static final String INIT_METHOD_PREFIX = "_initInjectable";
	private Class javaTaskClass;
	private Method javaTaskMethod;
	private Map<TaskContextType, Object> injectionInitContext;
	private BEClassLoader cldr;
	private Method initTaskMethod;
	private TaskFunctionModel functionModel;
	
	


	/* (non-Javadoc)
	 * @see com.tibco.cep.bpmn.runtime.activity.tasks.AbstractTask#init(com.tibco.cep.bpmn.runtime.activity.InitContext, java.lang.Object[])
	 */
	@Override
	public void init(InitContext context, Object... args) throws Exception {
		super.init(context, args);
		EObjectWrapper<EClass, EObject> flowNodeWrapper = EObjectWrapper.wrap(getTaskModel().getEInstance());
		EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(flowNodeWrapper);
		String javaFileResource = (String) valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JAVA_FILE_PATH);
		String javaPackage = (String) valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JAVA_PACKAGE);
		String javaTaskClassName = getClassName(javaFileResource, javaPackage);
		Ontology ontology = getInitContext().getProcessModel().getOntology();
		RuleServiceProvider rsp = getInitContext().getProcessAgent().getRuleServiceProvider();
		this.cldr = (BEClassLoader) rsp.getClassLoader();
		this.injectionInitContext = new HashMap<TaskContextType, Object>();
		this.initTaskMethod = injectTaskIOC(javaTaskClassName, cldr, injectionInitContext);
		this.javaTaskClass = this.initTaskMethod.getDeclaringClass();
		String methodName = valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_METHOD_NAME); //TODO: replace with method name
		EList<EObject> fnArgSymbols = valueWrapper.getListAttribute(BpmnMetaModelExtensionConstants.E_ATTR_METHOD_ARGUMENTS); //TODO: replace with a method arg symbols
		EObject fnReturnSymbol = valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_METHOD_RETURN_TYPE); //TODO: replace with method Return Symbol 
		this.functionModel = initFunctionModel(methodName,fnArgSymbols,fnReturnSymbol);
		this.javaTaskMethod = getMethod(javaTaskClass, functionModel);
		if(this.javaTaskMethod == null)
			throw new java.lang.IllegalStateException(String.format("Java Task method %s not found in class :%s", methodName,javaTaskClassName));
	}

	/**
	 * @param methodName
	 * @param fnArgSymbols
	 * @param fnReturnSymbol
	 * @return
	 */
	private TaskFunctionModel initFunctionModel(String methodName, EList<EObject> fnArgSymbols, EObject fnReturnSymbol) {
		TaskFunctionModel tfm = new TaskFunctionModel();
		tfm.setName(methodName);
		
		TaskFunctionModel.TypeSymbol rSym = new TaskFunctionModel.TypeSymbol();
		EObjectWrapper<EClass, EObject> wrSym = EObjectWrapper.wrap(fnReturnSymbol);
		rSym.setName((String) wrSym.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_ID_NAME));
		rSym.setArray((boolean) wrSym.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_IS_ARRAY));
		boolean isPrimitive = (boolean) wrSym.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_IS_PRIMITIVE);
		rSym.setPrimitive(isPrimitive);
		rSym.setUri((String) wrSym.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_ENTITY_PATH));
		EEnumLiteral typeLit = wrSym.getEnumAttribute(BpmnMetaModelExtensionConstants.E_ATTR_TYPE);
		rSym.setPropertyType(ModelTypeHelper.getPropertyType(typeLit,isPrimitive));
		tfm.setReturnType(rSym);
		for(EObject eObj:fnArgSymbols) {
			EObjectWrapper<EClass, EObject> argSym = EObjectWrapper.wrap(eObj);
			TaskFunctionModel.TypeSymbol aSym = new TaskFunctionModel.TypeSymbol();
			aSym.setName((String) argSym.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_ID_NAME));
			aSym.setArray((boolean) argSym.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_IS_ARRAY));
			isPrimitive = (boolean) argSym.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_IS_PRIMITIVE);
			aSym.setPrimitive(isPrimitive);
			aSym.setUri((String) argSym.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_ENTITY_PATH));
			typeLit = argSym.getEnumAttribute(BpmnMetaModelExtensionConstants.E_ATTR_TYPE);
			aSym.setPropertyType(ModelTypeHelper.getPropertyType(typeLit,isPrimitive));
			tfm.getArgTypes().add(aSym);
		}
		return tfm;
	}
	
	
	public TaskFunctionModel getFunctionModel() {
		return functionModel;
	}

	

	/**
	 * @param jtClazz
	 * @param fnModel
	 * @return
	 */
	private Method getMethod(Class jtClazz, TaskFunctionModel fnModel	) {
		Method[] methods = jtClazz.getMethods();
		Method retVal = null;
		for (Method m : methods) {
			
			int counter = 0;
			if (m.getName().equals(fnModel.getName())) {
				Class<?>[] ptypes = m.getParameterTypes();
				Annotation[][] panns = m.getParameterAnnotations();
				List<TaskFunctionModel.TypeSymbol> atypes = fnModel.getArgTypes();
				int i=0;
				boolean match = true;
				for(Class<?>p:ptypes) {
					TaskFunctionModel.TypeSymbol at = atypes.get(i);
					TaskFunctionModel.TypeSymbol pt = new TaskFunctionModel.TypeSymbol();
					pt.setArray(false);
					pt.setPrimitive(false);
					Class<?> cType=p;
					if(cType.isArray()) {
						pt.setArray(true);;
						cType=p.getComponentType();
					} 
					if(cType.isPrimitive()){
						pt.setPrimitive(true);
					} 
					boolean isContainedConcept = false;
					boolean isProcess = false;
					ModelTypeMap mtm = null;
					if(panns[i]!= null && panns[i][0] != null) {
						 mtm = (ModelTypeMap) panns[i++][0];
					}
					if (mtm != null) {
						pt.setUri(mtm.uri());
						if (mtm.type().equals(ModelType.CONTAINED_CONCEPT)) {
							isContainedConcept = true;
						}
						if (mtm.type().equals(ModelType.PROCESS)) {
							isProcess = true;
						}
					}
					
					if(int.class.isAssignableFrom(cType)){
						pt.setPropertyType(TaskFunctionModel.PROPERTY_TYPE.INTEGER);
					} else if(Integer.class.isAssignableFrom(cType)){
						pt.setPropertyType(TaskFunctionModel.PROPERTY_TYPE.INTEGER_WRAP);
					} else if(long.class.isAssignableFrom(cType)){
						pt.setPropertyType(TaskFunctionModel.PROPERTY_TYPE.LONG);
					} else if(Long.class.isAssignableFrom(cType)){
						pt.setPropertyType(TaskFunctionModel.PROPERTY_TYPE.LONG_WRAP);
					} else if(boolean.class.isAssignableFrom(cType)){
						pt.setPropertyType(TaskFunctionModel.PROPERTY_TYPE.BOOLEAN);
					} else if(Boolean.class.isAssignableFrom(cType)){
						pt.setPropertyType(TaskFunctionModel.PROPERTY_TYPE.BOOLEAN_WRAP);
					} else if(double.class.isAssignableFrom(cType)){
						pt.setPropertyType(TaskFunctionModel.PROPERTY_TYPE.DOUBLE);
					} else if(Double.class.isAssignableFrom(cType)){
						pt.setPropertyType(TaskFunctionModel.PROPERTY_TYPE.DOUBLE_WRAP);
					} else if(String.class.isAssignableFrom(cType) ){
						pt.setPropertyType(TaskFunctionModel.PROPERTY_TYPE.STRING);
					}else if( com.tibco.be.util.calendar.Calendar.class.isAssignableFrom(cType)){
						pt.setPropertyType(TaskFunctionModel.PROPERTY_TYPE.DATE_TIME);
					} else if(Calendar.class.isAssignableFrom(cType)){
						pt.setPropertyType(TaskFunctionModel.PROPERTY_TYPE.DATE_TIME);
					} else if(Concept.class.isAssignableFrom(cType) && isProcess){
						pt.setPropertyType(TaskFunctionModel.PROPERTY_TYPE.BASE_PROCESS);
					} else if(Concept.class.isAssignableFrom(cType) && isContainedConcept){
						pt.setPropertyType(TaskFunctionModel.PROPERTY_TYPE.CONCEPT);
					} else if(Concept.class.isAssignableFrom(cType) && !isContainedConcept){
						pt.setPropertyType(TaskFunctionModel.PROPERTY_TYPE.CONCEPT_REFERENCE);
					} 
					if(!at.equals(pt)){
						match=false;
						break;
					}
					
				}
				if(match) {
					return m;
				}
			}
		}
		return null;
	}

	
	/**
	 * @param javaFileResource
	 * @param javaPackage
	 * @return
	 */
	private String getClassName(final String javaFileResource, final String javaPackage) {
		String fileName = new String(javaFileResource);
		String[] parts = fileName.split("[\\\\/]");
		
		String packageName = "";
		if (javaPackage != null && !javaPackage.equals("null")) {
			packageName = javaPackage.endsWith(".") ? javaPackage.substring(0, javaPackage.length() - 1) : javaPackage;
		}
		 
		return String.format(packageName.isEmpty()? "%s%s":"%s.%s", packageName, parts[parts.length - 1]);
	}

	/**
	 * @param sourceClass
	 * @param cldr
	 * @param ctx
	 * @return
	 * @throws Exception
	 */
	public Method injectTaskIOC(String sourceClass, final BEClassLoader cldr, Map<TaskContextType, Object> ctx) throws Exception {
		final ClassPool pool = ClassPool.getDefault();
		pool.appendClassPath(new LoaderClassPath(cldr));
		pool.appendSystemPath();
		pool.get(TaskContextType.class.getName());
		pool.get(HashMap.class.getName());
		pool.get(Map.class.getName());

		final CtClass srcClazz = pool.get(sourceClass);

		if (srcClazz.isFrozen()) {
			srcClazz.defrost();
		}

		final CtField[] fields = srcClazz.getFields();
		final List<CtClass> params = new ArrayList<>();
		final StringBuilder ccSrc = new StringBuilder();
		ccSrc.append("public void").append(" ").append(INIT_METHOD_PREFIX).append("( java.util.Map ctx").append(")");
		ccSrc.append("{\n");
		for (CtField field : fields) {
			if (field.hasAnnotation(JavaTaskContext.class)) {
				JavaTaskContext fAnn = (JavaTaskContext) field.getAnnotation(JavaTaskContext.class);
				TaskContextType ctxType = fAnn.value();
				ccSrc.append("this.").append(field.getName()).append("=").append("(").append(ctxType.getClassType().getName()).append(")").append("ctx")
						.append(".get(").append(TaskContextType.class.getName()).append(".").append(ctxType.name()).append(");\n");
				switch (ctxType) {
				case NAME:
					ctx.put(ctxType, this.getName());
					break;
				case RULE_SERVICE_PROVIDER:
					ctx.put(ctxType, this.getInitContext().getProcessAgent().getRuleServiceProvider());
					break;
				case RULE_SESSION:
					ctx.put(ctxType, this.getInitContext().getProcessAgent().getRuleSession());
					break;

				}

			}

		} // end fields
		ccSrc.append("}\n");

		CtClass injClazz = null;
		String initMethodName = INIT_METHOD_PREFIX;

		injClazz = pool.makeClass(sourceClass + "_" + getName().replaceAll("\\.", "_") );
		injClazz.setModifiers(Modifier.PUBLIC);
		CtConstructor ctr = CtNewConstructor.defaultConstructor(injClazz);
		ctr.setModifiers(Modifier.PUBLIC);
		injClazz.addConstructor(ctr);

		injClazz.setSuperclass(srcClazz);

		CtMethod initMethod = CtNewMethod.make(ccSrc.toString(), injClazz);
		injClazz.addMethod(initMethod);
		injClazz.toClass(cldr, cldr.getClass().getProtectionDomain());
		Class<?> userClass = cldr.loadClass(injClazz.getName());
		return userClass.getMethod(initMethodName, Map.class);

	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.bpmn.runtime.activity.tasks.AbstractTask#execute(com.tibco.cep.bpmn.runtime.agent.Job, com.tibco.cep.bpmn.runtime.utils.Variables, com.tibco.cep.bpmn.runtime.activity.Task)
	 */
	@Override
	public TaskResult execute(Job context, Variables vars, Task loopTask) {
		TaskResult result = null;
		try {
			setCurrentJobContext(context.getJobContext());
			// call the default constructor	
			Object jTask = this.javaTaskClass.newInstance(); 
																
			Map<TaskContextType, Object> localContext = new HashMap<TaskContextType, Object>();
			localContext.putAll(this.injectionInitContext);
			// call the injection init method with local context
			this.initTaskMethod.invoke(jTask, localContext); 
			
			
            List<Object> args = new ArrayList<>();
            int i=0;
            Class<?>[] parameterTypes = this.javaTaskMethod.getParameterTypes();
            for(TaskFunctionModel.TypeSymbol s: this.functionModel.getArgTypes()){
                args.add(ModelTypeHelper.getTypedValue(vars, s,parameterTypes[i++]));
            }
            
            Object obj = this.javaTaskMethod.invoke(jTask, args.toArray());

            result =  new DefaultResult(TaskResult.Status.OK,  obj);

		} catch (Throwable e) {
			result = new ExceptionResult(e);
		} finally {
			setCurrentJobContext(null);
		}
		return result;
	}
	
	public static void setCurrentJobContext(JobContext currentJavaContext) {
		ProcessJavaTask.currentJobContext.set(currentJavaContext);
	}
	
	public static JobContext getCurrentJobContext() {
		return currentJobContext.get();
	}
	
	
	
	

}
