package com.tibco.cep.studio.core.functions.annotations;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.Completion;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;


/**
 * @author Pranab Dhar
 * 
 */
public class AnnotationProcessorImpl implements Processor {
	/**
	 * Processing environment providing by the tool framework.
	 */
	protected ProcessingEnvironment processingEnv;
	private boolean initialized = false;

	public Set<String> supportedAnnotationTypes = new HashSet<String>();
	private AnnotationProcessingContext context;
	private Filer filer = null;
	private Messager messager = null;;

	@Override
	public Set<String> getSupportedOptions() {
		return Collections.emptySet();
	}
	



	@Override
	public Set<String> getSupportedAnnotationTypes() {
		return supportedAnnotationTypes;
	}

	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.latest();
	}

	@Override
	public void init(ProcessingEnvironment processingEnv) {
		if (initialized)
			throw new IllegalStateException("Cannot call init more than once.");
		if (processingEnv == null)
			throw new NullPointerException("Tool provided null ProcessingEnvironment");

		this.processingEnv = processingEnv;
		this.filer = processingEnv.getFiler();
		this.messager = processingEnv.getMessager();
		context = AnnotationInfoCollector.getThreadLocalContext();
		if (context == null) {
			messager.printMessage(Kind.NOTE, "AnnotationProcessingContext not found");
		} else {
			supportedAnnotationTypes.addAll(context.getSupportedAnnotationTypes());
			context.setProcessingEnv(processingEnv);
			initialized = true;
		}
	}

	/**
	 * Returns {@code true} if this object has been {@linkplain #init
	 * initialized}, {@code false} otherwise.
	 * 
	 * @return {@code true} if this object has been initialized, {@code false}
	 *         otherwise.
	 */
	protected synchronized boolean isInitialized() {
		return initialized;
	}

	/* (non-Javadoc)
	 * @see javax.annotation.processing.Processor#process(java.util.Set, javax.annotation.processing.RoundEnvironment)
	 */
	@Override
	public boolean process(final Set<? extends TypeElement> annotations,final RoundEnvironment env) {
		if(!initialized)
			return false;
		context.setProcessingAnnotations(annotations);
		context.setRoundEnvironment(env);
		Set<String> types = new HashSet<String>();
		for(TypeElement an:annotations){
			types.add(an.getQualifiedName().toString());
		}
		Collection<AnnotationHandler<?>> handlers = context.getHandlers(types);
		boolean result = false;
		for(AnnotationHandler<?> handler:handlers) {
			if(types.containsAll(handler.getSupportedAnnotationTypes())){
				result =  handler.process(context) || result;
			}
		}
		return result;
	}

	@Override
	public Iterable<? extends Completion> getCompletions(Element element, AnnotationMirror annotation, ExecutableElement member, String userText) {
		// TODO Auto-generated method stub
		return null;
	}

}
