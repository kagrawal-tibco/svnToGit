package com.tibco.cep.studio.core.search;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.domain.DomainInstance;
import com.tibco.cep.designtime.core.model.element.ElementPackage;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.event.EventPackage;
import com.tibco.cep.designtime.core.model.event.ImportRegistryEntry;
import com.tibco.cep.designtime.core.model.event.NamespaceEntry;
import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.EventElement;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.scope.CompilableScopeEntry;
import com.tibco.cep.studio.core.index.model.search.ElementMatch;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.rdf.EMFRDFTnsFlavor;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.datamodel.helpers.XiAttribute;
import com.tibco.xml.datamodel.helpers.XiChild;

public class EventSearchParticipant extends CompilableContainerSearchParticipant {

	@Override
	protected void searchEntity(EObject resolvedElement, DesignerProject index,
			String nameToFind, IProgressMonitor monitor, SearchResult result) {
		List<DesignerElement> allEntities = IndexUtils.getAllElements(index.getName(), new ELEMENT_TYPES[] { ELEMENT_TYPES.SIMPLE_EVENT, ELEMENT_TYPES.TIME_EVENT });
		for (DesignerElement element : allEntities) {
			Entity entity = ((EntityElement)element).getEntity();
			Event event = (Event) entity;
			if (isEqual(event, resolvedElement)) {
				result.addExactMatch(createElementMatch(DEFINITION_FEATURE, event.eClass(), resolvedElement));
			}
			EList<PropertyDefinition> allProperties = event.getProperties();
			for (PropertyDefinition propertyDefinition : allProperties) {
				if (isEqual(propertyDefinition, resolvedElement)) {
					result.addExactMatch(createElementMatch(EventPackage.EVENT__PROPERTIES, event.eClass(), propertyDefinition));
				}
				if (resolvedElement instanceof EntityElement && ((EntityElement)resolvedElement).getElementType() == ELEMENT_TYPES.DOMAIN) {
					EList<DomainInstance> domainInstances = propertyDefinition.getDomainInstances();
					for (int i = 0; i < domainInstances.size(); i++) {
						DomainInstance domainInstance = domainInstances.get(i);
						if (domainInstance.getResourcePath().equals(((EntityElement) resolvedElement).getEntity().getFullPath())) {
							result.addExactMatch(createElementMatch(ElementPackage.PROPERTY_DEFINITION__DOMAIN_INSTANCES, propertyDefinition.eClass(), propertyDefinition));
						}
					}
				}
			}
			Event superEvent = event.getSuperEvent();
			if (isEqual(superEvent, resolvedElement)) {
				result.addExactMatch(createElementMatch(EventPackage.EVENT__SUPER_EVENT, event.eClass(), element));
			}
			
			if (event instanceof SimpleEvent) {
				searchSimpleEvent(resolvedElement, nameToFind, result, element,
						(SimpleEvent)event);
			}
			Object obj = resolvedElement;
			if (obj instanceof EntityElement) {
				obj = ((EntityElement) resolvedElement).getEntity();
			}
			if (obj instanceof Entity || obj instanceof RuleElement) {
				// search the namespaces
				searchNamespaces(result, event, obj);
			}

		}
	}

	private void searchNamespaces(SearchResult result, Event event, Object obj) {
		String fullEntityPath = "";
		if (obj instanceof Entity) {
			Entity resolvedEntity = (Entity) obj;
			fullEntityPath = resolvedEntity.getFullPath();
		} else {
			fullEntityPath = ((RuleElement)obj).getFolder()+((RuleElement)obj).getName();
		}
		EList<NamespaceEntry> namespaceEntries = event.getNamespaceEntries();
		for (int i = 0; i < namespaceEntries.size(); i++) {
			NamespaceEntry namespaceEntry = namespaceEntries.get(i);
			String namespace = namespaceEntry.getNamespace();
			if (namespace.startsWith(EMFRDFTnsFlavor.BE_NAMESPACE)) {
				String entityName = namespace.substring(EMFRDFTnsFlavor.BE_NAMESPACE.length());
				if (fullEntityPath.equals(entityName)) {
					ElementMatch match = createElementMatch(EventPackage.NAMESPACE_ENTRY__NAMESPACE, namespaceEntry.eClass(), event);
					match.setLabel("Namespace entry with prefix '"+namespaceEntry.getPrefix()+"'");
					result.addExactMatch(match);
				}
			}
		}
		EList<ImportRegistryEntry> importEntries = event.getRegistryImportEntries();
		for (int i = 0; i < importEntries.size(); i++) {
			ImportRegistryEntry registryEntry = importEntries.get(i);
			String location = registryEntry.getLocation();
			String namespace = registryEntry.getNamespace();
			if (namespace.startsWith(EMFRDFTnsFlavor.BE_NAMESPACE)) {
				String entityName = namespace.substring(EMFRDFTnsFlavor.BE_NAMESPACE.length());
				if (fullEntityPath.equals(entityName)) {
					ElementMatch match = createElementMatch(EventPackage.IMPORT_REGISTRY_ENTRY__NAMESPACE, registryEntry.eClass(), event);
					match.setLabel("Registry import entry namespace '"+namespace+"'");
					result.addExactMatch(match);
				}
			}
			if (!location.startsWith("/")) {
				location = "/"+location;
			}
			IPath locPath = new Path(location);
			locPath = locPath.removeFileExtension();
			IPath entityPath = new Path(fullEntityPath);
			if (locPath.equals(entityPath)) {
				ElementMatch match = createElementMatch(EventPackage.IMPORT_REGISTRY_ENTRY__LOCATION, registryEntry.eClass(), event);
				match.setLabel("Registry import entry location '"+location+"'");
				result.addExactMatch(match);
			}
		}
		if (event.getPayloadString() != null && event.getPayloadString().length() > 0) {
			XiNode payloadPropertyNode;
			try {
				payloadPropertyNode = XiParserFactory.newInstance().parse(new InputSource(new StringReader(event.getPayloadString())));
				XiNode node = payloadPropertyNode.getFirstChild();
				String ref = XiAttribute.getStringValue(node, "ref");
				if (ref == null) {
			        XiNode fc = XiChild.getFirstChild(node);
			        if (fc != null) {
			        	ref = XiAttribute.getStringValue(fc,"type");
			        }
				}
				if (ref != null) {
					String[] refs = ref.split(":");
					if (refs.length == 2) {
						String pfx = refs[0];
						NamespaceEntry nsEntry = getNamespaceEntry(namespaceEntries, pfx);
						if (matchNsEntry(nsEntry, fullEntityPath)) {
							ElementMatch match = createElementMatch(EventPackage.EVENT__PAYLOAD_STRING, event.eClass(), event);
							match.setLabel("XML Element reference in payload");
							result.addExactMatch(match);
						}
					}
				}
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private boolean matchNsEntry(NamespaceEntry nsEntry, String fullEntityPath) {
		if (nsEntry == null) {
			return false;
		}
		String namespace = nsEntry.getNamespace();
		if (namespace.startsWith(EMFRDFTnsFlavor.BE_NAMESPACE)) {
			String entityName = namespace.substring(EMFRDFTnsFlavor.BE_NAMESPACE.length());
			if (fullEntityPath.equals(entityName)) {
				return true;
			}
		}
		return false;
	}

	private NamespaceEntry getNamespaceEntry(EList<NamespaceEntry> namespaceEntries, String pfx) {
		for (int i = 0; i < namespaceEntries.size(); i++) {
			NamespaceEntry namespaceEntry = namespaceEntries.get(i);
			if (pfx.equals(namespaceEntry.getPrefix())) {
				return namespaceEntry;
			}
		}
		return null;
	}

	private void searchSimpleEvent(EObject resolvedElement, String nameToFind,
			SearchResult result, DesignerElement element, SimpleEvent event) {
		Object obj = resolvedElement;
		if (obj instanceof EntityElement) {
			obj = ((EntityElement) resolvedElement).getEntity();
		}
		if (obj instanceof Channel) {
			String channelPath = ((Channel) obj).getFullPath();
			if (channelPath.equals(event.getChannelURI())) {
				result.addExactMatch(createElementMatch(EventPackage.SIMPLE_EVENT__CHANNEL_URI, event.eClass(), element));
			}
		}
		if (obj instanceof Destination) {
			Destination dest = (Destination) obj;
			String destName = event.getDestinationName();
			Channel channel = (Channel) dest.eContainer().eContainer();
			if (dest.getName().equals(destName) && channel.getFullPath().equals(event.getChannelURI())) {
				result.addExactMatch(createElementMatch(EventPackage.SIMPLE_EVENT__DESTINATION_NAME, event.eClass(), element));
			}
		}
		if (element instanceof EventElement) {
			CompilableScopeEntry expiryActionScopeEntry = ((EventElement) element).getExpiryActionScopeEntry();
			if (expiryActionScopeEntry != null) {
				processCompilableScopeEntry(event.getOwnerProjectName(), resolvedElement, expiryActionScopeEntry, nameToFind, result, ((Event)((EventElement) element).getEntity()).getExpiryAction());
			}
		}
		
	}

	@Override
	protected boolean isEqual(Object element, Object resolvedElement) {
		if (element instanceof Destination
				&& resolvedElement instanceof Destination) {
			Destination dest1 = (Destination) element;
			Destination dest2 = (Destination) resolvedElement;
			if (dest1.getName().equals(dest2.getName())
					&& dest1.getEventURI() != null
					&& dest2.getEventURI() != null
					&& dest1.getEventURI().equals(dest2.getEventURI())
					&& dest1.getFolder() != null 
					&& dest2.getFolder()!= null 
					&& dest1.getFolder().equals(dest2.getFolder())) {
				return true;
			}
		}
		return super.isEqual(element, resolvedElement);
	}

}
