package com.tibco.cep.ws.Export.wizard;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogSettings;
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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.cep.studio.ws.WsPlugin;
import com.tibco.cep.ws.Export.util.WsdlGenerator;
import com.tibco.cep.ws.component.CDDResourceSelector;
import com.tibco.cep.ws.component.FileSelector;

/**
 * @author majha
 *
 */

public class WsdlExportPage extends WizardPage {

	private static String last_dire_selection = "";
	private static String last_cdd_selection = "";
	private String projName;
    String wsdlName = "";
    private Composite composite;
	private IProject project;
	private Text txtCdd;
	private Text wsdlNameText;
	private Text schemaFoldername;
	private boolean overrideDefaultTNS = false;
	protected boolean overrideDefaultProjName = false;
	private String targetNamespace;
	private static final String LAST_SELECTED_SCHEMA_FOLDER = "schema_folder_path";
	private IDialogSettings fDialogSettings;
	private String projectName;

	protected WsdlExportPage(IProject prj) {
		super("WSDL Export");
		setTitle(getName());
		setDescription("Provide folder, cluster config file and file name for WSDL");
		this.project = prj;
		projName = prj.getName();
		fDialogSettings = WsPlugin.getDefault().getDialogSettings();
	}

	@Override
	public void createControl(final Composite parent) {
		composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Label label = new Label(composite, SWT.NONE);
		label.setText("Choose WSDL Location:");
		GridData gd = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		gd.horizontalSpan = 2;
		label.setLayoutData(gd);

		schemaFoldername = new Text(composite, SWT.BORDER);
		schemaFoldername.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING,
				true, false));
		
		String path = fDialogSettings.get(LAST_SELECTED_SCHEMA_FOLDER);
		if (path != null) {
			last_dire_selection = path;
			schemaFoldername.setText(last_dire_selection);
		}
		schemaFoldername.addModifyListener(new ModifyListener(){
			public void modifyText(ModifyEvent e) {
				last_dire_selection = schemaFoldername.getText();
				boolean validFolderSpecified = isValidFolderSpecified();
				boolean pageComplete = isValidUserInput();
				setPageComplete(pageComplete);
				if (validFolderSpecified) {
					fDialogSettings.put(LAST_SELECTED_SCHEMA_FOLDER, last_dire_selection);
				}
			}
		});

		Button chooser = new Button(composite, SWT.PUSH);
		chooser.setText("Browse");
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
				}
			}

		});
		
		label = new Label(composite, SWT.NONE);
		label.setText("Enter WSDL file name: ");
		gd = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		gd.horizontalSpan = 2;
		label.setLayoutData(gd);

	    wsdlNameText = new Text(composite, SWT.BORDER);
	    wsdlNameText.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING,
				true, false));
	    
	    wsdlNameText.addModifyListener(new ModifyListener(){
			public void modifyText(ModifyEvent e) {
				Text widget = (Text)e.widget;
				String name = widget.getText().trim();
				boolean validWsdlFileName = isValidWsdlFileName(name);
				if(validWsdlFileName ){
					if(name.contains(".wsdl"))
						wsdlName= name;
					else
						wsdlName= name+".wsdl";
				}else{
					wsdlName = "";
				}
					
				setPageComplete(isValidUserInput());
			}
	    	
	    });
	    
	    Label lblCdd = new Label(composite, SWT.NONE);
	    lblCdd.setText("Select cluster config file");
		gd = new GridData(SWT.FILL, SWT.END, true, false);
		gd.horizontalSpan = 2;
		lblCdd.setLayoutData(gd);

		txtCdd = new Text(composite, SWT.BORDER);
		txtCdd.setEditable(false);
		gd = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		txtCdd.setLayoutData(gd);
		if (isCDDExistInProject(last_cdd_selection))
			txtCdd.setText(last_cdd_selection);
		Button cddBrowseButton = new Button(composite, SWT.PUSH);
		cddBrowseButton.setLayoutData(new GridData(SWT.RIGHT, SWT.BEGINNING,
				false, false));
		cddBrowseButton.setToolTipText("Browse resources...");
		cddBrowseButton.setText("Browse");
		cddBrowseButton.addSelectionListener(new CDDSelector());
		
		final Button lblTns = new Button(composite, SWT.CHECK);
		lblTns.setText("Override base target namespace");
		
		gd = new GridData(SWT.FILL, SWT.END, true, false);
		gd.horizontalSpan = 2;
		lblTns.setLayoutData(gd);
		
		final Text tnsText = new Text(composite, SWT.BORDER);
		tnsText.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		    
		targetNamespace = "www.tibco.com/be/ontology/";
		tnsText.setText(targetNamespace);
		tnsText.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				targetNamespace = tnsText.getText();
			}
		});
		tnsText.setEnabled(false);
		
		final Button lblProjName = new Button(composite, SWT.CHECK);
		lblProjName.setText("Override project name");
		
		gd = new GridData(SWT.FILL, SWT.END, true, false);
		gd.horizontalSpan = 2;
		lblProjName.setLayoutData(gd);

		final Text projNameText = new Text(composite, SWT.BORDER);
		projNameText.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		    
		projectName = this.project.getName();
		projNameText.setText(projectName);
		projNameText.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				projectName = projNameText.getText();
			}
		});
		projNameText.setEnabled(false);

		lblTns.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				overrideDefaultTNS = lblTns.getSelection();
				tnsText.setEnabled(overrideDefaultTNS);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		
		lblProjName.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				overrideDefaultProjName = lblProjName.getSelection();
				projNameText.setEnabled(overrideDefaultProjName);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		setControl(composite);
	}
	
	private class CDDSelector implements SelectionListener {

		public void widgetDefaultSelected(SelectionEvent e) {

		}

		public void widgetSelected(SelectionEvent e) {
			Set<String> hashSet = new HashSet<String>();
			hashSet.add("cdd");
			FileSelector fileSelector = new CDDResourceSelector(composite
					.getShell(), projName, hashSet);
			if (fileSelector.open() == Dialog.OK) {
				if (fileSelector.getFirstResult() instanceof String) {
					String firstResult = (String) fileSelector.getFirstResult();
					txtCdd.setText(firstResult);
					last_cdd_selection = firstResult;
					setPageComplete(isValidUserInput());
				}
			}
		}

	}

	void performFinish(IProgressMonitor monitor) throws Exception {
		if(monitor == null)
			monitor = new NullProgressMonitor();
		
		WsdlGenerator gen = new WsdlGenerator(project, composite);
		if (overrideDefaultTNS) {
			gen.setBaseNamespace(targetNamespace);
		}
		if (overrideDefaultProjName) {
			gen.setProjectName(projectName);
		}
		gen.generateWsdl(last_dire_selection, wsdlName, txtCdd.getText(), monitor);
		monitor.done();
	}

	private boolean isValidUserInput() {
		boolean wsdlFolderValid = isValidFolderSpecified();
		if(!wsdlFolderValid)
			return false;

		boolean wsdlNameValid = isValidWsdlFileName(wsdlNameText.getText().trim());

		if(!wsdlNameValid)
			return false;
		
		boolean cddNameEmpty = txtCdd.getText().trim().isEmpty();
		if(cddNameEmpty){
			setErrorMessage("select valid cluster config file");
			return false;
		}
		
		setErrorMessage(null);
		return true ;
	}
	
	private boolean isValidFolderSpecified(){
		String fileName = last_dire_selection.trim();
		boolean wsdlFolderValid = false;
		if (fileName != null && !fileName.isEmpty()) {
			File file = new File(fileName);	
			if(file != null && file.exists() && !file.isFile()) {
				setErrorMessage(null);
				wsdlFolderValid = true;
			}else{
				setErrorMessage("provide valid folder location");
			}
		}
		else{
			setErrorMessage("provide valid folder location");
		}
		return wsdlFolderValid;
	}
	
	private boolean isValidWsdlFileName(String name){
		if(name.isEmpty()){
			setErrorMessage("provide valid wsdl file name");
			return false;
		}
		boolean valid = ModelNameUtil.isValidIdentifier(name);
//		String invalidCharRegex = "[^\\!\\[\\]]*";
//    	boolean valid = Pattern.matches(invalidCharRegex, name);
		if(!valid){
			setErrorMessage(name + " is not valid name");
			return false;
		}else{
			if(name.contains(".")){
				StringTokenizer stringTokenizer = new StringTokenizer(name,".");
				int countTokens = stringTokenizer.countTokens();
				if(countTokens>2 || countTokens ==1){
					setErrorMessage(name + " is not valid name");
					return false;
				}else{
					stringTokenizer.nextToken();
					String string =stringTokenizer.nextToken();
					if(string.equalsIgnoreCase("wsdl")){
						setErrorMessage(null);
						return true;
					}else{
						setErrorMessage("wrong extension provided for file name");
						return false;
					}
				}
			}else{
				setErrorMessage(null);
				return true;
			}
		}
	}
	
	private boolean isCDDExistInProject(String cddFile){
		if(cddFile.trim().isEmpty())
			return false;
		
		IFile file = project.getFile(cddFile);
		if(file != null && file.exists())
			return true;
		
		return false;
	}

    public String getWSDLLocation() {
    	return last_dire_selection;
    }
	
}