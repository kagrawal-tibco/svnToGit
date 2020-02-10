package com.tibco.cep.modules.db.service;

import java.util.Collection;
import java.util.Iterator;

import com.tibco.be.jdbcstore.impl.DBManager;
import com.tibco.be.jdbcstore.ssl.JdbcSSLConnectionInfo;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.modules.ModuleManager;
import com.tibco.cep.modules.RTFactory;
import com.tibco.cep.repo.ArchiveResourceProvider;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.runtime.service.security.BEIdentityObjectFactory;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.util.SystemProperty;
import com.tibco.security.ObfuscationEngine;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;

public class DBConnectionFactory implements RTFactory, JDBCExpandedNames {

    private JDBCConnectionPoolManager connPoolManager;
    ModuleManager moduleManager;

    static DBConnectionFactory thisINSTANCE;

    public static DBConnectionFactory getInstance() {
        return thisINSTANCE;
    }

    public DBConnectionFactory() {
        connPoolManager = new JDBCConnectionPoolManager(this);
        thisINSTANCE = this;
    }


    public void initialize(ModuleManager mgr) throws Exception {
        this.moduleManager = mgr;
        final RuleServiceProvider rsp = this.moduleManager.getRuleServiceProvider();
        // do not start the dbconcepts connection pool if its a cache server
        if (RuleServiceProviderManager.isConfigAsCacheServer(rsp.getProperties())) {
        	rsp.getLogger(DBConnectionFactory.class).log(Level.DEBUG,
                    "Cache node, DB Concepts connection pool not started.");
        	return;
        } else if (! Boolean.valueOf(rsp.getProperties().getProperty(
                SystemProperty.DB_CONCEPTS_ENABLED.getPropertyName()))) {
            rsp.getLogger(DBConnectionFactory.class).log(Level.DEBUG,
                    "DB Concepts disabled, connection pool not started.");
            return;
        }

        rsp.getLogger(DBConnectionFactory.class).log(Level.DEBUG, "DB Concepts enabled.");

        if (System.getProperty("TIBCO.CEP.modules.use.constraints.file", "false").equals("false")) {
        	loadConstraints();
        }
        loadJDBCResources();
        connPoolManager.init();
    }
    

    private void loadConstraints() {
    	try {
    		Ontology o = moduleManager.getRuleServiceProvider().getProject().getOntology();
    		ConstraintRegistry constraintReg = ConstraintRegistry.getInstance();
    		constraintReg.generateConstraints(o);
    		//System.out.println(constraintReg.toString());
    	} catch (Exception e) {
    		this.moduleManager.getRuleServiceProvider().getLogger(DBConnectionFactory.class).log(Level.ERROR, e,
                    "Error while loading constraints");
    	}
    }



    public void start(int mode) throws Exception {
        connPoolManager.start(mode);
    }

    public void stop() {
        connPoolManager.stop();
    }

    public JDBCConnectionPoolManager getConnectionPoolManager() {
        return connPoolManager;
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }

    private void loadJDBCResources() throws Exception {
        ArchiveResourceProvider provider = moduleManager.getRuleServiceProvider().getProject().getSharedArchiveResourceProvider();
        //get a comma separated list of jdbc resource names to start connections.
        //eg: "SharedResources/JDBC_Connection1.sharedjdbc, SharedResources/JDBC_Connection2.sharedjdbc"
        // this is to ensure that DB concepts dont start connections that are meant for Backing Store.
        String jdbcURIList = moduleManager.getRuleServiceProvider().getProperties().getProperty(SystemProperty.DB_CONCEPTS_DBURI.getPropertyName(), null);
        String [] uris = new String [0];
        if (jdbcURIList != null) {
            uris = jdbcURIList.split(",");
        }

   	Collection col = provider.getAllResourceURI();
        Iterator r = col.iterator();
        while (r.hasNext()) {
            String uri = (String) r.next();
            uri = uri.trim();
            if (uri.endsWith(".sharedjdbc")) {
                XiNode node = provider.getResourceAsXiNode(uri);
//                if (jdbcURIList == null) { // if property is not specified, go ahead and load them (to ensure bkwd compat)
//                	loadJDBCResource_2_0(uri, node);
//            	} else { //only load if there is a match
               		for (int i=0; i<uris.length; i++) {
               			if (uris[i].trim().equals(uri)) {
               				loadJDBCResource_2_0(uri, node);
               			}
               		}
//            	}
            }
            else if (uri.endsWith("jdbcDataSource")) {
                XiNode node = provider.getResourceAsXiNode(uri);
                loadJDBCResource_1_4(uri, node);
            }
            else if (uri.endsWith(".constraints.xml")) {
                if (System.getProperty("TIBCO.CEP.modules.use.constraints.file", "false").equals("true")) {
                	XiNode node = provider.getResourceAsXiNode(uri);
                	ConstraintRegistry.readConstraints(node);
                }
            }
            else if (uri.endsWith(".sequences.xml")) {
            	XiNode node = provider.getResourceAsXiNode(uri);
            	ConstraintRegistry.readsequences(node);
            }
        }
    }

    private void loadJDBCResource_1_4(String uri, XiNode jdbcChannelNode) {

        GlobalVariables gVars = moduleManager.getRuleServiceProvider().getGlobalVariables();
        String folder = jdbcChannelNode.getAttributeStringValue(FOLDER);
        String name = jdbcChannelNode.getAttributeStringValue(NAME);
        String jdbcResourceName = folder + name;
        String jdbcDriver = gVars.substituteVariables(jdbcChannelNode.getAttributeStringValue(DRIVER)).toString();
        String jdbcurl = gVars.substituteVariables(jdbcChannelNode.getAttributeStringValue(URL)).toString();
        String username = gVars.substituteVariables(jdbcChannelNode.getAttributeStringValue(USERNAME)).toString();
        String password = gVars.substituteVariables(jdbcChannelNode.getAttributeStringValue(PASSWORD)).toString();
        String poolSize = gVars.substituteVariables(jdbcChannelNode.getAttributeStringValue(POOLSIZE)).toString();

        password = deObfscuate(password);

        JDBCConnectionPool pool = new JDBCConnectionPool(jdbcResourceName, jdbcDriver, jdbcurl, username, password, 1, Integer.parseInt(poolSize), 0, null);
        connPoolManager.addConnectionPool(jdbcResourceName, pool);

    }

    private String deObfscuate(String pwd) {
        String password = pwd;
        try {
            if(ObfuscationEngine.hasEncryptionPrefix(password)) {
                char[] pass = ObfuscationEngine.decrypt(pwd);
                password = new String(pass);
            }
        }
        catch (Exception e) {
            this.moduleManager.getRuleServiceProvider().getLogger(DBConnectionFactory.class).log(Level.ERROR, e,
                    "Password specified cannot be decrypted, please verify.");
        }
        return password;
    }

    private void loadJDBCResource_2_0(String uri, XiNode node) throws Exception {

        GlobalVariables gVars = moduleManager.getRuleServiceProvider().getGlobalVariables();
        XiNode jdbcChannelNode = XiChild.getChild(node, CONFIG);
        String jdbcDriver = gVars.substituteVariables(XiChild.getString(jdbcChannelNode,DRIVER_2_0)).toString();
        String jdbcurl = gVars.substituteVariables(XiChild.getString(jdbcChannelNode,URL_2_0)).toString();
        String username = gVars.substituteVariables(XiChild.getString(jdbcChannelNode,USERNAME_2_0)).toString();
        String password = gVars.substituteVariables(XiChild.getString(jdbcChannelNode,PASSWORD_2_0)).toString();
        String poolSize = gVars.substituteVariables(XiChild.getString(jdbcChannelNode,POOLSIZE_2_0)).toString();
        String loginTimeout = gVars.substituteVariables(XiChild.getString(jdbcChannelNode,LOGINTIMEOUT)).toString();
        String useSsl = gVars.substituteVariables(XiChild.getString(jdbcChannelNode, ExpandedName.makeName("useSsl"))).toString();
    	JdbcSSLConnectionInfo sslConnectionInfo = null;
        
        int timeout = 0;
        try {
        	timeout = loginTimeout == null ? 0 : Integer.parseInt(loginTimeout);
        } catch (Exception e) {
        	e.printStackTrace();
        }
        password = deObfscuate(password);
        int maxConns = Integer.parseInt(poolSize);
        int initConns = (maxConns/2);
        
        if ("true".equalsIgnoreCase(useSsl)) {
        	XiNode sslConfig = XiChild.getChild(jdbcChannelNode, ExpandedName.makeName(BEIdentityObjectFactory.SSL_NODE_NS, "ssl"));
        	sslConnectionInfo = DBManager.getSSLConnectionInfo(username, password, jdbcurl, jdbcDriver, sslConfig, moduleManager.getRuleServiceProvider(), uri);
        }
        
        JDBCConnectionPool pool = null;
        if ( (jdbcDriver.startsWith("Oracle") || jdbcDriver.startsWith("oracle"))
        		&& moduleManager.getRuleServiceProvider().getProperties().getProperty("be.dbconcepts.use.oracle.pool", "true").equalsIgnoreCase("true")) {
        	pool = new JDBCConnectionPoolOracle(uri, jdbcDriver, jdbcurl, username, password, initConns, maxConns, timeout, sslConnectionInfo);
        } else {
        	pool = new JDBCConnectionPool(uri, jdbcDriver, jdbcurl, username, password, initConns, maxConns, timeout, sslConnectionInfo);
        }
        connPoolManager.addConnectionPool(uri, pool);
    }
}



