package com.tibco.cep.studio.ui.editors.wizardPages;

import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IPageChangingListener;
import org.eclipse.jface.dialogs.PageChangingEvent;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.be.common.ConnectionPool;
import  com.tibco.be.jdbcstore.ssl.JdbcSSLConnectionInfo;
import com.tibco.cep.sharedresource.jdbc.JdbcConfigModelMgr;
import com.tibco.cep.sharedresource.jdbc.JdbcSSLSharedresourceHelper;
import com.tibco.cep.studio.core.util.GvUtil;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.forms.components.SharedJdbcSelector;
import com.tibco.cep.studio.ui.util.Messages;

/**
 * 
 * @author smarathe
 * 
 */

public class ImportDatabaseTableDomainWizardPage extends WizardPage implements IDomainSourceWizardPage, IPageChangingListener {

	private final static Character PASSWORD_ECHO_CHAR = Character.valueOf('*');

	private String jdbcURI;
	private String username;
	private String password;
	private String ownerName;
	private String driver;
	private String connURL;
	private int retryCount = -1;
	private String domainDataType;

	private Text txtJDBCURI;
	private Text txtConnectAsUser;
	private Text txtConnectAsPwd;
	private Label lblOwnerName;
	private Text txtOwnerName;
	// create if TRA prop is set true
	private Label userSqlQueryLabel;
	private Text userSqlQueryName;
	public static String userDefinedSQLQueryString;

	private ModifyListener modifyListener;

	private Composite parentComposite;

	private String projectName;
	
	private IProject project;

	Connection connection;
	
	ImportDatabaseTableSelectionDomainWizardPage page;
	
	boolean childPageFlag = false;

	private Map<String, Object> dataSource;
	
	HashMap<String, Object> connectionMap;

	private JdbcSSLConnectionInfo sslConnectionInfo;
	
	public ImportDatabaseTableDomainWizardPage(String pageName) {
		super(pageName);
		setTitle(Messages.getString("ImportDomainWizard.page.Title"));
		setDescription(Messages.getString("import.domain.wizard.db.description"));
	}

	@Override
	public boolean isPageComplete() {
		return false;
	}

	@Override
	public void createControl(Composite parent) {

		parentComposite = new Composite(parent, SWT.NONE);

		GridLayout gridLayout = new GridLayout();
		parentComposite.setLayout(gridLayout);

		Group group = new Group(parentComposite, SWT.NULL);
		group.setText("");
		gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		gridLayout.makeColumnsEqualWidth = false;
		group.setLayout(gridLayout);
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		group.setLayoutData(gd);

		Label lblJDBCURI = new Label(group, SWT.NONE);
		lblJDBCURI
				.setText(Messages
						.getString("import.domain.DB.Connection.Parameter.Page.JDBCLabel"));
		gd = new GridData(SWT.FILL, SWT.END, true, false);
		gd.horizontalSpan = 2;
		lblJDBCURI.setLayoutData(gd);

		txtJDBCURI = new Text(group, SWT.BORDER);
		txtJDBCURI.setEditable(false);
		gd = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		txtJDBCURI.setLayoutData(gd);

		Button jdbcBrowseButton = new Button(group, SWT.PUSH);
		jdbcBrowseButton.setLayoutData(new GridData(SWT.RIGHT, SWT.BEGINNING,
				false, false));
		jdbcBrowseButton.setToolTipText("Browse resources...");
		jdbcBrowseButton.setImage(EditorsUIPlugin.getDefault().getImage(
				"/icons/browse_file_system.gif"));
		jdbcBrowseButton.addSelectionListener(new SharedJdbcResourceSelector());

		Label lbluser = new Label(group, SWT.NONE);
		lbluser
				.setText(Messages
						.getString("import.domain.DB.Connection.Parameter.Page.UserLabel"));
		gd = new GridData(SWT.FILL, SWT.END, true, false);
		gd.horizontalSpan = 2;
		lbluser.setLayoutData(gd);

		txtConnectAsUser = new Text(group, SWT.BORDER);
		gd = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		gd.horizontalSpan = 2;
		txtConnectAsUser.setLayoutData(gd);

		Label lblpasword = new Label(group, SWT.NONE);
		lblpasword
				.setText(Messages
						.getString("import.domain.DB.Connection.Parameter.Page.PasswordLabel"));
		gd = new GridData(SWT.FILL, SWT.END, true, false);
		gd.horizontalSpan = 2;
		lblpasword.setLayoutData(gd);

		txtConnectAsPwd = new Text(group, SWT.BORDER);
		gd = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		gd.horizontalSpan = 2;
		txtConnectAsPwd.setLayoutData(gd);
		txtConnectAsPwd.setEchoChar(PASSWORD_ECHO_CHAR);
		txtConnectAsPwd.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				connectionMap.put(Messages.getString("import.domain.databse.table.password"),txtConnectAsPwd.getText());
				password = txtConnectAsPwd.getText();
			}
			
		});

		lblOwnerName = new Label(group, SWT.NONE);
		lblOwnerName
				.setText(Messages
						.getString("import.domain.DB.Connection.Parameter.Page.OwnerLabel"));
		gd = new GridData(SWT.FILL, SWT.END, true, false);
		gd.horizontalSpan = 2;
		lblOwnerName.setLayoutData(gd);

		txtOwnerName = new Text(group, SWT.BORDER);
		gd = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		gd.horizontalSpan = 2;
		txtOwnerName.setLayoutData(gd);

		setErrorMessage(null);
		setMessage(null);

		txtJDBCURI.addModifyListener(getModifyListener());
		txtConnectAsUser.addModifyListener(getModifyListener());
		txtConnectAsPwd.addModifyListener(getModifyListener());
		txtOwnerName.addModifyListener(getModifyListener());

		WizardDialog dialog = (WizardDialog) getContainer();
		dialog.addPageChangingListener(this);

		setPageComplete(false);
		setControl(parentComposite);
	}

	public String getDomainDataType() {
		return ((DomainImportSourceSelectionWizardPage)getWizard().getStartingPage()).getDomainDataType();
	}

	public void setDomainDataType(String domainDataType) {
		this.domainDataType = domainDataType;
	}
	
	@Override
	public IWizardPage getNextPage() {
		try {
		//	if (!childPageFlag) {
				Constructor<ImportDatabaseTableSelectionDomainWizardPage> constructor_DatabaseTable = null;
				constructor_DatabaseTable = ImportDatabaseTableSelectionDomainWizardPage.class.getConstructor(String.class, Connection.class, String.class);
				page = constructor_DatabaseTable.newInstance(Messages.getString("ImportDomainWizard.Title"), connection, domainDataType);
				Wizard wizard = (Wizard) getWizard();
				wizard.addPage(page);
			//	childPageFlag = true;
			//}
			return page;
		} catch (Exception e) {
			StudioUIPlugin.log(e);
			return null;
		}
	}

	private ModifyListener getModifyListener() {
		if (modifyListener == null) {
			modifyListener = new ModifyListener() {

				public void modifyText(ModifyEvent e) {
					if (e.widget == txtJDBCURI) {
						IResource resource = ResourcesPlugin.getWorkspace()
								.getRoot().getProject(projectName).getFile(
										txtJDBCURI.getText());
						handleDBChange(resource);
					}
					setPageComplete(validatePage());
				}

			};
		}
		return modifyListener;
	}

	private boolean validatePage() {
		
		if ((txtJDBCURI != null && txtJDBCURI.getText().trim().isEmpty() )
				|| (txtConnectAsUser != null && txtConnectAsUser.getText().trim().isEmpty())
				|| (txtConnectAsPwd != null && txtConnectAsPwd.getText().trim().isEmpty())
				|| (txtOwnerName !=null && txtOwnerName.isVisible() && txtOwnerName.getText().trim()
						.isEmpty())) {
			return false;
		}
		setMessage("");
		return true;
	}
	
	public boolean canFlipToNextPage() {
		return validatePage();
	}

	private boolean validatIntegerText(String s) {
		try {
			Integer.parseInt(s);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	private void handleDBChange(IResource resource) {

		Map<String, String> jdbcParams = getJDBCComponents(resource);

		username = jdbcParams.get("user");
		password = jdbcParams.get("password");
		driver = jdbcParams.get("driver");
		connURL = jdbcParams.get("url");
		jdbcURI = jdbcParams.get("location");

		// handle Global variables defined for JDBC resource
		/*project = ResourcesPlugin.getWorkspace().getRoot().getProject(
				projectName);
		username = GvUtil.getGvDefinedValue(project, username);
		password = GvUtil.getGvDefinedValue(project, password);
		driver = GvUtil.getGvDefinedValue(project, driver);
		connURL = GvUtil.getGvDefinedValue(project, connURL);
		jdbcURI = GvUtil.getGvDefinedValue(project, jdbcURI);*/

		
		txtConnectAsUser.setText(username);
		txtOwnerName.setText(username);
		txtConnectAsPwd.setText(password);
		
		parentComposite.layout(true);
	}

	public Map<String, String> getJDBCComponents(IResource resource) {

		// get the JDBC props
		JdbcConfigModelMgr modelMgr = new JdbcConfigModelMgr(resource);

		try {		
			modelMgr.parseModel();
		}catch(Exception e) {
			
		}
		project = modelMgr.getProject();

		Map<String, String> jdbcPropMap = new HashMap<String, String>();
		jdbcPropMap.put("driver", GvUtil.getGvDefinedValue(project,modelMgr.getStringValue("driver")));
		jdbcPropMap.put("location", getSharedResourcePath(resource));
		jdbcPropMap.put("user", GvUtil.getGvDefinedValue(project,modelMgr.getStringValue("user")));
		jdbcPropMap.put("password", GvUtil.getGvDefinedValue(project,modelMgr.getStringValue("password")));
		jdbcPropMap.put("loginTimeout", GvUtil.getGvDefinedValue(project,modelMgr.getStringValue("loginTimeout")));
		jdbcPropMap.put("maxConnections", GvUtil.getGvDefinedValue(project,modelMgr.getStringValue("maxConnections")));
		jdbcPropMap.put("url", GvUtil.getGvDefinedValue(project,modelMgr.getStringValue("location")));

		connectionMap = new HashMap<String, Object>();
		connectionMap.put(Messages.getString("import.domain.databse.table.driver"), GvUtil.getGvDefinedValue(project,modelMgr.getStringValue("driver")));
		connectionMap.put(Messages.getString("import.domain.databse.table.url"), GvUtil.getGvDefinedValue(project,modelMgr.getStringValue("location")));
		connectionMap.put(Messages.getString("import.domain.databse.table.username"), GvUtil.getGvDefinedValue(project,modelMgr.getStringValue("user")));
		
		String useSsl = modelMgr.getStringValue("useSsl");
		useSsl = GvUtil.getGvDefinedValue(modelMgr.getProject(), useSsl);
		
		if ("true".equalsIgnoreCase(useSsl)) {
			try {
				sslConnectionInfo = JdbcSSLSharedresourceHelper.getSSLConnectionInfo(
						modelMgr.getStringValue("user"),
						modelMgr.getStringValue("password"),
						modelMgr.getStringValue("location"),
						modelMgr.getStringValue("driver"),
						modelMgr);
				if (sslConnectionInfo != null) {
					connectionMap.put(JdbcSSLConnectionInfo.KEY_JDBC_SSL_CONNECTION_INFO, sslConnectionInfo);
				}
			} catch (Exception e) {
				String message = "JDBC configure SSL failed.\n" + e.toString();
				EditorsUIPlugin.getDefault().getLog().log(new Status(IStatus.ERROR, EditorsUIPlugin.PLUGIN_ID, message, e));
			}
		}
		
		return jdbcPropMap;
	}
	
	public HashMap<String, Object> getConnectionMap() {
		return this.connectionMap;
	}
	
	private String getSharedResourcePath(IResource resource) {
		String sharedResourcePath = null;
		String sharedRelativePath = resource.getProjectRelativePath()
				.toString();
		if (sharedRelativePath.startsWith("/")) {
			sharedResourcePath = sharedRelativePath;
		} else {
			sharedResourcePath = "/" + sharedRelativePath;
		}
		return sharedResourcePath;
	}

	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getDataSource() {
		return this.dataSource;
	}

	private class SharedJdbcResourceSelector implements SelectionListener {

		public void widgetDefaultSelected(SelectionEvent e) {

		}

		public void widgetSelected(SelectionEvent e) {
			Set<String> hashSet = new HashSet<String>();
			hashSet.add("sharedjdbc");
			try {
				SharedJdbcSelector fileSelector = new SharedJdbcSelector(
						Display.getDefault().getActiveShell(), projectName);
				if (fileSelector.open() == Window.OK) {
					if (fileSelector.getFirstResult() instanceof IFile) {
						String firstResult = ((IResource) fileSelector
								.getFirstResult()).getProjectRelativePath()
								.toOSString();
						txtJDBCURI.setText(firstResult);
					}
				}
			} catch (Exception e1) {
				StudioUIPlugin.log(e1);
			}
		}
	}

	@Override
	public String getProjectName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProjectName(String projectName) {
		this.projectName = projectName;

	}

	public Connection getConnection() {
		return connection;
	}

	public void setDataSource(Map<String, Object> dataSource) {
		this.dataSource = dataSource;
	}
	
	@Override
	public void handlePageChanging(PageChangingEvent event) {
		if ((event.getCurrentPage() == this) && (event.getTargetPage() instanceof ImportDatabaseTableSelectionDomainWizardPage)) {
			try {
				Driver driverClass = null;
				if(driver != null) {
					driverClass = (Driver) Class.forName(driver).newInstance();
					DriverManager.registerDriver(driverClass);
					if (sslConnectionInfo == null) {
						connection = DriverManager.getConnection(connURL, username, password);
					} else {
						sslConnectionInfo.setUser(username);
						sslConnectionInfo.setPassword(password);
						sslConnectionInfo.loadSecurityProvider();
						connection = DriverManager.getConnection(connURL, sslConnectionInfo.getProperties());
					}
					ConnectionPool.unlockDDConnection(connection);
					connection.setAutoCommit(false);
					page.setConnection(connection);
				}
			} catch (ClassNotFoundException cnfex) {
				event.doit = false;
				setErrorMessage("Jdbc driver not found : " + cnfex.getMessage());
				StudioUIPlugin.log(cnfex);
			} catch (Exception ex) {
				event.doit = false;
				setErrorMessage(ex.getMessage());
				StudioUIPlugin.log(ex);
			}
		}
	}
	
	public String getJdbcURI() {
		return this.jdbcURI;
	}
}
