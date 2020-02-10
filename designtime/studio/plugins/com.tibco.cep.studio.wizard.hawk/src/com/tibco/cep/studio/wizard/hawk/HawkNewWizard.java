package com.tibco.cep.studio.wizard.hawk;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

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
import org.xml.sax.SAXException;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.driver.hawk.HawkConstants;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;
import com.tibco.cep.studio.wizard.hawk.pages.ChooseWizardPage;
import com.tibco.cep.studio.wizard.hawk.pages.HawkAgentPage;
import com.tibco.cep.studio.wizard.hawk.pages.HawkMicroAgentMethodPage;
import com.tibco.cep.studio.wizard.hawk.pages.HawkMicroAgentPage;
import com.tibco.cep.studio.wizard.hawk.pages.HawkWizardPage;
import com.tibco.cep.studio.wizard.hawk.pages.ProjectSelectionErrorPage;
import com.tibco.cep.studio.wizard.hawk.pages.SelectChannelPage;
import com.tibco.cep.studio.wizard.hawk.pages.SelectMonitorTypePage;
import com.tibco.cep.studio.wizard.hawk.pages.SetNamePage;
import com.tibco.cep.studio.wizard.hawk.util.EntityCreator;
import com.tibco.cep.studio.wizard.hawk.util.Messages;
import com.tibco.cep.studio.wizard.hawk.util.TemplatePaser;
import com.tibco.hawk.jshma.plugin.HawkConsoleBase;

public class HawkNewWizard extends Wizard implements INewWizard {

	public static int WIZARD_TYPE_DEST_AND_EVENT = 0;
	public static int WIZARD_TYPE_EVENT_ONLY = 1;

	private String projectName;
	private IProject project;
	private boolean initialize = false;
	private Map<String, Channel> channelMap;
	private HawkConsoleBase hawkConsoleBase;
	private boolean eventExist, destExist;
	private int wizardType;

	/**
	 * 
	 * Root page.
	 */
	private HawkWizardPage rootPage = null;

	/**
	 * 
	 * All the pages in the wizard.
	 */
	private Set<HawkWizardPage> pages = new HashSet<HawkWizardPage>();

	/**
	 * 
	 * Creates a new empty wizard.
	 */
	public HawkNewWizard() {
		super();
		setNeedsProgressMonitor(true);
		setWindowTitle(Messages.getString("hawk.wizard.title"));
	}

	/**
	 * Creates a new empty wizard.
	 * 
	 * @param page
	 *            Root page.
	 */
	public HawkNewWizard(IWizardPage page) {
		super();
		setNeedsProgressMonitor(true);
		setWindowTitle(Messages.getString("hawk.wizard.title"));
		if (page instanceof HawkWizardPage) {
			rootPage = (HawkWizardPage) page;
			addPage(rootPage);
		}
	}

	public int getWizardType() {
		return wizardType;
	}

	public void setWizardType(int wizardType) {
		this.wizardType = wizardType;
	}

	/**
	 * set root page of the wizard
	 */
	public void setRootPage(IWizardPage page) {
		if (page instanceof HawkWizardPage) {
			addPage((HawkWizardPage) page);
			rootPage = (HawkWizardPage) page;
		}
	}

	public HawkConsoleBase getHawkConsoleBase() {
		return hawkConsoleBase;
	}

	public void setHawkConsoleBase(HawkConsoleBase hawkConsoleBase) {
		this.hawkConsoleBase = hawkConsoleBase;
	}

	public Map<String, Channel> getChannelMap() {
		return channelMap;
	}

	public void setChannelMap(Map<String, Channel> channelMap) {
		this.channelMap = channelMap;
	}

	/**
	 * initialize the wizard, get the project info and get the channel list
	 */
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
					if (HawkConstants.CHANNEL_DRIVER_HAWK.equals(driverType)) {
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

	/**
	 * set the pages of the wizard
	 */
	public void addPages() {
		if (initialize) {
			// page0 choose wizard
			ChooseWizardPage choosePage = new ChooseWizardPage(HawkWizardConstants.PAGE_NAME_CHOOSE);

			// page1 set the name of destination and event
			SetNamePage setNamePage = new SetNamePage(HawkWizardConstants.PAGE_NAME_NAME);

			// page2 choose channel
			SelectChannelPage selectChannelPage = new SelectChannelPage(HawkWizardConstants.PAGE_NAME_CHANNEL);

			// page3 choose monitor type
			SelectMonitorTypePage selectMonitorTypePage = new SelectMonitorTypePage(
					HawkWizardConstants.PAGE_NAME_MONITORTYPE);

			// page4-6 if choose Subscription
			HawkAgentPage hawkAgentPage = new HawkAgentPage(HawkWizardConstants.PAGE_NAME_AGENT);
			HawkMicroAgentPage hawkMicroAgentPage = new HawkMicroAgentPage(HawkWizardConstants.PAGE_NAME_MICROAGENT);
			HawkMicroAgentMethodPage hawkMicroAgentMethodPage = new HawkMicroAgentMethodPage(
					HawkWizardConstants.PAGE_NAME_METHOD);

			hawkMicroAgentMethodPage.addChild(setNamePage);
			hawkMicroAgentPage.addChild(hawkMicroAgentMethodPage);
			hawkAgentPage.addChild(hawkMicroAgentPage);

			selectMonitorTypePage.addChild(hawkAgentPage);
			selectMonitorTypePage.addChild(setNamePage);

			selectChannelPage.addChild(selectMonitorTypePage);
			selectChannelPage.addChild(hawkAgentPage);

			choosePage.addChild(selectChannelPage);
			setRootPage(choosePage);

			choosePage.setNextPage(selectChannelPage);
			selectChannelPage.setNextPage(selectMonitorTypePage);
			hawkAgentPage.setNextPage(hawkMicroAgentPage);
			hawkMicroAgentPage.setNextPage(hawkMicroAgentMethodPage);
			hawkMicroAgentMethodPage.setNextPage(setNamePage);
		} else {
			ProjectSelectionErrorPage errorPage = new ProjectSelectionErrorPage(HawkWizardConstants.PAGE_NAME_ERROR);
			setRootPage(errorPage);
		}
	}

	public void addPage(IWizardPage page) {
		if (!(page instanceof HawkWizardPage))
			return;

		pages.add((HawkWizardPage) page);
		page.setWizard(this);

		// add all the child pages
		List<HawkWizardPage> children = ((HawkWizardPage) page).getChildren();
		for (int i = 0; i < children.size(); i++) {
			addPage(children.get(i));
		}
	}

	/**
	 * 
	 * 
	 * @param page
	 *            page need to be inserted
	 * @param prviousPage
	 *            insert page after this page
	 * @return
	 */
	public void addPage(HawkWizardPage page, HawkWizardPage previousPage) {
		boolean exist = pages.contains(previousPage);
		if (!exist)
			return;

		if (previousPage.getChildren().add(page))
			addPage(page);
	}

	/**
	 * remove page from wizard
	 * 
	 * @param page
	 * @return
	 */
	public void removePage(HawkWizardPage page) {
		pages.remove(page);
		List<HawkWizardPage> children = page.getChildren();
		for (int i = 0; i < children.size(); i++) {
			removePage(children.get(i));
		}
	}

	/**
	 * 
	 * @param page
	 * @param prviousPage
	 * @return
	 */
	public void removePage(HawkWizardPage page, HawkWizardPage previousPage) {
		boolean exist = pages.contains(previousPage);
		if (exist) {
			if (previousPage.getChildren().remove(page))
				removePage(page);
		}
	}

	/**
	 * 
	 * @param className
	 * @param previousPage
	 * @return
	 */
	public boolean removePage(String className, HawkWizardPage previousPage) {
		boolean exist = pages.contains(previousPage);
		if (!exist)
			return false;

		List<HawkWizardPage> children = previousPage.getChildren();
		for (int i = 0; i < children.size(); i++) {
			HawkWizardPage temp = children.get(i);
			if (temp.getClass().getCanonicalName().equalsIgnoreCase(className)) {
				if (children.remove(temp)) {
					removePage(temp);
					return true;
				} else
					return false;
			}
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#canFinish()
	 */
	public boolean canFinish() {
		HawkWizardPage page = rootPage;
		while (page != null) {
			if (!page.isPageComplete())
				return false;
			page = ((HawkWizardPage) page).getNextPageDirectly();
		}

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#getPreviousPage
	 * (org.eclipse.jface.wizard.IWizardPage)
	 */
	public IWizardPage getPreviousPage(IWizardPage page) {
		if (page == null)
			return null;

		if (!(page instanceof HawkWizardPage))
			return null;

		return ((HawkWizardPage) page).getPreviousPage();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#getNextPage
	 * (org.eclipse.jface.wizard.IWizardPage)
	 */
	public IWizardPage getNextPage(IWizardPage page) {
		if (page == null)
			return null;

		if (!(page instanceof HawkWizardPage))
			return null;

		return ((HawkWizardPage) page).getNextPageDirectly();
	}

	public int getPageCount() {
		return pages.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#getPage(java.lang.String)
	 */
	public IWizardPage getPage(String name) {
		Iterator<HawkWizardPage> iter = pages.iterator();
		while (iter.hasNext()) {
			IWizardPage page = (IWizardPage) iter.next();
			String pageName = page.getName();
			if (pageName.equals(name)) {
				return page;
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#getPages()
	 */
	public IWizardPage[] getPages() {
		IWizardPage[] allPages = new WizardPage[pages.size()];

		return pages.toArray(allPages);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#getStartingPage()
	 */
	public IWizardPage getStartingPage() {
		return rootPage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	public boolean performFinish() {
		SetNamePage setNamePage = (SetNamePage) getPage(HawkWizardConstants.PAGE_NAME_NAME);
		String eventName = setNamePage.getEventName();
		String eventFolderName = setNamePage.getEventFolderName();
		String channelName = ((SelectChannelPage) getPage(HawkWizardConstants.PAGE_NAME_CHANNEL)).getChannelName();
		Channel channel = channelMap.get(channelName);
		final List<Entity> entities = new ArrayList<Entity>();

		Properties eventProps = new Properties();
		Properties destProps = new Properties();

		SimpleEvent event = null;

		String agentName = ((HawkAgentPage) getPage(HawkWizardConstants.PAGE_NAME_AGENT)).getAgentName();
		String microAgentName = ((HawkMicroAgentPage) getPage(HawkWizardConstants.PAGE_NAME_MICROAGENT))
				.getMicroAgentName();

		HawkMicroAgentMethodPage methodPage = (HawkMicroAgentMethodPage) getPage(HawkWizardConstants.PAGE_NAME_METHOD);
		String methodName = methodPage.getMethodName();
		HawkConsoleBase console = getHawkConsoleBase();

		if (wizardType == WIZARD_TYPE_EVENT_ONLY) {

			int argsNum = console.getMethodArguments(eventProps, agentName, microAgentName, methodName);
			if (argsNum == 0) {
				MessageBox mb = new MessageBox(getShell(), SWT.ICON_WARNING | SWT.YES);
				mb.setMessage(Messages.getString("hawk.wizard.warning.no.argument"));
				mb.open();
				return false;
			} else {
				event = EntityCreator.createEvent(projectName, eventFolderName, eventName, eventProps);
				eventExist = EntityCreator.checkEventExist(projectName, event);
				if (checkOverwrite() == SWT.YES) {
					entities.add(event);
				} else {
					return false;
				}
			}

		} else {
			String destinationName = ((SetNamePage) getPage(HawkWizardConstants.PAGE_NAME_NAME)).getDestinationName();
			String monitorType = ((SelectMonitorTypePage) getPage(HawkWizardConstants.PAGE_NAME_MONITORTYPE))
					.getMonitorType();
			destProps.setProperty(HawkConstants.DESTINATION_PROP_MONITORTYPE, monitorType);
			
			if (!HawkConstants.MONITOR_TYPE_SUBSCRIPTION.equals(monitorType)) {
				try {
					eventProps = TemplatePaser.getProperties(monitorType);
				} catch (URISyntaxException e) {
					e.printStackTrace();
				} catch (SAXException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ParserConfigurationException e) {
					e.printStackTrace();
				}
				destProps.setProperty(HawkConstants.DESTINATION_PROP_SUBSCRIPTION, "");
				destProps.setProperty(HawkConstants.DESTINATION_PROP_TIMEINTERVAL, "");
				destProps.setProperty(HawkConstants.DESTINATION_PROP_ARGUMENTS, "");

			} else {
				String timeInterval = methodPage.getTimeInterval();
				String arguments = methodPage.getArguments();

				if (timeInterval.equals("")) {
					timeInterval = "10";
				}
				
				destProps.setProperty(HawkConstants.DESTINATION_PROP_SUBSCRIPTION, "/" + agentName + "/"
						+ microAgentName + "/" + methodName);
				destProps.setProperty(HawkConstants.DESTINATION_PROP_TIMEINTERVAL, timeInterval);
				destProps.setProperty(HawkConstants.DESTINATION_PROP_ARGUMENTS, arguments);

				int returnArgsNum = console.getMethodReturnArgs(eventProps, agentName, microAgentName, methodName);
				if (returnArgsNum == 0) {
					MessageBox mb = new MessageBox(getShell(), SWT.ICON_WARNING | SWT.YES);
					mb.setMessage(Messages.getString("hawk.wizard.warning.no.return"));
					mb.open();
					return false;
				}
			}
			event = EntityCreator.createEvent(projectName, eventFolderName, eventName, eventProps);
			eventExist = EntityCreator.checkEventExist(projectName, event);
			destExist = EntityCreator.checkDestExist(channel, destinationName);
			if (checkOverwrite() == SWT.YES) {
				EntityCreator.addDestination(channel, destinationName, event, destProps);
				entities.add(channel);
				entities.add(event);
			} else {
				return false;
			}
		}

		WorkspaceModifyOperation op = new WorkspaceModifyOperation() {

			@Override
			protected void execute(IProgressMonitor monitor) throws CoreException, InvocationTargetException,
					InterruptedException {
				monitor.beginTask("Generate", 50);
				monitor.worked(20);

				try {
					EntityCreator.persistEntities(entities, project);
				} catch (Exception e) {
					e.printStackTrace();
				}
				monitor.worked(30);
				if (project != null) {
					project.refreshLocal(IProject.DEPTH_INFINITE, null);
				}
				monitor.done();
				MessageBox mb = new MessageBox(getShell(), SWT.ICON_INFORMATION | SWT.YES);
				mb.setMessage(Messages.getString("hawk.wizard.info.success"));
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

	public IProject getProject() {
		return project;
	}

	private int checkOverwrite() {
		int choice = SWT.YES;
		if (eventExist || destExist) {
			MessageBox mb = new MessageBox(getShell(), SWT.ICON_WARNING | SWT.YES | SWT.NO);
			mb.setMessage(Messages.getString("hawk.wizard.warning.overwrite"));
			choice = mb.open();
		}
		return choice;
	}

	public boolean performCancel() {
		return super.performCancel();
	}

}
