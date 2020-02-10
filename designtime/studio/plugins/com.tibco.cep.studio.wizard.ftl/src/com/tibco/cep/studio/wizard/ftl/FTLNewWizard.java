package com.tibco.cep.studio.wizard.ftl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;
import com.tibco.cep.studio.wizard.ftl.model.FTLDataModel;
import com.tibco.cep.studio.wizard.ftl.pages.ConfirmSelectedApps;
import com.tibco.cep.studio.wizard.ftl.pages.FTLWizardPage;
import com.tibco.cep.studio.wizard.ftl.pages.ProjectSelectionErrorPage;
import com.tibco.cep.studio.wizard.ftl.pages.SelectAppInforsPage;
import com.tibco.cep.studio.wizard.ftl.pages.SelectAppPage;
import com.tibco.cep.studio.wizard.ftl.pages.SelectChannelPage;
import com.tibco.cep.studio.wizard.ftl.pages.SelectFilesWizardPage;
import com.tibco.cep.studio.wizard.ftl.pages.SelectTransportPage;
import com.tibco.cep.studio.wizard.ftl.pages.SetNamePage;
import com.tibco.cep.studio.wizard.ftl.utils.EntityCreator;
import com.tibco.cep.studio.wizard.ftl.utils.Messages;

public class FTLNewWizard extends Wizard implements INewWizard {

	private String projectName;
	private IProject project;
	
	private boolean initialize = false;
	
	private FTLWizardPage rootPage = null;

	private Set<FTLWizardPage> pages = new HashSet<FTLWizardPage>();
	
	private Map<String, Channel> channelMap;
	
	private boolean eventExist, destExist;

	public Map<String, Channel> getChannelMap() {
		return channelMap;
	}

	public void setChannelMap(Map<String, Channel> channelMap) {
		this.channelMap = channelMap;
	}

	public boolean isInitialize() {
		return initialize;
	}

	public void setInitialize(boolean initialize) {
		this.initialize = initialize;
	}
	
	public void setRootPage(IWizardPage page) {
		if (page instanceof FTLWizardPage) {
			addPage((FTLWizardPage) page);
			rootPage = (FTLWizardPage) page;
		}
	}
	
	public FTLNewWizard() {
		super();
		setNeedsProgressMonitor(true);
		setWindowTitle(Messages.getString("ftl.wizard.title"));
	}

	public FTLNewWizard(IWizardPage page) {
		super();
		setNeedsProgressMonitor(true);
		setWindowTitle(Messages.getString("ftl.wizard.title"));
		if (page instanceof FTLWizardPage) {
			rootPage = (FTLWizardPage) page;
			addPage(rootPage);
		}
	}
	
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		try {
			project = StudioResourceUtils.getProjectForWizard(selection);
			if (project == null) {
				return;
			}
			projectName = project.getName();

			List<Channel> channels = CommonIndexUtils.getAllEntities(projectName,
					new ELEMENT_TYPES[] { ELEMENT_TYPES.CHANNEL });
			if (channels == null || channels.size() == 0) {
				return;
			} else {
				channelMap = new HashMap<String, Channel>();
				for (int i = 0; i < channels.size(); i++) {
					Channel temp = channels.get(i);
					String driverType = temp.getDriver().getDriverType().getName();
					if(FTLWizardConstants.CHANNEL.equals(driverType)) {
						channelMap.put(channels.get(i).getName(), channels.get(i));
					}
				}
				if (channelMap.keySet().size() < 1) {
					return;
				}
			}
			setNeedsProgressMonitor(false);
			initialize = true;
		} catch (Exception e) {
			String localizedMessage = e.getLocalizedMessage();
			String msg = localizedMessage != null ? localizedMessage : e.toString();
			MessageDialog.openError(getShell(), "Error", msg);
		}
	}

	
	@Override
	public void addPages() {
		if(initialize) {
			SelectChannelPage selectChannelPage = new SelectChannelPage(FTLWizardConstants.PAGE_NAME_CHANNEL);
			
			SelectFilesWizardPage filesWizardPage = new SelectFilesWizardPage(FTLWizardConstants.PAGE_NAME_FILE);
			selectChannelPage.addChild(filesWizardPage);
			
			SelectAppPage appPage = new SelectAppPage(FTLWizardConstants.PAGE_NAME_APP);
			filesWizardPage.addChild(appPage);
			
			SelectAppInforsPage appInforsPage = new SelectAppInforsPage(FTLWizardConstants.PAGE_NAME_APP_INFORS);
			appPage.addChild(appInforsPage);
			
			SelectTransportPage transportPage = new SelectTransportPage(FTLWizardConstants.PAGE_NAME_TRAN);
			appInforsPage.addChild(transportPage);
			
			SetNamePage setNamePage = new SetNamePage(FTLWizardConstants.PAGE_NAME_SETNAME);
			transportPage.addChild(setNamePage);
			
			ConfirmSelectedApps confirmSelectedApps = new ConfirmSelectedApps(FTLWizardConstants.PAGE_NAME_CONFIRM_APPS);
			setNamePage.addChild(confirmSelectedApps);
			
			setRootPage(selectChannelPage);
			
			selectChannelPage.setNextPage(filesWizardPage);
			filesWizardPage.setNextPage(appPage);
			appPage.setNextPage(appInforsPage);
			appInforsPage.setNextPage(transportPage);
			transportPage.setNextPage(setNamePage);
			setNamePage.setNextPage(confirmSelectedApps);
			
		} else  {
			ProjectSelectionErrorPage errorPage = new ProjectSelectionErrorPage(FTLWizardConstants.PAGE_NAME_ERROR);
			setRootPage(errorPage);
		}
	}
	
	public void addPage(IWizardPage page) {
		if (!(page instanceof FTLWizardPage))
			return;

		pages.add((FTLWizardPage) page);
		page.setWizard(this);

		// add all the child pages
		List<FTLWizardPage> children = ((FTLWizardPage)page).getChildren();
		for (int i = 0; i < children.size(); i++) {
			addPage(children.get(i));
		}
	}
	
	public IWizardPage getNextPage(IWizardPage page) {
		// TODO Auto-generated method stub
		return super.getNextPage(page);
	}
	
	public IWizardPage getPreviousPage(IWizardPage page) {
		if (page == null)
			return null;

		if (!(page instanceof FTLWizardPage))
			return null;

		
		page.createControl(null);
		return ((FTLWizardPage) page).getPreviousPage();
	}
	
	@Override
	public IWizardPage getPage(String pageName) {
		for (FTLWizardPage page : pages) {
		    if(pageName.equals(page.getName())) {
		    	return page;
		    }
		}
		return null;
	}
	
	@Override
	public int getPageCount() {
		// TODO Auto-generated method stub
		return super.getPageCount();
	}

	@Override
	public IWizardPage[] getPages() {
		// TODO Auto-generated method stub
		IWizardPage[] allPages = new WizardPage[pages.size()];

		return pages.toArray(allPages);
	}

	@Override
	public IWizardPage getStartingPage() {
		// TODO Auto-generated method stub
		return rootPage;
	}
	
	@Override
	public boolean performCancel() {
		// TODO Auto-generated method stub
		return super.performCancel();
	}
	
	@Override
	public boolean canFinish() {
		FTLWizardPage page = rootPage;
		while (page != null) {
			if (!page.isPageComplete())
				return false;
			page = ((FTLWizardPage) page).getNextPageDirectly();
		}

		return true;

	}
	
	private int checkOverwrite() {
		int choice = SWT.YES;
		if (eventExist || destExist) {
			MessageBox mb = new MessageBox(getShell(), SWT.ICON_WARNING | SWT.YES | SWT.NO);
			mb.setMessage(Messages.getString("ftl.wizard.warning.overwrite"));
			choice = mb.open();
		}
		return choice;
	}
	
	@Override
	public boolean performFinish() {
		SelectChannelPage channelPage = (SelectChannelPage)getPage(FTLWizardConstants.PAGE_NAME_CHANNEL);
		SelectAppPage appPage = (SelectAppPage)getPage(FTLWizardConstants.PAGE_NAME_APP);
		
		Properties formatProps = null;
		SimpleEvent sendEvent = null, sendInboxEvent = null, recvEvent = null, recvInboxEvent = null;
		Channel channel = null;
		Properties destProps = null;
		channel = channelMap.get(channelPage.getSelectChannelName());
		final List<List<Entity>> entitiesList = new ArrayList<List<Entity>>();
		
		List<FTLDataModel> dataModels = appPage.getDataModels();
		for (FTLDataModel ftlDataModel : dataModels) {
			List<Entity> entities = new ArrayList<Entity>();

			destProps = new Properties();
			destProps.put("AppName", ftlDataModel.getAppName());
			destProps.put("EndpointName", ftlDataModel.getEndpointName());
			destProps.put("InstName", ftlDataModel.getInstanceName());
			destProps.put("Secondary", "");
//			destProps.put("MessageType", ftlDataModel.getMessageType());
			destProps.put("Formats", "");
			
			formatProps = ftlDataModel.getFormatProp();
			destProps.put("Formats", ftlDataModel.getFormatName());
			
			destProps.put("Match", "");
			destProps.put("Receive", "false");
			destProps.put("Send", "false");
			destProps.put("ReceiveInbox", "false");
			destProps.put("SendInbox", "false");
			destProps.put("DurableName", "");


				destProps.put("Receive", "true");
				recvEvent = EntityCreator.createEvent(projectName, ftlDataModel.getEventFolderName(), ftlDataModel.getRecvEventName(), formatProps, null, null);
				eventExist = EntityCreator.checkEventExist(projectName,recvEvent);
				destExist = EntityCreator.checkDestExist(channel,ftlDataModel.getDestName());
			
			
				destProps.put("Send", "true");
				sendEvent = EntityCreator.createEvent(projectName, ftlDataModel.getEventFolderName(), ftlDataModel.getSendEventName(), formatProps, channel, ftlDataModel.getDestName());
				eventExist = EntityCreator.checkEventExist(projectName, sendEvent);
				destExist = EntityCreator.checkDestExist(channel,
						ftlDataModel.getDestName());
			
			

			if (checkOverwrite() == SWT.YES) {
				EntityCreator.addDestination(channel, ftlDataModel.getDestName(), recvEvent, destProps);
				
				entities.add(channel);
				if (sendEvent != null) {
					entities.add(sendEvent);
				}
				if (recvEvent != null) {
					entities.add(recvEvent);
				}
				if (sendInboxEvent != null) {
					entities.add(sendInboxEvent);
				}
				if (recvInboxEvent != null) {
					entities.add(recvInboxEvent);
				}
			} else {
				return false;
			}

			entitiesList.add(entities);
		}  
	      
	      
	      
		
		WorkspaceModifyOperation op = new WorkspaceModifyOperation() {

			@Override
			protected void execute(IProgressMonitor monitor) throws CoreException, InvocationTargetException,
					InterruptedException {
				monitor.beginTask("Generate", 50);
				monitor.worked(20);

				for (List<Entity> entities : entitiesList) {
					try {
						EntityCreator.persistEntities(entities, project);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				monitor.worked(30);
				if (project != null) {
					project.refreshLocal(IProject.DEPTH_INFINITE, null);
				}
				monitor.done();
				MessageBox mb = new MessageBox(getShell(), SWT.ICON_INFORMATION | SWT.YES);
				mb.setMessage(Messages.getString("ftl.wizard.info.success"));
				mb.open();
			}

		};
		try {
			getContainer().run(false, true, op);
			if (project != null) {
				project.refreshLocal(IProject.DEPTH_INFINITE, null);
			}
		} catch (Exception e) {
			String localizedMessage = e.getLocalizedMessage();
			String msg = localizedMessage != null ? localizedMessage : e.toString();
			MessageDialog.openError(getShell(), "Error", msg);
			return false;
		}

		return true;	
	}

}
