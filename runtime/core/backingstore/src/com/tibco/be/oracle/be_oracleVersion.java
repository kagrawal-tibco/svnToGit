/*
 * $HeadURL$ $Revision$ $Date$
 *
 * Copyright(c) 2004-2012 TIBCO Software Inc. All rights reserved.
 *
 * be-oracle.jar Version Information
 *
 */

/*





        AUTOMATICALLY GENERATED AT BUILD TIME !!!!

        DO NOT EDIT !!!





 * "be_oracleVersion.java" is automatically generated at
 * build time from "be_oracleVersion.tag"
 *
 * Any maintenance changes MUST be applied to "be_oracleVersion.tag"
 * and an official build triggered to propagate such changes to
 * "be_oracleVersion.java"
 *
 * If maintenance changes must be applied immediately without going
 * through an official build, then they MUST be applied to *BOTH*
 * "be_oracleVersion.tag" *AND* "be_oracleVersion.java"
 *
 */

package com.tibco.be.oracle;

public final class be_oracleVersion {
        static final public String asterisks       = "**********************************************************************";
        static final public String copyright       = "Copyright(c) 2004-2019 TIBCO Software Inc. All rights reserved.";
        static final public String line_separator  = System.getProperty("line.separator");
        static final public String version = "6.0.0";
        static final public String build = "040";
        static final public String buildDate = "2020-02-05";
        static final private String company = "TIBCO Software Inc.";
        static final private String component = "TIBCO BusinessEvents";
        static final private String license = "*** !!! NOT FOR PRODUCTION USE !!! ***";

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
