package com.tibco.cep.bpmn.ui.utils;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.SimpleElementVisitor6;
import javax.tools.JavaFileObject;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.utils.BpmnCommonIndexUtils;
import com.tibco.cep.bpmn.runtime.activity.tasks.ModelType;
import com.tibco.cep.bpmn.runtime.activity.tasks.ModelTypeMap;
import com.tibco.cep.bpmn.runtime.model.JavaTask;
import com.tibco.cep.bpmn.runtime.model.JavaTaskMethod;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.validation.AbstractStudioAnnotationProcessor;

/**
 * 
 * @author sasahoo
 *
 */
@SupportedAnnotationTypes({"com.tibco.cep.bpmn.runtime.activity.tasks.JavaTask", "com.tibco.cep.bpmn.runtime.activity.tasks.JavaTaskMethod", 
"com.tibco.cep.bpmn.runtime.activity.tasks.ModelTypeMap"})
public class ModelTypeProcessor extends AbstractStudioAnnotationProcessor {

	/* (non-Javadoc)
	 * @see javax.annotation.processing.AbstractProcessor#process(java.util.Set, javax.annotation.processing.RoundEnvironment)
	 */
	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

		Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(JavaTask.class);

		Set<? extends Element> elementTaskMethods = roundEnv.getElementsAnnotatedWith(JavaTaskMethod.class);
		Element[] elt = elementTaskMethods.toArray(new Element[elementTaskMethods.size()]);

		Set<? extends Element> modeTypeMapElements = roundEnv.getElementsAnnotatedWith(ModelTypeMap.class);
		if (!roundEnv.processingOver()) {
			for (Element el :  elements) {
				if (el.getKind() == ElementKind.CLASS) {
					if (!containsModifier(el,PUBLIC_MODIFIER)) {
						reportError("Invalid Java Task Class", el); 
					}
				}
			}
			boolean isReturnModelTypeMap = false;
			for (Element el :  elementTaskMethods) {
				if (el.getKind() == ElementKind.METHOD) {
					for (final Element te : modeTypeMapElements) {
						if (te.getKind() == ElementKind.METHOD) {
							isReturnModelTypeMap = true;
							break;
						}
					}
				}
			}
			if (!isReturnModelTypeMap) {
				reportError("Java Task Return Type ModelTypeMap is missing", elt[0]); 
			}
			for (final Element te : modeTypeMapElements) {
				ModelTypeMap annotation = te.getAnnotation(ModelTypeMap.class);
				final ModelType type = annotation.type();
				if (te.getKind() == ElementKind.METHOD) {
					ElementVisitor<ExecutableElement,Object> ev=new SimpleElementVisitor6<ExecutableElement,Object>(){
						@Override 
						public ExecutableElement visitExecutable(ExecutableElement e, Object p){
							String retType = e.getReturnType().toString();
							if (!isMatchElementType(retType, type)) {
								reportError("Return Type ModelTypeMap mismatch", te); 
							}
							for (VariableElement el : e.getParameters()) {
								ModelTypeMap vannotation = el.getAnnotation(ModelTypeMap.class);
								if (vannotation == null) {
									reportError("Parameter ModelTypeMap is missing", el); 
								}
								final ModelType vtype = vannotation.type();
								if (vtype == ModelType.VOID) {
									reportError("Parameter VOID ModelTypeMap is not allowed", el); 
								}
								String eltype = el.asType().toString();
								if (!isMatchElementType(eltype, vtype)) {
									reportError("Parameter Type ModelTypeMap mismatch", el); 
								}
							}
							return e;
						}
					};
					te.accept(ev, null);
				}

				if (type.equals(ModelType.CONCEPT_REFERENCE) || type.equals(ModelType.CONTAINED_CONCEPT)
						|| type.equals(ModelType.PROCESS)) {
					try {
						boolean isMethod = false;
						uri = annotation.uri();
						
						if (te.getKind() == ElementKind.METHOD) {
							isMethod = true;
						}
						String retURIerr = isMethod ? "Return Type " : "";
						
						JavaFileObject sourceFile = processingEnvironment.getFiler().createSourceFile("TempAnnotation");
						String[] segments = sourceFile.toUri().getPath().split("/");
						String projectName = segments[segments.length - 3];
						File file = new File(sourceFile.toUri());
						File parent = file.getParentFile();
						if (file.exists()) {
							file.delete();
						}
						if (parent.exists()) {
							parent.delete();
						}
//						IProject project  = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
						if (uri.isEmpty()) {
							reportError(retURIerr + "\"uri\" field must not be empty", te);
						} else {
							if (type.equals(ModelType.PROCESS)) {
								uri = uri + BpmnCommonIndexUtils.DOT + BpmnCommonIndexUtils.BPMN_PROCESS_EXTENSION;
								EObject object = BpmnCommonIndexUtils.getElement(projectName, uri);	
								if (object == null) {
									reportError(retURIerr + "Invalid Process uri = \"" + uri + "\"", te); 
								}
							} else {
//								IndexUtils.waitForUpdate();
								DesignerElement designerElement = CommonIndexUtils.getElement(projectName, uri);
								if (designerElement == null) {
									reportError(retURIerr + "Invalid Concept uri = \"" + uri + "\"", te); 
								}
							}

						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}
		return true;
	}

	/**
	 * @param retType
	 * @param type
	 * @return
	 */
	private boolean isMatchElementType(String retType, ModelType type) {

		//Handle Array Return Type
		if (retType.endsWith(ARRAY_SUFFIX)) {
			retType = retType.replace(ARRAY_SUFFIX, "");
		}

		if (type == ModelType.OBJECT && retType.equals("java.lang.Object")) {
			return true;
		}

		if (type == ModelType.VOID && retType.equals("void")) {
			return true;
		}

		if (type == ModelType.INT && (retType.equals("int") || retType.equals("java.lang.Integer"))) {
			return true;
		}

		if (type == ModelType.DOUBLE && (retType.equals("double") || retType.equals("java.lang.Double"))) {
			return true;
		}

		if (type == ModelType.LONG && (retType.equals("long") || retType.equals("java.lang.Long"))) {
			return true;
		}

		if (type == ModelType.BOOLEAN && (retType.equals("boolean") || retType.equals("java.lang.Boolean"))) {
			return true;
		}

		if (type == ModelType.DATETIME && (retType.equals("java.util.Calendar") || retType.equals("java.util.Date"))) {
			return true;
		}

		if (type == ModelType.STRING && retType.equals("java.lang.String")) {
			return true;
		}

		if ((type == ModelType.CONCEPT_REFERENCE || type == ModelType.CONTAINED_CONCEPT) 
				&& retType.equals("com.tibco.cep.runtime.model.element.Concept")) {
			return true;
		}

		if ((type == ModelType.PROCESS) 
				&& retType.equals("com.tibco.cep.runtime.model.element.ProcessJob")) {
			return true;
		}

		return false;
	}

}