/**
 * 
 */
package com.tibco.cep.bpmn.ui.graph.properties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.graphics.Image;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDFactory;
import org.eclipse.xsd.XSDModelGroup;
import org.eclipse.xsd.XSDParticle;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.XSDTerm;
import org.eclipse.xsd.XSDTypeDefinition;
import org.eclipse.xsd.impl.XSDSchemaImpl;
import org.eclipse.xsd.util.XSDConstants;
import org.w3c.dom.Element;

import com.tibco.be.util.XSTemplateSerializer;
import com.tibco.be.util.shared.ModelConstants;
import com.tibco.cep.bpmn.core.index.BpmnIndexUtils;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.runtime.templates.MapperConstants;
import com.tibco.cep.bpmn.ui.mapper.BpmnSchemaCache;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.java.JavaSource;
import com.tibco.cep.mapper.xml.xdata.xpath.VariableDefinition;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.mapper.BEMapperCoreInputOutputAdapter;
import com.tibco.cep.studio.core.rdf.EMFRDFTnsFlavor;
import com.tibco.cep.studio.core.util.mapper.MapperXSDUtils;
import com.tibco.cep.studio.mapper.ui.MapperActivator;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.mappermodel.emfapi.VariableBindingKind;
import com.tibco.xml.mapperui.emfapi.EMapperInputOutputAdapter;
import com.tibco.xml.mapperui.emfapi.IEMapperControl;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.build.MutableSchema;
import com.tibco.xml.xmodel.IXDataConsts;
/**
 * @author sshekhar
 *
 */
public class BpmnEMapperInputOutputAdapter extends EMapperInputOutputAdapter {

	private IEMapperControl mapperControl;
	private BpmnEMapperControlArgs mapperControlArgs;
	private List< String > sourcelabel = new ArrayList< String > ( ) ;
	public XSDSchema xsdSchema;
	public boolean isCallActivity = false;
	public boolean isManualTask = false ;
	public boolean isJavaTask = false ;
	protected SmElement callActivityProcessSchema = null ;
	protected SmElement rfSmelement ;
	private List<VariableDefinition> varDefs = new ArrayList<VariableDefinition>();
	SmElement wsdlSmElement = null ;
    BpmnSchemaCache bpmnSchemaCache = null ;
	String strGlobalVarLabel = "globalVariables" ;
	Map< String, String > wsdlSchemaMap  = new HashMap< String, String >( );
	String callActivityUri = "/callActivity" ;
	String targetLabel ;
	public static String BE_PROCESS_EXTN = ".beprocess";
	public static String DELEM = "}";
	private HashMap<String, String> nsPrefixMap;	
	private com.tibco.cep.designtime.core.model.Entity returnEnt;
	
	public BpmnEMapperInputOutputAdapter(IEMapperControl parent, BpmnEMapperControlArgs mapperControlArgs) {
		this.mapperControl = parent;
		this.mapperControlArgs = mapperControlArgs;
	}

	public IEMapperControl getMapperControl() {
		return mapperControl;
	}
	@Override
	public ResourceSet getResourceSet() {
		return new ResourceSetImpl();
	}

	@Override
	public List<String> getSourceTreeRootLabels() {
		List<String> labels= new ArrayList<String>();
 		for(String str : sourcelabel){
 			labels.add(str);
 		}
		return labels ;
	}

	@Override
	public Image getTargetTreeRootImage() {
			return MapperActivator.getDefault().getImage("icons/event.png");
	}

	@Override
	public String getTargetTreeRootLabel() {
		return "job";
		
	}

	@Override
	public String getXSLT() {
		String xslt = mapperControlArgs.getXSLT() != null ? mapperControlArgs.getXSLT() : null;
		if (xslt != null) {
			xslt = XSTemplateSerializer.deSerialize(xslt, new ArrayList<>(), new ArrayList<>());
		}
		return xslt;
	}

	public XSDTerm getTargetXSDTerm() {
		MutableSchema schemaSmElt = null;
		XSDSchema xsdschema = null;
		XSDTerm term = null;
		String objName;
		String rootLabel = null;
		String entityPath = mapperControlArgs.getEntityUri();
		BpmnSchemaCache bsc = bpmnSchemaCache;
		if (mapperControlArgs.getTargetSchema() != null)
			schemaSmElt = (MutableSchema) mapperControlArgs.getTargetSchema()
					.getSchema();
		if (schemaSmElt != null) {
			BpmnSchemaGenerator.loadWsdlSchema(wsdlSchemaMap, bsc);
			if (isCallActivity) {
				schemaSmElt = (MutableSchema) mapperControlArgs.getImportedSchema().getSchema();
				BpmnSchemaGenerator.getSchema(schemaSmElt.getNamespace(),schemaSmElt, bsc);
				entityPath = entityPath + callActivityUri;
				objName = mapperControlArgs.getTargetSchema().getName();
				xsdschema = BpmnSchemaGenerator.getSchema(entityPath,(MutableSchema) mapperControlArgs.getTargetSchema()
								.getSchema(), bsc);
				xsdschema = bsc.getSchemaUsingURI(entityPath);
			} else {
				
				com.tibco.cep.designtime.core.model.Entity srcEnt = CommonIndexUtils
						.getEntity(mapperControlArgs.project.getName(),entityPath);
				if(srcEnt == null || srcEnt instanceof JavaSource ){
					xsdschema = BpmnSchemaGenerator.getSchema(
							mapperControlArgs.getEntityUri(), schemaSmElt, bsc);
				} else {
					XSDTerm xsdEltDec = createTermFromEntity(srcEnt, null);
					xsdschema = xsdEltDec.getSchema();
				}
				objName = mapperControlArgs.getTargetSchema().getName();
			}
			EList<XSDElementDeclaration> xsdDeclTrial = xsdschema.getElementDeclarations();
			if (!mapperControlArgs.isInputMapper) {
				rootLabel = MapperConstants.JOB;
			} else {
				rootLabel = objName;
			}
			for (XSDElementDeclaration xsdElementDeclaration : xsdDeclTrial) {
				if (xsdElementDeclaration.getName().equalsIgnoreCase(objName)) {
					if (mapperControlArgs.isInputMapper)
						term = xsdElementDeclaration;
					else
						term = createParentDecl(xsdElementDeclaration,
								rootLabel, "", null);
					return term;
				}
			}
		}
		return term;

	}

	
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	@Override
//	public Map<String, String> getPrefixToNamespaceMap() {
////		Map<String, String> prefixMap = new HashMap<String, String>();
//		String xpath = mapperControlArgs.getXSLT() != null ? mapperControlArgs.getXSLT() : null;
//		if (xpath != null) {
//			XiNode node;
//			try {
//				node = XSTemplateSerializer.deSerializeXPathString(xpath);
//				HashMap nsPrefixesinXPath = XSTemplateSerializer.getNSPrefixesinXPath(node);
//				return nsPrefixesinXPath;
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		return null;
//	}
	
	@Override
	public Map<String, String> getPrefixToNamespaceMap() {
		if (nsPrefixMap != null) {
			nsPrefixMap.clear();
		} else {
			nsPrefixMap = new HashMap<String, String>();
		}
		String xpath = mapperControlArgs.getXSLT() != null ? mapperControlArgs.getXSLT() : null;
		if (xpath != null) {
			XiNode node;
			try {
				node = XSTemplateSerializer.deSerializeXPathString(xpath);
				HashMap<?, ?> nsPrefixesinXPath = XSTemplateSerializer.getNSPrefixesinXPath(node);
				Set<?> keySet = nsPrefixesinXPath.keySet();
				Iterator<?> iterator = keySet.iterator();
				while (iterator.hasNext()) {
					String key = (String) iterator.next();
					nsPrefixMap.put(key, (String) nsPrefixesinXPath.get(key));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (!nsPrefixMap.containsKey(IXDataConsts.XMLSCHEMA_PREFIX)) {
			nsPrefixMap.put(IXDataConsts.XMLSCHEMA_PREFIX, IXDataConsts.XMLSCHEMA_NAMESPACE);
		}
		return nsPrefixMap;
	}

	
	@Override
	public String getXPath() {
		String xpath = mapperControlArgs.getXSLT() != null ? mapperControlArgs.getXSLT() : null;
		if (xpath != null) {
			xpath = XSTemplateSerializer.getXPathExpressionAsStringValue(xpath);
		}
		System.out.println("xpath--."+xpath);
		return xpath;
	}
	
	@Override
	public String getXPathResultNativeTypeName() {
			return null ;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void setXPath(String xpath) {
		if (mapperControlArgs.getXpath() != null) {
			final List vars = XSTemplateSerializer.searchForVariableNamesinExpression(xpath);
			Map<String, String> pfxs = getPrefixToNamespaceMap();
			String serializedXslt = XSTemplateSerializer.serializeXPathString(xpath, (HashMap) pfxs, vars);
			mapperControlArgs.setXSLT(serializedXslt);
		}
	}
	
	private void addTermsForSourceXsdForInputMapper(EList<XSDElementDeclaration> xsdDeclTrial ,List<XSDTerm> terms,String name, boolean isProcessEntity){
		for (XSDElementDeclaration xsdElementDeclaration : xsdDeclTrial) {
			String xsdElementName = xsdElementDeclaration.getName();
			if(xsdElementName.equalsIgnoreCase(name)){
				terms.add(xsdElementDeclaration);
			} 
		}
		if(isProcessEntity)
			sourcelabel.add(MapperConstants.JOB);
		return ;
	}
	private void addTermsForSourceXsdForOutputMapper(EList<XSDElementDeclaration> xsdDeclTrial ,List<XSDTerm> terms,String name, boolean isProcessEntity,boolean isStarterEvent){
		for (XSDElementDeclaration xsdElementDeclaration : xsdDeclTrial) {
			String xsdElementName = xsdElementDeclaration.getName();
			if(xsdElementName.equalsIgnoreCase(MapperConstants.RETURN)){
				terms.add(xsdElementDeclaration);
				sourcelabel.add(MapperConstants.RETURN);
				return;
			}
			else if(name != null && xsdElementName.equalsIgnoreCase(name)&& !name.equals(MapperPropertySection.WS_RETURNTYPE)){
				if(isStarterEvent){
					terms.add(xsdElementDeclaration);
					sourcelabel.add(name);
				}
				else {
					terms.add(createParentDecl(xsdElementDeclaration,MapperConstants.RETURN, "", null));
					sourcelabel.add(MapperConstants.RETURN);
				}
				return;
			}else if(xsdElementName.equalsIgnoreCase(MapperPropertySection.RF_RETURNTYPE.toLowerCase())){
				sourcelabel.add(MapperConstants.RETURN);
				terms.add(xsdElementDeclaration);
				return ;
			}else if(xsdElementName.equalsIgnoreCase(MapperPropertySection.WS_RETURNTYPE.toLowerCase())){
				sourcelabel.add(MapperPropertySection.WS_RETURNTYPE);
				if(!terms.contains(xsdElementDeclaration))
					terms.add(xsdElementDeclaration);
				return ;
			}
		}
	}

	private List<XSDTerm> getSourceXSDTermForGloblVar(){
		XSDSchema srcxsdschema = null;
		BpmnSchemaCache bsm = bpmnSchemaCache;
		List<VariableDefinition> globalVars = mapperControlArgs.getGlobalVars();
		List<XSDTerm> xsdTerms = new ArrayList<XSDTerm>();
		for (VariableDefinition vardef : globalVars) {
			if (vardef.getElement().getName().equalsIgnoreCase(strGlobalVarLabel)) {
				MutableSchema schemaGlobalVar = (MutableSchema) vardef.getElement().getSchema();
				srcxsdschema = BpmnSchemaGenerator.getSchema(strGlobalVarLabel,schemaGlobalVar, bsm);
				srcxsdschema = (XSDSchema) bsm.getSchemaUsingURI(strGlobalVarLabel);

				if (srcxsdschema != null) {
					EList<XSDElementDeclaration> xsdDecl = srcxsdschema.getElementDeclarations();
					for (XSDElementDeclaration xsdElementDeclaration : xsdDecl) {
						String xsdElementName = xsdElementDeclaration.getName();
						if (xsdElementName.equalsIgnoreCase(strGlobalVarLabel.toLowerCase())) {
							xsdTerms.add(xsdElementDeclaration);
							sourcelabel.add(strGlobalVarLabel);
						}
					}
				}
			}
		}
		return xsdTerms;
	}
	private List<XSDTerm> getSourceXSDTermsForInputMapper(){
		MutableSchema sourceSchema = null;
		XSDSchema srcxsdschema = null;
//		com.tibco.cep.designtime.core.model.Entity srcEnt = null;
		List<XSDTerm> terms = new ArrayList<XSDTerm>();
		BpmnSchemaCache bsm = bpmnSchemaCache;
		try {
			//Add global vars
			List<VariableDefinition> sourceElements = mapperControlArgs.getSourceElements();
			if (sourceElements == null) {
				return terms;
			}
			Iterator<VariableDefinition> iter = sourceElements.iterator();
			do {
				VariableDefinition varDef = iter.next();
				String name = null;
				SmElement smelt = null;
				boolean isProcessEntity = false;
				//Creating XSD schema for loop variables
				if (varDef != null
						&& varDef.getName().toString()
								.equals(MapperConstants.LOOP_VAR)) {
					com.tibco.cep.designtime.core.model.Entity srcEnt = CommonIndexUtils
							.getEntity(mapperControlArgs.project.getName(),
									varDef.getSmTypestr());
					XSDTerm xsdEltDec = convertLoopVarVariableDefinitionToXSDTerm(srcEnt,varDef);
					terms.add(xsdEltDec);
					sourcelabel.add(varDef.getName().toString());
					continue;
				} else if (varDef != null
						&& (varDef.getName().toString()
								.equals(MapperConstants.LOOP_MAX) || varDef
								.getName().toString()
								.equals(MapperConstants.LOOP_COUNTER))) {
					XSDTerm xsdEltDec = convertLoopVariableDefinitionToXSDTerm(varDef);
					terms.add(xsdEltDec);
					sourcelabel.add(varDef.getName().toString());
					continue;
				}
				else if (varDef != null && varDef.getElement() != null) {
					smelt = varDef.getElement();
					if (smelt == null) {
						return null;
					}
				
					EObjectWrapper<EClass, EObject> modelObjWrapper = null;
					String expandedPath = smelt.getExpandedName().toString();
					expandedPath = expandedPath.substring(1,
							expandedPath.lastIndexOf(DELEM));
					String fullPath = expandedPath.substring(
							EMFRDFTnsFlavor.BE_NAMESPACE.length(),
							expandedPath.length());

					EObject eobj = BpmnIndexUtils.getElement(
							mapperControlArgs.project.getName(), fullPath
									+ BE_PROCESS_EXTN);
					if (eobj != null) {
						isProcessEntity = true;
						sourceSchema = (MutableSchema) smelt.getSchema();
						modelObjWrapper = EObjectWrapper.wrap(eobj);
						name = (String) modelObjWrapper
								.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
						srcxsdschema = BpmnSchemaGenerator.getSchema(fullPath,
								sourceSchema, bsm);
						srcxsdschema = (XSDSchema) bsm
								.getSchemaUsingURI(fullPath);
					}

					// }
					if (srcxsdschema != null) {
						EList<XSDElementDeclaration> xsdDecl = srcxsdschema
								.getElementDeclarations();
						addTermsForSourceXsdForInputMapper(xsdDecl, terms,
								name, isProcessEntity);
					}
				}
				 else{
					 XSDTerm xsdEltDec = convertVariableDefinitionToXSDTerm(varDef);
					if (xsdEltDec != null) {
						terms.add(xsdEltDec);
						sourcelabel.add(varDef.getName().toString());
					}
					}
				
			} while (iter.hasNext());

		} catch (Exception e) {
			e.printStackTrace();
		}

			return terms;
	}
	
	private List<XSDTerm> getSourceXSDTermsForOutputMapper(){
		XSDSchema srcxsdschema = null;
		MutableSchema sourceSchema = null;
		com.tibco.cep.designtime.core.model.Entity srcEnt = null;
		List<XSDTerm> terms = new ArrayList<XSDTerm>();
		BpmnSchemaCache bsm = bpmnSchemaCache;
		boolean isStarterEvent = false;
		try {
			// / For source always go to vardefinition
			List<VariableDefinition> sourceElements = mapperControlArgs.getSourceElements();
			if (sourceElements == null) {
				return terms;
			}
			Iterator<VariableDefinition> iter = sourceElements.iterator();
			while (iter.hasNext()){
				VariableDefinition varDef = iter.next();
				String name = null;
				SmElement smelt = null;
				isStarterEvent = false;
				boolean isProcessEntity = false;
				//Creating XSD sxhema for Loop Variables
				if (varDef != null
						&& varDef.getName().toString()
								.equals(MapperConstants.LOOP_VAR)) {
					com.tibco.cep.designtime.core.model.Entity loopSrcEnt = CommonIndexUtils
							.getEntity(mapperControlArgs.project.getName(),
									varDef.getSmTypestr());
					XSDTerm xsdEltDec = convertLoopVarVariableDefinitionToXSDTerm(loopSrcEnt,varDef);
					terms.add(xsdEltDec);
					sourcelabel.add(varDef.getName().toString());
					continue;
				} else if (varDef != null
						&& (varDef.getName().toString()
								.equals(MapperConstants.LOOP_MAX) || varDef
								.getName().toString()
								.equals(MapperConstants.LOOP_COUNTER))) {
					XSDTerm xsdEltDec = convertLoopVariableDefinitionToXSDTerm(varDef);
					terms.add(xsdEltDec);
					sourcelabel.add(varDef.getName().toString());
					continue;

				}
				//Creating XSD schema for array of primitive element
				else if (varDef != null
						&& varDef.getSmTypestr() != null
						&& (MapperXSDUtils.isPrimitiveType(varDef
								.getSmTypestr()) || MapperXSDUtils
								.isPrimitiveType(varDef.getSmTypestr()
										.toLowerCase()))) {
					
					XSDTerm xsdEltDec = convertVariableDefinitionToXSDTerm(varDef);
					
					if (xsdEltDec != null && varDef.isArray()) {
						sourcelabel.add("return");
						terms.add(xsdEltDec);
						continue;
					}
					
				}
				
				else if (varDef.getElement() != null) {
					smelt = varDef.getElement();
					if (smelt == null) {
						return null;
					}
					name = varDef.getElement().getName();
					EObjectWrapper<EClass, EObject> modelObjWrapper = null;
					String expandedPath = smelt.getExpandedName().toString();
					if (expandedPath != null && expandedPath.contains(DELEM))
						expandedPath = expandedPath.substring(1,
								expandedPath.lastIndexOf(DELEM));
					String fullPath = expandedPath;
					if (expandedPath != null && expandedPath.contains(EMFRDFTnsFlavor.BE_NAMESPACE))
						fullPath = expandedPath.substring(EMFRDFTnsFlavor.BE_NAMESPACE.length(),
								expandedPath.length());
					sourceSchema = (MutableSchema) smelt.getSchema();
					srcEnt = (com.tibco.cep.designtime.core.model.Entity) mapperControlArgs
							.getSourceElement();

					if (srcEnt != null && srcEnt instanceof Event) {
						isStarterEvent = true;
						name = srcEnt.getName();
					}
					EObject eobj = BpmnIndexUtils.getElement(
							mapperControlArgs.project.getName(), fullPath
									+ BE_PROCESS_EXTN);
					if (eobj != null) {
						isProcessEntity = true;
						modelObjWrapper = EObjectWrapper.wrap(eobj);
						name = (String) modelObjWrapper
								.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
					}
					if (srcEnt != null) {
						srcxsdschema = bsm.getSchema(srcEnt);
					}
					if (srcEnt == null || srcxsdschema == null) {
						srcxsdschema = BpmnSchemaGenerator.getSchema(fullPath,
								sourceSchema, bsm);
						srcxsdschema = (XSDSchema) bsm
								.getSchemaUsingURI(fullPath);
					}
					if (srcxsdschema != null) {
						EList<XSDElementDeclaration> xsdDeclTrial = srcxsdschema
								.getElementDeclarations();
						if(!isStarterEvent)
							addTermsForSourceXsdForOutputMapper(xsdDeclTrial,
								terms, name, isProcessEntity, isStarterEvent);
						else{
							for (XSDElementDeclaration xsdElementDeclaration : xsdDeclTrial) {
								String xsdElementName = xsdElementDeclaration.getName();
								if(xsdElementName.equals(name)){
									terms.add(xsdElementDeclaration);
									sourcelabel.add(name);
								}
							}
							
						}
					}
				} else if (varDef.getType() != null && wsdlSmElement != null) {
					BpmnSchemaGenerator.loadWsdlSchema(wsdlSchemaMap, bsm);
					sourceSchema = (MutableSchema) wsdlSmElement.getSchema();
					srcxsdschema = BpmnSchemaGenerator
							.getSchema(mapperControlArgs.getEntityUri(),
									sourceSchema, bsm);
					srcxsdschema = (XSDSchema) bsm
							.getSchemaUsingURI(mapperControlArgs.getEntityUri());

					if (srcxsdschema != null) {
						EList<XSDElementDeclaration> xsdDecl = srcxsdschema
								.getElementDeclarations();
						addTermsForSourceXsdForOutputMapper(xsdDecl, terms,
								varDef.getName().toString(), isProcessEntity,
								isStarterEvent);
					}
				} else {
					// Evaluating concept for o/p mapper
					srcEnt = returnEnt;
					XSDTerm term = createTermFromEntity(srcEnt, varDef);
					if (term != null) {
						sourcelabel.add("return");
						terms.add(term);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return terms;
	}
	private static XSDTerm convertLoopVariableDefinitionToXSDTerm(VariableDefinition varDef){
		XSDSchema schema = XSDFactory.eINSTANCE.createXSDSchema();
		XSDElementDeclaration term = XSDFactory.eINSTANCE.createXSDElementDeclaration();
		schema.getContents().add(term);
		term.setName(varDef.getName().toString());
		XSDTypeDefinition typeDef = getXSDType(varDef);
		term.setTypeDefinition(typeDef);
		
		return term;
	}
	private static XSDTerm convertLoopVarVariableDefinitionToXSDTerm(com.tibco.cep.designtime.core.model.Entity srcEnt,VariableDefinition varDef){
		if(srcEnt != null){
			XSDTerm term =  BEMapperCoreInputOutputAdapter.getEntityXSDTerm(srcEnt);
			return term;
		}else{
			//LoopVar of primitive type
			return convertVariableDefinitionToXSDTerm(varDef);
//			return null;
		}
	}
	
	private static XSDTerm convertVariableDefinitionToXSDTerm(
			VariableDefinition varDef) {
		if(varDef == null)
			return null;
		XSDSchema schema = XSDFactory.eINSTANCE.createXSDSchema();
		XSDElementDeclaration term = XSDFactory.eINSTANCE.createXSDElementDeclaration();
		schema.getContents().add(term);
		term.setName(varDef.getName().toString());
		XSDTypeDefinition typeDef = getXSDType(varDef);
		term.setTypeDefinition(typeDef);
		if (varDef.isArray()) {
			XSDModelGroup group = XSDFactory.eINSTANCE.createXSDModelGroup();
			XSDParticle particle = XSDFactory.eINSTANCE.createXSDParticle();
			particle.setMinOccurs(0);
			particle.setMaxOccurs(Integer.MAX_VALUE);
			term.setName("elements");
			particle.setTerm(term);
			group.getParticles().add(particle);
			return group;
		}
		return term; 
	}
	
	private static XSDTypeDefinition getXSDType(VariableDefinition varDef) {
		String type = varDef.getSmTypestr();
		return getXSDType(type);
	}
	
	private static XSDTypeDefinition getXSDType(String type) {
		XSDSchema schemaForSchema = XSDSchemaImpl.getSchemaForSchema("http://www.w3.org/2001/XMLSchema");
		XSDTypeDefinition typeDefinition = schemaForSchema.resolveTypeDefinition(MapperXSDUtils.getSchemaType(type));
		return typeDefinition;
	}
	
	private  XSDTerm  createTermFromEntity(com.tibco.cep.designtime.core.model.Entity srcEnt,VariableDefinition vardef){

		XSDTerm term =  BEMapperCoreInputOutputAdapter.getEntityXSDTerm(srcEnt);
		if (term != null) {
			if (vardef != null && vardef.isArray()) {
				XSDElementDeclaration elContent = (XSDElementDeclaration) term;
				XSDElementDeclaration elementContent = EcoreUtil.copy(elContent);
				elementContent.setElement((Element) elContent.getElement().cloneNode(true));
				XSDFactory factory = XSDFactory.eINSTANCE;
				XSDSchema schema = factory.createXSDSchema();
				schema.getContents().add(elementContent);

				XSDModelGroup group = XSDFactory.eINSTANCE.createXSDModelGroup();
				XSDParticle particle = XSDFactory.eINSTANCE.createXSDParticle();
				particle.setMinOccurs(0);
				particle.setMaxOccurs(Integer.MAX_VALUE);
				elementContent.setName(ModelConstants.ARRAY_ELEMENT_NAME);
				particle.setTerm(elementContent);
				group.getParticles().add(particle);
				return group;
			} else {
				return term;
			}
			
	}
		return term;
	
	}
	
	
	@Override
	public List<XSDTerm> getSourceXSDTerms() {
		List<XSDTerm> terms = new ArrayList<XSDTerm>();
		terms.addAll(getSourceXSDTermForGloblVar());
		if(mapperControlArgs.isInputMapper) {
			terms.addAll(getSourceXSDTermsForInputMapper());
		} else {
			terms.addAll(getSourceXSDTermsForOutputMapper());
		}
		varDefs.addAll(mapperControlArgs.getGlobalVars());
		varDefs.addAll( mapperControlArgs.getSourceElements());
		return terms;
	}
			
	
private XSDElementDeclaration createParentDecl(XSDElementDeclaration elContent, String rootLabel, String subLabel, XSDSchema schema2) {
	XSDElementDeclaration createEventDecl = null;
		try {
			XSDElementDeclaration elementContent = EcoreUtil.copy(elContent);

			elementContent.setElement((Element) elContent.getElement()
					.cloneNode(true));

			XSDFactory factory = XSDFactory.eINSTANCE;
			XSDSchema schema = factory.createXSDSchema();

			createEventDecl = schema.resolveElementDeclaration(rootLabel);

			schema.setSchemaForSchemaQNamePrefix("xsd");
			schema.getQNamePrefixToNamespaceMap().put("xsd",
					XSDConstants.SCHEMA_FOR_SCHEMA_URI_2001);
			schema.getQNamePrefixToNamespaceMap().put("ns",
					elContent.getTargetNamespace());

			schema.getElementDeclarations().add(createEventDecl);
			if (createEventDecl.eContainer() == null) {
				schema.getContents().add(createEventDecl);
			}

			// XSDComplexTypeDefinition createEventComplexTypeDef =
			// factory.createXSDComplexTypeDefinition();
			// createEventComplexTypeDef.setTargetNamespace(elContent.getTargetNamespace());
			// if (createEventComplexTypeDef.eContainer() == null) {
			// schema.getContents().add(createEventComplexTypeDef);
			// }

			// createEventDecl.setTypeDefinition(createEventComplexTypeDef);
			// XSDParticle part = factory.createXSDParticle();
			// createEventComplexTypeDef.setContent(part);
			// part.setMinOccurs(1);
			// part.setMaxOccurs(1);

			XSDModelGroup mg = factory.createXSDModelGroup();
			// part.setContent(mg);
			// part.setContent(elementContent);
			elementContent.setTargetNamespace("");

			XSDParticle subpart = factory.createXSDParticle();
			subpart.setMinOccurs(0);
			subpart.setMaxOccurs(1);
			// elementContent.setName(subLabel);
			elementContent.setTargetNamespace("");
			subpart.setContent(elementContent);
			mg.getParticles().add(subpart);
			createEventDecl.setTypeDefinition(elementContent
					.getTypeDefinition());
			// SWTMapperUtils.printXSDSchema(schema);

		}catch(Exception e){
			System.out.println("Exception while creating xsd parent declaration ");
		}
		return createEventDecl;
	}

	public BpmnEMapperControlArgs getMapperArgs() {
		return mapperControlArgs;
	}
	
	@Override
	public VariableBindingKind getVariableBindingKind() {
		return VariableBindingKind.MIXED;
	}

	@Override
	public VariableBindingKind[] getVariableBindingKinds() {
//		VariableDefinition[] sourceElements = (VariableDefinition[]) /*mapperControlArgs.getGlobalVars().getVariables()*/mapperControlArgs.getSourceElements().toArray() ;
//		if (sourceElements == null) {
//			return super.getVariableBindingKinds();
//		}
		List<VariableBindingKind> kinds = new ArrayList<VariableBindingKind>();
		for (VariableDefinition eObject : varDefs) {
			if (((VariableDefinition) eObject).isArray()) {
				kinds.add(VariableBindingKind.DOCUMENT);
				continue;
			}
			
			kinds.add(VariableBindingKind.ELEMENT);
		}
//		for(int i=0; i<sourcelabel.size();i++){
//			kinds.add(VariableBindingKind.ELEMENT);
//		}
		return (VariableBindingKind[]) kinds.toArray(new VariableBindingKind[kinds
				.size()]);
	}

	@Override
	public void setXSLT(String s) {
		// TODO Auto-generated method stub
		
	}

	public com.tibco.cep.designtime.core.model.Entity getReturnEnt() {
		return returnEnt;
	}

	public void setReturnEnt(com.tibco.cep.designtime.core.model.Entity returnEnt) {
		this.returnEnt = returnEnt;
	}
	


	    
}
