package com.tibco.cep.studio.core.validation;

import java.util.Set;

import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

import com.tibco.be.model.functions.BEFunction;
import com.tibco.be.model.functions.BEPackage;

/**
 * 
 * @author sasahoo
 *
 */
@SupportedAnnotationTypes({"com.tibco.be.model.functions.BEPackage", "com.tibco.be.model.functions.BEFunction"})
public class CustomBEFunctionProcessor extends AbstractStudioAnnotationProcessor {

	/* (non-Javadoc)
	 * @see javax.annotation.processing.AbstractProcessor#process(java.util.Set, javax.annotation.processing.RoundEnvironment)
	 */
	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

		Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(BEPackage.class);
		if (!roundEnv.processingOver()) {
			for (Element el :  elements) {
				if (el.getKind() == ElementKind.CLASS) {
					if (!containsModifier(el,PUBLIC_MODIFIER)) {
						reportError("Invalid Custom Catalog Class", el); 
					}
				}
			}
		}

		Set<? extends Element> beFunctionElements = roundEnv.getElementsAnnotatedWith(BEFunction.class);
		if (!roundEnv.processingOver()) {
			Element[] el = new Element[beFunctionElements.size()];
			beFunctionElements.toArray(el);
			Element emn = el[0];
			if (containsModifier(emn,PUBLIC_MODIFIER) && containsModifier(emn,STATIC_MODIFIER)) {

			} else {
				reportError("Invalid Custom Catalog Function", emn); 
			}
		}

		return true;

	}

}