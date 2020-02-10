package com.tibco.be.migration.expimp;

import java.util.Properties;

import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.repo.Project;
import com.tibco.cep.runtime.session.RuleServiceProvider;



/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Feb 17, 2008
 * Time: 2:12:58 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ExpImpContext {


    /**
     * return the Project associated to the input ear
     * @return  Project
     */
    Project getProject();

    /**
     * return the be-engine project name from the EAR.
     * @return
     */
    String getProjectName();

    /**
     * return the be-engine version from the EAR.
     * @return  String
     */
    String getComponentVersion();

    /**
     * return the Environment Properties associated to the migration.
     *
     * @return  Properties
     */
    Properties getEnvironmentProperties();

    /**
     * Provide Logger for logging facilities
     * @return  Logger
     */
    Logger getLogger();

    /**
     * Provide the input Path for BDB/Cache
     * for bdb - specify the file location of the BDB files
     * for cache - specify the location of the Cache Coherence Config file.
     * This method is used for Export purpose only
     * @return String
     */
    String getInputUrl();

    /**
     *Provide the output Path for BDB/Cache
     * for bdb - specify the file location of the BDB files to generate
     * for cache - specify the location of the Cache Coherence Config file.
     * This method is used for Export purpose only
     * @return String
     */
    String getOutputUrl();

    /**
     * Specify the method of import or export
     * The following two method of import or export are supported
     * BDB -> CSV
     * CACHE-> CSV
     * CSV -> BDB
     * CSV-> CACHE
     * @return  int
     */
    int getMethod();

    /**
     * return the commandline arguments that was passed to the migration framework.
     * Any extra processing that needs to be done by the specific plugin, are derived from
     * here.
     * @return String
     */
    String getCommandLineArguments();


    /**
     * return the BusinessEvent archive name specified in the arguments
     * @return  String
     */
    String getBarName();


    /**
     *
     * @return RuleServiceProvider
     */
    RuleServiceProvider getRuleServiceProvider();
}
