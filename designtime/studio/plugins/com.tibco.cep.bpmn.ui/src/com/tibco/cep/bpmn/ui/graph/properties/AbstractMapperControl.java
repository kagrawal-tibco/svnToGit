package com.tibco.cep.bpmn.ui.graph.properties;

import java.awt.Font;
import java.awt.Frame;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Composite;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tibco.be.model.functions.PredicateWithXSLT;
import com.tibco.be.model.rdf.RDFTnsFlavor;
import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.model.rdf.primitives.RDFBaseConceptTerm;
import com.tibco.be.model.rdf.primitives.RDFBaseEventTerm;
import com.tibco.be.model.rdf.primitives.RDFBaseProcessTerm;
import com.tibco.be.model.rdf.primitives.RDFBaseTerm;
import com.tibco.be.model.rdf.primitives.RDFPrimitiveTerm;
import com.tibco.be.model.rdf.primitives.RDFUberType;
import com.tibco.be.util.XSTemplateSerializer;
import com.tibco.be.util.XiSupport;
import com.tibco.cep.bpmn.core.BpmnCorePlugin;
import com.tibco.cep.bpmn.core.index.BpmnIndexUtils;
import com.tibco.cep.bpmn.core.utils.BpmnModelUtils;
import com.tibco.cep.bpmn.core.wsdl.WsdlWrapper;
import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.ontology.symbols.SymbolEntryImpl;
import com.tibco.cep.bpmn.model.designtime.utils.BpmnCommonModelUtils;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.runtime.templates.MapperConstants;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.graph.BpmnGraphUtils;
import com.tibco.cep.bpmn.ui.graph.properties.MapperPropertySection.EntityMapperContext;
import com.tibco.cep.bpmn.ui.mapper.BpmnSchemaCache;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.process.ProcessModel;
import com.tibco.cep.designtime.model.process.SubProcessModel;
import com.tibco.cep.designtime.model.rule.RuleFunction;
import com.tibco.cep.designtime.model.rule.Symbol;
import com.tibco.cep.designtime.model.rule.Symbols;
import com.tibco.cep.mapper.xml.xdata.DefaultImportRegistry;
import com.tibco.cep.mapper.xml.xdata.DefaultNamespaceMapper;
import com.tibco.cep.mapper.xml.xdata.NamespaceMapper;
import com.tibco.cep.mapper.xml.xdata.bind.BindingElementInfo;
import com.tibco.cep.mapper.xml.xdata.bind.BindingManipulationUtils;
import com.tibco.cep.mapper.xml.xdata.bind.BindingRunner;
import com.tibco.cep.mapper.xml.xdata.bind.DefaultStylesheetResolver;
import com.tibco.cep.mapper.xml.xdata.bind.ReadFromXSLT;
import com.tibco.cep.mapper.xml.xdata.bind.StylesheetBinding;
import com.tibco.cep.mapper.xml.xdata.bind.StylesheetResolver;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateEditorConfiguration;
import com.tibco.cep.mapper.xml.xdata.xpath.CoercionSet;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.FunctionResolver;
import com.tibco.cep.mapper.xml.xdata.xpath.VariableDefinition;
import com.tibco.cep.mapper.xml.xdata.xpath.VariableDefinitionList;
import com.tibco.cep.repo.BETargetNamespaceCache;
import com.tibco.cep.repo.provider.GlobalVariablesProvider;
import com.tibco.cep.studio.core.index.model.DecisionTableElement;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.rdf.EMFRDFTnsFlavor;
import com.tibco.cep.studio.core.util.mapper.MapperCoreUtils;
import com.tibco.cep.studio.core.util.mapper.MapperInvocationContext;
import com.tibco.cep.studio.core.util.mapper.XMLReadException;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingEditor;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingEditorPanel;
import com.tibco.cep.studio.ui.xml.utils.MapperUtils;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.datamodel.helpers.XiSerializer;
import com.tibco.xml.schema.SmAttribute;
import com.tibco.xml.schema.SmComponent;
import com.tibco.xml.schema.SmComponentProviderEx;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmFactory;
import com.tibco.xml.schema.SmModelGroup;
import com.tibco.xml.schema.SmNamespace;
import com.tibco.xml.schema.SmNamespaceProvider;
import com.tibco.xml.schema.SmParticle;
import com.tibco.xml.schema.SmParticleTerm;
import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.build.MutableElement;
import com.tibco.xml.schema.build.MutableSchema;
import com.tibco.xml.schema.build.MutableSupport;
import com.tibco.xml.schema.build.MutableType;
import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.schema.helpers.SmComponentProviderExOnSmNamespaceProvider;
import com.tibco.xml.schema.impl.DefaultComponentFactory;
import com.tibco.xml.schema.impl.DefaultModelGroup;
import com.tibco.xml.schema.impl.DefaultSchema;
import com.tibco.xml.schema.impl.DefaultType;
import com.tibco.xml.schema.impl.SmNamespaceProviderImpl;
import com.tibco.xml.schema.xtype.SmSequenceTypeFactory;
import com.tibco.xml.tns.TargetNamespaceProvider;
import com.tibco.xml.tns.TnsComponent;
import com.tibco.xml.tns.cache.TnsCache;
import com.tibco.xml.tns.impl.TargetNamespaceCache;
import com.tibco.xml.tns.parse.impl.TnsImportImpl;

public abstract class AbstractMapperControl {

	public static final String ENTITY_URI = "entity_uri";
	public static final String DESTINATION_URI = "destination_uri";
	public static final String RULESESSION_URI = "rulesession_uri";
	protected static final MutableSchema defaultSchema;
	protected static final MutableType defaultType;
	protected final static SmElement defaultOutputSchema;
	protected static String copyXsltTemplate;
	protected boolean isManualTask ;
	protected boolean isJavaTask ;
	static {
		defaultSchema = SmFactory.newInstance().createMutableSchema();
		defaultSchema.setNamespace("http://www.tibco.com/be/bpmn/task/input");
		defaultType = MutableSupport.createType(defaultSchema,"any");
		defaultOutputSchema = MutableSupport.createElement(defaultSchema,"output",defaultType);
		MutableSupport.addOptionalLocalElement(defaultType, XSDL.ANYTYPE_NAME.localName, XSDL.ANY_TYPE);
	}

	MapperContext mapperContext;
	MapperInvocationContext mapperInvoContext;
	UIAgent uiAgent;
	Composite parent;
	SmElement entitySchema;
	BpmnXSDResourceImpl genXsdRes;
	WsdlWrapper wsdlWrapper;
	protected BindingEditorPanel bindingEditorPanel;
	protected BindingEditor bindingEditor;
	protected ExprContext expressionContext;
	protected String entityURI;
	protected boolean isCallActivity = false;
	protected SmElement processEntitySchema;
	protected SmElement rulefunctionEntity;
	protected SmElement ipMapperTargetSchema;
	protected SmElement ipMapperSourceSchema;
	protected Entity entity;
	protected com.tibco.cep.designtime.core.model.Entity coreModelEntity;
	protected SmElement callActivityProcessSchema ;
	protected SmElement manualTaskSchema ;
	public IProject fproject;
	protected static BpmnSchemaCache bpmnSchemaCache ;
	protected EObject userObject;

	/**
	 * @param agent
	 */
	public AbstractMapperControl( MapperInvocationContext mapperInvoContext, EntityMapperContext context, Composite parent) {
		super();
		this.mapperContext = context;
		this.mapperInvoContext=mapperInvoContext;
		this.parent = parent;
	}


	/**
	 * @return the MapperContext
	 */
	public MapperContext getMapperContext() {
		return mapperContext;
	}

	/**
	 * @return the agent
	 */
	public UIAgent getUIAgent() {
		return uiAgent;
	}

	/**
	 * @param uiAgent
	 *            the uiAgent to set
	 */
	public void setUIAgent(UIAgent uiAgent) {
		this.uiAgent = uiAgent;
	}

	/**
	 * @return the parent
	 */
	public Composite getParent() {
		return parent;
	}

	//	/**
	//	 * @return the awtframe
	//	 */
	//	public Frame getFrame() {
	//		return awtframe;
	//	}

	//	/**
	//	 * @param awtframe
	//	 *            the Frame to set
	//	 */
	//	public void setFrame(Frame awtframe) {
	//		this.awtframe = awtframe;
	//	}

	/**
	 * @return the schema
	 */
	public SmElement getOutputSchema() {
		return defaultOutputSchema;
	}


	//	/**
	//	 * @return the mapperPanel
	//	 */
	//	public Container getMapperPanel() {
	//		return mapperPanel;
	//	}

	//	/**
	//	 * @param mapperPanel
	//	 *            the mapperPanel to set
	//	 */
	//	public void setMapperPanel(Container mapperPanel) {
	//		this.mapperPanel = mapperPanel;
	//	}

	/**
	 * @return the bindingEditor
	 */
	public BindingEditor getBindingEditor() {
		return bindingEditor;
	}

	/**
	 * @param bindingEditor
	 *            the bindingEditor to set
	 */
	public void setBindingEditor(BindingEditor bindingEditor) {
		this.bindingEditor = bindingEditor;
	}

	/**
	 * @return the expressionContext
	 */
	public ExprContext getExpressionContext() {
		return expressionContext;
	}

	/**
	 * @param expressionContext
	 *            the expressionContext to set
	 */
	public void setExpressionContext(ExprContext expressionContext) {
		this.expressionContext = expressionContext;
	}

	/**
	 * @return the bindingEditorPanel
	 */
	public BindingEditorPanel getBindingEditorPanel() {
		return bindingEditorPanel;
	}

	/**
	 * @param bindingEditorPanel
	 *            the bindingEditorPanel to set
	 */
	public void setBindingEditorPanel(BindingEditorPanel bindingEditorPanel) {
		this.bindingEditorPanel = bindingEditorPanel;
	}

	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return getBindingEditorPanel().isEnabled();
	}

	/**
	 * @param enabled
	 *            the enabled to set
	 */
	public void setEnabled(final boolean enabled) {
		getBindingEditorPanel().setEnabled(enabled);
	}

	/**
	 * @return the visible
	 */
	public abstract boolean isVisible() ;

	/**
	 * @param visible
	 *            the visible to set
	 */
	public abstract void setVisible(final boolean visible);

	/**
	 * @return the entitySchema
	 */
	public SmElement getEntitySchema() {
		if(entitySchema == null)
			return getOutputSchema();
		else
			return entitySchema;
	}

	/**
	 * @param entitySchema the entitySchema to set
	 */
	protected void setEntitySchema(SmElement entitySchema) {
		this.entitySchema = entitySchema;
	}

	/**
	 * @return the entityURI
	 */
	public String getEntityURI() {
		return entityURI;
	}

	/**
	 * @param entityURI
	 * the entity to set
	 */
	public void setEntityURI(String entityURI, String oldXslt) {
		if(entityURI != null ) {
			this.entityURI = entityURI;
			MutableElement element = buildElement(entityURI);
			setEntitySchema(element);
			String xslt =oldXslt;
			if(xslt == null || xslt.trim().isEmpty())
				xslt = updateSchema(entityURI);
			updateMapperPanel(xslt);
		} else {
			setEntitySchema(null);
			String xslt = updateSchema(entityURI);
			updateMapperPanel(xslt);
		}

	}


	public void buildWsdlElement(String uri, SmElement element, String wsdlName, String oldXslt) {
		this.entityURI = uri;
		setEntitySchema(element);
		ipMapperTargetSchema = element ;
		String xslt = oldXslt;
		if(xslt == null || xslt.trim().isEmpty())
			xslt = updateSchema(entityURI);
		updateMapperPanel(xslt);
	}

	public void setEntity(Entity entity, String oldXslt) {
		this.entity=entity;
		if(entity != null) {
			//			genXsdRes.setEnt((com.tibco.cep.designtime.core.model.Entity) entity);
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
				SmElement oldElt=element;
				if(element == null){
					ProcessModel pm = (ProcessModel) entity;
					element = pm.toSmElement();
				}
				if(element == null){
					// sometimes i am getting null, still investigating why
					setEntityURI(null, "");
					return;
				}
				element = processProcessSchema(element);

				processEntitySchema = element ;
//				processEntitySchema = buildProcessSchema();
				MutableSchema schema =/* SmFactory.newInstance().createMutableSchema();*/ DefaultComponentFactory
						.getTnsAwareInstance().createSchema();
				MutableType type = MutableSupport.createType(schema, "job");
				//	            type.setDerivationMethod(SmComponent.EXTENSION);
				SmType smType = element.getType();
				MutableElement addLocalElement = MutableSupport.addLocalElement(type, "job", smType, 1,1); 
				schema.addSchemaComponent(addLocalElement);
//				processEntitySchema = addLocalElement;
				setEntitySchema(addLocalElement);
				String xslt = oldXslt;
				if(xslt == null || xslt.trim().isEmpty())
					xslt = updateSchema(entityURI);
				if(! BpmnModelUtils.isSWTMapper ( ) ) {
					updateMapperPanel(xslt);
				} else {	
					updateMapperPanel(xslt);
				}
				//				updateMapperPanel(xslt);
			}else{
				setEntityURI(entity.getFullPath(), oldXslt);
			}

		} else {
			setEntityURI(null, "");
		}
	}
	
	protected MutableType newSmType(ExpandedName typeName, MutableSchema schema) {
	        MutableType smType = DefaultComponentFactory.getTnsAwareInstance().createType();
	        smType.setExpandedName(typeName);
	        smType.setSchema(schema);
	        smType.setComplex();
	        smType.setAllowedDerivation(SmComponent.EXTENSION | SmComponent.RESTRICTION);

	        schema.addSchemaComponent(smType);

	        // Add the element
	        MutableElement elem = DefaultComponentFactory.getTnsAwareInstance().createElement();
	        elem.setType(smType);
	        elem.setSchema(schema);
	        elem.setExpandedName(typeName);
	        /**
	         MutableType typeRef= mcf.createTypeRef();
	         typeRef.setReference(schema);
	         typeRef.setSchema(schema);
	         typeRef.setExpandedName(smType.getExpandedName());
	         MutableElement element= MutableSupport.createElement(schema, smType.getExpandedName().getLocalName(), typeRef);
	         **/
	        schema.addSchemaComponent(elem);
	        return smType;
	    }
	
	

	public SmElement processProcessSchema(SmElement element){
		MutableType elementType = (MutableType) element.getType();
		if (elementType != null) {
			MutableSchema schema = DefaultComponentFactory.getTnsAwareInstance().createSchema();
			schema.setNamespace(elementType.getNamespace());
			schema.setFlavor(XSDL.FLAVOR);
			MutableType type = MutableSupport.createType(schema, elementType.getName());
			type.setNamespace(elementType.getNamespace());
			schema.addSchemaComponent(type);
			MutableElement createElement = MutableSupport.createElement(schema, element.getName(), type);
			createElement.setNamespace(element.getNamespace());
			schema.addSchemaComponent(element);
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
			DefaultModelGroup contentModel = (DefaultModelGroup)type.getContentModel();
			contentModel.setSchema(schema);
			createElement.setSchema(schema);
			return createElement;
		}
		return element;

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
		if (type == null) {// Element referenced from schema
			ExpandedName makeName = ExpandedName.makeName( element.getNamespace(), element.getName());
			SmElement ele = (SmElement)mapperContext.getBEProject()
					.getSmElement(makeName);
			ExpandedName en = element.getExpandedName();
			// MutableComponent c = createComponentRef(schema, en,
			// SmComponent.ELEMENT_TYPE);
			MutableSupport.addElement(onType, (MutableElement) ele, min, max);

			schema.getSingleFragment().addImport(
					new TnsImportImpl(null, null, en.namespaceURI,
							SmNamespace.class));
		} else {//Allow for in place schema elements
			String localName = pTerm.getExpandedName().localName;
			ExpandedName en = type.getExpandedName();
			if(en != null && en.namespaceURI != null && type.getAtomicType() == null){
				ExpandedName expName = ExpandedName.makeName(nameSpaceUri, localName);
				MutableElement newElement = createSmElement(expName, type);
				MutableSupport.addElement(onType, newElement, min, max);
				//        		MutableSupport.addLocalElement(onType, localName, mutableType, min, max);
				schema.getSingleFragment().addImport(new TnsImportImpl(null, null, en.namespaceURI, SmNamespace.class));
			} else {// This is a local type
				ExpandedName expName = ExpandedName.makeName(nameSpaceUri, localName);
				MutableElement newElement = createSmElement(expName, type);
				MutableSupport.addElement(onType, newElement, min, max);

			}
		}
	}

	public static MutableElement createSmElement(ExpandedName typeName, SmType smType){
		MutableElement elem = DefaultComponentFactory.getTnsAwareInstance().createElement();
		elem.setType(smType);
		elem.setSchema(smType.getSchema());
		elem.setExpandedName(typeName);
		return elem;
	}

	public void createSchemaForManualTask( String oldXslt , String lname ) {

	}

	public void setEntityForCallActivity(Entity entity, String oldXslt, String lName) {
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
				ipMapperTargetSchema = element;
				if(element == null){
					// sometimes i am getting null, still investigating why
					setEntityURI(null, "");
					return;
				}
				MutableSchema schema = SmFactory.newInstance().createMutableSchema();
				MutableType type = MutableSupport.createType(schema, lName);
				MutableType typeNew = MutableSupport.createType(schema, "Input");
				MutableSupport.addAttribute(typeNew, "process_uri", XSDL.STRING, false);
				MutableElement addLocalElement = MutableSupport.addLocalElement(type, "Input" , typeNew, 1,1); 
				//	            type.setDerivationMethod(SmComponent.EXTENSION);
				SmType smType = element.getType(); 
				MutableSupport.addLocalElement(typeNew, lName , smType, 1,1); 
				setEntitySchema(addLocalElement);
				String xslt = oldXslt;
				if(xslt == null || xslt.trim().isEmpty())
					xslt = updateSchema(entityURI);
				if(! BpmnModelUtils.isSWTMapper ( ) ) {
					updateMapperPanel(xslt);
				} else {	
					updateMapperPanel(xslt);
				}
			}else{
				setEntityURI(null, "");
			}

		} else {
			setEntityURI(null, "");
		}
	}

	public SmElement processProcessSchemaForCallActivity(SmElement element) {
		// TODO Auto-generated method stub
		return processProcessSchema(element);
	}

	public void setEntityForBusinessRule(EObjectWrapper<EClass, EObject> wrap , String oldXslt){
//		this.entity=(Entity) wrap;
		String  lname = wrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_NAME);
		lname =lname.replace(".", "$");
		MutableSchema schema = SmFactory.newInstance().createMutableSchema();
		MutableType type = MutableSupport.createType(schema,lname);
		MutableElement defaultOutputSchema = MutableSupport.createElement(schema,lname,type);
		defaultOutputSchema.setNillable(true);
		MutableSupport.addLocalElement(type , MapperConstants.RULES, XSDL.STRING, 0, Integer.MAX_VALUE);

		setEntitySchema(defaultOutputSchema);
		ipMapperTargetSchema = defaultOutputSchema;
		String xslt = oldXslt;
		if(xslt == null || xslt.trim().isEmpty())
			xslt = updateSchema(entityURI);
		if(! BpmnModelUtils.isSWTMapper ( ) ) {
			updateMapperPanel(xslt);
		} else {	
			updateMapperPanel(xslt);
		}

	}

	public SmElement manualTaskToSmTypeRef(){
		DefaultComponentFactory factory = new DefaultComponentFactory();
		MutableSchema schema = getSchema(factory, "/manualTask");
		ExpandedName mtInputTypeName= ExpandedName.makeName(schema.getNamespace(),"manualTask");
		MutableType mtInType = getSmType( factory , schema, mtInputTypeName);
		MutableElement elem = factory.createElement();
		elem.setType(mtInType);
		elem.setSchema(schema);
		elem.setExpandedName(mtInputTypeName);
		schema.addSchemaComponent(elem);
		MutableType argsType  = MutableSupport.createType(schema, null, null);
		argsType.setComplex();
		MutableSupport.addLocalElement( mtInType, "args" , argsType , 1 , 1 ) ; 
		SmType smType = XSDL.STRING ;
		MutableElement smEle = MutableSupport.createElement( schema, "emailHost", smType ) ;
		MutableSupport.addElement(argsType, smEle, 1 ,1) ;  
		smEle = MutableSupport.createElement(schema, "toEmailId", smType ) ;
		MutableSupport.addElement(argsType, smEle, 1 ,1) ;  
		smEle = MutableSupport.createElement(schema, "fromEmailId", smType ) ;
		MutableSupport.addElement(argsType, smEle, 1 ,1) ;
		smEle = MutableSupport.createElement(schema, "userName", smType ) ;
		MutableSupport.addElement(argsType, smEle, 1 ,1) ;
		smEle = MutableSupport.createElement(schema, "password", smType ) ;
		MutableSupport.addElement(argsType, smEle, 1,1) ;
		smEle = MutableSupport.createElement(schema, "eventUri", smType ) ;
		MutableSupport.addElement(argsType, smEle, 1 ,1);
		smEle = MutableSupport.createElement(schema, "Port", smType ) ;
		MutableSupport.addElement(argsType, smEle, 1,1);
		smEle = MutableSupport.createElement(schema, "Host", smType ) ;
		MutableSupport.addElement(argsType, smEle, 1,1);
		DefaultModelGroup contentModel = (DefaultModelGroup)mtInType.getContentModel();
		contentModel.setSchema(schema);
		elem.setSchema(schema);
		return elem ;
	}
	
//	public SmElement javaTaskArgsSmTypeRef(){
//		Map< String , String > map = new HashMap< String ,String >();
//		map = BpmnModelUtils.getJavaResourceArgs();
//		DefaultComponentFactory factory = new DefaultComponentFactory();
//		MutableSchema schema = getSchema(factory, "/javaTask");
//		ExpandedName mtInputTypeName= ExpandedName.makeName(schema.getNamespace(),"javaTask");
//		MutableType mtInType = getSmType( factory , schema, mtInputTypeName);
//		MutableElement elem = factory.createElement();
//		elem.setType(mtInType);
//		elem.setSchema(schema);
//		elem.setExpandedName(mtInputTypeName);
//		schema.addSchemaComponent(elem);
//		MutableType argsType  = MutableSupport.createType(schema, null, null);
//		argsType.setComplex();
//		MutableSupport.addLocalElement( mtInType, "args" , argsType , 1 , 1 ) ; 
//		for ( Map.Entry<String, String > mp : map.entrySet()) {
//			SmType smType = null;
//			if (mp.getKey().equals("String") ) {
//				smType = XSDL.STRING ;
//
//			}
//			else if (mp.getKey().equals("Boolean") ) {
//				smType = XSDL.BOOLEAN ;
//			}
//			else if (mp.getKey().equals("Double") ) {
//				smType = XSDL.DOUBLE ;
//			}
//			else if (mp.getKey().equals("Integer") ) {
//				smType = XSDL.INTEGER ;
//			}
//			else if (mp.getKey().equals("Object") ) {
//				smType = XSDL.ANY_TYPE ;
//			}
//			else {
//				smType = XSDL.ANY_TYPE ; 
//			}
//			MutableElement smEle = MutableSupport.createElement( schema, mp.getValue(), smType ) ;
//			MutableSupport.addElement(argsType, smEle, 1 ,1) ;  
//		}
//
//		DefaultModelGroup contentModel = (DefaultModelGroup)mtInType.getContentModel();
//		contentModel.setSchema(schema);
//		elem.setSchema(schema);
//		return elem ;
//	}
	
	protected SmElement javaTaskArgsSmTypeRef(String entityPath) {
		
		String fullPath = entityPath;
		boolean noMethodPresent = false;
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
		List<SymbolEntryImpl> symbols = BpmnModelUtils.getMethodArguments(getUserObject());
		
		DefaultComponentFactory factory = new DefaultComponentFactory();
		MutableSchema schema = getSchema(factory, fullPath /*rf.getFullPath()*/);
		//ExpandedName rfInputTypeName= ExpandedName.makeName(schema.getNamespace(),rf.getName()+"_arguments");
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
		ExpandedName argsTypeName= ExpandedName.makeName(schema.getNamespace(),"args");
		MutableType argsType = getSmType(factory, schema, argsTypeName);
		MutableSupport.addLocalElement(rfInType, "args", argsType, 1,1); 
		

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
//			SmType smType = getSmTypeFromTypeNameRef(schema, projName, type,getUIAgent(),null);
			MutableElement smEle = MutableSupport.createElement(schema, argName, smType);
			
			if(!symbol.isArray()) {
				MutableSupport.addElement(argsType, smEle, 0, 1); 
			} else {
				MutableSupport.addElement(argsType, smEle, 0, Integer.MAX_VALUE);
			}
			
			smEle.setNillable(true);

			if ((smType == RDFTnsFlavor.getBaseEventType()) || (smType == RDFTnsFlavor.getBaseConceptType())) {
				smEle.setAllowedSubstitution(SmType.RESTRICTION + SmType.EXTENSION + SmType.SUBSTITUTION) ;
			}	

		}
		
		DefaultModelGroup contentModel = (DefaultModelGroup)rfInType.getContentModel();
		contentModel.setSchema(schema);
		elem.setSchema(schema);
		
		return elem;
	}
	
	
	/**
	 * @param entityPath
	 * @return
	 */
	public  MutableElement buildElement(String entityPath) {
		SmElement entityElement = null;
		if ( isManualTask ) {
			entityElement = manualTaskToSmTypeRef() ;
			ipMapperTargetSchema = entityElement;
			return ( MutableElement )entityElement ;
		}
		if ( isJavaTask ) {
			entityElement = javaTaskArgsSmTypeRef(entityPath);
			ipMapperTargetSchema = entityElement;
			return ( MutableElement )entityElement ;
		}
		if (entityPath != null) {
			Ontology o = getMapperContext().getBEProject().getOntology();
			Entity entity = o.getEntity(entityURI);
			String name = null;
			if (entity == null) {
				DesignerElement designerElement = CommonIndexUtils.getElement(
						o.getName(), entityURI);
				if (designerElement instanceof DecisionTableElement) {
					DecisionTableElement table = (DecisionTableElement) designerElement;
					Table implementation = (Table) table.getImplementation();
					String implementsURI = implementation.getImplements();
					Entity implEmtity = o.getEntity(implementsURI);
					if (implEmtity instanceof RuleFunction) {
						entityElement = ruleFunctionToSmTypeRef(
								(RuleFunction) implEmtity, table.getName());
						rulefunctionEntity = entityElement;
						ipMapperTargetSchema = entityElement;
					}
				}
			} else if (entity instanceof RuleFunction) {
				entityElement = ruleFunctionToSmTypeRef((RuleFunction) entity,
						entity.getName());
				rulefunctionEntity = entityElement;
				ipMapperTargetSchema = entityElement;
			} else {
				name = entityPath.substring(entityPath.lastIndexOf('/') + 1);
				String nsURI = RDFTnsFlavor.BE_NAMESPACE + entityPath;
				ExpandedName exname = ExpandedName.makeName(nsURI, name);
				entityElement = getMapperContext().getBEProject().getSmElement(
						exname);
				ipMapperTargetSchema = entityElement;

			}

		}
		return (MutableElement)entityElement;
	}

	//    private SmElement ruleFunctionToSmType(RuleFunction rf, String name) {
	//    	DefaultComponentFactory factory = new DefaultComponentFactory();
	//    	 MutableSchema schema = getSchema(factory, rf.getFullPath());
	//
	//         Ontology ont = rf.getOntology();
	//         //ExpandedName rfInputTypeName= ExpandedName.makeName(schema.getNamespace(),rf.getName()+"_arguments");
	//         ExpandedName rfInputTypeName= ExpandedName.makeName(schema.getNamespace(),name);
	//         MutableType rfInType = getSmType(factory, schema,rfInputTypeName);
	//         
	//      // Add the element
	//         MutableElement elem = factory.createElement();
	//         elem.setType(rfInType);
	//         elem.setSchema(schema);
	//         elem.setExpandedName(rfInputTypeName);
	//
	//         schema.addSchemaComponent(elem);
	//        
	//         ExpandedName argsTypeName= ExpandedName.makeName(schema.getNamespace(),"args");
	//         MutableType argsType = getSmType(factory, schema, argsTypeName);
	//         MutableSupport.addLocalElement(rfInType, "args", argsType, 1,1); 
	//         
	//        String projName = getMapperContext().getBEProject().getName();
	//        Symbols syms = rf.getScope();
	//        for (Iterator itr = syms.getSymbolsList().iterator(); itr.hasNext();)
	//        {
	//            Symbol s = (Symbol) itr.next();
	//            String argName = s.getName();
	//            String typeName = s.getType();
	//            SmType smType = getSmTypeFromTypeName(projName, typeName);
	//            MutableElement smEle =null;
	//            if(!s.isArray())
	//            	smEle = MutableSupport.addLocalElement(argsType, argName, smType, 1,1);  //Aha we dont support arrays in RuleFunction
	//            else
	//            	smEle = MutableSupport.addLocalElement(argsType, argName, smType, 0, Integer.MAX_VALUE);
	//            smEle.setNillable(true);
	//            
	//            if ((smType == RDFTnsFlavor.getBaseEventType()) || (smType == RDFTnsFlavor.getBaseConceptType()))
	//                smEle.setAllowedSubstitution(SmType.RESTRICTION + SmType.EXTENSION + SmType.SUBSTITUTION) ;
	//
	//        }
	//
	//        return elem;
	//    }
	//    

	protected SmElement ruleFunctionToSmTypeRef(RuleFunction rf, String name) {
		DefaultComponentFactory factory = new DefaultComponentFactory();
		MutableSchema schema = getSchema(factory, rf.getFullPath());
		//ExpandedName rfInputTypeName= ExpandedName.makeName(schema.getNamespace(),rf.getName()+"_arguments");
		ExpandedName rfInputTypeName= ExpandedName.makeName(schema.getNamespace(),name);
		MutableType rfInType = getSmType(factory, schema,rfInputTypeName);

		// Add the element
		MutableElement elem = factory.createElement();
		elem.setType(rfInType);
		elem.setSchema(schema);
		elem.setExpandedName(rfInputTypeName);
		schema.addSchemaComponent(elem);

		ExpandedName argsTypeName= ExpandedName.makeName(schema.getNamespace(),"args");
		MutableType argsType = getSmType(factory, schema, argsTypeName);
		MutableSupport.addLocalElement(rfInType, "args", argsType, 1,1); 
		String projName = getMapperContext().getBEProject().getName();
		Symbols syms = rf.getScope();
		for (Iterator<?> itr = syms.getSymbolsList().iterator(); itr.hasNext();)
		{
			Symbol s = (Symbol) itr.next();
			String argName = s.getName();
			String typeName = s.getType();
			SmType smType = getSmTypeFromTypeNameRef(schema, projName, typeName,getUIAgent(),null);
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



	public static MutableSchema getSchema(DefaultComponentFactory factory, String fullpath) {
		String ns = RDFTnsFlavor.BE_NAMESPACE + fullpath;
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


	public static MutableType getSmType(DefaultComponentFactory factory, MutableSchema schema, ExpandedName typeName) {

		MutableType smType = (DefaultType) factory.createType();
		smType.setExpandedName(typeName);
		smType.setSchema(schema);
		smType.setComplex();
		smType.setAllowedDerivation(SmComponent.EXTENSION | SmComponent.RESTRICTION);
		//        smType.setDerivationMethod(SmComponent.EXTENSION);

		schema.addSchemaComponent(smType);


		return smType;
	}

	protected MutableElement createElement(String namespace, String localname, SmType type, MutableSchema schema) {
		//throws ConversionException {
		// If element already exists in our schema, just return that element.
		MutableElement element = null; // todo (caching)   SmSupport.getElement(schema, localname);
		// If we still don't have an element, create one.
		if (element == null) {
			MutableElement mutableElement = schema.getComponentFactory().createElement();
			mutableElement.setName(localname);
			mutableElement.setNamespace(namespace);
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

	//	private MutableElement buildRuleElement(String entityPath, RuleFunction func){
	//		 MutableSchema schema = SmFactory.newInstance().createMutableSchema();
	//         MutableType type = MutableSupport.createType(schema, "output");
	//         type.setDerivationMethod(SmComponent.EXTENSION);
	//         String name = "arguments";
	//         String nsURI = RDFTnsFlavor.BE_NAMESPACE + entityPath;
	//         ExpandedName exname = ExpandedName.makeName(nsURI, name);
	//     	 SmElement smElement = getMapperContext().getBEProject().getSmElement(exname);
	////     	 SmType smType = smElement.setType;
	//     	 Symbols scope = func.getScope();
	//     	 List<Symbol> symbolsList = scope.getSymbolsList();
	//     	 for (Symbol object : symbolsList) {
	//			
	//		}
	//         MutableSupport.addLocalElement(type, ModelConstants.ARRAY_ELEMENT_NAME, smType, 0, Integer.MAX_VALUE);
	//         ExpandedName makeName = ExpandedName.makeName(localName);
	//	}

	//	private MutableElement buildElement(String projectName, String entity){
	//		EMFTnsCache tnsCache = StudioCorePlugin.getCache(projectName);
	//		Map<ExpandedName, TnsComponent> elements = BpmnCommonModelUtils.getCachedElements(tnsCache);
	//		for (Entry<ExpandedName, TnsComponent> element : elements.entrySet()) {
	//			ExpandedName exName = element.getKey();
	//			TnsComponent tnsComponent = element.getValue();
	//			int type = tnsComponent.getComponentType();
	//			SmElement smt = (SmElement) tnsComponent.getContainingFragment().getComponent(exName.localName, type);
	//			String location =exName.namespaceURI.replace(RDFTnsFlavor.BE_NAMESPACE, "");
	//			if(location.equals(entity)){
	//				return (MutableElement) smt;
	//			}
	//		}
	//		return null;
	//	}

	public static SmElement getSchema() {
		return defaultOutputSchema;
	}

	protected String updateSchema(String entityURI) {
		ArrayList<String> params = new ArrayList<String>();
		String newXslt = "xslt://";
		if(entityURI != null) {
			params.add(entityURI);
			newXslt = XSTemplateSerializer.serialize("", params, new ArrayList<Object>());
		}
		return newXslt;

	}


	public void initAgent() {}

	protected abstract void createControl();

	/**
	 * @param xslt
	 * @param element
	 * @param agent
	 */
	public abstract void updateMapperPanel( String xslt);

	/**
	 * @return
	 */
	protected TemplateEditorConfiguration generateXslt(String xslt){
		VariableDefinitionList vdl = makeInputVariableDefinitions();
		final TemplateEditorConfiguration tec = new TemplateEditorConfiguration();
		SmElement schema=getEntitySchema();
		NamespaceMapper nsm = getNamespaceMapper();
		TemplateBinding template = getBinding(xslt);
		List receivingParams = XSTemplateSerializer.getReceivingParms(xslt);

		if (getMapperContext().getFunction() != null) {
			if (receivingParams.size() == 0) {
				SmElement inputType = ((PredicateWithXSLT)getMapperContext().getFunction()).getInputType();
				tec.setExpectedOutput(SmSequenceTypeFactory.create(inputType));
			} else {
				String entityPath = (String) receivingParams.get(0);
				MutableElement element = buildElement(getMapperContext(), entityPath);
				tec.setExpectedOutput(SmSequenceTypeFactory.create(element));
			}
		} else {
			tec.setExpectedOutput(SmSequenceTypeFactory.create(getEntitySchema()));
		}

		StylesheetBinding sb = (StylesheetBinding) template.getParent();

		if (sb != null) {
			BindingElementInfo.NamespaceDeclaration[] nd = sb.getElementInfo().getNamespaceDeclarations();
			for (int i = 0; i < nd.length; i++) {
				nsm.registerOrGetPrefixForNamespaceURI(nd[i].getNamespace(),nd[i].getPrefix());
			}
		}
		expressionContext = new ExprContext(vdl, getUIAgent().getFunctionResolver()).createWithNamespaceMapper(nsm);

		TargetNamespaceProvider targetNamespaceProvider = ((TargetNamespaceCache) getUIAgent().getTnsCache()).getNamespaceProvider();

		SmNamespaceProvider smNamespaceProvider = new SmNamespaceProviderImpl(targetNamespaceProvider);

		//		expressionContext = expressionContext.createWithInputAndOutputSchemaProvider(smNamespaceProvider);
		expressionContext = expressionContext.createWithInputAndOutputSchemaAndComponentProvider(smNamespaceProvider, new SmComponentProviderExOnSmNamespaceProvider(smNamespaceProvider));
		tec.setExprContext(expressionContext);

		tec.setImportRegistry(new DefaultImportRegistry());

		tec.setStylesheetResolver(new DefaultStylesheetResolver());

		tec.setBinding(template);
		CoercionSet cs = new CoercionSet();
		tec.setCoercionSet((CoercionSet) cs.clone());
		return tec;
	}
	
	protected VariableDefinitionList makeInputVariableDefinitions() {

		VariableDefinitionList vdlist = new VariableDefinitionList();
		if (getMapperContext() != null) {

			vdlist.add(new VariableDefinition(
					ExpandedName.makeName(GlobalVariablesProvider.NAME), 
					SmSequenceTypeFactory.create(getMapperContext().getGlobalVariables().toSmElement(false))));

			List<VariableDefinition> definitions = getMapperContext().getDefinitions();
			for (VariableDefinition variableDefinition : definitions) {
				vdlist.add(variableDefinition);
			}

		}

		return vdlist;
	}

	public static MutableElement buildElement(MapperContext context, String entityPath) {
		PredicateWithXSLT function = (PredicateWithXSLT) context.getFunction();
		SmElement refEle = getSmElementFromPath(context.getProject().getName(), entityPath);
		if (refEle == null) {
			// TODO : report error
			BpmnUIPlugin.log("Could not find element "+entityPath+" in cache.");
			return null;
		}
		MutableSchema schema = SmFactory.newInstance().createMutableSchema();
		MutableType type = MutableSupport.createType(schema, "fn");

		//        type.setDerivationMethod(SmComponent.EXTENSION);
		SmType fnType = function.getInputType().getType();
		copyType(fnType, type);
		MutableElement fElement = MutableSupport.createElement(schema, function.getInputType().getName(), type);
		if ("createEvent".equals(fElement.getName())) {
			// don't add object, attributes go under 'event' element
			MutableSupport.addLocalElement(type, "event", refEle.getType(),0,1 );
		} else {
			MutableSupport.addLocalElement(type, "object", refEle.getType(),0,1 );
		}
		return fElement;
	}

	public  static String getCopyXsltTemplate(){
		if(copyXsltTemplate == null){
			copyXsltTemplate = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><xsl:stylesheet xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" version=\"1.0\" exclude-result-prefixes=\"xsl xsd\"> <xsl:output method=\"xml\"/><xsl:param name=\"job\"/><xsl:template match=\"/\"><job><xsl:copy-of select=\"$job/ancestor-or-self::*/namespace::node()\"/><xsl:copy-of select=\"$job/@*\"/><xsl:copy-of select=\"$job/node()\"/></job></xsl:template></xsl:stylesheet>";
		}

		return copyXsltTemplate;
	}

	//	public static SmElement getSmElementFromVarDef(String projectName, com.tibco.cep.studio.core.index.model.VariableDefinition varDef) {
	//		Object objType = ElementReferenceResolver.resolveVariableDefinitionType(varDef);
	//		if (objType instanceof EntityElement) {
	//			com.tibco.cep.designtime.core.model.Entity entity = ((EntityElement) objType).getEntity();
	//			return getSmElementFromPath(projectName, entity.getFullPath());
	//		}
	//		
	//		return null;
	//	}

	//	public static VariableDefinition getVariableDefinitionFromTypeName(String projectName, com.tibco.cep.studio.core.index.model.VariableDefinition varDef) {
	//    	String typeName = varDef.getType();
	//    	String varName = varDef.getName();
	//    	
	//    	boolean isArray = varDef.isArray();
	//    	
	////        int numBrackets = numTrailinglBracketPairs(typeName);
	//    	if (typeName.indexOf('.') != -1) {
	//    		typeName = ModelUtils.convertPackageToPath(typeName);
	//    	}
	//        
	//        if(!isArray) {
	//        	SmType smType = getSmTypeFromTypeName(projectName, typeName);
	//        	if (smType == null) {
	//        		SmElement smElement = getSmElementFromVarDef(projectName, varDef);
	//        		if (smElement != null) {
	//        			smType = smElement.getType();
	//        		}
	//        	}
	//        	SmSequenceType xtype = SmSequenceTypeFactory.createElement(smType);
	//            return new VariableDefinition(ExpandedName.makeName(varName), xtype);
	//        } else {
	//            //create a new type with the name of the array variable
	//            MutableSchema schema = SmFactory.newInstance().createMutableSchema();
	//            MutableType type = MutableSupport.createType(schema, varName);
	//            type.setDerivationMethod(SmComponent.EXTENSION);
	//
	//            //add a single element to the new type with the schema of the local variable's type
	//        	SmType smType = getSmTypeFromTypeName(projectName, typeName);
	//        	if (smType == null) {
	//        		SmElement smElement = getSmElementFromVarDef(projectName, varDef);
	//        		if (smElement != null) {
	//        			smType = smElement.getType();
	//        		}
	//        	}
	//            MutableSupport.addLocalElement(type, ModelConstants.ARRAY_ELEMENT_NAME, smType, 0, Integer.MAX_VALUE);
	//        
	//            return new VariableDefinition(ExpandedName.makeName(varName), SmSequenceTypeFactory.createElement(type));
	//        }
	//    }

	    public static SmType getSmTypeFromTypeName(String projectName, String typeName) {
	//        typeName = stripTrailingBracketPairs(typeName);
	        SmType type = null;
	        
	        RDFUberType rdfType = RDFTypes.getType(typeName);
	        if(rdfType == null) rdfType = RDFTypes.getType(typeName + "_Wrapper");
	        
	        if(rdfType instanceof RDFPrimitiveTerm) {
	            if(rdfType == RDFTypes.EXCEPTION) {
	                type = RDFTnsFlavor.getBaseExceptionType();
	            } else {
	                type = rdfType.getXSDLTerm();
	            }
	        } 
	        else if(rdfType == RDFTypes.BASE_ENTITY) {
	            type = RDFTnsFlavor.getBaseEntityType();
	        }
	        else if(rdfType == RDFTypes.BASE_PROCESS) {
	            type = RDFTnsFlavor.getBaseProcessType();
	        }
	        else if(rdfType == RDFTypes.BASE_CONCEPT) {
	            type = RDFTnsFlavor.getBaseConceptType();
	        }       
	        else if(rdfType == RDFTypes.BASE_EVENT) {
	            type = RDFTnsFlavor.getBaseEventType();
	        }
	        else if(rdfType == RDFTypes.CONCEPT) {
	            type = RDFTnsFlavor.getBaseContainedConceptType();
	        }
	        else if(rdfType == RDFTypes.EVENT) {
	            type = RDFTnsFlavor.getBaseSimpleEventType();
	        }
	        else if(rdfType == RDFTypes.TIME_EVENT) {
	            type = RDFTnsFlavor.getBaseTimeEventType();
	        }
	        else if(rdfType == RDFTypes.ADVISORY_EVENT) {
	            type = RDFTnsFlavor.getBaseAdvisoryEventType();
	        }
	        else if(rdfType == RDFTypes.OBJECT) {
	            type = rdfType.getXSDLTerm();
	        }
	
	        if(type == null) {
	            SmElement ele = getSmElementFromPath(projectName, typeName);
	            if(ele != null) {
	                type = ele.getType();
	            }else{
	            	type = getSimpleTypeForType(typeName);
	            }
	        }
	        
	        return type;
	    }
	    
	public static SmType getSmTypeFromTypeNameRef(MutableSchema schema, String projectName, String typeName, UIAgent uiAgent,TnsCache tnscache) {
		//      typeName = stripTrailingBracketPairs(typeName);
		SmType type = null;

		RDFUberType rdfType = RDFTypes.getType(typeName);
		if(rdfType == null) rdfType = RDFTypes.getType(typeName + "_Wrapper");

		if(rdfType instanceof RDFPrimitiveTerm) {
			if(rdfType == RDFTypes.EXCEPTION) {
				type = RDFTnsFlavor.getBaseExceptionType();
			} else {
				type = rdfType.getXSDLTerm();
			}
		} 
		else if(rdfType == RDFTypes.BASE_ENTITY) {
			type = RDFTnsFlavor.getBaseEntityType();
		}
		else if(rdfType == RDFTypes.BASE_PROCESS) {
			type = RDFTnsFlavor.getBaseProcessType();
		}
		else if(rdfType == RDFTypes.BASE_CONCEPT) {
			type = RDFTnsFlavor.getBaseConceptType();
		}
		else if(rdfType == RDFTypes.BASE_EVENT) {
			type = RDFTnsFlavor.getBaseEventType();
		}
		else if(rdfType == RDFTypes.CONCEPT) {
			type = RDFTnsFlavor.getBaseContainedConceptType();
		}
		else if(rdfType == RDFTypes.EVENT) {
			type = RDFTnsFlavor.getBaseSimpleEventType();
		}
		else if(rdfType == RDFTypes.TIME_EVENT) {
			type = RDFTnsFlavor.getBaseTimeEventType();
		}
		else if(rdfType == RDFTypes.ADVISORY_EVENT) {
			type = RDFTnsFlavor.getBaseAdvisoryEventType();
		}
		else if(rdfType == RDFTypes.OBJECT) {
			type = rdfType.getXSDLTerm();
		}else if(typeName != null && !typeName.isEmpty()) {
			if(uiAgent != null){
				type = getSmTypefromTnsCache(uiAgent.getTnsCache(),type,typeName);
			} else if (tnscache != null){
				type = getSmTypefromTnsCache(tnscache,type,typeName);
			}
		}
		return type;
	}
	 public static SmType getSmTypefromTnsCache(TnsCache tnsCache,SmType type,String typeName){

			RDFBaseTerm term = null;
			String runtimeClassName = com.tibco.be.model.util.ModelNameUtil.modelPathToGeneratedClassName(typeName);
			for(Iterator<?> it=tnsCache.getLocations();it.hasNext();){
				String loc = (String) it.next();
				if(loc != null && !loc.isEmpty() && loc.indexOf(typeName) != -1){
					if (loc.endsWith("."+CommonIndexUtils.CONCEPT_EXTENSION)) {
						term = new RDFBaseConceptTerm(runtimeClassName, true);
						break;
					} else if (loc.endsWith("."+CommonIndexUtils.EVENT_EXTENSION)) {
						term = new RDFBaseEventTerm(runtimeClassName, true);
						break;
					}else if (loc.endsWith("."+CommonIndexUtils.PROCESS_EXTENSION)) {
						term = new RDFBaseProcessTerm(runtimeClassName, true);    	
						break;
					}
				}
			}
			type = term.getXSDLTerm(); 
		return type;
	 }
	@SuppressWarnings("unused")
	private static MutableType getEntityTypeReference(MutableSchema schema, String entityFullPath, String loc) {
		String conceptName= parseConceptName(entityFullPath);
		MutableType refType=  DefaultComponentFactory.getTnsAwareInstance().createTypeRef();
		refType.setReference(schema);
		refType.setSchema(schema);
		refType.setExpandedName(ExpandedName.makeName(RDFTnsFlavor.BE_NAMESPACE+entityFullPath, conceptName));
		//TODO: Add the Location
		((DefaultSchema)schema) .getSingleFragment().addImport(new TnsImportImpl(loc, null, RDFTnsFlavor.BE_NAMESPACE+entityFullPath,SmNamespace.class));
		return refType;
	}


	static String parseConceptName(String conceptFullPath) {
		return conceptFullPath.substring(conceptFullPath.lastIndexOf('/')+1);
	}


	protected static SmType getSimpleTypeForType(String type) {
		SmType smType = null;
		if (type.equalsIgnoreCase(PROPERTY_TYPES.BOOLEAN.getName()))
			smType = XSDL.BOOLEAN;
		else if (type.equalsIgnoreCase(PROPERTY_TYPES.INTEGER
				.getName()))
			smType = XSDL.INTEGER;
		else if (type.equalsIgnoreCase(PROPERTY_TYPES.DOUBLE
				.getName()))
			smType = XSDL.DOUBLE;
		else if (type.equalsIgnoreCase(PROPERTY_TYPES.STRING
				.getName()))
			smType = XSDL.STRING;
		else if (type.equalsIgnoreCase(PROPERTY_TYPES.DATE_TIME
				.getName()))
			smType = XSDL.DATETIME;
		else if (type.equalsIgnoreCase(PROPERTY_TYPES.LONG
				.getName()))
			smType = XSDL.LONG;

		return smType;
	}

	public static SmElement getSmElementFromPath(String projectName, String entity) {
		SmNamespaceProvider snp = BpmnCorePlugin.getCache(projectName).getSmNamespaceProvider();
		SmNamespace sm =  snp.getNamespace(EMFRDFTnsFlavor.BE_NAMESPACE +  entity);
		String[] terms = entity.split(IndexUtils.PATH_SEPARATOR);
		String elname = terms[terms.length - 1];

		if(sm != null) {
			return (SmElement)sm.getComponent(SmComponent.ELEMENT_TYPE, elname);
		} else {
			return null;
		}
	}

	public static SmType getSmType(String type) {
		if ("String".equals(type)) {
			return XSDL.STRING;
		} else if ("int".equals(type)) {
			return XSDL.INT;
		} else if ("boolean".equals(type)) {
			return XSDL.BOOLEAN;
		} 

		return XSDL.STRING; // default case (?)
	}


	/**
	 * @param xslt
	 * @return
	 */
	protected TemplateBinding getBinding(String template) {
		String xslt = XSTemplateSerializer.deSerialize(template, new ArrayList<Object>(), new ArrayList<Object>());
		TemplateBinding tb = null;
		if ((xslt != null) && (xslt.length() != 0))
		{
			StylesheetBinding ssb = null;
			try {
				ssb = ReadFromXSLT.read(xslt);
			} catch (RuntimeException e) {
				throw new XMLReadException(e); // throw error to allow callers to handle
			}
			if (ssb == null) {
				tb = new TemplateBinding(BindingElementInfo.EMPTY_INFO,null,"/*");
			} else {
				tb = BindingManipulationUtils.getNthTemplate(ssb, 0);
			}
		}
		else {
			tb = new TemplateBinding(BindingElementInfo.EMPTY_INFO,null,"/*");
		}
		return tb;
	}

	/**
	 * @return
	 */
	NamespaceMapper getNamespaceMapper() {

		NamespaceMapper nsmapper = new DefaultNamespaceMapper("xsd");
		((DefaultNamespaceMapper) nsmapper).addXSDNamespace();
		return nsmapper;
	}

	/**
	 * @param src
	 * @param dest
	 */
	protected static void copyType(SmType src, MutableType dest) {
		SmModelGroup grp = src.getContentModel();
		if (grp == null)
			return;
		Iterator<?> itr = grp.getParticles();
		while (itr.hasNext()) {
			SmParticle particle = (SmParticle) itr.next();
			SmParticleTerm term = particle.getTerm();
			if (!XSDL.ANYTYPE_NAME.localName.equalsIgnoreCase(term.getName()))
				MutableSupport.addParticleTerm(dest, term, particle
						.getMinOccurrence(), particle.getMaxOccurrence());
		}
	}


	/**
	 * @return
	 */
	private XiNode getXSLTNode() {
		try {
			String xslt = getXSLTString();
			if (null == xslt)
				return null;
			XiNode node = XiParserFactory.newInstance().parse(
					new InputSource(new StringReader(xslt)));
			node.normalize();
			return node.getFirstChild();
		} catch (SAXException e) {
			BpmnUIPlugin.log(e); // To change body of catch statement use File |
			// Settings | File Templates.
		} catch (IOException e) {
			BpmnUIPlugin.log(e); // To change body of catch statement use File |
			// Settings | File Templates.
		}
		return null;
	}

	/**
	 * @return
	 */
	public String getXSLTString() {
		getBindingEditorPanel().stopEditing();
		TemplateBinding tb = getBindingEditorPanel().getCurrentTemplate();
		NamespaceMapper nsm = getNamespaceMapper();
		StylesheetBinding sb = (StylesheetBinding) tb.getParent();
		BindingElementInfo.NamespaceDeclaration[] nd = sb.getElementInfo()
				.getNamespaceDeclarations();
		for (int i = 0; i < nd.length; i++) {
			nsm.registerOrGetPrefixForNamespaceURI(nd[i].getNamespace(), nd[i]
					.getPrefix());
		}

		String xslt = BindingRunner.getXsltFor(tb, nsm);
		return xslt;
	}

	public String getNewXSLT() {
		getBindingEditorPanel().stopEditing();
		TemplateBinding tb = getBindingEditorPanel().getCurrentTemplate();
		String newXslt = "";
		if (tb != null) {
			NamespaceContextRegistry nsm = MapperUtils.getNamespaceMapper();
			StylesheetBinding sb = (StylesheetBinding)tb.getParent();
			BindingElementInfo.NamespaceDeclaration[] nd = sb.getElementInfo().getNamespaceDeclarations();
			for (int i=0; i<nd.length; i++) {
				nsm.getOrAddPrefixForNamespaceURI(nd[i].getNamespace(), nd[i].getPrefix());
			}
			String xsltTemplate = BindingRunner.getXsltFor(tb, nsm );
			BpmnUIPlugin.debug(xsltTemplate);
			ArrayList<String> params = new ArrayList<String>();
			params.add(getEntityURI());
			CoercionSet cs = bindingEditorPanel.getCurrentCoercionSet();
			newXslt = XSTemplateSerializer.serialize(xsltTemplate, params, MapperCoreUtils.serializeCoercionSet(cs));
		}
		return newXslt;
	}

	public String createNewXSLT(String xsltTemplate) {
		ArrayList<String> params = new ArrayList<String>();
		params.add(getEntityURI());
		CoercionSet cs = bindingEditorPanel.getCurrentCoercionSet();
		String newXslt = XSTemplateSerializer.serialize(xsltTemplate, params, MapperCoreUtils.serializeCoercionSet(cs));
		return newXslt;
	}

	/**
	 * @param fileName
	 */
	public String getMapperOutput(String fileName) {
		if (getMapperContext() instanceof NullMapperContext) {
			return null;
		}
		try {
			XiNode doc = XiSupport.getXiFactory().createDocument();
			XiNode xsltNode = getXSLTNode();

			if (xsltNode != null) {
				doc.appendChild(xsltNode.copy());
			}

			StringWriter sw = new StringWriter();
			XiSerializer.serialize(doc, sw, "UTF-8", true);
			sw.flush();
			return sw.toString();
		} catch (Exception e) {
			BpmnUIPlugin.log(e);
		}
		return null;
	}

	/**
	 * @param fileName
	 */
	public void setMapperInput(Entity entity, String xslt) {
		try {
			setEntity(entity, xslt);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void setMapperInputForSubprocess(EObjectWrapper<EClass, EObject> subprocess, ProcessModel process,  Ontology ontology, String oldXslt) {
		if(subprocess != null) {
			this.entityURI = process.getFolderPath() ;
			if(this.entityURI.equals("/"))
				this.entityURI = this.entityURI+ process.getName();
			else
				this.entityURI = this.entityURI + "/"+process.getName();


			SmElement element = getSubProcessSmElement(subprocess, process, ontology);
			if(element == null){
				// sometimes i am getting null, still investigating why
				setEntityURI(null, "");
				return;
			}
			element = processProcessSchema(element);
			processEntitySchema = element ;
			MutableSchema schema = SmFactory.newInstance().createMutableSchema();
			String  lname = subprocess.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_NAME);
			lname =lname.replace(".", "$");
			MutableType type = MutableSupport.createType(schema, lname);
			//	            type.setDerivationMethod(SmComponent.EXTENSION);
			SmType smType = element.getType();
			MutableElement addLocalElement = MutableSupport.addLocalElement(type, lname, smType, 1,1); 
			setEntitySchema(addLocalElement);
			String xslt = oldXslt;
			if(xslt == null || xslt.trim().isEmpty())
				xslt = updateSchema(entityURI);
			updateMapperPanel(xslt);
		} else {
			setEntityURI(null, "");
		}
	}

	public SmElement getSubProcessSmElement(EObjectWrapper<EClass, EObject> subprocess, ProcessModel adaptor, Ontology ontology) {
		String id = subprocess.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
		SubProcessModel taskElement = adaptor.getTaskElement(id, SubProcessModel.class);
		SmElement element = null;
		if(taskElement != null){
			String ns =RDFTnsFlavor.BE_NAMESPACE +adaptor.getFullPath();
			ExpandedName exname = ExpandedName.makeName(ns,taskElement.getFullPath());
			BETargetNamespaceCache tnsCache = getMapperContext().getBEProject().getTnsCache();
			Map<ExpandedName, TnsComponent> cachedElements = BpmnCommonModelUtils.getCachedElements(tnsCache);
			element = (SmElement)cachedElements.get(exname);
			if(element == null)
				element = taskElement.toSmElement();
		}

		return element;
	}
	
	public EObject getUserObject() {
		return userObject;
	}

	public void setUserObject(EObject userObject) {
		this.userObject = userObject;
	}

	public class DelegatingUIAgent implements UIAgent {

		UIAgent delegate;
		TargetNamespaceCache tnsCache;

		public DelegatingUIAgent(UIAgent agent) {
			setDelegate(agent);
		}

		/**
		 * @param delegate
		 *            the delegate to set
		 */
		public void setDelegate(UIAgent delegate) {
			this.delegate = delegate;
		}

		private UIAgent getDelegate() {
			return delegate;
		}

		@Override
		public String getAbsoluteURIFromProjectRelativeURI(String location) {
			return getDelegate().getAbsoluteURIFromProjectRelativeURI(location);
		}

		@Override
		public Font getAppFont() {
			return getDelegate().getAppFont();
		}

		@Override
		public Frame getFrame() {
			//			return MapperControl.this.getFrame();
			return getDelegate().getFrame();
		}

		@Override
		public FunctionResolver getFunctionResolver() {
			return getDelegate().getFunctionResolver();
		}

		@Override
		public void openResource(String location) {
			getDelegate().openResource(location);

		}

		@Override
		public String getProjectName() {
			return getDelegate().getProjectName();
		}

		@Override
		public String getProjectRelativeURIFromAbsoluteURI(String location) {
			return getDelegate().getProjectRelativeURIFromAbsoluteURI(location);
		}

		@Override
		public String getRootProjectPath() {
			return getDelegate().getRootProjectPath();
		}

		@Override
		public Font getScriptFont() {
			return getDelegate().getScriptFont();
		}

		@Override
		public SmNamespaceProvider getSmNamespaceProvider() {
			return getDelegate().getSmNamespaceProvider();
		}

		@Override
		public StylesheetResolver getStyleSheetResolver() {
			return getDelegate().getStyleSheetResolver();
		}

		@Override
		public TnsCache getTnsCache() {
			if(tnsCache == null) {
				if(getProjectName() != null) {
					tnsCache = (TargetNamespaceCache) getDelegate().getTnsCache();
				} else {
					tnsCache = new TargetNamespaceCache();
				}
			}
			return tnsCache;
		}

		@Override
		public String getUserPreference(String name) {
			return getDelegate().getUserPreference(name);
		}

		@Override
		public String getUserPreference(String name, String defaultValue) {
			return getDelegate().getUserPreference(name, defaultValue);
		}

		@Override
		public void setUserPreference(String name, String val) {
			getDelegate().setUserPreference(name, val);
		}

		@Override
		public SmComponentProviderEx getSmComponentProviderEx() {
			return getDelegate().getSmComponentProviderEx();
		}

	}

	public com.tibco.cep.designtime.core.model.Entity getCoreModelEntity() {
		return coreModelEntity;
	}


	public void setCoreModelEntity(
			com.tibco.cep.designtime.core.model.Entity coreModelEntity) {
		this.coreModelEntity = coreModelEntity;
	}




}
