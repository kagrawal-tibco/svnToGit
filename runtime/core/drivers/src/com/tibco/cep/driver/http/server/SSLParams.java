package com.tibco.cep.driver.http.server;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: Feb 26, 2009
 * Time: 11:55:44 AM
 * <!--
 * Add Description of the class here
 * -->
 */
public class SSLParams {
    /**
     * Whether client authenticates to the server or not
     */
    boolean isClientAuthRequired;
    
    String algorithm;
    /**
     * Key alias needs to be specified for keystore with multiple key pairs.
     */
    String keyAlias;
    /**
     * Properties for Keystore configuration
     */
    private String keyStoreFile;
    /**
     * Password for the keystore file. Clear Text or obfuscated
     */
    private String keyStorePassword;
    /**
     * Whether password is obfuscated
     */
    protected boolean isPasswordObfuscated;
    /**
     * Storetype for the keystore file
     */
    private String keyStoreType;
    /**
     * Default is TLS if not specified
     */
    String sslEnabledProtocols;
    String ciphers;
    String trustStoreFile;
    String trustStorePass;
    String trustStoreType;

	private String clientCert;

	private String privateKey;

	private String keyPassword;
	
	private boolean isKeyStoreType;
	
	private boolean useServerCipherOrder;

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getCiphers() {
        return ciphers;
    }

    public void setCiphers(String ciphers) {
        this.ciphers = ciphers;
    }

    public boolean isClientAuthRequired() {
        return isClientAuthRequired;
    }

    public void setClientAuthRequired(boolean clientAuthRequired) {
        isClientAuthRequired = clientAuthRequired;
    }

    public String getKeyAlias() {
        return keyAlias;
    }

    public boolean isPasswordObfuscated() {
        return isPasswordObfuscated;
    }

    public void setPasswordObfuscated(boolean passwordObfuscated) {
        isPasswordObfuscated = passwordObfuscated;
    }

    public void setKeyAlias(String keyAlias) {
        this.keyAlias = keyAlias;
    }

    public String getKeyStoreType() {
        return keyStoreType;
    }

    public void setKeyStoreFileType(String keyStoreType) {
        keyStoreType = (keyStoreType == null) ? "JKS" : keyStoreType;
        this.keyStoreType = keyStoreType;
    }

    public String getKeyStorePassword() {
        return keyStorePassword;
    }

    public void setKeyStorePassword(String keyStorePassword) {
        this.keyStorePassword = keyStorePassword;
    }

    public String getKeyStoreFile() {
        return keyStoreFile;
    }

    public void setKeyStoreFile(String keyStoreFile) {
        this.keyStoreFile = keyStoreFile;
    }

    public String getSslEnabledProtocols() {
        return sslEnabledProtocols;
    }

    public void setSslEnabledProtocols(String sslProtocol) {
        this.sslEnabledProtocols = sslProtocol;
    }

    public String getTrustStoreFile() {
        return trustStoreFile;
    }

    public void setTrustStoreFile(String trustStoreFile) {
        this.trustStoreFile = trustStoreFile;
    }

    public String getTrustStorePass() {
        return trustStorePass;
    }

    public void setTrustStorePass(String trustStorePass) {
        this.trustStorePass = trustStorePass;
    }

    public String getTrustStoreType() {
        return trustStoreType;
    }

    public void setTrustStoreType(String trustStoreType) {
        this.trustStoreType = trustStoreType;
    }

	public void setCertUrl(String clientCert) {
		this.clientCert = clientCert;
		
	}
	
	public String getCertUrl() {
		return clientCert;
	}
	
	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKeyUrl(String privateKey) {
		this.privateKey = privateKey;
		
	}
	
	public String getKeyPassword() {
		return keyPassword;
	}

	public void setKeyPassword(String keyPassword) {
		this.keyPassword = keyPassword;
		
	}

	public boolean isKeyStoreType() {
		return isKeyStoreType;
	}

	public void setKeyStoreType(boolean isKeyStoreType) {
		this.isKeyStoreType = isKeyStoreType;
	}

	public boolean isUseServerCipherOrder() {
		return useServerCipherOrder;
	}

	public void setUseServerCipherOrder(boolean useServerCipherOrder) {
		this.useServerCipherOrder = useServerCipherOrder;
	}
}
