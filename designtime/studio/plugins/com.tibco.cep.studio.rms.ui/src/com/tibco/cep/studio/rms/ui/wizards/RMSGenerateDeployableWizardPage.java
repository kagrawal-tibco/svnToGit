/**
 * 
 */
package com.tibco.cep.studio.rms.ui.wizards;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.container.cep_containerVersion;
import com.tibco.cep.security.util.AuthTokenUtils;
import com.tibco.cep.studio.core.util.Utils;
import com.tibco.cep.studio.rms.core.utils.RMSUtil;
import com.tibco.cep.studio.rms.ui.utils.Messages;

/**
 * @author hitesh
 *
 */
public class RMSGenerateDeployableWizardPage extends WizardPage {

	private Text urlText;
	private Text userText;
	private Text versionText;
	private Button generateClassesButton;
	private Combo projectsCombo;
	private String[] projectsList;
	private Button includeServiceLevelGVBtn;
	private Button genDebugInfoVBtn;
	
	protected RMSGenerateDeployableWizardPage(String pageName, String[] projectList) {
		super("Generate Deployable");
		setTitle("Generate Deployable");
		setDescription("Generate Deployable.");
		this.projectsList = projectList;
	}

	@Override
	public void createControl(Composite parent) {
		setPageComplete(false);
		
		Group composite = new Group(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);
		
		Label urlLabel = new Label(composite, SWT.NONE);
		urlLabel.setText(Messages.getString("URL"));
		
		// URL TextBox
		urlText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		String baseURL = RMSUtil.buildRMSURL();
		if (baseURL == null || baseURL.length() == 0) {
			baseURL = "http://localhost:5000/Transports/Channels/HTTPChannel/";
		}
		GridData data_utext = new GridData(GridData.FILL_HORIZONTAL);
		urlText.setLayoutData(data_utext);
		urlText.setText(baseURL);
		urlText.setEditable(false);
		
		Label userLabel = new Label(composite, SWT.NONE);
		userLabel.setText("User:");
		
		// User TextBox
		userText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		GridData data_userText = new GridData(GridData.FILL_HORIZONTAL);
		userText.setLayoutData(data_userText);
		
		String username = AuthTokenUtils.getLoggedinUser();
		if (username != null) {
			userText.setText(username);
		}
		
		userText.setEditable(false);
		
		Label versionLabel = new Label(composite, SWT.NONE);
		versionLabel.setText("Version:");
		
		
		// User TextBox
		versionText = new Text(composite, SWT.SINGLE | SWT.BORDER);
		GridData data_versionText = new GridData(GridData.FILL_HORIZONTAL);
		versionText.setLayoutData(data_versionText);
		versionText.setText(cep_containerVersion.version.substring(0, cep_containerVersion.version.indexOf('.', 2)));
		versionText.setEditable(false);
		
		Label genClassesLabel = new Label(composite, SWT.NONE);
		genClassesLabel.setText("Generate Classes Only:");
		
		generateClassesButton = new Button(composite, SWT.CHECK);
		GridData data_generateClassesButton = new GridData(GridData.FILL_HORIZONTAL);
		generateClassesButton.setLayoutData(data_generateClassesButton);
		generateClassesButton.addSelectionListener(new SelectionListener() {

			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}

			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				
			}
			
		});
		
		Label projectLabel = new Label(composite, SWT.NONE);
		projectLabel.setText("Select Project:");
		
		projectsCombo = new Combo(composite, SWT.READ_ONLY);
		projectsCombo.setItems(this.projectsList);
		projectsCombo.setData(1);
		projectsCombo.addTraverseListener(new TraverseListener() {

			public void keyTraversed(TraverseEvent e) {
				if (e.detail == SWT.TRAVERSE_RETURN) {
					e.doit = false;
					e.detail = SWT.TRAVERSE_NONE;
					String newText = projectsCombo.getText();
					try {
						projectsCombo.add(newText);
						projectsCombo.setSelection(new Point(0, newText.length()));
					} catch (Exception ex) {
					}
				}
			}
		});
		projectsCombo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				validatePage();
			}
		});
		
		GridData data_combo = new GridData(GridData.FILL_HORIZONTAL);
		projectsCombo.setLayoutData(data_combo);
		
		new Label(composite,SWT.NONE).setText("Generate Debug Info:");
		genDebugInfoVBtn = new Button(composite,SWT.CHECK);
		genDebugInfoVBtn.setSelection(true);
		genDebugInfoVBtn.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		genDebugInfoVBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				validatePage();
			}			
		});
		new Label(composite,SWT.NONE).setText("Include all service level global variables:");
		includeServiceLevelGVBtn = new Button(composite,SWT.CHECK);
		includeServiceLevelGVBtn.setSelection(true);
		includeServiceLevelGVBtn.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		includeServiceLevelGVBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				validatePage();
			}			
		});
		
		setControl(composite);
	
	}

	private void validatePage() {
		if (projectsCombo.getText().length() > 0) {
			String selectedProject = projectsCombo.getText();
			boolean hasAccess = true;
			if (Utils.isStandaloneDecisionManger()) {
				hasAccess = RMSUtil.ensureGenerateDeployableAccess(selectedProject);
			}
			if (hasAccess) {
				setErrorMessage(null);
				setPageComplete(true);
			} else {
				setErrorMessage("Access Denied to Generate Deployable for project - " + selectedProject);
				setPageComplete(false);					
			}	
		} else {
			setErrorMessage("Select a project");
			setPageComplete(false);
		}
	}
	
	public String getSelectedProject() {
		return projectsCombo.getText();
	}
	
	public double getVersion() {
		return Double.parseDouble(versionText.getText());
	}
	
	public boolean isGenDebugInfo() {
		return genDebugInfoVBtn.getSelection();
	}
	
	public boolean isIncludeServiceVars() {
		return includeServiceLevelGVBtn.getSelection();
	}
	
	public boolean isGenerateClassesOnly() {
		return generateClassesButton.getSelection();
	}
	
	public String getURL() {
		return urlText.getText();
	}
}
