package com.tibco.cep.studio.mapper.ui;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
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

import com.tibco.be.model.functions.Predicate;
import com.tibco.be.model.rdf.RDFTnsFlavor;
import com.tibco.be.util.XSTemplateSerializer;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.model.VariableDefinition;
import com.tibco.cep.studio.core.mapper.BEMapperCoreInputOutputAdapter;
import com.tibco.cep.studio.core.mapper.EntitySchemaCache;
import com.tibco.cep.studio.core.util.mapper.MapperInvocationContext;
import com.tibco.cep.studio.core.util.mapper.MapperXSDUtils;
import com.tibco.cep.studio.mapper.core.BEMapperControlArgs;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.mappermodel.emfapi.VariableBindingKind;
import com.tibco.xml.mappermodel.primary.IMapperModelForUI;
import com.tibco.xml.mapperui.emfapi.EMapperInputOutputAdapter;
import com.tibco.xml.mapperui.emfapi.IEMapperControl;
import com.tibco.xml.mapperui.exports.XPathBuilderExp;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmModelGroup;
import com.tibco.xml.schema.SmParticle;
import com.tibco.xml.schema.SmParticleTerm;
import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.xmodel.IXDataConsts;
import com.tibco.xml.xmodel.xpath.Coercion;

public class BEMapperInputOutputAdapter extends EMapperInputOutputAdapter {

	private IEMapperControl mapperControl;
	private BEMapperControlArgs mapperControlArgs;
	private HashMap<String, String> nsPrefixMap;

	public BEMapperInputOutputAdapter(IEMapperControl parent, BEMapperControlArgs mapperControlArgs) {
		this.mapperControl = parent;
		this.mapperControlArgs = mapperControlArgs;
	}

	public IEMapperControl getMapperControl() {
		return mapperControl;
	}

	@Override
	public ResourceSet getResourceSet() {
		String projectName = mapperControlArgs.getContext().getProjectName();
		EntitySchemaCache cache = StudioCorePlugin.getSchemaCache(projectName);
		return cache.getResourceSet();
	}

	@Override
	public List<String> getSourceTreeRootLabels() {
		List<String> labels= new ArrayList<String>();
		List<EObject> sourceElements = mapperControlArgs.getSourceElements();
		if (sourceElements == null) {
			return labels;
		}
		for (EObject eObject : sourceElements) {
			if (eObject instanceof Entity) {
				labels.add(((Entity) eObject).getName());
			} else if (eObject instanceof VariableDefinition) {
				labels.add(((VariableDefinition) eObject).getName());
			} else if (eObject instanceof XSDElementDeclaration) {
				labels.add(((XSDElementDeclaration) eObject).getName());
			}
		}
		return labels;
	}

	@Override
	public Map<String, String> getPrefixToNamespaceMap() {
		if (nsPrefixMap == null) {
			nsPrefixMap = new HashMap<String, String>();
			String xpath = mapperControlArgs.getContext() != null ? mapperControlArgs.getContext().getXslt() : null;
			if (xpath != null) {
				XiNode node;
				try {
					node = XSTemplateSerializer.deSerializeXPathString(xpath);
					HashMap nsPrefixesinXPath = XSTemplateSerializer.getNSPrefixesinXPath(node);
					Set keySet = nsPrefixesinXPath.keySet();
					Iterator iterator = keySet.iterator();
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
		}
		return nsPrefixMap;
	}

	@Override
	public String getXPath() {
		String xpath = mapperControlArgs.getContext() != null ? mapperControlArgs.getContext().getXslt() : null;
		if (xpath != null) {
			xpath = XSTemplateSerializer.getXPathExpressionAsStringValue(xpath);
		}
		return xpath;
	}

	@Override
	public String getXPathResultNativeTypeName() {
		Predicate function = mapperControlArgs.getContext().getFunction();
		Class returnClass = function.getReturnClass();
		if (Calendar.class.equals(returnClass)) {
			return "dateTime";
		}
		return returnClass.getSimpleName().toLowerCase();
//		return super.getXPathResultNativeTypeName();
	}

	@Override
	public String getTargetTreeRootLabel() {
		Entity targetElement = mapperControlArgs.getTargetElement();
		if (targetElement instanceof Concept) {
			return "Function";
		} else if (targetElement instanceof Event) {
			return "Function";
		}
		return "Function";
	}

	@Override
	public Image getTargetTreeRootImage() {
		Entity targetElement = mapperControlArgs.getTargetElement();
		if (targetElement instanceof Concept) {
			return MapperActivator.getDefault().getImage("icons/concept.png");
		} else if (targetElement instanceof Event) {
			return MapperActivator.getDefault().getImage("icons/event.png");
		}
		return null;
	}

	@Override
	public String getXSLT() {
		String xslt = mapperControlArgs.getContext() != null ? mapperControlArgs.getContext().getXslt() : null;
		if (xslt != null) {
			ArrayList<Object> params = new ArrayList<>();
			xslt = XSTemplateSerializer.deSerialize(xslt, params, new ArrayList<>());
			if (params.size() == 1) {
				mapperControlArgs.getContext().setParamName((String) params.get(0));
			}
		}
		return xslt;
	}

	@Override
	public void setXSLT(String xslt) {
		MapperInvocationContext context = mapperControlArgs.getContext();
		if (context != null) {
			ArrayList<String> params = new ArrayList<String>();
			if (context.isUpdate()) {
				String paramName = context.getParamName();
				params.add(paramName);
			} else {
				Entity entity = mapperControlArgs.getTargetElement();
				params.add(entity.getFullPath());
			}

			String serializedXslt = XSTemplateSerializer.serialize(xslt, params, new ArrayList<>());
			mapperControlArgs.getContext().setXslt(serializedXslt);
		}
	}

	@Override
	public void setXPath(String xpath) {
		if (mapperControlArgs.getContext() != null) {
			final List vars = XSTemplateSerializer.searchForVariableNamesinExpression(xpath);
			Map<String, String> pfxs = getPrefixToNamespaceMap();
			String serializedXslt = XSTemplateSerializer.serializeXPathString(xpath, (HashMap) pfxs, vars);
			
			serializedXslt = insertCoercions(serializedXslt);

			mapperControlArgs.getContext().setXslt(serializedXslt);
		}
	}

	private String insertCoercions(String xpath) {
		XPathBuilderExp exp = (XPathBuilderExp) mapperControl;
		try {
			IMapperModelForUI model = (IMapperModelForUI) exp.getClass().getMethod("getModel").invoke(exp);
			List<Coercion> coercionList = model.getCoercionList();
			if (coercionList.size() > 0) {
				StringBuilder b = new StringBuilder();
				b.append("<!--START COERCIONS-->");
				for (Coercion coercion : coercionList) {
					b.append("<!-- ");
					
					b.append(coercion.getXPath());
					b.append(", ");
					int coercionType = coercion.getType();
					String typeString = MapperXSDUtils.getCoercionTypeString(coercionType);
					b.append(typeString);
					b.append(", ");
					b.append(coercion.getTypeOrElementName());
					b.append(" -->");
				}
				b.append("<!--END COERCIONS-->");
				return xpath.replace("<xpath>", b.toString()+"<xpath>");
			}
		} catch (IllegalAccessException e) {
			StudioCorePlugin.log(e);
		} catch (IllegalArgumentException e) {
			StudioCorePlugin.log(e);
		} catch (InvocationTargetException e) {
			StudioCorePlugin.log(e);
		} catch (NoSuchMethodException e) {
			StudioCorePlugin.log(e);
		} catch (SecurityException e) {
			StudioCorePlugin.log(e);
		}
		return xpath;
	}
	
	@Override
	public List<XSDTerm> getSourceXSDTerms() {
		List<XSDTerm> terms = new ArrayList<XSDTerm>();

		List<EObject> sourceElements = mapperControlArgs.getSourceElements();
		if (sourceElements == null) {
			return terms;
		}
		for (EObject eObject : sourceElements) {
			if (eObject instanceof Entity) {
				terms.add(getEntityXSDTerm((Entity)eObject));
			} else if (eObject instanceof VariableDefinition) {
				VariableDefinition varDef = (VariableDefinition) eObject;
				XSDTerm term = BEMapperCoreInputOutputAdapter.getVariableDefinitionXSDTerm(varDef);
				if (term != null) {
					terms.add(term);
				}
			} else if (eObject instanceof XSDTerm) {
				terms.add((XSDTerm) eObject);
			}
		}
		return terms;
	}

	private XSDTerm getEntityXSDTerm(Entity entity) {
		XSDSchema schema = StudioCorePlugin.getSchemaCache(entity.getOwnerProjectName()).getSchema(entity);
		XSDElementDeclaration decl = getElementDecl(schema, entity);
		return decl;
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

	private XSDElementDeclaration createParentDecl_new(XSDElementDeclaration elContent, String rootLabel, String subLabel, XSDSchema schema2) {
		XSDElementDeclaration elementContent = EcoreUtil.copy(elContent);
		if (elContent.getElement() != null) {
			elementContent.setElement((Element) elContent.getElement().cloneNode(true));
		}
		XSDFactory factory = XSDFactory.eINSTANCE;
		XSDSchema schema = factory.createXSDSchema();
		XSDElementDeclaration createEventDecl = schema.resolveElementDeclaration(rootLabel);
		schema.setSchemaForSchemaQNamePrefix("xsd");
		schema.getQNamePrefixToNamespaceMap().put("xsd", XSDConstants.SCHEMA_FOR_SCHEMA_URI_2001);
		schema.getQNamePrefixToNamespaceMap().put("ns", elContent.getTargetNamespace());
// WIP
//		EList<XSDSchemaContent> contents = schema2.getContents();
//		for (XSDSchemaContent xsdSchemaContent : contents) {
//			if (xsdSchemaContent instanceof XSDImport) {
//				schema.getContents().add(EcoreUtil.copy(xsdSchemaContent));
//			}
//		}
//		schema.getIncorporatedVersions().add(schema2);
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
		subpart.setMinOccurs(0);
		subpart.setMaxOccurs(1);
		elementContent.setName(subLabel);
		elementContent.setTargetNamespace("");
		subpart.setContent(elementContent);
		mg.getParticles().add(subpart);
		
//		SWTMapperUtils.printXSDSchema(schema);
		return createEventDecl;
	}

	@Override
	public XSDTerm getTargetXSDTerm() {
		Entity targetElement = mapperControlArgs.getTargetElement();
		SmElement inputType = mapperControlArgs.getContext().getInputElement();
		if (targetElement != null) {
			XSDSchema schema = StudioCorePlugin.getSchemaCache(targetElement.getOwnerProjectName()).getSchema(targetElement);

			XSDTerm term = getElementDecl(schema, targetElement);
			// create dummy parent nodes for createObject/createEvent nodes
			if (inputType != null) {
				return copyType(inputType, term);
			} 
//			else {
//				if (targetElement instanceof Event) {
//					return createParentDecl((XSDElementDeclaration) term, "createEvent", "event", schema);
//				} else {
//					return createParentDecl((XSDElementDeclaration) term, "createObject", "object", schema);
//				}
//			}
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
			
//				MutableSupport.addParticleTerm(dest, term, particle
//						.getMinOccurrence(), particle.getMaxOccurrence());
		}
		return null;
	}
	
	public BEMapperControlArgs getMapperArgs() {
		return mapperControlArgs;
	}

	@Override
	public VariableBindingKind getVariableBindingKind() {
		return VariableBindingKind.MIXED;
	}

	@Override
	public VariableBindingKind[] getVariableBindingKinds() {
		List<EObject> sourceElements = mapperControlArgs.getSourceElements();
		if (sourceElements == null) {
			return super.getVariableBindingKinds();
		}
		List<VariableBindingKind> kinds = new ArrayList<VariableBindingKind>();
		for (EObject eObject : sourceElements) {
			if(eObject instanceof VariableDefinition) {
				if (((VariableDefinition) eObject).isArray()) {
					kinds.add(VariableBindingKind.DOCUMENT);
					continue;
				}
			}
			kinds.add(VariableBindingKind.ELEMENT);
		}
		
		return (VariableBindingKind[]) kinds.toArray(new VariableBindingKind[kinds
				.size()]);
	}
	
	@Override
	public URI getBaseURIForImportLocations() {
		if (mapperControlArgs.getContext() != null) {
			String projectName = mapperControlArgs.getContext().getProjectName();
			return URI.createURI(ResourcesPlugin.getWorkspace().getRoot().getProject(projectName).getLocationURI().toString());
		}
		return URI.createURI(ResourcesPlugin.getWorkspace().getRoot().getLocationURI().toString());
	}

}
