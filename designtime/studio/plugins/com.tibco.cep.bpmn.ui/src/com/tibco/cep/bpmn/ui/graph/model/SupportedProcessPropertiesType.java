package com.tibco.cep.bpmn.ui.graph.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;

import com.tibco.be.model.rdf.RDFTnsFlavor;
import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.runtime.activity.tasks.ModelType;
import com.tibco.xml.data.primitive.ExpandedName;

/**
 * 
 * @author majha
 *
 */
public enum SupportedProcessPropertiesType {
	
	Integer("Integer",ExpandedName.parse("{http://www.w3.org/2001/XMLSchema}integer"), "iconInteger16.gif", BpmnModelClass.ENUM_PROPERTY_TYPES_Integer),
	Boolean("Boolean",ExpandedName.parse("{http://www.w3.org/2001/XMLSchema}boolean"), "iconBoolean16.gif", BpmnModelClass.ENUM_PROPERTY_TYPES_Boolean),
	Double("Double",ExpandedName.parse("{http://www.w3.org/2001/XMLSchema}double"), "iconReal16.gif",  BpmnModelClass.ENUM_PROPERTY_TYPES_Double),
	Long("Long",ExpandedName.parse("{http://www.w3.org/2001/XMLSchema}long"), "iconLong16.gif", BpmnModelClass.ENUM_PROPERTY_TYPES_Long),
	String("String",ExpandedName.parse("{http://www.w3.org/2001/XMLSchema}string"), "iconString16.gif", BpmnModelClass.ENUM_PROPERTY_TYPES_String),
	DateTime("DateTime",ExpandedName.parse("{http://www.w3.org/2001/XMLSchema}dateTime"), "iconDate16.gif", BpmnModelClass.ENUM_PROPERTY_TYPES_DateTime),
	ContainedConcept("ContainedConcept",ExpandedName.parse("{http://www.w3.org/2001/XMLSchema}containedConcept"), "iconConcept16.gif", BpmnModelClass.ENUM_PROPERTY_TYPES_Concept),
	ConceptReference("ConceptReference",ExpandedName.parse("{http://www.w3.org/2001/XMLSchema}ConceptReference"), "iconConceptRef16.gif", BpmnModelClass.ENUM_PROPERTY_TYPES_ConceptReference),
	Process("Process",ExpandedName.parse("{http://www.w3.org/2001/XMLSchema}Process"), "iconConceptRef16.gif", BpmnModelClass.ENUM_PROPERTY_TYPES_Process),;
	
	
	private static Map<ExpandedName, SupportedProcessPropertiesType> supportedTypeMap;
	private static final String Default_Image = "error_mark.png";
	private String name;
	private ExpandedName type;
	private EEnumLiteral emfType;
	private String imageIcon;
	
	private SupportedProcessPropertiesType(String name, ExpandedName type, String imageIcon , EEnumLiteral pType){
		this.name = name;
		this.type = type;
		this.imageIcon = imageIcon;
		this.emfType = pType;
	}
	
	public String getName() {
		return name;
	}

	public ExpandedName getType() {
		return type;
	}

	public String getImageIcon() {
		return imageIcon;
	}
	
	public EEnumLiteral getEmfType(){
		return emfType;
	}
	
	
	public static EEnumLiteral getEmfType(String name){
		
		if (name.equals(ModelType.CONTAINED_CONCEPT.toString())) {
			return ContainedConcept.getEmfType(); 
		}
		
		if (name.equals(ModelType.CONCEPT_REFERENCE.toString())) {
			return ConceptReference.getEmfType(); 
		}
		
		if (name.equals(ModelType.PROCESS.toString())) {
			return Process.getEmfType(); 
		}
		
		if (name.equalsIgnoreCase("Calendar")) {
			return DateTime.getEmfType();
		}
		
		Map<ExpandedName, SupportedProcessPropertiesType> map = getSupportedTypeMap();
		Collection<SupportedProcessPropertiesType> values = map.values();
		for (SupportedProcessPropertiesType supportedProcessPropertiesType : values) {
			if(supportedProcessPropertiesType.getName().equalsIgnoreCase(name))
				return supportedProcessPropertiesType.getEmfType();
		}
		if(name.equalsIgnoreCase("int"))
			return Integer.getEmfType();
		return ContainedConcept.getEmfType();			
	}
	
	public static ExpandedName getExpandedName(String type){
		
		if(SupportedProcessPropertiesType.String.equals(type))
			return SupportedProcessPropertiesType.String.getType();
		else if(SupportedProcessPropertiesType.Boolean.equals(type))
			return SupportedProcessPropertiesType.Boolean.getType();
		else if(SupportedProcessPropertiesType.Integer.equals(type))
			return SupportedProcessPropertiesType.Integer.getType();
		else if(SupportedProcessPropertiesType.Long.equals(type))
			return SupportedProcessPropertiesType.Long.getType();
		else if(SupportedProcessPropertiesType.Double.equals(type))
			return SupportedProcessPropertiesType.Double.getType();
		else if(SupportedProcessPropertiesType.ContainedConcept.equals(type))
			return SupportedProcessPropertiesType.ContainedConcept.getType();
		else if(SupportedProcessPropertiesType.ConceptReference.equals(type))
			return SupportedProcessPropertiesType.ConceptReference.getType();
		else if(SupportedProcessPropertiesType.Process.equals(type))
			return SupportedProcessPropertiesType.Process.getType();
		else 
			return SupportedProcessPropertiesType.String.getType();
		
	}

	private static Map<ExpandedName, SupportedProcessPropertiesType> getSupportedTypeMap(){
		if(supportedTypeMap == null){
			supportedTypeMap = new HashMap<ExpandedName, SupportedProcessPropertiesType>();
			SupportedProcessPropertiesType[] values = SupportedProcessPropertiesType.values();
			for (SupportedProcessPropertiesType type : values) {
				supportedTypeMap.put(type.getType(), type);
			}
		}
		return supportedTypeMap;
	}
	
	public static String getResourceType(EObjectWrapper<EClass, EObject> propdef){
		EObject item = propdef.getAttribute(BpmnMetaModelConstants.E_ATTR_ITEM_SUBJECT_REF);
		
		if (item != null ) {
			EObjectWrapper<EClass, EObject> itemDef = EObjectWrapper.wrap(item);
			String id = itemDef.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
			if(id!= null){
				ExpandedName itemDefinitionType = getItemDefinitionType(itemDef);

				SupportedProcessPropertiesType supportedProcessPropertiesType = getSupportedTypeMap()
						.get(itemDefinitionType);
				if (supportedProcessPropertiesType != null) {
					return supportedProcessPropertiesType.getName();
				}
				
				
				EObject obj =(EObject) itemDef.getAttribute(BpmnMetaModelConstants.E_ATTR_IMPORT);
				if (obj != null){
					EObjectWrapper<EClass, EObject> wrap = EObjectWrapper
							.wrap(obj);
					return wrap
							.getAttribute(BpmnMetaModelConstants.E_ATTR_LOCATION);
				}
			}else if(item.eIsProxy()){
				String fragment = ((org.eclipse.emf.ecore.InternalEObject)item).eProxyURI().fragment();
				ExpandedName parse = ExpandedName.parse(fragment);
				if (parse != null) {
					if (parse.getNamespaceURI() != null
							&& parse.getNamespaceURI().contains(
									RDFTnsFlavor.BE_NAMESPACE)) {
						String replaceAll = parse.getNamespaceURI().replaceAll(
								RDFTnsFlavor.BE_NAMESPACE, "");
						return replaceAll;
					} else if (parse.getLocalName() != null) {
						String replaceAll = parse.getLocalName().replaceAll(
								RDFTnsFlavor.BE_NAMESPACE, "");
						return replaceAll;
					}
				}
				
			}
		}
		//TODO add generic implementation
		return "";
		
	}
	
	public static String getImageIcon(EObjectWrapper<EClass, EObject> propdef){
		EObject item = propdef.getAttribute(BpmnMetaModelConstants.E_ATTR_ITEM_SUBJECT_REF);
		if (item != null) {
			EObjectWrapper<EClass, EObject> itemDef = EObjectWrapper.wrap(item);
			String id = itemDef.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
			if (id != null) {
				ExpandedName itemDefinitionType = getItemDefinitionType(itemDef);

				SupportedProcessPropertiesType supportedProcessPropertiesType = getSupportedTypeMap()
						.get(itemDefinitionType);
				if (supportedProcessPropertiesType != null) {
					return supportedProcessPropertiesType.getImageIcon();
				}
				EObjectWrapper<EClass, EObject> addDataExtensionValueWrapper = ExtensionHelper
						.getAddDataExtensionValueWrapper(propdef);
				EEnumLiteral propType = addDataExtensionValueWrapper
						.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_PROP_TYPE);

				if (propType
						.equals(BpmnModelClass.ENUM_PROPERTY_TYPES_ConceptReference)) {
					return ConceptReference.getImageIcon();
				} else
					return ContainedConcept.getImageIcon();
			}

		}
		
		
		//TODO add generic implementation
		return Default_Image;
		
	}
	
	public static String getPropertyType(EObjectWrapper<EClass, EObject> propDef){
		EObject item = propDef
				.getAttribute(BpmnMetaModelConstants.E_ATTR_ITEM_SUBJECT_REF);
		if (item != null) {
			EObjectWrapper<EClass, EObject> itemDef = EObjectWrapper.wrap(item);
			String id = itemDef.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
			if (id != null) {
				ExpandedName itemDefinitionType = getItemDefinitionType(itemDef);
				SupportedProcessPropertiesType supportedProcessPropertiesType = getSupportedTypeMap()
						.get(itemDefinitionType);
				if (supportedProcessPropertiesType != null) {
					return supportedProcessPropertiesType.getName();
				} else {
					EObjectWrapper<EClass, EObject> addDataExtensionValueWrapper = ExtensionHelper
							.getAddDataExtensionValueWrapper(propDef);
					EEnumLiteral propType = addDataExtensionValueWrapper
							.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_PROP_TYPE);

					if (propType
							.equals(BpmnModelClass.ENUM_PROPERTY_TYPES_ConceptReference)) {
						return ConceptReference.getName();
					} else
						return ContainedConcept.getName();
				}
			}

		}
		return "";
		
	}
	
	
	
	public static ExpandedName getItemDefinitionType(EObjectWrapper<EClass, EObject> itemdef) {
		String id = itemdef.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
		if(id == null)
			return null;
		id = id.replace("[]", "");
		ExpandedName parse = ExpandedName.parse(id);
		return parse;
	}
	
	
	public static SupportedProcessPropertiesType getPropertyType(String type) {
		Collection<SupportedProcessPropertiesType> values = supportedTypeMap.values();
		for (SupportedProcessPropertiesType supportedProcessPropertiesType : values) {
			if(supportedProcessPropertiesType.getName().equalsIgnoreCase(type))
				return supportedProcessPropertiesType;
		}
		if(type.equalsIgnoreCase("int"))
			return Integer;
		return String;
	}
	
}