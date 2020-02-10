package com.tibco.cep.driver.tibrv;

import com.tibco.cep.runtime.service.security.*;
import com.tibco.io.uri.URI;
import com.tibco.security.Identity;
import com.tibco.security.IdentityFactory;
import com.tibco.security.ObfuscationEngine;
import com.tibco.security.impl.util.SecFileUtil;
import com.tibco.tibrv.TibrvException;
import com.tibco.tibrv.TibrvSdContext;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class TibrvSslHelper {

    private static final int BUFFER_SIZE = 1024;


    public static void initializeRvForSsl(
            TibRvChannelConfig channelConfig)
            throws Exception {

        if (channelConfig.isUseSsl()) {
            loadDaemonCertificates(channelConfig);
            loadIdentity(channelConfig);
        }
    }


    private static byte[] loadCertificateFromFile(
            String fileName)
            throws Exception {

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            final InputStream is = new URL(fileName).openStream();
            try {
                byte[] bytes = new byte[BUFFER_SIZE];
                for (int numBytesRead = is.read(bytes); numBytesRead >= 0; numBytesRead = is.read(bytes)) {
                    baos.write(bytes, 0, numBytesRead);
                }
            } finally {
                is.close();
            }
        } finally {
            baos.close();
        }
        return baos.toByteArray();
    }


    private static void loadDaemonCertificates(
            TibRvChannelConfig channelConfig)
            throws IOException, TibrvException {

        for (final String certificate : channelConfig.getSslDaemonCertificates()) {
            TibrvSdContext.setDaemonCert(channelConfig.getDaemon(), certificate); //TODO all?
        }
    }


    private static void loadIdentity(
            TibRvChannelConfig channelConfig)
            throws Exception {

        final BEIdentity id = channelConfig.getSslIdentity();
        if (id instanceof BEKeystoreIdentity) {
            loadIdentity((BEKeystoreIdentity) id);

        } else if (id instanceof BEUserIdPasswordIdentity) {
            loadIdentity((BEUserIdPasswordIdentity) id);

//        } else if (id instanceof BECertPlusKeyIdentity) {
//            loadIdentity((BECertPlusKeyIdentity) id);

        } else if (null != id)  {
            throw new RuntimeException("Identity type: " + id.getObjectType() + " is not supported.");
        }
    }


    private static void loadIdentity(
            BECertPlusKeyIdentity certPlusKeyId)
            throws Exception {

        final URI certUri = new URI(certPlusKeyId.getCertUrl());
        final URI keyStoreUri = new URI(certPlusKeyId.getKeyUrl());
        final char[] keyStorePassword = certPlusKeyId.getPassword().toCharArray();

        String keyStoreType = SecFileUtil.getIdentityType(keyStoreUri.getFullName());
        if (keyStoreType == null) {
            keyStoreType = IdentityFactory.PKCS8;
        }

        final Identity id;
        final InputStream keyStoreStream = keyStoreUri.createInputStream();
        try {
            final InputStream certStream = certUri.createInputStream();
            try {
                id = IdentityFactory.createIdentity(keyStoreStream, keyStoreType, keyStorePassword, certStream);
                //todo
            } finally {
                certStream.close();
            }
        } finally {
            keyStoreStream.close();
        }
    }

    private static void loadIdentity(
            BEKeystoreIdentity urlId)
            throws Exception {

        final String url = new URI(urlId.getStrKeystoreURL()).getFullName();
        final String password = urlId.isPasswordObfuscated()
                ? new String(ObfuscationEngine.decrypt(urlId.getStrStorePassword())) //todo: charset
                : urlId.getStrStorePassword();

        final byte[] certificateInBytes = loadCertificateFromFile(url);
        try {
            TibrvSdContext.setUserCertWithKey(new String(certificateInBytes), password); // todo: charset
        } catch (TibrvException e) {
            TibrvSdContext.setUserCertWithKeyBin(certificateInBytes, password);
        }
    }


    private static void loadIdentity(
            BEUserIdPasswordIdentity namePasswordId)
            throws TibrvException {

        TibrvSdContext.setUserNameWithPassword(namePasswordId.getUserId(), namePasswordId.getPassword());
    }


}
