package com.tibco.cep.studio.ui.wizards;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.resource.ImageDescriptor;

import com.tibco.cep.designtime.core.model.TIMEOUT_UNITS;
import com.tibco.cep.designtime.core.model.event.EVENT_TYPE;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.event.EventFactory;
import com.tibco.cep.designtime.core.model.event.EventPackage;
import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.rule.RuleFactory;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;
import com.tibco.cep.studio.ui.util.StudioWorkbenchConstants;

/**
 * 
 * @author sasahoo
 *
 */
public class NewEventWizard extends AbstractNewEntityWizard<EntityFileCreationWizard> {

	public NewEventWizard(){
		setWindowTitle(Messages.getString("new.event.wizard.title"));
	}

	public NewEventWizard(IDiagramEntitySelection diagramEntitySelect, String currentProjectName){
		setWindowTitle(Messages.getString("new.event.wizard.title"));
		setDefaultPageImageDescriptor(getDefaultPageImageDescriptor());
		this.diagramEntitySelect = diagramEntitySelect;
		this.currentProjectName = currentProjectName;
	}

	@Override
	protected void createEntity(String filename, String baseURI,
			IProgressMonitor monitor) throws Exception{
		SimpleEvent event = EventFactory.eINSTANCE.createSimpleEvent();
		populateEntity(filename, event);
		event.setSuperEventPath("");
//		event.setDestinationName(filename); why are we setting this to the filename?
		event.setTtl("0");
		event.setTtlUnits(TIMEOUT_UNITS.SECONDS);
		event.setType(EVENT_TYPE.SIMPLE_EVENT);
		addCompilable(event, event.getOwnerProjectName(), EventPackage.eINSTANCE.getEvent_ExpiryAction().getName());
		StudioResourceUtils.persistEntity(event, baseURI,project,monitor);	
	}


	/**
	 * @param event
	 * @param projectName
	 * @param feature
	 */
	private  void addCompilable(Event event, 
								String projectName, 
								String feature){
		Rule rule = RuleFactory.eINSTANCE.createRule();
		rule.setOwnerProjectName(projectName);
		rule.setName(event.getName() +"_"+ feature);
		System.out.println("event.getFullPath()---"+event.getFullPath());
		Symbol symbol = RuleFactory.eINSTANCE.createSymbol();
		symbol.setType(event.getFullPath());
		symbol.setIdName(event.getName().toLowerCase());
		rule.getSymbols().getSymbolMap().put(symbol.getIdName(),symbol);
		event.setExpiryAction(rule);
	}
	
	@Override
	protected String getEntityType() {
		return StudioWorkbenchConstants._WIZARD_TYPE_NAME_EVENT;
	}

	@Override
	protected String getWizardDescription() {
		return Messages.getString("new.event.wizard.desc");
	}

	@Override
	protected String getWizardTitle() {
		return Messages.getString("new.event.wizard.title");
	}

	@Override
	protected ImageDescriptor getDefaultPageImageDescriptor() {
		return StudioUIPlugin.getImageDescriptor("icons/wizard/eventWizard.png");
	}
	
}