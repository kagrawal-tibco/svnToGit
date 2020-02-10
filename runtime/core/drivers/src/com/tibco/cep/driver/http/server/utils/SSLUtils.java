package com.tibco.cep.driver.http.server.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;

import com.tibco.cep.repo.ArchiveResourceProvider;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.runtime.service.security.BETrustedCertificateManager;
import com.tibco.security.Cert;
import com.tibco.security.TrustedCerts;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: Feb 27, 2009
 * Time: 5:49:50 PM
 * <p/>
 * A utilities class for SSL related functionality
 */
public class SSLUtils {

    public static final String PASSWORD_NOT_SET = "NOTSET";
    public static final String KEYSTORE_JKS_TYPE = "JKS";
    public static final String DELIMITER_FOR_SSL_PARAMS = ":";

    public static final String SSL_SERVER_CIPHERS = "be.http.ssl_server_ciphers";
    public static final String SSL_SERVER_ENABLED_PROTOCOLS = "be.http.ssl_server_enabledprotocols";
    public static final String SSL_SERVER_KEYMANAGER_ALGO = "be.http.ssl_server_keymanageralgorithm";
    public static final String SSL_SERVER_TRUSTMANAGER_ALGO = "be.http.ssl_server_trustmanageralgorithm";
    public final static String SSL_SERVER_USE_SERVER_CIPHER_ORDER = "be.http.ssl_server_useServerCipherOrder";

	public static final String HTTP_SSL_SERVER_CIPHERS = "TLS_AES_128_GCM_SHA256,TLS_AES_256_GCM_SHA384,TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384,TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384,TLS_DHE_RSA_WITH_AES_128_GCM_SHA256,TLS_DHE_RSA_WITH_AES_256_GCM_SHA384,TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256,TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256,TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA384,TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384,TLS_RSA_WITH_AES_256_CBC_SHA256,TLS_RSA_WITH_AES_128_CBC_SHA256,TLS_RSA_WITH_AES_256_GCM_SHA384,TLS_RSA_WITH_AES_128_GCM_SHA256,TLS_DHE_RSA_WITH_AES_256_CBC_SHA256,TLS_DHE_RSA_WITH_AES_128_CBC_SHA256,TLS_DHE_DSS_WITH_AES_128_CBC_SHA,TLS_DHE_DSS_WITH_AES_128_CBC_SHA256,TLS_DHE_DSS_WITH_AES_128_GCM_SHA256,TLS_DHE_DSS_WITH_AES_256_CBC_SHA,TLS_DHE_DSS_WITH_AES_256_CBC_SHA256,TLS_DHE_DSS_WITH_AES_256_GCM_SHA384";

	/**
     * Create an instance of {@link KeyStore} using a base folder location
     * containing all trusted certificates.
     *
     * @param trustedCertsURI-> Base folder location containing certificate files
     * @param provider          -> The {@link ArchiveResourceProvider} used to load these
     *                          resources from the project
     * @param loadIfNecessary
     * @return
     * @throws Exception
     */
    public static KeyStore createKeystore(String trustedCertsURI, String password,
                                          ArchiveResourceProvider provider,
                                          GlobalVariables gv,
                                          boolean loadIfNecessary) throws Exception {

        //Create a Keystore instance dynamically
        List<X509Certificate> trustedCerts = getTrustedCerts(trustedCertsURI,
                provider,
                gv,
                loadIfNecessary);
        //Add each certificate with the Subject DN as alias
        //TODO change the hard-coded keystore type
        KeyStore keyStore = KeyStore.getInstance(SSLUtils.KEYSTORE_JKS_TYPE);
        char[] passwordChars = null;
        if (password != null && password.trim().length() > 0) {
            passwordChars = password.toCharArray();
        }
        keyStore.load(null, passwordChars);
        for (X509Certificate cert : trustedCerts) {
            //Assuming DNs will be unique.
            String subjectDN = cert.getSubjectDN().toString();
            keyStore.setCertificateEntry(subjectDN, cert);
        }
        return keyStore;
    }

    private static List<X509Certificate> getTrustedCerts(String trustedCertsURI,
                                                         ArchiveResourceProvider provider,
                                                         GlobalVariables gv,
                                                         boolean loadIfNecessary) throws Exception {
        TrustedCerts trustedCerts =
                BETrustedCertificateManager.getInstance()
                        .getTrustedCerts(provider, gv, trustedCertsURI, loadIfNecessary);
        Cert[] certList = trustedCerts.getCertificateList();
        List<X509Certificate> certs = new ArrayList<X509Certificate>(certList.length);
        for (Cert cert : certList) {
            certs.add(cert.getCertificate());
        }
        return certs;
    }

    /**
     * Write a {@link KeyStore} to disk.
     *
     * @param keyStore
     * @param password
     * @param filePath
     * @return
     * @throws Exception
     */
    public static String storeKeystore(KeyStore keyStore, String password, String fileName) throws Exception {
        //Cannot use the folder location as it is part of the project
        final File tmpDir = new File(System.getProperty("java.io.tmpdir"));
        String ksFilePath = tmpDir.getAbsolutePath() +
                File.separatorChar +
                fileName;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(ksFilePath);
            if (password != null && password.trim().length() > 0) {
                keyStore.store(fos, password.toCharArray());
            }
            else {
                keyStore.store(fos, PASSWORD_NOT_SET.toCharArray());
            }
        } finally {
        	if (fos != null) {
        	    fos.close();
            }
        }
        return ksFilePath;
    }
    
    /**
     * Write a {@link KeyStore} to disk.
     *
     * @param keyStore
     * @return
     * @throws Exception
     */
    public static String storeKeystore(KeyStore keyStore, String password) throws Exception {
        String fileName = "Keystore.ks";

        return storeKeystore(keyStore, password, fileName);
    }

    /**
     * Creates the SSL Context with the identity and trusted certificate store as given
     *
     * @param identityKeystore
     * @param identityKeystorePassword
     * @param trustedCertsKeystore
     * @param trustedCertsPassword
     * @param sslProtocol
     * @return
     * @throws Exception
     */
    public static SSLContext createSSLContext(Object identityKeystore,
                                              String identityKeystorePassword,
                                              Object trustedCertsKeystore,
                                              String trustedCertsPassword,
                                              String sslProtocol) throws Exception {
        KeyManager[] keymanagers = null;
        if (identityKeystore != null) {
            KeyManagerFactory kmfactory = KeyManagerFactory.getInstance(
                    KeyManagerFactory.getDefaultAlgorithm());
            kmfactory.init((KeyStore) identityKeystore, identityKeystorePassword.toCharArray());
            keymanagers = kmfactory.getKeyManagers();
        }
        TrustManager[] trustmanagers = null;
        KeyStore truststore = (KeyStore) trustedCertsKeystore;
        if (truststore != null) {
            String trustManagerAlgo = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory trustmanager = TrustManagerFactory.getInstance(trustManagerAlgo);
            trustmanager.init(truststore);
            trustmanagers = trustmanager.getTrustManagers();
        }
        // get the appropriate SSL protocol
        sslProtocol = SSLProtocol.getProtocolByValue(sslProtocol);
        if (sslProtocol == null) sslProtocol = SSLProtocol.TLS.getProtocol();
        
        SSLContext sslContext = SSLContext.getInstance(sslProtocol);
        sslContext.init(keymanagers, trustmanagers, null);
        return sslContext;
    }
    
    /**
     * Extracts the private key of the certificate chain
     * 
     * @param keyStore
     * @param certPassword
     * @param certAlias
     * @return
     * @throws Exception
     */
    public static PrivateKey getCertificatePrivateKey(KeyStore keyStore, String certPassword, 
    		String certAlias) throws Exception {
		if (keyStore == null) {
			throw new KeyStoreException("Cannot pass an invalid or null keystore");
		}
		boolean isAliasWithPrivateKey = false;
		PrivateKey privateKey = null;
		
		// Iterate over all aliases
		Enumeration<String> es = keyStore.aliases();
		String alias = "";
		while (es.hasMoreElements()) {
			alias = (String) es.nextElement();
			// If alias refers to a private key break at that point, as we want to use that certificate
			if (isAliasWithPrivateKey = keyStore.isKeyEntry(alias)) {
				break;
			}
		}	// End of while loop
		
		if (!isAliasWithPrivateKey) {
			alias = certAlias;
		}
		KeyStore.PrivateKeyEntry pkEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(alias,
				new KeyStore.PasswordProtection(certPassword.toCharArray()));
		privateKey = pkEntry.getPrivateKey();
		
		return privateKey;
	}

    /**
     * Get the certificate chain from the KeyStore
     * 
     * @param keyStore
     * @param userAlias
     * @return
     * @throws KeyStoreException
     */
    public static Certificate[] getCertificateChain(KeyStore keyStore, String userAlias) 
    		throws KeyStoreException {
		if (keyStore == null) {
			throw new KeyStoreException("Cannot pass an invalid or null keystore");
		}
		boolean isAliasWithPrivateKey = false;
		Certificate[] chain = null;
		
		// iterate over all aliases
		Enumeration<String> es = keyStore.aliases();
		String alias = "";
		while (es.hasMoreElements()) {
			alias = (String) es.nextElement();
			// If alias refers to a private key break at that point, as we want to use that certificate
			if (isAliasWithPrivateKey = keyStore.isKeyEntry(alias)) {
				break;
			}
		}	// End of while loop
		
		if (isAliasWithPrivateKey) {
			// Load certificate chain
			chain = keyStore.getCertificateChain(alias);
		} else {
			chain = new Certificate[1];
			chain[0] = keyStore.getCertificate(userAlias);
		}
		
		return chain;
	}

    /**
     * Creates a XML signature that is yet to be signed
     * 
     * @param cert
     * @return
     * @throws Exception
     */
    public static XMLSignature createXMLSignature(Certificate cert) throws Exception {
		XMLSignature signature = null;
		
		// Create a DOM XMLSignatureFactory that will be used to generate the enveloped signature.
		XMLSignatureFactory sigFactory = XMLSignatureFactory.getInstance("DOM");
		
		// Create a Digest Method.
		DigestMethod dMethod = sigFactory.newDigestMethod(DigestMethod.SHA256, null);
		
		// Create a Reference to the enveloped document 
		// and also specify the SHA1 digest algorithm and the ENVELOPED Transform.
		// - a URI of "" signifies that we are signing the whole document 
		Reference sigReference = sigFactory.newReference("", dMethod,
				Collections.singletonList(sigFactory.newTransform(Transform.ENVELOPED, (TransformParameterSpec) null)),
				null, null);
		
		// Create the SignedInfo.
		SignedInfo sInfo = sigFactory.newSignedInfo(
				sigFactory.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE_WITH_COMMENTS, (C14NMethodParameterSpec) null),
				sigFactory.newSignatureMethod(/*SignatureMethod.RSA_SHA1*/"http://www.w3.org/2000/09/xmldsig#rsa-sha1", null),
				Collections.singletonList(sigReference));
		
		// Create the KeyInfo.
		KeyInfoFactory kInfoFactory = sigFactory.getKeyInfoFactory();
		KeyValue keyValue = kInfoFactory.newKeyValue(cert.getPublicKey());
		KeyInfo kInfo = kInfoFactory.newKeyInfo(Collections.singletonList(keyValue));
		
		// Create the XMLSignature, but don't sign it yet.
		signature = sigFactory.newXMLSignature(sInfo, kInfo);
		
		return signature;
    }
    
    /**
     * ENUM listing out all the available SSL protocols that can be used as part of the secure SSL communication
     * 
     * @author vpatil
     */
    public enum SSLProtocol {
    	// BE-27283 - Disable/Disallow weaker protocols
    	// SSLv3 and TLSv1 are still kept around for backward compatibility as suggested
    	SSL("SSL"), TLS("TLS"), SSLV3("SSLv3"), TLS1("TLSv1"), TLS11("TLSv1.1"), TLS12("TLSv1.2"), TLS13("TLSv1.3");
    	
    	private String protocol;
    	SSLProtocol(String protocol) {
    		this.protocol = protocol;
    	}
    	
    	/**
    	 * Get protocol value
    	 * @return
    	 */
    	public String getProtocol() {
    		return this.protocol;
    	}
    	
    	/**
    	 * Get the correct protocol based on the value specified
    	 * 
    	 * @param protocolValue
    	 * @return
    	 */
    	public static String getProtocolByValue(String protocolValue) {
    		for (SSLProtocol sslProtocol : SSLProtocol.values()) {
    			if (sslProtocol.getProtocol().equalsIgnoreCase(protocolValue)) {
    				return sslProtocol.getProtocol();
    			}
    		}
    		
    		return null;
    	}
    }
}
