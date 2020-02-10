/*
 * $HeadURL: http://svn.tibco.com/be/branches/5.0/studio/functions/src/com/tibco/be/functions/be_coherencefunctionsVersion.tag $ $Revision: 27349 $ $Date: 2010-01-08 16:48:26 -0800 (Fri, 08 Jan 2010) $
 *
 * Copyright(c) 2004-2011 TIBCO Software Inc. All rights reserved.
 *
 * be-coherencefunctions.jar Version Information
 *
 */

/*





        AUTOMATICALLY GENERATED AT BUILD TIME !!!!

        DO NOT EDIT !!!





 * "be_coherencefunctionsVersion.java" is automatically generated at
 * build time from "be_coherencefunctionsVersion.tag"
 *
 * Any maintenance changes MUST be applied to "be_coherencefunctionsVersion.tag"
 * and an official build triggered to propagate such changes to
 * "be_coherencefunctionsVersion.java"
 *
 * If maintenance changes must be applied immediately without going
 * through an official build, then they MUST be applied to *BOTH*
 * "be_coherencefunctionsVersion.tag" *AND* "be_coherencefunctionsVersion.java"
 *
 */

package com.tibco.be.functions;

public final class be_coherencefunctionsVersion {
        static final public String asterisks       = "**********************************************************************";
        static final public String copyright       = "Copyright 2004-2011 TIBCO Software Inc. All rights reserved.";
        static final public String line_separator  = System.getProperty("line.separator");
        static final public String version = "5.0.0";
        static final public String build = "058";
        static final public String buildDate = "2011-03-17";
        static final private String company = "TIBCO Software Inc.";
        static final private String component = "TIBCO BusinessEvents";
        static final private String license = "";

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

