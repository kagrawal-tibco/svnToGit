/**
 * 
 */
package com.tibco.cep.studio.ui.diagrams;

import static com.tibco.cep.studio.ui.editors.events.EventDiagramEditHandler.is_UNDO;

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
import com.tibco.cep.designtime.core.model.element.ElementFactory;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.event.impl.EventImpl;
import com.tibco.cep.diagramming.utils.DiagramUtils;
import com.tibco.cep.studio.core.index.model.SharedEntityElement;
import com.tibco.cep.studio.core.index.model.impl.SharedEntityElementImpl;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.actions.DeleteElementAction;
import com.tibco.cep.studio.ui.editors.events.EventDiagramEditor;
import com.tibco.cep.studio.ui.editors.utils.EditorUtils;
import com.tomsawyer.graph.TSNode;
import com.tomsawyer.graph.events.TSGraphChangeEvent;
import com.tomsawyer.graph.events.TSGraphChangeEventData;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSENode;

/**
 * @author mgujrath
 *
 */
public class EventDiagramChangeListener extends EntityDiagramChangeListener{
	
	
	
	EventDiagramManager manager;
//	private int flagcnt =0;
	public EventDiagramChangeListener(EventDiagramManager manager) {
		super(manager);
		this.manager = manager;
	}

	@Override
	//has been added as for TSGraphChangeEvent.NODE_DISCARDED onNodeDeleted was getting called which was undesired for EventDiagram
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
		EntityNodeData EventData = (EntityNodeData) tsNode.getUserObject();
		EntityNodeItem EventItem =  EventData.getEntity();
		Event Event=((EventImpl)EventItem.getUserObject());
		//Event.getOwnerProjectName();
		//IPath path = new Path(ResourceHelper.getLocationURI(project).getPath());
		
		Event=(Event)EcoreUtil.copy(Event);
		manager.populateTSNode(tsNode, Event);
			//	tsNode.setUserObject(Event);
		/*boolean isEntityCreationError = false;	
		    
		final String path = ((EventImpl)EventItem.getUserObject()).getFullPath();
		final Project project = (Project) ResourcesPlugin.getWorkspace().getRoot().getProject(((EventImpl)EventItem.getUserObject()).getOwnerProjectName());
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
		EntityNodeData EventData = (EntityNodeData) node.getUserObject();
		EntityNodeItem EventItem =  EventData.getEntity();
		final String path = ((EventImpl)EventItem.getUserObject()).getFullPath();
		final Project project = (Project) ResourcesPlugin.getWorkspace().getRoot().getProject(((EventImpl)EventItem.getUserObject()).getOwnerProjectName());
		manager.entityNodes.remove(EventData);
		final IResource simpleEventType = project.findMember(path + ".event");
		final IResource timeEventType=project.findMember(path + ".time");
		
		Display.getDefault().syncExec(new Runnable(){
			public void run(){
				DeleteElementAction act = new DeleteElementAction();
				if(simpleEventType!=null)
				act.selectionChanged(null, new StructuredSelection(project.getFile(path + ".event")));
				if(timeEventType!=null)
				act.selectionChanged(null, new StructuredSelection(project.getFile(path + ".time")));
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
		//TODO
	}

	private void changeEventFileContents(EventImpl entity) {
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
		//TODO
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
				((EventDiagramEditor)manager.getEditor()).modified();
			}});
	}

	@SuppressWarnings("unused")
	private void asyncRefreshEditor(){
		Display.getDefault().asyncExec(new Runnable(){
			@Override
			public void run() {
				EditorUtils.refreshDiagramEditor(manager.getEditor().getSite().getPage(), ((EventDiagramManager)manager).getProject().getFile(((EventDiagramManager)manager).getProject().getName()+".Eventview"));
			}});
	}

	

}
