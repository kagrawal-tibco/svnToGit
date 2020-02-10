package com.tibco.cep.studio.ui.editors.domain;

import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.checkDomainEditorReference;
import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.checkEditorReference;
import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.refreshPropertyTable;
import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.runDomainResourceSelector;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.domain.DomainInstance;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.AbstractSaveableEntityEditorPart;
import com.tibco.cep.studio.ui.StudioNavigatorNode;
import com.tibco.cep.studio.ui.editors.utils.EditorUtils;
import com.tibco.cep.studio.ui.navigator.model.EventPropertyNode;
import com.tibco.cep.studio.ui.navigator.model.PropertyNode;

/**
 * 
 * @author sasahoo
 *
 */
public class AssociateDomainsAction extends AssociateDomains implements IWorkbenchWindowActionDelegate, IObjectActionDelegate {

	private ISelection _selection;
	private IStructuredSelection strSelection;
	private IProject project;	
	private IWorkbenchPage page;
	
    
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		try {
			if (_selection != null && _selection instanceof IStructuredSelection) {
				strSelection = (IStructuredSelection)_selection;
				Object firstElement = strSelection.getFirstElement();
				
				if (firstElement instanceof StudioNavigatorNode && ((StudioNavigatorNode) firstElement).isSharedElement()) {
					return;
				} 
				
				if (firstElement instanceof PropertyNode) {
					PropertyNode propertyNode = (PropertyNode)firstElement;
					propertyDefinition = (PropertyDefinition)propertyNode.getEntity();
					if (propertyDefinition.getType() == PROPERTY_TYPES.get(6) || 
							propertyDefinition.getType() == PROPERTY_TYPES.get(7)) {
						return;
					}
				} else if (firstElement instanceof EventPropertyNode) {
					EventPropertyNode propertyNode = (EventPropertyNode)firstElement;
					propertyDefinition = propertyNode.getUserProperty();
				}
				final Entity entity = (Entity)propertyDefinition.eContainer();
				IFile file = IndexUtils.getFile(entity.getOwnerProjectName(), entity);
				project = file.getProject();
				
				AbstractSaveableEntityEditorPart editor = EditorUtils.getEditorReference(file);
				if (editor != null && editor.isDirty()) {
					boolean save = page.saveEditor(editor, true);
					if(!save){
						return;
					}
				}
				
								
				Set<String> modifiedSet = new HashSet<String>();
				for (DomainInstance instance : propertyDefinition.getDomainInstances()) {
					modifiedSet.add(instance.getResourcePath());
				}
				
				editorOpen = false;
				
				checkEditorReference(file, entity, propertyDefinition.getName(), this);
								
				boolean isOkPressed = runDomainResourceSelector(propertyDefinition, 
									                            page, 
									                            project.getName(), 
									                            false, 
									                            editorOpen);
				if(isOkPressed){
					if (editorOpen) {
						editor.modified();
						refreshPropertyTable(editor, propertyDefinition, entity, index);
						page.saveEditor(editor, false);
					}else{
						for(DomainInstance instance: propertyDefinition.getDomainInstances()){
							modifiedSet.add(instance.getResourcePath());
						}
						if (file != null) {
							try {
								file.refreshLocal(IFile.DEPTH_INFINITE, new NullProgressMonitor());
							} catch (CoreException e) {
								e.printStackTrace();
							}
						}
						checkDomainEditorReference(modifiedSet, page);
					}
				}
			
			} else {
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void selectionChanged(IAction action, ISelection selection) {
		_selection = selection;
	}

	public void init(IWorkbenchWindow window) {
		// TODO Auto-generated method stub
	}
	
	public void dispose() {
		// TODO Auto-generated method stub
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// TODO Auto-generated method stub
	}
}