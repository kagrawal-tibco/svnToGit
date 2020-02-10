package com.tibco.cep.studio.ui.editors.clusterconfig;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IFormPart;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.MMAgent.ActionConfigList.ActionConfig;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.MMAgent.ActionConfigList.ActionConfig.Alert;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.MMAgent.ActionConfigList.ActionConfig.ExecCommand;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.MMAgent.ActionConfigList.ActionConfig.HealthLevel;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.MMAgent.ActionConfigList.ActionConfig.SendEmail;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.editors.utils.BlockUtil;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

/*
@author ssailapp
@date Apr 29, 2010 12:18:37 AM
 */

public class NodeActionConfigPage extends ClusterNodeDetailsPage {

	private static String ALERT = "Alert";
	private static String HEALTH_LEVEL = "Health Level";
	private static String EXECUTE_COMMAND = "Execute Command";
	private static String SEND_EMAIL = "Send Email";

	private ActionConfig actionConfig;
	private Label lSevVal;
	private Text tId, tPath, tSevVal;
	private Text tCommand, tTo, tCc, tSubject, tMessage;
	private Combo cConditionType, cActionType;
	private Group gCondition, gAction;
	
	private StackLayout actionTabLayout;
	private Control actionTabs[];
	private Composite actionComp;
	
	public NodeActionConfigPage(ClusterConfigModelMgr modelmgr, TreeViewer viewer) {
		super(modelmgr, viewer);
	}

	@Override
	public void createContents(Composite parent) {
		super.createContents(parent);
		
		PanelUiUtil.createLabel(client, "Action ID: ");
		tId = PanelUiUtil.createText(client);
		tId.addListener(SWT.Modify, getIdModifyListener());
		
		gCondition = new Group(client, SWT.NONE);
		gCondition.setLayout(new GridLayout(2, false));
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		gCondition.setLayoutData(gd);
		gCondition.setText("Trigger Condition");
		
		PanelUiUtil.createLabel(gCondition, "Trigger Condition: ");
		cConditionType = PanelUiUtil.createComboBox(gCondition, new String[]{ALERT, HEALTH_LEVEL});
		cConditionType.addListener(SWT.Selection, getConditionChangeListener());
		tPath = createConditionTextField("Path: ", "path");
		lSevVal = PanelUiUtil.createLabel(gCondition, "Severity: ");
		tSevVal = PanelUiUtil.createText(gCondition);
		tSevVal.addListener(SWT.Modify, getConditionTextModifyListener(tSevVal, "sevval"));
		gCondition.pack();
		
		gAction = new Group(client, SWT.NONE);
		gAction.setLayout(new GridLayout(2, false));
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		gAction.setLayoutData(gd);
		gAction.setText("Action");
		
		PanelUiUtil.createLabel(gAction, "Action: ");
		cActionType = PanelUiUtil.createComboBox(gAction, new String[]{EXECUTE_COMMAND, SEND_EMAIL});
		cActionType.addListener(SWT.Selection, getActionChangeListener());
		createActionTabs(gAction);
		gAction.pack();
		
		toolkit.paintBordersFor(section);
		section.setClient(client);
	}
	
	private Listener getIdModifyListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				boolean updated = modelmgr.updateActionConfigId(actionConfig, tId.getText());
				if (updated)
					BlockUtil.refreshViewer(viewer);
			}
		};
		return listener;
	}
	
	private Text createConditionTextField(String label, String modelId) {
		PanelUiUtil.createLabel(gCondition, label);
		Text tField = PanelUiUtil.createText(gCondition);
		tField.addListener(SWT.Modify, getConditionTextModifyListener(tField, modelId));
		return tField;
	}
	
	private void createActionTabs(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayout(PanelUiUtil.getCompactWidthGridLayout(1, false));
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		comp.setLayoutData(gd);
		
		actionComp = new Composite(comp, SWT.NONE);
		actionComp.setLayoutData(new GridData(GridData.FILL_BOTH));
		actionTabLayout = new StackLayout();
		actionComp.setLayout(actionTabLayout);
	    
	    actionTabs = new Control[2];
	    actionTabs[0] = createExecCommandTab();
	    actionTabs[1] = createSendEmailTab();
        actionTabLayout.topControl = actionTabs[0]; 
        
		actionComp.pack();
		comp.pack();
	}
	
	private Control createExecCommandTab() {
		Composite comp = new Composite(actionComp, SWT.NONE);
		comp.setLayout(PanelUiUtil.getCompactGridLayout(2, false));
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));

		PanelUiUtil.createLabel(comp, "Command: ");
		tCommand = PanelUiUtil.createText(comp);
		tCommand.addListener(SWT.Modify, getExecCommandListener());
		
		comp.pack();
		return comp;
	}

	private Control createSendEmailTab() {
		Composite comp = new Composite(actionComp, SWT.NONE);
		comp.setLayout(PanelUiUtil.getCompactWidthGridLayout(2, false));
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));

		tTo = createSendEmailTextField(comp, "To: ", "to");
		tCc = createSendEmailTextField(comp, "Cc: ", "cc");
		tSubject = createSendEmailTextField(comp, "Subject: ", "subject");

		Label lMessage = PanelUiUtil.createLabel(comp, "Message: ");
		lMessage.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));
		tMessage = PanelUiUtil.createTextMultiLine(comp, 100);
		tMessage.addListener(SWT.Modify, getSendEmailModifyListener(tMessage, "msg"));

		comp.pack();
		return comp;
	}
	
	private Text createSendEmailTextField(Composite comp, String label, String modelId) {
		PanelUiUtil.createLabel(comp, label);
		Text tField = PanelUiUtil.createText(comp);
		tField.addListener(SWT.Modify, getSendEmailModifyListener(tField, modelId));
		return tField;
	}
	
	private Listener getConditionChangeListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				boolean updated = modelmgr.updateActionConfigConditionType(actionConfig, cConditionType.getText().equals(ALERT));
				if (updated)
					BlockUtil.refreshViewer(viewer);
				if (cConditionType.getText().equals(ALERT)) {
					lSevVal.setText("Severity: ");
					tSevVal.setText(((Alert)actionConfig.triggerCondition.trigger).severity);
				} else { 
					lSevVal.setText("Health Level: ");
					tSevVal.setText(((HealthLevel)actionConfig.triggerCondition.trigger).value);
				}
				tPath.setText(actionConfig.triggerCondition.trigger.path);
			}
		};
		return listener;
	}
	
	private Listener getConditionTextModifyListener(final Text tField, final String key) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				boolean updated = modelmgr.updateActionConfigCondition(actionConfig, key, tField.getText());
				if (updated)
					BlockUtil.refreshViewer(viewer);
			}
		};
		return listener;
	}
	
	private Listener getActionChangeListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				boolean updated = modelmgr.updateActionConfigActionType(actionConfig, cActionType.getText().equals(EXECUTE_COMMAND));
				if (updated)
					BlockUtil.refreshViewer(viewer);
				if (cActionType.getText().equals(EXECUTE_COMMAND)) {
					setActionCompStackTopControl(0);
					tCommand.setText(((ExecCommand) actionConfig.action.actionElement).command);
				} else {
					setActionCompStackTopControl(1);
					SendEmail sendEmail = (SendEmail) actionConfig.action.actionElement;
					tTo.setText(sendEmail.values.get("to"));
					tCc.setText(sendEmail.values.get("cc"));
					tSubject.setText(sendEmail.values.get("subject"));
					tMessage.setText(sendEmail.values.get("msg"));
				}
			}
		};
		return listener;
	}

	private Listener getExecCommandListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				boolean updated = modelmgr.updateActionConfigExecCommand(actionConfig, tCommand.getText());
				if (updated)
					BlockUtil.refreshViewer(viewer);
			}
		};
		return listener;
	}
	
	private Listener getSendEmailModifyListener(final Text tField, final String key) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				modelmgr.updateActionConfigSendEmail(actionConfig, key, tField.getText());
			}
		};
		return listener;
	}
	
	private void setActionCompStackTopControl(int top) {
		actionTabLayout.topControl = actionTabs[top];
		actionComp.layout();
	}
	
	@Override
	public void selectionChanged(IFormPart part, ISelection selection) {
		IStructuredSelection ssel = (IStructuredSelection)selection;
		if (ssel.size() == 1)
			actionConfig = ((ActionConfig) ssel.getFirstElement());
		else
			actionConfig = null;
		update();
	}
	
	private void update() {
		if (actionConfig != null) {
			tId.setText(actionConfig.id);
			if (actionConfig.triggerCondition.trigger instanceof Alert) {
				cConditionType.setText(ALERT);
			} else {
				cConditionType.setText(HEALTH_LEVEL);
			}
			cConditionType.notifyListeners(SWT.Selection, new Event());
			if (actionConfig.action.actionElement instanceof SendEmail) {
				cActionType.setText(SEND_EMAIL);
			} else {
				cActionType.setText(EXECUTE_COMMAND);
			}
			cActionType.notifyListeners(SWT.Selection, new Event());
		}
	}

	@Override
	public Listener getListener(Control field, String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getValue(String key) {
		// TODO Auto-generated method stub
		return null;
	}
}


