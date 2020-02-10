package com.tibco.cep.studio.application;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.IWorkbenchConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

import com.tibco.cep.studio.application.utils.Messages;
import com.tibco.cep.studio.ui.navigator.workbench.CustomWorkbenchAdvisor;

/**
 * This workbench advisor creates the window advisor, and specifies the
 * perspective id for the initial window.
 */
public class ApplicationWorkbenchAdvisor extends CustomWorkbenchAdvisor {

	private static final String PERSPECTIVE_ID = "com.tibco.cep.studio.application.perspective";

	public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(
			IWorkbenchWindowConfigurer configurer) {
		return new ApplicationWorkbenchWindowAdvisor(configurer);
	}

	public void initialize(IWorkbenchConfigurer configurer) {
		super.initialize(configurer);
		configurer.setSaveAndRestore(true);
		PlatformUI.getPreferenceStore().setValue(
				IWorkbenchPreferenceConstants.SHOW_TRADITIONAL_STYLE_TABS,
				false);
		PlatformUI.getPreferenceStore().setValue(
				IWorkbenchPreferenceConstants.DOCK_PERSPECTIVE_BAR, "topRight");
		PlatformUI.getPreferenceStore().setValue(
				IWorkbenchPreferenceConstants.SHOW_PROGRESS_ON_STARTUP, true);
	}

	public String getInitialWindowPerspectiveId() {
		return PERSPECTIVE_ID;
	}

	@Override
	public boolean preShutdown() {
		System.setProperty("javax.xml.parsers.DocumentBuilderFactory",
			"com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");
		boolean result = MessageDialog.openConfirm(PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getShell(), Messages.EXIT_TITLE,
				Messages.EXIT_MESSAGE);
		return result;
	}
}
