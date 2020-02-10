package com.tibco.cep.studio.ui.wizards.export;

import static com.tibco.cep.runtime.service.security.BETrustedCertificateManager.FOLDER_SUFFIX;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import  com.tibco.be.jdbcstore.ssl.JdbcSSLConnectionInfo;
import com.tibco.cep.repo.BEProject;
import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.cep.runtime.service.security.BEIdentity;
import com.tibco.cep.runtime.service.security.BEIdentityUtilities;
import com.tibco.cep.runtime.service.security.BEKeystoreIdentity;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.util.GvUtil;
import com.tibco.cep.studio.core.util.JdbcStudioSSLUtils;
import com.tibco.cep.studio.core.util.PasswordUtil;
import com.tibco.cep.studio.core.validation.DefaultResourceValidator;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.dialog.ResourceSelector;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

public class JDBCDeployExportMigrationWizardPage extends WizardPage {

	private static final String SHARED_JDBC_CONNECTION_FILE_EXTENSION = "sharedjdbc"; //$NON-NLS-1$

	private IProject project;
	private Map<String, GlobalVariableDescriptor> globalVariables;

	private String dataBaseType;
	private String dataBaseDriver;

	// user option to turn on migration mode
	private Button bMigrationEnabled;
	private boolean migrationEnabled;

	// jdbc shared connection resource
	private Text tJDBCSharedConnPath;
	private Button bJDBCSharedConnBrowse;
	private Button bJDBCSharedConnClear;

	// JDBC URI
	private Text tDBURI;
	private String dbURL;

	// username
	private Text tDBUserName;
	private String dbUserName;

	// password
	private Text tDBPassword;
	private String dbPassword;

	// schema owner
	private Text tDBSchemaOwner;
	private String dbSchemaOwner;

	// populateObjectTable [be.jdbc.schemamigration.populateObjectTable]
//	private Button bPopulateObjectTable;
//	private boolean populateObjectTable;

	private Button bTestConnection;

	private WidgetListener widgetListener = null;

	private JdbcSSLConnectionInfo sslConnectionInfo;

	protected JDBCDeployExportMigrationWizardPage(IProject project) {
		super(JDBCDeployExportMigrationWizardPage.class.getSimpleName());
		this.project = project;
		globalVariables = DefaultResourceValidator.getGlobalVariableNameValues(project.getName());
		this.widgetListener = new WidgetListener();
		setTitle(Messages.getString("jdbc.scripts.export.wizard.migrationpage.title")); //$NON-NLS-1$
		setMessage(Messages.getString("jdbc.scripts.export.wizard.migrationpage.description"));//$NON-NLS-1$
	}

	@Override
	public void createControl(Composite parent) {
		Composite parentComposite = new Composite(parent, SWT.NULL);
		parentComposite.setLayout(new GridLayout(4, false));
		parentComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// migration enabled button
		bMigrationEnabled = PanelUiUtil.createCheckBox(parentComposite, "Generate Migration Scripts"); //$NON-NLS-1$
		bMigrationEnabled.setLayoutData(PanelUiUtil.getGridData(4));
		bMigrationEnabled.addSelectionListener(widgetListener);

		PanelUiUtil.createLabel(parentComposite, "Connection Configuration :"); //$NON-NLS-1$
		Composite comp = PanelUiUtil.getTextButtonComposite(parentComposite, 3, 3);
		tJDBCSharedConnPath = PanelUiUtil.createTextSpan(comp, 1);
		tJDBCSharedConnPath.addModifyListener(widgetListener);
		bJDBCSharedConnBrowse = PanelUiUtil.createBrowsePushButton(comp, tJDBCSharedConnPath);
		bJDBCSharedConnBrowse.addSelectionListener(widgetListener);
		bJDBCSharedConnClear = PanelUiUtil.createImageButton(comp, StudioUIPlugin.getDefault().getImage("icons/rem_co.gif"), tJDBCSharedConnPath.getLineHeight()); //$NON-NLS-1$
		bJDBCSharedConnClear.addSelectionListener(widgetListener);

		// db uri control
		PanelUiUtil.createLabel(parentComposite, "Database URL :"); //$NON-NLS-1$
		tDBURI = PanelUiUtil.createText(parentComposite, SWT.DEFAULT, 3);
		tDBURI.addModifyListener(widgetListener);

		// db user name
		PanelUiUtil.createLabel(parentComposite, "Database Username :"); //$NON-NLS-1$
		tDBUserName = PanelUiUtil.createText(parentComposite, SWT.DEFAULT, 3);
		tDBUserName.addModifyListener(widgetListener);

		// db password
		PanelUiUtil.createLabel(parentComposite, "Database Password :"); //$NON-NLS-1$
		tDBPassword = PanelUiUtil.createTextPassword(parentComposite);
		tDBPassword.setLayoutData(PanelUiUtil.getGridData(3));
		tDBPassword.addModifyListener(widgetListener);

		PanelUiUtil.createLabel(parentComposite, "Database Schema Owner :"); //$NON-NLS-1$
		tDBSchemaOwner = PanelUiUtil.createText(parentComposite, SWT.DEFAULT, 3);
		tDBSchemaOwner.addModifyListener(widgetListener);

		// populate object table control
//		PanelUiUtil.createLabel(parentComposite, "Populate Object Table :"); //$NON-NLS-1$
//		bPopulateObjectTable = PanelUiUtil.createCheckBox(parentComposite, ""); //$NON-NLS-1$
//		bPopulateObjectTable.addSelectionListener(widgetListener);
//		PanelUiUtil.createLabelFiller(parentComposite);
//		PanelUiUtil.createLabelFiller(parentComposite);

		// test connection
		bTestConnection = PanelUiUtil.createPushButton(parentComposite, "Test Connection"); //$NON-NLS-1$
		bTestConnection.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 4, 1));
		bTestConnection.addSelectionListener(widgetListener);
		setControl(parentComposite);

		// set defaults
		tJDBCSharedConnPath.setEnabled(false);
		bJDBCSharedConnBrowse.setEnabled(false);
		bJDBCSharedConnClear.setEnabled(false);
		tDBURI.setEnabled(false);
		tDBUserName.setEnabled(false);
		tDBPassword.setEnabled(false);
		tDBSchemaOwner.setEnabled(false);
//		bPopulateObjectTable.setEnabled(false);
		bTestConnection.setEnabled(false);
	}

	private boolean handleWidgetSelection(final Object source) {
		if (source == bMigrationEnabled) {
			migrationEnabled = bMigrationEnabled.getSelection();
			//enable/disable all the db conn inputs
			setDBConnInputsEnabled(migrationEnabled);
			//enable/disable the populate object table selection
//			bPopulateObjectTable.setEnabled(migrationEnabled);
		}
		else if (source == bJDBCSharedConnBrowse) {
			ResourceSelector fileSelector = new ResourceSelector(getShell());
			fileSelector.setTitle("JDBC Connection Configuration Selector"); //$NON-NLS-1$
			fileSelector.setMessage("Select a JDBC Connection Configuration"); //$NON-NLS-1$
			fileSelector.setAllowMultiple(false);
			fileSelector.setEmptyListMessage("No JDBC Connection Configuration found"); //$NON-NLS-1$
			fileSelector.setInput(project);
			fileSelector.setInitialSelection(project.findMember(tJDBCSharedConnPath.getText()));
			fileSelector.addFilter(new FileExtensionFilter(new String[] { SHARED_JDBC_CONNECTION_FILE_EXTENSION }));
			fileSelector.setValidator(new SupportedJDBCFileSelectionValidator());
			if (fileSelector.open() == Dialog.OK) {
				if (fileSelector.getFirstResult() instanceof IResource) {
					//get the result
					IResource firstResult = (IResource) fileSelector.getFirstResult();
					//update the path text, modify listener will trigger further processing
					tJDBCSharedConnPath.setText(firstResult.getProjectRelativePath().toPortableString());
				}
			}
		}
		else if (source == bJDBCSharedConnClear) {
			tJDBCSharedConnPath.setText(""); //$NON-NLS-1$
			tDBURI.setText(""); //$NON-NLS-1$
			tDBUserName.setText(""); //$NON-NLS-1$
			tDBPassword.setText(""); //$NON-NLS-1$
			setDataBaseType(dataBaseType, JDBCDeployExportWizard.DBTYPE_DRIVER_MAP.get(dataBaseType));
			setDBConnInputsEnabled(true);
		}
		else if (source == bTestConnection) {
			Connection conn = null;
			try {
				Class.forName(dataBaseDriver).newInstance();

				if (sslConnectionInfo != null) {
					sslConnectionInfo.setUser(tDBUserName.getText());
					sslConnectionInfo.setPassword(tDBPassword.getText());
					sslConnectionInfo.loadSecurityProvider();
					conn = DriverManager.getConnection(tDBURI.getText(), sslConnectionInfo.getProperties());
				}
				else {
					conn = DriverManager.getConnection(tDBURI.getText(), tDBUserName.getText(), tDBPassword.getText());
				}
				
				DatabaseMetaData metadata = conn.getMetaData();
				String msg = Messages.getString("jdbc.scripts.export.wizard.migrationpage.connection.success.msg", //$NON-NLS-1$
						metadata.getDatabaseProductName(),
						metadata.getDatabaseProductVersion(),
						metadata.getDriverName(),
						metadata.getDriverVersion());
				MessageDialog.openInformation(getShell(), "JDBC Connection", msg); //$NON-NLS-1$
			} catch (InstantiationException e) {
				StudioUIPlugin.log(Messages.getString("jdbc.scripts.export.wizard.migrationpage.connection.iex.log", dataBaseDriver,dataBaseType), e); //$NON-NLS-1$
				setErrorMessage(Messages.getString("jdbc.scripts.export.wizard.migrationpage.connection.iex.msg",dataBaseType)); //$NON-NLS-1$
				setPageComplete(false);
				return false;
			} catch (IllegalAccessException e) {
				StudioUIPlugin.log(Messages.getString("jdbc.scripts.export.wizard.migrationpage.connection.iaex.log", dataBaseDriver,dataBaseType), e); //$NON-NLS-1$
				setErrorMessage(Messages.getString("jdbc.scripts.export.wizard.migrationpage.connection.iaex.msg",dataBaseType)); //$NON-NLS-1$
				setPageComplete(false);
				return false;
			} catch (ClassNotFoundException e) {
				StudioUIPlugin.log(Messages.getString("jdbc.scripts.export.wizard.migrationpage.connection.cnfex.log", dataBaseDriver,dataBaseType), e); //$NON-NLS-1$
				setErrorMessage(Messages.getString("jdbc.scripts.export.wizard.migrationpage.connection.cnfex.msg",dataBaseType)); //$NON-NLS-1$
				setPageComplete(false);
				return false;
			} catch (SQLException e) {
				StudioUIPlugin.log(Messages.getString("jdbc.scripts.export.wizard.migrationpage.connection.sqlex.log", tDBURI.getText(), tDBUserName.getText()), e); //$NON-NLS-1$
				setErrorMessage(Messages.getString("jdbc.scripts.export.wizard.migrationpage.connection.sqlex.msg", tDBURI.getText())); //$NON-NLS-1$
				setPageComplete(false);
				return false;
			} finally {
				if (conn != null) {
					try {
						conn.close();
					} catch (SQLException e) {
						StudioUIPlugin.log(Messages.getString("jdbc.scripts.export.wizard.migrationpage.connection.closefailure.log", tDBURI.getText()), e); //$NON-NLS-1$
					}
				}
			}
		}
		return true;
	}

	private boolean handleTextModification(Object source) {
		if (source == tJDBCSharedConnPath) {
			String jdbcSharedConnPath = tJDBCSharedConnPath.getText();
			if (jdbcSharedConnPath != null) {
				IResource resource = project.findMember(jdbcSharedConnPath);
				if (resource instanceof IFile) {
					IFile resourceAsFile = (IFile) resource;
					if (resourceAsFile.getFileExtension().equals(SHARED_JDBC_CONNECTION_FILE_EXTENSION) == true) {
						Map<String, Object> model = parse(resource);
						if (isValidSharedJDBCConnectionResource(model) == true) {
							updateDBConnInputs(resource);
						}
					}
				}
			}
		}
		return true;
	}

	private void setDBConnInputsEnabled(boolean enabled) {
		tJDBCSharedConnPath.setEnabled(enabled);
		bJDBCSharedConnBrowse.setEnabled(enabled);
		bJDBCSharedConnClear.setEnabled(tJDBCSharedConnPath.getText() != null && tJDBCSharedConnPath.getText().length() != 0);
		tDBURI.setEnabled(enabled);
		tDBUserName.setEnabled(enabled);
		tDBPassword.setEnabled(enabled);
		tDBSchemaOwner.setEnabled(enabled);
	}

	private void updateDBConnInputs(IResource resource) {
		// enable the clear button
		setDBConnInputsEnabled(true);
		// parse the selected resource
		Map<String, Object> model = parse(resource);
		if (model != null && "JDBC".equals(model.get("connectionType")) == true) {  //$NON-NLS-1$
			// we are in business
			tDBURI.removeModifyListener(widgetListener);
			String location = (String)model.get("location");  //$NON-NLS-1$
			tDBURI.setEnabled(GvUtil.isGlobalVar(location));
			String substitutedGlobalVar = GvUtil.getGvDefinedValue(globalVariables, location);
			tDBURI.setText(substitutedGlobalVar.equals("") ? location : substitutedGlobalVar);  //$NON-NLS-1$
			tDBURI.addModifyListener(widgetListener);
			
			dataBaseDriver = (String)model.get("driver");
			
			tDBUserName.removeModifyListener(widgetListener);
			String username = (String)model.get("user");  //$NON-NLS-1$
			tDBUserName.setEnabled(GvUtil.isGlobalVar(username));
			substitutedGlobalVar = GvUtil.getGvDefinedValue(globalVariables, username);
			tDBUserName.setText(substitutedGlobalVar.equals("") ? username : substitutedGlobalVar);  //$NON-NLS-1$
			tDBUserName.addModifyListener(widgetListener);

			tDBPassword.removeModifyListener(widgetListener);
			String password = (String)model.get("password");  //$NON-NLS-1$
			tDBPassword.setEnabled(GvUtil.isGlobalVar(password));
			password = GvUtil.getGvDefinedValue(globalVariables, password);
			if (PasswordUtil.isEncodedString(password) == true) {
				password = PasswordUtil.getDecodedString(password);
			}
			tDBPassword.setText(password);
			tDBPassword.addModifyListener(widgetListener);

			tDBSchemaOwner.removeModifyListener(widgetListener);
			tDBSchemaOwner.setText("");
			tDBSchemaOwner.setEnabled(true);
			tDBSchemaOwner.addModifyListener(widgetListener);

			if (model.containsKey(JdbcSSLConnectionInfo.KEY_JDBC_SSL_CONNECTION_INFO)) {
				sslConnectionInfo = (JdbcSSLConnectionInfo)model.get(JdbcSSLConnectionInfo.KEY_JDBC_SSL_CONNECTION_INFO);
			}
			else {
				sslConnectionInfo = null;
			}
		}
	}

	private void validate() {
		setPageComplete(false);
		bTestConnection.setEnabled(false);
		// do validation only if migration is enabled
		if (bMigrationEnabled.getSelection() == true) {
			// we have migration enabled, so do the validations
			// shared connection validation
			String jdbcSharedConnPath = tJDBCSharedConnPath.getText();
			if (jdbcSharedConnPath != null) {
				IResource resource = project.findMember(jdbcSharedConnPath);
				if (resource == null) {
					setErrorMessage(Messages.getString("jdbc.scripts.export.wizard.migrationpage.invalid.conn.config")); //$NON-NLS-1$
					return;
				}
				if (resource instanceof IFile) {
					IFile resourceAsFile = (IFile) resource;
					if (resourceAsFile.getFileExtension().equals(SHARED_JDBC_CONNECTION_FILE_EXTENSION) == false) {
						setErrorMessage(Messages.getString("jdbc.scripts.export.wizard.migrationpage.invalid.conn.config")); //$NON-NLS-1$
						return;
					}
					Map<String, Object> model = parse(resource);
					if (isValidSharedJDBCConnectionResource(model) == false) {
						setErrorMessage(Messages.getString("jdbc.scripts.export.wizard.migrationpage.invalid.conn.config"));  //$NON-NLS-1$
						return;
					}
				}
			}
			// db URL
			String uri = tDBURI.getText();
			if (uri == null || uri.trim().length() == 0) {
				setErrorMessage(Messages.getString("jdbc.scripts.export.wizard.migrationpage.invalid.db.url")); //$NON-NLS-1$
				return;
			}
			if (GvUtil.isGlobalVar(uri) == true) {
				setErrorMessage(Messages.getString("jdbc.scripts.export.wizard.migrationpage.invalid.db.url")); //$NON-NLS-1$
				return;
			}
			// db username
			String username = tDBUserName.getText();
			if (username != null && GvUtil.isGlobalVar(username) == true) {
				setErrorMessage(Messages.getString("jdbc.scripts.export.wizard.migrationpage.invalid.db.username")); //$NON-NLS-1$
				return;
			}
			String password = tDBPassword.getText();
			if (password != null && GvUtil.isGlobalVar(password) == true) {
				setErrorMessage(Messages.getString("jdbc.scripts.export.wizard.migrationpage.invalid.db.password")); //$NON-NLS-1$
				return;
			}
			dbURL = uri;
			dbUserName = username;
			dbPassword = password;
			// schema owner can be anything
			dbSchemaOwner = tDBSchemaOwner.getText();
			// populate object table
//			populateObjectTable = bPopulateObjectTable.getSelection();
			// enable test connection
			bTestConnection.setEnabled(true);
		}
		setErrorMessage(null);
		setPageComplete(true);
	}

	private Map<String, Object> parse(IResource jdbcSharedConnectionFile) {
		try {
			Map<String, Object> model = new HashMap<String, Object>();
			// parse the incoming file
			DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
			fact.setNamespaceAware(true);
			DocumentBuilder builder = fact.newDocumentBuilder();
			String filePath = jdbcSharedConnectionFile.getLocation().toString();
			Boolean isProjLib = false;
			String sharedResFolder = null;
			if(filePath!=null && filePath.contains("projlib")){
				String sharedResFile= filePath.split("/")[(filePath.split("/")).length-1];
				sharedResFolder = filePath.split(".projlib")[0];
				if(!new File(sharedResFolder+".projlib").isDirectory()){
					filePath=getSharedResource(filePath,sharedResFolder,sharedResFile);
					isProjLib = true;
				}
			}
			FileInputStream fis = new FileInputStream(filePath);
			Document doc = builder.parse(fis);
			boolean useSsl = false;
			Node sslNode = null;
			NodeList docChildNodeList = doc.getFirstChild().getChildNodes();
			for (int i = 0; i < docChildNodeList.getLength(); i++) {
				Node docChild = docChildNodeList.item(i);
				if (docChild.getNodeType() != Node.COMMENT_NODE && docChild.getNodeType() != Node.TEXT_NODE) {
					if (docChild.getNodeName().equals("config") == true) {  //$NON-NLS-1$
						NodeList childNodes = docChild.getChildNodes();
						for (int j = 0; j < childNodes.getLength(); j++) {
							Node childNode = childNodes.item(j);
							if (childNode.getNodeType() != Node.COMMENT_NODE && childNode.getNodeType() != Node.TEXT_NODE) {
								if ("ssl".equalsIgnoreCase(childNode.getNodeName())) {
									sslNode = childNode;
								}
								else if (childNode.getChildNodes().getLength() > 0) {
									model.put(childNode.getNodeName(), GvUtil.getGvDefinedValue(project,childNode.getFirstChild().getNodeValue()));
									if ("useSsl".equalsIgnoreCase(childNode.getNodeName())) {
										String useSslValue=GvUtil.getGvDefinedValue(project, childNode.getFirstChild().getNodeValue());
										useSsl = Boolean.parseBoolean(useSslValue);
									}
								}
							}
						}
					}
				}
			}
			if (useSsl && sslNode != null) {
				NodeList sslChildNodes = sslNode.getChildNodes();
				Map<String, String> sslModel = new HashMap<String, String>();
				for (int i = 0; i < sslChildNodes.getLength(); i++) {
					Node childNode = sslChildNodes.item(i);
					if (childNode.getChildNodes().getLength() > 0) {
						String val=GvUtil.getGvDefinedValue(project, childNode.getFirstChild().getNodeValue());
						sslModel.put(childNode.getNodeName(), val);
					}
				}
				BEProject beProject = new BEProject(jdbcSharedConnectionFile.getProject().getLocation().toString());
				beProject.load();
				String user= GvUtil.getGvDefinedValue(project, (String)model.get("user"));
				String password= GvUtil.getGvDefinedValue(project, (String)model.get("password"));
				String location= GvUtil.getGvDefinedValue(project, (String)model.get("location"));
				String driver= GvUtil.getGvDefinedValue(project, (String)model.get("driver"));
				
				if(isProjLib&&sharedResFolder!=null){
					String cert = sslModel.get("cert");
					if (cert.endsWith(FOLDER_SUFFIX)) {
			            cert = cert.substring(0, cert.lastIndexOf(FOLDER_SUFFIX));
			        }
					String path = JdbcStudioSSLUtils.unzip(sharedResFolder+".projlib", cert);
					path = path+File.separator+cert;
					sslModel.put("cert", path);
					
					sslModel.put("projLibPath", sharedResFolder+".projlib");
					
					
				}
				
				JdbcSSLConnectionInfo sslConnectionInfo = createSSLConnectionInfo(user, password, location,driver, sslModel, beProject);
				model.put(JdbcSSLConnectionInfo.KEY_JDBC_SSL_CONNECTION_INFO, sslConnectionInfo);
			}
			return model;
		} catch (FileNotFoundException ex) {
			StudioUIPlugin.log(Messages.getString("jdbc.scripts.export.wizard.migrationpage.conn.config.notfound.log", jdbcSharedConnectionFile.getLocation().toString()), ex); //$NON-NLS-1$
			return null;
		} catch (SAXException e) {
			StudioUIPlugin.log(Messages.getString("jdbc.scripts.export.wizard.migrationpage.conn.config.saxex.log", jdbcSharedConnectionFile.getLocation().toString()), e); //$NON-NLS-1$
			return null;
		} catch (IOException e) {
			StudioUIPlugin.log(Messages.getString("jdbc.scripts.export.wizard.migrationpage.conn.config.ioex.log", jdbcSharedConnectionFile.getLocation().toString()), e); //$NON-NLS-1$
			return null;
		} catch (ParserConfigurationException e) {
			StudioUIPlugin.log(Messages.getString("jdbc.scripts.export.wizard.migrationpage.conn.config.parserconfigex.log", jdbcSharedConnectionFile.getLocation().toString()), e); //$NON-NLS-1$
			return null;
		} catch (Exception e) {
			StudioUIPlugin.log(Messages.getString("jdbc.scripts.export.wizard.migrationpage.conn.config.ex.log", jdbcSharedConnectionFile.getLocation().toString()), e);
			return null;
		}
	}
	/**
     * Fetches referenced identity file details.
     * @param idReference
     * @param path
     * @param project
     * @return
     * @throws Exception
     */
	private static BEIdentity getIdentity(String idReference, String path, BEProject project) throws Exception {
		if ((idReference != null) && !idReference.trim().isEmpty()) {
			if (idReference.startsWith("/")) {
				return BEIdentityUtilities.fetchIdentity(path, project.getGlobalVariables(), idReference);
			}
			else {
				throw new Exception("Incorrect identity file reference : " + idReference);
			}
		}
		return null;
	}

	public static String getSharedResource(String filePath, String sharedResFolder,String sharedResFile) {
		 byte[] buffer = new byte[1024];
	     try{
	    	File folder = new File(sharedResFolder+"_temp");
	    	if(!folder.exists()){
	    		folder.mkdir();
	    	}
	 
	    	ZipInputStream zis = new ZipInputStream(new FileInputStream(sharedResFolder+".projlib"));
	    	ZipEntry ze = zis.getNextEntry();
	    	String fileName=null;
	    	while(ze!=null){
	    	   fileName = ze.getName();
	    	   if(fileName.contains(sharedResFile)){
	    		   File newFile = new File(sharedResFolder+"_temp" + File.separator + fileName);
                  new File(newFile.getParent()).mkdirs();
                  FileOutputStream fos = new FileOutputStream(newFile);             
                  int len;
                  while ((len = zis.read(buffer)) > 0) {
               	   fos.write(buffer, 0, len);
                  }
                  fos.close();
                  break;
	    	   }
	            ze = zis.getNextEntry();
	    	}
	        zis.closeEntry();
	    	zis.close();
	    	return sharedResFolder+"_temp" + File.separator + fileName;
	     }catch(Exception e){
	    	 
	     }
		return sharedResFolder+"_temp";
	}
	
	private boolean isValidSharedJDBCConnectionResource(Map<String, Object> model) {
		if (model != null) {
			if ("JDBC".equals(model.get("connectionType")) == true
					&& JDBCDeployExportWizard.DBTYPE_COMPATIBLE_DRIVERS_MAP.get(dataBaseType) != null) {  //$NON-NLS-1$
				return JDBCDeployExportWizard.DBTYPE_COMPATIBLE_DRIVERS_MAP.get(dataBaseType).contains(model.get("driver"));
			}
		}
		return false;
	}

	public void setDataBaseType(String dataBaseType, String dataBaseDriver) {
		this.dataBaseType = dataBaseType;
		this.dataBaseDriver = dataBaseDriver;
	}

	public boolean isMigrationEnabled() {
		return migrationEnabled;
	}

	public String getDBURL() {
		return dbURL;
	}

	public String getDBUserName() {
		return dbUserName;
	}

	public String getDBPassword() {
		return dbPassword;
	}

	public String getDBSchemaOwner() {
		return dbSchemaOwner;
	}

	public JdbcSSLConnectionInfo getSSLConnectionInfo() {
		return this.sslConnectionInfo;
	}

//	public boolean isPopulateObjectTable() {
//		return populateObjectTable;
//	}

	private class WidgetListener extends SelectionAdapter implements ModifyListener {

		@Override
		public void widgetSelected(SelectionEvent e) {
			if (handleWidgetSelection(e.getSource()) == true) {
				validate();
			}
		}

		@Override
		public void modifyText(ModifyEvent e) {
			if (handleTextModification(e.getSource()) == true) {
				validate();
			}
		}

	}

	private class SupportedJDBCFileSelectionValidator implements ISelectionStatusValidator {

		@Override
		public IStatus validate(Object[] selection) {
			for (Object object : selection) {
				if (object instanceof IFile) {
					Map<String, Object> model = parse((IResource) object);
					if (isValidSharedJDBCConnectionResource(model) == true) {
						return new Status(IStatus.OK, StudioCorePlugin.PLUGIN_ID, null);
					} else {
						return new Status(IStatus.ERROR, StudioCorePlugin.PLUGIN_ID, Messages.getString("jdbc.scripts.export.wizard.migrationpage.unsuitable.driver", dataBaseType));  //$NON-NLS-1$
					}
				}
			}
			return new Status(IStatus.ERROR, StudioCorePlugin.PLUGIN_ID, null);
		}
	}
	
	/**
	 * Create SSL connection Info.
	 * @param username
	 * @param password
	 * @param connUrl
	 * @param jdbcDriver
	 * @param sslModel
	 * @param beProject
	 * @return
	 * @throws Exception
	 */
	private JdbcSSLConnectionInfo createSSLConnectionInfo(String username, String password, String connUrl, String jdbcDriver,
			Map<String, String> sslModel, BEProject beProject) throws Exception {
    	
    	JdbcSSLConnectionInfo sslConnectionInfo = JdbcSSLConnectionInfo.createConnectionInfo(username, password, connUrl, jdbcDriver);
    	
    	String trustedCertsURI = sslModel.get("cert");
    	trustedCertsURI = GvUtil.getGvDefinedValue(globalVariables, trustedCertsURI);
    	
    	String trustStorePassword = sslModel.get("trustStorePassword");
    	trustStorePassword = GvUtil.getGvDefinedValue(globalVariables, trustStorePassword);
    	if (PasswordUtil.isEncodedString(trustStorePassword) == true) {
    		trustStorePassword = PasswordUtil.getDecodedString(trustStorePassword);
		}
    	
    	KeyStore trustedKeysStore = JdbcStudioSSLUtils.createKeystore(trustedCertsURI, trustStorePassword, beProject, beProject.getGlobalVariables(), true);
    	String trustStore = JdbcStudioSSLUtils.storeKeystore(trustedKeysStore, trustStorePassword);
    	
    	sslConnectionInfo.setTrustStoreProps(trustStore, JdbcStudioSSLUtils.KEYSTORE_JKS_TYPE, trustStorePassword);
    	
		String clientAuth = sslModel.get("requiresClientAuthentication");
		clientAuth = GvUtil.getGvDefinedValue(globalVariables, clientAuth);
		
		if ("true".equalsIgnoreCase(clientAuth)) {
			String identityPath = sslModel.get("identity");
			identityPath = GvUtil.getGvDefinedValue(globalVariables, identityPath);
			
			BEIdentity keyStoreIdentity = null;
			if(sslModel.get("projLibPath")!=null&&!sslModel.get("projLibPath").isEmpty()){
				keyStoreIdentity = getIdentity(sslModel.get("identity"), sslModel.get("projLibPath"), beProject);	
			}else{
				keyStoreIdentity = getIdentity(identityPath, beProject);
			}
			
			if (keyStoreIdentity != null && keyStoreIdentity instanceof BEKeystoreIdentity) {
				sslConnectionInfo.setKeyStoreProps(
						((BEKeystoreIdentity)keyStoreIdentity).getStrKeystoreURL(),
						((BEKeystoreIdentity)keyStoreIdentity).getStrStoreType(),
						((BEKeystoreIdentity)keyStoreIdentity).getStrStorePassword());
			}
			else {
				String message = "Identity Resource - '" + identityPath + "' must be of type 'Identity file'";
				throw new Exception("JDBC Connection - " + message);
			}
		}
		
		String verifyHostName = sslModel.get("verifyHostName");
		verifyHostName = GvUtil.getGvDefinedValue(globalVariables, verifyHostName);
		
		if ("true".equalsIgnoreCase(verifyHostName)) {
			String expectedHostName = sslModel.get("expectedHostName");
			if(expectedHostName!=null){
				expectedHostName = GvUtil.getGvDefinedValue(globalVariables, expectedHostName);
			}
			
			sslConnectionInfo.setVerifyHostname(expectedHostName);
		}
		
		return sslConnectionInfo;
    }
	
	/**
	 * Get the BEIdentity for the specified id file reference.
	 * @param idReference
	 * @param project
	 * @return
	 * @throws Exception
	 */
	private static BEIdentity getIdentity(String idReference, BEProject project) throws Exception {
		project.load();
		if ((idReference != null) && !idReference.trim().isEmpty()) {
			if (idReference.startsWith("/")) {
				return BEIdentityUtilities.fetchIdentity(project, project.getGlobalVariables(), idReference);
			}
			else {
				throw new Exception("Incorrect identity file reference : " + idReference);
			}
		}
		return null;
	}

	public String getDatabaseDriver() {
		return this.dataBaseDriver;
	}
}