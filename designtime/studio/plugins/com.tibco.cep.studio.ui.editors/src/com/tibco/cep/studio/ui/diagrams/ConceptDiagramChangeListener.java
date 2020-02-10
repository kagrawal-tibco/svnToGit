package com.tibco.cep.studio.ui.diagrams;

import static com.tibco.cep.studio.ui.editors.concepts.ConceptDiagramEditHandler.is_UNDO;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.internal.resources.Project;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;

import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.ElementFactory;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.element.impl.ConceptImpl;
import com.tibco.cep.diagramming.utils.DiagramUtils;
import com.tibco.cep.studio.core.index.model.SharedEntityElement;
import com.tibco.cep.studio.core.index.model.impl.SharedEntityElementImpl;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.actions.DeleteElementAction;
import com.tibco.cep.studio.ui.editors.concepts.ConceptDiagramEditor;
import com.tibco.cep.studio.ui.editors.utils.EditorUtils;
import com.tomsawyer.graph.TSNode;
import com.tomsawyer.graph.events.TSGraphChangeEvent;
import com.tomsawyer.graph.events.TSGraphChangeEventData;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSENode;


/**
 * 
 * @author smarathe
 *
 */
@SuppressWarnings("restriction")
public class ConceptDiagramChangeListener extends
EntityDiagramChangeListener {

	ConceptDiagramManager manager;
//	private int flagcnt =0;
	public ConceptDiagramChangeListener(ConceptDiagramManager manager) {
		super(manager);
		this.manager = manager;
	}

	@Override
	//has been added as for TSGraphChangeEvent.NODE_DISCARDED onNodeDeleted was getting called which was undesired for ConceptDiagram
	public void graphChanged(TSGraphChangeEventData eventData)
	{
		try {
			if (eventData.getType() == TSGraphChangeEvent.NODE_INSERTED)
			{
				TSENode node = (TSENode) eventData.getSource();
				this.onNodeAdded(node);
			}
			else if (eventData.getType() == TSGraphChangeEvent.NODE_DISCARDED)
			{
				/*TSENode node = (TSENode) eventData.getSource();
				this.onNodeDeleted(node);*/
			}   
			else if (eventData.getType() == TSGraphChangeEvent.NODE_REMOVED)
			{
				TSENode node = (TSENode) eventData.getSource();
				this.onNodeDeleted(node);
			}       
			else if(eventData.getType() == TSGraphChangeEvent.NODE_RENAMED){
				TSENode node = (TSENode) eventData.getSource();
				this.onNodeRenamed(node);
			}
			else if (eventData.getType() == TSGraphChangeEvent.EDGE_INSERTED)
			{
				TSEEdge edge = (TSEEdge) eventData.getSource();
				this.onEdgeAdded(edge);
			}
			else if (eventData.getType() == TSGraphChangeEvent.EDGE_DISCARDED ||
					eventData.getType() == TSGraphChangeEvent.EDGE_REMOVED)
			{
				TSEEdge edge = (TSEEdge) eventData.getSource();
				this.onEdgeDeleted(edge);
			}else if(eventData.getType() == TSGraphChangeEvent.EDGE_ENDNODE_CHANGED){
				TSEEdge edge = (TSEEdge) eventData.getSource();
				this.onEdgeReconnected(edge);
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;

	
	@Override
	protected void onNodeAdded(TSENode tsNode) {
		if(tsNode.getUserObject()!=null && is_UNDO==true){
		EntityNodeData conceptData = (EntityNodeData) tsNode.getUserObject();
		EntityNodeItem conceptItem =  conceptData.getEntity();
		Concept concept=((ConceptImpl)conceptItem.getUserObject());
		//concept.getOwnerProjectName();
		//IPath path = new Path(ResourceHelper.getLocationURI(project).getPath());
		
		concept=(Concept)EcoreUtil.copy(concept);
		manager.populateTSNode(tsNode, concept);
			//	tsNode.setUserObject(concept);
		/*boolean isEntityCreationError = false;	
		    
		final String path = ((ConceptImpl)conceptItem.getUserObject()).getFullPath();
		final Project project = (Project) ResourcesPlugin.getWorkspace().getRoot().getProject(((ConceptImpl)conceptItem.getUserObject()).getOwnerProjectName());
		final String baseURI = getCurrentProjectBaseURI(project);*/
		// create the new entity creation operation
	/*	WorkspaceModifyOperation op = new WorkspaceModifyOperation() {
			protected void execute(IProgressMonitor monitor) throws CoreException {
				try{
				createEntity(filename, baseURI, monitor);
				}catch(final Exception e){
					isEntityCreationError = true;
					page.setErrorMessage(Messages.getString("new.resource.creation.error", page.getContainerFullPath().toPortableString(),getEntityType()));
				}
			}
		};*/
		//createEntity(filename, baseURI, monitor);
		}
		}
	

	protected void onNodeDeleted(TSENode node){
		// flagcnt has been added because onNodeDeleted was getting called twice.Need to investigated this just a quickfix.
		/*if(flagcnt <1){
			flagcnt ++ ;*/
		EntityNodeData conceptData = (EntityNodeData) node.getUserObject();
		EntityNodeItem conceptItem =  conceptData.getEntity();
		final String path = ((ConceptImpl)conceptItem.getUserObject()).getFullPath();
		final Project project = (Project) ResourcesPlugin.getWorkspace().getRoot().getProject(((ConceptImpl)conceptItem.getUserObject()).getOwnerProjectName());
		manager.entityNodes.remove(conceptData);
		Display.getDefault().syncExec(new Runnable(){
			public void run(){
				DeleteElementAction act = new DeleteElementAction();
				act.selectionChanged(null, new StructuredSelection(project.getFile(path + ".concept")));
				act.run(null);
			}
		});
		/*}else if(flagcnt>=1){
			flagcnt = 0;
		}*/
		/*RemoveCommand removeCommand = new RemoveCommand(editingDomain,composite.getRegions(), compositeState);
		EditorUtils.executeCommand((AbstractSaveableEntityEditorPart) manager.getEditor(), removeCommand);
		*/
	}
	
	protected void onEdgeAdded(TSEEdge tsEdge){
		if(!manager.getRefreshAction()) {
			if(DiagramUtils.getEdgeType(tsEdge).equals(DiagramUtils.INHERITANCE_EDGE_TYPE)) {
				TSNode srcNode = tsEdge.getSourceNode();
				TSNode tgtNode = tsEdge.getTargetNode();
				ConceptImpl srcConcept = ((ConceptImpl)((EntityNodeItem)((EntityNodeData)srcNode.getUserObject()).getEntity()).getUserObject());
				ConceptImpl tgtConcept = null ;
				if(!(tgtNode.getUserObject() instanceof SharedEntityElement)){
				
					tgtConcept = ((ConceptImpl)((EntityNodeItem)((EntityNodeData)tgtNode.getUserObject()).getEntity()).getUserObject());
				}else{
					tgtConcept = (ConceptImpl)(((SharedEntityElementImpl)tgtNode.getUserObject()).getEntity());
				}
				srcConcept.setSuperConceptPath(tgtConcept.getFullPath());
				srcConcept.setSuperConcept(tgtConcept);
				
				/*
				 *  This Code has been added to overwrite the Concept file with the new contents like change
				 *  in inheritance. This needs to be done as the here only the UserObject gets changed not the file.
				 */
				changeConceptFileContents(srcConcept);
				asyncEditorModified();
			} else if(DiagramUtils.getEdgeType(tsEdge).equals(DiagramUtils.CONTAINEMT_EDGE_TYPE)) {
				ConceptImpl srcConcept = ((ConceptImpl)((EntityNodeItem)((EntityNodeData)tsEdge.getSourceNode().getUserObject()).getEntity()).getUserObject());
				ConceptImpl tgtConcept = ((ConceptImpl)((EntityNodeItem)((EntityNodeData)tsEdge.getTargetNode().getUserObject()).getEntity()).getUserObject());
				EList<PropertyDefinition> list = srcConcept.getProperties();
				PropertyDefinition propertyDef  = ElementFactory.eINSTANCE.createPropertyDefinition();
				propertyDef.setName(getPropertyName(srcConcept.getName(), list));
				propertyDef.setOwnerPath(srcConcept.getFullPath());
				propertyDef.setConceptTypePath(tgtConcept.getFullPath());
				propertyDef.setOwnerProjectName(tgtConcept.getOwnerProjectName());
				propertyDef.setType(PROPERTY_TYPES.CONCEPT);
				propertyDef.setArray(new Boolean(false));
				propertyDef.setHistoryPolicy(0);
				propertyDef.setHistorySize(0);
				list.add(propertyDef);
				tsEdge.labels().clear();
				tsEdge.setName(propertyDef.getName());
				asyncEditorModified();
				EditorUtils.refreshDiagramEditor(this.manager.getEditor().getSite().getPage(), ((ConceptDiagramManager)this.manager).getProject().getFile(((ConceptDiagramManager)this.manager).getProject().getName()+".conceptview"));
			} else if(DiagramUtils.getEdgeType(tsEdge).equals(DiagramUtils.REFERENCE_EDGE_TYPE)) {
				ConceptImpl srcConcept = ((ConceptImpl)((EntityNodeItem)((EntityNodeData)tsEdge.getSourceNode().getUserObject()).getEntity()).getUserObject());
				ConceptImpl tgtConcept = ((ConceptImpl)((EntityNodeItem)((EntityNodeData)tsEdge.getTargetNode().getUserObject()).getEntity()).getUserObject());
				EList<PropertyDefinition> list = srcConcept.getProperties();
				PropertyDefinition propertyDef  = ElementFactory.eINSTANCE.createPropertyDefinition();
				propertyDef.setName(getPropertyName(srcConcept.getName(), list));
				propertyDef.setOwnerPath(srcConcept.getFullPath());
				propertyDef.setConceptTypePath(tgtConcept.getFullPath());
				propertyDef.setOwnerProjectName(tgtConcept.getOwnerProjectName());
				propertyDef.setType(PROPERTY_TYPES.CONCEPT_REFERENCE);
				propertyDef.setArray(new Boolean(false));
				propertyDef.setHistoryPolicy(0);
				propertyDef.setHistorySize(0);
				list.add(propertyDef);
				tsEdge.labels().clear();
				tsEdge.setName(propertyDef.getName());
				asyncEditorModified();
				EditorUtils.refreshDiagramEditor(this.manager.getEditor().getSite().getPage(), ((ConceptDiagramManager)this.manager).getProject().getFile(((ConceptDiagramManager)this.manager).getProject().getName()+".conceptview"));
			}
		}
	}

	private void changeConceptFileContents(ConceptImpl entity) {
		byte[] objectContents;
		try {
			
			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(entity.getOwnerProjectName());
			IPath projectLocation = project.getLocation();
			IPath path = projectLocation.append(entity.getFolder());
			objectContents = IndexUtils.getEObjectContents(path.toString(),entity);
			ByteArrayInputStream bos = new ByteArrayInputStream(objectContents);
			IFile file = IndexUtils.getFile(entity.getOwnerProjectName(),entity);
			try {
			if (file.exists()) {
			file.setContents(bos, IResource.FORCE, new NullProgressMonitor());
			} else {
				file.create(bos, false, new NullProgressMonitor());
			}
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			//throw new CoreException(new Status(IStatus.ERROR,StudioCorePlugin.PLUGIN_ID, "could not save "+entity.getFullPath(),e));
		} 
		
		
	}

	protected void onEdgeDeleted(TSEEdge tsEdge){
		if(tsEdge.getSourceNode() != null) {
			if(DiagramUtils.getEdgeType(tsEdge).equals(DiagramUtils.INHERITANCE_EDGE_TYPE)) {
				ConceptImpl srcConcept = ((ConceptImpl)((EntityNodeItem)((EntityNodeData)tsEdge.getSourceNode().getUserObject()).getEntity()).getUserObject());
				if(srcConcept != null) {
					srcConcept.setSuperConceptPath("");
					changeConceptFileContents(srcConcept);
					asyncEditorModified();
				}
			} else if(DiagramUtils.getEdgeType(tsEdge).equals(DiagramUtils.CONTAINEMT_EDGE_TYPE)) {
				ConceptImpl srcConcept = ((ConceptImpl)((EntityNodeItem)((EntityNodeData)tsEdge.getSourceNode().getUserObject()).getEntity()).getUserObject());
				if(srcConcept != null) {
					EList<PropertyDefinition> list = srcConcept.getProperties();
					int index = 0;
					for(PropertyDefinition property:list){
						if(property.getName()==tsEdge.getName()) {
							break;
						}
						index++;
					}
					list.remove(index);
					asyncEditorModified();
					EditorUtils.refreshDiagramEditor(this.manager.getEditor().getSite().getPage(), ((ConceptDiagramManager)this.manager).getProject().getFile(((ConceptDiagramManager)this.manager).getProject().getName()+".conceptview"));
				}

			} else if(DiagramUtils.getEdgeType(tsEdge).equals(DiagramUtils.REFERENCE_EDGE_TYPE)) {
				ConceptImpl srcConcept = ((ConceptImpl)((EntityNodeItem)((EntityNodeData)tsEdge.getSourceNode().getUserObject()).getEntity()).getUserObject());
				if(srcConcept != null) {
					EList<PropertyDefinition> list = srcConcept.getProperties();
					int index = 0;
					for(PropertyDefinition property:list){
						if(property.getName()==tsEdge.getName()) {
							break;
						}
						index++;
					}
					list.remove(index);
					asyncEditorModified();
					EditorUtils.refreshDiagramEditor(this.manager.getEditor().getSite().getPage(), ((ConceptDiagramManager)this.manager).getProject().getFile(((ConceptDiagramManager)this.manager).getProject().getName()+".conceptview"));
				}
			}
		}
	}


	protected void onEdgeReconnected(TSEEdge tsEdge) {
		this.onEdgeAdded(tsEdge);
	}

	private static String getPropertyName(String entityName, EList<PropertyDefinition> propertyList){
		String name = entityName + "_property_";
		List<Integer> noList = new ArrayList<Integer>();
		for(PropertyDefinition property:propertyList)
			if(property.getName().startsWith(name)){
				String subname =  property.getName().substring(entityName.length() + 10);
				if(isNumeric(subname)){
					noList.add(Integer.parseInt(subname));
				}
			}

		try{
			if(noList.size()>0){
				java.util.Arrays.sort(noList.toArray());
				int no = noList.get(noList.size()-1).intValue();no++;
				return name+no;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}

		return name + "0";

	}

	private static boolean isNumeric(String numstr) {
		try {
			if (!numstr.trim().equalsIgnoreCase(""))
				Integer.parseInt(numstr);
			return true;
		} catch (NumberFormatException err) {
			return false;
		}
	}
	private void asyncEditorModified(){
		Display.getDefault().asyncExec(new Runnable(){
			@Override
			public void run() {
				((ConceptDiagramEditor)manager.getEditor()).modified();
			}});
	}

	@SuppressWarnings("unused")
	private void asyncRefreshEditor(){
		Display.getDefault().asyncExec(new Runnable(){
			@Override
			public void run() {
				EditorUtils.refreshDiagramEditor(manager.getEditor().getSite().getPage(), ((ConceptDiagramManager)manager).getProject().getFile(((ConceptDiagramManager)manager).getProject().getName()+".conceptview"));
			}});
	}


}
