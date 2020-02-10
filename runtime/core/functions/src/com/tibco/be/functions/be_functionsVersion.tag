/*
 * $HeadURL$ $Revision$ $Date$
 *
 * Copyright(c) 2004-2012 TIBCO Software Inc. All rights reserved.
 *
 * be-functions.jar Version Information
 *
 */

/*





        AUTOMATICALLY GENERATED AT BUILD TIME !!!!

        DO NOT EDIT !!!





 * "be_functionsVersion.java" is automatically generated at
 * build time from "be_functionsVersion.tag"
 *
 * Any maintenance changes MUST be applied to "be_functionsVersion.tag"
 * and an official build triggered to propagate such changes to
 * "be_functionsVersion.java"
 *
 * If maintenance changes must be applied immediately without going
 * through an official build, then they MUST be applied to *BOTH*
 * "be_functionsVersion.tag" *AND* "be_functionsVersion.java"
 *
 */

package com.tibco.be.functions;

public final class be_functionsVersion {
        static final public String asterisks       = "**********************************************************************";
        static final public String copyright       = "@BE_COPYRIGHT@";
        static final public String line_separator  = System.getProperty("line.separator");
        static final public String version = "@BE_VERSION@";
        static final public String build = "@BE_BUILD@";
        static final public String buildDate = "@BE_DATE@";
        static final private String company = "@BE_COMPANY@";
        static final private String component = "@BE_PRODUCT@";
        static final private String license = "@BE_LICENSE@";

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

