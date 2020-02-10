package com.tibco.cep.studio.debug.ui.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.graphics.Image;
import org.eclipse.xsd.XSDComplexTypeDefinition;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDFactory;
import org.eclipse.xsd.XSDModelGroup;
import org.eclipse.xsd.XSDParticle;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.XSDTerm;
import org.eclipse.xsd.util.XSDConstants;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;

import com.tibco.be.model.rdf.RDFTnsFlavor;
import com.tibco.be.util.XSTemplateSerializer;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.mapper.EntitySchemaCache;
import com.tibco.cep.studio.mapper.ui.MapperActivator;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.mappermodel.emfapi.VariableBindingKind;
import com.tibco.xml.mapperui.emfapi.EMapperInputOutputAdapter;
import com.tibco.xml.mapperui.emfapi.IEMapperControl;
import com.tibco.xml.parsers.xmlfactories.DocumentBuilderFactory;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmModelGroup;
import com.tibco.xml.schema.SmParticle;
import com.tibco.xml.schema.SmParticleTerm;
import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.xmodel.IXDataConsts;

/**
 * 
 * @author sasahoo
 *
 */
public class RuleInputMapperInputOutputAdapter extends EMapperInputOutputAdapter {

	private IEMapperControl mapperControl;
	private RuleInputMapperControlArgs mapperControlArgs;
	private List<String> sourcelabel = new ArrayList<String>() ;
	private EntitySchemaCache entitySchemaCache = null ;
	private String strGlobalVarLabel = "globalVariables";
	private HashMap<String, String> nsPrefixMap;	

	public RuleInputMapperInputOutputAdapter(IEMapperControl parent, RuleInputMapperControlArgs mapperControlArgs) {
		this.mapperControl = parent;
		this.mapperControlArgs = mapperControlArgs;
	}

	public IEMapperControl getMapperControl() {
		return mapperControl;
	}
	@Override
	public ResourceSet getResourceSet() {
		String projectName = mapperControlArgs.getInvocationContext().getProjectName();
		EntitySchemaCache cache = StudioCorePlugin.getSchemaCache(projectName);
		return cache.getResourceSet();
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
		return MapperActivator.getDefault().getImage("icons/iconForEach16.gif");
	}

	@Override
	public String getTargetTreeRootLabel() {
		return "DebugInput";

	}

	@Override
	public XSDTerm getTargetXSDTerm() {

		if (getMapperArgs().getInvocationContext() != null) {
			SmElement inputType = mapperControlArgs.getTargetSchema()/*mapperControlArgs.getContext().getInputElement()*/;
			Entity targetElement = (Entity)mapperControlArgs.getTargetElement()/*mapperControlArgs.getTargetElement()*/;
			if (targetElement != null) {
				XSDSchema schema = StudioCorePlugin.getSchemaCache(targetElement.getOwnerProjectName()).getSchema(targetElement);

				XSDTerm term = getElementDecl(schema, targetElement);
				// create dummy parent nodes for createObject/createEvent nodes
				if (inputType != null) {
					return copyType(inputType, term);
				} 
			} else {
				// return an empty term to avoid exceptions
				XSDTerm el = XSDFactory.eINSTANCE.createXSDElementDeclaration();
				Element value = null;
				try {
					value = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument().createElement("empty");
				} catch (DOMException e) {
					e.printStackTrace();
				} catch (ParserConfigurationException e) {
					e.printStackTrace();
				}
				el.setElement(value);
				return copyType(inputType, el);
			}
		}
		return null;
	}

	protected XSDElementDeclaration copyType(SmElement rootSrc, XSDTerm xsdTerm) {
		SmType src = rootSrc.getType();
		SmModelGroup grp = src.getContentModel();
		if (grp == null) {
			return null;
		}
		Iterator<?> itr = grp.getParticles();
		while (itr.hasNext()) {
			SmParticle particle = (SmParticle) itr.next();
			SmParticleTerm term = particle.getTerm();
			if (!XSDL.ANYTYPE_NAME.localName.equalsIgnoreCase(term.getName()))
				return createParentDecl((XSDElementDeclaration) xsdTerm, rootSrc.getName(), term, particle.getMinOccurrence(), particle.getMaxOccurrence());
		}
		return null;
	}


//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	@Override
//	public Map<String, String> getPrefixToNamespaceMap() {
//		//		Map<String, String> prefixMap = new HashMap<String, String>();
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
		if (nsPrefixMap == null) {
			nsPrefixMap = new HashMap<String, String>();
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

	private List<XSDTerm> getSourceXSDTermForGloblVar(){
		List<XSDTerm> xsdTerms = new ArrayList<XSDTerm>();
		if (getMapperArgs().getInvocationContext() != null) {
			XSDTerm globalVarTerm = (XSDTerm)mapperControlArgs.getSourceElement();
			xsdTerms.add(globalVarTerm);
		}
		sourcelabel.add(strGlobalVarLabel);
		return xsdTerms;
	}

	@Override
	public List<XSDTerm> getSourceXSDTerms() {
		List<XSDTerm> terms = new ArrayList<XSDTerm>();
		terms.addAll(getSourceXSDTermForGloblVar());
		return terms;
	}

	public RuleInputMapperControlArgs getMapperArgs() {
		return mapperControlArgs;
	}

	@Override
	public VariableBindingKind getVariableBindingKind() {
		return VariableBindingKind.MIXED;
	}

	@Override
	public VariableBindingKind[] getVariableBindingKinds() {
		List<VariableBindingKind> kinds = new ArrayList<VariableBindingKind>();
		for(int i=0; i<sourcelabel.size();i++){
			kinds.add(VariableBindingKind.ELEMENT);
		}
		return (VariableBindingKind[])kinds.toArray(new VariableBindingKind[kinds.size()]);
	}


	//	@Override
	//	public String getXSLT() {
	//		String xslt = mapperControlArgs.getXSLT() != null ? mapperControlArgs.getXSLT() : null;
	//		if (xslt != null) {
	//			xslt = XSTemplateSerializer.deSerialize(xslt, new ArrayList<>(), new ArrayList<>());
	//		}
	//		return xslt;
	//	}

	@Override
	public String getXSLT() {
		String xslt = mapperControlArgs.getInvocationContext() != null ? mapperControlArgs.getInvocationContext().getXslt() : null;
		//		if (xslt != null) {
		//			ArrayList<Object> params = new ArrayList<>();
		//			xslt = XSTemplateSerializer.deSerialize(xslt, params, new ArrayList<>());
		//			if (params.size() == 1) {
		//				mapperControlArgs.getInvocationContext().setParamName((String) params.get(0));
		//			}
		//		}
		return xslt;
	}

	@Override
	public void setXSLT(String xslt) {
		//		RuleInputMapperInvocationContext context = mapperControlArgs.getInvocationContext();
		//		ArrayList<String> params = new ArrayList<String>();
		//		if (context != null) {
		//			Entity entity = (Entity)mapperControlArgs.getTargetElement();
		//			params.add(entity.getFullPath());
		//		}
		//		String serializedXslt = XSTemplateSerializer.serialize(xslt, params, new ArrayList<>());
		mapperControlArgs.getInvocationContext().setXslt(xslt);
	}

	private XSDElementDeclaration createParentDecl(XSDElementDeclaration elContent, String rootLabel, SmParticleTerm term, int minOccur, int maxOccur) {
		XSDElementDeclaration elementContent = EcoreUtil.copy(elContent);
		elementContent.setElement((Element) elContent.getElement().cloneNode(true));
		XSDFactory factory = XSDFactory.eINSTANCE;
		XSDSchema schema = factory.createXSDSchema();
		XSDElementDeclaration createEventDecl = schema.resolveElementDeclaration(rootLabel);
		schema.setSchemaForSchemaQNamePrefix("xsd");
		schema.getQNamePrefixToNamespaceMap().put("xsd", XSDConstants.SCHEMA_FOR_SCHEMA_URI_2001);
		schema.getQNamePrefixToNamespaceMap().put("ns", elContent.getTargetNamespace());

		schema.getElementDeclarations().add(createEventDecl);
		if (createEventDecl.eContainer() == null) {
			schema.getContents().add(createEventDecl);
		}

		XSDComplexTypeDefinition createEventComplexTypeDef = factory.createXSDComplexTypeDefinition();
		createEventComplexTypeDef.setTargetNamespace(elContent.getTargetNamespace());
		if (createEventComplexTypeDef.eContainer() == null) {
			schema.getContents().add(createEventComplexTypeDef);
		}

		createEventDecl.setTypeDefinition(createEventComplexTypeDef);
		XSDParticle part = factory.createXSDParticle();
		createEventComplexTypeDef.setContent(part);
		part.setMinOccurs(1);
		part.setMaxOccurs(1);
		XSDModelGroup mg = factory.createXSDModelGroup();
		part.setContent(mg);

		XSDParticle subpart = factory.createXSDParticle();
		subpart.setMinOccurs(minOccur);
		subpart.setMaxOccurs(maxOccur);
		elementContent.setName(term.getName());
		elementContent.setTargetNamespace(term.getNamespace());
		subpart.setContent(elementContent);
		mg.getParticles().add(subpart);

		//		SWTMapperUtils.printXSDSchema(schema);
		return createEventDecl;
	}

	private XSDElementDeclaration getElementDecl(XSDSchema schema, Entity entity) {
		if (schema == null) {
			return null;
		}
		EList<XSDElementDeclaration> elementDeclarations = schema.getElementDeclarations();
		for (XSDElementDeclaration xsdElementDeclaration : elementDeclarations) {
			if (entity.getName().equals(xsdElementDeclaration.getName())) {
				if ((RDFTnsFlavor.BE_NAMESPACE+entity.getFullPath()).equals(xsdElementDeclaration.getTargetNamespace())) {
					return xsdElementDeclaration;
				}
			}
		}
		return null;
	}


	public void setEntitySchemaCache(EntitySchemaCache entitySchemaCache) {
		this.entitySchemaCache = entitySchemaCache;
	}

	public EntitySchemaCache getEntitySchemaCache() {
		return entitySchemaCache;
	}

}
