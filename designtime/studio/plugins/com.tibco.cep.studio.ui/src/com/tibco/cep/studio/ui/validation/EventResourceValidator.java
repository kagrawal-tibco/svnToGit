/**
 * 
 */
package com.tibco.cep.studio.ui.validation;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tibco.be.util.wsdl.SOAPEventPayloadBuilder;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.event.ImportRegistryEntry;
import com.tibco.cep.designtime.core.model.event.NamespaceEntry;
import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.validation.ModelError;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.repo.EMFTnsCache;
import com.tibco.cep.studio.core.util.ResourceHelper;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.core.validation.ValidationContext;
import com.tibco.cep.studio.core.validation.ValidationUtils;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.util.StudioUIUtils;
import com.tibco.xml.data.primitive.XmlNodeKind;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.datamodel.helpers.XiAttribute;
import com.tibco.xml.datamodel.nodes.Element;
import com.tibco.xml.schema.SmComponent;
import com.tibco.xml.schema.SmNamespace;
import com.tibco.xml.schema.parse.xsd.XSDLSchema;
import com.tibco.xml.tns.parse.TnsDocument;

/**
 * @author rmishra
 *
 */
public class EventResourceValidator extends EntityResourceValidator {

	@Override
	public boolean canContinue() {
		// TODO Auto-generated method stub
		return super.canContinue();
	}

	@Override
	protected void reportProblem(IResource resource, String message,
			int lineNumber, int start, int length, int severity) {
		// TODO Auto-generated method stub
		super.reportProblem(resource, message, lineNumber, start, length, severity);
	}

	@Override
	protected void reportProblem(IResource resource, String message,
			int severity) {
		// TODO Auto-generated method stub
		super.reportProblem(resource, message, severity);
	}

	@Override
	public boolean validate(ValidationContext validationContext) {
		IResource resource = validationContext.getResource();
		if (resource == null) return true;
		deleteMarkers(resource);
		super.validate(validationContext);
		DesignerElement modelObj = getModelObject(resource);
//		int modificationType = validationContext.getModificationType();
//		int buildType = validationContext.getBuildType();
		if (modelObj instanceof EntityElement) {
			EntityElement enityElmt = (EntityElement) modelObj;
			if (enityElmt.getEntity() instanceof Event) {
				Event event = (Event)enityElmt.getEntity();
				deleteMarkers(resource, EVENT_DEFAULT_DESTINATION_VALIDATION_MARKER_TYPE);
				List<ModelError> modelErrorList = event.getModelErrors();
				for (ModelError modelError : modelErrorList){
					boolean iswarning = modelError.isWarning();
					int severityType = iswarning ? IMarker.SEVERITY_WARNING : IMarker.SEVERITY_ERROR;
					reportProblem(resource, modelError.getMessage(), severityType);
				}
				// validate Rule related to Event for example Expiry Action
				Rule rule = event.getExpiryAction();
				if (rule != null){
					validateCompilable(resource, rule);
				}

				validateNamespaces(event, resource);

				Map<PropertyDefinition, String> map = new HashMap<PropertyDefinition, String>();
				StudioUIUtils.collectPropertyDomainTypeMismatch(event.getOwnerProjectName(), event.getProperties(), map);
				if(map.size() > 0){
					for(PropertyDefinition pd:map.keySet()){
						reportProblem(resource, Messages.getString("associate_domain_error", 
								pd.getName(), pd.getType().getName(), map.get(pd)), IMarker.SEVERITY_ERROR);

					}
				}
			}
		} 
		
			// Resource is deleted from file system 
			// now validate all dependent Resources 
			// 1: all rules / Rule Function that refer these events 
			// validate all Rules
//		if ((IResourceDelta.CHANGED == modificationType || IResourceDelta.REMOVED == modificationType) 
//				&& IncrementalProjectBuilder.INCREMENTAL_BUILD == buildType){
//			validateAllDependentResources(validationContext);
//		}
		
		//Validate CDD resources, as they have property definitions
		if (validationContext.getBuildType() != IncrementalProjectBuilder.FULL_BUILD) {
			ValidationUtils.validateResourceByExtension(resource.getProject(), "cdd");
		}

		return true;
	}
	
	private void validateNamespaces(Event event, IResource resource) {
		IPath resPath = resource.getFullPath();
		resPath.removeFirstSegments(1);
		EList<NamespaceEntry> namespaceEntries = event.getNamespaceEntries();
		EMFTnsCache cache = StudioCorePlugin.getCache(event.getOwnerProjectName());
		EList<ImportRegistryEntry> importEntries = event.getRegistryImportEntries();
		
		validateNamespaceEntries(event, resource, namespaceEntries, cache,
				importEntries);
		
		validateImportRegistryEntries(resource, cache, importEntries);
		
		validatePayload(event, resource, namespaceEntries, cache, importEntries);
	}

	private void validateImportRegistryEntries(IResource resource,
			EMFTnsCache cache, EList<ImportRegistryEntry> importEntries) {
		for (int i = 0; i < importEntries.size(); i++) {
			ImportRegistryEntry registryEntry = importEntries.get(i);
			String location = registryEntry.getLocation();
			String namespace = registryEntry.getNamespace();
			SmNamespace smNamespace = cache.getSmNamespaceProvider().getNamespace(namespace);
			if (smNamespace == null) {
				TnsDocument document = getDocumentFromLocation(resource, cache, location);
				if (document == null) {
					reportProblem(resource, "Invalid namespace (location '"+location+"').  Cannot find namespace "+namespace, IMarker.SEVERITY_WARNING);
				}
			} else {
				TnsDocument document = getDocumentFromLocation(resource, cache, location);
				if (document == null) {
					reportProblem(resource, "Invalid namespace location.  Cannot find document at location "+"('"+location+"')", IMarker.SEVERITY_WARNING);
				}
			}
		}
	}

	private void validateNamespaceEntries(Event event, IResource resource,
			EList<NamespaceEntry> namespaceEntries, EMFTnsCache cache,
			EList<ImportRegistryEntry> importEntries) {
		for (int i = 0; i < namespaceEntries.size(); i++) {
			NamespaceEntry namespaceEntry = namespaceEntries.get(i);
			String namespace = namespaceEntry.getNamespace();
			SmNamespace smNamespace = StudioCorePlugin.getCache(event.getOwnerProjectName()).getSmNamespaceProvider().getNamespace(namespace);
			if (smNamespace == null) {
				ImportRegistryEntry registryEntry = ModelUtils.getImportRegistryEntry(importEntries, namespace);
				if (registryEntry != null) {
					String location = registryEntry.getLocation();
					TnsDocument document = getDocumentFromLocation(resource, cache, location);
					if (document == null) {
						reportProblem(resource, "Invalid namespace (prefix '"+namespaceEntry.getPrefix()+"').  Cannot find namespace "+namespace, IMarker.SEVERITY_WARNING);
					}
				}
			}
		}
	}

	private TnsDocument getDocumentFromLocation(IResource resource, EMFTnsCache cache,
			String location) {
		if (!location.startsWith("/")) {
			location = "/"+location;
		}
		IPath locPath = new Path(location);
		IFile file = resource.getProject().getFile(locPath);
		if (!file.exists()) {
			reportProblem(resource, "Invalid namespace location.  Cannot find namespace at location "+"('"+location+"')", IMarker.SEVERITY_WARNING);
		} else {
			String string = ResourceHelper.getLocationURI(file).toString();
			return cache.getDocument(string);
		}
		return null;
	}

	private void validatePayload(Event event, IResource resource,
			EList<NamespaceEntry> namespaceEntries, EMFTnsCache cache,
			EList<ImportRegistryEntry> importEntries) {
		if(event.isSoapEvent()) {
			XiNode node=null;
			if(event.getPayloadString() != null) {
				try {
					node = XiParserFactory.newInstance().parse(new InputSource(new StringReader(event.getPayloadString())));
				} catch (SAXException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				boolean valid = SOAPEventPayloadBuilder.validatePayload((Element)node.getChildren().next());
				if(!valid) {
					reportProblem(resource, "Event "+event.getFullPath()+" has invalid payload schema", resource.getFullPath().toPortableString(), IMarker.SEVERITY_ERROR);
				}
			} else {
				reportProblem(resource, "SOAPEvent "+event.getFullPath()+" cannot have empty payload schema", resource.getFullPath().toPortableString(), IMarker.SEVERITY_ERROR);
			}
		}
		if (event.getPayloadString() != null && event.getPayloadString().length() > 0) {
			XiNode payloadPropertyNode;
			try {
				payloadPropertyNode = XiParserFactory.newInstance().parse(new InputSource(new StringReader(event.getPayloadString())));
				Iterator children = payloadPropertyNode.getChildren();
				while (children.hasNext()) {
					XiNode object = (XiNode) children.next();
					if (object.getNodeKind() == XmlNodeKind.ELEMENT) {
						processChildNode(resource, namespaceEntries, cache,
								importEntries, object);
					}
				}
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void processChildNode(IResource resource,
			EList<NamespaceEntry> namespaceEntries, EMFTnsCache cache,
			EList<ImportRegistryEntry> importEntries, XiNode node) {
		boolean isType = false;
		String ref = XiAttribute.getStringValue(node, "ref");
		if (ref == null) {
			ref = XiAttribute.getStringValue(node,"type");
			if(ref != null)
				isType = true;
		}
		if (ref != null) {
			String[] refs = ref.split(":");
			if (refs.length == 2) {
				String pfx = refs[0];
				if ("xsd".equals(pfx)) {
					return;
				}
				String name = refs[1];
				NamespaceEntry nsEntry = ModelUtils.getNamespaceEntry(namespaceEntries, pfx);
				if (nsEntry == null) {
					reportProblem(resource, "Invalid XML Reference in payload.  No namespace with prefix '"+pfx+"'");
				}
				SmNamespace namespace = cache.getSmNamespaceProvider().getNamespace(nsEntry.getNamespace());
				if (namespace != null) {
					SmComponent component = null;
					if(isType)
						component =namespace.getComponent(SmComponent.TYPE_TYPE, name);
					else
						component =namespace.getComponent(SmComponent.ELEMENT_TYPE, name);
					if (component == null) {
						reportProblem(resource, "Invalid XML Reference in payload.  No element with name '"+name+"' found");
					}
				} else {
					// try to get by location instead
					ImportRegistryEntry registryEntry = ModelUtils.getImportRegistryEntry(importEntries, nsEntry.getNamespace());
					if (registryEntry != null) {
						String location = registryEntry.getLocation();
						if (!location.startsWith("/")) {
							location = "/"+location;
						}
						IPath locPath = new Path(location);
						IFile file = resource.getProject().getFile(locPath);
						if (!file.exists()) {
							reportProblem(resource, "Invalid schema reference in payload.  Cannot find namespace at location "+"('"+location+"')");
						} else {
							String string = ResourceHelper.getLocationURI(file).toString();
							TnsDocument document = cache.getDocument(string);
							if (document == null) {
								reportProblem(resource, "Invalid XML Reference in payload.  Namespace '"+nsEntry.getNamespace()+"' not found");
							} else {
								SmComponent component = null;
								if(isType)
									component = ((XSDLSchema)document).getComponent(SmComponent.TYPE_TYPE, name);
								else
									component = ((XSDLSchema)document).getComponent(SmComponent.ELEMENT_TYPE, name);
								if (component == null) {
									reportProblem(resource, "Invalid XML Reference in payload.  No element with name '"+name+"' found");
								}
							}
						}

					} else {
						reportProblem(resource, "Invalid XML Reference in payload.  Namespace '"+nsEntry.getNamespace()+"' not found");
					}
				}
			}
		}
		Iterator children = node.getChildren();
		while (children.hasNext()) {
			XiNode object = (XiNode) children.next();
			if (object.getNodeKind() == XmlNodeKind.ELEMENT) {
				processChildNode(resource, namespaceEntries, cache,
						importEntries, object);
			}
		}
	}

	/**
	 * validates all dependent resources
	 * @param resource
	 * @return
	 */
	protected boolean validateAllDependentResources(final ValidationContext vldContext){
		IResource resource = vldContext.getResource();
		if (resource == null) return true;
		
		// validate all the rules / rule functions
		DesignerProject index = IndexUtils.getIndex(resource);
		if (index == null) return true;
		for (RuleElement re : index.getRuleElements()){
			if (re == null) continue;
			ELEMENT_TYPES reType = re.getElementType();
			if (!(ELEMENT_TYPES.RULE == reType || ELEMENT_TYPES.RULE_FUNCTION == reType)) continue;
			String folder = re.getFolder();
			String name = re.getName();
			String ext = IndexUtils.getFileExtension(reType);
			String ruleResPath = folder + name + "." + ext;
			IResource resolvedRuleResource = ValidationUtils.resolveResourceReference(ruleResPath, re.getIndexName());
			if (resolvedRuleResource == null) continue;
			RuleResourceValidator rrv = new RuleResourceValidator();
			ValidationContext context = new ValidationContext(resolvedRuleResource, IResourceDelta.CHANGED , vldContext.getBuildType());
			rrv.validate(context);
		}
		return true;
	}
}