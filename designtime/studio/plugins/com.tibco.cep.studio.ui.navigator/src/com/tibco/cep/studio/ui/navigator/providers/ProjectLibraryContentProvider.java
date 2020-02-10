package com.tibco.cep.studio.ui.navigator.providers;
/**
 * @author rmishra
 */
import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.studio.core.SharedElementRootNode;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ElementContainer;
import com.tibco.cep.studio.core.index.model.SharedEntityElement;

public class ProjectLibraryContentProvider implements ITreeContentProvider {
	
//	public class RootNode {
//
//		private EList<DesignerProject> fChildren;
//
//		public RootNode(EList<DesignerProject> children) {
//			this.fChildren = children;
//		}
//
//		public EList<DesignerProject> getChildren() {
//			return fChildren;
//		}
//		
//	}
	
	private static final EntityContentProvider fConceptContentProvider = new ConceptContentProvider();
	private static final EventContentProvider fEventContentProvider = new EventContentProvider();
	private static final EntityContentProvider fChannelContentProvider = new ChannelContentProvider();
	private static final EntityContentProvider fSMContentProvider = new StateMachineContentProvider();
	
	public ProjectLibraryContentProvider(){
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof IProject && ((IProject)parentElement).isAccessible()) {
			DesignerProject index = StudioCorePlugin.getDesignerModelManager().getIndex((IProject) parentElement);
			if(index != null){
				EList<DesignerProject> referencedProjects = index.getReferencedProjects();
				if (referencedProjects.size() > 0) {
					return new Object[] { new SharedElementRootNode(referencedProjects) };
				}
			}
		} else if (parentElement instanceof SharedElementRootNode) {
			return ((SharedElementRootNode) parentElement).getChildren().toArray();
		} else if (parentElement instanceof ElementContainer){
			EList<DesignerElement> entries = ((ElementContainer) parentElement).getEntries();
			return entries.toArray();
		} else if (parentElement instanceof SharedEntityElement) {
			SharedEntityElement sharedElement = (SharedEntityElement) parentElement;
			return getSharedElementChildren(sharedElement);
		}
		return new Object[0];
	}

	private Object[] getSharedElementChildren(SharedEntityElement sharedElement) {
		Entity entity = sharedElement.getEntity();
		if (entity instanceof Concept) {
			return fConceptContentProvider.getEntityChildren(entity, true);
		} else if (entity instanceof Event) {
			return fEventContentProvider.getEntityChildren(entity, true);
		} else if (entity instanceof Channel) {
			return fChannelContentProvider.getEntityChildren(entity, true);
		} else if (entity instanceof StateMachine) {
			return fSMContentProvider.getEntityChildren(entity, true);
		}
		return new Object[0];
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof DesignerProject) {
			String archivePath = ((DesignerProject) element).getArchivePath();
			if (archivePath == null) {
				return false;
			}
			if (archivePath.endsWith(".projlib")) {
				File archiveFile = new File(archivePath);
				if (!archiveFile.exists()) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		
		return getChildren(inputElement);
		
	}

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

}
