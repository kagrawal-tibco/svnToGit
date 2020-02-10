package com.tibco.cep.runtime.service.dao.impl.tibas.backingstore;

import com.tibco.be.util.FileUtils;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.dao.impl.tibas.ASConstants;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSessionManager;

/**
 * Created by IntelliJ IDEA.
 * User: pgowrish
 * Date: 4/9/12
 * Time: 4:26 PM
 */
public class BEDataGridSharedNothingStore implements SharedNothingStore {

    private String sharedNothingPath;

    private Logger logger = LogManagerFactory.getLogManager().getLogger(BEDataGridSharedNothingStore.class);
    private String cddRawPath;
    private RuleServiceProvider rsp;

    public BEDataGridSharedNothingStore() {
        this.rsp = RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider();
    }

    public BEDataGridSharedNothingStore(RuleServiceProvider rsp, String path) {
        this.cddRawPath = path;
        this.rsp = rsp;
    }

    private boolean prepareSharedNothingPath() {
        if (cddRawPath != null) {
            logger.log(Level.INFO, "Raw sharedNothingPath from cdd is %s", cddRawPath);
            sharedNothingPath = this.rsp.getGlobalVariables().substituteVariables(cddRawPath).toString();
            sharedNothingPath = sharedNothingPath.replaceAll("%%EngineName%%", rsp.getName());
            if (System.getProperty("os.name").startsWith("Windows")) {
                sharedNothingPath = sharedNothingPath.replace("/","\\");
            }
            logger.log(Level.INFO, "Setting sharedNothingPath to %s", sharedNothingPath);
        } else {
            //default to this path
            // %%USER_HOME%%/be/datastore/%%EngineName%%
            String user_home = System.getProperty("user.home");
            String slash = "";

            if (System.getProperty("os.name").startsWith("Windows")) {
                slash = "\\";
            } else {
                slash = "/";
            }
            sharedNothingPath =  user_home + slash +  ASConstants.be + slash + ASConstants.datastore + slash + rsp.getName() ;
            logger.log(Level.INFO, "Defaulting sharedNothingPath to %s", sharedNothingPath);
        }

        // Create the directory hierarchy if it does not exist
        FileUtils.createWritableDirectory(sharedNothingPath);
        return true;
    }

    public String getSharedNothingPath() {
        if (sharedNothingPath == null) {
            prepareSharedNothingPath();
        }
        return sharedNothingPath;
    }
}
