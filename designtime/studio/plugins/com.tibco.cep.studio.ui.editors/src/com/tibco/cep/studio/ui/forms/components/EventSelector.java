package com.tibco.cep.studio.ui.forms.components;

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

import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.SharedEntityElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.ResourceHelper;
import com.tibco.cep.studio.ui.EObjectFilter;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.cep.studio.ui.filter.ProjectLibraryFilter;
import com.tibco.cep.studio.ui.filter.StudioProjectsOnly;
import com.tibco.cep.studio.ui.wizards.AbstractResourceElementSelector;
import com.tibco.cep.studio.ui.wizards.OnlyFileInclusionFilter;

/**
 * 
 * @author sasahoo
 *
 */
public class EventSelector extends AbstractResourceElementSelector implements ISelectionStatusValidator {

	private Event selectedEvent;

	
	/**
	 * @param parent
	 * @param currentProjectName
	 * @param baseEventPath
	 * @param superEventPath
	 */
	public EventSelector(Shell parent,String currentProjectName, String baseEventPath, String superEventPath) {
        super(parent);
        setTitle(Messages.getString("Event_Selector_Dialog_Title"));
        setMessage(Messages.getString("Event_Selector_Dialog_Message"));
        addFilter(new StudioProjectsOnly(currentProjectName));
        Set<String> extensions = new HashSet<String>();
        extensions.add("event");
        addFilter(new EntityFileInclusionFilter(extensions, baseEventPath, superEventPath,currentProjectName));
        addFilter(new EObjectFilter());
        addFilter(new ProjectLibraryFilter(ELEMENT_TYPES.SIMPLE_EVENT));

        setValidator(this);
        setInput(ResourcesPlugin.getWorkspace().getRoot());
        setInitialEntitySelection(currentProjectName, superEventPath, ELEMENT_TYPES.SIMPLE_EVENT);
    }
	
	/**
	 * @param parent
	 * @param currentProjectName
	 * @param eventPath
	 */
	public EventSelector(Shell parent, String currentProjectName, String eventPath, boolean isReturnType) {
        super(parent);
        setTitle(Messages.getString("Event_Selector_Dialog_Title"));
        setMessage(Messages.getString("Event_Selector_Dialog_Message"));
        addFilter(new StudioProjectsOnly(currentProjectName));
        Set<String> extensions = new HashSet<String>();
        extensions.add("event");
//        if(isReturnType){
//        	extensions.add("time");
//        }
        addFilter(new OnlyFileInclusionFilter(extensions));
        addFilter(new EObjectFilter());
        addFilter(new ProjectLibraryFilter(ELEMENT_TYPES.SIMPLE_EVENT));
        setValidator(this);
        setInput(ResourcesPlugin.getWorkspace().getRoot());
//        if(isReturnType){
//        	setInitialEntitySelection(currentProjectName, eventPath, new ELEMENT_TYPES[]{ELEMENT_TYPES.SIMPLE_EVENT, ELEMENT_TYPES.TIME_EVENT});	
//        }
//        else{
        	setInitialEntitySelection(currentProjectName, eventPath, ELEMENT_TYPES.SIMPLE_EVENT);
//        }
    }
	
//	/**
//	 * @param parent
//	 * @param currentProject
//	 */
//	public EventSelector(Shell parent,String currentProject) {
//        super(parent);
//        setTitle(Messages.getString("Event_Selector_Dialog_Title"));
//        setMessage(Messages.getString("Event_Selector_Dialog_Message"));
//        addFilter(new StudioProjectsOnly(currentProject));
//        Set<String> extensions = new HashSet<String>();
//        extensions.add("event");
//        addFilter(new OnlyFileInclusionFilter(extensions, false));
//        addFilter(new EObjectFilter());
//        setValidator(this);
//        setInput(ResourcesPlugin.getWorkspace().getRoot());
//    }
	
    /* (non-Javadoc)
     * @see org.eclipse.ui.dialogs.ISelectionStatusValidator#validate(java.lang.Object[])
     */
    public IStatus validate(Object[] selection) {
        if (selection != null && selection.length == 1) {
        	Object obj = selection[0];
            if (obj instanceof IFile) {
            	
            	EObject entityObj = IndexUtils.loadEObject(ResourceHelper.getLocationURI((IFile)selection[0]));
            	selectedEvent = (Event) entityObj;
                
                String statusMessage = MessageFormat.format(
                		Messages.getString("Event_Selector_Message_format"),
                        new Object[] { (selectedEvent != null ? selectedEvent
                                .getName() : "") }); //$NON-NLS-1$
                return new Status(Status.OK, EditorsUIPlugin.PLUGIN_ID, Status.OK, statusMessage, null);
            } else if (obj instanceof SharedEntityElement && ((SharedEntityElement) obj).getSharedEntity() instanceof Event) {
            	selectedEvent = (Event) ((SharedEntityElement) obj).getSharedEntity();

            	String statusMessage = MessageFormat.format(
            			Messages.getString("Event_Selector_Message_format"),
            			new Object[] { (selectedEvent != null ? selectedEvent
            					.getName() : "") }); //$NON-NLS-1$
            	return new Status(Status.OK, EditorsUIPlugin.PLUGIN_ID, Status.OK, statusMessage, null);
            }

        }
        return new Status(Status.ERROR, EditorsUIPlugin.PLUGIN_ID,
        				  Status.ERROR, Messages.getString("Event_Selector_Error_Message"), null);
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.dialogs.SelectionStatusDialog#getFirstResult()
     */
    @Override
    public Object getFirstResult() {
    	return selectedEvent;
    }
 }
