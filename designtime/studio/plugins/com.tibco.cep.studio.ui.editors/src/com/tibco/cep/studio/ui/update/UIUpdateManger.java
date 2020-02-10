/**
 * 
 */
package com.tibco.cep.studio.ui.update;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.domain.DomainInstance;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.ElementContainer;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.update.IStudioElementDelta;
import com.tibco.cep.studio.core.index.update.IStudioModelChangedListener;
import com.tibco.cep.studio.core.index.update.StudioElementDelta;
import com.tibco.cep.studio.core.index.update.StudioModelChangedEvent;
import com.tibco.cep.studio.core.index.update.StudioModelDelta;
import com.tibco.cep.studio.core.index.update.StudioProjectDelta;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.ui.AbstractSaveableEntityEditorPart;
import com.tibco.cep.studio.ui.editors.concepts.ConceptFormEditor;
import com.tibco.cep.studio.ui.editors.utils.EditorUtils;
import com.tibco.cep.studio.ui.navigator.view.ProjectExplorer;

/**
 * @author rmishra
 *
 */
public class UIUpdateManger implements IStudioModelChangedListener {
	
	public UIUpdateManger(){
	
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.index.update.IStudioModelChangedListener#modelChanged(com.tibco.cep.studio.core.index.update.StudioModelChangedEvent)
	 */
	@Override
	public void modelChanged(StudioModelChangedEvent event) {
		StudioModelDelta dmd = event.getDelta();
		if (dmd == null) return;
		List<StudioProjectDelta> dpdList = dmd.getChangedProjects();
		for (StudioProjectDelta dpd : dpdList){
			int type = dpd.getType();			
			DesignerProject affectedDesignerProject = dpd.getChangedProject();
			if (affectedDesignerProject == null) return;
			String projectName = affectedDesignerProject.getName();
			if (projectName == null) return;
			if (StudioProjectDelta.REMOVED == type){
				// if project is removed then close all editors which has the resources corresponding to this project	
				EditorUtils.closeEditorsForProject(projectName);
			} 
//			else if (StudioProjectDelta.CHANGED == type) {
//				// if project is changed then find out the changed elements
//				// if changed elements are referred by some other resources which are not already deleted
//				List<DesignerElement> elementsToRefresh = new ArrayList<DesignerElement>();
//				collectElementsToRefresh(dpd, elementsToRefresh);
//				for (DesignerElement designerElement : elementsToRefresh) {
//					switch (((IStudioElementDelta) designerElement).getType()) {
//						case IStudioElementDelta.ADDED:
//							processAddedElement((IStudioElementDelta) designerElement , projectName);
//							break;
//
//						case IStudioElementDelta.REMOVED:
//							processRemovedElement((IStudioElementDelta) designerElement , projectName);
//							break;
//
//						case IStudioElementDelta.CHANGED:
//							processChangedElement((IStudioElementDelta) designerElement , projectName);
//							break;
//
//						default:
//							break;
//					}				
//				
//				} 
//			
//			} else{
//			
//			}
		}

	}
		/**
		 * collect all the elements which are modified  
		 * @param delta
		 * @param elementsToRefresh
		 */
	protected void collectElementsToRefresh(ElementContainer delta, List<DesignerElement> elementsToRefresh) {
		EList<DesignerElement> entries = delta.getEntries();
		for (DesignerElement designerElement : entries) {
			if (designerElement instanceof ElementContainer) {
				collectElementsToRefresh((ElementContainer) designerElement, elementsToRefresh);
			} else if (designerElement instanceof StudioElementDelta) {
				elementsToRefresh.add(designerElement);
			}
		}
	}
	
	protected void processAddedElement(IStudioElementDelta delta , String projectName){
		// do nothing
		
	}
	
	protected void processRemovedElement(IStudioElementDelta delta , String projectName){
		// remove the opened editors if it's an editor input for any editor
		if (delta == null || projectName == null) return;
		DesignerElement de = delta.getAffectedChild();
		if (de == null) return;
		if (de instanceof EntityElement){
			EntityElement ee = (EntityElement)de;
			String folder = ee.getFolder();
			String name = ee.getName();
			String ext = IndexUtils.getFileExtension(ee.getElementType());
			if (ext == null || ext.trim().length() == 0) return;
			// replace all '/' Platform specific path separator
			folder = folder.replace('/', File.separatorChar);
			if (!folder.startsWith(File.separator)){
				folder = File.separator + folder;
			}
			if (!folder.endsWith(File.separator)){
				folder = folder + File.separator ;
			}
			StringBuilder sb = new StringBuilder("");
			sb.append(File.separator);
			sb.append(projectName);
			sb.append(folder);
			sb.append(name);
			sb.append(".");
			sb.append(ext);
			String resPath = sb.toString();
			// Close any open editor which has this resource as input Editor 
			EditorUtils.closeEditorForResource(resPath);
			// removed resource is a StateMachine Resource then check if any concept has reference to it 
			if (IndexUtils.getFileExtension(ELEMENT_TYPES.STATE_MACHINE).equals(ext)){
				// get all concepts from this Project and check for dangling references	
				//removeStateMachineAssociation(projectName , (StateMachineElement)ee);

				
			} else if (IndexUtils.getFileExtension(ELEMENT_TYPES.DOMAIN).equals(ext)){
				//String domainPath = ee.getFolder() + ee.getName();
				//removeDomainAssociation(domainPath, projectName);
			}
		}
		
		// if element is removed and that is type of first citizen elements and that is associated with some other
		// elements
		
	}
	protected void processChangedElement(IStudioElementDelta delta , String projectName){
		// do nothing
		
	}
	/**
	 * it removes the state machine association from Concepts
	 * @param projectName
	 * @param sm
	 */
//	protected void removeStateMachineAssociation(final String projectName , final StateMachineElement sm){
//		if (projectName == null || sm == null) return;
//		List<DesignerElement> conceptElementList = IndexUtils.getAllElements(projectName, ELEMENT_TYPES.CONCEPT,false);
//		for (DesignerElement dge : conceptElementList){
//			if (dge instanceof EntityElement){
//				EntityElement ene = (EntityElement)dge;
//				Entity entity = ene.getEntity();
//				if (entity instanceof Concept){
//					Concept concept = (Concept)entity;
//					List<StateMachine> smInstances = concept.getStateMachines();
//					List<StateMachine> removedSMInstances = new ArrayList<StateMachine>();
//					String smPath = sm.getFolder() + sm.getName();
//					for (StateMachine si : smInstances){
//						String res = IndexUtils.getFullPath(si);						
//						if (smPath.equals(res)){
//							removedSMInstances.add(si);
//						}								
//					}
//					if (removedSMInstances.size() > 0){
//						smInstances.removeAll(removedSMInstances);
//						// make any opened concept editor dirty
//						updateEntityEditor(entity ,smPath ,concept.getFullPath(),projectName,ELEMENT_TYPES.STATE_MACHINE);
//					}
//					
//	
//				}
//			}
//		}
//		// remove SMInstances from DesignerProject also
//		DesignerProject index = IndexUtils.getIndex(projectName);
//		if (index != null){
//			List<InstanceElement<SMInstance>> danglingSMInstances = new ArrayList<InstanceElement<SMInstance>>();
//			for (InstanceElement<SMInstance> ie : index.getStateMachineInstanceElements()){
//				SMInstance smi = ie.getInstance();
//				if (smi == null) continue;
//				danglingSMInstances.add(ie);
//			}
//			// remove all dangling references
//			index.getStateMachineInstanceElements().removeAll(danglingSMInstances);
//		}
//	}
	/**
	 * makes the editor dirty if any resource is opened in editor and some references  are deleted from 
	 * Project . If resource is not open then Resource is saved
	 * @param relResPath
	 * @param projName
	 * @param extension
	 */
	protected void updateEntityEditor(final Entity entity ,final String danglingRefPath , final String relResPath , final String projName , final ELEMENT_TYPES danglingResType){
		
		if (relResPath == null || projName == null ||  danglingRefPath == null || danglingResType == null ||
				   		relResPath.trim().length() == 0 || projName.trim().length() == 0 ) return;
		String extension = IndexUtils.getFileExtension(entity);
		final IPath path = Path.fromOSString("/"+projName + relResPath + "." + extension);
		Display.getDefault().asyncExec(new Runnable(){
			@Override
			public void run() {
				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				if (page == null) return;
				try {
				boolean propChangeEventFired = false;
				for(final IEditorReference reference:page.getEditorReferences()){					
					if(reference.getEditorInput() instanceof FileEditorInput){
						FileEditorInput fileEditorInput = (FileEditorInput)reference.getEditorInput();
						if (fileEditorInput == null) continue;
						IFile file = fileEditorInput.getFile();
						if (file == null) continue;
						if(path.equals(file.getFullPath())){				
							IEditorPart  editor = reference.getEditor(true);
							if (editor instanceof AbstractSaveableEntityEditorPart){								
								propChangeEventFired = true;
								Entity editorEntity = ((AbstractSaveableEntityEditorPart)editor).getEntity();
								if (editorEntity instanceof Concept){
									Concept editorConcept = (Concept)editorEntity;
									if (ELEMENT_TYPES.STATE_MACHINE == danglingResType){										
										//remove all SM instances from the concept which is real input to the Concept Editor
										List<StateMachine> smInstances = editorConcept.getStateMachines();
										List<StateMachine> removedSMInstances = new ArrayList<StateMachine>();									
										for (StateMachine si : smInstances){
											String res = IndexUtils.getFullPath(si);						
											if (danglingRefPath.equals(res)){
												removedSMInstances.add(si);
											}								
										}
										if (removedSMInstances.size() > 0){
											smInstances.removeAll(removedSMInstances);						
										}
										EditorUtils.resetStatemachineCombo(((ConceptFormEditor)editor).getConceptFromDesignViewer().getStateMachineCombo(), 
												(Concept)editorEntity, (AbstractSaveableEntityEditorPart)editor);
										((ConceptFormEditor)editor).setSmAssociationFlag(true);
									} else if (ELEMENT_TYPES.DOMAIN == danglingResType){
										// remove all domain instances from concept property definitions
										List<PropertyDefinition> propDefList = editorConcept.getProperties();
										removeDanglingDomainReferences((AbstractSaveableEntityEditorPart)editor , editorConcept ,propDefList, danglingRefPath);
										
									}
								} else if (editorEntity instanceof Event){
									Event editorEvent = (Event)editorEntity;
									if (ELEMENT_TYPES.DOMAIN == danglingResType){
										List<PropertyDefinition> propDefList = editorEvent.getProperties();
										removeDanglingDomainReferences((AbstractSaveableEntityEditorPart)editor , editorEvent ,propDefList, danglingRefPath);
									}									
									
								}

								((AbstractSaveableEntityEditorPart)editor).modified();
							}
						}
					}
				
				}
				if (!propChangeEventFired){
					// save the resource
					//Entity entity = IndexUtils.getEntity(projName, relResPath, IndexUtils.getElementType(extension));
					if (entity != null && entity.eResource() != null){
						ModelUtils.saveEObject(entity);
//						entity.eResource().save(ModelUtils.getPersistenceOptions());
						
						//Refreshing the Project Explorer
						IViewPart view = page.findView(ProjectExplorer.ID);
						if(view!=null){
							((ProjectExplorer)view).getCommonViewer().refresh();
						}
					}
				}
				} catch (Exception e){
					e.printStackTrace();
				}
				}});
		
	}
	/**
	 * removes the dangling domain instance references from property definition and updates the property table
	 * @param editor
	 * @param entity
	 * @param propDefList
	 * @param danglingRefPath
	 */
	private void removeDanglingDomainReferences(final AbstractSaveableEntityEditorPart editor ,final Entity entity, final List<PropertyDefinition> propDefList , final String danglingRefPath){
		if (propDefList == null || propDefList.size() == 0 || danglingRefPath == null) return;
		int rowIndex = 0;
		List<DomainInstance> dangDMInstances = new ArrayList<DomainInstance>();
		for (PropertyDefinition prop : propDefList){
			List<DomainInstance> dmInstanceList = prop.getDomainInstances();
			for (DomainInstance di : dmInstanceList){
				String refPath = di.getResourcePath();
				if (danglingRefPath.equals(refPath)){
					editor.getDomainPathSet().add(refPath);
					dangDMInstances.add(di);
				}
			}
			if (dangDMInstances.size() > 0){
				dmInstanceList.removeAll(dangDMInstances);
				dangDMInstances.clear();
				// refresh the property table on UI
				if (editor != null){
					editor.setDmAssociationFlag(true);
					EditorUtils.refreshPropertyTable(editor, prop, entity, rowIndex);
				}
			}
	
			rowIndex ++;
		}

	}
	/**
	 * removes Domain association from Concepts / Events / score card
	 * @param domainPath
	 * @param projectName
	 */
	protected void removeDomainAssociation(String domainPath, String projectName){
		try{
			if (domainPath == null || projectName == null) return;
			ELEMENT_TYPES[] types = {ELEMENT_TYPES.CONCEPT,ELEMENT_TYPES.SIMPLE_EVENT,ELEMENT_TYPES.SCORECARD};
			List<Entity> list = IndexUtils.getAllEntities(projectName, types ,false);
			for(Entity element:list){
				IFile file = IndexUtils.getFile(element.getOwnerProjectName(), element);
				EList<PropertyDefinition> propDef = null;
				if(element instanceof Concept){
					propDef = ((Concept)element).getProperties();
				} else if(element instanceof Event){
					propDef = ((Event)element).getProperties();
				}
				boolean dmInstancesRemoved = false;
				List<DomainInstance> removedDMInstances = new ArrayList<DomainInstance>();			
				for(PropertyDefinition def : propDef){					
					for(DomainInstance i:def.getDomainInstances()){
						if(i.getResourcePath().equals(domainPath)){
							removedDMInstances.add(i);	
							if (!dmInstancesRemoved){
								dmInstancesRemoved = true;
							}
							
						}
						
					}
					if(def.getDomainInstances().size()>0){
						//def.getDomainInstances().remove(index);
						// it removes Domain associations only from Index 
						def.getDomainInstances().removeAll(removedDMInstances);
						removedDMInstances.clear();
			
					}
			
				}
				if(dmInstancesRemoved){
					// Update any opened Editors
					updateEntityEditor(element, domainPath, element.getFullPath(), projectName,ELEMENT_TYPES.DOMAIN);
				}
			}
			// this will be done upon resource change notification
//			DesignerProject index = IndexUtils.getIndex(projectName);
//			// remove domain instances from DesignerProject also
//			List<InstanceElement<DomainInstance>> danglingDMInstances = new ArrayList<InstanceElement<DomainInstance>>();
//			if (index != null){
//				for (InstanceElement<DomainInstance> ie : index.getDomainInstanceElements()){
//					DomainInstance dmi = ie.getInstance();
//					if (dmi == null) continue;
//					String resPath = dmi.getResourcePath();
//					if (domainPath.equals(resPath)){
//						danglingDMInstances.add(ie);
//					}
//				}
//				// remove all dangling references
//				index.getDomainInstanceElements().removeAll(danglingDMInstances);
//			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	


}
