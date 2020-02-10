package com.tibco.cep.studio.cluster.topology.properties;

import com.tibco.cep.studio.cluster.topology.editors.ClusterTopologyDiagramManager;
import com.tibco.cep.studio.cluster.topology.model.UserCredentials;
import com.tibco.cep.studio.cluster.topology.model.impl.UserCredentialsImpl;
import com.tibco.cep.studio.cluster.topology.utils.ClusterTopologyUtils;
import com.tibco.cep.studio.cluster.topology.utils.Messages;
import com.tibco.security.AXSecurityException;
import com.tibco.security.ObfuscationEngine;
import com.tomsawyer.graphicaldrawing.TSENodeLabel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author ggrigore
 *
 */
public class ClusterDiagramHostPropertySection extends AbstractClusterTopologyPropertySection {
	
//	protected Text idText;
	protected Text nameText;
	protected Text ipText;
	protected Text usernameText;
	protected Text passwordText;
	protected CCombo osCombo;
	protected CCombo pusCombo;
    protected Composite composite;
	public static boolean localhost = false;
	

	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySection#createControls(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(2,false));
		
		getWidgetFactory().createLabel(composite, Messages.getString("host.hostname"),  SWT.NONE);
		nameText = getWidgetFactory().createText(composite,"",  SWT.BORDER);
		nameText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				nameText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
				nameText.setToolTipText("");
				String oldName = hostResource.getHostname();
				String newName = nameText.getText();
				if (!newName.equalsIgnoreCase(oldName)) {
					if(!((ClusterTopologyDiagramManager)getManager()).checkDuplicateHostResource(newName)) {
						hostResource.setHostname(newName);
						nameText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
						nameText.setToolTipText("");
						TSENodeLabel hostnameLabel = getSelectedObjectLabel();
						if (hostnameLabel != null) {
							hostnameLabel.setName(newName);
							getManager().refreshLabel(hostnameLabel);
						}
					} else {
						nameText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
						String problemMessage = com.tibco.cep.studio.ui.util.Messages.getString("invalid.duplicate.global.var.entry", 
								nameText.getText(), "Host");
						nameText.setToolTipText(problemMessage);
					}
				}
			}
			});
		GridData gd  = new GridData(/*GridData.FILL_HORIZONTAL*/);
		gd.widthHint = 615;
		nameText.setLayoutData(gd);
		
		getWidgetFactory().createLabel(composite, Messages.getString("host.IP"),  SWT.NONE);
		ipText = getWidgetFactory().createText(composite,"",  SWT.BORDER);
		ipText.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent e) {
				ipText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
				ipText.setToolTipText("");
				if (ipText.getText()!= null &&
						hostResource != null &&
						!ipText.getText().equalsIgnoreCase(hostResource.getIp())) {
					
					if (!ClusterTopologyUtils.isValidIP(ipText.getText())){
						ipText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
						String problemMessage = com.tibco.cep.studio.ui.util.Messages.getString("invalid.global.var.entry", ipText.getText(), "Host IP");
						ipText.setToolTipText(problemMessage);
					}
					if(ipText.getText().equalsIgnoreCase("localhost")) {
						hostResource.setIp("127.0.0.1");
					//	localhost= true;
					} else {
					hostResource.setIp(ipText.getText());
					}
				}
			}});
		gd = new GridData(/*GridData.FILL_HORIZONTAL*/);
		gd.widthHint = 615;
		ipText.setLayoutData(gd);
		
		
		getWidgetFactory().createLabel(composite, Messages.getString("host.username"),  SWT.NONE);
		usernameText = getWidgetFactory().createText(composite,"",  SWT.BORDER);
		usernameText.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent e) {
				UserCredentialsImpl ucImpl = hostResource.getUserCredentials();
				if (ucImpl != null){
					if(!usernameText.getText().equalsIgnoreCase(ucImpl.getUsername())){
						ucImpl.setUsername(usernameText.getText());
					}
				}
				else{
					ucImpl = factory.createUserCredentialsImpl(null);
					UserCredentials uc = ucImpl.getUserCredentials();
					uc.setUsername(usernameText.getText());
				}
				
			}});
		gd = new GridData(/*GridData.FILL_HORIZONTAL*/);
		gd.widthHint = 615;
		usernameText.setLayoutData(gd);
		
		getWidgetFactory().createLabel(composite, Messages.getString("host.password"),  SWT.NONE);
		passwordText = getWidgetFactory().createText(composite,"",  SWT.BORDER | SWT.PASSWORD);
		passwordText.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent e) {
				UserCredentialsImpl ucImpl = hostResource.getUserCredentials();
				if (ucImpl != null){
					String decodedPwd = getDecodedString(ucImpl.getPassword());
					if(passwordText.getText() != null 
							&& !passwordText.getText().equalsIgnoreCase(decodedPwd)){
						ucImpl.setPassword(getEncodedString(passwordText.getText()));
					}
				}
				else{
					ucImpl = factory.createUserCredentialsImpl(null);
					UserCredentials uc = ucImpl.getUserCredentials();
					if(passwordText.getText() != null && !passwordText.getText().trim().equals("")) {
						uc.setPassword(getEncodedString(passwordText.getText()));
					}
				}
				
			}});
		gd = new GridData(/*GridData.FILL_HORIZONTAL*/);
		gd.widthHint = 615;
		passwordText.setLayoutData(gd);
		

		getWidgetFactory().createLabel(composite, Messages.getString("host.operatingSystem"),  SWT.NONE);
		
		List<String> list= new ArrayList<String>();
		//TODO:populate actual OS values 
		list.add("Windows");
		list.add("Linux");
		list.add("Solaris");
		list.add("AIX");
		list.add("HP");
		list.add("Mac");

		String[] itemArray = new String[list.size()];
		list.toArray(itemArray);
		osCombo = getWidgetFactory().createCCombo(composite, SWT.READ_ONLY);
		osCombo.setItems(itemArray);

		gd = new GridData();
		gd.widthHint = 200;
		gd.horizontalIndent = 1;
		osCombo.setLayoutData(gd);
		osCombo.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent e) {
				if(!osCombo.getText().equalsIgnoreCase(hostResource.getOsType())){
					hostResource.setOsType(osCombo.getText());
				}
				
			}});
	}
	
	/*
	 * @see org.eclipse.ui.views.properties.tabbed.view.ITabbedPropertySection#refresh()
	 */
	public void refresh() {
		if (tseNode != null) {
			if(hostResource != null){
				if (hostResource.getHostname() != null) {
					nameText.setText(hostResource.getHostname());
				}
				if (hostResource.getIp() == null) {
					ipText.setText("");
				}else{
					ipText.setText(hostResource.getIp());
				}
				if(hostResource.getUserCredentials()!=null){
				UserCredentialsImpl uc = hostResource.getUserCredentials();
				if(uc.getUsername()== null ) {
					usernameText.setText("");
				} else {
					usernameText.setText(uc.getUsername());
						}
				if(uc.getPassword()==null){
					passwordText.setText("");
					
				}else{
					passwordText.setText(getDecodedString(uc.getPassword()));
					
				}
				
				}
				if(hostResource.getOsType()==null)
				{
					osCombo.setText("");
				}else
				osCombo.setText(hostResource.getOsType());
			}
		}
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		super.widgetSelected(e);
	}
	
	private String getEncodedString(String password) {
		String encoded = password;
		try {
			if (!ObfuscationEngine.hasEncryptionPrefix(password)) {
				encoded = ObfuscationEngine.encrypt(password.toCharArray());
			}
		} catch (AXSecurityException e) {
			e.printStackTrace();
		}
		return encoded;
	}
	
	private String getDecodedString(String encoded) {
		if (encoded == null || encoded.trim().equals(""))
			return ("");
		
		String password = encoded;
		try {
			if (ObfuscationEngine.hasEncryptionPrefix(encoded)) {
				password = new String(ObfuscationEngine.decrypt(encoded));
			}
		} catch (AXSecurityException e) {
			e.printStackTrace();
		}
		return password;
	}
}