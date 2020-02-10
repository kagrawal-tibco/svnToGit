package com.tibco.be.migration.expimp;

import java.util.Properties;

import com.tibco.be.migration.BEMigrationConstants;
import com.tibco.be.migration.BEMigrationProperties;
import com.tibco.be.migration.EnvironmentInfo;
import com.tibco.cep.repo.Project;
import com.tibco.cep.repo.provider.GlobalVariablesProvider;
import com.tibco.cep.runtime.session.RuleServiceProvider;



/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Feb 17, 2008
 * Time: 6:57:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultExpImpContext implements ExpImpContext{

    RuleServiceProvider mrsp;
    Properties envProperties;    
    String barName;


    public DefaultExpImpContext(RuleServiceProvider rsp, Properties envProperties) throws Exception {
        this.mrsp = rsp;
        this.envProperties = envProperties;
        this.barName = envProperties.getProperty(BEMigrationConstants.PROP_DB_CONN_STR);
    }

    public String getProjectName() {
        GlobalVariablesProvider gvp = (GlobalVariablesProvider) mrsp.getGlobalVariables();
        String name = gvp.getProjectName();
        return name;
    }

    public String getComponentVersion() {
        GlobalVariablesProvider gvp = (GlobalVariablesProvider) mrsp.getGlobalVariables();
        String versionKey = gvp.getPackagedComponentVersion("be-engine");
        return versionKey = versionKey.equals("1.4.0.0")? "1.4.0" : versionKey;
    }

    public Properties getEnvironmentProperties() {
        return envProperties;
    }

    public com.tibco.cep.kernel.service.logging.Logger getLogger() {
        return this.mrsp.getLogger(DefaultExpImpContext.class);
    }

    public Project getProject() {
        return mrsp.getProject();
    }

    public String getBarName() {
        return this.barName;
    }

    public String getCommandLineArguments() {
        return EnvironmentInfo.getDefault().getCommandLineArgs2String();
    }

    public String getInputUrl() {
        return envProperties.getProperty(BEMigrationProperties.PROP_INPUT_URL);
    }

    public int getMethod() {
        String method = envProperties.getProperty(BEMigrationProperties.PROP_EXPIMP_METHOD);
        if ((method == null) || BEMigrationConstants.BDB.equalsIgnoreCase(method)) return BEMigrationConstants.IMPEXP_METHOD_BDB;
        if (BEMigrationConstants.DATABASE.equalsIgnoreCase(method)) return BEMigrationConstants.IMPEXP_METHOD_DB;

        return BEMigrationProperties.IMPEXP_METHOD_BDB; //default method
    }

    public String getOutputUrl() {
        return envProperties.getProperty(BEMigrationProperties.PROP_OUTPUT_URL);
    }

    public RuleServiceProvider getRuleServiceProvider() {
        return this.mrsp;
    }

}
