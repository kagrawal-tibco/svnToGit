package com.tibco.cep.repo;

import java.util.Collection;

import com.tibco.xml.datamodel.XiNode;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Jul 1, 2006
 * Time: 6:53:06 AM
 * To change this template use File | Settings | File Templates.
 */

/**
 * An Archive Resource corresponds to an Archive in a DeployedProject. This usually done during the
 * packaging process in TIBCO Designer. The process is to create an Enterprise Archive(.ear) and then
 * add individual Products module archive. Please Refer TIBCO BusinessEvent's user guide to create an
 * Archive.
 * Currently TIBCO EnterpriseArchive(.ear) Solution provides 3 types of Archive
 * a.> Shared Archive Resource (.sar)
 * b.> BusinessWorks Archive Resource (.par)
 * c.> BusinessEvents Archive Resource(.bar)
 * c.1> BusinessEvents also supports Java Archive Resource, and is loaded by own Classloader.
 */
public interface ArchiveResourceProvider
        extends ResourceProvider {

    /**
     * Optional contract that can be implemented by individual Provide
     * @param uri
     * @return
     */
    XiNode getResourceAsXiNode(String uri)  throws Exception;

    /**
     * Optional contract that can be implemented by individual Provider
     * @param uri
     * @return
     */
    byte[] getResourceAsByteArray(String uri);

    /**
     * Return all the URI that are stored by the provider
     * @return
     */
    Collection getAllResourceURI();

    /**
     * free up the Provider, by removing the resources
     * @param uri
     */
    void removeResource(String uri);
}
