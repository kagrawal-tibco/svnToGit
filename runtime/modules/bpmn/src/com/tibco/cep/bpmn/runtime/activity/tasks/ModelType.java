package com.tibco.cep.bpmn.runtime.activity.tasks;

import java.util.Calendar;

import com.tibco.cep.bpmn.runtime.model.JobContext;
import com.tibco.cep.runtime.model.element.Concept;


/**
 * This ModelType enumeration
 * @since 5.2.0
 * @version 5.2.0
 * @.category public-api
 */
public enum ModelType {
	
	/**
	 * Identifies the model type integer.
	 * @.category public-api
	 * @since 5.2.0
	 */
	VOID(false,void.class),

	/**
	 * Identifies the model type integer.
	 * @.category public-api
	 * @since 5.2.0
	 */
	OBJECT(false,Object.class),

	/**
	 * Identifies the model type integer.
	 * @.category public-api
	 * @since 5.2.0
	 */
	INT(false,int.class),
	 /**
     * Identifies the model data type long.
     * @.category public-api
     * @since 5.2.0
     */
	LONG(false,long.class),
	 /**
     * Identifies the model data type double.
     * @.category public-api
     * @since 5.2.0
     */
	DOUBLE(false,double.class),
	 /**
     * Identifies the model data type DateTime.
     * @.category public-api
     * @since 5.2.0
     */
	DATETIME(false,Calendar.class),
	 /**
     * Identifies the model data type boolean.
     * @.category public-api
     * @since 5.2.0
     */
	BOOLEAN(false,boolean.class),
	 /**
     * Identifies the model data type String.
     * @.category public-api
     * @since 5.2.0
     */
	STRING(false,String.class),
	 /**
     * Identifies the model artifact type Contained Concept.
     * @.category public-api
     * @since 5.2.0
     */
	CONTAINED_CONCEPT(true,Concept.class),
	 /**
     * Identifies the model artifact type Reference Concept.
     * @.category public-api
     * @since 5.2.0
     */
	CONCEPT_REFERENCE(true,Concept.class),
	 /**
     * Identifies the model artifact type Process.
     * @.category public-api
     * @since 5.2.0
     */
	PROCESS(true,JobContext.class);
	
	/*
	 * Indicates if the model type is a complex type and it is identified by an model resource uri.
	 */
	private boolean hasURI;
	
	/*
	 * This is the generic java class type for the Model type
	 */
	private Class clazz;

	
	private ModelType(boolean hasURI,Class cls) {
		this.hasURI = hasURI;
		this.clazz = cls;
	}
}
