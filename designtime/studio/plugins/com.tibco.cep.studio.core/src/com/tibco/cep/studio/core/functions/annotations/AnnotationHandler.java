package com.tibco.cep.studio.core.functions.annotations;

import java.util.Collection;

public interface AnnotationHandler<T> {

	Collection<String> getSupportedAnnotationTypes();
	
	T getResult();

	boolean process(AnnotationProcessingContext context);

}
