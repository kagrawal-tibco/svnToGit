/////////////////////////////////////////////////////////////////////////////
//  Copyright TIBCO Software Inc. 2002. All rights reserved.
//  TIBCO Software Inc. proprietary information
//  TIBCO Software Inc. confidential
/////////////////////////////////////////////////////////////////////////////

/*
 * Created by IntelliJ IDEA.
 * User: fsyed
 * Date: Oct 4, 2002
 * Time: 11:10:13 AM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package com.tibco.cep.runtime.service.security;

import static com.tibco.cep.runtime.service.security.BETrustedCertificateManager.FOLDER_SUFFIX;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.security.cert.CertificateException;
import java.util.*;

import com.tibco.cep.repo.ArchiveResourceProvider;
import com.tibco.cep.repo.BEProject;
import com.tibco.cep.repo.DeployedProject;
import com.tibco.objectrepo.ObjectRepoException;
import com.tibco.security.AXSecurityException;
import com.tibco.security.Cert;
import com.tibco.security.CertUtils;
import com.tibco.security.TrustedCerts;
import com.tibco.security.TrustedCertsFactory;

/**
 * A BETrustedCerts object represents a trusted CA store.
 */
public class BETrustedCerts implements TrustedCerts, Serializable {
    protected TrustedCerts m_tc = null;
    //    private static String FOLDER_SUFFIX = ".folder";
    protected Object provider;

    public BETrustedCerts(ArchiveResourceProvider provider, String dirName)
            throws IOException, CertificateException, AXSecurityException, ObjectRepoException {

        this.provider = provider;
        /**
         * The folder location is inside the project. Hence it will be
         * suffixed with .folder; hence the need to do the below activity.
         */
        if (dirName.endsWith(FOLDER_SUFFIX)) {
            dirName = dirName.substring(0, dirName.lastIndexOf(FOLDER_SUFFIX));
        }
        m_tc = TrustedCertsFactory.createTrustedCerts();
        loadTrustedCertificates(dirName);
    }


    public BETrustedCerts(
            BEProject project,
            String dirName)
            throws IOException, CertificateException, AXSecurityException, ObjectRepoException
    {
        this.provider = project;
        if (dirName.endsWith(FOLDER_SUFFIX)) {
            dirName = dirName.substring(0, dirName.lastIndexOf(FOLDER_SUFFIX));
        }
        this.m_tc = TrustedCertsFactory.createTrustedCerts();
        this.loadTrustedCertificates(dirName);
    }


    public BETrustedCerts(
            File directory)
            throws IOException, CertificateException, AXSecurityException, ObjectRepoException {

        if (!directory.isDirectory()) {
            throw new IOException("Invalid directory path: " + directory.getPath());
        }

        this.m_tc = TrustedCertsFactory.createTrustedCerts();

        final List<Cert> trustedCAs = new ArrayList<Cert>();
        for (File f : directory.listFiles()) {
            if (f.canRead()) {
                final FileInputStream fis = new FileInputStream(f);
                try {
                    final Cert[] certChain = CertUtils.streamToCerts(fis);
                    trustedCAs.addAll(Arrays.asList(certChain));
                } catch (Exception e) {
                    //There will be non certificate URIs given by
                    //a shared archive resource provider.
                    //TODO What do we do here?
                } finally {
                    fis.close();
                }
            }
        }
        this.addCertificates(trustedCAs.toArray(new Cert[trustedCAs.size()]));
    }



    public void loadTrustedCertificates(String dirName)
            throws IOException, CertificateException, AXSecurityException, ObjectRepoException {

        addCertificates(initialLoad(dirName));
    }

    public synchronized void init(InputStream is, String type)
            throws IOException, AXSecurityException {
        m_tc.init(is, type);
    }

    public synchronized void init(Cert[] list) throws AXSecurityException {
        m_tc.init(list);
    }

    /**
     * Write contents
     * @param os stream to write to
     * @param format type of data, should be {@link TrustedCertsFactory#PKCS7} or
     * {@link TrustedCertsFactory#NETSCAPE}.
     * @exception AXSecurityException for security-related errors
     * @exception IOException if the data cannot be written
     */
    public synchronized void write(OutputStream os, String format) throws IOException, AXSecurityException {
        m_tc.write(os, format);
    }

    public synchronized void toPKCS7(OutputStream os) throws IOException, AXSecurityException {
        write(os, TrustedCertsFactory.PKCS7);
    }

    /**
     * Add a certificate
     */
    public synchronized void addCertificate(Cert cert) throws AXSecurityException {
        m_tc.addCertificate(cert);
    }

    /**
     * Add multiple certificates
     */
    public synchronized void addCertificates(Cert[] certs) throws AXSecurityException {
        for (int i = 0; i < certs.length; i++)
            addCertificate(certs[i]);
    }

    /**
     * Remove a certificate
     * @param certToRemove the certificate to remove
     */
    public synchronized void removeCertificate(Cert certToRemove) throws AXSecurityException {
        m_tc.removeCertificate(certToRemove);
    }

    /**
     * Remove a certificate by name
     */
    public synchronized void removeCertificate(String subjectName) throws AXSecurityException {
        m_tc.removeCertificate(subjectName);
    }

    /**
     * Get the contained certificate list
     */
    public synchronized Cert[] getCertificateList() {
        return m_tc.getCertificateList();
    }

    /**
     * Get a certificate corresponding to a particular distinguished name (DN)
     * @param DN name to match
     */
    public synchronized Cert getUserCertificate(String DN) throws AXSecurityException {
        return m_tc.getUserCertificate(DN);
    }

    /**
     * Return an iterator over all certificates matching a specified
     * distinguished name (or over all certificates if the name is null)
     * @param DN name to match
     * @return iterator over Cert objects
     */
    public synchronized Iterator getCertificates(String DN) throws AXSecurityException {
        return m_tc.getCertificates(DN);
    }

    /**
     * Free any resources held by this object.
     */
    public void dispose() {
    }


    public Cert[] initialLoad(
            String dirName)
            throws IOException, CertificateException, AXSecurityException, ObjectRepoException
    {
        final List<Cert> certs = new ArrayList<Cert>();

        final ArchiveResourceProvider arp = (this.provider instanceof ArchiveResourceProvider)
                ? (ArchiveResourceProvider) this.provider
                : (this.provider instanceof DeployedProject)
                        ? ((DeployedProject) this.provider).getSharedArchiveResourceProvider()
                        : null;

        if (null != arp) {
            //noinspection unchecked
            final Collection<String> uris = (Collection<String>) arp.getAllResourceURI();
            if (null != uris) {
                for (final String uri : uris) {
                    if (uri.startsWith(dirName)) {
                        final ByteArrayInputStream stream =
                                new ByteArrayInputStream(arp.getResourceAsByteArray(uri));
                        try {
                            Collections.addAll(certs, CertUtils.streamToCerts(stream));
                        } catch (Exception ignored) {
                            //There will be non certificate URIs given by a shared archive resource provider.
                        } finally {
                            stream.close();
                        }
                    }
                }
            }
        }
        else {
            try {
                final File certDir = new File(((BEProject) this.provider).getRepoPath() + dirName);
                //noinspection ConstantConditions
                for (final File f : certDir.listFiles()) {
                    if (f.isFile() && f.canRead()) {
                        try {
                            final InputStream stream = new FileInputStream(f);
                            try {
                                Collections.addAll(certs, CertUtils.streamToCerts(stream));
                            } finally {
                                stream.close();
                            }
                        } catch (Exception ignored) {
                        }
                    }
                }
            } catch (Exception ignored) {
            }
        }

        return certs.toArray(new Cert[certs.size()]);
    }


//    private static X509Certificate[] giveX509ArrayFromByteStream(byte [] byteData) throws Exception {
//        Vector vectorX509Certificates = new Vector();
//
//        ByteArrayInputStream inStream = new ByteArrayInputStream(byteData);
//        CertificateFactory cf = CertificateFactory.getInstance("X.509");
//        Collection certCollection = cf.generateCertificates(inStream);
//        Iterator i = certCollection.iterator();
//
//        Certificate cert = null;
//        for (; i.hasNext();) {
//            cert = (X509Certificate) i.next();
//            vectorX509Certificates.add(cert);
//        }
//
//        Object temp = (vectorX509Certificates.toArray()).getClass();
//
//        int size = vectorX509Certificates.size();
//        X509Certificate[] arrayX509Certificate = new X509Certificate[size];
//        for (int index = 0; index < size; ++index) {
//            arrayX509Certificate[index] = (X509Certificate) (vectorX509Certificates.get(index));
//        }
//
//        return arrayX509Certificate;
//    }


    //TODO - Temporary implementation for new interface in TRA 5.6.2. Contact Ashwin/Sridhar.
    //Kai: Use the getTrustAnchors implemented in the trusted CA store created by TrustedCertsFactory.createTrustedCerts().
    public Set<java.security.cert.TrustAnchor> getTrustAnchors() throws AXSecurityException {
        return m_tc.getTrustAnchors();
    }

//    public void addCertificate(byte[] bData)throws Exception{
//        X509Certificate[] xcert = giveX509ArrayFromByteStream(bData);
//        Vector mTrustedCAs = new Vector();
//        for (int i = 0; i < xcert.length; ++i) {
//            mTrustedCAs.add(CertFactory.createCert(xcert[i]));
//        }
//        addCertificates((Cert[]) mTrustedCAs.toArray(new Cert[0]));
//    }
//
}
