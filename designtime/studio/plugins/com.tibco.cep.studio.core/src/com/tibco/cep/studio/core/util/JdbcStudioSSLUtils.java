package com.tibco.cep.studio.core.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.tomcat.util.http.fileupload.FileUtils;

import com.tibco.cep.repo.BEProject;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.runtime.service.security.BETrustedCertificateManager;
import com.tibco.security.Cert;
import com.tibco.security.TrustedCerts;

/**
 * A utility class for JDBC SSL design-time functions.
 * 
 * @author moshaikh
 *
 */
public class JdbcStudioSSLUtils {

    public static final String PASSWORD_NOT_SET = "NOTSET";
    public static final String KEYSTORE_JKS_TYPE = "JKS";

    /**
     * Create an instance of {@link KeyStore} using a base folder location containing all trusted certificates.
     * @param trustedCertsURI Path to folder that contains trusted certificates.
     * @param password
     * @param beProject
     * @param gv
     * @param loadIfNecessary
     * @return
     * @throws Exception
     */
    public static KeyStore createKeystore(String trustedCertsURI, String password, BEProject beProject, GlobalVariables gv, boolean loadIfNecessary) throws Exception {

        //Create a Keystore instance dynamically
        List<X509Certificate> trustedCerts = getTrustedCerts(trustedCertsURI, beProject, gv, loadIfNecessary);
        
        KeyStore keyStore = KeyStore.getInstance(JdbcStudioSSLUtils.KEYSTORE_JKS_TYPE);
        char[] passwordChars = null;
        if (password != null && password.trim().length() > 0) {
            passwordChars = password.toCharArray();
        }
        keyStore.load(null, passwordChars);
        for (X509Certificate cert : trustedCerts) {
            keyStore.setCertificateEntry(UUID.randomUUID().toString(), cert);
        }
        return keyStore;
    }
    
    /**
     * Create an instance of {@link KeyStore} using a base folder location containing all trusted certificates.
     * @param trustedCertsURI Path to folder that contains trusted certificates.
     * @param password
     * @param loadIfNecessary
     * @return
     * @throws Exception
     */
    public static KeyStore createKeystore(String trustedCertsURI, String password, boolean loadIfNecessary) throws Exception {

        //Create a Keystore instance dynamically
        List<X509Certificate> trustedCerts = getTrustedCerts(trustedCertsURI, loadIfNecessary);
        
        KeyStore keyStore = KeyStore.getInstance(JdbcStudioSSLUtils.KEYSTORE_JKS_TYPE);
        char[] passwordChars = null;
        if (password != null && password.trim().length() > 0) {
            passwordChars = password.toCharArray();
        }
        keyStore.load(null, passwordChars);
        for (X509Certificate cert : trustedCerts) {
            keyStore.setCertificateEntry(UUID.randomUUID().toString(), cert);
        }
        return keyStore;
    }

    /**
     * Write a {@link KeyStore} to disk at java temp location and return its path.
     *
     * @param keyStore
     * @return
     * @throws Exception
     */
    public static String storeKeystore(KeyStore keyStore, String password) throws Exception {
        final File tmpDir = new File(System.getProperty("java.io.tmpdir"));
        String fileName = "Keystore.ks";
        String ksFilePath = tmpDir.getAbsolutePath() +
                File.separatorChar +
                fileName;

        FileOutputStream fos = new FileOutputStream(ksFilePath);
        if (password != null && password.trim().length() > 0) {
        	keyStore.store(fos, password.toCharArray());
        }
        else {
            keyStore.store(fos, PASSWORD_NOT_SET.toCharArray());        	
        }
        
        fos.close();
        return ksFilePath;
    }
    
    /**
     * Loads all the certificates found at the specified folder locations.
     * @param trustedCertsURI Path to folder that contains trusted certificates.
     * @param beProject
     * @param gv
     * @param loadIfNecessary
     * @return
     * @throws Exception
     */
    private static List<X509Certificate> getTrustedCerts(String trustedCertsURI,
            BEProject beProject, GlobalVariables gv, boolean loadIfNecessary) throws Exception {
    	BETrustedCertificateManager.getInstance().clearCache();
		TrustedCerts trustedCerts = BETrustedCertificateManager.getInstance().getTrustedCerts(beProject, gv, trustedCertsURI, loadIfNecessary);
		Cert[] certList = trustedCerts.getCertificateList();
		List<X509Certificate> certs = new ArrayList<X509Certificate>(certList.length);
		for (Cert cert : certList) {
			certs.add(cert.getCertificate());
		}
		return certs;
	}
    
    
    /**
     * Loads all the certificates found at the specified folder locations.
     * @param trustedCertsURI Path to folder that contains trusted certificates.
     * @param loadIfNecessary
     * @return
     * @throws Exception
     */
    private static List<X509Certificate> getTrustedCerts(String trustedCertsURI,
            boolean loadIfNecessary) throws Exception {
    	BETrustedCertificateManager.getInstance().clearCache();
		TrustedCerts trustedCerts = BETrustedCertificateManager.getInstance().getTrustedCerts(trustedCertsURI, loadIfNecessary);
		Cert[] certList = trustedCerts.getCertificateList();
		List<X509Certificate> certs = new ArrayList<X509Certificate>(certList.length);
		for (Cert cert : certList) {
			certs.add(cert.getCertificate());
		}
		return certs;
	}
    

/**
 * 
 * @param zipFilePath path of projlib file.
 * @param certFolderPath path to certificates folder.
 * @return
 * @throws IOException
 */

public static String unzip(String zipFilePath, String certFolderPath) throws IOException {
	final File tmpDir = new File(System.getProperty("java.io.tmpdir"));
	final String fileName = "projlib_zip";
	String destDirectory = tmpDir.getAbsolutePath() +
            File.separatorChar + 
            fileName;
	File destDir = new File(destDirectory);
	FileUtils.deleteDirectory(destDir);
    if (!destDir.exists()) {
        destDir.mkdir();
    }
    
    if(certFolderPath.charAt(0) == '/'||certFolderPath.charAt(0) == '\\'){
    	certFolderPath = certFolderPath.substring(1, certFolderPath.length());
    }
    
    
    ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
    ZipEntry entry = zipIn.getNextEntry();
    while (entry != null) {
        String filePath = destDirectory + File.separator + entry.getName();
        if (!entry.isDirectory()) {
        	if(filePath.contains(certFolderPath)){
	        	File file = new File(filePath);
	        	file.getParentFile().mkdirs();
	        	file.createNewFile();
	        	extractFile(zipIn, filePath);
        	}
        } else {
            File dir = new File(filePath);
            dir.mkdir();
        }
        zipIn.closeEntry();
        entry = zipIn.getNextEntry();
    }
    zipIn.close();
    return destDirectory;
}

private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
    byte[] bytesIn = new byte[4096];
    int read = 0;
    while ((read = zipIn.read(bytesIn)) != -1) {
        bos.write(bytesIn, 0, read);
    }
    bos.close();
}

}