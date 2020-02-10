package com.tibco.cep.sharedresource.jdbc;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
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
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.cep.sharedresource.model.SharedResModelMgr;
import com.tibco.cep.sharedresource.ssl.SslConfigurationJdbcDialog;
import com.tibco.cep.studio.core.util.GvUtil;
import com.tibco.cep.studio.core.validation.DefaultResourceValidator;
import com.tibco.cep.studio.ui.ISharedJdbcResourceConfigurationUi;
import com.tibco.cep.studio.ui.util.GvField;
import com.tibco.cep.studio.ui.util.GvUiUtil;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

/*
@author ssailapp
@date Aug 15, 2011
 */
@SuppressWarnings({"unused"})

//Note: JDBC XA Connection is taken out from the UI as it's not used by BE

public class JdbcConfigurationUi implements ISharedJdbcResourceConfigurationUi {

	private static String TYPE_JDBC = "JDBC";
	private static String TYPE_JNDI = "JNDI";
	//private static String TYPE_XA = "XA";
	
	private static int TAB_JDBC = 0;
	private static int TAB_JNDI = 1;
	//private static int TAB_XA = 2;
	
	private static int TAB_JNDI_CUSTOM = 0;
	private static int TAB_JNDI_SHARED = 1;
	
	private StackLayout typeTabLayout;
	private Control typeTabs[];
	private Composite typeComp;
	
	private StackLayout jndiTabLayout;
	private Control jndiTabs[];
	String conTypes[] = { TYPE_JDBC, TYPE_JNDI };
	private Composite jndiComp;
	
	private Text tDesc, tJndiConfig, tJndiDataSource;  
	//private Text tDbUrlXa, tUsernameXa;
	private Button bUseSharedJndi, bJndiConfigBrowse, bTestConnection,bSslConfig;
	private GvField tgDbUrlJdbc, tgFactory, tgJndiContextUrl, tgUsernameJndi, tgPasswordJndi, tgUsernameJdbc, tgMaxConJdbc, tgPasswordJdbc, tgTimeout,cgType,cgDriver,bgSsl;
	//private GvField tgMaxConXa, tgPasswordXa;
//	private Combo cDriver;
	//private Combo cXaDsClass;
	
	private LinkedHashMap<String, String> driversMap;
	//private LinkedHashMap<String, String> xaDsClassMap;
	
	private Shell shell;
	private Composite configUiParent;
	private JdbcConfigModelMgr modelmgr;
	
	private ArrayList<String> permittedDrivers = null;
	private Map<String,String> gvTypeFieldMap=new LinkedHashMap<String,String>();
	
	public JdbcConfigurationUi(JdbcConfigModelMgr modelmgr) {
		this.modelmgr = modelmgr;
	}
	
	@Override
	public void createConfigSectionContents(Composite configUiParent) {
		this.shell = configUiParent.getShell();
		this.configUiParent = configUiParent;
		
		initDriversMap();
		//initXaDsClassMap();
		initialiseGvFieldTypeMap();
		Composite comp = new Composite(configUiParent, SWT.NONE);
		comp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		comp.setLayout(new GridLayout(2, false));
		
		Label lDesc = PanelUiUtil.createLabel(comp, "Description: ");
		lDesc.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));
		tDesc = PanelUiUtil.createTextMultiLine(comp);
		tDesc.addListener(SWT.Modify, getListener(tDesc, "description"));
		
		
	//	Label lType = PanelUiUtil.createLabel(comp, "Connection Type*: ");
	//	cType = PanelUiUtil.createComboBox(comp, conTypes);
	//	cType.addListener(SWT.Selection, getTypeChangeListener(cType));
		
		cgType = createGvComboField(comp, "Connection Type*: ",modelmgr.getStringValue("connectionType"), new String[0], "connectionType");
		cgType.getField().addListener(SWT.Selection, getTypeChangeListener());
		cgType.setGvListener(getListener(cgType.getGvText(), "connectionType"));

		createTypeTabs(comp);
		
		bgSsl = createGvCheckboxField(comp, "Use SSL: ", modelmgr, "useSsl");
		PanelUiUtil.createLabel(comp, "");
        bSslConfig = PanelUiUtil.createPushButton(comp, "Configure SSL...");
        bSslConfig.setEnabled(getSslSelection());
        bSslConfig.addListener(SWT.Selection, getSslConfigListener());
        
		bTestConnection = PanelUiUtil.createPushButton(comp, "Test Connection");
		bTestConnection.addListener(SWT.Selection, getTestConnectionListener());
		
		validateFields();
		comp.pack();
	}
	
	private void initialiseGvFieldTypeMap() {
        gvTypeFieldMap.put("location", "String");
        gvTypeFieldMap.put("ContextFactory", "String");
        gvTypeFieldMap.put("ProviderUrl", "String");
        gvTypeFieldMap.put("UserName", "String");
        gvTypeFieldMap.put("UserPassword", "Password");
        gvTypeFieldMap.put("user", "String");
        gvTypeFieldMap.put("maxConnections", "Integer");
        gvTypeFieldMap.put("password", "Password");
        gvTypeFieldMap.put("loginTimeout", "Integer");
        gvTypeFieldMap.put("connectionType", "String");
        gvTypeFieldMap.put("driver", "String");
        gvTypeFieldMap.put("useSsl", "Boolean");
	}
	 public void validateFieldsForGv() {
		validateField((Text)tgDbUrlJdbc.getGvText(),gvTypeFieldMap.get("location"), modelmgr.getStringValue("location"), "location", modelmgr.getProject().getName());
		validateField((Text)tgFactory.getGvText(),gvTypeFieldMap.get("ContextFactory"), modelmgr.getStringValue("ContextFactory"), "ContextFactory", modelmgr.getProject().getName());
		validateField((Text)tgJndiContextUrl.getGvText(),gvTypeFieldMap.get("ProviderUrl"), modelmgr.getStringValue("ProviderUrl"), "ProviderUrl", modelmgr.getProject().getName());
		validateField((Text)tgUsernameJndi.getGvText(),gvTypeFieldMap.get("UserName"), modelmgr.getStringValue("UserName"), "UserName", modelmgr.getProject().getName());
		validateField((Text)tgPasswordJndi.getGvText(),gvTypeFieldMap.get("UserPassword"), modelmgr.getStringValue("UserPassword"), "UserPassword", modelmgr.getProject().getName());
		validateField((Text)tgUsernameJdbc.getGvText(),gvTypeFieldMap.get("user"), modelmgr.getStringValue("user"), "user", modelmgr.getProject().getName());
		validateField((Text)tgMaxConJdbc.getGvText(),gvTypeFieldMap.get("maxConnections"), modelmgr.getStringValue("maxConnections"), "maxConnections", modelmgr.getProject().getName());
		validateField((Text)tgPasswordJdbc.getGvText(),gvTypeFieldMap.get("password"), modelmgr.getStringValue("password"), "password", modelmgr.getProject().getName());
		validateField((Text)tgTimeout.getGvText(),gvTypeFieldMap.get("loginTimeout"), modelmgr.getStringValue("loginTimeout"), "loginTimeout", modelmgr.getProject().getName());
		validateField((Text)tgJndiContextUrl.getGvText(),gvTypeFieldMap.get("connectionType"), modelmgr.getStringValue("connectionType"), "connectionType", modelmgr.getProject().getName());
		validateField((Text)cgDriver.getGvText(),gvTypeFieldMap.get("driver"), modelmgr.getStringValue("driver"), "driver", modelmgr.getProject().getName());
		validateField((Text)bgSsl.getGvText(),gvTypeFieldMap.get("useSsl"), modelmgr.getStringValue("useSsl"), "useSsl", modelmgr.getProject().getName());
	}
	
	@Override
	public void setInput(IFile resource) {
		this.modelmgr = new JdbcConfigModelMgr(resource);
		modelmgr.setAllowResourceSave(true);
		modelmgr.parseModel();
		setInput(modelmgr);
	}

	public void setInput(JdbcConfigModelMgr modelmgr) {
		this.modelmgr = modelmgr;
		
		tDesc.setText(modelmgr.getStringValue("description"));

		if(GvUtil.isGlobalVar(modelmgr.getStringValue("connectionType"))){
			String gvVal=GvUtil.getGvDefinedValue(modelmgr.getProject(),modelmgr.getStringValue("connectionType"));
			if (gvVal.equals(TYPE_JDBC)) {
				setTypeCompStackTopControl(TAB_JDBC);
			} else if (gvVal.equals(TYPE_JNDI)) {
				setTypeCompStackTopControl(TAB_JNDI);
			} 
			cgType.setGvModeValue(modelmgr.getStringValue("connectionType"));
			cgType.onSetGvMode();
			validateFields();
		}else{
			String val=modelmgr.getStringValue("connectionType");
			if (val.equals(TYPE_JDBC)) {
				setTypeCompStackTopControl(TAB_JDBC);
			} else if (val.equals(TYPE_JNDI)) {
				setTypeCompStackTopControl(TAB_JNDI);
			} 
			
			Combo typeCombo = (Combo) cgType.getField();
			typeCombo.setItems(conTypes);
			if (!val.equals(""))
				typeCombo.setText(val);
			else if (typeCombo.getItemCount()>0)
				typeCombo.setText(typeCombo.getItem(0));
			cgType.setFieldModeValue(val);
			cgType.onSetFieldMode();
		}
		
		
		
		if(GvUtil.isGlobalVar(modelmgr.getStringValue("driver"))){
			String gvVal=GvUtil.getGvDefinedValue(modelmgr.getProject(),modelmgr.getStringValue("driver"));
			String dbUrl = driversMap.get(gvVal);
			if(GvUtil.isGlobalVar(modelmgr.getStringValue("location"))){
				tgDbUrlJdbc.setGvModeValue(modelmgr.getStringValue("location"));
				tgDbUrlJdbc.onSetGvMode();
			}else{
				tgDbUrlJdbc.setFieldModeValue(dbUrl);
				tgDbUrlJdbc.onSetFieldMode();
			}
			cgDriver.setGvModeValue(modelmgr.getStringValue("driver"));
			cgDriver.onSetGvMode();
			
		}else{
			cgDriver.setFieldModeValue(modelmgr.getStringValue("driver"));
			cgDriver.onSetFieldMode();
			if(GvUtil.isGlobalVar(modelmgr.getStringValue("location"))){
				tgDbUrlJdbc.setGvModeValue(modelmgr.getStringValue("location"));
				tgDbUrlJdbc.onSetGvMode();
			}else{
				tgDbUrlJdbc.setFieldModeValue(modelmgr.getStringValue("location"));
				tgDbUrlJdbc.onSetFieldMode();
			}
		}
		
		tgMaxConJdbc.setValue(modelmgr.getStringValue("maxConnections"));
		tgUsernameJdbc.setValue(modelmgr.getStringValue("user"));
		tgPasswordJdbc.setValue(modelmgr.getStringValue("password"));
		tgTimeout.setValue(modelmgr.getStringValue("loginTimeout"));
		
		Combo combo = (Combo) tgFactory.getField();
		combo.setItems(modelmgr.getFactoryList());
		String selFactory = modelmgr.getStringValue("ContextFactory");
		if (!selFactory.equals(""))
			combo.setText(selFactory);
		else if (combo.getItemCount()>0)
			combo.setText(combo.getItem(0));
		
		tgJndiContextUrl.setFieldModeValue(modelmgr.getStringValue("ProviderUrl"));
		tgUsernameJndi.setFieldModeValue(modelmgr.getStringValue("UserName"));
		tgPasswordJndi.setValue(modelmgr.getStringValue("UserPassword"));
		
		tJndiConfig.setText(modelmgr.getStringValue("JndiSharedConfiguration"));
		
		tJndiDataSource.setText(modelmgr.getStringValue("jndiDataSourceName"));
		bUseSharedJndi.setSelection(modelmgr.getBooleanValue("UseSharedJndiConfig"));
		
		bJndiConfigBrowse.addListener(SWT.Selection, PanelUiUtil.getFileResourceSelectionListener(configUiParent, modelmgr.getProject(), new String[]{"sharedjndiconfig"}, tJndiConfig));
		
		//cXaDsClass.setText(modelmgr.getStringValue("xaDsClass"));
		//tDbUrlXa.setText(modelmgr.getStringValue("location"));
		//tgMaxConXa.setValue(modelmgr.getStringValue("maxConnections"));	
		//tUsernameXa.setText(modelmgr.getStringValue("user"));
		//tgPasswordXa.setValue(modelmgr.getStringValue("password"));
		
		/*String dbUrl = tgDbUrlJdbc.getValue();
		if (((Combo)cgType.getField()).getText().equals(TYPE_JDBC) && dbUrl.equals("")) {
			cgDriver.getField().notifyListeners(SWT.Selection, new Event());
		}
		if (!cgType.isGvMode())
			cgType.getField().notifyListeners(SWT.Selection, new Event());*/
		if (!tgFactory.isGvMode() && tgJndiContextUrl.getValue().equals(""))
			tgFactory.getField().notifyListeners(SWT.Selection, new Event());
		bUseSharedJndi.notifyListeners(SWT.Selection, new Event());
		
		if(GvUtil.isGlobalVar(modelmgr.getStringValue("useSsl"))){
			String gvVal=GvUtil.getGvDefinedValue(modelmgr.getProject(),modelmgr.getStringValue("useSsl"));
			bgSsl.setGvModeValue(modelmgr.getStringValue("useSsl"));
			bgSsl.onSetGvMode();
			setConfigureSSL(Boolean.parseBoolean(gvVal));
		}
	}
	
	private Listener getTestConnectionListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				JdbcConnectionTester tester = new JdbcConnectionTester(shell, modelmgr);
				boolean success = tester.testConnection();
			}
		};
		return listener;
	}

	private Listener getTypeChangeListener(final Combo cType) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				String conType = cType.getText();
				if (conType.equals(TYPE_JDBC)) {
					setTypeCompStackTopControl(TAB_JDBC);
					tgMaxConJdbc.initializeDropTarget();
				} else if (conType.equals(TYPE_JNDI)) {
					setTypeCompStackTopControl(TAB_JNDI);
				} 
//					else if (conType.equals(TYPE_XA)) {
//					setTypeCompStackTopControl(TAB_XA);
//					tgMaxConXa.initializeDropTarget();
//				}
				modelmgr.updateStringValue("connectionType",conType);
				validateFields();
			}
		};
		return listener;
	}

	private void setTypeCompStackTopControl(int top) {
		typeTabLayout.topControl = typeTabs[top];
		typeComp.layout();
	}
	
	private void createTypeTabs(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayout(PanelUiUtil.getCompactWidthGridLayout(1, false));
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		comp.setLayoutData(gd);
		
		typeComp = new Composite(comp, SWT.NONE);
		typeComp.setLayoutData(new GridData(GridData.FILL_BOTH));
		typeTabLayout = new StackLayout();
		typeComp.setLayout(typeTabLayout);
	    
	    typeTabs = new Control[3];
	    typeTabs[TAB_JDBC] = createJdbcTab();
	    typeTabs[TAB_JNDI] = createJndiTab();
	    //typeTabs[TAB_XA] = createXaTab();
        typeTabLayout.topControl = typeTabs[TAB_JDBC]; 
        
		typeComp.pack();
		comp.pack();
	}

	private Text createConfigTextField(Composite comp, String label, String modelId) {
		PanelUiUtil.createLabel(comp, label);
		Text tField = PanelUiUtil.createText(comp);
		tField.addListener(SWT.Modify, getListener(tField, modelId));
		return tField;
	}

	private Button createConfigCheckboxField(Composite comp, String label, String modelId) {
		PanelUiUtil.createLabel(comp, label);
		Button bField = PanelUiUtil.createCheckBox(comp, "");
		bField.addListener(SWT.Selection, getListener(bField, modelId));
		return bField;
	}
	
	private Combo createConfigComboField(Composite comp, String label, String modelId, String items[]) {
		PanelUiUtil.createLabel(comp, label);
		Combo cField = new Combo(comp, SWT.DROP_DOWN);
		cField.setItems(items);
		cField.setText(items[0]);
		cField.setLayoutData(new GridData(GridData.BEGINNING));
		return cField;
	}
	
	private void initDriversMap() {
		driversMap = new LinkedHashMap<String, String>();
		driversMap.put("oracle.jdbc.OracleDriver", "jdbc:oracle:thin:@<host>:<port#>:<db_instancename>");
		driversMap.put("com.ibm.db2.jcc.DB2Driver","jdbc:db2://<hostname>:<port>/<databasename>");
		driversMap.put("com.microsoft.sqlserver.jdbc.SQLServerDriver","jdbc:sqlserver://<host>:<port>;databaseName=<databaseName>");
		driversMap.put("com.mysql.jdbc.Driver","jdbc:mysql://<hostname>:<port>/<databasename>");
		driversMap.put("org.postgresql.Driver","jdbc:postgresql://<hostname>:<port>/<databasename>?currentSchema=<schemaname>");
		driversMap.put("com.sybase.jdbc3.jdbc.SybDriver","jdbc:sybase:Tds:<host>:<port#>/<databaseName>");
		driversMap.put("com.sybase.jdbc4.jdbc.SybDriver","jdbc:sybase:Tds:<host>:<port#>/<databaseName>");
		driversMap.put("tibcosoftwareinc.jdbc.sqlserver.SQLServerDriver","jdbc:tibcosoftwareinc:sqlserver://<hostname>:<port>;databaseName=<databaseName>");
	}

//	private void initXaDsClassMap() {
//		xaDsClassMap = new LinkedHashMap<String, String>();
//		xaDsClassMap.put("oracle.jdbc.xa.client.OracleXADataSource", "jdbc:oracle:thin:@<host>:<port#>:<db_instancename>");
//		xaDsClassMap.put("com.sybase.jdbc2.jdbc.SybXADataSource", "jdbc:sybase:Tds:<host>:<port#>/<databaseName>");
//		xaDsClassMap.put("tibcosoftwareinc.jdbcx.sqlserver.SQLServerDataSource", "jdbc:tibcosoftwareinc:sqlserver://<host>:<port#>;DatabaseName=<databaseName>");
//		xaDsClassMap.put("tibcosoftwareinc.jdbcx.oracle.OracleDataSource", "jdbc:tibcosoftwareinc:oracle://<host>:<port#>;SID=<db_instancename>"); 				
//		xaDsClassMap.put("tibcosoftwareinc.jdbcx.sybase.SybaseDataSource", "jdbc:tibcosoftwareinc:sybase://<host>:<port#>;DatabaseName=<databaseName>;SelectMethod=Cursor"); 
//		xaDsClassMap.put("com.timesten.jdbc.xa.TimesTenXADataSource", "jdbc:timesten:<dsn>");								
//	}
		
	private Control createJdbcTab() {
		Composite comp = new Composite(typeComp, SWT.NONE);
		comp.setLayout(PanelUiUtil.getCompactWidthGridLayout(2, false));
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));

	//	PanelUiUtil.createLabel(comp, "JDBC Driver*: ");
		
		String drivers[];
		if (permittedDrivers == null)
			drivers = driversMap.keySet().toArray(new String[0]);
		else
			drivers = permittedDrivers.toArray(new String[0]);
		
		cgDriver = createGvComboField(comp, "JDBC Driver*: ", "", drivers, "driver");
//		cDriver = PanelUiUtil.createComboBox(comp, drivers, SWT.DROP_DOWN);
		
//		cDriver.addListener(SWT.Modify, getListener(cDriver, "driver"));
		
		tgDbUrlJdbc = createGvTextField(comp, "Database URL*: ", modelmgr, "location");
		tgMaxConJdbc = createGvTextField(comp, "Maximum Connections*: ", modelmgr, "maxConnections");
		tgUsernameJdbc = createGvTextField(comp, "User Name: ", modelmgr, "user");
		tgPasswordJdbc = createGvPasswordField(comp, "Password: ", modelmgr, "password");
		tgTimeout = createGvTextField(comp, "Login Timeout (sec)*: ", modelmgr, "loginTimeout");
		
		comp.pack();
		return comp;
	}
	
	private Control createJndiTab() {
		Composite comp = new Composite(typeComp, SWT.NONE);
		comp.setLayout(PanelUiUtil.getCompactWidthGridLayout(2, false));
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));

		tJndiDataSource = createConfigTextField(comp, "JNDI DataSource Name*: ", "jndiDataSourceName");
		bUseSharedJndi = createConfigCheckboxField(comp, "Use Shared JNDI Configuration: ", "UseSharedJndiConfig");
		
		createJndiTypeTabs(comp);
		//bUseSharedJndi.notifyListeners(SWT.Selection, new Event());
		
		comp.pack();
		return comp;
	}

//	private Control createXaTab() {
//		Composite comp = new Composite(typeComp, SWT.NONE);
//		comp.setLayout(PanelUiUtil.getCompactWidthGridLayout(2, false));
//		comp.setLayoutData(new GridData(GridData.FILL_BOTH));
//
//		String classes[] = xaDsClassMap.keySet().toArray(new String[0]);
//		cXaDsClass = createConfigComboField(comp, "XA DataSource Class*: ", "xaDsClass", classes);
//		tDbUrlXa = createConfigTextField(comp, "Database URL*: ", "location");
//		//tMaxConXa = createConfigTextField(comp, "Maximum Connections: ", "maxConnections");
//		tgMaxConXa = createGvTextField(comp, "Maximum Connections*: ", modelmgr, "maxConnections");
//		tUsernameXa = createConfigTextField(comp, "User Name: ", "user");
//		//tPasswordXa = createConfigPasswordField(comp, "Password: ", "password");
//		tgPasswordXa = createGvPasswordField(comp, "Password: ", modelmgr, "password");
//
//		cXaDsClass.addListener(SWT.Modify, getXaDsClassChangeListener());
//		cXaDsClass.addListener(SWT.Selection, getXaDsClassChangeListener()); 
//		
//		comp.pack();
//		return comp;
//	}

//	private Listener getXaDsClassChangeListener() {
//		Listener listener = new Listener() {
//			public void handleEvent(Event event) {
//				modelmgr.updateStringValue("xaDsClass", cXaDsClass.getText());
//				String url = xaDsClassMap.get(cXaDsClass.getText());
//				if (url != null)
//					tDbUrlXa.setText(url);
//			}
//		};
//		return listener;
//	}

	private void setJndiTypeTab() {
		boolean useShared = bUseSharedJndi.getSelection();
		if (useShared) {
			setJndiTypeCompStackTopControl(TAB_JNDI_SHARED);
		} else {
			setJndiTypeCompStackTopControl(TAB_JNDI_CUSTOM);
		}
		validateFields();
	}

	private void setJndiTypeCompStackTopControl(int top) {
		jndiTabLayout.topControl = jndiTabs[top];
		jndiComp.layout();
	}
	
	
	private void createJndiTypeTabs(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayout(PanelUiUtil.getCompactWidthGridLayout(1, false));
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		comp.setLayoutData(gd);
		
		jndiComp = new Composite(comp, SWT.NONE);
		jndiComp.setLayoutData(new GridData(GridData.FILL_BOTH));
		jndiTabLayout = new StackLayout();
		jndiComp.setLayout(jndiTabLayout);
	    
	    jndiTabs = new Control[2];
	    jndiTabs[TAB_JNDI_CUSTOM] = createJndiCustomTab();
	    jndiTabs[TAB_JNDI_SHARED] = createJndiSharedTab();
        jndiTabLayout.topControl = jndiTabs[TAB_JNDI_CUSTOM]; 
        
		jndiComp.pack();
		comp.pack();
	}
	
	private Control createJndiSharedTab() {
		Composite comp = new Composite(jndiComp, SWT.NONE);
		comp.setLayout(PanelUiUtil.getCompactGridLayout(3, false));
		comp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		tJndiConfig = createConfigTextField(comp, "JNDI Configuration*:   ", "JndiSharedConfiguration");
		bJndiConfigBrowse = PanelUiUtil.createBrowsePushButton(comp, tJndiConfig);
	//	bJndiConfigBrowse.addListener(SWT.Selection, PanelUiUtil.getFileResourceSelectionListener(comp, modelmgr.getProject(), new String[]{"sharedjndiconfig"}, tJndiConfig)); 
		
		comp.pack();
		return comp;
	}

	private Control createJndiCustomTab() {
		Composite comp = new Composite(jndiComp, SWT.NONE);
		comp.setLayout(PanelUiUtil.getCompactWidthGridLayout(2, false));
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));

		/*
		Label lFactory = PanelUiUtil.createLabel(comp, "JNDI Context Factory*: ");
		cFactory = PanelUiUtil.createComboBox(comp, new String[]{});
		*/
		tgFactory = createGvComboField(comp, "JNDI Context Factory*: ", "", new String[0], "ContextFactory");
		
		tgJndiContextUrl = createGvTextField(comp, "JNDI Context URL*: ", modelmgr, "ProviderUrl");
		tgUsernameJndi = createGvTextField(comp, "JNDI User Name: ", modelmgr, "UserName");
		//tPasswordJndi = createConfigPasswordField(comp, "JNDI Password: ", "UserPassword");
		tgPasswordJndi = createGvPasswordField(comp, "JNDI Password: ", modelmgr, "UserPassword");
		
		//cFactory.addListener(SWT.Selection, getFactoryChangeListener());
//		cFactory.notifyListeners(SWT.Selection, new Event());
		
		tgFactory.getField().addListener(SWT.Selection, getFactoryChangeListener());
		tgFactory.setGvListener(getListener(tgFactory.getGvText(), "ContextFactory"));

		comp.pack();
		return comp;
	}
	
	
	private Listener getFactoryChangeListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				String factory = ((Combo)tgFactory.getField()).getText();
				String url = modelmgr.getDefaultUrlForFactory(factory);
				tgJndiContextUrl.setFieldModeValue(url);
				tgJndiContextUrl.onSetFieldMode();
				modelmgr.updateStringValue("ContextFactory", factory);
				modelmgr.updateStringValue("ProviderUrl", url);
			}
		};
		return listener;
	}
	
	private Listener getTypeChangeListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				String conType = ((Combo)cgType.getField()).getText();
				if (conType.equals(TYPE_JDBC)) {
					setTypeCompStackTopControl(TAB_JDBC);
					tgMaxConJdbc.initializeDropTarget();
				} else if (conType.equals(TYPE_JNDI)) {
					setTypeCompStackTopControl(TAB_JNDI);
				} 
//					else if (conType.equals(TYPE_XA)) {
//					setTypeCompStackTopControl(TAB_XA);
//					tgMaxConXa.initializeDropTarget();
//				}
				modelmgr.updateStringValue("connectionType",conType);
				validateFields();
			}
		};
		return listener;
	}
		
	public Listener getListener(final Control field, final String key) {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
						
				if (field instanceof Text) {
					boolean updated = modelmgr.updateStringValue(key, ((Text) field).getText());	
					
					String val=((Text)field).getText();
					String gvVal=null;
					if(GvUtil.isGlobalVar(val)){
						if(modelmgr.getProject() !=null){
							gvVal=GvUtil.getGvDefinedValue(modelmgr.getProject(),val);
						}
						if (TYPE_JDBC.equalsIgnoreCase(gvVal)) {
							setTypeCompStackTopControl(TAB_JDBC);
						} else if (TYPE_JNDI.equalsIgnoreCase(gvVal)) {
							setTypeCompStackTopControl(TAB_JNDI);
						} else if ("driver".equalsIgnoreCase(key)){
							if(driversMap.get(gvVal) != null || driversMap.get(gvVal) !="") {
								String dbUrl = driversMap.get(gvVal);
								if(!(GvUtil.isGlobalVar(modelmgr.getStringValue("location")))){
									if (dbUrl != null)
										tgDbUrlJdbc.setFieldModeValue(modelmgr.getStringValue("location"));
									else
										tgDbUrlJdbc.setFieldModeValue("");
								}
							}
						}
//							else if (conType.equals(TYPE_XA)) {
//							setTypeCompStackTopControl(TAB_XA);
//							tgMaxConXa.initializeDropTarget();
//						}
						
						if("useSsl".equalsIgnoreCase(key)){
							if(Boolean.parseBoolean(gvVal) == true){
								setConfigureSSL(true);
							}else{
								setConfigureSSL(false);
							}
						}
						
						
						
						validateFields();
					}
					if (updated) {
//						if (key.equals("maxConnections")) {
//							if (field == tgMaxConJdbc.getField()) {
//								tgMaxConXa.setFieldModeValue(modelmgr.getStringValue(key));
//							} else if (field == tgMaxConJdbc.getGvText()) {
//								tgMaxConXa.setGvModeValue(modelmgr.getStringValue(key));
//							} else if (field == tgMaxConXa.getField()) {
//								tgMaxConJdbc.setFieldModeValue(modelmgr.getStringValue(key));
//							} else if (field == tgMaxConXa.getGvText()) {
//								tgMaxConJdbc.setGvModeValue(modelmgr.getStringValue(key));
//							} 
						} 
//							else if (key.equals("location")) {
//							if (field == tgDbUrlJdbc.getGvText()) {
//								tDbUrlXa.setText(modelmgr.getStringValue(key));
//							} else if (field == tDbUrlXa) {
//								tgDbUrlJdbc.setGvModeValue(modelmgr.getStringValue(key));
//							}
//						}
//				}  
					if(modelmgr.getProject() !=null){
						validateField((Text)field,gvTypeFieldMap.get(key), modelmgr.getStringValue(key), key, modelmgr.getProject().getName());
					}
				} else if (field instanceof Button) {
					modelmgr.updateBooleanValue(key, ((Button) field).getSelection());
					if (key.equals("UseSharedJndiConfig")) {
						setJndiTypeTab();
					} else if (key.equals("useSsl")) {
	                    bSslConfig.setEnabled(((Button) field).getSelection());
	                }
				} else if (field instanceof Combo) {
					String oldDriver=modelmgr.getStringValue(key);
					boolean updated = modelmgr.updateStringValue(key, ((Combo) field).getText());
					if (key.equals("driver")) {
						String driver = cgDriver.getFieldValue();
						String dbUrl = driversMap.get(driver);
						if(!(GvUtil.isGlobalVar(modelmgr.getStringValue("location")))){
							if (dbUrl != null){
								if(!(modelmgr.getStringValue("location").isEmpty())){
									if((oldDriver.equals(driver)))
										tgDbUrlJdbc.setFieldModeValue(modelmgr.getStringValue("location"));
									else
										tgDbUrlJdbc.setFieldModeValue(dbUrl);
								}else{
									tgDbUrlJdbc.setFieldModeValue(dbUrl);
								}
							}else
								tgDbUrlJdbc.setFieldModeValue("");
						}
					}
				}
				validateFields();
			}
		};
		return listener;
	}
	
	public boolean validateFields() {
		boolean valid = true;
		//valid &= GvUtil.validateGvField(tgDbUrlJdbc, true, false);
		//valid &= PanelUiUtil.validateTextField(tDbUrlXa, true, false);
		//valid &= GvUtil.validateGvField(tgMaxConJdbc, true, true);
		//valid &= GvUtil.validateGvField(tgMaxConXa, true, true);
		//valid &= GvUtil.validateGvField(tgTimeout, true, true);
		
		valid &= PanelUiUtil.validateTextField(tJndiConfig, true, false);
		//valid &= PanelUiUtil.validateTextField(tJndiContextUrl, true, false);
		valid &= PanelUiUtil.validateTextField(tJndiDataSource, true, false);

		//valid &= PanelUiUtil.validateTextField(cXaDsClass, true, false);
		return valid;
	}
	
	private Listener getSslConfigListener() {
        Listener listener = new Listener() {
            @Override
            public void handleEvent(Event event) {
                SslConfigurationJdbcDialog dialog = new SslConfigurationJdbcDialog(configUiParent.getShell(), modelmgr.getModel().getSslConfigJdbcModel(), modelmgr.getProject());
                dialog.initDialog("for TIBCO JDBC");
                dialog.openDialog();
                if (dialog.isDirty())
                	modelmgr.modified();
            }
        };
        return listener;
    }
	
	private boolean getSslSelection() {
        String stringSslCheck = modelmgr.getStringValue("useSsl");
        if (GvUtil.isGlobalVar(stringSslCheck)) {
            stringSslCheck = GvUtil.getGvDefinedValue(modelmgr.getProject(),
                    stringSslCheck);
        }
        return Boolean.parseBoolean(stringSslCheck);
    }
	
	public GvField createGvCheckboxField(Composite parent, String label, SharedResModelMgr modelmgr, String modelId) {
		Label lField = PanelUiUtil.createLabel(parent, label);
    	GvField gvField = GvUiUtil.createCheckBoxGv(parent, modelmgr.getStringValue(modelId));
		setGvFieldListeners(gvField, SWT.Selection, modelId);
		return gvField;
    }
	
    private GvField createGvTextField(Composite parent, String label, SharedResModelMgr modelmgr, String modelId) {
		Label lField = PanelUiUtil.createLabel(parent, label);
    	GvField gvField = GvUiUtil.createTextGv(parent, modelmgr.getStringValue(modelId));
		setGvFieldListeners(gvField, SWT.Modify, modelId);
		return gvField;
    }

    private GvField createGvPasswordField(Composite parent, String label, SharedResModelMgr modelmgr, String modelId) {
		Label lField = PanelUiUtil.createLabel(parent, label);
    	GvField gvField = GvUiUtil.createPasswordGv(parent, "");
		setGvFieldListeners(gvField, SWT.Modify, modelId);
		return gvField;
    }

    public GvField createGvComboField(Composite parent, String label, String initVal, String items[], String modelId) {
		Label lField = PanelUiUtil.createLabel(parent, label);
    	GvField gvField = GvUiUtil.createComboGv(parent, initVal);
		Combo combo = (Combo) gvField.getField();
		combo.setItems(items);
		String selItem = initVal;
		if (!selItem.equals(""))
			combo.setText(selItem);
		else if (combo.getItemCount()>0)
			combo.setText(combo.getItem(0));
		setGvFieldListeners(gvField, SWT.Selection, modelId);
		return gvField;
    }
    
    protected void setGvFieldListeners(GvField gvField, int eventType, String modelId) {
		gvField.setFieldListener(eventType, getListener(gvField.getField(), modelId));
		gvField.setGvListener(getListener(gvField.getGvText(), modelId));
    }

	@Override
	public Object getData() {
		Connection connection = null;
		String jdbcdriver = modelmgr.getStringValue("driver");
		jdbcdriver = GvUtil.getGvDefinedValue(modelmgr.getProject(), jdbcdriver);
		if ( jdbcdriver.startsWith("oracle.jdbc.driver.OracleDriver")) {
			jdbcdriver = "oracle.jdbc.OracleDriver";
		}

		if (!permittedDrivers.contains(jdbcdriver))
			return connection;
		
		String url = modelmgr.getStringValue("location");
		url = GvUtil.getGvDefinedValue(modelmgr.getProject(), url);
		String username = modelmgr.getStringValue("user");
		username = GvUtil.getGvDefinedValue(modelmgr.getProject(), username);
		String passwd = modelmgr.getStringValue("password");
		passwd = GvUtil.getGvDefinedValue(modelmgr.getProject(), passwd);

		try {
			Driver driver = (Driver) Class.forName(jdbcdriver).newInstance();
			connection = DriverManager.getConnection(url, username, passwd);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}

	@Override
	public void setDrivers(ArrayList<String> drivers) {
		permittedDrivers = drivers;
	}
	
	/**
	 * @param textField
	 * @param type
	 * @param deafultValue
	 * @param displayName
	 * @return
	 */
	protected static boolean validateField(Text textField, String type, String deafultValue,
			String displayName, String prjName) {
		final String problemMessage = validatePropertyValue(type, textField,
				deafultValue, displayName,prjName);
		if (problemMessage != null) {
			textField.setForeground(Display.getDefault().getSystemColor(
					SWT.COLOR_RED));
			textField.setToolTipText(problemMessage);
			return false;
		}else if(problemMessage == null){
			textField.setForeground(Display.getDefault().getSystemColor(
					SWT.COLOR_BLACK));
			textField.setToolTipText("");
			return true;
		}

		return true;
	}
	
	private void setConfigureSSL(boolean val) {
		bSslConfig.setEnabled(val);
	}
	
	/**
	 * @param type
	 * @param fieldName
	 * @param deafultValue
	 * @param propertyName
	 * @param propertyInstance
	 * @return
	 */
	protected static String validatePropertyValue(String type, Text textField,
			String deafultValue, String propertyName, String projectName) {
		final String message = com.tibco.cep.studio.ui.util.Messages.getString(
				"invalid.property.entry", textField.getText(), propertyName,
				type);
		String text = textField.getText();
		boolean globalVar = false;
		if (text.length() > 4) {
			globalVar = GvUtil.isGlobalVar(text.trim());
		}
		if (globalVar) {
			// check if global var defined
			Map<String, GlobalVariableDescriptor> glbVars = DefaultResourceValidator
					.getGlobalVariableNameValues(projectName);

			GlobalVariableDescriptor gvd = glbVars.get(stripGvMarkers(text));
			if (gvd == null) {
				return text + " is not defined";
			}
			if (!gvd.getType().equals(type.intern())) {
				if (type.intern().equals("int")
						&& gvd.getType().equals("Integer")) {
					return null;
				}
				return "Type of "+ text +" is not "+ type.intern();
			}
			return null;
		}
		return null;
	}
	
	private static String stripGvMarkers(String variable) {
		int firstIndex = variable.indexOf("%%");
		String stripVal = variable.substring(firstIndex + 2);
		stripVal = stripVal.substring(0, stripVal.indexOf("%%"));
		return stripVal;
	}
}
