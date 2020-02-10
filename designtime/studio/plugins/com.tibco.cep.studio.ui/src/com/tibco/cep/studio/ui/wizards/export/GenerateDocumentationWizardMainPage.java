package com.tibco.cep.studio.ui.wizards.export;

import static com.tibco.cep.studio.core.util.DocUtils.getCSSString;
import static com.tibco.cep.studio.core.util.DocUtils.getDefaultCSSString;
import static com.tibco.cep.studio.core.util.DocUtils.readFile;

import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.doc.DocumentationDescriptor;
import com.tibco.cep.studio.core.preferences.StudioCorePreferenceConstants;
import com.tibco.cep.studio.ui.util.PanelUiUtil;


public class GenerateDocumentationWizardMainPage extends WizardPage {

	private Text tProject, tLocation, tCssName, tCssText;
	private Button bLocationBrowse, bCssNameBrowse, bDefaults;
	
	private DocumentationDescriptor docDesc;
	private ArrayList<String> filter = new ArrayList<String>();
	
	protected GenerateDocumentationWizardMainPage(String pageName, DocumentationDescriptor docDesc) {
		super(pageName);
		setTitle(getName());
		setPageComplete(true);
		this.docDesc = docDesc;
		filter.add("*.css");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayout(new GridLayout(2, false));

		PanelUiUtil.createLabel(comp, "Project");
		tProject = PanelUiUtil.createText(comp);
		tProject.setText(docDesc.project.getName());
		tProject.setEditable(false);
		tProject.setEnabled(false);
		
		PanelUiUtil.createLabel(comp, "Doc Location");
		Composite locComp = new Composite(comp, SWT.NONE);
		locComp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		locComp.setLayout(PanelUiUtil.getCompactWidthGridLayout(2, false));
		tLocation = PanelUiUtil.createText(locComp, 250);
		tLocation.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		tLocation.addListener(SWT.Modify, getLocationModifyListener());
		tLocation.setText(docDesc.location);		
		bLocationBrowse = new Button(locComp, SWT.NONE);
		bLocationBrowse.setText("Browse...");
		bLocationBrowse.addListener(SWT.Selection, PanelUiUtil.getFolderBrowseListener(null, comp, tLocation));
		
		PanelUiUtil.createLabel(comp, "CSS Location");
		Composite csslocComp = new Composite(comp, SWT.NONE);
		csslocComp.setLayout(PanelUiUtil.getCompactWidthGridLayout(2, false));
		csslocComp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		tCssName = PanelUiUtil.createText(csslocComp);
		tCssName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		bCssNameBrowse = new Button(csslocComp, SWT.NONE);
		bCssNameBrowse.setText("Browse...");
		bCssNameBrowse.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				FileDialog fileDialog = new FileDialog(getShell());
				fileDialog.setFilterExtensions(filter.toArray(new String[2]));
				String file = fileDialog.open();
				if (file != null) {
					tCssName.setText(file);
					try {
						String contents = readFile(file);
						tCssText.setText(contents);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					tCssName.setText("No Such File exists");
				}
			}
		});

		tCssText = new Text(comp, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.BORDER);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 200;
		gd.horizontalSpan = 2;
		tCssText.setLayoutData(gd);
		String cssString = getCSSString();
		if (cssString != null) {
			tCssText.setText(cssString);
		}
		tCssText.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				// Enable the Restore defaults button on CSS modified.
				if (!bDefaults.isEnabled()) {
					bDefaults.setEnabled(true);	
				}
				if (!tCssText.getText().trim().isEmpty()) {
					InstanceScope.INSTANCE.getNode(StudioCorePlugin.PLUGIN_ID).put(StudioCorePreferenceConstants.DOC_GEN_CSS_STRING, tCssText.getText());
				}
			}
		});
		
		Composite composite = new Composite(comp, SWT.RIGHT_TO_LEFT);
		composite.setLayout(new GridLayout());
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 50;
		gd.horizontalSpan = 2;
		composite.setLayoutData(gd);
		bDefaults = new Button(composite, SWT.NONE);
		bDefaults.setText("Restore Default CSS");
		//Restore defaults button remains disabled until the CSS is modified.
		bDefaults.setEnabled(false);
		bDefaults.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				tCssText.setText(getDefaultCSSString());
				//Disable the Restore defaults button after restoring the CSS.
				bDefaults.setEnabled(false);
			}
		});
		
		setControl(comp);
	}

	private Listener getLocationModifyListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				docDesc.location = tLocation.getText();
			}
		};
		return listener;
	}
	
}