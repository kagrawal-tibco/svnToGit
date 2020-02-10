package com.tibco.cep.bpmn.ui.graph.properties;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.xml.sax.InputSource;

import com.tibco.be.model.rdf.RDFTnsFlavor;
import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.util.XSTemplateSerializer;
import com.tibco.cep.bpmn.core.index.BpmnIndexUtils;
import com.tibco.cep.bpmn.core.utils.BpmnModelUtils;
import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.ontology.symbols.SymbolEntryImpl;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.graph.BpmnGraphUtils;
import com.tibco.cep.bpmn.ui.graph.properties.MapperPropertySection.EntityMapperContext;
import com.tibco.cep.bpmn.ui.mapper.BpmnSchemaCache;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.model.process.ProcessModel;
import com.tibco.cep.designtime.model.rule.RuleFunction;
import com.tibco.cep.designtime.model.rule.Symbol;
import com.tibco.cep.designtime.model.rule.Symbols;
import com.tibco.cep.mapper.xml.xdata.xpath.VariableDefinition;
import com.tibco.cep.repo.provider.GlobalVariablesProvider;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.mapper.MapperInvocationContext;
import com.tibco.cep.studio.core.util.mapper.MapperXSDUtils;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.ui.util.ColorConstants;
import com.tibco.cep.studio.ui.util.StudioUIUtils;
import com.tibco.cep.studio.ui.xml.utils.MapperUtils;
import com.tibco.io.xml.XMLStreamReader;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiFactoryFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParser;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.datamodel.helpers.XiSerializer;
import com.tibco.xml.mapperui.emfapi.EMapperFactory;
import com.tibco.xml.mapperui.emfapi.IEMapperControl;
import com.tibco.xml.schema.SmAttribute;
import com.tibco.xml.schema.SmComponent;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmFactory;
import com.tibco.xml.schema.SmModelGroup;
import com.tibco.xml.schema.SmNamespace;
import com.tibco.xml.schema.SmParticle;
import com.tibco.xml.schema.SmParticleTerm;
import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.build.MutableElement;
import com.tibco.xml.schema.build.MutableSchema;
import com.tibco.xml.schema.build.MutableSupport;
import com.tibco.xml.schema.build.MutableType;
import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.schema.impl.DefaultComponentFactory;
import com.tibco.xml.schema.impl.DefaultModelGroup;
import com.tibco.xml.schema.parse.xsd.XSDLSchema;
import com.tibco.xml.schema.xtype.SmSequenceTypeFactory;
import com.tibco.xml.tns.parse.impl.TnsImportImpl;
import com.tibco.xml.util.NamespaceCounter;
import com.tibco.xml.util.XSDWriter;
import com.tibco.xml.ws.wsdl.WsWsdl;

public class BpmnMapperControl extends AbstractMapperControl{

	private BpmnEMapperInputOutputAdapter inputOutputAdapter;
	private IEMapperControl mapperControl;
	private EntityMapperContext context;
	private String Xslt;
	public MapperPropertySection mPs;
	Map<String, String> wsdlSchemaMap = new HashMap< String, String >();
	private static XiParser xiParser = XiParserFactory.newInstance();
	private static XiFactory xiFactory = XiFactoryFactory.newInstance();
	/**
	 * @param agent
	 */
	public BpmnMapperControl(MapperPropertySection mPs , MapperInvocationContext context , EntityMapperContext mapperContext , Composite parent , String Xslt ) {
		super(context, mapperContext ,parent);
		this.fproject= mPs.fProject ;
		this.context = mapperContext;
		this.parent = parent;
		this.Xslt=Xslt;
		this.mPs=mPs;
		if ( bpmnSchemaCache == null)
			bpmnSchemaCache = new BpmnSchemaCache() ;
		bpmnSchemaCache.setEsm(StudioCorePlugin.getSchemaCache( fproject.getProject().getName() )) ;
		if(this.parent != null){
			initAgent();
			createControl();
		}	
	}
	public BpmnMapperControl(EntityMapperContext context, Composite parent) {
		super(null , context ,parent);
		this.mapperContext = context;
		this.parent = parent;
		if(this.parent != null){
			initAgent();
			createControl();
		}
	}
	
	public void initAgent() {
		if (getMapperContext() != null) {
			UIAgent agent = StudioCorePlugin.getUIAgent(getMapperContext()
					.getProject().getName());
			setUIAgent(new DelegatingUIAgent(agent));
			
		}
	}

	/**
	 * @return the visible
	 */
	public boolean isVisible() {
		return true;
	}

	public BpmnEMapperInputOutputAdapter getInputOutputAdapter() {
		return inputOutputAdapter;
	}

	public void setInputOutputAdapter(
			BpmnEMapperInputOutputAdapter inputOutputAdapter) {
		this.inputOutputAdapter = inputOutputAdapter;
	}

	public void createControl() {
		
		Composite composite = parent;
		GridData gdtableComp = new GridData( GridData.BEGINNING ) ;
		gdtableComp.horizontalSpan = SWT.FILL ;
		gdtableComp.verticalSpan = SWT.FILL ;
		gdtableComp.grabExcessHorizontalSpace = false ;
		gdtableComp.grabExcessVerticalSpace = true ;
		gdtableComp.minimumHeight = 140 ;
		composite.setLayoutData( gdtableComp );
		GridLayout tablelayout = new GridLayout( 1 , false ) ;
		tablelayout.numColumns = 1;
		composite.setLayout( tablelayout ) ;
		composite.setBackground( ColorConstants.menuBackground ) ;
		inputOutputAdapter = createMapperControl( composite ) ;
		mapperControl = inputOutputAdapter.getMapperControl( ) ;
		composite.pack( ) ;
	}
	
	
	private boolean checkForGlobalVarAddition(List<VariableDefinition> vdl){
		for(VariableDefinition vardef: vdl){
			if(vardef.getName().toString().equalsIgnoreCase(GlobalVariablesProvider.NAME)){
				return true ;
			} 
		}
		return false;
	}
	/**
	 * @param xslt
	 * @param element
	 * @param agent
	 */
	public void updateMapperPanel( String xslt) {
		if(this.parent == null)
			return;
		List<VariableDefinition> vdl = new ArrayList<VariableDefinition>();
		vdl = this.mapperContext.getDefinitions();
		inputOutputAdapter.getMapperArgs().setSourceElements(vdl);
		
//		if(!checkForGlobalVarAddition(vdl)){
		List<VariableDefinition> gvdl = new ArrayList<VariableDefinition>();
		VariableDefinition vardef = new VariableDefinition(
				ExpandedName.makeName(GlobalVariablesProvider.NAME), 
				SmSequenceTypeFactory.create(getMapperContext().getGlobalVariables().toSmElement(false)));
		gvdl.add(vardef);
		inputOutputAdapter.getMapperArgs().setGlobalVars(gvdl);
//			vdl.add(vardef);
//		}
		
		inputOutputAdapter.getMapperArgs().setXSLT(xslt);
		inputOutputAdapter.getMapperArgs().isInputMapper = ((EntityMapperContext)mapperContext).isInputMapper;
		 //is I/p mapper
		if(inputOutputAdapter.getMapperArgs().isInputMapper){
			inputOutputAdapter.getMapperArgs().setTargetElement(getCoreModelEntity());
			inputOutputAdapter.getMapperArgs().setSourceElement(entity);
			inputOutputAdapter.getMapperArgs().setEntityUri(this.entityURI);
			inputOutputAdapter.getMapperArgs().setSourceSchema(ipMapperSourceSchema); 
			inputOutputAdapter.getMapperArgs().setTargetSchema(ipMapperTargetSchema); 
			inputOutputAdapter.getMapperArgs().setImportedSchema(processEntitySchema);
		} else {
			inputOutputAdapter.getMapperArgs().setTargetElement(entity);
			inputOutputAdapter.getMapperArgs().setSourceElement(getCoreModelEntity());
			inputOutputAdapter.getMapperArgs().setEntityUri(entityURI);
			inputOutputAdapter.getMapperArgs().setTargetSchema(processEntitySchema);
			inputOutputAdapter.getMapperArgs().setSourceSchema(((EntityMapperContext)mapperContext).getSmElement());
			inputOutputAdapter.getMapperArgs().setSourceXSDTerm(((EntityMapperContext)mapperContext).getXsdTerm());
			inputOutputAdapter.setReturnEnt(mPs.returnEnt);
		}
		
		inputOutputAdapter.getMapperArgs().project=mapperContext.getProject();
		inputOutputAdapter.getMapperArgs().beProject = ((EntityMapperContext) mapperContext).getBEProject();
		
		inputOutputAdapter.getMapperArgs().uiAgent=this.getUIAgent();
//		inputOutputAdapter.getMapperArgs().setGlobalVars(makeInputVariableDefinitions()) ;
		//wsdl poc 
		loadWsdlSchema();
		inputOutputAdapter.wsdlSchemaMap = wsdlSchemaMap ;
		inputOutputAdapter.wsdlSmElement = context.getSmElement() ;
		
		inputOutputAdapter.isCallActivity = isCallActivity ;
		inputOutputAdapter.callActivityProcessSchema = callActivityProcessSchema ;
		inputOutputAdapter.isManualTask = isManualTask;
		inputOutputAdapter.isJavaTask = isJavaTask ;
		inputOutputAdapter.rfSmelement = context.getRfsmElement();
		inputOutputAdapter.bpmnSchemaCache = bpmnSchemaCache ;

		StudioUIUtils.invokeOnDisplayThread( new Runnable() {

			@Override
			public void run() {
				mapperControl.setInput(inputOutputAdapter);
				System.out.println(inputOutputAdapter.getXSLT());
			}
		}, false);
		
	}
	

	private  BpmnEMapperInputOutputAdapter createMapperControl(Composite parent ) {
		final String XSLT_VALID = "<xsl:value-of select";
		final String XSLT_FOREACH_VALID = "<xsl:for-each select";
		BpmnEMapperControlArgs mEArgs = new BpmnEMapperControlArgs();
//		mEArgs.setExpandToShowAllMappingsOnStart(true);
		mEArgs.setAutoExpandSourceTreeLevel(1);
		mEArgs.setAutoExpandTargetTreeLevel(4);
		mEArgs.setXSLT(Xslt);
		initArgs(mEArgs);
		
		MapperUtils.refreshFunctions();
		
		mapperControl = EMapperFactory.createMapperControl(parent, mEArgs);
		BpmnEMapperInputOutputAdapter input = new BpmnEMapperInputOutputAdapter(mapperControl, mEArgs) {
			@Override
			public void setXSLT(String xslt) {
				if (inputOutputAdapter.getMapperArgs().getXSLT() != null) {
					ArrayList<String> params = new ArrayList<String>();
					params.add(inputOutputAdapter.getMapperArgs().getEntityUri());
					String serializedXslt = XSTemplateSerializer.serialize(xslt, params, new ArrayList<>());
					if(serializedXslt!= null && !isValidXslt(serializedXslt)){
						serializedXslt ="";
					}
					inputOutputAdapter.getMapperArgs().setXSLT(serializedXslt);
					updateModel(serializedXslt);
				}
				
			}
			
			private boolean isValidXslt(String xslt){
				if(xslt == null){
					return false;	
				} else if(xslt.contains(XSLT_VALID)){
					return true;
				} else if(xslt.contains(XSLT_FOREACH_VALID)){
					return true;
				} else
					return false;
			}
			
		};
		return input;
	}
	
	private  void initArgs(BpmnEMapperControlArgs mEArgs) {
		MapperXSDUtils.updateCustomFunctions(mapperContext.getProject().getName());
		List<?> receivingParams = XSTemplateSerializer.getReceivingParms(mEArgs.getXSLT());
		if (receivingParams.size() == 1) {
			String entityPath = (String) receivingParams.get(0);
			Entity entity = IndexUtils.getEntity(fproject.getProject().getName(), entityPath);
			if ( entity != null )
			mEArgs.setTargetElement(entity);
		}
	}
	
    
  MutableType getSmType( MutableSchema schema, ExpandedName typeName) {
    	
    	MutableType smType = MutableSupport.createType(schema, null, null); 
        smType.setExpandedName(typeName);
        smType.setSchema(schema);
        smType.setComplex();
        smType.setAllowedDerivation(SmComponent.EXTENSION | SmComponent.RESTRICTION);
//        smType.setDerivationMethod(SmComponent.EXTENSION);

        schema.addSchemaComponent(smType);

        
        return smType;
    }
  
  
   @Override
   protected SmElement ruleFunctionToSmTypeRef(RuleFunction rf, String name) {
       DefaultComponentFactory factory = new DefaultComponentFactory();
        MutableSchema schema = getSchema(factory, rf.getFullPath());
         ExpandedName rfInputTypeName= ExpandedName.makeName(schema.getNamespace(),name);
         MutableType rfInType = getSmType(factory, schema,rfInputTypeName);
      // Add the element
         MutableElement elem = factory.createElement();
         elem.setType(rfInType);
         elem.setSchema(schema);
         elem.setExpandedName(rfInputTypeName);
         schema.addSchemaComponent(elem);
         
//         ExpandedName argsTypeName= ExpandedName.makeName("args");
//         MutableType argsType = getSmType(factory, schema, n);
         MutableType argsType  = MutableSupport.createType(schema, null, null);
         argsType.setComplex();
         MutableSupport.addLocalElement(rfInType, "args", argsType, 1,1); 
         
        String projName = getMapperContext().getBEProject().getName();
        Symbols syms = rf.getScope();
        for (Iterator<?> itr = syms.getSymbolsList().iterator(); itr.hasNext();)
        {
            Symbol s = (Symbol) itr.next();
            String argName = s.getName();
            String typeName = s.getType();
            SmType smType = getSmTypeFromTypeNameRef(schema, projName, typeName ,getUIAgent(),null);
            MutableElement smEle = MutableSupport.createElement(schema, argName, smType);
            if(!s.isArray())
               MutableSupport.addElement(argsType, smEle, 0,1);  //Aha we dont support arrays in RuleFunction
            else
               MutableSupport.addElement(argsType, smEle, 0, Integer.MAX_VALUE);
            smEle.setNillable(true);
            
            if ((smType == RDFTnsFlavor.getBaseEventType()) || (smType == RDFTnsFlavor.getBaseConceptType()))
                smEle.setAllowedSubstitution(SmType.RESTRICTION + SmType.EXTENSION + SmType.SUBSTITUTION) ;

        }
        DefaultModelGroup contentModel = (DefaultModelGroup)rfInType.getContentModel();
                contentModel.setSchema(schema);
                elem.setSchema(schema);
        return elem;
    }
   
   @Override
   protected SmElement javaTaskArgsSmTypeRef(String entityPath) {
	   
	   boolean noMethodPresent = false;
	   String fullPath = entityPath;
	   String name = entityPath.substring(entityPath.lastIndexOf("/") + 1);
	   EObject eobj = getUserObject();
	   String projName = getMapperContext().getBEProject().getName();
	   EObjectWrapper<EClass, EObject> userObjWrapper = EObjectWrapper.wrap(eobj);
	   if (ExtensionHelper.isValidDataExtensionAttribute(userObjWrapper,
				BpmnMetaModelExtensionConstants.E_ATTR_METHOD_NAME)) {
			EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper
					.getAddDataExtensionValueWrapper(userObjWrapper);
			Object attribute = valueWrapper
					.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_METHOD_NAME);
			String javaFilePath = valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JAVA_FILE_PATH);
			IFile file =  BpmnIndexUtils.getFile(projName, javaFilePath, "java");
			if((attribute != null && ((String)attribute).isEmpty() ) || !(BpmnGraphUtils.isMethodPresentInJavaSrc(file, (String)attribute)) ){
				noMethodPresent = true;
			} else
				name = name + "." + (String) attribute;
		}
	   DefaultComponentFactory factory = new DefaultComponentFactory();
	   MutableSchema schema = getSchema(factory, fullPath/*rf.getFullPath()*/);
	   ExpandedName rfInputTypeName= ExpandedName.makeName(schema.getNamespace(),name);
	   MutableType rfInType = getSmType(factory, schema,rfInputTypeName);
	   // Add the element
	   MutableElement elem = factory.createElement();
	   elem.setType(rfInType);
	   elem.setSchema(schema);
	   elem.setExpandedName(rfInputTypeName);
	   schema.addSchemaComponent(elem);
	   if(noMethodPresent){
			return elem;
		}

	   //         ExpandedName argsTypeName= ExpandedName.makeName("args");
	   //         MutableType argsType = getSmType(factory, schema, n);
	   MutableType argsType  = MutableSupport.createType(schema, null, null);
	   argsType.setComplex();
	   MutableSupport.addLocalElement(rfInType, "args", argsType, 1,1); 


	   List<SymbolEntryImpl> symbols = BpmnModelUtils.getMethodArguments(getUserObject());

	   for (SymbolEntryImpl symbol: symbols) {

		   String argName = symbol.getKey();
		   
		   EObjectWrapper<EClass, EObject> typeWrapper = EObjectWrapper.wrap(symbol.getType());
			String type = typeWrapper.toString();

			type = BpmnModelUtils.getType(type);

			if (type.equals("Concept") || type.equals("ConceptReference")) {
				type = symbol.getPath();
			} 
			SmType smType = null;
//			if(typeWrapper.toString().equals("ConceptReference")){
//				MutableType concType = MutableSupport.createType(schema, null,	null);
//				MutableSupport.addAttribute(concType, "ref", XSDL.LONG, false);
//				smType = concType;
//			}
//			else {
				 smType = getSmTypeFromTypeNameRef(schema, projName, type ,getUIAgent(),null);
//			}
		   MutableElement smEle = MutableSupport.createElement(schema, argName, smType);
		   
		   if(!symbol.isArray())
			   MutableSupport.addElement(argsType, smEle, 0,1);
		   else
			   MutableSupport.addElement(argsType, smEle, 0, Integer.MAX_VALUE);

		   smEle.setNillable(true);

		   if ((smType == RDFTnsFlavor.getBaseEventType()) || (smType == RDFTnsFlavor.getBaseConceptType()))
			   smEle.setAllowedSubstitution(SmType.RESTRICTION + SmType.EXTENSION + SmType.SUBSTITUTION) ;

	   }
	   DefaultModelGroup contentModel = (DefaultModelGroup)rfInType.getContentModel();
	   contentModel.setSchema(schema);
	   elem.setSchema(schema);
	   return elem;
   }

   
	public void buildWsdlElement(String uri, SmElement element, String wsdlName, String oldXslt) {
		this.entityURI = uri;
		processEntitySchema = element ;
		ipMapperTargetSchema = element ;
		loadWsdlSchema();
		String xslt = oldXslt;
		if(xslt == null || xslt.trim().isEmpty())
			xslt = updateSchema(entityURI);
		updateMapperPanel(xslt);
	}
	 
	private void loadWsdlSchema(){
		try {
			WsWsdl wsdl = this.wsdlWrapper.getWsdl();
			Iterator internalSchemas = wsdl.getInternalSchemas();
			while(internalSchemas.hasNext()){
				XSDLSchema schemaLocal = (XSDLSchema)internalSchemas.next();
				NamespaceCounter nsc = new NamespaceCounter();
				schemaLocal.accept(nsc);

				XSDWriter xsdWriter = new XSDWriter();
				OutputStream oStream = new ByteArrayOutputStream();
				Writer w = new OutputStreamWriter(oStream,
						XMLStreamReader.getJavaEncodingName(XMLStreamReader.UTF8));
				xsdWriter.writeXmlHeader(w, XMLStreamReader.getXMLEncodingName(XMLStreamReader.UTF8));
				xsdWriter.setProperty(XSDWriter.SORTED, false);
				xsdWriter.write(schemaLocal, w, null);
				w.flush();

				String xsd = oStream.toString();
				xsd = addImports(xsd, nsc);
				wsdlSchemaMap.put(schemaLocal.getNamespace(), xsd);
			 }
			} catch ( Exception e ) {
				System.out.println("exception loading wsdl schema");
			}
	}
   
    
	private String addImports(String xsd, NamespaceCounter nsc) throws Exception {
		
		Iterator ns = nsc.getImportedNamespaces();
		XiNode rootNode = xiParser.parse(new InputSource(new ByteArrayInputStream(xsd.getBytes())));
		XiNode schemaNode = XiChild.getChild(rootNode, ExpandedName
				.makeName(XSDL.NAMESPACE, "schema"));
		String schemaNS = schemaNode.getName().getNamespaceURI();
		while (ns.hasNext()) {
			String importNS = (String) ns.next();
			XiNode importNode = xiFactory.createElement(ExpandedName.makeName(schemaNS, "import"));
			importNode.setAttributeStringValue(ExpandedName.makeName("namespace"), importNS);
			schemaNode.insertBefore(importNode, XiChild.getFirstChild(schemaNode));
		}
		xsd = XiSerializer.serialize(schemaNode);
		return xsd;
	}
	

	    
    @Override
	public void setEntityForCallActivity(com.tibco.cep.designtime.model.Entity entity, String oldXslt, String lName) {
		isCallActivity = true ;
		this.entity=entity;
		if(entity != null) {
			if(entity instanceof ProcessModel){
				this.entityURI = entity.getFolderPath() ;
				if(this.entityURI.equals("/"))
					this.entityURI = this.entityURI+ entity.getName();
				else
					this.entityURI = this.entityURI + "/"+entity.getName();
				
				String name = entityURI.substring(entityURI.lastIndexOf('/') + 1);
			    String nsURI = RDFTnsFlavor.BE_NAMESPACE + entityURI;
				ExpandedName exname = ExpandedName.makeName(nsURI, name);
				SmElement element = (SmElement)getMapperContext().getBEProject()
						.getSmElement(exname);
				if(element == null){
					ProcessModel pm = (ProcessModel) entity;
					element = pm.toSmElement();
				}
				element = processProcessSchema(element);
				processEntitySchema = element ;
				ipMapperTargetSchema=element;
				if(element == null){
					// sometimes i am getting null, still investigating why
					setEntityURI(null, "");
					return;
				}
//				MutableSchema schema = SmFactory.newInstance().createMutableSchema();
				MutableSchema schema = DefaultComponentFactory
						.getTnsAwareInstance().createSchema();
				schema.setFlavor(XSDL.FLAVOR);
				ExpandedName inputName = ExpandedName.makeName("Input");
		        MutableType inputT  = MutableSupport.createType(schema, null, null);
		        inputT.setComplex();
		        MutableSupport.addAttribute(inputT, "process_uri", XSDL.STRING, false);
		        SmType smType = element.getType(); 
	            MutableSupport.addLocalElement(inputT, lName, smType, 1, 1);
		        MutableElement inputE = createSmElement(inputName, inputT);
		        schema.addSchemaComponent(inputE);
		        schema.getSingleFragment().addImport(new TnsImportImpl(null, null, element.getNamespace(), SmNamespace.class));
		        callActivityProcessSchema = inputE ;
		        ipMapperTargetSchema = inputE;
				setEntitySchema(inputE);
				String xslt = oldXslt;
				if(xslt == null || xslt.trim().isEmpty())
					xslt = updateSchema(entityURI);
				updateMapperPanel(xslt);
			}else{
				setEntityURI(null, "");
			}
			
		} else {
			setEntityURI(null, "");
		}
	}
	
    @Override
    public SmElement processProcessSchema(SmElement element){
       MutableType elementType = (MutableType) element.getType();
       if (elementType != null) {
              MutableSchema schema = DefaultComponentFactory.getTnsAwareInstance().createSchema();
              schema.setNamespace(elementType.getNamespace());
              schema.setFlavor(XSDL.FLAVOR);
     MutableType type = newSmType(ExpandedName.makeName(elementType.getNamespace(), elementType.getName()), schema);//MutableSupport.createType(schema, elementType.getName());
    
     SmElement createElement = (SmElement)schema.getComponent(SmComponent.ELEMENT_TYPE, element.getName());//MutableSupport.createElement(schema, element.getName(), type);
              elementType =(MutableType) elementType.clone();
              SmModelGroup smModelGroup = elementType.getContentModel();
              if (smModelGroup != null) {
                     Iterator<?> particles = smModelGroup.getParticles();
                     while (particles.hasNext()) {
                                  SmParticle child = (SmParticle) particles.next();
                                  addNsToType(schema, type, child, type.getNamespace());
                           }
              }
              
              smModelGroup = elementType.getAttributeModel();
              if (smModelGroup != null) {
                     Iterator<?> particles = smModelGroup.getParticles();
                     while (particles.hasNext()) {
                                  SmParticle child = (SmParticle) particles.next();
                                  SmParticleTerm term = child.getTerm();
                                  if(term != null && term instanceof SmAttribute){
                                  SmAttribute attr = (SmAttribute) term;
                                  if(attr.getName().equalsIgnoreCase("extId")){
                                         MutableSupport.addAttribute(type, attr.getName(), XSDL.STRING, false);
                                  }
                                  }
                           }
              }
              
              return createElement;
 }
       return element;
       
}
    
    public void updateModel(String newXSLT){

    	if(this.context.isInputMapper)  { 	
    		MapperPropertySection imps = null;
			if (this.context.mapperPropertySection instanceof InputMapperPropertySection)
				imps = (InputMapperPropertySection) this.context.mapperPropertySection;
			else if (this.context.mapperPropertySection instanceof WebServiceInputPropertySection)
				imps = (WebServiceInputPropertySection) this.context.mapperPropertySection;
			if (imps == null) {
				return;
			}
    		EObject userObject = imps.getUserObject();
    		if(userObject != null){
    			//    			String newXSLT = currentMapper.getNewXSLT();
    			if(newXSLT != null  && !newXSLT.equals(imps.currentXslt)){
    				Map<String, Object> updateList = new HashMap<String, Object>();
    				imps.currentXslt = newXSLT;
    				if(newXSLT.isEmpty())
    					updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_INPUT_MAP_XSLT, null);
    				else
    					updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_INPUT_MAP_XSLT, newXSLT);
    				imps.updatePropertySection(updateList);
    			}
    		}
    	} else {
    		MapperPropertySection omps = null;
    		if(this.context.mapperPropertySection instanceof OutputMapperPropertySection) {
    			omps = (OutputMapperPropertySection) this.context.mapperPropertySection ;
    		} else if (this.context.mapperPropertySection instanceof WebServiceOutputPropertySection) {
    			omps = (WebServiceOutputPropertySection) this.context.mapperPropertySection;
    		}
    		if (omps == null) {
				return;
			}
    		EObject userObject = omps.getUserObject();
    		if(userObject != null){
    			//    			String newXSLT = currentMapper.getNewXSLT();
    			if(newXSLT != null  && !newXSLT.equals(omps.currentXslt)){
    				Map<String, Object> updateList = new HashMap<String, Object>();
    				omps.currentXslt = newXSLT;
    				if(newXSLT.isEmpty())
    					updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_OUTPUT_MAP_XSLT, null);	
    				else
    					updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_OUTPUT_MAP_XSLT, newXSLT);
    				omps.updatePropertySection(updateList);
    			}
    		}
    	}
    }

	@Override
	public void setVisible(boolean visible) {
		
	}



}
