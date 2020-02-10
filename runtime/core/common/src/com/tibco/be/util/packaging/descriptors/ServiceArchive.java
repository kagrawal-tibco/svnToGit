package com.tibco.be.util.packaging.descriptors;
/*
 * User: nprade
 * Date: Feb 4, 2010
 * Time: 4:06:40 PM
 */


import java.util.Date;
import java.util.List;

import com.tibco.objectrepo.vfile.VFileFactory;
import com.tibco.xml.data.primitive.ExpandedName;


public interface ServiceArchive {


    DeploymentDescriptor getDeploymentDescriptorByName(ExpandedName xmlType, String name);

    List<DeploymentDescriptor> getDeploymentDescriptors(ExpandedName xmlType);

    List<DeploymentDescriptor> getDeploymentDescriptors();
    
    ApplicationArchive getApplicationArchive();

    String getName();

    String getFileName();

    String getDescription();

    String getVersion();

    String getOwner();

    Date getCreationDate();

    Date getLastModificationDate();

    VFileFactory getVFileFactory();

    void destroy();

//    ClassLoader getClassLoader();
//
//    boolean isConfigurationNecessary();
//
//    boolean doesContentEqual(ServiceArchive servicearchive)
//            throws IOException, ObjectRepoException;
//
//    String diffServiceArchive(ServiceArchive servicearchive)
//            throws IOException, ObjectRepoException;
//
//    String diffServiceArchive(ServiceArchive servicearchive, boolean flag)
//            throws IOException, ObjectRepoException;
//
//    String diffServiceArchiveExtended(ServiceArchive servicearchive)
//            throws IOException, ObjectRepoException;
//
//    String diffServiceArchiveExtended(ServiceArchive servicearchive, boolean flag)
//            throws IOException, ObjectRepoException;
//
//    List getTabIDs()
//            throws SAXException, IOException, ObjectRepoException;
//
//    String getTabDisplayName(Locale locale, String s);
//
//    List getDeploymentDescriptorsByTabName(String s)
//            throws SAXException, IOException, ObjectRepoException;
//
//    Map diffServiceArchiveContents(ServiceArchive servicearchive)
//            throws IOException, ObjectRepoException;
//
//    VFileFactory getClassLoaderExtensionFile(String s)
//            throws ObjectRepoException;
//
//    NameValuePairs getServiceInstanceVars(String s);

}
