package com.tibco.cep.studio.core.dependency;

import java.io.File;
import java.util.List;

import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.domain.DomainInstance;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.event.ImportRegistryEntry;
import com.tibco.cep.designtime.core.model.event.NamespaceEntry;
import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.EventElement;
import com.tibco.cep.studio.core.index.model.scope.CompilableScopeEntry;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.rdf.EMFRDFTnsFlavor;

public class EventDependencyCalculator extends EntityDependencyCalculator {

	@Override
	protected void processEntityElement(File projectDir, String projectName,
			EntityElement element, List<Object> dependencies) {
		if (!(element.getEntity() instanceof Event)) {
			return;
		}
		Event event = (Event) element.getEntity();
		Event superEvent = event.getSuperEvent();
		if (superEvent != null) {
			processAndAddDependency(dependencies, projectName, superEvent);
//			IFile file = IndexUtils.getFile(projectName, superEvent);
//			addDependency(dependencies, file);
		}
		
		if (event instanceof SimpleEvent) {
			SimpleEvent simpEvent = (SimpleEvent) event;
			String uri = simpEvent.getChannelURI();
			if (uri != null) {
				Channel channel = IndexUtils.getChannel(projectName, uri);
				processAndAddDependency(dependencies, projectName, channel);
//				IFile file = IndexUtils.getFile(projectName, channel);
//				addDependency(dependencies, file);
			}
		}
		
		if (event.getExpiryAction() != null) {
			DesignerElement evElem = IndexUtils.getElement(projectName, event.getFullPath());
			if (evElem instanceof EventElement) {
				CompilableScopeEntry expiryActionScopeEntry = ((EventElement) evElem).getExpiryActionScopeEntry();
				if (expiryActionScopeEntry != null && expiryActionScopeEntry.getScope() != null) {
					new RulesDependencyCalculator().processScope(projectDir, projectName, expiryActionScopeEntry.getScope().getActionScope(), dependencies);
				}
			}
		}
		EList<NamespaceEntry> namespaceEntries = event.getNamespaceEntries();
		for (NamespaceEntry namespaceEntry : namespaceEntries) {
			String ns = namespaceEntry.getNamespace();
			if (ns.startsWith(EMFRDFTnsFlavor.BE_NAMESPACE)) {
				String entityName = ns.substring(EMFRDFTnsFlavor.BE_NAMESPACE.length());
				Entity entity = IndexUtils.getEntity(projectName, entityName);
				if (entity != null) {
					processAndAddDependency(dependencies, projectName, entity);
				}
			}
		}
		
		EList<ImportRegistryEntry> registryImportEntries = event.getRegistryImportEntries();
		for (ImportRegistryEntry entry :  registryImportEntries) {
			String path = entry.getLocation();
			if (path != null) {
				processAndAddDependency(projectDir, dependencies, projectName, new Path(path));
			}
		}
		
		EList<PropertyDefinition> properties = event.getProperties();
		for (int i = 0; i < properties.size(); i++) {
			PropertyDefinition propDefinition = properties.get(i);
			EList<DomainInstance> domains = propDefinition.getDomainInstances();
			if (domains.size() > 0) {
				for (int j = 0; j < domains.size(); j++) {
					DomainInstance domainInstance = domains.get(j);
					String path = domainInstance.getResourcePath();
					Path p = new Path(path+"."+IndexUtils.DOMAIN_EXTENSION);
					processAndAddDependency(projectDir, dependencies, projectName, p);
				}
			}
		}
	}

}