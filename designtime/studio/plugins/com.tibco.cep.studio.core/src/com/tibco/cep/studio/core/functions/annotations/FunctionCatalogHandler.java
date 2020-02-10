package com.tibco.cep.studio.core.functions.annotations;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

import com.tibco.be.model.functions.BEFunction;
import com.tibco.be.model.functions.BEPackage;

public class FunctionCatalogHandler implements AnnotationHandler<CatalogInfo> {
	
	CatalogInfo catalogInfo = null;
	
	public FunctionCatalogHandler(){
		
	}


	@Override
	public Collection<String> getSupportedAnnotationTypes() {
		String[] types = new String[] {BEPackage.class.getCanonicalName(),BEFunction.class.getCanonicalName()};
		return Arrays.asList(types);
	}

	@Override
	public CatalogInfo getResult() {
		return catalogInfo;
	}



	

	@Override
	public boolean process(AnnotationProcessingContext context) {
		RoundEnvironment env = context.getRoundEnvironment();
		ProcessingEnvironment procEnv = context.getProcessingEnv();
		catalogInfo = new CatalogInfo();
		
		for (Element element : env.getElementsAnnotatedWith(BEPackage.class)) {
			BEPackage pkgAnnotation = element.getAnnotation(BEPackage.class);
			catalogInfo.setName(pkgAnnotation.catalog());

			String category = pkgAnnotation.category();
			boolean isEnabled = pkgAnnotation.enabled().value();
			if (!isEnabled) {
				continue;
			}
			CategoryInfo catInfo = new CategoryInfo();
			catInfo.setCategory(category);
			catInfo.setAnnotation(pkgAnnotation);
			TypeMirror type = element.asType();

			catInfo.setImplClassName(type.toString());
			catalogInfo.getCategories().add(catInfo);
			String packageName = procEnv.getElementUtils().getPackageOf(element).getQualifiedName().toString();
			if (element instanceof TypeElement) {
				TypeElement typeElement = (TypeElement) element;
				for (Element enclosed : typeElement.getEnclosedElements()) {
					if (enclosed instanceof ExecutableElement) {
						ExecutableElement exElement = (ExecutableElement) enclosed;
						ElementKind kind = exElement.getKind();
						if (kind != ElementKind.METHOD)
							continue;
						Set<Modifier> modifiers = exElement.getModifiers();
						if (!(modifiers.contains(Modifier.PUBLIC) && modifiers.contains(Modifier.STATIC)))
							continue;
						BEFunction fnAnnotation = exElement.getAnnotation(BEFunction.class);
						if (fnAnnotation == null)
							continue;
						if (!fnAnnotation.enabled().value())
							continue;
						String fnName = exElement.getSimpleName().toString();
						FunctionInfo fnInfo = new FunctionInfo();
						fnInfo.setName(fnName);
						fnInfo.setDomain(fnAnnotation.fndomain());
						fnInfo.setDeprecated(fnAnnotation.deprecated().value());
						fnInfo.setMapperEnabled(fnAnnotation.mapper().enabled().value());
						fnInfo.setAnnotation(fnAnnotation);
						catInfo.getFunctions().add(fnInfo);
						for (VariableElement param : exElement.getParameters()) {
							ParamTypeInfo paramInfo = new ParamTypeInfo();
							paramInfo.setName(param.getSimpleName().toString());
							paramInfo.setPrimitive(param.asType().getKind().isPrimitive());
							paramInfo.setTypeClassName(param.asType().toString());
							paramInfo.setArray(param.asType().getKind() == TypeKind.ARRAY);
							fnInfo.getArgs().add(paramInfo);
						}
						ParamTypeInfo rtInfo = new ParamTypeInfo();
						rtInfo.setTypeClassName(exElement.getReturnType().toString());
						rtInfo.setPrimitive(exElement.getReturnType().getKind().isPrimitive());
						rtInfo.setArray(exElement.getReturnType().getKind() == TypeKind.ARRAY);
						fnInfo.setReturnType(rtInfo);

					}
				}

			}

		}
		return true;
	}

}
