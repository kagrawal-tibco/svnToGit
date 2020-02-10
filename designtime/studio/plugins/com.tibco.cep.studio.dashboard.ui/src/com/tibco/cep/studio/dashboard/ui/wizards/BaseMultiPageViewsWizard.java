package com.tibco.cep.studio.dashboard.ui.wizards;

import org.eclipse.jface.dialogs.IPageChangingListener;
import org.eclipse.jface.dialogs.PageChangingEvent;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardDialog;


public abstract class BaseMultiPageViewsWizard extends BaseViewsElementWizard {

	private PageChangingListener changingListener;

	public BaseMultiPageViewsWizard(String elementType, String elementTypeName, String wizardTitle, String pageName, String pageTitle, String pageDesc) {
		super(elementType, elementTypeName, wizardTitle, pageName, pageTitle, pageDesc);
		changingListener = new PageChangingListener();
	}

	@Override
	public void setContainer(IWizardContainer wizardContainer) {
		IWizardContainer existingContainer = getContainer();
		//is the existing container and new wizard container same ?
		if (wizardContainer != existingContainer) {
			//no they are not same. is the previous container instance of WizardDialog
			if (existingContainer instanceof WizardDialog){
				//yes we are, then remove the page changing listener
				((WizardDialog) existingContainer).removePageChangingListener(changingListener);
			}
			//is the incoming wizard container an instance of WizardDialog?
			if (wizardContainer instanceof WizardDialog){
				//yes, it is, then add the page changing listener
				((WizardDialog) wizardContainer).addPageChangingListener(changingListener);
			}

		}
		super.setContainer(wizardContainer);
	}


	protected abstract void pageIsAboutToBeHidden(IWizardPage page);

	protected abstract void pageIsAboutToBeShown(IWizardPage page);


	class PageChangingListener implements IPageChangingListener {

		@Override
		public void handlePageChanging(PageChangingEvent event) {
			if (event.getCurrentPage() instanceof IWizardPage){
				pageIsAboutToBeHidden((IWizardPage) event.getCurrentPage());
			}
			if (event.getTargetPage() instanceof IWizardPage){
				pageIsAboutToBeShown((IWizardPage) event.getTargetPage());
			}
		}

	}

}