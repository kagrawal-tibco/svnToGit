package com.tibco.cep.studio.wizard.hawk.pages;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Composite;

public class HawkWizardPage extends WizardPage {
	/*
	 * all child node pages (all probable next page)
	 */
	private List<HawkWizardPage> children = new ArrayList<HawkWizardPage>();

	/*
	 * next page
	 */
	private HawkWizardPage nextPage = null;

	private boolean isPrepared;

	/**
	 * 
	 * @param pageName
	 */
	protected HawkWizardPage(String pageName) {
		super(pageName);
	}

	/**
	 * 
	 * @param pageName
	 * @param title
	 * @param titleImage
	 */
	protected HawkWizardPage(String pageName, String title, ImageDescriptor titleImage) {
		super(pageName, title, titleImage);
	}

	public boolean isPrepared() {
		return isPrepared;
	}

	public void setPrepared(boolean isPrepared) {
		this.isPrepared = isPrepared;
	}

	public void createControl(Composite parent) {
	}

	public IWizardPage getPreviousPage() {

		return super.getPreviousPage();
	}

	/**
	 * this method will prepare data first before return next page
	 */
	public HawkWizardPage getNextPage() {

		BusyIndicator.showWhile(this.getControl().getDisplay(), new Runnable() {

			@Override
			public void run() {
				nextPage.prepareData();
			}
		});
		if (nextPage.isPrepared()) {
			return nextPage;
		} else {
			return this;
		}
	}

	public HawkWizardPage getNextPageDirectly() {
		return nextPage;
	}

	/**
	 * set the next page
	 * 
	 * @param nextPage
	 *            the nextPage to set
	 */
	public void setNextPage(HawkWizardPage nextPage) {
		for (int i = 0; i < children.size(); i++) {
			if (children.get(i) == nextPage) {
				this.nextPage = nextPage;
				return;
			}
		}
		this.nextPage = null;
	}

	/**
	 * set the next page with className
	 * 
	 * @param className
	 */
	public void setNextPage(String className) {
		int number = -1;
		for (int i = 0; i < children.size(); i++) {
			if (children.get(i).getClass().getCanonicalName().equalsIgnoreCase(className)) {
				number = i;
				break;
			}
		}
		if (number != -1) {
			this.nextPage = children.get(number);
			updateContainerButton();
		} else
			this.nextPage = null;
	}

	/**
	 * add probable next page
	 * 
	 * @param page
	 */
	public void addChild(HawkWizardPage page) {
		if (page != null) {
			children.add(page);
			page.setPreviousPage(this);
		}
	}

	/**
	 * delete probable next page
	 * 
	 * @param page
	 */
	public void removeChild(HawkWizardPage page) {
		if (page != null) {
			if (children.contains(page))
				children.remove(page);
		}
	}

	/**
	 * get all probable next pages
	 * 
	 * @return the children
	 */
	public List<HawkWizardPage> getChildren() {
		return children;
	}

	public boolean canFlipToNextPage() {
		return isPageComplete() && nextPage != null;
	}

	/**
	 * update all the bottom buttons of wizard page
	 */
	public void updateContainerButton() {
		getContainer().updateButtons();
	}

	/**
	 * update status of wizard
	 * 
	 * @param message
	 *            error message
	 */
	public void updateStatus(String message) {

		setErrorMessage(message);
		setPageComplete(message == null);
		updateContainerButton();

	}

	protected void prepareData() {
		isPrepared = true;
	}

}
