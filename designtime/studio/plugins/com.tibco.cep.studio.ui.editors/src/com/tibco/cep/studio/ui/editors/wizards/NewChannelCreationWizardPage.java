package com.tibco.cep.studio.ui.editors.wizards;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.tibco.cep.studio.ui.editors.channels.ChannelFormFeederDelegate;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;
import com.tibco.cep.studio.ui.wizards.EntityFileCreationWizard;

/**
 * 
 * @author sasahoo
 *
 */
public class NewChannelCreationWizardPage extends EntityFileCreationWizard{

	
	private Combo driverType_Combo;
    private NewChannelWizard wizard;
    private ChannelFormFeederDelegate delegate = null;
    
	public ChannelFormFeederDelegate getDelegate() {
		return delegate;
	}

	/**
	 * @param pageName
	 * @param selection
	 * @param type
	 */
	public NewChannelCreationWizardPage(String pageName, IStructuredSelection selection, String type, NewChannelWizard wizard ) {
		super(pageName, selection, type);
		this.wizard = wizard;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.wizards.EntityFileCreationWizard#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		Composite controls = (Composite) super.getControl();
		createLabel(controls, Messages.getString("new.channel.wizard.driver.type"), 0);
		
		
		try {
			if(wizard.getProject()!=null){
				delegate = new ChannelFormFeederDelegate(wizard.getProject().getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		Object[] values = DRIVER_TYPE.VALUES.toArray();
//		String[] domainDataTypes = new String[values.length];
//		int i = 0;
//		for (DRIVER_TYPE obj : list) {
//			domainDataTypes[i++] = obj.toString();
//		}
		
		driverType_Combo = new Combo(controls, SWT.BORDER | SWT.READ_ONLY);

		if(delegate != null){
			IResource resource  = StudioResourceUtils.getResourcePathFromContainerPath(getContainerFullPath());
			String projectName = resource == null ? null : resource.getProject().getName();
			driverType_Combo.setItems(delegate.getConfiguredDriverTypes(projectName));
			driverType_Combo.select(0);
		}
//		driverType_Combo.setText(DRIVER_TYPE.LOCAL.getLiteral());
		GridData dataTypeCombo_Data = new GridData(GridData.FILL_HORIZONTAL);
		driverType_Combo.setLayoutData(dataTypeCombo_Data);
		setControl(controls);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.wizards.EntityFileCreationWizard#delegateProject()
	 */
	protected void delegateProject(){
		try {
			if(delegate == null){
				delegate = new ChannelFormFeederDelegate(project.getName());
				driverType_Combo.setItems(delegate.getConfiguredDriverTypes(project.getName()));
				driverType_Combo.select(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected Label createLabel(Composite container, String labelstr, int indent) {
		Label label = new Label(container, SWT.NONE);
		GridData gData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		gData.horizontalIndent = indent;
		label.setLayoutData(gData);
		label.setText(labelstr);
		return label;
	}
	
	public String getDriverType() {
		return driverType_Combo.getText();
	}
}