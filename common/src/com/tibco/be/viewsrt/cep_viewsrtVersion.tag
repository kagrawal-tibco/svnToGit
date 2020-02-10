/*
 * $HeadURL: http://svn.tibco.com/be/branches/5.0/ui-rt-common/src/com/tibco/be/uirms/cep_uirmsVersion.tag $ $Revision: 37665 $ $Date: 2010-12-06 12:32:17 -0800 (Mon, 06 Dec 2010) $
 *
 * Copyright(c) 2007-2012 TIBCO Software Inc. All rights reserved.
 *
 *
 */

/*





        AUTOMATICALLY GENERATED AT BUILD TIME !!!!

        DO NOT EDIT !!!





 * "cep_viewsrtVersion.java" is automatically generated at
 * build time from "cep_viewsrtVersion.tag"
 *
 * Any maintenance changes MUST be applied to "cep_viewsrtVersion.tag"
 * and an official build triggered to propagate such changes to
 * "cep_viewsrtVersion.java"
 *
 * If maintenance changes must be applied immediately without going
 * through an official build, then they MUST be applied to *BOTH*
 * "cep_viewsrtVersion.tag" *AND* "cep_viewsrtVersion.java"
 *
 */

package com.tibco.be.viewsrt;

public final class cep_viewsrtVersion {
        static final public String asterisks       = "**********************************************************************";
        static final public String copyright       = "@BE_COPYRIGHT@";
        static final public String line_separator  = System.getProperty("line.separator");
        static final public String version         = "@BE_VERSION@";
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
