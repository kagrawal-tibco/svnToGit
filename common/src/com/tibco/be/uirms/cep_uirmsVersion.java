/*
 * $HeadURL: https://svn.tibco.com/svn/be/trunk/be-main/common/src/com/tibco/be/uirms/cep_uirmsVersion.tag $ $Revision: 48199 $ $Date: 2012-02-16 22:27:01 -0500 (Thu, 16 Feb 2012) $
 *
 * Copyright(c) 2007-2012 TIBCO Software Inc. All rights reserved.
 *
 *
 */

/*





        AUTOMATICALLY GENERATED AT BUILD TIME !!!!

        DO NOT EDIT !!!





 * "cep_buirmsVersion.java" is automatically generated at
 * build time from "cep_buirmsVersion.tag"
 *
 * Any maintenance changes MUST be applied to "cep_buirmsVersion.tag"
 * and an official build triggered to propagate such changes to
 * "cep_buirmsVersion.java"
 *
 * If maintenance changes must be applied immediately without going
 * through an official build, then they MUST be applied to *BOTH*
 * "cep_uirmsVersion.tag" *AND* "cep_uirmsVersion.java"
 *
 */

package com.tibco.be.uirms;

public final class cep_uirmsVersion {
        static final public String asterisks       = "**********************************************************************";
        static final public String copyright       = "Copyright(c) 2004-2019 TIBCO Software Inc. All rights reserved.";
        static final public String line_separator  = System.getProperty("line.separator");
        static final public String version         = "6.0.0";
        static final public String build           = "040";
        static final public String buildDate       = "2020-02-05";
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
