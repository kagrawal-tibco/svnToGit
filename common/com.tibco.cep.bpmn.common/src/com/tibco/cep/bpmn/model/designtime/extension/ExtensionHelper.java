package com.tibco.cep.bpmn.model.designtime.extension;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.tibco.cep.bpmn.model.designtime.BpmnModelCache;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.BpmnCommonIndexUtils;
import com.tibco.cep.bpmn.model.designtime.utils.CommonECoreHelper;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.xml.data.primitive.ExpandedName;

/**
 * @author pdhar
 *
 */
public class ExtensionHelper {

	/**
	 * @param type
	 * @return extension definition for a given type
	 */
	public static EObject getExtensionDefinitionForType(EClass type) {
		return BpmnMetaModel.INSTANCE.getExtensionDefinition(type);
	}
	

	/**
	 * get the extension attribute definition for the given element and <link>EXTENSION_ATTRIBUTE_NAME</link>
	 * @param element
	 * @return
	 */
	public static EObject getAddDataExtensionAttrDefinition(EObject element) {
		return getAddExtensionAttrDefintion(element, BpmnMetaModelExtensionConstants.EXTENSION_ATTRIBUTE_NAME);
	}
	
	/**
	 * @param element
	 * @return
	 */
	public static boolean hasExtensionAttrDefinition(EObject element) {
		return hasExtensionAttrDefinition(element, BpmnMetaModelExtensionConstants.EXTENSION_ATTRIBUTE_NAME);
	}
	
	/**
	 * @param element
	 * @param extAttrName
	 * @return
	 */
	public static boolean hasExtensionAttrDefinition(EObject element,String extAttrName) {
		EObjectWrapper<EClass, EObject> elementWrapper = EObjectWrapper.wrap(element);
		if(BpmnCommonIndexUtils.isBaseElement(element)) {
			EList<EObject> extDefs = elementWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_EXTENSION_DEFINITIONS);
			if(extDefs.size() > 0) {
				for(EObject extDef: extDefs) {
					EObjectWrapper<EClass, EObject> extDefWrapper = EObjectWrapper.wrap(extDef);
					EList<EObject> attrDefs = extDefWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_EXTENSION_ATTRIBUTE_DEFINITIONS);
					for(EObject attrDef:attrDefs) {
						EObjectWrapper<EClass, EObject> attrDefWrapper = EObjectWrapper.wrap(attrDef);
						String attrDefName = (String) attrDefWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
						if(attrDefName.equals(extAttrName)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * get extension attribute definition for the given element and attribute name
	 * @param element
	 * @param extAttrName
	 * @return
	 */
	public static EObject getAddExtensionAttrDefintion(EObject element,String extAttrName) {
		EObjectWrapper<EClass, EObject> elementWrapper = EObjectWrapper.wrap(element);
		if(BpmnCommonIndexUtils.isBaseElement(element)) {
			EList<EObject> extDefs = elementWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_EXTENSION_DEFINITIONS);
			if(extDefs.size() > 0) {
				for(EObject extDef: extDefs) {
					EObjectWrapper<EClass, EObject> extDefWrapper = EObjectWrapper.wrap(extDef);
					EList<EObject> attrDefs = extDefWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_EXTENSION_ATTRIBUTE_DEFINITIONS);
					for(EObject attrDef:attrDefs) {
						EObjectWrapper<EClass, EObject> attrDefWrapper = EObjectWrapper.wrap(attrDef);
						String attrDefName = (String) attrDefWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
						if(attrDefName.equals(extAttrName)) {
							return attrDef;
						}
					}
				}
			} 
			EObject extDef = getExtensionDefinitionForModel(element);
			if (extDef != null) {
				elementWrapper.addToListAttribute(
						BpmnMetaModelConstants.E_ATTR_EXTENSION_DEFINITIONS,
						extDef);
				return getExtensionAttrDefinition(extDef, extAttrName);
			}
		}
		return null;
	}

	private static EObject getExtensionDefinitionForModel(EObject element) {
		EObjectWrapper<EClass, EObject> process = getProcess(element);
		EObject extensionDefinition = null;
		if (process != null) {
			String projectName = process
					.getAttribute(BpmnMetaModelConstants.E_ATTR_OWNER_PROJECT);
			EObject index = BpmnModelCache.getInstance().getIndex(projectName);

			if (index != null) {
				EObjectWrapper<EClass, EObject> indexWrapper = EObjectWrapper
						.wrap(index);
				EList<EObject> extensions = indexWrapper
						.getListAttribute(BpmnMetaModelConstants.E_ATTR_EXTENSIONS);
				for (EObject object : extensions) {
					EObjectWrapper<EClass, EObject> objWrapper = EObjectWrapper
							.wrap(object);
					EObject extnDefinition = objWrapper
							.getAttribute(BpmnMetaModelConstants.E_ATTR_DEFINITION);
					EObjectWrapper<EClass, EObject> extnDefinitionWrapper = EObjectWrapper
							.wrap(extnDefinition);
					String name = extnDefinitionWrapper
							.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
					if (name.equals(CommonECoreHelper.getExpandedName(
							element.eClass()).getExpandedForm())) {
						extensionDefinition = extnDefinition;
						break;
					}
				}
			}
		}
		if (extensionDefinition == null){
			extensionDefinition = getExtensionDefinitionForType(element
					.eClass());
		}

		return extensionDefinition;
	}
	
	private static EObjectWrapper<EClass, EObject> getProcess(EObject element) {
		if(BpmnModelClass.PROCESS.isSuperTypeOf(element.eClass())) {
			return EObjectWrapper.wrap(element);
		}
		EObject container = element.eContainer();
		while(container != null && !BpmnModelClass.PROCESS.isSuperTypeOf(container.eClass())) {
			container = container.eContainer();
		}
		EObjectWrapper<EClass, EObject> useInstance = null;
		if(container != null){
			useInstance = EObjectWrapper.wrap(container);
		}
		return useInstance;
	}
	
	public static EObject getExtensionAttrDefintionByType(EObject element ,String extAttrName) {
		EObject extDef = getExtensionDefinitionForModel(element);

		return getExtensionAttrDefinition(extDef, extAttrName);
	}
	
	private static EObject getExtensionAttrDefinition(EObject extDef, String extAttrName){
		if(extDef != null) {
			EObjectWrapper<EClass, EObject> extDefWrapper = EObjectWrapper.wrap(extDef);
			EList<EObject> attrDefs = extDefWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_EXTENSION_ATTRIBUTE_DEFINITIONS);
			for(EObject attrDef:attrDefs) {
				EObjectWrapper<EClass, EObject> attrDefWrapper = EObjectWrapper.wrap(attrDef);
				String attrDefName = (String) attrDefWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
				if(attrDefName.equals(extAttrName)) {
					return attrDef;
				}
			}
		}
		return null;
	}
	
//	/**
//	 * @param modelObjWrapper
//	 * @param attributeName
//	 * @param attributeValue
//	 */
//	public static void setDataExtensionValueAttribute(
//			EObjectWrapper<EClass, EObject> modelObjWrapper,
//			String attributeName,
//			Object attributeValue) {
//		setDataExtensionValueAttribute(modelObjWrapper.getEInstance(), attributeName, attributeValue);
//	}
//	
//	/**
//	 * set attribute value on the data extension attribute EObject
//	 * @param modelObj
//	 * @param attributeName
//	 * @param attributeValue
//	 */
//	public static void setDataExtensionValueAttribute(EObject modelObj ,String attributeName,Object attributeValue) {
//		EObjectWrapper<EClass, EObject> dataExtValWrapper = getAddDataExtensionValueWrapper(modelObj);
//		dataExtValWrapper.setAttribute(attributeName, attributeValue);
//	}
	
	/**
	 * get or add extension value object for the given element and <link>EXTENSION_ATTRIBUTE_NAME</link>
	 * @param elementWrapper
	 * @return
	 */
	public static EObjectWrapper<EClass, EObject> getAddDataExtensionValueWrapper(EObjectWrapper<EClass, EObject> elementWrapper) {
		EObject eObj = getAddExtensionValue(elementWrapper.getEInstance(), BpmnMetaModelExtensionConstants.EXTENSION_ATTRIBUTE_NAME);
//		assert eObj != null;
		// quick fix to remove NPE, ideally it should no be null. Need to investigate why value could be null;/TODO 
		if(eObj == null)
			return null;
		
		return EObjectWrapper.wrap(eObj);
	}
	
	/**
	 * get or add extension value object for the given element and <link>EXTENSION_ATTRIBUTE_NAME</link>
	 * @param element
	 * @return
	 */
	public static EObjectWrapper<EClass, EObject> getAddDataExtensionValueWrapper(EObject element) {
		EObject addExtensionValue = getAddExtensionValue(element, BpmnMetaModelExtensionConstants.EXTENSION_ATTRIBUTE_NAME);
		if(addExtensionValue != null)
			return EObjectWrapper.wrap(addExtensionValue);
		else
			return null;
	}
	
	/**
	 * get or add extension value object for the given element and <link>EXTENSION_ATTRIBUTE_NAME</link>
	 * @param element
	 * @return
	 */
	public static EObject getAddDataExtensionValue(EObject element) {
		return getAddExtensionValue(element, BpmnMetaModelExtensionConstants.EXTENSION_ATTRIBUTE_NAME);
	}
	
	public static EObject getAddExtensionValueOld(EObject element,String exAttrName) {
		EObject extAttrDef = getAddDataExtensionAttrDefinition(element);
		if(extAttrDef != null) {
			EObjectWrapper<EClass, EObject> elementWrapper = EObjectWrapper.wrap(element);
			EList<EObject> extValues = elementWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_EXTENSION_VALUES);
			if(extValues.size() > 0) {
				for(EObject extValue: extValues) {
					EObjectWrapper<EClass, EObject> extValWrapper = EObjectWrapper.wrap(extValue);
					EObject valExtAttrDef = extValWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_EXTENSION_ATTRIBUTE_DEFINITION);
					if(valExtAttrDef != null && valExtAttrDef.equals(extAttrDef)) {
						return extValWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_VALUE);
					} else if(valExtAttrDef!= null && EcoreUtil.getURI(valExtAttrDef).equals(EcoreUtil.getURI(extAttrDef))) {
						return extValWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_VALUE);
					}
				}
			} else {
				return createExtensionValue(element,extAttrDef);
			}
		}
		return null;
	}
	
	/**
	 * get or add a extension value object for the given element and attribute name
	 * @param element
	 * @return
	 */
	public static EObject getAddExtensionValue(EObject element,String exAttrName) {
		EObjectWrapper<EClass, EObject> elementWrapper = EObjectWrapper.wrap(element);
		EObject extAttrDef = getAddDataExtensionAttrDefinition(element);
		if(extAttrDef != null) {
			EList<EObject> extValues = elementWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_EXTENSION_VALUES);
			if(extValues.size() > 0) {
				for(EObject extValue: extValues) {
					EObjectWrapper<EClass, EObject> extValWrapper = EObjectWrapper.wrap(extValue);
					EObject valExtAttrDef = extValWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_EXTENSION_ATTRIBUTE_DEFINITION);
					if(valExtAttrDef != null && valExtAttrDef.equals(extAttrDef)) {
						return extValWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_VALUE);
					} else if(valExtAttrDef!= null && EcoreUtil.getURI(valExtAttrDef).equals(EcoreUtil.getURI(extAttrDef))) {
						return extValWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_VALUE);
					}else if(valExtAttrDef == null){
						//if extension attribute definition is missing, that's an error
						extValWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_EXTENSION_ATTRIBUTE_DEFINITION, extAttrDef);
						return extValWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_VALUE);
					}
				}
			} else {
				return createExtensionValue(element,extAttrDef);
			}
		}
		EList<EObject> extValues = elementWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_EXTENSION_VALUES);
		if(extValues.size() > 0) {
			EObject extValue = extValues.get(0);
			EObjectWrapper<EClass, EObject> extValWrapper = EObjectWrapper.wrap(extValue);
			return extValWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_VALUE);
		}
		return null;
	}
	
	/**
	 * Create an extension value Object for a given element
	 * @param element
	 * @return
	 */
	public static EObject createDataExtensionValue(EObject element) {
		EObject extAttrDef = getAddDataExtensionAttrDefinition(element);
		return createExtensionValue(element,extAttrDef);
	}


	/**
	 * Create an extension value Object for the given attribute definition and element
	 * @param element
	 * @param extAttrDef
	 * @return
	 */
	public static EObject createExtensionValue(EObject element,EObject extAttrDef) {
		if(extAttrDef != null) {
			EObjectWrapper<EClass, EObject> elementWrapper = EObjectWrapper.wrap(element);
			EObjectWrapper<EClass, EObject> extAttrDefWrapper = EObjectWrapper.wrap(extAttrDef);
			// create the value object
			EObjectWrapper<EClass, EObject> extAttrValueWrapper = EObjectWrapper.createInstance(BpmnModelClass.EXTENSION_ATTRIBUTE_VALUE);
			
			// get the extension data type from the extension attribute and instantiate
			String dataType = (String)extAttrDefWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_TYPE);
			EObjectWrapper<EClass, EObject> valueObjectWrapper 
					= EObjectWrapper.createInstance(ExpandedName.parse(dataType));
			extAttrValueWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_EXTENSION_ATTRIBUTE_DEFINITION, extAttrDef);
			extAttrValueWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_VALUE, valueObjectWrapper.getEInstance());
			// add the value to the element
			elementWrapper.addToListAttribute(BpmnMetaModelConstants.E_ATTR_EXTENSION_VALUES, extAttrValueWrapper.getEInstance());
			return valueObjectWrapper.getEInstance();
		}
		return null;
	}
	
	public static EObject setDataExtensionValue(EObjectWrapper<EClass, EObject> elementWrapper ,EObject value) {
		return setDataExtensionValue(elementWrapper.getEInstance(), value);
	}
	
	
	/**
	 * Set the new Value object
	 * @param element
	 * @param value
	 * @return the old Value object
	 */
	public static EObject setDataExtensionValue(EObject element,EObject value) {
		EObject extAttrDef = getAddDataExtensionAttrDefinition(element);
		EObject oldValue = null;
		if(extAttrDef != null) {
			EObjectWrapper<EClass, EObject> elementWrapper = EObjectWrapper.wrap(element);
			EList<EObject> extValues = elementWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_EXTENSION_VALUES);
			if(extValues.size() > 0) {
				for(EObject extValue: extValues) {
					EObjectWrapper<EClass, EObject> extValWrapper = EObjectWrapper.wrap(extValue);
					EObject valExtAttrDef = extValWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_EXTENSION_ATTRIBUTE_DEFINITION);
					if(valExtAttrDef.equals(extAttrDef)) {
						oldValue =  extValWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_VALUE);
					}
				}
				extValues.clear();
				extValues.add(value);
			} else {
				// no existing value so just add it
				extValues.add(value);
			}
		}
		return oldValue;
	}


	/**
	 * verify if a structural feature by the given key exists in the data extension
	 * @param modelWrapper
	 * @param key
	 * @return true or false
	 */
	public static <C extends EClass, E extends EObject> boolean isValidDataExtensionAttribute(
			EObjectWrapper<C, E> modelWrapper, 
			String key) {
		
		EObject extdef = getExtensionDefinitionForModel(modelWrapper.getEInstance());
		if(extdef == null)
			return false;
		EObjectWrapper<EClass, EObject> extDefWrapper = EObjectWrapper.wrap(extdef);
		EList<EObject> attrDefs = extDefWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_EXTENSION_ATTRIBUTE_DEFINITIONS);
		for(EObject attrDef:attrDefs) {
			EObjectWrapper<EClass, EObject> attrDefWrapper = EObjectWrapper.wrap(attrDef);
			String attrDefName = (String) attrDefWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
			if(attrDefName.equals(BpmnMetaModelExtensionConstants.EXTENSION_ATTRIBUTE_NAME)) {
				String extTypeStr = (String) attrDefWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_TYPE);
				ExpandedName extTypeName = ExpandedName.parse(extTypeStr);
				EClass extType = BpmnMetaModel.INSTANCE.getEClass(extTypeName);
				return extType.getEStructuralFeature(key) != null;
			}
		}
		return false;
		
	}
	
	@SuppressWarnings("unchecked")
	public static <C extends EClass, E extends EObject,T> T getExtensionAttributeValue(
			EObjectWrapper<C, E> model, String attribute) {
		Object attr = null;
		if (ExtensionHelper.isValidDataExtensionAttribute(model, attribute)) {
			EObject valueObj = ExtensionHelper.getAddDataExtensionValue(model
					.getEInstance());
			if(valueObj != null) {
				EObjectWrapper<EClass, EObject> valWrapper = EObjectWrapper
				.wrap(valueObj);
				attr = valWrapper.getAttribute(attribute);
			}
		}
		return (T) attr;
	}
	
	public static <C extends EClass, E extends EObject,T> void setExtensionAttributeValue(
			EObject model, String attribute,Object val) {
		EObjectWrapper<EClass, EObject> modelWrapper = EObjectWrapper.wrap(model);
		if (ExtensionHelper.isValidDataExtensionAttribute(modelWrapper, attribute)) {
			EObject valueObj = ExtensionHelper.getAddDataExtensionValue(model);
			if(valueObj != null) {
				EObjectWrapper<EClass, EObject> valWrapper = EObjectWrapper
				.wrap(valueObj);
				valWrapper.setAttribute(attribute,val);
			}
		}
		return;
	}
	
	
	public static <C extends EClass, E extends EObject,T> void setExtensionListAttributeValue(
			EObject model, String attribute,EObject val) {
		EObjectWrapper<EClass, EObject> modelWrapper = EObjectWrapper.wrap(model);
		if (ExtensionHelper.isValidDataExtensionAttribute(modelWrapper, attribute)) {
			EObject valueObj = ExtensionHelper.getAddDataExtensionValue(model);
			if(valueObj != null) {
				EObjectWrapper<EClass, EObject> valWrapper = EObjectWrapper
				.wrap(valueObj);
				valWrapper.addToListAttribute(attribute,val);
			}
		}
		return;
	}
	
	public static  <C extends EClass, E extends EObject,T> void resetExtensionDeinitions(EObjectWrapper<EClass, EObject> modelWrapper){
		modelWrapper.clearListAttribute(BpmnMetaModelConstants.E_ATTR_EXTENSION_DEFINITIONS);
		EObject extensionAttrDefinition = getAddDataExtensionAttrDefinition(modelWrapper.getEInstance());
		EList<EObject> extValues = modelWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_EXTENSION_VALUES);
		if(extValues.size() > 0) {
			for(EObject extValue: extValues) {
				EObjectWrapper<EClass, EObject> extValWrapper = EObjectWrapper.wrap(extValue);
				extValWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_EXTENSION_ATTRIBUTE_DEFINITION, extensionAttrDefinition);
			}
		}
	}
	
}
