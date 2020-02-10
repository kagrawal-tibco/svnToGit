package com.tibco.cep.studio.debug.ui.launch;

import java.text.MessageFormat;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.studio.debug.core.launch.IStudioDebugLaunchConfigurationConstants;
import com.tibco.cep.studio.debug.core.launch.RunProfile;
import com.tibco.cep.studio.debug.ui.StudioDebugUIPlugin;

/*
@author ssailapp
@date Jun 18, 2009 4:33:31 PM
 */
@SuppressWarnings({"unused"})
public class RemoteTab extends AbstractLaunchConfigurationTab implements ILaunchConfigurationTab {

	private final static String TAB_ID = "com.tibco.cep.studio.debug.ui.launch.remotetab";
	
	protected static String debugString = "-Xdebug -Xrunjdwp:transport=dt_socket,address={0},suspend=n,server=y";
	
	private Text tHost, tPort, tdbgStr;
	private Label lHost, lPort;
	private Button bAttach;
	private WidgetListener wListener;

	public RemoteTab() {
		wListener = new WidgetListener();
	}
	
	@Override
	public boolean canSave() {
		return validateLocal();
	}

	@Override
	public void createControl(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		GridLayout gl = new GridLayout(1, false);
		comp.setLayout(gl);

		Group group= new Group(comp, SWT.NONE);
		group.setText("Remote Connection:");
		group.setLayout(new GridLayout(1, false));
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		new Label(group,SWT.NONE).setText("Remote debugging allows you to connect to a running JVM");
		
		new Label(group,SWT.NONE).setText("");
		new Label(group,SWT.NONE).setText("Use the following VM arguments for running remote JVM");
		new Label(group,SWT.NONE).setText("(You may copy and paste them in the TRA file to set the \"java.extended.properties\" property)");
		
		tdbgStr = new Text(group,SWT.NONE|SWT.BORDER|SWT.READ_ONLY);
		GridData tgd = new GridData(GridData.FILL_BOTH);
		tgd.heightHint=80;
		tdbgStr.setLayoutData(tgd);
		
		tdbgStr.addModifyListener(wListener);
		
		bAttach = new Button(group, SWT.CHECK);
		bAttach.setText("Attach");
//		bAttach.addSelectionListener(wListener);
		bAttach.setVisible(false);
		bAttach.setEnabled(false);
		
		Composite comp1 = new Composite(group, SWT.NONE);
		comp1.setLayout(new GridLayout(2, false));
		lHost = new Label(comp1, SWT.NONE);
		lHost.setText("Host: ");
		tHost = new Text(comp1, SWT.BORDER);
		tHost.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		tHost.addModifyListener(wListener);
		comp1.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		Composite comp2 = new Composite(group, SWT.NONE);
		comp2.setLayout(new GridLayout(2, false));
		lPort = new Label(comp2, SWT.NONE);
		lPort.setText("Port: ");
		tPort = new Text(comp2, SWT.BORDER);
		tPort.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		tPort.addModifyListener(wListener);		
		comp2.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));		
		
		setControl(comp);
	}

	@Override
	public Image getImage() {
		return StudioDebugUIPlugin.getImage("icons/remotetab.gif");
	}

	@Override
	public String getMessage() {
		return ("Attach to remote engine");
	}

	@Override
	public String getName() {
		return ("Remote");
	}
	
	public String getId() {
		return (TAB_ID);
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		try { 
			// TODO: Get the defaults from the Preference setting
			boolean defAttach = false;
			String defHost = RunProfile.DEFAULT_DEBUG_HOST;
			String defPort = RunProfile.DEFAULT_DEBUG_PORT;
			
//			boolean isAttach = configuration.getAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_ISATTACH, defAttach);
			String host = configuration.getAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_HOST, defHost);
			String port = configuration.getAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_PORT, defPort);
			if(host.isEmpty()) {
				host = RunProfile.DEFAULT_DEBUG_HOST;
			}
			if(port.isEmpty()) {
				port = RunProfile.DEFAULT_DEBUG_PORT;
			}
			
//			bAttach.setSelection(isAttach);
			tdbgStr.setText(MessageFormat.format(debugString, port));
			tHost.setText(host);
			tPort.setText(port);
//			enableRemotePropertyWidgets(isAttach);
		} catch (CoreException ce) {
			ce.printStackTrace();
		}
	}

	@Override
	public boolean isValid(ILaunchConfiguration launchConfig) {
//		if (bAttach.getSelection()) {
//		}
		return validateLocal();
	}

	private boolean validateLocal() {
		if (tHost.getText().trim().equals("")) {
			setErrorMessage("Host name can't be blank.");
			return false;
		} else if (tPort.getText().trim().equals("")) {
			setErrorMessage("Port number can't be blank.");
			return false;
		} else { 
			try {
				int portnum = Integer.valueOf(tPort.getText().trim());
			} catch (Exception e) {
				setErrorMessage("Invalid Port number specified.");
				return false;
			}
		}
		setErrorMessage(null);
		return true;
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
//		configuration.setAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_ISATTACH, bAttach.getSelection());
		configuration.setAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_HOST, tHost.getText());
		configuration.setAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_PORT, tPort.getText());
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		// TODO: Get the defaults from the Preference setting
		boolean defAttach = false;
		String defHost = "localhost";
		String defPort = "25192";
		
//		configuration.setAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_ISATTACH, defAttach);
		configuration.setAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_HOST, defHost);
		configuration.setAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_PORT, defPort);
	}
	
	private class WidgetListener implements ModifyListener, SelectionListener {
		public void modifyText(ModifyEvent e) {
			Object source = e.getSource();
			if(source == tPort){
				tdbgStr.setText(MessageFormat.format(debugString, tPort.getText()));
			}
			updateLaunchConfigurationDialog();
		}
		
		public void widgetDefaultSelected(SelectionEvent e) {
		}
		
		public void widgetSelected(SelectionEvent e) {
			Object source = e.getSource();
			if (source == bAttach) {
//				enableRemotePropertyWidgets(bAttach.getSelection());
			} 
			updateLaunchConfigurationDialog();
		}
	}

	private void enableRemotePropertyWidgets(boolean en) {
		lHost.setEnabled(en);
		tHost.setEnabled(en);
		lPort.setEnabled(en);
		tPort.setEnabled(en);
	}
}
