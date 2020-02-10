package com.tibco.cep.repo;


import java.util.List;
import java.util.Properties;
import java.util.Set;

import com.tibco.be.util.packaging.Constants;

/*
* User: ssubrama
* Date: Jul 1, 2006
* Time: 7:14:23 AM
*/

public interface BEArchiveResource {

    /**
     * Return the name of the archive
     * @return name of the archive
     */
    String getName();


    /**
     * Return a Set of String rule uris
     * @return Set of String rule uris
     */
    Set<String> getDeployedRuleUris();


    /**
     * Return a collection of enabled Input Destinations in InputDestinationConfig form
     * @return collection of enabled Input Destinations
     */
    Set<ArchiveInputDestinationConfig> getInputDestinations();

    /**
     * Return the configuration of Object Manager
     * @return  Object Manager's Configuration
     */
    Properties getCacheConfig();

    List<String> getStartupFunctionUris();
    List<String> getShutdownFunctionUris();
    
    Constants.ArchiveType getType();

    boolean isCacheEnabled(GlobalVariables gv);

    String getReferenceClassName();

    /**
     * Sets the name of the archive
     * @param name of the archive
     */
	void setName(String name);

}
