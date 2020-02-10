package com.tibco.cep.bpmn.ui.wizards;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.Messages;

/**
 * 
 * @author sasahoo
 *
 */
public class SubProcessWizardPage extends WizardPage implements ModifyListener {
	
    protected IProject fProject;
	@SuppressWarnings("unused")
	private EObjectWrapper<EClass, EObject> process;
	private Text subProcessNameText;
	private String subProcName = "";
	
	/**
	 * @param pageTitle
	 * @param project
	 * @param process
	 */
	protected SubProcessWizardPage(String pageTitle, 
			                  IProject project, 
			                  EObjectWrapper<EClass, EObject> process) {
		super(pageTitle);
		this.fProject = project;
		this.process = process;
	}

	/**
	 * 
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.EMBEDDED);
		container.setLayout(new GridLayout(2, false));
		container.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		createLabel(container, Messages.getString("new.subprocess.wizard.name"));
		subProcessNameText = createText(container);
		subProcessNameText.addModifyListener(this);
		
		setControl(container);
		setPageComplete(false);
	}
	
	/**
	 * @param container
	 * @param label
	 * @return
	 */
	protected Label createLabel(Composite container, String label) {
		return createLabel(container, label, 0);
	}
	
	/**
	 * @param container
	 * @param labelstr
	 * @param indent
	 * @return
	 */
	protected Label createLabel(Composite container, String labelstr, int indent) {
		Label label = new Label(container, SWT.NONE);
		GridData gData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		gData.horizontalIndent = indent;
		label.setLayoutData(gData);
		label.setText(labelstr);
		return label;
	}
	
	/**
	 * @param container
	 * @return
	 */
	protected Text createText(Composite container) {
		Text text = new Text(container, SWT.BORDER);
		GridData gData = new GridData(GridData.FILL_HORIZONTAL);
		gData.widthHint = 150;
		text.setLayoutData(gData);
		return text;
	}

	@Override
	public void modifyText(ModifyEvent e) {
		subProcName = subProcessNameText.getText();
		if (subProcName.isEmpty()) {
			setPageComplete(false);
			return;
		}
		setPageComplete(true);
	}
	
	public String getSubProcessName() {
		return subProcName;
	}
}