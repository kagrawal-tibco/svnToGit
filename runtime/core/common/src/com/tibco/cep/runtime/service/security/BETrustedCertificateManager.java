package com.tibco.cep.runtime.service.security;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.security.cert.CertificateException;
import java.util.HashMap;

import com.tibco.cep.repo.ArchiveResourceProvider;
import com.tibco.cep.repo.BEProject;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.objectrepo.ObjectRepoException;
import com.tibco.security.AXSecurityException;
import com.tibco.security.TrustedCerts;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Jul 7, 2006
 * Time: 9:45:42 AM
 * To change this template use File | Settings | File Templates.
 */
public class BETrustedCertificateManager {

    private static final String GLOBAL_TRUSTED_CA_STORE_GV_NAME = "BE_GLOBAL_TRUSTED_CA_STORE"; // Named after BW_GLOBAL_TRUSTED_CA_STORE

	public static final String FOLDER_SUFFIX = ".folder";

	private static BETrustedCertificateManager gInstance;

	private final HashMap<String, TrustedCerts> certTable = new HashMap<String, TrustedCerts>();

	private TrustedCerts globalTrustedCAStore;

	public static synchronized BETrustedCertificateManager getInstance() {
		if (null == gInstance) {
			gInstance = new BETrustedCertificateManager();
			return gInstance;
		}
		return gInstance;
	}


    public synchronized TrustedCerts getTrustedCerts(
            ArchiveResourceProvider provider,
            GlobalVariables gv,
            String path,
            boolean loadIfNeccessary)
            throws Exception {

		TrustedCerts cert = this.getGlobalTrustedCAStore(gv);
		if (null != cert) {
			return cert;
		}

		cert = this.certTable.get(path);
        if (cert != null) return cert;

        if (!loadIfNeccessary) return null;

		File certPath = new File(path);
        cert = (path.endsWith(FOLDER_SUFFIX) || !certPath.isDirectory())
        		? new BETrustedCerts(provider, path)
				: new BETrustedCerts(certPath);

		certTable.put(path, cert);
		return cert;
	}

    public synchronized TrustedCerts getTrustedCerts(
            BEProject project,
            GlobalVariables gv,
            String path,
            boolean loadIfNeccessary)
            throws Exception {

		TrustedCerts cert = this.getGlobalTrustedCAStore(gv);
		if (null != cert) {
			return cert;
		}

		cert = this.certTable.get(path);
		if (null != cert) {
			return cert;
		}

		if (!loadIfNeccessary) {
			return null;
		}

		File certPath = new File(path);
        cert = (path.endsWith(FOLDER_SUFFIX) || !certPath.isDirectory())
        		? new BETrustedCerts(project, path)
				: new BETrustedCerts(certPath);

		this.certTable.put(path, cert);
		return cert;
	}

    private TrustedCerts getGlobalTrustedCAStore(
            GlobalVariables gv)
            throws Exception {

		if (null == this.globalTrustedCAStore) {
			final String storePath = gv.getVariableAsString(GLOBAL_TRUSTED_CA_STORE_GV_NAME, null);
			if (null != storePath) {
				final URI storeUri = new URI(storePath);
				this.globalTrustedCAStore = new BETrustedCerts(new File(storeUri));
			}
		}

		return this.globalTrustedCAStore;
	}

	/**
     * Clear any cached TrustedCerts hence loading them again on next call to getTrustedCerts()
	 */
	public void clearCache() {
		this.certTable.clear();
	}

	public TrustedCerts getTrustedCerts(String path, boolean loadIfNecessary) throws CertificateException, AXSecurityException, IOException, ObjectRepoException {
		TrustedCerts cert = this.certTable.get(path);
		if (cert != null)
			return cert;

		if (!loadIfNecessary)
			return null;

		File certPath = new File(path);
		cert = (path.endsWith(FOLDER_SUFFIX) || !certPath.isDirectory()) ? null : new BETrustedCerts(certPath);

		certTable.put(path, cert);
		return cert;
	}
}
