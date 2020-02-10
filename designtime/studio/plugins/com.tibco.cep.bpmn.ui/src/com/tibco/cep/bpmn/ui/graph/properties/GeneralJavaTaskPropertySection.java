package com.tibco.cep.bpmn.ui.graph.properties;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.ILocalVariable;
import org.eclipse.jdt.core.IMemberValuePair;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.tibco.cep.bpmn.core.index.BpmnIndexUtils;
import com.tibco.cep.bpmn.core.utils.BpmnModelUtils;
import com.tibco.cep.bpmn.core.utils.ECoreHelper;
import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtension;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.ontology.symbols.SymbolEntryImpl;
import com.tibco.cep.bpmn.model.designtime.utils.BpmnCommonIndexUtils;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.runtime.activity.tasks.ModelType;
import com.tibco.cep.bpmn.runtime.utils.ModelTypeHelper;
import com.tibco.cep.bpmn.runtime.utils.TaskFunctionModel.TypeSymbol;
import com.tibco.cep.bpmn.ui.editor.BpmnMessages;
import com.tibco.cep.studio.core.index.model.SharedEntityElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.StudioJavaUtil;
import com.tibco.cep.studio.ui.dialog.StudioFilteredResourceSelectionDialog;
import com.tibco.cep.studio.ui.dialog.StudioResourceSelectionDialog;

public class GeneralJavaTaskPropertySection extends GeneralNodePropertySection {

	private Map<String, IMethod> signMethodMap = new HashMap<String, IMethod>();
	private Map<String, String> displaySignMap = new HashMap<String, String>();

	private List<TypeSymbol> argTypes = new ArrayList<>();

	@SuppressWarnings("unused")
	private boolean isArrayReturnType = false;
	private String returnType = "";
	@SuppressWarnings("unused")
	private String returnTypeURI = "";
	private boolean isPrimitiveReturnType = true;
	public GeneralJavaTaskPropertySection() {
		super();
		
	}

	@Override
	protected PropertyNodeTypeGroup getNodeTypeData() {
		return null;
	}
	@Override
	public void resourceBrowse() {
		StudioFilteredResourceSelectionDialog selectionDialog = getSeletionDialog();
		int status = selectionDialog.open();
		if (status == StudioResourceSelectionDialog.OK) {
			Object element = selectionDialog.getFirstResult();
			String path = "";
			if(element instanceof IFile ) {
				IResource res = (IFile) element;
				path = IndexUtils.getFullPath(res);
				js = BpmnIndexUtils.getJavaResource(path,fProject);			
				path = js.getFullPath();			
				resourceText.setText(path);
				populateMethods();
			} else if (element instanceof SharedEntityElement) {
				path = ((SharedEntityElement) element).getSharedEntity().getFullPath();
				resourceText.setText(path);
			}
		}
	}
	
	protected void createResourceProperty() {
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		
		Label checkPointLabel = getWidgetFactory().createLabel(composite, BpmnMessages.getString("taskProp_checkPointLabel_label"), SWT.NONE);
		Composite childContainer = getWidgetFactory().createComposite(composite, SWT.NONE);
		GridLayout layoutcheck = new GridLayout(7, false);
		layoutcheck.marginWidth = 0;
		layoutcheck.marginHeight = 0;

		childContainer.setLayout(layoutcheck);
		childContainer.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		timeoutEnable = new Button(childContainer, SWT.CHECK);
		

		resourceLabel = getWidgetFactory().createHyperlink(composite,
				BpmnMessages.getString("generalJavaPropertySection_Resource_label"), SWT.NONE);
		
		Composite browseComposite = getWidgetFactory().createComposite(
				composite);
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		browseComposite.setLayout(layout);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		browseComposite.setLayoutData(gd);
		resourceText = getWidgetFactory().createText(browseComposite, "",
				SWT.BORDER );
		gd = new GridData(GridData.FILL_HORIZONTAL);
		resourceText.setLayoutData(gd);
//		resourceHLinklistener = onResourceHyperlinkClick(this, resourceLabel, resourceText, 
//				fEditor, fEditor.getProject().getName(), false, true, getElementsTypeSupportedForAction());
		browseButton = new Button(browseComposite, SWT.NONE);
		browseButton.setText(BpmnMessages.getString("browse_label"));	

		javaMethodsLabel = getWidgetFactory().createLabel(composite, "Function",  SWT.NONE);
		javaMethodsCombo = getWidgetFactory().createCCombo(composite, SWT.READ_ONLY);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 20;
		gd.horizontalIndent = 1;
		javaMethodsCombo.setLayoutData(gd);
	}
	
	
	@Override
	protected void refreshResouceWidget(EObjectWrapper<EClass, EObject> userObjWrapper){
		super.refreshResouceWidget(userObjWrapper);
		populateMethods();
		refreshJavataskFunction(userObjWrapper);
	}

	void populateMethods() {
		if (resource != null && !resource.toString().isEmpty()) {
			String javaFile = resource.toString() + ".java";
			IFile file = fProject.getFile(javaFile);
			signMethodMap.clear();
			displaySignMap.clear();
			getJavaTaskMethods(file, BpmnIndexUtils.ANNOTATION_BPMN_JAVA_CLASS_METHOD_TASK);
			javaMethodsCombo.removeAll();
			for (String key : signMethodMap.keySet()) {
				javaMethodsCombo.add(key);
			}
		}
	}

	public  static String createJavaTaskSignature(EObjectWrapper<EClass, EObject> userObjWrapper){
		String methodName = null;
		String attrName = BpmnMetaModelExtensionConstants.E_ATTR_METHOD_NAME;
		
		if (userObjWrapper != null && attrName != null) {
			if (ExtensionHelper.isValidDataExtensionAttribute(userObjWrapper, attrName)) {
				EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper
						.getAddDataExtensionValueWrapper(userObjWrapper);
				if (valueWrapper != null) {
					Object attribute = valueWrapper.getAttribute(attrName);
					if (attribute instanceof String)
						methodName = (String) attribute;
					if (methodName == null)
						methodName = "";
				}
			} else if (userObjWrapper.containsAttribute(attrName)) {
//				methodName = getAttributeValue(userObjWrapper,  attrName);
				
				if(userObjWrapper.containsAttribute(attrName)){
					Object attr = userObjWrapper.getAttribute(attrName);
					if (attr != null)
						methodName = attr.toString();
				}

				methodName= "";
			}
		}
		if(methodName != null && methodName.isEmpty()){
			return "";
		}
		List<SymbolEntryImpl> symbols = BpmnModelUtils.getMethodArguments(userObjWrapper.getEInstance());
		
		List<String> parameterTypes = new ArrayList<String>();
		List<String> parameterNames = new ArrayList<String>();
		List<Boolean> parameterArrays = new ArrayList<Boolean>();
		List<Boolean> parameterIsPrimitivies = new ArrayList<Boolean>();
		
		for (SymbolEntryImpl symbol: symbols) {
			EObjectWrapper<EClass, EObject> typeWrapper = EObjectWrapper.wrap(symbol.getType());
			String type = typeWrapper.toString();
			
			parameterTypes.add(type);
			String name = symbol.getKey();
			parameterNames.add(name);
			
			parameterArrays.add(symbol.isArray());
			parameterIsPrimitivies.add(symbol.isPrimitive());
		}
		
		String functionSignature = getMethodSignature(methodName,
				parameterTypes.toArray(new String[parameterTypes.size()]), 
				parameterNames.toArray(new String[parameterNames.size()]),
				parameterArrays.toArray(new Boolean[parameterArrays.size()]),
				parameterIsPrimitivies.toArray(new Boolean[parameterIsPrimitivies.size()]));
		
		return functionSignature;
		
	}
	protected void refreshJavataskFunction(EObjectWrapper<EClass, EObject> userObjWrapper) {
		
		String functionSignature = createJavaTaskSignature(userObjWrapper);
		if (functionSignature != null && !functionSignature.isEmpty()) {
			if (signMethodMap.containsKey(functionSignature)) {
				javaMethodsCombo.setText(functionSignature);
			} else {
				javaMethodsCombo.setText("");
				// Remove values from xml as well
				if (userObjWrapper != null) {
					if (ExtensionHelper
							.isValidDataExtensionAttribute(
									userObjWrapper,
									BpmnMetaModelExtensionConstants.E_ATTR_JAVA_FILE_PATH)) {
						EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper
								.getAddDataExtensionValueWrapper(userObjWrapper);
						valueWrapper.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_METHOD_NAME,"");
						List<EObject> eObjlist = new ArrayList<EObject>();
						valueWrapper.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_METHOD_ARGUMENTS, eObjlist);
						valueWrapper.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_METHOD_RETURN_TYPE, null);
					}
				}
				IFile file = BpmnIndexUtils.getFile(this.getProject().getName(), BpmnModelUtils.getProcess(userObjWrapper).getEInstance());
				if (file.exists()) {
					try {
						ECoreHelper.serializeModelXMI(file, BpmnModelUtils.getProcess(userObjWrapper).getEInstance());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}
		}
	}

	@Override
	protected void updateJavaTaskFunction(Map<String, Object> updateList) {
		String taskFunctiontext = javaMethodsCombo.getText().trim();
		String taskFunction = displaySignMap.get(taskFunctiontext);
		String attrName = BpmnMetaModelExtensionConstants.E_ATTR_METHOD_NAME;
		updateList.put(attrName,taskFunction);
		updateMethodArguments(updateList);
		updateMethodReturnType(updateList);
	}

	/**
	 * @param updateList
	 */
	private void updateMethodArguments(Map<String, Object> updateList) {
		
		EObjectWrapper<EClass, EObject> useInstance = getUserObject();
		String taskFunctionText = javaMethodsCombo.getText().trim();
		if (!taskFunctionText.isEmpty()) {
			IMethod iMethod = signMethodMap.get(taskFunctionText);
			getJavaTaskMethodArguments(iMethod);

			List<EObject> eObjlist = new ArrayList<EObject>();
			for (TypeSymbol ts: argTypes) {
				EObject eobject  = createMethodArgumentSymbol(ts);
				eObjlist.add(eobject);
			}
			
			EObjectWrapper<EClass, EObject> addDataExtensionValueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(useInstance);
			EList<EObject> attribute = addDataExtensionValueWrapper
					.getListAttribute(BpmnMetaModelExtensionConstants.E_ATTR_METHOD_ARGUMENTS);
			if (attribute.size() > 0) {
				addDataExtensionValueWrapper.clearListAttribute(BpmnMetaModelExtensionConstants.E_ATTR_METHOD_ARGUMENTS);
			}
			if (eObjlist.size() > 0) {
				updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_METHOD_ARGUMENTS, eObjlist);
			}
		}

	}

	/**
	 * @param updateList
	 */
	private void updateMethodReturnType(Map<String, Object> updateList) {

		String taskFunctionText = javaMethodsCombo.getText().trim();
		if (!taskFunctionText.isEmpty()) {
			IMethod iMethod = signMethodMap.get(taskFunctionText);
			TypeSymbol ts = getJavaTaskReturnTypes(iMethod);
			List<EObject> eObjlist = new ArrayList<EObject>();
			EObject eobject  = createMethodArgumentSymbol(ts);
			eObjlist.add(eobject);
			if (eObjlist.size() > 0) {
				updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_METHOD_RETURN_TYPE, eObjlist.get(0));
			}
		}

	}

	protected EObjectWrapper<EClass, EObject> getUserObject() {
		EObject userObject = null;
		if (fTSENode != null) {
			userObject= (EObject) fTSENode.getUserObject();
		}
		if (userObject != null) {
			return EObjectWrapper.wrap(userObject);
		} else {
			return null;
		}	
	}

	/**
	 * @param iMethod
	 * @param annotationName
	 * @return
	 */
	public void getJavaTaskMethodArguments(IMethod iMethod) {
		try {
			
			argTypes.clear();
			
			for(ILocalVariable variable :iMethod.getParameters()) {
				
				IAnnotation annotation = variable.getAnnotation(BpmnCommonIndexUtils.JAVA_TASK_TYPE_ANNOTATION);
				String var = variable.getElementName();
				
				if (annotation.getElementName().equals(BpmnCommonIndexUtils.JAVA_TASK_MODEL_TYPE_MAP)) {
					
					boolean isArray = false;
					boolean isPrimitive = false; 
					
					if (StudioJavaUtil.isJavaArray(variable.getTypeSignature())) {
						isArray = true;
					}
					String varType = variable.getTypeSignature();
					if(varType != null && varType.startsWith("[")){
						varType = varType.substring(1,varType.length());
					}
					if (StudioJavaUtil.isJavaPrimitive(/*variable.getTypeSignature()*/varType)) {
						isPrimitive = true;
					}
					
					String uri = "";
					String type = "";
					
					for (IMemberValuePair pair :annotation.getMemberValuePairs()) {
						if (pair.getMemberName().equals("type")) {
							type = pair.getValue().toString();
						}
					}
					
					type = type.replace(BpmnCommonIndexUtils.JAVA_TASK_MODEL_TYPE + BpmnIndexUtils.DOT, "");
					if (ModelType.valueOf(type) == ModelType.CONCEPT_REFERENCE
							|| ModelType.valueOf(type) == ModelType.CONTAINED_CONCEPT
							|| ModelType.valueOf(type) == ModelType.PROCESS) {
						for (IMemberValuePair pair :annotation.getMemberValuePairs()) {
							if (pair.getMemberName().equals("uri")) {
								uri = pair.getValue().toString();
							}
						}
					}
					
					TypeSymbol ts = new TypeSymbol();
					ts.setName(var);
					ts.setArray(isArray);
					ts.setPrimitive(isPrimitive);
					ts.setPropertyType(ModelTypeHelper.getPropertyType(ModelType.valueOf(type), isPrimitive));
					ts.setUri(uri);
					argTypes.add(ts);
					
				} 
			}
		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * @param iMethod
	 * @param annotationName
	 * @return
	 */
	public TypeSymbol getJavaTaskReturnTypes(IMethod iMethod) {
		isArrayReturnType = false;
		returnType = "";
		returnTypeURI = "";
		isPrimitiveReturnType = false;
		TypeSymbol ts = new TypeSymbol();
		ts.setName(iMethod.getElementName());
		boolean flag = false;
		List<Annotation> annotationList = StudioJavaUtil.getAnnotations(iMethod);
		for (Annotation annot : annotationList) {
			if (StudioJavaUtil.getAnnotationName(annot).equals(BpmnCommonIndexUtils.JAVA_TASK_TYPE_ANNOTATION)) {
				flag =  true;
				break;
			}
		}
		if (flag/*annotation.getElementName().equals(BpmnCommonIndexUtils.JAVA_TASK_MODEL_TYPE_MAP)*/) {
			IAnnotation annotation = iMethod.getAnnotation(BpmnCommonIndexUtils.JAVA_TASK_TYPE_ANNOTATION);
			try {
				if (StudioJavaUtil.isJavaArray(iMethod.getReturnType())) {
					ts.setArray(true);
					isArrayReturnType = true;
				}
				
				if (StudioJavaUtil.isJavaPrimitive(iMethod.getReturnType())) {
					ts.setPrimitive(true);
					isPrimitiveReturnType = true;
				}

				for (IMemberValuePair pair :annotation.getMemberValuePairs()) {
					if (pair.getMemberName().equals("type")) {
						returnType = pair.getValue().toString();
						
					}
				}
				returnType = returnType.replace(BpmnCommonIndexUtils.JAVA_TASK_MODEL_TYPE + BpmnIndexUtils.DOT, "");
				ModelType modelType = ModelType.VOID;
				if (returnType != null && !returnType.isEmpty()) {
					modelType = ModelType.valueOf(returnType);
					if (modelType == ModelType.CONCEPT_REFERENCE
							|| modelType == ModelType.CONTAINED_CONCEPT
							|| modelType == ModelType.PROCESS) {
						for (IMemberValuePair pair :annotation.getMemberValuePairs()) {
							if (pair.getMemberName().equals("uri")) {
								returnTypeURI = pair.getValue().toString();
								ts.setUri(pair.getValue().toString());
							}
						}
					} 
				}
				if (modelType == ModelType.VOID) {
					isPrimitiveReturnType = false;
				}
				ts.setPropertyType(ModelTypeHelper.getPropertyType(modelType, isPrimitiveReturnType));

			} catch (JavaModelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} 
		return ts;
	}


	/**
	 * @param ts
	 * @return
	 */
	public EObject createMethodArgumentSymbol(TypeSymbol ts) {
		EObjectWrapper<EClass, EObject> argSymbol = EObjectWrapper.createInstance(BpmnMetaModelExtension.EXTN_SYMBOL);
		argSymbol.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_ID_NAME, ts.getName());
		argSymbol.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_TYPE, ts.getPropertyType() == null ? 
				BpmnMetaModelExtension.ENUM_PROPERTY_TYPES_Void : ModelTypeHelper.getModelPropertyType(ts.getPropertyType()));
		argSymbol.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_ENTITY_PATH, ts.getUri());
		argSymbol.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_IS_ARRAY, ts.isArray());
		argSymbol.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_IS_PRIMITIVE, ts.isPrimitive());
		return argSymbol.getEInstance();
	}

	/**
	 * 
	 * @param file
	 */
	public void getJavaTaskMethods(IFile file, String annotationName) {

		try {
			ICompilationUnit cu = JavaCore.createCompilationUnitFrom(file);
			if (cu != null ) {
				cu.getImports();
				IType[] types = cu.getTypes();
				for (IType iType : types) {
					IMethod[] methods = iType.getMethods();
					for (IMethod iMethod : methods) {
						
						int modifier= iMethod.getFlags();
						if (Modifier.isPublic(modifier)) {
							boolean methodFlag = false;
							boolean returnFlag = false;
							List<Annotation> annotationList = StudioJavaUtil.getAnnotations(iMethod);	
							for (Annotation annotation : annotationList) {
								if (StudioJavaUtil.getAnnotationName(annotation).equals(annotationName)) {
									methodFlag =  true;
								}
								if (StudioJavaUtil.getAnnotationName(annotation).equals(BpmnCommonIndexUtils.JAVA_TASK_TYPE_ANNOTATION)) {
									returnFlag =  true;
								}
							}
							
							if (methodFlag && returnFlag/*iMethod.getAnnotation(annotationName) != null*/) {
								String displaySign = getJavaMethodSignature(iMethod);
								signMethodMap.put(displaySign, iMethod);
								displaySignMap.put(displaySign, iMethod.getElementName());
							}
						}
					}
				}
			}
		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * 
	 * @param iMethod
	 * @return
	 */
	public static String getJavaMethodSignature(IMethod iMethod) {
		
		StringBuilder methodName = new StringBuilder();
//		methodName.append(iMethod.getDeclaringType().getFullyQualifiedName());
//		methodName.append(".");
		methodName.append(iMethod.getElementName());
		methodName.append("(");
		String comma = "";
		String[] parameterTypes = iMethod.getParameterTypes();
		try {
			String[] parameterNames = iMethod.getParameterNames();
			for (int i=0; i<iMethod.getParameterTypes().length; ++i) {
				methodName.append(comma);
				methodName.append(Signature.toString(parameterTypes[i]));
				methodName.append(" ");
				methodName.append(parameterNames[i]);
				comma = ", ";
			}
		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		methodName.append(")");
		return methodName.toString();
	}
	
	public static String getMethodSignature(String methodElementName, 
			String[] parameterTypes, 
			String[] parameterNames, 
			Boolean[] arrays, 
			Boolean[] isPrimitives) {
		StringBuilder methodName = new StringBuilder();
		//		methodName.append(iMethod.getDeclaringType().getFullyQualifiedName());
		//		methodName.append(".");
		methodName.append(methodElementName);
		methodName.append("(");
		String comma = "";
		for (int i = 0; i < parameterTypes.length; ++i) {
			boolean primitive = isPrimitives[i];
			@SuppressWarnings("unused")
			boolean array =  arrays[i];
			methodName.append(comma);
			String paramType=  parameterTypes[i];
			if( parameterTypes[i].equalsIgnoreCase("ConceptReference")){
				paramType = "Concept";
			}
			methodName.append(BpmnModelUtils.getType(primitive,paramType));
			if(array){
				methodName.append("[]");
			}
			methodName.append(" ");
			methodName.append(parameterNames[i]);
			comma = ", ";
		}
		methodName.append(")");
		return methodName.toString();
	}
	
}
