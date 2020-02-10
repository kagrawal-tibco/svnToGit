package com.tibco.cep.studio.dbconcept.wizards;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IPageChangingListener;
import org.eclipse.jface.dialogs.PageChangingEvent;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.IWizardPage;
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
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import  com.tibco.be.jdbcstore.ssl.JdbcSSLConnectionInfo;
import com.tibco.cep.studio.core.util.GvUtil;
import com.tibco.cep.studio.dbconcept.ModulePlugin;
import com.tibco.cep.studio.dbconcept.component.FileSelector;
import com.tibco.cep.studio.dbconcept.component.JdbcResourceSelector;
import com.tibco.cep.studio.dbconcept.conceptgen.DBDataSource;
import com.tibco.cep.studio.dbconcept.conceptgen.impl.DBDataSourceImpl;
import com.tibco.cep.studio.dbconcept.palettes.tools.DBCeptGenHelper;
import com.tibco.cep.studio.dbconcept.utils.DBConnectionManager;
import com.tibco.cep.studio.dbconcept.utils.Messages;
import com.tibco.cep.studio.ui.wizards.OnlyFileInclusionFilter;

/**
 * 
 * @author majha
 * 
 */

public class SpecifyDBConnectionPage extends WizardPage implements
		IPageChangingListener {

	public static boolean userDefinedSQLQuery = System.getProperty("be.dbconcepts.dbimport.use.sql", "false").equals("true");

	private final static Character PASSWORD_ECHO_CHAR = Character.valueOf('*');

	private DBCeptGenHelper helper;

	private String jdbcURI;
	private String username;
	private String password;
	private String ownerName;
	private String driver;
	private String connURL;
	private int retryCount = -1;
	private JdbcSSLConnectionInfo sslConnectionInfo;

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

	private ReadDatabaseCatalogExecutor cancelableJobExecutor;

	private Composite parentComposite;

	private String projectName;
	private IProject project;

	protected SpecifyDBConnectionPage(IProject project, DBCeptGenHelper helper) {
		super(Messages.getString("DB.Connection.Parameter.Page.Description"));
		this.helper = helper;
		this.project = project;
		this.projectName = project.getName();
		setTitle(getName());
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
		lblJDBCURI.setText(Messages
				.getString("DB.Connection.Parameter.Page.JDBCLabel"));
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
		jdbcBrowseButton.setImage(ModulePlugin.getDefault().getImage(
				"icons/browse_file_system.gif"));
		jdbcBrowseButton.addSelectionListener(new SharedJdbcSelector());

		Label lbluser = new Label(group, SWT.NONE);
		lbluser.setText(Messages
				.getString("DB.Connection.Parameter.Page.UserLabel"));
		gd = new GridData(SWT.FILL, SWT.END, true, false);
		gd.horizontalSpan = 2;
		lbluser.setLayoutData(gd);

		txtConnectAsUser = new Text(group, SWT.BORDER);
		gd = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		gd.horizontalSpan = 2;
		txtConnectAsUser.setLayoutData(gd);

		Label lblpasword = new Label(group, SWT.NONE);
		lblpasword.setText(Messages
				.getString("DB.Connection.Parameter.Page.PasswordLabel"));
		gd = new GridData(SWT.FILL, SWT.END, true, false);
		gd.horizontalSpan = 2;
		lblpasword.setLayoutData(gd);

		txtConnectAsPwd = new Text(group, SWT.BORDER);
		gd = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		gd.horizontalSpan = 2;
		txtConnectAsPwd.setLayoutData(gd);
		txtConnectAsPwd.setEchoChar(PASSWORD_ECHO_CHAR);

		lblOwnerName = new Label(group, SWT.NONE);
		lblOwnerName.setText(Messages
				.getString("DB.Connection.Parameter.Page.OwnerLabel"));
		gd = new GridData(SWT.FILL, SWT.END, true, false);
		gd.horizontalSpan = 2;
		lblOwnerName.setLayoutData(gd);

		txtOwnerName = new Text(group, SWT.BORDER);
		gd = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		gd.horizontalSpan = 2;
		txtOwnerName.setLayoutData(gd);

		// Create the user defined SQL label/text by TRA file...
		if (userDefinedSQLQuery) {

			userSqlQueryLabel = new Label(group, SWT.NONE);
			userSqlQueryLabel
					.setText(Messages
							.getString("DB.Connection.Parameter.Page.userSqlQueryLabel"));
			gd = new GridData(SWT.FILL, SWT.END, true, false);
			gd.horizontalSpan = 2;
			userSqlQueryLabel.setLayoutData(gd);

			userSqlQueryName = new Text(group, SWT.BORDER);
			gd = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
			gd.horizontalSpan = 2;
			userSqlQueryName.setLayoutData(gd);
			userSqlQueryName.addModifyListener(getModifyListener());

		}

		// add modify listeners
		txtJDBCURI.addModifyListener(getModifyListener());
		txtConnectAsUser.addModifyListener(getModifyListener());
		txtConnectAsPwd.addModifyListener(getModifyListener());
		txtOwnerName.addModifyListener(getModifyListener());

		setPageComplete(validatePage());

		WizardDialog dialog = (WizardDialog) getContainer();
		dialog.addPageChangingListener(this);
		setControl(parentComposite);
	}

	private boolean validatePage() {
		if (userDefinedSQLQuery) {

			if (txtJDBCURI.getText().trim().isEmpty()
					|| txtConnectAsUser.getText().trim().isEmpty()
					|| txtConnectAsPwd.getText().trim().isEmpty()
					|| (txtOwnerName.isVisible()
							&& txtOwnerName.getText().trim().isEmpty() || (userSqlQueryName
							.isVisible() && userSqlQueryName.getText().trim()
							.isEmpty()))) {
				return false;
			}

		} else {

			if (txtJDBCURI.getText().trim().isEmpty()
					|| txtConnectAsUser.getText().trim().isEmpty()
					|| txtConnectAsPwd.getText().trim().isEmpty()
					|| (txtOwnerName.isVisible() && txtOwnerName.getText()
							.trim().isEmpty())) {
				return false;
			}
		}
		setMessage("");
		return true;
	}

	protected boolean validatIntegerText(String s) {
		try {
			Integer.parseInt(s);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	private ModifyListener getModifyListener() {
		if (modifyListener == null) {
			modifyListener = new ModifyListener() {

				public void modifyText(ModifyEvent e) {
					if (e.widget == txtJDBCURI) {
						IResource resource = project.getFile(txtJDBCURI
								.getText());
						handleDBChange(resource);
					}
					setPageComplete(validatePage());
				}

			};
		}
		return modifyListener;
	}

	/**
	 * Truncate3xOracleDriverName
	 * 
	 * @param x_driver
	 * @return 
	 * 
	 * 
	 * x_driver = "oracle.jdbc.driver.OracleDriver (thin)" and return
	 *         string is driveName ="oracle.jdbc.driver.OracleDriver";
	 * 
	 */
	private String Truncate3xOracleDriverName(String x_driver) {
		String driverName = null;
		String stripName = "(thin)";
		if (x_driver.endsWith(stripName)) {
			int index = x_driver.indexOf(stripName);
			// index-1 , 1 - is remove the space....
			driverName = x_driver.substring(0, index - 1);
		} else {
			driverName = x_driver;
		}
		return driverName;
	}

	private void handleDBChange(IResource resource) {

		Map<?, ?> jdbcParams = helper.getDBCeptGenerator()
				.getJDBCComponents(resource);

		username = (String) jdbcParams.get("user");
		password = (String) jdbcParams.get("password");
		driver = (String) jdbcParams.get("driver");
		connURL = (String) jdbcParams.get("url");
		jdbcURI = (String) jdbcParams.get("location");
		sslConnectionInfo = (JdbcSSLConnectionInfo) jdbcParams.get(JdbcSSLConnectionInfo.KEY_JDBC_SSL_CONNECTION_INFO);
		
		//handle Global variables defined for JDBC resource
		username = GvUtil.getGvDefinedValue(project, username);
		password = GvUtil.getGvDefinedValue(project, password);
		driver = GvUtil.getGvDefinedValue(project, driver);
		connURL = GvUtil.getGvDefinedValue(project, connURL);
		jdbcURI = GvUtil.getGvDefinedValue(project, jdbcURI);
		

		txtConnectAsUser.setText(username);
		txtConnectAsPwd.setText(password);

		// Strip the (thin) content from the drivername
		driver = Truncate3xOracleDriverName(driver);

		String dbType = DBDataSourceImpl.getDBType(driver);
		switch (dbType) {
		case DBDataSource.MSSQL:
			txtOwnerName.setVisible(true);
			lblOwnerName.setVisible(true);
			txtOwnerName.setText(DBDataSource.DEFAULT_OWNER_FOR_SQLSERVER);
			break;
		case DBDataSource.SYBASE:
			txtOwnerName.setVisible(true);
			lblOwnerName.setVisible(true);
			txtOwnerName.setText(DBDataSource.DEFAULT_OWNER_FOR_SYBASE);
			break;
		case DBDataSource.MYSQL:
		case DBDataSource.POSTGRES:
		case DBDataSource.DB2:
		case DBDataSource.ORACLE:
		default:
			txtOwnerName.setVisible(true);
			lblOwnerName.setVisible(true);
			txtOwnerName.setText(username);
			break;
		}
		
		parentComposite.layout(true);
	}

	private String setUserDefinedSQLQuery(String userSQLQuery) {
		return userDefinedSQLQueryString = userSQLQuery;
	}

	private String getUserDefinedSQLQuery() {
		return userDefinedSQLQueryString;
	}

	public void handlePageChanging(PageChangingEvent event) {
		if (event.getTargetPage() == getPreviousPage())
			return;
		IWizardPage page = (IWizardPage) event.getCurrentPage();
		if (!page.getName().equals(getName()))
			return;
		username = txtConnectAsUser.getText();
		password = txtConnectAsPwd.getText();
		ownerName = txtOwnerName.getText();

		if (userDefinedSQLQuery) {
			setUserDefinedSQLQuery(userSqlQueryName.getText());
		}

		cancelableJobExecutor = new ReadDatabaseCatalogExecutor();
		IRunnableWithProgress op = new IRunnableWithProgress() {

			public void run(IProgressMonitor monitor)
					throws InvocationTargetException, InterruptedException {
				monitor.beginTask("Connecting to database...", 100);
				monitor.worked(10);
				cancelableJobExecutor.performJob(monitor);
				monitor.done();
			}
		};
		boolean retry = true;
		boolean performJob = true;
		while (retry) {
			retry = false;
			try {
				cancelableJobExecutor = new ReadDatabaseCatalogExecutor();
				if (!parentComposite.isDisposed())
					getContainer().run(true, true, op);

			} catch (Throwable e) {
				cancelableJobExecutor.exception = e;
			}
			if (!cancelableJobExecutor.isCancelled()) {
				performJob = cancelableJobExecutor.isJobDoneSuccesfully();
				if (!performJob) {
					Throwable exception = cancelableJobExecutor.getException();
					if (exception != null) {
						Status status = new Status(IStatus.ERROR,
								ModulePlugin.PLUGIN_ID, 0,
								exception.toString(), exception);

						DBErrorDialog dbErrorDialog = new DBErrorDialog(
								getShell(), "Database Import Error", "",
								status, IStatus.ERROR, false);
						 dbErrorDialog.open();
					}
				}
			} else {
				performJob = false;
			}
		}
		cancelableJobExecutor = null;
		event.doit = performJob;
	}

	protected Integer parseStringToInt(String s) throws NumberFormatException {
		if (s == null || "".equals(s))
			return 0;

		Integer n = Integer.parseInt(s);

		return n;
	}

	private class SharedJdbcSelector implements SelectionListener {

		public void widgetDefaultSelected(SelectionEvent e) {

		}

		public void widgetSelected(SelectionEvent e) {
			Set<String> hashSet = new HashSet<String>();
			hashSet.add("sharedjdbc");
			FileSelector fileSelector = new JdbcResourceSelector(
					parentComposite.getShell(), projectName, hashSet);
			fileSelector.addFilter(new OnlyFileInclusionFilter(hashSet));
			if (fileSelector.open() == Dialog.OK) {
				if (fileSelector.getFirstResult() instanceof String) {
					String firstResult = (String) fileSelector.getFirstResult();
					txtJDBCURI.setText(firstResult);
				}
			}
		}

	}

	public class ReadDatabaseCatalogExecutor {

		private DaemonThreadFactory factory = new DaemonThreadFactory();
		private ExecutorService executor;
		private ArrayBlockingQueue<Object> mailbox;
		public Throwable exception;
		private boolean done;
		private boolean cancelled;

		/**
		 * Default constructor.
		 */
		public ReadDatabaseCatalogExecutor() {
			this.executor = Executors.newSingleThreadExecutor(factory);
			this.mailbox = new ArrayBlockingQueue<Object>(1);
		}

		/**
		 * This method provides facility to make a connection to the JMX agent.
		 */
		public void performJob(final IProgressMonitor monitor) {

			done = false;
			exception = null;
			boolean result = true;
			executor.submit(new Runnable() {
				public void run() {
					boolean success = false;

					try {

						if (userDefinedSQLQuery) {
							helper.generateCatalog(
									DBDataSourceImpl.resolveDatabaseName(
											driver, connURL), jdbcURI, driver,
									connURL, username, password, ownerName,
									retryCount, sslConnectionInfo, true, monitor,
									getUserDefinedSQLQuery());
							success = true;
						} else {
							if (DBDataSourceImpl.isOracleDriver(driver)) {
								helper.generateCatalog(ownerName, jdbcURI,
										driver, connURL, username, password,
										ownerName, retryCount, sslConnectionInfo, true, monitor, null);
							} else {
								helper.generateCatalog(DBDataSourceImpl
										.resolveDatabaseName(driver, connURL),
										jdbcURI, driver, connURL, username,
										password, ownerName, retryCount, sslConnectionInfo, true,
										monitor, null);
							}
							success = true;
						}
					} catch (Throwable e) {
						exception = e;
					}
					mailbox.offer(success);
				}
			});
			try {
				Boolean take = null;
				while (!cancelled && take == null) {
					take = (Boolean) mailbox.poll(2000, TimeUnit.MILLISECONDS);
					cancelled = monitor.isCanceled();// do we need to get lock?
				}
				if (cancelled) {
					mailbox.offer(false);
					take = false;
				}
				result = take;

			} catch (InterruptedException e) {

			} finally {
				done = result;
				// this call ensures to close the connectionpool
				// after import completes
				DBConnectionManager.getInstance().release();
				executor.shutdownNow();
			}
		}

		public boolean isJobDoneSuccesfully() {
			return done;
		}

		public boolean isCancelled() {
			return cancelled;
		}

		public Throwable getException() {
			return exception;
		}

		/**
		 * This class provides a facility to create daemon threads.
		 */
		private class DaemonThreadFactory implements ThreadFactory {
			public Thread newThread(Runnable r) {
				Thread t = Executors.defaultThreadFactory().newThread(r);
				t.setName("JobThread");
				t.setDaemon(true);
				return t;
			}
		}
	}
}
