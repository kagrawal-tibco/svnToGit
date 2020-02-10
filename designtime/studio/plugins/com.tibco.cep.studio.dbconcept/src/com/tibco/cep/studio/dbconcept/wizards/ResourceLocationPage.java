package com.tibco.cep.studio.dbconcept.wizards;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IPageChangingListener;
import org.eclipse.jface.dialogs.PageChangingEvent;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.studio.dbconcept.ModulePlugin;
import com.tibco.cep.studio.dbconcept.component.ConceptFolderSelector;
import com.tibco.cep.studio.dbconcept.component.EventDestinationNodeSelector;
import com.tibco.cep.studio.dbconcept.component.EventFolderSelector;
import com.tibco.cep.studio.dbconcept.palettes.tools.DBCeptGenHelper;
import com.tibco.cep.studio.dbconcept.utils.Messages;

/**
 * 
 * @author majha
 * 
 */

public class ResourceLocationPage extends WizardPage implements
		IPageChangingListener {
	DBCeptGenHelper helper;

	Composite parentComposite = null;

	private Label lblCeptPath;
	private Text txtCeptPath;
	private Button btnBrowseCeptPath;
	private Button btnEvent;
	private Label lblEventFolder;
	private Text txtEventFolder;
	private Button btnEventFolder;
	private Label lblEventDestinationURI;
	private Text txtEventDestinationURI;
	private Button btnEventDestinationURI;

	private ModifyListener modifyListener;

	private String projectName;

	protected ResourceLocationPage(String projectName,  DBCeptGenHelper helper) {
		super(Messages
				.getString("Project.Resource.Location.Page.Description"));
		this.helper = helper;
		this.projectName = projectName;
		setTitle(getName());
	}

	@Override
	public void createControl(Composite parent) {
		parentComposite = new Composite(parent, SWT.NONE);

		GridLayout gridLayout = new GridLayout();
		parentComposite.setLayout(gridLayout);

		Group group = new Group(parentComposite, SWT.NULL);
		group.setText("");
		gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		gridLayout.makeColumnsEqualWidth = false;
		group.setLayout(gridLayout);
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		group.setLayoutData(gd);

		lblCeptPath = new Label(group, SWT.NONE);
		lblCeptPath.setText(Messages
				.getString("Project.Resource.Location.Page.ConceptLabel"));
		gd = new GridData(SWT.FILL, SWT.END, true, false);
		gd.horizontalSpan = 2;
		lblCeptPath.setLayoutData(gd);

		txtCeptPath = new Text(group, SWT.BORDER);
		txtCeptPath.setEditable(false);
		gd = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		txtCeptPath.setLayoutData(gd);

		btnBrowseCeptPath = new Button(group, SWT.PUSH);
		btnBrowseCeptPath.setLayoutData(new GridData(SWT.RIGHT, SWT.BEGINNING,
				false, false));
		btnBrowseCeptPath.setToolTipText("Browse concepts folder...");
		btnBrowseCeptPath.setImage(ModulePlugin.getDefault().getImage(
				"icons/browse_file_system.gif"));
		btnBrowseCeptPath.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {

			}

			public void widgetSelected(SelectionEvent e) {
				ConceptFolderSelector fileSelector = new ConceptFolderSelector(
						parentComposite.getShell(),
						projectName);
				if (fileSelector.open() == Dialog.OK) {
					if (fileSelector.getFirstResult() instanceof String) {
						String firstResult = (String) fileSelector
								.getFirstResult();
						txtCeptPath.setText(firstResult);
					}
				}
			}

		});

		btnEvent = new Button(group, SWT.CHECK);
		gd = new GridData(SWT.FILL, SWT.FILL, true, false);
		gd.horizontalSpan = 2;
		btnEvent.setLayoutData(gd);
		btnEvent.setText(Messages
				.getString("Project.Resource.Location.Page.EventButton"));

		lblEventFolder = new Label(group, SWT.NONE);
		lblEventFolder.setText(Messages
				.getString("Project.Resource.Location.Page.EventFolderLabel"));
		gd = new GridData(SWT.FILL, SWT.END, true, false);
		gd.horizontalSpan = 2;
		lblEventFolder.setLayoutData(gd);
		lblEventFolder.setVisible(false);

		txtEventFolder = new Text(group, SWT.BORDER);
		txtEventFolder.setEditable(false);
		gd = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		txtEventFolder.setLayoutData(gd);
		txtEventFolder.setVisible(false);

		btnEventFolder = new Button(group, SWT.PUSH);
		btnEventFolder.setLayoutData(new GridData(SWT.RIGHT, SWT.BEGINNING,
				false, false));
		btnEventFolder.setToolTipText("Browse events folder...");
		btnEventFolder.setImage(ModulePlugin.getDefault().getImage(
				"icons/browse_file_system.gif"));
		btnEventFolder.setVisible(false);
		btnEventFolder.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				EventFolderSelector fileSelector = new EventFolderSelector (
						parentComposite.getShell(),
						projectName);
				if (fileSelector.open() == Dialog.OK) {
					if (fileSelector.getFirstResult() instanceof String) {
						String firstResult = (String) fileSelector
								.getFirstResult();
						txtEventFolder.setText(firstResult);
					}
				}
			}

		});

		lblEventDestinationURI = new Label(group, SWT.NONE);
		lblEventDestinationURI.setText(Messages
				.getString("Project.Resource.Location.Page.EventDestinationLabel"));
		gd = new GridData(SWT.FILL, SWT.END, true, false);
		gd.horizontalSpan = 2;
		lblEventDestinationURI.setLayoutData(gd);
		lblEventDestinationURI.setVisible(false);

		txtEventDestinationURI = new Text(group, SWT.BORDER);
		txtEventDestinationURI.setEditable(false);
		gd = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		txtEventDestinationURI.setLayoutData(gd);
		txtEventDestinationURI.setEditable(false);
		txtEventDestinationURI.setVisible(false);

		btnEventDestinationURI = new Button(group, SWT.PUSH);
		btnEventDestinationURI.setLayoutData(new GridData(SWT.RIGHT,
				SWT.BEGINNING, false, false));
		btnEventDestinationURI.setToolTipText("Browse destination uri...");
		btnEventDestinationURI.setImage(ModulePlugin.getDefault().getImage(
				"icons/browse_file_system.gif"));
		btnEventDestinationURI.setVisible(false);
		btnEventDestinationURI.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent e) {

			}

			public void widgetSelected(SelectionEvent e) {
				Set<String> hashSet = new HashSet<String>();
				hashSet.add("channel");
				EventDestinationNodeSelector fileSelector = new EventDestinationNodeSelector(parentComposite
						.getShell(),projectName, hashSet);
				if (fileSelector.open() == Dialog.OK) {
					if (fileSelector.getFirstResult() instanceof String) {
						String firstResult = (String) fileSelector.getFirstResult();
						txtEventDestinationURI.setText(firstResult);
					}
				}
			}
		});

		txtCeptPath.addModifyListener(getModifyListener());
		txtEventDestinationURI.addModifyListener(getModifyListener());
		txtEventFolder.addModifyListener(getModifyListener());

		btnEvent.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			public void widgetSelected(SelectionEvent e) {
				Button widget = (Button) e.widget;
				boolean selection = widget.getSelection();
				lblEventFolder.setVisible(selection);
				txtEventFolder.setVisible(selection);
				btnEventFolder.setVisible(selection);
				lblEventDestinationURI.setVisible(selection);
				txtEventDestinationURI.setVisible(selection);
				btnEventDestinationURI.setVisible(selection);
				setPageComplete(validatePage());
			}

		});

		WizardDialog dialog = (WizardDialog) getContainer();
		dialog.addPageChangingListener(this);

		setPageComplete(validatePage());
		setControl(parentComposite);
	}

	private boolean validatePage() {
		if (txtCeptPath.getText().trim().isEmpty()
				|| (txtEventFolder.isVisible() && txtEventFolder.getText()
						.trim().isEmpty()))
			return false;

		return true;
	}

	private ModifyListener getModifyListener() {
		if (modifyListener == null) {
			modifyListener = new ModifyListener() {

				public void modifyText(ModifyEvent e) {
					setPageComplete(validatePage());
				}

			};
		}
		return modifyListener;
	}

	public void handlePageChanging(PageChangingEvent event) {
		IWizardPage page = (IWizardPage) event.getCurrentPage();
		if (!page.getName().equals(getName()))
			return;
		helper.setConceptFolderPath(txtCeptPath.getText());
		boolean selection = btnEvent.getSelection();
		helper.setGenerateEvents(selection);
		if (selection) {
			helper.setDestinationURI(txtEventDestinationURI.getText());
			helper.setEventFolderPath(txtEventFolder.getText());
		} 
	}
	
}
