/*
 * $HeadURL: http://svn.tibco.com/be/trunk//leo/security/src/com/tibco/cep/security/cep_securityVersion.tag $ $Revision: 27349 $ $Date: 2010-01-08 16:48:26 -0800 (Fri, 08 Jan 2010) $
 *
 * Copyright(c) 2004-2012 TIBCO Software Inc. All rights reserved.
 *
 * cep-security.jar Version Information
 *
 */

/*
 * AUTOMATICALLY GENERATED AT BUILD TIME !!!! 
 * DO NOT EDIT !!!
 * 
 * "cep_securityVersion.java" is automatically generated at build time 
 * from "cep_securityVersion.tag"
 *
 * Any maintenance changes MUST be applied to "cep_securityVersion.tag"
 * and an official build triggered to propagate such changes to
 * "cep_securityVersion.java"
 *
 * If maintenance changes must be applied immediately without going
 * through an official build, then they MUST be applied to *BOTH*
 * "cep_securityVersion.tag" *AND* "cep_securityVersion.java"
 *
 */

package com.tibco.cep.bemm.security;

public final class cep_securityVersion {
        static final public String asterisks       = "**********************************************************************";
        static final public String copyright       = "@BE_COPYRIGHT@";
        static final public String line_separator  = System.getProperty("line.separator");
        static final public String version         = "@BE_VERSION@";
        static final public String engineMinVersion = "@BE_CONTAINER_MINIMUM_VERSION@";
        static final public String engineName      = "@BE_CONTAINER_ID@";
        static final public String build           = "@BE_BUILD@";
        static final public String buildDate       = "@BE_DATE@";
        static final public String container_id    = "@BE_CONTAINER_ID@";
        static final private String company        = "@BE_COMPANY@";
        static final private String component      = "@BE_PRODUCT@";
        static final private String license        = "@BE_LICENSE@";

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
