package com.tibco.cep.studio.ui.editors.clusterconfig;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.rt.AddonUtil;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.AgentClass;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

/*
@author ssailapp
@date Dec 9, 2009 3:57:10 PM
 */

public class NewAgentClassDialog extends Dialog {
	private Text tName;
	private Combo cType;
	private String name;
	private String type;
	private ArrayList<String> types;
	private ClusterConfigModelMgr modelmgr;
	
	public NewAgentClassDialog(Shell shell, ClusterConfigModelMgr modelmgr) {
		super(shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		this.modelmgr = modelmgr;
		initializeAgentClassTypes();
	}

	private void initializeAgentClassTypes() {
		types = new ArrayList<String>();
		
		types.add(AgentClass.AGENT_TYPE_INFERENCE);
		if (!AddonUtil.isExpressEdition()) {
			types.add(AgentClass.AGENT_TYPE_CACHE);
			/* types.add(AgentClass.AGENT_TYPE_MM); */ //BE-27585 Remove MM agent class type from agent class.
		}
		
		types.addAll(modelmgr.getAgentClassContributions());
	}

	public void open() { 
		final Shell shell = getParent();
		final Shell dialog = new Shell(shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE | SWT.TITLE | SWT.BORDER);
		dialog.setText("New Agent Class");
		dialog.setImage(ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_AGENT));
		dialog.setLayout(new GridLayout(2, false));

		Label lName = PanelUiUtil.createLabel(dialog, "Agent Class Name: ");
		tName = PanelUiUtil.createText(dialog);

		Label lType = PanelUiUtil.createLabel(dialog, "Agent Class Type: ");
		cType = PanelUiUtil.createComboBox(dialog, types.toArray(new String[0]));

		Label lFiller = PanelUiUtil.createLabelFiller(dialog);
		lFiller = PanelUiUtil.createLabelFiller(dialog);
		
		Composite butComp = new Composite(dialog, SWT.NONE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		butComp.setLayoutData(gd);
		butComp.setLayout(new GridLayout(3, false));
		
		lFiller = PanelUiUtil.createLabelFiller(butComp);
		
		final Button bOK = new Button(butComp, SWT.PUSH);
		bOK.setText("OK");
		gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		gd.widthHint = 80;
		bOK.setLayoutData(gd);
		Button bCancel = new Button(butComp, SWT.PUSH);
		bCancel.setText("Cancel");
		gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		gd.widthHint = 80;
		bCancel.setLayoutData(gd);
		
		bOK.setEnabled(false);
		
		tName.addListener(SWT.Modify, new Listener() {
			@Override
			public void handleEvent(Event event) {
				//TODO - Display error message in the dialog itself
				if (tName.getText().indexOf(' ') >= 0) {
					PanelUiUtil.showErrorMessage("Agent Class name can't contain spaces.");
					tName.setText(tName.getText().trim());
				}
				bOK.setEnabled(tName.getText().length() > 0);
			}
		});

		bOK.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				name = tName.getText();
				type = cType.getText();
				dialog.dispose();
			}
		});

		bCancel.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				name = null;
				dialog.dispose();
			}
		});

		dialog.addListener(SWT.Traverse, new Listener() {
			public void handleEvent(Event event) {
				if (event.detail == SWT.TRAVERSE_ESCAPE) {
					event.doit = false;
					dialog.dispose();
				}
			}
		});

		tName.setText("");
		dialog.pack();

        Rectangle shellBounds = shell.getBounds();
        Point dialogSize = dialog.getSize();
        dialog.setLocation(shellBounds.x + (shellBounds.width - dialogSize.x) / 2, shellBounds.y + (shellBounds.height - dialogSize.y) / 4);

        dialog.open();
        
		Display display = shell.getDisplay();
		while (!dialog.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}
	
	public String getName() {
		return name;
	}
	
	public String getType() { 
		return type;
	}
}
