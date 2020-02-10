package com.tibco.cep.studio.dashboard.ui.wizards.page.dashboard;

import java.util.List;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.wizards.page.PageTemplate;

public class DashboardPageTemplateSelectionWizardPage extends WizardPage {

	private String displayName;

	private PageTemplate[] availableTemplates;
	private PageTemplate selectedTemplate;

	private Text displayNameFld;
	private boolean userChangedDisplayName;

	private Combo templateSelectionDropDown;

	private Label templateImageLbl;

	private Text templateDescriptionText;

	private boolean userChangedTemplate;

	protected DashboardPageTemplateSelectionWizardPage(PageTemplate[] availableTemplates, PageTemplate defaultTemplate) {
		super("DashboardPageTemplateSelectionPage", "Dashboard Page Template Selection", null);
		setDescription("Select the template to use for the dashboard page");
		this.availableTemplates = availableTemplates;
		this.selectedTemplate = defaultTemplate;
		this.userChangedTemplate = false;
	}

	@Override
	public void createControl(Composite parent) {
		Composite pageComposite = new Composite(parent, SWT.NONE);
		pageComposite.setLayout(new GridLayout(2, false));
		pageComposite.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL | GridData.HORIZONTAL_ALIGN_FILL));

		// display name control
		Label displayNameLbl = new Label(pageComposite, SWT.NONE);
		displayNameLbl.setText("Display Name :");
		displayNameLbl.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));

		displayNameFld = new Text(pageComposite, SWT.BORDER);
		if (displayName != null){
			displayNameFld.setText(displayName);
		}
		displayNameFld.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		displayNameFld.addKeyListener(new KeyAdapter(){

			@Override
			public void keyReleased(KeyEvent e) {
				userChangedDisplayName = true;
				displayName = displayNameFld.getText();
			}

		});

		// template selection
		Label templateSelectionLbl = new Label(pageComposite, SWT.NONE);
		templateSelectionLbl.setText("Template :");
		templateSelectionLbl.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));

		templateSelectionDropDown = new Combo(pageComposite, SWT.READ_ONLY);
		templateSelectionDropDown.setItems(getAvailableTemplates());
		templateSelectionDropDown.select(getTemplateIndex(selectedTemplate));
		templateSelectionDropDown.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		Group templateDescGrp = new Group(pageComposite, SWT.NONE);
		templateDescGrp.setText("Template Description");
		FillLayout templateDescGrpLayout = new FillLayout();
		templateDescGrpLayout.marginHeight = 10;
		templateDescGrpLayout.marginWidth = 10;
		templateDescGrp.setLayout(templateDescGrpLayout);

		templateDescriptionText = new Text(templateDescGrp, SWT.WRAP | SWT.READ_ONLY | SWT.MULTI | SWT.V_SCROLL);
		templateDescriptionText.setText(selectedTemplate.getDescription());
		GridData templateDescGrpLayoutData = new GridData(SWT.FILL, SWT.FILL, false, true, 2, 1);
		templateDescGrpLayoutData.widthHint = 450;
		templateDescGrp.setLayoutData(templateDescGrpLayoutData);

		Group previewImageGrp = new Group(pageComposite, SWT.NONE);
		previewImageGrp.setText("Template Preview");
		FillLayout previewImageGrpLayout = new FillLayout();
		previewImageGrpLayout.marginHeight = 10;
		previewImageGrpLayout.marginWidth = 10;
		previewImageGrp.setLayout(previewImageGrpLayout);
		templateImageLbl = new Label(previewImageGrp, SWT.NONE);
		templateImageLbl.setAlignment(SWT.CENTER);
		previewImageGrp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 2, 1));
		if (selectedTemplate.getPreviewImage() != null) {
			templateImageLbl.setImage(selectedTemplate.getPreviewImage());
		} else {
			templateImageLbl.setText("No Preview");
		}

		templateSelectionDropDown.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);

			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				userChangedTemplate = true;
				templateSelectionChanged();
			}

		});

		setControl(pageComposite);
	}

	private String[] getAvailableTemplates() {
		String[] names = new String[availableTemplates.length];
		for (int i = 0; i < names.length; i++) {
			names[i] = availableTemplates[i].getName();
		}
		return names;
	}

	private int getTemplateIndex(PageTemplate template) {
		for (int i = 0; i < availableTemplates.length; i++) {
			if (availableTemplates[i].equals(template) == true) {
				return i;
			}
		}
		return -1;
	}

	private PageTemplate getTemplate(String name) {
		for (int i = 0; i < availableTemplates.length; i++) {
			if (availableTemplates[i].getName().equals(name) == true) {
				return availableTemplates[i];
			}
		}
		return null;
	}

	public PageTemplate getSelectedTemplate() {
		return selectedTemplate;
	}

	public String getDisplayName(){
		return displayName;
	}

	public void setDisplayName(String displayName){
		if (userChangedDisplayName == true){
			return;
		}
		this.displayName = displayName;
		if (this.displayNameFld != null){
			this.displayNameFld.setText(this.displayName);
		}
	}

	public void setSelection(List<LocalElement> selection) {
		if (userChangedTemplate == true) {
			return;
		}
		for (int i = 0; i < availableTemplates.length; i++) {
			if (availableTemplates[i].isAcceptable(selection) == true) {
				templateSelectionDropDown.select(i);
				templateSelectionChanged();
				return;
			}
		}
	}

	protected void templateSelectionChanged() {
		int selectionIndex = templateSelectionDropDown.getSelectionIndex();
		selectedTemplate = getTemplate(templateSelectionDropDown.getItem(selectionIndex));
		if (selectedTemplate.getPreviewImage() != null) {
			templateImageLbl.setImage(selectedTemplate.getPreviewImage());
		} else {
			templateImageLbl.setText("No Preview");
		}
		templateDescriptionText.setText(selectedTemplate.getDescription());
	}


}