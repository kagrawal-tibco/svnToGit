package com.tibco.cep.studio.ui.editors.clusterconfig;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IFormPart;

import com.tibco.be.util.config.ConfigNS.Elements;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.LogConfig;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.core.util.GvUtil;
import com.tibco.cep.studio.ui.editors.utils.BlockUtil;
import com.tibco.cep.studio.ui.util.GvField;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

/*
@author ssailapp
@date Feb 8, 2010 1:16:52 PM
 */

public class NodeLogConfigPage extends ClusterNodeDetailsPage {
	
	private Text tLogConfigName, tDir, tFileName, tClass, tArgs, tEncoding;
	private Button bLogConfigEnable, bFiles, bAppend, bTerminal, bLineLayout, bIncludeOutput, bIncludeError;
	private LogConfig logConfig;
	private Label lFiles, lDir, lFileName, lAppend, lTerminal, lIncludeOutput, lIncludeError, lLineLayout, lClass, lArgs, lEncoding;
	private Group gFiles, gTerminal, gLineLayout;
	private GvField tgRoles,tgMaxNum,tgMaxSize;
	public NodeLogConfigPage(ClusterConfigModelMgr modelmgr, TreeViewer viewer) {
		super(modelmgr, viewer);
	}
	
	@Override
	public void createContents(Composite parent) {
		super.createContents(parent);
		
		Label lLogConfigName = PanelUiUtil.createLabel(client, "Name: ");
		tLogConfigName = PanelUiUtil.createText(client);
		tLogConfigName.addListener(SWT.Modify, getLogConfigNameModifyListener());
		
		Label lLogConfigEnable = PanelUiUtil.createLabel(client, "Enable: ");
		bLogConfigEnable = PanelUiUtil.createCheckBox(client, "");
		bLogConfigEnable.addListener(SWT.Selection, getLogConfigEnablementListener());
		
		/*lLevels = PanelUiUtil.createLabel(client, "Levels: ");
		tRoles = PanelUiUtil.createText(client);
		tRoles.addListener(SWT.Modify, getLogConfigRolesModifyListener());*/
		
		tgRoles = createGvTextField(client,"Levels: ", Elements.ROLES.localName);
		
		//TODO - Add selection dialog for possible log levels
		
		Composite client1 = new Composite(client, SWT.NONE);
		GridLayout gl = new GridLayout(1, false); 
		gl.marginWidth = 0;
		client1.setLayout(gl);
		GridData gd1 = new GridData(GridData.FILL_HORIZONTAL);
		gd1.horizontalSpan = 2;
		client1.setLayoutData(gd1);
			
		gFiles = new Group(client1, SWT.NONE);
		gFiles.setText("Files");
		gFiles.setLayout(new GridLayout(2, false));
		gFiles.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		lFiles = PanelUiUtil.createLabel(gFiles, "Enable: ");
		bFiles = PanelUiUtil.createCheckBox(gFiles, "");
		bFiles.addListener(SWT.Selection, getFilesEnablementListener());
		lDir = PanelUiUtil.createLabel(gFiles, "Directory: ");
		tDir = PanelUiUtil.createText(gFiles);
		tDir.addListener(SWT.Modify, getFilesModifyListener(tDir, Elements.DIR.localName));
		lFileName = PanelUiUtil.createLabel(gFiles, "Name: ");
		tFileName = PanelUiUtil.createText(gFiles);
		tFileName.addListener(SWT.Modify, getFilesModifyListener(tFileName, Elements.NAME.localName));

		tgMaxNum = createGvTextField(gFiles,"Max Number: ", Elements.MAX_NUMBER.localName);
		tgMaxSize = createGvTextField(gFiles,"Max Size: ", Elements.MAX_SIZE.localName);

		lAppend = PanelUiUtil.createLabel(gFiles, "Append: ");
		bAppend = PanelUiUtil.createCheckBox(gFiles, "");
		bAppend.addListener(SWT.Selection, getFilesModifyListener(bAppend, Elements.APPEND.localName));
		gFiles.pack();

		PanelUiUtil.createSpacer(toolkit, client1, 1);
		
		gTerminal = new Group(client1, SWT.NONE);
		gTerminal.setText("Send to Terminal");
		gTerminal.setLayout(new GridLayout(2, false));
		gTerminal.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		lTerminal = PanelUiUtil.createLabel(gTerminal, "Enable: ");
		bTerminal = PanelUiUtil.createCheckBox(gTerminal, "");
		bTerminal.addListener(SWT.Selection, getTerminalEnablementListener());
		lIncludeOutput = PanelUiUtil.createLabel(gTerminal, "Include Output: ");
		bIncludeOutput = PanelUiUtil.createCheckBox(gTerminal, "");
		bIncludeOutput.addListener(SWT.Selection, getTerminalModifyListener(bIncludeOutput, Elements.SYS_OUT_REDIRECT.localName));
		lIncludeError = PanelUiUtil.createLabel(gTerminal, "Include Error: ");
		bIncludeError = PanelUiUtil.createCheckBox(gTerminal, "");
		bIncludeError.addListener(SWT.Selection, getTerminalModifyListener(bIncludeError, Elements.SYS_ERR_REDIRECT.localName));
		lEncoding = PanelUiUtil.createLabel(gTerminal, "Encoding: ");
		tEncoding = PanelUiUtil.createText(gTerminal);
		tEncoding.addListener(SWT.Modify, getTerminalModifyListener(tEncoding, Elements.ENCODING.localName));
		gTerminal.pack();
		
		PanelUiUtil.createSpacer(toolkit, client1, 1);
		
		gLineLayout = new Group(client1, SWT.NONE);
		gLineLayout.setText("Custom Line Layout");
		gLineLayout.setLayout(new GridLayout(2, false));
		gLineLayout.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		lLineLayout = PanelUiUtil.createLabel(gLineLayout, "Enable: ");
		bLineLayout = PanelUiUtil.createCheckBox(gLineLayout, "");
		lClass = PanelUiUtil.createLabel(gLineLayout, "Class: ");
		tClass = PanelUiUtil.createText(gLineLayout);
		tClass.addListener(SWT.Modify, getLayoutModifyListener(tClass, Elements.CLASS.localName));
		lArgs = PanelUiUtil.createLabel(gLineLayout, "Arguments: ");
		tArgs = PanelUiUtil.createText(gLineLayout);
		tArgs.addListener(SWT.Modify, getLayoutModifyListener(tArgs, Elements.ARG.localName));
		gLineLayout.pack();
		bLineLayout.addListener(SWT.Selection, getLayoutEnablementListener());

		client1.pack();
		client.pack();
		toolkit.paintBordersFor(section);
		section.setClient(client);

	}
	
	private Listener getLogConfigNameModifyListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (logConfig == null)
					return;
				String newName = tLogConfigName.getText();
				String updated = modelmgr.updateLogConfigName(logConfig, newName);
				if (updated.equals("true"))
					BlockUtil.refreshViewerForError(viewer,0,tLogConfigName);
				if (updated.equals("trueError")){
					BlockUtil.refreshViewerForError(viewer,1,tLogConfigName);
				}
			}
		};
		return listener;
	}

	private Listener getLogConfigEnablementListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				boolean en = bLogConfigEnable.getSelection();
				modelmgr.updateLogConfigEnable(logConfig, en);
				tgRoles.setEnabled(en);
				
				gFiles.setEnabled(en);
				lFiles.setEnabled(en);
				bFiles.setEnabled(en);
				lDir.setEnabled(bFiles.getSelection() && en);
				tDir.setEnabled(bFiles.getSelection() && en);
				lFileName.setEnabled(bFiles.getSelection() && en);
				tFileName.setEnabled(bFiles.getSelection() && en);
				
				tgMaxNum.setEnabled(bFiles.getSelection() && en);
				tgMaxSize.setEnabled(bFiles.getSelection() && en);
				
				lAppend.setEnabled(bFiles.getSelection() && en);
				bAppend.setEnabled(bFiles.getSelection() && en);
				
				gTerminal.setEnabled(en);
				lTerminal.setEnabled(en);
				bTerminal.setEnabled(en);
				lIncludeOutput.setEnabled(bTerminal.getSelection() && en);
				bIncludeOutput.setEnabled(bTerminal.getSelection() && en);
				lIncludeError.setEnabled(bTerminal.getSelection() && en);
				bIncludeError.setEnabled(bTerminal.getSelection() && en);
				lEncoding.setEnabled(bTerminal.getSelection() && en);
				tEncoding.setEnabled(bTerminal.getSelection() && en);
				
				gLineLayout.setEnabled(en);
				lLineLayout.setEnabled(en);
				bLineLayout.setEnabled(en);
				lClass.setEnabled(bLineLayout.getSelection() && en);
				tClass.setEnabled(bLineLayout.getSelection() && en);
				lArgs.setEnabled(bLineLayout.getSelection() && en);
				tArgs.setEnabled(bLineLayout.getSelection() && en);
				
				validateFields();
			}
		};
		return listener;
	}
	
	/*private Listener getLogConfigRolesModifyListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				modelmgr.updateLogConfigRoles(logConfig, tRoles.getText());
			}
		};
		return listener;
	}*/
	
	private Listener getFilesEnablementListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				boolean en = bFiles.getSelection();
				modelmgr.updateLogConfigFilesEnable(logConfig, en);
				lDir.setEnabled(en);
				tDir.setEnabled(en);
				lFileName.setEnabled(en);
				tFileName.setEnabled(en);
				tgMaxNum.setEnabled(en);
				tgMaxSize.setEnabled(en);
				lAppend.setEnabled(en);
				bAppend.setEnabled(en);
				validateFields();
			}
		};
		return listener;
	}

	private Listener getFilesModifyListener(final Control field, final String key) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				String value = getFieldValue(field);
				if (value != null) {
					modelmgr.updateLogConfigFilesValue(logConfig, key, value);
				}
				validateFields();
			}
		};
		return listener;
	}
	
	private String getFieldValue(Control field) {
		String value = null;
		if (field instanceof Text) {
			value = ((Text)field).getText();
		} else if (field instanceof Button) {
			value = new Boolean(((Button)field).getSelection()).toString();
		}
		return value;
	}
	
	
	private Listener getTerminalEnablementListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				boolean en = bTerminal.getSelection();
				modelmgr.updateLogConfigTerminalEnable(logConfig, en);
				lIncludeOutput.setEnabled(en);
				bIncludeOutput.setEnabled(en);
				lIncludeError.setEnabled(en);
				bIncludeError.setEnabled(en);
				lEncoding.setEnabled(en);
				tEncoding.setEnabled(en);
			}
		};
		return listener;
	}
	
	private Listener getTerminalModifyListener(final Control field, final String key) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				String value = getFieldValue(field);
				if (value != null) {
					modelmgr.updateLogConfigTerminalValue(logConfig, key, value);
				}
			}
		};
		return listener;
	}
	
	private Listener getLayoutEnablementListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				boolean en = bLineLayout.getSelection();
				modelmgr.updateLogConfigLayoutEnable(logConfig, en);
				lClass.setEnabled(en);
				tClass.setEnabled(en);
				lArgs.setEnabled(en);
				tArgs.setEnabled(en);
			}
		};
		return listener;
	}
	
	private Listener getLayoutModifyListener(final Control field, final String key) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				String value = getFieldValue(field);
				if (value != null) {
					modelmgr.updateLogConfigLayoutValue(logConfig, key, value);
				}
			}
		};
		return listener;
	}
	
	@Override
	public void selectionChanged(IFormPart part, ISelection selection) {
		IStructuredSelection ssel = (IStructuredSelection)selection;
		tLogConfigName.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
		if (ssel.size() == 1)
			logConfig = ((LogConfig) ssel.getFirstElement());
		else
			logConfig = null;
		update();
	}
	
	private void update() {
		if (logConfig != null) {
			tLogConfigName.setText(logConfig.id);
			bLogConfigEnable.setSelection(logConfig.enabled);
			bLogConfigEnable.notifyListeners(SWT.Selection, new Event());

			if(GvUtil.isGlobalVar(logConfig.roles)){
				tgRoles.setGvModeValue(logConfig.roles);
				tgRoles.onSetGvMode();
			}else{
				tgRoles.setFieldModeValue(logConfig.roles);
				tgRoles.onSetFieldMode();
			}
			
			bFiles.setSelection(modelmgr.getLogFilesEnabled(logConfig));
			bFiles.notifyListeners(SWT.Selection, new Event());
			tDir.setText(logConfig.files.get(Elements.DIR.localName));
			tFileName.setText(logConfig.files.get(Elements.NAME.localName));
			
			if (GvUtil.isGlobalVar(logConfig.files.get(Elements.MAX_NUMBER.localName))) {
				tgMaxNum.setGvModeValue(logConfig.files.get(Elements.MAX_NUMBER.localName));
				tgMaxNum.onSetGvMode();
			} else {
				tgMaxNum.setFieldModeValue(logConfig.files.get(Elements.MAX_NUMBER.localName));
				tgMaxNum.onSetFieldMode();
			}

			if (GvUtil.isGlobalVar(logConfig.files.get(Elements.MAX_SIZE.localName))) {
				tgMaxSize.setGvModeValue(logConfig.files.get(Elements.MAX_SIZE.localName));
				tgMaxSize.onSetGvMode();
			} else {
				tgMaxSize.setFieldModeValue(logConfig.files.get(Elements.MAX_SIZE.localName));
				tgMaxSize.onSetFieldMode();
			}
			
			bAppend.setSelection(modelmgr.getLogFilesAppend(logConfig));
			
			bTerminal.setSelection(modelmgr.getLogTerminalEnabled(logConfig));
			bTerminal.notifyListeners(SWT.Selection, new Event());
			bIncludeOutput.setSelection(modelmgr.getBooleanValue(logConfig.terminal.get(Elements.SYS_OUT_REDIRECT.localName)));
			bIncludeError.setSelection(modelmgr.getBooleanValue(logConfig.terminal.get(Elements.SYS_ERR_REDIRECT.localName)));
			tEncoding.setText(logConfig.terminal.get(Elements.ENCODING.localName));
			
			bLineLayout.setSelection(modelmgr.getLogLayoutEnabled(logConfig));
			bLineLayout.notifyListeners(SWT.Selection, new Event());
			tClass.setText(logConfig.layout.get(Elements.CLASS.localName));
			tArgs.setText(logConfig.layout.get(Elements.ARG.localName));
			
			validateFields();
		}
	}
	
	public boolean validateFields() {
		boolean valid = true;
		//if (tMaxNum.getEnabled())
			valid &= PanelUiUtil.validateTextField(tgMaxNum.getGvText(), false, true);
		//if (tMaxSize.getEnabled())
			valid &= PanelUiUtil.validateTextField(tgMaxSize.getGvText(), false, true);
		return valid;
	}

	@Override
	public Listener getListener(final Control field, final String key) {
		return new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (key.equals(Elements.ROLES.localName)) {
					if (field instanceof Text) {
						if (GvUtil.isGlobalVar(((Text) field).getText())) {
							modelmgr.updateLogConfigRoles(logConfig, tgRoles.getGvText().getText());
						} else {
							modelmgr.updateLogConfigRoles(logConfig, ((Text) tgRoles.getField()).getText());
						}
					}
				} else if (key.equals(Elements.MAX_NUMBER.localName) || key.equals(Elements.MAX_SIZE.localName)) {
					String value = getFieldValue((Text) field);
					if (value != null) {
						modelmgr.updateLogConfigFilesValue(logConfig, key, value);
					}
					validateFields();

				}

			}
		};
	}

	@Override
	public String getValue(String key) {
		if(logConfig == null){
			return "";
		}
		return modelmgr.getLogConfig(logConfig.id).roles;
	}
}
