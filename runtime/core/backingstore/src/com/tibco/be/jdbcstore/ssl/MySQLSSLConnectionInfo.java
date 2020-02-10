package com.tibco.be.jdbcstore.ssl;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class MySQLSSLConnectionInfo extends JdbcSSLConnectionInfo {
    public MySQLSSLConnectionInfo(String user, String password) {
        super(user, password);
        infoProperties.put("useSSL", "true");
        infoProperties.put("requireSSL", "true");
        infoProperties.put("verifyServerCertificate", "true");
    }

    public void setTrustStoreProps(String trustStore, String trustStoreType, String trustStorePassword) {

        try {
            URL turstStoreURL = new File(trustStore).toURI().toURL();
            infoProperties.setProperty("trustCertificateKeyStoreUrl", turstStoreURL.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (trustStoreType != null) {
            infoProperties.setProperty("trustCertificateKeyStoreType", trustStoreType);
        }
        if (trustStorePassword != null) {
            infoProperties.setProperty("trustCertificateKeyStorePassword", trustStorePassword);
        }
    }

    public void setKeyStoreProps(String keyStore, String keyStoreType, String keyStorePassword) {

        try {
            URL keyStoreURL = new File(keyStore).toURI().toURL();
            infoProperties.setProperty("clientCertificateKeyStoreUrl", keyStoreURL.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (keyStoreType != null) {
            infoProperties.setProperty("clientCertificateKeyStoreType", keyStoreType);
        }
        if (keyStorePassword != null) {
            infoProperties.setProperty("clientCertificateKeyStorePassword", keyStorePassword);
        }
    }
}
