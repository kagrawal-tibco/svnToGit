package com.tibco.cep.repo.provider;


import java.util.Collection;
import java.util.Map;

import com.tibco.be.util.BEJarVersionsInspector;
import com.tibco.cep.repo.ArchiveResourceProvider;
import com.tibco.cep.repo.BEArchiveResource;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Jul 1, 2006
 * Time: 10:25:06 AM
 * To change this template use File | Settings | File Templates.
 */


/**
 * Resource Provider for .bar file
 */
public interface BEArchiveResourceProvider
        extends ArchiveResourceProvider {


    Collection<BEArchiveResource> getBEArchives();


    Map<String, BEJarVersionsInspector.Version> getDesignTimeVersions();

}

