package com.tibco.cep.ws.wizard;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.Dialog;
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
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.studio.util.StudioConfig;
import com.tibco.cep.ws.component.FileSelector;
import com.tibco.cep.ws.component.WSDLResourceSelector;
import com.tibco.cep.ws.util.WSDLImport;

/**
 * @author majha
 *
 */

public class WsdlImportPage extends WizardPage {
	private static String last_dir_selection = null;
	private String projName;
	private Text text;
	private IProject project;
	private Composite composite;

	protected WsdlImportPage(IProject prj) {
		super("WSDL Import");
		setTitle(getName());
		setDescription("Import a WSDL file to create TIBCO BusinessEvents artifacts");
		projName = prj.getName();
		project = prj;
	}
	

	@Override
	public void createControl(Composite parent) {
	    composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		Group group = new Group(composite, SWT.NULL);
		group.setText("");
		group.setLayout(new GridLayout(2, false));
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Label label = new Label(group, SWT.NONE);
		GridData gd = new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false);
		gd.horizontalSpan = 2;
		label.setLayoutData(gd);
		label.setText("Select WSDL File (must end with .wsdl)");
		
		text = new Text(group, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL,SWT.BEGINNING, true, false));
		
		Button chooser = new Button(group, SWT.PUSH);
		chooser.setText("Browse");
		chooser.setLayoutData(new GridData(SWT.END, SWT.BEGINNING, false, false));

		String property = StudioConfig.getInstance().getProperty("com.tibco.cep.studio.wsdl.includeSchema", "false");
		if (!Boolean.valueOf(property)){
			chooser.addSelectionListener(new WSDLSelector());
			text.setEditable(false);
		}else{
			text.setEditable(true);
			chooser.addSelectionListener(new SelectionListener() {

				public void widgetDefaultSelected(SelectionEvent e) {
					// TODO Auto-generated method stub

				}

				public void widgetSelected(SelectionEvent e) {

					FileDialog fileDialog = new FileDialog(composite.getShell(),
							SWT.MULTI);
					if(last_dir_selection == null){
						IWorkspace workspace = ResourcesPlugin.getWorkspace();
						String loc = workspace.getRoot().getLocation()
								.toPortableString();
						last_dir_selection = loc + "/" + projName;
					}
					if (isValidDirSpecified())
						fileDialog.setFilterPath(text.getText());
					else
						fileDialog.setFilterPath(last_dir_selection);

					fileDialog.setFilterExtensions(new String[] { "*.wsdl" });
					String firstFile = fileDialog.open();

					if (firstFile != null) {
						File file = new File(firstFile);
						last_dir_selection = file.getParent();
						text.setText(firstFile);
					}
				}

			});
			
		}
		
		
		text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				boolean validUserInput = isValidUserInput();
				setPageComplete(validUserInput);
				if(validUserInput){
					String fileName = text.getText().trim();
					last_dir_selection = fileName;
				}
			}
		});

		setControl(composite);
	}
	
	String performFinish(IProgressMonitor monitor) throws Exception{
		if(monitor == null)
			monitor = new NullProgressMonitor();
		
		WSDLImport import1 = new WSDLImport(project);
		import1.importWSDL(text.getText(), monitor);
		return import1.getStoredErrors();
	}
	
	private boolean isValidUserInput() {
		String property = StudioConfig.getInstance().getProperty("com.tibco.cep.studio.wsdl.includeSchema", "false");
		if (Boolean.valueOf(property)){
			String fileName = text.getText().trim();
			if (fileName != null && !fileName.isEmpty()) {
				File file = new File(fileName);	
				if(file != null && file.exists() && file.isFile()) {
					String canonicalFileName = file.getName();
					if (canonicalFileName.endsWith(".wsdl")) {
						setErrorMessage(null);
						return true;
					} else {
						setErrorMessage("choose valid wsdl file");
						return false;
					}
				}
			}
			 setErrorMessage("choose valid wsdl file");
			 return false;
		}
		else{
			String fileName = text.getText().trim();
			return fileName.length() !=0;
		}
	}
	
	private boolean isValidDirSpecified(){
		String fileName = text.getText().trim();
		if (fileName != null && !fileName.isEmpty()) {
			File file = new File(fileName);	
			if(file != null && file.exists()) {
				return true;
			}
		}
		
		return false;
	}
	
	private class WSDLSelector implements SelectionListener {

		public void widgetDefaultSelected(SelectionEvent e) {

		}

		public void widgetSelected(SelectionEvent e) {
			Set<String> hashSet = new HashSet<String>();
			hashSet.add("wsdl");
			FileSelector fileSelector = new WSDLResourceSelector(composite
					.getShell(), projName, hashSet);
			if (fileSelector.open() == Dialog.OK) {
				if (fileSelector.getFirstResult() instanceof String) {
					String firstResult = (String) fileSelector.getFirstResult();
					text.setText(firstResult);
//					last_cdd_selection = firstResult;
					setPageComplete(isValidUserInput());
				}
			}
		}

	}
	
	public void setProject(IProject project) {
		this.project = project;
	}

}
