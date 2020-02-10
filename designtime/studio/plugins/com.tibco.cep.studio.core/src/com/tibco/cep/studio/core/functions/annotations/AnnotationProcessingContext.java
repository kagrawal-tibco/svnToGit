package com.tibco.cep.studio.core.functions.annotations;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;

public class AnnotationProcessingContext {

	private Map<Class<?>,AnnotationHandler<?>> handlerMaps = new HashMap<Class<?>,AnnotationHandler<?>>();
	private ProcessingEnvironment processingEnv;
	private Set<? extends TypeElement> processingAnnotations;
	private RoundEnvironment roundEnvironment;
	
	public AnnotationProcessingContext() {
		
	}
	
	public void register(Class<?> key,AnnotationHandler<?> value) {
		handlerMaps.put(key, value);
		
	}
	
	public  Map<Class<?>,AnnotationHandler<?>> getRegisteredHandlers() {
		return Collections.unmodifiableMap(handlerMaps);
	}
	
	public Collection<AnnotationHandler<?>> getHandlers(Collection<String> types){
		Set<AnnotationHandler<?>> handlers = new HashSet<AnnotationHandler<?>>();
		for(AnnotationHandler<?> value:handlerMaps.values()){
			if(value.getSupportedAnnotationTypes().containsAll(types)){
				handlers.add(value);
			}
		}
		return handlers;
	}

	public void register(AnnotationHandler<?>[] handlers) {
		for(AnnotationHandler<?> handler:handlers){
			register(handler.getClass(),handler);
		}
	}
	
	public Collection<String> getSupportedAnnotationTypes() {
		Set<String> types = new HashSet<String>();
		for(AnnotationHandler<?> value:handlerMaps.values()){
			types.addAll(value.getSupportedAnnotationTypes());
		}
		return Collections.unmodifiableSet(types);
	}
	


	public void setProcessingEnv(ProcessingEnvironment processingEnv) {
		this.processingEnv = processingEnv;
		
	}

	public ProcessingEnvironment getProcessingEnv() {
		return processingEnv;
	}

	public void setProcessingAnnotations(Set<? extends TypeElement> annotations) {
		this.processingAnnotations = annotations;
		
	}
	
	public Set<? extends TypeElement> getProcessingAnnotations() {
		return processingAnnotations;
	}

	public void setRoundEnvironment(RoundEnvironment env) {
		this.roundEnvironment = env;
		
	}
	
	public RoundEnvironment getRoundEnvironment() {
		return roundEnvironment;
	}
}
