package com.tibco.cep.studio.ui.navigator.dnd;

import static com.tibco.cep.studio.core.index.utils.IndexUtils.getFullPath;
import static com.tibco.cep.studio.core.util.CommonUtil.replace;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.getDestinationPath;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.CommonDragAdapterAssistant;

import com.tibco.cep.decisionproject.ontology.AbstractResource;
import com.tibco.cep.decisionproject.ontology.ArgumentResource;
import com.tibco.cep.decisionproject.ontology.Concept;
import com.tibco.cep.decisionproject.ontology.Event;
import com.tibco.cep.decisionproject.ontology.PrimitiveHolder;
import com.tibco.cep.decisionproject.ontology.Property;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.SharedEntityElement;
import com.tibco.cep.studio.core.index.model.SharedRuleElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.ui.StudioNavigatorNode;

public class StudioNavigatorDragAdapterAssistant extends CommonDragAdapterAssistant{
	
	private Set<String> extn = new HashSet<String>();

	public StudioNavigatorDragAdapterAssistant(){
		extn.add("sharedjmsapp"); 
		extn.add("sharedjmscon");
		extn.add("sharedjndiconfig");
		extn.add("rvtransport");
		extn.add("sharedhttp");
		extn.add("sharedjdbc");
		extn.add("sharedrsp");
		extn.add("sharedtcp");
		extn.add("id"); 
		extn.add("rule");
		extn.add("channel");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.navigator.CommonDragAdapterAssistant#getSupportedTransferTypes()
	 */
	@Override
	public Transfer[] getSupportedTransferTypes() {
		return new Transfer[]{TextTransfer.getInstance()};
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.navigator.CommonDragAdapterAssistant#setDragData(org.eclipse.swt.dnd.DragSourceEvent, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	@Override
	public boolean setDragData(DragSourceEvent anEvent,	IStructuredSelection selection) {
		List<String> list = new ArrayList<String>();
		for(Object obj:selection.toArray()){
			if(obj instanceof IFolder || obj instanceof IFile){
				IResource file = (IResource)obj;
				String path = "";
				boolean isProjectLibType = IndexUtils.isProjectLibType(file);
				if (isProjectLibType) {
					/**	[scheme:][//authority][path][?query][#fragment] */
					String fragment = file.getLocationURI().getFragment();
					path = fragment.replace(CommonIndexUtils.DOT + file.getFileExtension(), "");
				} else {
					path = getFullPath(file);
				}
				String type = "";
				if (obj instanceof IFile && isURIOnlyResourceDrop(((IFile)obj).getFileExtension())) {
					//For Shared Resources check
					type = "\"" +  path + "\"";
				} else if (isProjectLibType) {
					type = replace(path,"/",".");
				} else {
					type = replace(path,"/",".").substring(1);
				}
				list.add(type);
				continue;
			} else if (obj instanceof SharedRuleElement) {
				String path = ((SharedRuleElement) obj).getFolder() + ((SharedRuleElement) obj).getName();
				SharedRuleElement ruleElement = (SharedRuleElement)obj;
				if(ruleElement.getRule() instanceof Rule){
					path = "\"" +ruleElement.getRule().getFullPath()+"\"";
					list.add(path);
				}else{
					path = ModelUtils.convertPathToPackage(path);
					list.add(path);
				}
				continue;
			}
			Entity entity = null;
			if (obj instanceof StudioNavigatorNode) {
				entity = ((StudioNavigatorNode) obj).getEntity();
			} else if (obj instanceof EntityElement) {
				entity = ((EntityElement) obj).getEntity();
			} else if (obj instanceof SharedEntityElement) {
				entity = ((SharedEntityElement) obj).getSharedEntity();
			}
			//Argument resource Drag handling
			if(obj instanceof AbstractResource){
				if (selection.getFirstElement() instanceof Concept
						|| selection.getFirstElement() instanceof Event) {
					continue;
				}
				list.add(getTransferable(selection));
			}
			
			if (entity != null) {
				String type = entity.getFullPath();
				//For Destination Drag
				if(entity instanceof Destination){
					Destination destination = (Destination)entity;
					type = "\""+ getDestinationPath(destination) +"\"";
				} else if (entity instanceof Channel) {
					type = "\""+ type +"\"";
				} else {
					type = ModelUtils.convertPathToPackage(type);
				}
				list.add(type);
			}
		}
		if(list.size() > 0 ){

			if (isCurrentJavaEditor()) {
				List<String> newlist = new ArrayList<String>();
				for (String s: list) {
					String ns = "/" + s.replace(".", "/");
					newlist.add(ns);
				}
				list.clear();
				list.addAll(newlist);
			}
			
			String data = list.get(0);
			for(int l = 1;l < list.size();l++){
				data = data + "\n" + list.get(l); 
			}
			anEvent.data = data;
			return true;
		}
		return false;
	}
	
	private String getTransferable(IStructuredSelection selection) {
		String parent = "";

		if (selection != null){
			if (selection.getFirstElement() instanceof Property) {
				Property property = (Property) selection.getFirstElement();
				if(property.eContainer() != null){
					ArgumentResource resource = (ArgumentResource)property.eContainer();
					String alias =resource.getAlias();
					if (alias != null){
						parent = alias + "." + property.getName();
					}
				}
			}
			//Supporting primitive types since 3.0.2
			if (selection.getFirstElement() instanceof PrimitiveHolder) {
				PrimitiveHolder primitiveType = (PrimitiveHolder) selection.getFirstElement();
				parent = primitiveType.getName();
			}
		}
		return parent;
	}
	
	/**
	 * @param extension
	 * @return
	 */
	private boolean isURIOnlyResourceDrop(String extension){
		if(extn.contains(extension)){
			return true;
		}
		return false;
	}
	
	private boolean isCurrentJavaEditor() {
		IEditorPart editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		if (editor.getEditorInput() instanceof IFileEditorInput) {
			if (((IFileEditorInput)editor.getEditorInput()).getFile().getFileExtension().equals(CommonIndexUtils.JAVA_EXTENSION)) {
				return true;
			}
		}
		return false;
	}
}
