package com.tibco.cep.sharedresource.jdbc;

import static com.tibco.cep.runtime.service.security.BETrustedCertificateManager.FOLDER_SUFFIX;

import java.io.File;
import java.security.KeyStore;

import com.tibco.be.jdbcstore.ssl.JdbcSSLConnectionInfo;
import com.tibco.cep.repo.BEProject;
import com.tibco.cep.runtime.service.security.BEIdentity;
import com.tibco.cep.runtime.service.security.BEIdentityUtilities;
import com.tibco.cep.runtime.service.security.BEKeystoreIdentity;
import com.tibco.cep.sharedresource.ssl.SslConfigJdbcModel;
import com.tibco.cep.studio.core.util.GvUtil;
import com.tibco.cep.studio.core.util.JdbcStudioSSLUtils;

/**
 * A helper class which provides functions to create sslConnectionInfo.
 * 
 * @author moshaikh
 *
 */
public class JdbcSSLSharedresourceHelper {

    /**
     * Creates a JdbcSSLConnectionInfo instance with all the necessary SSL configuration properties.
     * @param username
     * @param password
     * @param connUrl
     * @param jdbcDriver
     * @param modelmgr
     * @return
     * @throws Exception
     */
    public static JdbcSSLConnectionInfo getSSLConnectionInfo(String username,
    		String password, String connUrl, String jdbcDriver, JdbcConfigModelMgr modelmgr)
    				throws Exception {
    	JdbcSSLConnectionInfo connInfo = JdbcSSLConnectionInfo.createConnectionInfo(username, password, connUrl, jdbcDriver);
    	
    	String useSsl = modelmgr.getStringValue("useSsl");
		useSsl = GvUtil.getGvDefinedValue(modelmgr.getProject(), useSsl);
		
		if ("true".equalsIgnoreCase(useSsl)) {
			SslConfigJdbcModel sslConfigJdbcModel = modelmgr.getModel().getSslConfigJdbcModel();
			
			BEProject project = new BEProject(modelmgr.getProject().getLocation().toString());
			project.load();
			
			String cert = null;
			if(GvUtil.isGlobalVar(sslConfigJdbcModel.getCert())){
				cert=GvUtil.getGvDefinedValue(modelmgr.getProject(), sslConfigJdbcModel.getCert());
			}else {
				cert=sslConfigJdbcModel.getCert();
			}
			
			KeyStore trustedKeysStore = null;
			
			String trustStore = null;
			
			String filePath = modelmgr.getFilePath();

			String projPath = modelmgr.getProject().getLocation().toString();
			
			BEIdentity keyStoreIdentity = null;
			
			String projLibPath = null;
			
			
			/* BE-23589: Check if sharedjdbc path contains projlib. and extract projlib path */
			
			int i = filePath.indexOf(".projlib");
			if(i!=-1){
				projLibPath = filePath.substring(0, i)+".projlib";
			}
			
			if(projLibPath!=null&&!new File(projLibPath).isDirectory()){		/* BE-23589 check if projlib path is not a folder with *.projlib name */
				if (cert.endsWith(FOLDER_SUFFIX)) {
		            cert = cert.substring(0, cert.lastIndexOf(FOLDER_SUFFIX));
		        }
				String certPath = JdbcStudioSSLUtils.unzip(projLibPath,cert);	/* BE-23589 unzip projlib in user temp folder and get the absoulte path to unzip location.*/
				certPath = certPath+File.separator+cert;						/* BE-23589 build absolute path to certificate folder */
				trustedKeysStore = JdbcStudioSSLUtils.createKeystore(certPath, sslConfigJdbcModel.getTrustStorePasswd(), true);
				projLibPath = projLibPath+File.separator;
				keyStoreIdentity = getIdentity(sslConfigJdbcModel.getIdentity(), projLibPath, project,modelmgr);
			}else if(filePath.contains(projPath+"/")||filePath.contains(projPath+"\\")){ 	/* BE-23589 check if shardejdbc belongs to selected project. */
				trustedKeysStore = JdbcStudioSSLUtils.createKeystore(cert, sslConfigJdbcModel.getTrustStorePasswd(), project,project.getGlobalVariables(), true);
				keyStoreIdentity = getIdentity(sslConfigJdbcModel.getIdentity(), project);
			}
			
			trustStore = JdbcStudioSSLUtils.storeKeystore(trustedKeysStore, sslConfigJdbcModel.getTrustStorePasswd());
			connInfo.setTrustStoreProps(trustStore, JdbcStudioSSLUtils.KEYSTORE_JKS_TYPE, sslConfigJdbcModel.getTrustStorePasswd());
			
			
			
			boolean isClientAuth = false;
			if(GvUtil.isGlobalVar(sslConfigJdbcModel.getClientAuth())){
				String val=GvUtil.getGvDefinedValue(modelmgr.getProject(), sslConfigJdbcModel.getClientAuth());
				isClientAuth = Boolean.parseBoolean(val);
			}else{
				isClientAuth = sslConfigJdbcModel.isClientAuth();
			}
			
			if (isClientAuth && keyStoreIdentity != null) {
				if (keyStoreIdentity instanceof BEKeystoreIdentity) {
					connInfo.setKeyStoreProps(
							((BEKeystoreIdentity)keyStoreIdentity).getStrKeystoreURL(),
							((BEKeystoreIdentity)keyStoreIdentity).getStrStoreType(), ((BEKeystoreIdentity)keyStoreIdentity).getStrStorePassword());
				}
				else {
					String message = "Identity Resource - '" + sslConfigJdbcModel.getIdentity() + "' must be of type 'Identity file'";
					throw new Exception("JDBC Connection - " + message);
				}
			}
			
			boolean isVerifyHostName = false;
			if(GvUtil.isGlobalVar(sslConfigJdbcModel.getVerifyHostName())){
				String val=GvUtil.getGvDefinedValue(modelmgr.getProject(), sslConfigJdbcModel.getVerifyHostName());
				isVerifyHostName = Boolean.parseBoolean(val);
			}else{
				isVerifyHostName = sslConfigJdbcModel.isVerifyHostName();
			}
			
			String hostName=null;
			if (isVerifyHostName) {
				if(GvUtil.isGlobalVar(sslConfigJdbcModel.getExpectedHostName())){
					hostName=GvUtil.getGvDefinedValue(modelmgr.getProject(), sslConfigJdbcModel.getExpectedHostName());
				}else{
					hostName = sslConfigJdbcModel.getExpectedHostName();
				}
				
				connInfo.setVerifyHostname(hostName);
			}
		}
		return connInfo;
	}
    
    /**
     * Fetches referenced identity file details.
     * @param idReference
     * @param project
     * @return
     * @throws Exception
     */
	public static BEIdentity getIdentity(String idReference, BEProject project) throws Exception {
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
	
	/**
     * Fetches referenced identity file details.
     * @param idReference
     * @param path
     * @param project
     * @param modelMgr
     * @return
     * @throws Exception
     */
	public static BEIdentity getIdentity(String idReference, String path, BEProject project, JdbcConfigModelMgr modelMgr) throws Exception {
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
}
