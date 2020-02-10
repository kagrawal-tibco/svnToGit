package com.tibco.cep.studio.ui.wizards;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.resource.ImageDescriptor;

import com.tibco.cep.designtime.core.model.event.EVENT_SCHEDULE_TYPE;
import com.tibco.cep.designtime.core.model.event.EVENT_TYPE;
import com.tibco.cep.designtime.core.model.event.EventFactory;
import com.tibco.cep.designtime.core.model.event.TimeEvent;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;
import com.tibco.cep.studio.ui.util.StudioWorkbenchConstants;

/**
 * 
 * @author sasahoo
 *
 */
public class NewTimeEventWizard extends AbstractNewEntityWizard<EntityFileCreationWizard> {

	public NewTimeEventWizard(){
		setWindowTitle(Messages.getString("new.timeevent.wizard.title"));
	}

	public NewTimeEventWizard(IDiagramEntitySelection diagramEntitySelect, String currentProjectName){
		setWindowTitle(Messages.getString("new.timeevent.wizard.title"));
		setDefaultPageImageDescriptor(getDefaultPageImageDescriptor());
		this.diagramEntitySelect = diagramEntitySelect;
		this.currentProjectName = currentProjectName;
	}
	
	@Override
	protected void createEntity(String filename, String baseURI,
			IProgressMonitor monitor)throws Exception{
		TimeEvent event = EventFactory.eINSTANCE.createTimeEvent();
		populateEntity(filename, event);
		event.setSuperEventPath("");
		event.setScheduleType(EVENT_SCHEDULE_TYPE.RULE_BASED);
		event.setType(EVENT_TYPE.TIME_EVENT);
		StudioResourceUtils.persistEntity(event, baseURI,project,monitor);	
	}

	@Override
	protected String getEntityType() {
		return StudioWorkbenchConstants._WIZARD_TYPE_NAME_TIME_EVENT;
	}

	@Override
	protected String getWizardDescription() {
		return Messages.getString("new.timeevent.wizard.desc");
	}

	@Override
	protected String getWizardTitle() {
		return Messages.getString("new.timeevent.wizard.title");
	}

	@Override
	protected ImageDescriptor getDefaultPageImageDescriptor() {
		return StudioUIPlugin.getImageDescriptor("icons/wizard/timeeventWizard.png");
	}
	
}