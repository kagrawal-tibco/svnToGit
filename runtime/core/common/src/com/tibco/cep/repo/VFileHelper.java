package com.tibco.cep.repo;

import java.io.File;

import com.tibco.infra.repository.RepoClient;
import com.tibco.infra.repository.RepoFactory;
import com.tibco.objectrepo.vfile.VFileFactory;
import com.tibco.objectrepo.vfile.streamfile.FileFactory;
import com.tibco.objectrepo.vfile.tibrepo.RepoVFileFactory;
import com.tibco.objectrepo.vfile.zipfile.ZipVFileFactory;


public class VFileHelper {


    //TODO HTTP, and WebDav
    public static VFileFactory createVFileFactory(String repoFile, String pwd) {
        try {
            VFileFactory factory = null;
            if (repoFile == null || repoFile.length() == 0) {
                throw new Exception("No tibco.repourl is specified");
            }
            if (repoFile.endsWith(".ear")) {
                factory = new ZipVFileFactory(repoFile);
            } else if (repoFile.startsWith("tibcr")) {
                repoFile = repoFile + ":embeddedTicket=" + "TIBCOAdministrator 123704e never "
                        + VFileHelper.getEmbeddedTicket();
                if ((pwd != null) && (pwd.length() > 0)) {
                    repoFile = repoFile + ":password=" + pwd;
                }
                RepoClient client = RepoFactory.newClient(repoFile);
                if (client != null) {
                    factory = new RepoVFileFactory(repoFile, client);
                }
            } else if (repoFile.startsWith("http:") || repoFile.startsWith("https:")) {
                //System.out.println("#### case HTTP, url = " + repoFile);
                final RepoClient client = RepoFactory.newClient(repoFile);
                if (null != client) {
                    factory = new RepoVFileFactory(repoFile, client);
                }//if
            } else {
                if (new File(repoFile).exists())
                    factory = new FileFactory(repoFile);
            }

            return factory;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    // Courtesy of Jean Marie
    public static String getEmbeddedTicket() {
        String a = "wGP";
        StringBuffer buf = new StringBuffer("c3M8");
        buf.append('-');
        buf.append('G');
        buf.append("PV");
        buf.append("-Jmu-");
        buf.append("J");
        buf.append(a);
        buf.append("-a");
        buf.append("B5-JW8-sb4J");
        return buf.toString();
    }


}