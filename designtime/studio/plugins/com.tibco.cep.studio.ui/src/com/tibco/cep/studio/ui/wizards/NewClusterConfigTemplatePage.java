package com.tibco.cep.studio.ui.wizards;

 import java.util.ArrayList;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.rt.AddonUtil;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

/*
@author ssailapp
@date Jan 29, 2010 1:49:52 PM
 */

public class NewClusterConfigTemplatePage extends WizardPage implements Listener {

	private Label lOm, lAgent;
	private Text tDesc;
	private Combo cOm;
	private Table taAgent;
	
	public static final String OM_TYPE_IN_MEMORY = "In Memory";
	public static final String OM_TYPE_CACHE = "Cache";
//	public static final String OM_TYPE_BDB = "Berkeley DB";
	
	protected NewClusterConfigTemplatePage(String pageName) {
		super(pageName);
		setTitle("Object Manager Selection");
		setDescription("Select an object management type to be used for creating this Cluster Deployment Descriptor");
	}

	@Override
	public void handleEvent(Event event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createControl(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayout(new GridLayout(1, false));
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Group group = new Group(comp, SWT.NONE);
		//group.setText("Template Selection");
		group.setLayout(new GridLayout(2, false));
		group.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		createOmTypes(group);
		PanelUiUtil.createLabelFiller(group);
		PanelUiUtil.createLabelFiller(group);
		
		//createAgentClassesTable(group); //TODO
		
		/*	TODO
		PanelUiUtil.createLabel(group, "Description:");
		PanelUiUtil.createLabelFiller(group);
		tDesc = PanelUiUtil.createTextMultiLine(group);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		gd.heightHint = 40;
		tDesc.setLayoutData(gd);
		tDesc.setEnabled(false);
		*/
		
		group.pack();
		comp.pack();
		setControl(comp);
	}
	
	
	private void createOmTypes(Composite comp) {
		ArrayList<String> omTypes = new ArrayList<String>();
		omTypes.add(OM_TYPE_IN_MEMORY);
		if (!AddonUtil.isExpressEdition()) {
			omTypes.add(OM_TYPE_CACHE);
//			omTypes.add(OM_TYPE_BDB);
		}
		
		lOm = PanelUiUtil.createLabel(comp, "Object Management Type:");
		//lOm.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));
		//lOm.setLayoutData(new GridData(GridData.BEGINNING));
		cOm = PanelUiUtil.createComboBox(comp, omTypes.toArray(new String[0]));
		cOm.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		cOm.addListener(SWT.Selection, getOmSelectionChangeListener());
		cOm.notifyListeners(SWT.Selection, new Event());
	}
	
	private Listener getOmSelectionChangeListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				NewClusterConfigNamePage namePage = ((NewClusterConfigurationWizard)getWizard()).cddNamePage;
				String omType = cOm.getText();
				namePage.setOmType(omType);
			}
		};
		return listener;
	}

	private void createAgentClassesTable(Composite comp) {
		lAgent = PanelUiUtil.createLabel(comp, "Agent Class types:");
		lAgent.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));
		taAgent = new Table(comp, SWT.CHECK | SWT.BORDER);
		taAgent.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		String agents[] = new String[]{ "Inference", "Cache", "Query", "Dashboard", "Monitoring and Management"};
		for (String agent: agents) {
			TableItem item = new TableItem(taAgent, SWT.NONE);
			item.setText(agent);
		}
	}
	
    public boolean isPageComplete() {
    	if (getErrorMessage() != null)
    		return false;
    	return true;
    }
    
    public int getOmType() {
    	return cOm.getSelectionIndex(); 
    }
}
