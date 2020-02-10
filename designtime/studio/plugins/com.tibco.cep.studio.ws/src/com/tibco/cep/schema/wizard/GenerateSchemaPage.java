package com.tibco.cep.schema.wizard;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
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
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.studio.core.util.SchemaGenerator;

/**
 * 
 * @author majha
 * 
 */

public class GenerateSchemaPage extends WizardPage {
	private static String last_dire_selection = null;
	private String projName;
	private String xsdLocation = "";
	private boolean overrideBENamespace = false;
	private String baseNamespace = "";
	private boolean generateConceptSchemas = false;
	private boolean generateEventSchemas = false;
	private Composite composite;

	protected GenerateSchemaPage(String projectName) {
		super("Generate Schema");
		setTitle(getName());
		projName = projectName;
	}

	@Override
	public void createControl(final Composite parent) {
		composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Label label = new Label(composite, SWT.NONE);
		label.setText("Schemas Folder:");
		GridData gd = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		gd.horizontalSpan = 2;
		label.setLayoutData(gd);

		final Text schemaFoldername = new Text(composite, SWT.BORDER);
		schemaFoldername.setEditable(false);
		schemaFoldername.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING,
				true, false));

		Button chooser = new Button(composite, SWT.PUSH);
		chooser.setText("...");
		chooser
				.setLayoutData(new GridData(SWT.END, SWT.BEGINNING, false,
						false));

		chooser.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			public void widgetSelected(SelectionEvent e) {

				DirectoryDialog directoryDlg = new DirectoryDialog(composite
						.getShell());
				if (last_dire_selection == null) {
					IWorkspace workspace = ResourcesPlugin.getWorkspace();
					String loc = workspace.getRoot().getLocation()
							.toPortableString();
					last_dire_selection = loc + "/" + projName;
				}

				directoryDlg.setFilterPath(last_dire_selection);
				String firstFile = directoryDlg.open();

				if (firstFile != null) {
					schemaFoldername.setText(firstFile);
					xsdLocation = firstFile;
					last_dire_selection = firstFile;
					setPageComplete(isPageComplete());
				}
			}

		});

		Button overrideButton = new Button(composite, SWT.CHECK);
		gd = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		gd.horizontalSpan = 2;
		overrideButton.setLayoutData(gd);
		overrideButton.setText("Override BusinessEvents Namespace");

		final Text overrideText = new Text(composite, SWT.BORDER);
		gd = new GridData(SWT.FILL, SWT.BEGINNING, false, false);
		gd.horizontalSpan = 2;
		overrideText.setLayoutData(gd);
		overrideText.setVisible(false);

		overrideText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				Text widget = (Text) e.widget;
				baseNamespace = widget.getText().trim();
				setPageComplete(isPageComplete());
			}

		});

		overrideButton.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			public void widgetSelected(SelectionEvent e) {
				Button but = (Button) e.widget;
				boolean selection = but.getSelection();
				overrideBENamespace = selection;
				overrideText.setVisible(selection);
				parent.layout(true);
				setPageComplete(isPageComplete());
			}
		});

		Group group = new Group(composite, SWT.NULL);
		group.setText("Select Resources");
		group.setLayout(new GridLayout());
		group.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));

		Button generateConceptBut = new Button(group, SWT.CHECK);
		gd = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		generateConceptBut.setLayoutData(gd);
		generateConceptBut.setText("Concepts");
		generateConceptBut.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			public void widgetSelected(SelectionEvent e) {
				Button but = (Button) e.widget;
				boolean selection = but.getSelection();
				generateConceptSchemas = selection;
				setPageComplete(isPageComplete());
			}

		});

		Button generateEventBut = new Button(group, SWT.CHECK);
		gd = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		generateEventBut.setLayoutData(gd);
		generateEventBut.setText("Events");
		generateEventBut.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			public void widgetSelected(SelectionEvent e) {
				Button but = (Button) e.widget;
				boolean selection = but.getSelection();
				generateEventSchemas = selection;
				setPageComplete(isPageComplete());
			}

		});

		setControl(composite);
	}

	void performFinish(IProgressMonitor monitor) throws Exception {
		if(monitor == null)
			monitor = new NullProgressMonitor();
		
		SchemaGenerator gen = new SchemaGenerator(projName, true);
		gen.setGenerateLocation(xsdLocation);
		if (overrideBENamespace) {
			gen.setBaseNamespace(baseNamespace);
		} else {
			boolean confirm = MessageDialog
					.openQuestion(
							composite.getShell(),
							"Confirm",
							"Generating schema in the current project with BusinessEvents namespace will result in conflicts.\n Do you want to continue?");
			if(!confirm)
				return;
		}

		if (generateConceptSchemas) {
			gen.generateConceptSchemas(new SubProgressMonitor(monitor, 50));
		}

		if (generateEventSchemas) {
			gen.generateEventSchemas(new SubProgressMonitor(monitor, 50));
			
		}
		monitor.done();
	}

	public boolean isPageComplete() {
		boolean schemaFolderEmpty = xsdLocation.isEmpty();
		boolean generateSchemaSelected = generateConceptSchemas
				|| generateEventSchemas;
		boolean overrideNameNeeded = overrideBENamespace
				&& baseNamespace.isEmpty();

		return !schemaFolderEmpty && generateSchemaSelected
				&& !overrideNameNeeded;
	}


}
