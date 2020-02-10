package com.tibco.cep.studio.ui.navigator.dnd;

import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.addDomainInstances;
import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.checkDomainEditorReference;
import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.checkEditorReference;
import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.refreshPropertyTable;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.domain.Domain;
import com.tibco.cep.designtime.core.model.domain.DomainInstance;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.ui.editors.domain.AssociateDomains;
import com.tibco.cep.studio.ui.navigator.model.EventPropertyNode;
import com.tibco.cep.studio.ui.navigator.model.PropertyNode;
import com.tibco.cep.studio.ui.navigator.view.ProjectExplorer;

/**
 * 
 * @author sasahoo
 *
 */
public class DomainDropHandler extends AssociateDomains{

	private Object target;
	private DropTargetEvent dropTargetEvent;

	/**
	 * @param dropTargetEvent
	 * @param target
	 */
	public DomainDropHandler(DropTargetEvent dropTargetEvent, Object target) {
		this.dropTargetEvent = dropTargetEvent;
		this.target = target;
	}

	/**
	 * @return
	 */
	public IStatus handleDomainDrop() {
		if (target instanceof PropertyNode) {
			PropertyNode propertyNode = (PropertyNode)target;
			propertyDefinition = (PropertyDefinition)propertyNode.getEntity();
			if(propertyDefinition.getType() == PROPERTY_TYPES.CONCEPT || 
					propertyDefinition.getType() == PROPERTY_TYPES.CONCEPT_REFERENCE) {
				return null;
			}
		} else if (target instanceof EventPropertyNode) {
			EventPropertyNode propertyNode = (EventPropertyNode)target;
			propertyDefinition = propertyNode.getUserProperty();
		}
		if (!(dropTargetEvent.data instanceof IStructuredSelection)) return null;
		IStructuredSelection selection = (IStructuredSelection)dropTargetEvent.data;
		if(selection.isEmpty())return null;

		int oldDMInstancesSize = propertyDefinition.getDomainInstances().size();
		
		

		final Entity entity = (Entity)propertyDefinition.eContainer();
		String projectName = entity.getOwnerProjectName();
		
		
		IFile targetFile = IndexUtils.getFile(projectName, entity);
		
		
		Set<String> modifiedSet = new HashSet<String>();
		for (DomainInstance instance : propertyDefinition.getDomainInstances()) {
			modifiedSet.add(instance.getResourcePath());
		}
		
		editorOpen = false;
		
		checkEditorReference(targetFile,entity, propertyDefinition.getName(), this);

		Object[] objs = selection.toArray();
		//Flush all related index entries
//		removeIndexEntry(rootIndex, 
//		                 targetFile, 
//		                 propertyDefinition,
//		                 ELEMENT_TYPES.DOMAIN_INSTANCE);
		for (Object obj : objs) {
			if (obj instanceof IFile) {
				IFile file = (IFile)obj;
				if (!file.getFileExtension().equals("domain")) {
					return null;	
				} else {
					Domain domain = IndexUtils.getDomain(file.getProject().getName(), IndexUtils.getFullPath(file));
					if (propertyDefinition.getType().getLiteral().equals(domain.getDataType().getLiteral())) {
						addDomainInstances(propertyDefinition, IndexUtils.getFullPath(domain));
					}
				}
			}
		}
		if (diff(oldDMInstancesSize, propertyDefinition.getDomainInstances().size())) {
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			try {
				if (editorOpen) {
					editor.modified();
					refreshPropertyTable(editor, propertyDefinition, entity, index);
				} else {
					ModelUtils.saveEObject(propertyDefinition.eContainer());
//					((Entity)propertyDefinition.eContainer()).eResource().save(ModelUtils.getPersistenceOptions());
					if (targetFile != null) {
						try {
							targetFile.refreshLocal(0, null);
						} catch (CoreException e) {
							e.printStackTrace();
						}
					}
					//Refreshing the Project Explorer
					IViewPart view = page.findView(ProjectExplorer.ID);
					if(view != null) {
						((ProjectExplorer)view).getCommonViewer().refresh();
					}
				}
				
				for(DomainInstance instance: propertyDefinition.getDomainInstances()){
					modifiedSet.add(instance.getResourcePath());
				}
				
				checkDomainEditorReference(modifiedSet, page);
			
			} catch (IOException e) {
				e.printStackTrace();
			}
			return Status.OK_STATUS;
		}
		return null;
	}

	/**
	 * @param oldProp
	 * @param newProp
	 * @return
	 */
	private boolean diff(int oldsize, int newsize) {
		if (newsize > oldsize) {
			return true;
		}
		return false;
	}
}
