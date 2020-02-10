package com.tibco.cep.repo.provider;

import java.net.URL;
import java.util.List;

import com.tibco.cep.repo.ArchiveResourceProvider;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Jul 1, 2006
 * Time: 10:26:54 AM
 * To change this template use File | Settings | File Templates.
 */

/**
 * Jar resource Provider
 */
public interface JavaArchiveResourceProvider
        extends ArchiveResourceProvider {

	boolean isOpen();
    
    void close();
    
    List<URL> getJarResourceURLS();
}
