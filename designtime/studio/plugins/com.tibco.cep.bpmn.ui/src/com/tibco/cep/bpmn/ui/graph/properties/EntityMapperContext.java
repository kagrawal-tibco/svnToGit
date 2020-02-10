package com.tibco.cep.bpmn.ui.graph.properties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.be.model.functions.Predicate;
import com.tibco.be.model.rdf.RDFTnsFlavor;
import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.util.shared.ModelConstants;
import com.tibco.cep.bpmn.core.BpmnCorePlugin;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.ontology.ProcessAdapter;
import com.tibco.cep.bpmn.model.designtime.ontology.ProcessOntologyAdapter;
import com.tibco.cep.bpmn.model.designtime.ontology.symbols.SymbolEntryImpl;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.runtime.templates.MapperConstants;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.utils.BpmnXSLTutils;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.designtime.model.process.ProcessModel;
import com.tibco.cep.mapper.xml.xdata.xpath.VariableDefinition;
import com.tibco.cep.repo.BEProject;
import com.tibco.cep.repo.provider.GlobalVariablesProvider;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.manager.GlobalVariablesMananger;
import com.tibco.cep.studio.core.repo.emf.StudioEMFProject;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmComponent;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmFactory;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.build.MutableElement;
import com.tibco.xml.schema.build.MutableSchema;
import com.tibco.xml.schema.build.MutableSupport;
import com.tibco.xml.schema.build.MutableType;
import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.schema.impl.DefaultComponentFactory;
import com.tibco.xml.schema.impl.DefaultType;
import com.tibco.xml.schema.xtype.SmSequenceTypeFactory;

public class EntityMapperContext implements MapperContext {

	private IProject project;
	private BEProject beProject;
	private List<VariableDefinition> definitions;
	private AbstractMapperControl mapper;
	private boolean isInputMapper= false;
	private MapperPropertySection mapperPropertySection;
	private SmElement smElement;
	//	public boolean containsLoop ;
	//	public String loopVartype ;
	//	public String loopvarPath ;
	//	public String loopVarName ;
	//	public Boolean loopVarisArray;
	private String integerType = "INTEGER" ;
	/**
	 * @param mappedEntity
	 */
	public EntityMapperContext(IProject project) {
		this.project = project;
		BpmnCorePlugin.getCache(project.getName(), true);
		this.beProject = new StudioEMFProject(project.getName());
		definitions = new ArrayList<VariableDefinition>();
		try {
			this.beProject.load();
		} catch (Exception e) {
			BpmnUIPlugin.log(e);
		}

	}

	public void setMapper(AbstractMapperControl mapper) {
		this.mapper = mapper;
	}

	@Override
	public GlobalVariablesProvider getGlobalVariables() {
		return GlobalVariablesMananger.getInstance().getProvider(getProject().getName());
	}

	@Override
	public IProject getProject() {
		return project;
	}

	public SmElement getSmElement() {
		return smElement;
	}


	/**
	 * @return the beProject
	 */
	public BEProject getBEProject() {
		return beProject;
	}

	/**
	 * @param beProject the beProject to set
	 */
	public void setBEProject(BEProject beProject) {
		this.beProject = beProject;
	}

	public  void clearDefinitions(){
		definitions.clear();
	}

	public List<VariableDefinition> getDefinitions() {
		return definitions;
	}

	public void addSimpleTypeDefinition(String localName, String returnType, boolean isArray){
		if(isArray){
			//create a new type with the name of the array variable
			MutableSchema schema = SmFactory.newInstance().createMutableSchema();
			MutableType type = MutableSupport.createType(schema, localName);
			SmType smType = getSimpleTypeForReturnType(returnType);
			if(smType == null)
				return;
			MutableSupport.addLocalElement(type, ModelConstants.ARRAY_ELEMENT_NAME, smType, 0, Integer.MAX_VALUE);
			ExpandedName makeName = ExpandedName.makeName(localName);
			definitions.add( new VariableDefinition(makeName, SmSequenceTypeFactory.createElement(type)));

		}else{
			MutableSchema schema = SmFactory.newInstance().createMutableSchema();
			MutableType type = MutableSupport.createType(schema, localName);

			SmType smType = getSimpleTypeForReturnType(returnType);
			if(smType == null)
				return;

			MutableSupport.addLocalElement(type, localName, smType, 1,1);
			SmSequenceType seType = SmSequenceTypeFactory.createElement(type);
			ExpandedName makeName = ExpandedName.makeName(localName);
			definitions.add(new VariableDefinition(makeName, seType));
		}
	}

	public void addDefinition(String localName, String entityPath, boolean isArray){
		if(isArray){
			//create a new type with the name of the array variable
			MutableSchema schema = SmFactory.newInstance().createMutableSchema();
			MutableType type = MutableSupport.createType(schema, localName);
			//            type.setDerivationMethod(SmComponent.EXTENSION);
			SmElement smElement = MapperControl.getSmElementFromPath(project.getName(), entityPath);
			SmType smType = smElement.getType();
			MutableSupport.addLocalElement(type, ModelConstants.ARRAY_ELEMENT_NAME, smType, 0, Integer.MAX_VALUE);
			ExpandedName makeName = ExpandedName.makeName(localName);
			definitions.add( new VariableDefinition(makeName, SmSequenceTypeFactory.createElement(type)));
		}else{
			SmElement smElementFromPath =MapperControl.getSmElementFromPath(project.getName(), entityPath);
			SmSequenceType seType = SmSequenceTypeFactory.create(smElementFromPath);
			ExpandedName makeName = ExpandedName.makeName(localName);
			definitions.add(new VariableDefinition(makeName, seType));
		}
	}

	MutableSchema getSchema(DefaultComponentFactory factory, String fullpath) {
		String ns=RDFTnsFlavor.BE_NAMESPACE+ fullpath;
		//        MutableSchema cached = (DefaultSchema) factory.createSchema();
		MutableSchema cached = DefaultComponentFactory.getTnsAwareInstance().createSchema();
		cached.setNamespace(ns);
		cached.setFlavor(XSDL.FLAVOR);

		MutableType type = MutableSupport.createType(cached, fullpath);
		type.setNamespace(ns);
		cached.addSchemaComponent(type);

		MutableElement createElement = MutableSupport.createElement(cached, fullpath, type);
		createElement.setNamespace(ns);
		cached.addSchemaComponent(createElement);

		//schemaCache.put(ns, cached);
		//}
		return cached;
	}

	MutableType getSmType(DefaultComponentFactory factory, MutableSchema schema, ExpandedName typeName) {

		MutableType smType = (DefaultType) factory.createType();
		smType.setExpandedName(typeName);
		smType.setSchema(schema);
		smType.setComplex();
		smType.setAllowedDerivation(SmComponent.EXTENSION | SmComponent.RESTRICTION);
		//        smType.setDerivationMethod(SmComponent.EXTENSION);

		schema.addSchemaComponent(smType);


		return smType;
	}

	/**
	 * Adding Java Task definition for Mapper
	 * @param symbol
	 */
	public void addJavaTaskDefinition(SymbolEntryImpl symbol, String projectName) {
		EObjectWrapper<EClass, EObject> typeWrapper = EObjectWrapper.wrap(symbol.getType());
		String returnType = typeWrapper.toString();
		boolean isArray = symbol.isArray();
		String name = MapperConstants.RETURN;
		if (!symbol.isPrimitive()) {
			String path = symbol.getPath();
			com.tibco.cep.designtime.core.model.Entity entity = CommonIndexUtils.getEntity(projectName, path);
			if (entity != null) {
				String fullPath = CommonIndexUtils.getEntity(projectName,	path).getFullPath();
				addDefinition(name, fullPath, isArray);
			}
		} else {
			addSimpleTypeDefinition(name, returnType, isArray);
		}
	}


	//	public void addJavaTaskDefinition(String localName, String entityPath,
	//			boolean isArray) {
	//		String returnType = BpmnModelUtils.getJavaResourceReturnType();
	//		DefaultComponentFactory factory = new DefaultComponentFactory();
	//		MutableSchema schema = getSchema(factory, "/javaTask");
	//		ExpandedName mtInputTypeName = ExpandedName.makeName(
	//				schema.getNamespace(), "javaTask");
	//		MutableType mtInType = getSmType(factory, schema, mtInputTypeName);
	//		MutableElement elem = factory.createElement();
	//		elem.setType(mtInType);
	//		elem.setSchema(schema);
	//		elem.setExpandedName(mtInputTypeName);
	//		schema.addSchemaComponent(elem);
	//		MutableType argsType = MutableSupport
	//				.createType(schema, null, null);
	//		argsType.setComplex();
	//		MutableSupport.addLocalElement(mtInType, "args", argsType, 1, 1);
	//		SmType smType = null;
	//		if (returnType.equals("String")) {
	//			smType = XSDL.STRING;
	//
	//		} else if (returnType.equals("Boolean")) {
	//			smType = XSDL.BOOLEAN;
	//		} else if (returnType.equals("Double")) {
	//			smType = XSDL.DOUBLE;
	//		} else if (returnType.equals("Integer")) {
	//			smType = XSDL.INTEGER;
	//		} else if (returnType.equals("Object")) {
	//			smType = XSDL.ANY_TYPE;
	//		} else {
	//			smType = XSDL.ANY_TYPE;
	//		}
	//		MutableElement smEle = MutableSupport.createElement(schema, "ret",
	//				smType);
	//		MutableSupport.addElement(argsType, smEle, 1, 1);
	//		DefaultModelGroup contentModel = (DefaultModelGroup) mtInType
	//				.getContentModel();
	//		contentModel.setSchema(schema);
	//		elem.setSchema(schema);
	//		ExpandedName makeName = ExpandedName.makeName("javaTask");
	//		smElement = elem;
	//		definitions.add(new VariableDefinition(makeName,
	//				SmSequenceTypeFactory.createElement(elem.getType())));
	//	}


	public void addDefinition(String localName, Entity entity, boolean isArray){
		if(!(entity instanceof ProcessModel)){
			addDefinition(localName, entity.getFullPath(), isArray);
			return;
		}
		//		if ( containsLoop && !isInputMapper) {
		//			isArray = true ;
		//		}
		if(isArray){
			//create a new type with the name of the array variable
			MutableSchema schema = SmFactory.newInstance().createMutableSchema();
			MutableType type = MutableSupport.createType(schema, localName);
			//            type.setDerivationMethod(SmComponent.EXTENSION);
			ProcessModel process = (ProcessModel)entity;
			SmElement smElement = getProcessSmElement(process);
			SmType smType = smElement.getType();
			MutableSupport.addLocalElement(type, ModelConstants.ARRAY_ELEMENT_NAME, smType, 0, Integer.MAX_VALUE);
			ExpandedName makeName = ExpandedName.makeName(localName);
			definitions.add( new VariableDefinition(makeName, SmSequenceTypeFactory.createElement(type)));
		}else{
			ProcessModel process = (ProcessModel)entity;
			SmElement element = getProcessSmElement(process);
			SmSequenceType seType = SmSequenceTypeFactory.create(element);
			ExpandedName makeName = ExpandedName.makeName(localName);
			definitions.add(new VariableDefinition(makeName, seType));
		}
		//		if ( containsLoop && isInputMapper) {
		//	        addSimpleTypeDefinitionforLoopVar(loopVarName , loopVartype, loopvarPath);
		//		}
	}

	public void addDefinitionForLoop(EObjectWrapper<EClass, EObject> objectWrapper,EObjectWrapper<EClass, EObject> process,  boolean isInputMapper){
		if(BpmnModelClass.ACTIVITY.isSuperTypeOf(objectWrapper.getEClassType()) || (objectWrapper.isInstanceOf(BpmnModelClass.SUB_PROCESS)) ) {

			Map<Object,Object> map = new HashMap<Object,Object>();
			map.put(BpmnXSLTutils.mapcontainsLoop,false);
			map.put(BpmnXSLTutils.maploopVartype, "");
			map.put(BpmnXSLTutils.maploopVarpath, "");
			map.put(BpmnXSLTutils.maploopVarname, "");
			map.put(BpmnXSLTutils.maplooptype, "");
			map.put(BpmnXSLTutils.maploopvarisArray, false);
			BpmnXSLTutils.getloopMapperVar(project, map, objectWrapper.getEInstance());
			boolean containsLoop = (Boolean) map.get(BpmnXSLTutils.mapcontainsLoop) ;
			String loopVartype = (String) map.get(BpmnXSLTutils.maploopVartype) ;
			String loopvarPath = (String) map.get(BpmnXSLTutils.maploopVarpath) ;
			String loopVarName = (String) map.get(BpmnXSLTutils.maplooptype);

			if(containsLoop){
				if(isInputMapper){
					addSimpleTypeDefinitionforLoopVar(process, loopVarName , loopVartype, loopvarPath);
				}else{
					addSimpleTypeDefinitionforLoopVar(process, MapperConstants.LOOP_COUNTER , loopVartype, loopvarPath);
				}
			}

		}

	}

	public void addSimpleTypeDefinitionforLoopVar(EObjectWrapper<EClass, EObject> process, String localName,
			String enttype, String entityPath) {
		String mapperElt = localName;
		if(mapperElt.equalsIgnoreCase(MapperConstants.LOOP_COUNTER)){
			if(enttype == null || enttype.trim().isEmpty()){
				enttype= PROPERTY_TYPES.INTEGER.getName();
			}
		}
		//		MutableSchema schema = SmFactory.newInstance()
		//				.createMutableSchema();
		//		MutableType type = MutableSupport.createType(schema, mapperElt);
		String concept = "concept";
		SmType smType = null;
		SmElement smElt = null;
		SmSequenceType seType = null;
		//		Boolean conceptExtension = entityPath.endsWith("." + concept);
		//		if ( !conceptExtension) {
		//			if(entityPath.trim().isEmpty())

		if (enttype!=null && enttype.equals("Object")) {
			if (!entityPath.startsWith("/")) {
				entityPath = "/" + entityPath;
			}
			EObject bpmnIndex = BpmnCorePlugin.getDefault()
					.getBpmnModelManager().getBpmnIndex(project);
			ProcessAdapter processModel = new ProcessAdapter(
					process.getEInstance(), new ProcessOntologyAdapter(
							bpmnIndex));
			Iterator allProperties = processModel.getPropertyDefinitions()
					.iterator();
			while (allProperties.hasNext()) {
				PropertyDefinition pd = (PropertyDefinition) allProperties
						.next();
				int pdType = pd.getType();
				if (pdType == RDFTypes.CONCEPT_TYPEID
						|| pdType == RDFTypes.CONCEPT_REFERENCE_TYPEID) {
					String conceptTypePath = pd.getConceptTypePath();
					if (conceptTypePath != null
							&& conceptTypePath.equals(entityPath)) {
						smType = getSmTypeForProperty(pdType,
								conceptTypePath);
						break;
					}
				}

			}

		} else {
			smType = getSimpleTypeForReturnType(enttype);
		}
		//		} 
		//		else {
		//			String newentPath = entityPath;
		//			if (conceptExtension) {
		//				newentPath = entityPath.replace("." + concept, "");
		//			}
		//			Object ent = IndexUtils.getEntity(project.getProject()
		//					.getName(), newentPath);
		//			if (ent instanceof Entity || ent instanceof Concept) {
		//				Concept conceptEnt = (Concept) ent;
		//				if (!newentPath.startsWith("/")) {
		//					newentPath = "/" + newentPath;
		//				}
		//				String nsURI = RDFTnsFlavor.BE_NAMESPACE + newentPath;
		//				ExpandedName exname = ExpandedName.makeName(nsURI,
		//						conceptEnt.getName());
		//				SmElement element = beProject.getSmElement(exname);
		//				smElt = element;
		//				smType = smElt.getType();
		//				mapperElt = conceptEnt.getName();
		//
		//			}
		//		}
		if (smType == null)
			return;
		if ( localName.equalsIgnoreCase(MapperConstants.LOOP_COUNTER) ) {
			addDefinitionForLoopMax();
			addDefinitionForLoopCounter();
			return ;
		}
		addDefinitionForLoopMax();
		addDefinitionForLoopCounter();
		//		if (!isArray)
		//			MutableSupport.addLocalElement(type, mapperElt, smType, 1, 1);
		//		else
		//			MutableSupport.addLocalElement(type, mapperElt, smType, 0,
		//					Integer.MAX_VALUE);

		seType = SmSequenceTypeFactory.createElement(smType);
		ExpandedName makeName = ExpandedName.makeName(MapperConstants.LOOP_VAR);
		definitions.add(new VariableDefinition(makeName, seType));
	}


	//
	//    private SmType getSmTypeForProperty () {
	////        if (System.getProperty("tibco.be.schema.ref.expand", "false").equals("false")) { //default behavior
	////           if (typeId == RDFTypes.CONCEPT_REFERENCE_TYPEID) { // concept reference properties
	//                // String conceptName=parseConceptName(conceptType);
	//                // MutableType refType =
	//                // newSmType(ExpandedName.makeName(conceptName + "_ref"),
	//                // schema);
	//    	MutableSchema schema = SmFactory.newInstance()
	//				.createMutableSchema();
	//                MutableType type = MutableSupport.createType(schema, null, null);
	//                MutableSupport.addAttribute(type, "ref", XSDL.LONG, false);
	//
	////				MutableType refType = newSmType(ExpandedName.makeName(pdName),
	////						schema);
	////				MutableSupport
	////						.addOptionalAttribute(refType, "ref", XSDL.STRING);
	//                return type;
	////            }
	////        } 
	////        return term;
	//
	//    }

	private SmType getSmTypeForProperty(int typeId, String conceptType) {
		if (System.getProperty("tibco.be.schema.ref.expand", "false")
				.equals("false")) { // default behavior
			if (typeId == RDFTypes.CONCEPT_TYPEID) { // contained concept
				// properties
				return getTypeForPath(conceptType);
			} else if (typeId == RDFTypes.CONCEPT_REFERENCE_TYPEID) { // concept
				// reference
				// properties
				// String conceptName=parseConceptName(conceptType);
				// MutableType refType =
				// newSmType(ExpandedName.makeName(conceptName + "_ref"),
				// schema);
				MutableSchema schema = SmFactory.newInstance()
						.createMutableSchema();
				MutableType type = MutableSupport.createType(schema, null,
						null);
				MutableSupport.addAttribute(type, "ref", XSDL.LONG, false);

				// MutableType refType =
				// newSmType(ExpandedName.makeName(pdName),
				// schema);
				// MutableSupport
				// .addOptionalAttribute(refType, "ref", XSDL.STRING);
				return type;
			}
		} else { // if tibco.be.schema.ref.expand is set, always generate
			// full schemas
			if (typeId == RDFTypes.CONCEPT_TYPEID
					|| typeId == RDFTypes.CONCEPT_REFERENCE_TYPEID) {
				return getTypeForPath(conceptType);
			}

		}

		return null;
	}

	private SmType getTypeForPath(String path) {
		Object ent = IndexUtils.getEntity(project.getProject()
				.getName(), path);
		SmType smType = null;
		if (ent instanceof Entity || ent instanceof Concept) {
			Concept conceptEnt = (Concept) ent;
			if (!path.startsWith("/")) {
				path = "/" + path;
			}
			String nsURI = RDFTnsFlavor.BE_NAMESPACE + path;
			ExpandedName exname = ExpandedName.makeName(nsURI,
					conceptEnt.getName());
			SmElement element = beProject.getSmElement(exname);
			smType = element.getType();

		}

		return smType;
	}
	private void addDefinitionForLoopCounter(){
		SmType smType = XSDL.INTEGER; 
		SmSequenceType seType = SmSequenceTypeFactory.createElement(smType);
		ExpandedName makeName = ExpandedName.makeName(MapperConstants.LOOP_COUNTER);
		definitions.add(new VariableDefinition(makeName, seType));
	}

	private void addDefinitionForLoopMax(){
		SmType smType = XSDL.INTEGER; 
		SmSequenceType seType = SmSequenceTypeFactory.createElement(smType);
		ExpandedName makeName = ExpandedName.makeName(MapperConstants.LOOP_MAX);
		definitions.add(new VariableDefinition(makeName, seType));
	}


	public SmElement addDefinitionForSubprocess(String localName,
			EObjectWrapper<EClass, EObject> subprocess, ProcessModel process, Ontology ontology, boolean isArray) {
		//		if ( containsLoop && !isInputMapper) {
		//			isArray = true ;
		//		}
		if(mapper != null){
			if (isArray) {
				// create a new type with the name of the array variable
				MutableSchema schema = SmFactory.newInstance()
						.createMutableSchema();
				MutableType type = MutableSupport.createType(schema, localName);
				//				type.setDerivationMethod(SmComponent.EXTENSION);
				SmElement smElement = mapper.getSubProcessSmElement(subprocess, process, ontology);
				SmType smType = smElement.getType();
				MutableSupport.addLocalElement(type,
						ModelConstants.ARRAY_ELEMENT_NAME, smType, 0,
						Integer.MAX_VALUE);
				ExpandedName makeName = ExpandedName.makeName(localName);
				definitions.add(new VariableDefinition(makeName,
						SmSequenceTypeFactory.createElement(type)));
				return smElement;
			} else {
				SmElement element = mapper.getSubProcessSmElement(subprocess, process, ontology);
				SmSequenceType seType = SmSequenceTypeFactory.create(element);
				ExpandedName makeName = ExpandedName.makeName(localName);
				definitions.add(new VariableDefinition(makeName, seType));
				return element;
			}
		}
		//		if ( containsLoop && isInputMapper) {
		//	        addSimpleTypeDefinitionforLoopVar(loopVarName , loopVartype, loopvarPath );
		//		}
		return null;
	}

	public void addDefinition(String localName, SmElement smelement) {
		MutableSchema schema = SmFactory.newInstance().createMutableSchema();
		MutableType type = MutableSupport.createType(schema, localName);
		//        type.setDerivationMethod(SmComponent.EXTENSION);
		SmType smType = smelement.getType();
		MutableSupport.addLocalElement(type, localName, smType, 0, 1);
		SmSequenceType seType = SmSequenceTypeFactory.createElement(type);
		ExpandedName makeName = ExpandedName
				.makeName(localName);
		VariableDefinition variableDefinition = new VariableDefinition(makeName, seType);
		variableDefinition.getElement();
		smElement = smelement ;
		definitions.add(new VariableDefinition(makeName, seType));
	}

	public void addDefinition( SmElement smelement) {
		SmType smType = smelement.getType();
		SmSequenceType seType = SmSequenceTypeFactory.createElement(smType);
		ExpandedName makeName = ExpandedName
				.makeName(smelement.getName());
		smElement = smelement ;
		definitions.add(new VariableDefinition(makeName, seType));
	}


	@Override
	public Predicate getFunction() {
		// TODO Auto-generated method stub
		return null;
	}

	protected SmType getSimpleTypeForReturnType(String returnType) {
		SmType smType = null;
		if (returnType.equalsIgnoreCase(PROPERTY_TYPES.BOOLEAN.getName()))
			smType = XSDL.BOOLEAN;
		else if (returnType.equalsIgnoreCase(PROPERTY_TYPES.INTEGER
				.getName()) || returnType.equalsIgnoreCase(integerType))
			smType = XSDL.INTEGER;
		else if (returnType.equalsIgnoreCase(PROPERTY_TYPES.DOUBLE
				.getName()))
			smType = XSDL.DOUBLE;
		else if (returnType.equalsIgnoreCase(PROPERTY_TYPES.STRING
				.getName()))
			smType = XSDL.STRING;
		else if (returnType.equalsIgnoreCase(PROPERTY_TYPES.DATE_TIME
				.getName()))
			smType = XSDL.DATETIME;
		else if (returnType.equalsIgnoreCase(PROPERTY_TYPES.LONG
				.getName()))
			smType = XSDL.LONG;
		else if (returnType.equalsIgnoreCase("object"))
			smType = XSDL.ANY_TYPE;

		return smType;
	}

	public SmElement getProcessSmElement(ProcessModel model){
		String entityURI = model.getFolderPath() ;
		if(entityURI.equals("/"))
			entityURI = entityURI+ model.getName();
		else
			entityURI = entityURI+ "/"+model.getName();

		String name = entityURI.substring(entityURI.lastIndexOf('/') + 1);
		String nsURI = RDFTnsFlavor.BE_NAMESPACE + entityURI;
		ExpandedName exname = ExpandedName.makeName(nsURI, name);
		SmElement element = beProject.getSmElement(exname);

		return element;
	}

	public SmElement getSmElement(Entity model){
		String entityURI = model.getFolderPath() ;
		if(entityURI.equals("/"))
			entityURI = entityURI+ model.getName();
		else
			entityURI = entityURI+ "/"+model.getName();

		String name = entityURI.substring(entityURI.lastIndexOf('/') + 1);
		String nsURI = RDFTnsFlavor.BE_NAMESPACE + entityURI;
		ExpandedName exname = ExpandedName.makeName(nsURI, name);
		SmElement element = beProject.getSmElement(exname);

		return element;
	}

	public boolean isInputMapper() {
		return isInputMapper;
	}

	public void setInputMapper(boolean isInputMapper) {
		this.isInputMapper = isInputMapper;
	}

	public MapperPropertySection getMapperPropertySection() {
		return mapperPropertySection;
	}

	public void setMapperPropertySection(MapperPropertySection mapperPropertySection) {
		this.mapperPropertySection = mapperPropertySection;
	}

	
}