package com.tibco.cep.bpmn.model.designtime.ontology;

import com.tibco.cep.bpmn.model.designtime.ProcessAdapterFactory;
import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.ontology.impl.DefaultProcessIndex;
import com.tibco.cep.bpmn.model.designtime.utils.BpmnCommonModelUtils;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.model.designtime.utils.ROEObjectWrapper;
import com.tibco.cep.designtime.core.model.HISTORY_POLICY;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.model.Folder;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.designtime.model.process.ProcessModel;
import com.tibco.cep.designtime.model.process.SubProcessModel;
import com.tibco.cep.designtime.model.process.TaskModel;
import com.tibco.cep.designtime.model.process.TransitionModel;
import com.tibco.cep.studio.core.adapters.ConceptAdapter;
import com.tibco.cep.studio.core.adapters.PropertyDefinitionAdapter;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmModelGroup;
import com.tibco.xml.schema.SmSupport;
import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.build.MutableElement;
import com.tibco.xml.schema.build.MutableSchema;
import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.schema.impl.*;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.text.SimpleDateFormat;
import java.util.*;

public class ProcessAdapter implements ProcessModel {
	private EObject process;
	private Ontology ontology;
	private ROEObjectWrapper<EClass, EObject> wrapper;
	
	
	public ProcessAdapter(EObject process,Ontology o,Object...params) {
		this.process = process;
		this.ontology = o;
		wrapper = ROEObjectWrapper.wrap(process);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T cast(Class<T> typeOf) {
		if(ProcessIndex.class.isAssignableFrom(typeOf)){
			return (T) new DefaultProcessIndex(process);
		} else if(ROEObjectWrapper.class.isAssignableFrom(typeOf)) {
			ROEObjectWrapper<EClass, EObject> wrapper = ROEObjectWrapper.wrap(process);
			return (T) wrapper;
		}  else if(Concept.class.isAssignableFrom(typeOf)) {
//			String folderPath = wrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_FOLDER);
//			if(!folderPath.endsWith("/")){
//				folderPath = folderPath + "/";
//			}
//			final String name = wrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
//			com.tibco.cep.designtime.core.model.element.Concept concept = CommonIndexUtils
//					.createConcept(ontology.getName(), folderPath, folderPath,
//							name, null, true);
//			
//			EList<EObject> properties = wrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_PROPERTIES);
//			for (EObject prop : properties) {
//				
//				ROEObjectWrapper<EClass, EObject> pwrapper = EObjectWrapper.wrap(prop);
//				
//				final String pname = pwrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
//				
//				ROEObjectWrapper<EClass, EObject> itDef = pwrapper.getWrappedEObjectAttribute(BpmnMetaModelConstants.E_ATTR_ITEM_SUBJECT_REF);
//				
//				final boolean isArray = (Boolean)itDef.getAttribute(BpmnMetaModelConstants.E_ATTR_IS_COLLECTION);
//				
//				PROPERTY_TYPES type = getPropertyType(pwrapper);
//				
//				com.tibco.cep.designtime.core.model.element.PropertyDefinition pd = CommonIndexUtils.createPropertyDefinition(
//							concept, 
//							pname, 
//							type,
//							concept.getFullPath(), 
//							0, 
//							0, 
//							isArray, 
//							null);
//				
//				if(type == PROPERTY_TYPES.CONCEPT || type == PROPERTY_TYPES.CONCEPT_REFERENCE) {
//					String fullPath = getPropertyFullPath(itDef);
//					if(fullPath != null){
//						pd.setConceptTypePath(fullPath);
//					}
//				}
//				concept.getProperties().add(pd);
//			}
			com.tibco.cep.designtime.core.model.element.Concept concept = cast(com.tibco.cep.designtime.core.model.element.Concept.class);
//			List<PropertyDefinition> allAtributes = populateBaseAttributes(ontology, concept);
			Concept adaptor = getProcessConceptAdaptor(concept, ontology);
//			Concept adaptor = getConceptAdaptor(concept, ontology,allAtributes);
			return (T)adaptor;
		} else if(com.tibco.cep.designtime.core.model.element.Concept.class.isAssignableFrom(typeOf)) {
			
			String folderPath = wrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_FOLDER);
			if(!folderPath.endsWith("/")){
				folderPath = folderPath + "/";
			}
			final String name = wrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
			com.tibco.cep.designtime.core.model.element.Concept concept = CommonIndexUtils
					.createConcept(ontology.getName(), folderPath, folderPath,
							name, null, true);
			
			EList<EObject> properties = wrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_PROPERTIES);
			for (EObject prop : properties) {
				
				ROEObjectWrapper<EClass, EObject> pwrapper = EObjectWrapper.wrap(prop);
				
				final String pname = pwrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
				
				Object attribute = pwrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ITEM_SUBJECT_REF);
				if(attribute == null)
					continue;
				
				ROEObjectWrapper<EClass, EObject> itDef = EObjectWrapper.wrap((EObject)attribute) ;
				
				final boolean isArray = (Boolean)itDef.getAttribute(BpmnMetaModelConstants.E_ATTR_IS_COLLECTION);
				
				PROPERTY_TYPES type = getPropertyType(pwrapper);
				if(type == null)
					continue;
				
				com.tibco.cep.designtime.core.model.element.PropertyDefinition pd = CommonIndexUtils.createPropertyDefinition(
							concept, 
							pname, 
							type,
							concept.getFullPath(), 
							0, 
							0, 
							isArray, 
							null);
				
				if(type == PROPERTY_TYPES.CONCEPT || type == PROPERTY_TYPES.CONCEPT_REFERENCE) {
					String fullPath = getPropertyFullPath(itDef);
					if(fullPath != null){
						pd.setConceptTypePath(fullPath);
					}
				}
				concept.getProperties().add(pd);				
			}
			List<com.tibco.cep.designtime.core.model.element.PropertyDefinition> allAtributes = populateBaseAttributes(concept);
			concept.setAttributeDefinitions(allAtributes);
			
			return (T)concept;
		}else if(EObject.class.isAssignableFrom(typeOf)) {
			return (T) process;
		}
		
		return null;
	}
	
	protected  List<PropertyDefinition> populateBaseAttributes(Ontology o,com.tibco.cep.designtime.core.model.element.Concept concept) {
		List<PropertyDefinition> attribs = new ArrayList<PropertyDefinition>();
		List<com.tibco.cep.designtime.core.model.element.PropertyDefinition> cAttribs = populateBaseAttributes(concept);
		for(com.tibco.cep.designtime.core.model.element.PropertyDefinition c:cAttribs) {
			attribs.add(new PropertyDefinitionAdapter(c, o));
		}
		return attribs;
	}
	protected List<com.tibco.cep.designtime.core.model.element.PropertyDefinition> populateBaseAttributes(com.tibco.cep.designtime.core.model.element.Concept concept) {
		List<com.tibco.cep.designtime.core.model.element.PropertyDefinition> attributes = new ArrayList<com.tibco.cep.designtime.core.model.element.PropertyDefinition>();
		for(BASE_ATTRIBUTES n : BASE_ATTRIBUTES.values() ) {
			boolean isArray = false;
			int historyPolicy = HISTORY_POLICY.CHANGES_ONLY_VALUE;
			PROPERTY_TYPES type = PROPERTY_TYPES.get(n.getPropTypesIdx());
			
			com.tibco.cep.designtime.core.model.element.PropertyDefinition pd 
			    = CommonIndexUtils.createAttributeDefinition(
					concept, // main concept
					n.name(), // name
					type, // type
					concept.getFullPath(), // owner path
					historyPolicy, // history policy
					0, // history size
					isArray, // multi 
					null,getFullPath()); // default value
			
			attributes.add(pd);
		}	
		return attributes;
	}
	
	
	

	protected MutableElement createElement(String namespace, String localname, SmType type, MutableSchema schema) {
        //throws ConversionException {
        // If element already exists in our schema, just return that element.
        MutableElement element = null; // todo (caching)   SmSupport.getElement(schema, localname);
        // If we still don't have an element, create one.
        if (element == null) {
            MutableElement mutableElement = schema.getComponentFactory().createElement();
            mutableElement.setName(localname);
//            mutableElement.setNamespace(namespace);
            mutableElement.setSchema(schema);
            mutableElement.setType(type);
            mutableElement.setAllowedSubstitution(SmType.RESTRICTION + SmType.EXTENSION + SmType.SUBSTITUTION);
            mutableElement.setAllowedDerivation(SmType.RESTRICTION + SmType.EXTENSION);
            mutableElement.setNillable(false);
            mutableElement.setAbstract(false);
            element = mutableElement;
        }
        return element;
    }

	@Override
	public String getAlias() {
		return null;
	}



	@Override
	public String getBindingString() {
		// TODO Auto-generated method stub
		return null;
	}

	// why we need this? core adaptor factor is caching adaptor, if change the proprties in design time then try to access this 
	// this is temporary fix, we need to find better solution
	private Concept getProcessConceptAdaptor(com.tibco.cep.designtime.core.model.element.Concept concept, Ontology ontology,Object ... params){
		try {
			Constructor<ProcessConceptAdapter> constructor =
					ProcessConceptAdapter.class.getConstructor(com.tibco.cep.designtime.core.model.element.Concept.class, Ontology.class,Object[].class);
			ConceptAdapter adapter = (ConceptAdapter)constructor.newInstance(concept, ontology,params);
			return adapter;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return null;
	}
	
	// why we need this? core adaptor factor is caching adaptor, if change the proprties in design time then try to access this 
	// this is temporary fix, we need to find better solution
	protected Concept getConceptAdaptor(com.tibco.cep.designtime.core.model.element.Concept concept, Ontology ontology){
		try {
			Constructor<ConceptAdapter> constructor =
					ConceptAdapter.class.getConstructor(com.tibco.cep.designtime.core.model.element.Concept.class, Ontology.class);
			ConceptAdapter adapter = (ConceptAdapter)constructor.newInstance(concept, ontology);
			return adapter;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.process.Process#getDeployedDate()
	 */
	@Override
	public String getDeployedDate() {
		EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper
				.getAddDataExtensionValueWrapper(process);
		Date dt = valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_LAST_DEPLOYED);
		SimpleDateFormat f = new SimpleDateFormat(ProcessModel.DATE_TIME_PATTERN);
		return f.format(dt);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.Entity#getDescription()
	 */
	@Override
	public String getDescription() {
		ROEObjectWrapper<EClass, EObject> pw = ROEObjectWrapper.wrap(process);
		List<ROEObjectWrapper<EClass, EObject>> wrappedEObjectList = pw.getWrappedEObjectList(BpmnMetaModelConstants.E_ATTR_DOCUMENTATION);
		String description = "";
		if(wrappedEObjectList.size() > 0 ){
			ROEObjectWrapper<EClass, EObject> doc = wrappedEObjectList.get(0);
			description = (String)doc.getAttribute(BpmnMetaModelConstants.E_ATTR_TEXT);
		}
		return description;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.Entity#getExtendedProperties()
	 */
	@Override
	public Map<?, ?> getExtendedProperties() {
		// TODO Auto-generated method stub
		return null;
	}



	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.Entity#getFolder()
	 */
	@Override
	public Folder getFolder() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.Entity#getFolderPath()
	 */
	@Override
	public String getFolderPath() {
		// TODO Auto-generated method stub
		return (String) wrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_FOLDER);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.Entity#getFullPath()
	 */
	@Override
	public String getFullPath() {
		String fullPath = getFolderPath().trim();
        if (fullPath.endsWith("/")) {
            return String.format("%s%s", fullPath, getName());
        }
        return String.format("%s/%s", fullPath,getName());

//		if(fullPath.equals("/"))
//			fullPath += getName()+"."+ BpmnCommonIndexUtils.BPMN_PROCESS_EXTENSION;
//		else
//			fullPath += "/"+getName()+"."+ BpmnCommonIndexUtils.BPMN_PROCESS_EXTENSION;
//		return fullPath;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.Entity#getGUID()
	 */
	@Override
	public String getGUID() {
		// TODO Auto-generated method stub
		return (String) wrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.Entity#getHiddenProperties()
	 */
	@Override
	public Map<?, ?> getHiddenProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.Entity#getHiddenProperty(java.lang.String)
	 */
	@Override
	public String getHiddenProperty(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.Entity#getIcon()
	 */
	@Override
	public Icon getIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.Entity#getIconPath()
	 */
	@Override
	public String getIconPath() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.Entity#getLastModified()
	 */
	@Override
	public String getLastModified() {
		Date dt = (Date)  wrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_LAST_MODIFIED);
		SimpleDateFormat f = new SimpleDateFormat(ProcessModel.DATE_TIME_PATTERN);
		return f.format(dt);
	}


	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.process.Process#getLastModifiedAuthor()
	 */
	@Override
	public String getLastModifiedAuthor() {
		EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper
				.getAddDataExtensionValueWrapper(process);
		return valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_LAST_AUTHOR);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.Entity#getName()
	 */
	@Override
	public String getName() {
		return (String) wrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.Entity#getNamespace()
	 */
	@Override
	public String getNamespace() {
		return process.eClass().getEPackage().getNsURI();
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.Entity#getOntology()
	 */
	@Override
	public Ontology getOntology() {
		return ontology;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.process.Process#getOriginalAuthor()
	 */
	@Override
	public String getOriginalAuthor() {
		EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper
				.getAddDataExtensionValueWrapper(process);
		return valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_AUTHOR);
	}
	
	/**
	 * @return
	 */
	public String getOwnerProject() {
		// TODO Auto-generated method stub
		return (String) wrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_OWNER_PROJECT);
	}
	
	
	/**
	 * @param itemDef
	 * @return
	 */
	private String getPropertyFullPath(ROEObjectWrapper<EClass, EObject> itemDef){
		String id = itemDef.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
		ExpandedName parse = ExpandedName.parse(id);
		String localName = parse.localName;
		PROPERTY_TYPES type = PROPERTY_TYPES.get(localName);
		EObject attr = itemDef.getAttribute(BpmnMetaModelConstants.E_ATTR_IMPORT);
		
		if(type == null && attr != null){
			EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(attr);
			String attribute = wrap.getAttribute(BpmnMetaModelConstants.E_ATTR_LOCATION);
			return attribute;
		}
		
		return null;
	}
	
	
	@SuppressWarnings("unchecked")
	public Collection<PropertyDefinition> getPropertyDefinitions() {
		Concept c = cast(Concept.class);
		return c.getAllPropertyDefinitions();
	}
	

	private PROPERTY_TYPES getPropertyType(
			ROEObjectWrapper<EClass, EObject> propDef) {
		ROEObjectWrapper<EClass, EObject> itemDef = propDef.getWrappedEObjectAttribute(BpmnMetaModelConstants.E_ATTR_ITEM_SUBJECT_REF);
		String id = itemDef.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
		// if resouce like concept/event deleted from project , id here can be null
		if(id == null)
			return null;
		ExpandedName parse = ExpandedName.parse(id);
		
		String localName = parse.localName;
		boolean isCollection = (Boolean)itemDef.getAttribute(BpmnMetaModelConstants.E_ATTR_IS_COLLECTION);
		if(isCollection)
			localName = localName.replace("[]", "");
		
		if(localName.equalsIgnoreCase("integer")) {
			localName = "int";
		}
		
		
		PROPERTY_TYPES type = PROPERTY_TYPES.get(localName);
		
		if(type == null){
			EObjectWrapper<EClass, EObject> addDataExtensionValueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(propDef.getEInstance());
			EEnumLiteral propType = addDataExtensionValueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_PROP_TYPE);
			if(propType.equals(BpmnModelClass.ENUM_PROPERTY_TYPES_ConceptReference))
				type = PROPERTY_TYPES.CONCEPT_REFERENCE;
			else
				type = PROPERTY_TYPES.CONCEPT;
		}

		return type;
	}
	
	/**
	 * @return
	 */
	public int getRevision() {
		EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper
				.getAddDataExtensionValueWrapper(process);
		return (Integer) valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_VERSION);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.process.ProcessModel#getTaskElement(java.lang.String, java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getTaskElement(String id, Class<T> typeOf) {
		if(ROEObjectWrapper.class.isAssignableFrom(typeOf)) {
			final ProcessIndex pi = cast(ProcessIndex.class);
			final EObject eObj = pi.getElementById(id);
			// Check for Null to avoid NPE in ROEObjectWrapper.wrap()
			if(eObj != null) {
				return (T) ROEObjectWrapper.wrap(eObj);
			}
		} else if (TaskModel.class.isAssignableFrom(typeOf)) {
			final ProcessIndex pi = cast(ProcessIndex.class);
			final EObject eObj = pi.getElementById(id);
			// Check for Null to avoid NPE in ROEObjectWrapper.wrap()
			if(eObj != null) {
				return (T) ProcessAdapterFactory.INSTANCE.createElementAdapter(this, eObj, new Object[0]);
			}
		} else if (TransitionModel.class.isAssignableFrom(typeOf)) {
			final ProcessIndex pi = cast(ProcessIndex.class);
			final EObject eObj = pi.getElementById(id);
			// Check for Null to avoid NPE in ROEObjectWrapper.wrap()
			if(eObj != null) {
				return (T) ProcessAdapterFactory.INSTANCE.createElementAdapter(this, eObj, new Object[0]);
			}
		}else if (SubProcessModel.class.isAssignableFrom(typeOf)) {
			final ProcessIndex pi = cast(ProcessIndex.class);
			final EObject eObj = pi.getElementById(id);
			// Check for Null to avoid NPE in ROEObjectWrapper.wrap()
			if(eObj != null) {
				return (T) new SubProcessAdapter(eObj,this, ontology, new Object[0]);
			}
		}
		return null;
	}
	
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.Entity#getTransientProperties()
	 */
	@Override
	public Map<?, ?> getTransientProperties() {
		return Collections.emptyMap();
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.Entity#getTransientProperty(java.lang.String)
	 */
	@Override
	public Object getTransientProperty(String key) {
		return null;
	}

	/**
	 * @param schema
	 * @param dpd
	 * @return
	 */
	protected SmType getTypeForPropertyDefinition(DefaultSchema schema, PropertyDefinition dpd) {
        SmType type = null;
        switch (dpd.getType()) {
        case PropertyDefinition.PROPERTY_TYPE_BOOLEAN :
            type = XSDL.BOOLEAN;
            break;
        case PropertyDefinition.PROPERTY_TYPE_INTEGER :
            type = XSDL.INTEGER;
            break;
        case PropertyDefinition.PROPERTY_TYPE_REAL :
            type = XSDL.DOUBLE;
            break;
        case PropertyDefinition.PROPERTY_TYPE_STRING :
            type = XSDL.STRING;
            break;
        case PropertyDefinition.PROPERTY_TYPE_DATETIME :
            type = XSDL.DATETIME;
            break;
        case PropertyDefinition.PROPERTY_TYPE_LONG :
            type = XSDL.LONG;
            break;
        case PropertyDefinition.PROPERTY_TYPE_CONCEPT :
        case PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE :
                type = SmSupport.getType(schema, dpd.getConceptTypePath());
                if (type != null) {
                    break;
                }
//                DesignerProject index = StudioProjectCache.getInstance().getIndex(getOwnerProject());
//                CoreOntologyAdapter co = new CoreOntologyAdapter(index,ontology);
                
                Concept concept =ontology.getConcept(dpd.getConceptTypePath());
                type = concept.toSmElement().getType();
                schema.addSchemaComponent(type);
                break;
        }
        return type;
    }

	/**
	 * @param attr
	 * @return
	 */
	protected boolean isValidExtensionAttribute(String attr){
		EObjectWrapper<EClass, EObject> objWrapper = EObjectWrapper.wrap(process);
		boolean validDataExtensionAttribute = ExtensionHelper.isValidDataExtensionAttribute(objWrapper,attr);
		
		return validDataExtensionAttribute;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.Entity#serialize(java.io.OutputStream)
	 */
	@Override
	public void serialize(OutputStream out) throws IOException {
		
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.process.ProcessModel#toSmElement()
	 */
	public SmElement toSmElement() {
		String fullPath = getFullPath();
        DefaultComponentFactory factory = new DefaultComponentFactory();
        DefaultSchema schema = (DefaultSchema) factory.createSchema();
        schema.setNamespace(fullPath);

        // The complex type for this Process
        DefaultType type = (DefaultType) factory.createType();
        type.setName(getName());
        type.setNamespace(fullPath);
        type.setSchema(schema);
        type.setComplex();

        // todo Might not need these - rkt
        type.setAllowedDerivation(SmType.RESTRICTION + SmType.EXTENSION);
        type.setAllowedSubstitution(SmType.RESTRICTION + SmType.EXTENSION);
        type.setMixedContent(false);
        type.setIsOnAttributesOnly(false);
        type.setAbstract(false);
        type.setNative(false);

//        Concept superConcept = getSuperConcept();
//        if (superConcept != null) {
//            type.setBaseType(superConcept.toSmElement().getType());
//        }
        schema.addSchemaComponent(type); /* Add the complex type to the schema */

        /* The element for this Concept within the schema */
        //DefaultElement element = (DefaultElement) factory.createElement();
        MutableElement element = createElement(fullPath, getName(), type, schema);
        //element.setName(fullPath);
        schema.addSchemaComponent(element); /* Add an element of this type to the schema */

        /* Create the sub-element group for this type */
        DefaultModelGroup modelGroup = (DefaultModelGroup) factory.createModelGroup();
        modelGroup.setCompositor(SmModelGroup.SEQUENCE);
        modelGroup.setSchema(schema);

        Collection<PropertyDefinition> props = getPropertyDefinitions();
        Iterator<PropertyDefinition> propIt = props.iterator();
        while (propIt.hasNext()) {
            PropertyDefinition dpd = (PropertyDefinition) propIt.next();
            DefaultParticle propParticle = (DefaultParticle) factory.createParticle();
            if (dpd.isArray()) {
                propParticle.setMinOccurrence(0);
                propParticle.setMaxOccurrence(Integer.MAX_VALUE);
            } else {
                propParticle.setMinOccurrence(0);
                propParticle.setMaxOccurrence(1);
            }
            SmType propElemType = getTypeForPropertyDefinition(schema, dpd);
            MutableElement propElement = createElement(fullPath, dpd.getName(), propElemType, schema);
            //propElement.setName(dpd.getName());
            //propElement.setNamespace(fullPath);
            //propElement.setType(propElemType);
            propParticle.setTerm(propElement);
            modelGroup.addParticle(propParticle);
        }//endwhile
        type.setContentModel(modelGroup);
        return element;
	}
	
	
	
	public PropertyDefinition getAttributeDefinition(String attributeName) {
		Concept c = cast(Concept.class);
		return c.getAttributeDefinition(attributeName);
	}
	
	@SuppressWarnings("unchecked")
	public Collection<PropertyDefinition> getAttributeDefinitions() {
		Concept c = cast(Concept.class);
		return c.getAttributeDefinitions();
	}

	@Override
	public Collection<SubProcessModel> getSubProcesses() {
		Collection<EObject> subprocess = BpmnCommonModelUtils.getSubprocess(wrapper);
		List<SubProcessModel> models = new ArrayList<SubProcessModel>();
		for (EObject eObject : subprocess) {
			models.add(new SubProcessAdapter(eObject, this, ontology, new Object[0]));
		}
		return models;
	}
	
	@Override
	public SubProcessModel getSubProcessById(String id) {
		Collection<EObject> subprocess = BpmnCommonModelUtils.getSubprocess(wrapper);
		for (EObject eObject : subprocess) {
			ROEObjectWrapper<EClass, EObject> wrapper = ROEObjectWrapper.wrap(eObject);
			String sid = (String) wrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
			if(sid.equals(id)) {				
				return new SubProcessAdapter(eObject, this, ontology, new Object[0]);
			}
		}
		return null;
	}
	
}
