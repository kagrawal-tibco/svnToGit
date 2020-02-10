package com.tibco.cep.studio.application;

import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

import com.tibco.cep.studio.application.utils.Messages;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

	public ApplicationWorkbenchWindowAdvisor(
			IWorkbenchWindowConfigurer configurer) {
		super(configurer);
	}

	public ActionBarAdvisor createActionBarAdvisor(
			IActionBarConfigurer configurer) {
		return new ApplicationActionBarAdvisor(configurer);
	}

	public void preWindowOpen() {
		IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
		configurer.setInitialSize(new Point(1150, 900));
		configurer.setShowStatusLine(true);
		configurer.setShowCoolBar(true);
		configurer.setShowMenuBar(true);
		configurer.setShowProgressIndicator(true);
		configurer.setShowPerspectiveBar(true);
		configurer.setShowFastViewBars(true);
		configurer.setTitle(Messages.Apps_Window_Title);
	}

	@Override
	public void postWindowOpen() {
		IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		page.resetPerspective();

		getWindowConfigurer()
				.getActionBarConfigurer().getStatusLineManager();
//		statusline.setMessage(null, Messages.Apps_Window_Status_Line);
	}
}
