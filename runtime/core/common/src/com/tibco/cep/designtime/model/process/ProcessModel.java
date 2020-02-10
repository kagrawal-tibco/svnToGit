package com.tibco.cep.designtime.model.process;

import java.util.Collection;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.runtime.model.process.LoopTuple;
import com.tibco.cep.runtime.model.process.MergeTuple;
import com.tibco.xml.schema.SmElement;

/*
* Author: Suresh Subramani / Date: 11/23/11 / Time: 3:29 PM
*/
public interface ProcessModel extends Entity , BaseModelType {
	
	public static final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";
	
	public enum PROCESS_CONTROL_BEAN {
		LOOP_CONTROL_BEAN(LoopTuple.class),
		MERGE_CONTROL_BEAN(MergeTuple.class);
		
		Class bclazz;
		private PROCESS_CONTROL_BEAN(Class clz ){
			this.bclazz = clz;
		}
		
		
	};
	public enum BASE_ATTRIBUTES {
			id("getId()", RDFTypes.LONG_TYPEID), 
			extId("getExtId()", RDFTypes.STRING_TYPEID),
			parent("getParent()", RDFTypes.PROCESS_TYPEID) {
				public int getPropTypesIdx() {
					return RDFTypes.CONCEPT_TYPEID;
				}
			},
			//children,
			state("getProcessStatus().getName()", RDFTypes.STRING_TYPEID),
			taskId("getLastTaskExecuted().getName()", RDFTypes.STRING_TYPEID),
			templateVersion("getProcessTemplateVersionAsInt()", RDFTypes.INTEGER_TYPEID);
			
			
			private String getter;
			private int rdfTypeIndex;
			private BASE_ATTRIBUTES(String g, int typeId) {
				this.getter = g;
				rdfTypeIndex = typeId;
			}
			public String getGetter() {
				return getter;
			}
			public int getRdfTypeId() {
				return rdfTypeIndex;
			}
			public int getPropTypesIdx() {
				return rdfTypeIndex;
			}
			
			};

    /**
     * Cast to either Concept or EObject.
     *
     * A Process can be thought of a Concept with variables.
     * An E-Object allows it to be thought of as a EMF Object
     * @param typeOf
     * @param <T>
     * @return
     */
    <T> T cast(Class<T> typeOf);
    
    
    
    SmElement toSmElement();
    
    
    int getRevision();
    
    
    String getOriginalAuthor();



	String getLastModifiedAuthor();



	String getDeployedDate();


    <T> T getTaskElement(String name, Class<T> typeOf);
    
    
    Collection<PropertyDefinition> getPropertyDefinitions();
    
    Collection<SubProcessModel> getSubProcesses();
    
    /**
     * @return a Collection
     */
    Collection<PropertyDefinition> getAttributeDefinitions();
    
    PropertyDefinition getAttributeDefinition(String attributeName);

	SubProcessModel getSubProcessById(String id);
}
