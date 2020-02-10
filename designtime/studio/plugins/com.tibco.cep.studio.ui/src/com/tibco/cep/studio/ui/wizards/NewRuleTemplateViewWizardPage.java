/**
 * 
 */
package com.tibco.cep.studio.ui.wizards;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.decisionproject.ontology.AbstractResource;
import com.tibco.cep.designtime.core.model.rule.RuleTemplate;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;
import com.tibco.cep.studio.ui.widgets.RuleTemplateSelector;

/**
 * @author hitesh
 *
 */
public class NewRuleTemplateViewWizardPage extends EntityFileCreationWizard implements SelectionListener, ModifyListener {

	private Text templateText;
	private String ruleTemplatePath;
	//Work with the new model
	private AbstractResource template;
	private	String projectName;
	private Text fileLocationText;
	private Button templateViewButton;
	private DesignerElement duplicateElement;
	private Button showDescription;
	private IFile duplicateResource; 
	
	public NewRuleTemplateViewWizardPage(String pageName, IStructuredSelection selection, 
			String type, String virtualRFPath, String projectName) {
		super(pageName, selection, type);
		this.ruleTemplatePath = virtualRFPath;
		this.projectName = projectName;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.wizards.EntityFileCreationWizard#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(1, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		createLabel(container, Messages.getString("new.ruletemplate.view.select.title"));
		
		Composite childContainer = new Composite(container, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;

		childContainer.setLayout(layout);
		childContainer.setLayoutData( new GridData(GridData.FILL_HORIZONTAL));
		
		templateText = createText(childContainer);
		templateText.setLayoutData( new GridData(GridData.FILL_HORIZONTAL));
		templateText.addModifyListener(this);
		
		templateViewButton = new Button(childContainer, SWT.NONE);
		templateViewButton.setText(Messages.getString("Browse"));
		templateViewButton.addSelectionListener(this);

		createResourceContainer(container);
		
		createLabel(container, Messages.getString("wizard.desc"));
		_typeDesc = new Text(container, SWT.BORDER);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		_typeDesc.setLayoutData(gd);
		
//		showDescription = new Button(container,SWT.CHECK);
//		showDescription.setText(Messages.getString("DECISION_TABLE_SHOW_DOMAIN_DESCRIPTION"));
		
		if (ruleTemplatePath != null) {
			DesignerElement element = 
				IndexUtils.getElement(this.projectName, ruleTemplatePath, ELEMENT_TYPES.RULE_TEMPLATE);
			if (element instanceof RuleElement) {
				templateText.setText(ruleTemplatePath);
			}
		}
		setErrorMessage(null);
		setMessage(null);
		setControl(container);
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		if (e.getSource() == templateViewButton) {
			if (projectName ==  null || projectName.length() == 0) {
				if (getContainerFullPath() != null) {
					IResource resource  = StudioResourceUtils.getResourcePathFromContainerPath(getContainerFullPath());
					if (resource.exists()) {
						project = resource.getProject();
						projectName = project.getName();
					}
				}
			}
			if (projectName == null) return;
			Set<String> extensions = new HashSet<String>();
	        extensions.add("ruletemplate");
	        RuleTemplateSelector picker = new RuleTemplateSelector(getShell(), projectName);
			picker.addFilter(new RuleTemplateOnlyFilter(extensions));
			
			if (picker.open() == Dialog.OK) {
				if (picker.getFirstResult() instanceof RuleTemplate) {
					RuleTemplate vrf = (RuleTemplate) picker.getFirstResult();
					templateText.setText(vrf.getFullPath());
				}
			}
		}
	}

	@Override
	public void modifyText(ModifyEvent e) {
		validatePage();
	}

	public void handleEvent(Event event) {
		setPageComplete(validatePage());
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.wizards.WizardNewFileCreationPage#validatePage()
	 */
	protected boolean validatePage() {
		String ruleTemplatePath = templateText.getText().toString();
		
		DesignerElement element = 
			IndexUtils.getElement(this.projectName, ruleTemplatePath);
		if (element instanceof RuleElement) {
			RuleElement ruleElement = (RuleElement)element;
			
			if (ruleElement != null && super.validatePage()) {
				setErrorMessage(null);
				setMessage(null);
				setPageComplete(true);
				return true;
			}
			
			if (ruleElement != null && super.validatePage()) {
				String operation = "create";
				setErrorMessage(Messages.getString("access.resource.error", operation));
				setPageComplete(false);
				return false;
			}
		} else {
			setErrorMessage(Messages.getString("new.ruletemplate.view.select.title"));
			setPageComplete(false);
			return false;
		}

		return super.validatePage();
	}

	public AbstractResource getTemplate() {
		return template;
	}

	public boolean showDescription() {
		if (showDescription != null) {
			return showDescription.getSelection();
		}
		return false;
	}
	
	/**
	 * @return the duplicateElement
	 */
	public DesignerElement getDuplicateElement() {
		return this.duplicateElement;
	}
	
	public Text getFileLocationText() {
		return fileLocationText;
	}
	
	public IFile getDuplicateResource() {
		return duplicateResource;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.wizards.WizardNewFileCreationPage#isDuplicateBEResource(org.eclipse.core.resources.IResource, java.lang.String, java.lang.StringBuilder)
	 */
	@Override
	protected boolean isDuplicateBEResource(IResource resource,
			String resourceName, StringBuilder duplicateFileName) {
		boolean result = false;
		IResource containerResource = StudioResourceUtils.getResourcePathFromContainerPath(getContainerFullPath());
		String containerResourceName = resourceContainer.getResourceName();
		result = StudioResourceUtils.isDuplicateBEResource(containerResource, containerResourceName, duplicateFileName);
		return result;
	}
	
	public String getRuleTemplatePath() {
		return templateText.getText();
	}

}
