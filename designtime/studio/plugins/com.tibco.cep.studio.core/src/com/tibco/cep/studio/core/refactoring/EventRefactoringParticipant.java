package com.tibco.cep.studio.core.refactoring;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.domain.Domain;
import com.tibco.cep.designtime.core.model.domain.DomainInstance;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.designtime.core.model.rule.Symbols;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.search.EntitySearchParticipant;
import com.tibco.cep.studio.core.search.EventSearchParticipant;

public class EventRefactoringParticipant extends EntityRefactoringParticipant<Event> {

	private static final String[] SUPPORTED_EXTENSIONS = new String[] { CommonIndexUtils.EVENT_EXTENSION, CommonIndexUtils.TIME_EXTENSION };

	public EventRefactoringParticipant() {
		super();
	}

	@Override
	protected EntitySearchParticipant getSearchParticipant() {
		return new EventSearchParticipant();
	}

	@Override
	protected String[] getSupportedExtensions() {
		return SUPPORTED_EXTENSIONS;
	}

	@Override
	public RefactoringStatus checkConditions(IProgressMonitor pm,
			CheckConditionsContext context) throws OperationCanceledException {
		if (isDeleteRefactor()) {
			// checks for references to the deleted element(s)
			return super.checkConditions(pm, context);
		}
		IResource resource = getResource();
		if (!(resource instanceof IFile)) {
			return null;
		}
		IFile file = (IFile) resource;
		if (isSupportedResource(file)) {
			return super.checkConditions(pm, context);
		}
		return new RefactoringStatus();
	}
	
	@Override
	protected void checkForDuplicateElement(RefactoringStatus status,
			String newName, IResource parentResource) {
		Object element = getElementToRefactor();
		if (element instanceof PropertyDefinition) {
			EObject container = ((PropertyDefinition) element).eContainer();
			if (container instanceof Event) {
				EList<PropertyDefinition> properties = ((Event) container).getProperties();
				for (int i=0; i<properties.size(); i++) {
					if (newName.equals(properties.get(i).getName())) {
						status.addFatalError("A property already exists with the name '"+newName+"'");
						return;
					}
				}
			}
		} else {
			super.checkForDuplicateElement(status, newName, parentResource);
		}
	}

	@Override
	public Change processEntity(Object elementToRefactor, String projectName, IProgressMonitor pm, boolean preChange) throws CoreException,
			OperationCanceledException {
		// look through all concepts and make appropriate changes
		if (elementToRefactor instanceof EntityElement) {
			elementToRefactor = ((EntityElement) elementToRefactor).getEntity();
		}
		if (!shouldUpdateReferences()) {
			System.out.println("not updating references");
			return null;
		}
		//Check for Event Resource entity for Property Refactor 
		if(elementToRefactor instanceof PropertyDefinition){
			PropertyDefinition propertyDefinition = (PropertyDefinition)elementToRefactor;
			if(propertyDefinition.eContainer() instanceof Event ||
					propertyDefinition.eContainer() instanceof Concept){
				//Do Nothing
			}else{
				return null;
			}
		}
		CompositeChange compositeChange = new CompositeChange("Event changes:");
		List<Event> allEvents = IndexUtils.getAllEvents(projectName);
		for (Event event : allEvents) {
			//Check for Event Resource entity for Property Refactor 
			if(elementToRefactor instanceof PropertyDefinition){
				PropertyDefinition propertyDefinition = (PropertyDefinition)elementToRefactor;
				if(propertyDefinition.eContainer() instanceof Event){
					Event revent = (Event)propertyDefinition.eContainer();
					if(revent.getFullPath().equals(event.getFullPath())){
						continue;
					}
				}
			}else if (event == elementToRefactor) {
				// already processed in the pre-change
				continue;
			}
			processEvent(projectName, compositeChange, event, elementToRefactor, preChange);
		}
		if (compositeChange.getChildren() != null && compositeChange.getChildren().length > 0) {
			return compositeChange;
		}
		return null;
	}

	@Override
	protected Event preProcessEntityChange(Event refactorParticipant) {
		CompositeChange compositeChange = new CompositeChange("");
		try {
			return processEvent(refactorParticipant.getOwnerProjectName(), compositeChange, refactorParticipant, getElementToRefactor(), true);
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return refactorParticipant;
	}

	private Event processEvent(String projectName,
			CompositeChange compositeChange, Event event, Object elementToRefactor, boolean isPreProcess) throws CoreException {
		if (isSharedElement(event)) {
			return event;
		}
		// must be sure to copy the concept before making changes, otherwise canceling the wizard will keep the changes made and corrupt the concepts
		IFile file = IndexUtils.getFile(projectName, event);
		event = (Event) EcoreUtil.copy(event);
		boolean changed = false;
		if (isContainedDelete(file)) {
			return event;
		}
		if (isProjectRefactor()) {
			processChildren(projectName, event, getNewElementName());
			changed = true;
		}

		if (elementToRefactor instanceof EntityElement) {
			elementToRefactor = ((EntityElement) elementToRefactor).getEntity();
		}
		
		if (elementToRefactor instanceof RuleElement) {
			elementToRefactor = ((RuleElement) elementToRefactor).getRule();
		}
		
		if (processFolder(event)) {
			changed = true;
		}
		if (elementToRefactor instanceof Concept || isFolderRefactor()) {
			String newFullPath = "";
			String oldFullPath = "";
			if (elementToRefactor instanceof Concept) {
				Concept refactoredElement = (Concept) elementToRefactor;
				oldFullPath = refactoredElement.getFullPath();
				if (isRenameRefactor()) {
					newFullPath = refactoredElement.getFolder() + getNewElementName();
				} else if (isMoveRefactor()){
					newFullPath = getNewElementPath() + refactoredElement.getName();
				}
			}
			EList<PropertyDefinition> properties = event.getProperties();
			for (PropertyDefinition propDef : properties) {
				String conceptPath = propDef.getConceptTypePath();
				String ownerPath = propDef.getOwnerPath();
				if (isFolderRefactor()) {
					IFolder folder = (IFolder) getResource();
					if (IndexUtils.startsWithPath(ownerPath, folder)) {
						String newPath = getNewPath(ownerPath, folder);
						propDef.setOwnerPath(newPath);
						changed = true;
					}
					if (IndexUtils.startsWithPath(conceptPath, folder)) {
						String newPath = getNewPath(conceptPath, folder);
						propDef.setConceptTypePath(newPath);
						changed = true;
					}
				} else {
					if (ownerPath != null && ownerPath.equals(oldFullPath)) {
						propDef.setOwnerPath(newFullPath);
						changed = true;
					}
					if (conceptPath != null && conceptPath.equals(oldFullPath)) {
						propDef.setConceptTypePath(newFullPath);
						changed = true;
					}
				}
			}
		} 

		if (elementToRefactor instanceof PropertyDefinition) {
			if (isPreProcess) {
				PropertyDefinition propDef = (PropertyDefinition) elementToRefactor;
				EList<PropertyDefinition> properties = event.getProperties();
				for (PropertyDefinition propertyDef : properties) {
					if (propertyDef.getName().equals(propDef.getName())) {
						propertyDef.setName(getNewElementName());
					}
				}
			}
		} 

		if (elementToRefactor instanceof Event || isFolderRefactor()) {
			String newFullPath = "";
			String oldFullPath = "";
			if (elementToRefactor instanceof Event) {
				Event refactoredEvent = (Event) elementToRefactor;
				oldFullPath = refactoredEvent.getFullPath();
				if (isRenameRefactor()) {
					newFullPath = refactoredEvent.getFolder() + getNewElementName();
				} else if (isMoveRefactor()){
					newFullPath = getNewElementPath() + refactoredEvent.getName();
				}
			}
			if (isFolderRefactor()) {
				IFolder folder = (IFolder) getResource();
				if (IndexUtils.startsWithPath(event.getSuperEventPath(), folder)) {
					String newPath = getNewPath(event.getSuperEventPath(), folder);
					event.setSuperEventPath(newPath);
					changed = true;
				}
			} else {
				if (event.getSuperEventPath() != null && event.getSuperEventPath().equalsIgnoreCase(oldFullPath)) {
					event.setSuperEventPath(newFullPath);
					changed = true;
				}
			}
			
			EList<PropertyDefinition> properties = event.getProperties();
			for (PropertyDefinition propDef : properties) {
				String eventPath = event.getFullPath();
				String ownerPath = propDef.getOwnerPath();
				if (isFolderRefactor()) {
					IFolder folder = (IFolder) getResource();
					if (IndexUtils.startsWithPath(ownerPath, folder)) {
						String newPath = getNewPath(ownerPath, folder);
						propDef.setOwnerPath(newPath);
						changed = true;
					}
					if (IndexUtils.startsWithPath(eventPath, folder)) {
						String newPath = getNewPath(eventPath, folder);
						propDef.setConceptTypePath(newPath);
						changed = true;
					}
				} else {
					if (ownerPath != null && ownerPath.equals(oldFullPath)) {
						propDef.setOwnerPath(newFullPath);
						changed = true;
					}
					if (eventPath != null && eventPath.equals(oldFullPath)) {
						propDef.setConceptTypePath(newFullPath);
						changed = true;
					}
				}
			}
			
//			EList<String> subEventsPath = event.getSubEventsPath();
//			for (int i = 0; i < subEventsPath.size(); i++) {
//				String path = subEventsPath.get(i);
//				if (isFolderRefactor()) {
//					IFolder folder = (IFolder) getResource();
//					if (IndexUtils.startsWithPath(path, folder)) {
//						String newPath = getNewPath(path, folder);
//						subEventsPath.remove(i);
//						subEventsPath.add(i, newPath);
//						changed = true;
//					}
//				} else {
//					if (path.equals(oldFullPath)) {
//						subEventsPath.remove(i);
//						subEventsPath.add(i, newFullPath);
//						changed = true;
//					}
//				}
//			}
		} 
		
//		if (elementToRefactor instanceof Channel) {
//			if (isDeleteRefactor()) {
//				((SimpleEvent)event).setChannelURI(null);
//				((SimpleEvent)event).setDestinationName(null);
//				changed = true;
//			}
//		}

		if (elementToRefactor instanceof Destination) {
			if (event instanceof SimpleEvent) {
				Destination dest = (Destination) elementToRefactor;
				String destName = ((SimpleEvent) event).getDestinationName();
				Channel channel = (Channel) dest.eContainer().eContainer();
				//TODO:Destination delete refactoring 
				if (isDeleteRefactor()) {
					((SimpleEvent)event).setChannelURI(null);
					((SimpleEvent)event).setDestinationName(null);
				}else{
					if (dest.getName().equals(destName) && channel.getFullPath().equals(((SimpleEvent)event).getChannelURI())) {
						((SimpleEvent)event).setDestinationName(getNewElementName());
						changed = true;
					}
				}
			}
		} 

		if (elementToRefactor instanceof Channel || isFolderRefactor()) {
			if (event instanceof SimpleEvent) {
				String newFullPath = null;
				if (isFolderRefactor()) {
					IFolder folder = (IFolder) getResource();
					if (IndexUtils.startsWithPath(((SimpleEvent)event).getChannelURI(), folder)) {
						newFullPath = getNewPath(((SimpleEvent)event).getChannelURI(), folder);
					}
				} else {
					Channel channel = (Channel) elementToRefactor;
					if (channel.getFullPath().equals(((SimpleEvent)event).getChannelURI())) {
						if (isRenameRefactor()) {
							newFullPath = channel.getFolder() + getNewElementName();
						} else if (isMoveRefactor()){
							newFullPath = getNewElementPath() + channel.getName();
						}else if (isDeleteRefactor()) {
							((SimpleEvent)event).setChannelURI(null);
							((SimpleEvent)event).setDestinationName(null);
							changed = true;
						}
					}
				}
				if (newFullPath != null && !newFullPath.equals(((SimpleEvent)event).getChannelURI())) {
					((SimpleEvent)event).setChannelURI(newFullPath);
					changed = true;
				}
			}
		} 

		if (elementToRefactor instanceof Domain || isFolderRefactor()) {
			String newFullPath = "";
			if (elementToRefactor instanceof Domain) {
				if (isRenameRefactor()) {
					newFullPath = ((Domain)elementToRefactor).getFolder() + getNewElementName();
				} else if (isMoveRefactor()) {
					newFullPath = getNewElementPath() + ((Domain)elementToRefactor).getName();
				}
			}
			for(DomainInstance instance:event.getAllDomainInstances()){
				PropertyDefinition propertyDefinition =  instance.getOwnerProperty();
				EList<DomainInstance> domainInstnces = propertyDefinition.getDomainInstances();
				List<DomainInstance> disToRemove = new ArrayList<DomainInstance>();
				for (int i = 0; i < domainInstnces.size(); i++) {
					DomainInstance di = domainInstnces.get(i);
					if (isFolderRefactor()) {
						IFolder folder = (IFolder) getResource();
						if (IndexUtils.startsWithPath(di.getResourcePath(), folder)) {
							if (isDeleteRefactor()) {
								disToRemove.add(di);
							} else {
								String newPath = getNewPath(di.getResourcePath(), folder);
								di.setResourcePath(newPath);
								changed = true;
							}
						}
					} else {
						if (di.getResourcePath().equals(((Domain) elementToRefactor).getFullPath())) {
							if (isDeleteRefactor()) {
								disToRemove.add(di);
							} else {
								di.setResourcePath(newFullPath);
								changed = true;
							}
						}
					}
				}
				for (DomainInstance domainInstance : disToRemove) {
					domainInstnces.remove(domainInstance);
					changed = true;
				}
			}
		}

		if (elementToRefactor instanceof Entity || isFolderRefactor()) {
			Object obj = elementToRefactor;
//			if (isFolderRefactor()) {
//				obj = getResource();
//			}
			if (new CompilableRenameParticipant(isMoveRefactor(), getOldElementName(), getOldElementPath()).processRule(event.getExpiryAction(), obj, getNewElementPath(), getNewElementName())) {
				changed = true;
			}
			// update declaration in expiry action
			if (event.getExpiryAction() != null) {
				Rule expiryAction = event.getExpiryAction();
				String expActionFolder = expiryAction.getFolder();
				if (isFolderRefactor()) {
					IFolder folder = (IFolder) getResource();
					if (IndexUtils.startsWithPath(expActionFolder, folder)) {
						String newPath = getNewPath(expActionFolder, folder);
						expiryAction.setFolder(newPath);
						changed = true;
					}
				} else if (elementToRefactor instanceof Event) {
					Event ev = (Event) elementToRefactor;
					String newFullPath = "";
					if (isRenameRefactor()) {
						newFullPath = ((Entity) elementToRefactor).getFolder() + getNewElementName();
					} else if (isMoveRefactor()){
						newFullPath = getNewElementPath() + ((Entity) elementToRefactor).getName();
					}
					if (expActionFolder != null && expActionFolder.equals(ev.getFullPath())) {
						expiryAction.setFolder(newFullPath);
						changed = true;
					}
				}

				Symbols symbols = event.getExpiryAction().getSymbols();
				if (symbols.getSymbolList().size() > 0) {
					Symbol symbol = symbols.getSymbolList().get(0);
					String symType = symbol.getType();
					if (isFolderRefactor()) {
						IFolder folder = (IFolder) getResource();
						if (IndexUtils.startsWithPath(symType, folder)) {
							String newPath = getNewPath(symType, folder);
							symbol.setType(newPath);
							changed = true;
						}
					} else if (elementToRefactor instanceof Event) {
						Event ev = (Event) elementToRefactor;
						if (symType != null && symType.equals(ev.getFullPath())) {
							symbol.setType(getNewElementPath());
							changed = true;
						}
					}
				}
			}

		}
		
		if (changed) {
			Change change = createTextFileChange(file, event);
			compositeChange.add(change);
		}
		return event;
	}
	
	@Override
	protected void processChildren(String newName, EObject eobj, String newProjName) {
		super.processChildren(newName, eobj, newProjName);
		if(eobj.eClass().getEStructuralFeature("ownerPath") != null) {
			if (eobj.eIsSet(eobj.eClass().getEStructuralFeature("ownerPath"))) {
				String ownerPath = ((Event)((PropertyDefinition)eobj).eContainer()).getFolder()+((Event)((PropertyDefinition)eobj).eContainer()).getName();
				((PropertyDefinition) eobj).setOwnerPath(ownerPath);
			}
		}
	}

	@Override
	protected void pasteEntity(String newName, Entity entity, IResource source,
			IContainer target, boolean overwrite) throws CoreException {
		if (newName == null) {
			return;
		}
		int idx = newName.lastIndexOf('.');
		String newEntName = newName;
		if (idx > 0) {
			newEntName = newName.substring(0, idx);
		}
		String newPath = target instanceof IProject ? "/" : "/"+target.getFullPath().removeFirstSegments(1).toString()+"/";
		String oldPath = source instanceof IProject ? "/" : "/"+source.getFullPath().removeFirstSegments(1).removeFileExtension().toString();
		if (entity instanceof Event) {
			Event ev = (Event) entity;
			// update declaration in expiry action
			if (ev.getExpiryAction() != null) {
				Symbols symbols = ev.getExpiryAction().getSymbols();
				if (symbols.getSymbolList().size() > 0) {
					Symbol symbol = symbols.getSymbolList().get(0);
					if (symbol.getType().equals(oldPath)) {
						symbol.setType(newPath+newEntName);
					}
				}
			}
		}
		super.pasteEntity(newName, entity, source, target, overwrite);
	}
	
	
}
