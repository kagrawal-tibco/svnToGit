package com.tibco.cep.releases.be;

import com.tibco.cep.vcs.VcsBranchContext;
import com.tibco.cep.vcs.VcsConnection;
import com.tibco.cep.vcs.VcsConnectionFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * User: nprade
 * Date: 6/30/11
 * Time: 2:07 PM
 */
public class BeVcsBranchContext
        implements VcsBranchContext {


    public static final String BRANCH_PREFIX;
    public static final String CONFIG_FILE = "be-vcs.properties";
    public static final String DEFAULT_BRANCH;
    public static final String DEFAULT_CONNECTION_URL;

    static {
        final Properties config = new Properties();
        final InputStream configSource = BeVcsBranchContext.class.getClassLoader().getResourceAsStream(CONFIG_FILE);
        if (null != configSource) {
            try {
                try {
                    config.load(configSource);
                } finally {
                    configSource.close();
                }
            } catch (IOException e) {
                    throw new RuntimeException(e);
            }
        }

        DEFAULT_BRANCH = config.getProperty("com.tibco.cep.releases.vcs.branch.default");
        BRANCH_PREFIX = config.getProperty("com.tibco.cep.releases.vcs.branch.prefix");
        DEFAULT_CONNECTION_URL = config.getProperty("com.tibco.cep.releases.vcs.url.default");
    }


    private final String branchPath;
    private final String connectionUrl;
    private final VcsConnectionFactory vcsConnectionFactory;


    public BeVcsBranchContext() {
        this(DEFAULT_CONNECTION_URL, DEFAULT_BRANCH);
    }


    public BeVcsBranchContext(
            String connectionUrl) {

        this(connectionUrl, DEFAULT_BRANCH);
    }


    public BeVcsBranchContext(
            String connectionUrl,
            String branchPath) {

        this.branchPath = (null == branchPath)
                ? BRANCH_PREFIX + DEFAULT_BRANCH
                : (branchPath.startsWith(BRANCH_PREFIX) ? branchPath : BRANCH_PREFIX + branchPath);

        this.connectionUrl = (null == connectionUrl) ? DEFAULT_CONNECTION_URL : connectionUrl;
        this.vcsConnectionFactory = new VcsConnectionFactory();
    }


    @Override
    public String getBranchPath() {

        return this.branchPath;
    }

    @Override
    public String getConnectionUrl() {

        return this.connectionUrl;
    }


    @Override
    public VcsConnection makeConnection()
            throws Exception {

        return this.vcsConnectionFactory.create(this.connectionUrl);
    }

}
