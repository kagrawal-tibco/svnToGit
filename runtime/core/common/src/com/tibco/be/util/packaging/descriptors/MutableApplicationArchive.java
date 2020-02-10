package com.tibco.be.util.packaging.descriptors;
/*
 * User: nprade
 * Date: Feb 4, 2010
 * Time: 5:11:56 PM
 */


public interface MutableApplicationArchive
        extends ApplicationArchive, MutableServiceArchive {


    MutableServiceArchive addServiceArchive(String name);

    void addServiceArchive(ServiceArchive serviceArchive);

    void removeServiceArchive(ServiceArchive serviceArchive);


}
