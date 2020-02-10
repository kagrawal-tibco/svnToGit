/*
 * $HeadURL: http://svn.tibco.com/be/trunk/leo/dashboard/metricengine/src/com/tibco/cep/metric/cep_metricVersion.tag $ $Revision: 27480 $ $Date: 2010-01-12 17:33:46 -0800 (Tue, 12 Jan 2010) $
 *
 * Copyright(c) 2007-2012 TIBCO Software Inc. All rights reserved.
 *
 * cep-metric.jar Version Information
 *
 */

/*
 * AUTOMATICALLY GENERATED AT BUILD TIME !!!! 
 * DO NOT EDIT !!!
 * 
 * "cep_metricVersion.java" is automatically generated at build time 
 * from "cep_metricVersion.tag"
 *
 * Any maintenance changes MUST be applied to "cep_metricVersion.tag"
 * and an official build triggered to propagate such changes to
 * "cep_metricVersion.java"
 *
 * If maintenance changes must be applied immediately without going
 * through an official build, then they MUST be applied to *BOTH*
 * "cep_metricVersion.tag" *AND* "cep_metricVersion.java"
 *
 */

package com.tibco.cep.metric;

public final class cep_metricVersion {

    static final public String asterisks       		= "**********************************************************************";
    static final public String copyright       		= "Copyright(c) 2004-2019 TIBCO Software Inc. All rights reserved.";
    static final public String line_separator  		= System.getProperty("line.separator");
    static final public String version         		= "6.0.0";
    static final public String build          		= "040";
    static final public String buildDate       		= "2020-02-05";
    static final public String container_id    		= "be-engine";
    static final public String container_version 	= "6.0.0.0";
    static final private String company        		= "TIBCO Software Inc.";
    static final private String component     		= "TIBCO BusinessEvents";
    static final private String license        		= "*** !!! NOT FOR PRODUCTION USE !!! ***";

    static public String getVersion() {
            return "Version " + version + "." + build + ", " + buildDate;
    }

    static public String getCompany() {
            return company;
    }

    static public String getComponent() {
            return component;
    }

    static public void main(String[] args) {
            System.out.println(getCompany() + " - " + getComponent() + " " + getVersion() + " " + getLicense());
    }

    static public String getLicense() {
            return license;
    }
}
