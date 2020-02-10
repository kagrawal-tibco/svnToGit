package com.tibco.cep.decision.tree.ui.tool;

import static com.tibco.cep.studio.ui.util.StudioUIUtils.getStateGraphPath;

import java.text.MessageFormat;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.navigator.CommonViewer;

import com.tibco.cep.decision.tree.common.model.DecisionTree;
import com.tibco.cep.decision.tree.ui.util.Messages;
import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.navigator.model.StateEntityNode;
import com.tibco.cep.studio.ui.wizards.AbstractResourceElementSelector;

public class DecisionTreeComponentsSelector extends AbstractResourceElementSelector implements  ISelectionStatusValidator {

	private StateEntity selectedStateEntity;
    private StateEntityNode entityNode;
    private String GUID = null;
	private TreeItem selectTreeItem = null;
	private State currentState;
	/**
	 * @param parent
	 * @param currentProject
	 * @param currentDecisionTree
	 */
	public DecisionTreeComponentsSelector(Shell parent,String currentProject,DecisionTree currentDecisionTree, State currentState, State state) {
		super(parent);
		setTitle(Messages.getString("Select_State_Wizard_title"));
		setMessage(Messages.getString("Select_State_Wizard_message"));
		setValidator(this);
		setInput(currentDecisionTree);
		this.currentState = currentState;
		if(state != null){
			GUID = state.getGUID();
		}
    }
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.components.BaseObjectSelector#create()
	 */
	public void create() {
		super.create();
		if(GUID !=null){
			treeViewer.expandAll();
			for(TreeItem i : treeViewer.getTree().getItems()){
				String key = ((StateEntityNode)i.getData()).getEntity().getGUID();
				if(key.equals(GUID)){
					selectTreeItem = i;
					break;
				}
				traverseItems(i);
			}
			if(selectTreeItem != null){
				treeViewer.getTree().setSelection(selectTreeItem);
			}
		}
	}
    
	/**
	 * @param item
	 */
	private void traverseItems(TreeItem item){
		for(TreeItem i:item.getItems()){
			String key = ((StateEntityNode)i.getData()).getEntity().getGUID();
			if(key.equals(GUID)){
				selectTreeItem = i;
				break;
			}
			if(item.getItemCount()>0){
				traverseItems(i);
			}
		}
	}
	
	@Override
	 protected TreeViewer createTreeViewer(Composite composite) {
		if (treeViewer == null) {
			treeViewer = new CommonViewer(PROJECT_EXPLORER_ID, composite, SWT.BORDER | (allowMultiple ? SWT.MULTI : SWT.SINGLE));
			// Set the content and label providers
			//treeViewer.setLabelProvider(new DecisionTreeLabelProvider());
			treeViewer.addFilter(new DecisionTreeComponentsFilter(currentState));
			treeViewer.setInput(input);
			// Check if tree is empty
			treeIsEmpty = (treeViewer.getTree().getItemCount() == 0);
			// Listen to tree selection
			treeViewer
			.addSelectionChangedListener(new ISelectionChangedListener() {
				public void selectionChanged(
						SelectionChangedEvent event) {
					// Update result and check status of input
					setResult(((IStructuredSelection) event
							.getSelection()).toList());
					updateOKStatus();
				}
			});
			treeViewer.addDoubleClickListener(new IDoubleClickListener() {
				public void doubleClick(DoubleClickEvent event) {
					updateOKStatus();
					boolean okStatus = getOKButtonStatus();
					if (okStatus) {
						okPressed();
					}
				}
			});
		}
		return treeViewer;
	}
    /* (non-Javadoc)
     * @see org.eclipse.ui.dialogs.ISelectionStatusValidator#validate(java.lang.Object[])
     */
	public IStatus validate(Object[] selection) {
		if (selection != null && selection.length == 1) {
			if(selection[0] instanceof StateEntityNode){
				entityNode = (StateEntityNode)selection[0];
				selectedStateEntity = (StateEntity) entityNode.getEntity();
				
				String statusMessage = MessageFormat.format(Messages.getString("Select_State_Message"), new Object[]{(selectedStateEntity != null ? getStateGraphPath((StateEntity)selectedStateEntity) : "") }); 
				
				return new Status(Status.OK, EditorsUIPlugin.PLUGIN_ID, Status.OK, statusMessage, null);
			}else{
				return new Status(Status.ERROR, EditorsUIPlugin.PLUGIN_ID, Status.ERROR, Messages.getString("Select_State_message_status"), null);
			}
		}
		return new Status(Status.ERROR, EditorsUIPlugin.PLUGIN_ID, Status.ERROR, Messages.getString("Select_State_message_status"), null);
	}

    public StateEntity getSelectedStateEntity() {
		return selectedStateEntity;
	}

	@Override
    public Object getFirstResult() {
    	return selectedStateEntity;
     }

 }