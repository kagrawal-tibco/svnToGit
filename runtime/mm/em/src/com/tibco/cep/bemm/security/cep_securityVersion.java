/*
 * $HeadURL: http://svn.tibco.com/be/trunk//leo/security/src/com/tibco/cep/security/cep_securityVersion.tag $ $Revision: 27349 $ $Date: 2010-01-08 16:48:26 -0800 (Fri, 08 Jan 2010) $
 *
 * Copyright(c) 2004-2011 TIBCO Software Inc. All rights reserved.
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
        static final public String copyright       = "Copyright 2004-2011 TIBCO Software Inc. All rights reserved.";
        static final public String line_separator  = System.getProperty("line.separator");
        static final public String version         = "5.0.0";
        static final public String engineMinVersion = "5.0.0.0";
        static final public String engineName      = "be-engine";
        static final public String build           = "073";
        static final public String buildDate       = "2011-04-08";
        static final public String container_id    = "be-engine";
        static final private String company        = "TIBCO Software Inc.";
        static final private String component      = "TIBCO BusinessEvents";
        static final private String license        = "";

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
