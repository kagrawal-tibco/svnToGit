package com.tibco.cep.studio.core.mapper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xsd.XSDComplexTypeDefinition;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDFactory;
import org.eclipse.xsd.XSDModelGroup;
import org.eclipse.xsd.XSDParticle;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.XSDSchemaContent;
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
import com.tibco.cep.designtime.core.model.event.TimeEvent;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.VariableDefinition;
import com.tibco.cep.studio.core.index.resolution.ElementReferenceResolver;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.SchemaGenerator;
import com.tibco.cep.studio.core.util.mapper.MapperInvocationContext;
import com.tibco.cep.studio.core.util.mapper.MapperXSDUtils;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.mappermodel.emfapi.EMapperModelInputOutputAdapter;
import com.tibco.xml.mappermodel.emfapi.VariableBindingKind;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmModelGroup;
import com.tibco.xml.schema.SmParticle;
import com.tibco.xml.schema.SmParticleTerm;
import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.flavor.XSDL;

/** 
 * Core (non-UI) IOAdapter used for validating BW6 functions only
 * 
 * @author rhollom
 *
 */
public class BEMapperCoreInputOutputAdapter extends
		EMapperModelInputOutputAdapter {

	protected MapperInvocationContext context;
	private List<EObject> sourceElements;
	private Entity targetElement;
	
	public BEMapperCoreInputOutputAdapter(MapperInvocationContext context, List<EObject> sourceElements, Entity targetElement) {
		this.context = context;
		this.sourceElements = sourceElements;
		this.targetElement = targetElement;
	}

	@Override
	public ResourceSet getResourceSet() {
		EntitySchemaCache cache = StudioCorePlugin.getSchemaCache(context.getProjectName());
		return cache.getResourceSet();
	}

	@Override
	public List<String> getSourceTreeRootLabels() {
		List<String> labels= new ArrayList<String>();
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
	public String getTargetTreeRootLabel() {
		if (targetElement instanceof Concept) {
			return "Function";
		} else if (targetElement instanceof Event) {
			return "Function";
		}
		return "Function";
	}

	@Override
	public String getXPathResultNativeTypeName() {
		Predicate function = context.getFunction();
		Class returnClass = function.getReturnClass();
		if (Calendar.class.equals(returnClass)) {
			return "dateTime";
		}
		return returnClass.getSimpleName().toLowerCase();
	}

	@Override
	public Map<String, String> getPrefixToNamespaceMap() {
		String xpath = context != null ? context.getXslt() : null;
		if (xpath != null) {
			HashMap<String, String> map = new HashMap<String, String>();
			XiNode xpathNode = null;
			try {
				xpathNode = XSTemplateSerializer.deSerializeXPathString(xpath);
				HashMap nsPrefixesinXPath = XSTemplateSerializer.getNSPrefixesinXPath(xpathNode);
				return nsPrefixesinXPath;
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return super.getPrefixToNamespaceMap();
	}

	@Override
	public String getXPath() {
		String xpath = context != null ? context.getXslt() : null;
		if (xpath != null) {
			xpath = XSTemplateSerializer.getXPathExpressionAsStringValue(xpath);
		}
		return xpath;
	}

	@Override
	public String getXSLT() {
		String xslt = context != null ? context.getXslt() : null;
		if (xslt != null) {
			ArrayList<Object> params = new ArrayList<>();
			ArrayList<Object> coercions = new ArrayList<>();
			xslt = XSTemplateSerializer.deSerialize(xslt, params, coercions);
			if (params.size() == 1) {
				context.setParamName((String) params.get(0));
			}
		}
		return xslt;
	}

	@Override
	public List<XSDTerm> getSourceXSDTerms() {
		List<XSDTerm> terms = new ArrayList<XSDTerm>();

		if (sourceElements == null) {
			return terms;
		}
		for (EObject eObject : sourceElements) {
			if (eObject instanceof Entity) {
				terms.add(getEntityXSDTerm((Entity)eObject));
			} else if (eObject instanceof VariableDefinition) {
				VariableDefinition varDef = (VariableDefinition) eObject;
				XSDTerm term = getVariableDefinitionXSDTerm(varDef);
				if (term != null) {
					terms.add(term);
				}
			} else if (eObject instanceof XSDTerm) {
				terms.add((XSDTerm) eObject);
			}
		}
		return terms;
	}

	public static XSDTerm getVariableDefinitionXSDTerm(VariableDefinition varDef) {
		if (MapperXSDUtils.isPrimitiveType(varDef.getType())) {
			return MapperXSDUtils.convertVariableDefinitionToXSDTerm(varDef);
		} else {
			Object object = ElementReferenceResolver.resolveVariableDefinitionType(varDef);
			if (object instanceof EntityElement) {
				object = ((EntityElement) object).getEntity();
			}
			if (object instanceof Entity) {
				if (object instanceof TimeEvent) {
					// Generic 'TimeEvent' event, retrieve that schema
					String projectName = getProjectNameFromVar(varDef);
					EntitySchemaCache schemaCache = StudioCorePlugin.getSchemaCache(projectName);
					XSDSchema schema = schemaCache.getSchemaFromURI(URI.createFileURI(SchemaGenerator.BE_BASE_TIME_EVENT_NS));
					if (schema.getElementDeclarations().size() > 0) {
						// it SHOULD always be size 1, but just grab the first one
						return schema.getElementDeclarations().get(0);
					}
				}
				XSDTerm term = getEntityXSDTerm((Entity) object);
				if (term != null) {
					if (varDef.isArray()) {
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
						elementContent.setName("elements");
						particle.setTerm(elementContent);
						group.getParticles().add(particle);
						return group;
					} else {
						return term;
					}
				} else {
					System.err.println("Could not find term for "+object);
				}
			} else if ("Concept".equals(object)) {
				// Generic 'Base' concept, retrieve that schema
				String projectName = getProjectNameFromVar(varDef);
				EntitySchemaCache schemaCache = StudioCorePlugin.getSchemaCache(projectName);
				XSDSchema schema = schemaCache.getSchemaFromURI(URI.createFileURI(SchemaGenerator.BE_BASE_CONCEPT_NS));
				if (schema.getElementDeclarations().size() > 0) {
					// it SHOULD always be size 1, but just grab the first one
					return schema.getElementDeclarations().get(0);
				}
			} else if ("Event".equals(object) || "SimpleEvent".equals(object)) {
				// Generic 'Base' event, retrieve that schema
				String projectName = getProjectNameFromVar(varDef);
				EntitySchemaCache schemaCache = StudioCorePlugin.getSchemaCache(projectName);
				XSDSchema schema = schemaCache.getSchemaFromURI(URI.createFileURI(SchemaGenerator.BE_BASE_EVENT_NS));
				if (schema.getElementDeclarations().size() > 0) {
					// it SHOULD always be size 1, but just grab the first one
					return schema.getElementDeclarations().get(0);
				}
			}  else if ("Exception".equals(object)) {
				// Generic 'Base' exception, retrieve that schema
				String projectName = getProjectNameFromVar(varDef);
				EntitySchemaCache schemaCache = StudioCorePlugin.getSchemaCache(projectName);
				XSDSchema schema = schemaCache.getSchemaFromURI(URI.createFileURI(SchemaGenerator.BE_BASE_EXCEPTION_NS));
				if (schema.getElementDeclarations().size() > 0) {
					// it SHOULD always be size 1, but just grab the first one
					return schema.getElementDeclarations().get(0);
				}
			}
		}
		return null;
	}

	private static String getProjectNameFromVar(VariableDefinition varDef) {
		EObject rootContainer = IndexUtils.getRootContainer(varDef);
		if (rootContainer instanceof RuleElement) {
			return ((RuleElement) rootContainer).getIndexName();
		}
		return null;
	}

	public static XSDTerm getEntityXSDTerm(Entity entity) {
		XSDSchema schema = StudioCorePlugin.getSchemaCache(entity.getOwnerProjectName()).getSchema(entity);
		XSDElementDeclaration decl = getElementDecl(schema, entity);
		return decl;
	}

	private static XSDElementDeclaration getElementDecl(XSDSchema schema, Entity entity) {
		if (schema == null) {
			return null;
		}
		EList<XSDSchemaContent> contents = schema.getContents();
		for (XSDSchemaContent xsdContent: contents) {
			if (xsdContent instanceof XSDElementDeclaration && entity.getName().equals(((XSDElementDeclaration) xsdContent).getName())) {
				if ((RDFTnsFlavor.BE_NAMESPACE+entity.getFullPath()).equals(((XSDElementDeclaration) xsdContent).getTargetNamespace())) {
					return (XSDElementDeclaration) xsdContent;
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
		
//		MapperXSDUtils.printXSDSchema(schema);
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
		
//		MapperXSDUtils.printXSDSchema(schema);
		return createEventDecl;
	}

	@Override
	public XSDTerm getTargetXSDTerm() {
		SmElement inputType = context.getInputElement();
//		SmElement inputType = ((JavaStaticFunctionWithXSLT)context.getFunction()).getInputType();
		if (targetElement != null) {
			XSDSchema schema = StudioCorePlugin.getSchemaCache(targetElement.getOwnerProjectName()).getSchema(targetElement);
			if (schema == null) {
				return null; // TODO : report error
			}
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
	
	@Override
	public VariableBindingKind getVariableBindingKind() {
		return VariableBindingKind.MIXED;
	}

	@Override
	public VariableBindingKind[] getVariableBindingKinds() {
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
		if (context != null) {
			String projectName = context.getProjectName();
			return URI.createURI(ResourcesPlugin.getWorkspace().getRoot().getProject(projectName).getLocationURI().toString());
		}
		return URI.createURI(ResourcesPlugin.getWorkspace().getRoot().getLocationURI().toString());
	}
	
	
	
}
