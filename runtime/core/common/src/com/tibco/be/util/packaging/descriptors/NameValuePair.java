package com.tibco.be.util.packaging.descriptors;

/*
 * User: nprade
 * Date: Feb 4, 2010
 * Time: 4:21:11 PM
 */


public interface NameValuePair
        extends DeploymentDescriptor {


    String getValue();

    void setValue(String s);

}
