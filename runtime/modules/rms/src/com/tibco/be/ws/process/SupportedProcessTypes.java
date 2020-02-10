/**
 * 
 */
package com.tibco.be.ws.process;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EEnumLiteral;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.xml.data.primitive.ExpandedName;

/**
 * Listing out all the types supported in BE Process
 * 
 * @author vpatil
 */
public enum SupportedProcessTypes {

	Integer("Integer",ExpandedName.parse("{http://www.w3.org/2001/XMLSchema}integer"), BpmnModelClass.ENUM_PROPERTY_TYPES_Integer),
	Boolean("Boolean",ExpandedName.parse("{http://www.w3.org/2001/XMLSchema}boolean"), BpmnModelClass.ENUM_PROPERTY_TYPES_Boolean),
	Double("Double",ExpandedName.parse("{http://www.w3.org/2001/XMLSchema}double"), BpmnModelClass.ENUM_PROPERTY_TYPES_Double),
	Long("Long",ExpandedName.parse("{http://www.w3.org/2001/XMLSchema}long"), BpmnModelClass.ENUM_PROPERTY_TYPES_Long),
	String("String",ExpandedName.parse("{http://www.w3.org/2001/XMLSchema}string"), BpmnModelClass.ENUM_PROPERTY_TYPES_String),
	DateTime("DateTime",ExpandedName.parse("{http://www.w3.org/2001/XMLSchema}dateTime"), BpmnModelClass.ENUM_PROPERTY_TYPES_DateTime),
	ContainedConcept("ContainedConcept",ExpandedName.parse("{http://www.w3.org/2001/XMLSchema}containedConcept"), BpmnModelClass.ENUM_PROPERTY_TYPES_Concept),
	ConceptReference("ConceptReference",ExpandedName.parse("{http://www.w3.org/2001/XMLSchema}ConceptReference"), BpmnModelClass.ENUM_PROPERTY_TYPES_ConceptReference),;
	
	private String name;
	private ExpandedName type;
	private EEnumLiteral emfType;
	
	private static Map<ExpandedName, SupportedProcessTypes> supportedTypeMap;
	
	/**
	 * Default constructor
	 * 
	 * @param name
	 * @param type
	 * @param pType
	 */
	private SupportedProcessTypes(String name, ExpandedName type, EEnumLiteral pType) {
		this.name = name;
		this.type = type;
		this.emfType = pType;
	}
	
	/**
	 * Retrieve the associated EMF type
	 * 
	 * @param name
	 * @return
	 */
	public static EEnumLiteral getEmfType(String name){
		Map<ExpandedName, SupportedProcessTypes> map = getSupportedTypeMap();
		
		Collection<SupportedProcessTypes> values = map.values();
		for (SupportedProcessTypes supportedProcessPropertiesType : values) {
			if(supportedProcessPropertiesType.getName().equalsIgnoreCase(name))
				return supportedProcessPropertiesType.getEmfType();
		}
		
		if(name.equalsIgnoreCase("int")) {
			return Integer.getEmfType();
		}
		return ContainedConcept.getEmfType();			
	}
	
	/**
	 * Get the expanded name of the process type
	 * 
	 * @param type
	 * @return
	 */
	public static ExpandedName getExpandedName(String type){
		if(SupportedProcessTypes.String.getName().equals(type))
			return SupportedProcessTypes.String.getType();
		else if(SupportedProcessTypes.Boolean.getName().equals(type))
			return SupportedProcessTypes.Boolean.getType();
		else if(SupportedProcessTypes.Integer.getName().equals(type))
			return SupportedProcessTypes.Integer.getType();
		else if(SupportedProcessTypes.Long.getName().equals(type))
			return SupportedProcessTypes.Long.getType();
		else if(SupportedProcessTypes.Double.getName().equals(type))
			return SupportedProcessTypes.Double.getType();
		else if(SupportedProcessTypes.ContainedConcept.getName().equals(type))
			return SupportedProcessTypes.ContainedConcept.getType();
		else if(SupportedProcessTypes.ConceptReference.getName().equals(type))
			return SupportedProcessTypes.ConceptReference.getType();
		else 
			return SupportedProcessTypes.String.getType();
	}
	
	/**
	 * Get the Map of supported types
	 * @return
	 */
	private static Map<ExpandedName, SupportedProcessTypes> getSupportedTypeMap(){
		if(supportedTypeMap == null){
			supportedTypeMap = new HashMap<ExpandedName, SupportedProcessTypes>();
			SupportedProcessTypes[] values = SupportedProcessTypes.values();
			for (SupportedProcessTypes type : values) {
				supportedTypeMap.put(type.getType(), type);
			}
		}
		return supportedTypeMap;
	}

	public String getName() {
		return name;
	}

	public ExpandedName getType() {
		return type;
	}

	public EEnumLiteral getEmfType() {
		return emfType;
	}
}
