package com.tibco.cep.studio.dbconcept.conceptgen.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.tibco.be.jdbcstore.ssl.JdbcSSLConnectionInfo;
import com.tibco.cep.sharedresource.jdbc.JdbcConfigModelMgr;
import com.tibco.cep.sharedresource.jdbc.JdbcSSLSharedresourceHelper;
import com.tibco.cep.studio.core.util.GvUtil;
import com.tibco.cep.studio.dbconcept.ModulePlugin;
import com.tibco.cep.studio.dbconcept.conceptgen.DBDataSource;
import com.tibco.cep.studio.dbconcept.conceptgen.DBEntityCatalog;
import com.tibco.cep.studio.dbconcept.conceptgen.ModelGenerator;
import com.tibco.security.ObfuscationEngine;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.navigation.NodeKindNodeTest;

public class DBCeptGenerator {
	
    static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }
    
    
	//private static final int defaultTimeout = 60; 
	
	private IProject project;
	
	private String projectPath;
	private String conceptFolderPath;
	private String eventFolderPath;
	private String eventDestURI;
	protected String sharedResourceFolderPath;
	protected String constraintFileName;
	private String jdbcResourceURI;
	
	private String connURL;
	private String driverName;
	private String dsUserName;
	private String dsPassword;
	private String schemaOwner;
	private String dsName;
	
	protected String jdbcUserName;
	protected String jdbcPassword;
	
	protected String catalogImplClass;
	private boolean genCeptsWithRel;
	private boolean generateEvent = false;
	
	private DBEntityCatalog dbEntityCatalog;
	private ModelGenerator modelGen;
	
	private Properties prop = new Properties();
	
	public DBCeptGenerator(IProject prj) throws Exception {
//		this.projectPath = projectName;
//		loadProject();
		this.project = prj;
	}
	
	public DBCeptGenerator() throws Exception {
//		parseArgs();
//		validateInputAndLoadProject();
	}
	
	public void generateCepts() throws Exception {
		
		DBDataSourceImpl dbs = new DBDataSourceImpl();
		dbs.setConnectionUrl(connURL);
		dbs.setDriver(driverName);
		dbs.setUsername(dsUserName);
		dbs.setPassword(dsPassword);
		dbs.setDsName(dsName);
		dbs.setSchemaOwner(schemaOwner);
		dbs.setJdbcResourceURI(jdbcResourceURI);
		
		generateCatalog(dbs, genCeptsWithRel, null, new NullProgressMonitor());
		
		modelGen = new ModelGenerator(dbEntityCatalog, project);
		generateConcepts(conceptFolderPath, true);
		if(generateEvent){
			generateEvents(eventFolderPath, eventDestURI, true);
		}
		//TODO is it valid for EMF?
//        if (System.getProperty("TIBCO.CEP.modules.use.constraints.file", "false").equals("true")) {
//        	generateConstraintRegistry(conceptFolderPath, sharedResourceFolderPath, constraintFileName);
//        }
		modelGen.save();
	}
	
	public void generateCatalog(DBDataSource dbs, boolean genCeptWithRel, String userSQLQuery, IProgressMonitor monitor) throws Exception {
		CatalogHelper catalogHelper = DBCatalogHelperFactory.createDBCatalogHelper(dbs);
		dbEntityCatalog = new GenericDBCatalog(dbs,catalogHelper);
		dbEntityCatalog.buildCatalog(genCeptWithRel, userSQLQuery, monitor);
	}
	
	public void generateConcepts(String ceptPath, boolean overwrite) throws Exception {
		if (modelGen != null) {
			modelGen.generateConcepts(ceptPath, overwrite);
		} else {
			modelGen = new ModelGenerator(dbEntityCatalog, project);
			modelGen.generateConcepts(ceptPath, overwrite);
		}
	}
	
	public void generateEvents(String eventPath, String destinationURI, boolean overwrite) throws Exception {
		if (modelGen != null) {
			modelGen.generateEvents(eventPath, destinationURI, overwrite);
		} else {
			modelGen = new ModelGenerator(dbEntityCatalog, project);
			modelGen.generateEvents(eventPath, destinationURI, overwrite);
		}
	}
	
	public void saveProject() throws Exception {
		if (modelGen != null) {
			modelGen.save(); 
		} else {
			modelGen = new ModelGenerator(dbEntityCatalog, project);
			modelGen.save(); 
		}
	}

	public void generateConstraintRegistry(String ceptPath, String sharedResPath, String constFileName) throws Exception {
	}
	
	/**
	 * get shared resource path...
	 * @param resource
	 * @return
	 */
	private String getSharedResourcePath(IResource resource) {
		String sharedResourcePath = null;
		String sharedRelativePath = resource.getProjectRelativePath().toString();
		if ( sharedRelativePath.startsWith("/")) {
			sharedResourcePath = sharedRelativePath;
		} else {
			sharedResourcePath = "/" + sharedRelativePath;
		}
		return sharedResourcePath;
	}
    
	/*
	 * Get JDBC values from shared resource file.
	 */
	public Map<?, ?> getJDBCComponents(IResource resource) {
		
		//get the JDBC props 
		JdbcConfigModelMgr modelMgr = new JdbcConfigModelMgr(resource);

		modelMgr.parseModel();
		
		String jdbcdriver = modelMgr.getStringValue("driver");
		jdbcdriver = GvUtil.getGvDefinedValue(modelMgr.getProject(), jdbcdriver);
		if ( jdbcdriver.startsWith("oracle.jdbc.driver.OracleDriver") )
			jdbcdriver = "oracle.jdbc.OracleDriver";
		
			//String url = tgDbUrlJdbc.getGvDefinedValue(modelmgr.getProject());
			String url = modelMgr.getStringValue("location");
			url = GvUtil.getGvDefinedValue(modelMgr.getProject(), url);
			//String username = tgUsernameJdbc.getGvDefinedValue(modelmgr.getProject());
			String username = modelMgr.getStringValue("user");
			username = GvUtil.getGvDefinedValue(modelMgr.getProject(), username);
			//String passwd = tgPasswordJdbc.getGvDefinedValue(modelmgr.getProject());
			String passwd = modelMgr.getStringValue("password");
			passwd = GvUtil.getGvDefinedValue(modelMgr.getProject(), passwd);
			
			String useSsl = modelMgr.getStringValue("useSsl");
			useSsl = GvUtil.getGvDefinedValue(modelMgr.getProject(), useSsl);
		
		
		

		Map<String, Object> jdbcPropMap = new HashMap<String, Object>();
		jdbcPropMap.put("driver", jdbcdriver);
		jdbcPropMap.put("location",getSharedResourcePath(resource));
		jdbcPropMap.put("user", username);
		jdbcPropMap.put("password", passwd);
		jdbcPropMap.put("loginTimeout", modelMgr.getStringValue("loginTimeout"));
		jdbcPropMap.put("maxConnections", modelMgr.getStringValue("maxConnections"));
		jdbcPropMap.put("url", url);
		
			
		if ("true".equalsIgnoreCase(useSsl)) {
			try {
				JdbcSSLConnectionInfo sslConnInfo = JdbcSSLSharedresourceHelper.getSSLConnectionInfo(
						username,
						passwd,
						url,
						jdbcdriver,
						modelMgr);
				jdbcPropMap.put(JdbcSSLConnectionInfo.KEY_JDBC_SSL_CONNECTION_INFO, sslConnInfo);
			} catch(Exception e) {
				String message = "JDBC configure SSL failed.\n" + e.toString();
				ModulePlugin.getDefault().getLog().log(new Status(IStatus.ERROR, ModulePlugin.PLUGIN_ID, message, e));
			}
		}
		
		return jdbcPropMap;
	}

	/**
	 * This method parses and resolves all the global variables which start and end with %% from the given string
	 * @param str
	 * @return the parsed string
	 */
//	private String resolveGlobalVarString(String str) {
//		StringBuilder parsedUrl = new StringBuilder(); 
//		String strToParse = str;
//		while(strToParse != null && strToParse.indexOf("%%") != -1) {
//			String strVar = null;
//			if(strToParse.indexOf("%%") != 0) {
//				strVar = strToParse.substring(0,strToParse.indexOf("%%"));
//			}
//			strToParse = strToParse.substring(strToParse.indexOf("%%"));
//			if(strVar != null) {
//				parsedUrl.append(strVar);
//			}
//			strToParse = strToParse.substring(2);
//			String gvName = strToParse.substring(0,strToParse.indexOf("%%"));
//			parsedUrl.append(resolveGlobalVariable(gvName));
//			if(strToParse.indexOf("%%")+2 < strToParse.length()) {
//				strToParse = strToParse.substring(strToParse.indexOf("%%")+2);
//			} else {
//				strToParse = null;
//			}
//		}
//		if(strToParse != null && strToParse.trim().length() > 0) {
//			parsedUrl.append(strToParse);
//		}
//		return parsedUrl.toString();
//	}

	/**
	 * Resolve the globalVariable with name gvName from the list of global variables
	 * @param gvName
	 * @return
	 */
//	private String resolveGlobalVariable(String gvName) {
//		String pathToGV = "";
//		if (gvName.contains("/")) {
//			pathToGV = gvName.substring(0, gvName.lastIndexOf("/")+1);
//			gvName = gvName.substring (gvName.lastIndexOf("/")+1);
//		} 
//		try {
//			VFile gVars = project.getVFileFactory().get("defaultVars/" + pathToGV + "defaultVars.substvar");
//			if (gVars != null && gVars instanceof VFileStream) {
//				InputStream is = ((VFileStream)gVars).getInputStream();
//				XiNode rootNode = XiParserFactory.newInstance().parse(new InputSource(is)).getRootNode();
//				
//				XiNode gvs = findDeepNode(rootNode, "globalVariables");
//				Iterator i = XiChild.getIterator(gvs);
//				while (i.hasNext()) {
//					
//					XiNode gv = (XiNode) i.next();
//					if (gv.getName().localName.equals("globalVariable")) {
//						String name = findDeepNode(gv, "name")
//								.getStringValue();
//						if (name.equals(gvName)) {
//							String val = findDeepNode(gv, "value")
//									.getStringValue();
//							return val;
//						}
//					}
//				}
//			}
//		} catch (Exception e) {
//		}
//		return gvName;
//	}
	
	protected static XiNode findDeepNode (XiNode parent, String childName) {
		Iterator<?> i = parent.getChildren(NodeKindNodeTest.ELEMENT);
		while(i.hasNext()) {
			XiNode n = (XiNode) i.next();
			if (n.getName().localName.equals(childName)) {
				return n;
			} else {
				if ((n = findDeepNode (n, childName)) != null) {
					return n;
				}
			}
		}
		return null;
	}

	protected static String decryptPwd(String pwd) {
		try {
			if (ObfuscationEngine.hasEncryptionPrefix(pwd)) {
				char[] pass = ObfuscationEngine.decrypt(pwd);
				String decrPwd = new String(pass);
				return decrPwd;
			}
		} catch (Exception e) {
		}
		return pwd;
	}
	
//	private String generateJDBCResource(DBDataSource dbs) throws Exception {
//
//		String driver = dbs.getDriver();
//		String url = dbs.getConnectionUrl();
//		String userId = dsUserName;
//		String passwd = dsPassword;
//		String maxCons = "10";
//		String timeout = "10";
//		
//		JDBCResourceGen jdbcGen = new JDBCResourceGen();
//		
//		String jdbcGlobalVarPath = "/defaultVars" + sharedResourceFolderPath + "/" + dsName + "/defaultVars.substvar";
//		String jdbcGlobalVars    = jdbcGen.getGlobalVars(url, userId, passwd, maxCons, timeout);
//		
//		writeToFile(jdbcGlobalVarPath, jdbcGlobalVars, null);
//		
//		String jdbcResourcePath = sharedResourceFolderPath + "/" + dsName + ".sharedjdbc";
//		String globalVarPathRef = "%%" + sharedResourceFolderPath.substring(1) + "/" + dsName + "/";
//		String jdbcResource     = jdbcGen.getJDBCResource(driver, globalVarPathRef);
//		
//		writeToFile(jdbcResourcePath, jdbcResource, null);
//		
//		return jdbcResourcePath;
//	}

//	private void loadProject() throws Exception {
//		project = new EMFProject(projectPath);
//		project.load();
//		project.cacheSmElements();
//	}
	
	public String getProjectPath() {
		return projectPath;
	}
	
	public DBEntityCatalog getDbEntityCatalog() {
		return dbEntityCatalog;
	}
	
//	public EMFProject getBEProject() {
//		return project;
//	}

	public static void main (String args[]) {
		try {
			DBCeptGenerator cg = new DBCeptGenerator();
			cg.generateCepts();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void parseArgs() throws Exception {

		String propfile = System.getProperty("wrapper.tra.file", "db-cepts-gen.tra");

		File f = new File(propfile);
		InputStream io = null;
		try {
			io = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			usage();
		}
		loadProperties(io);
	}

	private void usage() {
		System.out.println("java -Dwrapper.tra.file=<property-file> DBCeptGenerator");
		System.out.println("      <property-file>: default is db-cepts-gen.tra");
		System.exit(0);
	}
	
	private void loadProperties(InputStream io) throws Exception {

		prop.load(io);
		
		projectPath = prop.getProperty("db.project.path");
		conceptFolderPath = prop.getProperty("db.project.concept.path");
		eventFolderPath = prop.getProperty("db.project.event.path");
		eventDestURI = prop.getProperty("db.project.event.destURI");
		sharedResourceFolderPath = prop.getProperty("db.project.shared.path");
		constraintFileName = prop.getProperty("db.project.constraint.file.name");
		jdbcResourceURI = prop.getProperty("db.jdbc.uri");
		if (!jdbcResourceURI.endsWith(".sharedjdbc")) {
			jdbcResourceURI += ".sharedjdbc";
		}
		
		connURL = prop.getProperty("db.connection.url");
		driverName = prop.getProperty("db.driver");
		dsUserName = prop.getProperty("db.datasource.user");
		dsPassword = prop.getProperty("db.datasource.password");
		dsName = prop.getProperty("db.datasource.name");
		jdbcUserName = prop.getProperty("db.connect.as.user");
		jdbcPassword = prop.getProperty("db.connect.as.password");
		schemaOwner = prop.getProperty("db.schema.owner");
		
		catalogImplClass = prop.getProperty("db.entitycatalog.impl.class");
		genCeptsWithRel = "true".equalsIgnoreCase(prop.getProperty("db.concept.with.rel"));
		generateEvent = "true".equalsIgnoreCase(prop.getProperty("db.generate.event"));
	}
	protected static boolean isNullOrEmpty (String s) {
		return s == null ? true : "".equals(s) ? true : false;
	}
//	private void validateInputAndLoadProject() throws Exception {
//		
//		if(isNullOrEmpty(projectPath)) {
//			String errorText = "missing property db.project.path";
//			throw new Exception(errorText);
//		}
//		if (isNullOrEmpty(dsName)){
//			String errorText = "missing property db.datasource.name";
//			throw new Exception(errorText);
//		}
//		
//		project = new EMFProject(projectPath);
//		project.load();
//		project.cacheSmElements();
//		
//		VFileFactory vfac = project.getVFileFactory();
//		VFile jdbcResFile = vfac.get(jdbcResourceURI);
//		if((jdbcResFile == null))
//		{ 
//			if(schemaOwner == null 	|| dsUserName == null || dsPassword == null 
//					|| connURL == null || driverName == null 
//					|| jdbcUserName == null || jdbcPassword == null) {
//				String errorText = "one or more properties are missing." +
//				" required properties - db.schema.owner,db.connection.url, db.driver,"+
//				" db.datasource.name, db.datasource.user," +
//				" db.datasource.password, db.connect.as.user," +
//				" db.connect.as.password";
//				throw new Exception(errorText);
//			}
//		} else {
//			Map jdbcParams = getJDBCComponents(jdbcResourceURI);
//			dsUserName = (String) jdbcParams.get("user");
//			dsPassword = (String) jdbcParams.get("password");
//			driverName = (String) jdbcParams.get("driver");
//			connURL    = (String) jdbcParams.get("url");
//			
//			if(jdbcUserName != null && !"".equals(jdbcUserName.trim())) {
//				dsUserName = jdbcUserName;
//				if (jdbcPassword == null) {
//					jdbcPassword = "";
//				}
//				dsPassword = jdbcPassword;
//			}
//		}
//	}
}
