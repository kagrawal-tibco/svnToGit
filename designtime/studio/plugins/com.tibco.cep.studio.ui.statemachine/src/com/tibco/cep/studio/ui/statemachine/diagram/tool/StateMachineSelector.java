package com.tibco.cep.studio.ui.statemachine.diagram.tool;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;

import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.ResourceHelper;
import com.tibco.cep.studio.ui.EObjectFilter;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.filter.StudioProjectsOnly;
import com.tibco.cep.studio.ui.navigator.model.StateEntityNode;
import com.tibco.cep.studio.ui.statemachine.diagram.utils.Messages;
import com.tibco.cep.studio.ui.wizards.AbstractResourceElementSelector;
import com.tibco.cep.studio.ui.wizards.OnlyFileInclusionFilter;

/**
 * 
 * @author sasahoo
 *
 */
public class StateMachineSelector extends AbstractResourceElementSelector implements  ISelectionStatusValidator {

	private StateMachine selectedStatemachine;
	private boolean isCallStateMachine;
	private StateEntity selectedStateEntity;
	private IFile selectedFile; 
	
	/**
	 * @param parent
	 * @param currentProject
	 * @param currentStateMachine
	 * @param ownerConcept
	 * @param isCallStateMachine
	 */
	public StateMachineSelector(Shell parent,
			                    String currentProjectName,
			                    StateMachine currentStateMachine, 
			                    String ownerConcept,
			                    StateMachine ownerStateMachine, 
			                    boolean isCallStateMachine) {
        super(parent);
        this.isCallStateMachine = isCallStateMachine;
        
        Set<String> extensions = new HashSet<String>();
        extensions.add("statemachine");
        // Only include Designer nature projects
        addFilter(new StudioProjectsOnly(currentProjectName));
        if(isCallStateMachine){
        	setTitle(Messages.getString("Select_Statemachine_Wizard_title"));
        	setMessage(Messages.getString("Select_Statemachine_Wizard_message"));
        }else{
        	setTitle(Messages.getString("Select_State_Wizard_title"));
        	setMessage(Messages.getString("Select_State_Wizard_message"));
        }
            
        // Include only statemachine files
        addFilter(new StudioProjectsOnly(currentProjectName));
        addFilter(new StateMachineFileInclusionFilter(extensions,currentStateMachine.getFullPath(), ownerConcept, isCallStateMachine));
        addFilter(new OnlyFileInclusionFilter(extensions));
        addFilter(new EObjectFilter());
        setValidator(this);
        
        setInput(ResourcesPlugin.getWorkspace().getRoot());
        
        if(ownerStateMachine != null){
        	setInitialEntitySelection(currentProjectName, ownerStateMachine.getFullPath(), ELEMENT_TYPES.STATE_MACHINE);
        }
    }
	
    /* (non-Javadoc)
     * @see org.eclipse.ui.dialogs.ISelectionStatusValidator#validate(java.lang.Object[])
     */
	public IStatus validate(Object[] selection) {
		if (selection != null && selection.length == 1) {
			if(isCallStateMachine ){
				if (selection[0] instanceof IFile) {
					selectedFile =  (IFile)selection[0];
					EObject entityObj = IndexUtils.loadEObject(ResourceHelper.getLocationURI(selectedFile));
					selectedStatemachine = (StateMachine) entityObj;
					String statusMessage = MessageFormat.format(Messages.getString("Select_StateMachine_Message"), new Object[]{(selectedStatemachine != null ? selectedStatemachine.getName() : "") }); 
					return new Status(Status.OK, EditorsUIPlugin.PLUGIN_ID, Status.OK, statusMessage, null);
				}
			}
			else{
				if(selection[0] instanceof StateEntityNode){
					selectedStateEntity = (StateEntity) ((StateEntityNode)selection[0]).getEntity();
					String statusMessage = MessageFormat.format(Messages.getString("Select_State_Message"), new Object[]{(selectedStateEntity != null ? selectedStateEntity.getName() : "") }); 
					return new Status(Status.OK, EditorsUIPlugin.PLUGIN_ID, Status.OK, statusMessage, null);
				}else{
					return new Status(Status.ERROR, EditorsUIPlugin.PLUGIN_ID, Status.ERROR, Messages.getString("Select_State_message_status"), null);
				}
			}
		}
		return new Status(Status.ERROR, EditorsUIPlugin.PLUGIN_ID, Status.ERROR, isCallStateMachine?
				Messages.getString("Select_State_Machine_message_status"):
					Messages.getString("Select_State_message_status"), null);
	}

    @Override
    public int open() {
        return super.open();
    }
    
    @Override
    public Object getFirstResult() {
    	if(isCallStateMachine){
    		return selectedStatemachine;
    	}else{
    		return selectedStateEntity;
    	}
     }
 }