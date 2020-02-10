/*
 * $HeadURL: http://svn.tibco.com/be/branches/5.0/common/src/com/tibco/cep/cep_coherenceVersion.tag $ $Revision: 27349 $ $Date: 2010-01-08 16:48:26 -0800 (Fri, 08 Jan 2010) $
 *
 * Copyright(c) 2004-2012 TIBCO Software Inc. All rights reserved.
 *
 */

/*





        AUTOMATICALLY GENERATED AT BUILD TIME !!!!

        DO NOT EDIT !!!





 * "cep_datagrid_oracleVersion.java" is automatically generated at
 * build time from "cep_datagrid_oracleVersion.tag"
 *
 * Any maintenance changes MUST be applied to "cep_datagrid_oracleVersion.tag"
 * and an official build triggered to propagate such changes to
 * "cep_datagrid_oracleVersion.java"
 *
 * If maintenance changes must be applied immediately without going
 * through an official build, then they MUST be applied to *BOTH*
 * "cep_datagrid_oracleVersion.tag" *AND* "cep_datagrid_oracleVersion.java"
 *
 */

package com.tibco.cep;

public final class cep_datagrid_oracleVersion {
        static final public String asterisks       = "**********************************************************************";
        static final public String copyright       = "Copyright(c) 2004-2019 TIBCO Software Inc. All rights reserved.";
        static final public String line_separator  = System.getProperty("line.separator");
        static final public String version         = "6.0.0";
        static final public String engineMinVersion = "6.0.0.0";
        static final public String engineName      = "be-engine";
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
