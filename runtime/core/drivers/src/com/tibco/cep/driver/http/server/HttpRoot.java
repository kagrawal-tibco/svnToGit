package com.tibco.cep.driver.http.server;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import com.tibco.cep.driver.http.HttpChannel;
import com.tibco.cep.driver.http.HttpChannelConstants;


/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Mar 27, 2008
 * Time: 1:38:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class HttpRoot {

    static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }
    

    private File directory;

    protected static final String WEB_XML = "web.xml";
    protected static final String DIR_WEB_INF = "WEB-INF";
    protected static final String DIR_SERVLET = "ROOT";
    protected static final String DIR_WEBAPPS = "webapps";
    protected static final String DIR_MANAGER = "manager";
    protected static final String DIR_TOMCAT = "tomcat";
    protected static final String DIR_ROOT = DIR_TOMCAT;    // working/tomcat
    protected static final String DIR_HOST_PATH = DIR_ROOT + "/" + DIR_WEBAPPS; // working/tomcat/webapps
    protected static final String DIR_MANAGER_PATH = DIR_ROOT + "/" + DIR_MANAGER; // working/tomcat/manager
    protected static final String DIR_CONTEXT_PATH = DIR_HOST_PATH + "/" + DIR_SERVLET;  // working/tomcat/webapps/ROOT
    protected static final String ROOT_CONTEXT_WAR = DIR_HOST_PATH + "/" + DIR_SERVLET + ".war";  // working/tomcat/webapps/ROOT.war
    protected static final String WEB_XML_EXTERNAL_PARENT_PATH = DIR_CONTEXT_PATH + "/" + DIR_WEB_INF; // working/tomcat/webapps/servlet/WEB-INF
    protected static final String WEB_XML_EXTERNAL_PATH = WEB_XML_EXTERNAL_PARENT_PATH + "/" + WEB_XML; // working/tomcat/webapps/servlet/WEB-INF/web.xml


    protected static final String WEB_XML_LOCATION = "com/tibco/be/rms/driver/http/servlet/web.xml";
    private HttpChannel channel;
    private String docRoot, defaultPageName;


    public HttpRoot(HttpChannel channel, Properties beProperties) throws Exception {

        this.channel = channel;

        String rootDir = System.getProperty("com.tibco.be.http.root");
        if (rootDir == null) {
            File tdir = new File(System.getProperty("java.io.tmpdir"));
            this.directory = File.createTempFile(getClass().getSimpleName(), "", tdir);
            if (!this.directory.delete())
                throw new IOException();
            if (!this.directory.mkdir())
                throw new IOException();
            this.directory.deleteOnExit();
        } else {
            this.directory = new File(rootDir);
            if (!(this.directory.exists() && this.directory.isDirectory())) {
                throw new Exception("Directory <" + this.directory + "> as specified by <com.tibco.be.http.root> property in tra does not exist or is not a directory");
            }
        }

        if (beProperties != null) {
            docRoot = beProperties.getProperty(HttpChannelConstants.DOC_ROOT_PROPERTY, null);
        }

    }

    public void create() throws Exception {
        createWebXmlParent();
    }

    public void clean() {
        new File(getRootContextArchivePath()).delete();
        new File(getContextPath()).delete();
        new File(getHostPath()).delete();
        new File(getTomcatDirectory()).delete();
    }

    private void createWebXmlParent() {
        File webXmlParent = new File(directory, WEB_XML_EXTERNAL_PARENT_PATH);
        if (!webXmlParent.exists()) {
            webXmlParent.mkdirs();
        }
    }

    public String getTomcatDirectory() {
        return (new File(directory, DIR_ROOT)).getAbsolutePath();
    }

    public String getHostPath() {
        return (new File(directory, DIR_HOST_PATH)).getAbsolutePath();
    }

    public String getContextPath() {
        return (new File(directory, DIR_CONTEXT_PATH)).getAbsolutePath();
    }

    public String getRootContextArchivePath() {
        return (new File(directory, ROOT_CONTEXT_WAR)).getAbsolutePath();
    }

    public URL getRootContextURL() throws MalformedURLException {
        return (new File(directory, ROOT_CONTEXT_WAR)).toURL();
    }

    public String getDocRoot() {
        return docRoot;
    }

    // test
    public static final void main(String[] args) {
        final Object lock = new Object();
        try {
            HttpRoot httpRoot = new HttpRoot(null, null);
            httpRoot.create();
            synchronized (lock) {
                lock.wait(15000);
            }
            httpRoot.clean();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
