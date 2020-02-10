package com.tibco.cep.runtime.service.security;

import java.io.File;

import com.tibco.be.util.config.sharedresources.id.Identity;
import com.tibco.be.util.config.sharedresources.util.SharedResourcesHelper;
import com.tibco.cep.repo.ArchiveResourceProvider;
import com.tibco.cep.repo.BEProject;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.objectrepo.NotFoundException;
import com.tibco.security.AXSecurityException;
import com.tibco.security.ObfuscationEngine;
import com.tibco.xml.datamodel.XiNode;

public class BEIdentityUtilities {


    // Kai: Merged from revision 17873 for JMS SSL client identity
    public static BEIdentity fetchIdentity(
            ArchiveResourceProvider provider,
            GlobalVariables gv,
            String identityRef)
            throws Exception
    {

        if (identityRef == null) {
            throw new IllegalArgumentException("The identity name cannot be null");
        }

        XiNode node = provider.getResourceAsXiNode(identityRef);
//        XiSerializer.serialize(node, System.out, true);
        BEIdentity identity = BEIdentityObjectFactory.createIdentityObject(node, gv);
        initIdentity(identity, gv);

        return identity;
    }


    // Kai: Merged from revision 17873 for JMS SSL client identity
    public static BEIdentity fetchIdentity(
            BEProject project,
            GlobalVariables gv,
            String identityRef)
            throws Exception
    {
        if (null == identityRef) {
            throw new IllegalArgumentException("The identity name cannot be null");
        }

        return BEIdentityObjectFactory.createIdentityObject(
                SharedResourcesHelper.loadIdentity(makeUriString(project, identityRef)),
                gv);
    }
    
    public static BEIdentity fetchIdentity(
    		String path,
            GlobalVariables gv,
            String identityRef)
            throws Exception
    {
        if (null == identityRef) {
            throw new IllegalArgumentException("The identity name cannot be null");
        }

        Identity identity = SharedResourcesHelper.loadIdentity(makeUriString(path, identityRef));
        return BEIdentityObjectFactory.createIdentityObject(
                identity,
                gv);
    }


    private static void initIdentity(BEIdentity identity, GlobalVariables gv)
            throws NotFoundException, AXSecurityException {
        if (identity instanceof BEUrlIdentity)
        {
            BEUrlIdentity urlId = (BEUrlIdentity)identity;

            if (!urlId.isPasswordDecrypted()) {
                urlId.substituteGVars(gv);
                urlId.setDecryptedPassword(decryptPassword(urlId.getPassword()));
            }
        }
        else if (identity instanceof BEKeystoreIdentity) {
            final BEKeystoreIdentity id = (BEKeystoreIdentity)identity;
            if (!id.isPasswordDecrypted()) {
                id.substituteGVars(gv);
                id.setDecryptedPassword(decryptPassword(id.getStrStorePassword()));
            }
        }
        else if (identity instanceof BECertPlusKeyIdentity)
        {
            BECertPlusKeyIdentity certKeyId = (BECertPlusKeyIdentity)identity;
            if (!certKeyId.isPasswordDecrypted()) {
                certKeyId.substituteGVars(gv);
                certKeyId.setDecryptedPassword(decryptPassword(certKeyId.getPassword()));
            }
        }
    }


    static String decryptPassword(
            String password)
            throws AXSecurityException
    {
        String ret = password;
        try {
            ret = new String(ObfuscationEngine.decrypt(password));
        } catch (AXSecurityException e) {
            // For now, allow passwords in clear
        }
        return ret;
    }


    private static String makeUriString(
            BEProject project,
            String projectPath)
    {
        final String repoPath = project.getRepoPath();
        final File repoFile = new File(repoPath);

        final StringBuilder path = new StringBuilder();
        if (repoFile.isFile()) {
            path.append("archive:jar:").append(repoFile.toURI()).append("!/Shared%20Archive.sar!/");
        } else {
            path.append(repoFile.getAbsolutePath());
            if (!repoPath.endsWith("/")) {
                path.append("/");
            }
        }

        return path
                .append(projectPath.startsWith("/") ? projectPath.substring(1) : projectPath)
                .toString();
    }
    
    private static String makeUriString(
            String projLibPath,
            String idFilePath)
    {
        final String repoPath = projLibPath;
        final File repoFile = new File(repoPath);

        final StringBuilder path = new StringBuilder();
        if (repoFile.isFile()) {
            path.append("archive:").append(repoFile.toURI()).append("!/");
        } else {
            path.append(repoFile.getAbsolutePath());
        }

        return path
                .append(idFilePath.startsWith("/") ? idFilePath.substring(1) : idFilePath)
                .toString();
    }

}

