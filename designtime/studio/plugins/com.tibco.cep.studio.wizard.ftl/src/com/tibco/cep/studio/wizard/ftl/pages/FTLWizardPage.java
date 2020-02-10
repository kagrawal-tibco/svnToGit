package com.tibco.cep.studio.wizard.ftl.pages;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Composite;


public class FTLWizardPage extends WizardPage {
	private List<FTLWizardPage> children = new ArrayList<FTLWizardPage>();
	
	private FTLWizardPage nextPage = null;

	private boolean isPrepared;
	
	
	public boolean isPrepared() {
		return isPrepared;
	}

	public void setPrepared(boolean isPrepared) {
		this.isPrepared = isPrepared;
	}

	protected FTLWizardPage(String pageName) {
		super(pageName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createControl(Composite parent) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public IWizardPage getPreviousPage() {
		// TODO Auto-generated method stub
		return super.getPreviousPage();
	}
	
	public List<FTLWizardPage> getChildren() {
		return children;
	}
	
	public void addChild(FTLWizardPage page) {
		if(page != null) {
			children.add(page);
			page.setPreviousPage(this);
		}
	}

	public void removeChild(FTLWizardPage page) {
		if (page != null) {
			if (children.contains(page))
				children.remove(page);
		}
	}
	
	@Override
	public IWizardPage getNextPage() {
		// TODO Auto-generated method stub
		BusyIndicator.showWhile(this.getControl().getDisplay(), new Runnable() {
			@Override
			public void run() {
				if(nextPage.isControlCreated())
				{   nextPage.setPageComplete(false);
					nextPage.setControl(null);
				}
				nextPage.prepareData();
			}
		});
		if (nextPage.isPrepared()) {
			return nextPage;
		} else {
			return this;
		}
	}

	public FTLWizardPage getNextPageDirectly() {
		return nextPage;
	}
	
	public void setNextPage(FTLWizardPage nextPage) {
		for (FTLWizardPage ftlWizardPage : children) {
			if(ftlWizardPage == nextPage) {
				this.nextPage = nextPage;
				return;
			}
		}
		this.nextPage = null;
	}
	
	@Override
	public boolean canFlipToNextPage() {
		// TODO Auto-generated method stub
		return isPageComplete() && nextPage != null;
	}

	public void updateContainerButton() {
		getContainer().updateButtons();
	}
	
	public void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
		updateContainerButton();

	}
	
	protected void prepareData() {
		isPrepared = true;
	}
}
