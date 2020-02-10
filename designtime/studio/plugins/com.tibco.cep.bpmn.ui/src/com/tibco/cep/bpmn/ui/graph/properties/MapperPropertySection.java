package com.tibco.cep.bpmn.ui.graph.properties;


import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;
import javax.swing.event.ChangeListener;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.xsd.XSDTerm;

import com.tibco.be.model.functions.Predicate;
import com.tibco.be.model.rdf.RDFTnsFlavor;
import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.parser.semantic.FunctionsCatalogManager;
import com.tibco.be.util.shared.ModelConstants;
import com.tibco.cep.bpmn.core.BpmnCorePlugin;
import com.tibco.cep.bpmn.core.utils.BpmnModelUtils;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtension;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.ontology.ProcessAdapter;
import com.tibco.cep.bpmn.model.designtime.ontology.ProcessOntologyAdapter;
import com.tibco.cep.bpmn.model.designtime.ontology.symbols.SymbolEntryImpl;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.model.designtime.utils.ROEObjectWrapper;
import com.tibco.cep.bpmn.runtime.templates.MapperConstants;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.editor.BpmnEditor;
import com.tibco.cep.bpmn.ui.editor.BpmnEditorInput;
import com.tibco.cep.bpmn.ui.graph.BpmnGraphUtils;
import com.tibco.cep.bpmn.ui.utils.BpmnXSLTutils;
import com.tibco.cep.designtime.CommonOntologyAdapter;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.designtime.model.process.ProcessModel;
import com.tibco.cep.mapper.xml.xdata.xpath.VariableDefinition;
import com.tibco.cep.repo.BEProject;
import com.tibco.cep.repo.provider.GlobalVariablesProvider;
import com.tibco.cep.studio.core.index.model.JavaElement;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.manager.GlobalVariablesMananger;
import com.tibco.cep.studio.core.rdf.EMFRDFTnsFlavor;
import com.tibco.cep.studio.core.repo.emf.StudioEMFProject;
import com.tibco.cep.studio.core.util.mapper.MapperInvocationContext;
import com.tibco.cep.studio.core.util.mapper.MapperXSDUtils;
import com.tibco.cep.studio.ui.xml.utils.MapperUtils;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmComponent;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmFactory;
import com.tibco.xml.schema.SmNamespace;
import com.tibco.xml.schema.SmParticle;
import com.tibco.xml.schema.SmParticleTerm;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.build.MutableElement;
import com.tibco.xml.schema.build.MutableSchema;
import com.tibco.xml.schema.build.MutableSupport;
import com.tibco.xml.schema.build.MutableType;
import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.schema.impl.DefaultComponentFactory;
import com.tibco.xml.schema.impl.DefaultModelGroup;
import com.tibco.xml.schema.impl.DefaultType;
import com.tibco.xml.schema.xtype.SmSequenceTypeFactory;
import com.tibco.xml.tns.parse.impl.TnsImportImpl;

/**
 * 
 * @author ggrigore
 *
 */
abstract public class MapperPropertySection extends AbstractBpmnPropertySection {
	
    protected Composite mapperComposite;
	private Ontology ontology;
//	private Map<MapperContext,MapperControl> contextMap = new HashMap<MapperContext,MapperControl>();
	protected AbstractMapperControl currentMapper;
	protected EntityMapperContext mctx;
	private boolean isListenerAttached;
	private ChangeListener mapperChangeListener;
//	protected EObject userObject;
	protected String currentXslt;
	private FocusListener focusListener;
	public static MapperInvocationContext ctx;
	protected AbstractMapperControl mc;
	public static String RF_RETURNTYPE = "RFReturnTypes";
	public static String WS_RETURNTYPE = "message";
	protected static com.tibco.cep.designtime.core.model.Entity returnEnt;
	private boolean fUpdatedCustomFuncs = false;
	
	public MapperPropertySection(){
		this.mapperChangeListener = getMapperChangeListener();
		this.focusListener = getTextAreaFocusListener();
		this.currentXslt = "";
	}
	@Override
	public void aboutToBeHidden() {
		try{
		if (!mapperComposite.isDisposed()) {
			isListenerAttached = false;
			currentMapper.getBindingEditor().removeValueChangeListener(mapperChangeListener);
			currentMapper.getBindingEditor().removeTextAreaFocuslListener(focusListener);
			
		  }
		} catch (Exception e) {
			
		}
	}
	
	@Override
	public void aboutToBeShown() {
		try{
		if (!mapperComposite.isDisposed() && !isListenerAttached) {
			isListenerAttached = true;
			currentMapper.getBindingEditor().addValueChangeListener(mapperChangeListener);
			currentMapper.getBindingEditor().addTextAreaFocuslListener(focusListener);
			
			initialize();
			
		}
		} catch (Exception e) {
			
		}
	}

	protected String getInputMapperXslt(){
		EObject userObject = getUserObject();
		String xslt = "";
		if(userObject != null){
			EObjectWrapper<EClass, EObject> userObjWrapper = EObjectWrapper
					.wrap(userObject);
			if(BpmnModelClass.ACTIVITY.isSuperTypeOf(userObject.eClass())){
				List<EObject> dataInAssocs = userObjWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_DATA_INPUT_ASSOCIATIONS);
		        if (!dataInAssocs.isEmpty()) {
		            ROEObjectWrapper<EClass, EObject> doAssocWrap = ROEObjectWrapper.wrap((EObject)dataInAssocs.get(0));

		            EObject transform = (EObject) doAssocWrap.getAttribute(BpmnMetaModelConstants.E_ATTR_TRANSFORMATION);
		            ROEObjectWrapper<EClass, EObject> transformWrap = ROEObjectWrapper.wrap(transform);
		            xslt = (String) transformWrap.getAttribute(BpmnMetaModelConstants.E_ATTR_BODY);
		        }
			}else if(BpmnModelClass.THROW_EVENT.isSuperTypeOf(userObject.eClass())){
				List<EObject> dataInAssocs = userObjWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_DATA_INPUT_ASSOCIATION);
		        if (!dataInAssocs.isEmpty()) {
		            ROEObjectWrapper<EClass, EObject> doAssocWrap = ROEObjectWrapper.wrap((EObject)dataInAssocs.get(0));

		            EObject transform = (EObject) doAssocWrap.getAttribute(BpmnMetaModelConstants.E_ATTR_TRANSFORMATION);
		            ROEObjectWrapper<EClass, EObject> transformWrap = ROEObjectWrapper.wrap(transform);
		            xslt = (String) transformWrap.getAttribute(BpmnMetaModelConstants.E_ATTR_BODY);
		        }
			}
		}
		return xslt;
	}
	
	protected String getOutputMapperXslt(){
		EObject userObject = getUserObject();
		String xslt = "";
		if(userObject != null){
			EObjectWrapper<EClass, EObject> userObjWrapper = EObjectWrapper
					.wrap(userObject);
			if(BpmnModelClass.ACTIVITY.isSuperTypeOf(userObject.eClass())){
				List<EObject> dataOutAssocs = userObjWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_DATA_OUTPUT_ASSOCIATIONS);
		        if (!dataOutAssocs.isEmpty()) {
		            ROEObjectWrapper<EClass, EObject> doAssocWrap = ROEObjectWrapper.wrap((EObject)dataOutAssocs.get(0));

		            EObject transform = (EObject) doAssocWrap.getAttribute(BpmnMetaModelConstants.E_ATTR_TRANSFORMATION);
		            ROEObjectWrapper<EClass, EObject> transformWrap = ROEObjectWrapper.wrap(transform);
		            xslt = (String) transformWrap.getAttribute(BpmnMetaModelConstants.E_ATTR_BODY);
		        }
			}else if(BpmnModelClass.CATCH_EVENT.isSuperTypeOf(userObject.eClass())){
				List<EObject> dataOutAssocs = userObjWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_DATA_OUTPUT_ASSOCIATION);
		        if (!dataOutAssocs.isEmpty()) {
		            ROEObjectWrapper<EClass, EObject> doAssocWrap = ROEObjectWrapper.wrap((EObject)dataOutAssocs.get(0));

		            EObject transform = (EObject) doAssocWrap.getAttribute(BpmnMetaModelConstants.E_ATTR_TRANSFORMATION);
		            ROEObjectWrapper<EClass, EObject> transformWrap = ROEObjectWrapper.wrap(transform);
		            xslt = (String) transformWrap.getAttribute(BpmnMetaModelConstants.E_ATTR_BODY);
		        }
			}
		}
		return xslt;
	}
	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySection#createControls(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		fEditor = (BpmnEditor) tabbedPropertySheetPage.getSite().getPage().getActiveEditor();
		GridData gd = new GridData(GridData.FILL_BOTH);
    	gd.widthHint = 500;
    	gd.heightHint = 200;
    	parent.setLayoutData(gd);
    	 gd = new GridData(GridData.FILL_BOTH);
     	gd.widthHint = 500;
     	gd.heightHint = 200;
     	tabbedPropertySheetPage.getControl().setLayoutData(gd);
		
		
//		parent.setLayout(new GridLayout());
		createNodeTypeSpecificControl(parent);
		
		Composite createComposite = getWidgetFactory().createComposite(parent, SWT.EMBEDDED);
//		createComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		
		
		setMapperComposite(createComposite/*getWidgetFactory().createComposite(createComposite, SWT.EMBEDDED)*/);
//		FillLayout fillLayout = new FillLayout(SWT.VERTICAL);
//		fillLayout.marginHeight = 0;
//		fillLayout.marginWidth = 0;
//		fillLayout.spacing = 0;
//		createComposite.setLayout(fillLayout);
		
		fEditor = (BpmnEditor) tabbedPropertySheetPage.getSite()
				.getPage().getActiveEditor();
		fProject = ((BpmnEditorInput) fEditor.getEditorInput())
				.getFile().getProject();
		if (fProject == null || fEditor == null) {
			return;
		}
		try {
			setOntology(new CommonOntologyAdapter(getProject().getName()));
		} catch (Exception e) {
			BpmnUIPlugin.log(e);
		}
		
		createMapperContextComponent();
		
        getMapperComposite().layout();
	}

	protected void createMapperContextComponent() {
		mctx = new EntityMapperContext(fProject);
		
//		ctx = new MapperInvocationContext(fProject.getName(), mctx.getStudioTypeDefinitions()/*new ArrayList <com.tibco.cep.studio.core.index.model.VariableDefinition>() */, getInputMapperXslt(), null, null, null);
		
		if (BpmnModelUtils.isSWTMapper() ) {
			if (mctx.isInputMapper()) {
				mc = new BpmnMapperControl(this, ctx, mctx,getMapperComposite(), getInputMapperXslt());
			} else {
				mc = new BpmnMapperControl(this, ctx, mctx,getMapperComposite(), getOutputMapperXslt());
			}

		} else {
			mc = new MapperControl(mctx,getMapperComposite());
			mc.getBindingEditor().getBindingTree().enableIgnoreInlineEditChange(true);
		}
//		contextMap.put(mctx, mc);
		setMapper(mc);
		mctx.setMapper(mc);
	}
	
	
	protected void createNodeTypeSpecificControl(Composite parent){
		
	}
	
	public void setMapper(AbstractMapperControl mc) {
		this.currentMapper = mc;
	}
	
	@Override
	public boolean shouldUseExtraSpace() {
		return true;
	}
	
	
	/*
	 * @see org.eclipse.ui.views.properties.tabbed.view.ITabbedPropertySection#refresh()
	 */
	abstract public void refresh();
	
	protected EObject getUserObject(){
		EObject userObject = null;
		if (fTSENode != null) {
			userObject = (EObject)fTSENode.getUserObject();
		}
		
		if (fTSEGraph != null) {
			userObject = (EObject)fTSEGraph.getUserObject();
		} 
		
		return userObject;
	}
	

	protected void updateMapperForCallActivity(Entity entity, String xslt, String name) {
		getMapper().setEntityForCallActivity(entity, xslt, name);
		return;
	}

	protected void updateMapper(final Entity entity, final String xslt) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				getMapper().setMapperInput(entity, xslt);
			}
		});
		return;
	}
	

	protected void setNullMapper() {
		AbstractMapperControl mc = getMapper();
		mc.setMapperInput(null, "");
	}
		

	/**
	 * @return the composite
	 */
	public Composite getMapperComposite() {
		return mapperComposite;
	}

	/**
	 * @param composite the composite to set
	 */
	public void setMapperComposite(Composite composite) {
		this.mapperComposite = composite;
	}


	/**
	 * @return the currentMapper
	 */
	public AbstractMapperControl getMapper() {
		return currentMapper;
	}

	/**
	 * @param currentMapper the currentMapper to set
	 */
	public void setCurrentMapper(MapperControl currentMapper) {
		this.currentMapper = currentMapper;
	}



	@SuppressWarnings("unused")
	private Ontology getOntology() {
		return ontology;
	}

	private void setOntology(Ontology ontology) {
		this.ontology = ontology;
	}
	
	public void setMapperContext(EntityMapperContext context){
		mctx = context;
	}
	
	
	static public class EntityMapperContext implements MapperContext {
		
		private IProject project;
		private BEProject beProject;
		private List<VariableDefinition> definitions;
		private AbstractMapperControl mapper;
		public boolean isInputMapper= false;
		public MapperPropertySection mapperPropertySection ;
		private SmElement smElement;
		private SmElement rfsmElement;
		private String integerType = "INTEGER" ;
		private XSDTerm xsdTerm ;
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
		
		public void addSimpleTypeDefinition(String localName, String returnType, boolean isArray,Object obj){
			String schemaName = null;
			if(obj != null && obj instanceof RuleElement){
				RuleElement rule = (RuleElement)obj;
				schemaName = rule.getName();
			}
			else if(obj != null && obj instanceof JavaElement){
				JavaElement jElt = (JavaElement)obj;
				schemaName = jElt.getName();
			}
			if(isArray){
				//create a new type with the name of the array variable
	            MutableSchema schema = SmFactory.newInstance().createMutableSchema();
	            MutableType type = MutableSupport.createType(schema, localName);
	            SmType smType = MapperUtils.getSimpleTypeForReturnType(returnType);
	            if(smType == null)
					return;
	            MutableSupport.addLocalElement(type, ModelConstants.ARRAY_ELEMENT_NAME, smType, 0, Integer.MAX_VALUE);
	            ExpandedName makeName = ExpandedName.makeName(localName);
	            VariableDefinition varDef=  new VariableDefinition(makeName, SmSequenceTypeFactory.createElement(type));
	            varDef.setArray(isArray);
	            varDef.setSmTypestr(returnType);
				definitions.add(varDef);
			}else{
				
				SmFactory factory = SmFactory.newInstance();
				MutableSchema schema = factory.createMutableSchema();
				schema.setFlavor(com.tibco.xml.schema.flavor.XSDL.FLAVOR);
				schema.setNamespace(EMFRDFTnsFlavor.BE_NAMESPACE+"/"+schemaName);
				MutableType deployedVarsType = MutableSupport.createType(schema, "ReturnTypes");
				String name = localName;
				SmType smType = MapperUtils.getSimpleTypeForReturnType(returnType);
				if(smType == null)
					return;
				MutableSupport.addRequiredLocalElement(deployedVarsType, name, smType);
				SmElement sme = MutableSupport.createElement(schema, MapperPropertySection.RF_RETURNTYPE/*"RFReturnTypes"*/, deployedVarsType);
				VariableDefinition varDef=  new VariableDefinition(ExpandedName.makeName(name), SmSequenceTypeFactory.create(sme));
	            varDef.setArray(isArray);
	            definitions.add( varDef);
			}
		}
		
		
	        protected void addNsToType (MutableSchema schema,
	   			MutableType onType,
				SmParticle p,
				String nameSpaceUri) {
			SmParticleTerm pTerm = p.getTerm();
			if(!(pTerm instanceof SmElement)){ //Supporting Element type only
				return;
			}
			int min = p.getMinOccurrence();
			int max = p.getMaxOccurrence();
			SmElement element = (SmElement) pTerm;
			SmType type = element.getType();
			String localName = pTerm.getExpandedName().localName;
			ExpandedName en = type.getExpandedName();
			if(en != null && en.namespaceURI != null && type.getAtomicType() == null){
				ExpandedName expName = ExpandedName.makeName(nameSpaceUri, localName);
				MutableElement newElement = AbstractMapperControl.createSmElement(expName, type);
				MutableSupport.addElement(onType, newElement, min, max);
				//        		MutableSupport.addLocalElement(onType, localName, mutableType, min, max);
				schema.getSingleFragment().addImport(new TnsImportImpl(null, null, en.namespaceURI, SmNamespace.class));
			} else {// This is a local type
				ExpandedName expName = ExpandedName.makeName(nameSpaceUri, localName);
				MutableElement newElement = AbstractMapperControl.createSmElement(expName, type);
				MutableSupport.addElement(onType, newElement, min, max);

			}
		}

		public void addDefinition(String localName, String entityPath, boolean isArray){
			if(isArray){
				//create a new type with the name of the array variable
	            MutableSchema schema = SmFactory.newInstance().createMutableSchema();
	            MutableType type = MutableSupport.createType(schema, localName);
//	            type.setDerivationMethod(SmComponent.EXTENSION);
	        	SmElement smElement = MapperControl.getSmElementFromPath(project.getName(), entityPath);
	        	SmType smType = smElement.getType();
	            MutableSupport.addLocalElement(type, ModelConstants.ARRAY_ELEMENT_NAME, smType, 0, Integer.MAX_VALUE);
	            ExpandedName makeName = ExpandedName.makeName(localName);
	            VariableDefinition varDef=  new VariableDefinition(makeName, SmSequenceTypeFactory.createElement(type));
	            varDef.setArray(true);
	            definitions.add( varDef);
			}else{
				SmElement smElementFromPath =MapperControl.getSmElementFromPath(project.getName(), entityPath);
				SmSequenceType seType = SmSequenceTypeFactory.create(smElementFromPath);
				ExpandedName makeName = ExpandedName.makeName(localName);
				definitions.add(new VariableDefinition(makeName, seType));
			}
		}
		
	    MutableSchema getSchema(DefaultComponentFactory factory, String fullpath) {
	        String ns=RDFTnsFlavor.BE_NAMESPACE+ fullpath;
//	        MutableSchema cached = (DefaultSchema) factory.createSchema();
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
//	        smType.setDerivationMethod(SmComponent.EXTENSION);

	        schema.addSchemaComponent(smType);

	        
	        return smType;
	    }

	    /**
	     * Adding Java Task definition for Mapper
	     * @param symbol
	     */
	    public void addJavaTaskDefinition(SymbolEntryImpl symbol, String projectName,JavaElement javaEnt) {
	    	EObjectWrapper<EClass, EObject> typeWrapper = EObjectWrapper.wrap(symbol.getType());
	    	String returnType = typeWrapper.toString();
	    	if (returnType.equals(BpmnMetaModelExtensionConstants.ENUM_PROPERTY_TYPES_Void.getLocalName())) {
	    		return;
	    	}
	    	SmType smType= MapperUtils.getSimpleTypeForReturnType(returnType);
	    	boolean isArray = symbol.isArray();
	    	String name = MapperConstants.RETURN;
	    	if (smType == XSDL.ANY_TYPE/*!symbol.isPrimitive()*/) {
	    		String path = symbol.getPath();
	    		com.tibco.cep.designtime.core.model.Entity entity = CommonIndexUtils.getEntity(projectName, path);
	    		if (entity != null) {
	    			String fullPath = CommonIndexUtils.getEntity(projectName,	path).getFullPath();
//	    			if(returnEnt == null)
    				returnEnt=entity;
//	    			String type = typeWrapper.toString();
//	    			if(type != null && type.equalsIgnoreCase("ConceptReference")){
//	    				 smType = getSmTypeForProperty(RDFTypes.CONCEPT_REFERENCE_TYPEID,
//	    						fullPath);
//	    				 addDefinitionForConcRef(name, fullPath, isArray,smType);
//	    			} else{
	    				addDefinition(name, fullPath, isArray);
//	    			}
	    		}
	    	} else {
	    		addSimpleTypeDefinition(name, returnType, isArray,javaEnt);
	    	}
	    }
		
//		public void addDefinitionForConcRef(String localName, String entityPath, boolean isArray,SmType smType){
//			SmFactory factory = SmFactory.newInstance();
//			MutableSchema schema = factory.createMutableSchema();
//			schema.setFlavor(com.tibco.xml.schema.flavor.XSDL.FLAVOR);
//			schema.setNamespace(EMFRDFTnsFlavor.BE_NAMESPACE+"/"+entityPath);
//			MutableType deployedVarsType = MutableSupport.createType(schema, "ReturnTypes");
//			String name = localName;
//			if(smType == null)
//				return;
//			if (isArray) {
//				MutableSupport.addRepeatingLocalElement(deployedVarsType,  ModelConstants.ARRAY_ELEMENT_NAME, smType);
//			} else {
//				MutableSupport.addRequiredLocalElement(deployedVarsType, name, smType);
//			}
//			SmElement sme = MutableSupport.createElement(schema, MapperPropertySection.RF_RETURNTYPE/*"RFReturnTypes"*/, deployedVarsType);
//			definitions.add(new VariableDefinition(ExpandedName.makeName(name), SmSequenceTypeFactory.create(sme)));
//			return ;
//		}

		public void addSimpleJavaTaskDefinition(String localName, SmType returnType, boolean isArray,Object obj) {
			
			
			if(obj != null && obj instanceof JavaElement) {
				SmType smType = returnType;
	    		JavaElement javaElt = (JavaElement)obj;
				DefaultComponentFactory factory = new DefaultComponentFactory();
				MutableSchema schema = getSchema(factory,"/"+javaElt.getFolder()+javaElt.getName());
				ExpandedName mtInputTypeName = ExpandedName.makeName(schema.getNamespace(), "javaTask");
				
				MutableType mtInType = getSmType(factory, schema, mtInputTypeName);
				MutableElement elem = factory.createElement();
				elem.setType(mtInType);
				elem.setSchema(schema);
				elem.setExpandedName(mtInputTypeName);
				schema.addSchemaComponent(elem);
				MutableType argsType = MutableSupport
						.createType(schema, null, null);
//				argsType.setComplex();
				
				MutableElement smEle = MutableSupport.createElement(schema, localName,smType);
				if(isArray){
					MutableSupport.addElement(mtInType, smEle, 0, Integer.MAX_VALUE);
				}else 
					MutableSupport.addElement(mtInType, smEle, 1, 1);
	//			MutableSupport.addLocalElement(mtInType, localName, argsType, 1, 1);
				
	//			MutableElement smEle = MutableSupport.createElement(schema, "ret",
	//					smType);
	//			MutableSupport.addElement(argsType, smEle, 1, 1);
				DefaultModelGroup contentModel = (DefaultModelGroup) mtInType
						.getContentModel();
				contentModel.setSchema(schema);
				elem.setSchema(schema);
				ExpandedName makeName = ExpandedName.makeName("javaTask");
				smElement = elem;
				definitions.add(new VariableDefinition(makeName,
						SmSequenceTypeFactory.createElement(elem.getType())));
		}
		}
	    
	    
		public void addDefinition(String localName, Entity entity, boolean isArray){
			if(!(entity instanceof ProcessModel)){
				addDefinition(localName, entity.getFullPath(), isArray);
				return;
			}
//			if ( containsLoop && !isInputMapper) {
//				isArray = true ;
//			}
			if(isArray){
				//create a new type with the name of the array variable
	            MutableSchema schema = SmFactory.newInstance().createMutableSchema();
	            MutableType type = MutableSupport.createType(schema, localName);
//	            type.setDerivationMethod(SmComponent.EXTENSION);
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
//			if ( containsLoop && isInputMapper) {
//		        addSimpleTypeDefinitionforLoopVar(loopVarName , loopVartype, loopvarPath);
//			}
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
//			String concept = "concept";
			SmType smType = null;
//			SmElement smElt = null;
			String concPath = null;
			SmSequenceType seType = null;
			String mapperElt = localName;
			if(mapperElt.equalsIgnoreCase(MapperConstants.LOOP_COUNTER)){
				if(enttype == null || enttype.trim().isEmpty()){
					enttype= PROPERTY_TYPES.INTEGER.getName();
				}
			}
				
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
							concPath = entityPath;
							break;
						}
					}

				}

			} else {
				smType = MapperUtils.getSimpleTypeForReturnType(enttype);
			}
			if (smType == null)
				return;
			if ( localName.equalsIgnoreCase(MapperConstants.LOOP_COUNTER) ) {
				addDefinitionForLoopMax();
				addDefinitionForLoopCounter();
				return ;
			}
			addDefinitionForLoopMax();
			addDefinitionForLoopCounter();
			seType = SmSequenceTypeFactory.createElement(smType);
			ExpandedName makeName = ExpandedName.makeName(MapperConstants.LOOP_VAR);
			VariableDefinition varDef = new VariableDefinition(makeName, seType) ;
			if(concPath == null)
				varDef.setSmTypestr(smType.getName());
			else
				varDef.setSmTypestr(concPath);
			definitions.add(varDef);
		}
		
		
	    
		private SmType getSmTypeForProperty(int typeId, String conceptType) {
			if (System.getProperty("tibco.be.schema.ref.expand", "false")
					.equals("false")) { // default behavior
				if (typeId == RDFTypes.CONCEPT_TYPEID) { // contained concept
															// properties
					return getTypeForPath(conceptType);
				} else if (typeId == RDFTypes.CONCEPT_REFERENCE_TYPEID) { // concept
																			// reference
																			// properties
					MutableSchema schema = SmFactory.newInstance()
							.createMutableSchema();
					MutableType type = MutableSupport.createType(schema, null,
							null);
					MutableSupport.addAttribute(type, "ref", XSDL.LONG, false);

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
	    	VariableDefinition varDef = new VariableDefinition(makeName, seType);
	    	varDef.setSmTypestr(smType.getName());
	    	definitions.add(varDef);
	    }

	    private void addDefinitionForLoopMax(){
	    	SmType smType = XSDL.INTEGER; 
	    	SmSequenceType seType = SmSequenceTypeFactory.createElement(smType);
	    	ExpandedName makeName = ExpandedName.makeName(MapperConstants.LOOP_MAX);
	    	VariableDefinition varDef = new VariableDefinition(makeName, seType);
	    	varDef.setSmTypestr(smType.getName());
	    	definitions.add(varDef);
	    }
     

		public SmElement addDefinitionForSubprocess(String localName,
				EObjectWrapper<EClass, EObject> subprocess, ProcessModel process, Ontology ontology, boolean isArray) {
//			if ( containsLoop && !isInputMapper) {
//				isArray = true ;
//			}
			if(mapper != null){
				if (isArray) {
					// create a new type with the name of the array variable
					MutableSchema schema = SmFactory.newInstance()
							.createMutableSchema();
					MutableType type = MutableSupport.createType(schema, localName);
//					type.setDerivationMethod(SmComponent.EXTENSION);
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
//			if ( containsLoop && isInputMapper) {
//		        addSimpleTypeDefinitionforLoopVar(loopVarName , loopVartype, loopvarPath );
//			}
			return null;
		}
		
		public void addDefinition(String localName, SmElement smelement) {
			MutableSchema schema = SmFactory.newInstance().createMutableSchema();
            MutableType type = MutableSupport.createType(schema, localName);
//            type.setDerivationMethod(SmComponent.EXTENSION);
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

		public void setDefinitions(List<VariableDefinition> definitions) {
			this.definitions = definitions;
		}

		public void setSmElement(SmElement smElement) {
			this.smElement = smElement;
		}

		public XSDTerm getXsdTerm() {
			return xsdTerm;
		}

		public void setXsdTerm(XSDTerm xsdTerm) {
			this.xsdTerm = xsdTerm;
		}

		public SmElement getRfsmElement() {
			return rfsmElement;
		}

		public void setRfsmElement(SmElement rfsmElement) {
			this.rfsmElement = rfsmElement;
		}
		
	}
	
	@Override
	protected void updatePropertySection(Map<String, Object> updateList) {
		if(updateList.size() == 0)
			return;
		if(fTSENode != null)
			BpmnGraphUtils.fireUpdate(updateList,fTSENode ,fPropertySheetPage.getEditor().getBpmnGraphDiagramManager());
		if(fTSEConnector != null)
			BpmnGraphUtils.fireUpdate(updateList,fTSEConnector ,fPropertySheetPage.getEditor().getBpmnGraphDiagramManager());
		
	}
	
	private void initialize() {
		// wait for function catalog to be built
		FunctionsCatalogManager.waitForStaticRegistryUpdates();
	}
	
	public void updateCustomFunctions(String projectName) {
		if (!fUpdatedCustomFuncs) {
			MapperXSDUtils.updateCustomFunctions(projectName);
		}
		fUpdatedCustomFuncs = true;
	}
	
	abstract ChangeListener getMapperChangeListener();
	
	abstract FocusListener getTextAreaFocusListener();

}