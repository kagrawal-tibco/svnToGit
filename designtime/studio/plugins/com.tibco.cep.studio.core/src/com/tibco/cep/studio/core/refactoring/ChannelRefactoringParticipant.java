package com.tibco.cep.studio.core.refactoring;

import static com.tibco.cep.studio.core.util.CommonUtil.replace;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
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
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.designtime.core.model.service.channel.DriverConfig;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.search.ChannelSearchParticipant;
import com.tibco.cep.studio.core.search.EntitySearchParticipant;

public class ChannelRefactoringParticipant extends EntityRefactoringParticipant<Channel> {

	private static final String[] SUPPORTED_EXTENSIONS = new String[] { CommonIndexUtils.CHANNEL_EXTENSION };

	public ChannelRefactoringParticipant() {
		super();
	}

	@Override
	protected EntitySearchParticipant getSearchParticipant() {
		return new ChannelSearchParticipant();
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
		if (element instanceof Destination) {
			EObject container = ((Destination) element).eContainer();
			if (container instanceof DriverConfig) {
				EList<Destination> destinations = ((DriverConfig) container).getDestinations();
				for (int i=0; i<destinations.size(); i++) {
					if (newName.equals(destinations.get(i).getName())) {
						status.addFatalError("A destination already exists with the name '"+newName+"'");
						return;
					}
				}
			}
		} else {
			super.checkForDuplicateElement(status, newName, parentResource);
		}
	}

	@Override
	protected Channel preProcessEntityChange(Channel refactorParticipant) {
		if (!(refactorParticipant instanceof Channel)) {
			return super.preProcessEntityChange(refactorParticipant);
		}
		
		CompositeChange compositeChange = new CompositeChange("");
		try {
			return processChannel(refactorParticipant.getOwnerProjectName(), compositeChange, refactorParticipant, getElementToRefactor(), true);
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return refactorParticipant;
	}

	@Override
	public Change processEntity(Object elementToRefactor, String projectName, IProgressMonitor pm, boolean preChange) throws CoreException,
			OperationCanceledException {
//		if (isDeleteRefactor()) {
//			return new NullChange();
//		}
		// look through all concepts and make appropriate changes
		if (elementToRefactor instanceof EntityElement) {
			elementToRefactor = ((EntityElement) elementToRefactor).getEntity();
		}
		if (!shouldUpdateReferences()) {
			System.out.println("not updating references");
			return null;
		}
		CompositeChange compositeChange = new CompositeChange("Channel changes:");
		List<Entity> allChannel = IndexUtils.getAllEntities(projectName, new ELEMENT_TYPES[] { ELEMENT_TYPES.CHANNEL });
		for (Entity entity : allChannel) {
			if (entity == elementToRefactor) {
				// already processed in the pre-change
				continue;
			}
			processChannel(projectName, compositeChange, (Channel) entity, elementToRefactor, preChange);
		}
		if (compositeChange.getChildren() != null && compositeChange.getChildren().length > 0) {
			return compositeChange;
		}
		return null;
	}

	private Channel processChannel(String projectName,
			CompositeChange compositeChange, Channel channel, Object elementToRefactor, boolean isPreProcess) throws CoreException {
		if (isSharedElement(channel)) {
			return channel;
		}
//		must be sure to copy the concept before making changes, otherwise canceling the wizard will keep the changes made and corrupt the concepts
		IFile file = IndexUtils.getFile(projectName, channel);
		channel = (Channel) EcoreUtil.copy(channel);
		boolean changed = false;
		if (isContainedDelete(file)) {
			return channel;
		}
		if (isProjectRefactor()) {
			processChildren(projectName, channel, getNewElementName());
			changed = true;
		}
		if (elementToRefactor instanceof EntityElement) {
			elementToRefactor = ((EntityElement) elementToRefactor).getEntity();
		}
		if (processFolder(channel)) {
			changed = true;
		}
		if (elementToRefactor instanceof Event || isFolderRefactor()) {
			EList<Destination> destinations = channel.getDriver().getDestinations();
			for (Destination destination : destinations) {
				boolean needsChange = false;
				String newEventURI = destination.getEventURI();
				if (elementToRefactor instanceof Event) {
					Event refactoredElement = (Event) elementToRefactor;
					if (refactoredElement.getFullPath().equals(destination.getEventURI())) {
						if (isRenameRefactor()) {
							newEventURI = refactoredElement.getFolder()+getNewElementName();
							needsChange = true;
						} else if (isMoveRefactor()) {
							newEventURI = getNewElementPath()+refactoredElement.getName();
							needsChange = true;
						}else if(isDeleteRefactor()){
							newEventURI = null;
							needsChange = true;
						}
					}
				} else if (isFolderRefactor()) {
					IFolder folder = (IFolder) getResource();
					if (IndexUtils.startsWithPath(destination.getEventURI(), folder)) {
						newEventURI = getNewPath(destination.getEventURI(), folder);
						needsChange = true;
					}
				}
				if (needsChange) {
					destination.setEventURI(newEventURI);
					changed = true;
				}
			}
		} 

		if (elementToRefactor instanceof Destination) {
			if (isPreProcess) {
				EList<Destination> destinations = channel.getDriver().getDestinations();
				Destination dest = (Destination) elementToRefactor;
				for (Destination destination : destinations) {
					if (destination.getName().equals(dest.getName())) {
						destination.setName(getNewElementName());
					}
				}
			}
		}
        //Shared Resource Refactor Notification		
		if(isSharedResourceRefactor()){
			IFile sfile = (IFile)getResource();
			String oldSharedResourcePath = sfile.getFullPath().makeRelative().toOSString();
			oldSharedResourcePath = oldSharedResourcePath.substring(oldSharedResourcePath.indexOf(projectName)+projectName.length());
			oldSharedResourcePath = replace(oldSharedResourcePath,"\\","/");
			String newSharedResourcePath = null;
			if(isMoveRefactor()){
				newSharedResourcePath = getNewElementPath() + getNewElementName()+ "."+ sfile.getFileExtension();
			}else if (isRenameRefactor()) {
				newSharedResourcePath = sfile.getParent().getFullPath().makeRelative().toOSString();
				newSharedResourcePath = newSharedResourcePath.substring(newSharedResourcePath.indexOf(projectName)+projectName.length());
				newSharedResourcePath = newSharedResourcePath + "/" + getNewElementName()+ "."+ sfile.getFileExtension();
				newSharedResourcePath = replace(newSharedResourcePath,"\\","/");
			}else if (isDeleteRefactor()) {
				newSharedResourcePath =  null;
			}
			if(channel.getDriver().getReference() != null && 
					channel.getDriver().getReference().equals(oldSharedResourcePath)){
//				if(newSharedResourcePath != null){
					channel.getDriver().setReference(newSharedResourcePath);
					changed = true;
//				}
			}
		}
		if (changed) {
			Change change = createTextFileChange(file, channel);
			compositeChange.add(change);
		}
		return channel;
	}

	protected boolean isSharedResourceRefactor() {

		//All supported Shared Resources Extensions
		Set<String> extn = new HashSet<String>();
		extn.add("sharedjmscon");
		extn.add("sharedjndiconfig");
		extn.add("rvtransport");
		extn.add("sharedhttp");
		extn.add("sharedascon");
		extn.add("hawk");
		extn.add("sharedmqttcon");

		if(getResource() instanceof IFile){
			IFile file = (IFile)getResource();
			if(extn.contains(file.getFileExtension())){
				return true;
			}
		}
		return false;
	}
}
