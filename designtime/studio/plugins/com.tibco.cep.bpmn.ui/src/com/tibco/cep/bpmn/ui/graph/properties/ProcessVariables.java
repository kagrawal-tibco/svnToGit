package com.tibco.cep.bpmn.ui.graph.properties;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.graph.BpmnImages;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;

public class ProcessVariables {
	private String Name;
	private String Type;
	private Boolean Multiple;
	private String propTypeValue;
	private String ImageIcon;
	private Object process ;
	private String projectName ;
	public String getPropTypeValue() {
		return propTypeValue;
	}
	public void setPropTypeValue(String propTypeValue) {
		this.propTypeValue = propTypeValue;
	}
	private EObjectWrapper<EClass, EObject> propDef;
	ProcessVariables(){
		
	}
	ProcessVariables(String name,String Type,String propTypeValue,Boolean Multiple){
		this.Name=name;
		this.Type=Type;
		this.Multiple=Multiple;
		this.propTypeValue=propTypeValue;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getType() {
		return Type;
	}
	
	public void setType(String type) {
		Type = type;
		setImageIcon(type);
	}
	public Boolean getMultiple() {
		return Multiple;
	}
	public void setMultiple(Boolean multiple) {
		Multiple = multiple;
	}
	public EObjectWrapper<EClass, EObject> getPropDef() {
		return propDef;
	}
	public void setPropDef(EObjectWrapper<EClass, EObject> propDef) {
		this.propDef = propDef;
	}
	public String getImageIcon() {
		return ImageIcon;
	}
	public void setImageIcon(String imageIcon) {
		ImageIcon = getImageIconString(this.getType());
	}
	public static String getImageIconString(String types) {
		
		 if(types.equalsIgnoreCase(PROPERTY_TYPES.STRING.toString()/*"STRING"*/))
			return BpmnImages.STRING;
		else if(types.equalsIgnoreCase("INTEGER")|types.equalsIgnoreCase(PROPERTY_TYPES.INTEGER.toString()/*"int"*/))
				return BpmnImages.INTEGER;
		else if(types.equalsIgnoreCase(PROPERTY_TYPES.DATE_TIME.toString()/*"DATETIME"*/))
			return BpmnImages.DATETIME;
		else if(types.equalsIgnoreCase(PROPERTY_TYPES.BOOLEAN.toString()/*"BOOLEAN"*/))
				return  BpmnImages.BOOLEAN;
		else if(types.equalsIgnoreCase(PROPERTY_TYPES.DOUBLE.toString()/*"DOUBLE"*/))
				return BpmnImages.DOUBLE;
		else if(types.equalsIgnoreCase(PROPERTY_TYPES.CONCEPT.toString()/*"CONTAINEDCONCEPT"*/)||types.equalsIgnoreCase("CONCEPT"))
				return BpmnImages.CONTAINEDCONCEPT;
		else if(types.equalsIgnoreCase(PROPERTY_TYPES.CONCEPT_REFERENCE.toString()/*"CONCEPTREFERENCE"*/))
				return BpmnImages.CONCEPTREFERENCE;
		else if(types.equalsIgnoreCase(PROPERTY_TYPES.LONG.toString()/*"LONG"*/))
			return BpmnImages.LONG;
		else 
			return null;
		
	}
	
	public Boolean isNotEqualObj(ProcessVariables prvar){
		
		if(this.getMultiple()!=prvar.getMultiple())
			return true;
		else if(!this.getName().equals(prvar.getName()))
			return true;
		else if (!this.getType().equals(prvar.getType()))
			return true;
		else if(!this.getPropTypeValue().equals(prvar.getPropTypeValue())) //have to check for npe 
			return true;
		else 
			return false;
	}
	public Object getProcess() {
		return process;
	}
	public void setProcess(Object process) {
		this.process = process;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
}
