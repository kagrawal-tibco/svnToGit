package com.tibco.cep.sharedresource.jndi;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.sharedresource.SharedResourcePlugin;
import com.tibco.cep.sharedresource.ui.AbstractSharedResourceEditorPage;
import com.tibco.cep.sharedresource.ui.SectionProvider;
import com.tibco.cep.studio.core.util.GvUtil;
import com.tibco.cep.studio.ui.util.GvField;
import com.tibco.cep.studio.ui.util.PanelUiUtil;
import com.tibco.cep.studio.ui.util.TableProviderUi;

/*
@author ssailapp
@date Dec 29, 2009 8:40:22 PM
 */

public class JndiGeneralPage extends AbstractSharedResourceEditorPage {
	
	private JndiConfigModelMgr modelmgr;
	private Text tDesc;
	private GvField tgUrl, tgUsername, tgPassword;
	private Button bValidate, bTestConfiguration; 
	private GvField tgFactory;
	//private Combo cFactory;
	private Table taProps;
	private TableProviderUi taPropsProviderUi;
	private Map<String,String> gvTypeFieldMap=new LinkedHashMap<String,String>();
	
	public JndiGeneralPage(JndiConfigEditor editor, Composite parent, JndiConfigModelMgr modelmgr) {
		this.editor = editor;
		this.editorParent = parent;
		this.modelmgr = modelmgr;
		
		if (!editor.isEnabled() && fImage == null) {
			fImage = new Image(Display.getDefault(), editor.getTitleImage(), SWT.IMAGE_COPY);
		}
		createPartControl(this.editorParent, editor.getTitle(), editor.isEnabled() ? editor.getTitleImage() : fImage );
		initialiseGvFieldTypeMap();
//		createPartControl(this.editorParent, editor.getTitle(), editor.getTitleImage());
		SectionProvider sectionProvider = new SectionProvider(managedForm, sashForm, editor.getEditorFileName(), editor);

		Composite sConfig = sectionProvider.createSectionPart("Configuration", true);
		createConfigSectionContents(sConfig); 
		
		Composite sAdvanced = sectionProvider.createSectionPart(sConfig, "Advanced", true);
		createAdvancedSectionContents(sAdvanced); 
		
		managedForm.getForm().reflow(true);
		parent.layout();
		parent.pack();
	}
	
	private void initialiseGvFieldTypeMap() {
        gvTypeFieldMap.put("ProviderUrl", "String");
        gvTypeFieldMap.put("SecurityPrincipal", "String");
        gvTypeFieldMap.put("SecurityCredentials", "Password");
        gvTypeFieldMap.put("ContextFactory", "String");
 	}

	private void createConfigSectionContents(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		comp.setLayout(new GridLayout(2, false));
		
		Label lDesc = PanelUiUtil.createLabel(comp, "Description: ");
		lDesc.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));
		tDesc = PanelUiUtil.createTextMultiLine(comp);
		tDesc.setText(modelmgr.getStringValue("description"));
		tDesc.addListener(SWT.Modify, getListener(tDesc, "description"));

		/*
		Label lFactory = PanelUiUtil.createLabel(comp, "JNDI Context Factory: ");
		cFactory = PanelUiUtil.createComboBox(comp, modelmgr.getFactoryList());
		cFactory.setText(modelmgr.getStringValue("ContextFactory"));
		*/
		tgFactory = createGvComboField(comp, "JNDI Context Factory*: ", modelmgr, modelmgr.getFactoryList(), "ContextFactory");
		
		//tUrl = createConfigTextField(comp, "JNDI Context URL*: ", "ProviderUrl");
		tgUrl = createGvTextField(comp, "JNDI Context URL*: ", modelmgr, "ProviderUrl");
		tgUsername = createGvTextField(comp, "JNDI User Name: ", modelmgr, "SecurityPrincipal");
		tgPassword = createGvPasswordField(comp, "JNDI Password: ", modelmgr, "SecurityCredentials");		

		/*
		cFactory.addListener(SWT.Selection, getFactoryChangeListener());
		cFactory.notifyListeners(SWT.Selection, new Event());
		*/
		tgFactory.getField().addListener(SWT.Selection, getFactoryChangeListener());
		tgFactory.setGvListener(getListener(tgFactory.getGvText(), "ContextFactory"));
		if (!tgFactory.isGvMode() && tgUrl.getValue().equals(""))
			tgFactory.getField().notifyListeners(SWT.Selection, new Event());
		
		bTestConfiguration = PanelUiUtil.createPushButton(comp, "Test Configuration");
		bTestConfiguration.addListener(SWT.Selection, getTestConfigurationListener());
		
		validateFields();
		comp.pack();
	}

	private Listener getTestConfigurationListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				@SuppressWarnings("unused")
				boolean success = testConfiguration();
				/*
				if (success) {
					MessageDialog.openInformation(editorParent.getShell(), "JNDI Configuration", "JNDI configuration - test successful.");
				} else {
					MessageDialog.openError(editorParent.getShell(), "JNDI Configuration", "JNDI configuration - test failed.");					
				}
				*/
			}
		};
		return listener;
	}

	private boolean testConfiguration() {
		InitialContext ctx = null;
		try {
			ctx = new InitialContext(getJNDIProps());                    
		} catch (NamingException ne) {
			SharedResourcePlugin.log(ne);
			MessageDialog.openError(editorParent.getShell(), "JNDI Configuration", "JNDI configuration - test failed. \n\n" + ne.getMessage());
			return false;
		} catch (Throwable t) {
			SharedResourcePlugin.log(t);
			MessageDialog.openError(editorParent.getShell(), "JNDI Configuration", "JNDI configuration - test failed. \n\n" + t.getMessage());
			return false;
		}

		// perform lookup - some implementation of the 
		// initial context might lazily retrieve the context 
		// only when actual methods are invoked
		try {
			ctx.lookup("tmp");
		} catch (javax.naming.NameNotFoundException exp) {
			//ignore. This exception is excepted.
		} catch (Throwable t) {
			SharedResourcePlugin.log(t);
			MessageDialog.openError(editorParent.getShell(), "JNDI Configuration", "JNDI configuration - test failed. \n\n" + t.getMessage());
			return false;
		} finally {
			try {
				ctx.close();
			} catch (Throwable t) {
				//ignore exception
			}
		}
		MessageDialog.openInformation(editorParent.getShell(), "JNDI Configuration", "JNDI configuration - test successful.");
		return true;
	}

	private Hashtable<String, String> getJNDIProps() throws Exception {
		Hashtable<String, String> jndiProp = new Hashtable<String, String>();
		String ctxFactoryName = tgFactory.getGvDefinedValue(modelmgr.getProject());
		jndiProp.put(InitialContext.INITIAL_CONTEXT_FACTORY, ctxFactoryName);
		String ctxURL = tgUrl.getGvDefinedValue(modelmgr.getProject());
		jndiProp.put(InitialContext.PROVIDER_URL, ctxURL);

		String user = tgUsername.getGvDefinedValue(modelmgr.getProject());
		if(user != null && user.length() > 0){
			jndiProp.put(InitialContext.SECURITY_PRINCIPAL, user);
			String password = tgPassword.getGvDefinedValue(modelmgr.getProject());
			if(password != null && password.length() > 0)
				jndiProp.put(InitialContext.SECURITY_CREDENTIALS, password);
		}
		
        for (LinkedHashMap<String, String> prop: modelmgr.getModel().jndiProps) {
        	String propName = prop.get("JNDIPropNameCol");
        	String propVal = prop.get("JNDIPropValueCol");
        	if (propName != null) {
        		if (propVal != null && GvUtil.isGlobalVar(propVal)) {
        			propVal = GvUtil.getGvDefinedValue(modelmgr.getProject(), propVal);
        		}
        		jndiProp.put(propName, propVal);
        	}
        }
		
		return jndiProp;
	}
	
	private Listener getFactoryChangeListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				String factory = ((Combo)tgFactory.getField()).getText();
				String url = modelmgr.getDefaultUrlForFactory(factory);
				tgUrl.setFieldModeValue(url);
				tgUrl.onSetFieldMode();
				//tUrl.setText(url);
				modelmgr.updateStringValue("ContextFactory", factory);
				modelmgr.updateStringValue("ProviderUrl", url);
			}
		};
		return listener;
	}

	private void createAdvancedSectionContents(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		comp.setLayout(new GridLayout(2, false));
		
		createConfigCheckboxField(comp, bValidate, "Validate JNDI Security Context: ", "ValidateJndiSecurityContext");
		
		Composite tableComp = new Composite(parent, SWT.NONE);
		tableComp.setLayoutData(new GridData(GridData.FILL_BOTH));
		tableComp.setLayout(new GridLayout(1, false));
		PanelUiUtil.createLabel(tableComp, "Optional JNDI Properties:");
		String columns[] = new String[]{ "Name", "Type", "Value"};
		JndiPropsTableProviderModel tableModel = new JndiPropsTableProviderModel();  
		taPropsProviderUi = new TableProviderUi(tableComp, columns, true, tableModel);
		taProps = taPropsProviderUi.createTable();
		
		ArrayList<ArrayList<String>> tableData = new ArrayList<ArrayList<String>>();
	    for (LinkedHashMap<String, String> map: modelmgr.getModel().jndiProps) {
	    	ArrayList<String> rowData = new ArrayList<String>();
	    	for (int i=0; i<JndiConfigModelMgr.keys.length; i++) {
	    		rowData.add(map.get(JndiConfigModelMgr.keys[i]));
	    	}
	    	tableData.add(rowData);
	    }
	    taPropsProviderUi.setTableData(tableData);
        taProps.addMouseListener(taPropsProviderUi.tableTextModifyMouseListener(taProps, 0));
        taProps.addMouseListener(taPropsProviderUi.tableTextModifyMouseListener(taProps, 1, 0, TableProviderUi.TYPE_COMBO, tableModel.types));
        taProps.addMouseListener(taPropsProviderUi.tableTextModifyMouseListener(taProps, 2));
        taProps.addListener(SWT.Modify, getTableModifyListener());
		
		comp.pack();
	}
	
//	private Text createConfigTextField(Composite comp, String label, String modelId) {
//		PanelUiUtil.createLabel(comp, label);
//		Text tField = PanelUiUtil.createText(comp);
//		tField.setText(modelmgr.getStringValue(modelId));
//		tField.addListener(SWT.Modify, getListener(tField, modelId));
//		return tField;
//	}
//
//	private Text createConfigPasswordField(Composite comp, String label, String modelId) {
//		PanelUiUtil.createLabel(comp, label);
//		Text tField = PanelUiUtil.createTextPassword(comp);
//		tField.setText(modelmgr.getStringValue(modelId));
//		tField.addListener(SWT.Modify, getListener(tField, modelId));
//		return tField;
//	}
	
	private void createConfigCheckboxField(Composite comp, Button bField, String label, String modelId) {
		PanelUiUtil.createLabel(comp, label);
		bField = PanelUiUtil.createCheckBox(comp, "");
		bField.setSelection(modelmgr.getBooleanValue(modelId));
		bField.addListener(SWT.Selection, getListener(bField, modelId));
	}
	
	public Listener getListener(final Control field, final String key) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (field instanceof Text) {
					modelmgr.updateStringValue(key, ((Text) field).getText());
					String val=((Text)field).getText();
					String gvVal=null;
					if(GvUtil.isGlobalVar(val)){
						 validateField((Text)field,gvTypeFieldMap.get(key), modelmgr.getStringValue(key), key, modelmgr.getProject().getName());
					}
				} else if (field instanceof Combo) {
					if (key.equals("ContextFactory")) {
						modelmgr.updateStringValue("ContextFactory", tgFactory.getGvValue());
					}
				} else if (field instanceof Button) {
					modelmgr.updateBooleanValue(key, ((Button) field).getSelection());
				}
				validateFields();
			}
		};
		return listener;
	}

	private Listener getTableModifyListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				modelmgr.updateJndiProperties(taProps);
			}
		};
		return listener;
	}
	
	@Override
	public boolean validateFields() {
		boolean valid = true;
		//valid &= PanelUiUtil.validateTextField(tUrl, true, false);
		return valid;
	}
	
	public String getName() {
		return ("Configuration");
	}
}
