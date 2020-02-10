package com.tibco.cep.studio.ui.forms.components;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.EList;
import org.eclipse.swt.widgets.Shell;

import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.EObjectFilter;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.cep.studio.ui.filter.StudioProjectsOnly;
import com.tibco.cep.studio.ui.widgets.NavigatorResourcesSelector;


/**
 * 
 * @author sasahoo
 *
 */
public class StateMachineResourceSelector extends NavigatorResourcesSelector {

	protected String emptyListMessage = Messages.getString("MultipleStateMachinesSelector_empty_message");
	protected String emptySelectionMessage = Messages.getString("MultipleStateMachinesSelector_error_message");
	private Set<Object> checkedElements = null;

	/**
	 * @param parent
	 * @param currentProject
	 * @param list
	 */
	public StateMachineResourceSelector(Shell parent,String currentProject,EList<StateMachine> list) {
		super(parent);
		setTitle(Messages.getString("MultipleStateMachineSelection_Wizard_Title"));
		setMessage(Messages.getString("MultipleStateMachineSelection_Wizard_Message"));
		addFilter(new StudioProjectsOnly(currentProject));
		Set<String> extensions = new HashSet<String>();
		extensions.add("statemachine");
		addFilter(new StateMachinesFileInclusionFilter(extensions));
		addFilter(new EObjectFilter());
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
	 * @param list
	 */
	protected void setCheckedList(EList<StateMachine> list){
		List<IFile> existingResourcesList= new ArrayList<IFile>();
		for(StateMachine instance: list){
			String projectName = instance.getOwnerProjectName();
			String stateMachinePath = IndexUtils.getFullPath(instance);
			StateMachine stateMachine = (StateMachine)IndexUtils.getEntity(projectName,stateMachinePath,ELEMENT_TYPES.STATE_MACHINE);
			IFile file = IndexUtils.getFile(projectName, stateMachine);
			existingResourcesList.add(file);
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
			if(element instanceof IFile){
				addElements(element);
			}
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