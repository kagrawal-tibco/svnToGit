/*
 * $HeadURL:  $ $Revision:  $ $Date: $
 *
 * Copyright(c) 2007-2012 TIBCO Software Inc. All rights reserved.
 *
 * cep-modules.jar Version Information
 *
 */

/*





 AUTOMATICALLY GENERATED AT BUILD TIME !!!!

 DO NOT EDIT !!!





 * "cep_asChannelVersion.java" is automatically generated at
 * build time from "cep_asChannelVersion.tag"
 *
 * Any maintenance changes MUST be applied to "cep_asChannelVersion.tag"
 * and an official build triggered to propagate such changes to
 * "cep_asChannelVersion.java"
 *
 * If maintenance changes must be applied immediately without going
 * through an official build, then they MUST be applied to *BOTH*
 * "cep_asChannelVersion.tag" *AND* "cep_asChannelVersion.java"
 *
 */

package com.tibco.cep.driver.ftl;

public final class cep_ftl_channelVersion {
    static final public String asterisks       = "**********************************************************************";
    static final public String copyright       = "Copyright(c) 2004-2013 TIBCO Software Inc. All rights reserved.";
    static final public String line_separator  = System.getProperty("line.separator");
    static final public String version         = "5.2.0";
    static final public String build           = "319";
    static final public String buildDate       = "2015-1-26";
    static final public String container_id    = "be-engine";
    static final private String company        = "TIBCO Software Inc.";
    static final private String component      = "TIBCO BusinessEvents";
    static final private String license        = "*** !!! NOT FOR PRODUCTION USE !!! ***";
    
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
