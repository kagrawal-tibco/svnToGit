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
    static final public String copyright       		= "@BE_COPYRIGHT@";
    static final public String line_separator  		= System.getProperty("line.separator");
    static final public String version         		= "@BE_VERSION@";
    static final public String build          		= "@BE_BUILD@";
    static final public String buildDate       		= "@BE_DATE@";
    static final public String container_id    		= "@BE_CONTAINER_ID@";
    static final public String container_version 	= "@BE_CONTAINER_MINIMUM_VERSION@";
    static final private String company        		= "@BE_COMPANY@";
    static final private String component     		= "@BE_PRODUCT@";
    static final private String license        		= "@BE_LICENSE@";

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
