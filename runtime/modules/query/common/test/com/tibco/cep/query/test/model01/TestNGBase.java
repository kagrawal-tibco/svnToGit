package com.tibco.cep.query.test.model01;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Nov 3, 2007
 * Time: 9:33:18 PM
 * To change this template use File | Settings | File Templates.
 */

import com.tibco.cep.query.service.QuerySession;
import com.tibco.cep.query.service.impl.QueryServiceProviderImpl;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.session.RuleSession;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

@Test
public abstract class TestNGBase {

    public static final String ROOT_TEST_DIRECTORY_PATH = "Q:/be/query/orion/query/testprojects/";

    protected static RuleSession ruleSession;
    protected static QuerySession querySession;


    public TestNGBase() {
        super();
    }


    public TestNGBase(String name) {
    }


    /**
     * Loads the bundle, ruleSession (with data) and querySession.
     *
     * @throws Exception
     */
//    @BeforeSuite
    public void setUpClass() throws Exception {
        // Gets the rule session
        final String repoUrl = this.getTestDirectory().getCanonicalPath() + "/" + this.getTestDirectoryName() + ".ear";
        final String traFile = this.getTestDirectory().getCanonicalPath() + "/be-engine.tra";
        final String rspName = this.getRuleServiceProviderName();
        final String ruleSessionName = this.getRuleSessionName();
        final Properties env = new Properties();
        env.put("tibco.repourl", repoUrl);
        env.put("be.bootstrap.property.file", traFile);
        final RuleServiceProvider rsp = RuleServiceProviderManager.getInstance().newProvider(rspName, env);
        rsp.configure(RuleServiceProvider.MODE_API);
        ruleSession = rsp.getRuleRuntime().getRuleSession(ruleSessionName);

        // Loads data into the rule session.
        this.populateRuleSession();

        // Gets the query session.
        final QueryServiceProviderImpl qsp = new QueryServiceProviderImpl(rsp);
        querySession = qsp.getQuerySession(this.ruleSession.getName());
    }


    /**
     * Shuts the RSP down.
     *
     */
//    @AfterSuite
    public static void tearDownClass() throws Exception {
        ruleSession.getRuleServiceProvider().shutdown();
        RuleServiceProviderManager.getInstance().removeProvider(getRuleSession().getRuleServiceProvider().getName());
    }

    /**
     * Override to populate the RuleSession.
     *
     * @throws Exception
     */
    protected void populateRuleSession() throws Exception {
    }


    protected static RuleSession getRuleSession() {
        return ruleSession;
    }


    protected abstract String getRuleSessionName();


    protected abstract String getRuleServiceProviderName();


    protected abstract String getTestDirectoryName();


    protected File getTestDirectory() throws IOException {
        final File dir = new File(ROOT_TEST_DIRECTORY_PATH, this.getTestDirectoryName());
        if (!(dir.exists() && dir.isDirectory())) {
            throw new IOException("Invalid test directory name");
        }
        return dir;
    }


    protected static QuerySession getQuerySession() {
        return querySession;
    }


}

