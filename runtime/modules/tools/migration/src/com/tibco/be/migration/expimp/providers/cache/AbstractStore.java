package com.tibco.be.migration.expimp.providers.cache;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.ConfigurableCacheFactory;
import com.tangosol.net.DefaultConfigurableCacheFactory;
import com.tangosol.net.NamedCache;
import com.tangosol.run.xml.XmlDocument;
import com.tangosol.run.xml.XmlElement;
import com.tangosol.run.xml.XmlHelper;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.repo.BEArchiveResource;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.runtime.session.RuleServiceProvider;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Feb 22, 2008
 * Time: 12:09:29 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractStore {


    protected RuleServiceProvider mrsp;
    protected Logger logger;
    protected String barName;
    protected String configURI;
    private Properties m_cacheConfig;
    protected NamedCache m_recoveryCache;
    boolean initDone = false;


    public AbstractStore(RuleServiceProvider mrsp, String barName, String cacheConfigURI) {
        this.mrsp = mrsp;
        this.barName = barName;
        this.configURI = cacheConfigURI;
        this.logger = mrsp.getLogger(AbstractStore.class);


    }

    public String getBarName() {
        return barName;
    }

    public String getConfigURI() {
        return configURI;
    }

    public RuleServiceProvider getRuleServiceProvider() {
        return mrsp;
    }

    BEArchiveResource getBEArchiveResource(String barName) {
        BEArchiveResource beArchive;
        Collection archives = mrsp.getProject().getDeployedBEArchives();
        Iterator i = archives.iterator();
        while (i.hasNext()) {
            beArchive = (BEArchiveResource) i.next();
            if (beArchive.getName().equals(barName)) {
                return beArchive;
            }
        }
        return null;
    }

    public Logger getLogger() {
        return logger;
    }

    protected Properties getCacheConfig() {
        if (null == this.m_cacheConfig) {
            BEArchiveResource bar = getBEArchiveResource(barName);
            m_cacheConfig = new Properties();

            final GlobalVariables gvs = mrsp.getProject().getGlobalVariables();
            for (Iterator it = bar.getCacheConfig().entrySet().iterator(); it.hasNext();) {
                final Map.Entry entry = (Map.Entry) it.next();
                if (entry.getValue() instanceof String) {
                    m_cacheConfig.put(entry.getKey(), gvs.substituteVariables(entry.getValue().toString()).toString());
                } else {
                    m_cacheConfig.put(entry.getKey(), entry.getValue());
                }
            }
        }
        return this.m_cacheConfig;
    }

    public String getConfigXml(String uri) throws Exception {
        String strXml = null;
        byte[] data = null;

        // Check for File Path
        if (null != uri && uri.length() > 0) {
            final String m_cacheConfigURI = mrsp.getProject().getGlobalVariables()
                    .substituteVariables(uri).toString();
            this.getLogger().log(Level.DEBUG, "Initializing Cache using config file URI: %s", m_cacheConfigURI);
            File file = new File(m_cacheConfigURI);
            if (file.exists()) {
                FileInputStream fis = null;
                fis = new FileInputStream(file);
                DataInputStream dis = new DataInputStream(fis);
                data = new byte[dis.available()];
                dis.readFully(data);
                strXml = new String(data);
            } else
                throw new Exception("DefaultDistributedCacheBasedStore:Cache config file not found :" + m_cacheConfigURI);
        }
        return strXml;
    }

    protected void initConfig(String configXml) throws Exception {
        XmlDocument xmlDoc = null;
        ConfigurableCacheFactory cfactory;

        PrintStream stdout = System.out;
        PrintStream stderr = System.err;
        ByteArrayOutputStream os = new ByteArrayOutputStream(8192);
        boolean bCacheDump = Boolean.valueOf(
                System.getProperty("be.cache.dump", "false"))
                .booleanValue();
        try {
            PrintStream ps = new PrintStream(os);
            if (!bCacheDump) {
                System.setOut(ps);
                System.setErr(ps);
            }
            if (configXml != null && configXml != "")
                xmlDoc = XmlHelper.loadXml(configXml);

            if (xmlDoc != null) {

                cfactory = new DefaultConfigurableCacheFactory(xmlDoc);
                CacheFactory.setConfigurableCacheFactory(cfactory);
            } else {
                // use the default configuration from the the coherence.jar
                this.getLogger().log(Level.DEBUG, "Using default coherence-cache-config.xml from coherence.jar");
                cfactory = new DefaultConfigurableCacheFactory();
                CacheFactory.setConfigurableCacheFactory(cfactory);
            }
            //cfactory.
            if (this.getLogger().isEnabledFor(Level.DEBUG)) {
                XmlElement el = cfactory.getConfig().findElement("//caching-scheme-mapping");
                Iterator it = el.getElementList().listIterator();
                this.getLogger().log(Level.DEBUG,
                        "--------------------------cache-scheme-mapping--------------------------");
                while (it != null && it.hasNext()) {
                    XmlElement element = (XmlElement) it.next();
                    this.getLogger().log(Level.DEBUG, "%s :: %s",
                            element.findElement("cache-name"), element.findElement("scheme-name"));
                }

                this.getLogger().log(Level.DEBUG,
                        "-------------------------------------------------------------------------");
            }
            CacheFactory.getCluster().setContextClassLoader((ClassLoader) mrsp.getTypeManager());
        } catch (Exception e) {
            if (!bCacheDump) {
                e.printStackTrace(stdout);
                throw new RuntimeException(os.toString());
            } else {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        } finally {
            if (!bCacheDump) {
                System.setOut(stdout);
                System.setErr(stderr);
            }
        }

    }

    public abstract void init() throws Exception;

    public NamedCache getRecoveryCache() {        
        return this.m_recoveryCache;
    }
}
