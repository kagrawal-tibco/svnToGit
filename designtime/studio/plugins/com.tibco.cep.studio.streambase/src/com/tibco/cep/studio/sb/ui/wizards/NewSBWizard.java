package com.tibco.cep.studio.sb.ui.wizards;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import com.streambase.sb.Schema.Field;
import com.tibco.cep.studio.core.util.StudioWorkbenchConstants;
import com.tibco.cep.studio.sb.ui.util.Messages;

public class NewSBWizard extends Wizard implements INewWizard {

	private IStructuredSelection selection;
	private SBEventCreationWizardPage entityPage;
	private SBConnectionWizardPage connPage;
	private SBSchemaWizardPage sbSchemaWizardPage;
	private SBChannelCreationWizardPage channelPage;

	public NewSBWizard() {
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle(Messages.getString("new.sbwizard.title"));
		this.selection = selection;
	}

	@Override
	public void addPages() {
		entityPage = new SBEventCreationWizardPage("SBEventWizardPage.page", this.selection, StudioWorkbenchConstants._WIZARD_TYPE_NAME_EVENT);
		entityPage.setTitle("StreamBase Event Details");
		addPage(entityPage);
		
		connPage = new SBConnectionWizardPage("SBConnectionWizardPage.page", Messages.getString("new.sbwizard.connection.title"), null);
		addPage(connPage);
		
		channelPage = new SBChannelCreationWizardPage("SBChannelWizardPage.page", this.selection, StudioWorkbenchConstants._WIZARD_TYPE_NAME_CHANNEL);
		channelPage.setTitle("StreamBase Channel Details");
		channelPage.setFilterProjects(true);
		addPage(channelPage);
		
		sbSchemaWizardPage = new SBSchemaWizardPage("SBSchemaWizardPage.page", Messages.getString("new.sbwizard.schema.title"), null);
		addPage(sbSchemaWizardPage);
	}

	@Override
	public IWizardPage getNextPage(IWizardPage page) {
		if (page == entityPage) {
			connPage.setProject(entityPage.getProject());
			channelPage.setProject(entityPage.getProject());
		}
		if (page == connPage) {
			if (!connPage.shouldCreateChannel()) {
				channelPage.setPageComplete(true);
				return sbSchemaWizardPage;
			}
		}
		return super.getNextPage(page);
	}

	@Override
	public boolean performFinish() {
		Field[] selectedFields = sbSchemaWizardPage.getSelectedFields();
		entityPage.setFields(selectedFields);
		if (channelPage.getDestinationName() != null && channelPage.getDestinationName().length() > 0) {
			entityPage.setChannelURI("/"+channelPage.getContainerFullPath().removeFirstSegments(1).append(channelPage.getFileName()).removeFileExtension());
			entityPage.setDefaultDestination(channelPage.getDestinationName());
		}
		entityPage.createNewFile();
		if (connPage.shouldCreateChannel()) {
			channelPage.createNewFile();
		}
		
		return true;
	}

	public SBServerDetails getServerDetails() {
		return connPage.getServerDetails();
	}

	public String getEventURI() {
		return "/"+entityPage.getContainerFullPath().removeFirstSegments(1).append(entityPage.getFileName()).removeFileExtension().toString();
	}
	
	public String getSelectedSchemaName() {
		return sbSchemaWizardPage.getSelectedSchemaName();
	}

	public boolean isInputStreamSchema() {
		return sbSchemaWizardPage.isInputStreamSchema();
	}
	
}
