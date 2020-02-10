package com.tibco.cep.bpmn.ui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.swt.widgets.Shell;

import com.tibco.cep.bpmn.ui.editor.BpmnMessages;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.ui.filter.ProjectLibraryFilter;
import com.tibco.cep.studio.ui.filter.StudioProjectsOnly;
import com.tibco.cep.studio.ui.widgets.NavigatorResourcesSelector;
import com.tibco.cep.studio.ui.wizards.OnlyFileInclusionFilter;


/**
 * 
 * @author majha
 *
 */
public class BERuleSelector extends NavigatorResourcesSelector {

	private String currentProjectName;

	/**
	 * @param parent
	 * @param currentProject
	 * @param list
	 */
	public BERuleSelector(Shell parent,String currentProject,List<String> list, ELEMENT_TYPES[] displayTypes) {
		super(parent);
		currentProjectName = currentProject;
		setTitle(BpmnMessages.getString("beRuleSelector_title"));
    	setMessage(BpmnMessages.getString("beRuleSelector_title"));
		addFilter(new StudioProjectsOnly(currentProject));
		Set<String> extensions = new HashSet<String>();
		extensions.add("rule");
		addFilter(new OnlyFileInclusionFilter(extensions));
		addFilter(new ProjectLibraryFilter(displayTypes));
		setInput(ResourcesPlugin.getWorkspace().getRoot());
		setCheckedList(list);
	}

	/**
	 * @param element
	 */
	public void addElements(Object element) {
		if (this.checkedElements == null) {
			checkedElements = new HashSet<Object>();
		}
		checkedElements.add(element);
	}
	
	/**
	 * @param element
	 */
	public void addGrayedElements(Object element) {
		if (this.grayededElements == null) {
			grayededElements = new HashSet<Object>();
		}
		grayededElements.add(element);
	}

	/**
	 * @param list
	 */
	protected void setCheckedList(List<String> list){
		List<IFile> existingResourcesList= new ArrayList<IFile>();
		
		for(String instance: list){
			RuleElement ruleElement = IndexUtils.getRuleElement(currentProjectName, instance, ELEMENT_TYPES.RULE);
			if(ruleElement != null){
				IFile file = IndexUtils.getFile(currentProjectName, ruleElement);
				existingResourcesList.add(file);
			}
		}
		if(existingResourcesList.size()>0){
			existingInstances = new IFile[existingResourcesList.size()];
			existingResourcesList.toArray(existingInstances);
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.components.NavigatorResourcesSelector#addCheckedElements()
	 */
	protected void addCheckedElements(){
		if (this.checkedElements != null) {
			checkedElements.clear();
		}
		for(Object element: getTreeViewer().getCheckedElements()){
			
			if(element instanceof IProject){
				//to discard Project if grayed  
				if(this.grayededElements != null && !grayededElements.contains(element)){
					addChildren(((IProject)element));
		            break;			
				}
			}
			if(element instanceof IFolder){
				//to discard Folder if grayed
				if(this.grayededElements != null && !grayededElements.contains(element)){
					addChildren(((IFolder)element));
				}
			}
			if(element instanceof IFile){
				addElements(element);
			}
		}   	
	}
	
	/**
	 * @param container
	 */
	private void addChildren(IContainer container){
		for(Object element:CommonUtil.getResources(container)){
			if(element instanceof IFile){
				IFile file = (IFile)element;
				if(isValidResource(file)){
					addElements(element);
				}
			}
			if(element instanceof IContainer){
				addChildren((IContainer)element);
			}
		}
	}
	
	protected boolean isValidResource(IFile file){
		if(CommonIndexUtils.RULE_EXTENSION.equals(file.getFileExtension())) {
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.components.NavigatorResourcesSelector#addGrayedElements()
	 */
	protected void addGrayedElements(){
		
		if (this.grayededElements == null) {
			grayededElements = new HashSet<Object>();
		}
		
		if (this.grayededElements != null) {
			grayededElements.clear();
		}
		for(Object element: getTreeViewer().getGrayedElements()){
			addGrayedElements(element);
		}   	
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.dialogs.SelectionStatusDialog#getFirstResult()
	 */
	@Override
	public Object getFirstResult() {
		return checkedElements;
	}

	/**
	 * @return
	 */
	public Set<Object> getCheckedElements() {
		return checkedElements;
	}
}